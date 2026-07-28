package com.metehan.vindexcore.dashboard.service;

import com.metehan.vindexcore.common.enums.TransactionType;
import com.metehan.vindexcore.dashboard.dto.DashboardSummaryDTO;
import com.metehan.vindexcore.transaction.mapper.TransactionMapper;
import com.metehan.vindexcore.transaction.model.Transaction;
import com.metehan.vindexcore.transaction.repository.TransactionRepository;
import com.metehan.vindexcore.transaction.repository.TransactionSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public DashboardSummaryDTO getSummary(YearMonth month) {
        YearMonth targetMonth = month != null ? month : YearMonth.now();
        LocalDate start = targetMonth.atDay(1);
        LocalDate end = targetMonth.atEndOfMonth();

        TransactionSearchCriteria criteria = new TransactionSearchCriteria(
                null, null, start, end, null, null, null
        );
        Page<Transaction> monthlyPage = transactionRepository.search(criteria, Pageable.unpaged());
        List<Transaction> monthlyTransactions = monthlyPage.getContent();

        BigDecimal totalIncome = sumByType(monthlyTransactions, TransactionType.INCOME);
        BigDecimal totalExpense = sumByType(monthlyTransactions, TransactionType.EXPENSE);

        List<Transaction> recent = transactionRepository.findTop10ByOrderByTransactionDateDesc();

        DashboardSummaryDTO summary = new DashboardSummaryDTO();
        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        summary.setBalance(totalIncome.subtract(totalExpense));
        summary.setRecentTransactions(transactionMapper.toDtoList(recent));

        return summary;
    }

    private BigDecimal sumByType(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .map(t -> BigDecimal.valueOf(t.getAmountCent(), 2))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

package com.metehan.vindexcore.dashboard.dto;

import com.metehan.vindexcore.transaction.dto.TransactionDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardSummaryDTO {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private List<TransactionDTO> recentTransactions;

}

package com.metehan.vindexcore.recurring.scheduler;

import com.metehan.vindexcore.recurring.model.Recurring;
import com.metehan.vindexcore.recurring.repository.RecurringRepository;
import com.metehan.vindexcore.transaction.model.Transaction;
import com.metehan.vindexcore.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecurringTransactionScheduler {

    private final RecurringRepository recurringRepository;
    private final TransactionRepository transactionRepository;

    @Scheduled(cron = "0 5 0 * * *")
    public void processDueRecurrings() {
        LocalDate today = LocalDate.now();
        List<Recurring> dueRecurrings = recurringRepository.findDueRecurrings(today);

        log.info("Processing {} due recurring(s) for {}", dueRecurrings.size(), today);

        for (Recurring recurring : dueRecurrings) {
            processRecurring(recurring, today);
        }
    }

    private void processRecurring(Recurring recurring, LocalDate today) {
        while (!recurring.getNextDueDate().isAfter(today)
                && (recurring.getEndDate() == null || !recurring.getNextDueDate().isAfter(recurring.getEndDate()))) {

            generateTransaction(recurring);
            recurring.setNextDueDate(recurring.getFrequency().next(recurring.getNextDueDate()));
        }

        recurringRepository.save(recurring);
    }

    private void generateTransaction(Recurring recurring) {
        Transaction transaction = new Transaction();
        transaction.setTitle(recurring.getTitle());
        transaction.setAmountCent(recurring.getAmountCent());
        transaction.setCategory(recurring.getCategory());
        transaction.setType(recurring.getType());
        transaction.setCurrency(recurring.getCurrency());
        transaction.setTransactionDate(recurring.getNextDueDate());
        transaction.setCreatedAt(Instant.now());
        transaction.setRecurringId(recurring.getId());

        transactionRepository.save(transaction);
        log.info("Generated transaction '{}' for recurring '{}' (due: {})",
                transaction.getTitle(), recurring.getTitle(), recurring.getNextDueDate());
    }

}

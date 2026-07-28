package com.metehan.vindexcore.transaction.repository;

import com.metehan.vindexcore.common.enums.TransactionCategory;
import com.metehan.vindexcore.common.enums.TransactionType;

import java.time.LocalDate;

public record TransactionSearchCriteria (
        TransactionCategory category,
        TransactionType type,
        LocalDate startDate,
        LocalDate endDate,
        Long minAmountCent,
        Long maxAmountCent,
        String keyword
) {}

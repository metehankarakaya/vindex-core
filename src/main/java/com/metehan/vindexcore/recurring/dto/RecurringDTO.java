package com.metehan.vindexcore.recurring.dto;

import com.metehan.vindexcore.common.enums.TransactionCategory;
import com.metehan.vindexcore.common.enums.TransactionType;
import com.metehan.vindexcore.recurring.model.Frequency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RecurringDTO {

    private String id;
    private String title;
    private BigDecimal amount;
    private TransactionCategory category;
    private TransactionType type;
    private String currency;
    private Frequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextDueDate;

}

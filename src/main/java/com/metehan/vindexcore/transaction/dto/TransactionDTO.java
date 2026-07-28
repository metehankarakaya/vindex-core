package com.metehan.vindexcore.transaction.dto;

import com.metehan.vindexcore.common.enums.TransactionCategory;
import com.metehan.vindexcore.common.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {

    private String id;
    private String title;
    private BigDecimal amount;
    private TransactionCategory category;
    private TransactionType type;
    private String currency;
    private LocalDate transactionDate;
    private String recurringId;

}

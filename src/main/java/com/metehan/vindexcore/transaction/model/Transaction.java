package com.metehan.vindexcore.transaction.model;

import com.metehan.vindexcore.common.enums.TransactionCategory;
import com.metehan.vindexcore.common.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    @Field
    private String title;

    @Field
    private Long amountCent;

    @Field
    private TransactionCategory category;

    @Field
    private TransactionType type;

    @Field
    private String currency;

    @Field
    private LocalDate transactionDate;

    @Field
    private Instant createdAt;

    @Field
    private String recurringId;

}

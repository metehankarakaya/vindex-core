package com.metehan.vindexcore.recurring.model;

import com.metehan.vindexcore.common.enums.TransactionCategory;
import com.metehan.vindexcore.common.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@Document(collection = "recurrings")
public class Recurring {

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
    private Frequency frequency;

    @Field
    private LocalDate startDate;

    @Field
    private LocalDate endDate;

    @Field
    private LocalDate nextDueDate;

}

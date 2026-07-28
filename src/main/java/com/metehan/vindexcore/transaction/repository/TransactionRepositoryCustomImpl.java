package com.metehan.vindexcore.transaction.repository;

import com.metehan.vindexcore.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public TransactionRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Transaction> search(TransactionSearchCriteria criteria, Pageable pageable) {
        List<Criteria> filters = new ArrayList<>();

        if (criteria.category() != null) {
            filters.add(Criteria.where("category").is(criteria.category()));
        }
        if (criteria.type() != null) {
            filters.add(Criteria.where("type").is(criteria.type()));
        }
        if (criteria.startDate() != null) {
            filters.add(Criteria.where("transactionDate").gte(criteria.startDate()));
        }
        if (criteria.endDate() != null) {
            filters.add(Criteria.where("transactionDate").lte(criteria.endDate()));
        }
        if (criteria.minAmountCent() != null) {
            filters.add(Criteria.where("amountCent").gte(criteria.minAmountCent()));
        }
        if (criteria.maxAmountCent() != null) {
            filters.add(Criteria.where("amountCent").lte(criteria.maxAmountCent()));
        }
        if (criteria.keyword() != null && !criteria.keyword().isBlank()) {
            filters.add(Criteria.where("title").regex(criteria.keyword(), "i"));
        }

        Query query = new Query();
        if (!filters.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(filters.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Transaction.class);

        if (pageable.isPaged()) {
            query.with(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        }

        Sort sort = pageable.getSort().isSorted()
          ? pageable.getSort()
          : Sort.by(Sort.Order.desc("transactionDate"), Sort.Order.desc("createdAt"));
        query.with(sort);

        List<Transaction> results = mongoTemplate.find(query, Transaction.class);

        return PageableExecutionUtils.getPage(results, pageable, () -> total);
    }
}

package com.metehan.vindexcore.transaction.repository;

import com.metehan.vindexcore.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepositoryCustom {
    Page<Transaction> search(TransactionSearchCriteria criteria, Pageable pageable);
}

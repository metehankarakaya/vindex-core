package com.metehan.vindexcore.transaction.service;

import com.metehan.vindexcore.common.exception.RecurringTransactionImmutableException;
import com.metehan.vindexcore.common.exception.TransactionNotFoundException;
import com.metehan.vindexcore.transaction.dto.TransactionDTO;
import com.metehan.vindexcore.transaction.mapper.TransactionMapper;
import com.metehan.vindexcore.transaction.model.Transaction;
import com.metehan.vindexcore.transaction.repository.TransactionRepository;
import com.metehan.vindexcore.transaction.repository.TransactionSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionDTO create(TransactionDTO dto) {
        Transaction entity = transactionMapper.toEntity(dto);
        entity.setId(null);
        entity.setRecurringId(null);
        entity.setCreatedAt(Instant.now());
        Transaction saved = transactionRepository.save(entity);
        return transactionMapper.toDTO(saved);
    }

    public TransactionDTO getById(String id) {
        Transaction entity = findEntityOrThrow(id);
        return transactionMapper.toDTO(entity);
    }

    public Page<TransactionDTO> search(TransactionSearchCriteria criteria, Pageable pageable) {
        return transactionRepository.search(criteria, pageable)
                .map(transactionMapper::toDTO);
    }

    public TransactionDTO update(String id, TransactionDTO dto) {
        Transaction existing = findEntityOrThrow(id);
        guardAgainstRecurringEdit(existing);

        Transaction updated = transactionMapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setRecurringId(null);

        Transaction saved = transactionRepository.save(updated);
        return transactionMapper.toDTO(saved);
    }

    public void delete(String id) {
        Transaction existing = findEntityOrThrow(id);
        guardAgainstRecurringEdit(existing);
        transactionRepository.deleteById(id);
    }

    private Transaction findEntityOrThrow(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    private void guardAgainstRecurringEdit(Transaction transaction) {
        if (transaction.getRecurringId() != null) {
            throw new RecurringTransactionImmutableException(transaction.getId());
        }
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }

}

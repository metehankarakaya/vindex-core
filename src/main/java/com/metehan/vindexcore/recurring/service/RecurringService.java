package com.metehan.vindexcore.recurring.service;

import com.metehan.vindexcore.common.exception.RecurringNotFoundException;
import com.metehan.vindexcore.recurring.dto.RecurringDTO;
import com.metehan.vindexcore.recurring.mapper.RecurringMapper;
import com.metehan.vindexcore.recurring.model.Recurring;
import com.metehan.vindexcore.recurring.repository.RecurringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringService {

    private final RecurringRepository recurringRepository;
    private final RecurringMapper recurringMapper;

    public RecurringDTO create(RecurringDTO dto) {
        Recurring entity = recurringMapper.toEntity(dto);
        entity.setId(null);
        entity.setNextDueDate(entity.getStartDate());
        Recurring saved = recurringRepository.save(entity);
        return recurringMapper.toDTO(saved);
    }

    public RecurringDTO getById(String id) {
        return recurringMapper.toDTO(findEntityOrThrow(id));
    }

    public List<RecurringDTO> getAll() {
        return recurringMapper.toDtoList(recurringRepository.findAll());
    }

    public RecurringDTO update(String id, RecurringDTO dto) {
        Recurring existing = findEntityOrThrow(id);

        Recurring updated = recurringMapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setNextDueDate(existing.getNextDueDate());

        Recurring saved = recurringRepository.save(updated);
        return recurringMapper.toDTO(saved);
    }

    public void delete(String id) {
        findEntityOrThrow(id);
        recurringRepository.deleteById(id);
    }

    private Recurring findEntityOrThrow(String id) {
        return recurringRepository.findById(id)
                .orElseThrow(() -> new RecurringNotFoundException(id));
    }

    public void deleteAll() {
        recurringRepository.deleteAll();
    }

}

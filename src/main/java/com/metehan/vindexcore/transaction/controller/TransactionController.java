package com.metehan.vindexcore.transaction.controller;

import com.metehan.vindexcore.common.enums.TransactionCategory;
import com.metehan.vindexcore.common.enums.TransactionType;
import com.metehan.vindexcore.transaction.dto.TransactionDTO;
import com.metehan.vindexcore.transaction.repository.TransactionSearchCriteria;
import com.metehan.vindexcore.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> search(
            @RequestParam(required = false) TransactionCategory category,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long minAmountCent,
            @RequestParam(required = false) Long maxAmountCent,
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria(
                category, type, startDate, endDate, minAmountCent, maxAmountCent, keyword
        );
        return ResponseEntity.ok(transactionService.search(criteria, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable String id, @RequestBody TransactionDTO dto) {
        return ResponseEntity.ok(transactionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

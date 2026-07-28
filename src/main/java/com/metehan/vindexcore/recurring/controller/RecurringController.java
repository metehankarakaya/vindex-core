package com.metehan.vindexcore.recurring.controller;

import com.metehan.vindexcore.recurring.dto.RecurringDTO;
import com.metehan.vindexcore.recurring.service.RecurringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recurrings")
public class RecurringController {

    private final RecurringService recurringService;

    @PostMapping
    public ResponseEntity<RecurringDTO> create(@RequestBody RecurringDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recurringService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecurringDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(recurringService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<RecurringDTO>> getAll() {
        return ResponseEntity.ok(recurringService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecurringDTO> update(@PathVariable String id, @RequestBody RecurringDTO dto) {
        return ResponseEntity.ok(recurringService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        recurringService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

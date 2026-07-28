package com.metehan.vindexcore.recurring.controller;

import com.metehan.vindexcore.recurring.scheduler.RecurringTransactionScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class RecurringTestController {

    private final RecurringTransactionScheduler scheduler;

    @PostMapping("/trigger-recurring-scheduler")
    public ResponseEntity<String> triggerScheduler() {
        scheduler.processDueRecurrings();
        return ResponseEntity.ok("Scheduler triggered manually");
    }
}

package com.cpsoneghett.walletapi.controller;


import com.cpsoneghett.walletapi.scheduler.DynamicScheduler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scheduler")
public class SchedulerController {

    private final DynamicScheduler dynamicScheduler;

    public SchedulerController(DynamicScheduler dynamicScheduler) {
        this.dynamicScheduler = dynamicScheduler;
    }


    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Integer periodInMinutes) {
        dynamicScheduler.updateFixedRate(periodInMinutes);
        return new ResponseEntity<>("Updated scheduler to run in a interval of: " + periodInMinutes + " minutes.", HttpStatus.OK);
    }
}

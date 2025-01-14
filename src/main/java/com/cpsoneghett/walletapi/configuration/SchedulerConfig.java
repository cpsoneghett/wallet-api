package com.cpsoneghett.walletapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        var schedulerExecutor = Executors.newScheduledThreadPool(10);
        return new ConcurrentTaskScheduler(schedulerExecutor);
    }

}

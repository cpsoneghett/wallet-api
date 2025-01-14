package com.cpsoneghett.walletapi.scheduler;

import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import com.cpsoneghett.walletapi.domain.service.TokenService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Component
public class DynamicScheduler {

    public static final String ASSET_UPDATE_PERIOD = "ASSET_UPDATE_PERIOD";
    private static final Logger logger = LoggerFactory.getLogger(DynamicScheduler.class);
    private final TokenService tokenService;
    private final GlobalParameterService globalParameterService;

    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledTask;

    @Value("${global.configuration.asset-update-time-scheduler}")
    private Integer assetUpdateTime;

    public DynamicScheduler(TokenService tokenService, GlobalParameterService globalParameterService, TaskScheduler taskScheduler) {
        this.tokenService = tokenService;
        this.globalParameterService = globalParameterService;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void startScheduler() {
        long initialRate = getAssetUpdatePeriod();
        logger.info("Starting scheduler with initial rate: {} ms", initialRate);
        scheduleTask(initialRate);
    }

    public void scheduleTask(long fixedRate) {
        if (scheduledTask != null) {
            logger.info("Cancelling existing scheduled task before scheduling a new one.");
            scheduledTask.cancel(false);
        }


        logger.info("Scheduling task with a fixed rate of {} ms", fixedRate);
        scheduledTask = taskScheduler.schedule(tokenService::updateTokensHistoryList, new FixedRateTrigger(fixedRate));
        logger.info("Task scheduled successfully.");
    }

    public void updateFixedRate(long newRate) {
        logger.info("Updating fixed rate to {} minutes", newRate);

        globalParameterService.update(ASSET_UPDATE_PERIOD, String.valueOf(newRate));
        logger.info("Updated fixed rate in database: key={}, value={}", ASSET_UPDATE_PERIOD, newRate);

        newRate = newRate * 60000L;
        scheduleTask(newRate);
        logger.info("Rescheduled task with new fixed rate: {} ms", newRate);
    }

    public Long getAssetUpdatePeriod() {
        String parameter = globalParameterService.getValueByKey("ASSET_UPDATE_PERIOD");
        Long millisecondsInOneMinute = 60000L;

        if (parameter != null) {
            logger.info("Found custom asset update period in database: {} minutes", parameter);
            return Long.parseLong(parameter) * millisecondsInOneMinute;
        } else {
            logger.info("Using default asset update period from configuration: {} minutes", assetUpdateTime);
            return this.assetUpdateTime * millisecondsInOneMinute;
        }
    }
}

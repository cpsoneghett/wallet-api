package com.cpsoneghett.walletapi.scheduler;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.time.Instant;
import java.util.Date;

public class FixedRateTrigger implements Trigger {

    private final long fixedRate;

    public FixedRateTrigger(long fixedRate) {
        this.fixedRate = fixedRate;
    }

    @Override
    public Instant nextExecution(TriggerContext triggerContext) {
        Date lastExecution = triggerContext.lastCompletionTime();
        if (lastExecution == null)
            return new Date().toInstant();

        return new Date(lastExecution.getTime() + fixedRate).toInstant();
    }

}

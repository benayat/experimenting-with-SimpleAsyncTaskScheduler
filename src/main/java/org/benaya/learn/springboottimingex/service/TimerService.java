package org.benaya.learn.springboottimingex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.benaya.learn.springboottimingex.model.TimingEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimerService {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Qualifier("innerScheduler")
    private final SimpleAsyncTaskScheduler innerScheduler;
    @Qualifier("outerScheduler")
    private final SimpleAsyncTaskScheduler outerScheduler;
    @Async
    public void startTimerStream() {
        AtomicInteger i = new AtomicInteger();
        AtomicInteger j = new AtomicInteger();
        log.info("scheduling events, now in: " + i);
        outerScheduler.scheduleAtFixedRate(() -> {
            applicationEventPublisher.publishEvent(new TimingEvent("TOCK", "tock data, i is: " + i.getAndIncrement()));
            innerScheduler.scheduleAtFixedRate(() -> applicationEventPublisher
                    .publishEvent(new TimingEvent("TICK", "tick data, j is: " + j.getAndIncrement())), java.time.Duration.ofSeconds(3));
        }, java.time.Duration.ofSeconds(31));
    }
}

package org.benaya.learn.springboottimingex.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.benaya.learn.springboottimingex.factory.SseEmitterFactory;
import org.benaya.learn.springboottimingex.model.TimingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TimingEventListener {

    private final SseEmitterFactory sseEmitterFactory;
    @EventListener
    @Async
    public void handleTimingEvent(TimingEvent timingEvent) {
        sseEmitterFactory.sendToAll(timingEvent);
        log.info("Received timing event: " + timingEvent.getType() + " with data: " + timingEvent.getData());
    }




}

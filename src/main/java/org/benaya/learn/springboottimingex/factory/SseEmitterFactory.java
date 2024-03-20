package org.benaya.learn.springboottimingex.factory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.benaya.learn.springboottimingex.model.TimingEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
@Slf4j
public class SseEmitterFactory {
    private Map<String, SseEmitter> ipToSseEmitters = new ConcurrentHashMap<>();

    public SseEmitter getEmitter(String ipAddress) {
        SseEmitter newEmitter = new SseEmitter(1200000L);
        newEmitter.onCompletion(() -> {
            log.info("Emitter completed: " + ipAddress);
            ipToSseEmitters.remove(ipAddress);
        });
        newEmitter.onTimeout(() -> {
            log.info("Emitter timed out: " + ipAddress);
            ipToSseEmitters.remove(ipAddress);
        });
        newEmitter.onError((e) -> {
            log.error("Emitter error for ipAddress "+ipAddress+": " + e.getMessage());
            ipToSseEmitters.remove(ipAddress);
        });
        return ipToSseEmitters.computeIfAbsent(ipAddress, k -> new SseEmitter(1200000L));
    }

    public void sendToAll(TimingEvent timingEvent) {
        log.info("Sending message to emitters: " + timingEvent.getData());
        log.info("Emitters: " + ipToSseEmitters.size());
        ipToSseEmitters.forEach((ipAddress, emitter) -> {
            try {
                log.info("Sending message to: " + ipAddress);
                SseEmitter.SseEventBuilder event = SseEmitter.event().name(timingEvent.getType()).data(timingEvent.getData());
                emitter.send(event);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

}

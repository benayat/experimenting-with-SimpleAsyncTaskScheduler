package org.benaya.learn.springboottimingex.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.benaya.learn.springboottimingex.factory.SseEmitterFactory;
import org.benaya.learn.springboottimingex.service.TimerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {
    private final SseEmitterFactory sseEmitterFactory;
    private final TimerService timerService;
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    @GetMapping("/timing")
    public String timing() {
        return "Timing";
    }
    @GetMapping("/events")
    public SseEmitter events(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return sseEmitterFactory.getEmitter(ipAddress);
    }
    @PostMapping("/start")
    public void start() {
        timerService.startTimerStream();
    }

}

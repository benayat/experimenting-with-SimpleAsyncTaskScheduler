package org.benaya.learn.springboottimingex.config;

import org.springframework.boot.task.SimpleAsyncTaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;

@Configuration
public class SchedulersConfig {
    @Bean(name = "innerScheduler", destroyMethod = "close")
    public SimpleAsyncTaskScheduler simpleScheduler() {
        return new SimpleAsyncTaskSchedulerBuilder()
                .virtualThreads(true)
                .build();
    }
    @Bean(name = "outerScheduler", destroyMethod = "close")
    public SimpleAsyncTaskScheduler simpleScheduler2() {
        return new SimpleAsyncTaskSchedulerBuilder()
                .virtualThreads(true)
                .build();
    }
}

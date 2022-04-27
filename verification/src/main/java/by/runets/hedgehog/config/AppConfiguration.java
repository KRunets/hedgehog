package by.runets.hedgehog.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class AppConfiguration {

    @Bean
    public ScheduledTaskRegistrar scheduledTaskRegistrar() {
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        scheduledTaskRegistrar.setScheduler(threadPoolTaskScheduler());
        return scheduledTaskRegistrar;
    }

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        return scheduler;
    }

    @Bean
    public ConcurrentMap<UUID, ScheduledTask> tasksForTermination() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public BlockingQueue<UUID> executedVerificationQueue() {
        return new ArrayBlockingQueue<>(32);
    }

}

package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.service.ExpirationService;
import by.runets.hedgehog.service.ScheduleExpirationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

@Service
public class ScheduleExpirationServiceImpl implements ScheduleExpirationService, ApplicationListener<ContextStartedEvent> {

    private static final Logger LOG = LogManager.getLogger(ScheduleExpirationServiceImpl.class);

    @Value("#{new Long('${task.cleaner.timeout:300}')}")
    private Long taskCleanerTimeout;

    @Value("#{new Long('${task.expiration.timeout:300000}')}")
    private Long taskExpirationTimeout;

    @Autowired
    private ScheduledTaskRegistrar scheduledTaskRegistrar;
    @Autowired
    private ExpirationService expirationService;
    @Autowired
    private ConcurrentMap<UUID, ScheduledTask> tasksForTermination;
    @Autowired
    private BlockingQueue<UUID> executedVerificationQueue;

    @Override
    @Transactional
    public void scheduleVerificationExpiration(UUID id, Instant creationTime) {
        if (id == null) {
            return;
        }

        final Trigger trigger = triggerContext -> {
            final Instant nextExecutionTime = creationTime.plusMillis(taskExpirationTimeout);
            return Date.from(nextExecutionTime);
        };

        final TriggerTask task = new TriggerTask(() -> {
            LOG.debug("Trigger task started.");
            final Integer count = expirationService.expireVerification(id);
            if (count > 0) {
                if (!executedVerificationQueue.contains(id)) {
                    executedVerificationQueue.add(id);
                }
                LOG.warn("The verification by id={} is expired", id);
            }
            LOG.debug("Trigger task finished.");
        }, trigger);

        final ScheduledTask scheduledTask = scheduledTaskRegistrar.scheduleTriggerTask(task);
        tasksForTermination.put(id, scheduledTask);
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        final TriggerTask triggerTask = new TriggerTask(() -> {
            tasksForTermination.forEach((id, task) -> {
                        LOG.debug("Task cleaner started");
                        if (executedVerificationQueue.contains(id)) {
                            final boolean isRemoved = executedVerificationQueue.remove(id);
                            if (isRemoved) {
                                task.cancel();
                                tasksForTermination.remove(id);
                                LOG.warn("Task by id={} is cleaned", id);
                            }
                        }
                        LOG.debug("Task cleaner finished");
                    }
            );
        }, triggerContext -> {
            final Instant nextExecutionTime = Instant.now().plusMillis(taskCleanerTimeout);
            return Date.from(nextExecutionTime);
        });
        scheduledTaskRegistrar.scheduleTriggerTask(triggerTask);
    }

    public void setExecutedVerificationQueue(BlockingQueue<UUID> executedVerificationQueue) {
        this.executedVerificationQueue = executedVerificationQueue;
    }

    public void setTasksForTermination(ConcurrentMap<UUID, ScheduledTask> tasksForTermination) {
        this.tasksForTermination = tasksForTermination;
    }

    public void setTaskExpirationTimeout(Long taskExpirationTimeout) {
        this.taskExpirationTimeout = taskExpirationTimeout;
    }

    public void setExpirationService(ExpirationService expirationService) {
        this.expirationService = expirationService;
    }

    public void setScheduledTaskRegistrar(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
    }

    public void setTaskCleanerTimeout(Long taskCleanerTimeout) {
        this.taskCleanerTimeout = taskCleanerTimeout;
    }
}

package com.internship.books_api.scheduler;

import com.internship.books_api.entity.AuditLog;
import com.internship.books_api.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AuditLogPoller {

    private final AuditLogRepository auditLogRepository;

    public AuditLogPoller(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Har 10 seconds baad chalo
    @Scheduled(fixedDelay = 10000)
    public void pollNewAuditLogs() {
        List<AuditLog> newLogs = auditLogRepository.findByProcessedFalse();

        if (!newLogs.isEmpty()) {
            log.info("Poller: Found {} new audit log(s) to process", newLogs.size());
            for (AuditLog auditLog : newLogs) {
                log.info("  -> Processing audit: id={}, type={}, description='{}'",
                        auditLog.getId(), auditLog.getEventType(), auditLog.getDescription());
                auditLog.setProcessed(true);
                auditLogRepository.save(auditLog);
            }
        } else {
            log.debug("Poller: No new audit logs");
        }
    }
}
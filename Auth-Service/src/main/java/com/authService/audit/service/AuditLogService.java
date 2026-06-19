package com.authService.audit.service;

import com.authService.audit.dto.AuditLogDTO;
import com.authService.audit.entity.AuditLogEntity;
import com.authService.audit.repository.AuditLogRepository;
import com.authService.audit.util.AuditUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuditLogService {

    private final AuditLogRepository repository;
    private final AuditUtil auditUtil;
    private final ModelMapper modelMapper;

    /**
     * Log an audit event
     */
    public void logAudit(String username, String serviceName, String entityName, String entityId,
                        String actionType, Object oldValue, Object newValue, boolean success, String errorMessage) {
        try {
            AuditLogEntity log = AuditLogEntity.builder()
                    .username(username)
                    .serviceName(serviceName)
                    .entityName(entityName)
                    .entityId(entityId)
                    .actionType(actionType)
                    .oldValue(auditUtil.serializeToJson(oldValue))
                    .newValue(auditUtil.serializeToJson(newValue))
                    .ipAddress(auditUtil.getClientIpAddress())
                    .timestamp(LocalDateTime.now())
                    .success(success)
                    .errorMessage(errorMessage)
                    .build();

            repository.save(log);
            log.info("Audit logged: {} | {} | {} | {}", actionType, entityName, entityId, username);
        } catch (Exception e) {
            log.error("Error logging audit: {}", e.getMessage(), e);
        }
    }

    /**
     * Get audit logs for a user
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getAuditsByUser(String username, Pageable pageable) {
        return repository.findByUsername(username, pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }

    /**
     * Get audit logs by action type
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getAuditsByActionType(String actionType, Pageable pageable) {
        return repository.findByActionType(actionType, pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }

    /**
     * Get audit logs by entity name
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getAuditsByEntity(String entityName, Pageable pageable) {
        return repository.findByEntityName(entityName, pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }

    /**
     * Get audit logs within date range
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getAuditsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return repository.findByDateRange(startDate, endDate, pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }

    /**
     * Get audit logs for specific user and entity
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getAuditsByUserAndEntity(String username, String entityName, Pageable pageable) {
        return repository.findByUsernameAndEntity(username, entityName, pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }

    /**
     * Get failed audit attempts
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getFailedAudits(Pageable pageable) {
        return repository.findBySuccessFalse(pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }

    /**
     * Get audit logs by service
     */
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> getAuditsByService(String serviceName, Pageable pageable) {
        return repository.findByServiceName(serviceName, pageable)
                .map(entity -> modelMapper.map(entity, AuditLogDTO.class));
    }
}

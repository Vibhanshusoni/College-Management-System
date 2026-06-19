package com.authService.audit.controller;

import com.authService.audit.dto.AuditLogDTO;
import com.authService.audit.service.AuditLogService;
import com.authService.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
public class AuditLogController {

    private final AuditLogService auditLogService;

    /**
     * Get audit logs by username
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<ApiResponse> getAuditsByUser(
            @PathVariable String username,
            Pageable pageable) {
        log.info("Fetching audit logs for user: {}", username);
        
        Page<AuditLogDTO> audits = auditLogService.getAuditsByUser(username, pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }

    /**
     * Get audit logs by action type
     */
    @GetMapping("/action/{actionType}")
    public ResponseEntity<ApiResponse> getAuditsByAction(
            @PathVariable String actionType,
            Pageable pageable) {
        log.info("Fetching audit logs for action: {}", actionType);
        
        Page<AuditLogDTO> audits = auditLogService.getAuditsByActionType(actionType, pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }

    /**
     * Get audit logs by entity name
     */
    @GetMapping("/entity/{entityName}")
    public ResponseEntity<ApiResponse> getAuditsByEntity(
            @PathVariable String entityName,
            Pageable pageable) {
        log.info("Fetching audit logs for entity: {}", entityName);
        
        Page<AuditLogDTO> audits = auditLogService.getAuditsByEntity(entityName, pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }

    /**
     * Get audit logs by service
     */
    @GetMapping("/service/{serviceName}")
    public ResponseEntity<ApiResponse> getAuditsByService(
            @PathVariable String serviceName,
            Pageable pageable) {
        log.info("Fetching audit logs for service: {}", serviceName);
        
        Page<AuditLogDTO> audits = auditLogService.getAuditsByService(serviceName, pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }

    /**
     * Get audit logs by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse> getAuditsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            Pageable pageable) {
        log.info("Fetching audit logs from {} to {}", startDate, endDate);
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        
        Page<AuditLogDTO> audits = auditLogService.getAuditsByDateRange(start, end, pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }

    /**
     * Get audit logs for user and entity combination
     */
    @GetMapping("/user/{username}/entity/{entityName}")
    public ResponseEntity<ApiResponse> getAuditsByUserAndEntity(
            @PathVariable String username,
            @PathVariable String entityName,
            Pageable pageable) {
        log.info("Fetching audit logs for user {} and entity {}", username, entityName);
        
        Page<AuditLogDTO> audits = auditLogService.getAuditsByUserAndEntity(username, entityName, pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }

    /**
     * Get failed audit attempts (failed operations)
     */
    @GetMapping("/failed")
    public ResponseEntity<ApiResponse> getFailedAudits(Pageable pageable) {
        log.info("Fetching failed audit logs");
        
        Page<AuditLogDTO> audits = auditLogService.getFailedAudits(pageable);
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(200)
                        .message("Failed audit logs fetched successfully")
                        .data(audits)
                        .build()
        );
    }
}

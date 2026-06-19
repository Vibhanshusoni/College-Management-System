package com.authService.audit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_audit_username", columnList = "username"),
        @Index(name = "idx_audit_service", columnList = "serviceName"),
        @Index(name = "idx_audit_action", columnList = "actionType"),
        @Index(name = "idx_audit_timestamp", columnList = "timestamp"),
        @Index(name = "idx_audit_entity", columnList = "entityName")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 50)
    private String serviceName;

    @Column(nullable = false, length = 50)
    private String entityName;

    @Column(nullable = false)
    private String entityId;

    @Column(nullable = false, length = 20)
    private String actionType; // CREATE, UPDATE, DELETE, LOGIN, LOGOUT

    @Column(columnDefinition = "LONGTEXT")
    private String oldValue;

    @Column(columnDefinition = "LONGTEXT")
    private String newValue;

    @Column(length = 45)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean success = true;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
}

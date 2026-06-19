package com.authService.audit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditLogDTO {

    private Long id;
    private String username;
    private String serviceName;
    private String entityName;
    private String entityId;
    private String actionType;
    private String oldValue;
    private String newValue;
    private String ipAddress;
    private LocalDateTime timestamp;
    private boolean success;
    private String errorMessage;
}

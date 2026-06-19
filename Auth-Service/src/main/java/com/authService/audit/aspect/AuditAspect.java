package com.authService.audit.aspect;

import com.authService.audit.annotation.Auditable;
import com.authService.audit.service.AuditLogService;
import com.authService.audit.util.AuditUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditLogService auditLogService;
    private final AuditUtil auditUtil;

    @Value("${app.name:UNKNOWN}")
    private String serviceName;

    @Value("${app.audit.enabled:true}")
    private boolean auditEnabled;

    /**
     * Intercept methods annotated with @Auditable
     */
    @Around("@annotation(auditable)")
    public Object auditMethodExecution(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        if (!auditEnabled) {
            return joinPoint.proceed();
        }

        String username = extractUsername();
        String actionType = auditable.action();
        String entityName = auditable.entityName();
        String entityId = auditUtil.extractEntityId(joinPoint.getArgs());

        Object result = null;
        boolean success = true;
        String errorMessage = null;

        try {
            result = joinPoint.proceed();
            log.info("Audit Action - {} | {} | {} | User: {}", actionType, entityName, entityId, username);
            return result;
        } catch (Exception e) {
            success = false;
            errorMessage = e.getMessage();
            log.error("Audit Action Failed - {} | {} | {} | Error: {}", actionType, entityName, entityId, e.getMessage());
            throw e;
        } finally {
            try {
                auditLogService.logAudit(
                        username,
                        serviceName,
                        entityName,
                        entityId,
                        actionType,
                        null, // old value not captured in this simple implementation
                        result,
                        success,
                        errorMessage
                );
            } catch (Exception e) {
                log.error("Error saving audit log: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Extract username from request headers or security context
     */
    private String extractUsername() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String username = request.getHeader("X-User-Name");
                if (username != null && !username.isEmpty()) {
                    return username;
                }
            }
        } catch (Exception e) {
            log.debug("Could not extract username from headers: {}", e.getMessage());
        }

        return "SYSTEM";
    }
}

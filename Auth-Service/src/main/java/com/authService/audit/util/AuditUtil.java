package com.authService.audit.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditUtil {

    private final ObjectMapper objectMapper;

    /**
     * Get client IP address from request
     */
    public String getClientIpAddress() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) {
                return "UNKNOWN";
            }

            HttpServletRequest request = attrs.getRequest();
            String xForwardedFor = request.getHeader("X-Forwarded-For");
            
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                return xForwardedFor.split(",")[0].trim();
            }

            String xRealIp = request.getHeader("X-Real-IP");
            if (xRealIp != null && !xRealIp.isEmpty()) {
                return xRealIp;
            }

            return request.getRemoteAddr();
        } catch (Exception e) {
            log.warn("Error extracting client IP: {}", e.getMessage());
            return "UNKNOWN";
        }
    }

    /**
     * Serialize object to JSON string
     */
    public String serializeToJson(Object obj) {
        try {
            if (obj == null) {
                return null;
            }
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Error serializing object: {}", e.getMessage());
            return obj.toString();
        }
    }

    /**
     * Calculate diff between two objects
     */
    public String calculateDiff(Object oldValue, Object newValue) {
        try {
            String oldJson = serializeToJson(oldValue);
            String newJson = serializeToJson(newValue);

            if (oldJson == null && newJson == null) {
                return "No changes";
            }

            return String.format("Old: %s | New: %s", oldJson, newJson);
        } catch (Exception e) {
            log.warn("Error calculating diff: {}", e.getMessage());
            return "Unable to calculate diff";
        }
    }

    /**
     * Extract entity ID from method arguments
     */
    public String extractEntityId(Object[] args) {
        if (args == null || args.length == 0) {
            return "N/A";
        }

        // Typically first argument is the entity or ID
        Object firstArg = args[0];
        if (firstArg != null) {
            if (firstArg instanceof String) {
                return (String) firstArg;
            } else if (firstArg instanceof Long) {
                return String.valueOf(firstArg);
            } else if (firstArg instanceof Number) {
                return String.valueOf(firstArg);
            }

            // Try to extract ID from entity object
            try {
                if (firstArg.getClass().getDeclaredMethod("getId") != null) {
                    Object id = firstArg.getClass().getDeclaredMethod("getId").invoke(firstArg);
                    return String.valueOf(id);
                }
            } catch (Exception e) {
                log.debug("Could not extract ID from entity");
            }
        }

        return "N/A";
    }
}

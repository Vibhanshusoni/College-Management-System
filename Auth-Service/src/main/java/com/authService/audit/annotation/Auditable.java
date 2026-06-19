package com.authService.audit.annotation;

import java.lang.annotation.*;

/**
 * Marks a method for audit logging.
 * Use on service methods that modify data (Create, Update, Delete).
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {
    
    /**
     * Action type: CREATE, UPDATE, DELETE, LOGIN, LOGOUT
     */
    String action();
    
    /**
     * Entity name being audited
     */
    String entityName();
}

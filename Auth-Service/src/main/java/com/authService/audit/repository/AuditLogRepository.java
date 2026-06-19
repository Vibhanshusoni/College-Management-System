package com.authService.audit.repository;

import com.authService.audit.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {

    /**
     * Find all audit logs for a specific user
     */
    Page<AuditLogEntity> findByUsername(String username, Pageable pageable);

    /**
     * Find all audit logs for a specific action type
     */
    Page<AuditLogEntity> findByActionType(String actionType, Pageable pageable);

    /**
     * Find all audit logs for a specific entity
     */
    Page<AuditLogEntity> findByEntityName(String entityName, Pageable pageable);

    /**
     * Find audit logs within a date range
     */
    @Query("SELECT a FROM AuditLogEntity a WHERE a.timestamp BETWEEN :startDate AND :endDate")
    Page<AuditLogEntity> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    /**
     * Find audit logs for a specific user and entity
     */
    @Query("SELECT a FROM AuditLogEntity a WHERE a.username = :username AND a.entityName = :entityName")
    Page<AuditLogEntity> findByUsernameAndEntity(
            @Param("username") String username,
            @Param("entityName") String entityName,
            Pageable pageable
    );

    /**
     * Find failed audit logs
     */
    Page<AuditLogEntity> findBySuccessFalse(Pageable pageable);

    /**
     * Find audit logs by service name
     */
    Page<AuditLogEntity> findByServiceName(String serviceName, Pageable pageable);
}

package com.authService.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminPasswordGenerator {

    private final PasswordEncoderUtil passwordEncoderUtil;

    /**
     * Generate and print admin password on application startup
     * This runs once when the application is fully initialized
     */
    @EventListener(ApplicationReadyEvent.class)
    public void generateAdminPassword() {
        String adminPassword = "Admin@1234";
        String encryptedPassword = passwordEncoderUtil.encodePassword(adminPassword);

        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║          ADMIN PASSWORD GENERATION - COPY THE HASH             ║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
        log.info(" ");
        log.info("Raw Password: {}", adminPassword);
        log.info(" ");
        log.info("Encrypted Password (Copy this to database):");
        log.info(" ");
        log.info("═══════════════════════════════════════════════════════════════");
        log.info("{}", encryptedPassword);
        log.info("═══════════════════════════════════════════════════════════════");
        log.info(" ");
        log.info("SQL INSERT COMMAND:");
        log.info("═══════════════════════════════════════════════════════════════");
        log.info("INSERT INTO users (username, email, password, role, department) VALUES");
        log.info("('admin', 'admin@college.edu', '{}', 'ADMIN', 'Administration');", encryptedPassword);
        log.info("═══════════════════════════════════════════════════════════════");
        log.info(" ");
        log.info("Instructions:");
        log.info("1. Copy the encrypted password above");
        log.info("2. Run the SQL INSERT command in your MySQL database");
        log.info("3. Use 'admin' as username and 'Admin@1234' as password to login");
        log.info(" ");
    }
}

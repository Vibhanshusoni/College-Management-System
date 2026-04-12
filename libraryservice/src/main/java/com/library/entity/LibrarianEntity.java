package com.collage.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "librarians", indexes = {
        @Index(name = "idx_librarian_id", columnList = "universityId"),
        @Index(name = "idx_librarian_email", columnList = "librarianEmail")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Database Identity
    @Column(nullable = false, unique = true, length = 12)
    private String universityId;  // 12-digit librarian ID

    @Column(nullable = false)
    private String librarianName;

    @Column(nullable = false, unique = true)
    private String librarianEmail;

    @Column(nullable = false)
    private String librarianPhoneNumber;

    // Attendance Calendar (JSON or separate table)
    @Column(columnDefinition = "JSON")
    private String attendanceCalendar;

    @Column(name = "attendance_percentage", columnDefinition = "FLOAT DEFAULT 0.0")
    private Float attendancePercentage = 0.0f;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
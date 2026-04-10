package com.facultyService.entity;

import com.facultyService.enums.FacultySubRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "faculty", indexes = {
        @Index(name = "idx_faculty_id", columnList = "universityId"),
        @Index(name = "idx_faculty_email", columnList = "facultyEmail"),
        @Index(name = "idx_faculty_dept", columnList = "department")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Database Identity
    @Column(nullable = false, unique = true, length = 12)
    private String universityId;  // 12-digit faculty ID

    @Column(nullable = false)
    private String facultyName;

    @Column(nullable = false, unique = true)
    private String facultyEmail;

    @Column(nullable = false)
    private String facultyPhoneNumber;

    @Column(nullable = false)
    private String department;

    // Sub-role: HOD, Professor, Assistant Professor, Trainee
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacultySubRole subRole;

    // Attendance Calendar (JSON or separate table)
    @Column(columnDefinition = "JSON")
    private String attendanceCalendar;

    @Column(name = "attendance_percentage", columnDefinition = "FLOAT DEFAULT 0.0")
    private Float attendancePercentage = 0.0f;

    // Books issued and returned count
    @Column(name = "books_issued", columnDefinition = "INT DEFAULT 0")
    private Integer booksIssued = 0;

    @Column(name = "books_returned", columnDefinition = "INT DEFAULT 0")
    private Integer booksReturned = 0;

    // Schedule (if HOD)
    @Column(columnDefinition = "JSON")
    private String scheduleData;

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


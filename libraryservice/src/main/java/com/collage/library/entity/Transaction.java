package com.collage.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_issues", indexes = {
        @Index(name = "idx_issue_book_id", columnList = "bookId"),
        @Index(name = "idx_issue_user_id", columnList = "userId"),
        @Index(name = "idx_issue_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookIssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookId;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userRole; // STUDENT, FACULTY, etc.

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate returnableDate;

    @Column(nullable = true)
    private LocalDate returnedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status = IssueStatus.ISSUED;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer fineAmount = 0;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean finePaid = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();

        // Calculate fine if book is overdue
        if (status == IssueStatus.ISSUED && LocalDate.now().isAfter(returnableDate)) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(returnableDate, LocalDate.now());
            this.fineAmount = (int) (daysOverdue * 5); // 5 Rs per day fine
        }
    }
}
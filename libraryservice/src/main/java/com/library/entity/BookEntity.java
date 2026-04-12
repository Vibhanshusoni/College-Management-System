package com.collage.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "books", indexes = {
        @Index(name = "idx_book_id", columnList = "bookId"),
        @Index(name = "idx_book_name", columnList = "bookName"),
        @Index(name = "idx_book_author", columnList = "author")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bookId;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publication;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer totalCount = 0;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer availableCount = 0;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer issuedCount = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.availableCount = this.totalCount - this.issuedCount;
    }
}
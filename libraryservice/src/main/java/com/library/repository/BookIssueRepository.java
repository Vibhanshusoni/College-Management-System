package com.collage.library.repository;



import com.collage.library.entity.BookIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookIssueRepository extends JpaRepository<BookIssueEntity, Long> {

    boolean existsByBookIdAndStudentIdAndReturnDateIsNull(Long bookId, Long studentId);

    long countByStudentIdAndReturnDateIsNull(Long studentId);
}
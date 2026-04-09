package com.collage.library.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.collage.library.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByBookIdAndStudentIdAndReturnDateIsNull(Long bookId, Long studentId);

    long countByStudentIdAndReturnDateIsNull(Long studentId);
}
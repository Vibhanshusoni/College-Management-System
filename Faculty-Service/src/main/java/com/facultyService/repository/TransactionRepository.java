package com.facultyService.repository;


import com.facultyService.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByBookIdAndStudentIdAndReturnDateIsNull(Long bookId, Long studentId);

    long countByStudentIdAndReturnDateIsNull(Long studentId);
}
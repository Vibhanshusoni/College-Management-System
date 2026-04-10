package com.facultyService.service;


import com.facultyService.client.FacultyClient;
import com.facultyService.entity.Book;
import com.facultyService.entity.Transaction;
import com.facultyService.repository.BookRepository;
import com.facultyService.repository.TransactionRepository;
import com.facultyService.validation.FacultyValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private FacultyClient studentClient;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private FacultyValidation FacultyValidation;

    // Add Book
    public Book addBook(Book book) {
        FacultyValidation.validateBookFields(book);
        logger.info("Adding book: {}", book.getName());
        return bookRepo.save(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepo.findAll();
    }

    // Issue Book
    public Transaction issueBook(Long bookId, Long studentId) {
        logger.info("Issuing book ID {} to student ID {}", bookId, studentId);

        Book book = FacultyValidation.validateIssueBook(bookId, studentId);

        try {
            studentClient.getStudentById(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        book.setAvailable(false);
        bookRepo.save(book);

        Transaction tx = new Transaction();
        tx.setBookId(bookId);
        tx.setStudentId(studentId);
        tx.setIssueDate(LocalDate.now());

        return transactionRepo.save(tx);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        logger.info("Fetching all transactions");
        return transactionRepo.findAll();
    }

    // Return Book
    public Transaction returnBook(Long transactionId) {
        logger.info("Returning book for transaction ID {}", transactionId);

        Transaction tx = FacultyValidation.validateReturnBook(transactionId);

        Book book = bookRepo.findById(tx.getBookId()).get();
        book.setAvailable(true);
        bookRepo.save(book);

        tx.setReturnDate(LocalDate.now());
        return transactionRepo.save(tx);
    }
}
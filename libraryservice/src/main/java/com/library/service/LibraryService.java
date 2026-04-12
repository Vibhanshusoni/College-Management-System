package com.collage.library.service;


import com.collage.library.client.StudentClient;
import com.collage.library.entity.BookEntity;
import com.collage.library.entity.BookIssueEntity;
import com.collage.library.entity.Transaction;
import com.collage.library.repository.BookRepository;
import com.collage.library.repository.TransactionRepository;
import com.collage.library.validation.LibraryValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {

    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private LibraryValidation libraryValidation;

    // Add Book
    public BookEntity addBook(BookEntity book) {
        libraryValidation.validateBookFields(book);
        logger.info("Adding book: {}", book.getName());
        return bookRepo.save(book);
    }

    // Get all books
    public List<BookEntity> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepo.findAll();
    }

    // Issue Book
    public BookIssueEntity issueBook(Long bookId, Long studentId) {
        logger.info("Issuing book ID {} to student ID {}", bookId, studentId);

        BookEntity book = libraryValidation.validateIssueBook(bookId, studentId);

        try {
            studentClient.getStudentById(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        book.setAvailable(false);
        bookRepo.save(book);

        BookIssueEntity tx = new BookIssueEntity();
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

        Transaction tx = libraryValidation.validateReturnBook(transactionId);

        Book book = bookRepo.findById(tx.getBookId()).get();
        book.setAvailable(true);
        bookRepo.save(book);

        tx.setReturnDate(LocalDate.now());
        return transactionRepo.save(tx);
    }
}
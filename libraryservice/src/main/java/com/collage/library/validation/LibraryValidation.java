package com.collage.library.validation;

import com.collage.library.entity.Book;
import com.collage.library.entity.Transaction;
import com.collage.library.repository.BookRepository;
import com.collage.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LibraryValidation {

    private static final int MAX_BOOKS_PER_STUDENT = 3;
    private static final int MAX_NAME_LENGTH = 100;
    private static final String NAME_PATTERN = "^[a-zA-Z0-9 .,:'\\-]+$";
    private static final String AUTHOR_PATTERN = "^[a-zA-Z .'-]+$";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ─── Book Field Validations ───────────────────────────────────────────────

    /**
     * Validates all fields of a Book before saving it.
     * Checks: null, blank, length, pattern, and duplicate.
     */
    public void validateBookFields(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book data must not be null");
        }

        // Name validations
        if (book.getName() == null || book.getName().isBlank()) {
            throw new IllegalArgumentException("Book name must not be blank");
        }
        if (book.getName().trim().length() < 2) {
            throw new IllegalArgumentException("Book name must be at least 2 characters");
        }
        if (book.getName().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Book name must not exceed " + MAX_NAME_LENGTH + " characters");
        }
        if (!book.getName().matches(NAME_PATTERN)) {
            throw new IllegalArgumentException("Book name contains invalid characters. Only letters, numbers, spaces, and .,:'- are allowed");
        }

        // Author validations
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new IllegalArgumentException("Author name must not be blank");
        }
        if (book.getAuthor().trim().length() < 2) {
            throw new IllegalArgumentException("Author name must be at least 2 characters");
        }
        if (book.getAuthor().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Author name must not exceed " + MAX_NAME_LENGTH + " characters");
        }
        if (!book.getAuthor().matches(AUTHOR_PATTERN)) {
            throw new IllegalArgumentException("Author name contains invalid characters. Only letters, spaces, and .'- are allowed");
        }

        // Duplicate check
        if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCase(
                book.getName().trim(), book.getAuthor().trim())) {
            throw new IllegalArgumentException(
                    "Book '" + book.getName() + "' by '" + book.getAuthor() + "' already exists in the library");
        }
    }

    // ─── Book Existence & Availability ───────────────────────────────────────

    /**
     * Validates that a book exists by its ID and returns it.
     */
    public Book validateBookExists(Long bookId) {
        if (bookId == null || bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be a positive number");
        }
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));
    }

    /**
     * Validates that a book is currently available for issuing.
     */
    public void validateBookAvailability(Book book) {
        if (!book.isAvailable()) {
            throw new RuntimeException("Book '" + book.getName() + "' is currently not available for issuing");
        }
    }

    // ─── Student Validations ──────────────────────────────────────────────────

    /**
     * Validates that a student ID is a valid positive number.
     */
    public void validateStudentId(Long studentId) {
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be a positive number");
        }
    }

    /**
     * Validates that a student has not already issued the same book and not yet returned it.
     */
    public void validateNoDuplicateIssue(Long bookId, Long studentId) {
        if (transactionRepository.existsByBookIdAndStudentIdAndReturnDateIsNull(bookId, studentId)) {
            throw new RuntimeException(
                    "Student with ID " + studentId + " has already issued book with ID " + bookId + " and not returned it yet");
        }
    }

    /**
     * Validates that a student has not exceeded the maximum allowed books limit.
     */
    public void validateStudentBookLimit(Long studentId) {
        long activeBooks = transactionRepository.countByStudentIdAndReturnDateIsNull(studentId);
        if (activeBooks >= MAX_BOOKS_PER_STUDENT) {
            throw new RuntimeException(
                    "Student with ID " + studentId + " has already reached the maximum limit of "
                            + MAX_BOOKS_PER_STUDENT + " issued books. Please return a book before issuing a new one");
        }
    }

    // ─── Transaction Validations ──────────────────────────────────────────────

    /**
     * Validates that a transaction exists by its ID and returns it.
     */
    public Transaction validateTransactionExists(Long transactionId) {
        if (transactionId == null || transactionId <= 0) {
            throw new IllegalArgumentException("Transaction ID must be a positive number");
        }
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
    }

    /**
     * Validates that the book has not already been returned in this transaction.
     */
    public void validateBookNotAlreadyReturned(Transaction transaction) {
        if (transaction.getReturnDate() != null) {
            throw new RuntimeException(
                    "Book for transaction ID " + transaction.getId() + " has already been returned on "
                            + transaction.getReturnDate());
        }
    }

    /**
     * Validates that the return date is not before the issue date.
     */
    public void validateReturnDateNotBeforeIssueDate(Transaction transaction) {
        if (transaction.getIssueDate() != null && LocalDate.now().isBefore(transaction.getIssueDate())) {
            throw new IllegalArgumentException("Return date cannot be before the issue date: " + transaction.getIssueDate());
        }
    }

    /**
     * Validates that the issue date is not in the future.
     */
    public void validateIssueDate(LocalDate issueDate) {
        if (issueDate != null && issueDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Issue date cannot be in the future");
        }
    }

    // ─── Composite Validations ────────────────────────────────────────────────

    /**
     * Runs all validations required before issuing a book.
     * Returns the validated Book.
     */
    public Book validateIssueBook(Long bookId, Long studentId) {
        validateStudentId(studentId);
        Book book = validateBookExists(bookId);
        validateBookAvailability(book);
        validateNoDuplicateIssue(bookId, studentId);
        validateStudentBookLimit(studentId);
        return book;
    }

    /**
     * Runs all validations required before returning a book.
     * Returns the validated Transaction.
     */
    public Transaction validateReturnBook(Long transactionId) {
        Transaction transaction = validateTransactionExists(transactionId);
        validateBookNotAlreadyReturned(transaction);
        validateReturnDateNotBeforeIssueDate(transaction);
        return transaction;
    }
}
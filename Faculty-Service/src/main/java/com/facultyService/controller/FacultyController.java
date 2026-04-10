package com.facultyService.controller;


import com.facultyService.entity.Book;
import com.facultyService.entity.Transaction;
import com.facultyService.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService service;

    // Add Book
    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody Book book) {
        return service.addBook(book);
    }

    // Get Books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return service.getAllBooks();
    }

    // Issue Book
    @PostMapping("/issue")
    public Transaction issueBook(@RequestParam Long bookId, @RequestParam Long studentId) {
        return service.issueBook(bookId, studentId);
    }

    // Get all transactions
    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return service.getAllTransactions();
    }

    // Return Book
    @PostMapping("/return")
    public Transaction returnBook(@RequestParam Long transactionId) {
        return service.returnBook(transactionId);
    }
}
package com.facultyService.repository;


import com.facultyService.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByNameIgnoreCaseAndAuthorIgnoreCase(String name, String author);
}
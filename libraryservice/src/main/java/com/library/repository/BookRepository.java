package com.collage.library.repository;


import com.collage.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByNameIgnoreCaseAndAuthorIgnoreCase(String name, String author);
}
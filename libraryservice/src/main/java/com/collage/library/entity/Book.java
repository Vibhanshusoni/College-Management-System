package com.collage.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book name must not be blank")
    @Size(min = 1, max = 100, message = "Book name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Author name must not be blank")
    @Size(min = 1, max = 100, message = "Author name must be between 1 and 100 characters")
    private String author;

    private boolean available = true;
}
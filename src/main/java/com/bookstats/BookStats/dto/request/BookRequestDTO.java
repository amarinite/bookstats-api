package com.bookstats.BookStats.dto.request;

import com.bookstats.BookStats.entity.Book;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 255, message = "Author must not exceed 255 characters")
    private String author;

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    private String isbn;

    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    private Book.AuthorGender authorGender;

    @Min(value = 1000, message = "Published year must be at least 1000")
    @Max(value = 2030, message = "Published year cannot be in the far future")
    private Integer publishedYear;

    @URL(message = "Cover URL must be a valid URL")
    private String coverUrl;

    private Set<Long> genreIds = new HashSet<>();
}

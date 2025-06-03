package com.bookstats.BookStats.dto;

import com.bookstats.BookStats.entity.Book;
import lombok.Data;

import java.util.Set;

@Data
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer pages;
    private String language;
    private String country;
    private Book.AuthorGender authorGender;
    private Integer publishedYear;
    private String coverUrl;
    private Set<GenreDTO> genres;

}

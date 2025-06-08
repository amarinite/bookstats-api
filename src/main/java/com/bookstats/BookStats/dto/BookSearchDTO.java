package com.bookstats.BookStats.dto;

import com.bookstats.BookStats.entity.Book;
import lombok.Data;

import java.util.Set;

@Data
public class BookSearchDTO {
    private String title;
    private String author;
    private String language;
    private String country;
    private Book.AuthorGender authorGender;
    private Integer minYear;
    private Integer maxYear;
    private Set<Long> genreIds;
    private Integer minPages;
    private Integer maxPages;

    // Pagination
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "title";
    private String sortDirection = "ASC";
}

package com.bookstats.BookStats.dto;

import com.bookstats.BookStats.entity.Genre;
import lombok.Data;

@Data
public class UserReadingStatsDTO {
    private Long userId;
    private String username;
    private Integer booksCompleted;
    private Integer booksCurrentlyReading;
    private Integer booksWantToRead;
    private Double averageRating;
    private Integer totalPages;
    private Genre favoriteGenre;
}
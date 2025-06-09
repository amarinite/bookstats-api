package com.bookstats.BookStats.dto;

import com.bookstats.BookStats.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadingStatsDTO {
    private Long userId;
    private String username;
    private Integer booksCompleted;
    private Integer booksCurrentlyReading;
    private Integer booksWantToRead;
    private Double averageRating;
    private Integer totalPages;
    private Genre favoriteGenre;

    public UserReadingStatsDTO(Long userId, String username, Integer booksCompleted,
                               Integer booksCurrentlyReading, Integer booksWantToRead,
                               Double averageRating, Integer totalPages) {
        this.userId = userId;
        this.username = username;
        this.booksCompleted = booksCompleted;
        this.booksCurrentlyReading = booksCurrentlyReading;
        this.booksWantToRead = booksWantToRead;
        this.averageRating = averageRating;
        this.totalPages = totalPages;
        this.favoriteGenre = null;
    }
}
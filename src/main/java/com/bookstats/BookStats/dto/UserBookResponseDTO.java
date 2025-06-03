package com.bookstats.BookStats.dto;

import com.bookstats.BookStats.entity.UserBook;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserBookResponseDTO {
    private Long id;
    private Long userId;
    private BookResponseDTO book;
    private UserBook.ReadingStatus status;
    private Integer rating;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String notes;
    private LocalDateTime createdAt;

}


package com.bookstats.BookStats.dto;

import com.bookstats.BookStats.entity.UserBook;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBookRequestDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Reading status is required")
    private UserBook.ReadingStatus status;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    private LocalDate startDate;

    private LocalDate finishDate;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    @AssertTrue(message = "Finish date must be after start date")
    public boolean isValidDateRange() {
        if (startDate == null || finishDate == null) {
            return true;
        }
        return !finishDate.isBefore(startDate);
    }

    @AssertTrue(message = "Completed books should have a finish date")
    public boolean isCompletedBookValid() {
        if (status == UserBook.ReadingStatus.COMPLETED) {
            return finishDate != null;
        }
        return true;
    }
}


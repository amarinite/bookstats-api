package com.bookstats.BookStats.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_books", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "book_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-userbooks")
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference("book-userbooks")
    @NotNull(message = "Book is required")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Reading status is required")
    private ReadingStatus status;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UserBook(User user, Book book, ReadingStatus status) {
        this.user = user;
        this.book = book;
        this.status = status;
    }

    public UserBook(User user, Book book, ReadingStatus status, Integer rating,
                    LocalDate startDate, LocalDate finishDate, String notes) {
        this.user = user;
        this.book = book;
        this.status = status;
        this.rating = rating;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.notes = notes;
    }

    public enum ReadingStatus {
        WANT_TO_READ("want_to_read"),
        READING("reading"),
        COMPLETED("completed");

        private final String value;

        ReadingStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static ReadingStatus fromString(String value) {
            for (ReadingStatus status : ReadingStatus.values()) {
                if (status.value.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown reading status: " + value);
        }
    }
}
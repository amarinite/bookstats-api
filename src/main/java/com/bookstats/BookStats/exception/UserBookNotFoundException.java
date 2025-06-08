package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserBookNotFoundException extends RuntimeException {

    private final Long userBookId;
    private final Long userId;
    private final Long bookId;

    public UserBookNotFoundException(String message) {
        super(message);
        this.userBookId = null;
        this.userId = null;
        this.bookId = null;
    }

    public UserBookNotFoundException(Long userBookId) {
        super(String.format("UserBook not found with id: %d", userBookId));
        this.userBookId = userBookId;
        this.userId = null;
        this.bookId = null;
    }

    public UserBookNotFoundException(Long userId, Long bookId) {
        super(String.format("UserBook not found for user: %d and book: %d", userId, bookId));
        this.userBookId = null;
        this.userId = userId;
        this.bookId = bookId;
    }

    public UserBookNotFoundException(String message, Long userBookId, Long userId, Long bookId) {
        super(message);
        this.userBookId = userBookId;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Long getUserBookId() {
        return userBookId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookId() {
        return bookId;
    }

    // Static factory methods
    public static UserBookNotFoundException byId(Long userBookId) {
        return new UserBookNotFoundException(userBookId);
    }

    public static UserBookNotFoundException byUserAndBook(Long userId, Long bookId) {
        return new UserBookNotFoundException(userId, bookId);
    }
}


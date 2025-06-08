package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserBookException extends RuntimeException {

    private final Long userId;
    private final Long bookId;

    public DuplicateUserBookException(String message) {
        super(message);
        this.userId = null;
        this.bookId = null;
    }

    public DuplicateUserBookException(Long userId, Long bookId) {
        super(String.format("User %d already has book %d in their library", userId, bookId));
        this.userId = userId;
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookId() {
        return bookId;
    }
}

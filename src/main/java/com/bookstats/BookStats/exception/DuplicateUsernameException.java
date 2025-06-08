package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUsernameException extends RuntimeException {

    private final String username;

    public DuplicateUsernameException(String username) {
        super(String.format("Username '%s' is already taken", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

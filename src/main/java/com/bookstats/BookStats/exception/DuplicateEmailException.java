package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {

    private final String email;

    public DuplicateEmailException(String email) {
        super(String.format("Email '%s' is already registered", email));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
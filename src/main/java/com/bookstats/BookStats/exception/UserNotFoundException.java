package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final Long userId;
    private final String username;
    private final String email;

    public UserNotFoundException(String message) {
        super(message);
        this.userId = null;
        this.username = null;
        this.email = null;
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.userId = null;
        this.username = null;
        this.email = null;
    }

    public UserNotFoundException(Long userId) {
        super(String.format("User not found with id: %d", userId));
        this.userId = userId;
        this.username = null;
        this.email = null;
    }

    public UserNotFoundException(String identifier, boolean isUsername) {
        super(isUsername ?
                String.format("User not found with username: %s", identifier) :
                String.format("User not found with email: %s", identifier));
        this.userId = null;
        if (isUsername) {
            this.username = identifier;
            this.email = null;
        } else {
            this.username = null;
            this.email = identifier;
        }
    }

    public UserNotFoundException(String message, Long userId) {
        super(message);
        this.userId = userId;
        this.username = null;
        this.email = null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    // Static factory methods for cleaner usage
    public static UserNotFoundException byId(Long id) {
        return new UserNotFoundException(id);
    }

    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException(username, true);
    }

    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException(email, false);
    }

    public static UserNotFoundException withMessage(String message) {
        return new UserNotFoundException(message);
    }
}

package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    private final Long bookId;
    private final String isbn;

    public BookNotFoundException(String message) {
        super(message);
        this.bookId = null;
        this.isbn = null;
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.bookId = null;
        this.isbn = null;
    }

    public BookNotFoundException(Long bookId) {
        super(String.format("Book not found with id: %d", bookId));
        this.bookId = bookId;
        this.isbn = null;
    }

    public BookNotFoundException(String isbn, boolean isIsbn) {
        super(String.format("Book not found with ISBN: %s", isbn));
        this.bookId = null;
        this.isbn = isbn;
    }

    public BookNotFoundException(String message, Long bookId) {
        super(message);
        this.bookId = bookId;
        this.isbn = null;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    // Static factory methods for cleaner usage
    public static BookNotFoundException byId(Long id) {
        return new BookNotFoundException(id);
    }

    public static BookNotFoundException byIsbn(String isbn) {
        return new BookNotFoundException(isbn, true);
    }

    public static BookNotFoundException withMessage(String message) {
        return new BookNotFoundException(message);
    }
}

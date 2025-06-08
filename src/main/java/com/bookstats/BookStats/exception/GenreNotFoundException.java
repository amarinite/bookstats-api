package com.bookstats.BookStats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GenreNotFoundException extends RuntimeException {

    private final Long genreId;
    private final String genreName;

    public GenreNotFoundException(String message) {
        super(message);
        this.genreId = null;
        this.genreName = null;
    }

    public GenreNotFoundException(Long genreId) {
        super(String.format("Genre not found with id: %d", genreId));
        this.genreId = genreId;
        this.genreName = null;
    }

    public GenreNotFoundException(String genreName, boolean isName) {
        super(String.format("Genre not found with name: %s", genreName));
        this.genreId = null;
        this.genreName = genreName;
    }

    public Long getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    // Static factory methods
    public static GenreNotFoundException byId(Long id) {
        return new GenreNotFoundException(id);
    }

    public static GenreNotFoundException byName(String name) {
        return new GenreNotFoundException(name, true);
    }
}

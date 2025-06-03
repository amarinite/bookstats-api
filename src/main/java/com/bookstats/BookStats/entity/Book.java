package com.bookstats.BookStats.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"genres", "userBooks"}) // Prevent circular references
@ToString(exclude = {"genres", "userBooks"}) // Prevent circular references
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 255, message = "Author must not exceed 255 characters")
    private String author;

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    private String isbn;

    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Column(name = "author_gender")
    @Enumerated(EnumType.STRING)
    private AuthorGender authorGender;

    @Column(name = "published_year")
    @Min(value = 1000, message = "Published year must be at least 1000")
    @Max(value = 2030, message = "Published year cannot be in the far future")
    private Integer publishedYear;

    @Column(name = "cover_url")
    private String coverUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonManagedReference("book-genres")
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("book-userbooks")
    private Set<UserBook> userBooks = new HashSet<>();

    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getBooks().remove(this);
    }

    public void addUserBook(UserBook userBook) {
        this.userBooks.add(userBook);
        userBook.setBook(this);
    }

    public void removeUserBook(UserBook userBook) {
        this.userBooks.remove(userBook);
        userBook.setBook(null);
    }

    public Book(String title, String author, String isbn, Integer pages,
                String language, String country, AuthorGender authorGender,
                Integer publishedYear, String coverUrl) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
        this.language = language;
        this.country = country;
        this.authorGender = authorGender;
        this.publishedYear = publishedYear;
        this.coverUrl = coverUrl;
    }

    public enum AuthorGender {
        MALE("male"),
        FEMALE("female"),
        NONBINARY("nonbinary"),
        UNKNOWN("unknown");

        private final String value;

        AuthorGender(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static AuthorGender fromString(String value) {
            for (AuthorGender gender : AuthorGender.values()) {
                if (gender.value.equalsIgnoreCase(value)) {
                    return gender;
                }
            }
            throw new IllegalArgumentException("Unknown author gender: " + value);
        }
    }
}

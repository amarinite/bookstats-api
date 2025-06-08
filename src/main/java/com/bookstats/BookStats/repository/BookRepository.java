package com.bookstats.BookStats.repository;

import com.bookstats.BookStats.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.id IN :genreIds")
    Page<Book> findByGenreIds(@Param("genreIds") Set<Long> genreIds, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.language = :language")
    Long countByLanguage(@Param("language") String language);
}

package com.bookstats.BookStats.repository;

import com.bookstats.BookStats.entity.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT g FROM Genre g JOIN g.books b GROUP BY g ORDER BY COUNT(b) DESC")
    List<Genre> findMostPopularGenres(Pageable pageable);
}
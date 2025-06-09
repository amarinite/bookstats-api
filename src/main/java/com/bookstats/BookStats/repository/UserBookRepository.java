package com.bookstats.BookStats.repository;

import com.bookstats.BookStats.dto.UserReadingStatsDTO;
import com.bookstats.BookStats.entity.UserBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    Page<UserBook> findByUserId(Long userId, Pageable pageable);

    Page<UserBook> findByUserIdAndStatus(Long userId, UserBook.ReadingStatus status, Pageable pageable);

    Optional<UserBook> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    @Query("SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.status = :status")
    Long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") UserBook.ReadingStatus status);

    @Query("SELECT AVG(ub.rating) FROM UserBook ub WHERE ub.user.id = :userId AND ub.rating IS NOT NULL")
    Double getAverageRatingByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.status = 'COMPLETED'")
    Long countCompletedBooksByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.status = 'READING'")
    Long countReadingBooksByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.status = 'WANT_TO_READ'")
    Long countWantToReadBooksByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(b.pages) FROM UserBook ub JOIN ub.book b WHERE ub.user.id = :userId AND ub.status = 'COMPLETED'")
    Long getTotalPagesReadByUserId(@Param("userId") Long userId);
}

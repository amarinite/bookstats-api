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

    // Custom method for user reading statistics
    @Query("""
        SELECT new com.bookstats.dto.UserReadingStatsDTO(
            u.id,
            u.username,
            CAST(COUNT(CASE WHEN ub.status = 'COMPLETED' THEN 1 END) AS integer),
            CAST(COUNT(CASE WHEN ub.status = 'READING' THEN 1 END) AS integer),
            CAST(COUNT(CASE WHEN ub.status = 'WANT_TO_READ' THEN 1 END) AS integer),
            AVG(CASE WHEN ub.rating IS NOT NULL THEN ub.rating END),
            CAST(SUM(CASE WHEN ub.status = 'COMPLETED' THEN b.pages ELSE 0 END) AS integer),
            null
        )
        FROM User u
        LEFT JOIN u.userBooks ub
        LEFT JOIN ub.book b
        WHERE u.id = :userId
        GROUP BY u.id, u.username
    """)
    UserReadingStatsDTO getUserReadingStats(@Param("userId") Long userId);
}

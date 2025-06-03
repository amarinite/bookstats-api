package com.bookstats.BookStats.repository;

import com.bookstats.BookStats.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findByUserId(Long userId);
    List<UserBook> findByUserIdAndStatus(Long userId, UserBook.ReadingStatus status);
}


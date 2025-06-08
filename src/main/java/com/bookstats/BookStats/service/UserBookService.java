package com.bookstats.BookStats.service;

import com.bookstats.BookStats.dto.UserReadingStatsDTO;
import com.bookstats.BookStats.dto.request.UserBookRequestDTO;
import com.bookstats.BookStats.dto.response.UserBookResponseDTO;
import com.bookstats.BookStats.entity.Book;
import com.bookstats.BookStats.entity.User;
import com.bookstats.BookStats.entity.UserBook;
import com.bookstats.BookStats.exception.BookNotFoundException;
import com.bookstats.BookStats.exception.DuplicateUserBookException;
import com.bookstats.BookStats.exception.UserBookNotFoundException;
import com.bookstats.BookStats.exception.UserNotFoundException;
import com.bookstats.BookStats.mapper.UserBookMapper;
import com.bookstats.BookStats.repository.BookRepository;
import com.bookstats.BookStats.repository.UserBookRepository;
import com.bookstats.BookStats.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserBookMapper userBookMapper;

    public UserBookService(UserBookRepository userBookRepository,
                           UserRepository userRepository,
                           BookRepository bookRepository,
                           UserBookMapper userBookMapper) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userBookMapper = userBookMapper;
    }

    public UserBookResponseDTO addBookToUserLibrary(Long userId, UserBookRequestDTO requestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + requestDTO.getBookId()));

        // Check if user already has this book
        if (userBookRepository.existsByUserIdAndBookId(userId, requestDTO.getBookId())) {
            throw new DuplicateUserBookException("User already has this book in their library");
        }

        UserBook userBook = userBookMapper.toEntity(requestDTO);
        userBook.setUser(user);
        userBook.setBook(book);

        UserBook savedUserBook = userBookRepository.save(userBook);
        log.info("User {} added book {} with status {}", userId, book.getTitle(), requestDTO.getStatus());

        return userBookMapper.toResponseDTO(savedUserBook);
    }

    public UserBookResponseDTO updateUserBook(Long userId, Long userBookId, UserBookRequestDTO requestDTO) {
        UserBook userBook = userBookRepository.findByIdAndUserId(userBookId, userId)
                .orElseThrow(() -> new UserBookNotFoundException("UserBook not found"));

        userBookMapper.updateEntityFromDTO(requestDTO, userBook);

        UserBook savedUserBook = userBookRepository.save(userBook);
        log.info("Updated user book: {}", savedUserBook.getId());

        return userBookMapper.toResponseDTO(savedUserBook);
    }

    @Transactional(readOnly = true)
    public Page<UserBookResponseDTO> getUserBooks(Long userId, UserBook.ReadingStatus status, Pageable pageable) {
        Page<UserBook> userBooks;
        if (status != null) {
            userBooks = userBookRepository.findByUserIdAndStatus(userId, status, pageable);
        } else {
            userBooks = userBookRepository.findByUserId(userId, pageable);
        }
        return userBooks.map(userBookMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UserReadingStatsDTO getUserReadingStats(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        // Use custom repository methods or native queries for statistics
        return userBookRepository.getUserReadingStats(userId);
    }

    public void removeBookFromUserLibrary(Long userId, Long userBookId) {
        UserBook userBook = userBookRepository.findByIdAndUserId(userBookId, userId)
                .orElseThrow(() -> new UserBookNotFoundException("UserBook not found"));

        userBookRepository.delete(userBook);
        log.info("Removed book from user {} library: {}", userId, userBookId);
    }
}

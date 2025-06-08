package com.bookstats.BookStats.service;

import com.bookstats.BookStats.dto.BookSearchDTO;
import com.bookstats.BookStats.dto.request.BookRequestDTO;
import com.bookstats.BookStats.dto.response.BookResponseDTO;
import com.bookstats.BookStats.entity.Book;
import com.bookstats.BookStats.entity.Genre;
import com.bookstats.BookStats.exception.BookNotFoundException;
import com.bookstats.BookStats.exception.GenreNotFoundException;
import com.bookstats.BookStats.mapper.BookMapper;
import com.bookstats.BookStats.repository.BookRepository;
import com.bookstats.BookStats.repository.GenreRepository;
import com.bookstats.BookStats.specification.BookSpecification;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper; // MapStruct mapper

    public BookService(BookRepository bookRepository,
                       GenreRepository genreRepository,
                       BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return bookMapper.toResponseDTO(book);
    }

    public BookResponseDTO createBook(BookRequestDTO requestDTO) {
        // Validate genres exist
        Set<Genre> genres = validateAndGetGenres(requestDTO.getGenreIds());

        Book book = bookMapper.toEntity(requestDTO);
        book.setGenres(genres);

        Book savedBook = bookRepository.save(book);
        log.info("Created book: {} by {}", savedBook.getTitle(), savedBook.getAuthor());

        return bookMapper.toResponseDTO(savedBook);
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        // Update fields
        bookMapper.updateEntityFromDTO(requestDTO, existingBook);

        // Update genres if provided
        if (requestDTO.getGenreIds() != null) {
            Set<Genre> genres = validateAndGetGenres(requestDTO.getGenreIds());
            existingBook.setGenres(genres);
        }

        Book savedBook = bookRepository.save(existingBook);
        log.info("Updated book: {}", savedBook.getTitle());

        return bookMapper.toResponseDTO(savedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Deleted book with id: {}", id);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> searchBooks(BookSearchDTO searchDTO, Pageable pageable) {
        // Use Specification pattern for dynamic queries
        Specification<Book> spec = BookSpecification.withCriteria(searchDTO);
        return bookRepository.findAll(spec, pageable)
                .map(bookMapper::toResponseDTO);
    }

    private Set<Genre> validateAndGetGenres(Set<Long> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(genreIds));
        if (genres.size() != genreIds.size()) {
            throw new GenreNotFoundException("One or more genres not found");
        }
        return genres;
    }
}

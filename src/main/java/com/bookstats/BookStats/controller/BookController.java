package com.bookstats.BookStats.controller;

import com.bookstats.BookStats.dto.BookSearchDTO;
import com.bookstats.BookStats.dto.request.BookRequestDTO;
import com.bookstats.BookStats.dto.response.BookResponseDTO;
import com.bookstats.BookStats.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@Validated
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        Page<BookResponseDTO> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        BookResponseDTO createdBook = bookService.createBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO requestDTO) {
        BookResponseDTO updatedBook = bookService.updateBook(id, requestDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponseDTO>> searchBooks(
            @Valid BookSearchDTO searchDTO,
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        Page<BookResponseDTO> books = bookService.searchBooks(searchDTO, pageable);
        return ResponseEntity.ok(books);
    }
}
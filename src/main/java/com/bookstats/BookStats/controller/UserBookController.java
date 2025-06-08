package com.bookstats.BookStats.controller;


import com.bookstats.BookStats.dto.UserReadingStatsDTO;
import com.bookstats.BookStats.dto.request.UserBookRequestDTO;
import com.bookstats.BookStats.dto.response.UserBookResponseDTO;
import com.bookstats.BookStats.entity.UserBook;
import com.bookstats.BookStats.service.UserBookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users/{userId}/books")
@Validated
@Slf4j
public class UserBookController {

    private final UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @PostMapping
    public ResponseEntity<UserBookResponseDTO> addBookToLibrary(
            @PathVariable Long userId,
            @Valid @RequestBody UserBookRequestDTO requestDTO) {
        UserBookResponseDTO userBook = userBookService.addBookToUserLibrary(userId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userBook);
    }

    @GetMapping
    public ResponseEntity<Page<UserBookResponseDTO>> getUserBooks(
            @PathVariable Long userId,
            @RequestParam(required = false) UserBook.ReadingStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserBookResponseDTO> userBooks = userBookService.getUserBooks(userId, status, pageable);
        return ResponseEntity.ok(userBooks);
    }

    @PutMapping("/{userBookId}")
    public ResponseEntity<UserBookResponseDTO> updateUserBook(
            @PathVariable Long userId,
            @PathVariable Long userBookId,
            @Valid @RequestBody UserBookRequestDTO requestDTO) {
        UserBookResponseDTO updatedUserBook = userBookService.updateUserBook(userId, userBookId, requestDTO);
        return ResponseEntity.ok(updatedUserBook);
    }

    @DeleteMapping("/{userBookId}")
    public ResponseEntity<Void> removeBookFromLibrary(
            @PathVariable Long userId,
            @PathVariable Long userBookId) {
        userBookService.removeBookFromUserLibrary(userId, userBookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<UserReadingStatsDTO> getUserReadingStats(@PathVariable Long userId) {
        UserReadingStatsDTO stats = userBookService.getUserReadingStats(userId);
        return ResponseEntity.ok(stats);
    }
}
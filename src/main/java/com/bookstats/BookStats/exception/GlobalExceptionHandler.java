package com.bookstats.BookStats.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ============================================================================
    // Resource Not Found Exceptions
    // ============================================================================

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException ex, WebRequest request) {
        log.warn("Book not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Book Not Found")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        // Add specific details if available
        if (ex.getBookId() != null) {
            errorResponse.addDetail("bookId", ex.getBookId().toString());
        }
        if (ex.getIsbn() != null) {
            errorResponse.addDetail("isbn", ex.getIsbn());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        log.warn("User not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("User Not Found")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        // Add specific details if available
        if (ex.getUserId() != null) {
            errorResponse.addDetail("userId", ex.getUserId().toString());
        }
        if (ex.getUsername() != null) {
            errorResponse.addDetail("username", ex.getUsername());
        }
        if (ex.getEmail() != null) {
            errorResponse.addDetail("email", ex.getEmail());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserBookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserBookNotFound(UserBookNotFoundException ex, WebRequest request) {
        log.warn("UserBook not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("User Book Not Found")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        if (ex.getUserBookId() != null) {
            errorResponse.addDetail("userBookId", ex.getUserBookId().toString());
        }
        if (ex.getUserId() != null) {
            errorResponse.addDetail("userId", ex.getUserId().toString());
        }
        if (ex.getBookId() != null) {
            errorResponse.addDetail("bookId", ex.getBookId().toString());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenreNotFound(GenreNotFoundException ex, WebRequest request) {
        log.warn("Genre not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Genre Not Found")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        if (ex.getGenreId() != null) {
            errorResponse.addDetail("genreId", ex.getGenreId().toString());
        }
        if (ex.getGenreName() != null) {
            errorResponse.addDetail("genreName", ex.getGenreName());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // ============================================================================
    // Conflict/Duplicate Exceptions
    // ============================================================================

    @ExceptionHandler(DuplicateUserBookException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserBook(DuplicateUserBookException ex, WebRequest request) {
        log.warn("Duplicate user book: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Duplicate User Book")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        if (ex.getUserId() != null) {
            errorResponse.addDetail("userId", ex.getUserId().toString());
        }
        if (ex.getBookId() != null) {
            errorResponse.addDetail("bookId", ex.getBookId().toString());
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsername(DuplicateUsernameException ex, WebRequest request) {
        log.warn("Duplicate username: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Duplicate Username")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        errorResponse.addDetail("username", ex.getUsername());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex, WebRequest request) {
        log.warn("Duplicate email: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Duplicate Email")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        errorResponse.addDetail("email", ex.getEmail());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // ============================================================================
    // JPA and General Exceptions
    // ============================================================================

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        log.warn("Entity not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Entity Not Found")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.warn("Illegal argument: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Argument")
                .message(ex.getMessage())
                .path(getPath(request))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // ============================================================================
    // Validation Exceptions
    // ============================================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("Validation failed: {}", ex.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        List<String> globalErrors = ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Invalid input provided")
                .path(getPath(request))
                .build();

        errorResponse.addDetail("fieldErrors", fieldErrors);
        if (!globalErrors.isEmpty()) {
            errorResponse.addDetail("globalErrors", globalErrors);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log.warn("Constraint violation: {}", ex.getMessage());

        Map<String, String> validationErrors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            validationErrors.put(fieldName, errorMessage);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Constraint Violation")
                .message("Constraint validation failed")
                .path(getPath(request))
                .build();

        errorResponse.addDetail("validationErrors", validationErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.warn("Type mismatch: {}", ex.getMessage());

        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Type Mismatch")
                .message(message)
                .path(getPath(request))
                .build();

        errorResponse.addDetail("parameter", ex.getName());
        errorResponse.addDetail("providedValue", String.valueOf(ex.getValue()));
        errorResponse.addDetail("expectedType", ex.getRequiredType().getSimpleName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // ============================================================================
    // Generic Exception Handler
    // ============================================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .path(getPath(request))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // ============================================================================
    // Helper Methods
    // ============================================================================

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    // ============================================================================
    // Static Error Response Class
    // ============================================================================

    @Data
    @lombok.Builder
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, Object> details;

        public void addDetail(String key, Object value) {
            if (details == null) {
                details = new HashMap<>();
            }
            details.put(key, value);
        }
    }
}
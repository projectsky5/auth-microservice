package com.projectsky.auth_microservice.exception;

import com.projectsky.auth_microservice.exception.dto.ErrorResponse;
import com.projectsky.auth_microservice.exception.dto.SubError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<SubError> subErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> SubError.builder()
                        .object(error.getObjectName())
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Validation failed")
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(subErrors)
                        .build());
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRoleException(InvalidRoleException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }

    @ExceptionHandler(ConfirmationFailedException.class)
    public ResponseEntity<ErrorResponse> handleConfirmationFailedException(ConfirmationFailedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Server Error")
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .subErrors(Collections.emptyList())
                        .build()
        );
    }
}

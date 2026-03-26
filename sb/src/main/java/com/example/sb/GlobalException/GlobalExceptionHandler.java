package com.example.sb.GlobalException;

import com.example.sb.GlobalException.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for handling application-wide exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles runtime exceptions such as user not found or invalid input.
     *
     * @param ex the runtime exception
     * @return response entity with error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            final RuntimeException ex
    ) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all generic exceptions.
     *
     * @param ex the exception
     * @return response entity with generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            final Exception ex
    ) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

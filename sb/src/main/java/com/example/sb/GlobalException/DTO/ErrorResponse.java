package com.example.sb.GlobalException.DTO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for representing error responses.
 */
@Data
public class ErrorResponse {

    /**
     * Timestamp when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * Error message describing the issue.
     */
    private String message;

    /**
     * HTTP status code.
     */
    private int status;

    /**
     * Constructs an ErrorResponse.
     *
     * @param errorTimestamp the time of error
     * @param errorMessage the error message
     * @param errorStatus the HTTP status code
     */
    public ErrorResponse(
            final LocalDateTime errorTimestamp,
            final String errorMessage,
            final int errorStatus
    ) {
        this.timestamp = errorTimestamp;
        this.message = errorMessage;
        this.status = errorStatus;
    }
}

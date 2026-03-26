package com.example.sb.GlobalException;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException.
     *
     * @param message the error message
     */
    public UserNotFoundException(final String message) {
        super(message);
    }
}

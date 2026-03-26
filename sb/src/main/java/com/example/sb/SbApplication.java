package com.example.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main entry point for the Spring Boot application.
 */
@SpringBootApplication
@EnableCaching
public final class SbApplication {
    /**
     * Private constructor to prevent instantiation.
     */
    private SbApplication() {
    }
    /**
     * Main method to launch the application.
     *
     * @param args application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SbApplication.class, args);
    }
}

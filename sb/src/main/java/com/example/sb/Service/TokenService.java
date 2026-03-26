package com.example.sb.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing token caching operations.
 */
@Service
public class TokenService {

    /**
     * Stores a token in the cache.
     *
     * @param token the token to be stored
     * @return the stored token
     */
    @CachePut(value = "tokens", key = "#token")
    public String storeToken(final String token) {
        return token;
    }

    /**
     * Retrieves a token from the cache.
     *
     * @param token the token key
     * @return the cached token, or null if not found
     */
    @Cacheable(value = "tokens", key = "#token")
    public String getToken(final String token) {
        return null; // if not in cache
    }

    /**
     * Removes a token from the cache.
     *
     * @param token the token to be removed
     */
    @CacheEvict(value = "tokens", key = "#token")
    public void removeToken(final String token) {
    }
}

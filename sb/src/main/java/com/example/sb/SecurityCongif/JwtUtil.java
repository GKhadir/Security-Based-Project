package com.example.sb.SecurityCongif;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JwtUtil {

    /**
     * Secret key used for signing JWT tokens.
     */
    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    /**
     * Generates signing key from secret.
     *
     * @return signing key
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Generates a JWT token for a username.
     *
     * @param username the username
     * @return generated JWT token
     */
    public String generateToken(final String username) {
        final long expirationTime = 3600000;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expirationTime
                        )
                ) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts username from JWT token.
     *
     * @param token the JWT token
     * @return username
     */
    public String extractUsername(final String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extracts claims from JWT token.
     *
     * @param token the JWT token
     * @return claims
     */
    private Claims getClaims(final String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token
     * @param username expected username
     * @return true if valid, false otherwise
     */
    public boolean validateToken(
            final String token,
            final String username
    ) {

        final String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }

    /**
     * Checks whether token is expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(final String token) {

        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }
}

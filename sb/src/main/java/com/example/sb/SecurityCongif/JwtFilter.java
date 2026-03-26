package com.example.sb.SecurityCongif;

import com.example.sb.Entity.Users;
import com.example.sb.Service.CustomUserDetailsService;
import com.example.sb.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT filter for validating tokens and setting authentication context.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Utility class for JWT operations.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Service to load user details.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Service for token cache management.
     */
    @Autowired
    private TokenService tokenService;

    /**
     * Filters incoming requests and validates JWT tokens.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param chain filter chain
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain
    ) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        final int tokenPrefixLength  = 7;
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(tokenPrefixLength);

            final String cachedToken = tokenService.getToken(token);

            if (cachedToken == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            final String username = jwtUtil.extractUsername(token);

            if (username != null
                    && SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                Users userDetails =
                        userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(
                        token,
                        userDetails.getUsername()
                )) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority(
                                                    userDetails.getRole()
                                            )
                                    )
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);
                }
            }
        }

        chain.doFilter(request, response);
    }
}

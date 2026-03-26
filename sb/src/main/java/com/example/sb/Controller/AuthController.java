package com.example.sb.Controller;

import com.example.sb.Entity.Users;
import com.example.sb.SecurityCongif.JwtUtil;
import com.example.sb.Service.AuthService;
import com.example.sb.Service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for authentication and user-related operations.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    /**
     * Service for authentication logic.
     */
    @Autowired
    private AuthService service;

    /**
     * Utility for JWT operations.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Service for token cache management.
     */
    @Autowired
    private TokenService tokenService;

    /**
     * Registers a new user.
     *
     * @param user user details
     * @return success message
     */
    @PostMapping("/auth/register")
    public String register(@RequestBody final Users user) {
        return service.register(user);
    }

    /**
     * Authenticates user and returns JWT token.
     *
     * @param user login request containing username and password
     * @return map containing JWT token
     */
    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody final Users user) {

        Users validUser = service.login(
                user.getUsername(),
                user.getPassword()
        );

        String token = jwtUtil.generateToken(
                validUser.getUsername()
        );

        tokenService.storeToken(token);

        return Map.of("token", token);
    }

    /**
     * Retrieves all users (Admin only).
     *
     * @return list of users
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> profile() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Logs out user by invalidating JWT token.
     *
     * @param request HTTP request
     * @return logout message
     */
    @PostMapping("/logout")
    public String logout(final HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            final int tokenPrefixLength  = 7;
            String token = header.substring(tokenPrefixLength);

            tokenService.removeToken(token);
        }

        return "Logged out successfully";
    }
}

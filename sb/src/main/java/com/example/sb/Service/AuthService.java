/**
 * Contains service layer classes for authentication and user management.
 */
package com.example.sb.Service;

import com.example.sb.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import com.example.sb.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for authentication and user management.
 */
@Service
public class AuthService {

    /**
     * Repository for user data access.
     */
    @Autowired
    private UserRepository repo;

    /**
     * Password encoder for hashing passwords.
     */
    @Autowired
    private PasswordEncoder encoder;

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return success message
     */
    public String register(final Users user) {
        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() != null) {
            user.setRole(user.getRole());
        }

        repo.save(user);
        return "Registered successfully";
    }

    /**
     * Authenticates a user with username and password.
     *
     * @param username the username
     * @param password the raw password
     * @return authenticated user
     */
    @Cacheable(value = "user", key = "#username")
    public Users login(final String username, final String password) {
        Users user = repo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    /**
     * Retrieves all users.
     *
     * @return list of users
     */
    @Cacheable("users")
    public List<Users> getAllUsers() {
        return repo.findAll();
    }
}

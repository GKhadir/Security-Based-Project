package com.example.sb.Service;

import com.example.sb.Entity.Users;
import com.example.sb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for loading user details.
 */
@Service
public class CustomUserDetailsService {
    /**
     * Repository used to access user data.
     */
    @Autowired
    private UserRepository repo;

    /**
     * Loads a user by username.
     *
     * @param username the username of the user
     * @return the user entity
     * @throws UsernameNotFoundException if user is not found
     */
    @Cacheable("user")
    public Users loadUserByUsername(final String username) {
        return repo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );
    }
}

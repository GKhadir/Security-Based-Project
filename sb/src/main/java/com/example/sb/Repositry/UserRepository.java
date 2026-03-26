package com.example.sb.Repository;

import com.example.sb.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing user data.
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return optional user
     */
    Optional<Users> findByUsername(String username);
}

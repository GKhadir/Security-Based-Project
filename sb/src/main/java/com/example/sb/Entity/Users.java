package com.example.sb.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class representing application users.
 */
@Entity
public class Users {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Encrypted password of the user.
     */
    private String password;

    /**
     * Role assigned to the user (e.g., ADMIN, USER).
     */
    private String role;

    /**
     * Gets user ID.
     *
     * @return user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets user ID.
     *
     * @param newId user ID
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * Gets username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param newUsername username
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    /**
     * Gets password.
     *
     * @return encrypted password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param newPassword encrypted password
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    /**
     * Gets user role.
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets user role.
     *
     * @param newRole role
     */
    public void setRole(final String newRole) {
        this.role = newRole;
    }

    /**
     * Returns string representation of user.
     *
     * @return username
     */
    @Override
    public String toString() {
        return username;
    }
}

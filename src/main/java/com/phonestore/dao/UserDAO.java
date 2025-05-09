package com.phonestore.dao;

import com.phonestore.model.User;

/**
 * UserDAO interface for User-specific operations
 */
public interface UserDAO extends GenericDAO<User, Integer> {
    
    /**
     * Find a user by username
     * @param username The username to search for
     * @return The user or null if not found
     */
    User findByUsername(String username);
    
    /**
     * Find a user by email
     * @param email The email to search for
     * @return The user or null if not found
     */
    User findByEmail(String email);
    
    /**
     * Authenticate a user with username and password
     * @param username The username
     * @param password The password (plain text)
     * @return The authenticated user or null if authentication fails
     */
    User authenticate(String username, String password);
    
    /**
     * Update a user's profile
     * @param user The user with updated information
     * @return The updated user
     */
    User updateProfile(User user);
    
    /**
     * Update a user's password
     * @param userId The user ID
     * @param newPassword The new password (plain text)
     * @return true if updated, false otherwise
     */
    boolean updatePassword(int userId, String newPassword);
}

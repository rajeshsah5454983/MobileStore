package com.phonestore.dao;

import java.util.List;

/**
 * Generic DAO interface with common CRUD operations
 * @param <T> The model class
 * @param <ID> The ID type
 */
public interface GenericDAO<T, ID> {
    
    /**
     * Find an entity by its ID
     * @param id The ID of the entity
     * @return The entity or null if not found
     */
    T findById(ID id);
    
    /**
     * Find all entities
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Save an entity (create or update)
     * @param entity The entity to save
     * @return The saved entity with ID populated
     */
    T save(T entity);
    
    /**
     * Delete an entity by its ID
     * @param id The ID of the entity to delete
     * @return true if deleted, false otherwise
     */
    boolean deleteById(ID id);
}

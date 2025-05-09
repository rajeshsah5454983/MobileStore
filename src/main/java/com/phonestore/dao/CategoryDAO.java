package com.phonestore.dao;

import com.phonestore.model.Category;

/**
 * CategoryDAO interface for Category-specific operations
 */
public interface CategoryDAO extends GenericDAO<Category, Integer> {
    
    /**
     * Find a category by name
     * @param name The category name
     * @return The category or null if not found
     */
    Category findByName(String name);
}

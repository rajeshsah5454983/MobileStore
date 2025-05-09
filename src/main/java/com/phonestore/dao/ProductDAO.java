package com.phonestore.dao;

import com.phonestore.model.Product;

import java.util.List;

/**
 * ProductDAO interface for Product-specific operations
 */
public interface ProductDAO extends GenericDAO<Product, Integer> {
    
    /**
     * Find products by category ID
     * @param categoryId The category ID
     * @return List of products in the category
     */
    List<Product> findByCategoryId(int categoryId);
    
    /**
     * Search products by name or description
     * @param keyword The search keyword
     * @return List of matching products
     */
    List<Product> search(String keyword);
    
    /**
     * Update product stock
     * @param productId The product ID
     * @param quantity The quantity to add (positive) or subtract (negative)
     * @return true if updated, false otherwise
     */
    boolean updateStock(int productId, int quantity);
    
    /**
     * Find featured products (can be implemented based on criteria like newest, most popular, etc.)
     * @param limit The maximum number of products to return
     * @return List of featured products
     */
    List<Product> findFeatured(int limit);
}

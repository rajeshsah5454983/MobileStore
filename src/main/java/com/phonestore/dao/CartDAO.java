package com.phonestore.dao;

import com.phonestore.model.Cart;
import com.phonestore.model.CartItem;

/**
 * CartDAO interface for Cart-specific operations
 */
public interface CartDAO extends GenericDAO<Cart, Integer> {
    
    /**
     * Find a cart by user ID
     * @param userId The user ID
     * @return The cart or null if not found
     */
    Cart findByUserId(int userId);
    
    /**
     * Add an item to the cart
     * @param cartItem The cart item to add
     * @return The added cart item with ID populated
     */
    CartItem addCartItem(CartItem cartItem);
    
    /**
     * Update a cart item
     * @param cartItem The cart item to update
     * @return The updated cart item
     */
    CartItem updateCartItem(CartItem cartItem);
    
    /**
     * Remove a cart item
     * @param cartItemId The cart item ID
     * @return true if removed, false otherwise
     */
    boolean removeCartItem(int cartItemId);
    
    /**
     * Clear all items from a cart
     * @param cartId The cart ID
     * @return true if cleared, false otherwise
     */
    boolean clearCart(int cartId);
}

package com.phonestore.dao;

import com.phonestore.model.Order;
import com.phonestore.model.OrderItem;

import java.util.List;

/**
 * OrderDAO interface for Order-specific operations
 */
public interface OrderDAO extends GenericDAO<Order, Integer> {
    
    /**
     * Find orders by user ID
     * @param userId The user ID
     * @return List of orders for the user
     */
    List<Order> findByUserId(int userId);
    
    /**
     * Add an item to an order
     * @param orderItem The order item to add
     * @return The added order item with ID populated
     */
    OrderItem addOrderItem(OrderItem orderItem);
    
    /**
     * Update order status
     * @param orderId The order ID
     * @param status The new status
     * @return true if updated, false otherwise
     */
    boolean updateStatus(int orderId, String status);
    
    /**
     * Create an order from a cart
     * @param order The order to create
     * @param cartId The cart ID to convert to an order
     * @return The created order with ID populated
     */
    Order createFromCart(Order order, int cartId);
}

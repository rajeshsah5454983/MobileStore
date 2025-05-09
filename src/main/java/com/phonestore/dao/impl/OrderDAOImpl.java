package com.phonestore.dao.impl;

import com.phonestore.dao.CartDAO;
import com.phonestore.dao.OrderDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Cart;
import com.phonestore.model.CartItem;
import com.phonestore.model.Order;
import com.phonestore.model.OrderItem;
import com.phonestore.model.Product;
import com.phonestore.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of OrderDAO interface
 */
public class OrderDAOImpl implements OrderDAO {
    
    private ProductDAO productDAO;
    private CartDAO cartDAO;
    
    public OrderDAOImpl() {
        this.productDAO = new ProductDAOImpl();
        this.cartDAO = new CartDAOImpl();
    }

    @Override
    public Order findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Order order = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                order = mapResultSetToOrder(rs);
                order.setOrderItems(findOrderItemsByOrderId(order.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return order;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Order> orders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                order.setOrderItems(findOrderItemsByOrderId(order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return orders;
    }

    @Override
    public Order save(Order order) {
        if (order.getId() > 0) {
            return update(order);
        } else {
            return insert(order);
        }
    }

    private Order insert(Order order) {
        String sql = "INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, order.getUserId());
            stmt.setBigDecimal(2, order.getTotalAmount());
            stmt.setString(3, order.getStatus());
            stmt.setString(4, order.getShippingAddress());
            stmt.setString(5, order.getPaymentMethod());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return order;
    }

    private Order update(Order order) {
        String sql = "UPDATE orders SET total_amount = ?, status = ?, shipping_address = ?, payment_method = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setBigDecimal(1, order.getTotalAmount());
            stmt.setString(2, order.getStatus());
            stmt.setString(3, order.getShippingAddress());
            stmt.setString(4, order.getPaymentMethod());
            stmt.setInt(5, order.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        
        return order;
    }

    @Override
    public boolean deleteById(Integer id) {
        // In a real application, you might want to implement a soft delete instead
        String sql = "DELETE FROM orders WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            
            // First, delete all order items
            String deleteItemsSql = "DELETE FROM order_items WHERE order_id = ?";
            PreparedStatement deleteItemsStmt = conn.prepareStatement(deleteItemsSql);
            deleteItemsStmt.setInt(1, id);
            deleteItemsStmt.executeUpdate();
            deleteItemsStmt.close();
            
            // Then delete the order
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        
        return success;
    }

    @Override
    public List<Order> findByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Order> orders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                order.setOrderItems(findOrderItemsByOrderId(order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return orders;
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setBigDecimal(4, orderItem.getPrice());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating order item failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderItem.setId(rs.getInt(1));
            } else {
                throw new SQLException("Creating order item failed, no ID obtained.");
            }
            
            // Update product stock
            productDAO.updateStock(orderItem.getProductId(), -orderItem.getQuantity());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return orderItem;
    }

    @Override
    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            
            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        
        return success;
    }

    @Override
    public Order createFromCart(Order order, int cartId) {
        Connection conn = null;
        boolean autoCommit = true;
        
        try {
            conn = DBUtil.getConnection();
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            
            // Insert the order
            order = insert(order);
            
            // Get the cart and its items
            Cart cart = cartDAO.findById(cartId);
            if (cart != null && !cart.getCartItems().isEmpty()) {
                // Add each cart item as an order item
                for (CartItem cartItem : cart.getCartItems()) {
                    OrderItem orderItem = new OrderItem(
                            order.getId(),
                            cartItem.getProductId(),
                            cartItem.getQuantity(),
                            cartItem.getPrice()
                    );
                    addOrderItem(orderItem);
                }
                
                // Clear the cart
                cartDAO.clearCart(cartId);
            }
            
            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(autoCommit);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return findById(order.getId());
    }

    // Helper methods
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setUpdatedAt(rs.getTimestamp("updated_at"));
        return order;
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setPrice(rs.getBigDecimal("price"));
        orderItem.setCreatedAt(rs.getTimestamp("created_at"));
        
        // Load the product details
        Product product = productDAO.findById(orderItem.getProductId());
        orderItem.setProduct(product);
        
        return orderItem;
    }

    private List<OrderItem> findOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<OrderItem> orderItems = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderItem orderItem = mapResultSetToOrderItem(rs);
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return orderItems;
    }
}

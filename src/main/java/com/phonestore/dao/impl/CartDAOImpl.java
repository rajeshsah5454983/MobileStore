package com.phonestore.dao.impl;

import com.phonestore.dao.CartDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Cart;
import com.phonestore.model.CartItem;
import com.phonestore.model.Product;
import com.phonestore.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CartDAO interface
 */
public class CartDAOImpl implements CartDAO {
    
    private ProductDAO productDAO;
    
    public CartDAOImpl() {
        this.productDAO = new ProductDAOImpl();
    }

    @Override
    public Cart findById(Integer id) {
        String sql = "SELECT * FROM carts WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cart cart = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cart = mapResultSetToCart(rs);
                cart.setCartItems(findCartItemsByCartId(cart.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return cart;
    }

    @Override
    public List<Cart> findAll() {
        String sql = "SELECT * FROM carts";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cart> carts = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cart cart = mapResultSetToCart(rs);
                cart.setCartItems(findCartItemsByCartId(cart.getId()));
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return carts;
    }

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() > 0) {
            return update(cart);
        } else {
            return insert(cart);
        }
    }

    private Cart insert(Cart cart) {
        String sql = "INSERT INTO carts (user_id) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, cart.getUserId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating cart failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cart.setId(rs.getInt(1));
            } else {
                throw new SQLException("Creating cart failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return cart;
    }

    private Cart update(Cart cart) {
        // For a cart, we typically don't need to update the cart itself,
        // but rather the cart items. This method is included for completeness.
        return cart;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM carts WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            
            // First, clear all cart items
            clearCart(id);
            
            // Then delete the cart
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
    public Cart findByUserId(int userId) {
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cart cart = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cart = mapResultSetToCart(rs);
                cart.setCartItems(findCartItemsByCartId(cart.getId()));
            } else {
                // If no cart exists for this user, create one
                cart = new Cart(userId);
                cart = insert(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return cart;
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        // Check if the item already exists in the cart
        CartItem existingItem = findCartItemByProductId(cartItem.getCartId(), cartItem.getProductId());
        
        if (existingItem != null) {
            // Update quantity if item already exists
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            return updateCartItem(existingItem);
        } else {
            // Insert new item if it doesn't exist
            String sql = "INSERT INTO cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            try {
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, cartItem.getCartId());
                stmt.setInt(2, cartItem.getProductId());
                stmt.setInt(3, cartItem.getQuantity());
                stmt.setBigDecimal(4, cartItem.getPrice());
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Creating cart item failed, no rows affected.");
                }
                
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cartItem.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Creating cart item failed, no ID obtained.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs, stmt, conn);
            }
            
            return cartItem;
        }
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        String sql = "UPDATE cart_items SET quantity = ?, price = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartItem.getQuantity());
            stmt.setBigDecimal(2, cartItem.getPrice());
            stmt.setInt(3, cartItem.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        
        return cartItem;
    }

    @Override
    public boolean removeCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartItemId);
            
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
    public boolean clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartId);
            
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        
        return success;
    }

    // Helper methods
    private Cart mapResultSetToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at"));
        cart.setUpdatedAt(rs.getTimestamp("updated_at"));
        return cart;
    }

    private CartItem mapResultSetToCartItem(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getInt("id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setProductId(rs.getInt("product_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setPrice(rs.getBigDecimal("price"));
        cartItem.setCreatedAt(rs.getTimestamp("created_at"));
        cartItem.setUpdatedAt(rs.getTimestamp("updated_at"));
        
        // Load the product details
        Product product = productDAO.findById(cartItem.getProductId());
        cartItem.setProduct(product);
        
        return cartItem;
    }

    private List<CartItem> findCartItemsByCartId(int cartId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CartItem> cartItems = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                CartItem cartItem = mapResultSetToCartItem(rs);
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return cartItems;
    }

    private CartItem findCartItemByProductId(int cartId, int productId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CartItem cartItem = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cartItem = mapResultSetToCartItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        
        return cartItem;
    }
}

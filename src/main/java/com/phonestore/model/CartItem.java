package com.phonestore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * CartItem model class
 */
public class CartItem {
    private int id;
    private int cartId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private Product product; // For display purposes
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public CartItem() {
    }

    // Constructor with essential fields
    public CartItem(int cartId, int productId, int quantity, BigDecimal price) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructor with all fields
    public CartItem(int id, int cartId, int productId, int quantity, BigDecimal price, 
                   Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    public BigDecimal getSubtotal() {
        return price.multiply(new BigDecimal(quantity));
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}

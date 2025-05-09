package com.phonestore.controller;

import com.phonestore.model.Cart;
import com.phonestore.model.CartItem;
import com.phonestore.model.Product;
import com.phonestore.model.User;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Servlet for handling shopping cart
 */
@WebServlet("/cart/*")
public class CartServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        if (!isLoggedIn(request, response)) {
            return;
        }
        
        // Get user from session
        User user = SessionUtil.getUser(request);
        
        // Get user's cart
        Cart cart = cartDAO.findByUserId(user.getId());
        
        // Set attributes for the view
        request.setAttribute("cart", cart);
        
        // Forward to cart page
        forwardToPage(request, response, "/WEB-INF/views/cart/cart.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        if (!isLoggedIn(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Add to cart
            addToCart(request, response);
        } else if (pathInfo.equals("/update")) {
            // Update cart
            updateCart(request, response);
        } else if (pathInfo.equals("/remove")) {
            // Remove from cart
            removeFromCart(request, response);
        } else if (pathInfo.equals("/clear")) {
            // Clear cart
            clearCart(request, response);
        } else {
            // Invalid action, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        }
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get user from session
        User user = SessionUtil.getUser(request);
        
        // Get parameters
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");
        
        if (productIdParam == null || productIdParam.isEmpty() ||
            quantityParam == null || quantityParam.isEmpty()) {
            // Invalid parameters, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
            return;
        }
        
        try {
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);
            
            if (quantity <= 0) {
                // Invalid quantity, redirect to cart
                redirectToUrl(response, request.getContextPath() + "/cart");
                return;
            }
            
            // Get product
            Product product = productDAO.findById(productId);
            
            if (product == null) {
                // Product not found, redirect to cart
                redirectToUrl(response, request.getContextPath() + "/cart");
                return;
            }
            
            // Check if product is in stock
            if (product.getStock() < quantity) {
                // Not enough stock, redirect to product details with error
                redirectToUrl(response, request.getContextPath() + "/products/" + productId + "?error=stock");
                return;
            }
            
            // Get user's cart
            Cart cart = cartDAO.findByUserId(user.getId());
            
            // Create cart item
            CartItem cartItem = new CartItem(cart.getId(), productId, quantity, product.getPrice());
            
            // Add to cart
            cartDAO.addCartItem(cartItem);
            
            // Redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        } catch (NumberFormatException e) {
            // Invalid parameters, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        }
    }
    
    private void updateCart(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get parameters
        String cartItemIdParam = request.getParameter("cartItemId");
        String quantityParam = request.getParameter("quantity");
        
        if (cartItemIdParam == null || cartItemIdParam.isEmpty() ||
            quantityParam == null || quantityParam.isEmpty()) {
            // Invalid parameters, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
            return;
        }
        
        try {
            int cartItemId = Integer.parseInt(cartItemIdParam);
            int quantity = Integer.parseInt(quantityParam);
            
            if (quantity <= 0) {
                // If quantity is 0 or negative, remove the item
                cartDAO.removeCartItem(cartItemId);
            } else {
                // Get user's cart
                User user = SessionUtil.getUser(request);
                Cart cart = cartDAO.findByUserId(user.getId());
                
                // Find the cart item
                CartItem cartItem = null;
                for (CartItem item : cart.getCartItems()) {
                    if (item.getId() == cartItemId) {
                        cartItem = item;
                        break;
                    }
                }
                
                if (cartItem != null) {
                    // Check if product is in stock
                    Product product = productDAO.findById(cartItem.getProductId());
                    
                    if (product == null || product.getStock() < quantity) {
                        // Not enough stock, redirect to cart with error
                        redirectToUrl(response, request.getContextPath() + "/cart?error=stock");
                        return;
                    }
                    
                    // Update quantity
                    cartItem.setQuantity(quantity);
                    
                    // Update cart item
                    cartDAO.updateCartItem(cartItem);
                }
            }
            
            // Redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        } catch (NumberFormatException e) {
            // Invalid parameters, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        }
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get parameters
        String cartItemIdParam = request.getParameter("cartItemId");
        
        if (cartItemIdParam == null || cartItemIdParam.isEmpty()) {
            // Invalid parameters, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
            return;
        }
        
        try {
            int cartItemId = Integer.parseInt(cartItemIdParam);
            
            // Remove from cart
            cartDAO.removeCartItem(cartItemId);
            
            // Redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        } catch (NumberFormatException e) {
            // Invalid parameters, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
        }
    }
    
    private void clearCart(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get user's cart
        User user = SessionUtil.getUser(request);
        Cart cart = cartDAO.findByUserId(user.getId());
        
        // Clear cart
        cartDAO.clearCart(cart.getId());
        
        // Redirect to cart
        redirectToUrl(response, request.getContextPath() + "/cart");
    }
}

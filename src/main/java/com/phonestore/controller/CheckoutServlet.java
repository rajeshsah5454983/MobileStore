package com.phonestore.controller;

import com.phonestore.model.Cart;
import com.phonestore.model.Order;
import com.phonestore.model.User;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling checkout
 */
@WebServlet("/checkout")
public class CheckoutServlet extends BaseServlet {
    
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
        
        // Check if cart is empty
        if (cart.getCartItems().isEmpty()) {
            // Cart is empty, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
            return;
        }
        
        // Set attributes for the view
        request.setAttribute("cart", cart);
        request.setAttribute("user", user);
        
        // Forward to checkout page
        forwardToPage(request, response, "/WEB-INF/views/order/checkout.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        if (!isLoggedIn(request, response)) {
            return;
        }
        
        // Get user from session
        User user = SessionUtil.getUser(request);
        
        // Get user's cart
        Cart cart = cartDAO.findByUserId(user.getId());
        
        // Check if cart is empty
        if (cart.getCartItems().isEmpty()) {
            // Cart is empty, redirect to cart
            redirectToUrl(response, request.getContextPath() + "/cart");
            return;
        }
        
        // Get parameters
        String shippingAddress = request.getParameter("shippingAddress");
        String paymentMethod = request.getParameter("paymentMethod");
        
        if (shippingAddress == null || shippingAddress.trim().isEmpty() ||
            paymentMethod == null || paymentMethod.trim().isEmpty()) {
            // Invalid parameters, redirect to checkout
            redirectToUrl(response, request.getContextPath() + "/checkout?error=invalid");
            return;
        }
        
        // Create order
        Order order = new Order(user.getId(), cart.getTotalAmount(), shippingAddress, paymentMethod);
        
        // Create order from cart
        order = orderDAO.createFromCart(order, cart.getId());
        
        // Redirect to order confirmation
        redirectToUrl(response, request.getContextPath() + "/orders/" + order.getId());
    }
}

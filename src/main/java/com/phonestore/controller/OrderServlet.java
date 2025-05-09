package com.phonestore.controller;

import com.phonestore.model.Order;
import com.phonestore.model.User;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling orders
 */
@WebServlet("/orders/*")
public class OrderServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        if (!isLoggedIn(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Show order history
            showOrderHistory(request, response);
        } else {
            try {
                // Extract order ID from path
                int orderId = Integer.parseInt(pathInfo.substring(1));
                
                // Show order details
                showOrderDetails(request, response, orderId);
            } catch (NumberFormatException e) {
                // Invalid order ID, redirect to order history
                redirectToUrl(response, request.getContextPath() + "/orders");
            }
        }
    }
    
    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get user from session
        User user = SessionUtil.getUser(request);
        
        // Get user's orders
        List<Order> orders = orderDAO.findByUserId(user.getId());
        
        // Set attributes for the view
        request.setAttribute("orders", orders);
        
        // Forward to order history page
        forwardToPage(request, response, "/WEB-INF/views/order/history.jsp");
    }
    
    private void showOrderDetails(HttpServletRequest request, HttpServletResponse response, int orderId) 
            throws ServletException, IOException {
        // Get user from session
        User user = SessionUtil.getUser(request);
        
        // Get order by ID
        Order order = orderDAO.findById(orderId);
        
        if (order == null || order.getUserId() != user.getId()) {
            // Order not found or doesn't belong to user, redirect to order history
            redirectToUrl(response, request.getContextPath() + "/orders");
            return;
        }
        
        // Set attributes for the view
        request.setAttribute("order", order);
        
        // Forward to order details page
        forwardToPage(request, response, "/WEB-INF/views/order/details.jsp");
    }
}

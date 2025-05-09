package com.phonestore.controller.admin;

import com.phonestore.controller.BaseServlet;
import com.phonestore.model.Order;
import com.phonestore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling admin order management
 */
@WebServlet("/admin/orders/*")
public class AdminOrderServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is admin
        if (!isAdmin(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Show all orders
            showOrderList(request, response);
        } else if (pathInfo.startsWith("/view/")) {
            try {
                // Extract order ID from path
                int orderId = Integer.parseInt(pathInfo.substring(6));
                
                // Show order details
                showOrderDetails(request, response, orderId);
            } catch (NumberFormatException e) {
                // Invalid order ID, redirect to order list
                redirectToUrl(response, request.getContextPath() + "/admin/orders");
            }
        } else if (pathInfo.startsWith("/status/")) {
            try {
                // Extract order ID from path
                int orderId = Integer.parseInt(pathInfo.substring(8));
                
                // Update order status
                updateOrderStatus(request, response, orderId);
            } catch (NumberFormatException e) {
                // Invalid order ID, redirect to order list
                redirectToUrl(response, request.getContextPath() + "/admin/orders");
            }
        } else {
            // Invalid path, redirect to order list
            redirectToUrl(response, request.getContextPath() + "/admin/orders");
        }
    }
    
    private void showOrderList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get all orders
        List<Order> orders = orderDAO.findAll();
        
        // Set attributes for the view
        request.setAttribute("orders", orders);
        
        // Forward to admin order list page
        forwardToPage(request, response, "/WEB-INF/views/admin/order/list.jsp");
    }
    
    private void showOrderDetails(HttpServletRequest request, HttpServletResponse response, int orderId) 
            throws ServletException, IOException {
        // Get order by ID
        Order order = orderDAO.findById(orderId);
        
        if (order == null) {
            // Order not found, redirect to order list
            redirectToUrl(response, request.getContextPath() + "/admin/orders");
            return;
        }
        
        // Get user who placed the order
        User user = userDAO.findById(order.getUserId());
        
        // Set attributes for the view
        request.setAttribute("order", order);
        request.setAttribute("user", user);
        
        // Forward to admin order details page
        forwardToPage(request, response, "/WEB-INF/views/admin/order/view.jsp");
    }
    
    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response, int orderId) 
            throws ServletException, IOException {
        // Get order by ID
        Order order = orderDAO.findById(orderId);
        
        if (order == null) {
            // Order not found, redirect to order list
            redirectToUrl(response, request.getContextPath() + "/admin/orders");
            return;
        }
        
        // Get status from request parameter
        String status = request.getParameter("status");
        
        if (status != null && !status.trim().isEmpty()) {
            // Update order status
            orderDAO.updateStatus(orderId, status);
        }
        
        // Redirect to order details
        redirectToUrl(response, request.getContextPath() + "/admin/orders/view/" + orderId);
    }
}

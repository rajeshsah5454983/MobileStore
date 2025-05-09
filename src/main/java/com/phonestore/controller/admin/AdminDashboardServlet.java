package com.phonestore.controller.admin;

import com.phonestore.controller.BaseServlet;
import com.phonestore.model.Order;
import com.phonestore.model.Product;
import com.phonestore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling admin dashboard
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is admin
        if (!isAdmin(request, response)) {
            return;
        }
        
        // Get recent orders
        List<Order> recentOrders = orderDAO.findAll();
        if (recentOrders.size() > 5) {
            recentOrders = recentOrders.subList(0, 5);
        }
        
        // Get low stock products
        List<Product> products = productDAO.findAll();
        products.removeIf(product -> product.getStock() > 10);
        
        // Get user count
        List<User> users = userDAO.findAll();
        int userCount = users.size();
        
        // Set attributes for the view
        request.setAttribute("recentOrders", recentOrders);
        request.setAttribute("lowStockProducts", products);
        request.setAttribute("userCount", userCount);
        
        // Forward to admin dashboard page
        forwardToPage(request, response, "/WEB-INF/views/admin/dashboard.jsp");
    }
}

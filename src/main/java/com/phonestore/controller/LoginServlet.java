package com.phonestore.controller;

import com.phonestore.model.User;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling user login
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // If user is already logged in, redirect to home page
        if (SessionUtil.isLoggedIn(request)) {
            redirectToUrl(response, request.getContextPath() + "/");
            return;
        }
        
        // Forward to login page
        forwardToPage(request, response, "/WEB-INF/views/user/login.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String error = null;
        
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            error = "Username and password are required";
        } else {
            // Authenticate user
            User user = userDAO.authenticate(username, password);
            
            if (user != null) {
                // Set user in session
                SessionUtil.setUser(request, user);
                
                // Redirect based on role
                if (user.isAdmin()) {
                    redirectToUrl(response, request.getContextPath() + "/admin/dashboard");
                } else {
                    redirectToUrl(response, request.getContextPath() + "/");
                }
                return;
            } else {
                error = "Invalid username or password";
            }
        }
        
        // If there was an error, forward back to login page with error message
        request.setAttribute("error", error);
        request.setAttribute("username", username);
        forwardToPage(request, response, "/WEB-INF/views/user/login.jsp");
    }
}

package com.phonestore.controller;

import com.phonestore.model.Category;
import com.phonestore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling the home page
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"", "/home"})
public class HomeServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get featured products
        List<Product> featuredProducts = productDAO.findFeatured(8);
        
        // Get all categories
        List<Category> categories = categoryDAO.findAll();
        
        // Set attributes for the view
        request.setAttribute("featuredProducts", featuredProducts);
        request.setAttribute("categories", categories);
        
        // Forward to home page
        forwardToPage(request, response, "/WEB-INF/views/home.jsp");
    }
}

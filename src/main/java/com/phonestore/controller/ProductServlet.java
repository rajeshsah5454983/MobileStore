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
 * Servlet for handling product listing and details
 */
@WebServlet("/products/*")
public class ProductServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Show all products
            showProductList(request, response);
        } else {
            try {
                // Extract product ID from path
                int productId = Integer.parseInt(pathInfo.substring(1));
                
                // Show product details
                showProductDetails(request, response, productId);
            } catch (NumberFormatException e) {
                // Invalid product ID, redirect to product list
                redirectToUrl(response, request.getContextPath() + "/products");
            }
        }
    }
    
    private void showProductList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get category ID from request parameter
        String categoryIdParam = request.getParameter("category");
        
        // Get search keyword from request parameter
        String keyword = request.getParameter("keyword");
        
        List<Product> products;
        
        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            try {
                int categoryId = Integer.parseInt(categoryIdParam);
                products = productDAO.findByCategoryId(categoryId);
                
                // Get category for display
                Category category = categoryDAO.findById(categoryId);
                request.setAttribute("category", category);
            } catch (NumberFormatException e) {
                products = productDAO.findAll();
            }
        } else if (keyword != null && !keyword.isEmpty()) {
            products = productDAO.search(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            products = productDAO.findAll();
        }
        
        // Get all categories for sidebar
        List<Category> categories = categoryDAO.findAll();
        
        // Set attributes for the view
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        
        // Forward to product list page
        forwardToPage(request, response, "/WEB-INF/views/product/list.jsp");
    }
    
    private void showProductDetails(HttpServletRequest request, HttpServletResponse response, int productId) 
            throws ServletException, IOException {
        // Get product by ID
        Product product = productDAO.findById(productId);
        
        if (product == null) {
            // Product not found, redirect to product list
            redirectToUrl(response, request.getContextPath() + "/products");
            return;
        }
        
        // Get all categories for sidebar
        List<Category> categories = categoryDAO.findAll();
        
        // Set attributes for the view
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);
        
        // Forward to product details page
        forwardToPage(request, response, "/WEB-INF/views/product/details.jsp");
    }
}

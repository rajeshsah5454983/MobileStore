package com.phonestore.controller.admin;

import com.phonestore.controller.BaseServlet;
import com.phonestore.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling admin category management
 */
@WebServlet("/admin/categories/*")
public class AdminCategoryServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is admin
        if (!isAdmin(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Show all categories
            showCategoryList(request, response);
        } else if (pathInfo.equals("/add")) {
            // Show add category form
            showAddCategoryForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            try {
                // Extract category ID from path
                int categoryId = Integer.parseInt(pathInfo.substring(6));
                
                // Show edit category form
                showEditCategoryForm(request, response, categoryId);
            } catch (NumberFormatException e) {
                // Invalid category ID, redirect to category list
                redirectToUrl(response, request.getContextPath() + "/admin/categories");
            }
        } else if (pathInfo.startsWith("/delete/")) {
            try {
                // Extract category ID from path
                int categoryId = Integer.parseInt(pathInfo.substring(8));
                
                // Delete category
                deleteCategory(request, response, categoryId);
            } catch (NumberFormatException e) {
                // Invalid category ID, redirect to category list
                redirectToUrl(response, request.getContextPath() + "/admin/categories");
            }
        } else {
            // Invalid path, redirect to category list
            redirectToUrl(response, request.getContextPath() + "/admin/categories");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is admin
        if (!isAdmin(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Invalid path, redirect to category list
            redirectToUrl(response, request.getContextPath() + "/admin/categories");
        } else if (pathInfo.equals("/add")) {
            // Add category
            addCategory(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            try {
                // Extract category ID from path
                int categoryId = Integer.parseInt(pathInfo.substring(6));
                
                // Update category
                updateCategory(request, response, categoryId);
            } catch (NumberFormatException e) {
                // Invalid category ID, redirect to category list
                redirectToUrl(response, request.getContextPath() + "/admin/categories");
            }
        } else {
            // Invalid path, redirect to category list
            redirectToUrl(response, request.getContextPath() + "/admin/categories");
        }
    }
    
    private void showCategoryList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get all categories
        List<Category> categories = categoryDAO.findAll();
        
        // Set attributes for the view
        request.setAttribute("categories", categories);
        
        // Forward to admin category list page
        forwardToPage(request, response, "/WEB-INF/views/admin/category/list.jsp");
    }
    
    private void showAddCategoryForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to admin add category page
        forwardToPage(request, response, "/WEB-INF/views/admin/category/add.jsp");
    }
    
    private void showEditCategoryForm(HttpServletRequest request, HttpServletResponse response, int categoryId) 
            throws ServletException, IOException {
        // Get category by ID
        Category category = categoryDAO.findById(categoryId);
        
        if (category == null) {
            // Category not found, redirect to category list
            redirectToUrl(response, request.getContextPath() + "/admin/categories");
            return;
        }
        
        // Set attributes for the view
        request.setAttribute("category", category);
        
        // Forward to admin edit category page
        forwardToPage(request, response, "/WEB-INF/views/admin/category/edit.jsp");
    }
    
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response, int categoryId) 
            throws ServletException, IOException {
        // Delete category
        categoryDAO.deleteById(categoryId);
        
        // Redirect to category list
        redirectToUrl(response, request.getContextPath() + "/admin/categories");
    }
    
    private void addCategory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        String error = null;
        
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            error = "Category name is required";
        } else if (categoryDAO.findByName(name) != null) {
            error = "Category name already exists";
        } else {
            // Create new category
            Category category = new Category(name, description);
            
            // Save category to database
            category = categoryDAO.save(category);
            
            if (category.getId() > 0) {
                // Redirect to category list
                redirectToUrl(response, request.getContextPath() + "/admin/categories");
                return;
            } else {
                error = "Failed to add category";
            }
        }
        
        // If there was an error, forward back to add category form with error message
        request.setAttribute("error", error);
        request.setAttribute("name", name);
        request.setAttribute("description", description);
        
        forwardToPage(request, response, "/WEB-INF/views/admin/category/add.jsp");
    }
    
    private void updateCategory(HttpServletRequest request, HttpServletResponse response, int categoryId) 
            throws ServletException, IOException {
        // Get category by ID
        Category category = categoryDAO.findById(categoryId);
        
        if (category == null) {
            // Category not found, redirect to category list
            redirectToUrl(response, request.getContextPath() + "/admin/categories");
            return;
        }
        
        // Get parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        String error = null;
        
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            error = "Category name is required";
        } else {
            // Check if name is already used by another category
            Category existingCategory = categoryDAO.findByName(name);
            if (existingCategory != null && existingCategory.getId() != categoryId) {
                error = "Category name already exists";
            } else {
                // Update category
                category.setName(name);
                category.setDescription(description);
                
                // Save category to database
                category = categoryDAO.save(category);
                
                // Redirect to category list
                redirectToUrl(response, request.getContextPath() + "/admin/categories");
                return;
            }
        }
        
        // If there was an error, forward back to edit category form with error message
        request.setAttribute("error", error);
        request.setAttribute("category", category);
        
        forwardToPage(request, response, "/WEB-INF/views/admin/category/edit.jsp");
    }
}

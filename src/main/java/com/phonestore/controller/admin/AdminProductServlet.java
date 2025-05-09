package com.phonestore.controller.admin;

import com.phonestore.controller.BaseServlet;
import com.phonestore.model.Category;
import com.phonestore.model.Product;
import com.phonestore.util.FileUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling admin product management
 */
@WebServlet("/admin/products/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10, // 10 MB
    location = ""
)
public class AdminProductServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is admin
        if (!isAdmin(request, response)) {
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Show all products
            showProductList(request, response);
        } else if (pathInfo.equals("/add")) {
            // Show add product form
            showAddProductForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            try {
                // Extract product ID from path
                int productId = Integer.parseInt(pathInfo.substring(6));

                // Show edit product form
                showEditProductForm(request, response, productId);
            } catch (NumberFormatException e) {
                // Invalid product ID, redirect to product list
                redirectToUrl(response, request.getContextPath() + "/admin/products");
            }
        } else if (pathInfo.startsWith("/delete/")) {
            try {
                // Extract product ID from path
                int productId = Integer.parseInt(pathInfo.substring(8));

                // Delete product
                deleteProduct(request, response, productId);
            } catch (NumberFormatException e) {
                // Invalid product ID, redirect to product list
                redirectToUrl(response, request.getContextPath() + "/admin/products");
            }
        } else {
            // Invalid path, redirect to product list
            redirectToUrl(response, request.getContextPath() + "/admin/products");
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
            // Invalid path, redirect to product list
            redirectToUrl(response, request.getContextPath() + "/admin/products");
        } else if (pathInfo.equals("/add")) {
            // Add product
            addProduct(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            try {
                // Extract product ID from path
                int productId = Integer.parseInt(pathInfo.substring(6));

                // Update product
                updateProduct(request, response, productId);
            } catch (NumberFormatException e) {
                // Invalid product ID, redirect to product list
                redirectToUrl(response, request.getContextPath() + "/admin/products");
            }
        } else {
            // Invalid path, redirect to product list
            redirectToUrl(response, request.getContextPath() + "/admin/products");
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get all products
        List<Product> products = productDAO.findAll();

        // Set attributes for the view
        request.setAttribute("products", products);

        // Forward to admin product list page
        forwardToPage(request, response, "/WEB-INF/views/admin/product/list.jsp");
    }

    private void showAddProductForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get all categories
        List<Category> categories = categoryDAO.findAll();

        // Set attributes for the view
        request.setAttribute("categories", categories);

        // Forward to admin add product page
        forwardToPage(request, response, "/WEB-INF/views/admin/product/add.jsp");
    }

    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        // Get product by ID
        Product product = productDAO.findById(productId);

        if (product == null) {
            // Product not found, redirect to product list
            redirectToUrl(response, request.getContextPath() + "/admin/products");
            return;
        }

        // Get all categories
        List<Category> categories = categoryDAO.findAll();

        // Set attributes for the view
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);

        // Forward to admin edit product page
        forwardToPage(request, response, "/WEB-INF/views/admin/product/edit.jsp");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        // Get product by ID
        Product product = productDAO.findById(productId);

        if (product != null) {
            // Delete product image if exists
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String uploadDir = getServletContext().getRealPath("/uploads/products");
                FileUploadUtil.deleteFile(product.getImage(), uploadDir);
            }

            // Delete product
            productDAO.deleteById(productId);
        }

        // Redirect to product list
        redirectToUrl(response, request.getContextPath() + "/admin/products");
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the upload directory
        String uploadDir = getServletContext().getRealPath("/uploads/products");

        // Process the multipart request
        Map<String, String> formFields = FileUploadUtil.processRequest(request, uploadDir);

        String name = formFields.get("name");
        String description = formFields.get("description");
        String priceStr = formFields.get("price");
        String stockStr = formFields.get("stock");
        String categoryIdStr = formFields.get("categoryId");
        String image = formFields.get("image");

        String error = null;

        // Validate input
        if (name == null || name.trim().isEmpty() ||
            description == null || description.trim().isEmpty() ||
            priceStr == null || priceStr.trim().isEmpty() ||
            stockStr == null || stockStr.trim().isEmpty() ||
            categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            error = "All fields are required";
        } else {
            try {
                BigDecimal price = new BigDecimal(priceStr);
                int stock = Integer.parseInt(stockStr);
                int categoryId = Integer.parseInt(categoryIdStr);

                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    error = "Price must be greater than 0";
                } else if (stock < 0) {
                    error = "Stock cannot be negative";
                } else {
                    // Create new product
                    Product product = new Product(name, description, price, stock, image, categoryId);

                    // Save product to database
                    product = productDAO.save(product);

                    if (product.getId() > 0) {
                        // Redirect to product list
                        redirectToUrl(response, request.getContextPath() + "/admin/products");
                        return;
                    } else {
                        error = "Failed to add product";
                    }
                }
            } catch (NumberFormatException e) {
                error = "Invalid price, stock, or category";
            }
        }

        // If there was an error, forward back to add product form with error message
        request.setAttribute("error", error);
        request.setAttribute("name", name);
        request.setAttribute("description", description);
        request.setAttribute("price", priceStr);
        request.setAttribute("stock", stockStr);
        request.setAttribute("categoryId", categoryIdStr);

        // Get all categories
        List<Category> categories = categoryDAO.findAll();
        request.setAttribute("categories", categories);

        forwardToPage(request, response, "/WEB-INF/views/admin/product/add.jsp");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        // Get product by ID
        Product product = productDAO.findById(productId);

        if (product == null) {
            // Product not found, redirect to product list
            redirectToUrl(response, request.getContextPath() + "/admin/products");
            return;
        }

        // Get the upload directory
        String uploadDir = getServletContext().getRealPath("/uploads/products");

        // Process the multipart request
        Map<String, String> formFields = FileUploadUtil.processRequest(request, uploadDir);

        String name = formFields.get("name");
        String description = formFields.get("description");
        String priceStr = formFields.get("price");
        String stockStr = formFields.get("stock");
        String categoryIdStr = formFields.get("categoryId");
        String image = formFields.get("image");

        String error = null;

        // Validate input
        if (name == null || name.trim().isEmpty() ||
            description == null || description.trim().isEmpty() ||
            priceStr == null || priceStr.trim().isEmpty() ||
            stockStr == null || stockStr.trim().isEmpty() ||
            categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            error = "All fields are required";
        } else {
            try {
                BigDecimal price = new BigDecimal(priceStr);
                int stock = Integer.parseInt(stockStr);
                int categoryId = Integer.parseInt(categoryIdStr);

                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    error = "Price must be greater than 0";
                } else if (stock < 0) {
                    error = "Stock cannot be negative";
                } else {
                    // Update product
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    product.setStock(stock);
                    product.setCategoryId(categoryId);

                    // Update image if provided
                    if (image != null && !image.isEmpty()) {
                        // Delete old image if exists
                        if (product.getImage() != null && !product.getImage().isEmpty()) {
                            FileUploadUtil.deleteFile(product.getImage(), uploadDir);
                        }
                        product.setImage(image);
                    }

                    // Save product to database
                    product = productDAO.save(product);

                    // Redirect to product list
                    redirectToUrl(response, request.getContextPath() + "/admin/products");
                    return;
                }
            } catch (NumberFormatException e) {
                error = "Invalid price, stock, or category";
            }
        }

        // If there was an error, forward back to edit product form with error message
        request.setAttribute("error", error);
        request.setAttribute("product", product);

        // Get all categories
        List<Category> categories = categoryDAO.findAll();
        request.setAttribute("categories", categories);

        forwardToPage(request, response, "/WEB-INF/views/admin/product/edit.jsp");
    }
}

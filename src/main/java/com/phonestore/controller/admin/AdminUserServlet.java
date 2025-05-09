package com.phonestore.controller.admin;

import com.phonestore.controller.BaseServlet;
import com.phonestore.model.User;
import com.phonestore.util.FileUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling admin user management
 */
@WebServlet("/admin/users/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10, // 10 MB
    location = ""
)
public class AdminUserServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is admin
        if (!isAdmin(request, response)) {
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Show all users
            showUserList(request, response);
        } else if (pathInfo.equals("/add")) {
            // Show add user form
            showAddUserForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            try {
                // Extract user ID from path
                int userId = Integer.parseInt(pathInfo.substring(6));

                // Show edit user form
                showEditUserForm(request, response, userId);
            } catch (NumberFormatException e) {
                // Invalid user ID, redirect to user list
                redirectToUrl(response, request.getContextPath() + "/admin/users");
            }
        } else if (pathInfo.startsWith("/delete/")) {
            try {
                // Extract user ID from path
                int userId = Integer.parseInt(pathInfo.substring(8));

                // Delete user
                deleteUser(request, response, userId);
            } catch (NumberFormatException e) {
                // Invalid user ID, redirect to user list
                redirectToUrl(response, request.getContextPath() + "/admin/users");
            }
        } else {
            // Invalid path, redirect to user list
            redirectToUrl(response, request.getContextPath() + "/admin/users");
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
            // Invalid path, redirect to user list
            redirectToUrl(response, request.getContextPath() + "/admin/users");
        } else if (pathInfo.equals("/add")) {
            // Add user
            addUser(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            try {
                // Extract user ID from path
                int userId = Integer.parseInt(pathInfo.substring(6));

                // Update user
                updateUser(request, response, userId);
            } catch (NumberFormatException e) {
                // Invalid user ID, redirect to user list
                redirectToUrl(response, request.getContextPath() + "/admin/users");
            }
        } else {
            // Invalid path, redirect to user list
            redirectToUrl(response, request.getContextPath() + "/admin/users");
        }
    }

    private void showUserList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get all users
        List<User> users = userDAO.findAll();

        // Set attributes for the view
        request.setAttribute("users", users);

        // Forward to admin user list page
        forwardToPage(request, response, "/WEB-INF/views/admin/user/list.jsp");
    }

    private void showAddUserForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to admin add user page
        forwardToPage(request, response, "/WEB-INF/views/admin/user/add.jsp");
    }

    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        // Get user by ID
        User user = userDAO.findById(userId);

        if (user == null) {
            // User not found, redirect to user list
            redirectToUrl(response, request.getContextPath() + "/admin/users");
            return;
        }

        // Set attributes for the view
        request.setAttribute("user", user);

        // Forward to admin edit user page
        forwardToPage(request, response, "/WEB-INF/views/admin/user/edit.jsp");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        // Get user by ID
        User user = userDAO.findById(userId);

        if (user != null) {
            // Delete user profile image if exists
            if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                String uploadDir = getServletContext().getRealPath("/uploads/profiles");
                FileUploadUtil.deleteFile(user.getProfileImage(), uploadDir);
            }

            // Delete user
            userDAO.deleteById(userId);
        }

        // Redirect to user list
        redirectToUrl(response, request.getContextPath() + "/admin/users");
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the upload directory
        String uploadDir = getServletContext().getRealPath("/uploads/profiles");

        // Process the multipart request
        Map<String, String> formFields = FileUploadUtil.processRequest(request, uploadDir);

        String username = formFields.get("username");
        String password = formFields.get("password");
        String confirmPassword = formFields.get("confirmPassword");
        String email = formFields.get("email");
        String fullName = formFields.get("fullName");
        String role = formFields.get("role");
        String profileImage = formFields.get("profileImage");

        String error = null;

        // Validate input
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            role == null || role.trim().isEmpty()) {
            error = "All fields are required";
        } else if (!password.equals(confirmPassword)) {
            error = "Passwords do not match";
        } else if (userDAO.findByUsername(username) != null) {
            error = "Username already exists";
        } else if (userDAO.findByEmail(email) != null) {
            error = "Email already exists";
        } else {
            // Create new user
            User user = new User(username, password, email, fullName);
            user.setRole(role);
            user.setProfileImage(profileImage);

            // Save user to database
            user = userDAO.save(user);

            if (user.getId() > 0) {
                // Redirect to user list
                redirectToUrl(response, request.getContextPath() + "/admin/users");
                return;
            } else {
                error = "Failed to add user";
            }
        }

        // If there was an error, forward back to add user form with error message
        request.setAttribute("error", error);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        request.setAttribute("role", role);

        forwardToPage(request, response, "/WEB-INF/views/admin/user/add.jsp");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        // Get user by ID
        User user = userDAO.findById(userId);

        if (user == null) {
            // User not found, redirect to user list
            redirectToUrl(response, request.getContextPath() + "/admin/users");
            return;
        }

        // Get the upload directory
        String uploadDir = getServletContext().getRealPath("/uploads/profiles");

        // Process the multipart request
        Map<String, String> formFields = FileUploadUtil.processRequest(request, uploadDir);

        String username = formFields.get("username");
        String password = formFields.get("password");
        String confirmPassword = formFields.get("confirmPassword");
        String email = formFields.get("email");
        String fullName = formFields.get("fullName");
        String role = formFields.get("role");
        String profileImage = formFields.get("profileImage");

        String error = null;

        // Validate input
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            role == null || role.trim().isEmpty()) {
            error = "All fields are required";
        } else {
            // Check if username is already used by another user
            User existingUser = userDAO.findByUsername(username);
            if (existingUser != null && existingUser.getId() != userId) {
                error = "Username already exists";
            } else {
                // Check if email is already used by another user
                existingUser = userDAO.findByEmail(email);
                if (existingUser != null && existingUser.getId() != userId) {
                    error = "Email already exists";
                } else {
                    // Update user
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setFullName(fullName);
                    user.setRole(role);

                    // Update profile image if provided
                    if (profileImage != null && !profileImage.isEmpty()) {
                        // Delete old profile image if exists
                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                            FileUploadUtil.deleteFile(user.getProfileImage(), uploadDir);
                        }
                        user.setProfileImage(profileImage);
                    }

                    // Update password if provided
                    if (password != null && !password.isEmpty() &&
                        confirmPassword != null && !confirmPassword.isEmpty()) {

                        if (!password.equals(confirmPassword)) {
                            error = "Passwords do not match";
                        } else {
                            // Update password
                            userDAO.updatePassword(userId, password);
                        }
                    }

                    if (error == null) {
                        // Save user to database
                        user = userDAO.save(user);

                        // Redirect to user list
                        redirectToUrl(response, request.getContextPath() + "/admin/users");
                        return;
                    }
                }
            }
        }

        // If there was an error, forward back to edit user form with error message
        request.setAttribute("error", error);
        request.setAttribute("user", user);

        forwardToPage(request, response, "/WEB-INF/views/admin/user/edit.jsp");
    }
}

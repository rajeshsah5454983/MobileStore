package com.phonestore.controller;

import com.phonestore.model.User;
import com.phonestore.util.FileUploadUtil;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet for handling user profile
 */
@WebServlet("/profile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10, // 10 MB
    location = ""
)
public class ProfileServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        if (!isLoggedIn(request, response)) {
            return;
        }

        // Get user from session
        User user = SessionUtil.getUser(request);

        // Forward to profile page
        request.setAttribute("user", user);
        forwardToPage(request, response, "/WEB-INF/views/user/profile.jsp");
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

        // Get the upload directory
        String uploadDir = getServletContext().getRealPath("/uploads/profiles");

        // Process the multipart request
        Map<String, String> formFields = FileUploadUtil.processRequest(request, uploadDir);

        String email = formFields.get("email");
        String fullName = formFields.get("fullName");
        String profileImage = formFields.get("profileImage");
        String currentPassword = formFields.get("currentPassword");
        String newPassword = formFields.get("newPassword");
        String confirmPassword = formFields.get("confirmPassword");

        String error = null;
        String success = null;

        // Validate input
        if (email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            error = "Email and full name are required";
        } else {
            // Check if email is already used by another user
            User existingUser = userDAO.findByEmail(email);
            if (existingUser != null && existingUser.getId() != user.getId()) {
                error = "Email already exists";
            } else {
                // Update user profile
                user.setEmail(email);
                user.setFullName(fullName);

                // Update profile image if provided
                if (profileImage != null && !profileImage.isEmpty()) {
                    // Delete old profile image if exists
                    if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                        FileUploadUtil.deleteFile(user.getProfileImage(), uploadDir);
                    }
                    user.setProfileImage(profileImage);
                }

                // Update password if provided
                if (currentPassword != null && !currentPassword.isEmpty() &&
                    newPassword != null && !newPassword.isEmpty() &&
                    confirmPassword != null && !confirmPassword.isEmpty()) {

                    if (!newPassword.equals(confirmPassword)) {
                        error = "New passwords do not match";
                    } else {
                        // Authenticate with current password
                        User authenticatedUser = userDAO.authenticate(user.getUsername(), currentPassword);

                        if (authenticatedUser != null) {
                            // Update password
                            userDAO.updatePassword(user.getId(), newPassword);
                            success = "Profile and password updated successfully";
                        } else {
                            error = "Current password is incorrect";
                        }
                    }
                } else {
                    // Update profile without password
                    userDAO.updateProfile(user);
                    success = "Profile updated successfully";
                }

                // Update user in session
                SessionUtil.setUser(request, user);
            }
        }

        // Set attributes for the view
        request.setAttribute("user", user);
        request.setAttribute("error", error);
        request.setAttribute("success", success);

        // Forward to profile page
        forwardToPage(request, response, "/WEB-INF/views/user/profile.jsp");
    }
}

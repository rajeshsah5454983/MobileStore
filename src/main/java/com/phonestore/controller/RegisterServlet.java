package com.phonestore.controller;

import com.phonestore.model.User;
import com.phonestore.util.FileUploadUtil;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet for handling user registration
 */
@WebServlet("/register")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10, // 10 MB
    location = ""
)
public class RegisterServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If user is already logged in, redirect to home page
        if (SessionUtil.isLoggedIn(request)) {
            redirectToUrl(response, request.getContextPath() + "/");
            return;
        }

        // Forward to registration page
        forwardToPage(request, response, "/WEB-INF/views/user/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        String profileImage = formFields.get("profileImage");

        String error = null;

        // Validate input
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
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
            user.setProfileImage(profileImage);

            // Save user to database
            user = userDAO.save(user);

            if (user.getId() > 0) {
                // Set user in session
                SessionUtil.setUser(request, user);

                // Redirect to home page
                redirectToUrl(response, request.getContextPath() + "/");
                return;
            } else {
                error = "Failed to register user";
            }
        }

        // If there was an error, forward back to registration page with error message
        request.setAttribute("error", error);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        forwardToPage(request, response, "/WEB-INF/views/user/register.jsp");
    }
}

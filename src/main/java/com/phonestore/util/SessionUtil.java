package com.phonestore.util;

import com.phonestore.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for session management
 */
public class SessionUtil {
    
    private static final String USER_SESSION_KEY = "user";
    private static final String ADMIN_SESSION_KEY = "admin";
    private static final int SESSION_TIMEOUT = 30 * 60; // 30 minutes
    
    /**
     * Set the user in the session
     * @param request The HTTP request
     * @param user The user to set
     */
    public static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(USER_SESSION_KEY, user);
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
        
        if (user.isAdmin()) {
            session.setAttribute(ADMIN_SESSION_KEY, true);
        }
    }
    
    /**
     * Get the user from the session
     * @param request The HTTP request
     * @return The user or null if not logged in
     */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(USER_SESSION_KEY);
        }
        return null;
    }
    
    /**
     * Check if the user is logged in
     * @param request The HTTP request
     * @return true if logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null;
    }
    
    /**
     * Check if the user is an admin
     * @param request The HTTP request
     * @return true if admin, false otherwise
     */
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Boolean isAdmin = (Boolean) session.getAttribute(ADMIN_SESSION_KEY);
            return isAdmin != null && isAdmin;
        }
        return false;
    }
    
    /**
     * Invalidate the session
     * @param request The HTTP request
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}

package com.phonestore.controller;

import com.phonestore.dao.*;
import com.phonestore.dao.impl.*;
import com.phonestore.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base servlet that all servlets will extend
 */
public abstract class BaseServlet extends HttpServlet {
    
    protected UserDAO userDAO;
    protected ProductDAO productDAO;
    protected CategoryDAO categoryDAO;
    protected CartDAO cartDAO;
    protected OrderDAO orderDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAOImpl();
        productDAO = new ProductDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        cartDAO = new CartDAOImpl();
        orderDAO = new OrderDAOImpl();
    }
    
    /**
     * Forward the request to a JSP page
     * @param request The HTTP request
     * @param response The HTTP response
     * @param page The JSP page to forward to
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    protected void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) 
            throws ServletException, IOException {
        request.getRequestDispatcher(page).forward(request, response);
    }
    
    /**
     * Redirect to a URL
     * @param response The HTTP response
     * @param url The URL to redirect to
     * @throws IOException If an I/O error occurs
     */
    protected void redirectToUrl(HttpServletResponse response, String url) throws IOException {
        response.sendRedirect(url);
    }
    
    /**
     * Check if the user is logged in
     * @param request The HTTP request
     * @param response The HTTP response
     * @return true if logged in, false otherwise
     * @throws IOException If an I/O error occurs
     */
    protected boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!SessionUtil.isLoggedIn(request)) {
            redirectToUrl(response, request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
    
    /**
     * Check if the user is an admin
     * @param request The HTTP request
     * @param response The HTTP response
     * @return true if admin, false otherwise
     * @throws IOException If an I/O error occurs
     */
    protected boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!SessionUtil.isAdmin(request)) {
            redirectToUrl(response, request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}

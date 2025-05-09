package com.phonestore.filter;

import com.phonestore.util.SessionUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for handling authentication and authorization
 */
@WebFilter(urlPatterns = {"/admin/*", "/profile", "/cart/*", "/checkout", "/orders/*"})
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Check if user is logged in
        if (!SessionUtil.isLoggedIn(httpRequest)) {
            // User is not logged in, redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Check if user is accessing admin pages
        if (httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/admin")) {
            // Check if user is admin
            if (!SessionUtil.isAdmin(httpRequest)) {
                // User is not admin, redirect to home page
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
                return;
            }
        }
        
        // User is authenticated and authorized, continue with the request
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }
}

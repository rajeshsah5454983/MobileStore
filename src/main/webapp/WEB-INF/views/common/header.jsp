<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Phone Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
        }
    </style>
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/">
                    <i class="fas fa-mobile-alt"></i>
                    <h1>Phone Store</h1>
                </a>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/" class="${pageContext.request.servletPath == '/WEB-INF/views/home.jsp' ? 'active' : ''}">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/products" class="${pageContext.request.servletPath.contains('/product/') ? 'active' : ''}">Products</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <li><a href="${pageContext.request.contextPath}/login" class="${pageContext.request.servletPath == '/WEB-INF/views/user/login.jsp' ? 'active' : ''}">Login</a></li>
                            <li><a href="${pageContext.request.contextPath}/register" class="${pageContext.request.servletPath == '/WEB-INF/views/user/register.jsp' ? 'active' : ''}">Register</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/cart" class="${pageContext.request.servletPath.contains('/cart/') ? 'active' : ''}"><i class="fas fa-shopping-cart"></i> Cart</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders" class="${pageContext.request.servletPath.contains('/order/') ? 'active' : ''}"><i class="fas fa-box"></i> Orders</a></li>
                            <li><a href="${pageContext.request.contextPath}/profile" class="${pageContext.request.servletPath == '/WEB-INF/views/user/profile.jsp' ? 'active' : ''}"><i class="fas fa-user"></i> Profile</a></li>
                            <c:if test="${sessionScope.admin}">
                                <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="${pageContext.request.servletPath.contains('/admin/') ? 'active' : ''}"><i class="fas fa-cog"></i> Admin</a></li>
                            </c:if>
                            <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <button class="mobile-menu-toggle">
                    <i class="fas fa-bars"></i>
                </button>
            </nav>
        </div>
    </header>
    <main class="container">

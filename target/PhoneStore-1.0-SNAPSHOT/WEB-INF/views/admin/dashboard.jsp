<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin Dashboard" />
</jsp:include>

<div class="admin-dashboard">
    <div class="admin-sidebar">
        <h3>Admin Menu</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
        </ul>
    </div>
    
    <div class="admin-content">
        <h2>Admin Dashboard</h2>
        
        <div class="admin-stats">
            <div class="admin-stat-card">
                <h3>Total Users</h3>
                <div class="stat">${userCount}</div>
            </div>
            
            <div class="admin-stat-card">
                <h3>Total Products</h3>
                <div class="stat">${lowStockProducts.size()}</div>
            </div>
            
            <div class="admin-stat-card">
                <h3>Total Orders</h3>
                <div class="stat">${recentOrders.size()}</div>
            </div>
        </div>
        
        <div class="admin-recent-orders">
            <h3>Recent Orders</h3>
            
            <c:choose>
                <c:when test="${empty recentOrders}">
                    <p>No orders yet.</p>
                </c:when>
                <c:otherwise>
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>User</th>
                                <th>Date</th>
                                <th>Total</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${recentOrders}">
                                <tr>
                                    <td>${order.id}</td>
                                    <td>${order.userId}</td>
                                    <td><fmt:formatDate value="${order.createdAt}" pattern="MMM dd, yyyy" /></td>
                                    <td>Rs.<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" /></td>
                                    <td><span class="order-status order-status-${order.status.toLowerCase()}">${order.status}</span></td>
                                    <td>
                                        <div class="admin-actions">
                                            <a href="${pageContext.request.contextPath}/admin/orders/view/${order.id}" class="btn view">View</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div class="admin-low-stock">
            <h3>Low Stock Products</h3>
            
            <c:choose>
                <c:when test="${empty lowStockProducts}">
                    <p>No low stock products.</p>
                </c:when>
                <c:otherwise>
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Stock</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${lowStockProducts}">
                                <tr>
                                    <td>${product.id}</td>
                                    <td>${product.name}</td>
                                    <td>Rs.${product.price}</td>
                                    <td><span class="low-stock">${product.stock}</span></td>
                                    <td>
                                        <div class="admin-actions">
                                            <a href="${pageContext.request.contextPath}/admin/products/edit/${product.id}" class="btn edit">Edit</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />

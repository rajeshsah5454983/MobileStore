<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Orders" />
</jsp:include>

<div class="admin-dashboard">
    <div class="admin-sidebar">
        <h3>Admin Menu</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders" class="active">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
        </ul>
    </div>
    
    <div class="admin-content">
        <div class="admin-content-header">
            <h2>Orders</h2>
        </div>
        
        <c:choose>
            <c:when test="${empty orders}">
                <p>No orders found.</p>
            </c:when>
            <c:otherwise>
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>User ID</th>
                            <th>Date</th>
                            <th>Total</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.userId}</td>
                                <td><fmt:formatDate value="${order.createdAt}" pattern="MMM dd, yyyy HH:mm" /></td>
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
</div>

<jsp:include page="../../common/footer.jsp" />

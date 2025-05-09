<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Users" />
</jsp:include>

<div class="admin-dashboard">
    <div class="admin-sidebar">
        <h3>Admin Menu</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users" class="active">Users</a></li>
        </ul>
    </div>
    
    <div class="admin-content">
        <div class="admin-content-header">
            <h2>Users</h2>
            <a href="${pageContext.request.contextPath}/admin/users/add" class="btn">Add New User</a>
        </div>
        
        <c:choose>
            <c:when test="${empty users}">
                <p>No users found.</p>
            </c:when>
            <c:otherwise>
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>${user.fullName}</td>
                                <td>${user.role}</td>
                                <td><fmt:formatDate value="${user.createdAt}" pattern="MMM dd, yyyy" /></td>
                                <td>
                                    <div class="admin-actions">
                                        <a href="${pageContext.request.contextPath}/admin/users/edit/${user.id}" class="btn edit">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/users/delete/${user.id}" class="btn delete" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
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

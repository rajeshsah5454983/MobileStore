<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Categories" />
</jsp:include>

<div class="admin-dashboard">
    <div class="admin-sidebar">
        <h3>Admin Menu</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories" class="active">Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
        </ul>
    </div>
    
    <div class="admin-content">
        <div class="admin-content-header">
            <h2>Categories</h2>
            <a href="${pageContext.request.contextPath}/admin/categories/add" class="btn">Add New Category</a>
        </div>
        
        <c:choose>
            <c:when test="${empty categories}">
                <p>No categories found.</p>
            </c:when>
            <c:otherwise>
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.id}</td>
                                <td>${category.name}</td>
                                <td>${category.description}</td>
                                <td><fmt:formatDate value="${category.createdAt}" pattern="MMM dd, yyyy" /></td>
                                <td>
                                    <div class="admin-actions">
                                        <a href="${pageContext.request.contextPath}/admin/categories/edit/${category.id}" class="btn edit">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/categories/delete/${category.id}" class="btn delete" onclick="return confirm('Are you sure you want to delete this category?')">Delete</a>
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

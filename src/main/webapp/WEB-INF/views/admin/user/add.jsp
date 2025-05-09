<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Add User" />
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
            <h2>Add New User</h2>
            <a href="${pageContext.request.contextPath}/admin/users" class="btn">Back to Users</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <div class="admin-form">
            <form action="${pageContext.request.contextPath}/admin/users/add" method="post" enctype="multipart/form-data" class="needs-validation">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" class="form-control" value="${username}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" class="form-control" value="${email}" required>
                </div>
                
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" class="form-control" value="${fullName}" required>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="role">Role</label>
                    <select id="role" name="role" class="form-control" required>
                        <option value="USER" ${role == 'USER' ? 'selected' : ''}>User</option>
                        <option value="ADMIN" ${role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="profileImage">Profile Image</label>
                    <input type="file" id="profileImage" name="profileImage" class="form-control" accept="image/*">
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Add User</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer.jsp" />

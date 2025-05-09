<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Edit Category" />
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
            <h2>Edit Category</h2>
            <a href="${pageContext.request.contextPath}/admin/categories" class="btn">Back to Categories</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <div class="admin-form">
            <form action="${pageContext.request.contextPath}/admin/categories/edit/${category.id}" method="post" class="needs-validation">
                <div class="form-group">
                    <label for="name">Category Name</label>
                    <input type="text" id="name" name="name" class="form-control" value="${category.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" class="form-control" rows="5">${category.description}</textarea>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Update Category</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer.jsp" />

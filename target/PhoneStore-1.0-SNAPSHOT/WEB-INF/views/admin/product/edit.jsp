<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Edit Product" />
</jsp:include>

<div class="admin-dashboard">
    <div class="admin-sidebar">
        <h3>Admin Menu</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products" class="active">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
        </ul>
    </div>
    
    <div class="admin-content">
        <div class="admin-content-header">
            <h2>Edit Product</h2>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn">Back to Products</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <div class="admin-form">
            <form action="${pageContext.request.contextPath}/admin/products/edit/${product.id}" method="post" enctype="multipart/form-data" class="needs-validation">
                <div class="form-group">
                    <label for="name">Product Name</label>
                    <input type="text" id="name" name="name" class="form-control" value="${product.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" class="form-control" rows="5" required>${product.description}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="number" id="price" name="price" class="form-control" value="${product.price}" step="0.01" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="stock">Stock</label>
                    <input type="number" id="stock" name="stock" class="form-control" value="${product.stock}" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="categoryId">Category</label>
                    <select id="categoryId" name="categoryId" class="form-control" required>
                        <option value="">Select Category</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}" ${product.categoryId == category.id ? 'selected' : ''}>${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Current Image</label>
                    <div class="current-image">
                        <img src="${pageContext.request.contextPath}/uploads/products/${product.image}" alt="${product.name}">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="image">Change Image (leave empty to keep current image)</label>
                    <input type="file" id="image" name="image" class="form-control" accept="image/*">
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn">Update Product</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer.jsp" />

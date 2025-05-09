<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Products" />
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
            <h2>Products</h2>
            <a href="${pageContext.request.contextPath}/admin/products/add" class="btn">Add New Product</a>
        </div>
        
        <c:choose>
            <c:when test="${empty products}">
                <p>No products found.</p>
            </c:when>
            <c:otherwise>
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Category</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>${product.id}</td>
                                <td>
                                    <div class="admin-product-image">
                                        <img src="${pageContext.request.contextPath}/uploads/products/${product.image}" alt="${product.name}">
                                    </div>
                                </td>
                                <td>${product.name}</td>
                                <td>Rs.${product.price}</td>
                                <td>${product.stock}</td>
                                <td>${product.categoryName}</td>
                                <td>
                                    <div class="admin-actions">
                                        <a href="${pageContext.request.contextPath}/admin/products/edit/${product.id}" class="btn edit">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/products/delete/${product.id}" class="btn delete" onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
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

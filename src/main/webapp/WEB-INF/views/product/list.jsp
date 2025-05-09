<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${not empty category ? category.name : not empty keyword ? 'Search Results' : 'All Products'}" />
</jsp:include>

<div class="product-page">
    <div class="product-sidebar">
        <h3>Categories</h3>
        <ul class="category-list">
            <li><a href="${pageContext.request.contextPath}/products" class="${empty category ? 'active' : ''}">All Products</a></li>
            <c:forEach var="cat" items="${categories}">
                <li><a href="${pageContext.request.contextPath}/products?category=${cat.id}" class="${category.id == cat.id ? 'active' : ''}">${cat.name}</a></li>
            </c:forEach>
        </ul>
        
        <div class="search-box">
            <h3>Search</h3>
            <form action="${pageContext.request.contextPath}/products" method="get">
                <div class="form-group">
                    <input type="text" name="keyword" class="form-control" placeholder="Search products..." value="${keyword}">
                    <button type="submit" class="btn">Search</button>
                </div>
            </form>
        </div>
    </div>
    
    <div class="product-content">
        <h2>${not empty category ? category.name : not empty keyword ? 'Search Results for "' += keyword += '"' : 'All Products'}</h2>
        
        <c:if test="${empty products}">
            <p class="no-products">No products found.</p>
        </c:if>
        
        <div class="product-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <div class="product-image">
                        <img src="${pageContext.request.contextPath}/uploads/products/${product.image}" alt="${product.name}">
                    </div>
                    <div class="product-info">
                        <h3>${product.name}</h3>
                        <p class="product-price">Rs.${product.price}</p>
                        <a href="${pageContext.request.contextPath}/products/${product.id}" class="btn">View Details</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />

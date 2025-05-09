<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Welcome to Phone Store" />
</jsp:include>

<section class="hero">
    <div class="hero-content">
        <h2>Welcome to Phone Store</h2>
        <p>Your one-stop shop for the latest smartphones and accessories</p>
        <div class="hero-buttons">
            <a href="${pageContext.request.contextPath}/products" class="btn">Shop Now</a>
            <a href="${pageContext.request.contextPath}/products?category=1" class="btn btn-outline">Explore Phones</a>
        </div>
    </div>
</section>

<section class="featured-products">
    <h2>Featured Products</h2>
    <div class="product-grid">
        <c:forEach var="product" items="${featuredProducts}" varStatus="status">
            <div class="product-card">
                <div class="product-image">
                    <img src="${pageContext.request.contextPath}/uploads/products/${product.image}" alt="${product.name}">
                    <c:if test="${status.index < 3}">
                        <span class="product-badge">New</span>
                    </c:if>
                </div>
                <div class="product-info">
                    <h3>${product.name}</h3>
                    <p class="product-price">Rs.${product.price}</p>
                    <a href="${pageContext.request.contextPath}/products/${product.id}" class="btn">View Details</a>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<section class="categories">
    <h2>Shop by Category</h2>
    <div class="category-grid">
        <c:forEach var="category" items="${categories}">
            <div class="category-card">
                <h3>${category.name}</h3>
                <p>${category.description}</p>
                <a href="${pageContext.request.contextPath}/products?category=${category.id}" class="btn">View Products</a>
            </div>
        </c:forEach>
    </div>
</section>

<jsp:include page="common/footer.jsp" />

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${product.name}" />
</jsp:include>

<div class="product-details">
    <div class="product-details-image">
        <img src="${pageContext.request.contextPath}/uploads/products/${product.image}" alt="${product.name}">
    </div>
    
    <div class="product-details-info">
        <h2>${product.name}</h2>
        <p class="product-details-price">Rs.${product.price}</p>
        <p class="product-details-description">${product.description}</p>
        
        <p class="product-details-stock">
            <c:choose>
                <c:when test="${product.stock > 0}">
                    <span class="in-stock">In Stock (${product.stock} available)</span>
                </c:when>
                <c:otherwise>
                    <span class="out-of-stock">Out of Stock</span>
                </c:otherwise>
            </c:choose>
        </p>
        
        <c:if test="${product.stock > 0}">
            <form action="${pageContext.request.contextPath}/cart" method="post" class="product-details-form">
                <input type="hidden" name="productId" value="${product.id}">
                
                <div class="quantity-control">
                    <label for="quantity">Quantity:</label>
                    <button type="button" class="quantity-minus">-</button>
                    <input type="number" id="quantity" name="quantity" class="quantity-input" value="1" min="1" max="${product.stock}" required>
                    <button type="button" class="quantity-plus">+</button>
                </div>
                
                <button type="submit" class="btn">Add to Cart</button>
            </form>
        </c:if>
        
        <div class="product-details-meta">
            <p><strong>Category:</strong> ${product.categoryName}</p>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />

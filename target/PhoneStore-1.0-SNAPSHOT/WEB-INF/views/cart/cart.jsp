<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Shopping Cart" />
</jsp:include>

<h2>Shopping Cart</h2>

<c:choose>
    <c:when test="${empty cart.cartItems}">
        <div class="empty-cart">
            <p>Your cart is empty.</p>
            <a href="${pageContext.request.contextPath}/products" class="btn">Continue Shopping</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="cart-container">
            <div class="cart-items">
                <table class="cart-table">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Subtotal</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cart.cartItems}">
                            <tr>
                                <td>
                                    <div class="cart-product">
                                        <div class="cart-item-image">
                                            <img src="${pageContext.request.contextPath}/uploads/products/${item.product.image}" alt="${item.product.name}">
                                        </div>
                                        <div class="cart-item-details">
                                            <h4>${item.product.name}</h4>
                                        </div>
                                    </div>
                                </td>
                                <td>Rs.${item.price}</td>
                                <td>
                                    <div class="cart-item-quantity">
                                        <form action="${pageContext.request.contextPath}/cart/update" method="post">
                                            <input type="hidden" name="cartItemId" value="${item.id}">
                                            <input type="button" class="quantity-minus">-</input>
                                            <input type="number" name="quantity" class="quantity-input" value="${item.quantity}" min="1" max="${item.product.stock}" data-cart-item-id="${item.id}" required>
                                            <input type="button" class="quantity-plus">+</input>
                                            <button type="submit" class="btn btn-sm">Update</button>
                                        </form>
                                    </div>
                                </td>
                                <td>Rs.<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" /></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/remove" method="post">
                                        <input type="hidden" name="cartItemId" value="${item.id}">
                                        <button type="submit" class="btn btn-danger">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <div class="cart-summary">
                <h3>Order Summary</h3>
                
                <div class="cart-summary-item">
                    <span>Subtotal (${cart.totalItems} items):</span>
                    <span>Rs.<fmt:formatNumber value="${cart.totalAmount}" pattern="#,##0.00" /></span>
                </div>
                
                <div class="cart-summary-item">
                    <span>Shipping:</span>
                    <span>Free</span>
                </div>
                
                <div class="cart-summary-total">
                    <span>Total:</span>
                    <span>Rs.<fmt:formatNumber value="${cart.totalAmount}" pattern="#,##0.00" /></span>
                </div>
                
                <div class="cart-buttons">
                    <a href="${pageContext.request.contextPath}/products" class="btn">Continue Shopping</a>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn">Proceed to Checkout</a>
                </div>
                
                <form action="${pageContext.request.contextPath}/cart/clear" method="post" class="clear-cart-form">
                    <button type="submit" class="btn btn-link">Clear Cart</button>
                </form>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp" />

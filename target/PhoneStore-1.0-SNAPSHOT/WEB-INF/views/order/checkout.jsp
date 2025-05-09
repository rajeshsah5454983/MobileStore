<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Checkout" />
</jsp:include>

<h2>Checkout</h2>

<div class="checkout-container">
    <div class="checkout-form">
        <h3>Shipping Information</h3>
        
        <form action="${pageContext.request.contextPath}/checkout" method="post" class="needs-validation">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" class="form-control" value="${user.fullName}" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control" value="${user.email}" required>
            </div>
            
            <div class="form-group">
                <label for="shippingAddress">Shipping Address</label>
                <textarea id="shippingAddress" name="shippingAddress" class="form-control" rows="3" required></textarea>
            </div>

            <div class="form-group" style="display: none">
                <div class="payment-methods">
                    <div class="payment-method">
                        <input type="radio" id="creditCard" name="paymentMethod" value="Credit Card" checked>
                        <label for="creditCard">Credit Card</label>
                    </div>

                    <div class="payment-method">
                        <input type="radio" id="paypal" name="paymentMethod" value="PayPal">
                        <label for="paypal">PayPal</label>
                    </div>

                    <div class="payment-method">
                        <input type="radio" id="cashOnDelivery" name="paymentMethod" value="Cash on Delivery">
                        <label for="cashOnDelivery">Cash on Delivery</label>
                    </div>
                </div>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn">Place Order</button>
            </div>
        </form>
    </div>
    
    <div class="checkout-summary">
        <h3>Order Summary</h3>
        
        <div class="checkout-items">
            <c:forEach var="item" items="${cart.cartItems}">
                <div class="checkout-item">
                    <div class="checkout-item-image">
                        <img src="${pageContext.request.contextPath}/uploads/products/${item.product.image}" alt="${item.product.name}">
                    </div>
                    <div class="checkout-item-details">
                        <h4>${item.product.name}</h4>
                        <p>Quantity: ${item.quantity}</p>
                        <p>Price: Rs.${item.price}</p>
                        <p>Subtotal: Rs.<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" /></p>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="checkout-total">
            <div class="checkout-total-item">
                <span>Subtotal (${cart.totalItems} items):</span>
                <span>Rs.<fmt:formatNumber value="${cart.totalAmount}" pattern="#,##0.00" /></span>
            </div>
            
            <div class="checkout-total-item">
                <span>Shipping:</span>
                <span>Free</span>
            </div>
            
            <div class="checkout-total-final">
                <span>Total:</span>
                <span>Rs.<fmt:formatNumber value="${cart.totalAmount}" pattern="#,##0.00" /></span>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />

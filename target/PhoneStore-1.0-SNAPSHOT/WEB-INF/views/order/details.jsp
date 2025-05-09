<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Order Details" />
</jsp:include>

<h2>Order Details</h2>

<div class="order-details">
    <div class="order-details-header">
        <div class="order-details-info">
            <p><strong>Order #:</strong> ${order.id}</p>
            <p><strong>Date:</strong> <fmt:formatDate value="${order.createdAt}" pattern="MMM dd, yyyy HH:mm" /></p>
            <p><strong>Status:</strong> <span class="order-status order-status-${order.status.toLowerCase()}">${order.status}</span></p>
        </div>
        
        <div class="order-details-shipping">
            <h3>Shipping Address</h3>
            <p>${order.shippingAddress}</p>
        </div>
        
        <div class="order-details-payment">
            <h3>Payment Method</h3>
            <p>${order.paymentMethod}</p>
        </div>
    </div>
    
    <div class="order-details-items">
        <h3>Order Items</h3>
        
        <table class="order-items-table">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${order.orderItems}">
                    <tr>
                        <td>
                            <div class="order-item-product">
                                <div class="order-item-image">
                                    <img src="${pageContext.request.contextPath}/uploads/products/${item.product.image}" alt="${item.product.name}">
                                </div>
                                <div class="order-item-details">
                                    <h4>${item.product.name}</h4>
                                </div>
                            </div>
                        </td>
                        <td>Rs.${item.price}</td>
                        <td>${item.quantity}</td>
                        <td>Rs.<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" /></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="3" class="text-right"><strong>Subtotal:</strong></td>
                    <td>$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" /></td>
                </tr>
                <tr>
                    <td colspan="3" class="text-right"><strong>Shipping:</strong></td>
                    <td>Free</td>
                </tr>
                <tr>
                    <td colspan="3" class="text-right"><strong>Total:</strong></td>
                    <td>Rs.<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" /></td>
                </tr>
            </tfoot>
        </table>
    </div>
    
    <div class="order-details-actions">
        <a href="${pageContext.request.contextPath}/orders" class="btn">Back to Orders</a>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />

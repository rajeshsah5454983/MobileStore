<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../common/header.jsp">
    <jsp:param name="title" value="Admin - Order Details" />
</jsp:include>

<div class="admin-dashboard">
    <div class="admin-sidebar">
        <h3>Admin Menu</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders" class="active">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
        </ul>
    </div>
    
    <div class="admin-content">
        <div class="admin-content-header">
            <h2>Order #${order.id} Details</h2>
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn">Back to Orders</a>
        </div>
        
        <div class="order-details-admin">
            <div class="order-details-header">
                <div class="order-details-info">
                    <p><strong>Order ID:</strong> ${order.id}</p>
                    <p><strong>Date:</strong> <fmt:formatDate value="${order.createdAt}" pattern="MMM dd, yyyy HH:mm" /></p>
                    <p><strong>User:</strong> ${user.username} (ID: ${user.id})</p>
                    <p><strong>Email:</strong> ${user.email}</p>
                </div>
                
                <div class="order-details-status">
                    <p><strong>Status:</strong> <span class="order-status order-status-${order.status.toLowerCase()}">${order.status}</span></p>
                    
                    <form action="${pageContext.request.contextPath}/admin/orders/status/${order.id}" method="get" class="status-form">
                        <div class="form-group">
                            <label for="status">Update Status:</label>
                            <select id="status" name="status" class="form-control">
                                <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                <option value="PROCESSING" ${order.status == 'PROCESSING' ? 'selected' : ''}>Processing</option>
                                <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>Shipped</option>
                                <option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                                <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </div>
                    </form>
                </div>
            </div>
            
            <div class="order-details-shipping">
                <h3>Shipping Address</h3>
                <p>${order.shippingAddress}</p>
            </div>
            
            <div class="order-details-payment">
                <h3>Payment Method</h3>
                <p>${order.paymentMethod}</p>
            </div>
            
            <div class="order-details-items">
                <h3>Order Items</h3>
                
                <table class="admin-table">
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
                            <td colspan="3" class="text-right"><strong>Total:</strong></td>
                            <td>Rs.<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" /></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer.jsp" />

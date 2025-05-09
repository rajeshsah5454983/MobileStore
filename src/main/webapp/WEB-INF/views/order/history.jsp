<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Order History" />
</jsp:include>

<h2>Order History</h2>

<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-orders">
            <p>You have no orders yet.</p>
            <a href="${pageContext.request.contextPath}/products" class="btn">Start Shopping</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="order-list">
            <c:forEach var="order" items="${orders}">
                <div class="order-card">
                    <div class="order-header">
                        <div>
                            <span class="order-id">Order #${order.id}</span>
                            <span class="order-date"><fmt:formatDate value="${order.createdAt}" pattern="MMM dd, yyyy" /></span>
                        </div>
                        <div>
                            <span class="order-status order-status-${order.status.toLowerCase()}">${order.status}</span>
                        </div>
                    </div>
                    
                    <div class="order-items">
                        <c:forEach var="item" items="${order.orderItems}">
                            <div class="order-item">
                                <div class="order-item-image">
                                    <img src="${pageContext.request.contextPath}/uploads/products/${item.product.image}" alt="${item.product.name}">
                                </div>
                                <div class="order-item-details">
                                    <p class="order-item-name">${item.product.name}</p>
                                    <p class="order-item-price">Rs.${item.price} x ${item.quantity}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="order-footer">
                        <div class="order-total">
                            Total: $<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                        </div>
                        <div class="order-actions">
                            <a href="${pageContext.request.contextPath}/orders/${order.id}" class="btn">View Details</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp" />

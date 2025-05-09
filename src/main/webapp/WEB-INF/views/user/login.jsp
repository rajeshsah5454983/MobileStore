<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>

<div class="form-container">
    <h2>Login</h2>
    
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/login" method="post" class="needs-validation">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" value="${username}" required>
        </div>
        
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        
        <div class="form-group">
            <button type="submit" class="btn">Login</button>
        </div>
        
        <div class="form-group">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
        </div>
    </form>
</div>

<jsp:include page="../common/footer.jsp" />

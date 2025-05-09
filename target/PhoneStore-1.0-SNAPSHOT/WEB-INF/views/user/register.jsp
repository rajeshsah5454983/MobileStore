<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<div class="form-container">
    <h2>Register</h2>
    
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/register" method="post" class="needs-validation" enctype="multipart/form-data">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" value="${username}" required>
        </div>
        
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" class="form-control" value="${email}" required>
        </div>
        
        <div class="form-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" class="form-control" value="${fullName}" required>
        </div>
        
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        
        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
        </div>
        
        <div class="form-group">
            <label for="profileImage">Profile Image</label>
            <input type="file" id="profileImage" name="profileImage" class="form-control" accept="image/*">
        </div>
        
        <div class="form-group">
            <button type="submit" class="btn">Register</button>
        </div>
        
        <div class="form-group">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
        </div>
    </form>
</div>

<jsp:include page="../common/footer.jsp" />

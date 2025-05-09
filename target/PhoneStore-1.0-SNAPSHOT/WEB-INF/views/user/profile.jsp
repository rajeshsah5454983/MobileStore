<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="My Profile" />
</jsp:include>

<div class="form-container">
    <h2>My Profile</h2>
    
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    
    <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
    </c:if>
    
    <div class="profile-image">
        <c:choose>
            <c:when test="${not empty user.profileImage}">
                <img src="${pageContext.request.contextPath}/uploads/profiles/${user.profileImage}" alt="${user.username}">
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/assets/images/default-profile.png" alt="${user.username}">
            </c:otherwise>
        </c:choose>
    </div>
    
    <form action="${pageContext.request.contextPath}/profile" method="post" class="needs-validation" enctype="multipart/form-data">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" class="form-control" value="${user.username}" readonly>
        </div>
        
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" class="form-control" value="${user.email}" required>
        </div>
        
        <div class="form-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" class="form-control" value="${user.fullName}" required>
        </div>
        
        <div class="form-group">
            <label for="profileImage">Profile Image</label>
            <input type="file" id="profileImage" name="profileImage" class="form-control" accept="image/*">
        </div>
        
        <h3>Change Password</h3>
        
        <div class="form-group">
            <label for="currentPassword">Current Password</label>
            <input type="password" id="currentPassword" name="currentPassword" class="form-control">
        </div>
        
        <div class="form-group">
            <label for="newPassword">New Password</label>
            <input type="password" id="newPassword" name="newPassword" class="form-control">
        </div>
        
        <div class="form-group">
            <label for="confirmPassword">Confirm New Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control">
        </div>
        
        <div class="form-group">
            <button type="submit" class="btn">Update Profile</button>
        </div>
    </form>
</div>

<jsp:include page="../common/footer.jsp" />

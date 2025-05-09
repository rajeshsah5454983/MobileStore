<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Page Not Found" />
</jsp:include>

<div class="error-container">
    <img src="${pageContext.request.contextPath}/assets/images/404.png" alt="404 Error" onerror="this.src='https://cdn-icons-png.flaticon.com/512/755/755014.png'; this.onerror='';"/>
    <h2>404 - Page Not Found</h2>
    <p>Oops! The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p>
    <a href="${pageContext.request.contextPath}/" class="btn">Go to Home Page</a>
</div>

<jsp:include page="../common/footer.jsp" />

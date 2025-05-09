<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Server Error" />
</jsp:include>

<div class="error-container">
    <img src="${pageContext.request.contextPath}/assets/images/500.png" alt="500 Error" onerror="this.src='https://cdn-icons-png.flaticon.com/512/5219/5219070.png'; this.onerror='';"/>
    <h2>500 - Server Error</h2>
    <p>Sorry, something went wrong on our end. Our team has been notified and we're working to fix the issue. Please try again later.</p>
    <a href="${pageContext.request.contextPath}/" class="btn">Go to Home Page</a>
</div>

<jsp:include page="../common/footer.jsp" />

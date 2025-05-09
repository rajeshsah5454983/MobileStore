<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Error" />
</jsp:include>

<div class="error-container">
    <img src="${pageContext.request.contextPath}/assets/images/error.png" alt="Error" onerror="this.src='https://cdn-icons-png.flaticon.com/512/6195/6195678.png'; this.onerror='';"/>
    <h2>Oops! Something went wrong</h2>
    <p>We're sorry, but an error occurred while processing your request. Our team has been notified and we're working to resolve the issue.</p>
    <a href="${pageContext.request.contextPath}/" class="btn">Go to Home Page</a>
</div>

<jsp:include page="../common/footer.jsp" />

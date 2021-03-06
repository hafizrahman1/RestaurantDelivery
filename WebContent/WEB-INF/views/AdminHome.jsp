<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home</title>
<jsp:include page="AdminLinks.jsp" />
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<c:if test="${sessionScope.userId == null}">
		<c:redirect url="AdminError.jsp" />
	</c:if>
	<div class="container">
		<div class="jumbotron">
			<h2>Welcome to the Admin Home Page</h2>
		</div>
	</div>
	<jsp:include page="AdminFooter.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</body>
</html>
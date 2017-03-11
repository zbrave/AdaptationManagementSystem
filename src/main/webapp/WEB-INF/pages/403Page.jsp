<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/login.css" var="loginCSS" />
	<spring:url value="/resources/others/login.js" var="loginJS" />
	<spring:url value="/resources/others/navbar.css" var="navbarCSS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${loginCSS}" rel="stylesheet" />
	<script src="${loginJS}"></script>
	<link href="${navbarCSS}" rel="stylesheet" />
	<title>Access Denied</title>
</head>
<body>
<jsp:include page="_menu.jsp"/>
 
    <h3 style="color:red;">${message}</h3>
</body>
</html>
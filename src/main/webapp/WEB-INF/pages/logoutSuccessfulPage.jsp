<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="refresh" content="1.5;url=${pageContext.request.contextPath}/login" />
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<title>Logout</title>
</head>
<body>
 	<div class="text-center" style="margin-top: 320px;"><h1><span class="glyphicon glyphicon-hourglass"></span></h1></div>
 	<div class="text-center" style="margin-top: 20px;"><h1 class="">Çıkış yapılıyor.</h1></div>     
</body>
</html>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/others/ams.js" var="amsJS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="${amsJS}"></script>
	<title>Logout</title>
</head>
<body>
    <nav class="navbar">
	<div class="menu">
	    <div class="container-fluid">
			<div class="navbar-header">
				<a href="#">Bootsnipp</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-right" id="nav">
					<li><a href="${pageContext.request.contextPath}/welcome" ><span class="glyphicon glyphicon-user"></span> Home</a></li>
					<li><a href="${pageContext.request.contextPath}/userInfo"><span class="glyphicon glyphicon-log-in"></span> User Info</a></li>
					<li><a href="${pageContext.request.contextPath}/admin" ><span class="glyphicon glyphicon-user"></span> Admin</a></li>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	</nav>
 
    <h1>Logout Successful!</h1>
</body>
</html>
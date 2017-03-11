<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 

<nav class="navbar">
<div class="menu">
    <div class="container-fluid">
		<div class="navbar-header">
			<a href="#">Bootsnipp</a>
		</div>
		<div>
			<ul class="nav navbar-nav navbar-right">
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
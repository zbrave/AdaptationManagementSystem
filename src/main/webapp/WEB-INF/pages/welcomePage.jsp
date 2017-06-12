<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/others/ams.js" var="amsJS" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.bundle.js"></script>
	<title>${title}</title>
</head>
<script type="text/javascript">
$.get("http://freegeoip.net/json/", function (response) {
    $("#ip").html("IP: " + response.ip);
    $("#address").html(response.country_name + " / " + response.region_name);
}, "jsonp");
</script>
<body>
	<%@include file="navbar.jsp" %>
	<div id="country"></div>
	<div class="text-center">
        <div class="media">
            <div class="media-body">
                <h4 class="media-heading">Hoşgeldin, ${pageContext.request.userPrincipal.name} <small id="address"></small></h4>
                <h5 id="ip"></h5>
                <hr style="margin:8px auto">
                <c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
					<c:if test="${role.authority == 'ROLE_ADMIN' }">
				<div id="canvas-holder" style="width:20%; margin-left: 765px;">
					<h2>İntibak Görevlileri Yük Dağılımı</h2>
					<canvas id="myChart"  />
			    </div>
				<script>
				var ctx = document.getElementById("myChart").getContext('2d');
				var myChart = new Chart(ctx, {
				  type: 'pie',
				  data: {
				    labels: [${users}],
				    datasets: [{
				      backgroundColor: [${colors}],
				      data: [${numbers}]
				    }]
				  }
				});
				</script>
				</c:if>
				</c:forEach>
                <hr style="margin:8px auto">
                <c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
					<c:if test="${role.authority == 'ROLE_ADMIN' }">
						<span class="label label-info"><c:out value="Yönetici" /></span>
					</c:if>
					<c:if test="${role.authority == 'ROLE_MANAGER' }">
						<span class="label label-info"><c:out value="İntibak Görevlisi" /></span>
					</c:if>
					<c:if test="${role.authority == 'ROLE_USER' }">
						<span class="label label-default"><c:out value="Öğrenci" /></span>
					</c:if>
				</c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
                <h4 class="media-heading">Hoşgeldin, ${pageContext.request.userPrincipal.name} <c:forEach var="role"
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
				</c:forEach> <small id="address"></small></h4>
                <h5 id="ip"></h5>
                <hr style="margin:8px auto">
	
 <div class="col-lg-3" style="margin-left: 720px;">
     <div class="form" style="max-width: 600px;"> <!-- for background transparent color -->
     <p style="font-size: 30px; color: white; text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;">
    		Kullanıcı Bilgileri
    	</p>
 	<div class="form-group">
 	<div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i> Kullanıcı Adı:</span>
    	<li class="list-group-item">${userInfo.username }</li>
    </div>
 	<div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i> E-mail:</span>
    	<li class="list-group-item">${userInfo.email }</li>
    </div>
    <div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i> Telefon:</span>
    	<li class="list-group-item">${userInfo.tel }</li>
    </div>
    <div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i> Şifre :</span>
    	<li class="list-group-item">********** </li>
    	<span class="input-group-btn"> <button type="button" class="btn btn-info" data-toggle="modal" data-target="#passwordChange">Yeni Şifre Oluştur</button></span>
    </div>
    </div>
 	<c:if test="${not empty msg}">
		<div class="alert alert-info alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${msg}
		</div>
	</c:if> 
 	
 	<!-- Modal -->
	<div id="passwordChange" class="modal fade" role="dialog">
	  <div class="modal-dialog" role="document">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Şifre değiştirme</h4>
	      </div>
	      <div class="modal-body">
	      
	        <form:form action="changePass" method="POST" modelAttribute="userForm">
		<div class="form-group">
			<div class="input-group">
				<input id="id" name="id" type="hidden" value=""/>
				<span class="input-group-addon">Yeni şifre:</span>
				<input class="form-control" id="password" name="password"  required="required"/>
     		</div>
     				<div class="input-group">
				<input id="id" name="id" type="hidden" value=""/>
				<span class="input-group-addon">Yeni şifre tekrar:</span>
							<input class="form-control" id="passwordConf" name="passwordConf" required="required" />
     				</div>
     				<button type="submit" class="btn btn-default" value="" >Değiştir</button>
       			</div> <!-- form-group -->
    </form:form>
    
	      </div>
	      <div class="modal-footer">
	      	
	        <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
	      </div>
	    </div>
	
	  </div>
	</div><!-- modal end -->
    
  </div>
 </div><div class="col-lg-9" style="margin-left: 785px;">
                <c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
					<c:if test="${role.authority == 'ROLE_ADMIN' }">
				<div id="canvas-holder" style="width:20%;">
					<h5>İntibak Görevlileri Yük Dağılımı</h5>
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
				</div>
            </div>
        </div>
    </div>
    
</body>
</html>
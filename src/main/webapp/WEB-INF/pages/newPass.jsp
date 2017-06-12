<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/js/bootstrap-confirmation.js" var="bootstrapConfirmationJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<script src="${bootstrapConfirmationJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<title>IYS | Şifre Yenileme</title>	
</head>
<body>
	
<div class="container">
		<div class="row">
            <div class="panel panel-primary">
            <div class="panel-heading">Şifre yenileme</div>
	            <div class="panel-body">
     <div class="form"> <!-- for background transparent color -->
	
	<form:form action="refreshPass" method="POST" modelAttribute="userForm">
					<input id="id" name="id" type="hidden" value="${user.id }"/>
					<div class="input-group">
						<span class="input-group-addon">Yeni şifre</span>
						<input class="form-control" id="password" name="password" value=""/>
    				</div>
    				<div class="input-group">
						<span class="input-group-addon">Yeni şifre tekrar</span>
						<input class="form-control" id="passwordConf" name="passwordConf" value=""/>
    				</div>
    				<div class="input-group">
        				<span class="input-group-btn">
        					<button type="submit" class="btn btn-default" value="" >Değiştir</button>
        				</span>
          			</div>
        			<c:if test="${not empty msg}">
		   				<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   				</div>
					</c:if> 
      	</form:form>
      	
      	</div>
      </div>
     </div>
     </div>
     </div>
</body>
</html>
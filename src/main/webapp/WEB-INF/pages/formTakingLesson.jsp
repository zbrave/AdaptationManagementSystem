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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script type="text/javascript">
$(document).ready(function(){
	$('#uni').on('change',function(){
		$.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#dept").html(data);
	    });
	});
});</script>
<script type="text/javascript">
$(document).ready(function(){
	$('#dept').on('change',function(){
		$.get("${pageContext.request.contextPath}/getTakingLesson?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#takingLessonId").html(data);
	    });
	});
});</script>
<title>Alınan Ders Düzenleme</title>
<style>
.error-message {
   color: red;
   font-size:90%;
   font-style: italic;
}
</style>
</head>
<body>
 <%@include file="navbar.jsp" %>
	<!-- Forms -->
	<div id="newStu" >
  <div class="container">
		<div class="row">
            <div class="panel panel-primary">
            <div class="panel-heading">Alınan Ders Formu</div>
	            <div class="panel-body">
 
   <form:form action="saveTakingLesson" method="POST" modelAttribute="takingLessonForm">
					   			
		<form:hidden path="id" />
	       <form:hidden path="deptId" />
	       
	       <label class="control-label">Ders Adı</label><form:errors path="name" class="error-message" />
	                  
	       <form:input id="name" path="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Ders Kodu</label><form:errors path="code" class="error-message" />
	                  
	       <form:input id="code" path="code" class="form-control input-sm" name="code" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Ders Dili</label><form:errors path="lang" class="error-message" />
	                  
	       <form:input id="lang" path="lang" class="form-control input-sm" name="lang" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Kredi</label><form:errors path="credit" class="error-message" />
	       
	       <form:input id="credit" path="credit" class="form-control input-sm" name="credit" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">AKTS</label><form:errors path="akts" class="error-message" />
	       
	       <form:input id="akts" path="akts" class="form-control input-sm" name="akts" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Lab Saati</label><form:errors path="lab" class="error-message" />
	       
	       <form:input id="lab" path="lab" class="form-control input-sm" name="lab" type="text" value="" style="height: 35px!important"/>
	
			<label class="control-label">Ders Dönemi</label><form:errors path="term" class="error-message" />
	                   
        	<form:input id="term" path="term" class="form-control input-sm" name="term" type="text" value="" style="height: 35px!important"/>
	<div class="clearfix"></div><br/>
	       	<button type="submit" class="btn btn-primary" value="Ekle">Ekle</button>
	      
	     <c:if test="${not empty message4}">
	 <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message4}
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
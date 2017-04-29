<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="${amsJS}"></script>	
	<title>${title}</title>
</head>
<body>
 	<%@include file="navbar.jsp" %>
	<!-- Forms -->
	<div id="newStu" >
  <div class="container">
		<div class="row">
            <div class="panel panel-primary">
            <div class="panel-heading">Yeni Not Ekle<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button></div>
	            <div class="panel-body">
 
   <form:form action="saveOurmark" method="POST" modelAttribute="ourmarkForm">
    <div class="form-group">

      <label class="control-label">ID</label>
      <form:input class="form-control" path="id" readonly="true" />

      <label class="control-label">Harf notu</label>
      <form:input class="form-control" path="mark" />
      <form:errors path="mark" class="error-message" />    

      <label class="control-label">Puanı</label>
      <form:input class="form-control" path="value" />
      <form:errors path="value" class="error-message" />
          
      <button class="btn btn-primary" type="  " value="Submit" >Ekle</button>
      <button class="btn btn-danger"><a href="${pageContext.request.contextPath}/Ourmark">Cancel</a></button>
     </div>    
   </form:form>
 </div>
 </div>
 </div>
 </div>
 </div>
</body>
</html>
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
<script type="text/javascript">
$(document).ready(function(){
  $.get("${pageContext.request.contextPath}/getUni", null, function (data) {
          $("#sUniId").html(data);
          $("#sUniId").val('${uniId}');
      });
	$.get("${pageContext.request.contextPath}/getDept?id=${studentForm.deptId}", null, function (data) {
      $("#sDeptId").html(data);
      $('#sDeptId').val('${studentForm.deptId}');
	});
  $('#sUniId').on('change',function(){
    $.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
          $("#deptId").html(data);
      });
  });
  $('#sUniId').on('change',function(){
    $.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
          $("#sDeptId").html(data);
      });
  });
  $('#sDeptId').on('change' ,function(){
    var sa = $(this).val();
    $('#deptId').val(sa); 
  });
});</script>
<body>
 	<%@include file="navbar.jsp" %>
	<!-- Forms -->
	<div id="newStu" >
  <div class="container">
		<div class="row">
            <div class="panel panel-primary">
            <div class="panel-heading">Yeni Öğrenci Ekle<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button></div>
	            <div class="panel-body">
 
   <form:form action="saveStudent" method="POST" modelAttribute="studentForm">
    <div class="form-group">

      <label class="control-label">ID</label>
      <form:input class="form-control" path="id" readonly="true" />
      
      <label class="control-label">Üniversite</label>
      <select id="sUniId" class="form-control" ></select>

      <label class="control-label">Bölüm</label>
      <select id="sDeptId" class="form-control" ></select>

      <form:input path="deptId" class="form-control" type="hidden" />

      <label class="control-label">Ad</label>
      <form:input class="form-control" path="name" />
      <form:errors path="name" class="error-message" />    

      <label class="control-label">Soyad</label>
      <form:input class="form-control" path="surname" />
      <form:errors path="surname" class="error-message" />

      <label class="control-label">Numara</label>
      <form:input class="form-control" path="no" />
      <form:errors path="no" class="error-message" /> 

      <label class="control-label">İntibak Notu</label>
      <form:input class="form-control" path="adpScore" />
      <form:errors path="adpScore" class="error-message" />

      <label class="control-label">Kayıt Yılı</label>
      <form:input class="form-control" path="recordYear" />
      <form:errors path="recordYear" class="error-message" />
          
      <button class="btn btn-primary" type="  " value="Submit" >Ekle</button>
      <button class="btn btn-danger"><a href="${pageContext.request.contextPath}/Student">Cancel</a></button>
     </div>    
   </form:form>
 </div>
 </div>
 </div>
 </div>
 </div>
</body>
</html>
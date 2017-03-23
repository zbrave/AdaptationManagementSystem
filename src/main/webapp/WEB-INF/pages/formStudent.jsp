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
  $.get("${pageContext.request.contextPath}/getUniDept?id=" + $('#deptId').val(), null, function (data) {
    $("#UniDept").html(data);
  });
  $('#uniId').on('change',function(){
    $.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
          $("#deptId").html(data);
      });
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
      <fieldset disabled>
      <label class="control-label">ID</label>
      <form:input class="form-control disabled" path="id" />
      </fieldset>

      <form:input path="deptId" type="hidden" />

      <div id="UniDept"></div>

      <label class="control-label">Ad</label>
      <form:input class="form-control" path="name" /></td>
      <form:errors path="name" class="error-message" />    

      <label class="control-label">Soyad</label>
      <form:input class="form-control" path="surname" /></td>
      <form:errors path="surname" class="error-message" />

      <label class="control-label">Numara</label>
      <form:input class="form-control" path="no" /></td>
      <form:errors path="no" class="error-message" /> 

      <label class="control-label">İntibak Notu</label>
      <form:input class="form-control" path="adpScore" /></td>
      <form:errors path="adpScore" class="error-message" />

      <label class="control-label">Kayıt Yılı</label>
      <form:input class="form-control" path="recordYear" /></td>
      <form:errors path="recordYear" class="error-message" />
          
           <tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" />
                  <a href="${pageContext.request.contextPath}/Student">Cancel</a>
               </td>
               <td>&nbsp;</td>
           </tr>
       </table>
   </form:form>
 </div>
 </div></div></div></div>
</body>
</html>
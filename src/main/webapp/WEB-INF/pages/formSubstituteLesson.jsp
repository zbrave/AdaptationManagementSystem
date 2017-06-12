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
<title>Sayılan Ders Düzenleme Formu</title>
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
            <div class="panel-heading">Ders Düzenleme Formu</div>
	            <div class="panel-body">
 
   <form:form action="saveSubstituteLesson" method="POST" modelAttribute="substituteLessonForm">
					   			
		<form:hidden path="id" />
	       
	       <form:select id="curriculumId" path="curriculumId" name="curriculumId" class="form-control">
                  <c:forEach items="${curriculums }" var="data" >
                  <c:choose>
               	<c:when test="${data.enabled == true}">
               		<option id="${data.id }" value="${data.id }" selected>${data.year }</option>
               	</c:when>
               	<c:otherwise>
               		<option id="${data.id }" value="${data.id }" >${data.year }</option>
               	</c:otherwise>
               </c:choose>
               </c:forEach>
           </form:select>
	       
	       <label class="control-label">Ders Adı</label>
	                  
	       <form:input id="name" path="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Ders Kodu</label>
	                  
	       <form:input id="code" path="code" class="form-control input-sm" name="code" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Ders Dili</label>
	                  
	       <form:input id="lang" path="lang" class="form-control input-sm" name="lang" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Kredi</label>
	       
	       <form:input id="credit" path="credit" class="form-control input-sm" name="credit" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">AKTS</label>
	       
	       <form:input id="akts" path="akts" class="form-control input-sm" name="akts" type="text" value="" style="height: 35px!important"/>
	       
	       <label class="control-label">Lab Saati</label>
	       
	       <form:input id="lab" path="lab" class="form-control input-sm" name="lab" type="text" value="" style="height: 35px!important"/>
	
			<label class="control-label">Ders Dönemi</label>
	                   
        	<form:input id="term" path="term" class="form-control input-sm" name="term" type="text" value="" style="height: 35px!important"/>
        	
        	<label class="control-label">Ders şartı</label>
					            
            <form:select id="conditionId" path="conditionId" name="conditionId" class="form-control">
                   <c:forEach items="${subLes }" var="data" >
               		<option id="${data.id }" value="${data.id }" >${data.name }</option>
                </c:forEach>
            </form:select>
            
            <br>
			                    	<p id="demo"></p>
			                    <div id="tab" class="btn-group btn-group-justified" data-toggle="buttons">
							        <a id="p1" href="#pool1" class="btn btn-success active" data-toggle="tab" onclick="setPool(1)">
							          <input type="radio" class="pool1" />Havuz Dersi Değil
							        </a>
							        <a id="p2" href="#pool2" class="btn btn-success" data-toggle="tab" onclick="setPool(2)" >
							          <input type="radio" class="pool2" />Yeni Havuz Aç
							        </a>
							        <a id="p3" href="#pool3" class="btn btn-success" data-toggle="tab" onclick="setPool(3)">
							          <input type="radio" class="pool3" />Havuz Dersi Seç
							        </a>
							      </div>

							      <div class="tab-content">
							        <div class="tab-pane active" id="pool1">
							        <input id="base" class="form-control input-sm" name="base" type="hidden" value=""/>
							        </div>
							        <div class="tab-pane" id="pool2">
							        	<input id="base2" class="form-control input-sm pool2target" name="base" type="text" value="" style="height: 35px!important" disabled/>
							        </div>
							        <div class="tab-pane" id="pool3">
										<select id="base3" name="base" class="form-control" disabled>
				                            <c:forEach items="${pool }" var="data" >
				                        		<option value="${data }H" >${data}</option>
					                        </c:forEach>
					                    </select>
									</div>
							      </div>
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
   
 <script type="text/javascript">
	function setPool(id) {
		if (id == 1) {
			document.getElementById('base2').setAttribute('disabled', 'disabled');
			document.getElementById('base3').setAttribute('disabled', 'disabled');
		}
		if (id == 2) {
			document.getElementById('base2').removeAttribute('disabled');
			document.getElementById('base3').setAttribute('disabled', 'disabled');
			document.getElementById('base').setAttribute('disabled', 'disabled');
		}
		if (id == 3) {
			document.getElementById('base3').removeAttribute('disabled');
			document.getElementById('base2').setAttribute('disabled', 'disabled');
			document.getElementById('base').setAttribute('disabled', 'disabled');
			document.getElementById('term').setAttribute('readonly', 'readonly');
		}
}
</script>
</body>
</html>
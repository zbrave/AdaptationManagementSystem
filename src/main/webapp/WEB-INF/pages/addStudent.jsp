<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
  src="https://code.jquery.com/jquery-3.1.1.js"
  integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
  crossorigin="anonymous"></script>
  
   <script type="text/javascript">
$(document).ready(function(){
	$.get("${pageContext.request.contextPath}/getUni", null, function (data) {
	        $("#uniId").html(data);
	    });
});</script>
 <script type="text/javascript">
$(document).ready(function(){
	$('#uniId').on('change',function(){
		$.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#deptId").html(data);
	    });
	});
});</script>
<title>Create substituteLesson</title>
<style>
.error-message {
   color: red;
   font-size:90%;
   font-style: italic;
}
</style>
</head>
<body>
	<nav class="navbar">
	<div class="menu">
	    <div class="container-fluid">
			<div class="navbar-header">
				<a href="#">IYS</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-right" id="nav">
					<li><a href="${pageContext.request.contextPath}/welcome" ><span class="glyphicon glyphicon-user"></span> Anasayfa</a></li>
					<li><a href="${pageContext.request.contextPath}/userInfo"><span class="glyphicon glyphicon-log-in"></span> User Info</a></li>
					<li><a href="${pageContext.request.contextPath}/admin" ><span class="glyphicon glyphicon-user"></span> Admin</a></li>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li class="active"><a href="${pageContext.request.contextPath}/addRules" ><span class="glyphicon glyphicon-plus"></span> Yeni Kural</a></li>
						<li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	</nav>
	<div class="col-lg-1"></div>
	<!-- Forms -->
	<div class="col-lg-10">
		<div class="row">
            <div class="panel panel-default">
	            <div class="panel-body">
	            <form:form action="saveStudent" method="POST" modelAttribute="studentForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>
						
						<input id="adpScore" name="adpScore" type="hidden" value="0"/>
						
						<label class="control-label">Üniversite Adı</label>
									 			
				   		<select id="uniId" class="form-control" name="uniId" ></select>
				    
				    	<label class="control-label">Bölüm Adı</label>
				                       
				        <select id="deptId" class="form-control" name="deptId" ></select>
				          
				        <label class="control-label">Ad</label>
				                       
				        <input id="name" class="form-control" name="name" />
				        
				        <label class="control-label">Soyad</label>
				                       
				        <input id="surname" class="form-control" name="surname" />
				        
				        <label class="control-label">Numara</label>
				                       
				        <input id="no" class="form-control" name="no" />
				        
				        <label class="control-label">Kayıt Yılı</label>
				                       
				        <input id="recordYear" class="form-control" name="recordYear" />
				        
				        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
							        
				        <c:if test="${not empty message5}">
						   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
						   </div>
						</c:if> 
			        </div>
		        </form:form>
		        </div>
	        </div>
        </div>
	</div>                     
</body>
</html>
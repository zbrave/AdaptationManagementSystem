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
	$.get("${pageContext.request.contextPath}/getStudent", null, function (data) {
	        $("#id").html(data);
	    });
});</script>
<script type="text/javascript">
$(document).ready(function(){
	$.get("${pageContext.request.contextPath}/getTakingLesson?id=${deptId}" , null, function (data) {
        $("#takingLessonId").html(data);
    });
	$.get("${pageContext.request.contextPath}/getSubstituteLesson", null, function (data) {
        $("#substituteLessonId").html(data);
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
	            <form:form action="getStudentData" method="POST" modelAttribute="studentForm">
					<div class="form-group">
						
						<label class="control-label">Öğrenci Adı</label>
									 			
				   		<select id="id" class="form-control" name="id" ></select>

				   		<button type="submit" class="btn btn-default" value="Ara" >Ara</button>
							        
				        <c:if test="${not empty message5}">
						   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message}
						   </div>
						</c:if> 
			        </div>
		        </form:form>
		        </div>
	        </div> 
	        <div class="panel-body">
		        <table class="table table-striped table-bordered table-list">
		        	<thead>
						<tr class="active">
							<th>Üniversite</th>
							<th>Bölüm</th>
							<th>Numara</th>
							<th>Ad</th>
							<th>Soyad</th>
							<th>İntibak Notu</th>
							<th>Kayıt Yılı</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${uni}</td>
							<td>${dept}</td>
							<td>${no}</td>
							<td>${name}</td>
							<td>${surname}</td>
							<td>${adpScore}</td>
							<td>${recordYear}</td>
						</tr>
					</tbody>
				</table>
				<form:form action="saveStudentLesson" method="POST" modelAttribute="studentLessonForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>
						
						<input id="studentId" name="studentId" type="hidden" value="${id}"/>
						
						<label class="control-label">Alınan Ders</label>
									 			
				   		<select id="takingLessonId" class="form-control" name="takingLessonId" ></select>

						<label class="control-label">Sayılan Ders</label>
									 			
				   		<select id="substituteLessonId" class="form-control" name="substituteLessonId" ></select>
				   		
				   		<label class="control-label">Not</label>
				   		
				   		<input id="orgMark" name="orgMark" type="text" value=""/>
				   		
				   		<input id="convMark" name="convMark" type="hidden" value=""/>
				   		
				   		<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
							        
				        <c:if test="${not empty message5}">
						   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message}
						   </div>
						</c:if> 
			        </div>
		        </form:form>			
			</div>
        </div>
	</div>
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">1.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-6 text-right">
                    <h5 class="sub-header">Sayılan Dersler</h5>
                  </div>
                </div>
              </div>
              <div class="panel-body">
		        <table class="table table-striped table-bordered table-list">
		        	<thead>
						<tr class="active">
							<th>Ders kodu</th>
							<th>Ders adı</th>
							<th>Dersin dili</th>
							<th>Kredi</th>
							<th>AKTS</th>
							<th>Başarı notu</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lessons}" var="info">
							<c:if test="${info.subTerm == 1}">
								<tr>
								 	<td> ${info.subCode}  </td>
								   	<td> ${info.subName}  </td>
								  	<td> ${info.subLang}  </td>
								   	<td> ${info.subCredit}  </td>
								   	<td> ${info.subAkts}  </td>
								   	<td> ${info.subMark}  </td>
								</tr>
							</c:if>
						 </c:forEach>
					</tbody>
				</table>				
			</div>
			</div>
	</div>
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">1.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-6 text-right">
                    <h5 class="sub-header">Alınan Dersler</h5>
                  </div>
                </div>
              </div>
              <div class="panel-body">
		        <table class="table table-striped table-bordered table-list">
		        	<thead>
						<tr class="active">
							<th>Ders kodu</th>
							<th>Ders adı</th>
							<th>Dersin dili</th>
							<th>Kredi</th>
							<th>AKTS</th>
							<th>Başarı notu</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lessons}" var="info">
							<c:if test="${info.subTerm == 1}">
								<tr>
								   	<td> ${info.takCode}  </td>
								   	<td> ${info.takName}  </td>
								  	<td> ${info.takLang}  </td>
								   	<td> ${info.takCredit}  </td>
								   	<td> ${info.takAkts}  </td>
								   	<td> ${info.takMark}  </td>
								</tr>
							</c:if>
						 </c:forEach>
					</tbody>
				</table>				
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">2.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-6 text-right">
                    <h5 class="sub-header">Sayılan Dersler</h5>
                  </div>
                </div>
              </div>
              <div class="panel-body">
		        <table class="table table-striped table-bordered table-list">
		        	<thead>
						<tr class="active">
							<th>Ders kodu</th>
							<th>Ders adı</th>
							<th>Dersin dili</th>
							<th>Kredi</th>
							<th>AKTS</th>
							<th>Başarı notu</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lessons}" var="info">
							<c:if test="${info.subTerm == 2}">
								<tr>
								 	<td> ${info.subCode}  </td>
								   	<td> ${info.subName}  </td>
								  	<td> ${info.subLang}  </td>
								   	<td> ${info.subCredit}  </td>
								   	<td> ${info.subAkts}  </td>
								   	<td> ${info.subMark}  </td>
								</tr>
							</c:if>
						 </c:forEach>
					</tbody>
				</table>				
			</div>
			</div>
	</div>
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">2.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-6 text-right">
                    <h5 class="sub-header">Alınan Dersler</h5>
                  </div>
                </div>
              </div>
              <div class="panel-body">
		        <table class="table table-striped table-bordered table-list">
		        	<thead>
						<tr class="active">
							<th>Ders kodu</th>
							<th>Ders adı</th>
							<th>Dersin dili</th>
							<th>Kredi</th>
							<th>AKTS</th>
							<th>Başarı notu</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lessons}" var="info">
							<c:if test="${info.subTerm == 2}">
								<tr>
								   	<td> ${info.takCode}  </td>
								   	<td> ${info.takName}  </td>
								  	<td> ${info.takLang}  </td>
								   	<td> ${info.takCredit}  </td>
								   	<td> ${info.takAkts}  </td>
								   	<td> ${info.takMark}  </td>
								</tr>
							</c:if>
						 </c:forEach>
					</tbody>
				</table>				
			</div>
		</div>
	</div>         
</body>
</html>
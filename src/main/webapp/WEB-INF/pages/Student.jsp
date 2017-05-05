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
<script type="text/javascript">
$(document).ready(function(){
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	var pvar = getUrlParameter('pageid');
	$('#' + pvar).addClass("active");
	console.log(pvar-1);
	console.log(pvar+1);
	$("#lefta").attr("href", "Student?pageid="+pvar-1);
	$("#righta").attr("href", "Student?pageid="+pvar+1);
	if (pvar == 1) {
		$('#left').addClass("disabled");
		$("#lefta").attr("href", "#");
	}
	if (pvar == ${pageSize}) {
		$('#right').addClass("disabled");
		$("#righta").attr("href", "#");
	}
});
</script>
<title>IYS - Öğrenciler</title>
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
	<div id="newStu" class="container modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display: none; margin-top: 50px;">
		<div class="row">
            <div class="panel panel-primary">
            <div class="panel-heading">Yeni Öğrenci Ekle<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button></div>
	            <div class="panel-body">
	            <form:form action="saveStudent" method="POST" modelAttribute="studentForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>
						
						<input id="adpScore" name="adpScore" type="hidden" value=""/>
						
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
				        <br/>
				        <button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button> 
			        </div>
		        </form:form>
		        </div>
	        </div>
        </div>
	</div>
	
	<div class="container">
    <div class="row custyle">
    	<form action="${pageContext.request.contextPath}/Student" class="form-inline">
    		<input type="hidden" name="pageid" value="1" />
		    <input class="input-sm" name="searchTerm" placeholder="" />
		    <select name="category" class="form-control" style="width: 15%">
		    	<option value="uni">Üniversite</option>
		    	<option value="deptId">Bölüm</option>
		    	<option value="name">Ad</option>
		    	<option value="surname">Soyad</option>
		    	<option value="no">Numara</option>
		    	<option value="adp_Score">İntibak Yılı</option>
		    	<option value="record_year">Kayıt Yılı</option>
		    </select>
		    <button class="btn btn-default">Ara</button>
		</form>
    </div>
    </div>
	
	<div class="container">
    <div class="row custyle">
    <table class="table table-striped custab" style="background-color: #FFF;">
    <thead>
    <a href="#" class="btn btn-primary btn-xs pull-right" data-toggle="modal" data-target="#newStu" onclick="$('#newStu').show();"><b>+</b> Yeni Öğrenci Ekle</a>
        <tr>
            <th>ID</th>
            <th>Geldiği Üniversite</th>
            <th>Bölüm</th>
            <th>Ad</th>
            <th>Soyad</th>
            <th>Numara</th>
            <th>İntibak Yılı</th>
            <th>Kayıt Yılı</th>
            <th>Danışman</th>
            <th class="text-center">Action</th>
        </tr>
    </thead>
    	 <c:forEach items="${students}" var="info">
            <tr>
                <td>${info.id}</td>
                <td>${info.uniName}</td>
                <td>${info.deptName}</td>
                <td>${info.name}</td>
                <td>${info.surname}</td>
                <td>${info.no}</td>
                <td>${info.adpScore}</td>
                <td>${info.recordYear}</td>
                <td>${info.advisor}</td>
                <td class="text-center">
                <a class='btn btn-info btn-xs' href="getStudentData?id=${info.id}"><span class="glyphicon glyphicon-edit"></span> İntibak yap</a>
                <a class='btn btn-info btn-xs' href="editStudent?id=${info.id}"><span class="glyphicon glyphicon-edit"></span> Edit</a> 
                <a href="deleteStudent?id=${info.id}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Del</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <ul class="pagination pull-right">
	  <li id="left"><a id="lefta" href=""><span class="glyphicon glyphicon-chevron-left"></span></a></li>
	  <c:forEach var="i" begin="1" end="${pageSize }">
	  <li id="${i }"><a href="Student?pageid=${i }">${i }</a></li>
	  </c:forEach>
	  <li id="right"><a href=""><span class="glyphicon glyphicon-chevron-right"></span></a></li>
	</ul>
    </div>
    <c:if test="${not empty message5}">
	   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
	   </div>
	</c:if>
</div>

</body>
</html>
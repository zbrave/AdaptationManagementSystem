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
	var search = getUrlParameter('searchTerm');
	var cate = getUrlParameter('category');
	$('#' + pvar).addClass("active");
	pvar = parseInt(pvar,10);
	var l = pvar-1;
	var r = pvar+1;
	console.log(l);
	console.log(r);
	if (search != undefined && cate != undefined) {
		$("#lefta").attr("href", "listNewUser?pageid="+l+"&searchTerm="+search+"&category="+cate);
		$("#righta").attr("href", "listNewUser?pageid="+r+"&searchTerm="+search+"&category="+cate);
		for (i = 1; i <= ${pageSize}; i++) {
			$("#mid"+i).attr("href", "listNewUser?pageid="+i+"&searchTerm="+search+"&category="+cate);	
		}
	}
	else {
		$("#lefta").attr("href", "listNewUser?pageid="+l);
		$("#righta").attr("href", "listNewUser?pageid="+r);
		for (i = 1; i <= ${pageSize}; i++) {
			$("#mid"+i).attr("href", "listNewUser?pageid="+i);	
		}
	}
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
<title>IYS - Kullanıcılar</title>
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
	
	<div class="container">
    <div class="row custyle">
    	<form action="${pageContext.request.contextPath}/listNewUser" class="form-inline">
    		<input type="hidden" name="pageid" value="1" />
		    <input class="input-sm" name="searchTerm" placeholder="" />
		    <select name="category" class="form-control" style="width: 15%">
		    	<option value="username">Kullanıcı adı</option>
		    	<option value="email">E-Mail</option>
		    	<option value="studentId">Öğrenci Id</option>
		    	<option value="enabled">Yasaklı</option>
		    </select>
		    <button class="btn btn-default">Ara</button>
		</form>
    </div>
    </div>
	<c:if test="${not empty message}">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
	   <div class="alert alert-success alert-dismissible text-center" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message}
	   </div></div>
	</c:if>
	<div class="container">
    <div class="row custyle">
    <table class="table table-striped custab" style="background-color: #FFF;">
    <thead>
        <tr>
            <th>ID</th>
            <th>Kullanıcı adı</th>
            <th>E-Mail</th>
            <th>Telefon</th>
            <th>Öğrenci Id</th>
            <th style="text-align: center;">Giriş yapabilir ?</th>
            <th class="text-center">Eylem</th>
        </tr>
    </thead>
    	 <c:forEach items="${users}" var="info">
            <tr>
                <td>${info.id}</td>
                <td>${info.username}</td>
                <td>${info.email}</td>
                <td>${info.tel}</td>
                <td><a href="${pageContext.request.contextPath}/getStudentData?id=${info.studentId}">${info.studentId}</a></td>
                <td>
                	<c:if test="${info.enabled == true}">
                		<label class="btn btn-block">
							<span class="glyphicon glyphicon-ok"></span>
						</label>
                	</c:if>
                	<c:if test="${info.enabled == false}">
                		<label class="btn btn-block">
							<span class="glyphicon glyphicon-remove"></span>
						</label>
                	</c:if>
                </td>
                <td class="text-center">
               	<c:if test="${info.manager == true && empty info.studentId}">
                	<a href="setAdminToUser?id=${info.id}" class="btn btn-warning btn-xs"><span class="glyphicon glyphicon-remove"></span> Yönetici görevini sil</a>
            	</c:if>
            	<c:if test="${info.manager == false && empty info.studentId}">
            		<a href="setUserToAdmin?id=${info.id}" class="btn btn-warning btn-xs"><span class="glyphicon glyphicon-plus"></span> Yönetici görevi ata</a>
            	</c:if>
                <c:if test="${info.manager == true && empty info.studentId}">
                	<a href="setManager?id=${info.id}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-remove"></span> İntibak görevini sil</a>
            	</c:if>
            	<c:if test="${info.manager == false && empty info.studentId}">
            		<a href="setManager?id=${info.id}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-plus"></span> İntibak görevi ata</a>
            	</c:if>
            	<c:if test="${empty info.studentId}">
                	<a href="setUserToStudent?id=${info.id}" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-remove"></span> Öğrenci olarak ata</a>
            	</c:if>
            	<c:if test="${not empty info.studentId}">
            		<a href="setStudentToUser?id=${info.id}" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-plus"></span> Öğrenciliği sil</a>
            	</c:if>
                <a href="deleteUser?id=${info.id}" class="btn btn-danger btn-xs" data-toggle="confirmation" data-btn-ok-label="Evet" data-btn-cancel-label="Hayır" data-title="Bağlantılı tüm bilgiler bu işlemle birlikte silinecek. Emin misiniz?"><span class="glyphicon glyphicon-remove"></span> Sil</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <ul class="pagination pull-right">
	  <li id="left"><a id="lefta" href=""><span class="glyphicon glyphicon-chevron-left"></span></a></li>
	  <c:forEach var="i" begin="1" end="${pageSize }">
	  <li id="${i }"><a id="mid${i}" href="listNewUser?pageid=${i }">${i }</a></li>
	  </c:forEach>
	  <li id="right"><a id="righta" href=""><span class="glyphicon glyphicon-chevron-right"></span></a></li>
	</ul>
    </div>
</div>

</body>
<script type="text/javascript">
$('[data-toggle=confirmation]').confirmation({
  rootSelector: '[data-toggle=confirmation]',
  // other options
});
</script>
</html>
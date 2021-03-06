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
<title>Not sistemi(YTU)</title>
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
            <div class="panel-heading">Yeni Not Ekle<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button></div>
	            <div class="panel-body">
	            <form:form action="saveOurmark" method="POST" modelAttribute="ourmarkForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>
						
						<label class="control-label">Harf Notu</label>
									 			
				   		<input id="mark" class="form-control" name="mark" />
				    
				    	<label class="control-label">Puanı</label>
				                       
				        <input id="value" class="form-control" name="value" />
				          
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
    <table class="table table-striped custab" style="background-color: #FFF;">
    <thead>
    <a href="#" class="btn btn-primary btn-xs pull-right" data-toggle="modal" data-target="#newStu" onclick="$('#newStu').show();"><b>+</b> Yeni Not Ekle</a>
        <tr>
            <th>ID</th>
            <th>Harf Notu</th>
            <th>Puanı</th>
            <th class="text-center">Eylem</th>
        </tr>
    </thead>
    	 <c:forEach items="${ourmarks}" var="info">
            <tr>
                <td>${info.id}</td>
                <td>${info.mark}</td>
                <td>${info.value}</td>
                <td class="text-center">
                <a class='btn btn-info btn-xs' href="editOurmark?id=${info.id}"><span class="glyphicon glyphicon-edit"></span> Değiştir</a> 
                <a href="deleteOurmark?id=${info.id}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Sil</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    </div>
    <c:if test="${not empty message5}">
	   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
	   </div>
	</c:if>
</div>

</body>
</html>
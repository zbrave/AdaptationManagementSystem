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
	$.get("${pageContext.request.contextPath}/getTakingLesson?id=${deptId}" , null, function (data) {
        $("#takingLessonId").html(data);
    });
	$.get("${pageContext.request.contextPath}/getSubstituteLesson", null, function (data) {
        $("#substituteLessonId").html(data);
    });
});</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#takingLessonId").change(function(){
	    $.get("${pageContext.request.contextPath}/getSubstituteLessonById?id="+$(this).children("option").filter(":selected").attr("id") , null, function (data) {
	        $("#substituteLessonId").html(data);
	    });
	});
});</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#takingLessonId").change(function(){
		var id = $(this).children("option").filter(":selected").attr("id");
		console.log("id: "+id);
		if (id == -1) {
			document.getElementById('orgMark').setAttribute('readonly', 'readonly');
		}
		else {
			document.getElementById('orgMark').removeAttribute('readonly');
		}
	});
});
</script>
<title>Öğrenci İntibak Formu</title>
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
	<div id="newStu" class="container modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display: none; margin-top: 50px;">
		<div class="row">
            <div class="panel panel-primary">
            <div class="panel-heading">İntibak Yılı Hesapla<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button></div>
	            <div class="panel-body">
		        	<form:form class="form-inline" action="${pageContext.request.contextPath}/calcAdp" method="GET">
			    		<input type="hidden" class="form-control" name="id" value="${id}" />
					    <label class="control-label" style="margin-left: 10px;">Katsayı</label>
					    <input class="input-sm" class="form-control" name="coef" placeholder="Katsayı girin" />
					    <select name="type" class="form-control"><option value="Credit">Kredi</option><option value="Akts">AKTS</option></select>
					    <button class="btn btn-default btn-xs">Hesapla</button>   
					</form:form>
		        </div>
	        </div>
        </div>
	</div>
	<div class="col-lg-1"></div>
	<!-- Forms -->
	<div class="col-lg-10">
		<div class="row">
	        <c:if test="${not empty uni}">
	        <div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                    <h3 class="panel-title text-center">Öğrenci bilgisi<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
		<c:if test="${role.authority == 'ROLE_ADMIN' || role.authority == 'ROLE_MANAGER' }"><c:if test="${uni != 'Yıldız Teknik Üniversitesi'}">
	<a class='btn btn-info btn-xs' href="word?id=${id}"><span class="glyphicon glyphicon-edit"></span> Rapor al</a>
	</c:if>
	<c:if test="${uni == 'Yıldız Teknik Üniversitesi'}">
	<a class='btn btn-info btn-xs' href="word2?id=${id}"><span class="glyphicon glyphicon-edit"></span> Rapor al</a>
	</c:if></c:if></c:forEach></h3>
                </div>
              </div>
              <div class="panel-body">
		        <table class="table table-striped table-bordered table-list">
		        	<thead>
						<tr class="active">
							<th class="col-md-2 text-center">Üniversite</th>
							<th class="col-md-2 text-center">Bölüm</th>
							<th class="col-md-1 text-center">Numara</th>
							<th class="col-md-2 text-center">Ad</th>
							<th class="col-md-1 text-center">Soyad</th>
							<th class="col-md-1 text-center">İntibak Yılı<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
		<c:if test="${role.authority == 'ROLE_ADMIN' || role.authority == 'ROLE_MANAGER' }"><a href="#" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#newStu" onclick="$('#newStu').show();"><b>+</b> Hesapla</a></c:if></c:forEach></th>
							<th class="col-md-1 text-center">Kayıt Yılı</th>
							<th class="col-md-1 text-center">Danışmanı</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-md-2 text-center">${uni}</td>
							<td class="col-md-2 text-center">${dept}</td>
							<td class="col-md-1 text-center">${no}</td>
							<td class="col-md-2 text-center">${name}</td>
							<td class="col-md-1 text-center">${surname}</td>
							<td class="col-md-1 text-center">${adpScore}</td>
							<td class="col-md-1 text-center">${recordYear}</td>
							<td class="col-md-1 text-center">${advisor.username}</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_ADMIN' || role.authority == 'ROLE_MANAGER' }">
				<div class="panel panel-primary panel-table">
              
              <div class="panel-body">
						<form:form action="saveStudentLesson" class="form-inline" method="POST" modelAttribute="studentLessonForm">
							<div class="form-group col-lg-8">
								<input id="id" name="id" type="hidden" value=""/>
								
								<input id="studentId" name="studentId" type="hidden" value="${id}"/>
								
								<label class="control-label" style="margin-left: 10px;">Alınan Ders</label>
											 			
						   		<select id="takingLessonId" class="form-control" name="takingLessonId" style="width: 31%;" ></select>

								<label class="control-label" style="margin-left: 10px;">Sayılan Ders</label>
											 			
						   		<select id="substituteLessonId" class="form-control" name="substituteLessonId" style="width: 31%;" ></select>
						   		
						   		<label class="control-label" style="margin-left: 10px;">Not</label>
						   		
						   		<input id="orgMark" class="form-control" style="width: 5%;" name="orgMark" type="text" value="" readonly/>
						   		
						   		<input id="convMark" name="convMark" type="hidden" value=""/>
						   		
						   		<button type="submit" class="btn btn-default " style="margin-left: 30px;" value="Ekle" >Ekle</button>
									        
						        <c:if test="${not empty message}">
								   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message}
								   </div>
								</c:if> 
					        </div>
				        </form:form>
				        <form:form action="setToStudent?id=${id }" class="form-inline" method="POST" modelAttribute="userForm">
							<div class="form-group col-lg-4">
								<label class="control-label" style="margin-left: 10px;">Atanan Kullanıcı</label>
				        		<select id="studentId" class="form-control" name="studentId" >
				        			<option id="" value="">Atama yapma.</option>
   									<c:forEach items="${students }" var="data">
   										<c:choose>
    										<c:when test="${id == data.studentId }">
        										<option id="${data.id }" value="${data.id }" selected>${data.username }</option>
        									</c:when>
        									<c:otherwise>
        										<option id="${data.id }" value="${data.id }">${data.username }</option>
        									</c:otherwise>
        								</c:choose>
        							</c:forEach>
        						</select>
        						<button type="submit" class="btn btn-default " style="margin-left: 30px;" value="Ekle" >Ekle</button>
				        	</div>
				        </form:form>
		        </div>
			</div>
			</c:if>
			</c:forEach>
        </div>
	</div>
	<c:forEach var="i" begin="1" end="8">
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-2">
                    <h3 class="panel-title">${i}.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-10 text-right">
                    <h5 class="sub-header">${uni} - ${dept} / Alınan Dersler</h5>
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
							<th>Lab</th>
							<th>Başarı notu</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lessons}" var="info">
							<c:if test="${info.subTerm == i}">
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
	<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_ADMIN' || role.authority == 'ROLE_MANAGER' }">
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">${i}.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-6 text-right">
                    <h5 class="sub-header">(YTÜ) Sayılan Dersler</h5>
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
							<th>Eylem</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lessons}" var="info">
							<c:if test="${info.subTerm == i}">
								<tr>
								 	<td> ${info.subCode}  </td>
								   	<td> ${info.subName}  </td>
								  	<td> ${info.subLang}  </td>
								   	<td> ${info.subCredit}  </td>
								   	<td> ${info.subAkts}  </td>
								   	<form:form action="updateStudentLesson" class="form-inline" method="POST" modelAttribute="studentLessonForm">
								   	<td><input id="convMark" name="convMark" class="input-sm" style="width: 60px;" value="${info.subMark }" /></td>
								   	<input id="id" name="id" type="hidden" value="${info.stuLesId }"/>
								   	<td> 
								   		<a class='btn btn-danger btn-xs' href="deleteStudentLesson?id=${info.stuLesId}&studentId=${id}" data-toggle="confirmation" data-title="Bağlantılı tüm bilgiler bu işlemle birlikte silinecek. Emin misiniz?"><span class="glyphicon glyphicon-edit"></span> Sil</a>
								   		<button type="submit" class="btn btn-info btn-xs" value="Ekle" >Not güncelle</button>
								   	</td>
								   	</form:form>
								</tr>
							</c:if>
						 </c:forEach>
					</tbody>
				</table>				
			</div>
			</div>
	</div>
	</c:if>
	</c:forEach>
	
	<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_USER' }">
	<div class="col-lg-6">
		<div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">${i }.Yarıyıl</h3>
                  </div>
                  <div class="col col-xs-6 text-right">
                    <h5 class="sub-header">(YTÜ) Sayılan Dersler</h5>
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
							<c:if test="${info.subTerm == i}">
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
	</c:if>
	</c:forEach>
	</c:forEach>
	<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
		<c:if test="${role.authority == 'ROLE_USER' && not empty advisor.email }">
			<!-- Mail Form -->
			<form:form action="sendMail" class="form-inline" method="POST" modelAttribute="mailForm">
				<div class="col-lg-3"></div>
				<div class="form-group well">
					
					<input id="studentId" name="studentId" type="hidden" value="${id}"/>
					
					<label class="control-label" style="margin-left: 10px;">Kime</label>
								 			
	   				<input name="to" id="${advisor.email}" value="${advisor.email}" placeholder="${advisor.username}" readonly/>

					<label class="control-label" style="margin-left: 10px;">Konu</label>
								 			
			   		<select id="subject" class="form-control" name="subject" >
			   			<option value="İtiraz">İtiraz</option>
			   			<option value="İstek">İstek</option>
			   			<option value="Öneri">Öneri</option>
			   			<option value="Diğer">Diğer</option>
			   		</select>
			   		
			   		<label class="control-label" style="margin-left: 10px;">İçerik</label>
			   		
			   		<textarea id="text" class="form-control" name="text" rows="4" cols="55" style="margin-top: 20px;"></textarea>
			   		
			   		<button type="submit" class="btn btn-default " style="margin-left: 10px;" value="Ekle" >Gönder</button>
						        
			        <c:if test="${not empty message}">
					   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message}
					   </div>
					</c:if> 
		        </div>
	        </form:form>
		</c:if>
	</c:forEach>
	</c:if>       
</body>
<script type="text/javascript">
$('[data-toggle=confirmation]').confirmation({
  rootSelector: '[data-toggle=confirmation]',
  // other options
});
</script>
</html>
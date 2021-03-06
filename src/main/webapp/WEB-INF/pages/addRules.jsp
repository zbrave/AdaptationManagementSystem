<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/js/bootstrap-confirmation.js" var="bootstrapConfirmationJS" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<script src="${bootstrapConfirmationJS}"></script>
	<script src="${jqueryJS}"></script>
	
	<link href="${amsCSS}" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <script type='text/javascript' src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
  
   <script type="text/javascript">
$(document).ready(function(){
	$.get("${pageContext.request.contextPath}/getUni", null, function (data) {
	        $("#uniId").html(data);
	        $("#uniId2").html(data);
	        $("#uniId3").html(data);
	        $("#uniId4").html(data);
	    });
	$.get("${pageContext.request.contextPath}/getSubstituteLesson", null, function (data) {
        $("#substituteLessonId").html(data);
    });
});</script>
 <script type="text/javascript">
$(document).ready(function(){
	$('#uniId4').on('change',function(){
		$.get("${pageContext.request.contextPath}/getMarks?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#marks > tbody:last-child').html(data);
	    });
	});
	$('#curriculumId').on('change',function(){
		$.get("${pageContext.request.contextPath}/getSubstituteLessons?curriculumId="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#subLes > tbody:last-child').html(data);
	    });
	});
	$('#deptId').on('change',function(){
		$.get("${pageContext.request.contextPath}/getTakingLessons?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#takLes > tbody:last-child').html(data);
	    });
	});
	$('#uniId').on('change',function(){
		$.get("${pageContext.request.contextPath}/getDepts?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#depts > tbody:last-child').html(data);
	    });
	});
	$('#uniId2').on('change',function(){
		$.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#deptId").html(data);
	    });
	});
	$('#uniId3').on('change',function(){
		$.get("${pageContext.request.contextPath}/getDept?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#deptId2").html(data);
	    });
	});
	$('#curriculumId').on('change',function(){
		$.get("${pageContext.request.contextPath}/getSubstituteLessonCur?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
			$("#conditionId").html(data);
	    });
		$.get("${pageContext.request.contextPath}/getPoolLesson?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
			$("#base3").html(data);
	    });
	});
});</script>
<script type="text/javascript">

function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}
function doActive() {
		var x = $("#curriculumId").children("option").filter(":selected").attr("id");
		window.location.href = '${pageContext.request.contextPath}/doActive?id='+x;
}
function delCurriculum() {
	var x = $("#curriculumId").children("option").filter(":selected").attr("id");
	window.location.href = '${pageContext.request.contextPath}/deleteCurriculum?id='+x;
}

</script>

<script type="text/javascript">
$(document).ready(function(){
	$('#deptId2').on('change',function(){
		$.get("${pageContext.request.contextPath}/getTakingLesson?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#takingLessonId").html(data);
	    });
	    $.get("${pageContext.request.contextPath}/getRules?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#rules > tbody:last-child').html(data);
	    });
	});
	$.get("${pageContext.request.contextPath}/getUnis", null, function (data) {
	        $('#unis > tbody:last-child').html(data);
	    });
});</script>

<title>IYS - Kural ekleme</title>
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
            <div class="panel-heading">Yeni Müfredat Ekle<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button></div>
	            <div class="panel-body">
	            <form:form action="saveCurriculum" method="POST" modelAttribute="curriculumForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>
						
						<label class="control-label">Müfredat Yılı</label>
									 			
						<input id="year" name="year" class="form-control" value="" required="required"/>
				    
				    	<label class="control-label">Aktif yıl yapılsın mı ?</label>
				                       
				        <select id="enabled" class="form-control" name="enabled" >
				        	<option value="1" selected>Evet</option>
				        	<option value="0" selected>Hayır</option>
				        </select>
				          
				        
				        <br/>
				        <button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button> 
			        </div>
		        </form:form>
		        </div>
	        </div>
        </div>
	</div>
	
	<div class="col-lg-1"></div>
	<!-- Forms -->
	<div class="col-lg-4">
		<div class="row">
            <div class="panel panel-default">
	            <div class="panel-body">
	            <form:form action="saveRules" method="POST" modelAttribute="rulesForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>

						<label class="control-label">Üniversite Adı</label>
									 			
				   		<select id="uniId3" class="form-control" name="uniId3" required="required"></select>
				    
				    	<label class="control-label">Bölüm Adı</label>
				                       
				        <select id="deptId2" class="form-control" name="deptId2" required="required"></select>
				          
				        <label class="control-label">Alınan Ders Adı</label>
				                       
				        <select id="takingLessonId" class="form-control" name="takingLessonId" required="required"></select>
				        
				        <label class="control-label">Sayılan Ders Adı</label>
				                       
				        <select id="substituteLessonId" class="form-control" name="substituteLessonId" required="required"></select>
				        
				        <button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button>
							        
				        <c:if test="${not empty message5}">
						   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
						   </div>
						</c:if> 
			        </div>
		        </form:form>

		        <div class="row custyle">
				    <table id="rules" class="table table-striped custab" style="background-color: #FFF;">
				    <thead>
				        <tr>
				            <th>ID</th>
				            <th>Alınan Ders</th>
				            <th>Sayılan Ders</th>
				            <th>Eylem</th>
				        </tr>
				    </thead>
				   	<tbody>
				   		
				   	</tbody>
				    </table>
			    </div>
		        </div>
	        </div>
        </div>
	</div>
	<div class="col-lg-1"></div>
	<!-- Uni Form(Right Panel) -->
	<div class="col-lg-5">
		<div class="row">
            <div class="panel with-nav-tabs panel-default">
                <div class="panel-heading">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#uniTab" data-toggle="tab">Üniversite</a></li>
                            <li><a href="#deptTab" data-toggle="tab">Bölüm</a></li>
                            <li><a href="#takingLessonTab" data-toggle="tab">Alınan Ders</a></li>
                            <li><a href="#substituteLessonTab" data-toggle="tab">Sayılan Ders</a></li>
                            <li><a href="#markTab" data-toggle="tab">Not Sistemi</a></li>
                        </ul>
                </div>
                <div class="panel-body">
                    <div class="tab-content">
                        <div class="tab-pane fade in active" id="uniTab">
							<form:form action="saveUni" method="POST" modelAttribute="uniForm">
							  	<div class="form-group">
							   		<input id="id" name="id" type="hidden" value=""/>
					       			
						        	<label class="control-label">Üniversite Adı</label>
					
					              	<input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important" required="required"/>
					
					    	       	<button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button>
					           		
					           		<c:if test="${not empty message1}">
									   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message1}
									   </div>
									</c:if> 
					
					  			</div>
							</form:form>

							<div class="row custyle">
							    <table id="unis" class="table table-striped custab" style="background-color: #FFF;">
							    <thead>
							        <tr>
							            <th>ID</th>
							            <th>Üniversite Adı</th>
							            <th>Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
							   		
							   	</tbody>
							    </table>
						    </div>
						</div>
                        <div class="tab-pane fade" id="deptTab">
							<form:form action="saveDept" method="POST" modelAttribute="deptForm">
								<div class="form-group">
					 				
					 				<input id="id" name="id" type="hidden" value=""/>
					
					 				<label class="control-label">Üniversite Adı</label>
					
							       	<select id="uniId" class="form-control" name="uniId" required="required"></select>
					
							       	<label class="control-label">Bölüm Adı</label>
					
					               	<input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important" required="required"/>
					               	
					               	<button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button>
							        
							        <c:if test="${not empty message2}">
									   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message2}
									   </div>
									</c:if>   
							           
					       		</div>
					   		</form:form>

					   		<div class="row custyle">
							    <table id="depts" class="table table-striped custab" style="background-color: #FFF;">
							    <thead>
							        <tr>
							            <th>ID</th>
							            <th>Bölüm Adı</th>
							            <th>Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
							   		
							   	</tbody>
							    </table>
						    </div>
						</div>
                        <div class="tab-pane fade" id="takingLessonTab">
							<form:form action="saveTakingLesson" method="POST" modelAttribute="takingLessonForm">
   			
					   			<input id="id" name="id" type="hidden" value=""/>
					 			
					 			<label class="control-label">Üniversite Adı</label>
					 			
					       		<select id="uniId2" class="form-control" name="uniId2" required="required"></select>
							    
							    <label class="control-label">Bölüm Adı</label>
							                       
					            <select id="deptId" class="form-control" name="deptId" required="required"></select>
					            
					            <label class="control-label">Ders Adı</label>
					                       
					            <input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Ders Kodu</label>
					                       
					            <input id="code" class="form-control input-sm" name="code" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Ders Dili</label>
					                       
					            <input id="lang" class="form-control input-sm" name="lang" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Kredi</label>
					            
					            <input id="credit" class="form-control input-sm" name="credit" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">AKTS</label>
					            
					            <input id="akts" class="form-control input-sm" name="akts" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Lab Saati</label>
					            
					            <input id="lab" class="form-control input-sm" name="lab" type="text" value="" style="height: 35px!important" required="required" />
					 			
					 			<label class="control-label">Ders Dönemi</label>
					                       
					            <input id="term" class="form-control input-sm" name="term" type="text" value="" style="height: 35px!important" required="required"/>
					 			
					           	<button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button>
							        
						        <c:if test="${not empty message3}">
								   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message3}
								   </div>
								</c:if>
					   		</form:form>

					   		<div class="row custyle">
							    <table id="takLes" class="table table-striped custab" style="background-color: #FFF;">
							    <thead>
							        <tr>
							            <th>ID</th>
							            <th>Adı</th>
							            <th>Kodu</th>
							            <th>Dili</th>
							            <th>Kredi</th>
							            <th>Lab.</th>
							            <th>AKTS</th>
							            <th>Dönem</th>
							            <th>Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
							   		
							   	</tbody>
							    </table>
						    </div>
						</div>
                        <div class="tab-pane fade" id="substituteLessonTab">
                        <label class="control-label">Müfredat yılı</label>
                        <button onclick="doActive()" class="btn btn-warning btn-xs"><span class="glyphicon glyphicon-ok"></span> Aktif yıl yap</button>
                        <button onclick="delCurriculum()" class="btn btn-danger btn-xs" data-toggle="confirmation" data-btn-ok-label="Evet" data-btn-cancel-label="Hayır" data-title="Bağlantılı tüm bilgiler bu işlemle birlikte silinecek. Emin misiniz?"><span class="glyphicon glyphicon-remove"></span> Müfredat yılını sil</button>
                        <a href="#" class="btn btn-primary btn-xs pull-right" data-toggle="modal" data-target="#newStu" onclick="$('#newStu').show();"><b>+</b> Yeni Müfredat Ekle</a>
                        	<form:form action="saveSubstituteLesson" method="POST" modelAttribute="substituteLessonForm">
					   			
					   			<input id="id" name="id" type="hidden" value=""/>
					            
					            <select id="curriculumId" name="curriculumId" class="form-control">
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
			                    </select>
					            
					            <label class="control-label">Ders Adı</label>
					                       
					            <input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Ders Kodu</label>
					                       
					            <input id="code" class="form-control input-sm" name="code" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Ders Dili</label>
					                       
					            <input id="lang" class="form-control input-sm" name="lang" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Kredi</label>
					            
					            <input id="credit" class="form-control input-sm" name="credit" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">AKTS</label>
					            
					            <input id="akts" class="form-control input-sm" name="akts" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Lab Saati</label>
					            
					            <input id="lab" class="form-control input-sm" name="lab" type="text" value="" style="height: 35px!important" required="required"/>
					 			
					 			<label class="control-label">Ders Dönemi</label>
					                       
					            <input id="term2" class="form-control input-sm" name="term" type="text" value="" style="height: 35px!important" required="required"/>
					            
					            <label class="control-label">Ders şartı</label>
					            
					            <select id="conditionId" name="conditionId" class="form-control">
		                            <c:forEach items="${subLes }" var="data" >
		                        		<option id="${data.id }" value="${data.id }" >${data.name }</option>
			                        </c:forEach>
			                    </select>
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

					   		<div class="row custyle">
							    <table id="subLes" class="table table-striped custab" style="background-color: #FFF;">
							    <thead>
							        <tr>
							            <th>ID</th>
							            <th width="15%">Adı</th>
							            <th>Kodu</th>
							            <th>Dili</th>
							            <th>Kredi</th>
							            <th>Lab.</th>
							            <th>AKTS</th>
							            <th>Dönem</th>
							            <th width="15%">Önkoşul</th>
							            <th>Tip</th>
							            <th>Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
						   			<c:forEach items="${subLes2 }" var="data">
						   				<tr>
						   					<td>${data.id }</td>
						   					<td>${data.name }</td>
						   					<td>${data.code }</td>
						   					<td>${data.lang }</td>
						   					<td>${data.credit }</td>
						   					<td>${data.lab }</td>
						   					<td>${data.akts }</td>
						   					<td>${data.term }</td>
						   					<td><c:forEach items="${subLes }" var="data2"><c:if test="${data2.id == data.conditionId }">${data2.name }</c:if></c:forEach></td>
						   					<td>${data.base }</td>
						   					<td><a href="editSubstituteLesson?id=${data.id }" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span> Değiştir</a>
						   					<a href="deleteSubstituteLesson?id=${data.id }" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Sil</a></td>
					   					</tr>
						   			</c:forEach>
							   	</tbody>
							    </table>
						    </div>
						    <tag:paginate max="15" offset="${offset}" count="${count}"
						uri="addRules2" next="&raquo;" previous="&laquo;" />
                        </div>
                        <div class="tab-pane fade" id="markTab">
							<form:form action="saveMark" method="POST" modelAttribute="markForm">
							  	<div class="form-group">
							   		<input id="id" name="id" type="hidden" value=""/>
					       			
						        	<label class="control-label">Üniversite Adı</label>
									
									<select id="uniId4" class="form-control" name="uniId" ></select>

									<label class="control-label">Harf Notu</label>

					              	<input id="mark" class="form-control input-sm" name="mark" type="text" value="" style="height: 35px!important" />
									
									<label class="control-label">Puanı</label>

									<input id="value" class="form-control input-sm" name="value" type="text" value="" style="height: 35px!important" />

					    	       	<button type="submit" class="btn btn-primary" value="Ekle" >Ekle</button>
					           		
					           		<c:if test="${not empty message1}">
									   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message6}
									   </div>
									</c:if> 
					
					  			</div>
							</form:form>

							<div class="row custyle">
							    <table id="marks" class="table table-striped custab" style="background-color: #FFF;">
							    <thead>
							        <tr>
							            <th>ID</th>
							            <th>Harf notu</th>
							            <th>Puanı</th>
							            <th>Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
							   		
							   	</tbody>
							    </table>
						    </div>
						</div>
                    </div>
                </div>
        	</div>
    	</div>		
	</div>
	<script type="text/javascript">
	$( document ).ready(function() {
    

  	var url = document.location.toString();
	if (url.match('#')) {
	    $('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
	} //add a suffix

	// Change hash for page-reload
	$('.nav-tabs a').on('shown.bs.tab', function (e) {
	    window.location.hash = e.target.hash;
	});
	var val = GetURLParameter('id');
	var val2 = GetURLParameter('id2');   // get params from URL
	setTimeout(function() {
		document.querySelector('#uniId [id="' + val + '"]').selected = true;
		document.querySelector('#uniId2 [id="' + val + '"]').selected = true;
		document.querySelector('#uniId3 [id="' + val + '"]').selected = true;
		document.querySelector('#uniId4 [id="' + val + '"]').selected = true;
		$.get("${pageContext.request.contextPath}/getDept?id="+ $("#uniId3").children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#deptId2").html(data);
	    });
	    $.get("${pageContext.request.contextPath}/getDept?id="+ $("#uniId2").children("option").filter(":selected").attr("id"), null, function (data) {
	        $("#deptId").html(data);
	    });
	    $.get("${pageContext.request.contextPath}/getDepts?id="+ $("#uniId").children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#depts > tbody:last-child').html(data);
	    });
	    $.get("${pageContext.request.contextPath}/getMarks?id="+ $("#uniId4").children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#marks > tbody:last-child').html(data);
	    });
		setTimeout(function() {
			document.querySelector('#deptId2 [id="' + val2 + '"]').selected = true;
			document.querySelector('#deptId [id="' + val2 + '"]').selected = true;
			$.get("${pageContext.request.contextPath}/getTakingLessons?id="+ $("#deptId").children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#takLes > tbody:last-child').html(data);
	        $.get("${pageContext.request.contextPath}/getTakingLesson?id="+ $("#deptId2").children("option").filter(":selected").attr("id"), null, function (data) {
		        $("#takingLessonId").html(data);
		    });
		    $.get("${pageContext.request.contextPath}/getRules?id="+ $("#deptId2").children("option").filter(":selected").attr("id"), null, function (data) {
		        $('#rules > tbody:last-child').html(data);
		    });
	    });
		}, 150);
	}, 150);
});  </script>
<script type="text/javascript">
	function setPool(id) {
		if (id == 1) {
			document.getElementById('base2').setAttribute('disabled', 'disabled');
			document.getElementById('base3').setAttribute('disabled', 'disabled');
			document.getElementById('term2').removeAttribute('readonly');
			document.getElementById('base2').removeAttribute('required');
		}
		if (id == 2) {
			document.getElementById('base2').removeAttribute('disabled');
			document.getElementById('base2').setAttribute('required', 'required');
			document.getElementById('base3').setAttribute('disabled', 'disabled');
			document.getElementById('base').setAttribute('disabled', 'disabled');
			document.getElementById('term2').removeAttribute('readonly');
		}
		if (id == 3) {
			document.getElementById('base3').removeAttribute('disabled');
			document.getElementById('base2').removeAttribute('required');
			document.getElementById('base2').setAttribute('disabled', 'disabled');
			document.getElementById('base').setAttribute('disabled', 'disabled');
			document.getElementById('term2').value=0;
			document.getElementById('term2').setAttribute('readonly', 'readonly');
		}
}
</script>
</body>
<script type="text/javascript">
$('[data-toggle=confirmation]').confirmation({
  rootSelector: '[data-toggle=confirmation]',
  // other options
});
</script>
</html>
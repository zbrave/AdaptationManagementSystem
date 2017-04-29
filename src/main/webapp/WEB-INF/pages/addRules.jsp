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
});</script>
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
	$.get("${pageContext.request.contextPath}/getSubstituteLessons", null, function (data) {
	        $('#subLes > tbody:last-child').html(data);
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
	<div class="col-lg-1"></div>
	<!-- Forms -->
	<div class="col-lg-5">
		<div class="row">
            <div class="panel panel-default">
	            <div class="panel-body">
	            <form:form action="saveRules" method="POST" modelAttribute="rulesForm">
					<div class="form-group">
						<input id="id" name="id" type="hidden" value=""/>
						
						<label class="control-label">Üniversite Adı</label>
									 			
				   		<select id="uniId3" class="form-control" name="uniId3" ></select>
				    
				    	<label class="control-label">Bölüm Adı</label>
				                       
				        <select id="deptId2" class="form-control" name="deptId2" ></select>
				          
				        <label class="control-label">Alınan Ders Adı</label>
				                       
				        <select id="takingLessonId" class="form-control" name="takingLessonId" ></select>
				        
				        <label class="control-label">Sayılan Ders Adı</label>
				                       
				        <select id="substituteLessonId" class="form-control" name="substituteLessonId" ></select>
				        
				        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
							        
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
				            <th class="text-center">Eylem</th>
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
	<div class="col-lg-4">
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
					
					              	<input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important" />
					
					    	       	<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
					           		
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
							            <th class="text-center">Eylem</th>
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
					
							       	<select id="uniId" class="form-control" name="uniId" ></select>
					
							       	<label class="control-label">Bölüm Adı</label>
					
					               	<input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important"/>
					               	
					               	<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
							        
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
							            <th class="text-center">Eylem</th>
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
					 			
					       		<select id="uniId2" class="form-control" name="uniId2" ></select>
							    
							    <label class="control-label">Bölüm Adı</label>
							                       
					            <select id="deptId" class="form-control" name="deptId" ></select>
					            
					            <label class="control-label">Ders Adı</label>
					                       
					            <input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">Ders Kodu</label>
					                       
					            <input id="code" class="form-control input-sm" name="code" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">Ders Dili</label>
					                       
					            <input id="lang" class="form-control input-sm" name="lang" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">Kredi</label>
					            
					            <input id="credit" class="form-control input-sm" name="credit" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">AKTS</label>
					            
					            <input id="akts" class="form-control input-sm" name="akts" type="text" value="" style="height: 35px!important"/>
					 			
					 			<label class="control-label">Ders Dönemi</label>
					                       
					            <input id="term" class="form-control input-sm" name="term" type="text" value="" style="height: 35px!important"/>
					 			
					           	<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
							        
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
							            <th>AKTS</th>
							            <th>Dönem</th>
							            <th class="text-center">Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
							   		
							   	</tbody>
							    </table>
						    </div>
						</div>
                        <div class="tab-pane fade" id="substituteLessonTab">
                        	<form:form action="saveSubstituteLesson" method="POST" modelAttribute="substituteLessonForm">
					   			
					   			<input id="id" name="id" type="hidden" value=""/>
					            
					            <label class="control-label">Ders Adı</label>
					                       
					            <input id="name" class="form-control input-sm" name="name" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">Ders Kodu</label>
					                       
					            <input id="code" class="form-control input-sm" name="code" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">Ders Dili</label>
					                       
					            <input id="lang" class="form-control input-sm" name="lang" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">Kredi</label>
					            
					            <input id="credit" class="form-control input-sm" name="credit" type="text" value="" style="height: 35px!important"/>
					            
					            <label class="control-label">AKTS</label>
					            
					            <input id="akts" class="form-control input-sm" name="akts" type="text" value="" style="height: 35px!important"/>
					 			
					 			<label class="control-label">Ders Dönemi</label>
					                       
					            <input id="term" class="form-control input-sm" name="term" type="text" value="" style="height: 35px!important"/>
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
							            <th>Adı</th>
							            <th>Kodu</th>
							            <th>Dili</th>
							            <th>Kredi</th>
							            <th>AKTS</th>
							            <th>Dönem</th>
							            <th class="text-center">Eylem</th>
							        </tr>
							    </thead>
							   	<tbody>
							   		
							   	</tbody>
							    </table>
						    </div>
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

					    	       	<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
					           		
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
							            <th class="text-center">Eylem</th>
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
  	var url = document.location.toString();
	if (url.match('#')) {
	    $('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
	} //add a suffix

	// Change hash for page-reload
	$('.nav-tabs a').on('shown.bs.tab', function (e) {
	    window.location.hash = e.target.hash;
	})
  </script>
</body>
</html>
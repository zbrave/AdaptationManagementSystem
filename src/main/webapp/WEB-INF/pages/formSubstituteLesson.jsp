<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
  src="https://code.jquery.com/jquery-3.1.1.js"
  integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
  crossorigin="anonymous"></script>
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
 
   <h3>${formTitle}</h3>
 
   <form:form action="saveSubstituteLesson" method="POST"
       modelAttribute="substituteLessonForm">
 
       <form:hidden path="id" />
 
       <table>
       		<tr>
               <td>Uni-id</td>
               <td><select id="uni"><c:forEach items="${uniMap}" var="uni">
                       <option id="${uni.key}" value="${uni.key}" label="${uni.value}" />
                   </c:forEach></select></td>
               <td><errors  class="error-message" /></td>
           </tr>
           <tr>
               <td>Dept-id</td>
               <td><select id="dept"><c:forEach items="${deptMap}" var="dept">
                       <option id="${dept.key}" value="${dept.key}" label="${dept.value}" />
                   </c:forEach></select></td>
               <td><errors  class="error-message" /></td>
           </tr>
           <tr>
               <td>TakingLesson</td>
               <td><form:select path="takingLessonId">
                       <form:options items="${takingLessonMap}" />
                   </form:select></td>
               <td><form:errors path="takingLessonId" class="error-message" /></td>
           </tr>
           <tr>
               <td>Code</td>
               <td><form:input path="code" /></td>
               <td><form:errors path="code"
                       class="error-message" /></td>      
           </tr>
           <tr>
               <td>Credit</td>
               <td><form:input path="credit" /></td>
               <td><form:errors path="credit"
                       class="error-message" /></td>      
           </tr>
           <tr>
               <td>AKTS</td>
               <td><form:input path="akts" /></td>
               <td><form:errors path="akts"
                       class="error-message" /></td>      
           </tr>
 
           <tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" />
                  <a href="${pageContext.request.contextPath}/substituteLessonList">Cancel</a>
               </td>
               <td>&nbsp;</td>
           </tr>
       </table>
   </form:form>
 
</body>
</html>
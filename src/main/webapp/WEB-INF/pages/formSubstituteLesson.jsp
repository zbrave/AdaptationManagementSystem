<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
               <td>TakingLessonID</td>
               <td><form:input path="takingLessonId" /></td>
               <td><form:errors path="takingLessonId"
                       class="error-message" /></td>      
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
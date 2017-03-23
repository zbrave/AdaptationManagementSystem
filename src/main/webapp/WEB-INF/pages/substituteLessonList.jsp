<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Applicant List</title>
<style>
   table  {
       margin-top: 10px;
       border: solid black 1px;
   }
   table  td {
        padding: 5px;
   }
   .message  {
        font-size:90%;
        color:blue;
        font-style:italic;
        margin-top:30px;
   }
</style>
</head>
<body>
 
 
 
<a href="${pageContext.request.contextPath}/createSubstituteLesson">Create SubstituteLesson</a>
 
<br/>
 
 
<table border="1">
 <tr>
 	<th>Name</th>
   <th>Code</th>
   <th>Language</th>
   <th>Credit</th>
   <th>AKTS</th>
   <th>Term</th>
   <th>Delete</th>
   <th>Edit</th>
 </tr>
 <c:forEach items="${substituteLessonInfos}" var="info">
 <tr>
 	<td> ${info.name}  </td>
   <td> ${info.code}  </td>
   <td> ${info.lang}  </td>
   <td> ${info.credit}  </td>
   <td> ${info.term}  </td>
   <td> ${info.akts}  </td>
   <td> <a href="deleteSubstituteLesson?id=${info.id}">Delete</a> </td>
   <td> <a href="editSubstituteLesson?id=${info.id}">Edit</a> </td>
 </tr>   
 
 </c:forEach>
</table>
<c:if test="${not empty message}">
  
   <div class="message">${message}</div>
</c:if>
 
 
 
</body>
</html>
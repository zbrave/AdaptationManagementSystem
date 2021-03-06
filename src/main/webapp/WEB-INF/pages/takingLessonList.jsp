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
 
 
 
<a href="${pageContext.request.contextPath}/createTakingLesson">Create TakingLesson</a>
 
<br/>
 
 
<table border="1">
 <tr>
 <td> Name  </td>
 	<th>Dept Id</th>
   <th>Code</th>
   <th>Credit</th>
   <th>AKTS</th>
   <th>Delete</th>
   <th>Edit</th>
 </tr>
 <c:forEach items="${takingLessonInfos}" var="info">
 <tr>
 <td> ${info.name}  </td>
 	<td> ${info.deptId}  </td>
   <td> ${info.code}  </td>
   <td> ${info.credit}  </td>
   <td> ${info.akts}  </td>
   <td> <a href="deleteTakingLesson?id=${info.id}">Delete</a> </td>
   <td> <a href="editTakingLesson?id=${info.id}">Edit</a> </td>
 </tr>   
 
 </c:forEach>
</table>
<c:if test="${not empty message}">
  
   <div class="message">${message}</div>
</c:if>
 
 
 
</body>
</html>
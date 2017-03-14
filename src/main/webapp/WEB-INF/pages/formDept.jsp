<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Dept</title>
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
 
   <form:form action="saveDept" method="POST"
       modelAttribute="deptForm">
 
       <form:hidden path="id" />
 
       <table>
           <tr>
               <td>Uni-id</td>
               <td><form:input path="uniId" /></td>
               <td><form:errors path="uniId"
                       class="error-message" /></td>      
           </tr>
           <tr>
               <td>Name</td>
               <td><form:input path="name" /></td>
               <td><form:errors path="name"
                       class="error-message" /></td>      
           </tr>
 
           <tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" />
                  <a href="${pageContext.request.contextPath}/deptList">Cancel</a>
               </td>
               <td>&nbsp;</td>
           </tr>
       </table>
   </form:form>
 
</body>
</html>
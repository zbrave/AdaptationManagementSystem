

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Spring MVC PDF View Example</title>
</head>
<body>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>UniId</th>
    </tr>
    <c:forEach var="c" items="${courses}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>${c.uniId}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
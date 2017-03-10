<%@page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<!-- let's add tag srping:url -->
<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${bootstrapCSS}" rel="stylesheet" />
<script src="${bootstrapJS}"></script>
<title>${title}</title>
</head>
<body>
	<jsp:include page="_menu.jsp" />
  <h1>Message : ${message}</h1>
  
</body>
</html>
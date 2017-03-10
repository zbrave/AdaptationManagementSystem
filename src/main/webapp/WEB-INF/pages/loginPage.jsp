<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<title>Login</title>	
</head>
<body>
   <jsp:include page="_menu.jsp" />
    
    
   <h1>Login</h1>
     
     <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
         <div style="color:red;margin:10px 0px;">
          
                Login Failed!!!<br />
                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>
       
   	<h3>Enter user name and password:</h3>  
     
   	<form name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
		<div class="form-group">  
        	<label for="exampleInputEmail1">Username</label>
            <input type='text' class="form-control" id="exampleInputEmail1" placeholder="username" name='username' value=''>
        </div>
        <div class="form-group"> 
        	<label for="exampleInputPassword1">Password</label>
            <input type='password' class="form-control" id="exampleInputPassword1" placeholder="Password" name='password' />
        </div>
        
            <input name="submit" type="submit" value="submit" />
         
      	
  </form>
</body>
</html>
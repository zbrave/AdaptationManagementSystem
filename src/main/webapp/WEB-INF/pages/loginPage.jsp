<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/login.css" var="loginCSS" />
	<spring:url value="/resources/others/login.js" var="loginJS" />
	<spring:url value="/resources/others/navbar.css" var="navbarCSS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${loginCSS}" rel="stylesheet" />
	<script src="${loginJS}"></script>
	<link href="${navbarCSS}" rel="stylesheet" />
	<title>Login</title>	
</head>
<body>
   <jsp:include page="_menu.jsp" />
    
     
      
  <div class="container">
  
  <div class="row" id="pwd-container">
    <div class="col-md-4"></div>
    
    <div class="col-md-4">
      <section class="login-form">
        <form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="login" method='POST'>
          <img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" class="img-responsive" alt="" style="width:50%"/>
          <h3 class="text-center">Yildiz Teknik Universitesi</h3>
		  <h4 class="text-center">Intibak Yonetim Sistemi</h4>
		  <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
        <div class="alert alert-danger" role="alert">
  			<a href="#" class="alert-link">Login Failed!!!<br />
                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
		</div>
    </c:if>
          <input type='text' class="form-control" id="username" placeholder="Username" name='username' value=''>          
          <input type='password' class="form-control" id="password" placeholder="Password" name='password' />
                    
          
          <div class="pwstrength_viewport_progress"></div>
          
          
          <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Giris Yap</button>
          <div>
            <a href="#">Yeni hesap ac</a> yada <a href="#">Sifremi unuttum</a>
          </div>
          
        </form>
        
        <div class="form-links">
          <a href="#">www.website.com</a>
        </div>
      </section>  
      </div>
      
      <div class="col-md-4"></div>
      

  </div>
  </div>
</body>
</html>
<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/others/ams.js" var="amsJS" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="${amsJS}"></script>
	<title>Login</title>	
</head>
<body>
  <div class="container">
  
  <div class="row" id="loginbox">
    <div class="col-md-4"></div>
    
    <div class="col-md-4">
      <section class="login-form">
        <form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="login" method='POST'>
          <img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" class="img-responsive" alt="" style="width:50%"/>
          <h3 class="text-center">Yıldız Teknik Üniversitesi</h3>
		  <h4 class="text-center">İntibak Yönetim Sistemi</h4>
		  <!-- /login?error=true -->
      <c:if test="${not empty signupMsg}">
         <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${signupMsg}
         </div>
      </c:if>
     <c:if test="${param.error == 'true'}">
        <div class="alert alert-danger" role="alert">
  			<a href="#" class="alert-link">Giriş yapılamadı!<br />
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
		</div>
    </c:if>
          <input type='text' class="form-control" id="username" placeholder="Kullanıcı adı" name='username' value=''>          
          <input type='password' class="form-control" id="password" placeholder="Şifre" name='password' />
                    
          
          
          <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Giriş Yap</button>
          <div>
            <a href="#" onclick="$('#loginbox').hide(); $('#signupbox').show()">Yeni hesap aç</a> yada <a href="" data-toggle="modal" data-target="#passForgot">Şifremi unuttum</a>
          </div>
          
        </form>
        
        <div class="form-links">
          <a href="www.ce.yildiz.edu.tr">YTÜ Bilgisayar Müh. Bölümü Websitesi</a>
        </div>
      </section>  
      </div>
      
      <div class="col-md-4"></div>
      

  </div>
  <!-- Register Form -->
  <div class="row" id="signupbox" style="display:none">
    <div class="col-md-4"></div>
    
    <div class="col-md-4">
      <section class="login-form">
        <form method="post" action="${pageContext.request.contextPath}/saveUser" role="login" method='POST'>
          <img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" class="img-responsive" alt="" style="width:50%"/>
          <h3 class="text-center">Yıldız Teknik Üniversitesi</h3>
		  <h4 class="text-center">İntibak Yönetim Sistemi</h4>
      <h5 class="text-center">Yeni kullanıcı kaydı</h5>
		  <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
        <div class="alert alert-danger" role="alert">
  			<a href="#" class="alert-link">Hata!<br />
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
		</div>
    </c:if>
          <input type='text' class="form-control" id="username" placeholder="Kullanıcı adı" name='username' value='' required/>          
          <input type='text' class="form-control" id="email" placeholder="E-posta adresi" name='email' value='' required/>
          <input type='password' class="form-control" id="password" placeholder="Şifre" name='password' required/>
          <input type='password' class="form-control" id="passwordConf" placeholder="Şifre doğrulama" name='passwordConf' required/>
          <input type='text' class="form-control" id="tel" placeholder="Telefon" name='tel' required/>          
          
          <div class="pwstrength_viewport_progress"></div>
          
          
          <button type="submit" name="go" class="btn btn-lg btn-warning btn-block">Kayıt ol</button>
          <div>
            <a href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Giriş yap</a> yada <a href="" data-toggle="modal" data-target="#passForgot">Şifremi unuttum</a>
          </div>
          
        </form>
        
        <div class="form-links">
          <a href="#">www.mertaydar.com</a>
        </div>
      </section>  
      </div>
      
      
      <!-- Modal -->
<div class="modal fade" id="passForgot" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

  </div>
  </div>
</body>
</html>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/others/ams.js" var="amsJS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="${amsJS}"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">	
	<title>Erişim kısıtlandı!</title>
	<style>
/* Error Page Inline Styles */

/* Layout */
.jumbotron {
  font-size: 21px;
  font-weight: 200;
  line-height: 2.1428571435;
  color: inherit;
  padding: 10px 0px;
}
/* Everything but the jumbotron gets side spacing for mobile-first views */
.masthead, .body-content, {
  padding-left: 15px;
  padding-right: 15px;
}
/* Main marketing message and sign up button */
.jumbotron {
  text-align: center;
  background-color: transparent;
}
.jumbotron .btn {
  font-size: 21px;
  padding: 14px 24px;
}
/* Colors */
.green {color:#5cb85c;}
.orange {color:#f0ad4e;}
.red {color:#d9534f;}
</style>
<script type="text/javascript">
  function loadDomain() {
    var display = document.getElementById("display-domain");
    display.innerHTML = document.domain;
  }
</script>
</head>
<body onload="javascript:loadDomain();">
 <%@include file="navbar.jsp" %>
    <div class="container">
<div class="jumbotron">
  <h1><i class="fa fa-ban red"></i> 403 Erişim kısıtlandı.</h1>
  <p class="lead">Üzgünüz! Bu sayfaya erişim için izniniz yok. <em><span id="display-domain"></span></em>.</p>
    <p><a onclick=javascript:checkSite(); class="btn btn-default btn-lg green">Anasayfaya Dön</a>
      <script type="text/javascript">
          function checkSite(){
            var currentSite = window.location.hostname;
              window.location = "http://" + currentSite+":8080/AMS";
          }
      </script>
    </p>
</div>
</div>
</body>
</html>
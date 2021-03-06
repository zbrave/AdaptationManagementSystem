<%@ page pageEncoding="UTF-8" %>

<script type="text/javascript">
$(document).ready(function(){
	var path = window.location.pathname.split("/").pop();
	$('#' + path).addClass("active");
	if (path.indexOf('list') !== -1) {
		$('#dropdown').addClass("active");
	}
});
</script>

<nav class="navbar">
	<div class="menu">
	    <div class="container-fluid">
			<div class="navbar-header">
				<a href="${pageContext.request.contextPath}">İntibak Yönetim Sistemi</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-right" id="nav">
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li id="welcome"><a href="${pageContext.request.contextPath}/welcome" ><span class="glyphicon glyphicon-home"></span> Anasayfa</a></li>
					</c:if>
					<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_USER' }">
							<li id ="Student"><a href="${pageContext.request.contextPath}/myAdapt"><span class="glyphicon glyphicon-zoom-in"></span> İntibakım</a></li>
						</c:if>
					</c:forEach>
					<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_ADMIN' }">
							 <li id="dropdown" class="dropdown">
					          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Roller<span class="caret"></span></a>
					          <ul class="dropdown-menu">
									<li id ="listAdmin"><a href="${pageContext.request.contextPath}/listAdmin?pageid=1"> Yöneticiler</a></li>
									<li id ="listManager"><a href="${pageContext.request.contextPath}/listManager?pageid=1"> İntibak Görevlileri</a></li>
									<li id ="listUser"><a href="${pageContext.request.contextPath}/listUser?pageid=1"> Öğrenciler</a></li>
									<li id ="listNewUser"><a href="${pageContext.request.contextPath}/listNewUser?pageid=1"> Yeni Kullanıcılar</a></li>
								</ul>
							</li>
							<li id ="Student"><a href="${pageContext.request.contextPath}/Student?pageid=1"><span class="glyphicon glyphicon-file"></span> İntibaklar</a></li>
							<li id="addRules"><a href="${pageContext.request.contextPath}/addRules" ><span class="glyphicon glyphicon-plus"></span> Yeni Kural</a></li>
							<li id="Ourmark"><a href="${pageContext.request.contextPath}/Ourmark" ><span class="glyphicon glyphicon-dashboard"></span> Not Sistemi</a></li>
								
						</c:if>
					</c:forEach>
					<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_MANAGER' }">
							<li id ="Student"><a href="${pageContext.request.contextPath}/Student?pageid=1"><span class="glyphicon glyphicon-log-in"></span> Öğrenciler</a></li>
							<li id="addRules"><a href="${pageContext.request.contextPath}/addRules" ><span class="glyphicon glyphicon-plus"></span> Yeni Kural</a></li>
							<li id="Ourmark"><a href="${pageContext.request.contextPath}/Ourmark" ><span class="glyphicon glyphicon-plus"></span> Not Sistemi</a></li>
						</c:if>
					</c:forEach>
						<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_SUPER_ADMIN' }">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">CRUD <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="${pageContext.request.contextPath}/uniList">Üniversiteler</a></li>
										<li><a href="${pageContext.request.contextPath}/deptList">Bölümler</a></li>
										<li><a href="${pageContext.request.contextPath}/takingLessonList">Alınan Dersler</a></li>
										<li><a href="${pageContext.request.contextPath}/substituteLessonList">Sayılan Dersler</a></li>
									</ul>
							</li>
						</c:if>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-in"></span> Çıkış</a></li>
					
				</ul>
			</div>
		</div>
	</div>
</nav>
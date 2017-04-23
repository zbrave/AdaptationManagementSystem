<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Rapor</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <!-- Default panel contents -->
                <div class="panel-heading">
                    <h2 class="panel-title">
                        <h5 class="text-center">İntibak Yönetim Sistemi Raporu</h5>
                    </h2>
                </div>
                <div class="panel-body">
                	<div class="container-fluid">
                		<div class="row">
                			<div class="col-lg-4">
                				<img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" class="img-responsive" alt="" style="width:50%"/>
                			</div>
                			<div class="col-lg-4">
                				<h2 class="text-center"><b>LİSANS PROGRAMLARI İÇİN DERS İNTİBAK FORMU</b></h2>
                			</div>
                			<div class="col-lg-2"></div>
                			<div class="col-lg-2">
                				<br><br>
                				<label class="text-right">Form no: FR-1053</label>
                				<label class="text-nowrap">Revizyon Tarihi:	14.12.2015</label>
                				<label class="text-right">Revizyon No:	00</label>
                			</div>
                		</div>
                	</div>
                </div>
                <div class="container text-center">
					<div class="row ">
						<label for="c1" class="btn btn-default">LYS <input type="checkbox" id="c1" class="badgebox"><span class="badge"></span></label>
						<label for="c2" class="btn btn-default">DGS <input type="checkbox" id="c2" class="badgebox"><span class="badge"></span></label>
						<label for="c3" class="btn btn-default">K.ARASI GEÇİŞ <input type="checkbox" id="c3" class="badgebox"><span class="badge"></span></label>
						<label for="c4" class="btn btn-default">AF <input type="checkbox" id="c4" class="badgebox"><span class="badge"></span></label>
						<label for="c5" class="btn btn-default">MERKEZİ YERLEŞTİRME PUANI İLE YATAY GEÇİŞ <input type="checkbox" id="c5" class="badgebox"><span class="badge"></span></label>
						<label for="c6" class="btn btn-default">KURUM İÇİ YATAY GEÇİŞ <input type="checkbox" id="c6" class="badgebox"><span class="badge"></span></label>
			        </div>
				</div>
				<hr>
                <table class="table">
					<thead>
						<th>Öğrenci no</th>
						<th>Adı Soyadı</th>
						<th>Bölüm/Program</th>
						<th>İntibak Sınıfı</th>
						<th>Kayıt yılı</th>
						<th>Önceki Eğitim Kurumu</th>
						<th>Önceki Eğitim Programı</th>
					</thead>
					<tbody>
						<tr>
							<td>13011068</td>
							<td>Mert Aydar</td>
							<td>Bilgisayar Mühendisliği</td>
							<td>3</td>
							<td>2013</td>
							<td>Pamukkale Üniversitesi</td>
							<td>Matematik Mühendisliği</td>
						</tr>
					</tbody>
				</table>
				<hr>
				<div class="row">
				<div class="table-responsive col-lg-6">
					<div class="panel panel-info">
					<div class="panel-heading">1. Yarıyıl</div>
						<table class="table">
						<thead>
							<th>Öğrenci no</th>
							<th>Adı Soyadı</th>
							<th>Bölüm/Program</th>
							<th>İntibak Sınıfı</th>
							<th>Kayıt yılı</th>
							<th>Önceki Eğitim Kurumu</th>
							<th>Önceki Eğitim Programı</th>
						</thead>
						<tbody>
							<tr>
								<td>13011068</td>
								<td>Mert Aydar</td>
								<td>Bilgisayar Mühendisliği</td>
								<td>3</td>
								<td>2013</td>
								<td>Pamukkale Üniversitesi</td>
								<td>Matematik Mühendisliği</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
					<div class="table-responsive col-lg-6">
						<div class="panel panel-info">
						<div class="panel-heading">2. Yarıyıl</div>
							<table class="table">
							<thead>
								<th>Öğrenci no</th>
								<th>Adı Soyadı</th>
								<th>Bölüm/Program</th>
								<th>İntibak Sınıfı</th>
								<th>Kayıt yılı</th>
								<th>Önceki Eğitim Kurumu</th>
								<th>Önceki Eğitim Programı</th>
							</thead>
							<tbody>
								<tr>
									<td>13011068</td>
									<td>Mert Aydar</td>
									<td>Bilgisayar Mühendisliği</td>
									<td>3</td>
									<td>2013</td>
									<td>Pamukkale Üniversitesi</td>
									<td>Matematik Mühendisliği</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="table-responsive col-lg-6">
					<div class="panel panel-info">
					<div class="panel-heading">1. Yarıyıl</div>
						<table class="table">
						<thead>
							<th>Öğrenci no</th>
							<th>Adı Soyadı</th>
							<th>Bölüm/Program</th>
							<th>İntibak Sınıfı</th>
							<th>Kayıt yılı</th>
							<th>Önceki Eğitim Kurumu</th>
							<th>Önceki Eğitim Programı</th>
						</thead>
						<tbody>
							<tr>
								<td>13011068</td>
								<td>Mert Aydar</td>
								<td>Bilgisayar Mühendisliği</td>
								<td>3</td>
								<td>2013</td>
								<td>Pamukkale Üniversitesi</td>
								<td>Matematik Mühendisliği</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
					<div class="table-responsive col-lg-6">
						<div class="panel panel-info">
						<div class="panel-heading">2. Yarıyıl</div>
							<table class="table">
							<thead>
								<th>Öğrenci no</th>
								<th>Adı Soyadı</th>
								<th>Bölüm/Program</th>
								<th>İntibak Sınıfı</th>
								<th>Kayıt yılı</th>
								<th>Önceki Eğitim Kurumu</th>
								<th>Önceki Eğitim Programı</th>
							</thead>
							<tbody>
								<tr>
									<td>13011068</td>
									<td>Mert Aydar</td>
									<td>Bilgisayar Mühendisliği</td>
									<td>3</td>
									<td>2013</td>
									<td>Pamukkale Üniversitesi</td>
									<td>Matematik Mühendisliği</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="table-responsive col-lg-6">
					<div class="panel panel-info">
					<div class="panel-heading">1. Yarıyıl</div>
						<table class="table">
						<thead>
							<th>Öğrenci no</th>
							<th>Adı Soyadı</th>
							<th>Bölüm/Program</th>
							<th>İntibak Sınıfı</th>
							<th>Kayıt yılı</th>
							<th>Önceki Eğitim Kurumu</th>
							<th>Önceki Eğitim Programı</th>
						</thead>
						<tbody>
							<tr>
								<td>13011068</td>
								<td>Mert Aydar</td>
								<td>Bilgisayar Mühendisliği</td>
								<td>3</td>
								<td>2013</td>
								<td>Pamukkale Üniversitesi</td>
								<td>Matematik Mühendisliği</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
					<div class="table-responsive col-lg-6">
						<div class="panel panel-info">
						<div class="panel-heading">2. Yarıyıl</div>
							<table class="table">
							<thead>
								<th>Öğrenci no</th>
								<th>Adı Soyadı</th>
								<th>Bölüm/Program</th>
								<th>İntibak Sınıfı</th>
								<th>Kayıt yılı</th>
								<th>Önceki Eğitim Kurumu</th>
								<th>Önceki Eğitim Programı</th>
							</thead>
							<tbody>
								<tr>
									<td>13011068</td>
									<td>Mert Aydar</td>
									<td>Bilgisayar Mühendisliği</td>
									<td>3</td>
									<td>2013</td>
									<td>Pamukkale Üniversitesi</td>
									<td>Matematik Mühendisliği</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="table-responsive col-lg-6">
					<div class="panel panel-info">
					<div class="panel-heading">1. Yarıyıl</div>
						<table class="table">
						<thead>
							<th>Öğrenci no</th>
							<th>Adı Soyadı</th>
							<th>Bölüm/Program</th>
							<th>İntibak Sınıfı</th>
							<th>Kayıt yılı</th>
							<th>Önceki Eğitim Kurumu</th>
							<th>Önceki Eğitim Programı</th>
						</thead>
						<tbody>
							<tr>
								<td>13011068</td>
								<td>Mert Aydar</td>
								<td>Bilgisayar Mühendisliği</td>
								<td>3</td>
								<td>2013</td>
								<td>Pamukkale Üniversitesi</td>
								<td>Matematik Mühendisliği</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
					<div class="table-responsive col-lg-6">
						<div class="panel panel-info">
						<div class="panel-heading">2. Yarıyıl</div>
							<table class="table">
							<thead>
								<th>Öğrenci no</th>
								<th>Adı Soyadı</th>
								<th>Bölüm/Program</th>
								<th>İntibak Sınıfı</th>
								<th>Kayıt yılı</th>
								<th>Önceki Eğitim Kurumu</th>
								<th>Önceki Eğitim Programı</th>
							</thead>
							<tbody>
								<tr>
									<td>13011068</td>
									<td>Mert Aydar</td>
									<td>Bilgisayar Mühendisliği</td>
									<td>3</td>
									<td>2013</td>
									<td>Pamukkale Üniversitesi</td>
									<td>Matematik Mühendisliği</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-4">
						<div class="panel panel-danger">
					  	<div class="panel-heading text-center">Öğrenci</div>
					  	<div class="panel-body">
						    <p class="text-center">İntibakımı kabul ettim.</p>
						    <br>
						    Tarih:
						    <br><br>
						    İmza:<br><br>
					  	</div>
						</div>
					</div>
					<div class="col-lg-5">
						<div class="panel panel-danger">
					  	<div class="panel-heading text-center">Komisyon Üyeleri</div>
					  	<div class="panel-body">
						    <table class="table">
							    <tr>
							    	<td>1.Üye</td>
							    	<td>Ad-Soyad</td>
							    	<td>İmza</td>
							    </tr>
							    <tr>
							    	<td>1.Üye</td>
							    	<td>Ad-Soyad</td>
							    	<td>İmza</td>
							    </tr>
							    <tr>
							    	<td>1.Üye</td>
							    	<td>Ad-Soyad</td>
							    	<td>İmza</td>
							    </tr>
						    </table>
					  	</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="panel panel-danger">
					  	<div class="panel-heading text-center">Bölüm Başkanı</div>
					  	<div class="panel-body">
						    Ad Soyad
						    <br><br>
						    İmza:
						    <br><br><br><br>
					  	</div>
						</div>
					</div>
				</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
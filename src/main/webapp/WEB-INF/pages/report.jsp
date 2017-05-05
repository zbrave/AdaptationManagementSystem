<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/AMS/resources/css/print.css" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Rapor</title>
</head>
<script type="text/javascript">
	function printDiv(divName) {

        var printContents = document.getElementById(divName).innerHTML;
        w = window.open();
        w.document.write('<link rel="stylesheet" href="/AMS/resources/css/print.css" type="text/css" media="print" />');
        w.document.write(printContents);
        w.document.write('<scr' + 'ipt type="text/javascript">'+ 'window.onload = function() { window.print(); window.close(); };' + '</sc' + 'ript>');
        //   
        w.document.close(); // necessary for IE >= 10
        w.focus(); // necessary for IE >= 10

        return true;
    }
</script>
<body>
<input type="button" onclick="printDiv('printableArea');" value="print a div!" />
<div id="printableArea">
    <div class="header">
    <table class="head">
    	<tr>
			<td rowspan="3"><img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" alt="ytulogo"/></td>
			<td rowspan="3"><h1><b>LİSANS PROGRAMLARI İÇİN DERS İNTİBAK FORMU</b></h1></td>
			<td class="headinfo" colspan="1">Form no</td><td class="headinfo2" colspan="1">FR-1053</td>
			<tr><td class="headinfo" colspan="1">Revizyon Tarihi</td><td class="headinfo2" colspan="1">14.12.2015</td></tr>
			<tr><td class="headinfo" colspan="1">Revizyon No</td><td class="headinfo2" colspan="1">00</td></tr>
		</tr>
	</table>
	</div>
	<div class="options">
		<table>
			<tr>
				<td colspan="6"><label for="c1" >LYS <input type="checkbox" id="c1" ></label>
				<label for="c2" >DGS <input type="checkbox" id="c2" ></label>
				<label for="c3" >K.ARASI GEÇİŞ <input type="checkbox" id="c3" ></label>
				<label for="c4" >AF <input type="checkbox" id="c4" ></label>
				<label for="c5" >MERKEZİ YERLEŞTİRME PUANI İLE YATAY GEÇİŞ <input type="checkbox" id="c5" ></label>
				<label for="c6" >KURUM İÇİ YATAY GEÇİŞ <input type="checkbox" id="c6" ></label></td>
			</tr>
		</table>
		<table class="asd">
			<tr>
				<td colspan="1">ÖĞR.NO</td><td colspan="2">16011068</td><td colspan="1">ADI SOYADI</td><td colspan="3">Furkan Yılmaz</td><td colspan="1">BOLUM/PROGRAM</td><td colspan="3">Bilgisayar Mühendisliği</td>
			</tr>
		</table>
    </div>
    <div class="student">
	    <table class="table" cellspacing="10" border="1">
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
	<div class="lesson">
		<table class="left" border="1">
		<caption>1.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="right" border="1">
		<caption>2.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="left" border="1">
		<caption>1.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="right" border="1">
		<caption>2.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="left" border="1">
		<caption>1.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="right" border="1">
		<caption>2.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="left" border="1">
		<caption>1.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="right" border="1">
		<caption>2.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="left" border="1">
		<caption>1.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="lesson">
		<table class="right" border="1">
		<caption>2.Yarıyıl</caption>
		<thead>
			<th>Ders Kodu</th>
			<th>Dersin Adı</th>
			<th>Dili</th>
			<th>Kredisi</th>
			<th>AKTS</th>
			<th>Harf Notu</th>
		</thead>
		<tbody>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
			<tr>
				<td>BLM1541</td>
				<td>İstatistik Ve Olasılık Hesapları</td>
				<td></td>
				<td>3</td>
				<td>4</td>
				<td>CC</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="footer">
		<table border="0">
		<caption>Öğrenci</caption>
		<thead>
			<th></th>
			<th>İntibakımı kabul ettim.</th>
			<th></th>
		</thead>
		<tbody>
			<tr>
				<td>Tarih</td>
			</tr>
			<tr>
				<td>İmza</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="footer">
		<table border="1">
		<caption>Komisyon Üyeleri</caption>
		<thead>
			<th width="100">Üyeler</th>
			<th width="120">İmza</th>
		</thead>
		<tbody>
			<tr>
				<td>PROF. DR. NİZAMETTİN AYDIN</td><td></td>
			</tr>
			<tr>
				<td>ARS. GOR. ALPER EĞİTMEN</td><td></td>
			</tr>
			<tr>
				<td>ARS. GOR. AHMET ELBİR</td><td></td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="footer">
		<table border="1">
		<caption>Bölüm Başkanı</caption>
		<thead>
			<th width="100">Ad-Soyad</th>
			<th width="120">İmza</th>
		</thead>
		<tbody>
			<tr>
				<td>PROF. DR. NİZAMETTİN AYDIN</td><td></td>
			</tr>
			</tr>
		</tbody>
		</table>
	</div>
</div>
</body>
</html>
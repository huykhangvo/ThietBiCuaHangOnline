<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>Site admin</title>
	<style type="text/css">
		* {
			padding: 0px;
			margin: 0px;
		}
		#wrapper {
			width: 1200px;
			margin: auto;
		}
		table, th, td {
			border:1px solid black;
		}
	</style>
</head>
<body>
	<div id="wrapper">
		<p style="font-size:50px;margin-top:20px;font-weight:bold;color:red;text-align:center">DANH SÁCH SẢN PHẨM</p>
		<table style="margin-top:60px;text-align:center;background-color:lightyellow">
			<tr style="color:black;font-size:24px;background-color:#1895d4">
				<th width="50px">Mã SP</th>
				<th width="200px">Tên sản phẩm</th>
				<th>Giá SP</th>
				<th>Ảnh</th>
				<th>Mô tả</th>
				<th width="60px">Mã loại</th>
			</tr>
			<tbody id="data">
			
			</tbody>
		</table>
	</div>
<script>
	//call ajax
	var ajax = new XMLHttpRequest();
	var method = "GET";
	var url = "data.php";
	var asynchronous = true;
	
	ajax.open(method, url, asynchronous);
	//sending ajax request
	ajax.send();
	
	//receiving response from data.php
	ajax.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			//alert(this.responseText);
			
			//convert JSON back to array
			var data = JSON.parse(this.responseText);
			console.log(data);
			
			//html value for <tbody>
			var html = "";
			//lopping throught the data
			for (var i = 0; i < data.length; i ++) {
				var masanpham = data[i].masanpham;
				var tensanpham = data[i].tensanpham;
				var giasanpham = data[i].giasanpham;
				var hinhanhsanpham = data[i].hinhanhsanpham;
				var motasanpham = data[i].motasanpham;
				var maloaisanpham = data[i].maloaisanpham;
				
				//appending  at html
				html += "<tr>";
					html += "<td>" + masanpham + "</td>";
					html += "<td>" + tensanpham + "</td>";
					html += "<td>" + giasanpham + "</td>";
					html += "<td>" + "<img src='" + hinhanhsanpham + "' width='150px' height='150px' />" + "</td>";
					html += "<td style='text-align:justify'>" + motasanpham + "</td>";
					html += "<td>" + maloaisanpham + "</td>";
				html += "</tr>";
				
				//replace the <tbody> of <table>
				document.getElementById("data").innerHTML = html;
			}
		}
	}
</script>




</body>
</html>
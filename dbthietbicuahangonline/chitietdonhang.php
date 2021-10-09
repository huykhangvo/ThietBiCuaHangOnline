<?php

	include "connect.php";
	/*$json = '[{"giasanpham":17000,"madonhang":"5","soluongsanpham":1,"tensanpham":"Laptop Sony vaio","masanpham":20},
	{"giasanpham":20000,"madonhang":"2","soluongsanpham":3,"tensanpham":"Laptop HP 110","masanpham":21}]';*/
	$json = $_POST['json'];

	$data = json_decode($json, true);
	foreach ($data as $value) {
		$madonhang = $value['madonhang'];
		$masanpham = $value['masanpham'];
		$tensanpham = $value['tensanpham'];
		$giasanpham = $value['giasanpham'];
		$soluongsanpham = $value['soluongsanpham'];
		$query = "INSERT INTO chitietdonhang (id, madonhang, masanpham, tensanpham, giasanpham, soluongsanpham)
			VALUES (null, '$madonhang', '$masanpham', '$tensanpham', '$giasanpham', '$soluongsanpham')";
		$Dta = mysqli_query($connect, $query);
	}
	if ($Dta) {
		echo "success";
	} else {
		echo "error";
	}
?>

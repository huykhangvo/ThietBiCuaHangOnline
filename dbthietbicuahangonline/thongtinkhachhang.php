<?php

	include "connect.php";

	$sno = $_POST['sno'];
	$tenkhachhang = $_POST['tenkhachhang'];
	$diachi = $_POST['diachi'];
	$sodienthoai = $_POST['sodienthoai'];
	$email = $_POST['email'];
	$ngaydat = $_POST['ngaydat'];
	$yeucau = $_POST['yeucau'];

	if (strlen($sno) > 0 && strlen($tenkhachhang) > 0 && strlen($diachi) > 0 && strlen($sodienthoai) > 0 && strlen($email) > 0) {
		$query = "INSERT INTO donhang(madonhang, sno, tenkhachhang, diachi, sodienthoai, email, ngaydat, yeucau)
		VALUES (null, '$sno', '$tenkhachhang', '$diachi', '$sodienthoai', '$email', '$ngaydat', '$yeucau')";
		if (mysqli_query($connect, $query)) {
			$idmadonhang = $connect->insert_id;
			echo $idmadonhang;
		} else {
			echo "error";
		}
	} else {
		echo "Bạn hãy kiểm tra lại các dữ liệu";
	}

?>

<?php
	$host = "localhost";
	$username = "root";
	$password = "";
	$database = "thietbi";

	$connect = mysqli_connect($host, $username, $password, $database);
	mysqli_query($connect, "SET NAMES 'utf8'");
	/*if ($connect) {
		echo "Kết nối thành công";
	} else {
		echo "Kết nối thất bại";
	}*/
?>

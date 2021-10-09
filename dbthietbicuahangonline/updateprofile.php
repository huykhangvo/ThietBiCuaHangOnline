<?php

	require "connect.php";

	$unique_id = $_POST["unique_id"];
  $name = $_POST["name"];
	$ngaysinh = $_POST["ngaysinh"];
	$sodienthoai = $_POST["sodienthoai"];
	$diachi = $_POST["diachi"];

	$query = "UPDATE users SET name = '$name', ngaysinh = '$ngaysinh', sodienthoai = '$sodienthoai',
							diachi = '$diachi' WHERE unique_id = '$unique_id'";

	if (mysqli_query($connect, $query)) {
		//thanh cong
		echo "success";
	} else {
		echo "error";
	}

?>

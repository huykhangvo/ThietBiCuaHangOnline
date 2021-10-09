<?php
	
	require "connect.php";
	
	$unique_id = $_POST["unique_id"];
	$name = $_POST["name"];
	$image = $_POST["image"];
	$ngaysinh = $_POST["ngaysinh"];
	$sodienthoai = $_POST["sodienthoai"];
	$diachi = $_POST["diachi"];
	
	$sql = "UPDATE users SET name = '$name', image = '$image', ngaysinh = '$ngaysinh', sodienthoai = '$sodienthoai', 
									diachi = '$diachi' WHERE unique_id = '$unique_id'";
	$data = mysqli_query($connect, $sql);
	if ($data) {
		echo "success";
	} else {
		echo "fail";
	}
	
	
?>

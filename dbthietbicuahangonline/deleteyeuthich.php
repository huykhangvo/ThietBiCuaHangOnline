<?php

	require "connect.php";

	$masanpham = $_POST['masanpham'];
	$sno = $_POST['sno'];

	$query = "DELETE FROM yeuthich WHERE masanpham = '$masanpham' AND sno = '$sno'";

	if (mysqli_query($connect, $query)) {
		//thanh cong
		echo "success";
	} else {
		//loi
		echo "error";
	}

?>

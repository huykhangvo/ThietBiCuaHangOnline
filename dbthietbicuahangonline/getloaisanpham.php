<?php

	include "connect.php";
	$query = "SELECT * FROM loaisanpham";
	$data = mysqli_query($connect, $query);

	$mangloaisp = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangloaisp, new Loaisp(
			$row["maloaisanpham"],
			$row["tenloaisanpham"],
			$row["hinhanhloaisanpham"]
		));
	}
	echo json_encode($mangloaisp, JSON_UNESCAPED_UNICODE);

	class Loaisp {
		function Loaisp($maloaisanpham, $tenloaisanpham, $hinhanhsanpham) {
				$this->maloaisanpham = $maloaisanpham;
				$this->tenloaisanpham = $tenloaisanpham;
				$this->hinhanhsanpham = $hinhanhsanpham;
		}
	}
?>

<?php

	include "connect.php";

	$space = 5;


	$query = "SELECT * FROM sanpham WHERE maloaisanpham = 2 ORDER BY giasanpham DESC limit 5";
	$data = mysqli_query($connect, $query);
	$mangsanpham = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangsanpham, new SanPham(
			$row['masanpham'],
			$row['tensanpham'],
			$row['giasanpham'],
			$row['hinhanhsanpham'],
			$row['motasanpham'],
			$row['maloaisanpham']
		));
	}
	echo json_encode($mangsanpham, JSON_UNESCAPED_UNICODE);


	class SanPham {
		function SanPham($masanpham, $tensanpham, $giasanpham, $hinhanhsanpham, $motasanpham, $maloaisanpham) {
			$this->masanpham = $masanpham;
			$this->tensanpham = $tensanpham;
			$this->giasanpham = $giasanpham;
			$this->hinhanhsanpham = $hinhanhsanpham;
			$this->motasanpham = $motasanpham;
			$this->maloaisanpham = $maloaisanpham;
		}
	}

?>

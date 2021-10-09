<?php

	include "connect.php";
	$page = $_GET["page"];
	$maloaisp = $_POST["maloaisanpham"];
	$space = 5;
	$limit = ($page - 1) * $space;


	$query = "SELECT * FROM sanpham WHERE maloaisanpham = $maloaisp ORDER BY giasanpham DESC LIMIT $limit, $space";
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

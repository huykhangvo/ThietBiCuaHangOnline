<?php

	include "connect.php";
	$query = "SELECT * FROM sanpham ORDER BY masanpham DESC LIMIT 12";
	$data = mysqli_query($connect, $query);


	$mangsanphammoinhat = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangsanphammoinhat, new SanPhamMoiNhat(
			$row['masanpham'],
			$row['tensanpham'],
			$row['giasanpham'],
			$row['hinhanhsanpham'],
			$row['motasanpham'],
			$row['maloaisanpham']
		));
	}
	echo json_encode($mangsanphammoinhat, JSON_UNESCAPED_UNICODE);


	class SanPhamMoiNhat {
		function SanPhamMoiNhat($masanpham, $tensanpham, $giasanpham, $hinhanhsanpham, $motasanpham, $maloaisanpham) {
			$this->masanpham = $masanpham;
			$this->tensanpham = $tensanpham;
			$this->giasanpham = $giasanpham;
			$this->hinhanhsanpham = $hinhanhsanpham;
			$this->motasanpham = $motasanpham;
			$this->maloaisanpham = $maloaisanpham;
		}
	}

?>

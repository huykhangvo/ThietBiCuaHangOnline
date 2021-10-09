<?php

	//getting data from database
	$connect = mysqli_connect("localhost", "root", "", "thietbi");
	mysqli_query($connect, "SET NAMES 'utf8'");
	
	
	$query = "SELECT * FROM sanpham";
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
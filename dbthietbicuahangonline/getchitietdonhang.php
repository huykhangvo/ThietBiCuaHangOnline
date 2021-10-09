<?php

	include "connect.php";
	$query = "SELECT * FROM chitietdonhang";
	$data = mysqli_query($connect, $query);

	$mangusers = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangusers, new ChiTietDonHang(
			$row["id"],
			$row["madonhang"],
			$row["masanpham"],
      $row["tensanpham"],
      $row["giasanpham"],
      $row["soluongsanpham"]
		));
	}
	echo json_encode($mangusers, JSON_UNESCAPED_UNICODE);

	class ChiTietDonHang {
		function ChiTietDonHang($id, $madonhang, $masanpham, $tensanpham, $giasanpham, $soluongsanpham) {
				$this->id = $id;
				$this->madonhang = $madonhang;
				$this->masanpham = $masanpham;
        $this->tensanpham = $tensanpham;
        $this->giasanpham = $giasanpham;
        $this->soluongsanpham = $soluongsanpham;
		}
	}
?>

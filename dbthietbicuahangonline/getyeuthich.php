<?php

	include "connect.php";
  $sno = $_POST['sno'];

	$query = "SELECT * FROM yeuthich WHERE sno = '$sno'";
	$data = mysqli_query($connect, $query);

	$mangyeuthich = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangyeuthich, new Yeuthich(
			$row["id"],
			$row["masanpham"],
			$row["tensanpham"],
      $row["giasanpham"],
      $row["hinhanhsanpham"],
      $row["motasanpham"],
      $row["maloaisanpham"],
      $row["sno"]
		));
	}
	echo json_encode($mangyeuthich, JSON_UNESCAPED_UNICODE);

	class Yeuthich {
		function Yeuthich($id, $masanpham, $tensanpham, $giasanpham, $hinhanhsanpham, $motasanpham, $maloaisanpham, $sno) {
				$this->id = $id;
				$this->masanpham = $masanpham;
				$this->tensanpham = $tensanpham;
        $this->giasanpham = $giasanpham;
        $this->hinhanhsanpham = $hinhanhsanpham;
        $this->motasanpham = $motasanpham;
        $this->maloaisanpham = $maloaisanpham;
        $this->sno = $sno;
		}
	}
?>

  <?php

  	include "connect.php";

    $masp = $_POST['masanpham'];
    $tensp= $_POST['tensanpham'];
    $giasp = $_POST['giasanpham'];
    $hinhanhsp = $_POST['hinhanhsanpham'];
    $motasp = $_POST['motasanpham'];
    $maloaisp = $_POST['maloaisanpham'];
    $sno = $_POST['sno'];

    $check = "SELECT * FROM yeuthich WHERE masanpham = '$masp' AND sno = '$sno'";
    $check2 = mysqli_query($connect, $check);
    $num = mysqli_num_rows($check2);
    if ($num > 0) {
      echo "0";
    } else {
      $query = "INSERT INTO yeuthich (masanpham, tensanpham, giasanpham, hinhanhsanpham, motasanpham, maloaisanpham, sno)
    	VALUES ('$masp', '$tensp', '$giasp', '$hinhanhsp', '$motasp', '$maloaisp', '$sno')";
    	if (mysqli_query($connect, $query)) {
    		echo "success";
    	} else {
    		echo "error";
    	}
    }



  ?>

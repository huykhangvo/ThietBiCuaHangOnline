<?php

	include "connect.php";
  $iduser = $_POST["iduser"];
	//$email = "qq@yahoo.com";
	//$email2 = "'" . $email . "'";
	//$email2 = "'qq@yahoo.com'";

	$query = "SELECT * FROM users WHERE unique_id = '$iduser'";
	$data = mysqli_query($connect, $query);

	$mangusers = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangusers, new Users(
			$row["sno"],
			$row["unique_id"],
			$row["name"],
      $row["email"],
      $row["encrypted_password"],
      $row["salt"],
      $row["created_at"],
      $row["image"],
      $row["ngaysinh"],
      $row["sodienthoai"],
      $row["diachi"]
		));
	}
	
	echo json_encode($mangusers, JSON_UNESCAPED_UNICODE);

	class Users {
		function Users($sno, $unique_id, $name, $email, $encrypted_password, $salt, $created_at, $image, $ngaysinh, $sodienthoai, $diachi) {
				$this->sno = $sno;
				$this->unique_id = $unique_id;
				$this->name = $name;
        $this->email = $email;
        $this->encrypted_password = $encrypted_password;
        $this->salt = $salt;
        $this->created_at = $created_at;
        $this->image = $image;
        $this->ngaysinh = $ngaysinh;
        $this->sodienthoai = $sodienthoai;
        $this->diachi = $diachi;
		}
	}
?>

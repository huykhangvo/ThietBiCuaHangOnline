<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>MuaMua Shop | Site Admin</title>
	<link rel="stylesheet" href="login.css">
	<script type="text/javascript">
		function check() {
			var user = document.getElementById("user").value;
			var password = document.getElementById("password").value;
			if (user.length == 0 || password.length == 0) {
				alert("Không được để trống");
			} else {
				if (user == "admin" && password == "admin") {
					//alert("Login thành công");
					location.href = "admin.php";
				} else {
					alert("User hoặc Password không đúng, vui lòng nhập lại!");
				}
			}
		}
	</script>
</head>
<body>
	<!-- HTML BOX_LOGIN -->
	<div class="login">
		<div class="img">
			<div id="face">
		<div class="eye">
			<div class="eye-left">
				<div id="eyeballs"></div>
			</div>
			<div class="eye-right">
				<div id="eyeballs"></div>
			</div>
		</div>
		<!-- <div class="suar">	
		</div>
		<div class="suarvcl"></div> -->
		<div id="mouth">
			<div class="tongue"></div>
		</div>
		</div>
		</div>
		<div class="box">
			<div class="row">
			<input type="text" class="name" id="user" placeholder="User">
			</div>
			<div class="row">
			<input type="password" class="pass" id="password" placeholder="Pass">
			</div>
			<div class="row">
			<button type="submit" class="submit" onclick="check();">Login</button>
			</div>
		</div>
		<!-- DESIGED -->
		<span class="span"><i>Design by Ông Phùng ||<a href="https://www.facebook.com/boyhotkey96" target="_blank" style="color: #13D8FC"> @Boyhotkey96</a></i></span>
	</div>
</body>
</html>
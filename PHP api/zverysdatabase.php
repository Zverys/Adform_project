<?php
require_once('dbConnect.php');

$action = $_POST['action'];

if(strcmp($action, "login") == 0) {
	$password = $_POST['password'];
	$username = $_POST['username'];

	$hashed_pass = md5($password);

	$sql = "SELECT * FROM users WHERE username='$username' AND password='$hashed_pass'";

	$check = mysqli_fetch_array(mysqli_query($con,$sql));

	$HTTP_ACCEPTED = '202';
	$HTTP_NOT_AUTHORITATIVE = '203';
	if(isset($check)){
		echo $HTTP_ACCEPTED;
	} else {
		echo $HTTP_NOT_AUTHORITATIVE;
	}
	mysqli_close($con);
}

if (strcmp($action, "register") == 0) {
	$username = $_POST['username'];
	$password = $_POST['password'];
	$email = $_POST['email'];

	if($username == '' || $password == '' || $email == '') {
		echo 'please fill all values';
	} else {
		$sql = "SELECT * FROM users WHERE username='$username' OR email='$email'";
		$check = mysqli_fetch_array(mysqli_query($con,$sql));

		if(isset($check)){
			echo 'username or email already exist';
		} else {
			$ulevel = -1;
			if (strcasecmp($username, "Administrator") == 0) {
				$ulevel = 9;
			} else {
				$ulevel = 1;
			}

			$passwordHashed = md5($password);
			$userID = generateRandID();
			$timestamp = time();
			$sql = "INSERT INTO users VALUES ('$username', '$passwordHashed', '$userID', '$ulevel', '$email', '$timestamp', '123')";

			$HTTP_OK = '200';
			if(mysqli_query($con,$sql)){
				echo $HTTP_OK;
			} else {
				echo 'Registration failure';
			}
		}
		mysqli_close($con);
	}
}

if (strcmp($action, "task_create") == 0) {
	$pavadinimas = $_POST['pavadinimas'];
	$aprasymas = $_POST['aprasymas'];
	$kategorija = $_POST['kategorija'];
	$data = $_POST['data'];
	$id = $_POST['userid'];
	if($pavadinimas == '' || $aprasymas == '' || $kategorija == '' || $data == '') {
		echo 'please fill all values';
	} else {

			$timestamp = time();
			$sql = "INSERT INTO tasks VALUES ('$pavadinimas', '$aprasymas', '$kategorija', '$data', '$timestamp', '$id')";

			$HTTP_OK = '200';
			if(mysqli_query($con,$sql)){
				echo $HTTP_OK;
			} else {
				echo 'Registration failure';
			}
		mysqli_close($con);
	}
}

if (strcmp($action, "category_create") == 0) {
	$category = $_POST['category'];
	$description = $_POST['description'];
	$iduser = $_POST['iduser'];

	if($category == '' || $description == '' || $iduser == '') {
		echo 'please fill all values';
	} else {

			$sql = "INSERT INTO categories VALUES ('$category', '$description', '$iduser')";

			$HTTP_OK = '200';
			if(mysqli_query($con,$sql)){
				echo $HTTP_OK;
			} else {
				echo 'Registration failure';
			}
		mysqli_close($con);
	}
}
/**
* generateRandID - Generates a string made up of randomized
* letters (lower and upper case) and digits and returns
* the md5 hash of it to be used as a userid.
*/
function generateRandID() {
return md5(generateRandStr(16));
}

/**
* generateRandStr - Generates a string made up of randomized
* letters (lower and upper case) and digits, the length
* is a specified parameter.
*/
function generateRandStr($length) {
	$randstr = "";
	for ($i = 0; $i < $length; $i++) {
		$randnum = mt_rand(0, 61);
		if ($randnum < 10) {
			$randstr .= chr($randnum + 48);
		} else if ($randnum < 36) {
			$randstr .= chr($randnum + 55);
		} else {
			$randstr .= chr($randnum + 61);
		}
	}
	return $randstr;
}
?>

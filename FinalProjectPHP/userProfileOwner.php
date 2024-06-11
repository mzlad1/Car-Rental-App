<?php
include ('db.php');
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}

$sql = "SELECT email,phone,address FROM owners WHERE username = :username";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':username', $_GET['username']);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
if ($row) {
    $info = array(
        "email" => $row["email"],
        "phone" => $row["phone"],
        "address" => $row["address"]
    );
    $response = json_encode($info);
    echo $response;
} else {
    echo "user not found";
}
?>
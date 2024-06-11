<?php
include('db.php');
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}


$sql = "SELECT * FROM cars WHERE username = :username";

$stmt = $pdo->prepare($sql);
$stmt->bindParam(':username', $_GET['username']);
$stmt->execute();

if ($stmt->rowCount() > 0) {
    $cars = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $cars[] = array(
            "name" => $row["name"],
            "model" => $row["model"],
            "price" => $row["price"],
            "status" => $row["status"],
            "img" => $row["img"],
            "id" => $row["id"]
        );
    }
    echo json_encode($cars);
} else {
    echo "No cars found";
}
$pdo = null;
?>

<?php
include('db.php');
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}

$sql = "SELECT * FROM cars";
$stmt = $pdo->prepare($sql);
$stmt->execute();
if ($stmt->rowCount() > 0) {
    $cars = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $car = array(
            "name" => $row["name"],
            "model" => $row["model"],
            "price" => $row["price"],
            "status" => $row["status"],
            "img" => $row["img"],
            "id" => $row["id"]

        );
        $cars[] = $car;
    }

    $response = json_encode($cars);
    echo $response;
} else {
    echo "No cars found"; 
}
$pdo = null;
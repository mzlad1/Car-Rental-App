<?php
include ('db.php');
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}

$sql = "SELECT name,model,price,description,img  FROM cars WHERE id = :id";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':id', $_GET['id']);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
if ($row) {
    $car = array(
        "name" => $row["name"],
        "model" => $row["model"],
        "price" => $row["price"],
        "img" => $row["img"],
        "description" => $row["description"]
    );
    $response = json_encode($car);
    echo $response;
} else {
    echo "Car not found";
}
?>
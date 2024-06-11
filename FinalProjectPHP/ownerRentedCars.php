<?php
include('db.php');
$pdo = db_connect();

header('Content-Type: application/json');

if (!$pdo) {
    echo json_encode(array("error" => "Database connection failed."));
    exit;
}


$sql = "SELECT c.id AS carID, c.name, c.model, c.price, r.date, c.img
        FROM cars c 
        JOIN rented r ON c.id = r.carID
        WHERE c.username = :username";

$stmt = $pdo->prepare($sql);
$stmt->bindParam(':username', $_GET['username']);

if ($stmt->execute()) {
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($rows);
} else {
    echo json_encode(array("error" => "Query failed to execute"));
}
?>

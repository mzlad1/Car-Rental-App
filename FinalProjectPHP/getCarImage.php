<?php
include('db.php');
$pdo = db_connect();
header('Content-Type: application/json');

if (!$pdo) {
    echo json_encode(array("error" => "Database connection failed"));
    exit;
}

$id = isset($_GET['id']) ? intval($_GET['id']) : 0;
$sql = "SELECT img FROM cars WHERE id = :id";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':id', $id);

if ($stmt->execute()) {
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if ($row) {
        echo json_encode(array("img" => $row["img"]));
    } else {
        echo json_encode(array("error" => "Car not found"));
    }
} else {
    echo json_encode(array("error" => "Query failed to execute"));
}
?>
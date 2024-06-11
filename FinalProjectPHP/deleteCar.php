<?php
include 'db.php';
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}

if (isset($_GET['id'])) {
    $id = $_GET['id'];
    $sql = "DELETE FROM cars WHERE id = :id";
    $sql2 = "DELETE FROM rented WHERE carID = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $id, PDO::PARAM_INT);
    $stmt2 = $pdo->prepare($sql2);
    $stmt2->bindParam(':id', $id, PDO::PARAM_INT);
    if ($stmt->execute() && $stmt2->execute()) {
            echo "Car deleted successfully";
        } else {
            echo "No car found with that ID" . $id;
        }
} else {
    echo "Car ID not provided.";
}


$pdo = null;
?>

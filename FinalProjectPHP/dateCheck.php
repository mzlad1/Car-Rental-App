<?php
include ('db.php');
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}

$sql = "SELECT * FROM rented WHERE carID = :carID AND date = :date";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':carID', $_GET['carID']);
$stmt->bindParam(':date', $_GET['date']);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
//if there is a row, then the car is rented

if ($row) {
    echo "booked";    
    exit;
} else {
    echo "not booked";
    exit;
}

?>
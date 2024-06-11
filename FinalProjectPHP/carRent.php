<?php
include ('db.php');
$pdo=db_connect();
if(!$pdo){
die("Error in connection: " . print_r($pdo->errorInfo(),true));
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $carID = $_POST['carID'];
    $username = $_POST['username'];
    $name = $_POST['name'];
    $model = $_POST['model'];
    $price = $_POST['price'];
    $date = $_POST['date'];

        $query = "INSERT INTO rented (username, name, model, price, date, carID) VALUES (:username, :name, :model, :price, :date, :carID)";
        $stmt = $pdo->prepare($query);
        $stmt->bindParam(':username', $username);
        $stmt->bindParam(':name', $name);
        $stmt->bindParam(':model', $model);
        $stmt->bindParam(':price', $price);
        $stmt->bindParam(':date', $date);
        $stmt->bindParam(':carID', $carID);
        $stmt->execute();
        echo "Payment successful";
    
}
?>
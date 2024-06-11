<?php
include 'db.php';
$pdo = db_connect();

$name = $_POST['name'];
$model = $_POST['model'];
$price = $_POST['price'];
$username = $_POST['username'];
$description = $_POST['description'];
$encodedImage = $_POST['image'];
$decodedImage = base64_decode($encodedImage);
$imagePath = "uploads/" . uniqid() . '.png';
file_put_contents($imagePath, $decodedImage);

$imagePath2 = "http://10.0.2.2:80/FinalProjectPHP/" . $imagePath;

$query = "INSERT INTO cars (name, model, price, description, img ,username ) VALUES (?, ?, ?, ?, ?,?)";
$stmt = $pdo->prepare($query);
if ($stmt->execute([$name, $model, $price, $description, $imagePath2, $username])) {
    echo "Car added successfully";
} else {
    echo "Error adding car";
}
?>
<?php
include 'db.php'; 
$pdo = db_connect();
if (!$pdo) {
    die("Database connection failed.");
}
if (isset($_POST['id'])) {
    $id = $_POST['id'];
    $name = $_POST['name'];
    $model = $_POST['model'];
    $price = $_POST['price'];
    $description = $_POST['description'];   
    $sql = "UPDATE cars SET name = :name, model = :model, price = :price, description =:description WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $id, PDO::PARAM_INT);
    $stmt->bindParam(':name', $name, PDO::PARAM_STR);
    $stmt->bindParam(':model', $model, PDO::PARAM_STR);
    $stmt->bindParam(':price', $price, PDO::PARAM_INT);

    $stmt->bindParam(':description', $description, PDO::PARAM_STR);
    if ($stmt->execute()) {
        if ($stmt->rowCount() > 0) {
            echo "Car updated successfully";
        } else {
            echo "No car found with that ID" . $id;
        }
    } else {
        echo "Failed to update car";
    }
}
$pdo = null;
?>


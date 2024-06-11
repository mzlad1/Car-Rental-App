<?php
include ('db.php');
$pdo=db_connect();
if(!$pdo){
die("Error in connection: " . print_r($pdo->errorInfo(),true));
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $username = $_POST['username'];
    $password = $_POST['password'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];

    $query = "SELECT * FROM users WHERE username = :username";
    $stmt = $pdo->prepare($query);
    $stmt->bindParam(':username', $username);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if($row){
        echo "Username already taken";
        exit();
    }else{
        $query = "INSERT INTO users (username, password, phone, email) VALUES (:username, :password, :phone, :email)";
        $stmt = $pdo->prepare($query);
        $stmt->bindParam(':username', $username);
        $stmt->bindParam(':password', $password);
        $stmt->bindParam(':email', $email);
        $stmt->bindParam(':phone', $phone);
        $stmt->execute();
        echo "Registration successful";
    }
    
    


    
}
?>
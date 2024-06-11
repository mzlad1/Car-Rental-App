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
    $address = $_POST['address'];

    $query = "SELECT * FROM users WHERE username = :username";
    $stmt = $pdo->prepare($query);
    $stmt->bindParam(':username', $username);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);

    $query2 = "SELECT * FROM owners WHERE username = :username";
    $stmt2 = $pdo->prepare($query2);
    $stmt2->bindParam(':username', $username);
    $stmt2->execute();
    $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);

    if($row || $row2){
        echo "Username Exists!";
        exit();
    }else{
        $query = "INSERT INTO owners (username, password, phone, email, address) VALUES (:username, :password, :phone, :email, :address)";
        $stmt = $pdo->prepare($query);
        $stmt->bindParam(':username', $username);
        $stmt->bindParam(':password', $password);
        $stmt->bindParam(':email', $email);
        $stmt->bindParam(':phone', $phone);
        $stmt->bindParam(':address', $address);
        $stmt->execute();
        echo "Registration successful";
    }
    
    


    
}
?>
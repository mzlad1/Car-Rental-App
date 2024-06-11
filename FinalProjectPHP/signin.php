<?php

include ('db.php');

$pdo = db_connect();

if (!$pdo) {
    die("Database connection failed.");
}

if($_SERVER['REQUEST_METHOD'] == 'POST'){

  $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM users WHERE username = :username AND password = :password";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':username', $username);
    $stmt->bindValue(':password', $password);
    $stmt->execute();
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    $sql2 = "SELECT * FROM owners WHERE username = :username AND password = :password";
    $stmt2 = $pdo->prepare($sql2);
    $stmt2->bindValue(':username', $username);
    $stmt2->bindValue(':password', $password);
    $stmt2->execute();
    $user2 = $stmt2->fetch(PDO::FETCH_ASSOC);


    if ($user && $stmt->rowCount() > 0) {
        $_SESSION['username'] = $user['username'];
        $_SESSION['id'] = $user['id'];
        echo "user";
        exit;
    } elseif ($user2 && $stmt2->rowCount() > 0) {
        $_SESSION['username'] = $user2['username'];
        $_SESSION['ownerID'] = $user2['ownerID'];
        echo "owner";
        exit;
    } else {
        echo "Invalid username or password.";
    }
    

}


?>
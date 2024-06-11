<?php
include 'db.php'; 
$pdo = db_connect();

if (!$pdo) {
    echo json_encode(array("error" => "Database connection failed."));
    exit;
}

if (isset($_GET['username']) && isset($_GET['month'])) {
    $username = $_GET['username'];
    $month = $_GET['month'];
    $sql = "SELECT r.price 
            FROM rented r
            INNER JOIN cars c ON r.carID = c.id 
            WHERE c.username = :username AND MONTH(r.date) = :month";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':username', $username, PDO::PARAM_STR);
    $stmt->bindParam(':month', $month, PDO::PARAM_INT);

    if ($stmt->execute()) {
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($rows);
    } else {
        echo json_encode(array("error" => "Query failed to execute"));
    }
}
?>

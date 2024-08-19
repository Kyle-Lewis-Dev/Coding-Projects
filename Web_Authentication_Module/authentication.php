<?php
    require_once 'login.php'; // This file contains the database connection details
    require_once 'sanitize.php'; //This file contains string sanitization functions for security

    try {
        $pdo = new PDO($attr, $user, $pass, $opts);
    } catch (PDOException $e) {
        die("Could not connect to the database: " . $e->getMessage());
    }

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        // Server-side input validation
        $username = sanitizeString($_POST['username']);
        $password = sanitizeString($_POST['password']);

        // Prepared statement to prevent SQL injection
        $stmt = $pdo->prepare("SELECT passhash FROM users_table WHERE username = :username");
        $stmt->bindParam(':username', $username);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            $storedHash = $row['passhash'];

            // Verify the password using password_verify
            if (password_verify($password, $storedHash)) {
                echo "Welcome, $username!";
            } else {
                echo "Invalid username or password.";
            }
        } else {
            echo "Invalid username or password.";
        }
    }
    ?>
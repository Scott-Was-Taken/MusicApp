<?php
    session_start();
    if (!isset($_SESSION['logged_in']) || $_SESSION['logged_in']==false){
        header("location:index.php");
    }
    else{
        header("location:home.php");
    }
?>
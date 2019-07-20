<!DOCTYPE HTML>
<?php

function alert($msg) {
    echo "<script type='text/javascript'>alert('$msg');</script>";
}

/*start the session*/
    session_start();
    /* if already logged in */
    if (isset($_SESSION['logged_in']) && $_SESSION['logged_in']==true){
        /*redirect to success*/
        header("location: success.php");
    }

    /*if the user has entered a username and password*/
    if (isset($_POST['username']) && isset($_POST['password'])){
        
        

        //access the database for that user
        $enteredName=$_POST['username'];
        $enteredPassword= $_POST['password'];
        //$serverName = "localhost";
        //$connectionOptions = array(
           // "Database" => "ng_music",
            //"Uid" => "ng_user",
           // "PWD" => "ng"
        //);
        //Establishes the connection
        //$conn = sqlsrv_connect($serverName, $connectionOptions);
        //$tsql= "SELECT * FROM USERS WHERE USERNAME='$enteredName'";
        $mysqli = new mysqli("localhost", "ng_user", "ng", "ng_music");
        $getResults = $mysqli->query("SELECT * FROM ng_users WHERE USERNAME='$enteredName'");
        //$getResults= sqlsrv_query($conn, $tsql);
        if ($getResults == FALSE){
            echo (sqlsrv_errors());
        }
        while ($row = $getResults->fetch_assoc()) {
            echo ("there are results");
            $username=$row['username'];
            $password=$row['password'];
            echo($username);
            echo($password);
        }

        
        /*and if the username and password matches a database entry*/
        if ($_POST['username'] == $username && $_POST['password'] ==$password){
            /*log the user in*/
            $_SESSION['logged_in']=true;
            $_SESSION['USERNAME']=$username;
            /*redirect to success*/
            header("location: success.php");
        }
    }
?>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    <title>NextGate Music</title>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="login.css" type="text/css">
</head>
<body id="ngbody">
<img src="img/logo.png">
<div class="centercontainer">
    <div>
    <h1>NextGate Music App</h1>
    <p>All your favourite tunes in one place.</p>
    <!-- Button to open form -->
    <button onclick="document.getElementById('id01').style.display='block'">Login</button>
    </div>
    
</div>


<!-- The form modal -->
<div id="id01" class="modal">
      <span onclick="document.getElementById('id01').style.display='none'"
            class="close" title="Close Modal">&times;</span>

    <!-- Content Area -->
    <form method="post" class="modal-content animate" action="index.php">
        <div class="imgcontainer">
            <img src="img/listening2.jpg" alt="GCU" class="logo">
        </div>

        <div class="container">
            <label for="username"><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="username" required>

            <label for="password"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="password" required>

            <input type="submit">

        </div>

        <div class="container" style="background-color:#f1f1f1">
            <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
        </div>
    </form>
</div>
  
</body>

<html>
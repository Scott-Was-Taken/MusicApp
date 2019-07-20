<?php
function alert($msg) {
    echo "<script type='text/javascript'>alert('$msg');</script>";
}
session_start();
if (!isset($_SESSION['logged_in']) || $_SESSION['logged_in']==false){
    header("location:index.php");
}
else{
    $name=$_SESSION['USERNAME'];
    echo '<input type="hidden" id="usersname" value="'.$name.'">';
    echo("</br>");
}


$mysqli = new mysqli("localhost", "ng_user", "ng", "ng_music");
    $result = $mysqli->query("SELECT * FROM ng_singers");
    //Initialize array variable
  $dbdata = array();

//Fetch into associative array
  while ( $row = $result->fetch_assoc())  {
	$dbdata[]=$row;
  }

//Print array in JSON format
 $singerJson = json_encode($dbdata);

//echo the resulting json into a hidden input box
//$json=strval($json);
//echo '<input type="hidden" id="singerJson" value="'.$json.'">';
echo <<<EOT
<textarea id='singerJson' style='display:none;'>$singerJson</textarea>
EOT;
$result2 = $mysqli->query("SELECT * FROM ng_albums");
    
  $dbdata2 = array();

//Fetch into associative array
  while ( $row = $result2->fetch_assoc())  {
	$dbdata2[]=$row;
  }

//Print array in JSON format
 $albumJson = json_encode($dbdata2);


//echo the resulting json into a hidden input box
//$json=strval($json);
echo <<<EOT
<textarea id='albumJson' style='display:none;'>$albumJson</textarea>
EOT;



?>
<!DOCTYPE HTML>
<html>
<!--JQUERY-->
<script
    src="https://code.jquery.com/jquery-3.3.1.min.js"
    integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
    crossorigin="anonymous"></script>

<!--REACT-->
<script src="https://unpkg.com/react@16/umd/react.development.js"></script>
<script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js"></script>
<script src="https://unpkg.com/babel-standalone@6.15.0/babel.min.js"></script>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    <title>NextGate Music App</title>
    <link rel="stylesheet" href="Stylesheet.css" type="text/css">
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
    <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        * {
            box-sizing: border-box;
        }

        input[type=text], select, textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            resize: vertical;
        }
        .container {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
            color:black;
            overflow:scroll
        }

        .col-25 {
            float: left;
            width: 25%;
            margin-top: 6px;
        }

        .col-75 {
            float: left;
            width: 75%;
            margin-top: 6px;
        }

        /* Clear floats after the columns */
        .row:after {
            content: "";
            display: table;
            clear: both;
        }

        /* Responsive layout kicks in when the screen is less than 600px wide */
        @media screen and (max-width: 600px) {
            .col-25, .col-75, input[type=submit] {
                width: 100%;
                margin-top: 0;
            }
        }
    </style>



</head>
<body id="ngbody">
    <a href="logout.php" style="color:white">Logout</a>
<header style="background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0, 0.4); /* Black w/opacity/see-through */
  color: white;
  font-weight: bold;
  border: 3px solid #f1f1f1;
  margin:auto;
  top: 50%;
  left: 50%;
  z-index: 2;
  width: 90%;
  padding: 5px;
  text-align: center;">
    <img src="img/logo.png">
    <h2 style=" text-align: center" id="greeting"></h2>
    <h3>Your Music Library</h3>
    <div id="navButtons">
        <button class="button" onclick="displaySingers()">Show all Singers</button>
        <button class="button" onclick="displayAlbums()">Show all Albums</button>
    </div>
    <div class="search-container">
    <form action="searchresults.php?album=">
    <input type="text" name="search" placeholder="Search Singers...">
    <input type="hidden" name="album" placeholder="Search albums...">
      <button type="submit" ><i class="fa fa-search"></i></button>
    </form>
    </div>
    <div class="search-container" style="float:right">
    <form action="searchresults.php?search=">
      <input type="hidden" name="search" placeholder="Search Singers...">
      <input type="text" name="album" placeholder="Search Albums...">
      <button type="submit" ><i class="fa fa-search"></i></button>
    </form>    
  </div>
    <br>
</header>

<div class="container" id="contentArea" style="width:90%; margin-left: auto; margin-right:auto">
    <p></p>
    <br>
    

</div>
<br>
</body>
</html>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="View.js" type="text/babel"></script>
<script src="contentLoader.js"> </script>


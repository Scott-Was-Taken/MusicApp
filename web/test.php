<?php
    $mysqli = new mysqli("localhost", "ng_user", "ng", "ng_music");
    $result = $mysqli->query("SELECT * FROM ng_albums");
    while ($row = $result->fetch_assoc()) {
    echo $row['album_name']."<br>";
}
?>

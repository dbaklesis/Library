<?php

$con = mysql_connect("localhost","lib","1234");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("Library", $con);

$result = mysql_query("SELECT * FROM lib_table_category");

$options="";   
$i = 1;
while($row = mysql_fetch_array($result))
  {
    $f=$row["CATEGORY"]; 
    $l=$row["CATEGORY"]; 
    $options.="<OPTION VALUE=\"$i\">".$f.' '.$l."</OPTION>"; 
    $i++;
  }
  
  echo ($options);

mysql_close($con);

?> 

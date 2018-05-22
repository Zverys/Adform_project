<?php

    require_once('dbConnect.php');

    if(mysqli_connect_error($con))
    {
        echo "Failed to Connect to Database ".mysqli_connect_error();
    }

    $query=mysqli_query($con,"SELECT * FROM categories");

       if($query)
       {
           while($row=mysqli_fetch_array($query))
           {
               $flag[]=$row;
           }

           echo json_encode($flag, JSON_UNESCAPED_UNICODE);
       }

    mysqli_close($con);
    exit();
    ?>

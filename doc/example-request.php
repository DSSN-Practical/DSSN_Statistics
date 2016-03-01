#!/usr/bin/php
<?php
// usage: php request.php [time] [userid]
$time = $argv[1];
$userid = $argv[2];

$requestURI = 'localhost/workspace/dssn/xodx/?c=stat&a=getStats&time='.$time;
$requestURI .= '&id='.urlencode($userid);

//$requestURI = 'localhost/workspace/dssn/xodx/?c=stat&a=readStore';

//$requestURI = "http://localhost/workspace/dssn/xodx/?c=feed&a=getFeed&uri=".urlencode($user);

// create curl resource
$ch = curl_init();
// set url
curl_setopt($ch, CURLOPT_URL, $requestURI);

//return the transfer as a string
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

// $output contains the output string
$output = curl_exec($ch);

// close curl resource to free up system resources
curl_close($ch);

echo $output.PHP_EOL;


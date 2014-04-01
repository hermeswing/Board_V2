<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.sliding_menu.css" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js" />
<script type="text/javascript" src="js/jquery.sliding_menu.js" />
<script type="text/javascript" src="js/lavalamp.js" />
<script>
    $(document).ready(function() {
        $('#menu ul').sliding_menu_js({
            header_title : 'Nombre sitio web!',
            header_logo : "http://placehold.it/250x120"
        });

    });
</script>
</head>
<body>

    <div id="framecontentTop">
        <div id="wrapper">
            <div class="lavalamp">
                <ul>
                    <li class="active"><a href="">Home</a></li>
                    <li><a href="">About</a></li>
                    <li><a href="">Blog</a></li>
                    <li><a href="">Services</a></li>
                    <li><a href="">Portfolio</a></li>
                    <li><a href="">Contacts</a></li>
                    <li><a href="">Back to Article</a></li>
                    <li><a href="">How it Works?</a></li>
                </ul>
                <div class="floatr"></div>
            </div>
        </div>
    </div>
    <div id="maincontent"></div>
    <div id="framecontentBottom">
        <div class="innertube">Sample text here</div>
    </div>
    <div class="container"></div>
</html>
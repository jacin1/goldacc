<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/jquery.ad-gallery.css">
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />


<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery.ad-gallery.js"></script>
<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
<SCRIPT type="text/javascript" src="js/jquery.layout.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.montage.min.js"></script>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		$('body').layout({
			applyDefaultStyles : true
		});
	});
	$(function() {
		var $container = $('#am-container'), $imgs = $container.find('img')
				.hide(), totalImgs = $imgs.length, cnt = 0;

		$imgs.each(function(i) {
			var $img = $(this);
			$('<img/>').load(function() {
				++cnt;
				if (cnt === totalImgs) {
					$imgs.show();
					$container.montage({
						fillLastRow : true,
						alternateHeight : true,
						alternateHeightRange : {
							min : 90,
							max : 240
						}
					});

					/* 
					 * just for this demo:
					 */
					$('#overlay').fadeIn(500);
				}
			}).attr('src', $img.attr('src'));
		});
	});
</SCRIPT>

<title>main page</title>
</head>
<body>
	<DIV class="ui-layout-center">
		<div class="am-container" id="am-container">
			<a href="#"><img src="images/1.jpg"></img></a> 
			<a href="#"><img src="images/2.jpg"></img></a> 
			<a href="#"><img src="images/3.jpg"></img></a>
			<a href="#"><img src="images/4.jpg"></img></a> 
			<a href="#"><img src="images/5.jpg"></img></a> 
			<a href="#"><img src="images/006.jpg"></img></a>
			<a href="#"><img src="images/7.jpg"></img></a> 
			<a href="#"><img src="images/008.jpg"></img></a> 
			<a href="#"><img src="images/9.jpg"></img></a>
			<a href="#"><img src="images/10.jpg"></img></a> 
			<a href="#"><img src="images/010.jpg"></img></a> 
			<a href="#"><img src="images/12.jpg"></img></a>
			<a href="#"><img src="images/13.jpg"></img></a> 
			<a href="#"><img src="images/14.jpg"></img></a> 
			<a href="#"><img src="images/15.jpg"></img></a>
			<a href="#"><img src="images/16.jpg"></img></a> 
			<a href="#"><img src="images/17.jpg"></img></a> 
			<a href="#"><img src="images/18.jpg"></img></a>
			<a href="#"><img src="images/19.jpg"></img></a>
			<a href="#"><img src="images/20.jpg"></img></a> 
			<a href="#"><img src="images/21.jpg"></img></a>
			<a href="#"><img src="images/22.jpg"></img></a>
		</div>
	</DIV>
	<DIV class="ui-layout-east">East</DIV>
	<DIV class="ui-layout-west">West</DIV>

</body>
</html>
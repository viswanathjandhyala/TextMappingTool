<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/mystyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Text Mapping</title>
<script src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<script src="js/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="http://207.170.206.166/js/data.js"></script> -->
<script type="text/javascript" src="js/data.js"></script>
</head>
<body>
	<div class="rightText" style="float: left">
		<table class="tblsearch">
			<tr class="search">
				<td><label>Text Mapping </label></td>
			</tr>
		</table>
		<div class="divmapper">
		</div>
	</div>
	<div class="Imgmapper" style="width:17px;height:20px">
		<img alt="Mapper" src="images/mapper.png" class="mapperImg display">
	</div>
	<div id="wrdsearchdiv" class="wrdsearchdiv" style="float: left">
		<div>
			<table class="tblsearch">
				<tr class="search">
					<td><label>Word Search: </label> <input type="text"
						name="wrdSearch" id="wrdSearch" class="wrdSearch" style="background-image: url(/GMTextMapping/images/search.png); background-repeat: no-repeat; flo"></td>
				</tr>
			</table>
		</div>
		<div class="searchResults"></div>
	</div>
	<div style="margin-right: 107px;">
			<input type="submit" value="Save Mapping" class="button">
		</div>
</body>
</html>
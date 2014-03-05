<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script src="js/jquery-1.9.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload a JSON</title>
<script>
function validateChooseFile(){
	var fileID = document.getElementById('file').value;
	if(fileID == ''){
		alert('Please choose a file to upload');
		return false;
	}
}
</script>
</head>
<body>
	<h3 align="center">GM Text Mapping Tool</h3>
	<div class="container" style="width: 50%; margin-left: 20em;">
		<div class="ui-widget-header">File Upload:</div>
		<div>Select a file to upload into Text Mapping Tool:</div>
		<br />
		<div class="ui-state-highlight ui-corner-all">
			<form action="uploadfile.jsp" method="post"
				enctype="multipart/form-data" onsubmit="return validateChooseFile()">
				<input type="file" id="file" name="file" size="50" /> <br /> <input
					type="submit" value="Upload" />
			</form>
		</div>
	</div>
</body>
</html>
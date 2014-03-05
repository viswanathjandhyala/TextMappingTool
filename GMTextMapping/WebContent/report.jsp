<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Example 1 - apply dataTable()</title>
<!-- <script src="js/jquery-1.9.1.js"></script> -->
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables_themeroller.css">
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript">
	$(document).ready(function() {
		
		$.getJSON("/GMTextMapping/rest/mappingservice/screenName/", function(data){
			$.each(data.Screen_Names,function(key, values){
				$('.screenNameSelection').append("<option value='"+values.screenName+"'>"+values.screenName+"</option>");
			});
		});
	
	/**
	 * On click of this it will get the value which is selected 
	 */
	 
	$('#testbutton').click(function(){
		var screenName = $('.screenNameSelection').val();
		if(screenName == 'ChooseScreenName'){
			alert("Select screen Name to proceed");
		}else{
			$('#container').css('display','block');
			$('#example').dataTable( {
				"sPaginationType" : "full_numbers",
				"bDestroy": true,
		        "sAjaxSource": "/GMTextMapping/rest/mappingservice/screenLayers/"+screenName,
		        "aoColumns": [
		            { "mDataProp": "screenId"},
		            { "mDataProp": "fileName"},
		            { "mDataProp": "screenName"},
		            { "mDataProp": "projectName"},
		            { "mDataProp": "layerName"},
		            { "mDataProp": "fieldWidth"},
		            { "mDataProp": "fontFamily"},
		            { "mDataProp": "fontSize"},
		            { "mDataProp": "fontStyle"},
		            { "mDataProp": "lblShortKey"}
		        ]
		    });
		}
	});
	});
</script>
</head>
<body>

	<table>
		<tr>
			<td><label>Select Screen Name: </label></td>
			<td><select class="form-control screenNameSelection">
					<option value="ChooseScreenName">Choose Screen Name</option>
			</select></td>
			<td><input type="submit" class="btn btn-primary" id="testbutton"></td>
		</tr>
	</table>
	<div id="container" style="display: none;">
	<table cellpadding="0" cellspacing="0" class="dataTable" border="0"
		id="example">
		<thead>
			<tr>
				<th>Screen ID</th>
				<th>File Name</th>
				<th>Screen Name</th>
				<th>Project Name</th>
				<th>Layer Name</th>
				<th>Field Width</th>
				<th>Font Family</th>
				<th>Font Size</th>
				<th>Font Style</th>
				<th>Short Key</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
</body>
</html>
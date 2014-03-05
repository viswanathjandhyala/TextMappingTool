<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String json = (String) request.getAttribute("json");
	String jsonpar = (String) request.getParameter("json");
	System.out.println("json from JSP - " + jsonpar);
	String fileName = (String) request.getAttribute("fileName");
	String[] fspNames = fileName.split("_");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Text Mapping</title>

<link rel="stylesheet" type="text/css" href="css/newmystyle.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">

<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/labels.js"></script>
<script type="text/javascript" src="js/dynamicvariables.js"></script>
</head>
<body>
	<h3 align="center">GM Text Mapping Tool</h3>
	<div class="savemappedData" style="display: none"></div>
	<div class="uploadJSON" style="display: none;">
		<textarea class="textareajson" rows="" cols=""><%=json%></textarea>
	</div>
	<!-- capture details -->
	<!-- <div id="cptrFileDtls" style="width: 50%; margin-left: auto; margin-right: auto; padding: 10px;">
		<div style="float: left;">
			<div>
				<label style="float: left;">Project Name:</label> 
				<input type="text" id="txtPrjNmId" style="float: right;"/>
			</div>
		</div>
		<div style="float: right;">
			<div>
				<label style="float: left;">Model Year:</label>
				<input type="text" id="txtMdlYrId" style="float: left;"/>
			</div>
		</div>
		<div style="float: left;">
			<div>
				<label style="float: left;">Domain:</label> 
				<input type="text" id="txtDmNmId" style="float: right;"/>
			</div>
		</div>
		<div style="float: right;">
			<div>
				<label style="float: left;">Screen:</label> 
				<input type="text" id="txtScrNmId" style="float: left;"/>
			</div>
		</div>
	</div> <br/> -->
	<div>&nbsp;</div>
	<div class="bg-primary row">
		<div class="nameBox col-md-4">
			File Name:
			<span class='fileName'><%=fileName%></span></div>
		<div class="nameBox col-md-4">
			Screen Name:
			<span class="screenName"><%=fspNames[1]%></span></div>
		<div class="nameBox col-md-4">
			Project Name:
			<span class="projectName"><%=fspNames[0]%></span></div>
	</div>
	<br />
	<div class="layerMapheader">
		<div style="text-align: center;">
			<label class="divlabel" style="text-align: center;">Photoshop
				Screen</label>
		</div>
		<div id="tabs">
			<ul>
				<li class="tab" id="labels"><a href="#tab-1">Labels</a></li>
				<li class="tab" id="dynamic"><a href="#tab-2">Dynamic
						Variables</a></li>
				<li class="touchtab"><a href="#tab-3">Touch Zones</a></li>
			</ul>
			<div class="labelstab" id="tab-1"
				style="overflow: auto; height: 180px; font-weight: normal;"></div>
			<div class="dynamictab" id="tab-2"></div>
			<div id="tab-3"></div>
		</div>
		<div class="labelsbutton" style="display: block;">
			<input type="button" class="btn btn-default" id="mapTermButton"
				value="Map Term" /> <input type="button"
				class="btn btn-default" id="saveMapping" value="Save Mapping" /> <input
				type="button" class="btn btn-primary" id="newMapping"
				value="New Mapping" style="display: none;" />
		</div>
		<div class="dynamicbutton" style="display: none;">
			<input type="button" class="btn btn-default" id="dynamicMapButton"
				value="Map Term" /> <input type="button"
				class="btn btn-default" id="dynamicSaveMapping" value="Save Mapping" /> <input
				type="button" class="btn btn-primary" id="newMapping"
				value="New Mapping" style="display: none;" />
		</div>
	</div>
	<div class="engMasterheader" style="display: block;">
		<div style="text-align: center;">
			<label class="divlabel">English Master</label>
		</div>
		<div>
			<div style="background-color: gray;">
				<div style="float: left;">
					<b>Search Term: &nbsp;</b>
				</div>
				<div style="float: left;">
					<input type="text" name="wrdSearch" class="wrdSearch">
				</div>
				<div style="float: left;">
					<button id="labelsSrchBtnId" class="btn btn-info"
						onclick="javascript:void(0);">Search</button>
				</div>
				<div style="float: left;">
					<ul id="theList" style="display: none;">
					</ul>
				</div>
			</div>
			<div class="englishMaster" style="float: left; width: 100%;"></div>
			<div style="float: left; margin-left: 22em;">
				<input type="button" class="btn btn-primary" id="newRequestLabels"
					style="margin-top: 20px;" value="New Request" />
			</div>
		</div>
		<!-- <div class="button">
		</div> -->
	</div>

	<div class="variableMasterHeader" style="display: none;">
		<div style="text-align: center;">
			<label class="divlabel">Variables</label>
		</div>
		<div>
			<div style="background-color: gray;">
				<div style="float: left;">
					<b>Search Term: &nbsp;</b>
				</div>
				<div style="float: left;">
					<input type="text" name="dynamicWrdSearch" class="dynamicWrdSearch">
				</div>
				<div style="float: left;">
					<button id="dynamicSrchBtnId" class="btn btn-info"
						onclick="javascript:void(0);">Search</button>
				</div>
			</div>
			<div class="variableMaster" style="float: left; width: 100%;"></div>
		</div>
		<div style="float: left; margin-left: 22em;">
			<input type="button" class="btn btn-primary" id="newRequestDynVar"
				style="margin-top: 20px;" value="New Request" />
		</div>
	</div>

	<div class="addedScreenDetails"></div>
	<div class="addedScreenLayers"></div>

	<div id="dialog-form" class="dialogbody" style="display: none;"
		title="New Term Request">
		<p class="validateTips">All form fields are required.</p>

		<form>
			<fieldset class="fieldset" style="font-size: smaller;">
				<label for="newTerm">New Term</label> <input class="newTermText"
					type="text" name="newTerm" id="newTerm" disabled="disabled"
					class="text ui-widget-content ui-corner-all"> <label
					for="termDesc">Term Description</label>
				<textarea rows="3" cols="45" name="termDesc" id="termDesc"
					class="ui-widget textarea"></textarea>
			</fieldset>
		</form>
	</div>
	
	<div id="dialog-capture-file-details" class="dialogbody flDtlsCls" style="display: none;">
		<p class="validateTips">Please enter the details below. All fields are required.</p>
		<form>
			<fieldset class="fieldset">
				<label for="prjNme">Project Name: </label>
				<input id="prjNameTxtId" type="text" name="prjNme" class="text" value=""/>
				<label for="mdlYr">Model Year: </label>
				<input id="mdlYrTxtId" type="text" name="mdlYr" class="text" value=""/>
				<label for="dmn">Domain: </label>
				<input id="dmnTxtId" type="text" name="dmn" class="text" value=""/>
				<label for="scrnNme">Screen Name: </label>
				<input id="scrnNmeTxtId" type="text" name="scrnNme" class="text" value=""/>
			</fieldset>
		</form>
	</div>
</body>
</html>
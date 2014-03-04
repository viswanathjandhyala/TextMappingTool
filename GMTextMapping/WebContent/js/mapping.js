$(document).ready(function() {

	/*
	 * The value which we are getting after reading the file
	 * */
	jsonObj = [];
	var jsonData = $('.textareajson').val();
	dataFromFile = JSON.parse(jsonData);
	$('.saveMapping').attr('disabled','disabled');
	$('.newRequestq').attr('disabled','disabled');
	$('.wrdSearch').val('');
	$('#tabs').tabs();
	var lblsLength = '';
	var lbldata = '';
	var fileName = '';
	var screenName = '';
	var projectName = '';
	$('.labelstab').empty();
	lblsLength = dataFromFile.Screen.LabelWidget.length;
	var textMapping = "<table class=mapper>";
	textMapping = textMapping+"<tr class=header class=odd>";
	textMapping = textMapping+"<th>Layer Name</th>";
	textMapping = textMapping+"<th style='border-right: thin solid #A0A0A0;'>Field Max Width</th>";
	textMapping = textMapping+"<th>Translation Max Width</th>";
	textMapping = textMapping+"<th>Mapped</th>";
	textMapping = textMapping+"</tr>";
	var i = 1;
	var j = 0;
	lbldata = dataFromFile;
	$.each(dataFromFile.Screen.LabelWidget, function(key, values) {
		var trclass;
		if(i%2==0)
			trclass = "odd mappertrClick";
		else
			trclass = "even mappertrClick";
		if (dataFromFile.Screen.LabelWidget[j].ID.indexOf('$') == -1) {
			textMapping = textMapping+'<tr class="'+trclass+'">';
			textMapping = textMapping+"<td class='layerName'>" +
			"<img class='sideImg imgMapper' src=images/sidearrow.png rownum=" + i + ">";
			textMapping = textMapping + dataFromFile.Screen.LabelWidget[j].Value.Text;
			textMapping = textMapping+"</td>" 
			+"<td class=fieldMWidth>"+dataFromFile.Screen.LabelWidget[j].Size.SizeW+'px'+"</td>"
			+"<td class=trnsMWidth></td>"
			+"<td class=imageMapped></td>";
			textMapping = textMapping+"</tr>";

			/* row for font information; by default it is hidden  */
			textMapping = textMapping + "<tr class='details-" + i + " hidden'><td class='fontname' colspan='4'><b>Font: </b>"+dataFromFile.Screen.LabelWidget[j].Font.Name+", "
			+dataFromFile.Screen.LabelWidget[j].Font.Size+"px, ";
			if (dataFromFile.Screen.LabelWidget[j].Font.Decoration == 0) {
				textMapping = textMapping + "Light / Regular";
			} else if (dataFromFile.Screen.LabelWidget[j].Font.Decoration == 1) {
				textMapping = textMapping + "Bold";
			} else if (dataFromFile.Screen.LabelWidget[j].Font.Decoration == 2) {
				textMapping = textMapping + "Italic";
			} else if (dataFromFile.Screen.LabelWidget[j].Font.Decoration == 3) {
				textMapping = textMapping + "Underlined";
			}
			textMapping = textMapping +"</td></tr>";
		}
		i++;j++;
	});
	textMapping = textMapping+"</table>";
	$(".labelstab").append(textMapping);

	/**
	 * This will display the values in dynamic variables tab
	 */

	var l = 1;
	var k = 0;
	var dynamicVariables = "<table class=mapper>";
	dynamicVariables = dynamicVariables+"<tr class=header class=odd>";
	dynamicVariables = dynamicVariables+"<th>Layer Name</th>";
	dynamicVariables = dynamicVariables+"<th>Mapped</th>";
	dynamicVariables = dynamicVariables+"</tr>";
	$.each(dataFromFile.Screen.LabelWidget, function(key, values) {

		var trclass;
		if(l%2==0)
			trclass = "odd dynamicmappertrClick";
		else
			trclass = "even dynamicmappertrClick";
		if (dataFromFile.Screen.LabelWidget[k].ID.indexOf('$') != -1) {
			dynamicVariables = dynamicVariables+'<tr class="'+trclass+'">';
			dynamicVariables = dynamicVariables+"<td class=dynamiclayerName>"+dataFromFile.Screen.LabelWidget[k].Value.Text+"</td>";
			dynamicVariables = dynamicVariables+"<td class=dynamicimageMapped></td>";
			dynamicVariables = dynamicVariables+"</tr>";
		}
		l++;k++;
	});
	dynamicVariables = dynamicVariables+"</table>";
	$(".dynamictab").append(dynamicVariables);


	/**
	 * This click is for displaying the variables div on click of dynamic variables tab
	 */

	$('.dynamic').click(function(){
		$('.wrdSearch').val('');
		$('.engMasterheader').css('display','none');
		$('.variableMaster').css('display','block');
	});

	/**
	 * This click is for displaying the English master div on click of labels tab
	 */

	$('.labels').click(function(){
		$('.dynamicWrdSearch').val('');
		$('.engMasterheader').css('display','block');
		$('.variableMaster').css('display','none');
	});

	/*The above two click functions will hide or show the div based on the tab selected*/


	/**
	 *  This will highlight the row selected in the dynamic variables tab, the selected value will get populated in search term in the variables tab
	 */

	$('.dynamicmappertrClick').click(function(){
		console.log('clciked');
		$('.highlight').removeClass('highlight');
		$(this).addClass('highlight');
		var valueSelected = $(this).find(".dynamiclayerName").text();
		$('.dynamicWrdSearch').val(valueSelected);
		$('.englishMaster').empty();
	});



	$('.mappertrClick').click(function(){
		$('.highlight').removeClass('highlight');
		$(this).addClass('highlight');
		/*$('.saveMapping').attr('disabled','disabled');*/
		var valueSelected = $(this).find(".layerName").text();
		$('.fieldMWidth').val('');
		$('.trnsMWidth').val('');
		$('.wrdSearch').val(valueSelected);
		$('.englishMaster').empty();
		searchTerm();
	});

	$('.imgMapper').click(function(e){
		if($(this).hasClass('sideImg')){
			$(this).addClass('downImg');
			$(this).removeClass('sideImg');
			$(this).removeAttr('src');
			$(this).attr('src','images/downarrow.png');
			var rowNum = $(this).attr('rownum');
			rowNum = '.details-' + rowNum;
			$(rowNum).removeClass('hidden');
		}else{
			$(this).addClass('sideImg');
			$(this).removeClass('downImg');
			$(this).removeAttr('src');
			$(this).attr('src','images/sidearrow.png');
			var rowNum = $(this).attr('rownum');
			rowNum = '.details-' + rowNum;
			$(rowNum).addClass('hidden');
		}
	});

	var textString = '';
	var content = '';
	function valueOf(newTerm){
		$(function() {
			$('.newTermText').val(newTerm);
			$( "#dialog-form" ).dialog({
				autoOpen: false,
				height: 350,
				width: 370,
				modal: true,
				buttons: {
					"Submit": function() {
						textString = $('#newTerm').val();
						content = $('#termDesc').val();

						$.post("/GMTextMapping/rest/mappingservice/newterm/"+textString+","+content)
						.done(function(data) {
							$.each(data.insert, function(key, values) {
								if(values.value == true){
									$(".englishMaster").empty();
									searchTerm();
									content = $('#termDesc').val('');
								}else if(values.value == false){
									$(".englishMaster").empty();
									$(".englishMaster").append("<h1 style='color: red; font-size: large;text-align: center;'> Error while adding new term </h1>");
								}
							});
						});
						$( this ).dialog( "close" );
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
				}
			});
		});
	}


	var availableTags = [];
	$.getJSON("/GMTextMapping/rest/mappingservice/textstring",
			function (data){
		$.each(data.textString, function(key, values) {
			$('#theList').append("<li>"+values.term+"</li>");
			availableTags.push(values.term);
		});
	});

	$(".newRequestq").click(function() {
		$('.dialogbody').css('display','block');
		valueOf($('.wrdSearch').val());
		$("#dialog-form").dialog("open");
	});

	$(function() {
		$(".wrdSearch").autocomplete({
			minLength: 3,
			source: availableTags
		});
	});

	$('.wrdSearch').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == "13"){
			if($(".wrdSearch").val()==""){
				searchTerm();
			}else if($(".wrdSearch").val()!=""){
				searchTerm();
			}else{
				searchTerm();
			}
		}
		event.stopPropagation();
	});

	$("#srchBtnId").click(function() {
		if($(".wrdSearch").val()==""){
			searchTerm();
		}else if($(".wrdSearch").val()!=""){
			searchTerm();
		}else{
			searchTerm();
		}
	});

	function saveMappedData(stringJSON){
		window.open('./MappingServlet?jsonString='+stringJSON);
	}

	/*
	 * This is the function which is used to save screen details.
	 */

	function addScreenDetails(fileName, screenName, projectName){
		var success = false;
		$.post("/GMTextMapping/rest/mappingservice/addscreen/"+fileName+","+screenName+","+projectName)
		.done(function(data) {
			$.each(data.inserted, function(key, values) {
				if(values.addedfile == true){
					$('.addedScreenDetails').empty();
					/*$('.addedScreenDetails').append("<h3 style='color: green; font-size: large;'>"+fileName+" Details Added</h3>");*/
					success = true;
				}
			});
		});
		return success;
	}

	/*
	 * This is the function which is used to save the screen layer details which are mapped.
	 */
	var mappedData = '';
	function addScreenLayers(fileName, screenName, projectName, jsonObj){
		mappedData = JSON.stringify(jsonObj);
		var success = false;
		$.post("/GMTextMapping/rest/mappingservice/mappeddata?mappedData="+mappedData+"&fileName="+fileName+"&screenName="+screenName+"&projectName="+projectName)
		.done(function(data) {
			$.each(data.insert, function(key, values) {
				if(values.inserted == true){
					$('.addedScreenLayers').empty();
					/*$('.addedScreenLayers').append("<h3 style='color: green; font-size: large;'>Screen Layers Details Added</h3>");*/
					success = true;		/* when values inserted */
				}else{
					$('.addedScreenLayers').empty();
					/*$('.addedScreenLayers').append("<h3 style='color: red; font-size: large;'>Error while Adding Screen Layer Details</h3>");*/
					success = false;	/* when values not inserted */
				}
			});
		});
		//	jsonObj = [];	/* this is a fail safe; not sure of this is happening in below event */
		return success;
	}

	fileName = $('.fileName').text();
	screenName = $('.screenName').text();
	projectName = $('.projectName').text();

	/* 
	 * saves the mapping and lets the user download a JSON file 
	 */
	$('.saveMapping').click(function(){
		jsonString = JSON.stringify(lbldata);
		var screenLayers = addScreenLayers(fileName, screenName, projectName, jsonObj);
		console.log(screenLayers);
		//if(screenDetails == true && screenLayers == true){ 	/* something seems fishy here */
		if(true == true && true == true){						/* enabling download of file for now */
			if(count == lblsLength){
				saveMappedData(jsonString);
			}else{
				var r=confirm("You have not mapped all the terms, do you want to save the mapping anyways?");
				if(r==true){
					saveMappedData(jsonString);
				}
			}
		}
		$('.mapTermButton').hide();
		$('.saveMapping').hide();
		$('.newMapping').show();
	});
	var count = 0;
	var shortkey = '';
	var layerName = '';
	var fieldWidth = '';
	var fontFamily = '';
	var fontSize = '';
	var fontStyle = '';
	var textString = '';
	
	$('.mapTermButton').click(function(e){
		/* save the screen details in the DB */
		addScreenDetails(fileName, screenName, projectName);
		
		/* get values from Photoshop Screen section*/
		layerName = $('.highlight').find('.layerName').text();
		fieldWidth = $('.highlight').find('.fieldMWidth').text();
		fontFamily = $('.highlight').find('.fontname').text();
		fontSize = $('.highlight').find('.fontsize').text();
		fontStyle = $('.highlight').find('.fonttype').text();
		
		/* get the text from English Master section */
		textString = $('.highlightdiv').find('.textString').text();
		
		/* check if any value is selected in English Master section else throw an alert */
		if (textString == '' || textString == undefined) {
			alert('Please select a term from English Master and continue mapping activity.');
			/*$('#errorMsgDlgDiv').html('Please select a term from English Master and continue mapping activity.');
			$('#errorMsgDlgDiv').dialog();*/
		} else {	/* if a value is selected proceed with other logic */
			var i=0;
			if(!textString == ''){
				$.getJSON("/GMTextMapping/rest/mappingservice/shortkey/"+textString, function(data){
					$.each(data.id, function(key, values) {
						shortkey = values.shortkey;
					});
					if (layerName != textString) {	/* check if values in PS and EM are not same */
						var proceed = confirm("The selected Layer Name and Text String do not match (case sensitive also), do you want to map this term?");
						if (proceed) {
							$.each(lbldata.Screen.LabelWidget, function(key, values) {
								if (lbldata.Screen.LabelWidget[i].Value.Text == layerName) {
									lbldata.Screen.LabelWidget[i].shortkey = shortkey;
								}
								i++;
							});
							trnsMWidth =  $('.highlight').find('.trnsMWidth').text();
							fieldMWidth =  $('.highlight').find('.fieldMWidth').text();
							if(!trnsMWidth == ''){
								trnsMWidth = parseInt(trnsMWidth.replace('px',''));
								fieldMWidth = parseInt(fieldMWidth.replace('px',''));
								if(trnsMWidth > fieldMWidth){
									$(".labelstab  .highlight").find(".imageMapped").html('<img src="images/cross.png" />');
									$('.highlight').find('.trnsMWidth').css('color','red');
									$('.highlight').find('.trnsMWidth').css('font-weight','bold');
									alert('This field width cannot accomodate the translation max width, you might have to change the field width or font size. Choose wisely!');
									$('.saveMapping').removeAttr('disabled');
								}else{
									$(".labelstab  .highlight").find(".imageMapped").html('<img src="images/tick.png" />');
									$('.highlight').find('.mappertrClick').addClass('tick');
									$('.highlight').find('.trnsMWidth').css('font-weight','');
									$('.saveMapping').removeAttr('disabled');
								}

							}
						} else {
							//do nothing
						}
					} else {
						$.each(lbldata.Screen.LabelWidget, function(key, values) {
							if (lbldata.Screen.LabelWidget[i].Value.Text == layerName) {
								lbldata.Screen.LabelWidget[i].shortkey = shortkey;
							}
							i++;
						});
						trnsMWidth =  $('.highlight').find('.trnsMWidth').text();
						fieldMWidth =  $('.highlight').find('.fieldMWidth').text();
						if(!trnsMWidth == ''){
							trnsMWidth = parseInt(trnsMWidth.replace('px',''));
							fieldMWidth = parseInt(fieldMWidth.replace('px',''));
							if(trnsMWidth > fieldMWidth){
								$(".labelstab  .highlight").find(".imageMapped").html('<img src="images/cross.png" />');
								$('.highlight').find('.trnsMWidth').css('color','red');
								$('.highlight').find('.trnsMWidth').css('font-weight','bold');
								alert('This field width cannot accomodate the translation max width, you might have to change the field width or font size. Choose wisely!');
								$('.saveMapping').removeAttr('disabled');
							}else{
								$(".labelstab  .highlight").find(".imageMapped").html('<img src="images/tick.png" />');
								$('.highlight').find('.mappertrClick').addClass('tick');
								$('.highlight').find('.trnsMWidth').css('font-weight','');
								$('.saveMapping').removeAttr('disabled');
							}

						}
					}
					
					item = {};
					item['layerName'] = layerName;
					item['fieldWidth'] = fieldWidth;
					item['fontFamily'] = fontFamily;
					item['fontSize'] = fontSize;
					item['fontStyle'] = fontStyle;
					item['shortkey'] = shortkey;
					item['fileName'] = fileName;
					item['screenName'] = screenName;
					item['projectName'] = projectName;
					jsonObj.push(item);
				});
			}
		}

		count++;
		$('.savemappedData').empty();
	});

	function searchTerm(){
		$(".englishMaster").empty();
		var wrdSearch = $('.wrdSearch').val();
		if(!wrdSearch==""){
			$.getJSON("/GMTextMapping/rest/mappingservice/searchdata/"+wrdSearch, function(data){
				if(data.results.length == 0){
					$(".englishMaster").append("<h1 style='color: red; font-size: x-large;text-align: center;'>No Data Found</h1>");
					$('.newRequestq').removeAttr('disabled');
				}else{
					$('.newRequestq').attr('disabled','disabled');
					var englishMaster = "<table class=mapEngTerm>";
					englishMaster = englishMaster+"<tr class=header class=odd>";
					englishMaster = englishMaster+"<th>Text String</th>";
					englishMaster = englishMaster+"<th>Translation Max Width</th>";
					englishMaster = englishMaster+"</tr>";
					var i = 1;
					$.each(data.results, function(key, values) {
						var trclass;
						if(i%2==0)
							trclass = "odd mapEngTermtrClick";
						else
							trclass = "even mapEngTermtrClick";
						englishMaster = englishMaster+'<tr class="'+trclass+'">';
						englishMaster = englishMaster+"<td class=propertyname style='display: none'>"+values.propertyname+"</td>"
						+"<td class='textString' style='padding-left: 10px;'>"+values.textstring+"</td>"
						+"<td class='MaxPixelWidth' style='width: 20%; padding-left: 10px;'>"+values.maxpixelwidth+'px'+"</td>";
						englishMaster = englishMaster+"</tr>";
						i++;
					});
					englishMaster = englishMaster+"</table>";
					$(".englishMaster").append(englishMaster);
					$('.mapEngTermtrClick').click(function(){
						$('.highlightdiv').removeClass('highlightdiv');
						$(this).addClass('highlightdiv');
					});
				}
				$('.mapEngTermtrClick').click(function(){
					var maxwidth = '';
					maxwidth =  $(this).find('.MaxPixelWidth').text();
					propertyname = $(this).find('.propertyname').text();
					$('.highlight').find('.trnsMWidth').text(maxwidth);
				});
			});
		}else{
			$(".englishMaster").empty();
			$(".englishMaster").append("No Data To Display");
		}
	}

	$('.newMapping').click(function(){
		window.location.replace('/GMTextMapping/');
	});
});

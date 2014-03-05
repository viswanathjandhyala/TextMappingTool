$(document).ready(function() {

	dataFromFile = JSON.parse(jsonData);
	$('#newRequestDynVar').attr('disabled','disabled');
	
	/**
	 * This will display the values in dynamic variables tab
	 */
	
	var i = 1;
	var j = 0;
	var dynamicVariables = "<table class=mapper>";
	dynamicVariables = dynamicVariables+"<tr class=header class=odd>";
	dynamicVariables = dynamicVariables+"<th style='border-right: thin solid #A0A0A0;'>Layer Name</th>";
	dynamicVariables = dynamicVariables+"<th>Mapped</th>";
	dynamicVariables = dynamicVariables+"</tr>";
	$.each(dataFromFile.Screen.LabelWidget, function(key, values) {

		var trclass;
		if(i%2==0)
			trclass = "odd dynamicmappertrClick";
		else
			trclass = "even dynamicmappertrClick";
		if (dataFromFile.Screen.LabelWidget[j].ID.indexOf('$') != -1) {
			dynamicVariables = dynamicVariables+'<tr class="'+trclass+'">';
			dynamicVariables = dynamicVariables+"<td class=dynamiclayerName>"+dataFromFile.Screen.LabelWidget[j].Value.Text+"</td>";
			dynamicVariables = dynamicVariables+"<td class=dynamicimageMapped></td>";
			dynamicVariables = dynamicVariables+"</tr>";
		}
		i++;j++;
	});
	dynamicVariables = dynamicVariables+"</table>";
	$(".dynamictab").append(dynamicVariables);
	
	/**
	 * This click is for displaying the variables div on click of dynamic variables tab
	 */
	
	$('#dynamic').click(function(){
		$('.wrdSearch').val('');
		$('.engMasterheader').css('display','none');
		$('.variableMasterHeader').css('display','block');
		$('.dynamicbutton').css('display','block');
		$('.labelsbutton').css('display','none');
		$('.englishMaster').empty();
	});


	var dynamicLayerName = '';
	$('#dynamicMapButton').click(function(){
		dynamicLayerName = $('.dynamichighlight').find('.dynamiclayerName').text();
		if(dynamicLayerName == ''){
			alert('Please select a Layer Name from Dynamic Variables and continue with mapping');
		}else{
			alert(dynamicLayerName);
		}
	});
	
	/**
	 *  This will highlight the row selected in the dynamic variables tab, 
	 *  the selected value will get populated in search term in the variables tab
	 */
	
	$('.dynamicmappertrClick').click(function(){
		$('.dynamichighlight').removeClass('dynamichighlight');
		$(this).addClass('dynamichighlight');
		var valueSelected = $(this).find(".dynamiclayerName").text();
		$('.dynamicWrdSearch').val(valueSelected);
		$('.englishMaster').empty();
	});
});
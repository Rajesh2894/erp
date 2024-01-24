$(document).ready(function() {
	$("#hoardingRegDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		//minDate : '0d',
		maxDate : '0d',
		yearRange : "-100:+20",
	});
	
	$("#hoardingRegDate").keyup(function(e) {
	    if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
		    $(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
		    $(this).val($(this).val() + "/");
		}
	    }
	});
	$("#hoardingTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	var dateFields = $('.datepicker');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	if($("#saveMode").val() == 'V'){
		$("#hoardingMasterEntry input").prop("disabled", true);
		$("#hoardingMasterEntry select").prop("disabled", true);
		$("#hoardingMasterEntry textArea").prop("disabled", true);
	}
	
	$("#searchHoarding").click(function() {
		
		var errorList = [];
		var hoardingNumber = $('#hoardingNumber').val();
		var hoardingStatus = $("#hoardingStatus").val();
		if(hoardingStatus == '0'){
			hoardingStatus ='';
		}
		var hoardingType = $("#hoardingTypeId1").val();
		if(hoardingType == '0'){
			hoardingType ='';
		}
		var hoardingSubType = $("#hoardingTypeId2").val();
		if(hoardingSubType == '0'){
			hoardingSubType ='';
		}
		var hoardingSubType3 = $("#hoardingTypeId3").val();
		if(hoardingSubType3 == '0' || hoardingSubType3 == undefined){
			hoardingSubType3 ='';
		}
		var hoardingSubType4 = $("#hoardingTypeId4").val();
		if(hoardingSubType4 == '0' || hoardingSubType4 == undefined){
			hoardingSubType4 ='';
		}
		var hoardingSubType5 = $("#hoardingTypeId5").val();
		if(hoardingSubType5 == '0' || hoardingSubType5 == undefined){
			hoardingSubType5 ='';
		}
		var hoardingLocation = $("#locationId").val();
		if(hoardingLocation == '0'){
			hoardingLocation ='';
		}
		if (hoardingNumber != '' || hoardingStatus != '' || hoardingType != '' || hoardingSubType != '' 
			|| hoardingSubType3 != '' ||hoardingSubType4 != '' || hoardingSubType5 != '' || hoardingLocation != '' ){
			var requestData = '&hoardingNumber='+ hoardingNumber + 
			'&hoardingStatus='	+ hoardingStatus + 
			'&hoardingType=' + hoardingType + 
			'&hoardingSubType=' + hoardingSubType +
			'&hoardingSubType3=' + hoardingSubType3 +
			'&hoardingSubType4=' + hoardingSubType4 +
			'&hoardingSubType5=' + hoardingSubType5 + 
			'&hoardingLocation=' + hoardingLocation;
			var table = $('#hoardingTable').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest(
					'HoardingMaster.html?searchHoardingMaster',
					'POST', requestData, false,'json');
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("adh.validate.search"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
			$.each(ajaxResponse,function(index) {				
				var obj = ajaxResponse[index];
				result.push([
					obj.hoardingNumber,
					obj.hoardingOldNumber,
					obj.hrdFmtDate,
					obj.hoardingDescription,
					obj.hoardingTypeIdDesc,
					'<td >'
					+ '<button type="button" class="btn btn-blue-2 btn-sm margin-right-5"  onclick="editAndViewHoardingMaster(\''
					+ obj.hoardingId
					+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
					+ '<button type="button" class="btn btn-warning btn-sm" onclick="editAndViewHoardingMaster(\''
					+ obj.hoardingId
					+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>' ]);
			});
			table.rows.add(result);
			table.draw();
			/*Defect #153303*/
			var tableColSelector = $('table#hoardingTable tbody tr td');
			if(tableColSelector.children('button')) {
				tableColSelector.addClass('text-center');
			}
		}else{
			errorList.push(getLocalMessage('adh.select.any.field'));
			displayErrorsOnPage(errorList);
		}
	});
});

function calcuateArea(){
	var add = 0.00;	
	var length = parseFloat($("#hoardingLength").val());
	var height = parseFloat($("#hoardingHeight").val());
	if(!isNaN(length) && !isNaN(height) ){
		 add = parseFloat(length * height);
	}
	$("#hoardingArea").val(add.toFixed(2));
}

function addHoardingMaster(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#hrdNumber').hide();
	prepareTags();
}

function save(element) {

	var errorList = [];
	errorList = validateForm(errorList, saveMode)	
	if (errorList.length == 0) {		
		var locationId = $("#locationId").val();
		if(locationId != null && locationId != ''){
			var requestData = {
					"locationId" : locationId
					}
			var ajaxResponse = __doAjaxRequest(
					'HoardingMaster.html?getLocationMapping',
					'POST', requestData, false,'json');
			if(ajaxResponse == "N"){
				errorList.push(getLocalMessage('adh.mapLoc.operationalWardZone'));
				displayErrorsOnPage(errorList);	
				return false;
			}else{
				return saveOrUpdateForm(element, 'Hoarding Master saved successfully',
						'HoardingMaster.html', 'saveform');
			}				
		} 		
	}else{
		displayErrorsOnPage(errorList);	 
	}
}
/*function getLocationMapping(errorList) {
	debugger;
	var locationId = $("#locationId").val();
	if(locationId != '' || locationId != null){
		var requestData = {
				"locationId" : locationId
		};
		var ajaxResponse = __doAjaxRequest(
				'HoardingMaster.html?getLocationMapping',
				'POST', requestData, false,'json');
		if(ajaxResponse == "N"){
			errorList.push(getLocalMessage('Please map location and operational ward zone'));
		}
	}
	return errorList;
}*/

function editAndViewHoardingMaster(hoardingId, mode) {
	
	var divName = '.content-page';
	var requestData = {
			"saveMode" : mode,
			"hoardingId" : hoardingId
	};
	var ajaxResponse = doAjaxLoading('HoardingMaster.html?EDIT', requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
 

function validateForm(errorList, saveMode) {
	
	var hoardingRegDate = $("#hoardingRegDate").val();
	var description = $("#hoardingDescription").val();
	var hoardingType = $("#hoardingTypeId1").val();
	var length = $("#hoardingLength").val();
	var height = $("#hoardingHeight").val();
	var hoardingArea = $("#hoardingArea").val();
	var locationId = $("#locationId").val();
	var status = $("#hoardingStatus").val();
	var landOwnership = $("#hoardingPropTypeId").val();
	//var hoardingZone = $("#hoardingZone1").val();
	/*var hoardingDirection = $("#displayTypeId").val();*/

	if (hoardingRegDate == "" || hoardingRegDate == undefined
			|| hoardingRegDate == null) {
		errorList.push(getLocalMessage('hoarding.master.validate.regdate'));
	}
	if (description == "" || description == undefined || description == null) {
		errorList.push(getLocalMessage('hoarding.master.validate.description'));
	}
	if (hoardingType == "" || hoardingType == undefined || hoardingType == null) {
		errorList.push(getLocalMessage('hoarding.master.validate.hrdtype'));
	}
	if (length == "" || length == undefined || length == null) {
		errorList.push(getLocalMessage('hoarding.master.validate.hrdlength'));
	}
	if (height == "" || height == undefined || height == null) {
		errorList.push(getLocalMessage('hoarding.master.validate.height'));
	}
	if (locationId == "" || locationId == undefined || locationId == null) {
		errorList.push(getLocalMessage('hoarding.master.validate.locationId'));
	}
	if (status == "" || status == undefined || status == null || status == 0) {
		errorList.push(getLocalMessage('hoarding.master.validate.status'));
	}
	if (landOwnership == "" || landOwnership == undefined || landOwnership == null || landOwnership == 0) {
		errorList
		.push(getLocalMessage('hoarding.validate.land.ownership'));
	}
	/*if (hoardingZone == "" || hoardingZone == undefined || hoardingZone == null || hoardingZone == 0) {
		errorList
		.push(getLocalMessage('hoarding.master.validate.hoardingZone'));
	}*/
	/*if (hoardingDirection == "" || hoardingDirection == undefined
			|| hoardingDirection == null || hoardingDirection == 0) {
		errorList
		.push(getLocalMessage('hoarding.master.validate.displayTypeId'));
	}*/
	return errorList;
}

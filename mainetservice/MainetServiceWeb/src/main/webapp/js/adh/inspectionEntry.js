$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
	});
	//Restrict space at first position in text
	$("textarea").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
});

$(function() {
	$("#inspectionTableId").on('click', '.addInspection', function(e) {
	
		var errorList = [];
		 errorList = validateDetails(errorList);
		if (errorList.length == 0) {
			$('.error-div').hide();
			e.preventDefault();	
			var clickedRow = $('#inspectionTableId tr').length-2;	
			var content = $("#inspectionTableId").find('tr:eq(1)').clone();
			$("#inspectionTableId").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val("0");
			content.find("textarea").val("");
			reOrderInspectionIdSequence('.inspectionEntryTr');
		}else{
			displayErrorsOnPage(errorList);
		}	
		});
	});

var idcount;
function reOrderInspectionIdSequence(inspectionEntryTr){
	$(inspectionEntryTr).each(function(i) {
		//ID
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
		$(this).find("select:eq(0)").attr("id", "remarkId" + i);
		$(this).find("select:eq(1)").attr("id", "remarkStatusId" + i);
		$(this).find("textarea:eq(0)").attr("id", "observation" + i);
		//NAME
		$(this).find("select:eq(0)").attr("name", "inspectionEntryDto.inspectionEntryDetDto[" + i + "].remarkId");
		$(this).find("select:eq(1)").attr("name", "inspectionEntryDto.inspectionEntryDetDto[" + i + "].remarkStatusId");
		$(this).find("textarea:eq(0)").attr("name", "inspectionEntryDto.inspectionEntryDetDto[" + i + "].observation");
		idcount = i;
	});
}

$(function() {
	/* To delete row into table */
	$("#inspectionTableId").on('click', '.delButton', function() {
		if ($("#inspectionTableId tr").length != 2) {
			$(this).parent().parent().remove();
			//reOrderInspectionIdSequence('.inspectionEntryTr'); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("adh.first.row.not.remove"));
			displayErrorsOnPage(errorList);
		}
	});
});

function resetInspEntry(obj) {

	resetForm(obj);
	$('input[type=text]').val('');
	$('select').val('').trigger('chosen:updated');
	$(".alert-danger").hide();
}

function getAgencyName() {

	var requestData = {
			"licenseNo" : $("#licenseNo").val()
	};
	$(".warning-div").hide();
	var ajaxResponse =  doAjaxLoading(
		    'InspectionEntry.html?getAgencyName',
		    requestData, 'json');
	
	$("#agencyName").val(ajaxResponse.agencyName);
	$("#adhZone1").val(ajaxResponse.adhZone1);
	$('#adhZone2').append(
			$("<option></option>").attr("value",ajaxResponse.adhZone2).attr("selected",true).text(
					(ajaxResponse.adhZoneDesc2)));
	$('#adhZone3').append(
			$("<option></option>").attr("value",ajaxResponse.adhZone3).attr("selected",true).text(
					(ajaxResponse.adhZoneDesc3)));
	$('#adhZone4').append(
			$("<option></option>").attr("value",ajaxResponse.adhZone4).attr("selected",true).text(
					(ajaxResponse.adhZoneDesc4)));
	$('#adhZone5').append(
			$("<option></option>").attr("value",ajaxResponse.adhZone5).attr("selected",true).text(
					(ajaxResponse.adhZoneDesc5)));
	
	$("#adhTypeId1").val(ajaxResponse.newAdvertDetDtos[0].adhTypeId1);
	$('#adhTypeId2').append(
			$("<option></option>").attr("value",ajaxResponse.newAdvertDetDtos[0].adhTypeId2).attr("selected",true).text(
					(ajaxResponse.newAdvertDetDtos[0].adhTypeIdDesc2)));
	$('#adhTypeId3').append(
			$("<option></option>").attr("value",ajaxResponse.newAdvertDetDtos[0].adhTypeId3).attr("selected",true).text(
					(ajaxResponse.newAdvertDetDtos[0].adhTypeIdDesc3)));
	$('#adhTypeId4').append(
			$("<option></option>").attr("value",ajaxResponse.newAdvertDetDtos[0].adhTypeId4).attr("selected",true).text(
					(ajaxResponse.newAdvertDetDtos[0].adhTypeIdDesc4)));
	$('#adhTypeId5').append(
			$("<option></option>").attr("value",ajaxResponse.newAdvertDetDtos[0].adhTypeId5).attr("selected",true).text(
					(ajaxResponse.newAdvertDetDtos[0].adhTypeIdDesc5)));
}

function save(element) {

	var errorList = [];
	errorList = validateForm(errorList)
	errorList = validateDetails(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				'InspectionEntry saved successfully',
				'InspectionEntry.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function savePublicNotice(element) {
debugger;
	var errorList = [];
	errorList = validateForm(errorList)
	errorList = validateDetails(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				'Notice Generated successfully',
				'InspectionEntry.html?generateNotice', 'savePublicNotice');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateDetails(errorList) {

	var rowCount = $('#inspectionTableId tr').length;
	$('.inspectionEntryTr').each(function(i) {
		if(rowCount >= 2){
			var remarkId = $("#remarkId" + i).val();
			var remarkStatusId = $("#remarkStatusId" + i).val();
			var observation = $("#observation" + i).val();
			var level = 1;
		}else{
			var remarkId = $("#remarkId" + i).val();
			var remarkStatusId = $("#remarkStatusId" + i).val();
			var observation = $("#observation" + i).val();
		}
		var level = i + 1;
		if (remarkId == '' || remarkId == undefined || remarkId == '0') {
			errorList.push(getLocalMessage("adh.validate.terms.conditions" ) + level);
		}
		if (remarkStatusId == '' || remarkStatusId == undefined || remarkStatusId == '0') {
			errorList.push(getLocalMessage("adh.validate.status" ) + level);
		}
		if (observation == '' || observation == undefined || observation == '0') {
			errorList.push(getLocalMessage("adh.validate.observation" ) + level);
		}
	});
	return errorList;
}

function validateForm(errorList) {

	var errorList = [];
	var licenseNo = $("#licenseNo").val();
	var inesDate = $("#inesDate").val();
	var inspectorName = $("#inesEmpId").val();
	var validateDate = moment(inesDate, 'DD/MM/YYYY',true).isValid();//true
	if (licenseNo == 0 ) {
		errorList.push(getLocalMessage('adh.validate.license.number'));
	}
	if (inesDate == 0 ) {
		errorList.push(getLocalMessage('adh.validate.inspection.date'));
	}else{
		 if(validateDate == false){
		    	errorList.push(getLocalMessage('adh.Date.format'));
		    }
	}
	if (inspectorName == 0 ) {
		errorList.push(getLocalMessage('adh.validate.inspector.name'));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}
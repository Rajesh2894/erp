/**
 * 
 */

$(document).ready(function() {

	$('#mnassward1').val(-1);
	$('#mnassward2').val(-1);
	$('#mnassward3').val(-1);
	$('#mnassward4').val(-1);
	$('#mnassward5').val(-1);

	var radios = $('input:radio[Value]');

	/*
	 * if (radios.is(':checked') === false) {
	 * radios.filter('[value]').prop('checked', true); }
	 */

	$('#mnFromdt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	$("#mnFromdt").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#mnTodt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#mnTodt").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	/*
	 * var maxFinDate = $("#maxFinDate").val();
	 * 
	 * $('#mnFromdt').datepicker({ dateFormat : 'dd/mm/yy', changeMonth : true,
	 * changeYear : true, minDate : 0, maxDate : 0, });
	 * 
	 * $("#mnFromdt").keyup(function(e) {
	 * 
	 * if (e.keyCode != 8) { if ($(this).val().length == 2) {
	 * $(this).val($(this).val() + "/"); } else if ($(this).val().length == 5) {
	 * $(this).val($(this).val() + "/"); } } });
	 */

	getReportType();

});

function getReportType() {

	var radiovalue = $('input[type=radio]:checked').val();

	if (radiovalue == 'S') {
		$('#Detailtype').hide();
		$('#proertyNo').hide();
		$('#Summarytype').show();

	} else {
		$('#Detailtype').show();
		$('#proertyNo').show();
		$('#Summarytype').hide();
	}
}
function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$('#usageType1').val('');
	$("#DetailDemandRegisterFormReport").validate().resetForm();
}
function saveForm(obj) {
	
	var errorList = [];
	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var mnFromdt = $("#mnFromdt").val();
	var mnTodt = $("#mnTodt").val();
	var proertyNo = $("#proertyNo").val();
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == 'D') {
		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (mnFromdt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.from.date"));
		}
		if (mnTodt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.to.date"));
		}
		if ((compareDate(mnFromdt)) > compareDate(mnTodt)) {
			errorList
					.push(getLocalMessage("property.validation.detail.from.to.date"));
		}
	} else if (ReportType == 'S') {

		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (mnFromdt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.from.date"));
		}
		if (mnTodt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.to.date"));
		}

		if ((compareDate(mnFromdt)) > compareDate(mnTodt)) {
			errorList
					.push(getLocalMessage("property.validation.detail.from.to.date"));
		}
	}

	if (ReportType == "" || ReportType == undefined) {
		errorList
				.push(getLocalMessage("property.validation.demand.report.type"));

	}

	if (proertyNo == "") {
		proertyNo = 'X';
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1;
		var wardZ2;
		var wardZ3;
		var wardZ4;
		var wardZ5;

		if ($("#mnassward1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#mnassward1").val();
		}
		if ($("#mnassward2").val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#mnassward2").val();
		}
		if ($("#mnassward3").val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#mnassward3").val();
		}
		if ($("#mnassward4").val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#mnassward4").val();
		}
		if ($("#mnassward5").val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#mnassward5").val();
		}

		if (wardZ1 == -1) {
			wardZ1 = 0;
		}
		if (wardZ2 == -1) {
			wardZ2 = 0;
		}
		if (wardZ3 == -1) {
			wardZ3 = 0;
		}
		if (wardZ4 == -1) {
			wardZ4 = 0;
		}
		if (wardZ5 == -1) {
			wardZ5 = 0;
		}

		var requestData = {
			"wardZone1" : wardZ1,
			"wardZone2" : wardZ2,
			"wardZone3" : wardZ3,
			"wardZone4" : wardZ4,
			"wardZone5" : wardZ5,
			"mnFromdt" : mnFromdt,
			"mnTodt" : mnTodt,
			"ReportType" : ReportType,
			"proertyNo" : proertyNo
		};

		var URL = 'DetailDemandRegister.html?GetDetailDemand';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if (returnData == "f") {
			errorList.push(getLocalMessage("property.report.finYear.date"));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}
		
	}
}

$("#resetform").on("click", function() {
	window.location.reload("#DetailDemandRegisterFormReport")
});

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

/*for xlsx*/


function saveXLXSForm(obj) {
	
	var errorList = [];
	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var mnFromdt = $("#mnFromdt").val();
	var mnTodt = $("#mnTodt").val();
	var proertyNo = $("#proertyNo").val();
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == 'D') {
		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (mnFromdt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.from.date"));
		}
		if (mnTodt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.to.date"));
		}
		if ((compareDate(mnFromdt)) > compareDate(mnTodt)) {
			errorList
					.push(getLocalMessage("property.validation.detail.from.to.date"));
		}
	} else if (ReportType == 'S') {

		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (mnFromdt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.from.date"));
		}
		if (mnTodt == "") {
			errorList
					.push(getLocalMessage("property.validation.detail.to.date"));
		}

		if ((compareDate(mnFromdt)) > compareDate(mnTodt)) {
			errorList
					.push(getLocalMessage("property.validation.detail.from.to.date"));
		}
	}

	if (ReportType == "" || ReportType == undefined) {
		errorList
				.push(getLocalMessage("property.validation.demand.report.type"));

	}

	if (proertyNo == "") {
		proertyNo = 'X';
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1;
		var wardZ2;
		var wardZ3;
		var wardZ4;
		var wardZ5;

		if ($("#mnassward1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#mnassward1").val();
		}
		if ($("#mnassward2").val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#mnassward2").val();
		}
		if ($("#mnassward3").val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#mnassward3").val();
		}
		if ($("#mnassward4").val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#mnassward4").val();
		}
		if ($("#mnassward5").val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#mnassward5").val();
		}

		if (wardZ1 == -1) {
			wardZ1 = 0;
		}
		if (wardZ2 == -1) {
			wardZ2 = 0;
		}
		if (wardZ3 == -1) {
			wardZ3 = 0;
		}
		if (wardZ4 == -1) {
			wardZ4 = 0;
		}
		if (wardZ5 == -1) {
			wardZ5 = 0;
		}

		var requestData = {
			"wardZone1" : wardZ1,
			"wardZone2" : wardZ2,
			"wardZone3" : wardZ3,
			"wardZone4" : wardZ4,
			"wardZone5" : wardZ5,
			"mnFromdt" : mnFromdt,
			"mnTodt" : mnTodt,
			"ReportType" : ReportType,
			"proertyNo" : proertyNo
		};

		var URL = 'DetailDemandRegister.html?GetDemandXLSX';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if (returnData == "f") {
			errorList.push(getLocalMessage("property.report.finYear.date"));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}
		
	}
}
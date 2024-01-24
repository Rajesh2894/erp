$(document).ready(function() {

	/* var radios = $('input:radio[Value]'); */

	/*
	 * if (radios.is(':checked') === false) {
	 * radios.filter('[value=D]').prop('checked', true); }
	 */
	getReportType();
});

function getReportType() {

	var radiovalue = $('input[type=radio]:checked').val();
}

function saveZoneDcbForm(obj) {
	
	var errorList = [];
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == "" || ReportType == undefined) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = "";
		if (ReportType == 'D') {
			requestData = '&ReportType=' + ReportType;
		} else {
			requestData = '&ReportType=' + ReportType;
		}

		var URL = 'dcbBirtReport.html?GetZoneWiseReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

function savePropertyDcbForm(obj) {

	var errorList = [];
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == "" || ReportType == undefined) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = "";
		if (ReportType == 'D') {
			requestData = '&ReportType=' + ReportType;
		} else {
			requestData = '&ReportType=' + ReportType;
		}

		var URL = 'dcbBirtReport.html?GetPropertyReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}
$("#resetform").on("click", function() {
	window.location.reload("#DCBFormReport")
});

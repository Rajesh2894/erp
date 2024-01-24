$(document).ready(function() {

	var radios = $('input:radio[Value=D]');
	$('#reportTypes').val();

	if (radios.is(':checked') === false) {
		radios.filter('[value=D]').prop('checked', true);
	}

	getReportType();

	$("#resetform").on("click", function() {
		window.location.reload("#CareReportFormd")
	});
	
	$("#resetLISform").on("click", function() {
		window.location.reload("#LandSurveyReport")
	});

});

function getReportType() {
	var radiovalue = $('input[type=radio]:checked').val();
}

function saveForm(obj) {

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

		var URL = 'CareReportForm.html?GetCareReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

/* LIS Disputed Land Survey report specific to dehardun environment */
function saveLISForm(obj) {
	
	var errorList = [];
	var ReportTypes = $('#reportTypes').val();

	if (ReportTypes == "" || ReportTypes == undefined || ReportTypes == 0) {
		errorList.push(getLocalMessage("care.report.validation.reportType"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData1 = "";
		if (ReportTypes == 'A') {
			requestData1 = '&ReportTypes=' + ReportTypes;
		} else {
			requestData1 = '&ReportTypes=' + ReportTypes;
		}

		var URL = 'CareReportForm.html?getLandSurveyReport';
		var returnData1 = __doAjaxRequest(URL, 'POST', requestData1, false);
		window.open(returnData1, '_blank');

	}
}

$(document).ready(function() {

	var radios = $('input:radio[Value]');

	getReportType();
});

function getReportType() {

	var radiovalue = $('input[type=radio]:checked').val();
}

function saveDivsionUabForm(obj) {

	var errorList = [];
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == "" || ReportType == undefined) {
		errorList.push(getLocalMessage("lgl.report.validation"));
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

		var URL = 'divisionDepartmentUad.html?GetDivisionUadReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

$("#resetform").on("click", function() {
	window.location.reload("#divisionForm")
});

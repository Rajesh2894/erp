$(document).ready(function() {

	$('#reportType').val();

});

function saveLicenseReportForm(obj) {
	
	var errorList = [];
	var ReportType = $('#reportType').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList
				.push(getLocalMessage("lic.validation.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = "";
		if (ReportType == 'A') {
			requestData = '&ReportType=' + ReportType;
		} else {
			requestData = '&ReportType=' + ReportType;
		}

		var URL = 'legacyLicenseBirtReport.html?GetLegacyLicenseReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}


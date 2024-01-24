$(document).ready(function() {

	$('#reportType').val();

	$("#resetform").on("click", function() {
		window.location.reload("#mrmDetailSummaryReports")
	});

});

function saveMrmReportForm(obj) {
	
	var errorList = [];
	var ReportType = $('#reportType').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList.push(getLocalMessage("mrm.vald.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = {
			"ReportType" : ReportType
		}

		var URL = 'marriageDetailSummaryBirtReportList.html?getMrmDetailSummaryReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}
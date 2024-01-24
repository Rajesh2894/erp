$(document).ready(function() {

	$('#reportType').val();

	$("#resetform").on("click", function() {
		window.location.reload("#lqpReports")
	});

});

function saveLqpReportForm(obj) {
	
	var errorList = [];
	var ReportType = $('#reportType').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList.push(getLocalMessage("lqp.select.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = "";
		if (ReportType == "A") {
			requestData = "&ReportType=" + ReportType;
		} else {
			requestData = "&ReportType=" + ReportType;
		}

		var URL = 'lqpBirtReportList.html?getLqpReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

$(document).ready(function() {

	$('#reportType').val();
	$('#reportSubType').val();
	$("#SubType").hide();
});

function saveBirtForm(obj) {
	
	var errorList = [];
	var ReportType = $('#reportType').val();
	var ReportSubType = $('#reportSubType').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList.push(getLocalMessage("acc.report.val.report.type"));
	}

	if (ReportType == "B") {

		if (ReportSubType == "" || ReportSubType == undefined
				|| ReportSubType == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = {
			"ReportType" : ReportType,
			"ReportSubType" : ReportSubType
		};

		var URL = 'customizedAccountBirtReport.html?GetBirtReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

function showReportType() {

	var ReportSubType = $('#reportType').val();

	if (ReportSubType == "B") {
		$("#SubType").show();

	} else {
		$("#SubType").hide();

	}
}

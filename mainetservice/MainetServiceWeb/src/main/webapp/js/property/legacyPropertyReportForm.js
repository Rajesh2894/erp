$(document).ready(function() {

	$('#reportType').val();
	$('#reportSubType').val();
	$("#reportSubType2").val();
	$("#reportSubType3").val();
	$("#reportSubType4").val();
	$("#SubType").hide();
	$("#SubType2").hide();
	$("#SubType3").hide();
	$("#SubType4").hide();

	$("#resetDcbform").on("click", function() {
		window.location.reload("#legacyDCBBirtReport")
	});

});

/* SKDCL BIRT REPORTS */
function savelegacyBirtForm(obj) {

	var errorList = [];
	var ReportType = $('#reportType').val();
	var ReportSubType = $('#reportSubType').val();
	var ReportSubType2 = $('#reportSubType2').val();
	var ReportSubType3 = $('#reportSubType3').val();
	var ReportSubType4 = $('#reportSubType4').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}
	if (ReportType == "H") {

		if (ReportSubType == "" || ReportSubType == undefined
				|| ReportSubType == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "O") {

		if (ReportSubType2 == "" || ReportSubType2 == undefined
				|| ReportSubType2 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "NMAM") {

		if (ReportSubType3 == "" || ReportSubType3 == undefined
				|| ReportSubType3 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "Note") {

		if (ReportSubType4 == "" || ReportSubType4 == undefined
				|| ReportSubType4 == 0) {
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
			"ReportSubType" : ReportSubType,
			"ReportSubType2" : ReportSubType2,
			"ReportSubType3" : ReportSubType3,
			"ReportSubType4" : ReportSubType4
		};

		var URL = 'legacyPropertyBirtReport.html?GetLegacyPropertyReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

function showReportType() {

	var ReportSubTypes = $('#reportType').val();

	if (ReportSubTypes == "H") {
		$("#SubType").show();
		$("#SubType2").hide();
		$("#SubType3").hide();
		$("#SubType4").hide();
	} else if (ReportSubTypes == "O") {
		$("#SubType2").show();
		$("#SubType").hide();
		$("#SubType3").hide();
		$("#SubType4").hide();
	} else if (ReportSubTypes == "NMAM") {
		$("#SubType2").hide();
		$("#SubType").hide();
		$("#SubType3").show();
		$("#SubType4").hide();
	} else if (ReportSubTypes == "Note") {
		$("#SubType2").hide();
		$("#SubType").hide();
		$("#SubType3").hide();
		$("#SubType4").show();
	} else {
		$("#SubType4").hide();
		$("#SubType3").hide();
		$("#SubType2").hide();
		$("#SubType").hide();
	}

}

/** ***************SKDCL DCB REPORTS***************** */

function saveDCBBirtForm(obj) {

	var errorList = [];
	var ReportDcbType = $('#reportType').val();

	if (ReportDcbType == "" || ReportDcbType == undefined || ReportDcbType == 0) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData1 = "";
		if (ReportDcbType == "A") {
			requestData1 = "&ReportDcbType=" + ReportDcbType;
		} else {
			requestData1 = "&ReportDcbType=" + ReportDcbType;
		}

		var URL = 'legacyPropertyBirtReport.html?getDCBBirtReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData1, false);
		window.open(returnData, '_blank');

	}
}

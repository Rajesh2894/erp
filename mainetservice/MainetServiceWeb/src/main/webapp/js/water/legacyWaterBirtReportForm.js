$(document).ready(function() {

	$('#reportType').val();
	$('#reportSubType1').val();
	$('#reportSubType2').val();
	$('#reportSubType3').val();
	$('#reportSubType4').val();
	$('#reportSubType5').val();
	$('#reportSubType6').val();
	$("#SubType1").hide();
	$("#SubType2").hide();
	$("#SubType3").hide();
	$("#SubType4").hide();
	$("#SubType5").hide();
	$("#SubType6").hide();

	$("#resetReportform").on("click", function() {
		window.location.reload("#ReportForm")
	});
});

/* SKDCL BIRT REPORTS */
function saveReportForms(obj) {

	var errorList = [];
	var ReportType = $('#reportType').val();
	var ReportSubType1 = $('#reportSubType1').val();
	var ReportSubType2 = $('#reportSubType2').val();
	var ReportSubType3 = $('#reportSubType3').val();
	var ReportSubType4 = $('#reportSubType4').val();
	var ReportSubType5 = $('#reportSubType5').val();
	var ReportSubType6 = $('#reportSubType6').val();

	if (ReportType == "" || ReportType == undefined || ReportType == 0) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}
	if (ReportType == "A") {

		if (ReportSubType1 == "" || ReportSubType1 == undefined
				|| ReportSubType1 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "B") {

		if (ReportSubType2 == "" || ReportSubType2 == undefined
				|| ReportSubType2 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "C") {

		if (ReportSubType3 == "" || ReportSubType3 == undefined
				|| ReportSubType3 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "D") {

		if (ReportSubType4 == "" || ReportSubType4 == undefined
				|| ReportSubType4 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "E") {

		if (ReportSubType5 == "" || ReportSubType5 == undefined
				|| ReportSubType5 == 0) {
			errorList.push(getLocalMessage("prop.birt.vald.Report.Sub.Type"));
		}
	}
	if (ReportType == "F") {

		if (ReportSubType6 == "" || ReportSubType6 == undefined
				|| ReportSubType6 == 0) {
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
			"ReportSubType1" : ReportSubType1,
			"ReportSubType2" : ReportSubType2,
			"ReportSubType3" : ReportSubType3,
			"ReportSubType4" : ReportSubType4,
			"ReportSubType5" : ReportSubType5,
			"ReportSubType6" : ReportSubType6,
		};

		var URL = 'legacyWaterBirtReports.html?getWaterBirtReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

function showReportType() {

	var ReportSubTypes = $('#reportType').val();

	if (ReportSubTypes == "A") {
		$("#SubType1").show();
		$("#SubType2").hide();
		$("#SubType3").hide();
		$("#SubType4").hide();
		$("#SubType5").hide();
		$("#SubType6").hide();
	} else if (ReportSubTypes == "B") {
		$("#SubType1").hide();
		$("#SubType2").show();
		$("#SubType3").hide();
		$("#SubType5").hide();
		$("#SubType6").hide();
	} else if (ReportSubTypes == "C") {
		$("#SubType1").hide();
		$("#SubType2").hide();
		$("#SubType3").show();
		$("#SubType4").hide();
		$("#SubType5").hide();
		$("#SubType6").hide();
	} else if (ReportSubTypes == "D") {
		$("#SubType1").hide();
		$("#SubType2").hide();
		$("#SubType3").hide();
		$("#SubType4").show();
		$("#SubType5").hide();
		$("#SubType6").hide();
	} else if (ReportSubTypes == "E") {
		$("#SubType1").hide();
		$("#SubType2").hide();
		$("#SubType3").hide();
		$("#SubType4").hide();
		$("#SubType5").show();
		$("#SubType6").hide();
	} else if (ReportSubTypes == "F") {
		$("#SubType1").hide();
		$("#SubType2").hide();
		$("#SubType3").hide();
		$("#SubType4").hide();
		$("#SubType5").hide();
		$("#SubType6").show();
	} else {
		$("#SubType1").hide();
		$("#SubType2").hide();
		$("#SubType3").hide();
		$("#SubType4").hide();
		$("#SubType5").hide();
		$("#SubType6").hide();
	}

}

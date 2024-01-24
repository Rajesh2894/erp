$(document).ready(function() {

	$("#detType").hide();
	var radios = $('input:radio[Value]');

	/*
	 * if (radios.is(':checked') === false) {
	 * radios.filter('[value]').prop('checked', false); }
	 */
	// getReportType();
});

function getReportType() {
	
	var radiovalue = $('input[type=radio]:checked').val();

	if (radiovalue == 'S') {
		$('#detType').show();

	} else {
		$('#detType').show();

	}
}

/* Format-1 */
function saveDeptForm(obj) {
	
	var errorList = [];
	var reportDeptName = $('input[type=radio]:checked').val();
	var reportServiceDept = $(
			'input[name="careReportRequest.reports"]:checked',
			'#careDeptReports').val();

	if (reportDeptName == "" || reportDeptName == undefined
			|| reportDeptName == null) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	} else {
		if (reportServiceDept == "" || reportServiceDept == undefined
				|| reportServiceDept == null) {
			errorList
					.push(getLocalMessage("property.validation.collection.report.type"));
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = {
			"reportDeptName" : reportDeptName,
			"reportServiceDept" : reportServiceDept
		};

		var URL = 'CareReportBirtForm.html?getDeptCareReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}

/* Format-3 */
function saveDistrictForm(obj) {
	
	var errorList = [];
	var reportDisName = $('input[type=radio]:checked').val();
	var reportServiceDist = $(
			'input[name="careReportRequest.reports"]:checked',
			'#careDistrictReports').val();

	if (reportDisName == "" || reportDisName == undefined
			|| reportDisName == null) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	else {
		if (reportServiceDist == "" || reportServiceDist == undefined
				|| reportServiceDist == null) {
			errorList
					.push(getLocalMessage("property.validation.collection.report.type"));
		}

	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = {
			"reportDisName" : reportDisName,
			"reportServiceDist" : reportServiceDist
		};

		var URL = 'CareReportBirtForm.html?getDistCareReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}

/* Format-4 */

function saveComplaintWiseForm(obj) {
	
	var errorList = [];
	var deptComplaintName = $('input[type=radio]:checked').val();
	var reportServiceComp = $(
			'input[name="careReportRequest.reports"]:checked',
			'#careCorporationReports').val();

	if (deptComplaintName == "" || deptComplaintName == undefined
			|| deptComplaintName == null) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	} else {
		if (reportServiceComp == "" || reportServiceComp == undefined
				|| reportServiceComp == null) {
			errorList
					.push(getLocalMessage("property.validation.collection.report.type"));
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = {
			"deptComplaintName" : deptComplaintName,
			"reportServiceComp" : reportServiceComp
		};

		var URL = 'CareReportBirtForm.html?getComplaintForm';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}

/* format-5 */
function saveUlbForm(obj) {
	
	var errorList = [];

	var reportUlbName = $('input[type=radio]:checked').val();
	var reportServiceUlb = $('input[name="careReportRequest.reports"]:checked',
			'#careReports').val();

	if (reportUlbName == "" || reportUlbName == undefined
			|| reportUlbName == null) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	} else {
		if (reportServiceUlb == "" || reportServiceUlb == undefined
				|| reportServiceUlb == null) {

			errorList
					.push(getLocalMessage("property.validation.collection.report.type"));
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = {
			"reportUlbName" : reportUlbName,
			"reportServiceUlb" : reportServiceUlb
		};

		var URL = 'CareReportBirtForm.html?getUlbCareReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

$("#resetform").on("click", function() {
	window.location.reload("#careReports")
});

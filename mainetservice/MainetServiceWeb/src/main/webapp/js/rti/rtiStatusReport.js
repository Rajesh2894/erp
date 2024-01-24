/**
 * 
 */
$(document).ready(function() {
	$('#rtiDeptId').val();
	$('#inwardType').val();
	$('#status').val();
	$('#applicationSlaDurationInMS').val();

	$('#fromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	$("#fromDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#toDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#toDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	/*
	 * $("#resetForm").on("click", function() {
	 * window.location.reload("#rtiFormReport") });
	 */
});

function saveForm(obj) {

	var errorList = [];
	var DepartId = $("#rtiDeptId").val();
	var rtiMode = $("#inwardType").val();
	var rtiStatus = $("#status").val();
	var SLAid = $("#applicationSlaDurationInMS").val();
	var rtifromDate = $("#fromDate").val();
	var rtitoDate = $("#toDate").val();

	if (DepartId == 0 || DepartId == undefined) {
		errorList
				.push(getLocalMessage("rti.status.report.validation.department"));
	}
	if (rtiMode == 0 || rtiMode == "" || rtiMode == undefined) {
		errorList.push(getLocalMessage("rti.status.report.validation.mode"));
	}
	if (rtiStatus == 0 || rtiStatus == "" || rtiStatus == undefined) {
		errorList
				.push(getLocalMessage("rti.status.report.validation.rtiStatus"));
	}
	if (SLAid == 0 || SLAid == "" || SLAid == undefined) {
		errorList.push(getLocalMessage("rti.status.report.validation.sla"));
	}

	if (rtifromDate == "" || rtifromDate == 0) {
		errorList
				.push(getLocalMessage("rti.status.report.validation.fromDate"));
	}
	if (rtitoDate == "" || rtitoDate == 0) {
		errorList.push(getLocalMessage("rti.status.report.validation.toDate"));
	}

	if (compareDate(rtifromDate) > compareDate(rtitoDate)) {
		errorList
				.push(getLocalMessage("rti.status.report.validation.from.to.Date"));
	}

	if (rtiMode == -1) {
		rtiMode = 0;
	}
	if (SLAid == -1) {
		SLAid = 'X';
	}
	if (rtiStatus == -1) {
		rtiStatus = 'X';
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}

	else {
		var divName = '.content-page';
		// $("#errorDiv").hide();

		var requestData = '&rtifromDate=' + rtifromDate + '&rtitoDate='
				+ rtitoDate + '&rtiStatus=' + rtiStatus + '&SLAid=' + SLAid
				+ '&DepartId=' + DepartId + '&rtiMode=' + rtiMode;

		var URL = 'RtiStatusReport.html?GetRtiReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);

		if (returnData == "f") {
			errorList.push(getLocalMessage('rti.report.financialYr'));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}

	}
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

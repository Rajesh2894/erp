$(document).ready(function() {
	$('#schmeMsId').val(-1);
	$('#serviceId').val(-1);

	$('#createdDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	$("#createdDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#updatedDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#updatedDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

});

/*
 * function resetForm() { debugger; $('input[type=text]').val('');
 * $(".alert-danger").hide();
 * $("#DemographicFormReport").validate().resetForm(); }
 */

function saveReportForm(obj) {

	var errorList = [];
	var pSchedule = $("#schmeMsId").val();
	var pSchemeName = $("#serviceId").val();
	var pFrmDate = $("#createdDate").val();
	var pToDate = $("#updatedDate").val();

	if (pSchedule == 0 || pSchedule == "" || pSchedule == undefined) {
		errorList
				.push(getLocalMessage("social.demographicReport.valid.pensionSchedle"));
	}
	if (pSchemeName == 0 || pSchemeName == "" || pSchemeName == undefined) {
		errorList
				.push(getLocalMessage("social.demographicReport.valid.pensionSchemName"));
	}
	if (pFrmDate == "") {
		errorList
				.push(getLocalMessage("social.demographicReport.valid.fromdate"));
	}
	if (pToDate == "") {
		errorList
				.push(getLocalMessage("social.demographicReport.valid.todate"));
	}

	if (compareDate(pFrmDate) > compareDate(pToDate)) {
		errorList
				.push(getLocalMessage("social.demographicReport.valid.fromtoDate"));
	}

	if (pSchedule == -1) {
		pSchedule = 0;
	}
	if (pSchemeName == -1) {
		pSchemeName = 0;
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&pSchedule=' + pSchedule + '&pSchemeName='
				+ pSchemeName + '&pFrmDate=' + pFrmDate + '&pToDate=' + pToDate;

		var URL = 'demographicsReport.html?GetDemographicReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
		/*if (returnData == "f") {
			errorList
					.push(getLocalMessage("social.report.validation.finYrDate"));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}*/

	}
}
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

/*$("#resetForm").on("click", function() {
	window.location.reload("#DemographicFormReport")
});*/
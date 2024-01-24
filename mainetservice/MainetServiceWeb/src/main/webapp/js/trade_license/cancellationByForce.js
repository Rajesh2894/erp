$(document).ready(function() {
	debugger;
	var cancelDate = $('#cancelDate').val();
	if (cancelDate) {
		$('#cancelDate').val(cancelDate.split(' ')[0]);
	}
	$("#resetform").on("click", function() {
		window.location.reload("#cancellationLicenseByforce")
	});

});

function getLicenseDetails() {
	debugger;
	var errorList = [];
	var theForm = '#cancellationLicenseByforce';
	var trdLicno = $("#trdLicno").val();

	var requestData = {
		'trdLicno' : trdLicno
	}
	var response = __doAjaxRequest(
			'LicenseCancellationByForce.html?getLicenseDetails', 'POST',
			requestData, false, 'html');

	$(formDivName).html(response);
}

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function saveCancellationByForce(obj) {

	var errorList = [];

	errorList = validateCancellationLicenseForm(errorList);
	if (errorList.length == 0) {

		return saveOrUpdateForm(obj,
				"Cancellation Of License Submitted Successfully",
				"AdminHome.html", 'saveByForceCancelForm');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCancellationLicenseForm(errorList) {
	var errorList = [];
	var remark = $("#cancelReason").val();

	if (remark == "" || remark == undefined || remark == "") {
		errorList.push(getLocalMessage("trade.validation.reason"));
	}

	return errorList;
}

$(document).ready(function() {

	$('#serviceId').val(-1);

	$('#status').val(-1);
	$("#resetForm").on("click", function() {
		window.location.reload("#LifeCertificateFormReport")
	});
});

function saveForm(obj) {

	var errorList = [];
	var pSchemeName = $("#serviceId").val();
	var pStatus = $("#status").val();

	if (pSchemeName == "" || pSchemeName == 0) {
		errorList
				.push(getLocalMessage("social.demographicReport.valid.pensionSchemName"));
	}
	if (pStatus == "" || pStatus == 0) {
		errorList.push(getLocalMessage("social.validation.staus"));
	}
	if (pSchemeName == -1) {
		pSchemeName = 0;
	}
	if (pStatus == -1) {
		pStatus = 'X';
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&pSchemeName=' + pSchemeName + '&pStatus=' + pStatus;

		var URL = 'LifeCertificate.html?GetLifeCertificate';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}

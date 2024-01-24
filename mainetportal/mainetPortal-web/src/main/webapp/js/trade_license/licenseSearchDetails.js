function getLicenseDetails() {

	var errorList = [];
	errorList = validateLicenseSearchForm(errorList);
	if (errorList.length == 0) {
		var theForm = '#changeBusinessNameForm';
		var trdLicno = $("#licenseNo").val();
		requestData = {
			"trdLicno" : trdLicno
		}
		var response = __doAjaxRequest(
				'LicenseSearchDetails.html?getLicenseDetails', 'POST',
				requestData, false, 'html');
		if (response != null) {
			$(formDivName).html(response);

		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateLicenseSearchForm(errorList) {

	var errorList = [];
	var licenseNo = $("#licenseNo").val();

	if (licenseNo == "" || licenseNo == undefined || licenseNo == "") {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}

	return errorList;
}

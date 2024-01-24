function SearchButton(obj) {
	var errorList = [];
	errorList = ValidateDetails(errorList);
	if (errorList.length == 0) {
		var theForm = '#TradeManualReceiptEntry';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequest(
				'TradeManualReceiptEntry.html?getTradeLicDetails', 'POST',
				requestData, false, 'html');

		$("#dataDiv").html(ajaxResponse);
		return false;

	} else {
		displayErrorsOnPage(errorList);
	}
}

function ValidateDetails(errorList) {
	var trdLicno = $("#trdLicno").val();
	var trdOldlicno = $("#trdOldlicno").val();
	if (trdLicno == "" && trdOldlicno == "") {
		errorList.push(getLocalMessage("trade.validLeastOldNew"));
	}
	if (trdLicno != "" && trdOldlicno != "") {
		errorList.push(getLocalMessage("trade.validSelectOnlyOne"));
	}
	return errorList;
}

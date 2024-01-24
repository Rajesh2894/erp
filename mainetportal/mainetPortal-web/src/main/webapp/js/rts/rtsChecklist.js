$(document).ready(function() {

});

function saveRtsForm(element) {
	debugger;
	var errorList = [];
	var amtToPay = $("#AmountToShow").val();

	var applicationchargeApplFlag = $("#applicationchargeApplFlag").val();
	// errorList = validateRtiForm(errorList);
	errorList = validateDcsData();
	if (applicationchargeApplFlag == 'Y'
			&& ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == undefined || $(
					"input:radio[name='offlineDTO.onlineOfflineCheck']")
					.filter(":checked").val() == null)) {
		errorList.push(getLocalMessage("rti.validation.collectionMode"));
	}

	else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		errorList.push(getLocalMessage("rti.validation.payment.gateway"));
	}

	else if (applicationchargeApplFlag == 'Y'
			&& (amtToPay == "" || amtToPay == null || amtToPay == undefined || amtToPay == 0)) {
		errorList.push(getLocalMessage("rti.validation.payment.charge"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}

	if (errorList.length == 0) {

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(element,
					"Your application for RTS saved successfully!",
					'drainageConnection.html?PrintReport', 'saveRts');
		}

		else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'Y') {
			return saveOrUpdateForm(element,
					"Your application for RTS saved successfully!",
					'drainageConnection.html?redirectToPay', 'saveRts');
		}

		else {
			return saveOrUpdateForm(
					element,
					"Your application for Drainage connection saved successfully!",
					'drainageConnection.html?proceed', 'saveRts');
		}
	}

}

function validateDcsData() {
	var errorList = [];

	var rowcount = $("#DrainageTable tr").length;
	for (var i = 0; i < rowcount - 1; i++) {
		var checklistUploadedOrNot = $("#checkList" + i).val();
		if (checklistUploadedOrNot == "") {
			errorList.push(getLocalMessage("rti.checklist") + "    " + (i + 1));
		}
	}
	return errorList;
}

function previousPage() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('drainageConnection.html?previousPage',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}
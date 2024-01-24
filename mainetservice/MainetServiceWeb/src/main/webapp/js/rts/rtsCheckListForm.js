$(document).ready(function() {

});

function saveRtsForm(element) {
	debugger;
	var errorList = [];
	// For validating charges
	var amtToPay = $("#AmountToShow").val();
	var applicationchargeApplFlag = $("#applicationchargeApplFlag").val();

	if ((applicationchargeApplFlag == 'Y' || applicationchargeApplFlag == undefined)
			&& ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == undefined || $(
					"input:radio[name='offlineDTO.onlineOfflineCheck']")
					.filter(":checked").val() == null)) {
		errorList.push(getLocalMessage("rts.validation.collectionMode"));
	}

	else if (applicationchargeApplFlag == 'Y'
			&& (amtToPay == "" || amtToPay == null || amtToPay == undefined || amtToPay == 0)) {
		errorList.push(getLocalMessage("rts.validation.payment.charge"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}

	var divName = formDivName;
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	/*
	 * var ajaxResponse = __doAjaxRequest( 'drainageConnection.html?saveRts',
	 * 'POST', requestData, false, '', element);
	 */

	// errorList = validateRtiForm(errorList);
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
			return saveOrUpdateForm(element,
					"Your application for RTS saved successfully!",
					'drainageConnection.html?proceed', 'saveRts');
		}
	}
}

// else {
// displayErrorsOnPage(errorList);

function previousPage() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('drainageConnection.html?previousPage',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}
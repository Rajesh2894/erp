function validateForm() {
	var errorList = [];

	var address = $("#rtiAddress").val();
	var applicantType = $("#applicantType").val();
	var sizeOfConnection = $("#sizeOfConnection").val();
	var typeOfConnection = $("#typeOfConnection").val();
	var ward = $("#ward").val();
	var propertyIndexNo = $("#propertyIndexNo").val();

	if (address == "" || address == undefined || address == null) {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.address"));
	}
	if (applicantType == "" || applicantType == undefined
			|| applicantType == null || applicantType == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.applicantType"));
	}
	if (sizeOfConnection == "0" || sizeOfConnection == undefined
			|| sizeOfConnection == null) {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.sizeOfConn"));
	}
	if (typeOfConnection == "" || typeOfConnection == undefined
			|| typeOfConnection == null || typeOfConnection == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.typeOfConn"));
	}
	if (wardNo == "Select Ward Name") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.ward"));
	}
	if (propertyIndexNo == "" || propertyIndexNo == undefined
			|| propertyIndexNo == null) {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.propertyIndex"));
	}
	return errorList;
}

function getCheckList(element) {
	var errorList = [];
	errorList = validateForm();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if ($("#checkListApplFlag").val() == "N"
			&& $("#applicationchargeApplFlag").val() == "N") {
		return saveOrUpdateForm(element,
				"Your application for Drainage connection saved successfully!",
				'CitizenHome.html', 'saveRts');
	} else {
		var requestData = element;
		var divName = '.content-page'
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var formUrl = 'drainageConnection.html';
		var actionParam = 'getCheckListAndCharges';
		var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
				requestData, false, '', 'html');
		$(divName).html(response);
		$(divName).removeClass('ajaxLoader')
	}

	/*
	 * return saveOrUpdateForm(element, 'Drainage Connection Record Saved
	 * Successfully', 'rtsService.html', 'saveform');
	 */

}

function previousPage() {
	var applicationId = 0;
	var divName = '.content-page';
	requestData = {
		"applicationId" : applicationId
	};

	var ajaxResponse = doAjaxLoading('rtsService.html?applicantForm',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'drainageConnection.html?resetdraiangeForm', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

// checklist

function saveRtsForm(element) {

	var errorList = [];
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
			return saveOrUpdateForm(
					element,
					"Your application for Drainage connection saved successfully!",
					'AdminHome.html', 'saveRts');
		}
	}

}

function previousPage() {
	/*
	 * var divName = '.content-page'; var ajaxResponse =
	 * doAjaxLoading('drainageConnection.html?previousPage', {}, 'html',
	 * divName); $(divName).removeClass('ajaxloader');
	 * $(divName).html(ajaxResponse) $('.error-div').hide();
	 */
	var applicationId = $("#apmApplicationId").val();
	if (applicationId == "" || applicationId == null
			|| applicationId == undefined) {
		applicationId = "0";
	}

	var divName = '.content-page';
	requestData = {
		"applicationId" : applicationId
	};

	var ajaxResponse = doAjaxLoading('rtsService.html?applicantForm',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

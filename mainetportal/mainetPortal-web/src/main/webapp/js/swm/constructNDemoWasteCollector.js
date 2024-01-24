function saveWasteCollector(element) {
	
	var errorList = []
	errorList = validateApplicantDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
	} else {
		$("#errorDiv").hide();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == "N") {
			return doAjaxOperation(element,
					"Your application for C&D collection saved successfully!",
					'WasteCollector.html?PrintReport', 'save');
		} else {
			return doAjaxOperation(
					element,
					"Your application for Waste collection saved successfully!",
					'WasteCollector.html?redirectToPay', 'save');
		}
	}
}

function validateApplicantDetails(errorList) {
	
	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	var gender = $.trim($('#gender').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var capacity = $.trim($('#capacity').val());
	var trip = $.trim($('#noTrip').val());
	var permission = $('input[type=radio]').attr('value');
	var complaintNo = $.trim($('#complainNo').val());
	var locationId = $.trim($('#locationId').val());
	var vehicleId = $.trim($('#vehicleType').val());
	var vehicleNo = $.trim($('#veid').val());
	var locAddress = $.trim($('#locAddress').val());

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantLastName'));
	}
	if (gender == "" || gender == '0' || gender == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantGender'));
	}

	if (applicantMobileNo == '' || applicantMobileNo == null
			|| applicantMobileNo == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.applicantMobileNo'));
	} else {
		if (!validateMobile(applicantMobileNo)) {
			errorList
					.push(getLocalMessage('construct.demolition.validation.ApplicantInvalidmobile'));
		}
	}
	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.applicantarea'));
	}

	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.applicantPinCode'));
	}
	if (capacity == "" || capacity == undefined || capacity == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.capacity'));
	}
	if (trip == "" || trip == undefined || trip == '0') {
		errorList.push(getLocalMessage('construct.demolition.validation.trip'));
	}

	if (locationId == "" || locationId == undefined || locationId == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.locationId'));
	}
	if (vehicleId == "" || vehicleId == undefined || vehicleId == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.vehicleId'));
	}
	if (locAddress == "" || locAddress == undefined || locAddress == '0') {
		errorList
				.push(getLocalMessage('Address Of Construction Site Can not be Empty'));
	}

	return errorList;
}

function getChecklistAndCharges(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = "WasteCollector.html?getCheckListAndCharges";
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');

	if (returnData != null) {
		$('.content-page').removeClass('ajaxloader');
		$('.content-page').html(returnData);
	}
}

function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function showErrAstInfo(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstInfo()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	errorList = [];
}

function back() {
	window.location.href = "CitizenHome.html";
}
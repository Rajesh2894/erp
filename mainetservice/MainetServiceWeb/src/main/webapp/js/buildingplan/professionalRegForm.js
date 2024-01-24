$(document).ready(function() {

	$(".lessdatepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		minDate : 0,
		changeYear : true,
	});
	var userTypeCode = $('#userType option:selected').attr('code');
	if (userTypeCode == "P1") {
		$(".architectclass").show();
	} else {
		$(".architectclass").hide();
	}
});

function getchecklist() {
	var userTypeCode = $('#userType option:selected').attr('code');
	if (userTypeCode != null && userTypeCode != "0"
			&& userTypeCode != undefined) {
		var theForm = '#professionalRegistrationForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ProfessionalRegistrationForm.html?getCheckList';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		$(formDivName).html(returnData);
		prepareTags();
		if (userTypeCode == "P1") {
			$(".architectclass").show();
		} else {
			$(".architectclass").hide();
		}
	}

}

function saveRegForm(element) {
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, "Form Saved successfully",
				'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveRegApprovalForm(element) {
	var requestData = {};
	var theForm = '#professionalRegistrationForm';
	requestData = __serializeForm(theForm);
	var URL = 'ProfessionalRegistrationForm.html?saveAuthorization';
	var returnData = doFormActionForSaveApproval(element, "Saved Successfully",
			URL, true, "AdminHome.html");
	if (returnData) {
		$(formDivName).html(returnData);
	}
}
function doFormActionForSaveApproval(obj, successMessage, URL, sendFormData,
		successUrl) {

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;

	var requestData = {};

	if (sendFormData) {

		requestData = __serializeForm(theForm);
	}

	var url = URL;

	var returnData = __doAjaxRequestForSave(url, 'post', requestData, false,
			'', obj);
	if ($.isPlainObject(returnData)) {
		var message = returnData.command.message;

		var hasError = returnData.command.hasValidationError;

		if (!message) {
			message = successMessage;
		}

		if (message && !hasError) {
			if (returnData.command.hiddenOtherVal == 'SERVERERROR')

				showSaveResultBox(returnData, message, 'AdminHome.html');

			else

				showSaveResultBox(returnData, message, successUrl);
		} else if (hasError) {
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');
		} else
			return returnData;

	} else if (typeof (returnData) === "string") {
		$(formDivName).html(returnData);
		prepareTags();
	} else {
		alert("Invalid datatype received : " + returnData);
	}

	return false;

}

function validateForm(errorList) {
	var userTypeCode = $('#userType option:selected').attr('code');
	var userType = $('#userType').val();
	var firstName = $('#firstName').val();
	var lastName = $('#lastName').val();
	var emailId = $('#emailId').val();
	var mobileNo = $('#mobileNo').val();
	var district = $('#district').val();
	var state = $('#state').val();
	var address = $('#address').val();
	var pincode = $('#pincode').val();
	var officeCircle = $('#officeCircle').val();
	var coaNo = $('#coaNo').val();
	var coaDate = $('#coaDate').val();
	if (userType == null || userType == undefined || userType == "") {
		errorList.push(getLocalMessage("professional.val.user.type"));
	}
	if (firstName == null || firstName == undefined || firstName == "") {
		errorList.push(getLocalMessage("professional.val.first.name"));
	}
	if (lastName == null || lastName == undefined || lastName == "") {
		errorList.push(getLocalMessage("professional.val.last.name"));
	}
	if (emailId == null || emailId == undefined || emailId == "") {
		errorList.push(getLocalMessage("professional.val.email.id"));
	} else if (!validateEmail(emailId)) {
		errorList.push(getLocalMessage("professional.val.invalid.email.id"));
	}
	if (mobileNo == null || mobileNo == undefined || mobileNo == "") {
		errorList.push(getLocalMessage("professional.val.mobile.no"));
	} else if (!validateMobile(mobileNo)) {
		errorList.push(getLocalMessage("professional.val.invalid.mobile.no"));
	}
	if (state == null || state == undefined || state == "") {
		errorList.push(getLocalMessage("professional.val.state"));
	}
	if (district == null || district == undefined || district == "") {
		errorList.push(getLocalMessage("professional.val.district"));
	}

	if (pincode == null || pincode == undefined || pincode == "") {
		errorList.push(getLocalMessage("professional.val.pincode"));
	} else if (!validatePincode(pincode)) {
		errorList.push(getLocalMessage("professional.val.invalid.pincode"));
	}
	if (address == null || address == undefined || address == "") {
		errorList.push(getLocalMessage("professional.val.address"));
	}
	if (officeCircle == null || officeCircle == undefined || officeCircle == "") {
		errorList.push(getLocalMessage("professional.val.office.circle"));
	}
	if (userTypeCode == "P1") {
		if (coaNo == null || coaNo == undefined || coaNo == "") {
			errorList.push(getLocalMessage("professional.val.coa.no"));
		} else if (!validateCoaNo(coaNo)) {
			errorList.push(getLocalMessage("professional.val.invalid.coa.no"));
		}
		if (coaDate == null || coaDate == undefined || coaDate == "") {
			errorList.push(getLocalMessage("professional.val.coa.date"));
		}
	}
	return errorList;

}

function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function validatePincode(pincode) {
	var regexPattern = new RegExp("^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$");
	return regexPattern.test(pincode);
}

function validateEmail(email) {
	var regexPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return regexPattern.test(email);
}
function validateCoaNo(coaNo) {
	var regexPattern = /^[A-Z]{2}\/\d{4}\/\d{5}$/;
	return regexPattern.test(coaNo);
}
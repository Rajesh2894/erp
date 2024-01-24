$(document).ready(function() {

	$("#resetform").on("click", function() {
		window.location.reload("#CancellationLicenseForm")
	});
	$('.tradeCat').attr('disabled', true);
});

function getLicenseDetails() {

	var errorList = [];
    var userOtpNo = $("#userOtp").val();
    var envFlag = $("#envFlag").val();
    if (envFlag == 'N'){
	var time = document.getElementById('timer').innerHTML;

	if (userOtpNo == '' || userOtpNo == 'undefined') {
		errorList.push(getLocalMessage("duplicate.validation.otpno"));
	}
	//#129520-validation for otp timeout
	else if (time == '00:00'){
		errorList.push(getLocalMessage("otp.timeout"));
	}
    }
	if (errorList.length == 0) {
		var theForm = '#CancellationLicenseForm';
		var trdLicno = $("#licenseNo").val();
		requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'CancellationLicenseForm.html?getLicenseDetails', 'POST',
				requestData, false, 'html');
		if (response != null) {
			$(formDivName).html(response);
			 if (envFlag == 'N')
			 $('#otpdetails').show();
			 else
			 $('#otpdetails').hide();
			 
		} else {
			displayErrorsOnPage(errorList);
			
		}
	}else {
		displayErrorsOnPage(errorList);
		return false;
	}
}

function backPage() {

	window.location.href = getLocalMessage("CitizenHome.html");
}

function saveCancellationForm(obj) {

	var errorList = [];
	errorList = validateCancellationLicenseForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj,
				"Cancellation Of License Submitted Successfully",
				"CitizenHome.html", 'saveCancellationForm');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCancellationLicenseForm(errorList) {
	// var errorList = [];
	var remark = $("#remarks").val();

	if (remark == "" || remark == undefined || remark == "") {
		errorList.push(getLocalMessage("trade.validation.remark"));
	}

	return errorList;
}
function generateOtpNumber(element) {
	var theForm = '#CancellationLicenseForm';
	var requestData = __serializeForm(theForm);
	var URL = 'CancellationLicenseForm.html?' + 'generateOtp';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	//#129520-to show otp time
	timer(getLocalMessage("otp.timer"));
}
//#129520-to show otp time
function timer(remaining) {
	let timerOn = true;
	  var m = Math.floor(remaining / 60);
	  var s = remaining % 60;
	  
	  m = m < 10 ? '0' + m : m;
	  s = s < 10 ? '0' + s : s;
	  document.getElementById('timer').innerHTML = m + ':' + s;
	  remaining -= 1;
	  
	  if(remaining >= 0 && timerOn) {
	    setTimeout(function() {
	        timer(remaining);
	    }, 1000);
	    return;
	  }	 
	}

function getOtpBtn() {

	var errorList = [];
	var theForm = '#CancellationLicenseForm';
	var trdLicno = $("#licenseNo").val();
	if (errorList.length == 0) {
		var requestData = {
			'trdLicno' : trdLicno
		}
		var response = __doAjaxRequest(
				'CancellationLicenseForm.html?getOtpBtnShow', 'POST',
				requestData, false, 'html');
		if (response != null) {
			$(formDivName).html(response);
		}
		$('#otpdetails').show();
	} else {
		displayErrorsOnPage(errorList);
	}

}
function getChecklistAndCharges(obj) {

	var errorList = [];
	validateCancellationLicenseForm(errorList);
	if (errorList.length == 0) {
		var theForm = '#CancellationLicenseForm';
		requestData = __serializeForm(theForm);
		var URL = 'CancellationLicenseForm.html?getCheckListAndCharge';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		if (returnData != null) {
			$(formDivName).html(returnData);
			$('#otpdetails').hide();

		}
	} else {
		displayErrorsOnPage(errorList);
	}
}
$(document).ready(function(){
	$("#itemDetails thead tr th:first-child").append("<i class='text-red-1'>*</i>");
	$("#itemDetails thead tr th:nth-child(2)").append("<i class='text-red-1'>*</i>");
});
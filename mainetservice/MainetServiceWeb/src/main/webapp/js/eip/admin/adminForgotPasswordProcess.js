/*** Forgot Password Process ***/
var error500 = 'Internal Server Error !!!';
var adminsetp1Form = 'adminForgotPasswordForm';//Form Name
var adminsetp2Form = 'verifyOTPForm';//Form Name
var adminsetp3Form = 'setForgotPasswordForm';//Form Name
/*** Forgot Password Process ***/

var adminForgotPassword = 'AdminForgotPassword.html';//Get registration form
var adminisRegisteredMobile = '?IsRegisteredMobile';//Validate Mobile & Send OTP
var admingetOTPform = '?OTPVerficationFrm';//To get OTP form
var adminverifyOTP = '?verifyOTP';//OTP verification
var admingetReSetform = '?ResetPasswordFrm';//To get Reset Password Form form
var homePageURL = 'AdminHome.html';

/**
 * Method is used to get validate mobile form.(Step1)
 */

function isEmpty(elem) {
	
	if($.trim($(getElemId(elem)).val())=='')
		return true;
	else 
		return false;
}

function admintryStep1(e) {
    if (e.keyCode == 13) {
    	getAdminForgotPassStep2();
        return false;
    }
}
/**
 * Method to get OTP verification form 
 */
function getAdminForgotPassStep2() {

	var errorList = [];
	
	errorList = adminvalidateFormStep1(errorList);

	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(adminsetp1Form)).serialize();
		var response = __doAjaxRequest('AdminForgotPassword.html'+admingetOTPform, 'post', formData, false, 'html');
		openPopupWithResponse(response);
	}
	else
		showOTPError(errorList);
}

function admintryStep2(e) {
    if (e.keyCode == 13) {
    	getAdminForgotPassStep3();
        return false;
    }
}

/**
 * Method to get Reset password form
 */
function getAdminForgotPassStep3(){
	var errorList = [];

	errorList = adminvalidateFormStep2(errorList);

	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(adminsetp2Form)).serialize();
		var response = __doAjaxRequest('AdminForgotPassword.html?ResetPasswordFrm', 'post', formData, false, 'html');
		openPopupWithResponse(response);
	}
	else
		showError(errorList);	
}

function admintryStep3(e) {
    if (e.keyCode == 13) {
    	getAdminForgotPassStep4();
        return false;
    }
}

/**
 * Method to Reset the forgot password & redirect to home page.
 */
function getAdminForgotPassStep4(){
	var errorList = [];

	errorList = adminvalidateFormStep3(errorList);

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(adminsetp3Form)).serialize();
		var result = __doAjaxRequest('AdminForgotPassword.html?doResetPassword', 'post', formData, false, 'json');
		
		if (result == 'true') {
			formRedirect(homePageURL,true);
			//getAdminLoginFrm();
		} else {
			errorList.push(result);
		}
	}
	else
		showError(errorList);	
}

function getAdminLoginFrm() {

	var response = __doAjaxRequest('Home.html', 'get', {}, false, 'html');
	adminLogin(response);
}

/**
 * Method to resend OTP
 */
function adminresendOneTimePassword(){
	var errorList = [];
	var formData = $(document.getElementById(adminsetp2Form)).serialize();
	var result = __doAjaxRequest('AdminForgotPassword.html?ResendOTP', 'POST', formData, false);

	if (result == 'true'){
		errorList.push(getLocalMessage("admin.login.pass.send.success.msg"));
	} 
	else {
		errorList.push(result);
	}
	showError(errorList);
}

/**
 * Method to validate step 1 form.
 * 
 * @param errorList
 * @returns errorList
 */
function adminvalidateFormStep1(errorList) {

	if (isEmpty('mobNo')) {
		errorList.push(getLocalMessage("admin.login.mob.error"));
	}

	if (!isEmpty('mobNo')) {
		
		errorList= validateMobNo(getElementValue('mobNo'));
        if(errorList.length == 0){

				var formData = $(document.getElementById(adminsetp1Form)).serialize();
		
				var result = __doAjaxRequest('AdminForgotPassword.html?IsRegisteredMobile', 'POST', formData, false);
		
				if (result != null && result != "") 
				{
					if(result != 'success')
					{
						errorList.push(result);
					}
				}
				else{
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
				}
        }
	}
	
	return errorList;
}


/**
 * Method to validate step 2 form.
 * 
 * @param errorList
 * @returns errorList
 */
function adminvalidateFormStep2(errorList) {

	if (isEmpty('oneTimePassword')) {
		errorList.push(getLocalMessage("admin.login.otp.must.error"));
	}

	if (!isEmpty('oneTimePassword')) {

		var formData = $(document.getElementById(adminsetp2Form)).serialize();
		var result = __doAjaxRequest('AdminForgotPassword.html?verifyOTP', 'post', formData, false, 'json');

		if (result == 'success') {
			
		} 
		else
		{
			errorList.push(result);
		}
	}
	
	return errorList;
}

/***************************************************************************/


function loginDialog(result) {
	$('.popup-dialog').html(result);
	$('.popup-dialog').show();
	return false;
}


/**
 * Method to validate OTP from.
 * 
 * @param errorList
 * @returns errorList
 */
function validateOTPFrom(errorList) {

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("admin.login.mob.error"));
	}

	if (isEmpty('otpPassword')) {
		errorList.push(getLocalMessage("admin.login.otp.mustnot.empty.error"));
	}
	return errorList;
}



/**
 * Method to validate set password form
 * 
 * @param errorList
 * @returns errorList
 */
function adminvalidateFormStep3(errorList) {

	var newPwdID = document.getElementById('newPassword');
	var reEntPwdID = document.getElementById('reEnteredPassword');

	if (isEmpty('newPassword') || isEmpty('reEnteredPassword')) {
		errorList.push(getLocalMessage("admin.login.resetPass.pass.error"));
	}
if(getLocalMessage("eip.password.validation.enable")=='Y'){
	if ($.trim($(newPwdID).val()) == $.trim($(reEntPwdID).val())) {

		if ($(newPwdID).val().length > 7) {

			if ($(newPwdID).val().length < 16) {

				var passwordValidationRE = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
				var check = $(newPwdID).val().match(passwordValidationRE);

				if (check != null && check != 'null' && check != '') {

					// errorList.push("Entered password proper.");

				} else {
					errorList.push(getLocalMessage("admin.login.passMustContain.1st.error"));
					errorList.push(getLocalMessage("admin.login.passMustContain.2nd.error"));
					errorList.push(getLocalMessage("admin.login.passMustContain.3rd.error"));
					errorList.push(getLocalMessage("admin.login.passMustContain.4th.error"));
				}

			} else {
				errorList.push(getLocalMessage("admin.login.passMustContain.error"));
			}

		} else {
			errorList.push(getLocalMessage("admin.login.passMustContain.8char.error"));
		}

	} else {
		errorList.push(getLocalMessage("admin.login.bothMustSame.error"));
	}
}else{
	   if($(newPwdID).val().indexOf(" ") >= 0 || $(reEntPwdID).val().indexOf(" ") >= 0){
			errorList.push(getLocalMessage("eip.password.space"));
		}
		if($.trim($(newPwdID).val()) == $.trim($(reEntPwdID).val())){
			if($(newPwdID).val().length > 16){
				errorList.push(getLocalMessage("admin.login.passNotContain.15char.error"));
			}
		}else{
			errorList.push(getLocalMessage("admin.login.bothMustSame.error"));
		}
	  }
	return errorList;
}

function showOTPError(errorList) {
	
	
	var errMsg = '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	
    $('#otp_error').html(errMsg);
    if(errMsg){
    	$('#otp_error').show();
    }
}

/**
 * @param response is Popup Content.
 */

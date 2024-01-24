/*** Reset Password Process ***/
var error500 = 'Internal Server Error !!!';
var setpIFormName = 'agencyValidateMobileForm';//Form Name
var setpIIFormName = 'verifyOTPForm';//Form Name
var setpIIIFormName = 'resetPasswordForm';//Form Name
/*** Reset Password Process ***/

var agencyResetPassword = 'AgencyResetPassword.html';//Get registration form
var isRegisteredMobile = '?IsRegisteredMobile';//Validate Mobile & Send OTP
var getOTPform = '?OTPVerficationFrm';//To get OTP form
var verifyOTP = '?verifyOTP';//OTP verification
var getReSetform = '?ResetPasswordFrm';//To get Reset Password Form form
var homePageURL = 'CitizenHome.html';

/**
 * Method is used to get validate mobile form.(Step1)
 */

function isEmpty(elem) {
	
	if($.trim($(getElemId(elem)).val())=='' || $.trim($(getElemId(elem)).val())=='0')
		return true;
	else 
		return false;
}

function isEmptyNumField(elem) {
	if($.trim($(getElemId(elem)).val())=='')
		return true;
	else 
		return false;
}
function tryStepI(e) {
    if (e.keyCode == 13) {
    	getAgencyResetPassStepII();
        return false;
    }
}
/**
 * Method to get OTP verification form
 */
function getAgencyResetPassStepII() {

	var errorList = [];

		errorList = validateAgencyFormStepI(errorList);
	
	/*errorList = validateFormStepI(errorList);*/

	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(setpIFormName)).serialize();
		var response = __doAjaxRequest(agencyResetPassword+getOTPform, 'post', formData, false, 'html');
		 $('.content-page').html(response);
		
	}
	else
		showError(errorList);
}

function tryStepII(e) {
    if (e.keyCode == 13) {
    	getAgencyResetPassStepIII();
        return false;
    }
}

/**
 * Method to get Reset password form
 */
function getAgencyResetPassStepIII(){
	var errorList = [];

		
		errorList = validateAgencyFormStepII(errorList);
	
	/*errorList = validateFormStepII(errorList);*/

	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(setpIIFormName)).serialize();
		var response = __doAjaxRequest(agencyResetPassword+getReSetform, 'post', formData, false, 'html');
		 $('.content-page').html(response);
	}
	else{
		showError(errorList);	
}
}
	
function tryStepIII(e) {
    if (e.keyCode == 13) {
    	getAgencyResetPassStepIV();
        return false;
    }
}

/**
 * Method to Reset the reset password & redirect to home page.
 */
function getAgencyResetPassStepIV(){
	var errorList = [];

	
		
	errorList = validateAgencyFormStepIII(errorList);
	/*errorList = validateFormStepIII(errorList);*/

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(setpIIIFormName)).serialize();
		var result = __doAjaxRequest(agencyResetPassword+'?doResetPassword', 'post', formData, false);
		
		if (result == 'true') {
			//formRedirect(homePageURL);
			getALoginForm();
		} else {
			errorList.push(result);
			showError(errorList);	
		}
	}
	else
		showError(errorList);	
}

function getALoginForm() {

	var response = __doAjaxRequest(agencyLoginURL, 'get', {}, false, 'html');
	agencyLogin(response);
}

function agencyLogin(response) {
	 $('.content-page').html(response);
	
	var errorList = [];
	
	errorList.push(getLocalMessage("citizen.login.resetPass.success.msg"));
	/*errorList.push("Password reset successfully.");*/
	showError(errorList);	
}

/**
 * Method to resend OTP 
 */
function resendAgencyOTPResetPassword(){
	var errorList = [];
	
	var formData = $(document.getElementById(setpIIFormName)).serialize();
	var result = __doAjaxRequest(agencyResetPassword+'?ResendOTP', 'POST', formData, false);

	if (result == 'true'){
		errorList.push(getLocalMessage("citizen.login.pass.send.success.msg"));
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
function validateAgencyFormStepI(errorList) {

	

	if (isEmptyNumField('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	if (!isEmptyNumField('mobileNumber')) {
		
		errorList= validateMobNo(getElementValue('mobileNumber'));
        if(errorList.length == 0){

				var formData = $(document.getElementById(setpIFormName)).serialize();
		
				var result = __doAjaxRequest(agencyResetPassword+isRegisteredMobile, 'POST', formData, false);
		
				if (result != null && result != '') 
				{
					if(result != 'success')
					{
						errorList.push(result);
					}
				}
				else{
					errorList.push(getLocalMessage("citizen.login.internal.server.error"));
				}
        }
	}
	if (isEmpty('emplType')) {
		errorList.push(getLocalMessage("agency.login.agencyType.error"));
	}
	return errorList;
}



/**
 * Method to validate step 2 form.
 * 
 * @param errorList
 * @returns errorList
 */
function validateAgencyFormStepII(errorList) {

	if (isEmptyNumField('oneTimePassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.must.error"));
	}

	if (!isEmptyNumField('oneTimePassword')) {

		var formData = $(document.getElementById(setp2Form)).serialize();
		var result = __doAjaxRequest(agencyResetPassword+verifyOTP, 'post', formData, false);

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



/**
 * Method to validate OTP from.
 * 
 * @param errorList
 * @returns errorList
 */
function validateOTPFrom(errorList) {

	if (isEmptyNumField('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	if (isEmptyNumField('otpPassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.mustnot.empty.error"));
	}
	return errorList;
}



/**
 * Method to validate set password form
 * 
 * @param errorList
 * @returns errorList
 */
function validateAgencyFormStepIII(errorList) {

	var newPwdID = document.getElementById('newPassword');
	var reEntPwdID = document.getElementById('reEnteredPassword');

	if (isEmptyNumField('newPassword') || isEmptyNumField('reEnteredPassword')) {
		errorList.push(getLocalMessage("citizen.login.resetPass.pass.error"));
	}

	if ($.trim($(newPwdID).val()) == $.trim($(reEntPwdID).val())) {

		if ($(newPwdID).val().length > 7) {

			if ($(newPwdID).val().length < 16) {

				var passwordValidationRE = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
				var check = $(newPwdID).val().match(passwordValidationRE);

				if (check != null && check != 'null' && check != '') {

					// errorList.push("Entered password proper.");

				} else {
					errorList.push(getLocalMessage("citizen.login.passMustContain.1st.error"));
					errorList.push(getLocalMessage("citizen.login.passMustContain.2nd.error"));
					errorList.push(getLocalMessage("citizen.login.passMustContain.3rd.error"));
					errorList.push(getLocalMessage("citizen.login.passMustContain.4th.error"));
				}

			} else {
				errorList.push(getLocalMessage("citizen.login.passMustContain.error"));
			}

		} else {
			errorList.push(getLocalMessage("citizen.login.passMustContain.8char.error"));
		}

	} else {
		errorList.push(getLocalMessage("citizen.login.bothMustSame.error"));
	}

	return errorList;
}







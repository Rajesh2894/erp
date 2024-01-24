/*** Forgot Password Process ***/
var error500 = 'Internal Server Error !!!';
var setp1Form = 'agencyForgotPasswordForm';//Form Name
var setp2Form = 'verifyOTPForm';//Form Name
var setp3Form = 'setForgotPasswordForm';//Form Name
/*** Forgot Password Process ***/
var otpVerificationURL = 'AgencyOTPVerification.html';
var agencyForgotPassword = 'AgencyForgotPassword.html';//Get registration form
var isRegisteredMobile = '?IsRegisteredMobile';//Validate Mobile & Send OTP
var getOTPform = '?OTPVerficationFrm';//To get OTP form
var verifyOTP = '?verifyOTP';//OTP verification
var getReSetform = '?ResetPasswordFrm';//To get Reset Password Form form
var homePageURL = 'CitizenHome.html';

/**
 * Method is used to get registration form.(Step1)
 */
/*function getAgencyForgotPassStep1() {

	var response = __doAjaxRequest(agencyForgotPassword, 'get', {}, false, 'html');

	show_Poppup(response);
}*/

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

function tryStep1(e) {
    if (e.keyCode == 13) {
    	getAgencyForgotPassStep2();
        return false;
    }
}
/**
 * Method to get OTP verification form
 */
function getAgencyForgotPassStep2() {

	
	var errorList = [];	
	errorList = validateFormStep1(errorList);

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(setp1Form)).serialize();
		var response = __doAjaxRequest(agencyForgotPassword+getOTPform+'&checkOTPTypeValue=Register', 'post', formData, false, 'html');
		 $('.content-page').html(response);
	}
	else
		showError(errorList);
}


function validateFormStep1(errorList) {

	
	
	if (isEmptyNumField('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	
	
	if (!isEmptyNumField('mobileNumber')) {
		
		errorList= validateMobNo(getElementValue('mobileNumber'));
        if(errorList.length == 0){

				var formData = $(document.getElementById(setp1Form)).serialize();
		
				var result = __doAjaxRequest(agencyForgotPassword+isRegisteredMobile+'&checkOTPTypeValue=Register', 'POST', formData, false);
		
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

function tryStep2(e) {
    if (e.keyCode == 13) {
    	getAgencyForgotPassStep3();
        return false;
    }
}

/**
 * Method to get Reset password form
 */
function getAgencyForgotPassStep3(){
	var errorList = [];

	
		errorList = validateFormStep2(errorList);
	
	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(setp2Form)).serialize();
		var response = __doAjaxRequest(agencyForgotPassword+getReSetform, 'post', formData, false, 'html');
		 $('.content-page').html(response);
	}
	else
		showError(errorList);	
}

function tryStep3(e) {
    if (e.keyCode == 13) {
    	getAgencyForgotPassStep4();
        return false;
    }
}

/**
 * Method to Reset the forgot password & redirect to home page.
 */
function getAgencyForgotPassStep4(){
	
	var errorList = [];

		errorList = validateFormStep3(errorList);
	
	/*errorList = validateFormStep3(errorList);*/

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(setp3Form)).serialize();
		var result = __doAjaxRequest(agencyForgotPassword+'?doResetPassword', 'post', formData, false);
		
		if (result == 'true') {
			formRedirect(homePageURL);
		} else {
			errorList.push(result);
		}
	}
	else
		showError(errorList);	
}

/**
 * Method to resend OTP
 */
function resendAgencyOneTimePassword(){
	var errorList = [];
	var formData = $(document.getElementById(setp2Form)).serialize();
	var result = __doAjaxRequest(agencyForgotPassword+'?ResendOTP', 'POST', formData, false);
	
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
/*function validateFormStep1(errorList) {

	
	
	if (isEmptyNumField('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	
	
	if (!isEmptyNumField('mobileNumber')) {
		
		errorList= validateMobNo(getElementValue('mobileNumber'));
        if(errorList.length == 0){

				var formData = $(document.getElementById(setp1Form)).serialize();
		
				var result = __doAjaxRequest(agencyForgotPassword+isRegisteredMobile, 'POST', formData, false);
		
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
*/


/**
 * Method to validate step 2 form.
 * 
 * @param errorList
 * @returns errorList
 */
function validateFormStep2(errorList) {

	if (isEmptyNumField('oneTimePassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.must.error"));
	}

	if (!isEmptyNumField('oneTimePassword')) {

		var formData = $(document.getElementById(setp2Form)).serialize();
		var result = __doAjaxRequest(agencyForgotPassword+verifyOTP, 'post', formData, false);

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
 * Method forgot passwordProcess is used to submit registration form.
 */
/*function doRegForm() {

	var errorList = [];

	errorList = validateRegFrom(errorList);

	if (errorList.length == 0) {

		errorList = validateUniqueMobileEmailAddress(errorList);

		if (errorList.length == 0) {
			var regform = document.getElementById('agencyRegistrationForm');
			var formData = $(regform).serialize();
			var result = __doAjaxRequest(agencyRegURL, 'POST', formData, false);// result_is_OTP_verfication_URL

			if (result != null && result != '') {
				getAgencyOTPVerificationForm(result);
			} else {
				errorList.push(getLocalMessage("citizen.login.reg.fail.error"));
				showError(errorList);
			}
		} else {
			showError(errorList);
		}
	} else {
		showError(errorList);
	}
}*/



/**
 * Method to validate Unique Mobile and EmailAddress
 * 
 * @param errorList
 * @returns errorList
 */
function validateUniqueMobileEmailAddress(errorList) {

	var regform = document.getElementById('agencyRegistrationForm');

	if (!isEmptyNumField('newAgency.empemail')) {
		var formData = $(regform).serialize();
		var result = __doAjaxRequest(uniqueEmailChkURL, 'POST', formData, false);

		if (result != null && result != '') {
			errorList.push(result);
		}
	}

	if (!isEmptyNumField('newAgency.empmobno')) {

		var formData = $(regform).serialize();
		var result = __doAjaxRequest(uniqueMobileChkURL, 'POST', formData,
				false);

		if (result != null && result != '') {
			errorList.push(result);
		}
	}
	return errorList;
}



/**
 * Method doCitizenOTPVerification() is used for OTP Verification
 */
function doAgencyOTPVerification() {

	var errorList = [];

	errorList = validateOTPFrom(errorList);

	if (errorList.length == 0) {

		var otpForm = document.getElementById('agencyOTPVerificationForm');
		var formData = $(otpForm).serialize();
		var result = __doAjaxRequest(otpVerificationURL, 'POST', formData,
				false);

		if (result != null && result != '') {
			if (result == 'true') {
				getAgencySetPasswordForm();
			} else {
				errorList.push(result);
				showError(errorList);
			}
		} else {
			errorList.push(getLocalMessage("citizen.login.reg.otp.fail.error"));
			showError(errorList);
		}
		
	} else {
		showError(errorList);
	}
}

function getAgencySetPasswordForm() {
	
	openPopup('AgencySetPassword.html');
}





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
function validateFormStep3(errorList) {

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


/**
 * @param response is Popup Content.
 */


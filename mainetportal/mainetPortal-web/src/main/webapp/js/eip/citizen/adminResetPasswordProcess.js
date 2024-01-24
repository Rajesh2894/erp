/*** Reset Password Process ***/

var error500 = 'Internal Server Error !!!';
var adminResetPassword = 'AdminResetPassword.html';
var isRegisteredMobile = '?IsRegisteredMobile';//Validate Mobile & Send OTP
var getOTPform = '?OTPVerficationFrm';//To get OTP form
var verifyOTP = '?verifyOTP';//OTP verification
var getReSetform = '?ResetPasswordFrm';//To get Reset Password Form form
var homePageURL = 'CitizenHome.html';

/**
 * Method is used to get validate mobile form.(Step1)
 */
function isEmpty(elem) {
	
	if($.trim($(getElemId(elem)).val())=='')
		return true;
	else 
		return false;
}

function admintryStepI(e) {
    if (e.keyCode == 13) {
    	getAdminResetPassStepII();
        return false;
    }
}
/**
 * Method to get OTP verification form
 */
function getAdminResetPassStepII() {
	
	var errorList = [];

	errorList = adminvalidateFormStepI(errorList);

	if (errorList.length == 0) 
	{
		 showloader(true);
   	    	setTimeout(function(){
				var formData = $(document.getElementById('adminValidateMobileForm')).serialize();
				var response = __doAjaxRequest(adminResetPassword+getOTPform, 'post', formData, false, 'html');
				$('.content-page').html(response);
			showloader(false);
   	    	},2000);
	}
	else
		showError(errorList);
}

function admintryStepII(e) {
    if (e.keyCode == 13) {
    	adminvalidateFormStepII();
        return false;
    }
}

/**
 * Method to get Reset password form
 */
function getAdminResetPassStepIII(){
	var errorList = [];

	errorList = adminvalidateFormStepII(errorList);

	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById('verifyOTPForm')).serialize();
		var response = __doAjaxRequest(adminResetPassword+getReSetform, 'post', formData, false, 'html');
		$('.content-page').html(response);
	}
	else
		showError(errorList);	
}

function admintryStepIII(e) {
    if (e.keyCode == 13) {
    	getAdminResetPassStepIV();
        return false;
    }
}

/**
 * Method to Reset the reset password & redirect to home page.
 */
function getAdminResetPassStepIV(){
	var errorList = [];

	errorList = adminvalidateFormStepIII(errorList);

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById('resetPasswordForm')).serialize();
		var result = __doAjaxRequest(adminResetPassword+'?doResetPassword', 'post', formData, false);
		
		if (result == 'true') {
			//formRedirect(homePageURL);
			getAdminLoginFrm();
		} else {
			errorList.push(result);
			showError(errorList);	
		}
	}
	else
		showError(errorList);	
}

function getAdminLoginFrm() {

	var response = __doAjaxRequest('AdminLogin.html', 'get', {}, false, 'html');
	adminLogin(response);
}

function adminLogin(response) {

	$('.content-page').html(response);

	/*showModalBox(childPoppup);*/
	
	var errorList = [];
	errorList.push(getLocalMessage("admin.login.resetPass.success.msg"));
	showError(errorList);	
	
}

/**
 * Method to resend OTP
 */
function resetPasswordResendOTP(){
	var errorList = [];
	var formData = $(document.getElementById('verifyOTPForm')).serialize();
	var result = __doAjaxRequest(adminResetPassword+'?ResendOTP', 'POST', formData, false);

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
function adminvalidateFormStepI(errorList) {

	if (isEmpty('mobNo')) {

		errorList.push(getLocalMessage("admin.login.mob.error"));
	}
	
	if($('#captchaSessionLoginValue').val() == '' && getCookie("accessibility")!='Y' ){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}
	
	if (!isEmpty('mobNo')) {
		
		errorList= validateMobNo(getElementValue('mobNo'));
        if(errorList.length == 0){

				var formData = $(document.getElementById('adminValidateMobileForm')).serialize();
		
				var result = __doAjaxRequest(adminResetPassword+isRegisteredMobile, 'POST', formData, false);
		
				if (result != null && result != '') 
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

function adminvalidateFormStepII() {
	
		var errorList=[];
		if (isEmpty('oneTimePassword')) {
			errorList.push(getLocalMessage("admin.login.otp.must.error"));
			showError(errorList);
		}
		if (!isEmpty('oneTimePassword')) {

			var formData = $(document.getElementById(setp2Form)).serialize();
			var result = __doAjaxRequest(adminResetPassword+verifyOTP, 'post', formData, false);
			$(".content-page").html(result);
			
		}
	}

/***************************************************************************/



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
function adminvalidateFormStepIII(errorList) {

	var newPwdID = document.getElementById('newPassword');
	var reEntPwdID = document.getElementById('reEnteredPassword');

	if (isEmpty('newPassword') || isEmpty('reEnteredPassword')) {
		
		errorList.push(getLocalMessage("admin.login.resetPass.pass.error"));
	}

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

	return errorList;

}

/**
 * @param response is Popup Content.
 */


/*** Forgot Password Process ***/
var error500 = 'Internal Server Error !!!';
var setp1Form = 'citizenForgotPasswordForm';//Form Name
var setp2Form = 'verifyOTPForm';//Form Name
var setp3Form = 'setForgotPasswordForm';//Form Name
/*** Forgot Password Process ***/

var citizenForgotPassword = 'CitizenForgotPassword.html';//Get registration form
var isRegisteredMobile = '?IsRegisteredMobile';//Validate Mobile & Send OTP
var doChangeMobileNumber='?changeMobileNumber';
var nonRegisterMobile =  '?NonRegisterMobile';
var getOTPform = '?OTPVerficationFrm';//To get OTP form
var verifyOTP = '?verifyOTP';//OTP verification
var citizenOTPVerificationURL = 'CitizenOTPVerification.html';
var getReSetform = '?ResetPasswordFrm';//To get Reset Password Form form
var homePageURL = 'CitizenHome.html';
var updateMobileNumber='?updateMobileNumber';

/**
 * Method is used to get registration form.(Step1)
 */
function isEmpty(elem) {
	
	if($.trim($(getElemId(elem)).val())=='')
		return true;
	else 
		return false;
}

/*function tryStep1(e) {
	
    if (e.keyCode == 13) {
    	getCitizenForgotPassStep2($('#checkOTPTypeValue').val());
        return false;
    }
}*/
/**
 * Method to get OTP verification form
 */
/*function getCitizenForgotPassStep2(obj,otpType) {
	
	var errorList = [];	
	//var val=getLocalMessage("eip.commons.submitBT");
	errorList = validateCitizenFormStep1(errorList,otpType);	
	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(setp1Form)).serialize();
		var response = __doAjaxRequest(citizenForgotPassword+getOTPform+'&checkOTPTypeValue='+otpType, 'post', formData, false, 'html');
		var childPoppup = '.popup-dialog';
		$(childPoppup).addClass('login-dialog');
		$(childPoppup).html(response);
		var type=$('#mobileNumberType').val();
		  $('.content-page').html(response);
		$("#").html(response);
		
		
		$(childPoppup).find('#checkOTPType1').prepend('<input type=\'button\'  class=\'btn btn-primary\' value=\''+val+'\' onclick="getCitizenForgotPassStep3(\''+type +'\')"   />');
		$(childPoppup).find('#mobileTextForKeyLink1').prepend('<a href="javascript:void(0);" onclick="resendCitizenOneTimePassword(\''+type +'\')" >'+getLocalMessage("eip.citizen.otp.resendOTP")+'</a>');
		$(childPoppup).show();
	    showModalBox(childPoppup);
	}
	else{
		showError(errorList);
	}
}*/

function tryStep2(e) {
    if (e.keyCode == 13) {    	
    	getCitizenForgotPassStep3($('#otpvalid'),$('#mobileNumberType').val());
        return false;
    }
}

/**
 * Method to get Reset password form
 */
function getCitizenForgotPassStep3(obj,otpType){
	var errorList = [];
	errorList = validateCitizenFormStep2(errorList);
	
	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(setp2Form)).serialize();
		
		if(otpType=='Register'){
		      var response = __doAjaxRequest(citizenForgotPassword+getReSetform, 'post', formData, false);
		     $('.content-page').html(response);
		}else{
			var response = __doAjaxRequest(citizenForgotPassword+updateMobileNumber, 'post', formData, false);
			if (response == 'true') {
				/*formRedirect(homePageURL);*/
				location.reload(window.location.href);
			} else {
				errorList.push(result);
			}
			
		}
	}
	else
		showError(errorList);	
}

function tryStep3(e) {
    if (e.keyCode == 13) {
    	getCitizenForgotPassStep4();
        return false;
    }
}

/**
 * Method to Reset the forgot password & redirect to home page.
 */
function getCitizenForgotPassStep4(){
	
	var errorList = [];

		errorList = validateCitizenFormStep3(errorList);
	

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(setp3Form)).serialize();
		var result = __doAjaxRequest(citizenForgotPassword+'?doResetPassword', 'post', formData, false);
		
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
function resendCitizenOneTimePassword(){
	var errorList = [];
	var formData = $(document.getElementById(setp2Form)).serialize();
	var result = __doAjaxRequest(citizenForgotPassword+'?ResendOTP', 'POST', formData, false);
	
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
function validateCitizenFormStep1(obj,otpType) {
	var errorList=[];
	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}
	if($('#captchaSessionLoginValue').val() == '' && getCookie("accessibility")!='Y' ){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}
	if(errorList.length != 0){
		showError(errorList);
	}

	if (!isEmpty('mobileNumber')) {
		
		                            errorList= validateMobNo(getElementValue('mobileNumber'));
		                            if(errorList.length == 0){
						                    var formData = $(document.getElementById(setp1Form)).serialize();
									        var result=null;
									        if(otpType== 'Register'){
									             result = __doAjaxRequest(citizenForgotPassword+isRegisteredMobile+'&checkOTPTypeValue=Register', 'POST', formData, false);
									             $('.content-page').html(result);
									        }else{
									        	result = __doAjaxRequest(citizenForgotPassword+doChangeMobileNumber+'&checkOTPTypeValue=Nonregister', 'POST', formData, false);
									        	$('.content-page').html(result);
									        	
									        }
		                            }else{
		                            	showError(errorList);
		                            }
	    }
	
}



/**
 * Method to validate step 2 form.
 * 
 * @param errorList
 * @returns errorList
 */
function validateCitizenFormStep2(obj,otpType) {

	var errorList=[];
	if (isEmpty('oneTimePassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.must.error"));
		showError(errorList);
	}

	if (!isEmpty('oneTimePassword')) {

		var formData = $(document.getElementById(setp2Form)).serialize();
		var result = __doAjaxRequest(citizenForgotPassword+verifyOTP+'&checkOTPTypeValue='+otpType, 'post', formData, false);
		$('.content-page').html(result);
		
	}
}



/***************************************************************************/

/**
 * Method forgot passwordProcess is used to submit registration form.
 */
function doRegForm() {

	
	
	var errorList = [];

	errorList = validateRegFrom(errorList);

	if (errorList.length == 0) {

		errorList = validateUniqueMobileEmailAddress(errorList);

		if (errorList.length == 0) {
			var regform = document.getElementById('citizenRegistrationForm');
			var formData = $(regform).serialize();
			var result = __doAjaxRequest(citizenRegURL, 'POST', formData, false);// result_is_OTP_verfication_URL

			if (result != null && result != '') {
				getCitizenOTPVerificationForm(result);
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
}



/**
 * Method to validate Unique Mobile and EmailAddress
 * 
 * @param errorList
 * @returns errorList
 */
function validateUniqueMobileEmailAddress(errorList) {

	var regform = document.getElementById('citizenRegistrationForm');

	if (!isEmpty('newCitizen.empemail')) {
		var formData = $(regform).serialize();
		var result = __doAjaxRequest(uniqueEmailChkURL, 'POST', formData, false);

		if (result != null && result != '') {
			errorList.push(result);
		}
	}

	if (!isEmpty('newCitizen.empmobno')) {

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
function doCitizenOTPVerification() {

	
	
	
	var errorList = [];

	errorList = validateCitizenOTPFrom(errorList);

	if (errorList.length == 0) {

		var otpForm = document.getElementById('citizenOTPVerificationForm');
		var formData = $(otpForm).serialize();
		var result = __doAjaxRequest(citizenOTPVerificationURL, 'POST', formData,
				false);

		if (result != null && result != '') {
			if (result == 'true') {
				getCitizenSetPasswordForm();
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

/**
 * Method to validate OTP from.
 * 
 * @param errorList
 * @returns errorList
 */
function validateCitizenOTPFrom(errorList) {

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	if (isEmpty('otpPassword')) {
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
function validateCitizenFormStep3(errorList) {

	var newPwdID = document.getElementById('newPassword');
	var reEntPwdID = document.getElementById('reEnteredPassword');

	if (isEmpty('newPassword') || isEmpty('reEnteredPassword')) {
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


	function saveForm(obj, message, successUrl, actionParam)
	{
	 
	    var successMessage = getLocalMessage('citizen.editProfile.successMsg');
		if (!actionParam) {
			
			actionParam = "CitizenEditSave";
		}
		return doFormActionForSave(obj,successMessage, actionParam, true , successUrl);
	}
	


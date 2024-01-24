/*** Reset Password Process ***/
var error500 = 'Internal Server Error !!!';
var setpIFormName = 'citizenValidateMobileForm';//Form Name
var setpIIFormName = 'verifyOTPForm';//Form Name
var setpIIIFormName = 'resetPasswordForm';//Form Name
/*** Reset Password Process ***/

var citizenResetPassword = 'CitizenResetPassword.html';//Get registration form
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

function tryStepI(e) {
    if (e.keyCode == 13) {
    	getCitizenResetPassStepII('');
        return false;
    }
}


function tryStepII(e) {
    if (e.keyCode == 13) {
    	validateFormStepII();
        return false;
    }
}

/**
 * Method to get Reset password form
 */
/*function getCitizenResetPassStepIII(){
	var errorList = [];

		errorList = validateFormStepII(errorList);
	
	

	if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(setpIIFormName)).serialize();
		var response = __doAjaxRequest(citizenResetPassword+getReSetform, 'post', formData, false, 'html');
		$('.content-page').html(response);
	}
	else{
		showError(errorList);	
}
}*/
	
function tryStepIII(e) {
    if (e.keyCode == 13) {
    	getCitizenResetPassStepIV();
        return false;
    }
}

/**
 * Method to Reset the reset password & redirect to home page.
 */
function getCitizenResetPassStepIV(){
	var errorList = [];

	
		
		errorList = validateFormStepIII(errorList);
	

	if (errorList.length == 0) 
	{
		var str = $("#newPassword").val();
		var pass = window.btoa(str);
		$("#newPassword").val(pass);
		$("#reEnteredPassword").val(pass);
		var formData = $(document.getElementById(setpIIIFormName)).serialize();
		var result = __doAjaxRequest(citizenResetPassword+'?doResetPassword', 'post', formData, false);
		
		if (result == 'true') {
			//formRedirect(homePageURL);
			msgBoxConfirm();
			//getCLoginForm();
		} else {
			errorList.push(result);
			showError(errorList);	
		}
	}
	else
		showError(errorList);	
}

function msgBoxConfirm()
{
	var fst = getLocalMessage("citizen.login.resetPass.success.msg");
	
	var messageText = fst;
	var message='';
	message	+=	'<p style="text-align: center; padding: 15px 10px;">'+messageText+'</p>';
	message	+=	'<p style=\'text-align:center;margin: 2px;\'>';					
	
	message	+=	'<input type=\'button\' value=\'Ok\'';
	message	+=	' onclick="getCitizenLoginFormValidationPopUp(2)" class="btn btn-success"/>';
	message	+=	'</p>';
	$(errMsgDiv).html(message);	
	$(errMsgDiv).show();
	showModalBoxWithoutClose(errMsgDiv);
}

function getCLoginForm() {

	var response = __doAjaxRequest(citizenLoginURL, 'get', {}, false, 'html');
	getCitizenLoginForm('N');
}

function citizenLogin(response) {

	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	
	var errorList = [];
	
	errorList.push(getLocalMessage("citizen.login.resetPass.success.msg"));
	/*errorList.push("Password reset successfully.");*/
	showError(errorList);	
}

/**
 * Method to resend OTP 
 */
function resendOTPResetPassword(){
	var errorList = [];
	
	var formData = $(document.getElementById(setpIIFormName)).serialize();
	var result = __doAjaxRequest(citizenResetPassword+'?ResendOTP', 'POST', formData, false);

	if (result == 'true'){
		errorList.push(getLocalMessage("citizen.login.otp.send.success.msg"));
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

/**
 * Method to get OTP verification form
 */
function getCitizenResetPassStepII(obj) {
	var errorList = [];
		errorList = validateFormStepI(errorList);
	
   if (errorList.length == 0) 
	{
		var formData = $(document.getElementById(setpIFormName)).serialize();
		var response = __doAjaxRequest(citizenResetPassword+getOTPform, 'post', formData, false, 'html');
		  $('.content-page').html(response);
	}
	else
		showError(errorList);
}
function validateFormStepI() {
	var errorList = [];
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
        	  
        	   showloader(true);
        	  
       	    	setTimeout(function(){
					var formData = $(document.getElementById(setpIFormName)).serialize();
			
					var result = __doAjaxRequest(citizenResetPassword+isRegisteredMobile, 'POST', formData, false);
					//var result=doAjaxLoading(citizenResetPassword+isRegisteredMobile,formData,'login-panel')
					
					$('.content-page').html(result);
					showloader(false);
       	    	},2000);
				
				/*if (result != null && result != '') 
				{
					if(result != 'success')
					{
						errorList.push(result);
					}
				}
				else{
					errorList.push(getLocalMessage("citizen.login.internal.server.error"));
				}*/
           }else{
        	   showError(errorList); 
           }
	}
	
	
}



/**
 * Method to validate step 2 form.
 * Method to get Reset password form
 */
function validateFormStepII() {
	var errorList=[];
	if (isEmpty('oneTimePassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.must.error"));
		showError(errorList);
	}

	if (!isEmpty('oneTimePassword')) {
		var formData = $(document.getElementById(setp2Form)).serialize();
		var result = __doAjaxRequest(citizenResetPassword+verifyOTP, 'post', formData, false);
		$('.content-page').html(result);
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
function validateFormStepIII(errorList) {
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




/**
 * @param response is Popup Content.
 */


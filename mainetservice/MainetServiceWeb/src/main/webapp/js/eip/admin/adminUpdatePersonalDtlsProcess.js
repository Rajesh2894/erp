/*** Reset Password Process ***/

var error500 = 'Internal Server Error !!!';
var adminResetPassword = 'AdminResetPassword.html';
var isRegisteredMobile = '?IsRegisteredMobile';//Validate Mobile & Send OTP
var getOTPform = '?OTPVerficationFrm';//To get OTP form
var verifyOTP = '?verifyOTP';//OTP verification
var getReSetform = '?ResetPasswordFrm';//To get Reset Password Form form
var homePageURL = 'AdminHome.html';

function validateAndupdatePersonalDtls(){
	debugger;
	var errorList = [];
	var formData = $(document.getElementById('adminUpdatePersonalDtlsForm')).serialize();
	var URL1 = 'AdminUpdatePersonalDtls.html?updatePersonalDtls';
	var result = __doAjaxRequest(URL1, 'POST', formData, false,'');
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
	if (errorList.length != 0) {
		showResetError(errorList);
	}
	if(result == 'success')
		openPopup(adminResetPassword)
}

function showResetError(errorList){
	
	var errMsg = '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('.error-div').html(errMsg);
    if(errMsg){
    	$('.error-div').show();
    }
}

function openPopup(url){
	
	var response = __doAjaxRequest(url, 'get', {}, false, 'html');
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	
}
function skipToResetPassword(){
	openPopup(adminResetPassword)
}
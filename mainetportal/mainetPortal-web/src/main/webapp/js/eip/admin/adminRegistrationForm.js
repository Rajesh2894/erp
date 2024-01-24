var error500 = 'Internal Server Error !!!';
var adminRegURL = 'AdminRegistration.html';
var AdminOtpVerificationURL = '';
var childDivName	=	'.child-popup-dialog';
var adminOTPVerificationURL = 'AdminOTPVerification.html';
var adminHomePageURL = 'CitizenHome.html';

/*$(document).ready(function() {
	
	jQuery('.hasPincode').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','6');
		});
	jQuery('.hasMobileNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','10');
		});
	});*/





function getAdminOTPVerificationForm(AdminOtpVerificationURL)
{
	openPopup(AdminOtpVerificationURL);
}




function _adminAjaxRequest(obj, successMessage,divclassName,actionName,addParam,objChild){
	
	
	var errorList = [];
	if(($('#empGender').val() != '0' && $('#empGender').val()!= null ) && ($('#title').val() != '0' && $('#title').val()!= null ))  {
		
		if($('#title :selected').attr('code').toLowerCase() == 'mr.'){
			if($('#empGender :selected').attr('code').toLowerCase() == 'f'){
				errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
			}
		}else if($('#title :selected').attr('code').toLowerCase() == 'mrs.' || $('#title :selected').attr('code').toLowerCase() == 'miss.'){
             if($('#empGender :selected').attr('code').toLowerCase() == 'm'){
				 errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
			 }
		}
		
	}
	
		
		if (errorList.length == 0){
	         var response = _customAjaxRequest1(obj, successMessage,divclassName,actionName,addParam);
	         return response;
		}else{

			var errMsg = '<ul>';
            $.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
            errMsg += '</ul>';
            $('.error-div').remove();
            $('.error1-div').show();
		    $('.error1-div').html(errMsg);
		}   
	         
	   

}

function doAdminOTPVerification() {

	var errorList = [];

	errorList = validateAdminOTPFrom(errorList);

	if (errorList.length == 0) {

		var otpForm = document.getElementById('adminOTPVerificationForm');
		var formData = $(otpForm).serialize();
		var result = __doAjaxRequest(adminOTPVerificationURL, 'POST', formData,
				false);

		if (result != null && result != '') {
			if (result == 'true') {
				getAdminSetPasswordForm();
			} else {
				errorList.push(result);
				showAdminError(errorList);
			}
		} else {
			errorList.push(getLocalMessage("citizen.login.reg.otp.fail.error"));
			showAdminError(errorList);
		}
		
	} else {
		showAdminError(errorList);
	}
}

function validateAdminOTPFrom(errorList) {

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	if (isEmpty('otpPassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.mustnot.empty.error"));
	}
	return errorList;
}

function getAdminSetPasswordForm() {
	openPopup('AdminSetPassword.html');
}

function showAdminError(errorList) {
	var errMsg = '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
}


function resendAdminOTP() {
	var errorList = [];

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	var otpForm = document.getElementById('adminOTPVerificationForm');
	var formData = $(otpForm).serialize();
	var result = __doAjaxRequest('AdminOTPVerification.html?ResendOTP',
			'POST', formData, false);

	if (result == 'true') {
		errorList.push(getLocalMessage("citizen.login.pass.send.success.msg"));
	} else {
		errorList.push(result);
	}
	showAdminError(errorList);
}


function doAdminSetPassword() {

	var errorList = [];
	errorList = validateAdminSetPassword(errorList);

	if (errorList.length == 0) {
		var str = $("#enterPassword").val();
		var pass = window.btoa(str);
		$("#enterPassword").val(pass);
		var otpForm = document.getElementById('adminSetPasswordForm');
		var formData = $(otpForm).serialize();
		var result = __doAjaxRequest('AdminSetPassword.html', 'POST',
				formData, false);

		if (result == 'true') {
			//adminFormRedirect(adminHomePageURL);
			formRedirect(adminHomePageURL);
		} else {
			errorList.push(result);
		}
	}

	showAdminError(errorList);
}

function validateAdminSetPassword(errorList) {

	var newPwdID = document.getElementById('enterPassword');
	var reEntPwdID = document.getElementById('reEnteredPassword');

	if (isEmpty('enterPassword') || isEmpty('reEnteredPassword')) {
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




function fn_setDesignation(obj,objChild) {
	
	var deptId = obj.value;
	var selectObject = document.getElementById(objChild);
	//window.alert(selectObject);  //correct
	
	$(selectObject).find('option:gt(0)').remove(); 
	
	if (deptId > 0) {
		var postdata = 'deptId=' + deptId;
		
		var json = __doAjaxRequest('AdminRegistration.html?searchDesg',	'POST', postdata, false, 'json');
		
		var optionAsString = '';
		for(var i=0;  i<json.length;  i++){
			optionAsString += "<option value='"+ json[i].lookUpId +"'>" + json[i].lookUpDesc +"</option>";
		}
		$("#"+objChild).append(optionAsString);
		/*var lookUpList=__doAjaxRequest('AdminRegistration.html?searchDesg','post',postdata,false,'json');*/
		/*prepareChildrens(lookUpList, objChild);*/
		
	}
}


var error500 = 'Internal Server Error !!!';
var agencyRegURL = 'AgencyRegistration.html';
var agencyHomePageURL = 'CitizenHome.html';
var otpVerificationURL = 'AgencyOTPVerification.html';
function tryLogin(e) {
    if (e.keyCode == 13) {
    	doAgencyLogin();
        return false;
    }
}

function getAgencyRegistrationForm() {

	openPopup(agencyRegURL);
}



function doAgencyRegistrationForm(){
	
	var regdform = document.getElementById('agencyRegistrationForm');
	var formData = $(regdform).serialize();
	var result = __doAjaxRequest(agencyRegURL, 'POST', formData, false);
	
	getAgencyOTPVerificationForm(result);
}


function resendOTP() {
	var errorList = [];

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	var otpForm = document.getElementById('agencyOTPVerificationForm');
	var formData = $(otpForm).serialize();
	var result = __doAjaxRequest('AgencyOTPVerification.html?ResendOTP',
			'POST', formData, false);

	if (result == 'true') {
		errorList.push(getLocalMessage("citizen.login.pass.send.success.msg"));
	} else {
		errorList.push(result);
	}
	showError(errorList);
}

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

/*function _agencyAjaxRequest(obj, successMessage,divclassName,actionName,addParam,objChild){
	
	alert("HII");
	var errorList = [];
	if(($('#empGender').val() != '0' && $('#empGender').val()!= null ) && ($('#title').val() != '0' && $('#title').val()!= null ))  {
				
				if($('#title :selected').attr('code').toLowerCase() == 'mr.'){
					if($('#empGender :selected').attr('code').toLowerCase() == 'm' || $('#empGender :selected').attr('code').toLowerCase() == 't'){
					 }else{
						errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
					}
				}else if($('#title :selected').attr('code').toLowerCase() == 'mrs.' || $('#title :selected').attr('code').toLowerCase() == 'miss.'){
		             if($('#empGender :selected').attr('code').toLowerCase() == 'f' || $('#empGender :selected').attr('code').toLowerCase() == 't'){
					 }else{
						 errorList.push(getLocalMessage("citizen.login.title.right.error") +"<br/>" +getLocalMessage("citizen.login.gender.right.error"));
					 }
				}
				
			}
	
		
		if (errorList.length == 0){
	         var response = _customAjaxRequest1(obj, successMessage,divclassName,actionName,addParam);
	         return response;
		}else{
            alert("Eror");
			var errMsg = '<ul>';
            $.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
            errMsg += '</ul>';
            $('.error-div').remove();
            $('.error1-div').html(errMsg);
            $('.error1-div').show();
		}   
	         
	   

}*/


function validateOTPFrom(errorList) {

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	if (isEmpty('otpPassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.mustnot.empty.error"));
	}
	return errorList;
}

function doAgencySetPassword() {

	var errorList = [];
	errorList = validateSetPassword(errorList);

	if (errorList.length == 0) {
		var str = $("#enterPassword").val();
		var pass = window.btoa(str);
		$("#enterPassword").val(pass);
		var otpForm = document.getElementById('agencySetPasswordForm');
		var formData = $(otpForm).serialize();
		var result = __doAjaxRequest('AgencySetPassword.html', 'POST',
				formData, false);
 
		
		if (result == 'true') {
			formRedirect(agencyHomePageURL);
		} else {
			errorList.push(result);
		}
	}

	showError(errorList);
}



/**
 * Method getCitizenOTPVerificationForm() is used get OTP VerificationForm
 */

/**
 * Method doAgencyRegistrationForm() is used to submit registration form.
 */
/*function doAgencyRegistrationForm() {

	var errorList = [];

	errorList = validateRegFrom(errorList);

	if (errorList.length == 0) {

		errorList = validateUniqueMobileEmailAddress(errorList);

		if (errorList.length == 0) {
			var regform = document.getElementById('agencyRegistrationForm');
			var formData = $(regform).serialize();
			var result = __doAjaxRequest(agencyRegURL, 'POST', formData, false);// result
			// is
			// OTP
			// verfication
			// URL

			if (result != null && result != '') {
				getAgencyOTPVerificationForm(result);
			} else {
				errorList.push('Registration Failed.');
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
 * Method to validateagency registration form
 * 
 * @param errorList
 * @returns errorList
 */
/*function validateRegFrom(errorList) {
	var iscorres=$('input:radio[name=isCorrespondence]:checked').val();
	
	
	if(document.agencyRegistrationForm.agencyType.value=='0'){
		
		errorList.push("Please select your Title.");
	}
	if (isEmpty('newAgency.empname')) {
		errorList.push("Agency name must not be empty.");
	}

	if (isEmpty('newAgency.empMName')) {
		errorList.push("Owner name must not be empty.");
	}
	
//	if (isEmpty('newCitizen.empemail')) {
//		errorList.push("Email-Id must not be empty.");
//	}
	
if(document.agencyRegistrationForm.empGender.value=='0'){
		
		errorList.push("Please select your Gender.");
	}
	
	//alert($.trim($(getElemId('newCitizen.empGender')).val()));
	if ($.trim($(getElemId('newAgency.empGender')).val()) == '0') {
		errorList.push("Please select gender.");
	}
	
	if(isEmpty('newCitizen.empGender')){
		errorList.push("Please select your Gender.");
		
	}

	if (isEmpty('newAgency.empdob')) {
		errorList.push("Please select date of birth.");
	}
	if (isEmpty('newAgency.empmobno')) {
		errorList.push("Mobile number must not be empty.");
	}
	if(isEmpty('newAgency.empAddress'))
		{
		errorList.push("Permanent address must not be empty.");
		
		}
	if(isEmpty('newAgency.panCardNo'))
	{
	errorList.push("PAN No must not be empty.");
	
	}
	
	if(iscorres=='N')
		{
		if(isEmpty('newAgency.empCorAddress1'))
		{
		errorList.push("Correspondence address must not be empty.");
		
		}
		
		if(isEmpty('newAgency.corPincode'))
		{
			errorList.push("Correspondence pincode must not be empty.");
		}
		}
	return errorList;
}
*/

function doRefreshLoginCaptcha(){
	 var idxRAND=Math.floor(Math.random()*90000) + 10000;
	 $("#cimg").attr("src",'AdminRegistration.html?captcha&id='+idxRAND);
	 //$("#cimg").attr("alt",'CitizenRegistration.html?captcha&id='+idxRAND);
}

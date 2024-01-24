var error500 = 'Internal Server Error !!!';
var agencyLoginURL = 'AgencyLogin.html';
var otpVerificationURL = 'AgencyOTPVerification.html';

function tryAgencyLogin(e) {
    if (e.keyCode == 13) {
    	doAgencyLogin();
        return false;
    }
}



function getAgencyOTPVerificationForm(otpVerificationURL) {
	openPopup(otpVerificationURL);
}




// * Method to validate login credentials
//* @param errorList
// * @returns errorList
 
function validateAgencyLoginFrom(errorList) {

	if (isEmpty('emplType')) {
		errorList.push(getLocalMessage("agency.login.agencyType.error"));
	}
	if (isEmpty('loginName')) {
		errorList.push(getLocalMessage("agency.login.userid.error"));
	}

	if (isEmpty('emppassword')) {
		errorList.push(getLocalMessage("agency.login.pass.error"));
	}

	return errorList;
}

function validateAgencyLoginFrom1(errorList) {

	if (isEmpty('fdUserName')) {
		errorList.push("  Enter Your Name ");
	}
	if (isEmpty('mobileNo')) {
		errorList.push("  Enter Your Mobile No");
	}

	if (isEmpty('emailId')) {
		errorList.push("  Enter Your EmailId");
	}

	if (isEmpty('feedback.feedBackDetails')) {
		errorList.push("  Enter Your Detail");
	}
	return errorList;
}

function agencyForgotPassword() {
	var errorList = [];
	errorList.push(getLocalMessage("agency.login.forgotPass.proccess.msg"));
	showError(errorList);
}


function _agencyAjaxRequest(obj, successMessage,divclassName,actionName,addParam,objChild){
	
	
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

			var errMsg = '<div>';
            $.each(errorList, function(index) {
				errMsg += '<li>' + errorList[index] + '</li>';
			});
            errMsg += '</div>';
            $('.error-div').remove();
            $('.error1-div').html(errMsg);
            $('.error1-div').show();
		}   
	         
	   

}
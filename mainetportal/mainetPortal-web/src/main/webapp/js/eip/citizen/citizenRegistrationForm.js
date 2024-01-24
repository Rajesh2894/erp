var error500 = 'Internal Server Error !!!';
var citizenRegURL = 'CitizenRegistration.html';
var uniqueMobileChkURL = 'CitizenRegistration.html?IsUniqueMobile';
var uniqueEmailChkURL = 'CitizenRegistration.html?IsUniqueEmailID';
var citizenOTPVerificationURL = 'CitizenOTPVerification.html';
var citizenHomePageURL = 'CitizenHome.html';
var updateEmilIdForCitizen='CitizenHome.html?UpdateEmailID';



function doCitizenRegistrationForm(obj) {

	
	
	var errorList = [];
	errorList = validateRegFrom(errorList);

	if (errorList.length == 0) {

		/*alert('inside if()-1');*/
		errorList = validateUniqueMobileEmail(errorList);
			/*alert(errorList);*/
		if (errorList.length == 0) {
			var regform = document.getElementById('citizenRegistrationForm');
			var formData = $(regform).serialize();
			/*alert(formData);*/
			var result = __doAjaxRequest(citizenRegURL+"?registerCitizen", 'POST', formData, false);
			
			/*	alert(result);*/
			if (result != null && result != '') {
				{
					if(result == 'captchaNotMatched'){
						errorList.push(getLocalMessage("citizen.login.reg.captha.valid.error"));
						doRefreshCaptcha();
						$('#captchaSessionValue').val('');
						showError(errorList);
					}else{
						
						getCitizenOTPVerificationForm(result);
					}
					
				}
				
			} else {
				
				errorList.push(getLocalMessage("citizen.login.reg.fail.error"));
				doRefreshCaptcha();
				$('#captchaSessionValue').val('');
				showError(errorList);
				
			}
		} else {
			
			doRefreshCaptcha();
			$('#captchaSessionValue').val('');
			showError(errorList);
		}
	} else {
		
		doRefreshCaptcha();
		$('#captchaSessionValue').val('');
		showError(errorList);
	}
}




function validateRegFrom(errorList) {
	var iscorres=$('input:radio[name=isCorrespondence]:checked').val();
	
	
	
	if(document.citizenRegistrationForm.title.value=='0'){
		
		errorList.push(getLocalMessage("citizen.login.reg.title.error"));
	}
	 if(document.getElementById('newCitizen.title').value == '')
    {
    	errorList.push(getLocalMessage("citizen.login.reg.title.error"));
    }
	
	if (isEmpty('newCitizen.empname')) {
		errorList.push(getLocalMessage("citizen.login.reg.fname.error"));
	}
	
	if (isEmpty('newCitizen.empLName')) {
		errorList.push(getLocalMessage("citizen.login.reg.lname.error"));
	}
	if(document.getElementById('empGender').value == ''){
    	errorList.push(getLocalMessage("citizen.login.reg.gender.error"));
    }
	
    if(document.citizenRegistrationForm.empGender.value=='0'){
		
		errorList.push(getLocalMessage("citizen.login.reg.gender.error"));
	}
	
	if ($.trim($(getElemId('newCitizen.empGender')).val()) == '0') {
		errorList.push(getLocalMessage("citizen.login.reg.gender.error"));
	}
	
   
	
	var empDOB = document.getElementById('newCitizen.empdob').value;
    if(document.getElementById('newCitizen.empdob').value == "")
    {
    	errorList.push(getLocalMessage("citizen.login.reg.dob.error"));
    }else
    {    	
	if(!empDOB.match(/^(0[1-9]|[12][0-9]|3[01])[\- \/.](?:(0[1-9]|1[012])[\- \/.](19|20)[0-9]{2})$/))
        {
	      errorList.push(getLocalMessage("citizen.login.reg.dob.error2")); 
        }
    	else 
    		
    		{
    		var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/;
    		 var matchArray = empDOB.match(datePat);
    		  month = matchArray[3]; // parse date into variables
    		    day = matchArray[1];
    		    year = matchArray[4];
    		 
    		    
    		    if (month < 1 || month > 12) { // check month range
    		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
    		    }
    		 
    		    if (day < 1 || day > 31) {
    		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
    		    }
    		 
    		    if ((month==4 || month==6 || month==9 || month==11) && day==31) {
    		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
    		    }
    		 
    		    if (month == 2) { // check for february 29th
    		    var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    		    if (day>29 || (day==29 && !isleap)) {
    		    	 errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
    		    }
    		    }
    	
    		    	   var today = new Date();
    		    	   var curr_date = today.getDate();
    		    	   var curr_month = today.getMonth() + 1;
    		    	   var curr_year = today.getFullYear();

    		    	   var pieces = empDOB.split('/');
    		    	   var birth_date = pieces[0];
    		    	   var birth_month = pieces[1];
    		    	   var birth_year = pieces[2];

    		    	   if(curr_date < birth_date)
    		    		   curr_month=curr_month-1;
    		    		 if(curr_month < birth_month)
    		    			 curr_year=curr_year-1;
    		    		 curr_year=curr_year-birth_year;
    		    			 if( curr_year <= 0)
    		    			 	// because It should show same message in both conditions
    		    				// errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
    		    				errorList.push(getLocalMessage("citizen.login.reg.dob.error3"));
    		    			 else if(curr_year<18)
    		    				 errorList.push(getLocalMessage("citizen.login.reg.dob.error3")); 
    		    				 
    		    	}
    		
    	}
    	if(isEmpty('newCitizen.empemail')){
	    	errorList.push(getLocalMessage("citizen.login.valid.email.error"));
	    }
    
	if(document.getElementById('newCitizen.mobileExtension').value == '')
    {
    	errorList.push(getLocalMessage("citizen.login.mob.ext.error"));
    }
var mobileNo=document.getElementById('newCitizen.empmobno').value;
	if(mobileNo.length!=""){
		var count=0;
		
		for(var i=0; i<mobileNo.length; i++){
			
			var val=mobileNo.charAt(i);
			if(val==0){
				count++;
			}
			
		} 
		if(mobileNo.charAt(0) < 1){
			errorList.push(getLocalMessage("citizen.login.valid.mob.error"));
		}
		
		if(count==10){
			errorList.push(getLocalMessage("citizen.login.valid.mob.error"));
		}else if(mobileNo.length<=6){
			errorList.push(getLocalMessage("citizen.login.valid.10digit.mb.error"));
		}
		
	}else{
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}
	
    if($('#captchaSessionValue').val() == ''){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}
    if($('#isAcceptance').is(':checked') == false)
    {
    	errorList.push(getLocalMessage("citizen.login.reg.policy.error"));
    }
    
    	
    
	    
 
	
		if(!isEmpty('newCitizen.empemail')){
		
		var empEmail=document.getElementById('newCitizen.empemail').value;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		
		if (reg.test(empEmail) == false) 
	    {
	        errorList.push(getLocalMessage("citizen.login.valid.email.error"));
	       
	    }
		
	 }
	
	
	
	
	
	
	/*if(isEmpty('newCitizen.empAddress'))
		{
		errorList.push(getLocalMessage("citizen.login.reg.paddress.error"));
		
		}*/
	
		/*var pinCode=document.getElementById('newCitizen.pincode').value;
	
		if(pinCode.length<=6){
			 var count=0;
			if(pinCode.length<6){
				if(pinCode.length==""){
					errorList.push(getLocalMessage("citizen.login.reg.pincode.error"));
				}else{
					errorList.push(getLocalMessage("citizen.login.reg.pincode.length.error"));
				}
				
			}
			
			for(var i=0; i<pinCode.length; i++){
				
				var eachPinCode=pinCode.charAt(i);
				if(eachPinCode==0){
					count++;
				}
				
			}
			
			if(count==6){
				errorList.push(getLocalMessage("citizen.login.reg.pincode.digits.error"));
			}
			
			
		}*/
	
	
	/*if(iscorres=='N')
		{
		
		var corresPinCode=document.getElementById('newCitizen.corPincode').value;
			if(isEmpty('newCitizen.empCorAddress1'))
			{
				errorList.push(getLocalMessage("citizen.login.reg.caddress.error"));
		
			}
		
			if(corresPinCode.length<6){
				if(corresPinCode.length==""){
					errorList.push(getLocalMessage("citizen.login.reg.cpincode.error"));
				}else{
					errorList.push(getLocalMessage("citizen.login.reg.cpincode.length.error"));
				}
				
			}
			
			
		}*/
	
	
	
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
	return errorList;
}
	   

function validateEmail(emailField){
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

    if (reg.test(emailField.value) == false) 
    {
        alert('Invalid Email Address');
        return false;
    }

    return true;

}



/**
 * Method to validate Unique Mobile and EmailAddress
 * 
 * @param errorList
 * @returns errorList
 */
function validateUniqueMobileEmail(errorList) {

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
 * OTP Verification
 */

/**
 * Method getCitizenOTPVerificationForm() is used get OTP VerificationForm
 */
function getCitizenOTPVerificationForm(citizenOTPVerificationURL) {
	
	openPopup(citizenOTPVerificationURL);
}

/**
 * Method doCitizenOTPVerification() is used for OTP Verification
 */
function doCitizenOTPVerification() {

	var errorList = [];

	errorList = validateOTPFrom(errorList);

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
function validateOTPFrom(errorList) {

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	if (isEmpty('otpPassword')) {
		errorList.push(getLocalMessage("citizen.login.otp.mustnot.empty.error"));
	}
	return errorList;
}

function resendOTP() {
	var errorList = [];

	if (isEmpty('mobileNumber')) {
		errorList.push(getLocalMessage("citizen.login.mob.error"));
	}

	var otpForm = document.getElementById('citizenOTPVerificationForm');
	var formData = $(otpForm).serialize();
	var result = __doAjaxRequest('CitizenOTPVerification.html?ResendOTP',
			'POST', formData, false);

	if (result == 'true') {
		errorList.push(getLocalMessage("citizen.login.pass.send.success.msg"));
	} else {
		errorList.push(result);
	}
	showError(errorList);
}

/**
 * Method getCitizenSetPasswordForm() is used to get Citizen Set Password Form
 */
function getCitizenSetPasswordForm() {
	openPopup('CitizenSetPassword.html');
}

/**
 * Method doCitizenSetPassword() is used for Citizen Set Password
 */
function doCitizenSetPassword() {

	var errorList = [];
	errorList = validateSetPassword(errorList);

	if (errorList.length == 0) {
		var str = $("#enterPassword").val();
		var pass = window.btoa(str);
		$("#enterPassword").val(pass);
		var otpForm = document.getElementById('citizenSetPasswordForm');
		var formData = $(otpForm).serialize();
		var result = __doAjaxRequest('CitizenSetPassword.html', 'POST',
				formData, false);

		if (result == 'true') {
			formRedirect(citizenHomePageURL);
		} else {
			errorList.push(result);
		}
	}

	showError(errorList);
}



/**
 * Method to validate set password form
 * 
 * @param errorList
 * @returns errorList
 */
function validateSetPassword(errorList) {

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

function doRefreshCaptcha(){
	 var idxRAND=Math.floor(Math.random()*90000) + 10000;
	 $("#simg").attr("src",'CitizenRegistration.html?captcha&id='+idxRAND);
}

function doUpdateCitizenEmailId(obj) {

	
	var errorList = [];	
	errorList = validateCitizenEmailId(errorList);

	if (errorList.length == 0) 
	{
		//var formData= $(obj).serialize();
		var formData = 'updateEmailId='+$('#citizenEmail').val();
		var result = __doAjaxRequest(updateEmilIdForCitizen, 'post', formData, false, 'html');
		
		
		if (result != null && result != '') {
			errorList.push(result);
		}else{
			/*formRedirect(citizenHomePageURL);*/
			location.reload();
		}
	}	
		
	showError(errorList);
}

	
function validateCitizenEmailId(errorList){
	
	
	if($('#citizenEmail').val() == ''){
		errorList.push(getLocalMessage("eip.citizen.editUserProile.eMail.empty"));
		
	}else{
		
		var empEmail=$('#citizenEmail').val();
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		
		if (reg.test(empEmail) == false) 
	    {
	        errorList.push(getLocalMessage("eip.citizen.editUserProile.eMail"));
	       
	    }
	}
	
	return errorList;
}

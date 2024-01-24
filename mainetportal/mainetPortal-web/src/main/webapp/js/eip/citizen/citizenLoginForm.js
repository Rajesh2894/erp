/*var error500 = 'Internal Server Error !!!';
var citizenLoginURL = 'CitizenLogin.html';

function tryCitizenLogin(e) {
    if (e.keyCode == 13) {
    	doCitizenLogin();
        return false;
    }
}


*//**
 * Method to validate login credentials
 * 
 * @param errorList
 * @returns errorList
 *//*
function validateCitizenLoginFrom(errorList) {

	if (isEmpty('ename')) {
		errorList.push(getLocalMessage("citizen.login.userid.error"));
	}

	if (isEmpty('citizenEmployee.emppassword')) {
		errorList.push(getLocalMessage("citizen.login.pass.error"));
	}
	
	if($('#captchaSessionLoginValue').val() == ''){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}

	return errorList;
}

function doRefreshLoginCaptcha(){
	 var idxRAND=Math.floor(Math.random()*90000) + 10000;
	 $("#cimg").attr("src",'CitizenRegistration.html?captcha&id='+idxRAND);
}


function citizenForgotPassword() {
	var errorList = [];
	errorList.push(getLocalMessage("citizen.login.forgotPass.proccess.msg"));
	showError(errorList);
}

*/
var error500 = 'Internal Server Error !!!';
var citizenLoginURL = 'CitizenLogin.html';

function tryCitizenLogin(e) {
    if (e.keyCode == 13) {
    	doCitizenLogin();
        return false;
    }
}


/**
 * Method to validate login credentials
 * 
 * @param errorList
 * @returns errorList
 */
function validateCitizenLoginFrom(errorList) {

	
	if (($("#loginselectedOrg").find("option:selected").attr('value') == 0 || $("#loginselectedOrg").find("option:selected").attr('value') == -1 )&& localStorage.getItem("selectorg") == 'Y') {
		errorList.push(getLocalMessage('eip.landingpage.urbanlocal.msg'));
	}

	
	
	if (isEmpty('ename')) {
		errorList.push(getLocalMessage("citizen.login.userid.error"));
	}

	if (isEmpty('citizenEmployee.emppassword')) {
		errorList.push(getLocalMessage("citizen.login.pass.error"));
	}
	
	if($('#captchaSessionLoginValue').val() == '' && getCookie("accessibility")!='Y' ){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}

	return errorList;
}

function doRefreshLoginCaptcha(){
	 var idxRAND=Math.floor(Math.random()*90000) + 10000;
	 $("#cimg").attr("src",'CitizenRegistration.html?captcha&id='+idxRAND);
	 //$("#cimg").attr("alt",'CitizenRegistration.html?captcha&id='+idxRAND);
}


function citizenForgotPassword() {
	var errorList = [];
	errorList.push(getLocalMessage("citizen.login.forgotPass.proccess.msg"));
	showError(errorList);
}


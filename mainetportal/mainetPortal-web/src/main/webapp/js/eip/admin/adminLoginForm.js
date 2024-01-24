var error500 = 'Internal Server Error !!!';
var adminResetPassword = 'AdminResetPassword.html';

function tryLogin(e) {
    if (e.keyCode == 13) {
    	doAdminLogin();
        return false;
    }
}


function adminForgotPassword() {
	var errorList = [];
	errorList.push(getLocalMessage("admin.login.forgotPass.proccess.msg"));
	showError(errorList);
}

function validateAdminLoginFrom(errorList) {

	if(isEmpty('emploginname')){
		
		errorList.push(getLocalMessage("admin.login.userid.error"));
		
	}
	if(isEmpty('adminEmployee.emppassword')){
		
		errorList.push(getLocalMessage("admin.login.pass.error"));
		
	}

	if($('#captchaSessionLoginValue').val() == '' && getCookie("accessibility")!='Y' ){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}
	
	return errorList;
}

function doRefreshLoginCaptcha(){
	 var idxRAND=Math.floor(Math.random()*90000) + 10000;
	 $("#cimg").attr("src",'AdminRegistration.html?captcha&id='+idxRAND);
	 //$("#cimg").attr("alt",'CitizenRegistration.html?captcha&id='+idxRAND);
}

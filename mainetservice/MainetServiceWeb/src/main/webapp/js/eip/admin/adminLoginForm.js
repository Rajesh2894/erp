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

	if ($('#selectedDistrict').val() == 0 || $('#selectedDistrict').val() == -1){
		
		errorList.push(getLocalMessage("eip.landingpage.urbanlocal.msg"));
	}
	
	if($('#selectedLocation').val() == 0 || $('#selectedLocation').val() == -1){
		
		errorList.push(getLocalMessage("eip.landingpage.selectedlocation.msg"));
		
	}
	return errorList;
}



function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}


function renewLicenseNumber(obj) {

	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"License Data Updated Successfully","RenewalLicenseApprovalReprinting.html?getLicensePrint",
				'generateLicenseNumber');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}



function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}


function transferLicenseNumber(obj) {

	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"License Data Updated Successfully","TransferLicenseApprovalReprinting.html?getLicensePrint",
				'generateLicenseNumber');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}


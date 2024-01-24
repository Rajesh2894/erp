
function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}


function generateLicenseNumber(obj) {
	
	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"License Generated Successfully", "LicenseGeneration.html?getLicensePrint",
				'generateLicenseNumber');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}


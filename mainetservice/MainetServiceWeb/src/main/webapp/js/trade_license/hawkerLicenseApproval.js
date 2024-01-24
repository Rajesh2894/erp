
function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}


function generateLicenseNumber(obj) {
	
	
	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"Hawker License Generated Successfully", "HawkerLicenseGeneration.html?getLicensePrint",
				'generateLicenseNumber');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}


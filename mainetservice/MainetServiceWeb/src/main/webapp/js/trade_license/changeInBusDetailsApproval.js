
function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}


function rePrintLicense(obj) {

	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"License Transfered Successfully","ChangeInBusinessPrinting.html?getLicensePrint",
				'generateLicenseNumber');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}


function rePrintLicense(obj) {

	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"License Details Saved Successfully","ChangeInCategorySubcategoryPrinting.html?getLicensePrint",
				'generateLicenseNumber');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}


function printdiv(printpage) {

	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

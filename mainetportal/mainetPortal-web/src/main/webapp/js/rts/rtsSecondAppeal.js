function validateForm() {
	var errorList = [];
	var address = $("#address").val();
	var Pincode = $("#pincode").val();
	var reasonForAppeal = $("#reasonForAppeal").val();
	var groundForAppeal = $("#groundForAppeal").val();

	if (address == "" || address == null || address == undefined) {
		errorList.push("Please Enter address");
	}
	/*if (Pincode == "" || Pincode == null || Pincode == undefined) {
		errorList.push("Please Enter Pincode");
	}*/
	if (reasonForAppeal == "" || reasonForAppeal == null
			|| reasonForAppeal == undefined) {
		errorList.push("Please Select Reason For Appeal ");
	}
	if (groundForAppeal == "" || groundForAppeal == null
			|| groundForAppeal == undefined) {
		errorList.push("Please Enter Ground For Appeal");
	}
	return errorList;
}

function saveForm(element) {
	var errorList = [];
	errorList = validateForm();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var result= saveOrUpdateForm(element, "Try again after some time!",'CitizenHome.html', 'saveSecondAppeal');
	}
}
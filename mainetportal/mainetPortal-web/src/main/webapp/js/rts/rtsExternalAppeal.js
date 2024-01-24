function validateForm() {
	var errorList = [];

	let apmApplicationId = $('#apmApplicationId').val();
	let title = $('#title').val();
	let fName = $('#fName').val();
	let lName = $('#lName').val();
	let gender = $('#gender').val();
	let mobileNo = $('#mobileNo').val();
	let address = $("#address").val();
	// var objectionReason = $("input[name='objectionReason']:checked"). val();
	var objectionReason = $('input[type=radio]:checked').val();
	let groundForAppeal = $("#groundForAppeal").val();

	if (apmApplicationId == "" || apmApplicationId == null
			|| apmApplicationId == undefined) {
		errorList.push(getLocalMessage("rti.citizenHme.entApplicaNo"));
	}
	if (title == "0" || title == undefined) {
		errorList.push(getLocalMessage("rti.validation.title"));
	}

	if (fName == "" || fName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantFirstName"));
	}
	if (lName == "" || lName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantLastName"));
	}
	if (gender == "0" || gender == undefined) {
		errorList.push(getLocalMessage("rti.validation.Gender"));
	}
	if (mobileNo == "" || mobileNo == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo"));
	}
	if (address == "" || address == undefined) {
		errorList.push(getLocalMessage("rti.citizenHme.entAddress"));
	}

	if (address == "" || address == null || address == undefined) {
        errorList.push(getLocalMessage("rti.citizenHme.entAddress"));
	}

	if (objectionReason == "" || objectionReason == null
			|| objectionReason == undefined) {
		errorList.push(getLocalMessage("rti.citizenHme.resnFrAppeal"));
	}
	if (groundForAppeal == "" || groundForAppeal == null
			|| groundForAppeal == undefined) {
		errorList.push(getLocalMessage("rti.citizenHome.enterGrdAppeal"));
	}
	return errorList;
}

function saveForm(element) {
	var errorList = [];
	errorList = validateForm();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var result= saveOrUpdateForm(element, "Try again after some time!",'CitizenHome.html', 'saveExternalAppeal');
	}
}
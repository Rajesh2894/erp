var department;

$(document).ready(function() {
	$("#pionrepname").hide();
	$("#appName").hide();
	$("#applicantRepName").hide();
	/* var department = $("#deptName").val(); */
	department = $("#deptCode").val();
	if (department == "RTI") {
		$("#showPioName").show();
		$(".applicantName").show();
		$(".ownerName").hide();
		$(".pioname").show();
		$("#nameofPerson").hide();

	} else {
		$("#showPioName").hide();
		$(".applicantName").hide();
		$(".ownerName").show();
		$(".pioname").hide();
		$("#nameofPerson").show();
	}

});

function submitEntryDetails(element) {

	var errorList = [];
	errorList = validateHearingForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, "shedule save successfully!",
				'AdminHome.html', 'saveHearing');
	} else {
		displayErrorMsgs(errorList);
	}
}
function validateHearingForm(errorList) {
	var nameOfHearingAuthority = $("#nameofHearingAuthoritys").val();
	var hearingRemark = $("#remark").val();
	var availPerson = $("#availPerson").val();
	var name = $("#name").val();
	var mobno = $("#mobno").val();
	var hearingStatus = $("#hearingStatus").val();
	// added validation for applicant name, applicant representative name & pio
	// representative
	var applicantName = $("#applicantName").val();
	// var applicantRepName = $("#applicantRepName").val();
	var pioRepresentativeName = $("#pioRepresentativeName").val();
	var pionrepname = $("#pionrepname").val();
	var decisionInFavorOf = $("#decisionInFavorOf").val();
	var radioCheck = $('input[type=radio]:checked').val();

	if (nameOfHearingAuthority == '' || nameOfHearingAuthority == undefined) {
		errorList
				.push(getLocalMessage("obj.enter.name.hearing.authority"));
	}
	if (hearingRemark == '' || hearingRemark == undefined) {
		errorList.push(getLocalMessage("obj.validation.hearingRemark"));
	}
	if (availPerson == '' || availPerson == undefined) {
		errorList.push(getLocalMessage("obj.select.avail.person"));
	}
	if (department != "RTI") {
		if (name == '' || name == undefined) {
			errorList.push(getLocalMessage("obj.enter.person.name"));
		}
	}

	if (mobno == '' || mobno == undefined) {
		errorList.push(getLocalMessage("obj.enter.mobNo"));
	} else {
		// D#80201 validate mobile no
		let error = validateMobNo(mobno);
		error.length > 0 ? errorList.push(error) : "";
	}
	if (hearingStatus == '' || hearingStatus == undefined
			|| hearingStatus == '0') {
		errorList.push(getLocalMessage("obj.select.hearing.status"));
	}
	// added validation for applicant name, applicant representative name & pio
	// representative

	if (!department == "RTI" && radioCheck == 'O') {
		if (applicantName == '' || applicantName == undefined) {
			errorList.push(getLocalMessage("obj.enter.applicant.name"));
		}
	}
	if (radioCheck == 'R') {
		if (applicantRepName == '' || applicantRepName == undefined) {
			errorList
					.push(getLocalMessage("obj.enter.applicant.representative.name"));
		}
	}

	/*
	 * if (radioCheck == 'PR') { if (pionrepname == '' || pionrepname ==
	 * undefined) { errorList.push(getLocalMessage("Please enter Name Of PIO
	 * Representative")); } }
	 */

	if (!department == "RTI" && radioCheck == 'E') {
		if (pioRepresentativeName == '' || pioRepresentativeName == undefined) {
			errorList
					.push(getLocalMessage("obj.enter.pio.representative.name"));
		}
	}

	// D#80201 email validation
	let emailId = $("#emailid").val();
	if (emailId != '' && emailId != undefined) {
		var filter = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(emailId)) {
			errorList
					.push(getLocalMessage("collection.validation.invalidemail"));
		}
	}
	if ((decisionInFavorOf == '' || decisionInFavorOf == undefined || decisionInFavorOf == '0')
			&& department != "RTI") {
		errorList.push(getLocalMessage("obj.select.decision.favor"));
	}

	return errorList;
}

function displayErrorMsgs(errorList) {

	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;

}
function displayPiorepName() {

	// var radioCheck = $('input[type=radio]:checked').val();
	var radioCheck = $("input[name='hearingInspectionDto.availPerson']:checked")
			.val();
	if (radioCheck == 'O') {
		$("#appName").show();
		$("#applicantRepName").hide();
	} else if (radioCheck == 'R') {
		$("#applicantRepName").show();
		$("#appName").hide();
	}
}

function displayPioName() {

	var radioCheck = $("input[name='hearingInspectionDto.repName']:checked")
			.val();
	// var radioCheck = $('#repName input[type=radio]:checked').val();
	if (radioCheck == 'E') {
		$("#pionrepname").show();
	}

	if (radioCheck == 'P') {
		$("#pionrepname").hide();
	}
}


/*#34050*/
var inspectionDisableForRTi;


$(document).ready(function() {
	
	$("#rtiapplicant").hide();
	var department = $("#deptCode").val();
	$("#appName").hide();
	$("#appRepName").hide();
	if (department == "RTI") {
		$("#rtiapplicant").show();
		$("#appName").show();
		$("#owner").hide();
		$("#name").hide();
	}
	else{
		$("#owner").show();
		$("#name").show();
	}
	
	inspectionDisableForRTi= $("#inspectionStatus").val();

	prepareDateTag();

});

function submitEntryDetails(element) {
	
	var errorList = [];
	if (inspectionDisableForRTi == 'Y') {
		errorList.push(getLocalMessage("obj.inspection.done.objection.no"));
		displayErrorMsgs(errorList);
		return false;
	}
	
	errorList = validateHearingForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, "shedule save successfully!",
				'AdminHome.html', 'saveInspection');
	} else {
		displayErrorMsgs(errorList);
	}
}

function validateHearingForm(errorList) {
	
	var empId = $("#empId").val();
	var hearingRemark = $("#remark").val();
	var availPerson = $("#availPerson").val();
	var name = $("#name").val();
	var mobno = $("#mobno").val();
	var applicantName = $("#applicantName").val();
	var radioCheck = $('input[type=radio]:checked').val();
	var deptCode = $("#deptCode").val();

	if (empId == '' || empId == undefined || empId == '0') {
		errorList
				.push(getLocalMessage("obj.select.name.inspection.authority"));
	}
	if (hearingRemark == '' || hearingRemark == undefined) {
		errorList.push(getLocalMessage("obj.enter.inspection.remark"));
	}
	if (availPerson == '' || availPerson == undefined) {
		errorList.push(getLocalMessage("obj.select.avail.person"));
	}
	if (!deptCode == "RTI") {
		if (name == '' || name == undefined) {
			errorList.push(getLocalMessage("obj.enter.person.name"));
		}
	}
	if (mobno == '' || mobno == undefined) {
		errorList.push(getLocalMessage("obj.enter.mobNo"));
	}else {
		// D#80201 validate mobile no
		let error= validateMobNo(mobno);
		error.length>0?errorList.push(error):"";
	}

	if (radioCheck == 'A') {
		if (applicantName == '' || applicantName == undefined) {
			errorList.push(getLocalMessage("obj.enter.applicant.name"));
		}
	}
	if (radioCheck == 'E') {
		if (applicantRepName == '' || applicantRepName == undefined) {
			errorList
					.push(getLocalMessage("obj.enter.applicant.representative.name"));
		}
	}
	//D#80201 email validation
	let emailId = $("#emailid").val();
	if(emailId != '' && emailId != undefined){
		var filter = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(emailId)) {
	        errorList.push(getLocalMessage("collection.validation.invalidemail"));
	    }
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

function showErrorOnPage(errorList) {
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

/*Defect #34050*/
function displayApplicantName() {

	var radioCheck = $("input[name='hearingInspectionDto.availPerson']:checked")
			.val();
	if (radioCheck == 'A') {
		$("#appName").show();
		$("#appRepName").hide();
	} else if (radioCheck == 'E') {
		$("#appRepName").show();
		$("#appName").hide();
	}
}

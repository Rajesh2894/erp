function populateRecrds(element) {
	var URL = 'WaterReconnectionForm.html?getSelectedData';
	var data = {
		"id" : element,
	};

	var returnData = __doAjaxRequest(URL, 'post', data, false);

	$("#connectionNo").val(returnData.connectionNo);
	$("#consumerName").val(returnData.consumerName);
	$("#conCategory").val(returnData.tarrifCategory);
	$("#conPremiseType").val(returnData.premiseType);
	$("#conSize").val(returnData.connectionSize);
	$("#discMethod").val(returnData.discMethod);
	$("#discDate").val(returnData.discDate);
	$("#discType").val(returnData.disconnectionType);
	$("#remarks").val(returnData.discRemarks);

	$('.child-popup-dialog').hide();

	disposeModalBox();

	$.fancybox.close();

}
function checkRegisteredPluber(objParent) {

	var URL = 'WaterReconnectionForm.html?checkPlumberLicNo';

	theForm = '#' + findClosestElementId(objParent, 'form');

	var requestData = {
		'plumberLicNo' : $('#plumberLicNo').val(),
	};

	var result = __doAjaxRequest(URL, 'POST', requestData, false, 'json');

	if (result == "N") {

		$('#plumberLicNo').val("");
		$(errMsgDiv)
				.html(getLocalMessage("water.plumber.licence.not.register"));
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);

	}
}

function isEditableBPLNo(obj) {

	if (obj.value == "Y") {
		$("#BPLNo").attr("readonly", false);
	} else {
		$("#BPLNo").val("");
		$("#BPLNo").attr("readonly", true);
	}
}

function saveWaterReconnectionForm(element) {

	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N') {
		return saveOrUpdateForm(element,
				"Your application for Water Reconnection saved successfully!",
				'WaterReconnectionForm.html?PrintReport', 'save');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		return saveOrUpdateForm(element,
				"Your application for Water Reconnection saved successfully!",
				'WaterReconnectionForm.html?redirectToPay', 'save');
	} else {
		return saveOrUpdateForm(element,
				"Your application for Water Reconnection saved successfully!",
				'CitizenHome.html', 'save');
	}
}

/**
 * used to find applicable checklist and charges for change of ownership
 * 
 * @param obj
 */
function getChecklistAndChargesWaterReconnection(obj) {
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = validateReconnectionInfo(errorList);
	var isBPL = $('#povertyLineId').val();
	if (errorList.length == 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = {
			"isBPL" : isBPL
		};

		requestData = __serializeForm(theForm);
		var url = 'WaterReconnectionForm.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '',
				obj);
		$(formDivName).html(returnData);
		$('#povertyLineId').val(isBPL);
		// $('#searchConnection').attr('disabled',true);
		// $('#confirmToProceedId').attr('disabled',true);

	} else {
		displayErrorsOnPage(errorList);
	}
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	return false;
}

/**
 * validate applicant info
 * 
 * @param errorList
 * @returns
 */
function validateApplicantInfo(errorList) {

	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	// var gender = $.trim($('#gender').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());
	var blockName = $.trim($('#blockName').val());
	// var villTownCity = $.trim($('#villTownCity').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var applicantAdharNo = $.trim($('#adharNo').val());
	var povertyLineId = $.trim($('#povertyLineId').val());
	var dwzid1 = $.trim($('#dwzid1').val());
	var dwzid2 = $.trim($('#dwzid2').val());

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	}
	/*
	 * if (gender == "" || gender == '0' || gender == undefined) {
	 * errorList.push(getLocalMessage('water.validation.ApplicantGender')); }
	 */
	if (applicantMobileNo == "" || applicantMobileNo == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	}
	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantarea'));
	}
	/*
	 * if (villTownCity == "" || villTownCity == undefined) {
	 * errorList.push(getLocalMessage('water.validation.ApplicantcityVill')); }
	 */
	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	}
	/*if (povertyLineId == "" || povertyLineId == '0'
			|| povertyLineId == undefined) {
		errorList.push(getLocalMessage('water.validation.Applicantpovertyln'));
	} else {
		if (povertyLineId == 'Y') {
			var bplNo = $.trim($('#bplNo').val());
			if (bplNo == '' || bplNo == undefined) {
				errorList
						.push(getLocalMessage('water.validation.bplnocantempty'));
			}
		}
	}*/

	return errorList;
}

function validateReconnectionInfo(errorList) {
	
	var connectionNumber = $.trim($('#connectionNo').val());

	var uLBPlumber = $.trim($('#ULBRegister').val());

	var notULBPlumber = $.trim($('#NotRegister').val());

	if (connectionNumber == "" || connectionNumber == undefined) {
		errorList.push(getLocalMessage('water.validation.connectionno'));
	}

	var ulb = $("input:radio[name='reconnRequestDTO.plumber']").filter(
			":checked").val();
	if (ulb == 'N') {
		var plumberId = $("#licPlumber").val();
		if (plumberId == "" || plumberId == undefined) {
			errorList.push(getLocalMessage('Please Select Plumber Name'));
		}
	} else {
		var plumberId = $("#plumber").val();
		if (plumberId == "" || plumberId == undefined) {
			errorList.push(getLocalMessage('Please Select Plumber Name'));
		}
	}

	return errorList;
}

function closeOutErrBox() {
	$('.error-div').hide();
}
function resetReconnection() {
	$("#connectionNo").val("").attr("readonly", false);
	$("#consumerName").val("");
	$("#conCategory").val("");
	$("#conPremiseType").val("");
	$("#conSize").val("");
	$("#discMethod").val("");
	$("#discDate").val("");
	$("#discType").val("");
	$("#remarks").val("");
}
$(document).ready(
		function() {
			$("input:radio[name='reconnRequestDTO.plumber']").click(function() {
				
				if ($(this).attr('value') == 'Y') {
					$("#ulbPlumber").show();
					$("#licensePlumber").hide();
					$("#licPlumber").attr("disabled", true);
					$("#plumber").attr("disabled", false);

				} else {
					$("#ulbPlumber").hide();
					$("#licensePlumber").show();
					$("#plumber").attr("disabled", true);
					$("#licPlumber").attr("disabled", false);
				}
			});
			
			$("#resetform").click(function() {
						this.form.reset();
						resetWaterForm();
						resetOtherFields();
					});

			if ($("input:radio[name='reconnRequestDTO.plumber']").filter(
					":checked").val() == 'Y') {
				
				$("#ulbPlumber").show();
				$("#licensePlumber").hide();
				$("#licPlumber").attr("disabled", true);
			} else {
				$("#ulbPlumber").hide();
				$("#licensePlumber").show();
				$("#plumber").attr("disabled", true);
			}

		});

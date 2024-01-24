function getAssessmentDetail() {
	var errorList = [];
	var propNo = $("#assNo").val();
	var oldPropNo = $("#assOldpropno").val();
	var requestData = {
		"propNo" : propNo,
		"oldPropNo" : oldPropNo
	};
	if (propNo == '' && oldPropNo == '') {
		errorList.push(getLocalMessage("property.changeInAss"));
		displayErrorsOnPage(errorList);
		return false;
	} else {

		var URL = 'UpdateDataEntrySuite.html?getAssessmentDetail';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		$(formDivName).html(returnData);

		// var divName = '.content-page';
		// var URL = 'UpdateDataEntrySuite.html?getAssessmentDetail';
		// var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
		// 'html');
		// $(divName).removeClass('ajaxloader');
		// $(divName).html(returnData);
		// prepareTags();
	}
}

function updateDataEntry(element) {
	var singleOwnerTable = $("#singleOwnerTable").closest('table').attr('id');
	var ownerTable = $("#ownerTable").closest('table').attr('id');
	var jointOwnerTable = $("#jointOwnerTable").val();
	var errorList = [];
	if (singleOwnerTable || ownerTable) {
		errorList = validateForm(errorList);
	} else {
		errorList = validateForm1(errorList);
	}

	if (errorList == 0) {
		var theForm = '#UpdateDataEntrySuite';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData = __doAjaxRequestForSave(
				'UpdateDataEntrySuite.html?updateDataEntry', 'post',
				requestData, false);
		popUpMsg(returnData);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateMobileNumber() {
	var mobileNumber = $("#mobileNo").val();
	var filter = /[1-9]{1}[0-9]{9}/;
	if (mobileNumber != '') {
		if (filter.test(mobileNumber)) {
			$('#mobNumber').hide();
		} else {
			$('#mobNumber').show();
			$('#mobNumber').html('Invalid Mobile Number');
			$('#mobNumber').css('color', 'red');
		}
	} else {
		$('#mobNumber').hide();
	}
}

function popUpMsg(returnData) {
	successUrl = 'UpdateDataEntrySuite.html';
	if ($.isPlainObject(returnData)) {
		var message = returnData.command.message;
		var hasError = returnData.command.hasValidationError;
		if (!message) {
			message = successMessage;
		}
		if (message && !hasError) {
			if (returnData.command.hiddenOtherVal == 'SERVERERROR')
				showSaveResultBox(returnData, message, 'AdminHome.html');
			else
				showSaveResultBox(returnData, message, successUrl);
		} else if (hasError) {
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');
		} else
			return returnData;
	} else if (typeof (returnData) === "string") {
		$('.content-page').html(returnData);
		prepareTags();
	} else {
		alert("Invalid datatype received : " + returnData);
	}
	return false;
}

function validateForm1(errorList) {
	{
		$("#jointOwnerTable tbody tr")
				.each(
						function(i) {
							var assoOwnerName = $("#assoOwnerName" + i).val();
							var assoGuardianName = $("#assoGuardianName" + i)
									.val();
							var assoMobileno = $("#assoMobileno" + i).val();
							var assoAddharno = $("#assoAddharno" + i).val();
							var rowCount = i + 1;
							var pattern = /^[a-zA-Z\s-, ]+$/;
							if (assoOwnerName == "" || assoOwnerName == null
									|| assoOwnerName == undefined) {
								if (!pattern.test(assoOwnerName)) {
									errorList
											.push(getLocalMessage("update.ownerName ")
													+ rowCount);
								}
							}
							var pattern = /^[a-zA-Z\s-, ]+$/;
							if (assoGuardianName == ""
									|| assoGuardianName == null) {
								if (!pattern.test(assoGuardianName)) {
									errorList
											.push(getLocalMessage("update.guardianName ")
													+ rowCount);
								}
							}
							var filter = /[1-9]{1}[0-9]{9}/;
							if (assoMobileno == undefined) {

							} else if (assoMobileno != '') {
								if (!filter.test(assoMobileno)) {
									errorList
											.push(getLocalMessage("update.invalid.mobileNo ")
													+ rowCount);
								}
							} else {
								errorList
										.push(getLocalMessage("update.mobileNo  ")
												+ rowCount);
							}
							var pattern = /^\d{12}$/;
							if (assoAddharno == undefined) {

							} else if (assoAddharno != "") {
								if (!pattern.test(assoAddharno)) {
									errorList
											.push(getLocalMessage("update.adhaarNo ")
													+ rowCount);
								}
							}
						});
		var pincode = $("#assPincode").val();
		var filter = /[0-9]{6}/;
		if (pincode != '') {
			if (!filter.test(pincode)) {
				errorList.push(getLocalMessage("update.invalid.pincode"));
			}
		} else {
			errorList.push(getLocalMessage("update.pincode"));
		}
		var locId = $("#locId").val();
		if (locId == '' || locId == null || locId == undefined || locId == '0') {
			errorList.push(getLocalMessage("update.location"));
		}
		var assAddress = $("#assAddress").val();
		if (assAddress == '' || assAddress == null || assAddress == undefined) {
			errorList.push(getLocalMessage("update.location.address"));
		}
		return errorList;

	}
}
function validateForm(errorList) {
	var adhaarNo = $("#assoAddharno").val();
	var pattern = /^\d{12}$/;
	if (adhaarNo == undefined) {

	} else if (adhaarNo != "") {
		if (!pattern.test(adhaarNo)) {
			errorList.push(getLocalMessage("update.adhaarNumber"));
		}
	}
	var mobileNumber = $("#assoMobileno").val();
	var filter = /[1-9]{1}[0-9]{9}/;
	if (mobileNumber != '') {
		if (!filter.test(mobileNumber)) {
			errorList.push(getLocalMessage("update.invalidNumber"));
		}
	}

	var singleOwnerTable = $("#singleOwnerTable").closest('table').attr('id');
	var ownerName = $("#assoOwnerName").val();
	var ownershiptype = $("#ownershiptype").val();

	// var pattern = /^[a-zA-Z]/;
	var pattern = /^[a-zA-Z\s-, ]+$/;
	if (singleOwnerTable) {
		if (ownerName == "" || ownerName == null || ownerName == undefined) {
			if (!pattern.test(ownerName)) {
				errorList.push(getLocalMessage("update.ownerName.empty"));
			}
		}
	} else {
		if (ownerName == "" || ownerName == null || ownerName == undefined) {
			if (!pattern.test(ownerName)) {
				errorList.push(getLocalMessage("update.nameOf " + ownershiptype
						+ " update.empty"));
			}
		}
	}
	var assoGuardianName = $("#assoGuardianName").val();
	var pattern = /^[a-zA-Z\s-, ]+$/;
	if (singleOwnerTable) {
		if (assoGuardianName == "" || assoGuardianName == null) {
			if (!pattern.test(assoGuardianName)) {
				errorList.push(getLocalMessage("update.guardianName.empty"));
			}
		}
	} else {
		if (assoGuardianName == "" || assoGuardianName == null) {
			if (!pattern.test(assoGuardianName)) {
				errorList.push(getLocalMessage("update.contact.person"));
			}
		}
	}

	var pincode = $("#assPincode").val();
	var filter = /[0-9]{6}/;
	if (pincode != '') {
		if (!filter.test(pincode)) {
			errorList.push(getLocalMessage("update.invalid.pincode"));
		}
	} else {
		errorList.push(getLocalMessage("update.pincode"));
	}
	var locId = $("#locId").val();
	if (locId == '' || locId == null || locId == undefined || locId == '0') {
		errorList.push(getLocalMessage("update.location"));
	}
	var assAddress = $("#assAddress").val();
	if (assAddress == '' || assAddress == null || assAddress == undefined) {
		errorList.push(getLocalMessage("update.location.address"));
	}
	var applicantEmailId = $.trim($('#emailId').val());
	if (applicantEmailId != '' && applicantEmailId != null
			&& applicantEmailId != undefined) {
		if (!validateEmail(applicantEmailId)) {
			errorList.push(getLocalMessage('update.invalid.email'));
		}
	}

	return errorList;
}

function validateEmail(email) {
	var regexPattern = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regexPattern.test(email);
}

function validateAdharNumber() {
	var adhaarNumber = $("#adharNumber").val();
	var pattern = /^\d{12}$/;
	if (adhaarNumber != '') {
		if (pattern.test(adhaarNumber)) {
			$('#"addharno"').hide();
		} else {
			$('#"addharno"').show();
			$('#"addharno"').html('Invalid Adhaar Number');
			$('#"addharno"').css('color', 'red');
		}
	} else {
		$('#"addharno"').hide();
	}
}

function validatePAN(Obj) {

	$('.error-div').hide();
	var errorList = [];
	if (Obj == null)
		Obj = $('#pannumber').val();
	if (Obj.value != "") {
		ObjVal = Obj.value;
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = ObjVal.substring(3, 4);
		if (ObjVal.search(panPat) == -1) {
			errorList.push('Invaild PAN Number');
			$('#pannumber').val("");
		} else if (code.test(code_chk) == false) {
			errorList.push('Invaild PAN Number');
			$('#pannumber').val("");
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}
/*------------- Show Error Page Display ---------------*/

function showWaterError(errorList) {
	$('.error-div').hide();
	var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right"  onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;<i class="fa fa-exclamation-circle"></i> &nbsp;'
			+ errorList + '</li></ul>';
	$(".error-div").html(errMsg);
	$('.error-div').show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

/* --------------- Search Old Owner Connection ------------- */
$(function() {
	if ($("#enableSubmit").val() == 'false') {
		$("#closeDiv").hide();
	} else {
		// code update for D#139190 the time of application charges with cheque/DD/Bank payment mode the drawn on field should be enabled.
		//$('input[type="text"],textarea,select').attr('disabled', true);
		$("#payModeIn").attr('disabled', false);
	}
	$("#searchConnection")
			.click(
					function() {
						/* _openPopUpForm('ChangeOfOwnership.html','getConnectionRecords'); */
						var errorList = [];
						errorList = validateApplicantInfo(errorList);
						var conNum = $.trim($("#conNum").val());
						if (conNum == "" || conNum == undefined) {
							errorList
									.push(getLocalMessage('water.serchconnectn.entrconnctnno'));

						}
						if (errorList.length == 0) {

							var theForm = '#ChangeOfOwnershipId';
							var data = {
								"conNum" : conNum
							};
							data = __serializeForm(theForm);
							var URL = 'ChangeOfOwnership.html?getConnectionRecords';
							var a_middleName = $('#middleName').val();
							var a_emailId = $('#emailId').val();
							var a_flatNo = $('#flatNo').val();
							var a_buildingName = $('#buildingName').val();
							var a_roadName = $('#roadName').val();
							var a_areaName = $('#areaName').val();
							var a_blockName = $('#blockName').val();
							var a_villTownCity = $('#villTownCity').val();
							var a_aadharNo = $('#aadharNo').val();
							var a_povertyLineId = $('#povertyLineId').val();
							var a_bplNo = $('#bplNo').val();
							var a_dwzid1 = $('#dwzid1').val();
							var a_dwzid2 = $('#dwzid2').val();

							var responseObj = __doAjaxRequest(URL, 'POST',
									data, false);
							var errMsg = responseObj["errMsg"];
							if (responseObj.status == 'N') {
								errorList
										.push(getLocalMessage('water.serchconnectn.norecrdconcntno'
												+ conNum));
								$("#conName").val('');
								$("#oldAdharNo").val('');
								$("#conCat").val('');
								$("#conType").val('');
								$("#conSize").val('');

								displayErrorsOnPageView(errorList);
							} else if (errMsg != '' && errMsg != undefined) {
								var errorList = [];
								errorList.push(errMsg);
								// $("#conNum").val('');
								$("#conName").val('');
								$("#connectionSize").val(0);
								displayErrorsOnPage(errorList);
							}else {

								$('#fomDivId').html(responseObj);
								$('#povertyLineId').val(a_povertyLineId);
								$('#searchConnection').attr('disabled', true);
								$('#conNum').attr('disabled', true);
								$('#conSize').attr('disabled', true);
								$(".error-div").hide();
							}

						} else {
							displayErrorsOnPageView(errorList);
						}

					});

	$("#resetConnection").click(function() {

		var URL = 'ChangeOfOwnership.html?resetConnectionRecord';
		var responseObj = __doAjaxRequest(URL, 'POST', {}, false);
		$('#fomDivId').html(responseObj);
		$('#povertyLineId').val(a_povertyLineId);
		$('#searchConnection').attr('disabled', false);
		$('#conSize').attr('disabled', true);
		$(".error-div").hide();
	});
});

/* ----------------- Error page Display -------------- */

function displayErrorsOnPageView(errorList) {
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

/*----------  Poverty Line Box Display  ----------*/
$(function() {
	$("#povertyLineId").change(function() {
		var isBelowPoverty = $('#povertyLineId').val();
		if (isBelowPoverty == 'Y') {
			$('#bpldiv').show();
		} else {
			$('#bpldiv').hide();
		}
	});
});

/*----------- Applicant Info Validation --------------------*/

/**
 * used to validate a valid mobile no
 * 
 * @param mobile
 */
function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function validateEmail(email) {
	var regexPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return regexPattern.test(email);
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

	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());

	var applicantPinCode = $.trim($('#pinCode').val());
	var applicantAdharNo = $.trim($('#adharNo').val());
	var povertyLineId = $.trim($('#povertyLineId').val());

	var applicantEmailId = $.trim($('#emailId').val());

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
	 * if (applicantMobileNo == "" || applicantMobileNo == undefined){
	 * errorList.push(getLocalMessage('water.validation.applicantMobileNo')); }
	 */

	if (applicantMobileNo == '' || applicantMobileNo == null
			|| applicantMobileNo == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	} else {
		if (!validateMobile(applicantMobileNo)) {
			errorList
					.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
		}
	}
	if (applicantEmailId != '' && applicantEmailId != null
			&& applicantEmailId != undefined) {
		if (!validateEmail(applicantEmailId)) {
			errorList.push(getLocalMessage('emailId.invalid'));
		}
	}

	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantarea'));
	}

	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	}

	if (povertyLineId == "" || povertyLineId == '0'
			|| povertyLineId == undefined) {
		errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
	} else {
		if (povertyLineId == 'Y') {
			var bplNo = $.trim($('#bplNo').val());
			if (bplNo == '' || bplNo == undefined) {

				errorList
						.push(getLocalMessage('water.validation.bplnocantempty'));
			}
		}
	}

	return errorList;
}

/*-------------  Validate Additional Owner Details  --------------------*/

/**
 * validate additional owners details
 * 
 * @param errorList
 */
function validateAdditionalOwners(errorList) {

	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);

	if (errorList.length == 0) {
		$('.appendableClass').each(function(i) {
			row = i + 1;
			if (isAdditionalOwnerDetailRequired(i)) {
				errorList = validateAdditionalOwnerTableData(errorList, i);
			}

		});
	}

	return errorList;
}

/*-------------  Validate old Owner Details  --------------------*/
/**
 * to validate old owner details
 */
function validateOldOwnerInfo(errorList) {

	var conNum = $.trim($('#conNum').val());
	if (conNum == "" || conNum == undefined) {
		errorList.push(getLocalMessage('water.validation.connectionno'));
	}
	return errorList;
}

/*-------------  Validate New Owner Details  --------------------*/
/**
 * to validate New Owner Information
 * 
 * @param errorList
 * @returns
 */
function validateNewOwnerInfo(errorList) {

	var tMode = $.trim($('#ownerTransferMode').val())
	var cooNotitle = $.trim($('#cooNotitle').val());
	var cooNoname = $.trim($('#changeOwnerMaster\\.cooNoname').val());
	var cooNolname = $.trim($('#changeOwnerMaster\\.cooNolname').val());
	var newGender = $.trim($('#newGender').val());

	if (tMode == "" || tMode == '0' || tMode == undefined) {
		errorList.push(getLocalMessage('water.validation.transferMode'));
	}

	if (cooNotitle == "" || cooNotitle == '0' || cooNotitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ctitle'));
	}
	if (cooNoname == "" || cooNoname == undefined) {
		errorList.push(getLocalMessage('water.newowner.fname.validtn'));
	}
	if (cooNolname == "" || cooNolname == undefined) {
		errorList.push(getLocalMessage('water.newowner.lname.validtn'));
	}
	if (newGender == "" || newGender == '0' || newGender == undefined) {
		errorList.push(getLocalMessage('water.newowner.gender.validtn'));
	}

	return errorList;
}

/**
 * validate each mandatory column of additional owner details
 * 
 * @param errorList
 * @param i
 * @returns
 */
function validateAdditionalOwnerTableData(errorList, i) {

	var applicantTitle = $.trim($("#caoNewTitle_" + i).val());
	var applicantFirstName = $.trim($("#caoNewFName_" + i).val());
	var applicantLastName = $.trim($("#caoNewLName_" + i).val());
	var gender = $.trim($("#caoNewGender_" + i).val());

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('water.additnlowner.titel.validtn'));
	}
	if (applicantFirstName == "" || applicantFirstName == undefined) {
		errorList.push(getLocalMessage('water.additnlowner.fname.validtn'));
	}
	if (applicantLastName == "" || applicantLastName == undefined) {
		errorList.push(getLocalMessage('water.additnlowner.lname.validtn'));
	}
	if (gender == "" || gender == '0' || gender == undefined) {
		errorList.push(getLocalMessage('water.additnlowner.gender.validtn'));
	}

	return errorList;
}

/**
 * function to validate additional owner info whether additional owner info is
 * required or not,if any mandatory field of additioal owner is entered, rest of
 * mandatory fields need to be validated.
 */

function isAdditionalOwnerDetailRequired(index) {

	var isAdditionalOwnerDetailRequired = "N";
	var applicantTitle = $.trim($("#caoNewTitle_" + index).val());
	var applicantFirstName = $.trim($("#caoNewFName_" + index).val());
	var applicantLastName = $.trim($("#caoNewLName_" + index).val());
	var gender = $.trim($("#caoNewGender_" + index).val());

	if (applicantTitle != "" && applicantTitle != '0' && applicantTitle != 0
			&& applicantTitle != undefined) {
		isAdditionalOwnerDetailRequired = "Y";
	}
	if (applicantFirstName != "" && applicantFirstName != undefined) {
		isAdditionalOwnerDetailRequired = "Y";
	}
	if (applicantLastName != "" && applicantLastName != undefined) {
		isAdditionalOwnerDetailRequired = "Y";
	}
	if (gender != "" && gender != '0' && gender != 0 && gender != undefined) {
		isAdditionalOwnerDetailRequired = "Y";
	}
	console.log('applicantTitle=' + applicantTitle + ',applicantFirstName='
			+ applicantFirstName + ',applicantLastName=' + applicantLastName
			+ ',gender=' + gender);
	console.log('inside=' + isAdditionalOwnerDetailRequired);
	return isAdditionalOwnerDetailRequired;
}

/**
 * 
 */
function reOrderTableIdSequence() {

	$('.appendableClass')
			.each(
					function(i) {

						// $(this).find("td:eq(0)").attr("id", "srNoId_"+i);
						$(this).find("select:eq(0)").attr("id",
								"caoNewTitle_" + i);
						$(this).find("input:text:eq(0)").attr("id",
								"caoNewFName_" + i);
						$(this).find("input:text:eq(1)").attr("id",
								"caoNewMName_" + i);
						$(this).find("input:text:eq(2)").attr("id",
								"caoNewLName_" + i);
						$(this).find("select:eq(1)").attr("id",
								"caoNewGender_" + i);
						$(this).find("input:text:eq(3)").attr("id",
								"caoNewUID_" + i);
						$("#srNoId_" + i).text(i + 1);

						$(this).find("select:eq(0)").attr("name",
								"additionalOwners[" + i + "].caoNewTitle");
						$(this).find("input:text:eq(0)").attr("name",
								"additionalOwners[" + i + "].caoNewFName");
						$(this).find("input:text:eq(1)").attr("name",
								"additionalOwners[" + i + "].caoNewMName");
						$(this).find("input:text:eq(2)").attr("name",
								"additionalOwners[" + i + "].caoNewLName");
						$(this).find("select:eq(1)").attr("name",
								"additionalOwners[" + i + "].caoNewGender");
						$(this).find("input:text:eq(3)").attr("name",
								"additionalOwners[" + i + "].caoNewUID");

					});

}

/*
 *//**
	 * additional owner event
	 */
$(function() {

	$("#customFields").on('click', '.addCF', function(i) {

		var row = 0;
		var errorList = [];
		errorList = validateAdditionalOwners(errorList);

		if (errorList.length == 0) {
			if (errorList.length == 0) {
				var romm = 0;
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;
				content.find("input:text").val('');
				content.find("select").val('0');
				$('.error-div').hide();

				reOrderTableIdSequence();
			} else {
				displayErrorsOnPageView(errorList);
			}

		} else {
			displayErrorsOnPageView(errorList);
		}

	});

	$("#customFields")
			.on(
					'click',
					'.remCF',
					function() {

						if ($("#customFields tr").length != 2) {
							$(this).parent().parent().remove();
							reOrderTableIdSequence();
						} else {
							var errorList = [];
							errorList
									.push(getLocalMessage("water.additnlowner.deletrw.validtn"));
							displayErrorsOnPageView(errorList);
							// alert("You cannot delete first row");
						}

					});

});

function closeOutErrBox() {
	$('.error-div').hide();
}

var count;

/*--------------  Save Form  -----------*/

/**
 * 
 */
function saveChangeOfOwnerShip(element) {

	var errorList = [];
	errorList = validateChangeOfOwnershipFormData(errorList);
	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'Y') {

			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'ChangeOfOwnership.html?redirectToPay', 'saveform');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'ChangeOfOwnership.html?PrintReport', 'saveform');
		} else if ($("#FreeService").val() == 'F') {
			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'AdminHome.html', 'saveform');
		}
	} else {
		displayErrorsOnPageView(errorList);
	}

}

/*--------------   Get Checklist and Charges   -------------------*/

/**
 * used to find applicable checklist and charges for change of ownership
 * 
 * @param obj
 */
function getChecklistAndChargesForChangeOfOwner(obj) {

	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);
	var isBPL = $('#povertyLineId').val();

	$('.appendableClass').each(function(i) {
		row = i + 1;
		if (isAdditionalOwnerDetailRequired(i) == 'Y') {
			errorList = validateAdditionalOwnerTableData(errorList, i);
		}
	});

	if (errorList.length == 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = {
			"isBPL" : isBPL
		};

		requestData = __serializeForm(theForm);

		var url = 'ChangeOfOwnership.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '',
				obj);

		$('#fomDivId').html(returnData);
		$('#povertyLineId').val(isBPL);
		$('#searchConnection').attr('disabled', true);
		$('#resetConnection').attr('disabled', true);
		$('#confirmToProceedId').attr('disabled', true);
		$('.addCF').attr('disabled', true);
		$('.remCF').attr('disabled', true);
		// $("#confirm").hide();

	} else {
		displayErrorsOnPageView(errorList);
	}
}

/**
 * to validate Change Of Ownership Form data on submit
 * 
 * @param errorList
 * @returns
 */
function validateChangeOfOwnershipFormData(errorList) {

	var payMode = $("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val();

	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);
	// validating additional owners
	if (errorList.length == 0) {
		$('.appendableClass').each(function(i) {
			row = i + 1;
			if (isAdditionalOwnerDetailRequired(i) == 'Y') {
				errorList = validateAdditionalOwnerTableData(errorList, i);
			}
		});
	}

	if ($("#FreeService").val() == 'N') {
		if (payMode == "" || payMode == undefined) {
			errorList.push(getLocalMessage('water.validation.paymode'));
		}
	}
	return errorList;

}
jQuery('.hasSpecialChara').keyup(function () { 
	
	if (this.value.match(/[^a-zA-Z ]/g )|| this.value.match(/[^\u0900-\u0954 ]/g) ){
		this.value = this.value.replace(/[^a-zA-Z\u0900-\u0954 ]/g, '');
	}   
});

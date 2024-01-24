$(document).ready(function() {
	
	if ($("#enableSubmit").val() == 'false') {
		$("#closeDiv").hide();
	} else {
		$('input[type="text"],textarea,select').attr('disabled', true);
		$("#payModeIn").attr('disabled', false);
	}
   var conName=$("#conName").val();
   if(conName!=undefined && conName!=''&&conName!=null){
	   $("#closeDiv").show();
   }
	$("#searchConnection").click(function() {

		var errorList = [];
		var conNo = $("#conNum").val();
		var errorList = validateApplicantInfo(errorList);
		if (errorList.length == 0)
			if (conNo == '') {
				errorList.push(getLocalMessage('water.billGen.ccnNumber'));
			}

		if (errorList.length == 0) {
			var theForm = '#changeOfUsageId';
			var connectionNumber = $.trim($("#conNum").val());
			var data = __serializeForm(theForm);
			var URL = 'ChangeOfUsage.html?getConnectionInfo';

			var returnData = __doAjaxRequest(URL, 'POST', data, false);

			var errMsg = returnData["errMsg"];
			if (errMsg != '' && errMsg != undefined) {
				var errorList = [];
				errorList.push(errMsg);
				// $("#conNum").val('');
				$("#conName").val('');
				$("#connectionSize").val(0);
				displayErrorsOnPage(errorList);
			} else {
				$(formDivName).html(returnData);
				$("#conNum").prop("readonly", true);
				$('#searchConnection').attr('disabled', true);
				$("#back").hide();
				$("#closeDiv").show();
				// prepareDateTag();
			}
		} else {
			displayErrorsOnPage(errorList);
		}

		// $(formDivName).html(returnData);

	});

	$("#resetConnection").click(function() {
		var URL = 'ChangeOfUsage.html?resetConnectionRecord';
		var responseObj = __doAjaxRequest(URL, 'POST', {}, false);
		$('#fomDivId').html(responseObj);
		$(".error-div").hide();
		$("#closeDiv").hide();
		$("#conNum").prop("readonly", false);
		$("#conNum").val('');
	});
});

$(function() {
	showHideBPL($('#povertyLineId').val());
	$("#povertyLineId").change(function() {
		var isBelowPoverty = $('#povertyLineId').val();
		showHideBPL(isBelowPoverty);
	});
});
function showHideBPL(value) {
	if (value == 'Y') {
		$('#bpldiv').show();
	} else {
		$('#bpldiv').hide();
	}
}

function getChecklistAndCharges(obj) {

	var errorList = [];
	/*
	 * errorList = validateApplicantInfo(errorList); errorList =
	 * validateOldOwnerInfo(errorList); errorList =
	 * validateNewOwnerInfo(errorList);
	 */
	var isBPL = $('#povertyLineId').val();
	var newTrmGroup1 = $("#newTrmGroup1").val();
	var remark = $("#remark").val();
	if (newTrmGroup1 == 0) {
		errorList.push(getLocalMessage('water.selectTarrifCatg.validn'));
	}
	if (remark == '') {
		errorList.push(getLocalMessage('water.select.remarks'));
	}
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

		var url = 'ChangeOfUsage.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '',
				obj);

		$('#fomDivId').html(returnData);
		$('#povertyLineId').val(isBPL);
		$('#searchConnection').attr('disabled', true);
		$('#resetConnection').attr('disabled', true);
		$("#back").hide();
		/* $('#confirmToProceedId').attr('disabled',true); */

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

function saveChangeOfUsage(element) {
	var url = "ChangeOfUsage.html?noAmount";
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		url = 'ChangeOfUsage.html?redirectToPay';
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		url = 'ChangeOfUsage.html?PrintReport';
	}
	return saveOrUpdateForm(element,
			"Your application for change Of Uses saved successfully!", url,
			'saveform');
}

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
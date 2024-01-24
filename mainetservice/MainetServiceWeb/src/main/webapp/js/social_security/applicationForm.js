var url = "SchemeApplicationForm.html";
$(document).ready(function() {

	$("#applicantId").datepicker({

		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-150:-0"
	});

	$("#applicantId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#percenrofDis").prop("disabled", true);
	$("#bplinspectyr").prop("disabled", true);
	$("#bplfamily").prop("disabled", true);

	$("#lastDateLifeCertiId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});

	$("#lastDateLifeCertiId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#resetform").on("click", function() {
		window.location.reload("#schemeApplicationFormId")
	});
	
	let bplvar = $('#applicationformdto\\.bplid option:selected').attr('code');
	if (bplvar == "YES") {
		$("#bplinspectyr").prop("disabled", false);
		$("#bplfamily").prop("disabled", false);
	} else {
		$("#bplinspectyr").prop("disabled", true);
		$("#bplfamily").prop("disabled", true);
	}
	
	let typeofdisvar = $('#applicationformdto\\.typeofDisId option:selected')
	.attr('code');
	if (typeofdisvar == "NA") {
	$("#percenrofDis").prop("disabled", true);
	} else {
	$("#percenrofDis").prop("disabled", false);
	}
});

function saveData(element) {

	var errorList = [];
	errorList = applicationFormValidation(errorList);
	var validate = $("#approvalFlag").val();
	if (validate == "N") {
		errorList.push('Cannot Apply for this Scheme:Date has been expired');
	}
	if (validate == "D") {
		errorList.push('Scheme has been disabled');
	}
	if (errorList.length == 0) {

		var divName = formDivName;
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(url + '?showCheckList', 'POST',
				requestData, false, '', 'html');
		$(divName).html(response);

	} else {
		displayErrorsOnPage(errorList);
	}
}

function applicationFormValidation(errorList) {

	var schemenameId = $("#schemenameId").val();
	var nameofApplicant = $("#nameofApplicant").val();
	var applicationDobId = $("#applicantId").val();
	var ageason = $("#ageason").val();
	var genderId = $("#applicationformdto\\.genderId").val();
	var applicantAdress = $("#applicantAdress").val();
	var aadharCard = $("#aadharCard").val();
	var pinCode = $("#pinCode").val();
	var mobNum = $("#mobNum").val();
	var educationId = $("#applicationformdto\\.educationId").val();
	var maritalStatusId = $("#applicationformdto\\.maritalStatusId").val();
	var categoryId = $("#applicationformdto\\.categoryId").val();
	var annualIncome = $("#annualIncome").val();
	var typeofDisId = $("#applicationformdto\\.typeofDisId").val();
	var percenrofDis = $("#percenrofDis").val();
	var bplid = $("#applicationformdto\\.bplid").val();
	var bplinspectyr = $("#bplinspectyr").val();
	var bplfamily = $("#bplfamily").val();
	var banknameId = $("#banknameId").val();
	var accountNumber = $("#accountNumber").val();
	var nameofFather = $("#nameofFather").val();
	var nameofMother = $("#nameofMother").val();
	var contactNumber = $("#contactNumber").val();
	var detailsoffamIncomeSource = $("#detailsoffamIncomeSource").val();
	var annualIncomeoffam = $("#annualIncomeoffam").val();
	var lastDateLifeCertiId = $("#lastDateLifeCertiId").val();

	if (schemenameId == "0" || schemenameId == undefined || schemenameId == '') {
		errorList.push(getLocalMessage('social.sec.schemename.req'));
	}

	if (nameofApplicant == "0" || nameofApplicant == undefined
			|| nameofApplicant == '') {
		errorList.push(getLocalMessage('social.sec.nameofapp.req'));
	}

	if (applicationDobId == "0" || applicationDobId == undefined
			|| applicationDobId == '') {
		errorList.push(getLocalMessage('social.sec.appdob.req'));
	} else {
		var dateformat = $('#applicantId').val();
		dateformat = dateformat.split(' ')[0];
		/* var formaterror = validateDateFormat($('#applicantId').val()); */
		var formaterror = validateDateFormat(dateformat);
		if (correct == false) {
			errorList.push("Invalid Date Format");
		}
	}

	if (ageason == "0" || ageason == undefined || ageason == '') {
		errorList.push(getLocalMessage('social.sec.ageason.req'));
	}

	if (genderId == "0" || genderId == undefined || genderId == '') {
		errorList.push(getLocalMessage('social.sec.gen.req'));
	}

	if (applicantAdress == "0" || applicantAdress == undefined
			|| applicantAdress == '') {
		errorList.push(getLocalMessage('social.sec.appadd.req'));
	}

	if (pinCode == "0" || pinCode == undefined || pinCode == '') {
		errorList.push(getLocalMessage('social.sec.pincode.req'));
	}

	if (pinCode.length < 6 || pinCode.length > 6) {
		errorList.push(getLocalMessage('social.sec.valid.pincode'));
	}

	if (aadharCard == "0" || aadharCard == undefined || aadharCard == '') {
		errorList.push(getLocalMessage('social.aadharNo.required'));
	}
	
	if (!validateadharNo($("#aadharCard").val())) {
		errorList
				.push(getLocalMessage("social.sec.adharnumber.valid"));
	}

	if (mobNum == "0" || mobNum == undefined || mobNum == '') {
		errorList.push(getLocalMessage('social.sec.mobnum.req'));
	}

	if (maritalStatusId == "0" || maritalStatusId == undefined
			|| maritalStatusId == '') {
		errorList.push(getLocalMessage('social.sec.maritalstat.req'));
	}

	if (categoryId == "0" || categoryId == undefined || categoryId == '') {
		errorList.push(getLocalMessage('social.sec.cate.req'));
	}

	if (typeofDisId == "0" || typeofDisId == undefined || typeofDisId == '') {
		errorList.push(getLocalMessage('social.sec.typeofdis.req'));
	}

	let typeofdisvar = $('#applicationformdto\\.typeofDisId option:selected')
			.attr('code');
	if (typeofdisvar != "NA") {
		if (percenrofDis == "0" || percenrofDis == undefined
				|| percenrofDis == '') {
			errorList.push(getLocalMessage('social.sec.percentofdis.req'));
		}
	}

	if (bplid == "0" || bplid == undefined || bplid == '') {
		errorList.push(getLocalMessage('social.sec.bpl.req'));
	}
	let bplvar = $('#applicationformdto\\.bplid option:selected').attr('code');
	$("#isBplApplicableId").val("N");
	if (bplvar != "NO") {
		$("#isBplApplicableId").val("Y");
		if (bplinspectyr == "0" || bplinspectyr == undefined
				|| bplinspectyr == '') {
			errorList.push(getLocalMessage('social.sec.bplyr.req'));
		}

		if (bplfamily == "0" || bplfamily == undefined || bplfamily == '') {
			errorList.push(getLocalMessage('social.sec.bplid.req'));
		}
	}

	if (banknameId == "0" || banknameId == undefined || banknameId == '') {
		errorList.push(getLocalMessage('social.sec.bankname.req'));
	}

	if (accountNumber == "0" || accountNumber == undefined
			|| accountNumber == '') {
		errorList.push(getLocalMessage('social.sec.accnum.req'));
	}
	
	if (!validateAcNo($("#accountNumber").val())) {
		errorList
				.push(getLocalMessage("social.validation.account.no"));
	}

	if (nameofFather == "0" || nameofFather == undefined || nameofFather == '') {
		errorList.push(getLocalMessage('social.sec.namefather.req'));
	}

	if (nameofMother == "0" || nameofMother == undefined || nameofMother == '') {
		errorList.push(getLocalMessage('social.sec.namemother.req'));
	}

	if (contactNumber == "0" || contactNumber == undefined
			|| contactNumber == '') {
		errorList.push(getLocalMessage('social.sec.contactnum.req'));
	}

	if (detailsoffamIncomeSource == "0"
			|| detailsoffamIncomeSource == undefined
			|| detailsoffamIncomeSource == '') {
		errorList.push(getLocalMessage('social.sec.detailsfamincome.req'));
	}

	if (lastDateLifeCertiId == "0" || lastDateLifeCertiId == ""
			|| lastDateLifeCertiId == undefined) {
		errorList.push(getLocalMessage('social.sec.lastdtlifecerti.req'));
	} else {
		var dateformater = $('#lastDateLifeCertiId').val();
		dateformater = dateformater.split(' ')[0];
		// var formaterror =
		// validateDateFormat($('#lastDateLifeCertiId').val());
		var formaterror = validateDateFormat(dateformater);
		if (correct == false) {
			errorList.push("Invalid Date Format");
		}
	}
	if ((applicationDobId != "0" || applicationDobId != undefined || applicationDobId != '')
			&& (aadharCard != "0" || aadharCard != undefined || aadharCard != '')) {

		var requestData = {
			"dob" : applicationDobId,
			"adhaarNo" : aadharCard
		};
		var object = __doAjaxRequest("SchemeApplicationForm.html?duplicate",
				'POST', requestData, false, 'json');
		if (object.error != null)
			errorList.push(object.error);

	}
	return errorList;
}

// function to validate contact number
function validateContactNum() {
	var errorList = [];
	var contactNumber = $("#contactNumber").val();
	errorList = validateMobNo(contactNumber);

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}

// function to validate mobile number
function validateMobNum() {
	var errorList = [];
	var mobNum = $("#mobNum").val();
	errorList = validateMobNo(mobNum);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}

function geteducationdetails() {

	let educationId = $("#applicationformdto\\.educationId").val();
	if (educationId == undefined || educationId == '' || educationId == '0') {

		$("#classs").prop("disabled", true);

	} else {

		$("#classs").prop("disabled", false);
	}
}

// function to hide and show type of disability textbox
function disabilitydetails() {

	let typeofdisvar = $('#applicationformdto\\.typeofDisId option:selected')
			.attr('code');

	if (typeofdisvar == "NA") {

		$("#percenrofDis").prop("disabled", true);
	} else {

		$("#percenrofDis").prop("disabled", false);

	}

}

// function to hide and show type of disability bpl
function bpldetails() {

	let bplvar = $('#applicationformdto\\.bplid option:selected').attr('code');

	if (bplvar == "YES") {

		$("#bplinspectyr").prop("disabled", false);
		$("#bplfamily").prop("disabled", false);

	} else {

		$("#bplinspectyr").prop("disabled", true);
		$("#bplfamily").prop("disabled", true);

	}
}

function dobdetails() {

	var dateString = $("#applicantId").val();

	/* #29786 by Priti */

	var dateParts = dateString.split("/");

	// month is 0-based, that's why we need dataParts[1] - 1
	var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);

	// document.body.innerHTML = dateObject.toString();
	if (dateString != undefined || dateString != '' || dateString != '0') {

		var dates = new Date(dateObject.toString());
		var age = getAge(dates);
		$("#ageason").val(age);
	}
}
// function to calculate age on entry of dob
function getAge(DOB) {

	var today = new Date();
	var birthDate = new Date(DOB);
	var age = today.getFullYear() - birthDate.getFullYear();
	var m = today.getMonth() - birthDate.getMonth();
	if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
		age = age - 1;
	}

	return age;
}

// function to save the form (checklist)
function saveCheckListAppForm(element) {

	var errorList = [];
	// errorList =applicationFormValidation(errorList);
	if (errorList.length == 0) {

		var divName = formDivName;
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		// var response = __doAjaxRequest(url+'?showCheckList',
		// 'POST',requestData, false, '', 'html');
		// $(divName).html(response);
		// return saveOrUpdateForm(element, '', URL, 'saveform');
	}

	var ajaxResponse = __doAjaxRequest(
			'SchemeApplicationForm.html?saveCheckListAppdetails', 'POST',
			requestData, false, '', element);

	var tempDiv = $('<div id="tempDiv">' + ajaxResponse + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');

	$.fancybox.close();
	if (errorsPresent.length > 0) {
		var divError = $(tempDiv).find('#validationerrordiv');
		$(divError).addClass('show');
		$(divName).html(ajaxResponse);
		prepareDateTag();
	} else {
		if ($.isPlainObject(ajaxResponse)) {
			var message = ajaxResponse.command.message;
			showMessageOnSubmit(ajaxResponse, message, 'AdminHome.html');
		}
	}
}
var correct = true;
// final submit confirm box
function showMessageOnSubmit(successMsg, message, redirectURL) {

	var errMsgDiv = '.msg-dialog-box';
	var cls = getLocalMessage('asset.proceed');

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}
function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

// function for proceed button
function proceed() {

	// backToHomePage();

	window.location.href = url;
	$.fancybox.close();
}

function resetSchemeApplicationForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#schemeApplicationFormId").validate().resetForm();
}

function validateDateFormat(dateElementId) {
	var errorList = [];

	var dateValue = dateElementId;
	if (dateValue != null && dateValue != "") {
		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		if (dateValue.match(dateformat)) {
			var opera1 = dateValue.split('/');
			lopera1 = opera1.length;
			if (lopera1 > 1) {
				var pdate = dateValue.split('/');
			}
			var dd = parseInt(pdate[0]);
			var mm = parseInt(pdate[1]);
			var yy = parseInt(pdate[2]);
			var ListofDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
			if (mm == 1 || mm > 2) {
				if (dd > ListofDays[mm - 1]) {
					errorList.push('Invalid Date Format');
				}
			}
			if (mm == 2) {
				var lyear = false;
				if ((!(yy % 4) && yy % 100) || !(yy % 400)) {
					lyear = true;
				}
				if ((lyear == false) && (dd >= 29)) {
					errorList.push('Invalid Date Format');
				}
				if ((lyear == true) && (dd > 29)) {
					errorList.push('Invalid Date Format');
				}
			}
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var sDate = new Date(dateValue.replace(pattern, '$3-$2-$1'));
			if (sDate > new Date()) {
			}
		} else {
			errorList.push('Invalid Date Format');
		}
	}

	displayErrorsOnPage(errorList);
	if (errorList.length > 0) {
		correct = false;
	} else {
		correct = true;
	}
	return errorList;

}

function checkDate(element) {
	var divName = '.content-page';
	var URL = 'SchemeApplicationForm.html?checkDate';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	prepareTags();
	$("#errorDiv").hide();
	$('#banknameId').chosen().addClass('chosen-select-no-results');
}
//#143153
function validateAcNo(accountNumber) {
	
	var regexPattern = /^[0-9]{8,16}$/;
	return regexPattern.test(accountNumber);
}
function validateadharNo(aadharCard)
{
	var regexPattern = /^[0-9]{12}$/;
	return regexPattern.test(aadharCard);
}

function openBankForm(){
	var bankId = 1;
    var url = "GeneralBankMaster.html?form";
    var requestData = "bankId=" + bankId  + "&MODE_DATA=" + "ADD";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	
	var divName = ".content";
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	return false;
}
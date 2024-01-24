
$(document).ready(function() {
$('.tradeCat').attr('disabled', true);
});

function getLicenseDetails() {

	var errorList = [];
	var userOtpNo = $("#userOtp").val();
	var envFlag = $("#envFlag").val();
	if (envFlag == 'N') {
		if (userOtpNo == '' || userOtpNo == 'undefined') {
			errorList.push(getLocalMessage("duplicate.validation.otpno"));
		}
	}
	if (errorList.length == 0) {
		var theForm = '#duplicateLicenseForm';
		var trdLicno = $("#licenseNo").val();
		requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'DuplicateLicenseForm.html?getLicenseDetails', 'POST',
				requestData, false, 'html');
		if (response != null) {
			$(formDivName).html(response);
			if (envFlag == 'N')
				$('#otpdetails').show();
			else
				$('#otpdetails').hide();	
		} else {
			displayErrorsOnPage(errorList);
		}
	}
}

function getChecklistAndCharges(obj) {

	var errorList = [];
	if (errorList.length == 0) {
		var theForm = '#duplicateLicenseFormDetails';
		requestData = __serializeForm(theForm);
		var URL = 'DuplicateLicenseForm.html?getCheckListAndCharge';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		if (returnData != null) {

			$(formDivName).html(returnData);
			$('#otpdetails').hide();

		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateDuplicateLicenseForm(errorList) {

	var errorList = [];
	var licenseNo = $("#licenseNo").val();

	if (licenseNo == "" || licenseNo == undefined) {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}

	return errorList;
}

function backPage() {

	window.location.href = getLocalMessage("CitizenHome.html");
}

function generateChallanAndPayment(element) {

	// #129512
	var status;
	saveObj = element;
	var yes = getLocalMessage('license.yes');
	var no = getLocalMessage('license.No');
	var warnMsg = getLocalMessage('payment.popup');

	message = '<p class="text-blue-2 text-center padding-15">' + warnMsg
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="onPaymentYes()"/>&nbsp;'
			+ '<input class="btn btn-success " type=\'button\' value=\'' + no
			+ '\'  id=\'no\' ' + ' onclick="closeConfirmBoxForm()"/>'
			+ '</div>';

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;

}

//#129512
function onPaymentYes() {

	$.fancybox.close();
	var errorList = [];
	$('#hideOtpbtn').hide();
	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(
					saveObj,
					"Your application for Duplicate License saved successfully!",
					'DuplicateLicenseForm.html?PrintReport',
					'generateChallanAndPayement');

		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {

			var status = saveOrUpdateForm(
					saveObj,
					"Your application for Duplicate License saved successfully!",
					'DuplicateLicenseForm.html?redirectToPay',
					'generateChallanAndPayement');
			if (status != false) {
				openDuplicateLicenseWindowTab(status);
			}
		} else {
			return saveOrUpdateForm(
					saveObj,
					"Your application for Duplicate License saved successfully!",
					'CitizenHome.html', 'generateChallanAndPayement');
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}
function generateOtpNumber(element) {

	var theForm = '#duplicateLicenseForm';
	var requestData = __serializeForm(theForm);
	var URL = 'DuplicateLicenseForm.html?' + 'generateOtp';

	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
}

function openDuplicateLicenseWindowTab(status) {

	if (!status) {
		var URL = 'DuplicateLicenseForm.html?getDuplicateLicensePrint';
		var returnData = __doAjaxRequest(URL, 'POST', {}, false);

		var title = 'Duplicate License Certificate';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');
		printWindow.document
				.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
		printWindow.document
				.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
		printWindow.document
				.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
		printWindow.document
				.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
		printWindow.document
				.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
		printWindow.document
				.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
		printWindow.document
				.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
		printWindow.document.write('</head><body style="background:#fff;">');
		printWindow.document
				.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();

	}
}

function getOtpBtn() {

	var errorList = [];
	var theForm = '#duplicateLicenseForm';
	var trdLicno = $("#licenseNo").val();

	errorList = validateDuplicateLicenseForm(errorList);
	if (errorList.length == 0) {
		var requestData = {
			'trdLicno' : trdLicno
		}
		var response = __doAjaxRequest(
				'DuplicateLicenseForm.html?getOtpBtnShow', 'POST', requestData,
				false, 'html');
		if (response != null) {
			$(formDivName).html(response);
		}
		$('#otpdetails').show();
	} else {
		displayErrorsOnPage(errorList);
	}

}
function resetDuplicateForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#duplicateLicenseForm").validate().resetForm();
	window.location.reload("#duplicateLicenseForm");
}
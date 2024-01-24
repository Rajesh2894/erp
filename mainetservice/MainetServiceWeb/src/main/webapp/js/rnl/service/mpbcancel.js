/* author : priti.singh*/

function getBookedPropertyDetails(element) {

	var errorList = [];

	errorList = validateMPBdetails(errorList);
	if (errorList.length == 0) {
		var bookingId = $("#bookingId").val();

		$('#bookingIdSet').val(bookingId);
		let bookingNo = $("#bookingId option:selected").text();
		let booking = bookingNo.split(" -");

		$('#bookingNoSet').val(booking[0]);
		let actionParam = "";
		errorList = validateMPBdetails(errorList);
		if (errorList == 0) {
			var theForm = '#mpbestateCancel';

			var requestData = {};
			requestData = __serializeForm(theForm);
			var URL = 'MPBCancellation.html?getBookedPropertyDetails';
			var response = __doAjaxRequest(URL, 'POST', requestData, false);
			$(formDivName).html(response);
		}
	}

	else {
		displayErrorsOnPage(errorList);
	}
}

function validateMPBdetails(errorList) {

	var bookingId = $("#bookingId").val();

	if (bookingId == '' || bookingId == "0" || bookingId == undefined) {
		errorList.push(getLocalMessage("rnl.select.bookingId"));
	}
	return errorList;
}

function saveMPBCancellation(element) {
	var errorList = [];
	let collectionMode = $("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val();
	let isFree = $("#isFree").val();
	// check payment details selected or not
	if ((isFree == false || isFree == "false")
			&& (collectionMode == undefined || collectionMode == '')) {
		errorList.push("Please select Collection Type");
	}
	if (errorList.length == 0) {
		var status;
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			status = saveOrUpdateForm(element,
					"MPB Cancelation done successfully!",
					'MPBCancellation.html?PrintReport', 'saveMPBCancellation');
			getMPBCancellationReport();
		} else {
			showConfirmBoxSave();
		}

	} else {
		displayErrorsOnPage(errorList);
	}
}
function validateDetails(errorList) {

	var checkOrUnchecked = $('input:checkbox[id=checkBoxIds]').is(':checked');
	if (checkOrUnchecked == '' || checkOrUnchecked == "0"
			|| checkOrUnchecked == undefined) {
		errorList.push(getLocalMessage("rnl.read.disclaimer"));
	}
	return errorList;
}

/* function to show pop up box */

function showConfirmBoxSave() {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Yes';

	message += '<h4 class=\"text-center text-blue-2 padding-10\">Are you sure you want to cancel this booking?</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="saveDataAndShowSuccessMsg()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

/* function to show popup box with booking cancel message */

function saveDataAndShowSuccessMsg() {

	var requestData = {};
	var object = __doAjaxRequest(
			"MPBCancellation.html?saveMPBCancellationForFreeCharge", 'POST',
			requestData, false, 'json');

	if (object.error != null && object.error != 0) {
		$.each(object.error, function(key, value) {
			$.each(value, function(key, value) {
				if (value != null && value != '') {
					errorList.push(value);
				}
			});
		});
		displayErrorsOnPage(errorList);
	} else {

		if (object.bookingNo != null) {
			showBoxForApproval(getLocalMessage("rnl.booking.cancel") + " "
					+ object.bookingNo);
		}
	}
}

function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("Proceed");
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	/*
	 * if (payableFlag == 'Y') { var redirectUrl =
	 * 'AgencyRegistration.html?PrintReport'; message += '<div
	 * class=\'text-center padding-bottom-10\'>' + '<input type=\'button\'
	 * value=\'' + 'Proceed' + '\' id=\'btnNo\' class=\'btn btn-success\' ' + '
	 * onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl + '\')"/>' + '</div>'; }
	 */
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' '
			+ ' onclick="getMPBCancellationReport();"/>' + '</div>';

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
}

/* Task #33101 */
function getMPBCancellationReport() {
	//D#102716
	let isFree = $("#isFree").val();
	var requestData = {};
	var URL = 'MPBCancellation.html?getMPBCancellationReport';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	window.open(returnData, '_blank');
	//D#102716
	if (isFree == "true"|| isFree==true) {
	window.location.href = "AdminHome.html";
}}

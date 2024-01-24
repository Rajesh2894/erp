$(document).ready(function() {
	$(".datepicker").datepicker('setDate', new Date());
	$("#inspDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
});

function submitEntryDetails(obj) {

	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length == 0) {

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var data = __doAjaxRequest("InspectionDetailForm.html?saveInspection",
				'POST', requestData, false, 'json');
		var message = '';
		message += '<p class="text-blue-2 text-center padding-15">'
				+ data.messageText
				+ '<br>Do you want to generate show cause notice?</p>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<button class=\'btn btn-success\' onclick="openShowCauseNotice(\''
				+ data.licNo
				+ '\' , \''
				+ data.inspNo
				+ '\')" type=\'button\'>	Yes	</button>&nbsp;'
				+ '<button class=\'btn btn-warning\' onclick="closebox(\''
				+ errMsgDiv
				+ '\',\'InspectionDetailForm.html\')" type=\'button\'>	No	</button>'
				+ '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateForm(errorList) {

	var inspDate = $("#inspDate").val();
	var licNo = $("#licNo").val();
	// var inspNo = $("#inspNo").val();
	var inspectorName = $("#inspectorName").val();
	var desc = $("#desc").val();

	if (inspDate == '' || inspDate == undefined) {
		errorList.push(getLocalMessage("validate.inspection.date"));
	}
	if (licNo == '' || licNo == undefined) {
		errorList.push(getLocalMessage("validate.inspection.licno"));
	}
	/*
	 * if (inspNo == '' || inspNo == undefined) {
	 * errorList.push(getLocalMessage("validate.inspection.no")); }
	 */

	if (inspectorName == '' || inspectorName == undefined) {
		errorList.push(getLocalMessage("validate.inspector.name"));
	}

	if (desc == '' || desc == undefined) {
		errorList.push(getLocalMessage("validate.inspection.desc"));
	}

	return errorList;
}

function openShowCauseNotice(licNo, inspNo) {
	var divName = '.content-page';
	$("#errorDiv").hide();
	var requestData = $("form").serialize() + '&licNo=' + licNo + '&inspNo='
			+ inspNo;

	var ajaxResponse = doAjaxLoading(
			'InspectionDetailForm.html?generateShowCauseNotice', requestData,
			'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$('.fancybox-overlay').hide();

}

function submitNotice(obj) {
	var errorList = [];
	errorList = validateNoticeForm(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var data = __doAjaxRequest(
				"InspectionDetailForm.html?saveNoticeDetails", 'POST',
				requestData, false, 'json');
		var message = '';
		var cls = getLocalMessage("bt.proceed");
		message += '<p class="text-blue-2 text-center padding-15">'
				+ data.messageText + '<br></p>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="printNotice(\'' + data.licNo + '\', \''
				+ data.inspNo + '\', \'' + data.noticeNo + '\')"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	} else {
		displayErrorsOnPage(errorList);
	}

}

function showConfirmBox(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + sucessMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="printNotice()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function printNotice(licNo, inspNo, noticeNo) {
	var divName = '.content-page';
	$("#errorDiv").hide();
	var requestData = $("form").serialize() + '&licNo=' + licNo + '&inspNo='
			+ inspNo + '&noticeNo=' + noticeNo;

	var ajaxResponse = doAjaxLoading(
			'InspectionDetailForm.html?printShowCauseNotice', requestData,
			'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$('.fancybox-overlay').hide();

}

function validateNoticeForm(errorList) {
	 //D#129843 for validating mobile no
	var troMobileno = $("#mobNo").val();
	
	$(".appendableClass tbody tr").each(function(i) {
		var reason = $("#reason" + i).val();
		var rowCount = i + 1;
		if (reason == '' || reason == undefined) {
			errorList.push(getLocalMessage("trade.enter.reason") + rowCount);
		}
	});

	if (troMobileno == "" || troMobileno == undefined || troMobileno == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownerMobileNo"));
	} else if (troMobileno.length < 10) {
		errorList
				.push(getLocalMessage("tradelicense.validation.validMobileNo"));
	}

	return errorList;

}

function addEntryData() {
	var errorList = [];
	errorList = validateNoticeForm(errorList);
	if (errorList.length == 0) {
		addTableRow('noticeReasonTable');
	} else {
		$('#noticeReasonTable').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deleteEntry(obj, ids) {
	var totalWeight = 0;
	var rowCount = $('#noticeReasonTable >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList
				.push(getLocalMessage("tradelicense.validation.firstrowcannotbeRemove"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	deleteTableRow('noticeReasonTable', obj, ids);
	$('#noticeReasonTable').DataTable().destroy();
	triggerTable();
}
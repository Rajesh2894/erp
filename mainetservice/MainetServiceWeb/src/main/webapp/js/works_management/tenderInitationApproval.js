$(document).ready(function() {


});

function saveTenderApprovalData(element) {
	debugger;
	var errorList = [];
	$.fancybox.close();
	errorList = validatetenderAddForm(errorList);
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} 
	else{
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest(
				"TenderInitiationApproval.html?saveTenderApproval", 'POST',requestData, false, 'json');

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
			if (object.tenderInitiationNo != null) {
				showMessageForApproval(object.tenderInitiationNo);
			} else if (object.tndApprovalStatus == 'REJECTED') {
				showBoxForApproval(getLocalMessage('works.tendr.approval.rejected.success'));
			} else {
				showBoxForApproval(getLocalMessage('works.tendr.approval.update.success'));
			}
		}
	}
}


function validatetenderAddForm(errorList) {
	
	
	var decision = $("input[id='decision']:checked").val();
	if (decision == undefined || decision == '') 
		errorList.push(getLocalMessage("Please Select Decision"));
	if ($("#comments").val() == "") 
		errorList.push(getLocalMessage("dmsDto.valid.remark"));
	return errorList;
}

function showMessageForApproval(tenderInitiationNo) {
	debugger;
	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("works.management.proceed");
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">'
			+ getLocalMessage('works.tender.approved.success') + " "
			+ getLocalMessage('works.tender.initiation.no') + " " + tenderInitiationNo
	'</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
			+ '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
	return false;
}

function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("works.management.proceed");
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
			+ '</div>';
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

function saveTenderAwardApprovalData(element) {
	var errorList = [];
	$.fancybox.close();
	var divName = '.content-page';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var object = __doAjaxRequest(
			"TenderAwardApproval.html?saveTenderApproval", 'POST',requestData, false, 'json');
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
		if (object.tenderInitiationNo != null) {
			showMessageForApproval(object.tenderInitiationNo);
		} else if (object.tndApprovalStatus == 'REJECTED') {
			showBoxForApproval(getLocalMessage('works.tendr.approval.rejected.success'));
		} else {
			showBoxForApproval(getLocalMessage('works.tendr.approval.update.success'));
		}
	}
}
function saveDeploymentStaffReqApprovalData(element) {
	var errorList = [];
	if ($("#remark").val() == "") {
		errorList.push(getLocalMessage("dmsDto.valid.remark"))
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest($(theForm).attr('action')
				+ '?saveReqApproval', 'POST', requestData,
				false, 'json');
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
			if (object.wfStatus == 'REJECTED') {
				showBoxForApproval(getLocalMessage('dmsDto.valid.rejectedStatus'));
			} else if (object.wfStatus == 'SEND_BACK') {
				showBoxForApproval(getLocalMessage('dmsDto.valid.sendBack'));
			} else if (object.wfStatus == 'FORWARD_TO') {
				showBoxForApproval(getLocalMessage('dmsDto.valid.forward'));
			} else {
				showBoxForApproval(getLocalMessage('dmsDto.valid.acceptStatus'));
			}
		}
	}
}

function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = 'Proceed';
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

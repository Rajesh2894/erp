function generateBillForMissingNos(element){
	$.fancybox.close();
	var divName = '.content-page';

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var object = __doAjaxRequest(
		"PropertyBillProvisionalDemandGen.html?saveDemandBillSpending", 'POST',
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

	    if (object.success == 'Y') {
		showBoxForApproval(getLocalMessage("Demand generation process initiated"));
	    }
	}
}
function showBoxForApproval(succesMessage) {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

	var redirectUrl = 'PropertyBillProvisionalDemandGen.html?getBillGenLog';
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input type=\'button\' value=\'' + 'Proceed'
		+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
		+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
		+ '\')"/>' + '</div>';

    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
    $(childDivName).html(message);
    $(childDivName).show();
    $('#Proceed').focus();
    showModalBoxWithoutClose(childDivName);
}
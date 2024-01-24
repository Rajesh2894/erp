$(document).ready(function() {
	$('#judgeFName').removeAttr('value');
	$('#judgeLName').removeAttr('value');
});




function saveJudgeData1(element,caseId) {
	debugger;
	var errorList = [];
	errorList = validateJudgeEntryDetails();
	errorList = ValidateJudgeForm().concat(errorList);

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		var flag = saveOrUpdateForm(element,
				getLocalMessage('lgl.saveJudgetMaster'),'',
				'saveform');
		
		if(flag == false)
			{
				backToHearing('HearingDetails.html?EDIT','E',caseId);
			}
		 
	}
}

function backToHearing(url , mode , id)
		{
				var data = {
					"id" : id,
					"mode" : mode
				};
				var divName = '.content-page';
				var ajaxResponse = doAjaxLoading(url, data, 'html', divName);
				$(divName).removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
		}




/*function saveJudgeData(element,caseId) {
	debugger;
	var errorList = [];
	errorList = validateJudgeEntryDetails();
	errorList = ValidateJudgeForm().concat(errorList);
	var id = caseId;
    if (errorList.length > 0) {
	displayErrorsOnPage(errorList);
    }

    else {
	$.fancybox.close();
	var divName = '.content-page';

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var object = __doAjaxRequest(
		"HearingDetails.html?saveJudge", 'POST',
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

	    if (object== true) {
		showBoxForApproval(getLocalMessage('lgl.saveJudgetMaster'),id);
	    }
	    agencyRegAcknow();
	//}

   }

}

function showBoxForApproval(succesMessage,caseId) {
	debugger;
	    var childDivName = '.msg-dialog-box';
	    var message = '';
	    var Proceed = getLocalMessage("proceed");
	    var no = 'No';
	    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
		    + '</p>';

	   
	    var mode = 'E';
		var url = 'HearingDetails.html?EDIT';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' '
			+ ' onclick="getData(\'' + caseId + '\',\'' + mode
			+ '\',\'' + url + '\');"/>' + '</div>';

	    

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

*/
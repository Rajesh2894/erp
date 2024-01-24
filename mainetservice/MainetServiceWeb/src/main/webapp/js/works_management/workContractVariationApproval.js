/*
 * vishwajeet.kumar 
 * 19 June 2018
 */

$(document).ready(function() {
	if($("#contractTaskName").val() == 'Initiator'){
		$("input[type=radio][value='REJECTED']").attr("disabled",true);	
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",true);
		$("input[type=radio][value='SEND_BACK']").attr("disabled",true);
	}
});

function viewWorkVariation(contId,contractMode){
	
	var divName = '.content-page';
	var url = "WorkContractVariationApproval.html?viewWorkContractVariation";
	var actionParam = $("form").serialize() + '&contId=' + contId + '&contractMode=' + contractMode ;
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getContractDetailsByContId(contId);
	
}



function showConfirmBoxForApproval(element){
		var errorList = [];
		errorList = validateTermsAndConditionForApproval(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		$.fancybox.close();
		return saveOrUpdateForm(element,
				getLocalMessage('work.contract.variation.approval.creation.success'),
				'AdminHome.html', 'saveform');
	}
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}
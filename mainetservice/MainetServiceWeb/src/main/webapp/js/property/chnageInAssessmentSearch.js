function SearchButton(element) {	
/*	var errorList = [];
	errorList = ValidateInfoDetails(errorList);	*/
/*	if (errorList.length == 0) {*/
	var theForm	=	'#changeAssessmentSearch';
	var requestData = {};
	requestData = __serializeForm(theForm);
/*	var URL = 'ChangeInAssessmentForm.html?displayChangeInAssessmentForm';
	var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
	if(returnData)
	{
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}*/
	var ajaxResponse = __doAjaxRequest(
			'ChangeInAssessmentForm.html?displayChangeInAssessmentForm', 'POST',
			requestData, false, 'html');

	$("#dataDiv").html(ajaxResponse);
	return false;
	/*}
	else {
		displayErrorsOnPage(errorList);
	}*/
}


function ValidateInfoDetails(errorList){

	if ($("#proertyNo").val() == "" && $("#oldPid").val() == "") {
		errorList.push(getLocalMessage("property.changeInAss"));
	}
	return errorList;
}
function SearchButton(element) {
	var theForm	=	'#changeAssessmentSearch';
	var requestData = {};
	requestData = __serializeForm(theForm);

	var ajaxResponse = __doAjaxRequest(
			'ChangeInAssessmentForm.html?displayChangeInAssessmentForm', 'POST',
			requestData, false, 'html');
	
	// for Assessment Type  
	if(($('option:selected', $("#selectedAssType")).attr('code')) == 'C'){
	$(".dataDiv").html(ajaxResponse);
	}
	$("#dataDiv").html(ajaxResponse);
	reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table

	reorderfactor('.specFact','.eachFact');
	
	return false;
	
}

function ValidateInfoDetails(errorList){

	if ($("#proertyNo").val() == "" && $("#oldPid").val() == "") {
		errorList.push(getLocalMessage("property.changeInAss"));
	}
	return errorList;
}

function backToMain(){
	var data={};
	var URL='PropertyAssessmentType.html?backToMainPage';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}
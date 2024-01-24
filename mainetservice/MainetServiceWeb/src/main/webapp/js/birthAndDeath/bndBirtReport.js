
function confirmToProceed(requestData) {
	
	var requestData='birtReport='+ requestData;
	var URL='BirthAndDeathReport.html?GetBirtReport';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	if(returnData == "f")
	{
		errorList.push('Invalid');
		displayErrorsOnPage(errorList);
	}
	else
	{
		window.open(returnData,'_blank' );
	}
	
}

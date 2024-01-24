function viewReport(obj) {
	
	var errorList = [];
	
	
	errorList = validateForm(errorList);
	
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	else
		{
		
	var fromDate= $('#fromDateId').val();
	var toDate= $('#toDateId').val();
	errorList = dateValidation(errorList,'fromDateId','toDateId');
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	
	else
		{
	 if (fromDate == undefined ) {
		fromDate = "";
	} if (toDate == undefined ) {
		toDate = "";
	} 
	
	var selectedReportType = $('#reportTypeId option:selected').attr('code');
	var requestData = {
			'reportTypeCode': selectedReportType,
			'fromDate': fromDate,
			'toDate': toDate
	}
	var url = "AccountCollectionSummaryReport.html?report";
	var ajaxResponse = __doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
	    if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
	    	if (hiddenVal == 'Y') {
	    		var errorList =[];
	    		errorList.push('No record found!');
	    		displayErrorsOnPage(errorList);
	    	} else {
	    		$('#content').html(ajaxResponse);
	    	}
	    }
	    }
		}
}

function validateForm(errorList) {
	
	var fromDate= $('#fromDateId').val();
	if (fromDate == "" || fromDate == null) {
		errorList.push(getLocalMessage('account.select.valid.fromDate'));
	}
	var toDate= $('#toDateId').val();
	if (toDate == "" || toDate == null) {
		errorList.push(getLocalMessage('account.select.valid.toDate'));
	}
	var selectedReportType = $('#reportTypeId option:selected').attr('code');
	if (selectedReportType == "" || selectedReportType == null) {
		errorList.push(getLocalMessage('account.select.reportType'));
	}
	
	
	return errorList;
}
	

function displayErrorsPage(errorList) {

	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}


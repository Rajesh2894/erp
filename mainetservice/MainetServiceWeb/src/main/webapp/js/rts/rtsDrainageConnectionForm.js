function validateForm() {
	var errorList = [];
debugger;
	var address = $("#rtiAddress").val();
	var applicantType = $("#applicantType").val();
	var sizeOfConnection = $("#sizeOfConnection").val();
	var typeOfConnection = $("#typeOfConnection").val();
	var applType = $("#applType").val();
	var ward = $("#ward").val();
	var propertyIndexNo = $("#propertyIndexNo").val();

	if (address == "" || address == undefined || address == null) {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.address"));
	}
	if (applicantType == "" || applicantType == undefined || applicantType == null || applicantType == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.applicantType"));
	}
	if (sizeOfConnection == "0" || sizeOfConnection == undefined || sizeOfConnection == null || sizeOfConnection == "") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.sizeOfConn"));
	}
	if (typeOfConnection == "" || typeOfConnection == undefined || typeOfConnection == null || typeOfConnection == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.typeOfConn"));
	}
	if (applType == "" || applType == undefined || applType == null || applType == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.ApppFor"));
	}
	if (ward == "0" || ward == undefined || ward == null|| ward == "") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.ward"));
	}
	if (propertyIndexNo == "" || propertyIndexNo == undefined || propertyIndexNo == null) {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.propertyIndex"));
	}
	return errorList;
}

function saveDrainageConnection(element) {
	 
	var checkListApplFlag =$("#checkListApplFlag").val();
	var errorList = [];
	errorList = validateForm();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		if(checkListApplFlag =="N" && $("#applicationchargeApplFlag").val() =="N")
		{
			return saveOrUpdateForm(element,"Your application for Drainage connection saved successfully!", 'AdminHome.html', 'saveRts');
		}
		
		else{
		var requestData = element;
		var divName = '.content-page'
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		// var formUrl = 'drainageConnection.html';
		var formUrl = 'drainageConnection.html';
		var actionParam = 'showCheckList';
		var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
				requestData, false, '', 'html');
		$(divName).html(response);
		$(divName).removeClass('ajaxLoader')
		prepareTags();
		}
	}

	/*
	 * return saveOrUpdateForm(element, 'Drainage Connection Record Saved
	 * Successfully', 'rtsService.html', 'saveform');
	 */

}


function previousPage()
{  
	var applicationId =$("#apmApplicationId").val();
	if(applicationId == "" || applicationId == null || applicationId== undefined)
		{
		applicationId ="0";
		}
	
	var divName = '.content-page';
	requestData =
	{"applicationId":applicationId};
	

	var ajaxResponse = doAjaxLoading('rtsService.html?applicantForm', requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function ResetForm()
{
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('drainageConnection.html?resetdraiangeForm', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}


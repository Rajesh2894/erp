
$(document).ready(function() {
	
 $("#resetform").on("click", function(){ 
		  window.location.reload("#cancellationLicenseForm")
		});

});

function getLicenseDetails() {
	var errorList = [];
	var theForm = '#cancellationLicenseForm';
	var trdLicno = $("#trdLicno").val();
	if(trdLicno == null || trdLicno == "" || trdLicno == undefined){
		errorList.push(getLocalMessage("trade.ValidateLicenseNo"));
	}
	if (errorList.length == 0) {
		
	var requestData = {
		'trdLicno' : trdLicno
	}
	var response = __doAjaxRequest(
			'CancellationLicenseForm.html?getLicenseDetails', 'POST', requestData,
			false, 'html');

	$(formDivName).html(response);
	}else{
		displayErrorsOnPage(errorList);
	}
}

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function saveCancellationLicense(obj){
	
	var errorList = [];
	
	errorList = validateCancellationLicenseForm(errorList);
	if (errorList.length == 0) {
		
		return saveOrUpdateForm(obj,
				"Cancellation Of License Submitted Successfully", "AdminHome.html",
				'saveCancellationForm');
	}
	else{
		displayErrorsOnPage(errorList);
	}
}

function getChecklistAndCharges(obj) {
	var errorList = [];
	errorList = validateCancellationLicenseForm(errorList);
	if (errorList.length == 0) {
	var theForm = '#cancellationLicenseForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'CancellationLicenseForm.html?getCheckListAndCharges';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	if (returnData != null) {

		$(formDivName).html(returnData);
		prepareTags();
	}
	}
	else{
		displayErrorsOnPage(errorList);
	}
}

function generateChallanAndPayment(element) {
	var errorList = [];
	if (errorList.length == 0) {
	
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		return saveOrUpdateForm(element,
				"Your application cancel successfully!",
				'CancellationLicenseForm.html?PrintReport', 'generateChallanAndPayement');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		return saveOrUpdateForm(element,
				"Your application cancel successfully!!",
				'CancellationLicenseForm.html?redirectToPay', 'generateChallanAndPayement');
	}
	else{
		
		var status =  saveOrUpdateForm(element,
		"Your application cancel successfully!!",
		'AdminHome.html', 'generateChallanAndPayement');
	}
	} else {
		
		displayErrorsOnPage(errorList);
	}
}


function validateCancellationLicenseForm(errorList) {
	var errorList = [];
	var remark = $("#remarks").val();

	if (remark == "" || remark == undefined || remark == "") {
		errorList.push(getLocalMessage("trade.validation.remark"));
	}

	return errorList;
}
function saveCancellation(obj) {
	var errorList = [];
	//errorList = validateMarketLicenseForm(errorList);
	//errorList = errorList.concat(validateOwnerDetailsTable());
	// = errorList.concat(validateitemDetailsTable());
	
	
	if (errorList.length == 0) {
		
	
		
		return saveOrUpdateForm(obj,
				"Cancellation Form Submitted Successfully", "CancellationLicenseForm.html",
				'saveCancellationLicenseForm');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}

function editForm(element){
	return saveOrUpdateForm(element,
			"Your application Data  saved successfully!!",
			'AdminHome.html', 'saveCancellationLicenseForm');	
}
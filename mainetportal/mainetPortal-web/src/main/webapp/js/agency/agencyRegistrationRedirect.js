
function saveForm(element) {
	var messg=getLocalMessage('tp.app.succsMsg');

	var value = $("input:radio[name='entity.onlineOfflineCheck']").filter(":checked").val();

	var paramValue = '';

	switch (value) {
	case 'Y':
		paramValue = 'AgencyRegistrationRedirect.html?redirectToPay';
		break;

	case 'N':
		paramValue = 'AgencyRegistrationRedirect.html?PrintReport';
		break;

	case 'P':
		paramValue = 'AgencyRegistrationRedirect.html?PrintCounterReceipt';
		break;
	default:
		//Arun
		/*paramValue = 'CitizenHome.html';*/
		paramValue ='TPTechnicalPerson.html';
		break;

	}

	return saveOrUpdateForm(element,messg,paramValue, 'saveTechPerson');
}

function saveOnceAgainPaymentForm(element) {
	var messg=getLocalMessage('tp.app.succsMsg');

	var value = $("input:radio[name='entity.onlineOfflineCheck']").filter(
			":checked").val();

	var paramValue = '';

	switch (value) {
	case 'Y':
		paramValue = 'AgencyRegistrationRedirect.html?redirectToPay';
		break;

	case 'N':
		paramValue = 'AgencyRegistrationRedirect.html?PrintReport';
		break;

	case 'P':
		paramValue = 'AgencyRegistrationRedirect.html?PrintCounterReceipt';
		break;
	default:
		paramValue = 'AgencyRegistrationRedirect.html';
		break;

	}

	return saveOrUpdateForm(element,messg,paramValue, 'saveOnceAgainPayment'); 
}


function clearForm(url) {
	window.open(url, '_self', false);

}


function saveTPLicRegForm(element) {
	var messg=getLocalMessage('tp.app.succsMsg');
    if($("#amountvalue").val() == 0) {
		return saveOrUpdateForm(element, messg, 'AgencyRegistrationRedirect.html?noAmount', 'saveform');
	}else{
		return saveOrUpdateForm(element, messg, 'AgencyRegistrationRedirect.html?redirectToPay', 'saveform');
	}
}


/**
 * used to find applicable checklist and charges for change of plumber license
 * @param obj
 */
 function getChecklistAndChargesPlumberLicense(obj) {
	var errorList = [];
	//errorList = validateApplicantInfo(errorList);
	//errorList = checkAcademicDetails(errorList);
	//var isBPL = $('#povertyLineId').val();
	/*if (errorList.length == 0) {*/
		var	formName =	findClosestElementId(obj, 'form');
		var theForm	=	'#'+formName;
		var request = __serializeForm(theForm);
		var url	='AgencyRegistrationRedirect.html?getCheckListAndCharges';
		var returnData=__doAjaxRequest(url, 'POST',request, false);
		$(formDivName).html(returnData);
		/*$('#povertyLineId').val(isBPL);*/
	/*} else {
		displayErrorsOnPage(errorList);
	}*/
 }

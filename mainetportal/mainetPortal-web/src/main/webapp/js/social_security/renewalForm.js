var url="RenewalForm.html";
$(document).ready(function() {	
	
	$("#validtodateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});
	
	$("#validtodateId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$("#nameofApplicant").prop("disabled",true);
	$("#selectSchemeName").prop("disabled",true);
	$("#lastDateofLifeCerti").prop("disabled",true);
	
	$("#resetform").on("click", function(){ 
		  window.location.reload("#renewalFormId")
		});
		
});

function renewalFormValidation(errorList) {
	
	let beneficiaryno=$("#beneficiaryno").val();
	
	let validtodateId=$("#validtodateId").val();
	
	if(beneficiaryno == "0" || beneficiaryno == undefined
			|| beneficiaryno == '')
		{
		errorList.push(getLocalMessage('Beneficiary Number is  Required'));
		}
	
	if(validtodateId == "0" || validtodateId == undefined
			|| validtodateId == '')
		{
		errorList.push(getLocalMessage('Valid to Date is Required'));
		}
	return errorList;
}


/*function saveRenewalForm(element){
	
	
	var errorList = [];
	errorList =renewalFormValidation(errorList);
	if(errorList.length == 0){
		
		var divName = formDivName;
		//var formName = findClosestElementId(element, 'form');
		var theForm = '#renewalFormId'; 

		var requestData = __serializeForm(theForm);
	//return saveOrUpdateForm(element, '', 'RenewalForm.html', 'saveform');
		return saveOrUpdateForm(element, '', 'RenewalForm.html', 'saveDecision');
		var response = __doAjaxRequest(url+'?saveRenewalFormDetails', 'POST', requestData, false, 'html');
		//var response = __doAjaxRequest(url+'?saveRenewalFormDetails', 'POST',requestData, false, '', 'html');
		$(divName).html(response);
	}
	else{
		displayErrorsOnPage(errorList);
	}
	
	
}*/

//function to save renewal form
function saveRenewalForm(element){
	
	var errorList = [];
	errorList =renewalFormValidation(errorList);
	if(errorList.length == 0){
		
		return saveOrUpdateForm(element,"Form Saved Successfully", 'RenewalForm.html', 'saveRenewalFormDetails');

		$(divName).html(response);
	}
	else{
		displayErrorsOnPage(errorList);
	}
	
}
//function to get data on enter of beneficiary number
function getDataOnBenef(){
	
	var errorList = [];
	var benefId= $("#beneficiaryno").val();
	if (benefId == "" || benefId == undefined) {
		errorList.push(getLocalMessage("Please Enter the Beneficiary Number"));
	}
	var theForm = '#renewalFormId';

	/*var data = {"benefId" : benefId};
	var URL = 'RenewalForm.html?getDataonBenefNo';*/
	if(errorList.length == 0){
	//var requestData = {};
		//var requestData = {'benefId':benefId};
	var requestData = __serializeForm(theForm);
	var url = "RenewalForm.html?getDataonBenefNo";
	var returnData = __doAjaxRequest(url, 'POST', requestData, false);
	$(formDivName).html(returnData);
	}
	else
		{
		displayErrorsOnPage(errorList);
		}
	
	function resetRenewalForm()
	{
		
		$('input[type=text]').val('');  
		$(".alert-danger").hide();
		$("#renewalFormId").validate().resetForm();
	}
	
}


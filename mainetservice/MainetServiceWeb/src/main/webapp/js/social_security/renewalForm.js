var url="RenewalForm.html";
$(document).ready(function() {	
var lastcertidate=$('#lastDateofLifeCerti').val();
$("#validtodateId").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : lastcertidate ,
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
	
	
	var benefnumNname = $("#benefnumNname").val();
	let validtodateId=$("#validtodateId").val();
	
	if (benefnumNname == "0" || benefnumNname == undefined
			|| benefnumNname == '') {
		errorList.push(getLocalMessage('social.sec.dropdown.benefnoNname.req'));
	}
	
	if(validtodateId == "0" || validtodateId == undefined
			|| validtodateId == '')
		{
		errorList.push(getLocalMessage('social.valid.toDate'));
		}
	return errorList;
}



// function to save renewal form
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

// function to get data on enter of beneficiary number

/*function getDataOnBenef(){
	
	var errorList = [];
	var benefId= $("#beneficiaryno").val();

	if (benefId == "" || benefId == undefined) {
		errorList.push(getLocalMessage("Please Enter the Beneficiary Number"));
	}
	var theForm = '#renewalFormId';

	if(errorList.length == 0){
	
	var requestData = __serializeForm(theForm);
	var url = "RenewalForm.html?getDataonBenefNo";
	var returnData = __doAjaxRequest(url, 'POST', requestData, false);
	$(formDivName).html(returnData);
	}
	else
		{
		displayErrorsOnPage(errorList);
		}
}*/

function resetRenewalForm()
{
	
	$('input[type=text]').val('');  
	$(".alert-danger").hide();
	$("#renewalFormId").validate().resetForm();
}


$(document).on('change', '#benefnumNname', function() {
	
	var errorList = [];
	var theForm = '#renewalFormId';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'RenewalForm.html?getDataonBenefNo';
	var response = __doAjaxRequest(URL, 'POST', requestData, false);
	$(formDivName).html(response);
});

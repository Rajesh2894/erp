$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0

	});

	$('.chosen-select-no-results').chosen();
	
$('.alphaNumeric').keyup(function() {
		
		var regx = /^[A-Za-z0-9.\s]*$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
});

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FundReleaseRequestForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var fileReferenceNumber = $("#fileReferenceNumber").val();
	var financialYear = $("#financialYear").val();
	var iaId = $("#iaId").val();
	var divName = '.content-page';
	if ((fileReferenceNumber == "" || fileReferenceNumber == undefined) && (financialYear == "" ||  financialYear == undefined || financialYear =="0")) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
				"iaId" : iaId,
				"applicationRef" :fileReferenceNumber,
				"fy" :financialYear
				
		};
		var ajaxResponse = doAjaxLoading('FundReleaseRequestForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}



function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FundReleaseRequestForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(frrId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"frrId" : frrId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveFundReleaseReqApprovalData(obj){
	return saveOrUpdateForm(obj, "Fund Release Request Saved Successfully!", 'AdminHome.html', 'saveform');
}

function addNewDemandButton(element) {
	var errorList = [];
	errorList = validateDocDetailsTable(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var row = $("#newDemandetailsTable tbody .appendableNewDemandetails").length;
	
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('FundReleaseRequestForm.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		
	
		$('.content-page').html(response);
	prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}

}

function validateDocDetailsTable(errorList){
	

	
	var rowCount = $('#newDemandetailsTable tr').length;	


	
		$("#newDemandetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var purposeFor = $("#purposeFor" + i).val();
				var allocatedBudget = $("#allocatedBudget" + i).val();
				var totalFundRelTillDate = $("#totalFundRelTillDate" + i).val();
				var utilizedAmount = $("#utilizedAmount" + i).val();
				var newDemand = $("#newDemand" + i).val();
			
				var constant = 1;
       }
        else{
        	var purposeFor = $("#purposeFor" + i).val();
			var allocatedBudget = $("#allocatedBudget" + i).val();
			var totalFundRelTillDate = $("#totalFundRelTillDate" + i).val();
			var utilizedAmount = $("#utilizedAmount" + i).val();
			var newDemand = $("#newDemand" + i).val();
        	var constant = i+1;
     }
		
				
				if (purposeFor == undefined || purposeFor == "" || purposeFor =="0") {
					errorList.push(getLocalMessage("sfac.fund.release.req.validation.purposeFor") + " " + (i + 1));
				}
				
				if (allocatedBudget == undefined || allocatedBudget == "" ) {
					errorList.push(getLocalMessage("sfac.fund.release.req.validation.allocatedBudget") + " " + (i + 1));
				}
				if (totalFundRelTillDate == undefined || totalFundRelTillDate == "" ) {
					errorList.push(getLocalMessage("sfac.fund.release.req.validation.totalFundRelTillDate") + " " + (i + 1));
				}
				if (utilizedAmount == undefined || utilizedAmount == "" ) {
					errorList.push(getLocalMessage("sfac.fund.release.req.validation.utilizedAmount") + " " + (i + 1));
				}
				if (newDemand == undefined || newDemand == "") {
					errorList.push(getLocalMessage("sfac.fund.release.req.validation.newDemand") + " " + (i + 1));
				}
			
		});
	
	return errorList;

}

function deleteNewDemandDetails(obj, id) {

	requestData = {
		"id" : id
	};

	var row = $("#newDemandetailsTable tbody .appendableNewDemandetails").length;
	if (row != 1) {
		var response = __doAjaxRequest('FundReleaseRequestForm.html?doEntryDeletion',
				'POST', requestData, false, 'html');
		$(".content-page").html(response);
		prepareTags();
	}
}

function calculateTotal(ids) {
	var newDemand = $("#newDemand"+ids).val();
	var newDemandTotal = $("#newDemandTotal").val();
	if (newDemand !=undefined && newDemand != ""){
		if(newDemandTotal ==undefined || newDemandTotal == ""){
			newDemandTotal = 0;
		}
		$("#newDemandTotal").val(parseFloat(newDemandTotal) + parseFloat(newDemand));
	}
}

function saveFundRelaseRequestForm(obj) {
	
	var errorList = [];
	
	errorList = validateFundReleaseReqForm(errorList);
  
	if (errorList.length == 0) {
		
		return saveOrUpdateForm(obj, "Fund Release Request Details Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateFundReleaseReqForm(errorList) {
	


	 var fileReferenceNumber =$("#fileReferenceNumber").val();
	   
	    var financialYear =$("#financialYear").val();
	   	
	    
		if (fileReferenceNumber == undefined || fileReferenceNumber == "" ) {
			errorList.push(getLocalMessage("sfac.fund.release.req.validation.fileReferenceNumber") );
		}
		
		if (financialYear == undefined || financialYear == "" || financialYear == "0") {
			errorList.push(getLocalMessage("sfac.fpo.pm.validation.finYear") );
		}
		
	    
				
		errorList = validateDocDetailsTable(errorList);
		
	
	return errorList;
}





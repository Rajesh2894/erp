$(document).ready(function() {   
	$('#vendorId').chosen();

	if('V' == $('#saveMode').val()){
		disableFields();
	}
			
	isPaid();
		
	timeRemover();
	
	
});


function receiptAmmount() {
	var receiptAmt = $('#receiptAmt').val();
	$('#amountToPay').val(receiptAmt);
}


function timeRemover(){
	
	$(".disposedDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : $('#movementDate').val(),
		maxDate : new Date(),
	});
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
	});
	
	var expiryCheck = $('#expiryCheck').val();
	if (expiryCheck) 
		$('#expiryCheck').val(expiryCheck.split(' ')[0]);
	
	var movementDate = $('#movementDate').val();
	if (movementDate) 
		$('#movementDate').val(movementDate.split(' ')[0]);
	
	var disposedDate = $('#disposedDate').val();
	if (disposedDate) 
		$('#disposedDate').val(disposedDate.split(' ')[0]);
}


function isPaid(){
	var paymentFlag = $("input[id='paymentFlag']:checked").val();
	if ( paymentFlag == "N") {
		$('.paymentRecievedDiv').hide();
	}else {
		$('.paymentRecievedDiv').show();
	}
}


function disableFields(){
	$('select').attr("disabled", true);
	$('input[type=text]').attr("disabled", true);
	$('input[type="text"], textarea').attr("disabled", true);
	$('select').prop('disabled', true).trigger("chosen:updated");
}


/***************** save Disposal Form ****************/
function saveApprovalForm(obj) {  
	var errorList = [];
	if( 1 == $('#levelCheck').val())
		errorList = validateExpiryAddForm(errorList);		
	else
		errorList = validateReceiptEntryForm(errorList);	
	
	var paymentFlag = $("input[id='paymentFlag']:checked").val();
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {		
		return saveOrUpdateForm(obj, " ", 'DisposalOfStock.html', 'saveform');
	}
}

function closebox() {
	if('E' == $('#saveMode').val())
		window.location.href = 'DisposalOfStock.html';
	else
		window.location.href = 'AdminHome.html';
    $.fancybox.close();	
}


function validateExpiryAddForm(errorList) {

	errorList = validateItemDetails(errorList);	
	
	var movementNo = $('#movementNo').val();
	var movementBy = $('#movementBy').val();
	var storeId = $('#storeId').val();
	var expiryCheck = $('#expiryCheck').val();
	var movementDate = $('#movementDate').val();
	
	if (movementNo == "" || movementNo == null || movementNo == undefined || movementNo == '0')
		errorList.push(getLocalMessage("material.management.movement.no.valid"));
	if (movementBy == "" || movementBy == null || movementBy == undefined || movementBy == '0')
		errorList.push(getLocalMessage("material.management.movement.by.valid"));
	if (movementDate == "" || movementDate == null || movementDate == undefined) 
		errorList.push(getLocalMessage("material.management.movement.date.valid"));
	if (storeId == "" || storeId == null || storeId == undefined || storeId == '0')
		errorList.push(getLocalMessage("material.management.store.name.valid"));
	if (expiryCheck == "" || expiryCheck == null || expiryCheck == undefined) 
		errorList.push(getLocalMessage("material.management.expiry.on.before.date.valid"));
	
	var decision = $("input[id='decision']:checked").val();
	if (decision == undefined || decision == '') 
		errorList.push(getLocalMessage("material.validation.select.Decision"));
	if ($("#comments").val() == "") 
		errorList.push(getLocalMessage("material.management.remark.user.action.valid"))

	return errorList;
}


function validateItemDetails(errorList) {	
	$('.ExpiredItemDetailsRow').each(function(i){	
		var binLocation = $("#binLocation" + i).val();
		var itemId = $("#itemId" + i).val();
		var uomId = $("#uomId" + i).val();
		var quantity = $("#quantity" + i).val();
		var remark = $("#remark" + i).val();
		
		if (binLocation == "0" || binLocation == null || binLocation == "" || binLocation == undefined) 
			errorList.push(getLocalMessage("material.management.bin.location.valid") + " " + (i + 1));
		if (itemId == "0" || itemId == null || itemId == "" || itemId == undefined) 
			errorList.push(getLocalMessage("material.management.item.name.cannot.empty") + " " + (i + 1));
		if (uomId == "0" || uomId == null || uomId == "" || uomId == undefined) 
			errorList.push(getLocalMessage("uoM.Can.Not.Be.Empty") + " " + (i + 1));
		if (quantity == "0" || quantity == null || quantity == "" || quantity == undefined) 
			errorList.push(getLocalMessage("material.management.expiry.quantity.valid") + " " + (i + 1));
		if (remark == "0" || remark == null || remark == "" || remark == undefined) 
			errorList.push(getLocalMessage("material.management.validate.Remark") + " " + (i + 1));
	});
	return errorList;
}



function validateReceiptEntryForm(errorList) {
	var paymentFlag = $("input[id='paymentFlag']:checked").val();
	if (paymentFlag == "" || paymentFlag == null || paymentFlag == undefined || paymentFlag == '0')
		errorList.push(getLocalMessage("material.management.is.payment.recieved.valid"));
		
	if ( paymentFlag == "Y") {
		var receiptAmt = $('#receiptAmt').val();
		var vendorId = $('#vendorId').val();
		var disposedDate = $('#disposedDate').val();
		var movementDate = $('#movementDate').val();
	
		if (receiptAmt == "" || receiptAmt == null || receiptAmt == undefined || receiptAmt == '0')
			errorList.push(getLocalMessage("material.management.receipt.amount.valid"));
		if (vendorId == "" || vendorId == null || vendorId == undefined) 
			errorList.push(getLocalMessage("please.Select.Vendor.Name"));
		if (disposedDate == "" || disposedDate == null || disposedDate == undefined || disposedDate == '0')
			errorList.push(getLocalMessage("material.management.disposed.date.valid"));
				
		errorList = validateRreceiptEntryDetails(errorList);	
	}
	return errorList;
}


function validateRreceiptEntryDetails(errorList){
	var option=$("#payModeIn option:selected").attr("code");
	var bankID = $('#bankID').val();
	var acNo = $('#acNo').val();
	var chqNo = $('#chqNo').val();
	var bmChqDDDate = $("input[name*='offlineDTO.bmChqDDDate']").val();
		
	if (option == "" || option == null || option == undefined || option == '0')
		errorList.push(getLocalMessage("material.management.entry.validation.payMode"));
	
	if(option == 'D' || option == 'B' || option == 'Q' ){
		if (bankID == "" || bankID == null || bankID == undefined || bankID == '0')
			errorList.push(getLocalMessage("material.management.drawn.on.valid"));
		if (acNo == "" || acNo == null || acNo == undefined || acNo == '0')
			errorList.push(getLocalMessage("material.management.account.no.valid"));
		if (chqNo == "" || chqNo == null || chqNo == undefined || chqNo == '0'){
			if(option == 'D')
				errorList.push(getLocalMessage("account.enter.dd.code"));
			else if( option == 'B' || option == 'Q' )
				errorList.push(getLocalMessage("account.enter.chequeNo"));
		}
		if (bmChqDDDate == "" || bmChqDDDate == null || bmChqDDDate == undefined || bmChqDDDate == '0'){
			if(option == 'D')
				errorList.push(getLocalMessage("account.select.dd.date"));
			else if( option == 'B' || option == 'Q' )
				errorList.push(getLocalMessage("account.select.chequeDate"));
		}			
	}
	
 	return errorList;
}


function backTenderForm(){
	var divName = '.content-page';
	var url = "TenderInitiation.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');	
	$(divName).html(ajaxResponse);
	prepareTags();
	getProjectList();
	
}


function getProjectList(){
	var deptId = $("#deptId").val();
	var project = $("#project").val();
	if(deptId!="" &&  deptId!=0){
		var requestData ={"deptId":deptId}
		var projectList = __doAjaxRequest('TenderInitiation.html?getProjectList', 'POST', requestData, false,'json');
		$.each(projectList, function(index, value) {
		$('#projId').append($("<option></option>").attr("value",value.projId).attr("code",value.projCode).text(value.projNameEng));
	});
	$("#projId").val(project).trigger('chosen:updated');
	}
}



$(document).ready(function() {
	$("#paymentDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		// minDate : '0d',
		// maxDate : '0d',
		yearRange : "-100:+20",
	});
	$("#stoppayDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		// minDate : '0d',
		// maxDate : '0d',
		yearRange : "-100:+20",
	});
	
	$("#paymentDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	
	$("#stoppayDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	//Restrict space at first position in text
	$("textarea").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
	$("input").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});

	$("#showPaymentDetail").hide();
});

function save(element) {
	var errorList = [];
	errorList = validateForm(errorList)
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, 'Stop payment entry updated successfully',
				'StopPayment.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}
function validateForm(errorList) {
	debugger;
	var stoppayDate = $("#stoppayDate").val();
	var stopPayRemark = $("#stopPayRemark").val();
	var paymentNumber = $("#paymentNumber").val();
	var paymentdate = $("#paymentdate").val();
	
	//var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	//var date = new Date($("#depEntryDateId").val().replace(pattern,'$3-$2-$1'));
	//var paidDate = new Date(paymentdate.replace(pattern,'$3-$2-$1'));
	//var stopPaymentDate = new Date(stoppayDate.replace(pattern,'$3-$2-$1'));
	
	
	if ($.datepicker.parseDate('dd/mm/yy', paymentdate) > $.datepicker
		    .parseDate('dd/mm/yy', stoppayDate)) {
		errorList
			.push(getLocalMessage("account.stopPaymentDate.not.less.paymentDate"))
	    }
	
	
	if(stoppayDate == ""){
		errorList.push(getLocalMessage("account.enter.stopPaymentDate"))
	}
	if(stopPayRemark == ""){
		errorList.push(getLocalMessage("account.enter.reason"));
	}
	if(paymentNumber == ""){
		errorList.push(getLocalMessage("account.search.paymentDetails"));
	}
	return errorList;
}
function searchPaymentDetails() {
	debugger;
	var errorList = [];
	var paymentNo = $("#paymentNo").val();
	var instrumentNumber = $("#instrumentNumber").val();
	var paymentDate = $("#paymentDate").val();
	var requestData = {
		"paymentNo" : paymentNo,
		"instrumentNumber" : instrumentNumber,
		"paymentDate" : paymentDate
	};

	if (paymentNo != '' || instrumentNumber != '' || paymentDate != '') {
		if (paymentDate != '' && paymentNo == '' && instrumentNumber == '') {
			errorList
					.push(getLocalMessage("account.enter.paymentNo.chequeNo"));
			displayErrorsOnPage(errorList);
			return false;
		}
		if (paymentDate == '') {
			errorList.push(getLocalMessage("account.select.date"));
			displayErrorsOnPage(errorList);
			return false;
		}
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'StopPayment.html?searchPaymentDetails', requestData, 'json');
		
		if(ajaxResponse.biilPaymentFlag != null && ajaxResponse.biilPaymentFlag != "" && ajaxResponse.biilPaymentFlag != undefined){
			
			if(ajaxResponse.biilPaymentFlag == 'Q'){
				if(ajaxResponse.checqueStatusCode != null && ajaxResponse.checqueStatusCode != "" && ajaxResponse.checqueStatusCode != undefined){
					if(ajaxResponse.checqueStatusCode != 'ISD'){
						errorList
						.push(getLocalMessage("acccount.transaction.already.done") + " "+ajaxResponse.paymentEntryDto.paymentNo);
						displayErrorsOnPage(errorList);
						return false;
					}
				}
			}else{
				errorList
				.push(getLocalMessage("account.paymentDoneBy.bank.other.mode.notEligible.stopPayment"));
				displayErrorsOnPage(errorList);
				return false;
			}
			
		}else{
				errorList
				.push(getLocalMessage("accout.nodata.criteria"));
				displayErrorsOnPage(errorList);
				return false;
			}
		
		$("#paymentNumber").val(ajaxResponse.paymentEntryDto.paymentNo);
		$("#paymentdate").val(ajaxResponse.paymentEntryDto.paymentEntryDate);
		$("#vmVendorid").val(ajaxResponse.tbAcVendormaster.vmVendorname);
		$("#paymentAmount").val(ajaxResponse.paymentEntryDto.paymentAmount.toFixed(2));
		$("#baAccountNo").val(ajaxResponse.bankAccountMasterDto.baAccountNo);
		$("#instrumentNo").val(ajaxResponse.paymentEntryDto.instrumentNumber);
		$("#showPaymentDetail").show();
		$("#searchButtons").hide();
		$("#paymentNo").prop("readonly", true);
		$("#instrumentNumber").prop("readonly", true);
		$("#paymentDate").prop("readonly", true);
		
	} else {
		errorList
				.push(getLocalMessage('account.select.paymentDate.chequeNo'));
		displayErrorsOnPage(errorList);
	}
}

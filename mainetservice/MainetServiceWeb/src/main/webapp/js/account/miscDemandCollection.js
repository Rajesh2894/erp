$(document).ready(function() {
	$('.has2Decimal').keyup(function() {
		var $this = $(this);
		var value = parseFloat(Math.round($this.val()))
		$this.val(value);
	});

	$('input:checkbox[class^="chkbox"]').each(function() {
		$('input:checkbox[class^="chkbox"]').prop('checked', true);
	});
	sum1();
});

function getCheckBoxValue(index) {
	if ($('#tableBillId1').is(":checked")) {
		$('input:checkbox[class^="chkbox' + index + '"]').each(function() {
			$('input:checkbox[class^="chkbox' + index + '"]').prop('checked', true);
		});
	} else {
		$('input:checkbox[class^="chkbox' + index + '"]').each(function() {
			$('input:checkbox[class^="chkbox' + index + '"]').prop('checked', false);
		});
	}
}

function amountPay() {
	$('#amountToPay').val($("#receiptAmount").val());
}

function searchBillNo(formUrl, actionParam) {
	var errorList = [];
	var refNumber = $("#refNumber").val();
	var billNo = $("#billNo").val();

	if (refNumber != "" || billNo != "") {
		var ccnregexPattern = /^[a-zA-Z @.]{3}\d{7}$/;
		var oldIdnregexPattern = /^[a-zA-Z]{5}\d{6}$/;
		var newIdnregexPattern = /^[a-zA-Z]{5}\d{7}$/;
		var billregexPattern = /^\d{4}[HES]{3}\d{8}$/;
		var oldbillregexPattern = /^\d{2}[HES]{3}\d{8}$/;

		if (refNumber != "") {
			if (!(oldIdnregexPattern.test(refNumber) || newIdnregexPattern.test(refNumber) || ccnregexPattern.test(refNumber))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.ccnidn"));
			}
		} else {
			if (!(billregexPattern.test(billNo) || oldbillregexPattern.test(billNo))) {
				errorList.push(getLocalMessage("receivable.demand.entry.validation.billNo"));
			}
		}
		if (errorList.length == 0) {
			
			var divName = '.content-page';
			var requestData = {
				"billNo" : billNo,
				"refNumber" : refNumber
			};
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
			$("#refNumber").val(refNumber);
			$("#billNo").val(billNo);
			
			if ($("#validationerror_errorslist").length == 0) {
				$('#refNumber').attr("readOnly", "true");
				$('#billNo').attr("readOnly", "true");
			}
		}
	} else {
		errorList.push(getLocalMessage("misc.search.error"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
}

function Proceed(element) {
	var errorList = [];
	
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, getLocalMessage('account.form.submit.success'), 'MiscDemandCollection.html?PrintSupplementryBill', 'saveform');
	}
}

function validateForm(errorList) {
	
	var receivedAmount = $("#receivedAmount").val();
	var mode = $("#payModeIn").val();
	var totalAmount = $("#totalAmount").val();
	if (receivedAmount == null || receivedAmount == "0" || receivedAmount == "") {
		errorList.push(getLocalMessage("misc.demand.collection.validation.select"));
	}

	if (mode == null || mode == "0" || mode == "") {
		errorList.push(getLocalMessage("misc.demand.collection.collection.mode"));
	}
	
	var collectionMode = $("#payModeIn option:selected").attr("code");
	var chkNo = $("#chqNo").val();
	var accNo = $("#acNo").val();
	var chkDate = $("input[name*='offlineDTO.bmChqDDDate']").val()
	var micrCode = $("#micrCode").val();
	var currDate =  new Date();
	var dateBefore3Month = currDate.setMonth(currDate.getMonth() - 3);
	
	
	if(collectionMode == 'D'){
		if (micrCode == null || micrCode == "0" || micrCode == "") {
			errorList.push(getLocalMessage("account.enter.micr.code"));
		}
		if (chkNo == null || chkNo == "0" || chkNo == "") {
			errorList.push(getLocalMessage("account.enter.dd.code"));
		}
		if (chkDate == null || chkDate == "0" || chkDate == "") {
			errorList.push(getLocalMessage("account.select.dd.date"));			
		}else if(process(chkDate)> new Date() || process(chkDate) < dateBefore3Month){
			errorList.push(getLocalMessage("account.ddDate.acceptDate.over.threeMonths"));
		}
	}else if(collectionMode == 'Q'){
		if (micrCode == null || micrCode == "0" || micrCode == "") {
			errorList.push(getLocalMessage("account.enter.micr.code"));
		}
		if (chkNo == null || chkNo == "0" || chkNo == "") {
			errorList.push(getLocalMessage("account.enter.chequeNo"));
		}
		if (chkDate == null || chkDate == "0" || chkDate == "") {
			errorList.push(getLocalMessage("account.select.chequeDate"));
			
		}else if(process(chkDate)> new Date() || process(chkDate) < dateBefore3Month){
			errorList.push(getLocalMessage("account.chequeDate.acceptDate.over.threeMonths"));
		}
		
	}else if(collectionMode == 'P'){
		if (micrCode == null || micrCode == "0" || micrCode == "") {
			errorList.push(getLocalMessage("account.enter.micr.code"));
		}
		if (chkNo == null || chkNo == "0" || chkNo == "") {
			errorList.push(getLocalMessage("account.enter.PO.No"));
		}
		if (chkDate == null || chkDate == "0" || chkDate == "") {
			errorList.push(getLocalMessage("account.select.PO.date"));
			
		}else if(process(chkDate)> new Date() || process(chkDate) < dateBefore3Month){
			errorList.push(getLocalMessage("account.PODate.acceptDate.over.threeMonths"));
		}
		
	}
	

	return errorList;
}

function sum1() {
	var i = 0;
	var gtotal = 0;
	$("#id_miscDemandTbl tbody tr").each(function(i) {

		var amnt = $("#billamnt" + i).text();
		if ($("#checkBillPay" + i).is(':checked')) {
			gtotal = parseFloat(Math.round(gtotal)) + parseFloat(Math.round(amnt));
		}
		if (gtotal == 'NaN') {
			gtotal = 0;
		}
	});
	
	$('#receivableAmount').val(gtotal);
	$('#totalAmount').val(gtotal);
	$('#receivedAmount').val(gtotal);
}

function process(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

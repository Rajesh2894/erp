$(document).ready(function() {
	prepareDateTag();
});

function saveManualEntry(element) {

	var errorList = [];
	var manualReceiptNo = $('#manualReceiptNo').val();
	var manualReceiptDate = $("#manualReceiptDate").val();
	var receiptAmount = $("#receiptAmount").val();
	if (manualReceiptNo == '' || manualReceiptNo == undefined) {
		errorList.push(getLocalMessage("trade.mandateReceiptNo"));
	}
	if (manualReceiptDate == '' || manualReceiptDate == undefined) {
		errorList.push(getLocalMessage("trade.mandateReceiptDate"));
	}
	if (receiptAmount == '' || receiptAmount == undefined) {
		errorList.push(getLocalMessage("trade.mandateReceiptAmt"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'P') {
			return saveOrUpdateForm(element, "Bill Payment done successfully!",
					'TradeManualReceiptEntry.html?PrintReport', 'saveform');
		}
	}
}

function backToManualReceipt() {
	var data = {};
	var URL = 'TradeManualReceiptEntry.html?backToMainPage';
	var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false);
	$("#dataDiv").html(ajaxResponse);
}

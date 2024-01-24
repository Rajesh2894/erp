$(document)
		.ready(
				function(e) {
					var payAmt = $("#payAmount").val();
					if(payAmt == "" || payAmt==0.0){
						$('#submitBT').prop('disabled', true);
					}
					// code update ISRAT-01-OCT
					var totalPayable = Number($("#totalPayable").val());
					$("#payAmount").keyup(
							function() {
								let payAmount = Number($("#payAmount").val());
								if (totalPayable == 0
										|| totalPayable == 'undefined'
										|| payAmount <= 0) {
									$('#submitBT').prop('disabled', true);
								} else {
									$('#submitBT').prop('disabled', false);
								}
							});
					

					$("#amountToPay").val($("#payAmount").val());

					$("#btnsearch")
							.click(
									function() {
										// T#37721
										var contractNo = $("#contractNo").val();
										let propertyContractNo = $(
												"#propertyNo").val();
										if (contractNo != 0 || propertyNo != 0) {

											let requestData = {
												"contNo" : contractNo,
												"propertyContractNo" : propertyContractNo
											}
											var ajaxResponse = __doAjaxRequest(
													'EstateContractBillPayment.html?serachBillPayment',
													'POST', requestData, false,
													'html');
											if (ajaxResponse != false) {
												$('.content-page').html(
														ajaxResponse);
												prepareTags();
											}
										} else {
											let errorList = [];
											errorList
													.push(getLocalMessage("rnl.bill.pay.search.record"));
											displayErrorsOnPage(errorList);
										}
										tableTextRoundOff();
									});
				});

function tableTextRoundOff() {
	var tableColText = $( "#second table tbody tr td p" );
	tableColText.each( function() {
		var value = $(this).text();
		value = Number(Math.round(value * 100) / 100).toFixed(2); //Round to 2 decimal places
		$(this).text(value);
	});
}

function receiptAmountRoundOff() {
	var payAmount = $('#payAmount').val();
	payAmount = (Math.round(payAmount * 100) / 100).toFixed(2); //Round to 2 decimal places
	$('#payAmount').val(payAmount);
}

function resetbillPayment(element) {
	$("#contractBillPayment").submit();
}

function saveData(element) {
	// check reciept amt and receivable amt equal or not
	let amt = Number($('#payAmount').val());
	if (amt <= 0) {
		showErrormsgboxTitle(getLocalMessage("rnl.receiptAmt.invalid"));
	}/*
		 * else if(Number($('#payAmount').val()) !=
		 * Number($("#totalPayable").val())){
		 * showErrormsgboxTitle(getLocalMessage("receipt amt can't be more than
		 * receivable amt ")); return false; }
		 */
	$("#errorDiv").hide();
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		return saveOrUpdateForm(element, "Bill Payment done successfully!",
				'EstateContractBillPayment.html?PrintReport', 'saveform');
	} else {
		return saveOrUpdateForm(element, "Bill Payment done successfully!",
				'EstateContractBillPayment.html', 'saveform');
	}
}

//T#86583
function getTaxCharges(element) {
	var errorList = [];
	let inputAmountId = Number($("#inputAmountId").val());
	if (inputAmountId == 0
			|| inputAmountId == 'undefined'
			|| inputAmountId <= 0) {
		$('#submitBT').prop('disabled', true);
	} else {
		//hit for get taxable charge
		var divName=".content-page"
		var URL = 'EstateContractBillPayment.html?getTaxCharges';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		
		var returnData = __doAjaxRequest(URL, 'Post', requestData, false,'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		prepareTags();
		tableTextRoundOff();
		receiptAmountRoundOff();
	}


}
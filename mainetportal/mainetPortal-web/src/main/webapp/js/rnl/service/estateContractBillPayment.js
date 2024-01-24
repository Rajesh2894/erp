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
									function() {debugger;
										// T#37721
										let errorList = [];
										var contractNo = $("#contractNo").val();
										var propertyContractNo = $("#propertyNo").val();
										
										if(contractNo == "0" && propertyContractNo == "0"){
											errorList
											.push(getLocalMessage("rnl.bill.pay.select.contract.property"));
										}
										if(contractNo != "0" && propertyContractNo == "0"){
											errorList
											.push(getLocalMessage("rnl.bill.pay.select.property.no"));
										}
										if (errorList.length == 0) {
											
											let requestData = {
													"contNo" : contractNo,
													"propertyContractNo" : propertyContractNo
												}
												var ajaxResponse = __doAjaxRequest(
														'EstateContractBillPayment.html?serachBillPayment',
														'POST', requestData, false,
														'html');
											$("#propertyNo").val(propertyContractNo);
												if (ajaxResponse != false) {
													$('.content-page').html(
															ajaxResponse);
													getPropertyDetail();
													/*prepareTags();*/
													
												}
											$('.error-div').hide();
										}else{
											displayErrorsOnPage(errorList);
										}
										
									});
				});

function resetbillPayment(element) {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("0").trigger("chosen:updated");
	$('.error-div').hide();
	$("#accordion_single_collapse").hide();
	//$("#contractBillPayment").submit();	
}

function saveData(element) {
	// check reciept amt and receivable amt equal or not
	let amt = Number($('#payAmount').val());
	if (amt <= 0) {
		showErrormsgboxTitle(getLocalMessage("rnl.receiptAmt.invalid"));
	}
	$("#errorDiv").hide();
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		return saveOrUpdateForm(element, "Bill Payment done successfully!",
				'EstateContractBillPayment.html?PrintReport', 'saveform');
	}
	else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val() == 'Y') {
		return saveOrUpdateForm(element, "Bill Payment done successfully!",
				'EstateContractBillPayment.html?redirectToPay', 'saveform');
	}else {
		return saveOrUpdateForm(element, "Bill Payment done successfully!",
				'EstateContractBillPayment.html', 'saveform');
	}
}

function receiptAmountRoundOff() {
	var payAmount = $('#payAmount').val();
	var inputAmount = $("#inputAmountId").val()
	payAmount = (Math.round(payAmount * 100) / 100).toFixed(2); // Round to 2
	inputAmount = (Math.round(inputAmount * 100) / 100).toFixed(2); 														// decimal
	$('#inputAmountId').val(inputAmount);													// places
	$('#payAmount').val(payAmount);
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
		//prepareTags();
		$('.error-div').hide();
		receiptAmountRoundOff();
	}


}

function getPropertyDetail(element){debugger;
	var optionsAsString = '';
	var contractNo = $("#contractNo").val();
	$('#propertyNo').html('');
	if (contractNo != 0 ) {
		let requestData = {
				"contNo" : contractNo,
				
			}
			var ajaxResponse = __doAjaxRequest(
					'EstateContractBillPayment.html?getPorpertyDetail',
					'POST', requestData, false,
					'json');
		
		for (var i = 0; i < ajaxResponse.length; i++) {
			optionsAsString += "<option value='" + ajaxResponse[i][0]+ "'>" +ajaxResponse[i][3]+' - ' + ajaxResponse[i][4] +"</option>";
		}
			if (ajaxResponse != false) {
				
				$("#propertyNo").append(optionsAsString);
				
				
			}
			$("#propertyNo").trigger("chosen:updated");
	}
}
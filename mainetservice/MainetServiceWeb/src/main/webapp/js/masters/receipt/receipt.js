
$(document).ready(
		function() {
			
			if ($("#form_mode").val() == 'view') {
				$('.error-div').hide();
				$('#editOriew').find('*').attr('disabled', 'disabled');
				$('#editOriew').find('*').addClass("disablefield");
				$('#manual_ReceiptNo').attr('disabled', 'disabled');
				$('#rmDatetemp').attr('disabled', 'disabled');
				$('#rm_Receivedfrom').attr('disabled', 'disabled');
				$('#mobile_Number').attr('disabled', 'disabled');
				$('#email_Id').attr('disabled', 'disabled');
				$('#vm_VendorId').attr('disabled', 'disabled');
				$('#VmVendorIdDesc').attr('disabled', 'disabled');
				$('#rdamount').attr('disabled', 'disabled');
				$('#cpdFeemode').attr('disabled', 'disabled');
				$('#bankId').attr('disabled', 'disabled');
				$('#tranRefNumber').attr('disabled', 'disabled');
				$('#rdchequedddatetemp').attr('disabled', 'disabled');
				$('#rmNarration').attr('disabled', 'disabled');
				$('#baAccountId').attr('disabled', 'disabled');
				$('#tranRefNumber1').attr('disabled', 'disabled');
				$('#tranRefDate1').attr('disabled', 'disabled');
				$('#isdeleted').attr('disabled', 'disabled');
				$('#isdeleted').addClass("disablefield");

				$('label.control-label').each(function() {
					$(this).removeClass('required-control');

				});

				CashModeValidationView();
			}
			var response = __doAjaxRequest('DepositReceiptEntry.html?SLIDate',
					'GET', {}, false, 'json');
			var disableBeforeDate = new Date(response[0], response[1],
					response[2]);
			var date = new Date();
			var today = new Date(date.getFullYear(), date.getMonth(), date
					.getDate());
			$("#rmDatetemp").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				minDate : disableBeforeDate,
				maxDate : today,
				changeYear : true
			});
			$("#transactionDateId").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : today
			});
			$("#rmDatetemp").datepicker('setDate', new Date());

			$("#rmDatetemp").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			$('#datatables').dataTable();

			$("#receiptDatatables").dataTable(
					{
						"oLanguage" : {
							"sSearch" : ""
						},
						"aLengthMenu" : [ [ 5, 10, 15, -1 ],
								[ 5, 10, 15, "All" ] ],
						"iDisplayLength" : 5,
						"bInfo" : true,
						"lengthChange" : true,
						"order": [[ 0, "desc" ]]
					});	
			
			$("#rmDatetemp").bind("keyup change", function(e) {				
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			$("#transactionDateId").bind("keyup change", function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});			
		});


/* function getReceiptCategoryType(obj){
	
	var errorList = [];
	var receiptCategoryType = $("#receiptCategoryId option:selected").attr("code");
		
	var receiptCategoryId = $('#receiptCategoryId').val();
	if(receiptCategoryId != null && receiptCategoryId != ""){

	if(receiptCategoryType=="M"){
		$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
	}else{
		$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
	}
	
	if (errorList.length == 0) {		
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var url = "DepositReceiptEntry.html?getReceiptAccountHeadData";

		var response = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).html(response);		
		var receiptCategoryType = $("#receiptCategoryId option:selected").attr("code");
		if(receiptCategoryType=="M"){
			$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
		}else{
			$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
		}
      }
    }	
 }
*/
function createData() {
	
	$("#create_btn").attr("disabled", true);
	var divName = formDivName;
	var url = "DepositReceiptEntry.html?form";
	data = {};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	CashModeValidationView();

	var depositSlipType1 = $("#receiptCategoryId option:selected").attr("code");
	if (depositSlipType1 == "M") {
		$('#vm_VendorId').attr("disabled", true).trigger('chosen:updated');
		var transactionDateDup = $('#transactionDateDup').val();
		if (transactionDateDup != null && transactionDateDup != "") {
			$('#transactionDateId').val(transactionDateDup);
		}
	} else {
		$('#vm_VendorId').attr("disabled", false).trigger('chosen:updated');
		var transactionDateDup = $('#transactionDateDup').val();
		if (transactionDateDup != null && transactionDateDup != "") {
			$('#transactionDateId').val(transactionDateDup);
		}
	}
	prepareTags();
}

function searchReceiptData() {
	
	var errorList = [];
	errorList = validateReceiptSearchForm(errorList);
	if (errorList.length == 0) {

		var rmRcptno = $("#rmRcptno").val();
		var rmAmount = $("#rdamount").val();
		var rm_Receivedfrom = $("#rm_Receivedfrom").val();
		var rmDate = $("#rmDatetemp").val();

		if(rmDate != null || rmDate != 0){
			errorList = validatedate(errorList,'rmDatetemp');
			if(errorList.length >0){
				displayErrorsOnPage(errorList);
				return false;
			}
		}			
		var requestData = {
			"rmAmount" : rmAmount,
			"rmRcptno" : rmRcptno,
			"rm_Receivedfrom" : rm_Receivedfrom,
			"rmDate" : rmDate
		};
		var table = $('#receiptDatatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'DepositReceiptEntry.html?getAllReceiptData', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("collection.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			var result = [];
			$
					.each(
							prePopulate,
							function(index) {
								var obj = prePopulate[index];
								result
										.push([
												obj.rmReceiptNo,
												obj.rmDatetemp,
												obj.rmReceivedfrom,
												obj.rmAmount,
												'<td class="text-center" align="center">'
														+ '<button type="button" class="btn btn-blue-2 btn-sm  margin-right-10" onclick="getActionForReceipt(\''
														+ obj.rmRcptid
														+ '\',\'V'
														+ '\')"  title="view"><i class="fa fa-eye" align="center"></i></button>'
														+ '<button type="button" class="btn btn-blue-2 btn-sm  margin-right-10" onclick="printReceipt(\''
														+ obj.rmRcptid
														+ '\',\'P'
														+ '\')"  title="Print"><i class="fa fa-print" align="center"></i></button>'
														+ '</td>']);

							});
			table.rows.add(result);
			table.draw();
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateReceiptSearchForm(errorList) {
	var receiptNo = $("#rmRcptno").val();
	var receiptDate = $("#rmDatetemp").val();
	var receiptAmount = $("#rdamount").val();
	var payeeName = $("#rm_Receivedfrom").val();
	if (receiptNo == "" && receiptDate == "" && receiptAmount == ""
			&& payeeName == "" && payeeName == "") {
		errorList
				.push(getLocalMessage('collection.validation.searchcriteria'));
	}
	return errorList;
}

function getActionForReceipt(rmRcptId,saveMode) {
	
	var divName = formDivName;
	var url = "DepositReceiptEntry.html?actionForView";
	var requestData = "rmRcptid=" + rmRcptId + "&saveMode=" + saveMode;
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);	
	CashModeValidationView();
	prepareTags();
}


function saveDataReceipt(element) {
	
	var myerrorList = [];
	var receiptDate = $.trim($('#transactionDateId').val());
	if (receiptDate == "" || receiptDate == undefined) {
		myerrorList.push(getLocalMessage('collection.validation.receiptdate'));
	}else{
		myerrorList = validatedate(myerrorList,'receiptDate');
	}	
	var receiptCategoryId = $.trim($("#receiptCategoryId").val());
	if (receiptCategoryId == 0 || receiptCategoryId == "") {
		myerrorList.push(getLocalMessage('collection.validation.receiptcategory'));
	}

	if (receiptCategoryId != null && receiptCategoryId != "") {
		var depositSlipType = $("#receiptCategoryId option:selected").attr(
				"code");
		if (depositSlipType == "M") {

		} else {
			var vm_VendorId = $.trim($("#vm_VendorId").val());
			if (vm_VendorId == 0 || vm_VendorId == "") {
				myerrorList
						.push(getLocalMessage('collection.validation.receivedfrom'));
			}
		}
	}
	var apmApplicationId = $.trim($("#apmApplicationId").val());
	if (apmApplicationId == 0 || apmApplicationId == "") {
		myerrorList
				.push(getLocalMessage('collection.validation.referenceId'));
	}

	var rmreceivedfrom = $.trim($("#rm_Receivedfrom").val());
	if (rmreceivedfrom == 0 || rmreceivedfrom == "")
		myerrorList.push(getLocalMessage('collection.validation.payeename'));

	var rmnarration = $.trim($("#rmNarration").val());
	if (rmnarration == 0 || rmnarration == "")
		myerrorList.push(getLocalMessage('collection.validation.narration'));

	var emailId = $.trim($("#email_Id").val());
	if (emailId != "") {
		var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
		var valid = emailRegex.test(emailId);
		if (!valid) {
			myerrorList
					.push(getLocalMessage('collection.validation.invalidemail'));
		}
	}

	$('.accountClass').each(function(i) {
		for (var m = 0; m < i; m++) {
			if (($('#budgetCode' + m).val() == $('#budgetCode' + i).val())) {
				myerrorList.push("collection.validation.receipthead.duplicate");
			}
		}
	});

	$('.accountClass').each(function(i) {
		var budgetCode = $.trim($("#budgetCode" + i).val());

		var rffeeamount = $.trim($("#rfFeeamount" + i).val());
		var level = i + 1;

		if (budgetCode == 0 || budgetCode == "") {
			myerrorList.push(getLocalMessage("collection.validation.receipthead"));
		}

		if (rffeeamount == 0 || rffeeamount == "") {
			myerrorList.push(getLocalMessage("collection.validation.receiptamount"));
		}

	});
	var tbl = document.getElementById("receiptAccountHeadsTable");
	var key;
	var key1;
	var rowCount = $('#receiptAccountHeadsTable tr').length;

	var cpdfeemode = $.trim($("#cpdFeemode").val());
	if (cpdfeemode == 0 || cpdfeemode == "")
		myerrorList.push(getLocalMessage('collection.validation.receiptmode'));

	var option = $("#cpdFeemode option:selected").attr("code");
	if (option == 'Q' || option == 'D' || option == 'F' || option == 'P') {

		var cbbankid = $.trim($("#bankId").val());
		if (cbbankid == 0 || cbbankid == "")
			myerrorList.push(getLocalMessage('collection.validation.bankname'));

		var rdchequeddnotemp = $.trim($("#rdchequeddno").val());
		if (rdchequeddnotemp == 0 || rdchequeddnotemp == "")
			myerrorList.push(getLocalMessage('collection.validation.instrumentnumber'));

		var rdchequedddate = $.trim($("#rdchequedddatetemp").val());
		
		if (rdchequedddate == 0 || rdchequedddate == "")
			myerrorList.push(getLocalMessage('collection.validation.instrumentdate'));

	}

	if (option == 'RT' || option == 'N' || option == 'B') {
		var baaccountid = $.trim($("#baAccountId").val());
		if (baaccountid == 0 || baaccountid == "")
			myerrorList.push(getLocalMessage('collection.validation.bankname'));

		var tranrefNumber1 = $.trim($("#tranRefNumber1").val());
		if (tranrefNumber1 == 0 || tranrefNumber1 == "")
			myerrorList.push(getLocalMessage('collection.validation.instrumentnumber'));

		var tranrefDate1 = $.trim($("#tranRefDate1").val());
		if (tranrefDate1 == 0 || tranrefDate1 == "")
			myerrorList.push(getLocalMessage('collection.validation.instrumentdate'));
	}

	if (cpdfeemode == 0 || cpdfeemode == "") {
		var tranrefNumber1 = $.trim($("#tranRefNumber1").val());
		if (tranrefNumber1 == 0 || tranrefNumber1 == "") {
			myerrorList.push(getLocalMessage('collection.validation.instrumentnumber'));
		}
	}

	var feeModeStatus = $("#feeModeStatus").val();
	if (feeModeStatus == "N") {
		myerrorList
				.push(getLocalMessage("collection.validation.budgetheadnotavailable"));
	}

	var templateExistsFlag = $("#templateExistsFlag").val();
	if (templateExistsFlag == "N") {
		myerrorList
				.push(getLocalMessage('collection.validation.receiptvouchertemplate'));
	}

	var totalAmount = $("#rdamount").val();
	var sumOfDedctionAmt = 0;
	var rowCount = $('#receiptAccountHeadsTable tr').length;

	for (var i = 0; i < rowCount - 1; i++) {
		sumOfDedctionAmt += parseFloat($('#rfFeeamount' + i).val());
	}

	if ((sumOfDedctionAmt != null) && (sumOfDedctionAmt != "")
			&& (!isNaN(sumOfDedctionAmt)) && (sumOfDedctionAmt != undefined)) {
		if (parseFloat(sumOfDedctionAmt) != parseFloat(totalAmount)) {
			myerrorList
					.push(getLocalMessage('collection.validation.Discrepency'));
		}
	}

	if (myerrorList.length > 0) {
		displayErrorsOnPage(myerrorList);
		$("#errorDiv").show();
		return false;
	}
	else {		
		let rmRcptno = $('#rmRcptno').val();
		var dpDeptId = $('#dpDeptId').val();
		var receiptdate = $.trim($('#transactionDateId').val());
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest("DepositReceiptEntry.html?create", 'POST', requestData, false, 'json');
		if (response.rmRcptno != null) {
			showConfirmBoxReceipt(getLocalMessage('collection.sucess.receiptno')
					+ ' ' +response.rmRcptno + ' '
					+ getLocalMessage('collection.sucess'),response.rmRcptno,dpDeptId,receiptdate);
		} else {
			myerrorList
					.push(getLocalMessage('collection.validation.Discrepency'));
			displayErrorsOnPage(myerrorList);
			$("#errorDiv").show();
			return false;
		}
	}
}

function showConfirmBoxReceipt(successMsg,rmRcptno,dpDeptId,receiptdate) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedReceipt('+rmRcptno+','+dpDeptId+',\''+receiptdate+'\')"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}
//#134426
function proceedReceipt(rmRcptno,dpDeptId,receiptdate) {

	var requestData = 'rmRcptno=' + rmRcptno +'&dpDeptId=' +dpDeptId +'&receiptdate=' +receiptdate;
	var url = "DepositReceiptEntry.html?reciptPrintForm"
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$(errMsgDiv).show('false');
	   $.fancybox.close();
	   var title = 'Receipt Print';
	   var printWindow = window.open('', '_blank');
		window.location.href='AdminHome.html';
		printWindow.document.write('<html><head><title>' + title + '</title>');
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();
	
}

/*function proceedReceipt() {
	$.fancybox.close();
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DepositReceiptEntry.html');
	$("#postMethodForm").submit();
}*/

function setVendorName(obj) {
	
	$('.error-div').hide();
	var errorList = [];

	$('#mobile_Number').val("");
	$('#email_Id').val("");

	var vmVendrnameId = $("#vm_VendorId option:selected").val();
	var vmVendrname = $("#vm_VendorId option:selected").text();
	var venderName = vmVendrname.split("-");

	if (vmVendrnameId != null && vmVendrnameId != undefined
			&& vmVendrnameId != "" && errorList.length == 0) {
		$("#rm_Receivedfrom").val(venderName[1]).prop("readonly", true);
	} else {
		$("#rm_Receivedfrom").val("").prop("readonly", false);
	}

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "DepositReceiptEntry.html?getVendorPhoneNoAndEmailId";
		var returnRevData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnRevData, function(index, value) {

			$('#mobile_Number').val(returnRevData[0]);
			$('#email_Id').val(returnRevData[1]);

			$("#mobile_Number").prop("readonly", true);
			$("#email_Id").prop("readonly", true);

		});
}

function enableMobileAndEmail() {
	$("#mobile_Number").prop("readonly", false);
	$("#email_Id").prop("readonly", false);
}

function CashModeValidation() {
	
	var option = $("#cpdFeemode option:selected").attr("code");
	var cpdFeemode = $("#cpdFeemode option:selected").val();

	if (option == 'C' || option == 'POS') {

		$('#tablePayModeHeading_cheqDD').hide();
		$('#tablePayModeHeading_cash').show();
		$('#tablePayModecbBankid').hide();
		$('#rdchequeddnodata').hide();
		$('#rdchequedddatetempdata').hide();
		$('#tablePayModeHeading_nftwebrtgs').hide();
		$('#tableRecModecbBankid').hide();
		$('#tranRefNumber').hide();
		$('#tranRefDate').hide();
		$('#bankAccountid').hide();
		$('#baAccountId').val('').trigger('chosen:updated');


	} else if (option == "Q" || option == "D" || option == "F" || option == "P") {
		$('#tablePayModeHeading_cheqDD').show();
		$('#tablePayModeHeading_cash').hide();
		$('#tablePayModecbBankid').show();
		$('#rdchequeddnodata').show();
		$('#rdchequedddatetempdata').show();
		$('#tablePayModeHeading_nftwebrtgs').hide();
		$('#tableRecModecbBankid').hide();
		$('#tranRefNumber').hide();
		$('#tranRefDate').hide();
		$('#baAccountId').val('').trigger('chosen:updated');
		$("#tranRefDate1").datepicker('setDate', new Date());
		$("#rdchequedddatetemp").datepicker('setDate', new Date());
		$('#bankAccountid').show();
	} else {
		$('#tablePayModeHeading_cheqDD').hide();
		$('#tablePayModeHeading_cash').hide();
		$('#tablePayModecbBankid').hide();
		$('#rdchequeddnodata').hide();
		$('#tablePayModeHeading_nftwebrtgs').show();
		$('#rdchequedddatetempdata').hide();
		$('#tableRecModecbBankid').show();
		$('#tranRefNumber').show();
		$('#tranRefDate').show();
		$('#baAccountId').val('').trigger('chosen:updated');
		$("#tranRefDate1").datepicker('setDate', new Date());
		$("#rdchequedddatetemp").datepicker('setDate', new Date());
		$('#bankAccountid').hide();
	}

	var postData = "cpdFeemode=" + cpdFeemode;
	var url = "DepositReceiptEntry.html?checkBudgetCodeForFeeMode"
	var result = __doAjaxRequest(url, 'POST', postData, false, 'json');
	$("#feeModeStatus").val(result);

	copyReceiptDate();
}

function CashModeValidationView() {
	
	var option = $("#cpdFeemodeCode").val();

	if (option == 'C' || option == 'POS') {

		$('#rdchequeddno').val('');
		$('#rdchequedddatetemp').val('');
		$('#tranRefNumber1').val('');
		$('#tranRefDate1').val('');
		$('#tablePayModeHeading_cheqDD').hide();
		$('#tablePayModeHeading_cash').show();
		$('#tablePayModecbBankid').hide();
		$('#rdchequeddnodata').hide();
		$('#rdchequedddatetempdata').hide();
		$('#tablePayModeHeading_nftwebrtgs').hide();
		$('#tableRecModecbBankid').hide();
		$('#tranRefNumber').hide();
		$('#tranRefDate').hide();
		$('#bankAccountid').hide();
		$('#rdChequedddatetemp').hide();
	
	} else if (option == "Q" || option == "D" || option == "F" || option == "P") {
		$('#tablePayModeHeading_cheqDD').show();
		$('#tablePayModeHeading_cash').hide();
		$('#tablePayModecbBankid').show();
		$('#rdchequeddnodata').show();
		$('#rdchequedddatetempdata').show();
		$('#tablePayModeHeading_nftwebrtgs').hide();
		$('#tableRecModecbBankid').hide();
		$('#tranRefNumber').hide();
		$('#tranRefDate').hide();
		$('#bankAccountid').show();
	} else {

		$('#tablePayModeHeading_cheqDD').hide();
		$('#tablePayModeHeading_cash').hide();
		$('#tablePayModecbBankid').hide();
		$('#rdchequeddnodata').hide();
		$('#tablePayModeHeading_nftwebrtgs').show();
		$('#rdchequedddatetempdata').hide();
		$('#tableRecModecbBankid').show();
		$('#tranRefNumber').show();
		$('#tranRefDate').show();
		$('#rdChequedddatetemp').hide();
		$('#bankAccountid').hide();
	}

}

function totalReceiptamount() {
	var amount = 0;

	var rowCount = $('#receiptAccountHeadsTable tr').length;

	for (var m = 0; m <= rowCount - 1; m++) {

		var n = parseFloat(parseFloat($("#rfFeeamount" + m).val()));
		if (isNaN(n)) {
			return n = 0;
		}
		amount += n;

		var result = amount.toFixed(2);
		$("#rdamount").val(result);
		$("#rmAmount").val(result);

	}

}

function getAmountFormat() {

	var rowCount = $('#receiptAccountHeadsTable tr').length;

	for (var m = 0; m <= rowCount - 1; m++) {

		var transferAmount = $('#rfFeeamount' + m).val();

		if (transferAmount != undefined && !isNaN(transferAmount)
				&& transferAmount != null && transferAmount != '') {

			var actualAmt = transferAmount.toString().split(".")[0];
			var decimalAmt = transferAmount.toString().split(".")[1];

			var decimalPart = ".00";
			if (decimalAmt == null || decimalAmt == undefined) {
				$('#rfFeeamount' + m).val(actualAmt + decimalPart);
			} else {
				if (decimalAmt.length <= 0) {
					decimalAmt += "00";
					$('#rfFeeamount' + m).val(actualAmt + (".") + decimalAmt);
				} else if (decimalAmt.length <= 1) {
					decimalAmt += "0";
					$('#rfFeeamount' + m).val(actualAmt + (".") + decimalAmt);
				} else {
					if (decimalAmt.length <= 2) {
						$('#rfFeeamount' + m).val(
								actualAmt + (".") + decimalAmt);
					}
				}
			}
		}
	}

}

function dynamicTotalReceiptAmount(rowCount) {
	var amount = 0;

	for (var m = 0; m <= rowCount - 1; m++) {

		var n = parseFloat(parseFloat($("#rfFeeamount" + m).val()));
		if (isNaN(n)) {
			n = 0;
		}
		amount += n;

		$("#rdamount").val(parseFloat(amount.toFixed(2)));
		$("#rmAmount").val(parseFloat(amount.toFixed(2)));

	}
}

function resetDate() {

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		minDate : $("#tranRefDate1").val(),
		maxDate : '-0d',
		changeYear : true
	});
	$("#tranRefDate1").datepicker('setDate', new Date());
	$("#rdchequedddatetemp").datepicker('setDate', new Date());

}

function validateChequeDate() {

	var errorList = [];
	

	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#rdchequedddatetemp").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);

	if ((transactionDate != "" && transactionDate != null)
			&& (instrumentDate != "" && instrumentDate != null)) {
		if (instrumentDate > transactionDate) {
			errorList
					.push(getLocalMessage("collection.validation.chequedate.to.receiptdate"));
			$("#rdchequedddatetemp").val("");
		}
	}
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
	// errorList = validatePaymentForm(errorList);
	if (errorList.length == 0) {
		
		$('#errorDivId').hide();
		var postData = __serializeForm("#tbServiceReceiptMas");
		
		var url = "DepositReceiptEntry.html?validateChequeDate";
		var returnData = __doAjaxRequest(url, 'post', postData, false);
		if (returnData) {
			errorList
					.push("collection.validation.chequedate.tothree.receiptdate");
			$("#rdchequedddatetemp").val("");
			if (errorList.length > 0) {
				var errMsg = '<ul>';
				$
						.each(
								errorList,
								function(index) {
									errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
											+ errorList[index] + '</li>';
								});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
				return false;
			}
		}
	}
}

function resetReceiptModeDetails(obj) {

	$('#cpdFeemode').val("");
}

function getReceiptCategoryType(obj) {
	
	$('.error-div').hide();
	var errorList = [];
	var receiptCategoryType = $("#receiptCategoryId option:selected").attr("code");
	
	var receiptCategoryId = $('#receiptCategoryId').val();
	if(receiptCategoryId != null && receiptCategoryId != ""){

	if(receiptCategoryType=="M"){		
		$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
		$('#vm_VendorId').val('');	
	}else{
		$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
	}
	
		if (errorList.length == 0) {

			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "DepositReceiptEntry.html?getReceiptAccountHeadData";

			var response = __doAjaxRequest(
					'DepositReceiptEntry.html?getReceiptAccountHeadData',
					'POST', requestData, false, 'json');
			$('#budgetCode0 option').remove();
			$.each(response, function(key, value) {
				$('#budgetCode0').append(
						$("<option></option>").attr("value", key).attr("code",
								value).text(value));
			});

			$("#budgetCode0").val(response).trigger('chosen:updated');
			
			var receiptCategoryType = $("#receiptCategoryId option:selected").attr("code");
			if(receiptCategoryType=="M"){				
				$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
				$("#rm_Receivedfrom").val("").prop("readonly", false);
			}else{
				$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
			}

		}
	}
};

function resetReceiptCategoryForVendor(obj) {
	
	$('.error-div').hide();
	var errorList = [];
	var receiptCategoryId = $('#receiptCategoryId').val();
	if (receiptCategoryId != null && receiptCategoryId != "") {
		var depositSlipType = $("#receiptCategoryId option:selected").attr(
				"code");
		if (depositSlipType == "M") {
			$('#vm_VendorId').attr("disabled", true).trigger('chosen:updated');
			
		} else {
			$('#vm_VendorId').attr("disabled", false).trigger('chosen:updated');
		}
	}
}
// #134426
function printReceipt(rmRcptid,mode) {
	var URL = 'DepositReceiptEntry.html?printReceipt';
	var requestdata = {
		"rmRcptid" : rmRcptid			
	};
	var returnData = __doAjaxRequest(URL, 'POST', requestdata, false);

	var title = 'Receipt Print';
	var printWindow = window.open('', '_blank');
	window.location.href='AdminHome.html';
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}
//to generate dynamic table
$("#receiptAccountHeadsTable")
		.on(
				"click",
				'.addDedButton',
				function(e) {
					debugger;

					var errorList = [];
					$('.accountClass').each(function(i) {
										var budgetCode = $.trim($("#budgetCode" + i).val());
										if (budgetCode == 0 || budgetCode == "" || budgetCode == null){
											errorList.push(getLocalMessage("collection.validation.receipthead"));
										}
										
										
										var deductionAmt = $.trim($("#rfFeeamount" + i).val());
										if (deductionAmt == 0 || deductionAmt == "" || deductionAmt == null){
											errorList.push(getLocalMessage("collection.validation.receiptamount"));
										}
									});
					if (errorList.length > 0) {

						var errMsg = '<ul>';
						$
								.each(
										errorList,
										function(index) {
											errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
													+ errorList[index]
													+ '</li>';
										});

						errMsg += '</ul>';

						$('#errorId').html(errMsg);
						$('#errorDivId').show();
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');
						return false;
					}

					var content = $(this).closest('#receiptAccountHeadsTable tr').clone();
					$(this).closest("#receiptAccountHeadsTable tbody").last().append(content);

					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find('div.chosen-container').remove();
					content.find("select:eq(0)").chosen().trigger(
							"chosen:updated");
					reOrderDedTableIdSequence();
					e.preventDefault();

				});

// to delete row
$("#receiptAccountHeadsTable").on("click", '.delDedButton', function(e) {
	debugger;
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	var mode = $("#form_mode").val();
	if(mode =="edit"){	
		if (rowCount <= 1) {
			return false;
		}
	}else{
		if (rowCount <= 2) {
			return false;
		}
	}

	$(this).closest('#receiptAccountHeadsTable tr').remove();
/*
	var deletedDedId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
	 if(deletedDedId != ''){
		 removeDedIdArray.push(deletedDedId);
		 $("#deletedDedId").val(removeDedIdArray);
	 }*/
	 
	var dedAmountTotal = 0;
	var rowCount = $('#receiptAccountHeadsTable tr').length;
	for (var i = 0; i < rowCount - 1; i++) {
		dedAmountTotal += parseFloat($("#rfFeeamount" + i).val());
		if (isNaN(dedAmountTotal)) {
			dedAmountTotal = "";
		}
	}
	getAmountFormat();
	//getNetPayable();
	totalReceiptamount();
	reOrderDedTableIdSequence();
	e.preventDefault();
});

function reOrderDedTableIdSequence() {
	debugger;
	$('.accountClass')
			.each(
					function(i) {
						debugger;
						/*
						 * $(this).find("select:eq(2)").attr("id","dedFundId"+i);
						 * $(this).find("select:eq(3)").attr("id","dedFunctionId"+i);
						 * $(this).find("select:eq(4)").attr("id","dedFieldId"+i);
						 */
						$(this).find("select:eq(0)").attr("id",
								"budgetCode" + i);
						
								/*"deductionRate" + i);*/
						$(this).find("input:text:eq(1)").attr("id",
								"rfFeeamount" + i);
						$(this).find("select:eq(0)").attr("onchange","validateDedAccountHead("+i+")");
						/*$(this).find("select:eq(0)").attr("onchange","validateDedAccountHead("+i+")");
						$(this).find("select:eq(1)").attr("onchange","validateDedExpenditureAccountHead("+i+")");
						$(this).find("input:text:eq(1)").attr("onblur",
								"calculatePercentage(" + i + ")");
						$(this).find("input:text:eq(1)").attr("onblur",
								"calculateDeductionAmt(" + i + ")");*/
						/*
						 * $(this).find("select:eq(2)").attr("name","deductionDetailList["+i+"].dedFundId");
						 * $(this).find("select:eq(3)").attr("name","deductionDetailList["+i+"].dedFunctionId");
						 * $(this).find("select:eq(4)").attr("name","deductionDetailList["+i+"].dedFieldId");
						 */
						$(this).find("select:eq(0)").attr("name",
								"receiptFeeDetail[" + i + "].sacHeadId");
						//$(this).find("select:eq(1)").attr("name","deductionDetailList["+i+"].bchId");
						/*$(this).find("input:text:eq(1)").attr("name",
								"deductionDetailList[" + i + "].deductionRate");*/
						$(this).find("input:text:eq(1)").attr(
								"name",
								"receiptFeeDetail[" + i
										+ "].rfFeeamount");

						$(this).find(".delDedButton").attr("id",
								"delDedButton" + i);
						$(this).find(".addDedButton").attr("id",
								"addDedButton" + i);

						$(this).closest("tr").attr("id", "deduction" + (i));
					});
}

function validateDedAccountHead(cnt) {
	debugger;
	$('.error-div').hide();
	var errorList = [];
	
	$('.accountClass')
			.each(
					function(i) {
						for (var m = 0; m < i; m++) {

							if (($('#budgetCode' + m).val() == $(
									'#budgetCode' + i).val())) {
								errorList
										.push("Reciept heads cannot be same,please select another Account Head");
								// var prRevBudgetCode =
								// $("#expenditureBudgetCode"+i).val("");
								// $("#prCollected"+i).prop("disabled",true);
								$('#budgetCode'+cnt).val("");
								$('#budgetCode'+cnt).val('').trigger('chosen:updated');
							}
						}
					});
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
		}
	}

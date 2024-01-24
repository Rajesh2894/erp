$(function() {
	$("#tdspaymentEntryGrid")
			.jqGrid(
					{
						url : "TdsPaymentEntry.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								'',
								getLocalMessage('accounts.payment.no'),
								getLocalMessage('advance.management.master.paymentdate'),
								getLocalMessage('account.tenderentrydetails.VendorEntry'),
								getLocalMessage('advance.management.master.paymentamount'),
								getLocalMessage('bill.action') ],
						colModel : [
								{
									name : "id",
									width : 20,
									align : 'center',
									sortable : true,
									searchoptions : {
										"sopt" : [ "bw", "eq" ]
									},
									hidden : true
								},
								{
									name : "paymentNo",
									width : 55,
									align : 'center',
									sortable : false,
									searchoptions : {
										"sopt" : [ "bw", "eq", "ne", "ew",
												"cn", "lt", "gt" ]
									}
								},
								{
									name : "paymentEntryDate",
									align : 'center',
									width : 75,
									sortable : false,
									searchoptions : {
										"sopt" : [ "eq" ]
									}
								},
								{
									name : "vendorDesc",
									align : 'center',
									width : 75,
									sortable : false,
									searchoptions : {
										"sopt" : [ "eq" ]
									}
								},
								{
									name : "billAmountStr",
									align : 'right',
									width : 75,
									sortable : false,
									searchoptions : {
										"sopt" : [ "bw", "eq", "ne", "ew",
												"cn", "lt", "gt" ]
									}
								}, {
									name : 'id',
									index : 'id',
									width : 40,
									align : 'center !important',
									formatter : addLink,
									search : false
								}, ],
						pager : "#pagered",
						emptyrecords: getLocalMessage("jggrid.empty.records.text"),
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "dsgid",
						sortorder : "desc",
						height : 'auto',
						viewrecords : true,
						gridview : true,
						loadonce : true,
						jsonReader : {
							root : "rows",
							page : "page",
							total : "total",
							records : "records",
							repeatitems : false,
						},
						autoencode : true,
						caption : getLocalMessage("tds.payment.entry")
					});
	jQuery("#tdspaymentEntryGrid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		search : true,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

$(function() {

	$("#baAccountidPay").chosen();
	$('.required-control').next().children().addClass('mandColorClass').attr(
			"required", true);

	var templateExistFlag = $("#templateExistFlag").val();
	var errorList = [];
	if (templateExistFlag == "N") {
		errorList
				.push(getLocalMessage('account.paymentVoucher.not.defined'));
		displayErrorsPage(errorList);
	}
	$(document).on('click', '.createData', function() {
		var url = "TdsPaymentEntry.html?formForCreate";
		var requestData = "MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();
		return false;

	});
});

/**
 * ************************************************TDS PAYMENTENTRY
 * SEARCH**************************************
 */

function searchTdsPayData() {
	
	var errorsList = [];
	errorsList = validatePaymentSearchForm(errorsList);
	displayErrorsPage(errorsList);

	if (errorsList <= 0) {
		var url = "TdsPaymentEntry.html?searchTdsPayData";
		var returnData = {
			"paymentEntryDate" : $("#paymentEntryDate").val(),
			"paymentAmount" : $("#paymentAmount").val(),
			"vendorId" : $("#vmVendorname option:selected").attr("value"),
			"budgetCodeId" : $("#budgetCodeId option:selected").attr("value"),
			"paymentNo" : $("#paymentNo").val(),
			"baAccountid" : $("#baAccountid option:selected").attr("value"),
		};
		var result = __doAjaxRequest(url, 'POST', returnData, false, 'json');
		if (result != null && result != '') {
			$("#tdspaymentEntryGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			$('#errorDivId').hide();
		} else {
			var errorList = [];
			errorList
					.push(getLocalMessage("account.norecord.criteria"));
			if (errorList.length > 0) {

				displayErrorsPage(errorList);
			}
			$("#tdspaymentEntryGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
}

function validatePaymentSearchForm(errorList) {
	
	var paymentEntryDate = $("#paymentEntryDate").val();
	var paymentAmount = $("#paymentAmount").val();
	var vmVendorname = $("#vmVendorname").val();
	var budgetCodeId = $("#budgetCodeId").val();
	var paymentNo = $("#paymentNo").val();
	var baAccountid = $("#baAccountid").val();

	if (paymentEntryDate == "" && paymentAmount == "" && vmVendorname == ""
			&& budgetCodeId == "" && paymentNo == "" && baAccountid == "") {

		errorList
				.push(getLocalMessage('account.select.criteria'));
	}
	if (paymentEntryDate != null) {
		errorList = validatedate(errorList, 'paymentEntryDate');
	}
	return errorList;
}

/** ********************************************END********************************************************* */
function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function addLink(cellvalue, options, rowdata) {
	return "<a class='btn btn-blue-3 btn-sm viewClass' title='view'value='"
			+ rowdata.id
			+ "' id='"
			+ rowdata.id
			+ "' ><i class='fa fa-building-o'></i></a> "
			+ " <a class='btn btn-blue-3 btn-sm printClass' title='Print' value='"
			+ rowdata.id + "' id='" + rowdata.id
			+ "'><i class='fa fa-print'></i></a>";
}

$(document).on('click', '.viewClass', function() {
	
	var $link = $(this);
	var id = $link.closest('tr').find('td:eq(0)').text();
	var url = "TdsPaymentEntry.html?formForView";
	var requestData = "id=" + id;
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$('.content').html(returnData);
	prepareDateTag();

});
$(document).on('click', '.printClass', function() {
	var $link = $(this);
	var id = $link.closest('tr').find('td:eq(0)').text();
	var url = "TdsPaymentEntry.html?formForPrint";
	var requestData = "id=" + id;
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$('.content').html(returnData);
	prepareDateTag();
});

function printdiv(gridId, viewURL) {
	
	var divName = formDivName;
	var url = "TdsPaymentEntry.html?paymentGridPrintForm";
	var requestData = 'gridId=' + gridId;
	var ajaxResponse = __doAjaxRequest(url, 'GET', requestData, false, 'html');
	$('#frmdivId').html(ajaxResponse);
}
function validateSearchInput() {
	var errorList = [];
	var paymentDate = $('#paymentDate').val();
	var paymentAmount = $('#paymentAmount').val();
	var vendorId = $('#vendorId').val();
	var sacHeadId = $('#sacHeadId').val();
	var paymentNo = $('#paymentNo').val();
	var bankAcId = $('#bankAcId').val();
	var count = 0;
	if (paymentDate != undefined && $.trim(paymentDate) != '') {
		count++;
	}
	if (paymentAmount != undefined && $.trim(paymentAmount) != '') {
		count++;
	}
	if (vendorId != undefined && vendorId != '0') {
		count++;
	}
	if (sacHeadId != undefined && sacHeadId != '0') {
		count++;
	}
	if (paymentNo != undefined && $.trim(paymentNo) != '') {
		count++;
	}
	if (bankAcId != undefined && bankAcId != '0') {
		count++;
	}
	if (count == 0) {
		errorList.push('Please provide at least one search criteria.');
	}

	return errorList;

}

function resetBillType() {

	$("#bmBilltypeCpdId").val('0');
	$('#bmBilltypeCpdId').trigger('chosen:updated');
}

function getBillNumbers() {

	var errorList = [];
	
	var billTypeId = $("#bmBilltypeCpdId").val();
	var paymentDate = $("#transactionDateId").val();
	var vendorId = $("#vendorId").val();

	if ((paymentDate == "" || paymentDate == "0")) {
		errorList.push('Please select payment Date');
	}
	if ((vendorId == "" || vendorId == "0")) {
		errorList.push('Please select Vendor Name first.');
	}

	if (billTypeId != undefined && billTypeId != '0' && billTypeId != '') {
		if (vendorId == undefined || vendorId == '0' || vendorId == '') {
			// errorList.push('Please select Vendor Name first.');
		}
	}
	if (errorList.length == 0) {
		
		$('#errorDivId').hide();
		var postData = __serializeForm("#paymentEntry");
		// var postData = 'billTypeId=' + billTypeId + '&vendorId=' + vendorId +
		// '&paymentDate=' + paymentDate;
		
		var url = "PaymentEntry.html?checkPaymentDateisExists";
		var returnData = __doAjaxRequest(url, 'post', postData, false);
		if (returnData) {
			errorList
					.push("Bill's not available for payment against selected payment date.");
			displayErrorsPage(errorList);
			$("#transactionDateId").val("");
			$("#bmBilltypeCpdId").val('0');
			$('#bmBilltypeCpdId').trigger('chosen:updated');
			$("#transactionDateId").prop("disabled", false);
			$("#vendorId").prop("disabled", false);
			$("#vendorId").trigger('chosen:updated');
			$("#bmBilltypeCpdId").prop("disabled", false);
			$("#bmBilltypeCpdId").trigger('chosen:updated');
		}
	}
	if (errorList.length == 0) {
		$('#errorDivId').hide();
		var postData = 'billTypeId=' + billTypeId + '&vendorId=' + vendorId
				+ '&paymentDate=' + paymentDate;
		var url = "PaymentEntry.html?getBillNumbers";
		var response = __doAjaxRequest(url, 'post', postData, false, "json");
		if (jQuery.isEmptyObject(response)) {
			errorList
					.push('No Bills are available against selected Vendor Name and Bill Type.');
			displayErrorsPage(errorList);
			$("#bmBilltypeCpdId").val('0');
			$('#bmBilltypeCpdId').trigger('chosen:updated');
			$("#transactionDateId").prop("disabled", false);
			$("#vendorId").prop("disabled", false);
			$("#vendorId").trigger('chosen:updated');
			$("#bmBilltypeCpdId").prop("disabled", false);
			$("#bmBilltypeCpdId").trigger('chosen:updated');
		} else {
			$.each(response, function(key, value) {
				$('#id0').append(
						$("<option></option>").attr("value", key).text(value));
			});
			$('#id0').trigger('chosen:updated');
			var vendorDesc = $('#vendorId :selected').text();
			$('#vendorDesc').val(vendorDesc);
			$("#transactionDateId").prop("disabled", true);
			$("#vendorId").prop("disabled", true);
			$("#vendorId").trigger('chosen:updated');
			// $("#vendorId").prop("data-rule-required",false);
			$("#bmBilltypeCpdId").prop("disabled", true);
			$("#bmBilltypeCpdId").trigger('chosen:updated');
		}
	} else {
		$("#bmBilltypeCpdId").val('0');
		$('#bmBilltypeCpdId').trigger('chosen:updated');
		$("#transactionDateId").prop("disabled", false);
		$("#vendorId").prop("disabled", false);
		$("#vendorId").trigger('chosen:updated');
		$("#bmBilltypeCpdId").prop("disabled", false);
		$("#bmBilltypeCpdId").trigger('chosen:updated');
		displayErrorsPage(errorList);
	}

}

function validateOnBillType() {

}

function getBillData(count) {

	var errorList = [];

	$('#paymentAmount' + count).val("");

	var billTypeId = $("#bmBilltypeCpdId").val();
	if ((billTypeId == "" || billTypeId == "0" || billTypeId == null)) {
		errorList.push('Please Select Bill Type');
		$("#id" + count).val("");
		$('#id').trigger('chosen:updated');
	}
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else {

		var templateExistFlag = $("#templateExistFlag").val();
		if (templateExistFlag == "Y") {
			$('.billDetailClass')
					.each(
							function(j) {
								for (var m = 0; m < j; m++) {
									if ((($('#id' + m).val() == $('#id' + j)
											.val()))
											&& ($('#id' + m).val() != "" && $(
													'#id' + j).val() != "")) {
										errorList
												.push("Bill number cannot be same,please select another bill number");
									}
								}
							});
			if (errorList.length == 0) {
				var billId = $("#id" + count).val();
				var postData = '&billId=' + billId;
				var url = "PaymentEntry.html?searchBillData";
				var response = __doAjaxRequest(url, 'post', postData, false);
				// $("#billDate"+count).val(response[0].billDate);
				$("#amount" + count).val(response[0].billAmountStr);
				$("#deductions" + count).val(response[0].deductionsStr);
				$("#netPayable" + count).val(response[0].netPayableStr);
				// $("#paymentAmount"+count).val(response[0].netPayable);
				// getAmountFormatInStatic('paymentAmount'+count);

				var amount = 0;
				var rowCount = $('#billDetailTable tr').length;
				for (var m = 0; m <= rowCount - 1; m++) {

					var n = parseFloat(parseFloat($("#paymentAmount" + m).val()));
					if (isNaN(n)) {
						return n = 0;
					}
					amount += n;

					// var result = parseFloat(amount.toFixed(2));
					// $("#totalAmount").val(result);
					$("#totalAmountHidden").val(result);
					// getAmountFormatInStatic('totalAmount');
					getAmountFormatInStatic('totalAmountHidden');
				}

				$('#errorDivId').hide();
			} else {
				displayErrorsPage(errorList);
			}
		}
	}
}

function viewBillDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	// var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var billId = $("#id" + count).val();
	var entryDate = $('#transactionDateId').val();
	var paymentAmt = $('#paymentAmount' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (billId == "" || billId == null) {
		errorList.push("Please select bill number");
	}

	if (paymentAmt == 0 || paymentAmt == "") {
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
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

	// });
	else {

		var postData = 'billId=' + billId + '&entryDate=' + entryDate
				+ '&paymentAmt=' + parseInt(paymentAmt) + '&count=' + count;
		var url = "AccountBillEntry.html?viewPaymentExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);

		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {

			$('#paymentAmount' + count).val("");

			var errorList = [];
			errorList.push(errorMsg);
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

		} else {
			// var divName = '.child-popup-dialog';
			var divName = '.popUp';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			$('#expActAmt0').val(paymentAmt);
			// prepareTags();
			// $(".popUp").append('#expDetailTable tbody tr td:nth-child(10)');
			// showModalBox(divName);
			$(".popUp").show();
			$(".popUp").draggable();
		}
	}
}

function viewPaymentBillDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	// var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var billId = $("#billId" + count).val();
	var entryDate = $('#paymentEntryDate').val();
	var paymentAmt = $('#paymentAmount' + count).val().replace(",", "");
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (billId == "" || billId == null) {
		errorList.push("Please select bill number");
	}

	if (paymentAmt == 0 || paymentAmt == "") {
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
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

	// });
	else {

		var postData = 'billId=' + billId + '&entryDate=' + entryDate
				+ '&paymentAmt=' + parseInt(paymentAmt) + '&count=' + count;
		var url = "AccountBillEntry.html?viewPaymentExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);

		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {

			// $('#paymentAmount' + count).val("");

			var errorList = [];
			errorList.push(errorMsg);
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

		} else {
			// var divName = '.child-popup-dialog';
			var divName = '.popUp';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			$('#expActAmt0').val(paymentAmt);
			// prepareTags();
			// $(".popUp").append('#expDetailTable tbody tr td:nth-child(10)');
			// showModalBox(divName);
			$(".popUp").show();
			$(".popUp").draggable();
		}
	}
}

function viewBugdetPaymentDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	// var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var billId = $("#id" + count).val();
	var entryDate = $('#transactionDateId').val();
	var paymentAmt = $('#paymentAmount' + count).val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (billId == "" || billId == null) {
		errorList.push("Please select bill number");
	}

	if (paymentAmt == 0 || paymentAmt == "") {
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
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

	// });
	else {

		var postData = 'billId=' + billId + '&entryDate=' + entryDate
				+ '&paymentAmt=' + parseInt(paymentAmt) + '&count=' + count;
		var url = "AccountBillEntry.html?viewPaymentExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);

		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {

			$('#paymentAmount' + count).val("");
			$("#totalAmount").val("");

			var errorList = [];
			errorList.push(errorMsg);
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

		}
	}
}

function viewBillDetailsForView(count) {

	var billId = $("#billId" + count).val();

	var errorList = [];
	if (billId != null && billId != "") {
		var postData = '&bmId=' + billId + "&MODE_DATA=" + "paymentEntryView";
		var url = "AccountBillEntry.html?formForView";
		var response = __doAjaxRequest(url, 'post', postData, false);
		$('.content').html(response);
	} else {
		errorList.push("Please select bill number");
		displayErrorsPage(errorList);
	}
}

// to generate dynamic row
$(function() {

	$("#billDetailTable").on("click", '.addButton11', function(e) {
		
		var content = $(this).closest('#billDetailTable tr').clone();
		$(this).closest("#billDetailTable").append(content);
		content.find("select").attr("value", "");
		content.find("input:text").val("");
		content.find("input:checkbox").attr("checked", false);
		content.find('div.chosen-container').remove();
		content.find("select:eq(0)").chosen().trigger("chosen:updated");
		content.find('label').closest('.error').remove(); // for removal
		// duplicate
		reOrderTableIdSequence();
		// e.preventDefault();

	});

	// to delete row
	$("#billDetailTable").on("click", '.delButton', function(e) {
		
		var rowCount = $('#billDetailTable tr').length;
		if (rowCount <= 2) {
			return false;
		}
		$(this).closest('#billDetailTable tr').remove();

		var billAmountTotal = 0;
		var rowCount = $('#billDetailTable tr').length;

		dynamicTotalBilltAmount(rowCount);

		// getNetPayable();
		reOrderTableIdSequence();
		e.preventDefault();
	});

	$('#resetId').click(function() {
		$('#paymentEntry').trigger("reset");
		$('.chosenReset').chosen().trigger("chosen:updated");
	});

});

function dynamicTotalBilltAmount(rowCount) {

	var amount = 0;
	for (var m = 0; m <= rowCount - 1; m++) {
		var n = parseFloat(parseFloat($("#paymentAmount" + m).val()));
		if (isNaN(n)) {
			n = 0;
		}
		amount += n;
		$("#totalAmount").val(parseFloat(amount.toFixed(2)));
		getAmountFormatInStatic('totalAmount');
		$("#totalAmountHidden").val(parseFloat(amount.toFixed(2)));
		getAmountFormatInStatic('totalAmountHidden');
	}
}

function totalPaymentamount() {

	var amount = 0;
	var count = $('.billDetailTableClass tr').length;
	for (var m = 0; m <= count - 1; m++) {
		var n = parseFloat(parseFloat($("#paymentAmount" + m).val()));
		if (isNaN(n)) {
			return n = 0;
		}
		amount += n;

		$("#totalAmount").val(parseFloat(amount.toFixed(2)));
		getAmountFormatInStatic('totalAmount');
		$("#totalAmountHidden").val(parseFloat(amount.toFixed(2)));
		getAmountFormatInStatic('totalAmountHidden');
	}
}

function reOrderTableIdSequence() {

	$('.billDetailClass').each(
			function(i) {
				
				// IDs
				$(this).find("select:eq(0)").attr("id", "id" + i);
				$(this).find("input:text:eq(1)").attr("id", "amount" + i);
				$(this).find("input:text:eq(2)").attr("id", "deductions" + i);
				$(this).find("input:text:eq(3)").attr("id", "netPayable" + i);
				$(this).find("input:text:eq(4)")
						.attr("id", "paymentAmount" + i);
				// $(this).find("input:checkbox:eq(0)").attr("id",+ i);
				$(this).find(".viewBill").attr("id", "viewBillDet" + i);

				// names
				$(this).find("select:eq(0)").attr("name",
						"paymentDetailsDto[" + i + "].id");
				$(this).find("input:text:eq(1)").attr("name",
						"paymentDetailsDto[" + i + "].amount");
				$(this).find("input:text:eq(2)").attr("name",
						"paymentDetailsDto[" + i + "].deductions");
				$(this).find("input:text:eq(3)").attr("name",
						"paymentDetailsDto[" + i + "].netPayable");
				$(this).find("input:text:eq(4)").attr("name",
						"paymentDetailsDto[" + i + "].paymentAmount");
				// $(this).find("input:checkbox:eq(0)").attr("name","selectBills"+i);

				// functions
				$(this).find("select:eq(0)").attr("onchange",
						"getBillData(" + i + ")");
				// $(this).find("input:checkbox:eq(0)").attr("onchange",
				// "getPayAmount(" + i + ")");
				$(this).find(".viewBill").attr("onclick",
						"viewBillDetails(" + i + ")");
				$(this).find('#paymentAmount' + i).attr("onchange",
						"validatePaymentAmount(" + (i) + ")");
			});
}

function validateOnAddRow() {

}

$(function() {
	$("#selectAllBills").click(
			function() {

				var status = this.checked; // "select all" checked status
				$('.selectBill').each(function() { // iterate all listed
					// checkbox items
					this.checked = status; // change ".checkbox" checked status
				});
				var totalAmount = 0;
				$('.billDetailClass').each(
						function(i) {
							var amount = parseFloat($("#paymentAmount" + i)
									.val().replace(/,/g, ''));
							if (isNaN(amount)) {
								amount = 0;
							}
							totalAmount = totalAmount + amount;
							$("#totalAmount").val(totalAmount);
							getAmountFormatInStatic('totalAmount');
							$("#totalAmountHidden").val(totalAmount);
							getAmountFormatInStatic('totalAmountHidden');
						});
				if ($("#selectAllBills:checked").length == 0) {
					$("#totalAmount").val(0.00);
					$("#totalAmountHidden").val(0.00);
				}
			});

});

function getPayAmount(count) {

	
	var amount = parseFloat($("#paymentAmount" + count).val().replace(/,/g, ''));
	if (isNaN(amount)) {
		amount = 0;
	}
	if ($("#totalAmount").val() != undefined && $("#totalAmount").val() != "") {
		var totalAmount = parseFloat($("#totalAmount").val());
	} else {
		var totalAmount = 0;
	}
	if ($('input[id="' + count + '"]:checked').length > 0) {
		if (isNaN(totalAmount)) {
			totalAmount = 0;
		}
		
		totalAmount = totalAmount + amount;
		$("#totalAmount").val(totalAmount);
		getAmountFormatInStatic('totalAmount');
		$("#totalAmountHidden").val(totalAmount);
		getAmountFormatInStatic('totalAmountHidden');
	} else {
		if (isNaN(totalAmount)) {
			totalAmount = 0;
		}
		
		totalAmount = totalAmount - amount;
		$("#totalAmount").val(totalAmount);
		getAmountFormatInStatic('totalAmount');
		$("#totalAmountHidden").val(totalAmount);
		getAmountFormatInStatic('totalAmountHidden');
	}
}

function getChequeNos() {

	var errorList = [];
	
	var transactionDate = $('#transactionDateId').val();

	if (transactionDate == "" || transactionDate == null
			|| transactionDate == undefined) {
		errorList.push(getLocalMessage('account.select.paymentDate'));
		$("#baAccountidPay").val('').trigger('chosen:updated');
	}

	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else {

		var bankAcId = $('#baAccountidPay').val();
		var data = "&bankAcId=" + bankAcId;
		var amountToPay = $('#totalAmount').val();
		var paymentMode = $("#paymentMode option:selected").attr("code");

		if (paymentMode == "Q" || paymentMode == "RT" || paymentMode == "B") {
			
			$('#chequebookDetid').find('option:gt(0)').remove();
			var response = __doAjaxRequest(
					'ContraVoucherEntry.html?getChequeNumbers', 'POST', data,
					false, 'json');

			if (bankAcId != '' && bankAcId != null && bankAcId != 'undefined') {
				var balData = "&bankAcId=" + bankAcId + "&transactionDate="
						+ transactionDate;
				var responseBal = __doAjaxRequest(
						'ContraVoucherEntry.html?getBankBalance', 'POST',
						balData, false, 'json');
			}
			$('#bankBalance').val(responseBal);
			var allow=getLocalMessage('account.payment.allow.neg.bal');
			if ((parseInt(amountToPay) > parseInt(responseBal))&& allow!='Y') {
				errorList
						.push("Bank balance is insufficient : Current Bank A/c Balance is -> "
								+ responseBal + "");
				$("#baAccountidPay").val('').trigger('chosen:updated');
				displayErrorsPage(errorList);
			} else {
				var chequeNoMap = '';
				$.each(response, function(key, value) {
					chequeNoMap += "<option value='" + key + "' selected>"
							+ value + "</option>";
				});
				$('#chequebookDetid').append(chequeNoMap);
				$('#chequebookDetid').val('');
				$('#errorDivId').hide();
			}
		}
	}
}

function proceedForPayment(element) {

	var errorList = [];
	errorList = validateListForm(errorList);
	if (errorList.length == 0) {
		var url = "PaymentEntry.html?proceedPayment";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		// var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var returnData = __doAjaxRequestValidationAccor(element, url, 'post',
				requestData, false, 'html');
		if (returnData != false) {
			$('.content').html(returnData);
			prepareDateTag();
			return false;
			var totalAmount = $("#totalAmount").val();
			$("#amountToPay").val(totalAmount);
		}
	} else {
		displayErrorsPage(errorList);
	}
}

function validateListForm(errorList) {

	var bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	if (bmBilltypeCpdId == undefined || bmBilltypeCpdId == '0'
			|| bmBilltypeCpdId == '') {
		errorList.push(getLocalMessage(account.billtype));
	}
	var vmVendorname = $("#vendorId").val();
	if (vmVendorname == undefined || vmVendorname == '0' || vmVendorname == '') {
		errorList.push(getLocalMessage('account.select.name'));
	}

	/*
	 * var rowCount = $('.billDetailTableClass tr').length; for (var i = 0; i <
	 * rowCount - 1; i++) {
	 * 
	 * var billNo = $('#id' + i).val(); if (billNo == "" || billNo == null) {
	 * errorList.push(getLocalMessage('Please select bill number')); } var
	 * paymentAmount = $("#paymentAmount" + i).val(); if (paymentAmount == "" ||
	 * paymentAmount == null) { errorList.push(getLocalMessage('Please enter
	 * payment amount')); }
	 * 
	 * if($('input[name="selectBills'+i+'"]:checked').length < 0){
	 * errorList.push(getLocalMessage('Please select bill to make payment')); } }
	 */
	$('.billDetailClass')
			.each(
					function(j) {
						for (var m = 0; m < j; m++) {
							if ((($('#id' + m).val() == $('#id' + j).val()))
									&& ($('#id' + m).val() != "" && $('#id' + j)
											.val() != "")) {
								errorList
										.push("Bill number cannot be same,please select another bill number");
							}
						}
					});

	var totalAmount = $("#totalAmount").val();
	if (totalAmount == "" || totalAmount == null) {
		errorList.push(getLocalMessage('account.totalAmt.not.empty'));
	}
	return errorList;
}

function displayErrorsPage(errorList) {

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

function savePaymentEntry(element) {
	
	var errorList = [];
	errorList = validateForm(errorList);

	var from = $("#transactionDateId").val().split("/");
	var transactionDate = new Date(from[2], from[1] - 1, from[0]);
	var to = $("#instrumentDate").val().split("/");
	var instrumentDate = new Date(to[2], to[1] - 1, to[0]);

	if ((transactionDate != "" && transactionDate != null)
			&& (instrumentDate != "" && instrumentDate != null)) {
		if (instrumentDate > transactionDate) {
			errorList
					.push("Instrument Date should not be less than Payment Date");
		}
	}

	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	// errorList = validatePaymentForm(errorList);
	if (errorList.length == 0) {
		
		showConfirmBoxSave();
	}
}

function showConfirmBoxSave(){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+' </h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg(){
     
	var vendorId = $("#vendorId").val();
	$("#dupVendorId").val(vendorId);
	var transactionDateId = $("#transactionDateId").val();
	$("#dupTransactionDate").val(transactionDateId);
	var bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	$("#dupBillTypeId").val(bmBilltypeCpdId);

	var url = "TdsPaymentEntry.html?create";
	//var formName = findClosestElementId(element, 'form');
	//var theForm = '#' + formName;
	//var requestData = __serializeForm(theForm);
	
	var requestData = $('#tdsPaymentEntryFrm').serialize();
	
	var status = __doAjaxRequestForSave(url, 'post', requestData, false,
			'', '');
	// var status = __doAjaxRequestValidationAccor(element,url,'post',
	// requestData, false, 'json');
	if (status != false) {
		if(status != null && status != '' && status != undefined) {
			showConfirmBox(status);
		} else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	} else {
		displayErrorsPage(errorList);
	}
}

function validateForm(errorList) {
	
	var transactionDateId = $("#transactionDateId").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if (fromDate != null) {
		errorList = validatedate(errorList, 'fromDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("From date can not be less than SLI date");
			  }
			}
		  }
	}
	if (toDate != null) {
		errorList = validatedate(errorList, 'toDate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("To date can not be less than SLI date");
			  }
			}
		}
	}

	if (transactionDateId != null) {
		errorList = validatedate(errorList, 'transactionDateId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
				errorList.push("Payment date can not be less than SLI date");
			  }
			}
		  }
	}
	if (transactionDateId == "" || transactionDateId == null) {
		errorList.push(getLocalMessage('account.select.paymentDate'));
	}
	var tdsTypeId = $("#tdsTypeId").val();

	if (tdsTypeId == "" || tdsTypeId == null) {
		errorList.push(getLocalMessage('account.select.tdsType'));
	}

	var vendorDesc = $("#vendorDesc").val();

	if (vendorDesc == "" || vendorDesc == null) {
		errorList.push(getLocalMessage('account.enter.vendorname'));
	}

	var vendorId = $("#vendorId").val();
	if (vendorId == "" || vendorId == null) {
		errorList.push(getLocalMessage('account.vendorname'));
	}

	 /*commented below field against id #133285*/
	/*var bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	if (bmBilltypeCpdId == "" || bmBilltypeCpdId == null) {
		errorList.push(getLocalMessage('account.select.paymentType'));
	}*/

	var chequeTotalAmount = $("#chequeTotalAmount").val();

	if (chequeTotalAmount == "" || chequeTotalAmount == null) {
		errorList.push(getLocalMessage('account.select.tdsDetails'));
	}

	var paymentMode = $("#paymentMode").val();
	if (paymentMode == "" || paymentMode == null) {
		errorList.push(getLocalMessage('account.select.paymentMode'));
	}

	var option = $("#paymentMode option:selected").attr("code");
	if (option == 'Q' || option == 'RT') {
		var baAccountidPay = $.trim($("#baAccountidPay").val());
		if (baAccountidPay == null || baAccountidPay == "") {
			errorList.push(getLocalMessage('account.select.bankAccount'));
		}
		var chequebookDetid = $.trim($("#chequebookDetid").val());
		if (chequebookDetid == null || chequebookDetid == "") {
			errorList.push(getLocalMessage('account.enter.instrument.no'));
		}
		var instrumentDate = $.trim($("#instrumentDate").val());
		if (instrumentDate == null || instrumentDate == "") {
			errorList.push(getLocalMessage('account.select.instrument.date'));
		}
	} else if (option == 'B') {
		 /*commented below field against id #133285*/
		/*var utrNo = $.trim($("#utrNumber").val());
    	if (utrNo == null || utrNo == ""){
    		errorList.push(getLocalMessage('account.enter.UTR.no'));
    	}*/
		var baAccountidPay = $.trim($("#baAccountidPay").val());
		if (baAccountidPay == null || baAccountidPay == "") {
			errorList.push(getLocalMessage('account.select.bankAccount'));
		}
	} else {

	}

	var amountToPay = $("#totalAmount").val();
	if (amountToPay == "" || amountToPay == null || amountToPay == 0
			|| amountToPay == 0.00) {
		errorList.push(getLocalMessage('account.enter.paymentAmt'));
	}
	var bmNarration = $("#bmNarration").val();
	if (bmNarration == "" || bmNarration == null || bmNarration == 0) {
		errorList.push(getLocalMessage('account.narration'));
	}

	return errorList;
}

function showConfirmBox(paymentNo) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("account.proceed.btn");
    var receiptno = getLocalMessage("accounts.receipt.receiptnumber");
	var succ = getLocalMessage("accounts.receipt.success");
	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+ receiptno +' ' + paymentNo + ' '+succ+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	//showModalBox(errMsgDiv);
	showModalBoxWithoutClose(errMsgDiv);
}

function proceed() {
	
	// window.location.href = 'PaymentEntry.html';
	var url = "TdsPaymentEntry.html?paymentReportForm";
	var returnData = __doAjaxRequest(url, 'post', null, false);

	$(errMsgDiv).show('false');
	$.fancybox.close();
	$('.widget').html(returnData);
}

function validatePaymentForm(errorList) {

	var amountToPay = $("#amountToPay").val();
	var bankBalance = $("#bankBalance").val();
	var allow=getLocalMessage('account.payment.allow.neg.bal');
	if((parseInt(amountToPay)>parseInt(bankBalance))&& allow!='Y'){
		errorList.push("Bank balance is insufficient");
	} else {

		var baAccountidPay = $("#baAccountidPay").val();
		if (baAccountidPay == "" || baAccountidPay == null) {
			errorList.push(getLocalMessage('account.select.bankAccount'));
		}
		var paymentMode = $("#paymentMode").val();
		if (paymentMode == "" || paymentMode == null) {
			errorList.push(getLocalMessage('account.select.paymentMode'));
		}

		var paymentModeTxt = $('#paymentMode :selected').text();
		if (paymentModeTxt != "Cash") {

			var chequebookDetid = $("#chequebookDetid").val();
			if (chequebookDetid == "" || chequebookDetid == null) {
				errorList
						.push(getLocalMessage('account.select.instrument.no'));
			}
			var instrumentDate = $("#instrumentDate").val();
			if (instrumentDate == "" || instrumentDate == null) {
				errorList
						.push(getLocalMessage('account.select.instrument.date'));
			}
		}
		if (amountToPay == "" || amountToPay == null) {
			errorList.push(getLocalMessage('account.enter.paymentAmt'));
		}
		var bmNarration = $("#bmNarration").val();
		if (bmNarration == "" || bmNarration == null) {
			errorList.push(getLocalMessage('account.narration'));
		}

	}
	return errorList;
}

function toggleInstruments() {

	// var selectedMode = $('#paymentMode :selected').text();
	var selectedMode = $("#paymentMode option:selected").attr("code");

	if (selectedMode == 'C') {
		$("#chequebookDetid").prop("disabled", true);
		$("#instrumentDate").val('');
		$("#instrumentDate").prop("disabled", true);
		$("#chequebookDetid").val('');
		$("#baAccountidPay").prop("disabled", true);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		$("#baAccountidPay").val("");
	} else if (selectedMode == 'A') {
		$("#chequebookDetid").prop("disabled", true);
		$("#instrumentDate").val('');
		$("#instrumentDate").prop("disabled", true);
		$("#chequebookDetid").val('');
		$("#baAccountidPay").prop("disabled", true);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		$("#baAccountidPay").val("");
	} else if (selectedMode == 'B') {
		$("#chequebookDetid").prop("disabled", true);
		$("#instrumentDate").val('');
		$("#instrumentDate").prop("disabled", true);
		$("#chequebookDetid").val('');
		$("#baAccountidPay").prop("disabled", false);
		$("#baAccountidPay").val('').trigger('chosen:updated');
		$("#baAccountidPay").val("");
	} else {
		$("#chequebookDetid").prop("disabled", false);
		$("#instrumentDate").prop("disabled", false);
		$("#baAccountidPay").prop("disabled", false);
		$("#baAccountidPay").val('').trigger('chosen:updated');

		var transactionDate = $("#transactionDateId").val();
		$('#instrumentDate').val(transactionDate);

		// $(".datepicker").datepicker('setDate', new Date());
	}
}

function resetPaymentForm() {

	$('#baAccountidPay').val('').trigger('chosen:updated');
	$('#vendorId').val('').trigger('chosen:updated');
	$('#bmBilltypeCpdId').val('').trigger('chosen:updated');

}

/**
 * being used to display validation errors on page
 * 
 * @param errorList :
 *            pass array of errors
 * @returns {Boolean}
 */
function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('#errorDivId').html(errMsg);
	$("#errorDivId").show();

	$('html,body').animate({
		scrollTop : 0
	}, 'slow');

	return false;
}

function closeOutErrBox() {
	$('.error-div').hide();
}

function validatePaymentAmount(cont) {

	// $('#totalAmount').val("");
	// $('#selectAllBills').attr('checked', false); // Unchecks it
	// $('.selectBill').attr('checked', false);

	$("#paymentMode").val("");

	viewBugdetPaymentDetails(cont);

	getAmountFormatInStatic('paymentAmount' + cont);

	// var minAmt = $('#netPayable'+cont).val().toString().replace(",","");
	var minAmt = $('#netPayable' + cont).val().toString().replace(/\,/g, '');
	var maxAmt = $('#paymentAmount' + cont).val();

	if (maxAmt != "" && maxAmt != null && maxAmt != undefined && !isNaN(maxAmt)) {
		try {
			maxAmt = parseFloat(maxAmt);
			minAmt = parseFloat(minAmt);

			if (maxAmt > minAmt) {
				var msg = "Payment Amount should be less than balance/netPayable amount!";
				showErrormsgboxTitle(msg);
				$('#paymentAmount' + cont).val("");
				$("#totalAmount").val("");
				return false;
			}
		} catch (e) {
			return false;
		}
	} // end maxAmt minAmt comparison
}

function setInstrumentDate(obj) {

	var transactionDate = $("#transactionDateId").val();
	$('#instrumentDate').val(transactionDate);
	$('#paymentMode').val("");
}

function calcTotalAmount() {
	var amount = 0;
	var rowCount = $('#billDetailTable tr').length;
	for (var m = 0; m <= rowCount - 1; m++) {

		var n = parseFloat(parseFloat($("#paymentAmount" + m).val()));
		if (isNaN(n)) {
			return n = 0;
		}
		amount += n;

		var result = parseFloat(amount.toFixed(2));
		$("#totalAmount").val(result);
		$("#totalAmountHidden").val(result);
		getAmountFormatInStatic('totalAmount');
		getAmountFormatInStatic('totalAmountHidden');
	}
}

function setPrevBalanceAmount(prExpId, amount, count) {
	$(".popUp").hide();
}

function validateChequeDate() {

	var errorList = [];
	
	var transactionDate = $('#transactionDateId').val();
	if (transactionDate == "" || transactionDate == null
			|| transactionDate == undefined) {
		errorList.push(getLocalMessage('account.select.paymentDate'));
		$("#instrumentDate").val("");
	}
	var paymentMode = $('#paymentMode').val();
	if (paymentMode == "" || paymentMode == null || paymentMode == undefined) {
		errorList.push(getLocalMessage('account.select.paymentMode'));
		$("#instrumentDate").val("");
	}
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else {
		var from = $("#transactionDateId").val().split("/");
		var transactionDate = new Date(from[2], from[1] - 1, from[0]);
		var to = $("#instrumentDate").val().split("/");
		var instrumentDate = new Date(to[2], to[1] - 1, to[0]);

		if ((transactionDate != "" && transactionDate != null)
				&& (instrumentDate != "" && instrumentDate != null)) {
			if (instrumentDate > transactionDate) {
				errorList
						.push("Instrument date should not be greater than payment date");
				$("#instrumentDate").val("");
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
	}
}

function checkPaymetCashBalanceExists() {

	var errorList = [];
	
	var type = $("#paymentMode option:selected").attr("code");
	if (type == "C" || type == "PCA") {

		var transactionDate = $('#transactionDateId').val();

		if (transactionDate == "" || transactionDate == null
				|| transactionDate == undefined) {
			errorList.push(getLocalMessage('account.select.paymentDate'));
			$('#paymentMode').val("");
		}

		var sactionAmt = 0;
		$('.billDetailClass')
				.each(
						function(j) {
							
							for (var m = 0; m <= j; m++) {
								var paymentAmount = $("#paymentAmount" + m)
										.val();
								if (paymentAmount == ""
										|| paymentAmount == null
										|| paymentAmount == 0
										|| paymentAmount == 0.00) {
									errorList
											.push(getLocalMessage('account.enter.paymentAmt.invoiceDetails'));
									$('#paymentMode').val("");
								} else {
									sactionAmt += parseFloat($(
											"#paymentAmount" + m).val());
								}
							}
						});

		var resultCashAmt = sactionAmt.toFixed(2);

		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		} else {
			var balData = "&transactionDate=" + transactionDate;
			var responseBal = __doAjaxRequest(
					'ContraVoucherEntry.html?getCashAmount', 'POST', balData,
					false, 'json');
			if (parseFloat(responseBal) < 0) {
				errorList
						.push("Cash balance is insufficient/ negative amount : Current Cash A/c Balance is -> "
								+ responseBal + "");
				$('#paymentMode').val("");
				displayErrorsPage(errorList);
			} else if (parseFloat(resultCashAmt) > parseFloat(responseBal
					.toFixed(2))) {
				errorList
						.push("Cash balance is insufficient : Current Cash A/c Balance is -> "
								+ responseBal + "");
				$('#paymentMode').val("");
				displayErrorsPage(errorList);
			} else {
				$('#errorDivId').hide();
			}
		}
	}
	
	if(type == 'B'){
		$("#utrNo").show();
		$("#instrumentNo").hide();
	}else{
		$("#utrNo").hide();
		$("#instrumentNo").show();
	}
}

function getVendorDetails(obj) {
	
	var errorList = [];
	errorList = validateTDSForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	if (errorList.length == 0) {
		var url = "TdsPaymentEntry.html?vendordetails";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(url, 'post', requestData, false, '', obj);

		if (response != false) {
			var hiddenVal = $(response).find('#successfulFlag').val();
			if (hiddenVal != 'Y') {
				$(".widget-content").html(response);
				$(".widget-content").show();
				$("#tdsTypeId").prop("disabled", true)
						.trigger('chosen:updated');
				$("#fromDate").prop("disabled", true);
				$("#toDate").prop("disabled", true);
				$("#transactionDateId").prop("disabled", true);
				$("#vendorId").prop("disabled", true).trigger('chosen:updated');
				$("#bmBilltypeCpdId").prop("disabled", true).trigger(
						'chosen:updated');
				$('#tdsFirstWidget').hide();

			} else {
				errorList.push(getLocalMessage('account.norecord.found'));
				displayErrorsPage(errorList);
			}
		} else {

			displayErrorsPage(errorList);
		}

	}
}

function validateTDSForm(errorList) {
	
	var transactionDateId = $("#transactionDateId").val();

	var transactionDateId = $("#transactionDateId").val();
	if (transactionDateId == "" || transactionDateId == null) {
		errorList.push(getLocalMessage('account.select.paymentDate'));
	}

	var tdsTypeId = $("#tdsTypeId").val();
	if (tdsTypeId == "" || tdsTypeId == null) {
		errorList.push(getLocalMessage('account.select.tdsType'));
	}

	var vendorId = $("#vendorId").val();
	if (vendorId == "" || vendorId == null) {
		errorList.push(getLocalMessage('account.vendorname'));
	}
	/* commented below field against id #133285 */
	/*var paymentType = $("#bmBilltypeCpdId").val();
	if (paymentType == "" || paymentType == null) {
		errorList.push(getLocalMessage('account.select.paymentType'));
	}*/

	var fromDateId = $("#fromDate").val();
	if (fromDateId == "" || fromDateId == null) {
		errorList.push(getLocalMessage('account.select.fromDate'));
	}
	var toDateId = $("#toDate").val();
	if (toDateId == "" || toDateId == null) {
		errorList.push(getLocalMessage('account.select.toDate'));
	}

	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;

	var fromdate = new Date($("#fromDate").val().replace(pattern, '$3-$2-$1'));
	var todate = new Date($("#toDate").val().replace(pattern, '$3-$2-$1'));
	var paymentDate = new Date($("#transactionDateId").val().replace(pattern,
			'$3-$2-$1'));
	if (paymentDate < fromdate) {
		errorList.push("From Date can not be greater than Payment Date ");
	}
	if (paymentDate < todate) {
		errorList.push("To  Date can not be greater than  Payment Date ");
	}
	return errorList;
}

$(document).ready(function() {
	$('#tdsFirstWidget').show();

});

function printdiv(printpage) {
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

function checkProposalDate() {

	$("#transactionDateId").val("");
}

$("#transactionDateId").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});

$(document).ready(function() {
	$("#utrNo").hide();
	$("#instrumentNo").show();
});

function getvendornameByTdsType() {
	
	$('#vendorId').html('');
	var tdsTypeId = $('#tdsTypeId').val();
	var requestUrl = "TdsPaymentEntry.html?vendorNameOfTdstype";
	var requestData = {
		"tdsTypeId" : tdsTypeId
	};
	var divName = '.widget';
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	var count = Object.keys(ajaxResponse).length;
	if (jQuery.isEmptyObject(ajaxResponse)) {
		$('#vendorId').append($("<option></option>").attr("value", 0).text("Select"));
		$('#vendorId').trigger('chosen:updated');
		prepareTags();
	}else{	
			if(count==1){
				$.each(ajaxResponse, function(index, value) {
				    $('#vendorId').append($("<option></option>").attr("value", index).text(value));
					});
				    $('#vendorId').trigger('chosen:updated');
				    prepareTags();
			}else{
				    $('#vendorId').append($("<option></option>").attr("value", 0).text("Select"));
			        $.each(ajaxResponse, function(index, value) {
			    	$('#vendorId').append($("<option></option>").attr("value", index).text(value));
					});
				    $('#vendorId').trigger('chosen:updated');
			}
	}	
}
function populateAmount(){

	var chequeTotalAmount=$('#chequeTotalAmount').val();
	$('#totalAmount').val(chequeTotalAmount);
	
}



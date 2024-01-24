
$(function() {
	debugger;

	var url = "AccountChequeIssue.html?isChequeIssuanceRequired";
	var requestData = {};
	var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	if (result != "Y") {
		$("#searchId").attr('disabled', true);
		$("#addId").attr('disabled', true);
	}
})

$("#tbBankReconciliation").validate({
	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

var dsgid = '';
var dsgid = '';
var lastsel3;
$(function() {

	$("#grid")
			.jqGrid(
					{
						url : "AccountChequeIssue.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								'',
								getLocalMessage('budget.reappropriation.master.transactiondate'),
								getLocalMessage('account.cheque.cash.transaction'),
								getLocalMessage('challan.receipt.amount.Instrument'),
								getLocalMessage('accounts.receipt.cheque_dd_date'),
								getLocalMessage('bill.amount'),
								getLocalMessage('accounts.clearance.date'), "", ],// "Edit","View"
						colModel : [
								{
									name : "bankReconciliationId",
									width : 20,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									},
									hidden : true
								},
								{
									name : "transactionDate",
									width : 50,
									align : 'center',
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "transactionNo",
									width : 40,
									align : 'center',
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "chequeddno",
									width : 40,
									align : 'center',
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "chequedddate",
									width : 50,
									align : 'center',
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "amount",
									width : 40,
									align : 'right',
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "date",
									width : 50,
									align : 'center',
									editable : true,
									sorttype : "date",
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "id",
									index : 'id',
									width : 20,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									},
									hidden : true
								}, ],

						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "bankReconciliationId",
						sortorder : "desc",
						height : 'auto',
						viewrecords : true,
						gridview : true,
						loadonce : true,
						// multiselect : true,
						jsonReader : {
							root : "rows",
							page : "page",
							total : "total",
							records : "records",
							repeatitems : false,
						},
						autoencode : true,
						caption : "Cheque Dishonour List",

						formatter : function(v) {
							// uses "c" for currency formatter and "n" for
							// numbers
							return Globalize.format(Number(v), "c");
						},
						unformat : function(v) {
							return Globalize.parseFloat(v);
						},

						onSelectRow : function(id) {
							jQuery('#grid').jqGrid('restoreRow', id);
							jQuery('#grid').jqGrid('editRow', id, true,
									pickdates);
						},

					});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		search : true,
		refresh : false
	});
});

function pickdates(id) {
	jQuery("#" + id + "_date", "#grid").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		minDate : $("#transactionDate" + id).val(),
		maxDate : '-0d',
		changeYear : true,
		onSelect : function() {
			changeValidClearanceDate(id);
		}
	});

	$("#" + id + "_date").attr('readonly', true);

}

function changeValidIssueaneDate(id) {

	$('.error-div').hide();
	var errorList = [];
	if ($('#date' + id).val() != null) {
		errorList = validatedate(errorList, 'date' + id);
	}

	var maxDate = ($('#transactionDate' + id).val());
	var minDate = ($('#date' + id).val());

	if (minDate == null || minDate == '') {
		calculateBankStatement(id);
	}

	if ((maxDate != "" && maxDate != null)
			&& (minDate != "" && minDate != null)) {
		var arr = minDate.split('/');
		var day = arr[0];
		var month = arr[1];
		var year = arr[2];
		var curArr = month + "/" + day + "/" + year;

		var arry = maxDate.split('/');
		var day = arry[0];
		var month = arry[1];
		var year = arry[2];
		var curArry = month + "/" + day + "/" + year;

		var D1 = new Date(curArry);
		var D2 = new Date(curArr);
		var D3 = new Date();

		if (D2 >= D1) {
			calculateBankStatement(id);

		} else {
			errorList
					.push("Issueance date should be equal or greater than Payment date");
			$("#date" + id).val("");
		}
	}
	if (errorList.length == 0) {
		var response = __doAjaxRequest(
				'AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,
				'json');
		if (response == "Y") {
			errorList.push("SLI Prefix is not configured");
		} else {

		}
	}
	if (errorList.length > 0) {
		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';
		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

}

function calculateBankStatement(i) {

	var date = $("#date" + i).val();
	if (!(date == null || date == '')) {
		var sumofamount = parseFloat($("#amount" + i).val().replace(/[,]/g, ''))
				+ parseFloat($("#statementId").val().replace(/[,]/g, ''));
		var totalamount = sumofamount.toLocaleString('en-IN', {
			maximumFractionDigits : 2,
		});
		$("#statementId").val(totalamount)
	} else if (date == '') {
		var sumofamount = parseFloat($("#statementId").val()
				.replace(/[,]/g, ''))
				- parseFloat($("#amount" + i).val().replace(/[,]/g, ''));
		var totalamount = sumofamount.toLocaleString('en-IN', {
			maximumFractionDigits : 2,
		});
		$("#statementId").val(totalamount)
	}

}

function addLink(cellvalue, options, rowdata) {
	return "<a class='btn btn-blue-3 btn-sm viewBugopnBalMasterClass' title='View'value='"
			+ rowdata.bankReconciliationId
			+ "' bankReconciliationId='"
			+ rowdata.bankReconciliationId
			+ "' ><i class='fa fa-building-o'></i></a> ";
}

function viewChecklistMst(cellValue, options, rowdata, action) {
	var myVar = rowdata.bankReconciliationId;
	return "<a href='#'  return false; class='viewClass' value='"
			+ myVar
			+ "'><img src='css/images/view.png' width='20px' alt='View Cheque Dishonour' title='View Cheque Dishonour' /></a>";
}

$(function() {
	$(document)
			.on(
					'click',
					'.viewClass',
					function() {
						var gr = jQuery("#grid").jqGrid('getGridParam',
								'selrow');
						var url = "AccountChequeIssue.html?viewData";
						var string = $(this).attr('value');
						var d = string.split(",");
						var bankReconciliationId = Number.parseInt(d[0]);
						var returnData = {
							"bankReconciliationId" : bankReconciliationId
						};
						$
								.ajax({
									url : url,
									datatype : "json",
									mtype : "POST",
									data : returnData,
									success : function(response) {
										var divName = '.child-popup-dialog';
										$(divName).removeClass('ajaxloader');
										$(divName).html(response);
										showModalBox(divName);
									},
									error : function(xhr, ajaxOptions,
											thrownError) {
										var errorList = [];
										errorList
												.push(getLocalMessage("admin.login.internal.server.error"));

										showError(errorList);
									}
								});
					});
});

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"
				+ rowdata.isdeleted
				+ "'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"
				+ rowdata.isdeleted
				+ "' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

function closeOutErrBox() {
	$('.error-div').hide();
}
$(function() {
	$(document).on(
			'click',
			'.createData',
			function() {
				var $link = $(this);
				var bankReconciliationId = 1;
				var url = "AccountChequeIssue.html?form";
				var requestData = "bankReconciliationId="
						+ bankReconciliationId + "&MODE_DATA=" + "ADD";
				var returnData = __doAjaxRequest(url, 'post', requestData,
						false);

				var divName = ".content";
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);

				return false;
			});

	$(document)
			.on(
					'click',
					'.editBugopnBalMasterClass',
					function() {
						var errorList = [];
						var $link = $(this);
						var bankReconciliationId = $link.closest('tr').find(
								'td:eq(0)').text();
						var authStatus = $link.closest('tr').find('td:eq(5)')
								.text();
						var url = "AccountChequeIssue.html?update";
						var requestData = "bankReconciliationId="
								+ bankReconciliationId + "&MODE_DATA=" + "EDIT";
						var returnData = __doAjaxRequest(url, 'post',
								requestData, false);
						if (authStatus != "Inactive") {
							$('.content').html(returnData);
						} else {
							errorList
									.push("It is already Inactive so EDIT is not allowed!");
							if (errorList.length > 0) {
								var errorMsg = '<ul>';
								$
										.each(
												errorList,
												function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
															+ errorList[index]
															+ '</li>';
												});
								errorMsg += '</ul>';
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');
							}
						}

					});

	$(document).on(
			'click',
			'.viewBugopnBalMasterClass',
			function() {
				var $link = $(this);
				var bankReconciliationId = $link.closest('tr').find('td:eq(0)')
						.text();
				var url = "AccountChequeIssue.html?formForView";
				var requestData = "bankReconciliationId="
						+ bankReconciliationId + "&MODE_DATA=" + "VIEW";
				var returnData = __doAjaxRequest(url, 'post', requestData,
						false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('input[type="text"], textarea').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				return false;
			});

});

$(function() {

	$(document).on('click', '.deleteDesignationClass', function() {
		var $link = $(this);
		var dsgid = $link.closest('tr').find('td:eq(0)').text();
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
		showConfirmBoxEmployee(dsgid);

	});

});

$(document)
		.ready(
				function() {

					$('.error-div').hide();
					var errorList = [];

					var status = $("#cpdIdStatusFlagDup").val();

					if (status === 'I') {
						errorList
								.push("You can not edit Inactive Budget Head!");
					}

					if (errorList.length > 0) {

						var errorMsg = '<ul>';
						$
								.each(
										errorList,
										function(index) {
											errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
													+ errorList[index]
													+ '</li>';
										});
						errorMsg += '</ul>';
						$('#errorIdI').html(errorMsg);
						$('#errorDivIdI').show();
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');
					}

					$('.hasMyNumber').keyup(function() {
						this.value = this.value.replace(/[^0-9.]/g, '');
						$(this).attr('maxlength', '13');
					});
					$("#AllId").prop('checked', true);
					$("#flag").click(function() {

						var chk = $('#flag').is(':checked');
						if (chk) {
							$("input[id^='flagFlzd']").prop('checked', true);
						} else {
							$("input[id^='flagFlzd']").prop('checked', false);
						}
					});
					var catTypeFlag = $("#AllId").val();
					if (catTypeFlag == "A") {
						$("#AllCategoryList").show();
						$("#paymentWiseCategoryList").hide();
						$("#receiptWiseCategoryList").hide();
						$("#transactionModeId").show();
						$("#transactionModeId1").hide();

					} else if (catTypeFlag == "R") {
						$("#AllCategoryList").hide();
						$("#paymentWiseCategoryList").hide();
						$("#receiptWiseCategoryList").show();
						$("#transactionModeId1").show();
						$("#transactionModeId").hide();
					} else if (catTypeFlag == "P") {
						$("#AllCategoryList").hide();
						$("#receiptWiseCategoryList").hide();
						$("#paymentWiseCategoryList").show();
						$("#transactionModeId1").show();
						$("#transactionModeId").hide();
					}
					var categoryType = $("#categoryType").val();
					if (categoryType == 'Y') {
						$('.chequeTr').each(function(i) {
							for (var m = 0; m <= i; m++) {
								$("#date" + m).prop("disabled", true);
							}
						});
					}

				});

function displayAddView(obj) {
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var divName = childDivName;

	var requestData = __serializeForm(theForm);

	var formAction = $(theForm).attr('action');

	var url = formAction + '?create';

	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$(divName).html(returnData);

	prepareDateTag();
}

$(".warning-div ul")
		.each(
				function() {
					var lines = $(this).html().split("<br>");
					$(this)
							.html(
									'<li>'
											+ lines
													.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;")
											+ '</li>');
				});
$('html,body').animate({
	scrollTop : 0
}, 'slow');

var objTemp;
function saveLeveledData(obj) {
	debugger;
	var errorList = [];
	objTemp=obj;
	var count = 0;
	if ($.fn.DataTable.isDataTable('#bankrec')) {
		$('#bankrec').DataTable().destroy();
	}
	$("#bankrec tbody tr").each(function(i) {
		for (var m = 0; m <= i; m++) {
			var date = $('#date' + m).val();
			if (date != "" && date != null) {
				count++;
			}
		}
	});

	if (count == '0') {
		errorList.push("Please enter at least one issuance date");
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	if (errorList.length == 0) {
		
		showConfirmBoxSave();
		  //#128596
		/*var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var url = $(theForm).attr('action');
		var response = __doAjaxRequestValidationAccor(obj, url + '?create',
				'post', requestData, false, 'html');
		if (response != false) {
			$('.content').html(response);
		}
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}*/

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
	' onclick="saveFormDataAfterConfimation()"/>  '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveFormDataAfterConfimation(){
	//debugger;
	var formName = findClosestElementId(objTemp, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');
	var response = __doAjaxRequestValidationAccor(objTemp, url + '?create',
			'post', requestData, false, 'html');
	if (response != false) {
		$('.content').html(response);
	}
	var val = $('#keyTest').val();
	if (val != '' && val != undefined) {
		displayMessageOnSubmit(val);
	}
}




function closeErrBox() {
	$('.error-div').hide();
}

function searchBankReconciliationOldData() {
	debugger;
	$('.error-div').hide();
	var errorList = [];
	if (errorList.length > 0) {
		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	if (errorList.length == 0) {

		var url = "AccountChequeIssue.html?getjqGridReceiptPaymentBothSearch";
		var requestData = {
			"categoryId" : categoryId,
			"bankAccount" : bankAccount,
			"transactionMode" : transactionMode,
			"formDate" : formDate,
			"toDate" : toDate,
			"serchType" : serchType,
		};
		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');

		} else {
			var errorList = [];

			errorList.push(getLocalMessage("account.norecord.criteria"));
			$("#viewBankReconciliationDetails").hide();
			if (errorList.length > 0) {
				var errorMsg = '<ul>';
				$
						.each(
								errorList,
								function(index) {
									errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
											+ errorList[index] + '</li>';
								});
				errorMsg += '</ul>';
				$('#errorId').html(errorMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
			}
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
};

function searchBankReconciliationData(obj) {
	debugger;
	var errorList = [];
	var serchType = $('input[name=serchType]:checked').val();
	var categoryId = $("#categoryId").val();
	errorList = validateForm(errorList);
	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}
	if (errorList.length == 0) {
		var url = "AccountChequeIssue.html?getjqGridReceiptPaymentBothSearch";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(url, 'post', requestData, false, '', obj);

		if (response != false) {
			var hiddenVal = $(response).find('#successfulFlag').val();
			if (hiddenVal != 'Y') {
				if (serchType == 'R' && categoryId != 'DNC') {
					$("#viewBankReconciliationDetails").hide();
				} else if (serchType == 'P' && categoryId != 'ISD') {
					$("#viewBankReconciliationDetails").hide();
				} else if (serchType == 'A') {
					$("#viewBankReconciliationDetails").show();
				}
				$(".widget-content").html(response);
				$(".widget-content").show();
			} else {
				errorList.push(getLocalMessage('account.norecord.criteria'));
				if (errorList.length > 0) {
					var errorMsg = '<ul>';
					$
							.each(
									errorList,
									function(index) {
										errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
												+ errorList[index] + '</li>';

									});
					errorMsg += '</ul>';
					$('#errorId').html(errorMsg);
					$('#errorDivId').show();
					$('html,body').animate({
						scrollTop : 0
					}, 'slow');

				}
			}
		} else {
			if (errorList.length > 0) {
				var errorMsg = '<ul>';
				$
						.each(
								errorList,
								function(index) {
									errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
											+ errorList[index] + '</li>';

								});
				errorMsg += '</ul>';
				$('#errorId').html(errorMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');

			}
		}

	}
}

function validateForm(errorList) {
	debugger;
	var dateFrom = '01/04/';
	var dateTo = '31/03/';
	var finciStartDate;
	var finciToDate;
	var finciToDate1;

	var serchType = $('input[name=serchType]:checked').val();

	/*if (serchType == "" || serchType == "0" || serchType == undefined) {
		// errorList.push('Kindly select Search Type');
	} else {*/
		var bankAccount = $("#bankAccount").val();

		// var AllCategoryId=$("#AllCategoryId").val();

		// var categoryId = $("#categoryId").val();
		// var paymentCategoryId = $("#paymentCategoryId").val();
		// transactionModeId1 = $("#trans").val();
		var formDate = $("#formDate").val();
		var toDate = $("#toDate").val();
		if (serchType == "A") {

		} else if (serchType == "R") {

		} else if (serchType == "P") {

		}
		if (bankAccount == "" || bankAccount == "0") {
			errorList.push(getLocalMessage('select.bank.account'));
		}
		if (formDate == "" && formDate == "") {
			errorList.push(getLocalMessage('selcet.from.date'));
		}
		if (toDate == "" && toDate == "") {
			errorList.push(getLocalMessage('select.to.date'));
		}

		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date($("#formDate").val().replace(pattern, '$3-$2-$1'));

		var currentFinancialYearFromDate = eDate.getFullYear() + 1;

		var sDate = new Date($("#toDate").val().replace(pattern, '$3-$2-$1'));

		finciToDate = sDate.getFullYear() + 1;

		finciToDate1 = sDate.getFullYear();

		finciStartDate = finciToDate1 - 1;
		if (eDate > sDate) {
			errorList.push("From Date can not be less than To Date");
			$("#reportTypeId").prop("disabled", true);
		}
		dateFrom = new Date((dateFrom + finciStartDate).replace(pattern,
				'$3-$2-$1'));
		dateTo = new Date((dateTo + finciToDate).replace(pattern, '$3-$2-$1'));

		var currentdateTo = new Date(("31/03/" + currentFinancialYearFromDate)
				.replace(pattern, '$3-$2-$1'));

		if ((eDate < dateFrom) || ((sDate > currentdateTo) || (sDate > dateTo))) {
			errorList
					.push("From Date and To Date should be within the financial year");
		}
		if ($("#formDate").val() != null) {
			errorList = validatedate(errorList, 'fromDateId');
		}
		if ($("#toDate").val() != null) {
			errorList = validatedate(errorList, 'toDateId');
		}
		if (formDate != "" && toDate != "") {
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var eDate = new Date(formDate.replace(pattern, '$3-$2-$1'));
			var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));
			if (eDate > sDate) {
				errorList.push("From Date can not be less than To Date");
			}
		}
	//}
	return errorList;
}

function displayMessageOnSubmit(successMsg) {
	debugger;
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>' + successMsg
			+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls
			+ '\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToDishonorHomePage() {
	$.fancybox.close();
	window.location.href = 'AccountChequeIssue.html';
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

$(function() {
	$('#divId input').keyup(function() {
		this.value = this.value.toUpperCase();
	});
});

function findduplicatecombinationifscexit(obj) {
	debugger;
	$('.error-div').hide();
	var errorList = [];

	var theForm = '#frmMaster';
	var requestData = __serializeForm(theForm);

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	if (errorList.length == 0) {

		var url = "AccountChequeIssue.html?getDuplicateIFSCCodeExit";

		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		if (returnData) {

			errorList
					.push("IFSC Code is Already Exists, against this Bank Master!");
			$('#ifsc').val("");

			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$
					.each(
							errorList,
							function(index) {
								var errorMsg = '<ul>';
								$
										.each(
												errorList,
												function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
															+ errorList[index]
															+ '</li>';
												});
								errorMsg += '</ul>';
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');
							});
			return false;
		}
	}
};

function findduplicatecombinationBranchexit(obj) {
	debugger;
	$('.error-div').hide();
	var errorList = [];
	var theForm = '#frmMaster';
	var requestData = __serializeForm(theForm);
	var bank = $('#bank').val();
	if (bank == '') {
		errorList.push('Please Enter Bank Name');
		$('#branch').val("");
	}
	if (errorList.length > 0) {
		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errorMsg += '</ul>';
		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

	if (errorList.length == 0) {
		var url = "AccountChequeIssue.html?getDuplicateBranchNameExit";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if (returnData) {
			errorList
					.push("Branch Name is Already Exists, against this Bank Name!");
			$('#branch').val("");
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$
					.each(
							errorList,
							function(index) {
								var errorMsg = '<ul>';
								$
										.each(
												errorList,
												function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
															+ errorList[index]
															+ '</li>';
												});
								errorMsg += '</ul>';
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');
							});
			return false;
		}
	}
};
function clearBranchName(obj) {
	$('#branch').val("");
}
$(function() {
	$('#divId textarea').keyup(function() {
		this.value = this.value.toUpperCase();
	});
});
function getBankAccountData(obj) {
	debugger;
	$('.error-div').hide();
	var errorList = [];
	$('#bankAccount').val("");
	$("#bankAccount").val('').trigger('chosen:updated');
	$('#bankAccountCheque').val("");
	$("#bankAccountCheque").val('').trigger('chosen:updated');
	if (errorList.length == 0) {
		var type = $('input[name=serchType]:checked').val();
		var postdata = 'type=' + type;
		var json = __doAjaxRequest(
				'AccountChequeIssue.html?getBankAccountData', 'POST', postdata,
				false, 'json');
		var optionsAsString = '';

		$.each(json, function(key, value) {

			optionsAsString += "<option value='" + key + "'>" + value
					+ "</option>";

		});
		$('#bankAccountCheque').append(optionsAsString);
		$('#bankAccountCheque').trigger('chosen:updated');

		if (type == 'D') {

			$("#payInSlip").hide();
			$("#chequeDDSlip").show();

		} else {
			$("#payInSlip").show();
			$("#chequeDDSlip").hide();
		}
	}
};

function renderRedirectPage(requestData) {

	var url = "AccountChequeIssue.html?form"
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$('.content').html(returnData);
}
function changeCategoryType() {
	var catTypeFlag = $('input[name=serchType]:checked').val();
	if (catTypeFlag == "A") {
		$("#AllCategoryList").show();
		$("#paymentWiseCategoryList").hide();
		$("#receiptWiseCategoryList").hide();
		$("#transactionModeId").show();
		$("#transactionModeId1").hide();
	} else if (catTypeFlag == "R") {
		$("#AllCategoryList").hide();
		$("#paymentWiseCategoryList").hide();
		$("#receiptWiseCategoryList").show();
		$("#transactionModeId1").show();
		$("#transactionModeId").hide();
	} else if (catTypeFlag == "P") {
		$("#AllCategoryList").hide();
		$("#receiptWiseCategoryList").hide();
		$("#paymentWiseCategoryList").show();
		$("#transactionModeId1").show();
		$("#transactionModeId").hide();
	}
}

$(document).ready(function() {
	$("#viewBankReconciliationDetails").hide();
	$(".datepicker").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	var val = $('#keyTest').val();
	if (val != '' && val != undefined) {
		displayMessageOnSubmit(val);
	}

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	$(".datepicker").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

});
function addBankReconcilationForm(formUrl, actionParam) {
	debugger;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchBankReconcilationData() {
	debugger;
	var errorList = [];
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var bankAccount = $('#bankAccount').val();
	errorList = bankReconcilationvalidateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"fromDate" : fromDate,
			"toDate" : toDate,
			"bankAccount" : bankAccount
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(
				'AccountChequeIssue.html?searchChequeIssuanceData', data,
				'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function bankReconcilationvalidateForm(errorList) {

	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var bankAccount = $('#bankAccount').val();
	if (fromDate == "" || fromDate == "0" || fromDate == undefined) {	
		errorList.push(getLocalMessage('account.stpPayment.slctFrmDate'));
	}
	if (toDate == "" || toDate == "0" || toDate == undefined) {
		errorList.push(getLocalMessage('account.stpPayment.slctToDate'));
	}
	if(toDate != "" &&  fromDate != ""){
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($('#fromDate').val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorList.push("To Date can not be less than From Date");
		    }
		}

	if (bankAccount == "" || bankAccount == "0" || bankAccount == undefined) {
		errorList.push(getLocalMessage('account.stpPayment.slctBankAc'));
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
function resetReconcilation() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AccountChequeIssue.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function showStatusId() {

	$('#statusId').html('');
	var bankAccount = $('#bankAccount').val();
	var requestUrl = "AccountChequeIssue.html?searchStatusId";
	var requestData = {
		"bankAccountId" : bankAccount
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#statusId').append(
			$("<option></option>").attr("value", "").attr("code", "").text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#statusId').append(
				$("<option></option>").attr("value", index).attr("code", index)
						.text(value));
	});
	$('#statusId').trigger('chosen:updated');
}
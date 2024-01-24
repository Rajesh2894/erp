$(function() {
	$("#grid")
			.jqGrid(
					{
						url : "StandardAccountHeadMappping.html?getGridData",
						datatype : "json",
						mtype : "GET",
						colNames : [
								getLocalMessage('accounts.Secondaryhead.PrimaryObjectcode'),
								getLocalMessage('bank.master.accountType'),
								getLocalMessage('accounts.acc.subtype'),
								getLocalMessage('accounts.master.status'), "",
								"", "", "", "", getLocalMessage('bill.action') ],// "Edit","View"
						colModel : [ {
							name : "codeDescription",
							width : 50,
							sortable : true
						}, {
							name : "accountTypeDesc",
							width : 20,
							align : 'center',
							sortable : true
						}, {
							name : "accountSubTypeDesc",
							width : 20,
							align : 'center',
							sortable : true
						}, {
							name : "statusDescription",
							width : 10,
							align : 'center',
							sortable : true
						}, {
							name : "defaultOrgFlag",
							sortable : true,
							hidden : true
						}, {
							name : "accountType",
							sortable : true,
							hidden : true
						}, {
							name : "primaryHeadId",
							sortable : true,
							hidden : true
						}, {
							name : "payMode",
							sortable : true,
							hidden : true
						}, {
							name : "status",
							sortable : true,
							hidden : true
						}, {
							name : 'baAccountId',
							index : 'baAccountId',
							width : 10,
							align : 'center !important',
							formatter : addLink,
							search : false
						}, ],
						pager : "#pagered",
						emptyrecords: getLocalMessage("jggrid.empty.records.text"),
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "bmBankid",
						sortorder : "comDesc",
						height : 'auto',
						viewrecords : true,
						gridview : true,
						showrow : true,
						loadonce : false,

						jsonReader : {
							root : "rows",
							page : "page",
							total : "total",
							records : "records",
							repeatitems : false,
						},
						autoencode : true,
						editurl : "StandardAccountHeadMappping.html?update",
						caption : getLocalMessage('account.configuration.account.head.mapping.list')
					});
});

function addLink(cellvalue, options, rowdata) {
	return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewForm('"
			+ rowdata.accountType
			+ "','"
			+ rowdata.primaryHeadId
			+ "','"
			+ rowdata.payMode
			+ "','"
			+ rowdata.status
			+ "')\"><i class='fa fa-building-o'></i></a> "
			+ "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"updateForm('"
			+ rowdata.accountType + "','" + rowdata.primaryHeadId + "','"
			+ rowdata.payMode + "','" + rowdata.status + "','"
			+ rowdata.statusDescription + "','" + rowdata.defaultOrgFlag
			+ "')\"><i class='fa fa-pencil'></i></a> ";
}

function viewForm(accountType, primaryHeadId, payMode, status) {

	var gr = jQuery("#grid").jqGrid('getGridParam', 'selrow');
	var url = "StandardAccountHeadMappping.html?formForView";
	var returnData = {
		"accountType" : accountType,
		"primaryHeadId" : primaryHeadId,
		"payMode" : payMode,
		"status" : status,
		"MODE" : "VIEW"
	};

	$
			.ajax({
				url : url,
				datatype : "json",
				mtype : "POST",
				data : returnData,
				success : function(response) {
					$(".widget-content").html(response);
					$(".widget-content").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
}

function updateForm(accountType, primaryHeadId, payMode, status,
		statusDescription, defaultOrgFlag) {
	var errorList = [];
	var gr = jQuery("#grid").jqGrid('getGridParam', 'selrow');
	var statusChecking = statusDescription;
	var url = "StandardAccountHeadMappping.html?formForUpdate";
	var requestData = {
		"accountType" : accountType,
		"primaryHeadId" : primaryHeadId,
		"payMode" : payMode,
		"status" : status,
		"MODE" : "UPDATE"
	};
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	if (defaultOrgFlag == "Y" && statusDescription != "Inactive") {
		$(".widget-content").html(returnData);
		$(".widget-content").show();
	} else {
		if (defaultOrgFlag != "Y") {
			errorList
					.push(getLocalMessage("account.protected.orgonization"));
		} else if (statusDescription == "Inactive") {
			errorList.push("It is already Inactive so EDIT is not allowed!");
		}
		if (errorList.length > 0) {
			showError(errorList);
			return false;
		}
	}
};

$(function() {
	$(document)
			.on(
					'click',
					'.createData',
					function() {

						var url = "StandardAccountHeadMappping.html?form";
						$
								.ajax({
									url : url,
									success : function(response) {
										$(".widget-content").html(response);
										$(".widget-content").show();
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

$(document).ready(
		function() {
			if ($('#formMode_Id').val() == "update" || ($('#formMode_Id').val() == "view")) {
			$("#payModeEditDiv").hide();
			$("#bankTypeEditDiv").hide();
			$("#depositTypeEditDiv").hide();
			$("#vendorTypeEditDiv").hide();
			$("#investmentTypeEditDiv").hide();
			$("#advanceTypeEditDiv").hide();
			$("#assetTypeEditDiv").hide();
			$("#statutoryDeductionEditDiv").hide();
			$("#loansEditDiv").hide();
			$("#bankAccountTypeEditDiv").hide();
			}
			if ($('#formMode_Id').val() == "update") {

				var payMode = $("#cpdAccountSubTypeCode").val();

				if (payMode == "PAY") {
					$("#payModeEditDiv").show();
				}
				if (payMode == "BK") {
					$("#bankTypeEditDiv").show();
				}
				if (payMode == "VN") {
					$("#vendorTypeEditDiv").show();
				}
				if (payMode == "IN") {
					$("#investmentTypeEditDiv").show();
				}
				if (payMode == "DP") {
					$("#depositTypeEditDiv").show();
				}
				if (payMode == "AD") {
					$("#advanceTypeEditDiv").show();
				}
				if (payMode == "AS") {
					$("#assetTypeEditDiv").show();
				}
				if (payMode == "SD") {
					$("#statutoryDeductionEditDiv").show();
				}
				if (payMode == "LO") {
					$("#loansEditDiv").show();
				}
				if (payMode == "BAI") {
					$("#bankAccountTypeEditDiv").show();
				}
			}

			if ($('#formMode_Id').val() == "view") {

				var payMode = $("#cpdAccountSubTypeCode").val();

				if (payMode == "PAY") {
					$("#payModeEditDiv").show();
				}
				if (payMode == "BK") {
					$("#bankTypeEditDiv").show();
				}
				if (payMode == "VN") {
					$("#vendorTypeEditDiv").show();
				}
				if (payMode == "IN") {
					$("#investmentTypeEditDiv").show();
				}
				if (payMode == "DP") {
					$("#depositTypeEditDiv").show();
				}
				if (payMode == "AD") {
					$("#advanceTypeEditDiv").show();
				}
				if (payMode == "AS") {
					$("#assetTypeEditDiv").show();
				}
				if (payMode == "SD") {
					$("#statutoryDeductionEditDiv").show();
				}
				if (payMode == "LO") {
					$("#loansEditDiv").show();
				}
				if (payMode == "BAI") {
					$("#bankAccountTypeEditDiv").show();
				}
			}

			$("#accountTypeId").chosen();
			$("#payModeId").chosen();
			$("#bankTypeId").chosen();
			$("#vendorTypeId").chosen();
			$("#depositTypeId").chosen();
			$("#advanceTypeId").chosen();
			$("#loansId").chosen();
			$("#investmentTypeId").chosen();
			$("#statutoryDeductionId").chosen();
			$("#assetId").chosen();
			$("#primaryHeadId0").chosen();
			$("#bankAccountIntTypeId").chosen();
			$('.required-control').next().children().addClass('mandColorClass')
					.attr("required", true);

		});

function viewOtherFields() {

	$("#bankTypeDiv").hide();
	$("#vendorTypeDiv").hide();
	$("#payModeDiv").hide();
	$("#depositTypeDiv").hide();
	$("#investmentTypeDiv").hide();
	$("#advanceTypeDiv").hide();
	$("#assetTypeDiv").hide();
	$("#statutoryDeductionDiv").hide();
	$("#loansDiv").hide();
	$("#bankAccountIntTypeDiv").hide(); 

	$("#primaryHeadId0").chosen().val('0');
	$("#primaryHeadId0").trigger("chosen:updated");

	if ($('#accountTypeId').find('option:selected').attr('code') == "BK") {
		$("#bankTypeId").chosen().val('0');
		$("#bankTypeId").trigger("chosen:updated");
		$("#bankTypeDiv").show();
	}
	if (($('#accountTypeId').find('option:selected').attr('code')) == "VN") {
		$("#vendorTypeId").chosen().val('0');
		$("#vendorTypeId").trigger("chosen:updated");
		$("#vendorTypeDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "PAY") {
		$("#payModeId").chosen().val('0');
		$("#payModeId").trigger("chosen:updated");
		$("#payModeDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "DP") {
		$("#depositTypeId").chosen().val('0');
		$("#depositTypeId").trigger("chosen:updated");
		$("#depositTypeDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "IN") {
		$("#investmentTypeId").chosen().val('0');
		$("#investmentTypeId").trigger("chosen:updated");
		$("#investmentTypeDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "AD") {
		$("#advanceTypeId").chosen().val('0');
		$("#advanceTypeId").trigger("chosen:updated");
		$("#advanceTypeDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "AS") {
		$("#assetId").chosen().val('0');
		$("#assetId").trigger("chosen:updated");
		$("#assetTypeDiv").show();
	}
	if (($('#accountTypeId').find('option:selected').attr('code')) == "SD") {
		$("#statutoryDeductionId").chosen().val('0');
		$("#statutoryDeductionId").trigger("chosen:updated");
		$("#statutoryDeductionDiv").show();
	}
	if (($('#accountTypeId').find('option:selected').attr('code')) == "LO") {
		$("#loansId").chosen().val('0');
		$("#loansId").trigger("chosen:updated");
		$("#loansDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "FAD") {
		$("#assetId").chosen().val('0');
		$("#assetId").trigger("chosen:updated");
		$("#assetTypeDiv").show();
	}

	if (($('#accountTypeId').find('option:selected').attr('code')) == "ADP") {
		$("#assetId").chosen().val('0');
		$("#assetId").trigger("chosen:updated");
		$("#assetTypeDiv").show();
	}
	if ($('#accountTypeId').find('option:selected').attr('code') == "BAI") {
		$("#bankAccountIntTypeId").chosen().val('0');
		$("#bankAccountIntTypeId").trigger("chosen:updated");
		$("#bankAccountIntTypeDiv").show();
	}

}

function validateAccountCodeDesc(idcount) {

	var errorList = [];
	var formMode_Id = $("#formMode_Id").val();

	var pacHeadId = $('#primaryHeadId' + idcount).val();

	var Revid = idcount;
	if (pacHeadId != "") {
		var dec;
		for (m = 0; m <= Revid; m++) {
			for (dec = 0; dec <= Revid; dec++) {
				if (m != dec) {
					if (($('#primaryHeadId' + m).val() == $(
							'#primaryHeadId' + dec).val())) {
						errorList
								.push("The Combination Primary Account Head already exists!");
						$("#primaryHeadId" + idcount).val("");
						$("#primaryHeadId" + idcount).val('').trigger(
								'chosen:updated');
					}
				}
			}
		}
	}

	var accountType = $("#accountTypeId").val();
	if (accountType == 0 || accountType == "") {
		errorList.push(getLocalMessage('account.select.accType'));
		$("#primaryHeadId" + idcount).chosen().val('0');
		$("#primaryHeadId" + idcount).trigger("chosen:updated");
	}

	var payMode = $('#accountTypeId').find('option:selected').attr('code');

	var accountSubType = null;
	if (payMode == "PAY") {
		var payModeId = $("#payModeId").val();
		if (payModeId == 0 || payModeId == "") {
			errorList.push(getLocalMessage('account.select.payMode'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#payModeId").val();
		}
	}
	if (payMode == "BK") {
		var bankTypeId = $("#bankTypeId").val();
		if (bankTypeId == 0 || bankTypeId == "") {
			errorList.push(getLocalMessage('account.select.bankType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#bankTypeId").val();
		}
	}
	if (payMode == "VN") {
		var vendorTypeId = $("#vendorTypeId").val();
		if (vendorTypeId == 0 || vendorTypeId == "") {
			errorList.push(getLocalMessage('vendormaster.validator.vendortype'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#vendorTypeId").val();
		}
	}
	if (payMode == "DP") {
		var depositTypeId = $("#depositTypeId").val();
		if (depositTypeId == 0 || depositTypeId == "") {
			errorList.push(getLocalMessage('account.select.depositType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#depositTypeId").val();
		}
	}
	if (payMode == "AD") {
		var advanceTypeId = $("#advanceTypeId").val();
		if (advanceTypeId == 0 || advanceTypeId == "") {
			errorList.push(getLocalMessage('account.select.advanceType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#advanceTypeId").val();
		}
	}
	if (payMode == "AS") {
		var assetId = $("#assetId").val();
		if (assetId == 0 || assetId == "") {
			errorList.push(getLocalMessage('account.select.assetType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#assetId").val();
		}
	}
	if (payMode == "SD") {
		var statutoryDeductionId = $("#statutoryDeductionId").val();
		if (statutoryDeductionId == 0 || statutoryDeductionId == "") {
			errorList
					.push(getLocalMessage('account.select.statutory.deductionType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#statutoryDeductionId").val();
		}
	}
	if (payMode == "LO") {
		var loansId = $("#loansId").val();
		if (loansId == 0 || loansId == "") {
			errorList.push(getLocalMessage('account.select.loanType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#loansId").val();
		}
	}
	if (payMode == "IN") {
		var investmentTypeId = $("#investmentTypeId").val();
		if (investmentTypeId == 0 || investmentTypeId == "") {
			errorList.push(getLocalMessage('account.select.investmentType'));
			$("#primaryHeadId" + idcount).chosen().val('0');
			$("#primaryHeadId" + idcount).trigger("chosen:updated");
		} else {
			accountSubType = $("#investmentTypeId").val();
		}
	}
	var primaryHeadId = $("#primaryHeadId" + idcount).val();
	var validateUrl = "StandardAccountHeadMappping.html?validateAccountCodeDesc";
	var reqData = {
		"primaryHeadId" : primaryHeadId,
		"accountType" : accountType,
		"accountSubType" : accountSubType,
	};
	var isDuplicateCode = __doAjaxRequestForSave(validateUrl, 'post', reqData,
			false, '');
	if (isDuplicateCode) {
		errorList
				.push(getLocalMessage('account.primaryAccHead.already.mapped'));
		$("#primaryHeadId" + idcount).chosen().val('0');
		$("#primaryHeadId" + idcount).trigger("chosen:updated");
		$("#accountNo" + idcount).val('');
	}

	if (errorList.length > 0) {
		showError(errorList);
		return false;
	} else {
		$('#errorDivId').hide();
	}

}

function checkingStatusPaymode(obj) {

	var errorList = [];
	var formMode_Id = $("#formMode_Id").val();

	$("#primaryHeadId0").val(0);
	$("#primaryHeadId0").trigger("chosen:updated");

	var accountType = $("#accountTypeId").val();
	if (accountType == 0 || accountType == "") {
		errorList.push(getLocalMessage('account.select.accType'));
		$("#payModeId").chosen().val('0');
		$("#payModeId").trigger("chosen:updated");
	}
	var payModeId = $("#payModeId").val();
	var validateUrl = "StandardAccountHeadMappping.html?checkingStatusPaymode";
	var reqData = {
		"accountType" : accountType,
		"payModeId" : payModeId,
	};

	var isDuplicateCode = __doAjaxRequestForSave(validateUrl, 'post', reqData,
			false, '');

	if (isDuplicateCode) {
		errorList.push(getLocalMessage('account.payMode.already.mapped'));

		$("#payModeId").chosen().val('0');
		$("#payModeId").trigger("chosen:updated");

	}

	if (errorList.length > 0) {
		showError(errorList);
		return false;
	} else {
		$('#errorDivId').hide();
	}

	if (errorList.length == 0) {

		var divName = ".widget-content";

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "StandardAccountHeadMappping.html?getPrimaryHeadCodeDesc";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		$(divName).show();

		if ($('#accountTypeId').find('option:selected').attr('code') == "BK") {
			$("#bankTypeDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "VN") {
			$("#vendorTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "PAY") {
			$("#payModeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "DP") {
			$("#depositTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "IN") {
			$("#investmentTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "AD") {
			$("#advanceTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "AS") {
			$("#assetTypeDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "SD") {
			$("#statutoryDeductionDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "LO") {
			$("#loansDiv").show();
		}
	}
}
function saveForm(element) {
	var errorList = [];
	var formMode_Id = $("#formMode_Id").val().toUpperCase();
	var status = $("#status").val();

	if (formMode_Id == "CREATE") {
		var accountType = $("#accountTypeId").val();
		if (accountType == 0 || accountType == "") {
			errorList.push(getLocalMessage('account.select.accType'));
		}

		var payMode = $('#accountTypeId').find('option:selected').attr('code');

		if (payMode == "PAY") {
			var payModeId = $("#payModeId").val();
			if (payModeId == 0 || payModeId == "") {
				errorList.push(getLocalMessage('account.select.payMode'));
			}
		}
		if (payMode == "BK") {
			var bankTypeId = $("#bankTypeId").val();
			if (bankTypeId == 0 || bankTypeId == "") {
				errorList.push(getLocalMessage('account.select.bankType'));
			}
		}
		if (payMode == "VN") {
			var vendorTypeId = $("#vendorTypeId").val();
			if (vendorTypeId == 0 || vendorTypeId == "") {
				errorList.push(getLocalMessage('vendormaster.validator.vendortype'));
			}
		}
		if (payMode == "DP") {
			var depositTypeId = $("#depositTypeId").val();
			if (depositTypeId == 0 || depositTypeId == "") {
				errorList.push(getLocalMessage('account.select.depositType'));
			}
		}
		if (payMode == "AD") {
			var advanceTypeId = $("#advanceTypeId").val();
			if (advanceTypeId == 0 || advanceTypeId == "") {
				errorList.push(getLocalMessage('account.select.advanceType'));
			}
		}
		if (payMode == "AS") {
			var assetId = $("#assetId").val();
			if (assetId == 0 || assetId == "") {
				errorList.push(getLocalMessage('account.select.assetType'));
			}
		}
		if (payMode == "SD") {
			var statutoryDeductionId = $("#statutoryDeductionId").val();
			if (statutoryDeductionId == 0 || statutoryDeductionId == "") {
				errorList
						.push(getLocalMessage('account.select.statutory.deductionType'));
			}
		}
		if (payMode == "LO") {
			var loansId = $("#loansId").val();
			if (loansId == 0 || loansId == "") {
				errorList.push(getLocalMessage('account.select.loanType'));
			}
		}
		if (payMode == "IN") {
			var investmentTypeId = $("#investmentTypeId").val();
			if (investmentTypeId == 0 || investmentTypeId == "") {
				errorList
						.push(getLocalMessage('account.select.investmentType'));
			}
		}

	}
	if (formMode_Id == "UPDATE") {

		if (status == 0 || status == "") {
			errorList.push(getLocalMessage("account.select.status"));
		}
	}

	$('.mappingTrClass')
			.each(
					function(i) {

						for (var m = 0; m <= i; m++) {

							var primaryHeadId = $("#primaryHeadId" + m).val();

							if (primaryHeadId == 0 || primaryHeadId == ""
									|| primaryHeadId == null
									|| primaryHeadId == undefined) {
								errorList
										.push(getLocalMessage("account.select.acchead"));
							}
						}
					});
	if (errorList.length > 0) {
		showError(errorList);
		return false;
	}
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;

	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');

	var status = __doAjaxRequestForSave(url, 'post', requestData, false, '',
			element);
	var obj = $(status).find('#successFlag');
	if (obj.val() == 'Y') {
		showConfirmBox();
	} else if (obj.val() == 'U') {
		showConfirmBoxUpdate();
	} else {
		$(".widget-content").html(status);
		$(".widget-content").show();
	}

}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("account.proceed.btn");
	var successmsg = getLocalMessage("accounts.fieldmaster.success");

	message += '<h4 class=\"text-center text-blue-2 margin-bottom-10\">'+ successmsg +'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2\' '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	showModalBoxWithoutClose(errMsgDiv);

}

function showConfirmBoxUpdate() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("account.proceed.btn"); 
	var msg=getLocalMessage("accounts.fieldmaster.update");
	message += '<h4 class=\"text-center text-blue-2 margin-bottom-10\">'+msg+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2\' '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	showModalBoxWithoutClose(errMsgDiv);

}

function proceed() {
	window.location.href = 'StandardAccountHeadMappping.html';
}

function searchAccountData() {

	var accountType = $("#accountType").val();

	if (accountType == '' || accountType == '0') {
		var errMsg = getLocalMessage("account.select.accType");
		$('#errorId').html(errMsg);
		$('#errorDivId').show();

	} else {
		var payMode = $('#accountType').find('option:selected').attr('code');

		var accountSubType = null;
		if (payMode == "PAY") {
			accountSubType = $("#payModeId").val();
		}
		if (payMode == "BK") {
			accountSubType = $("#bankTypeId").val();
		}
		if (payMode == "VN") {
			accountSubType = $("#vendorTypeId").val();
		}
		if (payMode == "DP") {
			accountSubType = $("#depositTypeId").val();
		}
		if (payMode == "AD") {
			accountSubType = $("#advanceTypeId").val();
		}
		if (payMode == "AS") {
			accountSubType = $("#assetId").val();
		}
		if (payMode == "SD") {
			accountSubType = $("#statutoryDeductionId").val();
		}
		if (payMode == "LO") {
			accountSubType = $("#loansId").val();
		}
		if (payMode == "IN") {
			accountSubType = $("#investmentTypeId").val();
		}
		if (payMode == "BAI") {
			accountSubType = $("#bankAccountIntTypeId").val();
		}

		$('.error-div').hide();

		var url = "StandardAccountHeadMappping.html?searchAccountType";
		var returnData = {
			"accountType" : accountType,
			"accountSubType" : accountSubType
		};

		var result = __doAjaxRequest(url, 'POST', returnData, false, 'json');

		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];

			errorList
					.push(getLocalMessage("account.norecord.criteria"));

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
}

function showError(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
}

$("#headMapingTable").on("click", '.addbtn', function(e) {
	var errorList = [];
	$('.mappingTrClass').each(function(i) {
		var accountType = $("#accountTypeId").val();
		if (accountType == 0 || accountType == "") {
			errorList.push(getLocalMessage('account.select.accType'));
		}
		var primaryHeadId = $("#primaryHeadId" + i).val();
		var status = $("#status" + i).val();
		var level = i + 1;
		if (primaryHeadId == 0 || primaryHeadId == "") {
			errorList.push(getLocalMessage("account.select.acchead"));
		}
	});

	if (errorList.length > 0) {
		showError(errorList);
		return false;
	}

	var content = $(this).closest('#mappingTable tr').clone();
	$(this).closest("#mappingTable").append(content);
	content.find("select").attr("value", "");
	content.find('div.chosen-container').remove();
	content.find("select:eq(0)").chosen().trigger("chosen:updated");
	reOrderTableIdSequence();
	e.preventDefault();
});

function reOrderTableIdSequence() {

	$('.mappingTrClass').each(
			function(i) {

				$(this).find("select:eq(0)").attr("id", "primaryHeadId" + i);
				$(this).find("select:eq(1)").attr("id", "status" + i);

				$(this).find(".addbtn").attr("id", "addButton" + i);
				$(this).find(".delButton").attr("id", "delButton" + i);

				$(this).find("#primaryHeadId" + i).attr("onchange",
						"validateAccountCodeDesc(" + (i) + ")");

				$(this).find("select:eq(0)").attr("name",
						"primaryHeadMappingList[" + i + "].primaryHeadId");
				$(this).find("select:eq(1)").attr("name",
						"primaryHeadMappingList[" + i + "].status");

				onchange = "validateAccountCodeDesc(0);"

				$(this).closest("tr").attr("id", "tr" + (i));

			});
}

$("#headMapingTable").on("click", '.delButton', function(e) {
	var errorList = [];
	var rowCount = $('#headMapingTable tr').length;

	if (rowCount <= 2) {
		errorList.push(getLocalMessage("account.first.row.not.delete"));
	}
	if (errorList.length > 0) {
		showError(errorList);
		return false;
	}
	$(this).closest('#headMapingTable tr').remove();

	reOrderTableIdSequence();
	e.preventDefault();
});

function viewOtherFieldsGridPage() {

	$("#bankTypeDivGrid").hide();
	$("#vendorTypeDivGrid").hide();
	$("#payModeDivGrid").hide();
	$("#depositTypeDivGrid").hide();
	$("#investmentTypeDivGrid").hide();
	$("#advanceTypeDivGrid").hide();
	$("#assetTypeDivGrid").hide();
	$("#statutoryDeductionDiv").hide();
	$("#loansDiv").hide();
	$("#bankAccountIntTypeDivGrid").hide(); 
	 
	if ($('#accountType').find('option:selected').attr('code') == "BK") {
		$("#bankTypeDivGrid").show();
	}
	if (($('#accountType').find('option:selected').attr('code')) == "VN") {
		$("#vendorTypeDivGrid").show();
	}

	if (($('#accountType').find('option:selected').attr('code')) == "PAY") {
		$("#payModeDivGrid").show();
	}

	if (($('#accountType').find('option:selected').attr('code')) == "DP") {
		$("#depositTypeDivGrid").show();
	}

	if (($('#accountType').find('option:selected').attr('code')) == "IN") {
		$("#investmentTypeDivGrid").show();
	}

	if (($('#accountType').find('option:selected').attr('code')) == "AD") {
		$("#advanceTypeDivGrid").show();
	}

	if (($('#accountType').find('option:selected').attr('code')) == "AS") {
		$("#assetTypeDivGrid").show();
	}
	if (($('#accountType').find('option:selected').attr('code')) == "SD") {
		$("#statutoryDeductionDiv").show();
	}
	if (($('#accountType').find('option:selected').attr('code')) == "LO") {
		$("#loansDiv").show();
	}
	if (($('#accountType').find('option:selected').attr('code')) == "BAI") {
		$("#bankAccountIntTypeDivGrid").show();
	}
}

function getPrimaryHeadDescOnPayMode(obj) {

	$('.error-div').hide();
	var errorList = [];

	$("#primaryHeadId0").val(0);
	$("#primaryHeadId0").trigger("chosen:updated");

	var accountType = $("#accountTypeId").val();

	if (accountType == 0 || accountType == "") {
		errorList.push(getLocalMessage('account.select.accType'));
	}

	if (errorList.length > 0) {
		showError(errorList);
		return false;
	}

	if (errorList.length == 0) {

		var divName = ".widget-content";

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "StandardAccountHeadMappping.html?getPrimaryHeadCodeDesc";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		$(divName).show();

		if ($('#accountTypeId').find('option:selected').attr('code') == "BK") {
			$("#bankTypeDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "VN") {
			$("#vendorTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "PAY") {
			$("#payModeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "DP") {
			$("#depositTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "IN") {
			$("#investmentTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "AD") {
			$("#advanceTypeDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "AS") {
			$("#assetTypeDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "SD") {
			$("#statutoryDeductionDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "LO") {
			$("#loansDiv").show();
		}

		if (($('#accountTypeId').find('option:selected').attr('code')) == "FAD") {
			$("#assetTypeDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "ADP") {
			$("#assetTypeDiv").show();
		}
		if (($('#accountTypeId').find('option:selected').attr('code')) == "BAI") {
			$("#bankAccountIntTypeDiv").show();
		}
	}
};

function clearPrimaryAcHeadData(obj) {

	$("#primaryHeadId0").val(0);
	$("#primaryHeadId0").trigger("chosen:updated");
}



function displaySelectedGrid(){
	debugger;
	var flValue=  $('#accountTypeId').find('option:selected').attr('code');
	if ( flValue == "BK") {
		$("#bankTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}
	else if (flValue == "VN") {
		$("#vendorTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}

	else if (flValue == "PAY") {
		$("#payModeDiv").show();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}

	else if (flValue == "DP") {
		$("#depositTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}

	else if (flValue == "IN") {
		$("#investmentTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}

	else if (flValue == "AD") {
		$("#advanceTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}

	else if (flValue == "AS") {
		$("#assetTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide();  
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}
	else if (flValue == "SD") {
		$("#statutoryDeductionDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide();
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}
	else if (flValue == "LO") {
		$("#loansDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}

	else if (flValue == "FAD") {
		$("#assetTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}
	else if (flValue == "ADP") {
		$("#assetTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
	}
	else if (flValue == "BAI") {
		$("#bankAccountIntTypeDiv").show();
		$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#assetTypeDiv").hide();
	}else{
			$("#payModeDiv").hide();
		   $("#bankTypeDiv").hide(); 
		   $("#depositTypeDiv").hide(); 
		   $("#vendorTypeDiv").hide(); 
		   $("#investmentTypeDiv").hide(); 
		   $("#advanceTypeDiv").hide(); 
		   $("#assetTypeDiv").hide(); 
		   $("#statutoryDeductionDiv").hide(); 
		   $("#loansDiv").hide(); 
		   $("#bankAccountIntTypeDiv").hide();
		   
	}
	
}
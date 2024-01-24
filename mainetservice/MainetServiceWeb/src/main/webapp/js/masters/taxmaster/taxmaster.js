/**
 * Harsha
 */

/*
 * document.ready function begins
 */

$(document)
		.ready(
				function() {
                 
					if($("#isAccountIntgMandi").val()=='N' && $("#isAccountIntgMandi").val()==""){
						$("#budgetcode-div").hide();
					}

					$("#dependsOnFac-div").hide();
					$("#errorDivTaxMas").hide();

					$("input").on("keypress", function(e) {
						if (e.which === 32 && !this.value.length)
							e.preventDefault();
					});
					var mode = $('#formModeId').val();

					if (mode == 'edit') {

						$("#dependsOnFac-div").show();
						$('#taxDesc').prop("disabled", true);
						$('#taxDesc-label').removeClass('required-control');
						$('#taxList').prop("readonly", true);

						var len = $('#budget-tbl tr').length - 1;
						for (var i = 0; i < len; i++) {
							if ($('#budgetcodeList' + i).val() != null
									&& $('#budgetcodeList' + i).val() != "") {
								$('#budgetcodeList' + i).attr("disabled", true);
							} else {
								$('#budgetcodeList' + i)
										.attr("disabled", false);
							}
						}

						if ($("#activeChkBox").val() == 'Y') {
							$("#taxActive").prop("checked", true);
						}

						if ($('#budgetList').val() != null) {
							if ($('#taxBudgetBean').val() == null
									|| $('#taxBudgetBean').val() == '[]') {
								getBudgetCodeList();
							} else {
								$(this).find('.removeClass').hide();
								$('.budgetClass').find('isBudgetCode').each(
										function() {
											$(this).addClass('mandColorClass')
													.attr('disabled', true);
										});
							}
						}
						$('#action-th').show();
						$('#action-td').show();
					} else if (mode == 'view') {
						$("#dependsOnFac-div").show();
						if ($('#taxBudgetBean').val() != null
								&& $('#taxBudgetBean').val() != "[]") {
							$('#budgetcode-div').show();
						} else {
							$('#budgetcode-div').hide();
						}
						$('#taxForm input').prop('disabled', true);
						$('#taxForm select').prop('disabled', true);
						$('#taxForm textarea').prop('disabled', true);
						$('#taxForm checkbox').prop('disabled', true);

						$('#right').attr('disabled', true);
						$('#rightall').attr('disabled', true);
						$('#left').attr('disabled', true);
						$('#leftall').attr('disabled', true);

						$('.addClass').attr('disabled', true);
						$('.removeClass').attr('disabled', true);
						$('#backBtn').prop('disabled', false);

						$('#action-th').show();
						$('#action-td').show();
					} else {
						$('#budgetStatus0').val('A').prop('disabled',
								'disabled');
						$('.budgetClass').find('select').each(function() {
							$(this).addClass('mandColorClass');
						});
						$(".chosen-select-no-results")
								.trigger("chosen:updated");
						$('#action-th').show();
						$('#action-td').show();
					}

					$('#taxValueType')
							.on(
									'change',
									function(e) {
										$('#taxGroup').prop('selectedIndex', 0);
										$("#taxDisplaySeqId").val("");
										$("#collSeqId").val("");
										serviceOptionRemove();
										if ($('#taxValueType')
												.find(':selected').attr('code') === 'BILL'
												|| $('#taxValueType').find(
														':selected').attr(
														'code') === 'RCPT') {
											$("#taxGroup option[code='ST']")
													.hide();
											$("#taxGroup option[code='GVT']")
													.show();
											$("#taxGroup option[code='NGT']")
													.show();
											$("#taxDisplaySeq").prop(
													"disabled", false).val('');
											$("#collSeq").prop("disabled",
													false).val('');

										} else {
											$("#taxGroup option[code='ST']")
													.show();
											$("#taxGroup option[code='GVT']")
													.hide();
											$("#taxGroup option[code='NGT']")
													.hide();
											$("#taxDisplaySeq").prop(
													"disabled", true).val('');
											$("#collSeq")
													.prop("disabled", true)
													.val('');
										}

										if ($('#taxValueType')
												.find(':selected').attr('code') === 'BILL') {
											closeOutErrBox();
											var errorList = [];
											var taxApplicableAt = $(
													"#taxValueType").val();
											var deptId = $("#dpDeptId").val();
											if (deptId == "" || deptId == null
													|| deptId == undefined) {
												errorList
														.push('please select department.');
												$('#taxValueType').prop(
														'selectedIndex', 0);
											}
											if (errorList.length == 0) {
												var reqData = {
													"dpDeptId" : deptId,
													"taxApplicableAt" : taxApplicableAt,
												};
												var responseObj = __doAjaxRequestForSave(
														"TaxMaster.html?getNextSequences",
														'POST', reqData, false,
														'', '');
												$("#taxDisplaySeqId").val(
														responseObj[0]);
												$("#collSeqId").val(
														responseObj[1]);
											} else {
												displayErrorsOnPage(errorList);
											}
										}
									});

					if ($("#serviceId").val() == ""
							|| $("#serviceId").val() == null) {
						serviceOptionRemove();
					}

					if ($("#taxGroup").val() == "0"
							|| $("#taxGroup").val() == null) {
						$('.govtTax').hide();
					}

					$('#taxGroup').on("change", function(obj) {
						getServiceList(obj);
					});

				});

function getServiceList(obj) {
	if ($('#taxGroup').find(':selected').attr('code') === 'ST') {
		if ($("#dpDeptId").val() > 0) {
			var requestData = {
				dpDeptId : $("#dpDeptId").val(),
			};

			// var formName = findClosestElementId(obj, 'form');
			var url = "TaxMaster.html?serviceList";
			var returnData = __doAjaxRequestForSave(url, 'post', requestData,
					false, '', obj);
			if (returnData != "") {
				$("#serviceId").prop("disabled", false);
				$.each(returnData, function(index, value) {
					$("#serviceId").append(
							$("<option></option>").attr("value",
									value.smServiceId).attr("code",
									value.smServiceId)
									.text(value.smServiceName));
				});

				$("#serviceId-lbl").addClass('required-control');

			}

		} else {
			$('#taxGroup').prop('selectedIndex', 0);
			showErrormsgboxTitle(getLocalMessage('tax.error.dpDept'));
		}
	} else {
		serviceOptionRemove();
	}
	$(".chosen-select-no-results").trigger("chosen:updated");
}

function getTaxList() {
	$("#errorDivTaxMas").hide();
	$("#taxList").html('');
	$("#taxList").text('');

	var errorList = [];

	if ($('#dpDeptId').val() > 0) {
		var requestData = {
			deptId : $("#dpDeptId").val(),
		};
		var url = "TaxMaster.html?taxList";
		var returnData = __doAjaxRequest(url, 'post', requestData, false, '',
				'');
		if (returnData != "" || returnData.length > 0) {
			$("#taxList")
					.append(
							$("<option></option>").attr("value", "").text(
									"Select Tax"));
			$.each(returnData, function(index, value) {
				if (value.lookUpId != null) {
					$("#taxList").append(
							$("<option></option>")
									.attr("value", value.lookUpId).text(
											value.lookUpDesc));
				} else {
					errorList.push(getLocalMessage('tax.error.notax'));
					showTaxError(errorList);
				}
			});
		} else {

			errorList.push(getLocalMessage('tax.error.notax'));
			showTaxError(errorList);
			$("#taxList")
					.append(
							$("<option></option>").attr("value", "").text(
									"Select Tax"));
		}

	} else if ($('#dpDeptId').val() == '') {
		$("#taxList").append(
				$("<option></option>").attr("value", "").text("Select Tax"));
	}
	$(".chosen-select-no-results").trigger("chosen:updated");
}

function getTaxcodeList() {
	$("#parentTaxCode").prop("disabled", true);
	$('#parentTaxCode').find('option:not(:first)').remove();
	if ($('#dpDeptId').val() > 0) {
		var requestData = {
			dpDeptId : $("#dpDeptId").val(),
		};

		var url = "TaxMaster.html?taxCodeList";
		var returnData = __doAjaxRequest(url, 'POST', requestData, false,
				'json');

		if (returnData != "") {
			$.each(returnData, function(index, value) {
				$("#parentTaxCode").append(
						$("<option></option>").attr("value", value.taxId).text(
								value.taxCode + ">>" + value.taxDesc));
			});
			$("#parentTaxCode").prop("disabled", false);
		}
	} else {
		showErrormsgboxTitle(getLocalMessage('tax.error.dpDept'));
	}
	$(".chosen-select-no-results").trigger("chosen:updated");
}

function serviceOptionRemove() {
	$("#serviceId").prop("disabled", true);
	$("#serviceId-lbl").removeClass('required-control');
	$('#serviceId').find('option:not(:first)').remove();
}

$('#taxActive').change(function() {
	if ($('input:checkbox[id=taxActive]').is(':checked')) {

		$('#taxActive').attr('checked', true);
		$('#taxActive').val(true);
	} else {
		$('#taxActive').attr('checked', false);
		$('#taxActive').val(false);

	}

});
function saveTaxMasterForm(obj) {
	$("#errorDivTaxMas").hide();
	$('#taxDetMasList option').not(':selected').prop('selected', 'selected');
	var errorList = [];
	var dpDeptId = 0;

	if ($('#formModeId').val() == 'create') {
		dpDeptId = $("#dpDeptId").val();
	} else {
		dpDeptId = $("#deptId").val();
	}

	var taxDescId = $("#taxList").val();

	var taxMethod = $("#taxMethod").val();
	var taxGroup = $("#taxGroup").val();
	var taxCategory1 = $("#taxCategory1").val();
	var taxCategory2 = $("#taxCategory2").val();
	var serviceId = $('#serviceId').val();
	var taxAppl = $('#taxValueType').val();
	var displaySeq = $('#taxDisplaySeqId').val();
	var collSeqId = $("#collSeqId").val();

	var activePresent = false;
	var activeBudgetCount = 0;
	var budgetCodeCount = 0;
	var budgetStatusCount = 0;
	var budgetdmdClassCount = 0;
	// var budgetPresent = false;

	if (taxDescId == '' || taxDescId == '0') {
		errorList.push(getLocalMessage('tax.error.taxDesc'));
	}
	if (displaySeq == '' || displaySeq == '0') {
		errorList.push(getLocalMessage('tax.error.taxDespDesq'));	
	}
	if (collSeqId == '' || collSeqId == '0') {
		errorList.push(getLocalMessage('tax.error.taxCollectionDesq'));
	}
	
	if (dpDeptId == '' || dpDeptId == '0') {
		errorList.push(getLocalMessage('tax.error.dpDept'));
	}
	if (taxAppl == '' || taxAppl == '0') {
		errorList.push(getLocalMessage('tax.error.taxValueType'));
	}
	if (taxMethod == '' || taxMethod == '0') {
		errorList.push(getLocalMessage('tax.error.taxMethod'));
	}
	if (taxGroup == '' || taxGroup == "0") {
		errorList.push(getLocalMessage('tax.error.taxGroup'));
	}
	if (taxCategory1 == '0') {
		errorList.push(getLocalMessage('tax.error.taxCategory1'));
	}
	if (taxCategory2 == '0') {
		errorList.push(getLocalMessage('tax.error.taxCategory2'));
	}
	if ($('#taxGroup').find(':selected').attr('code') === 'ST'
			&& serviceId === '') {
		if ($('#serviceId').is(':enabled')) {
			errorList.push(getLocalMessage('tax.error.services'));
		}
	}

	if ($('#taxPrintOn1').prop('checked') == false
			&& $('#taxPrintOn2').prop('checked') == false
			&& $('#taxPrintOn3').prop('checked') == false) {
		errorList.push(getLocalMessage('tax.error.printOn'));
	}

	if ($('#dependsOnFac-div').is(':visible')) {
		if ($("#taxDetMasList option").length == 0) {
			errorList.push(getLocalMessage('tax.error.taxDetMasList'));
		} else {
			$('#taxDetMasList option').not(':selected').attr('selected',
					'selected');
		}
	}

	if ($('#taxValueType').find(':selected').attr('code') === 'B') {
		if (taxDisplaySeq == '') {
			errorList.push("Display Sequence must not empty");
		} else if (taxDisplaySeq == 0) {
			errorList.push("Display Sequence 0 is not allowed");
		}
		if (collSeq == '') {
			errorList.push("Collection Sequence must not empty");
		} else if (collSeq == 0) {
			errorList.push("Collection Sequence 0 is not allowed");
		}
	}

	if ($('#budgetcode-div').is(':visible')) {
		$('.budgetClass').each(function(i) {
			if ($('#budgetStatus' + i).find(':selected').attr('code') === "A") {
				activeBudgetCount++;
				activePresent = true;
			} else if ($("#budgetStatus" + i).val() == "") {
				budgetStatusCount++;

			}

			if ($("#budgetcodeList" + i).val() == "") {
				budgetCodeCount++;
			}
			
			if ($("#demandcodeList" + i).val() == "") {
				budgetdmdClassCount++;
			}
		});

		if (!activePresent) {
			errorList.push(getLocalMessage('tax.error.budgetCode'));
		}

			if (activeBudgetCount >4) {
				errorList.push(getLocalMessage('tax.error.budgetCode.active'));
			}

		if (budgetCodeCount > 0) {
			errorList.push(getLocalMessage('tax.error.addbudget.budgetcode'));
		}

		if (budgetStatusCount > 0) {
			errorList.push(getLocalMessage('tax.error.addbudget.budgetstatus'));
		}
		
		if (budgetdmdClassCount > 0) {
			errorList.push(getLocalMessage('tax.error.addbudget.dmdClass'));
		}
		
		var arr = [];
		$(".dmdClass").each(function(i){
			if($("#budgetStatus" + i).val()==="A"){
		    var value = $(this).val();
		    if (arr.indexOf(value) == -1){
		        arr.push(value);}
		    else{
		    	errorList.push(getLocalMessage('tax.error.addbudget.uniqueClassification'));
		    	  return false; 
		    }
			}
		});
		
	}

	var checkDup = checkforDuplicate();
	if (checkDup == true) {
		errorList.push("The Combination selected for Account already exists!");
	}

	var taxCode = $('#taxCode').val();
	var taxValid = validateTax(taxDescId, dpDeptId, taxAppl, taxMethod,
			taxGroup, taxCategory1, taxCategory2, taxCode, serviceId);
	if (taxValid == false) {
		errorList.push(getLocalMessage('tax.error.validateTax'));
	}

	if (errorList.length > 0) {
		showTaxError(errorList);
	} else {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var url = $(theForm).attr('action');
		var returnData = __doAjaxRequestForSave(url, 'post', requestData,
				false, '', obj);
		if ($.isPlainObject(returnData)) {
			var msg = "Record Saved Successfully";
			showConfirmBox(msg, 1);
			return false;
		} else {
			$(".content").html(returnData);
			$(".content").show();
		}
	}
	return false;
}

function checkforDuplicate() {

	var len = $('#budget-tbl tr').length - 1;
	for (var i = 0; i < len; i++) {
		for (var j = i + 1; j < len; j++) {
			if (($('#budgetcodeList' + i).val() == $('#budgetcodeList' + j)
					.val())
					&& ($('#budgetStatus' + i).val() == $('#budgetStatus' + j)
							.val())
				&& ($('#demandcodeList' + i).val() == $('#demandcodeList' + j)
						.val())){
				return true;
			}
		}
	}
	return false;
}

function closeBox() {
	$.fancybox.close();
	return false;
}

function showConfirmBox(msg, val) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var okmsg = 'OK';
	if (val == 1) {
		cls = 'Proceed';

		message += '<h4 class=\"text-center text-blue-2 padding-10\">' + msg
				+ '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceed()"/>' + '</div>';
	} else {

		message += '<h4 class=\"text-center text-blue-2 padding-10\">' + msg
				+ '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + okmsg
				+ '\'  id=\'btnNo1\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="closeBox()"/>' + '</div>';
	}

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	if (val == 1) {
		showModalBoxWithoutClose(errMsgDiv);
	} else {
		showModalBox(errMsgDiv);
	}
	return false;
}

function proceed() {
	window.location.href = 'TaxMaster.html';
}

function dateFormat(value) {
	var date = value;
	var chunks = date.split('/');
	var formatDate = chunks[1] + '/' + chunks[0] + '/' + chunks[2];
	var newDate = new Date(formatDate);
	return newDate;
}

function setDepartment(obj) {
	$("#errorDivTaxMas").hide();
	$("#taxDisplaySeq").val('');
	$("#collSeq").val('');
	$("#deptCode").val($(obj).find('option:selected').attr('code'));
	serviceOptionRemove();
	if ($('#taxGroup').find(':selected').attr('code') === 'ST') {
		getServiceList(obj);
	}
	getDependsOnFactor();
	getTaxcodeList();

}

function getDependsOnFactor() {
	$("#eventMapSelectedId").html('');
	$("#eventMapSelectedId").text('');
	$("#dependsOnFac-div").show();

	var deptCode = $("#dpDeptId :selected").attr('code');
	var requestData = {
		deptCode : deptCode
	};
	var url = "TaxMaster.html?getDependsOnFactor";
	var returnData = __doAjaxRequestForSave(url, 'get', requestData, false, '',
			'');
	if (returnData.length != 0) {

		$.each(returnData, function(index, value) {
			$("#eventMapSelectedId").append(
					$("<option></option>").attr("value", value.lookUpId).text(
							value.lookUpDesc));
		});
	}
	$(".chosen-select-no-results").trigger("chosen:updated");
}

function getBudgetCodeList() {

	$("#budgetcodeList0").html('');
	$("#budgetcodeList0").text('');

	var url = "TaxMaster.html?getBudgetCodeList";
	var returnData = __doAjaxRequestForSave(url, 'get', '', false, '', '');
	$('#budgetStatus0 option[code="A"]').attr('selected', 'selected');
	if (returnData.length != 0) {
		$("#budgetcode-div").show();
		$("#budgetcodeList0").append(
				$("<option></option>").attr("value", "").text("Select"));

		$.each(returnData, function(index, value) {
			$("#budgetcodeList0").append(
					$("<option></option>").attr("value", value.sacHeadId).text(
							value.acHeadCode));
		});
		$("#budgetcode-div").show();
		$('#budgetcode-div').find('select').each(function() {
			$(this).addClass('mandColorClass');

		});
	} else {
		$('#budgetStatus0 option[value=""]').attr('selected', 'selected');
		$("#budgetcode-div").hide();
	}
	$(".chosen-select-no-results").trigger("chosen:updated");
}

function validateSequence(obj, dispSeq) {
	closeOutErrBox();
	var errorList = [];
	var savedSeq = '';
	var formMode = $("#formModeId").val();
	var deptId = $("#dpDeptId").val();
	var taxApplicableAt = $("#taxValueType").val();


	if (deptId == "" || deptId == null || deptId == undefined) {
		errorList.push('please select department.');
	}
	if ($(obj).val() == '0' || $(obj).val() == null) {
		errorList.push("Sequence can't start with 0.");
	}
	if (taxApplicableAt == '' || taxApplicableAt == null) {
		errorList.push("Please select Applicable At.");
	}
	
	var taxApplicableCode = $("#taxValueType :selected").attr("code");

	if (errorList.length > 0) {
		if (dispSeq == 'D') {
			$("#taxDisplaySeqId").val("");
		} else {
			$("#collSeqId").val("");
		}
		showTaxError(errorList);
	} else {

		if (taxApplicableCode == "BILL") {

			if (formMode == 'edit') {
				if (dispSeq == 'D') {
					savedSeq = $("#hiddenTaxDisplaySeqId").val();
				} else {
					savedSeq = $("#hiddenCollSeqId").val();
				}
				if (savedSeq == $(obj).val()) {
					return false;
				}
			}
			var url = "TaxMaster.html?validateSequence";
			var reqData = {
				"seqNum" : $(obj).val(),
				"dpDeptId" : deptId,
				"taxApplicableAt" : taxApplicableAt,
				"dispSeq" : dispSeq
			};
			var returnData = __doAjaxRequestForSave(url, 'POST', reqData,
					false, '', '');
			if (returnData != 0 || returnData != '0') {
				if (dispSeq == 'D') {
					if (formMode == 'edit') {
						$("#taxDisplaySeqId").val(savedSeq);
					} else {
						$("#taxDisplaySeqId").val("");
					}
					errorList
							.push('Display Sequence already present for selected department');
				} else {
					if (formMode == 'edit') {
						$("#collSeqId").val(savedSeq);
					} else {
						$("#collSeqId").val("");
					}

					errorList
							.push('Collection Sequence already present for selected department');
				}
				showTaxError(errorList);
			} else {
				closeOutErrBox();
				return false;
			}
		}

	}
}

function check_digit(e, obj) {
	var keycode;

	if (window.event)
		keycode = window.event.keyCode;
	else if (e) {
		keycode = e.which;
	} else {
		return true;
	}

	var fieldval = (obj.value);

	if (keycode == 8 || keycode == 9 || keycode == 13) {

		return true;
	}
	if ((keycode >= 32 && keycode <= 47) || (keycode >= 58 && keycode <= 126)) {
		return false;
	} else {
		return true;
	}
}

function validateServiceTax(dpDeptId, serviceId, taxGroup, taxCategory1,
		taxCategory2) {

	var requestData = {
		deptId : dpDeptId,
		serviceId : serviceId,
		taxGroup : taxGroup,
		taxCategory : taxCategory1,
		taxSubCategory : taxCategory2,
	};
	var url = "TaxMaster.html?validateServiseTax";
	var returnData = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	return returnData;
}

$(function() {
	$("#grid").jqGrid(
			{
				url : "TaxMaster.html?getMasGridData",
				datatype : "json",
				mtype : "GET",
				colNames : [ getLocalMessage('tax.code'),
						getLocalMessage('tax.name'),
						getLocalMessage('tax.group'),
						getLocalMessage('tax.subcategory.type'),
						getLocalMessage('tax.label.service'),
						getLocalMessage("tbOrganisation.orgStatus"),
						getLocalMessage('master.grid.column.action') ],
				colModel : [ {
					name : "taxCode",
					sortable : true,
					width : 30,
					search : true
				}, {
					name : "taxDesc",
					sortable : true,
					width : 80,
					search : true
				}, {
					name : "taxGroupName",
					sortable : true,
					width : 40,
					search : true
				}, {
					name : "taxCategory2Name",
					sortable : true,
					width : 50,
					search : true
				}, {
					name : "smServiceName",
					sortable : true,
					width : 60,
					search : true
				}, {
					name : 'taxActive',
					index : 'status',
					sortable : true,
					width : 20,
					align : 'center',
					edittype : 'checkbox',
					formatter : statusFormatter,
					editoptions : {
						value : "Yes:No"
					},
					formatoptions : {
						disabled : false
					},
					search : true
				}, {
					name : 'taxId',
					index : 'taxId',
					width : 35,
					align : 'center !important',
					sortable : false,
					search : false,
					formatter : actionFormatter
				} ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "cpmId",
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
				caption : getLocalMessage('tax.list')  
			});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

function actionFormatter(cellvalue, options, rowObject) {

	return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewTax('"
			+ rowObject.taxId
			+ "')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "
			+ "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editTax('"
			+ rowObject.taxId
			+ "')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
}

function statusFormatter(cellvalue, options, rowdata) {

	if (rowdata.taxActive == 'Y') {
		return "<a title='Tax is Active' alt='Tax is Active' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";
	} else {
		return "<a title='Tax is Inactive' alt='Tax is Inactive' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
	}
}

function editTax(taxId) {

	var errorList = [];
	var cpdMode = checkCpdMode();
	if (cpdMode != null && cpdMode != "") {
		var url = "TaxMaster.html?formForUpdate";
		var requestData = {
			"taxId" : taxId,
			"mode" : 'edit'
		};
		var returnData = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(".content").html(returnData);
		if (cpdMode == 'S') {
			$('#budgetcode-div').hide();
		}
	} else {
		errorList
				.push(getLocalMessage("master.select.sliPrefix"));
		showTaxError(errorList);
	}
};

function viewTax(taxId) {

	var errorList = [];
	var cpdMode = checkCpdMode();
	if (cpdMode != null && cpdMode != "") {
		var url = "TaxMaster.html?formForUpdate";
		var requestData = "taxId=" + taxId + "&mode=" + "view";
		var returnData = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(".content").html(returnData);
	} else {
		errorList
				.push(getLocalMessage("master.select.sliPrefix"));
		showTaxError(errorList);
	}
	if (cpdMode == 'S') {
		$('#budgetcode-div').hide();
	}

};

function openTaxMasAddForm() {
	
	var errorList = [];
	var cpdMode = checkCpdMode();
	var taxCat = validateTaxCategory();
	if (taxCat != null && taxCat != "") {

		if ($('#formModeId').val() == 'create') {
			$("#taxStatus").hide();
			$("#active").val('Y');
		}
		if (cpdMode != null && cpdMode != "") {
			var url = "TaxMaster.html?form";
			$
					.ajax({
						url : url,
						success : function(response) {
							$(".content").html(response);
							$(".content").show();
							if (cpdMode == 'S') {
								$('#budgetcode-div').hide();
							}
						},
						error : function(xhr, ajaxOptions, thrownError) {
							errorList
									.push(getLocalMessage("admin.login.internal.server.error"));
							showTaxError(errorList);
						}
					});
		} else {
			errorList
					.push(getLocalMessage("master.select.sliPrefix"));
			showTaxError(errorList);
		}
	} else {
		errorList
				.push(getLocalMessage("master.select.TacPrefix"));
		showTaxError(errorList);
	}

}

function checkCpdMode() {
	var checkUrl = "TaxMaster.html?validateCpdMode";
	var returnData = __doAjaxRequest(checkUrl, 'POST', '', '', '');
	return returnData;
}

function searchTaxMasData() {
	closeOutErrBox();

	$('#grid').jqGrid('clearGridData').trigger('reloadGrid');

	if ($('#dpDeptId').val() == null || $('#dpDeptId').val() == '') {
		var errorList = [];
		errorList.push(getLocalMessage('tax.error.dpDept'));
		showTaxError(errorList);
	} else {
		var url = "TaxMaster.html?searchTaxMasData";
		var reqData = {
			"taxDescId" : $("#taxList").val(),
			"dpDeptId" : $("#dpDeptId").val()
		};
		$
				.ajax({
					url : url,
					data : reqData,
					datatype : "json",
					mtype : "POST",
					success : function(response) {
						$("#grid").jqGrid('setGridParam', {
							datatype : 'json'
						}).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showTaxError(errorList);
					}
				});
	}

}

function taxMasterReset() {
	$('.error-div').html("");
	moveAllItems('#taxDetMasList', '#eventMapSelectedId');
	$('#taxmaster')
			.find('input:text, input:password, select,checkbox,textarea').val(
					'');
	$('#dpDeptId').val('').trigger('chosen:updated');
	$('#taxList').val('').trigger('chosen:updated');
	$('#taxList').html('').trigger('chosen:updated');
	$("#taxList").append(
			$("<option></option>").attr("value", "").text("Select Tax"));
	$(".chosen-select-no-results").trigger("chosen:updated");
	$(".warning-div").addClass('hide');
	$('#grid').jqGrid('clearGridData').trigger('reloadGrid');
}

$(function() {
	$("#eventMapSelectedId option").on('dblclick', function(e) {
		moveItems('#eventMapSelectedId', '#taxDetMasList');
	})

});

function reorderBudgetCode() {
	$('.budgetClass')
			.each(
					function(i) {
						var mode = $('#formModeId').val();
						$(this).closest("tr").attr("id", "tr" + (i));

						$(this).find("select:eq(0)").attr("id",
								"budgetcodeList" + (i));
						$(this).find("input:text:eq(0)").attr("id",
								"budgetcodeList" + i);
						$(this).find("select:eq(1)").attr("id",
								"demandcodeList" + (i));
						$(this).find("input:text:eq(1)").attr("id",
								"demandcodeList" + i);


						if (mode == 'create') {
							$(this).find("select:eq(2)").attr("id",
									"budgetStatus" + (i))
									.attr('disabled', true).val('A');
							$(this).find("input:text:eq(2)").attr("id",
									"budgetStatus" + i).attr('disabled', true)
									.val('A');
							
						} else {
							$(this).find("select:eq(2)").attr("id",
									"budgetStatus" + (i));
							$(this).find("input:text:eq(2)").attr("id",
									"budgetStatus" + i);
						}
						$(this).find("select:eq(2)").attr("id",
								"budgetStatus" + (i));
						$(this).find("input:text:eq(2)").attr("id",
								"budgetStatus" + i);
						

						$(this).find("select:eq(0)").attr("name",
								"taxBudgetBean[" + (i) + "].sacHeadId");
						$(this).find("input:text:eq(0)").attr("name",
								"taxBudgetBean[" + i + "].sacHeadId");
						
						$(this).find("select:eq(1)").attr("name",
								"taxBudgetBean[" + (i) + "].dmdClass");
						$(this).find("input:text:eq(1)").attr("name",
								"taxBudgetBean[" + i + "].dmdClass");

						$(this).find("select:eq(2)").attr("name",
								"taxBudgetBean[" + (i) + "].taxbActive");
						$(this).find("input:text:eq(2)").attr("name",
								"taxBudgetBean[" + i + "].taxbActive");

						$(this).find("input:hidden:eq(0)").attr("id",
								"taxbId" + i);
						$(this).find("input:hidden:eq(0)").attr("name",
								"taxBudgetBean[" + i + "].taxbId");

						$(this).find("input:hidden:eq(1)").attr("id",
								"sacHeadId" + i);
						$(this).find("input:hidden:eq(1)").attr("name",
								"taxBudgetBean[" + i + "].sacHeadId");

						$(this).find(".addClass").attr("onclick",
								"addRow(" + (i) + ")");
						$(this).find(".removeClass").attr("onclick",
								"removeRow(" + (i) + ")");
					});

	$('#budgetcode-div').find('select').each(function() {
		$(this).addClass('mandColorClass');
		$(".chosen-select-no-results").trigger("chosen:updated");
	});
}

function addRow(count) {

	var msg = "";
	var mode = $('#formModeId').val();
	var errorList = [];
	closeOutErrBox();

	if ($("#budgetcodeList" + count).val() != "") {
		if ($("#demandcodeList" + count).val() != ""){
		if ($("#budgetStatus" + count).val() != "") {

			for (i = 0; i < count; i++) {
				if (($('#budgetcodeList' + i).val() == $(
						'#budgetcodeList' + count).val())
						&& ($('#budgetStatus' + i).val() == $(
								'#budgetStatus' + count).val())
								&&($('#demandcodeList' + i).val() == $(
										'#demandcodeList' + count).val())) {
					errorList
							.push(getLocalMessage("tax.error.budgetCode.combination"));
				}
			}
			if (errorList.length > 0) {
				showTaxError(errorList);
			} else {
				var activeCount=0;
				for (i = 0; i < (count+1); i++) {
					if($("#budgetStatus"+i).val()=== 'A'){
						activeCount++;
					}
				}
				if(activeCount<4){
					var content = $("#budget-tbl tr").last().closest(
							'tr.budgetClass').clone();
					content.find('div.chosen-container').remove();
					if (mode == 'edit') {
						content.find("#budgetcodeList" + count).val("").attr(
								'disabled', false);
						content.find('.removeClass').show();
						content.find("#budgetStatus" + count).val("A");
					} else {
						
						$('#budgetStatus option[value="A"]').attr('selected',
								'selected');
					}
					content.find("#demandcodeList" + count).val("");
					content.find('[id^="budgetcodeList"]').chosen().trigger(
							"chosen:updated");
					content.find('[id^="demandcodeList"]').chosen().trigger(
					"chosen:updated");
					$('#tr' + count).after(content);
					count = count++;
					content.find("#taxbId" + count).val("");
					reorderBudgetCode();
				}else{
					msg = getLocalMessage("tax.error.addbudget.max3Active.dmdClass");
					showConfirmBox(msg, 0);
				}
				/*}*/
			}
		} else {
			msg = getLocalMessage("tax.error.addbudget.budgetstatus");
			showConfirmBox(msg, 0);
		}
		} else {
			msg = getLocalMessage("tax.error.addbudget.dmdClass");
			showConfirmBox(msg, 0);
		}
	} else {
		msg = getLocalMessage("tax.error.addbudget.budgetcode");
		showConfirmBox(msg, 0);
	}
}

function removeRow(count) {
	var rowCount = $('#budget-tbl tr').length - 1;
	if (rowCount <= 1) {
		var msg = getLocalMessage("tax.error.removebudget");
		showConfirmBox(msg, 0);
	} else {

		var requestData = {
			taxbId : $('#taxbId' + count).val()
		}
		var url = "TaxMaster.html?deleteBudgetcode";
		__doAjaxRequest(url, 'GET', requestData, false, '');
		$('#tr' + count).remove();
		reorderBudgetCode();
	}
}

function closeOutErrBox() {
	$("#errorDivTaxMas").hide();
}

function validateBudgetcode(taxId, budgetCodeId) {

	var requestData = {
		taxId : taxId,
		budgetCodeId : budgetCodeId
	};
	var url = "TaxMaster.html?validateBudget";
	var returnData = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	return returnData;
}

function showTaxError(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivTaxMas").html(errMsg);
	$('#errorDivTaxMas').show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function validateTax(taxDescId, deptId, taxAppl, taxMethod, taxGroup,
		taxCategory, taxSubCategory, taxCode, serviceId) {

	var requestData = {
		taxDescId : taxDescId,
		deptId : deptId,
		taxAppl : taxAppl,
		taxMethod : taxMethod,
		taxGroup : taxGroup,
		taxCategory : taxCategory,
		taxSubCategory : taxSubCategory,
		taxCode : taxCode,
		serviceId : serviceId
	};
	var url = "TaxMaster.html?validateTax";
	var returnData = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	return returnData;
}

function moveItems(origin, dest) {
	$(origin).find(':selected').appendTo(dest);
}

function moveAllItems(origin, dest) {
	$(origin).children().appendTo(dest);
}

function validateTaxCategory() {
	var checkUrl = "TaxMaster.html?validateTaxCategory";
	var returnData = __doAjaxRequest(checkUrl, 'POST', '', '', '');
	return returnData;
}

$(function() {
	$('#left').click(function() {
		moveItems('#taxDetMasList', '#eventMapSelectedId');
	});

	$('#right')
			.on(
					'click',
					function() {

						var eventMapSelectedId = $("#eventMapSelectedId").val();
						var taxDetMasList = $("#taxDetMasList").val();

						var taxDet = [];
						if (taxDetMasList == null) {
						} else if (taxDetMasList != null
								|| taxDetMasList != 'null') {
							var counter = 0;
							for (var count = 0; count < taxDetMasList.length; count++) {
								for (var count_2 = 0; count_2 < taxDetMasList.length; count_2++) {
									if (eventMapSelectedId[count_2] == taxDetMasList[count]) {
										counter++;
									}
								}
							}
							if (counter > 0) {
								var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
								errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Factor already selected </li></ul>';

								$(".errorDivTaxMas").html(errMsg);
								$(".errorDivTaxMas").hide();
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');

								return false;
							} else {
								$(".errorDivTaxMas").hide();
							}
						}

						moveItems('#eventMapSelectedId', '#taxDetMasList');
					});

	$('#leftall').on('click', function() {
		moveAllItems('#taxDetMasList', '#eventMapSelectedId');
	});

	$('#rightall').on('click', function() {
		moveAllItems('#eventMapSelectedId', '#taxDetMasList');
	});

	function resetForm() {
		moveAllItems('#taxDetMasList', '#eventMapSelectedId');
		$(".warning-div").addClass('hide');
	}

});
$(document).ajaxComplete(function() {
	$('.mand').children().addClass('mandColorClass');
});

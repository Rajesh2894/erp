var removeYearIdArray = [];
var removeDetailsIdArray = [];
$(document).ready(function() {
	
	$('.chosen-select-no-results').chosen();
	
	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date,
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
	
	var prDate =$('#prDate').val();
	if (prDate)
	    $('#prDate').val(prDate.split(' ')[0]);

	$(".deliveryDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});

	$("#id_purchaseRequisitin").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});
	yearLength();
	
	$("#financeDataDetails").on("click", '.addFinanceDetails', function(e) {
		var errorList = [];
		errorList = validateUniqueItem();
		errorList = validateFinancialDetails(errorList);
		if (errorList.length == 0) {
			var content = $('#financeDataDetails tr').last().clone();
			$('#financeDataDetails tr').last().after(content);
			content.find("select").val("");
			content.find('div.chosen-container').remove();
			content.find("select:eq(1)").chosen().trigger("chosen:updated");
			content.find("input:hidden").val("");
			content.find("input:text").val('');
			reOrderWorkDefTableSequence();
		} else
			displayErrorsOnPage(errorList);
	});
	
	$("#financeDataDetails").on("click",'.delButton',function(e) {
		var errorList = [];
		var rowCount = $('#financeDataDetails tr').length;
		if (rowCount <= 2) {
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		} else {
		    $(this).parent().parent().remove();
			var deletedYearId = $(this).parent().parent().find(
			'input[type=hidden]:first').attr('value');
			if (deletedYearId != '') {
				requestData = {
						"id" : deletedYearId
					};
				var response = __doAjaxRequest('PurchaseRequisition.html?doYearDeletion', 'POST',
						requestData, false, 'json');
				removeYearIdArray.push(deletedYearId);
			}
		}
		$('#removeYearIds').val(removeYearIdArray);
		reOrderWorkDefTableSequence();
		getTotalAmount();
		e.preventDefault();
	});

	getTotalAmount();
	
	$("input").on("keypress",function(e) {
		if ((e.which === 32 && !this.value.length)
				|| e.which == 13 || e.which == 34
				|| e.which == 39)
			e.preventDefault();
	});
	
	$("#datatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});
});

function reOrderWorkDefTableSequence() {	
	var cpdMode = $("#cpdMode").val();
	if (cpdMode == 'L') {
		$(".finacialInfoClass").each(function(i) {
			$(this).find("input:hidden:eq(0)").attr("id","yearId" + (i)).attr("name",
							"purchaseRequistionDto.yearDto[" + (i) + "].yearId");
			$(this).find("input:hidden:eq(1)").attr("id","finActiveFlag" + (i)).val("A");
			$(this).find("select:eq(0)").attr("id","faYearId" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].faYearId")
					.attr("onchange","resetFinanceCode(this," + i + ")");
			$(this).find("select:eq(1)").attr("id","sacHeadId" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].sacHeadId")
					.attr("onchange","checkForDuplicateHeadCode(this,"+ i + ")");
			$(this).find("button:eq(0)").attr("id","viewExpDet" + (i)).attr("onclick",
					"viewExpenditureDetails(" + i + ")");
			/*
			 * $(this).find("input:text:eq(0)").attr("id",
			 * "yearPercntWork" + (i)).attr( "name",
			 * "wmsDto.yearDtos[" + (i) + "].yearPercntWork");
			 */
			$(this).find("input:text:eq(1)").attr("id","yeDocRefNo" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].yeDocRefNo");
			$(this).find("input:text:eq(2)").attr("id","yeBugAmount" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].yeBugAmount").addClass("Comp");
			
			$(this).find("input:hidden:eq(1)").attr("name","purchaseRequistionDto.yearDto[" + (i)+ "].finActiveFlag");
		});
	} else {
		$(".finacialInfoClass").each(function(i) {
			$(this).find("input:hidden:eq(0)").attr("id","yearId" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].yearId");
			$(this).find("input:hidden:eq(1)").attr("id",
					"finActiveFlag" + (i)).val("A");
			$(this).find("select:eq(0)").attr("id", "faYearId" + (i))
			.attr("name","purchaseRequistionDto.yearDto[" + (i) + "].faYearId")
			.attr("onchange","resetFinanceCode(this," + i + ")");
			$(this).find("input:text:eq(0)").attr("id","financeCodeDesc" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].financeCodeDesc")
					.attr("onchange","checkForDuplicateFinanceCode(this," + i+ ")");
			$(this).find("button:eq(0)").attr("id", "viewExpDet" + (i))
			.attr("onclick","viewExpenditureDetails(" + i + ")");
			/*
			 * $(this).find("input:text:eq(1)").attr("id",
			 * "yearPercntWork" + (i)).attr("name", "wmsDto.yearDtos[" +
			 * (i) + "].yearPercntWork");
			 */
			$(this).find("input:text:eq(1)").attr("id","yeDocRefNo" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].yeDocRefNo");
			$(this).find("input:text:eq(2)").attr("id","yeBugAmount" + (i)).attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].yeBugAmount").addClass("Comp");
			$(this).find("input:hidden:eq(1)").attr("name",
					"purchaseRequistionDto.yearDto[" + (i) + "].finActiveFlag");
		});
	}
}

function resetFinanceCode(obj, index) {
	$("#sacHeadId" + index).val("");
	$("#financeCodeDesc" + index).val("");
}

function checkForDuplicateHeadCode(event, currentRow) {	
	$(".error-div").hide();
	var errorList = [];
	$('.finacialInfoClass').each(function(i) {
		var faYearId = $('#faYearId' + i).val();
		var sacHeadId = $("#sacHeadId" + i).val();	
		var c = i + 1;
	
		if (errorList.length == 0) {
			if (currentRow != i&& ($("#faYearId" + currentRow).val() == faYearId && event.value == sacHeadId)) {
				errorList.push(getLocalMessage('work.Def.valid.duplicate.fincode'));
				$("#sacHeadId" + currentRow).val("");
				displayErrorsOnPage(errorList);
				return false;
			}
		} else {
			$("#sacHeadId" + i).val('');
			displayErrorsOnPage(errorList);
			return false;
		}
	});
}


function checkForDuplicateFinanceCode(event, currentRow) {
	$(".error-div").hide();
	var errorList = [];
	$('.finacialInfoClass').each(function(i) {
		var faYearId = $('#faYearId' + i).val();
		var financeCodeDesc = $("#financeCodeDesc" + i).val();
		var c = i + 1;
		if (faYearId == undefined || faYearId == '') {
			errorList.push(getLocalMessage("work.Def.valid.selectFinYear")
					+ " - " + c);
		}
		if (financeCodeDesc == "" || financeCodeDesc == null) {
			errorList.push(getLocalMessage("work.Def.valid.enter.finCode")
					+ " - " + c);
		}
		if (errorList.length == 0) {
			if (currentRow != i&& ($("#faYearId" + currentRow).val() == faYearId && event.value == financeCodeDesc)) {
				errorList.push(getLocalMessage('work.Def.valid.duplicate.fincode'));
				$("#financeCodeDesc" + currentRow).val("");
				displayErrorsOnPage(errorList);
				return false;
			}
		} else {
			$("#financeCodeDesc" + i).val('');
			displayErrorsOnPage(errorList);
			return false;
		}
	});
}

function inputPreventSpace(key, obj) {
	if (key == 32 && obj.value.charAt(0) == ' ') {
		$(obj).val('');
	}
}

function getTotalAmount() {	
	$("#totalAmount").val("0.00");
	var amount = 0;
	var rowCount = $('#finance tr').length;
	for (var m = 0; m <= rowCount - 1; m++) {
		var n = parseFloat(parseFloat($("#yeBugAmount" + m).val()));
		if (isNaN(n)) {
			return n = 0;
		}
		amount += n;
		var result = amount.toFixed(2);
		$("#totalAmount").val(result);
	}
}


function validateFinancialDetails(errorList) {
	$(".finacialInfoClass").each(function(i) {
		var financeCodeDesc = $("#financeCodeDesc" + i).val();
		var faYearId = $("#faYearId" + i).val();
		var aprrovalNo = $("#yeDocRefNo" + i).val();
		var yeBugAmount = $("#yeBugAmount" + i).val();
		var cpdMode = $("#cpdMode").val();
		var sacHeadId = $("#sacHeadId" + i).val();
		var row = i + 1;
		if (cpdMode == 'L') {
			if (sacHeadId == '') {
				errorList.push(getLocalMessage("work.Def.valid.select.finCode")+ " - " + row);
			}
		} else {
			if (financeCodeDesc == '') {
				errorList.push(getLocalMessage("work.Def.valid.enter.finCode")+ " - " + row);
			}
		}
		if (faYearId == "" || faYearId == null) {
			errorList.push(getLocalMessage("work.Def.valid.selectFinYear")	+ " - " + row);
		}
		errorList= validateExpenditureDetails(i, errorList);
	});
	return errorList;
}

function yearLength() {
	var frmdateFields = $('#fromDate');
	var todateFields = $('#toDate');
	frmdateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
	todateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
}

/*  Add Function */
function addPurchaseRequisitionForm(formUrl, actionParam) {
	var errorList = [];
	var isDefaulValue = __doAjaxRequest(formUrl+ '?checkForDefaultSLI', 'POST', {}, false, 'json');
	if (isDefaulValue == "N") {
		errorList.push(getLocalMessage("work.Def.valid.setSLI"));
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function searchPurchaseRequisition() {
	var data = {
		"storeNameId" : $('#storeNameId').val(),
		"prnoId" : $('#prnoId').val(),
		"fromDate" : $('#fromDate').val(),
		"toDate" : $('#toDate').val(),
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PurchaseRequisition.html?searchPurchaseRequisition', data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
}


function getPurchaseRequisitionData(formUrl, actionParam, prId) {
	var data = {
		"prId" : prId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	reOrderUnitTabIdSequence('.firstUnitRow');
}


$(function() {
	$("#unitDetailTable").on('click', '.addPurReq', function() {
		$('.error-div').hide();
		var errorList = [];
		errorList = validateUniqueItem();
		errorList = validateItemDetails(errorList);
		if (errorList.length != 0)
			displayErrorsOnPage(errorList);
		else {
			var content = $("#unitDetailTable").find('tr:eq(1)').clone();
			$("#unitDetailTable").append(content);
			content.find("input:text").val('');
			content.find("input:hidden").val("");
			content.find("select").val('0');
			content.find('div.chosen-container').remove();
			content.find('[id^="itemId"]').chosen().trigger("chosen:updated");
			reOrderUnitTabIdSequence('.firstUnitRow');
		}
	});
		
	$("#unitDetailTable").on('click','.delButton',function() {
		var errorList = [];
		var rowCount = $('#unitDetailTable tr').length;
		if (rowCount <= 2) {
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			displayErrorsOnPage(errorList);
		} else {
			$(this).parent().parent().remove();
			var deletedDetId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if (deletedDetId != '') {
				requestData = {
					"id" : deletedDetId
				};
				var response = __doAjaxRequest('PurchaseRequisition.html?doDetailsDeletion', 'POST', requestData, false, 'json');
				removeDetailsIdArray.push(deletedDetId);
			}
		}
		$('#removeDetailIds').val(removeDetailsIdArray);
		reOrderUnitTabIdSequence('.firstUnitRow');
		e.preventDefault();
	});	
});


function reOrderUnitTabIdSequence(firstRow) {
	$(firstRow).each(function(i) {
		$(this).find("input:hidden:eq(0)").attr("id","prdetId" +i).attr("name",
						"purchaseRequistionDto.purchaseRequistionDetDtoList["+i+"].prdetId");
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
		$(this).find("select:eq(0)").attr("id", "itemId" + i);
		$(this).find("input:text:eq(2)").attr("id", "uomId" + i);
		$(this).find("input:text:eq(3)").attr("id", "quantity" + i);
		$(this).find("input:text:eq(4)").attr("id", "taxId" + i);
		
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name", "purchaseRequistionDto.purchaseRequistionDetDtoList[" + i + "].itemId");
		$(this).find("input:text:eq(2)").attr("name", "purchaseRequistionDto.purchaseRequistionDetDtoList[" + i + "].uomId");
		$(this).find("input:text:eq(3)").attr("name", "purchaseRequistionDto.purchaseRequistionDetDtoList[" + i + "].quantity");
		$(this).find("input:text:eq(4)").attr("name", "purchaseRequistionDto.purchaseRequistionDetDtoList[" + i + "].taxId");
		$(this).find('#itemId' + i).attr("onchange", "getUomName(" + (i) + ")");
		
		$(firstRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}


function backPurReqForm(url) {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', url);
	$("#postMethodForm").submit();
}


function backTenderForm(){
	var divName = '.content-page';
	var url = "TenderInitiation.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');	
	$(divName).html(ajaxResponse);
	prepareTags();
	getProjectList();
	
}


function getProjectList(){
	var deptId = $("#deptId").val();
	var project = $("#project").val();
	if(deptId!="" &&  deptId!=0){
		var requestData ={"deptId":deptId}
		var projectList = __doAjaxRequest('TenderInitiation.html?getProjectList', 'POST', requestData, false,'json');
		$.each(projectList, function(index, value) {
		$('#projId').append($("<option></option>").attr("value",value.projId).attr("code",value.projCode).text(value.projNameEng));
	});
	$("#projId").val(project).trigger('chosen:updated');
	}
}


function resetPurReqForm() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function getStoreData() {
	var storeId = $("#storeNameId").val();
	var requestUrl = "PurchaseRequisition.html?getStoredata";
	var requestData = {
		"storeId" : storeId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false, 'json');
	$.each(ajaxResponse, function(key, value) {
		if (key == "storeInchargeName")
			$("#departmentName").val(value);
		if (key == "locationName")
			$("#requestedName").val(value);
	});
	prepareTags();
}


function getUomName(id) {
	var errorList = validateUniqueItem();
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var itemId = $("#itemId" + id).val();
		var requestData = {
			"itemId": itemId
		};
		var requestUrl = "PurchaseRequisition.html?getUomdata";
		var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false, 'json');
		$("#uomId" + id).val(ajaxResponse);
	}
}


/*  Purchase Requisition Draft Save   */
function draftForm(element) {
	$(".error-div").hide();
	var errorList = [];
	errorList = validateUniqueItem();
	errorList = validatePurchaseRequisition(errorList);
	errorList = validateFinancialDetails(errorList);

	if( 1 == $('#levelCheck').val()){
		var decision = $("input[id='decision']:checked").val();
		if (decision == undefined || decision == '')
			errorList.push(getLocalMessage("department.indent.validation.decision"));
		if ($("#comments").val() == "")
			errorList.push(getLocalMessage("department.indent.validation.remark"))
	}

	if (errorList.length != 0)
		displayErrorsOnPage(errorList);
	else
		return saveOrUpdateForm(element, '', 'PurchaseRequisition.html', 'saveform');
}


function validatePurchaseRequisition(errorList){
	var storeNameId = $("#storeNameId").val();
	var storeStatus=$("#storeNameId option:selected").attr("code");
	var prDate = $("#prDate").val();
	if (storeNameId == "")
		errorList.push(getLocalMessage("material.management.store.name"));
	if (0 == $('#levelCheck').val()) {
		if ("Y" != storeStatus && storeNameId != "")
			errorList.push(getLocalMessage("store.inactive.validation"));
	}
	if (prDate == "") 
		errorList.push(getLocalMessage("material.management.requisitio.date"));
		
	errorList =	validateItemDetails(errorList);
	return errorList;
}

function validateItemDetails(errorList){
	$('.firstUnitRow').each(function(i) {
		var level = i + 1;
		var itemId = $.trim($("#itemId" + i).val());
		var quantity = $.trim($("#quantity" + i).val());

		if (itemId == 0 || itemId == "")
			errorList.push(getLocalMessage("material.management.validate.item") + " " + level);
		if (quantity == 0 || quantity == "")
			errorList.push(getLocalMessage("material.management.quantity.validation") + " " + level);
	});
	return errorList;
}


function closebox() {
	if( 0 == $('#levelCheck').val())
		window.location.href = 'PurchaseRequisition.html';
	else
		window.location.href = 'AdminHome.html';
    $.fancybox.close();	
}


function savePurchaseRequisitionForm(element) {
	$(".error-div").hide();
	var errorList = [];
	errorList = validateUniqueItem();
	errorList = validatePurchaseRequisition(errorList);
	errorList = validateFinancialDetails(errorList);
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('PurchaseRequisition.html?purchaseReqSaveForm', 'POST', requestData, false);
		showConfirmBoxForSubmit(getLocalMessage('material.management.Proceed'),response);
        $('.content').removeClass('ajaxloader');
	}
}

function showConfirmBoxForSubmit(successMsg,prNo) {
	var message = '';
	var errMsgDiv = '.msg-dialog-box';
	var cls = getLocalMessage(successMsg);
	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '
			+ getLocalMessage('material.management.Proceed.for.Approval') + " " + prNo + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForPurchaseOrder(\''+prNo+'\')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceedForPurchaseOrder(prNo) {
	$.fancybox.close();
	var divName = '.content-page';
	var url = "PurchaseRequisition.html?printPurchaseRequisition";
	var requestData = {
		"prNo" : prNo
	};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	return false;
}


function deletePurchaseRequisitionData(formUrl, actionParam, prId) {
	if (actionParam == "deletePurReqisition") {
		showConfirmBoxForDelete(prId, actionParam);
	}
}

/**
 * Show Confirm BoxFor Delete
 * 
 * @param roId
 * @param actionParam
 * @returns
 */
function showConfirmBoxForDelete(prId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('material.item.WantDelete') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + prId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

/**
 * Proceed For Delete
 * 
 * @param roId
 * @returns
 */
function proceedForDelete(prId) {
	$.fancybox.close();
	var requestData = 'prId=' + prId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PurchaseRequisition.html?'
			+ 'deletePurReqisition', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/*function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style-darkblue.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button onClick="window.close();" type="button" class="btn btn-danger hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}*/
function PrintDiv(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

function viewExpenditureDetails(i) {
	var errorList = [];

    var faYearId = $('#faYearId' + i).val();
    var sacHeadId = $('#sacHeadId' + i).val();
    var yeBugAmount = $('#yeBugAmount' + i).val();
    var location = $("#requestedName").val();
	    
    if (faYearId == '') {
        errorList.push(getLocalMessage("work.Def.valid.selectFinYear") + " - "+ i)
        displayErrorsOnPage(errorList);
        return false;
    }
    if (sacHeadId == '') {
        errorList.push(getLocalMessage("work.Def.valid.select.finCode") + " - "+ i);
        displayErrorsOnPage(errorList);
        return false;
    }
    if (yeBugAmount == '') {
        yeBugAmount=0;
    }
    if (location == '') {
        errorList.push(getLocalMessage("Please Select Location") + " - "+ i)
        displayErrorsOnPage(errorList);
        return false;
    }
    var requestData = {
        'faYearId' : faYearId,
        'sacHeadId' : sacHeadId,
        "yeBugAmount" : yeBugAmount
    };     
	var ajaxResponse = __doAjaxRequest("PurchaseRequisition.html?getBudgetHeadDetails", 'POST',
	            requestData, false, 'json');    
	if (ajaxResponse.authorizationStatus == 'Y') {
	        var message = '';
	        var sMsg = '';
	        message += '<h4 class="text-center">Budget Details</h4>';
	        message += '<div class="margin-right-10 margin-left-10">';
	        message += '<table class=\"table table-bordered"\>' + '<tr>'
	                + '<th>Budget' + '</th>' + '<td class="text-right"> '
	                + parseFloat(ajaxResponse.invoiceAmount).toFixed(2) + '</td> '
	                + '</tr>';
	        message += '<tr>' + ' <th>Previous Expenditure' + '</th>'
	                + '<td class="text-right">'
	                + parseFloat(ajaxResponse.sanctionedAmount).toFixed(2)
	                + '</td>' + '</tr>';
	        message += '<tr>' + ' <th>Current Expenditure' + '</th>'
	                + '<td class="text-right">'
	                + parseFloat(ajaxResponse.billAmount).toFixed(2) + '</td>'
	                + '</tr>';
	        message += '<tr>' + ' <th>Balance' + '</th>'
	                + '<td class="text-right">'
	                + parseFloat(ajaxResponse.netPayables).toFixed(2) + '</td>'
	                + '</tr></table>';
	        message += '</div>';
	        if (ajaxResponse.disallowedRemark == 'Y') {
	            sMsg = 'Bill Amount Is Greater Than Remaining Budget Amount';
	            message += '<h4 class=\"text-center red padding-12\">' + sMsg
	                    + '</h4>';
	        }
	} else {
		errorList.push(getLocalMessage("work.def.no.budget.availabe.against.selected.account") + " " + (i + 1));
	    displayErrorsOnPage(errorList);
	    return false;
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}


function validateExpenditureDetails(i, errorList) {
    var faYearId = $('#faYearId' + i).val();
    var sacHeadId = $('#sacHeadId' + i).val();
    var yeBugAmount = $('#yeBugAmount' + i).val();
	var location = $("#requestedName").val();
	if (yeBugAmount == '') {
		yeBugAmount=0;
	}
    if ( faYearId != '' && sacHeadId != ''  && location != ''){
		var requestData = {
			'faYearId' : faYearId,
			'sacHeadId' : sacHeadId,
			"yeBugAmount" : yeBugAmount
		};     
		var ajaxResponse = __doAjaxRequest("PurchaseRequisition.html?getBudgetHeadDetails", 'POST',
				            requestData, false, 'json');  
		if (ajaxResponse.authorizationStatus != 'Y')
		    errorList.push(getLocalMessage("work.def.no.budget.availabe.against.selected.account") + " " + (i + 1));
	}
    return errorList;
}



function validateUniqueItem() {
	var errorList = [];
	var itemArray = [];
	$('.firstUnitRow').each(function(i) {
		var itemId = $("#itemId" + i).val();
		if (itemArray.includes(itemId))
			errorList.push(getLocalMessage("purchase.requisition.duplicate.item.validate") + " " + (i + 1));
		itemArray.push(itemId);
	});
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	return errorList;
}


	
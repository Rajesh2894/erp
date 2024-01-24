$(document).ready(function() {   
	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});


	$("#id_purchaseOrder").dataTable({
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

	
	$(".poDate").datepicker({ 
		changeMonth: true,
        changeYear: true,
		dateFormat : "dd/mm/yy",
		maxDate : new Date(),
		onSelect: function( selectedDate ) {
			$( ".expectedDate" ).datepicker( "option", "minDate", selectedDate );
		}  
	});
	
	$(".expectedDate").datepicker({ 
		dateFormat : "dd/mm/yy",
		changeMonth : true,
	    changeYear : true,
		minDate : $('#poDate').val()
	});	
	

	$('.firstUnitRow').each(function(i) {
		var prDate = $("#prDate" + i).val();
		if (prDate)
			$("#prDate" + i).val(prDate.split(' ')[0]);
	});
	var poDate = $('#poDate').val()
	if (poDate) 
		$('#poDate').val(poDate.split(' ')[0]);
	var expectedDeliveryDate = $('#expectedDeliveryDate').val()
	if (expectedDeliveryDate) 
		$('#expectedDeliveryDate').val(expectedDeliveryDate.split(' ')[0]);
		

	if ($("#quantity").val() != "" || $("#quantity").val() != undefined
			|| $("#unitPrice").val() != "" || $("#unitPrice").val() != undefined
			|| $("#taxId").val() != "" || $("#taxId").val() != undefined) {
		calculateTotal();
	}

});


function searchPurchaseOrder() {
	var errorList = [];
	var storeNameId = $('#storeNameId').val();
	var vendorId = $('#vendorName').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	if((storeNameId == "" || storeNameId == null || storeNameId == undefined ) &&
			(vendorId == "" || vendorId == null || vendorId == undefined ) &&
			(fromDate == "" || fromDate == null || fromDate == undefined ) &&
			(toDate == "" || toDate == null || toDate == undefined ) ){
		errorList.push(getLocalMessage("grn.Select"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var data = {
			"storeNameId" : storeNameId,
			"vendorId" : vendorId,
			"fromDate" : fromDate,
			"toDate" : toDate,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('PurchaseOrder.html?searchPurchaseOrder', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function addPurchaseOrderForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function getPurchaseOrderData(formUrl, actionParam, poNo) {  
	var data = {
		"poNo" : poNo
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	reOrderUnitTabIdSequence('.firstUnitRow');
}


function resetPurOrderForm() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}


function backPurOrderForm(url) {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', url);
	$("#postMethodForm").submit();
}

/**
 * for Summary form Actions
 */
function getPurchaseOrderData(formUrl, actionParam, poId) {
	var data = {
		"poId" : poId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function deletePurchaseOrderData(formUrl, actionParam, poId) {
	if (actionParam == "deletePurchaseOrder") {
		showConfirmBoxForDelete(poId, actionParam);
	}
}

/** Show Confirm BoxFor Delete  */
function showConfirmBoxForDelete(poId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('material.item.WantDelete') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + poId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

/**  Proceed For Delete */
function proceedForDelete(poId) {
	$.fancybox.close();
	var requestData = 'poId=' + poId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PurchaseOrder.html?'+ 'deletePurchaseOrder', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function saveDraftPurchaseOrderForm(element) {
	var errorList = [];
	errorList = validatePurchaseOrder(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element, getLocalMessage('Purchase Order Saved Successfully....'), 'PurchaseOrder.html', 'saveform');
	}
}

function proceedAuth(){
	window.location.href = '';
	$.fancybox.close();
}


function submitPurchaseOrderForm(element) {  
	var errorList = [];
	errorList = validatePurchaseOrder(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element, '', 'PurchaseOrder.html', 'saveApprovalPurchaseOrder');		
	}
}


function getPurchaseRequisitionData(element) { 
	var errorList = [];
	var prId = $('#prId').val();
	if (prId == "" || prId == null || prId == undefined)
		errorList.push(getLocalMessage("material.management.pr.number.valid"));

	if (errorList.length == 0) {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading("PurchaseOrder.html?getpurreqdata", requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse);
		prepareTags();
	}else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}	
}


/***********  for purchase order  *************/
function validatePurchaseOrder() { 
	var errorList = [];	
	var vendorName = $("#vendorName").val();
	var expectedDeliveryDate = $("#expectedDeliveryDate").val();
	var storeNameId = $("#storeNameId").val();
	var poDate = $("#poDate").val();
	var prId = $('#prId').val();
	
	if (poDate == "" || poDate == null || poDate == undefined)
		errorList.push(getLocalMessage("please.Select.Purchase.Order.Date"));
	if (storeNameId == "" || storeNameId == null || storeNameId == undefined)
		errorList.push(getLocalMessage("please.Select.Store.Name"));
	if (vendorName == "" || vendorName == null || vendorName == undefined)
		errorList.push(getLocalMessage("please.Select.Vendor.Name"));
	if (expectedDeliveryDate == "" || expectedDeliveryDate == null || expectedDeliveryDate == undefined)
		errorList.push(getLocalMessage("please.Select.Expected.Delivery.Date"));
	if('A' == $("#saveMode").val()){
		if (prId == "" || prId == null || prId == undefined)
			errorList.push(getLocalMessage("material.management.pr.number.valid"));	
	}
	
	errorList = validatePurRequestDetailForm(errorList);
	errorList = validateTncDetailForm(errorList);
	errorList = validateEnclosureDetailForm(errorList);	
	return errorList;
}

/** for purchase Requisition Detail */
function validatePurRequestDetailForm(errorList) {
	$('.purRequisitionDetRow').each(
		function(i) {				
			var itemId = $("#itemId" + i).val();
			var uomId = $("#uomId" + i).val();
			var quantity = $("#quantity" + i).val();
			var unitPrice = $("#unitPrice" + i).val();
			var taxId = $("#taxId" + i).val();
			var totalAmt = $("#totalAmt" + i).val();
			
			if (itemId == "0" || itemId == null || itemId == "" || itemId == undefined) 
				errorList.push(getLocalMessage("material.management.validate.item") + " " + (i + 1));
			if (uomId == "0" || uomId == null  || uomId == "" || uomId == undefined) 
				errorList.push(getLocalMessage("uoM.Can.Not.Be.Empty") + " " + (i + 1));
			if (quantity == "0" || quantity == null || quantity == ""  || quantity == undefined) 
				errorList.push(getLocalMessage("please.Enter.Quantity") + " " + (i + 1));
			if (unitPrice == "0" || unitPrice == "" || unitPrice == null || unitPrice == undefined)
				errorList.push(getLocalMessage("please.Enter.Unit.Price")+ " " + (i + 1));
			if (taxId == "0" || taxId == "" || taxId == null || taxId == undefined)
				errorList.push(getLocalMessage("material.item.master.tax.percentage.validation")+ " " + (i + 1));
			if (totalAmt == "0" || totalAmt == "" || totalAmt == null || totalAmt == undefined)
				errorList.push(getLocalMessage("total.Value.Not.Be.Empty")+ " " + (i + 1));
		});
	
	var totItemAmount = $("#totItemAmount").val();
	if (totItemAmount == "0" || totItemAmount == "" || totItemAmount == null || totItemAmount == undefined)
		errorList.push(getLocalMessage("Total Item Amount Should Not Be Empty"));
	
	return errorList;
}

/**  for Purchase Requisition Details  */
function reOrderpurRequisitionDetTabIdSequence(purRequisitionDetRow) {
	$(purRequisitionDetRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
		$(this).find("input:text:eq(1)").attr("id", "itemId" +i);			
		$(this).find("input:text:eq(2)").attr("id", "uomId" + i);
		$(this).find("input:text:eq(3)").attr("id", "quantity" + i);
		$(this).find("input:text:eq(4)").attr("id", "unitPrice" + i);
		$(this).find("input:text:eq(5)").attr("id", "taxId" + i);
		$(this).find("input:text:eq(6)").attr("id", "totalAmt" + i);
					
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("input:text:eq(1)").attr("name","purchaseOrderDto.purchaseOrderDetDto[" + i + "].itemId");
		$(this).find("input:text:eq(2)").attr("name","purchaseOrderDto.purchaseOrderDetDto[" + i + "].uonName");
		$(this).find("input:text:eq(3)").attr("name","purchaseOrderDto.purchaseOrderDetDto[" + i + "].quantity");
		$(this).find("input:text:eq(4)").attr("name","purchaseOrderDto.purchaseOrderDetDto[" + i + "].unitPrice");
		$(this).find("input:text:eq(5)").attr("name","purchaseOrderDto.purchaseOrderDetDto[" + i + "].tax");
		$(this).find("input:text:eq(6)").attr("name","purchaseOrderDto.purchaseOrderDetDto[" + i + "].totalAmt");
	});
}


/**  for Overheads  */
function reOrderUnitTabIdSequence(overheadsTableRow) {
	$(overheadsTableRow).each(
		function(i) {
			$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
			$(this).find("input:text:eq(1)").attr("id", "description" +i);
			$(this).find("select:eq(0)").attr("id", "overHeadType" +i);
			$(this).find("input:text:eq(2)").attr("id", "amount" + i);
							
			$(this).find("input:text:eq(0)").val(i + 1);
			$(this).find("input:text:eq(1)").attr("name","purchaseOrderDto.purchaseOrderOverheadsDto[" + i + "].description");
			$(this).find("select:eq(0)").attr("name","purchaseOrderDto.purchaseOrderOverheadsDto[" + i + "].overHeadType");
			$(this).find("input:text:eq(2)").attr("name","purchaseOrderDto.purchaseOrderOverheadsDto[" + i + "].amount");
		});
}

/**  for Overheads  */
$("#overheadsTableID").on('click', '.addOF', function() {     
	var errorList = [];
	if (errorList.length == 0) {
		var content = $("#overheadsTableID").find('tr:eq(1)').clone();
		$("#overheadsTableID").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');			
		$('.error-div').hide();
		reOrderUnitTabIdSequence('.overheadsTableRow'); 
	} else {
		displayErrorsOnPage(errorList);
	}
});
	
/**  for Overheads  */
$("#overheadsTableID").on( 'click', '.remOF', function() {
			
	var rowCount = $('#overheadsTableID tr').length;
	var removeIdArray = [];
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}
	$(this).closest('#overheadsTableID tr').remove();
	reOrderUnitTabIdSequence('.overheadsTableRow');
	id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
	if (id != '') {
		removeIdArray.push(id);
	}
	$('#removeOverheadIds').val(removeIdArray);
	totalOverheadCount();
});

/** for Term & Condition  */ 
function validateTncDetailForm(errorList) {
	$('.tncDetailTableRow').each(
			function(i) {
				var tncdescription = $("#tncdescription" + i).val();
				
				if (tncdescription == "0" || tncdescription == null || tncdescription == ""  || tncdescription == undefined) {
					errorList.push(getLocalMessage("please.Enter.Term") + " " + (i + 1));
				}
			});
	return errorList;
}

/** for Term & Condition  */ 
function tncDetailTableIdSequence(tncDetailTableRow) {
	$(tncDetailTableRow).each(
		function(i) {
			$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
			$(this).find("input:text:eq(1)").attr("id", "tncdescription" +i);
							
			$(this).find("input:text:eq(0)").val(i + 1);
			$(this).find("input:text:eq(1)").attr("name","purchaseOrderDto.purchaseorderTncDto[" + i + "].description");
		});
}

/** for Term & Condition  */ 
$("#tncDetailTableID").on('click', '.addTnc', function() {    
	var errorList = [];
	errorList = validateTncDetailForm(errorList);
	if (errorList.length == 0) {
		var content = $("#tncDetailTableID").find('tr:eq(1)').clone();
		$("#tncDetailTableID").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');			
		$('.error-div').hide();
		tncDetailTableIdSequence('.tncDetailTableRow'); 
	} else {
		displayErrorsOnPage(errorList);
	}
});
	
/** for Term & Condition  */ 
$("#tncDetailTableID").on('click','.remTnc',function() {
	var rowCount = $('#tncDetailTableID tr').length;
	var removeIdArray = [];
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	  }
		$(this).closest('#tncDetailTableID tr').remove();
		tncDetailTableIdSequence('.tncDetailTableRow'); 
		id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (id != '') {
			removeIdArray.push(id);
		}
		$('#removeTncIds').val(removeIdArray);
  });


/** for Enclosure  */ 
function validateEnclosureDetailForm(errorList) { 
	$('.encTableDetailTableRow').each(
			function(i) {
				var encdescription = $("#encdescription" + i).val();
				var attchment = $(this).find("input:file:eq(0)").attr("id", "purchaseOrderDto.purchaseorderAttachmentDto"+i+".attachments"+i+".uploadedDocumentPath").val();			
				var attachmentDet="";
				$("#file_list_"+i).find("li").each(function(i, element) {
					attachmentDet =  $(this).find('img').attr('src');
				});
				
				if (encdescription == "0" || encdescription == null || encdescription == "" || encdescription == undefined) 
					errorList.push(getLocalMessage("please.Enter.Description") + " " + (i + 1));
				if ((attchment == "0" || attchment == null || attchment == "" || attchment == undefined)&&
						(attachmentDet == "0" || attachmentDet == null || attachmentDet == "" || attachmentDet == undefined)) 
					errorList.push(getLocalMessage("please.Attach.Document") + " " + (i + 1));
			});
	return errorList;
}


/** for Enclosure  */  
function fileCountUpload(element){  
	var errorList = [];
	errorList = validateEnclosureDetailForm(errorList);
	if (errorList.length == 0) {
		var row = $("#encTableDetailTableID tbody .encTableDetailTableRow").length;
		var requestData = $('form').serialize();
		var response = __doAjaxRequest('PurchaseOrder.html?fileCountUpload', 'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		$("#docDetails").find('.collapse').collapse('hide');
		$('.content-page').html(response);
		prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}
}


function encDetailTableIdSequence(encTableDetailTableRow) {
	$(encTableDetailTableRow).each(
		function(i) {
			$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
			$(this).find("input:text:eq(1)").attr("id", "encdescription" +i);
							
			$(this).find("input:text:eq(0)").val(i + 1);
			$(this).find("input:text:eq(1)").attr("name","purchaseOrderDto.purchaseorderAttachmentDto[" + i + "].description");
		});
}

/** for Enclosure  */ 
var removedocIdArray = [];
function deleteDocDetails(obj, id, podocId) {  
	
	requestData = {
		"id" : id
	};	
	var row = $("#encTableDetailTableID tbody .encTableDetailTableRow").length;
	if (row != 1) {		
		var response = __doAjaxRequest('PurchaseOrder.html?doEntryDeletion', 'POST', requestData, false, 'html');
		$(".content-page").html(response);
		if (podocId != '') {
			removedocIdArray.push(podocId);
		}
		$('#removeEncIds').val(removedocIdArray);
		prepareTags();	
	}else{
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}
} 


/********************* To Calculate Total Item Amount  */
function calculateTotal() {   
	var i = 0;
	if ($.fn.DataTable.isDataTable('#purRequisitionDetailTable')) {
		$('#purRequisitionDetailTable').DataTable().destroy();
	}
	$("#purRequisitionDetailTable .purRequisitionDetRow").each(function(i) {
		var quantity = $("#quantity" + i).val();
		var unitPrice = $("#unitPrice" + i).val();
		var taxId = $("#taxId" + i).val();
		if (quantity == "" || quantity == '0' || quantity == undefined)
			quantity = 0;
		if (unitPrice == "" || unitPrice == '0' || unitPrice == undefined)
			unitPrice = 0;
		if (taxId == "" || taxId == '0' || taxId == undefined)
			taxId = 0;
		//Formula From SRS >> Total Value = Quantity* (Unit Price+ (Unit Price* Tax %))
		var total = parseFloat(quantity) * (parseFloat(unitPrice) + parseFloat(unitPrice*(taxId/100)));
		total = total.toFixed(2);
		if (!isNaN(total)) {
			document.getElementById('totalAmt' + i).value = total;
		}
		totalCount();
	});
}

function totalCount() {
	var value = 0.0;
	$(".totalAmt").each(function(i) {
		if ($('#totalAmt' + i).val() == '') {
			value += parseFloat('0');
		} else {
			value += parseFloat($('#totalAmt' + i).val());
		}
		$('#totItemAmount').val(value.toFixed(2));
	});
	totalOverheadCount();
	totalPoCount();
}



/********************* To Calculate Total Overhead Amount  */
function totalOverheadCount() {   
	var value = 0.0;
	$(".amountOverhead").each(function(i) {
		var overheadType = $('#overHeadType' + i).val();
		if(overheadType=='A'){
			if ($('#amount' + i).val() == '') {
				value += parseFloat('0');
			} else {
				value += parseFloat($('#amount' + i).val());
			}
		}else if(overheadType=='D'){
			if ($('#amount' + i).val() == '') {
				value -= parseFloat('0');
			} else {
				value -= parseFloat($('#amount' + i).val());
			}
		}		
		$('#totOverheadAmount').val(value.toFixed(2));
	});
	totalPoCount();
}


/********************* To Calculate Total PO Amount  */
function totalPoCount() {   
	var totItemAmount = $("#totItemAmount").val();
	var totOverheadAmount = $("#totOverheadAmount").val();
	if (totItemAmount == "" || totItemAmount == '0' || totItemAmount == undefined)
		totItemAmount = 0.00;
	if (totOverheadAmount == "" || totOverheadAmount == '0' || totOverheadAmount == undefined)
		totOverheadAmount = 0.00;

	var total = parseFloat(totItemAmount) + parseFloat(totOverheadAmount) ;
	var total = total.toFixed(2);
	if (!isNaN(total)) {
		document.getElementById('totPoAmount').value = total;
	}	
}


$('.hasCharacterNumbers').on('keyup', function() {
    this.value = this.value.replace(/[^a-z A-Z 0-9]/g,'');
});

$('.hasCharacterNumbersPercentage').on('keyup', function() {
    this.value = this.value.replace(/[^a-z A-Z 0-9 % ]/g,'');
});


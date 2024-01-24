$(document).ready(function() {  

	$("#storeInentTableId").dataTable({
		"oLanguage": {
			"sSearch": ""
		},
		"aLengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
		"iDisplayLength": 5,
		"bInfo": true,
		"lengthChange": true,
		"ordering": false,
		"order": [[1, "desc"]]
	});	
	
	
	$("#mdnDate").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: new Date(),
	});
	
	
	var mdnDate = $('#mdnDate').val();
	var storeIndentdate = $('#storeIndentdate').val();
	if (mdnDate)
		$('#mdnDate').val(mdnDate.split(' ')[0]);
	if (storeIndentdate)
		$('#storeIndentdate').val(storeIndentdate.split(' ')[0]);

	
	if ('V' == $('#saveMode').val()) {
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

	$(".chosen-select-no-results").chosen();

});



/*  Search Store Indent   */
function searchMaterialDispatchData() {
	var errorList = [];
	var requestStoreId = $('#requestStoreId').val();
	var mdnId = $('#mdnId').val();
	var issueStoreId = $('#issueStoreId').val();
	var status = $('#status').val() == "0" ? "" : $('#status').val();
	var siId = $('#siId').val();

	if ((requestStoreId == "" || requestStoreId == null || requestStoreId == undefined || requestStoreId == '0')
		&& (mdnId == "" || mdnId == null || mdnId == undefined || mdnId == '0')
		&& (issueStoreId == "" || issueStoreId == null || issueStoreId == undefined || issueStoreId == '0')
		&& (status == "" || status == null || status == undefined || status == '0')
		&& (siId == "" || siId == null || siId == undefined || siId == '0'))
		errorList.push(getLocalMessage("grn.Select"));

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"requestStoreId": requestStoreId,
			"mdnId": mdnId,
			"issueStoreId": issueStoreId,
			"status": status,
			"siId": siId,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('MaterialDispatchNote.html?searchMaterialDispatchData', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


/*  Add Store Indent Form  */
function addMDNForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/*  Get Store Indent Details By Indent Id    */
function getStoreIndentDetails() {
	$('.error-div').hide();
	var siId = $("#siId").val();
	if (siId == "" || siId == null || siId == "0" || siId == undefined) {
		var errorList = [];
		errorList.push(getLocalMessage("Please Select Store Indent No."));
		displayErrorsOnPage(errorList);
	} else {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading('MaterialDispatchNote.html?getStoreIndentDetails', requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse);
	}
}


/*   Calculate Remaining Issue Quantity  */
function remainingIssueQuantity() {	
	$('#storeInentTableId').DataTable().destroy();
	$(".itemDetailsRow").each(function(index) {
		var requestedQty = parseFloat($('#requestedQty' + index).val()) || 0;
		var prevRecQty = parseFloat($('#prevRecQty' + index).val()) || 0;
		var issuedQty = parseFloat($('#issuedQty' + index).val()) || 0;

		if ( requestedQty != 0 && (requestedQty - prevRecQty) < issuedQty) {
			var errorList = [getLocalMessage("material.management.mdn.received.quantity.remaining.quantity.validation") + " " + (index + 1)];
			displayErrorsOnPage(errorList);
		} else {
			var remainingQty = Math.max(requestedQty - (prevRecQty + issuedQty), 0);
			$("#remainingQty" + index).val(remainingQty.toFixed(2));
		}
	});
}


/*  View MDN Issue Details Form  */
function viewMDNIssueDetails(count) {
	$('#indexCount').val(count);
	var requestData = $('form').serialize();
	var ajaxResponse = doAjaxLoading("MaterialDispatchNote.html?viewMDNIssueDetails", requestData, 'html', '');
	$('.content').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
}


/*  Submit Store Indent Form  */
function submitMDNFormData(obj) {
	$('.error-div').hide();
	var errorList = [];
	errorList = validateMDNForm(errorList);
	errorList = validateItemDetails(errorList);
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else
		return saveOrUpdateForm(obj, " ", 'MaterialDispatchNote.html', 'saveform');
}


function closebox() {
	if (1 == $('#levelCheck').val())
		window.location.href = 'AdminHome.html';
	else
		window.location.href = 'MaterialDispatchNote.html';
	$.fancybox.close();
}


/*  validate MDN Form   */
function validateMDNForm(errorList) {
	var mdnDate = $("#mdnDate").val();
	var siId = $("#siId").val();
	var storeIndentdate = $('#storeIndentdate').val();
	var requestStoreId = $('#requestStoreId').val();
	var requestStore = $("#requestStore").val();
	var requestStoreLocName = $('#requestStoreLocName').val();
	var requestedByName = $('#requestedByName').val();
	var issueStoreId = $("#issueStoreId").val();
	var issueStore = $('#issueStore').val();
	var issueStoreLocName = $('#issueStoreLocName').val();
	var issueInchargeName = $('#issueInchargeName').val();

	if (mdnDate == "" || mdnDate == '0' || mdnDate == null || mdnDate == undefined)
		errorList.push(getLocalMessage("material.management.mdn.date.validate"));
	if (siId == "" || siId == null || siId == "0" || siId == undefined)
		errorList.push(getLocalMessage("material.management.mdn.store.indent.no.validate"));
	if (storeIndentdate == "" || storeIndentdate == '0' || storeIndentdate == null || storeIndentdate == undefined)
		errorList.push(getLocalMessage("material.management.mdn.store.indent.date.validate"));
	if (requestStoreId == "" || requestStoreId == '0' || requestStoreId == null || requestStoreId == undefined
		|| requestStore == "" || requestStore == '0' || requestStore == null || requestStore == undefined)
		errorList.push(getLocalMessage("material.management.requesting.store.validation"));
	if (requestStoreLocName == "" || requestStoreLocName == '0' || requestStoreLocName == null || requestStoreLocName == undefined)
		errorList.push(getLocalMessage("material.management.validate.requesting.store.location"));
	if (requestedByName == "" || requestedByName == '0' || requestedByName == null || requestedByName == undefined)
		errorList.push(getLocalMessage("material.management.validate.requesting.store.incharge"));
	if (issueStoreId == "" || issueStoreId == '0' || issueStoreId == null || issueStoreId == undefined
		|| issueStore == "" || issueStore == '0' || issueStore == null || issueStore == undefined)
		errorList.push(getLocalMessage("material.management.issuing.store.validation"));
	if (issueStoreLocName == "" || issueStoreLocName == '0' || issueStoreLocName == null || issueStoreLocName == undefined)
		errorList.push(getLocalMessage("material.management.validate.issuing.store.location"));
	if (issueInchargeName == "" || issueInchargeName == '0' || issueInchargeName == null || issueInchargeName == undefined)
		errorList.push(getLocalMessage("material.management.validate.issuing.store.incharge"));

	return errorList;
}


/*  validate Item Details Form   */
function validateItemDetails(errorList) {
	var levelCheck = $("#levelCheck").val();
	var issuedQtyArray = [];
	var inspectorRemarksArray = [];
	
	$('.itemDetailsRow').each(function(i) {
		var itemId = $("#itemId" + i).val();
		var itemName = $("#itemName" + i).val();
		var uom = $("#uom" + i).val();
		var uomDesc = $("#uomDesc" + i).val();
		var issuedQty = $("#issuedQty" + i).val();

		if (!itemId && !itemName)
			errorList.push(getLocalMessage("material.management.item.name.cannot.empty") + " " + (i + 1));
		if (!uom && !uomDesc)
			errorList.push(getLocalMessage("material.management.validate.UoM") + " " + (i + 1));
		if (0 == levelCheck) {
			var requestedQty = $("#requestedQty" + i).val();
			var prevRecQty = $("#prevRecQty" + i).val();
			var inspectorRemarks = $("#inspectorRemarks" + i).val();
			if (requestedQty == "0" || requestedQty == null || requestedQty == "" || requestedQty == undefined)
				errorList.push(getLocalMessage("material.management.mdn.requested.quantity.validation") + " " + (i + 1));
			if (prevRecQty == "0" || prevRecQty == null || prevRecQty == "" || prevRecQty == undefined)
				errorList.push(getLocalMessage("material.management.mdn.previously.issued.quantity.validation") + " " + (i + 1));
			if(issuedQty && inspectorRemarks){
				issuedQtyArray.push(issuedQty);
				inspectorRemarksArray.push(inspectorRemarks);
			}
		} if (1 == levelCheck) {
			var acceptQty = $("#acceptQty" + i).val();
			var rejectQty = $("#rejectQty" + i).val();
			var storeRemarks = $("#storeRemarks" + i).val();
			if (issuedQty == "0" || issuedQty == null || issuedQty == "" || issuedQty == undefined)
				errorList.push(getLocalMessage("material.management.mdn.issued.quantity.validation") + " " + (i + 1));
			if (!acceptQty || !rejectQty || !storeRemarks)
				errorList.push(getLocalMessage("material.management.mdn.inspection.details.validate") + " " + (i + 1));
		}
	});
	if (0 == levelCheck) {
		if(issuedQtyArray.length == 0 && inspectorRemarksArray.length == 0)
			errorList.push(getLocalMessage("material.management.mdn.issue.details.validate"));	
	}
	
	return errorList;
}


/*  save MDN Issue Details Form  */
function saveMDNItemEntryDetails(element) {  
	var errorList = [];	
	var management = $('#management').val();
	if( management == 'B')
		errorList = validateInBatch(errorList);
	else if( management == 'S')
		errorList =  validateSerial(errorList);	
	else
		errorList = validateNotInBatch(errorList);

	var issuedQty = $('#issuedQty').val();
	var remainQtyToBeIssued = $("#remainQtyToBeIssued").val();
	var inspectorRemarks = $('#inspectorRemarks').val();
	if (issuedQty == "0" || issuedQty == null || issuedQty == "" || issuedQty == undefined) 
			errorList.push(getLocalMessage("department.indent.total.issued.quantity.not.empty"));
	if (issuedQty == "NaN") 
		errorList.push(getLocalMessage("material.management.mdn.total.issued.quantity.validation"));
	if (issuedQty > remainQtyToBeIssued) 
		errorList.push(getLocalMessage("material.management.mdn.total.issued.quantity.remaining.quantity.validation"));
	if (inspectorRemarks == "0" || inspectorRemarks == null || inspectorRemarks == "" || inspectorRemarks == undefined) 
		errorList.push(getLocalMessage("material.management.enter.remarks"));
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var requestData = $('form').serialize();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading("MaterialDispatchNote.html?saveMDNItemEntryDetails", requestData, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
	remainingIssueQuantity();
}


/*  Back to MDN Form  */
function backToMDNForm(){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MaterialDispatchNote.html?backToMDNForm');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}



/*  for In Batch Get Item Numbers By Bin  */
function getItemNumberListByBinLocation(index) {
	$("#batchNo" + index).html('');
	$("#batchNo" + index).trigger('chosen:updated');
	var itemId = $("#itemId").val();
	var binLocId = $("#inBatchBinLocId" + index).val();

	if(binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) {
		$("#availableQtyInBatch" + index).val('');
	}else{
		var requestData = {
			"itemId" : itemId,
			"binLocId" : binLocId,
		};
		var ajaxResponse = __doAjaxRequest("MaterialDispatchNote.html?getItemNumbersByBin", 'post', requestData, false, 'json');
		$("#batchNo" + index).append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$.each(ajaxResponse, function(key, value) {
			$("#batchNo" + index).append($("<option></option>").attr("code", key).attr("value", value).text(value));
		});
		$("#batchNo" + index).trigger('chosen:updated');		
	}
}

/*  for In Batch Get Availble Qty By Bin  */
function getInBatchAvailbleQtyByBin(index) {   
	$('.error-div').hide();
	var itemId = $("#itemId").val();
	var binLocId = $("#inBatchBinLocId" + index).val();
	var batchNo = $("#batchNo" + index).val();
	
	var errorList = [];
	if( (binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) ||
			(batchNo == "0" || batchNo == null || batchNo == "" || batchNo == undefined)){
		$("#availableQtyInBatch" + index).val('');
		errorList.push(getLocalMessage("material.management.mdn.bin.and.batch.validation") + " " + (index + 1));
	}
	errorList = validateUniqueBinAndBatch();
	if (errorList.length > 0) {
		$('.error-div').show();
		displayErrorsOnPage(errorList);
	} else {
		requestData = {
				"itemId" : itemId,
				"binLocId" : binLocId,
				"itemNo" : batchNo,
		};
		var availableQty = parseInt(__doAjaxRequest("MaterialDispatchNote.html?getAvailableQntyByBin", 'POST', requestData, false, 'json'));
		$("#availableQtyInBatch" + index).val(availableQty);
	}	
}

/* for In Batch Reorder  */
function inBatchTableIdSequence(inBatchTableRow) { 
	$(inBatchTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "inBatchSrNo" + i);
		$(this).find("select:eq(0)").attr("id", "inBatchBinLocId" + i ).attr("onchange", "getItemNumberListByBinLocation("+i+")");			
		$(this).find("select:eq(1)").attr("id", "batchNo" + i ).attr("onchange", "getInBatchAvailbleQtyByBin("+i+")");
		$(this).find("input:text:eq(3)").attr("id", "availableQtyInBatch" +i);
		$(this).find("input:text:eq(4)").attr("id", "issuedQtyInBatch" +i);
									
		$(this).find("input:text:eq(0)").val(i + 1);		
		$(this).find("select:eq(0)").attr("name","noteItemsEntryDTOList[" + i + "].issueBinLocation");
		$(this).find("select:eq(1)").attr("name","noteItemsEntryDTOList[" + i + "].itemNo");
		$(this).find("input:text:eq(3)").attr("name","noteItemsEntryDTOList[" + i + "].availableQuantity");
		$(this).find("input:text:eq(4)").attr("name","noteItemsEntryDTOList[" + i + "].quantity");
		
		$(inBatchTableRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}

/*  for In Batch Validation  */
function validateInBatch() {   
	var errorList = [];
	errorList = validateUniqueBinAndBatch();
	$('.inBatchTableRow').each(function(i) {
		var inBatchBinLocId = $("#inBatchBinLocId" + i).find("option:selected").attr('value');
		var batchNo = $("#batchNo" + i).val();
		var issuedQtyInBatch = $("#issuedQtyInBatch" + i).val();
		var availableQtyInBatch = $("#availableQtyInBatch" + i).val();
		
		if( parseInt(issuedQtyInBatch) > parseInt(availableQtyInBatch) )
			errorList.push(getLocalMessage("material.management.mdn.issued.quantity.not.greater.available.quantity") + " " + (i + 1));
		if (inBatchBinLocId == "0" || inBatchBinLocId == null || inBatchBinLocId == ""  || inBatchBinLocId == undefined) 
			errorList.push(getLocalMessage("please.Select.Bin.Location") + " " + (i + 1));
		if (batchNo == "0" || batchNo == null || batchNo == "" || batchNo == undefined) 
			errorList.push(getLocalMessage("material.management.mdn.batch.no.validation") + " " + (i + 1));
		if (availableQtyInBatch == "0" || availableQtyInBatch == null || availableQtyInBatch == "" || availableQtyInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.available.quantity.not.empty") + " " + (i + 1));
		if (issuedQtyInBatch == "0" || issuedQtyInBatch == null || issuedQtyInBatch == "" || issuedQtyInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
	});	
	return errorList;
}

/*  for In Batch Unique Validation  */
function validateUniqueBinAndBatch() {
	$("#errorDiv").hide();
	var errorList = [];
	var batchNoArray=[];
	$('.inBatchTableRow').each(function(i){
		var batchNo = $("#batchNo" + i).val();
		if (batchNoArray.includes(batchNo)) 
			errorList.push(getLocalMessage("material.management.mdn.duplicate.batch.no")+" "+batchNo);
		batchNoArray.push(batchNo);
	});	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}


/* for In Batch Add */
function addUnitRowInBatch() {
	var errorList = [];
	errorList = validateInBatch(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var content = $("#inBatchTableId").find('tr:eq(1)').clone();
		$("#inBatchTableId").append(content);	
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find('[id^="batchNo"]').chosen().trigger("chosen:updated");
		content.find('[id^="inBatchBinLocId"]').chosen().trigger("chosen:updated");
		$('.error-div').hide();
		inBatchTableIdSequence('.inBatchTableRow');
	}
}


/*  for In Batch Remove  */
var removeInBatchIdArray = [];
$("#inBatchTableId").on( 'click', '.deleteInBatchItemRow', function() {  
	var rowCount = $('#inBatchTableId tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
		var rowIndex = $(this).closest('tr').index(); // Get the row index
        $(this).closest('tr').remove();
		inBatchTableIdSequence('.inBatchTableRow');
        var requestData = {
            rowIndex: rowIndex,
        };
        var response = __doAjaxRequest('MaterialDispatchNote.html?doItemDeletionDetails', 'POST', requestData , false, 'json');
		calculateInBatchIssuedQty();
	}
});


/*  for In Batch Total Issue Qty   */
function calculateInBatchIssuedQty() {   
	
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateInBatch();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var totalIssuedQty = 0;
		$(".inBatchTableRow").each(function(i) {
			var issuedQtyInBatch= $('#issuedQtyInBatch' + i).val();
			if (issuedQtyInBatch == '') 
				totalIssuedQty += parseFloat('0');
			else 
				totalIssuedQty += parseInt(issuedQtyInBatch);
			$('#issuedQty').val(totalIssuedQty);
		});
	}	
}



/*  for Serial numbers list By Bin  */
function getSerialListByBinLocation(index) {  
	$("#serialNumber" + index).html('');
	$('#serialNumber' + index).trigger('chosen:updated');

	var itemId = $("#itemId").val();
	var binLocId = $("#serialBinLocId" + index).val();	

	if(binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) {
		$("#availableQtySerial" + index).val('');
	}else{
		var requestData = {
			"itemId" : itemId,
			"binLocId" : binLocId,
		};
		var ajaxResponse = __doAjaxRequest("MaterialDispatchNote.html?getItemNumbersByBin", 'post', requestData, false, 'json');
		$("#serialNumber" + index).append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$.each(ajaxResponse, function(key, value) {
			$("#serialNumber" + index).append($("<option></option>").attr("code", key).attr("value", value).text(value));
		});
		$("#serialNumber" + index).trigger('chosen:updated');		
	}
}

/*  for Serial Get Availble Qty  */
function getSerialAvailbleQty(index) {   
	$('.error-div').hide();
	var itemId = $("#itemId").val();
	var binLocId = $("#serialBinLocId" + index).val();
	var serialNumber = $("#serialNumber" + index).val();
	
	var errorList = [];
	if( (binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) ||
			(serialNumber == "0" || serialNumber == null || serialNumber == "" || serialNumber == undefined)){
		$("#availableQtySerial" + index).val('');
		errorList.push(getLocalMessage("material.management.mdn.bin.and.serial.validation") + " " + (index + 1));
	}
	errorList = validateUniqueSerial();
	if (errorList.length > 0) {
		$('.error-div').show();
		displayErrorsOnPage(errorList);
	} else {
		requestData = {
				"itemId" : itemId,
				"binLocId" : binLocId,
				"itemNo" : serialNumber,
		};
		var availableQty = parseInt(__doAjaxRequest("MaterialDispatchNote.html?getAvailableQntyByBin", 'POST', requestData, false, 'json'));
		$("#availableQtySerial" + index).val(availableQty);
	}	
}

/*  for Serial Reorder  */
function serialTableIdSequence(serialTableRow) {
	$(serialTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "serSrNo" + i);
		$(this).find("select:eq(0)").attr("id", "serialBinLocId" + i ).attr("onchange", "getSerialListByBinLocation("+i+")");
		$(this).find("select:eq(1)").attr("id", "serialNumber" + i ).attr("onchange", "getSerialAvailbleQty("+i+")");
		$(this).find("input:text:eq(3)").attr("id", "availableQtySerial" +i);
		$(this).find("input:text:eq(4)").attr("id", "issuedQtySerial" +i);
		
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name","noteItemsEntryDTOList[" + i + "].issueBinLocation");
		$(this).find("select:eq(1)").attr("name","noteItemsEntryDTOList[" + i + "].itemNo");
		$(this).find("input:text:eq(3)").attr("name","noteItemsEntryDTOList[" + i + "].availableQuantity");
		$(this).find("input:text:eq(4)").attr("name","noteItemsEntryDTOList[" + i + "].quantity");
				
		$(serialTableRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}

/*  for Serial Validation   */
function validateSerial() {   
	var errorList = [];
	errorList = validateUniqueSerial();
	$('.serialTableRow').each(function(i) {
		var serialBinLocId = $("#serialBinLocId" + i).find("option:selected").attr('value');
		var serialNumber = $("#serialNumber" + i).val();
		var availableQtySerial = $("#availableQtySerial" + i).val();
		var issuedQtySerial = $("#issuedQtySerial" + i).val();
			
		if( parseInt(issuedQtySerial) > parseInt(availableQtySerial) )
			errorList.push(getLocalMessage("material.management.mdn.issued.quantity.not.greater.available.quantity") + " " + (i + 1));
		if (serialBinLocId == "0" || serialBinLocId == null  || serialBinLocId == "" || serialBinLocId == undefined) 
			errorList.push(getLocalMessage("please.Select.Bin.Location") + " " + (i + 1));
		if (serialNumber == "0" || serialNumber == null || serialNumber == "" || serialNumber == undefined) 
			errorList.push(getLocalMessage("department.indent.please.select.serial.no") + " " + (i + 1));
		if (availableQtySerial == "0" || availableQtySerial == null || availableQtySerial == "" || availableQtySerial == undefined) 
			errorList.push(getLocalMessage("department.indent.available.quantity.not.empty") + " " + (i + 1));
		if (issuedQtySerial == "0" || issuedQtySerial == null || issuedQtySerial == "" || issuedQtySerial == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
	});
	return errorList;
}

/*  for Serial Unique Validation  */
function validateUniqueSerial() {
	$("#errorDiv").hide();
	var errorList = [];
	var serialNumberArray=[];
	$('.serialTableRow').each(function(i){
		var serialNumber = $("#serialNumber" + i).val();
		if (serialNumberArray.includes(serialNumber)) 
			errorList.push(getLocalMessage("material.management.mdn.duplicate.serial.no")+" "+serialNumber);
		serialNumberArray.push(serialNumber);
	});
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}

/*  for Serial Add  */
function addSerialUnitRow() {
	var errorList = [];
	errorList = validateSerial(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var content = $("#serialTableId").find('tr:eq(1)').clone();
		$("#serialTableId").append(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find('[id^="serialBinLocId"]').chosen().trigger("chosen:updated");
		content.find('[id^="serialNumber"]').chosen().trigger("chosen:updated");
		$('.error-div').hide();
		serialTableIdSequence('.serialTableRow');
	}
}

/*  for Serial Remove  */
var removeSerialIdArray = [];
$("#serialTableId").on( 'click', '.deleteSerialUnitRow', function() {   
	var rowCount = $('#serialTableId tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
		var rowIndex = $(this).closest('tr').index(); // Get the row index
        $(this).closest('tr').remove();
		serialTableIdSequence('.serialTableRow');
        var requestData = {
            rowIndex: rowIndex,
        };
        var response = __doAjaxRequest('MaterialDispatchNote.html?doItemDeletionDetails', 'POST', requestData , false, 'json');
        calculateSerialIssuedQty();
	}
});


/*  for Serial Total Issue Qty   */
function calculateSerialIssuedQty() {   
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateSerial();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var totalIssuedQty = 0;
		$(".serialTableRow").each(function(i) {
			var issuedQtySerial= $('#issuedQtySerial' + i).val();		
			if (issuedQtySerial == '') 
				totalIssuedQty += parseFloat('0');
			else 
				totalIssuedQty += parseInt(issuedQtySerial);
			$('#issuedQty').val(totalIssuedQty);
		});
	}	
}



/*  for Not In Batch Get Availble Qty By Bin  */
function getNotInBatchAvailbleQtyByBin(index) {  
	$('.error-div').hide();
	var errorList = [];
	var itemId = $("#itemId").val();
	var binLocId = $("#notInBatchBinLocId" + index).val();
	
	errorList = validateUniqueBin();	
	if (errorList.length > 0) {
		$('.error-div').show();
		displayErrorsOnPage(errorList);
	} else {		
		if(binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) {
			$("#availableQtyNotInBatch" + index).val('');
		}else{
			requestData = {
				"itemId" : itemId,
				"binLocId" : binLocId,
			};
			var availableQty = parseInt(__doAjaxRequest("MaterialDispatchNote.html?getNotInBatchAvailableQntyByBin", 'POST', requestData, false, 'json'));
			$("#availableQtyNotInBatch" + index).val(availableQty);		
		}
	}
}

/*  for Not In Batch Reorder  */
function notInBatchTableIdSequence(notInBatchTableRow) {
	$(notInBatchTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "notInBatchSequence" + i);
		$(this).find("select:eq(0)").attr("id", "notInBatchBinLocId" + i ).attr("onchange", "getNotInBatchAvailbleQtyByBin(" + i + ")");			
		$(this).find("input:text:eq(2)").attr("id", "availableQtyNotInBatch" +i);
		$(this).find("input:text:eq(3)").attr("id", "issuedQtyNotInBatch" + i);
								
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name","noteItemsEntryDTOList[" + i + "].issueBinLocation");
		$(this).find("input:text:eq(2)").attr("name","noteItemsEntryDTOList[" + i + "].availableQuantity");
		$(this).find("input:text:eq(3)").attr("name","noteItemsEntryDTOList[" + i + "].quantity");
		
		$(notInBatchTableRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
		
	});
}

/*  for Not In Batch Validation  */
function validateNotInBatch() {
	var errorList = [];
	errorList = validateUniqueBin();
	$('.notInBatchTableRow').each(function(i) {
		var notInBatchBinLocId = $("#notInBatchBinLocId" + i).find("option:selected").attr('value');
		var issuedQtyNotInBatch = $("#issuedQtyNotInBatch" + i).val();
		var availableQtyNotInBatch = $("#availableQtyNotInBatch" + i).val();
	
		if (notInBatchBinLocId == "0" || notInBatchBinLocId == null  || notInBatchBinLocId == "" || notInBatchBinLocId == undefined) 
			errorList.push(getLocalMessage("please.Select.Bin.Location") + " " + (i + 1));
		if (availableQtyNotInBatch == "0" || availableQtyNotInBatch == null || availableQtyNotInBatch == "" || availableQtyNotInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.available.quantity.not.empty") + " " + (i + 1));
		if (issuedQtyNotInBatch == "0" || issuedQtyNotInBatch == null || issuedQtyNotInBatch == "" || issuedQtyNotInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
		if( parseInt(issuedQtyNotInBatch) > parseInt(availableQtyNotInBatch) )
			errorList.push(getLocalMessage("material.management.mdn.issued.quantity.not.greater.available.quantity") + " " + (i + 1));
	});
	return errorList;
}

/*  for Not In Batch Validation  */
function validateUniqueBin() {
	$("#errorDiv").hide();
	var errorList = [];
	var locationArray=[];
	$('.notInBatchTableRow').each(function(i){
		var binLocation = $("#notInBatchBinLocId" + i).val();
		if (locationArray.includes(binLocation)) 
			errorList.push(getLocalMessage("material.management.mdn.duplicate.bin.location")+" "+ i );
		locationArray.push(binLocation);
	});	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}

/*  for Not In Batch Add */
function addNewNotInBatchItemRow() {
	var errorList = [];
	errorList = validateNotInBatch(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var content = $("#notInBatchTableID").find('tr:eq(1)').clone();
		$("#notInBatchTableID").append(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find('[id^="notInBatchBinLocId"]').chosen().trigger("chosen:updated");
		$('.error-div').hide();
		notInBatchTableIdSequence('.notInBatchTableRow');
	}
}

/*  for Not In Batch Remove  */
var removeNotInBatchIdArray = [];
$("#notInBatchTableID").on( 'click', '.deleteNotInBatchUnitRow', function() {  
	var rowCount = $('#notInBatchTableID tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
		var rowIndex = $(this).closest('tr').index(); // Get the row index
        $(this).closest('tr').remove();
		notInBatchTableIdSequence('.notInBatchTableRow'); 
        var requestData = {
            rowIndex: rowIndex,
        };
        var response = __doAjaxRequest('MaterialDispatchNote.html?doItemDeletionDetails', 'POST', requestData , false, 'json');
		calculateNotInBatchIssuedQty();
	}
});

/*  for Not In Batch Total Issue Qty  */
function calculateNotInBatchIssuedQty() {  
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateNotInBatch();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var totalIssuedQty = 0;
		$(".notInBatchTableRow").each(function(i) {
			var issuedQtyNotInBatch= $('#issuedQtyNotInBatch' + i).val();
			if (issuedQtyNotInBatch == '') 
				totalIssuedQty += parseFloat('0');
			else 
				totalIssuedQty += parseInt(issuedQtyNotInBatch);		
			$('#issuedQty').val(totalIssuedQty);
		});		
	}	
}


/*  Get MDN Data By Id   */
function getMDNDataById(formUrl, actionParam, mdnId) {
	var data = {
		"mdnId" : mdnId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


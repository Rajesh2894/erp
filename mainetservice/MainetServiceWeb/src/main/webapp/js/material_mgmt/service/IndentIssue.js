$(document).ready(function() { 
	
	$("#issuedDate").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: new Date()
	});
	
	$('.chosen-select-no-results').chosen();
	
	timeRemover();
	
});


/*  To Remove Time from Date  */
function timeRemover(){  	
	var indentdate = $('#indentdate').val();
	if (indentdate) 
		$('#indentdate').val(indentdate.split(' ')[0]);
	var expecteddate = $("#expecteddate").val();
	if (expecteddate)
		$("#expecteddate").val(expecteddate.split(' ')[0]);
	var issuedDate = $("#issuedDate").val();
	if (issuedDate)
			$("#issuedDate").val(issuedDate.split(' ')[0]);
}


/*  View Indent Issue Details Form */
function viewIndentIssue(object, count ) {
	var indexCount = $('#indexCount').val(count);
	var requestData = $('form').serialize();
	var ajaxResponse = doAjaxLoading("IndentProcessApproval.html?indentissueitem", requestData, 'html', '');
	$('.content').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
	prepareTags();
}


function backButton(){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('IndentProcessApproval.html?backToMainForm');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}


/* save Indent Issue Details Form */
function saveIndentIssue(element) {  
	var errorList = [];	
	var managementCode = $('#managementCode').val();
	if( managementCode == 'B')
		errorList = validateInBatch(errorList);
	else if( managementCode == 'S')
		errorList =  validateSerial(errorList);	
	else
		errorList = validateNotInBatch(errorList);

	var issuedqty = $('#issuedqty').val();
	var remarks = $('#Remarks').val();
	var receivedQuantity = $("#receivedQuantity").val();

	if (issuedqty == "0" || issuedqty == null || issuedqty == "" || issuedqty == undefined) 
			errorList.push(getLocalMessage("department.indent.total.issued.quantity.not.empty"));
	if (issuedqty == "NaN") 
		errorList.push(getLocalMessage("material.management.mdn.total.issued.quantity.validation"));
	if (issuedqty > receivedQuantity) 
		errorList.push(getLocalMessage("department.indent.issued.quantity.requested.quantity.validation"));
	if (remarks == "0" || remarks == null || remarks == "" || remarks == undefined) 
		errorList.push(getLocalMessage("material.management.enter.remarks"));
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var requestData = $('form').serialize();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading("IndentProcessApproval.html?saveIndentIssueForm", requestData, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
}


/*  Submit Indent Issue Form */
function submitIndentIssueForm(obj) {
	var errorList = [];
	if('true' == $('#lastChecker').val())
		errorList = validateItemTable(errorList);
	if(1 == $('#levelcheck').val())
		errorList = ValidateIndentApproval(errorList);	
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else
		return saveOrUpdateForm(obj, " ", 'IndentProcess.html', 'submitIndentForm');	
}

function closebox() {
	if (1 == $('#levelcheck').val()) {
		$.fancybox.close();
		var divName = '.content-page';
		var ajaxResponse = __doAjaxRequest('IndentProcess.html?printDepartmentalIndent', 'POST', "", false, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		return false;
	} else {
		window.location.href = 'AdminHome.html';
		$.fancybox.close();
	}
}


function ValidateIndentApproval(errorList) {
	var decision=$("input[id='decision']:checked").val();
	if(decision == undefined || decision == '' )
		errorList.push(getLocalMessage("lgl.validate.decision"));
	if ($("#comments").val() == "")
		errorList.push(getLocalMessage("material.management.validate.Remark"))
	return errorList;
}


function validateItemTable(errorList){
	 var issuedBy = $('#issuedBy').val();
	 var issuedDate = $('#issuedDate').val();
 
	 if (issuedBy == "" || issuedBy == undefined)
		 errorList.push(getLocalMessage('department.indent.validation.issue.by'));
	 if (issuedDate == "" || issuedDate == undefined)
		 errorList.push(getLocalMessage('department.indent.validation.issue.date'));

	$("#initialTable tbody tr").each(function(i) {
	     var issuedQty = $("#issuedQty" + i).val();
		 if(issuedQty =="" || issuedQty =="0" || issuedQty == null || issuedQty == undefined)
			 errorList.push(getLocalMessage("department.indent.validation.issue.item.details ")+" "+ + (i + 1));
	 });
	 return errorList;
}


/* for In Batch */
function getItemNumberListByBinLocation(index) {  

	$("#batchNo" + index).html('');
	$("#batchNo" + index).trigger('chosen:updated');

	var itemid = $("#itemid").val();
	var binLocId = $("#inBatchBinLocId" + index).val();
	
	if(binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) {
		$("#availableQtyInBatch" + index).val('');
	}else{
		var requestData = {
			"itemid" : itemid,
			"binLocId" : binLocId,
		};
		var ajaxResponse = __doAjaxRequest("IndentProcess.html?getItemNumbersByBin", 'post', requestData, false, 'json');
		$("#batchNo" + index).append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$.each(ajaxResponse, function(key, value) {
			$("#batchNo" + index).append($("<option></option>").attr("code", key).attr("value", value).text(value));
		});
		$("#batchNo" + index).trigger('chosen:updated');		
	}

}


/* for In Batch */
function getInBatchAvailbleQtyByBin(index) {   
	$('.error-div').hide();
	var divName = '.content-page';
	var itemid = $("#itemid").val();
	var binLocId = $("#inBatchBinLocId" + index).val();
	var batchNo = $("#batchNo" + index).val();
	var issuedqtyInBatch = 0;
	
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
				"itemid" : itemid,
				"binLocId" : binLocId,
				"itemNo" : batchNo,
		};
		var availableQty = parseInt(__doAjaxRequest("IndentProcess.html?getAvailableQntyByBin", 'POST', requestData, false, 'json'));
		$("#availableQtyInBatch" + index).val(availableQty);
	}	
}


/* for In Batch */
function inBatchTableIdSequence(inBatchTableRow) { 
	$(inBatchTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "inBatchSrNo" + i);
		$(this).find("select:eq(0)").attr("id", "inBatchBinLocId" + i ).attr("onchange", "getItemNumberListByBinLocation("+i+")");			
		$(this).find("select:eq(1)").attr("id", "batchNo" + i ).attr("onchange", "getInBatchAvailbleQtyByBin("+i+")");
		$(this).find("input:text:eq(3)").attr("id", "availableQtyInBatch" +i);
		$(this).find("input:text:eq(4)").attr("id", "issuedQtyInBatch" +i);
									
		$(this).find("input:text:eq(0)").val(i + 1);		
		$(this).find("select:eq(0)").attr("name","listIndentIssueDtos[" + i + "].binlocation");
		$(this).find("select:eq(1)").attr("name","listIndentIssueDtos[" + i + "].itemno");
		$(this).find("input:text:eq(3)").attr("name","listIndentIssueDtos[" + i + "].availableQty");
		$(this).find("input:text:eq(4)").attr("name","listIndentIssueDtos[" + i + "].issueqty");
		
		$(inBatchTableRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}

/* for In Batch  */
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
			errorList.push(getLocalMessage("material.management.bin.location.validation") + " " + (i + 1));
		if (batchNo == "0" || batchNo == null || batchNo == "" || batchNo == undefined) 
			errorList.push(getLocalMessage("material.management.mdn.batch.no.validation") + " " + (i + 1));
		if (availableQtyInBatch == "0" || availableQtyInBatch == null || availableQtyInBatch == "" || availableQtyInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.available.quantity.not.empty") + " " + (i + 1));
		if (issuedQtyInBatch == "0" || issuedQtyInBatch == null || issuedQtyInBatch == "" || issuedQtyInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
	});	
	return errorList;
}

/* for In Batch  */
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


/* for In Batch  */
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


/* for In Batch  */
$("#inBatchTableId").on( 'click', '.deleteInBatchItemRow', function() { 
	var rowCount = $('#inBatchTableId tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
        var requestData = {
            rowIndex: $(this).closest('tr').index(), // Get the row index
        };
        var response = __doAjaxRequest('IndentProcess.html?doItemDetailsDeletion', 'POST', requestData , false, 'json');	
        $(this).closest('tr').remove();
		inBatchTableIdSequence('.inBatchTableRow');
		calculateInBatchIssuedQty();		
	}
});


/* for In Batch  */
function calculateInBatchIssuedQty() {   
	
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateInBatch();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var issuedqty = 0;
		$(".inBatchTableRow").each(function(i) {
			var issuedQtyInBatch= $('#issuedQtyInBatch' + i).val();
			if (issuedQtyInBatch == '') 
				issuedqty += parseFloat('0');
			else 
				issuedqty += parseInt(issuedQtyInBatch);
			$('#issuedqty').val(issuedqty);
		});
	}	
}


/* for Serial numbers list */
function getSerialListByBinLocation(index) {  
	$("#serialNumber" + index).html('');
	$('#serialNumber' + index).trigger('chosen:updated');

	var itemid = $("#itemid").val();
	binLocId = $("#serialBinLocId" + index).val();	

	if(binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) {
		$("#availableQtySerial" + index).val('');
	}else{
		var requestData = {
			"itemid" : itemid,
			"binLocId" : binLocId,
		};
		var ajaxResponse = __doAjaxRequest("IndentProcess.html?getItemNumbersByBin", 'post', requestData, false, 'json');
		$("#serialNumber" + index).append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$.each(ajaxResponse, function(key, value) {
			$("#serialNumber" + index).append($("<option></option>").attr("code", key).attr("value", value).text(value));
		});
		$("#serialNumber" + index).trigger('chosen:updated');		
	}
}


/* for Serial */
function getSerialAvailbleQty(index) {   
	$('.error-div').hide();
	var divName = '.content-page';
	var itemid = $("#itemid").val();
	var binLocId = $("#serialBinLocId" + index).val();
	var serialNumber = $("#serialNumber" + index).val();
	var issuedqtySerial = 0;
	
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
				"itemid" : itemid,
				"binLocId" : binLocId,
				"itemNo" : serialNumber,
		};
		var availableQty = parseInt(__doAjaxRequest("IndentProcess.html?getAvailableQntyByBin", 'POST', requestData, false, 'json'));
		$("#availableQtySerial" + index).val(availableQty);
	}	
}


/* for Serial  */
function serialTableIdSequence(serialTableRow) {
	$(serialTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "serSrNo" + i);
		$(this).find("select:eq(0)").attr("id", "serialBinLocId" + i ).attr("onchange", "getSerialListByBinLocation("+i+")");
		$(this).find("select:eq(1)").attr("id", "serialNumber" + i ).attr("onchange", "getSerialAvailbleQty("+i+")");
		$(this).find("input:text:eq(3)").attr("id", "availableQtySerial" +i);
		$(this).find("input:text:eq(4)").attr("id", "issuedQtySerial" +i);
		
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name","listIndentIssueDtos[" + i + "].binlocation");
		$(this).find("select:eq(1)").attr("name","listIndentIssueDtos[" + i + "].itemno");
		$(this).find("input:text:eq(3)").attr("name","listIndentIssueDtos[" + i + "].availableQty");
		$(this).find("input:text:eq(4)").attr("name","listIndentIssueDtos[" + i + "].issueqty");
				
		$(serialTableRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});

	});
}

/* for Serial  */
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
			errorList.push(getLocalMessage("material.management.bin.location.validation") + " " + (i + 1));
		if (serialNumber == "0" || serialNumber == null || serialNumber == "" || serialNumber == undefined) 
			errorList.push(getLocalMessage("department.indent.please.select.serial.no") + " " + (i + 1));
		if (availableQtySerial == "0" || availableQtySerial == null || availableQtySerial == "" || availableQtySerial == undefined) 
			errorList.push(getLocalMessage("department.indent.available.quantity.not.empty") + " " + (i + 1));
		if (issuedQtySerial == "0" || issuedQtySerial == null || issuedQtySerial == "" || issuedQtySerial == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
	});
	return errorList;
}


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


/* for Serial  */
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


/* for Serial  */
$("#serialTableId").on( 'click', '.deleteSerialUnitRow', function() {  
	var rowCount = $('#serialTableId tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
        var requestData = {
            rowIndex: $(this).closest('tr').index(), // Get the row index
        };
        var response = __doAjaxRequest('IndentProcess.html?doItemDetailsDeletion', 'POST', requestData , false, 'json');	
        $(this).closest('tr').remove();		
		serialTableIdSequence('.serialTableRow');
		calculateSerialIssuedQty();
	}
});


/* for Serial  */
function calculateSerialIssuedQty() {   
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateSerial();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var issuedqty = 0;
		$(".serialTableRow").each(function(i) {
			var issuedQtySerial= $('#issuedQtySerial' + i).val();		
			if (issuedQtySerial == '') 
				issuedqty += parseFloat('0');
			else 
				issuedqty += parseInt(issuedQtySerial);
			
			$('#issuedqty').val(issuedqty);
		});
	}	
}


/* for Not In Batch */
function getNotInBatchAvailbleQtyByBin(index) {  
	$('.error-div').hide();
	var errorList = [];
	var divName = '.content-page';
	var itemid = $("#itemid").val();
	var binLocId = $("#notInBatchBinLocId" + index).val();
	var issuedqtyNotInBatch = 0;
	
	errorList = validateUniqueBin();	
	if (errorList.length > 0) {
		$('.error-div').show();
		displayErrorsOnPage(errorList);
	} else {		
		if(binLocId == "0" || binLocId == null || binLocId == ""  || binLocId == undefined) {
			$("#availableQtyNotInBatch" + index).val('');
		}else{
			requestData = {
				"itemid" : itemid,
				"binLocId" : binLocId,
			};
			var availableQty = parseInt(__doAjaxRequest("IndentProcess.html?getNotInBatchAvailableQntyByBin", 'POST', requestData, false, 'json'));
			$("#availableQtyNotInBatch" + index).val(availableQty);		
		}
	}
}


/* for Not In Batch  */
function notInBatchTableIdSequence(notInBatchTableRow) {
	$(notInBatchTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "notInBatchSequence" + i);
		$(this).find("select:eq(0)").attr("id", "notInBatchBinLocId" + i ).attr("onchange", "getNotInBatchAvailbleQtyByBin(" + i + ")");			
		$(this).find("input:text:eq(2)").attr("id", "availableQtyNotInBatch" +i);
		$(this).find("input:text:eq(3)").attr("id", "issuedQtyNotInBatch" + i);
								
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name","listIndentIssueDtos[" + i + "].binlocation");
		$(this).find("input:text:eq(2)").attr("name","listIndentIssueDtos[" + i + "].availableQty");
		$(this).find("input:text:eq(3)").attr("name","listIndentIssueDtos[" + i + "].issueqty");
		
		$(notInBatchTableRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
		
	});
}


/* for Not In Batch  */
function validateNotInBatch() {
	var errorList = [];
	errorList = validateUniqueBin();
	$('.notInBatchTableRow').each(function(i) {
		var notInBatchBinLocId = $("#notInBatchBinLocId" + i).find("option:selected").attr('value');
		var issuedQtyNotInBatch = $("#issuedQtyNotInBatch" + i).val();
		var availableQtyNotInBatch = $("#availableQtyNotInBatch" + i).val();
	
		if (notInBatchBinLocId == "0" || notInBatchBinLocId == null  || notInBatchBinLocId == "" || notInBatchBinLocId == undefined) 
			errorList.push(getLocalMessage("material.management.bin.location.validation") + " " + (i + 1));
		if (availableQtyNotInBatch == "0" || availableQtyNotInBatch == null || availableQtyNotInBatch == "" || availableQtyNotInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.available.quantity.not.empty") + " " + (i + 1));
		if (issuedQtyNotInBatch == "0" || issuedQtyNotInBatch == null || issuedQtyNotInBatch == "" || issuedQtyNotInBatch == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
		if( parseInt(issuedQtyNotInBatch) > parseInt(availableQtyNotInBatch) )
			errorList.push(getLocalMessage("material.management.mdn.issued.quantity.not.greater.available.quantity") + " " + (i + 1));
	});
	return errorList;
}


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


/* for Not In Batch  */
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


/* for Not In Batch  */
$("#notInBatchTableID").on( 'click', '.deleteNotInBatchUnitRow', function() {  
	var rowCount = $('#notInBatchTableID tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	}else{
		var requestData = {
            rowIndex: $(this).closest('tr').index(), // Get the row index
        };
        var response = __doAjaxRequest('IndentProcess.html?doItemDetailsDeletion', 'POST', requestData , false, 'json');	
        $(this).closest('tr').remove();
		notInBatchTableIdSequence('.notInBatchTableRow'); 
		calculateNotInBatchIssuedQty();
	}
});


/* for Not In Batch  */
function calculateNotInBatchIssuedQty() {  
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateNotInBatch();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var issuedqty = 0;
		$(".notInBatchTableRow").each(function(i) {
			var issuedQtyNotInBatch= $('#issuedQtyNotInBatch' + i).val();

			if (issuedQtyNotInBatch == '') 
				issuedqty += parseFloat('0');
			else 
				issuedqty += parseInt(issuedQtyNotInBatch);		
			$('#issuedqty').val(issuedqty);
		});		
	}	
}

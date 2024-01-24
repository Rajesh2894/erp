$(document).ready(function() {
	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});
	
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});

	$(".deliveryDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
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

	$("#id_DisposalOfStock").dataTable({
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
	
	$("#id_ExpiredItemDetails").dataTable({
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
	
	timeRemover();

});


function timeRemover(){

	var expiryCheck = $('#expiryCheck').val();
	if (expiryCheck) 
		$('#expiryCheck').val(expiryCheck.split(' ')[0]);
	
	var movementDate = $('#movementDate').val();
	if (movementDate) 
		$('#movementDate').val(movementDate.split(' ')[0]);
	
	var fromDate = $('#fromDate').val();
	if (fromDate) 
		$('#fromDate').val(fromDate.split(' ')[0]);
	
	var toDate = $('#toDate').val();
	if (toDate) 
		$('#toDate').val(toDate.split(' ')[0]);
}



function yearLength() {
	var frmdateFields = $('#fromDateId');
	var todateFields = $('#toDateId');
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


/******************** Add Function*********** */
function addDisposalOfStockForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/***************** search Expired Items From Store ****************/
function searchExpiredItemsFromStore(element) {
	var errorList = [];
	errorList = validateSearchExpiedForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var data = {
			"expiryDate" : $('#expiryCheck').val(),
			"storeId" : $('#storeId').val(),
			"movementDate" : $('#movementDate').val(),
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('DisposalOfStock.html?getExpiredItemsFromStore', data, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function validateSearchExpiedForm(errorList) {
	var storeId = $('#storeId').val();
	var expiryCheck = $('#expiryCheck').val();
	var movementDate = $('#movementDate').val();
	
	if (storeId == "" || storeId == null || storeId == undefined || storeId == '0')
		errorList.push(getLocalMessage("material.management.store.name"));
	if (expiryCheck == "" || expiryCheck == null || expiryCheck == undefined) 
		errorList.push(getLocalMessage("material.management.select.expiry.on.before"));
	if (movementDate == "" || movementDate == null || movementDate == undefined) 
		errorList.push(getLocalMessage("material.management.select.movement.date"));
	if ( movementDate != "" && expiryCheck > movementDate) 
		errorList.push(getLocalMessage("material.management.expiry.on.before.date.should.less.movement.date"));

	return errorList;
}


/***************** save Expired Entry Add Form ****************/
function saveExpiredEntryForm(obj) {  
	var errorList = [];
	$('#id_ExpiredItemDetails').DataTable().destroy();
	errorList = validateExpiryAddForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(obj, " ", 'DisposalOfStock.html', 'saveform');
	}
}


function validateExpiryAddForm(errorList) {
	var storeId = $('#storeId').val();
	var expiryCheck = $('#expiryCheck').val();
	var movementDate = $('#movementDate').val();
	
	if (storeId == "" || storeId == null || storeId == undefined || storeId == '0')
		errorList.push(getLocalMessage("material.management.store.name"));
	if (expiryCheck == "" || expiryCheck == null || expiryCheck == undefined) 
		errorList.push(getLocalMessage("material.management.select.expiry.on.before"));
	if (movementDate == "" || movementDate == null || movementDate == undefined) 
		errorList.push(getLocalMessage("material.management.select.movement.date"));
	if (expiryCheck > movementDate) 
		errorList.push(getLocalMessage("material.management.expiry.on.before.date.should.less.movement.date"));
	
	errorList = validateItemDetails(errorList);
	
	return errorList;
}


function validateItemDetails(errorList) {
	$('.ExpiredItemDetailsRow').each(function(i){	
		var binLocation = $("#binLocation" + i).val();
		var itemId = $("#itemId" + i).val();
		var uomId = $("#uomId" + i).val();
		var quantity = $("#quantity" + i).val();
		
		if (binLocation == "0" || binLocation == null || binLocation == "" || binLocation == undefined) 
			errorList.push(getLocalMessage("material.management.bin.location.valid") + " " + (i + 1));
		if (itemId == "0" || itemId == null || itemId == "" || itemId == undefined) 
			errorList.push(getLocalMessage("material.management.item.name.cannot.empty") + " " + (i + 1));
		if (uomId == "0" || uomId == null || uomId == "" || uomId == undefined) 
			errorList.push(getLocalMessage("uoM.Can.Not.Be.Empty") + " " + (i + 1));
		if (quantity == "0" || quantity == null || quantity == "" || quantity == undefined) 
			errorList.push(getLocalMessage("material.management.expiry.quantity.valid") + " " + (i + 1));
	});
	return errorList;
}


function searchDisposalOfStock() {
	var errorList = [];

	var movementNo = $('#movementNo').val();
	var storeId = $('#storeId').val();
	var fromDateId = $('.fromDateId').val();
	var toDateId = $('.toDateId').val();
	
	if((movementNo == "" || movementNo == null || movementNo == undefined ) &&
			(storeId == "" || storeId == null || storeId == undefined ))
		errorList.push(getLocalMessage("material.management.store.or.movement.valid"));
	if (fromDateId == "" || fromDateId == null)
		errorList.push(getLocalMessage("material.expired.item.entry.from.date.validation"));
	if (toDateId == "" || toDateId == null)
		errorList.push(getLocalMessage("material.expired.item.entry.to.date.validation"));
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"movementNo" : movementNo,
			"storeId" : storeId,
			"fromDate" : fromDateId,
			"toDate" : toDateId,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('DisposalOfStock.html?searchDisposalItem', data, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function getExpiryItemData(formUrl, actionParam, expiryId) {
	var data = {
		"expiryId" : expiryId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function printExpiredItemOfStock() {
	var data = {
		"expiryId" : 10
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('DisposalOfStock.html?printDisposalOfStock', data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/********** Print Reciept ***************/
function printContent(expiryId) {
	var divName = '.content-page';
	var requestData = {
		"expiryId" : expiryId
	};
	var returnData  = __doAjaxRequest('DisposalOfStock.html?receiptDownload','POST',requestData, false);
	var title = '';
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
}


function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DisposalOfStock.html');
	$("#postMethodForm").submit();
}


function resetDisposalForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('DisposalOfStock.html?addDisposalOfStock',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function ResetForm(resetBtn) {
	resetForm(resetBtn);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('DisposalOfStock.html?addDisposalOfStock',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();

	prepareTags();
}


$("#isassetall").click(function() {
	var chk = $('#isassetall').is(':checked');
	if (chk) {
		$("input[id^='isasset']").prop('checked', true);
	} else {
		$("input[id^='isasset']").prop('checked', false);
	}
});


function clearDisposalList() {
	$("input[id^='isasset']").prop('checked', false);
}

function checkDisposalForm(element) {
    var flag=$('#'+element).val('Y');
}






$(document).ready(function(){
	
	$("#returnDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
	});
	
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
	
	
	$("#purchaseReturnItems").dataTable({
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
	
	if('V' == $('#saveMode').val()){
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

	
	var returnDate = $('#returnDate').val();
	if (returnDate) 
		$('#returnDate').val(returnDate.split(' ')[0]);
	
});


/*  Add Purchase Return Form   */
function addPurchaseReturnForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/* search Inspection Data By GRNId */
function searchInspectionDataById(element) {   
	var errorList = [];
	var grnId = $('#grnId').val();
	if (grnId == "" || grnId == null || grnId == undefined || grnId =='0' )
		errorList.push(getLocalMessage("material.management.select.grn.no"));
	if (errorList.length == 0) {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading('PurchaseReturn.html?getDataByGRNId', requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse); 
	}else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}	
}


/*  save Purchase Return Add Form  */
function savePurchaseReturnForm(obj) {  
	var errorList = [];
	$('#purchaseReturnItems').DataTable().destroy();
	errorList = validatePurchaseReturnForm();
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(obj, " ", 'PurchaseReturn.html?printPurchaseReturn', 'saveform');
	}
}


function validatePurchaseReturnForm() {
	var errorList = [];

	var grnId = $('#grnId').val();
	var returnDate = $("#returnDate").val();
	var poId = $('#poId').val();
	var poNo = $('#poNo').val();
	var grnDate = $('#grnDate').val();
	var storeId = $('#storeId').val();
	var storeName = $('#storeName').val();
	var vendorId = $('#vendorId').val();
	var vendorName = $('#vendorName').val();
	var noting = $('#noting').val();
	
	if (grnId == "" || grnId == null || grnId == undefined)
		errorList.push(getLocalMessage("material.management.select.grn.no"));
	if (returnDate == "" || returnDate == null || returnDate == undefined)
		errorList.push(getLocalMessage("material.management.return.date.valid"));
	if (poId == "" || poId == null || poId == undefined || poNo == "" || poNo == null || poNo == undefined)
		errorList.push(getLocalMessage("po.No.validation"));
	if (grnDate == "" || grnDate == null || grnDate == undefined)
		errorList.push(getLocalMessage("material.management.grn.date.not.empty"));
	if (storeId == "" || storeId == null || storeId == undefined || storeName == "" || storeName == null || storeName == undefined)
		errorList.push(getLocalMessage("material.management.store.name.valid"));
	if (vendorId == '' || vendorId == null || vendorId == undefined || vendorName == '' || vendorName == null || vendorName == undefined)
		errorList.push(getLocalMessage('material.management.vendor.name.not.empty'));
	if (noting == '' || noting == null || noting == undefined) 
		errorList.push(getLocalMessage('material.management.noting.valid'));
		
	return errorList;
}

/*  Veiw Purchase Return Form  */
function getPurchaseReturnData(formUrl, actionParam, returnId) {
	var data = {
		"returnId" : returnId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function searchPurchaseReturnSummaryData() {

	var errorList = [];

	var purRetId = $('#purRetId').val();
	var grnId = $('#grnId').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var storeId = $('#storeId').val();
	var vendorId = $('#vendorId').val();

	if ((purRetId == "" || purRetId == null || purRetId == undefined || purRetId == 0)
			&& (grnId == "" || grnId == null || grnId == undefined || grnId == 0)
			&& (fromDate == "" || fromDate == null) && (toDate == "" || toDate == null)
			&& (storeId == "" || storeId == null || storeId == undefined || storeId == 0)
			&& (vendorId == "" || vendorId == null || vendorId == undefined || vendorId == 0))
		errorList.push(getLocalMessage("grn.Select"));
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"returnId" : purRetId,
			"grnId" : grnId,
			"fromDate" : fromDate,
			"toDate" : toDate,
			"storeId" : storeId,
			"vendorId" : vendorId,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('PurchaseReturn.html?searchPurchaseReturn', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function PrintDiv(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

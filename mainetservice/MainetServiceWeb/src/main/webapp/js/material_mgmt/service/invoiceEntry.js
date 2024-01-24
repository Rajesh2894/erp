$(document).ready(function(){ 
		
	$('.chosen-select-no-results').chosen();
	
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
		
	$("#invoiceDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
	});
	
	if('V' == $('#saveMode').val()){
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

	timeRemover();
		
	itemDetailsDatatable();
	
	calculateTotalItemValue();
	
	totalOverheadCount();
	
});


/*  To Remove Time from Date   */
function timeRemover(){  	
	var invoiceDate = $('#invoiceDate').val();
	if (invoiceDate) 
		$('#invoiceDate').val(invoiceDate.split(' ')[0]);

	$('.firstUnitRow').each(function(i) {
		var grnDate = $("#grnDate" + i).val();
		if (grnDate)
			$("#grnDate" + i).val(grnDate.split(' ')[0]);
	});	
}


/*  Item Details Table Data Table  */
function itemDetailsDatatable(){
	$("#invoiceItemDetailsTableID").dataTable({
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
}



/* Add Purchase Return Form  */
function addInvoiceEntryForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/* Get PO Number List By Store  */
function getPONumbers(){
	$('#poId').html('');
	var storeId = $("#storeId").val();

	if (storeId == 0 || storeId == "" || storeId == null || storeId == undefined ){
		$('#poId').append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$('#poId').trigger('chosen:updated');
		$('#vendorId').val('');
	    $('#vendorName').val('');
		$('#grnId').append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$('#grnId').trigger('chosen:updated');
	}else{
		var requestData = {
			"storeId" : storeId
		};	
		var ajaxResponse = __doAjaxRequest("StoreInvoiceEntry.html?getPONumbersByStore", 'post', requestData, false, 'json');
		$('#poId').append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$.each(ajaxResponse, function(index, value) {
			$('#poId').append($("<option></option>").attr("value", index).attr("code", index).text(value));
		});
		$('#poId').trigger('chosen:updated');
	}
}


/* Get GRN Number List By Store And PO Number  */
function getGRNListByPoId(){
	$('#grnId').html('');
	$('#vendorId').val('');
    $('#vendorName').val('');
     
	var storeId = $("#storeId").val();
	var poId = $("#poId").val();

	if (poId == 0 || poId == "" || poId == null || poId == undefined ){
		$('#vendorId').val('');
	    $('#vendorName').val('');
		$('#grnId').append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$('#grnId').trigger('chosen:updated');
	}else{
		var requestData = {
			"storeId" : storeId,
			"poId" : poId
		};	
		var ajaxResponse = __doAjaxRequest("StoreInvoiceEntry.html?getGRNListByPoId", 'post', requestData, false, 'json');
		
		$('#grnId').append($("<option></option>").attr("value", 0).attr("code", 0).text("select"));
		$.each(ajaxResponse, function(key, value) {
			if (key != 'vendorId') {
				$('#grnId').append($("<option></option>").attr("value", value[0]).attr("code", value[1]).text(value[1]));
            }
			if (key == 'vendorId') {
                $('#vendorId').val(value[0]);
                $('#vendorName').val(value[1]);
            }		
		});
		$('#grnId').trigger('chosen:updated');
	}

}


/*search Invoice Entry Data By GRNId */
function searchInspectionDataById(element) {   
	var errorList = [];
	var grnId = $('#grnId').val();
	if (grnId == "" || grnId == null || grnId == undefined || grnId =='0' )
		errorList.push(getLocalMessage("material.management.select.grn.no"));
	if (errorList.length == 0) {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading('StoreInvoiceEntry.html?getGrnAndItemDetails', requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse); 
	}else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}	
}


/* To Calculate Total Item Amount  */
function calculateTotalItemValue() {   
	if ($.fn.DataTable.isDataTable('#invoiceItemDetailsTableID'))
		$('#invoiceItemDetailsTableID').DataTable().destroy();

	var value = 0.0;
	$(".invoiceItemDetailsRow").each(function(i) {
		if ($('#totalAmt' + i).val() != '')
			value += parseFloat($('#totalAmt' + i).val());
	});
	$('#itemAmt').val(value.toFixed(2));
	itemDetailsDatatable();
}


/*  Overheads Reorder  */
function reOrderUnitTabIdSequence(overheadsTableRow) { 
	$(overheadsTableRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
		$(this).find("select:eq(0)").attr("id", "description" +i);
		$(this).find("select:eq(1)").attr("id", "overHeadType" +i);
		$(this).find("input:text:eq(2)").attr("id", "amount" + i);
							
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name","invoiceEntryDTO.entryOverheadsDTOList[" + i + "].description");
		$(this).find("select:eq(1)").attr("name","invoiceEntryDTO.entryOverheadsDTOList[" + i + "].overHeadType");
		$(this).find("input:text:eq(2)").attr("name","invoiceEntryDTO.entryOverheadsDTOList[" + i + "].amount");
	});
}


/*  Deduction Validation  */
function validateDeductionForm(errorList) {
	$('.overheadsTableRow').each(function(i) {
		var description = $("#description" + i).find("option:selected").attr('value');
		var overHeadType = $("#overHeadType" + i).find("option:selected").attr('value');
		var amount = $("#amount" + i).val();

		if (description == "0" || description == null || description == "" || description == undefined)
			errorList.push(getLocalMessage("material.management.deductions.valid") + " " + (i + 1));
		if (overHeadType == "0" || overHeadType == null  || overHeadType == "" || overHeadType == undefined)
			errorList.push(getLocalMessage("department.indent.validation.decision") + " " + (i + 1));
		if (amount == null || amount == ""  || amount == undefined)
			errorList.push(getLocalMessage("material.management.amount.valid") + " " + (i + 1));
	});
	return errorList;
}


/* Overheads Add  */
$("#overheadsTableID").on('click', '.addOF', function() {
	var errorList = [];
	errorList = validateDeductionForm(errorList);
	if (errorList.length == 0) {
		var content = $("#overheadsTableID").find('tr:eq(1)').clone();
		$("#overheadsTableID").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		content.find('div.chosen-container').remove();
		content.find('[id^="description"]').chosen().trigger("chosen:updated");		
		$('.error-div').hide();
		reOrderUnitTabIdSequence('.overheadsTableRow'); 
	} else {
		displayErrorsOnPage(errorList);
	}
});

/* Overheads Remove  */
var removeIdArray = [];
$("#overheadsTableID").on( 'click', '.remOF', function() {
	var rowCount = $('#overheadsTableID tr').length;
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
	if (id != '')
		removeIdArray.push(id);
	$('#removeOverheadIds').val(removeIdArray);
	totalOverheadCount();
});


/* To Calculate Total Overhead Amount  */
function totalOverheadCount() {   
	var value = 0.0;
	$(".amountOverhead").each(function(i) {
		var overheadType = $('#overHeadType' + i).val();
		if(overheadType=='A'){
			if ($('#amount' + i).val() != '')
				value += parseFloat($('#amount' + i).val());
		}else if(overheadType=='D'){
			if ($('#amount' + i).val() != '')
				value -= parseFloat($('#amount' + i).val());
		}		
		$('#overheadAmt').val(value.toFixed(2));
	});
	totalInvoiceCount();
}


/* To Calculate Total PO Amount  */
function totalInvoiceCount() {
	var itemAmt = parseFloat($("#itemAmt").val()) || 0.00;
	var overheadAmt = parseFloat($("#overheadAmt").val()) || 0.00;

	var total = itemAmt + overheadAmt;
	if (!isNaN(total))
		$('#invoiceAmt').val(total.toFixed(2));
}


/* Invoice Entry Send for Approval OR Approval Submit */
function submitInvoiceEntryForm(obj) {  
	var errorList = [];	
	$('#invoiceItemDetailsTableID').DataTable().destroy();
	if('A' == $('#saveMode').val()){
		errorList = validateStoreInvoiceEntryForm(errorList);	
		errorList = validateDeductionForm(errorList);
	}
	
	if(1 == $('#levelCheck').val()){
		var decision = $("input[id='decision']:checked").val();
		if (decision == undefined || decision == '') 
			errorList.push(getLocalMessage("material.validation.select.Decision"));
		if ($("#comments").val() == "") 
			errorList.push(getLocalMessage("material.management.validate.Remark"))
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else
		return saveOrUpdateForm(obj," ", 'StoreInvoiceEntry.html?printInvoiceEntry', 'saveform');
}


function validateStoreInvoiceEntryForm(errorList) {
	var storeId = $('#storeId').val();
	var invoiceDate = $("#invoiceDate").val();
	var poId = $('#poId').val();
	var vendorId = $('#vendorId').val();
	var vendorName = $('#vendorName').val();
	var grnId = $('#grnId').val();
	var itemAmt = $('#itemAmt').val();
	var overheadAmt = $('#overheadAmt').val();
	var invoiceAmt = $('#invoiceAmt').val();
	
	if (storeId == "" || storeId== 0 || storeId == null || storeId == undefined )
		errorList.push(getLocalMessage("department.indent.validation.store"));
	if (invoiceDate == "" || invoiceDate == null || invoiceDate == undefined)
		errorList.push(getLocalMessage("material.management.invoice.date.valid"));
	if (poId == "" || poId == 0 || poId == null || poId == undefined )
		errorList.push(getLocalMessage("material.management.purchase.order.valid"));
	if (vendorId == '' || vendorId == null || vendorId == undefined || vendorName == '' || vendorName == null || vendorName == undefined)
		errorList.push(getLocalMessage('material.management.vendor.name.not.empty'));
	if (grnId == "" || grnId == null || grnId == undefined)
		errorList.push(getLocalMessage("material.management.grn.nulber.select.valid"));
	if (itemAmt == "" || itemAmt == null || itemAmt == undefined)
		errorList.push(getLocalMessage("material.management.total.item.value.valid"));
	if (overheadAmt == "" || overheadAmt == null || overheadAmt == undefined)
		errorList.push(getLocalMessage("material.management.total.overhead.amount.valid"));
	if (invoiceAmt == "" || invoiceAmt == null || invoiceAmt == undefined)
		errorList.push(getLocalMessage("material.management.total.invoice.amount.valid"));
		
	return errorList;
}



/* View OR Edit Invoice Entry Form */
function getStoreInvoiceEntryData(formUrl, actionParam, invoiceId) {
	var data = {
		"invoiceId" : invoiceId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/* Invoice Summary Search*/
function searchStoreInvoiceEntryData() {
	var errorList = [];
	var invoiceId = $('#invoiceId').val();
	var poId = $('#poId').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var storeId = $('#storeId').val();
	var vendorId = $('#vendorId').val();

	if ((invoiceId == "" || invoiceId == null || invoiceId == undefined || invoiceId == 0)
			&& (poId == "" || poId == null || poId == undefined || poId == 0)
			&& (fromDate == "" || fromDate == null) && (toDate == "" || toDate == null)
			&& (storeId == "" || storeId == null || storeId == undefined || storeId == 0)
			&& (vendorId == "" || vendorId == null || vendorId == undefined || vendorId == 0))
		errorList.push(getLocalMessage("grn.Select"));
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"invoiceId" : invoiceId,
			"poId" : poId,
			"fromDate" : fromDate,
			"toDate" : toDate,
			"storeId" : storeId,
			"vendorId" : vendorId,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('StoreInvoiceEntry.html?searchInvoiceEntryForm', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


var fileArray=[];
$("#deleteDoc").on("click",'#deleteFile',function(e) {
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {
		$(this).parent().parent().remove();
		var fileId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (fileId != '')
			fileArray.push(fileId);
		$('#removeFileById').val(fileArray);
	}
});


function PrintDiv(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}


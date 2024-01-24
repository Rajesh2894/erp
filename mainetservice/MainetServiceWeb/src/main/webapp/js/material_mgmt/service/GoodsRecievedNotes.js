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
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});


	var poDate = $('#poDate').val()
	if (poDate) 
		$('#poDate').val(poDate.split(' ')[0]);
	var receiveddate = $('#receiveddate').val()
	if (receiveddate) 
		$('#receiveddate').val(receiveddate.split(' ')[0]);
	
	$("#id_goodsReceivedNote").dataTable({
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

	if("V" == $("#saveMode").val())
		remainingqtytorcv(i);

});


function validategoodsitem(errorList){
	var errorList = [];	
	var Storeid=$('#storeId').val();
	var poNo= $('#poNo').val();
	let ReceivedDate = $("#GoodsReceivedNote input[id=receiveddate]").val();
	let Remark = $("#GoodsReceivedNote input[id=remarks]").val();
	
	if (Storeid == '' || Storeid == undefined)
		errorList.push(getLocalMessage('material.management.store.name'));
	if (ReceivedDate == '' || ReceivedDate == undefined)
		errorList.push(getLocalMessage('material.management.validate.ReceivedDate'));
	if ( Remark == '' ||  Remark == undefined)
		errorList.push(getLocalMessage('material.management.validate.Remark'));
	if (poNo == '' || poNo == undefined)
		errorList.push(getLocalMessage('material.management.validate.poNo'));
	
	var receivedqtyArray=[];
	var receivedqtyDisabledArray=[];
	$(".firstItemRow").each(function(i) {
		var orederqty = $("#orederqty" + i).val();
		var prevrecqt = $("#prevrecqt" + i).val();
		var receivedqty = $("#receivedqty" + i).val();
		
		if(receivedqty != '' && (parseFloat(receivedqty) > (parseFloat(orederqty) - parseFloat(prevrecqt))))
			errorList.push(getLocalMessage("material.management.order.received.validation") + " " + (i + 1));
		
		if($("#receivedqty" + i).is(':disabled') == false ){
			if(parseFloat(orederqty) > parseFloat(prevrecqt)){
				if(receivedqty =="" || receivedqty == undefined || receivedqty == null)
					errorList.push(getLocalMessage("material.management.received.quantity.valid")+" "+(i+1));	
				receivedqtyArray.push(parseFloat(receivedqty));
			}	
			receivedqtyDisabledArray.push(false);
		}else{
			receivedqtyDisabledArray.push(true);
		}
	});
	
	var hasNonZeroValue = receivedqtyArray.some(function(item) { return item !== 0; });
	if (!hasNonZeroValue && poNo){
		if(receivedqtyDisabledArray.includes(false))
			errorList.push(getLocalMessage("material.management.received.quantity.can.not.proceed"));		
		else
			errorList.push(getLocalMessage("material.management.grn.completed.against.po.valid"));		
	}
	return errorList;
}


function getpoitem() {
    var poNo= $('#poNo').val();
	if (poNo == '' || poNo == undefined){
		resetCommonForm();
		var errorList = [getLocalMessage('material.management.validate.poNo')];
		displayErrorsOnPage(errorList);		
	} else {
		var requestData = $('form').serialize();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('GoodsReceivedNotesItem.html?getPoItems', requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		vaidateQuantity();
	}
}

function vaidateQuantity() {
	$(".firstItemRow").each(function(i) {
		var orederqty = $("#orederqty" + i).val();
		var prevrecqt = $("#prevrecqt" + i).val();
		if (orederqty == prevrecqt){
			$("#receivedqty" + i).attr("disabled", true);
			$("#receivedqty" + i).val(0);
		}
	});
}


function saveGoodsReceivedNote(element) {
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validategoodsitem(errorList);
	if(errorList.length == 0){
		var respone = __doAjaxRequest("GoodsReceivedNotesItem.html?checkFileUpload", 'POST',$('form').serialize(), false, 'json');
		if (!respone)
			errorList.push(getLocalMessage('Please Upload Delivery Challan Or PO.'));		
	}	
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		return saveOrUpdateForm(element, '', 'GoodsReceivedNotesItem.html', 'saveform');
	}
}


function resetCommonForm() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}


function remainingqtytorcv(i) {
	if ($.fn.DataTable.isDataTable('#GoodsEntryTable'))
		$('#GoodsEntryTable').DataTable().destroy();
	var errorList = [];
	$(".firstItemRow").each(function(i) {
		var orderqty = parseFloat($('#orederqty' + i).val()) || 0.0;
		var receivedqty = parseFloat($('#receivedqty' + i).val()) || 0;
		var prevrecqt = parseFloat($('#prevrecqt' + i).val()) || 0.0;

		if (receivedqty != 0 && ((orderqty - prevrecqt) < receivedqty))
			errorList.push(getLocalMessage("material.management.order.received.validation") + " " + (i + 1));
		else {
			var remainingQty = orderqty - (prevrecqt + receivedqty);
			$("#remainingQty" + i).val(remainingQty.toFixed(1));			
		}
	});
	if(errorList.length > 0)
		displayErrorsOnPage(errorList);
}


function searchGoodsReceivedNote() {
	var errorList = [];
	var grnid = $('#grnid').val();
	var poid = $('#poid').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var storeId = $('#storeId').val();	
	
	if((grnid == "" || grnid == null || grnid == undefined ) &&
			(poid == "" || poid == null || poid == undefined ) &&
			(fromDate == "" || fromDate == null || fromDate == undefined ) &&
			(toDate == "" || toDate == null || toDate == undefined )&&
			(storeId == "" || storeId == null || storeId == undefined )){
		errorList.push(getLocalMessage("grn.Select"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var data = {
			"storeId" : storeId,
			"grnid" : grnid,
			"fromDate" : fromDate,
			"toDate" : toDate,
			"poid" : poid,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('GoodsReceivedNotesItem.html?searchGoodsReceivedNote', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function getPurchaseOrderData(formUrl, actionParam, grnid) {
	var data = {
		"grnid": grnid
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


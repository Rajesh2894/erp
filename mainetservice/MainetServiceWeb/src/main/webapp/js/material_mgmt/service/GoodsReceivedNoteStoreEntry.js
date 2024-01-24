$(document).ready(function(){ 
	
	$(".fromDateClass").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate : new Date(),
		onSelect: function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});

	$(".toDateClass").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate : new Date(),
		onSelect: function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	
	$("#inspectiondate").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: new Date(),
	});
	
	grnTimeRemover();
		
	$('.chosen-select-no-results').chosen();

	$("#id_goodsStore").dataTable({
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
	
	if ('V' == $('#saveMode').val()) {
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}
	
});


function grnTimeRemover(){  
	var receiveddate = $('#receiveddate').val()
	var inspectiondate = $('#inspectiondate').val()
	if (receiveddate) 
		$('#receiveddate').val(receiveddate.split(' ')[0]);
	if (inspectiondate) 
		$('#inspectiondate').val(inspectiondate.split(' ')[0]);
}

/********************SearchFunction************/
function searchGRNStoreEntryData(element) {    
	var errorList = [];
	var grnid = $('#grnid').val();
	if (grnid == "" || grnid == null || grnid == undefined)
		errorList.push(getLocalMessage("Please Select any GRN No."));
	if (errorList.length == 0) {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading('GoodsReceivedNoteStoreEntry.html?searchGrnStoreEntryData', requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse); 
	}else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}	
}

/*  validate Function  */
function validateGRNStoreEntry(){   
	var errorList = [];	

	var grnid = $('#grnid').val();
	var receiveddate = $('#receiveddate').val();
	var storeId = $('#storeId').val();
	var poNo = $('#poNo').val();
	var inspectiondate = $('#inspectiondate').val();
	var inspectorName = $('#inspectorName').val();
	
	if (grnid == "" || grnid == null || grnid == undefined)
		errorList.push(getLocalMessage("enter.grn.number"));
	if (receiveddate == "" || receiveddate == null || receiveddate == undefined)
		errorList.push(getLocalMessage("received.Date.validation"));
	if (storeId == "" || storeId == null || storeId == undefined)
		errorList.push(getLocalMessage("store.Name.validation"));
	if (poNo == "" || poNo == null || poNo == undefined)
		errorList.push(getLocalMessage("po.No.validation"));
	if (inspectiondate == '' || inspectiondate == undefined)
		errorList.push(getLocalMessage('Inspection.Date'));
	if (inspectorName == '' || inspectorName == undefined) 
		errorList.push(getLocalMessage('Inspector.Name'));
	
	errorList = validateItemDetails(errorList)
	
	return errorList;
}


function validateItemDetails(errorList) {   
	$('.firstUnitRow').each(
			function(i) {
				var itemDesc = $("#itemDesc" + i).val();
				var uomDesc = $("#uomDesc" + i).val();
				var managementCode = $("#managementCode" + i).val();
				var receivedqty = $("#receivedqty" + i).val();
				var acceptqty = $("#acceptqty" + i).val();
				var rejectqty = $("#rejectqty" + i).val();
				var storeRemarks = $("#storeRemarks" + i).val();
				
				if(itemDesc == "0" || itemDesc == null || itemDesc == ""  || itemDesc == undefined) 
					errorList.push(getLocalMessage("Item Name Can not Be Empty") + " " + (i + 1));				
				if(uomDesc == "0" || uomDesc == null || uomDesc == ""  || uomDesc == undefined) 
					errorList.push(getLocalMessage("UOM Can not Be Empty") + " " + (i + 1));				
				if(managementCode == "0" || managementCode == null || managementCode == ""  || managementCode == undefined) 
					errorList.push(getLocalMessage("Item Management Method Can not Be Empty") + " " + (i + 1));
				if(receivedqty == "0" || receivedqty == null || receivedqty == ""  || receivedqty == undefined) 
					errorList.push(getLocalMessage("Received Quantity Can not Be Empty") + " " + (i + 1));
				if(acceptqty == "0" || acceptqty == null || acceptqty == ""  || acceptqty == undefined) 
					errorList.push(getLocalMessage("Accepted Quantity Can not Be Empty") + " " + (i + 1));
				if(rejectqty == "0" || rejectqty == null || rejectqty == ""  || rejectqty == undefined) 
					errorList.push(getLocalMessage("Accepted Quantity Can not Be Empty") + " " + (i + 1));
				if(storeRemarks == "0" || storeRemarks == null || storeRemarks == ""  || storeRemarks == undefined) 
					errorList.push(getLocalMessage("material.management.validate.Remark") + " " + (i + 1));
			});
			return errorList;
	}

/*  view Inspection Page  */
function viewStoreEntryItemsForm(count, grnid, grnitemid,itemid,isExpiry,receivedqty,managementCode) { 
	var divName = '.content-page';
	requestData ={
			"count" : count,
			"grnid" : grnid,
			"grnitemid" : grnitemid,
			"itemid" : itemid,
			"isExpiry" : isExpiry,
			"receivedqty" : receivedqty,
			"managementCode" : managementCode
			};
	var ajaxResponse = __doAjaxRequest('GoodsReceivedNoteStoreEntry.html?grnStoreEntryDetails', 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}


/* submit form  */
function saveForm(obj, status) {
	 
	var errorList = [];
	errorList = validateGRNStoreEntry(errorList);
	if (errorList.length == 0) {
		$('#inspectionStatus').val(status);
		return saveOrUpdateForm(obj, " ", 'GoodsReceivedNoteStoreEntry.html', 'saveform');
	} else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
}

function searchGRNStoreEntry() {   
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
		var ajaxResponse = doAjaxLoading('GoodsReceivedNoteStoreEntry.html?searchGoodsStoreEnrty', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function resetGoodsStoreEntry() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function getGRNStoreEntryData(formUrl, actionParam, grnid) {
	var data = {
		"grnid" : grnid
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


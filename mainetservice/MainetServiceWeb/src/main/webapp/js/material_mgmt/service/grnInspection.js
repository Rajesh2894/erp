var url = "GoodsReceivedNoteInspection.html"
$(document).ready(function() {

	grnTimeRemover();

	$('.chosen-select-no-results').chosen();

	$("#id_GrnTable").dataTable({
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
	
	$("#inspectiondate").datepicker({
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
function searchInspectionData(element) {   
	var errorList = [];
	var grnId = $('#grnid').val();
	if (grnId == "" || grnId == null || grnId == undefined || grnId =='0' )
		errorList.push(getLocalMessage("material.management.select.grn.no"));
	if (errorList.length == 0) {
		var requestData = $('form').serialize();
		var ajaxResponse = doAjaxLoading('GoodsReceivedNoteInspection.html?searchGrnData', requestData, 'html', '');
		$('.content').removeClass('ajaxloader');
		$('.content-page').html(ajaxResponse); 
	}else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}	
}

/********************validate Function************/
function validateGRNInspection(){  
	var errorList = [];	
	
	var grnid = $('#grnid').val();
	var receiveddate = $('#receiveddate').val();
	var storeId = $('#storeId').val();
	var poNo = $('#poNo').val();
	var inspectiondate = $('#inspectiondate').val();
	var inspectorName = $('#inspectorName').val();
	
	if($("#saveMode").val() == "A"){
		if (grnid == "" || grnid == null || grnid == undefined || grnid == "grnid")
			errorList.push(getLocalMessage("material.management.select.grn.no"));		
	}
	if (receiveddate == "" || receiveddate == null || receiveddate == undefined)
		errorList.push(getLocalMessage("received.Date.validation"));
	if (storeId == "" || storeId == null || storeId == undefined)
		errorList.push(getLocalMessage("store.Name.validation"));
	if (poNo == "" || poNo == null || poNo == undefined || poNo =="0")
		errorList.push(getLocalMessage("po.No.validation"));
	if (inspectiondate == '' || inspectiondate == undefined)
		errorList.push(getLocalMessage('Inspection.Date'));
	if (inspectorName == '' || inspectorName == undefined || inspectorName == "0") 
		errorList.push(getLocalMessage('Inspector.Name'));

	errorList = validateItemDetails(errorList);
	
	return errorList;
}


function validateItemDetails(errorList) {   
	$('.firstUnitRow').each(function(i) {
			var itemDesc = $("#itemDesc" + i).val();
			var uomDesc = $("#uomDesc" + i).val();
			var managementCode = $("#managementCode" + i).val();
			var receivedqty = $("#receivedqty" + i).val();
			var acceptqty = $("#acceptqty" + i).val();
			var rejectqty = $("#rejectqty" + i).val();
			var inspectorremarks = $("#inspectorremarks" + i).val();
			
			if(itemDesc == "0" || itemDesc == null || itemDesc == ""  || itemDesc == undefined) 
				errorList.push(getLocalMessage("material.management.item.name.cannot.empty") + " " + (i + 1));				
			if(uomDesc == "0" || uomDesc == null || uomDesc == ""  || uomDesc == undefined) 
				errorList.push(getLocalMessage("UOM Can not Be Empty") + " " + (i + 1));				
			if(managementCode == "0" || managementCode == null || managementCode == ""  || managementCode == undefined) 
				errorList.push(getLocalMessage("material.management.method.cannot.empty") + " " + (i + 1));
			if(receivedqty == "0" || receivedqty == null || receivedqty == ""  || receivedqty == undefined) 
				errorList.push(getLocalMessage("material.management.quantity.cannot.empty") + " " + (i + 1));
			if((acceptqty == "0" || acceptqty == "0.0" || acceptqty == null || acceptqty == ""  || acceptqty == undefined) &&
				(rejectqty == "0" || rejectqty == "0.0" || rejectqty == null || rejectqty == ""  || rejectqty == undefined)) 
					errorList.push(getLocalMessage("material.management.accept.reject ") + " " + (i + 1));
			if(inspectorremarks == "0" || inspectorremarks == null || inspectorremarks == ""  || inspectorremarks == undefined) 
				errorList.push(getLocalMessage("material.management.enter.remarks") + " " + (i + 1));
		});
	return errorList;
}



/********************view Inspection Page************/
function viewInspectionItemsForm(object, count ) {
	var indexCount = $('#indexCount').val(count);
	var requestData = $('form').serialize();
	var ajaxResponse = doAjaxLoading("GoodsReceivedNoteInspection.html?grnInspectionDetails", requestData, 'html', '');
	$('.content').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
	prepareTags();
}



/******************** save form ************/
function saveForm(obj,status) {  
	var errorList = [];	
	errorList = validateGRNInspection(errorList);

	if(errorList.length == 0){
		var saveMode = $('#inspectionStatus').val(status);
		return saveOrUpdateForm(obj," ", 'GoodsReceivedNoteInspection.html', 'saveform');
	}else{
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
}


function searchGRNInspection() {  
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
		var ajaxResponse = doAjaxLoading('GoodsReceivedNoteInspection.html?searchGRNInspection', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


function getGRNInspectionData(formUrl, actionParam, grnid) {
	var data = {
		"grnid" : grnid
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}



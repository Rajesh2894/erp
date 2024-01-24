$(document).ready(function(){ 
	
	$("#storeInentSummaryTableID").dataTable({
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

	$("#storeIndentdate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
	});
	
	$(".expectedDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : new Date(),
	});
	
	
	if('V' == $('#saveMode').val()){
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

	var storeIndentdate = $('#storeIndentdate').val();
	if (storeIndentdate) 
		$('#storeIndentdate').val(storeIndentdate.split(' ')[0]);	
	var expectedDate = $('#expectedDate').val();
	if (expectedDate) 
		$('#expectedDate').val(expectedDate.split(' ')[0]);	
	
	$(".chosen-select-no-results").chosen();
		
});


/*  Search Store Indent   */
function searchStoreIndentData() {
	$('.error-div').hide();
	var errorList = [];

	var requestStore = $('#requestStore').val();
	var storeIndentId = $('#storeIndentId').val();
	var issueStore = $('#issueStore').val();
	var status = $('#status').val();

	if ((requestStore == "" || requestStore == null || requestStore == undefined || requestStore == '0')
			&& (storeIndentId == "" || storeIndentId == null || storeIndentId == undefined || storeIndentId == '0')
			&& (issueStore == "" || issueStore == null || issueStore == undefined || issueStore == '0')
			&& (status == "" || status == null || status == undefined || status == '0'))
		errorList.push(getLocalMessage("grn.Select"));
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"requestStore" : requestStore,
			"storeIndentId" : storeIndentId,
			"issueStore" : issueStore,
			"status" : status,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('StoreIndent.html?StoreIndentSearchForm', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


/*  Add Store Indent Form   */
function addStoreIndentForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/*  Get Requesting Store Details  */
function getRequestStoreDetails() {
	$('.error-div').hide();
	var errorList = [];
	var storeId = $("#requestStore").val();
	errorList = validateStores(errorList);
	if (errorList.length > 0 || storeId == ""){
		$('#requestStoreLocId').val('');
		$('#requestStoreLocName').val('');
		$('#requestedBy').val('');
		$('#requestedByName').val('');
		displayErrorsOnPage(errorList);
	} else {
		var requestData = "storeId=" + storeId;
		var object = __doAjaxRequest("StoreIndent.html?getStoreDetails", 'POST', requestData, false, 'json');
		$.each(object, function(key, value) {
			if (key == 'location') {
				$('#requestStoreLocId').val(value[0]);
				$('#requestStoreLocName').val(value[1]);
			}
			if (key == 'incharge') {
				$('#requestedBy').val(value[0]);
				$('#requestedByName').val(value[1]);
			}
		});
	}
}


/*  Get Issuing Store Details   */
function getIssuingStoreDetails() {
	$('.error-div').hide();
	var errorList = [];
	var storeId = $("#issueStore").val();
	errorList = validateStores(errorList);
	if (errorList.length > 0) {
		$('#issueStoreLocId').val('');
		$('#issueStoreLocName').val('');
		$('#issueIncharge').val('');
		$('#issueInchargeName').val('');
		displayErrorsOnPage(errorList);
	} else {
		var requestData = "storeId=" + storeId;
		var object = __doAjaxRequest("StoreIndent.html?getStoreDetails", 'POST', requestData, false, 'json');
		$.each(object, function(key, value) {
			if (key == 'location') {
				$('#issueStoreLocId').val(value[0]);
				$('#issueStoreLocName').val(value[1]);
			}
			if (key == 'incharge') {
				$('#issueIncharge').val(value[0]);
				$('#issueInchargeName').val(value[1]);
			}
		});
	}
}


function validateStores(errorList) {
	$('.error-div').hide();
	var requestStore = $("#requestStore").val();
	var issueStore = $("#issueStore").val();
	if((requestStore && issueStore) && (requestStore == issueStore))
		errorList.push(getLocalMessage('material.management.store.issueing.store.not.same'));
	return errorList;
}


/*  Get Unit of Measurement by Item   */
function getUom(i) {
	$('.error-div').hide();
	var errorList = validateUniqueItem();
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var itemId = $("#itemId" + i).val();
		if (itemId == "")
			$('#uomName' + i).val("");
		else {
			var requestData = "itemId=" + itemId;
			var uom = __doAjaxRequest("StoreIndent.html?getUom", 'POST', requestData, false, 'json');
			$('#uomName' + i).val(uom);
		}
	}
}


/* reOrder Item Details * */
function reOrderItemDetails(){
	$('.itemDetailsRow').each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("select:eq(0)").attr("id","itemId" + (i));	
		$(this).find("input:text:eq(2)").attr("id" ,"uomName" + (i));
		$(this).find("input:text:eq(3)").attr("id" ,"requestedQuantity" + (i));

        $("#sNo"+i).val(i+1);
		$(this).find("select:eq(0)").attr("name","storeIndentDto.storeIndentItemDtoList["+i+"].itemId").attr("onchange","getUom("+i+")");
		$(this).find("input:text:eq(2)").attr("name","storeIndentDto.storeIndentItemDtoList["+i+"].uomName");
		$(this).find("input:text:eq(3)").attr("name","storeIndentDto.storeIndentItemDtoList["+i+"].requestedQuantity");
		
		$(".itemDetailsRow").find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}


/* validate Item Details Form  */
function validateItemDetails(errorList) {
	$('.error-div').hide();
	$('.itemDetailsRow').each(function(i) {
		var itemId = $("#itemId" + i).val();
		var uomName = $("#uomName" + i).val();
		var requestedQuantity = $("#requestedQuantity" + i).val();
		
		if (itemId == "0" || itemId == null || itemId == "" || itemId == undefined)
			errorList.push(getLocalMessage('material.management.validate.item') + " " + (i + 1));
		if (uomName == "0" || uomName == null || uomName == "" || uomName == undefined)
			errorList.push(getLocalMessage('material.management.validate.UoM') + " " + (i + 1));
		if (requestedQuantity == "0" || requestedQuantity == null || requestedQuantity == "" || requestedQuantity == undefined)
			errorList.push(getLocalMessage('material.management.enter.quantity') + " " + (i + 1));
	});
	return errorList;
}


/* Add Item Details  */
function addIndentDetail(e) {
	var errorList = [];
	errorList = validateUniqueItem();
	errorList = validateItemDetails(errorList);
	if (errorList.length == 0) {
		var content = $("#storeIndentDetailTable").find('tr:eq(1)').clone();
		$("#storeIndentDetailTable").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("select").val("");
		content.find("input:hidden").val('');
		content.find('div.chosen-container').remove();
		content.find('[id^="itemId"]').chosen().trigger("chosen:updated");
		$('.error-div').hide();
		reOrderItemDetails(); 
	}else
		displayErrorsOnPage(errorList);
}


/* Remove Item Details  */
var removeIndentDetailArray = [];
$("#storeIndentDetailTable").on('click', '.remItemDet', function() {
	$('.error-div').hide();
	var rowCount = $('#storeIndentDetailTable tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$(this).closest('#storeIndentDetailTable tr').remove();
		reOrderItemDetails();
		id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (id != '')
			removeIndentDetailArray.push(id);
		$('#removeIndentDetailIds').val(removeIndentDetailArray);
	}
});


/* Submit Store Indent Form */
function submitStoreIndentForm(obj) {  
	$('.error-div').hide();
	var errorList = [];
	errorList = validateUniqueItem();
	errorList = validateStoreIndentForm(errorList);	
	errorList = validateItemDetails(errorList);
	errorList = validateStores(errorList);
	
	if(1 == $('#levelCheck').val()){
		var decision = $("input[id='decision']:checked").val();
		if (decision == undefined || decision == '') 
			errorList.push(getLocalMessage("material.validation.select.Decision"));
		if ($("#comments").val() == "") 
			errorList.push(getLocalMessage("material.management.validate.Remark"))
	}	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else
		return saveOrUpdateForm(obj," ", 'StoreIndent.html', 'saveform');
}


function closebox() {
	if( 1 == $('#levelCheck').val())
		window.location.href = 'AdminHome.html';
	else
		window.location.href = 'StoreIndent.html';
    $.fancybox.close();	
}



/* validate Store Indent Form * */
function validateStoreIndentForm(errorList) {
	var storeIndentdate = $('#storeIndentdate').val();	
	var requestStore = $("#requestStore").val();	
	var requestStoreLocName = $('#requestStoreLocName').val();
	var requestStoreLocId = $('#requestStoreLocId').val();
	var requestedBy = $('#requestedBy').val();
	var requestedByName = $('#requestedByName').val();
	var issueStore = $('#issueStore').val();	
	var issueStoreLocId = $('#issueStoreLocId').val();
	var issueStoreLocName = $('#issueStoreLocName').val();
	var issueIncharge = $('#issueIncharge').val();
	var issueInchargeName = $('#issueInchargeName').val();
	var deliveryAt = $('#deliveryAt').val();
	var expectedDate = $('#expectedDate').val();
	
	if (storeIndentdate == "" || storeIndentdate== '0' || storeIndentdate == null || storeIndentdate == undefined )
		errorList.push(getLocalMessage('material.management.select.indent.date'));
	if (requestStore == "" || requestStore == '0' || requestStore == null || requestStore == undefined)
		errorList.push(getLocalMessage('material.management.select.requesting.store'));
	if (requestStoreLocId == "" || requestStoreLocId == '0' || requestStoreLocId == null || requestStoreLocId == undefined ||
			requestStoreLocName == "" || requestStoreLocName == '0' || requestStoreLocName == null || requestStoreLocName == undefined)
		errorList.push(getLocalMessage('material.management.validate.requesting.store.location'));
	if (requestedBy == "" || requestedBy == '0' || requestedBy == null || requestedBy == undefined ||
			requestedByName == "" || requestedByName == '0' || requestedByName == null || requestedByName == undefined)
		errorList.push(getLocalMessage('material.management.validate.requesting.store.incharge'));
	if (issueStore == "" || issueStore == '0' || issueStore == null || issueStore == undefined)
		errorList.push(getLocalMessage('material.management.select.issuing.store'));
	if (issueStoreLocId == "" || issueStoreLocId == '0' || issueStoreLocId == null || issueStoreLocId == undefined ||
			issueStoreLocName == "" || issueStoreLocName == '0' || issueStoreLocName == null || issueStoreLocName == undefined)
		errorList.push(getLocalMessage('material.management.validate.issuing.store.location'));
	if (issueIncharge == "" || issueIncharge == '0' || issueIncharge == null || issueIncharge == undefined ||
			issueInchargeName == "" || issueInchargeName == '0' || issueInchargeName == null || issueInchargeName == undefined)
		errorList.push(getLocalMessage('material.management.validate.issuing.store.incharge'));
	if (deliveryAt == "" || deliveryAt== '0' || deliveryAt == null || deliveryAt == undefined )
		errorList.push(getLocalMessage('material.management.enter.delivety.at'));
	if (expectedDate == "" || expectedDate== '0' || expectedDate == null || expectedDate == undefined )
		errorList.push(getLocalMessage('material.management.select.delivery.date'));

	return errorList;
}


function getIndentDataById(formUrl, actionParam, storeIndentId) {
	var data = {
		"storeIndentId" : storeIndentId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function validateUniqueItem() {
	$('.error-div').hide();
	var errorList = [];
	var itemArray = [];
	$('.itemDetailsRow').each(function(i) {
		var itemId = $("#itemId" + i).val();
		if (itemArray.includes(itemId))
			errorList.push(getLocalMessage("purchase.requisition.duplicate.item.validate") + " " + (i + 1));
		itemArray.push(itemId);
	});
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	return errorList;
}


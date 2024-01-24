$(document).ready(function(){
	
	storeReturnDatatable();

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
	
	
	var storeReturnDate = $('#storeReturnDate').val();
	if (storeReturnDate){ $('#storeReturnDate').val(storeReturnDate.split(' ')[0]); }
	var mdnDate = $('#mdnDate').val();
	if (mdnDate) { $('#mdnDate').val(mdnDate.split(' ')[0]); }

	if('V' == $('#saveMode').val()){ 
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('input[type="text"], input[type="checkbox"]').prop("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

});


function storeReturnDatatable() {
	$("#storesReturnTableID").dataTable({
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
}


/*   Search Store Return    */
function searchStoreReturnData() {
	var errorList = [];
	var storeReturnId = $("#storeReturnId").val();
	var mdnId = $("#mdnId").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var requestStoreId = $('#requestStoreId').val();
	var issueStoreId = $('#issueStoreId').val();

	if ((storeReturnId == "" || storeReturnId == null || storeReturnId == undefined || storeReturnId == "0")
			&& (mdnId == "" || mdnId == null || mdnId == undefined || mdnId == "0")
			&& (fromDate == "" || fromDate == null || fromDate == undefined || fromDate == "0")
			&& (toDate == "" || toDate == null || toDate == undefined || toDate == "0")
			&& (requestStoreId == "" || requestStoreId == null || requestStoreId == undefined || requestStoreId == "0")
			&& (issueStoreId == "" || issueStoreId == null || issueStoreId == undefined || issueStoreId == "0"))
		errorList.push(getLocalMessage("grn.Select"));
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var data = {
			"storeReturnId" : storeReturnId,
			"mdnId" : mdnId,
			"fromDate" : fromDate,
			"toDate" : toDate,
			"requestStoreId" : requestStoreId,
			"issueStoreId" : issueStoreId,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('StoresReturn.html?searchStoresReturn', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}


/*  Add Store Return Form   */
function addStoreReturnForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/*  Get MDN Data and Rejected Item Details By MDN Id   */
function getMDNRejectedDetails() {
	var requestData = $('form').serialize();
	var ajaxResponse = doAjaxLoading('StoresReturn.html?getDataByNDNId', requestData, 'html', '');
	$('.content').removeClass('ajaxloader');
	$('.content-page').html(ajaxResponse);
}


/*   save Purchase Return Add Form   */
function submitStoreReturnForm(obj) {
	$('#storesReturnTableID').DataTable().destroy();
	var errorList = [];
	errorList = validateStoreReturnForm(errorList);
	errorList = validateReturnDetailForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		storeReturnDatatable();
	} else
		return saveOrUpdateForm(obj, " ", 'StoresReturn.html', 'saveform');
}


function closebox() {
	if( 1 == $('#levelCheck').val())
		window.location.href = 'AdminHome.html';
	else
		window.location.href = 'StoresReturn.html';
    $.fancybox.close();	
}


/*   Return Form Validation   */
function validateStoreReturnForm(errorList) {
    var storeReturnDate = $("#storeReturnDate").val();
	var mdnId = $('#mdnId').val();
	var mdnDate = $("#mdnDate").val();
	var issueStoreId = $('#issueStoreId').val();
	var issueStoreName = $('#issueStoreName').val();
	var requestStoreId = $('#requestStoreId').val();
	var requestStoreName = $('#requestStoreName').val();
	var storeIndentId = $('#storeIndentId').val();
	var storeIndentNo = $('#storeIndentNo').val();
	var noting = $('#noting').val();

    if (!storeReturnDate)
        errorList.push(getLocalMessage("material.management.return.date.valid"));
    if (!mdnId)
        errorList.push(getLocalMessage("material.management.mdn.validation"));
    if (!mdnDate)
        errorList.push(getLocalMessage("material.management.mdn.date.validation"));
    if (!issueStoreId || !issueStoreName)
        errorList.push(getLocalMessage("material.management.issuing.store.validation"));
    if (!requestStoreId || !requestStoreName)
        errorList.push(getLocalMessage("material.management.requesting.store.validation"));
    if (!storeIndentId || !storeIndentNo)
        errorList.push(getLocalMessage("material.management.store.indent.no.validation"));
    if (!noting)
        errorList.push(getLocalMessage("material.management.noting.valid"));
    return errorList;
}


/*   Return Item Detail Validation   */
function validateReturnDetailForm(errorList) {
	$('.storesReturnDetailRow').each(function(i) {
		var itemId = $("#itemId" + i).val();
		var uomName = $("#uomName" + i).val();
		var quantity = $("#quantity" + i).val();
		var returnReason = $("#returnReason" + i).val();
		var notForDisposal = !$("#disposalFlag" + i).is(':checked');
		var binLocation = $("#binLocation" + i).val();
		
		if (!itemId)
			errorList.push(getLocalMessage("material.management.item.name.cannot.empty") + " " + (i + 1));
		if (!uomName)
			errorList.push(getLocalMessage("material.management.validate.UoM") + " " + (i + 1));
		if (!quantity)
			errorList.push(getLocalMessage("material.management.store.quantity.validation") + " " + (i + 1));
		if (!returnReason)
			errorList.push(getLocalMessage("material.management.return.remarks.validation") + " " + (i + 1));
		if ($("#levelCheck").val() == 1) {
			if (notForDisposal) {
				if (!binLocation)
					errorList.push(getLocalMessage("material.management.bin.location.validation") + " " + (i + 1));
			}
		}
	});
	return errorList;
}


/* View Store Return Form */
function getStoreReturnById(formUrl, actionParam, storeReturnId) {
	var data = {
		"storeReturnId" : storeReturnId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


/* Check Is Mark For Disposal */
function checkDisposalFlag(obj, rowIndex) {
    var binLocation = $('#binLocation' + rowIndex);
    var disposalFlag = $(obj).is(':checked');
    if (disposalFlag) {
		$("#disposalFlag" + rowIndex).prop('checked', true).val('Y');
		binLocation.val('').prop("disabled", true).trigger("chosen:updated");
	} else {
		$("#disposalFlag" + rowIndex).prop('checked', false).val('N');
		binLocation.prop("disabled", false).trigger("chosen:updated");
	}
}


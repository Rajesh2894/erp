$(document).ready(function() {  
	
	getDatePicker();
	
	mdnTimeRemover();
	
	checkIsExpiry();
	
	calculateTotal();

	if ('V' == $('#saveMode').val()) {
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

});

function getDatePicker() {
	$(".datepickerMfg").datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: 'dd/mm/yy',
		maxDate: new Date(),
		onSelect: function(selectedDate) {
			$(".datepickerExp").datepicker("option", "minDate", selectedDate);
		}
	});

	$('.datepickerExp').datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
	});
}


/*  Remove Time from Dates   */
function mdnTimeRemover() {
	$(".mdnInspectionEntryRow").each(function(i) {
		var mfgDate = $("#mfgDate" + i).val();
		var expiryDate = $("#expiryDate" + i).val();
		if (mfgDate)
			$("#mfgDate" + i).val(mfgDate.split(' ')[0]);
		if (expiryDate)
			$("#expiryDate" + i).val(expiryDate.split(' ')[0]);
	});
}


/*  checks whether Item is marked for Expiry  */
function checkIsExpiry() {
	var isExpiry = $("#isExpiry").val();
	if (isExpiry != 'Y') {
		$('.mdnInspectionEntryRow').each(function() {
			$("[id^=expiryDate]").attr("disabled", true);
		});
	}
}


/*  for Reorder Table  */
function reorderTableIdSequence(mdnInspectionEntryRow) {
	var management = $("#management").val();
	
	$(mdnInspectionEntryRow).each(function(i) {
		$(".datepickerMfg").removeClass("hasDatepicker");
		$(".datepickerExp").removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "srNo" + i);
		if (management != "N") {
			$(this).find("input:text:eq(1)").attr("id", "itemNo" + i);
			$(this).find("input:text:eq(2)").attr("id", "quantity" + i);
			$(this).find("input:text:eq(3)").attr("id", "acceptQty" + i);
			$(this).find("input:text:eq(4)").attr("id", "rejectQty" + i);
			$(this).find("input:text:eq(5)").attr("id", "mfgDate" + i);
			$(this).find("input:text:eq(6)").attr("id", "expiryDate" + i);
		} else {
			$(this).find("input:text:eq(1)").attr("id", "quantity" + i);
			$(this).find("input:text:eq(2)").attr("id", "acceptQty" + i);
			$(this).find("input:text:eq(3)").attr("id", "rejectQty" + i);
			$(this).find("input:text:eq(4)").attr("id", "mfgDate" + i);
			$(this).find("input:text:eq(5)").attr("id", "expiryDate" + i);
		}
		$(this).find("select:eq(1)").attr("id", "rejectionReason" + i);
		$(this).find("select:eq(2)").attr("id", "receivedBinLocation" + i);
											
		$(this).find("input:text:eq(0)").val(i + 1);
		if (management != "N") {
			$(this).find("input:text:eq(1)").attr("name", "noteItemsEntryDTOList[" + i + "].itemNo");
			$(this).find("input:text:eq(2)").attr("name", "noteItemsEntryDTOList[" + i + "].quantity");
			$(this).find("input:text:eq(3)").attr("name", "noteItemsEntryDTOList[" + i + "].acceptQty");
			$(this).find("input:text:eq(4)").attr("name", "noteItemsEntryDTOList[" + i + "].rejectQty");
			$(this).find("input:text:eq(5)").attr("name", "noteItemsEntryDTOList[" + i + "].mfgDate");
			$(this).find("input:text:eq(6)").attr("name", "noteItemsEntryDTOList[" + i + "].expiryDate");
		} else {
			$(this).find("input:text:eq(1)").attr("name", "noteItemsEntryDTOList[" + i + "].quantity");
			$(this).find("input:text:eq(2)").attr("name", "noteItemsEntryDTOList[" + i + "].acceptQty");
			$(this).find("input:text:eq(3)").attr("name", "noteItemsEntryDTOList[" + i + "].rejectQty");
			$(this).find("input:text:eq(4)").attr("name", "noteItemsEntryDTOList[" + i + "].mfgDate");
			$(this).find("input:text:eq(5)").attr("name", "noteItemsEntryDTOList[" + i + "].expiryDate");
		}
		$(this).find("select:eq(1)").attr("name", "noteItemsEntryDTOList[" + i + "].rejectionReason");
		$(this).find("select:eq(2)").attr("name", "noteItemsEntryDTOList[" + i + "].receivedBinLocation");

		getDatePicker();
		$(mdnInspectionEntryRow).find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	});
}


/*  Validation  */
function validateInspectionDetails(errorList) {
	var management = $("#management").val();
	$('.mdnInspectionEntryRow').each(function(i) {
		if (management != "N")
			var itemNo = $("#itemNo" + i).val();
		var quantity = $("#quantity" + i).val();
		var acceptQty = $("#acceptQty" + i).val();
		var rejectQty = $("#rejectQty" + i).val();
		var mfgDate = $("#mfgDate" + i).val();
		var expiryDate = $("#expiryDate" + i).val();
		var rejectionReason = $("#rejectionReason" + i).find("option:selected").attr('value');
		var receivedBinLocation = $("#receivedBinLocation" + i).find("option:selected").attr('value');
		
		if (management != "N") {
			if (itemNo == "0" || itemNo == null || itemNo == "" || itemNo == undefined)
				if (management == "B")
					errorList.push(getLocalMessage("material.management.mdn.management.batch.validate") + " " + (i + 1));			
				else if (management != "S")
					errorList.push(getLocalMessage("material.management.mdn.management.serial.validate") + " " + (i + 1));
		}		
		if (quantity == "0" || quantity == null || quantity == "" || quantity == undefined) 
			errorList.push(getLocalMessage("department.indent.issued.quantity") + " " + (i + 1));
		if (acceptQty == null || acceptQty == "" || acceptQty == undefined) 
			errorList.push(getLocalMessage("material.management.mdn.accept.quantity.validate") + " " + (i + 1));
		if (rejectQty == null || rejectQty == "" || rejectQty == undefined)
			errorList.push(getLocalMessage("material.management.mdn.accept.reject.validate") + " " + (i + 1));
		if (parseFloat(acceptQty) > parseFloat(quantity))
			errorList.push(getLocalMessage("material.management.mdn.accept.and.issue.quantity.validate") + " " + (i + 1));
		if (parseFloat(rejectQty) > parseFloat(quantity))
			errorList.push(getLocalMessage("material.management.mdn.reject.and.issue.quantity.validate") + " " + (i + 1));
		if ((parseFloat(acceptQty) + parseFloat(rejectQty)) > parseFloat(quantity))
			errorList.push(getLocalMessage("material.management.mdn.accept.reject.greater.issue.quantity.validate") + " " + (i + 1));
		if (mfgDate == "0" || mfgDate == null || mfgDate == "" || mfgDate == undefined)
			errorList.push(getLocalMessage("material.MFG.date.validation") + " " + (i + 1));
		if ($('#isExpiry').val() == 'Y'){
			if (expiryDate == "0" || expiryDate == null || expiryDate == "" || expiryDate == undefined)
				errorList.push(getLocalMessage("material.Expiry.date.validation") + " " + (i + 1));			
		}
		if (parseInt(rejectQty) > "0") {
			if (rejectionReason == "0" || rejectionReason == null || rejectionReason == "" || rejectionReason == undefined)
				errorList.push(getLocalMessage("material.Select.Rejection.Reason") + " " + (i + 1));
		} 
		if (parseInt(acceptQty) > "0") {
			if (receivedBinLocation == "0" || receivedBinLocation == null || receivedBinLocation == "" || receivedBinLocation == undefined)
				errorList.push(getLocalMessage("material.management.bin.location.validation") + " " + (i + 1));			
		}
	});	
	return errorList;
}


/*  for In Batch   */
function calculateTotal() {
	var errorList = [];
	var acceptQty = 0.00;
	var rejectQty = 0.00;
	$(".mdnInspectionEntryRow").each(function(i) {
		var quantity = parseFloat($('#quantity' + i).val());
		var detAcceptQty = parseFloat($('#acceptQty' + i).val()) || 0.00;
		var detRejectQty = parseFloat($('#rejectQty' + i).val()) || 0.00;
		var rejectionReason = $('#rejectionReason' + i);
		var receivedBinLocation = $('#receivedBinLocation' + i);
		
		if (detAcceptQty > quantity)
			errorList.push(getLocalMessage("material.management.mdn.accept.and.issue.quantity.validate") + " " + (i + 1));
		else {
			if (detAcceptQty > 0.00) {
				acceptQty += detAcceptQty;
				receivedBinLocation.prop("disabled", false).chosen().trigger('chosen:updated');
			} else
				receivedBinLocation.val('').prop("disabled", true).chosen().trigger('chosen:updated');
			detRejectQty = parseFloat($('#rejectQty' + i).val(quantity - detAcceptQty).val());

			if (detRejectQty > 0.00) {
				rejectQty += detRejectQty;
				rejectionReason.prop("disabled", false);
			} else
				rejectionReason.val('').prop("disabled", true);

			if (detRejectQty > quantity)
				errorList.push(getLocalMessage("material.management.mdn.reject.and.issue.quantity.validate") + " " + (i + 1));
			if ((detAcceptQty + detRejectQty) > quantity)
				errorList.push(getLocalMessage("material.management.mdn.accept.reject.greater.issue.quantity.validate") + " " + (i + 1));
		}
	});
	if(errorList.length > 0){
		displayErrorsOnPage(errorList);		
	} else {
		$('#acceptQty').val(acceptQty.toFixed(2));
		$('#rejectQty').val(rejectQty.toFixed(2));		
	}
}


/*  save MDN Issue Details Form  */
function saveMDNInspectionDetails(element) {  
	$("#errorDiv").hide();
	var errorList = [];	
	errorList = validateInspectionDetails(errorList);

	if (parseInt($('#issuedQty').val()) !== (parseInt($('#acceptQty').val()) + parseInt($('#rejectQty').val()))) 
		errorList.push(getLocalMessage("material.total.rejected.Quantity.equal.validation"));
	var storeRemarks = $('#storeRemarks').val();
	if (storeRemarks == "0" || storeRemarks == null || storeRemarks == "" || storeRemarks == undefined) 
		errorList.push(getLocalMessage("material.management.validate.Remark"));
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var requestData = $('form').serialize();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading("MaterialDispatchNote.html?saveMDNItemEntryDetails", requestData, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
}


/*  Back to MDN Form  */
function backToMDNForm(){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MaterialDispatchNote.html?backToMDNForm');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}


$(document).ready(function() {  

	getDatePicker();
	
	grnTimeRemover();
	
	checkIsExpired();
		
	if ('V' == $('#saveMode').val()) {
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}
	
	var managementMethod = $('#managementMethod').val();
	if (managementMethod == 'B')
		inBatchChangeDecision();
	else if (managementMethod == 'S')
		serialChangeDecision();
	else
		notInBatchChangeDecision();

	
	$('.hasCharacterNumbers').on('keyup', function() {
	    this.value = this.value.replace(/[^a-z A-Z 0-9]/g,'');
	});

})


function getDatePicker(){
	$(".datepickerMfg").datepicker({                        
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd/mm/yy',  
        maxDate : new Date(),
        onSelect: function( selectedDate ) {
            $( ".datepickerExp" ).datepicker( "option", "minDate", selectedDate );
          }        
	});
	
    $('.datepickerExp').datepicker({
        dateFormat : 'dd/mm/yy',
        changeMonth : true,
        changeYear : true,
    });		
}

function grnTimeRemover(){  
	var managementMethod = $('#managementMethod').val();
	if( managementMethod == 'B'){
		$('.inBatchTableRow').each(function(i) {
			var mfgdate = $("#inBatchMfgdate" + i).val();
			var expirydate = $("#inBatchExpirydate" + i).val();
			
			if (mfgdate)
				$("#inBatchMfgdate" + i).val(mfgdate.split(' ')[0]);
			if (expirydate)
				$("#inBatchExpirydate" + i).val(expirydate.split(' ')[0]);
		});
	}else if( managementMethod == 'S'){
		$('.serialTableRow').each(function(i) {
			var mfgdate = $("#serialMfgdate" + i).val();
			var expirydate = $("#serialExpirydate" + i).val();
			
			if (mfgdate)
				$("#serialMfgdate" + i).val(mfgdate.split(' ')[0]);
			if (expirydate)
				$("#serialExpirydate" + i).val(expirydate.split(' ')[0]);
		});
	}else{
		$('.notInBatchTableRow').each(function(i) {
			var mfgdate = $("#notInBatchMfgdate" + i).val();
			var expirydate = $("#notInBatchExpirydate" + i).val();
			
			if (mfgdate)
				$("#notInBatchMfgdate" + i).val(mfgdate.split(' ')[0]);
			if (expirydate)
				$("#notInBatchExpirydate" + i).val(expirydate.split(' ')[0]);
		});
	}

}

/* checks whether Item is marked for Expiry  */
function checkIsExpired(){
	var isExpiry = $("#isExpiry").val();
	if(isExpiry != 'Y'){   
		$('.notInBatchTableRow').each(function(i) {
			$("[id^=notInBatchExpirydate]").attr("disabled", true);
		});
		$('.inBatchTableRow').each(function(i) {
				$("[id^=inBatchExpirydate]").attr("disabled", true);
		});
		$('.serialTableRow').each(function(i) {
				$("[id^=serialExpirydate]").attr("disabled", true);
		});
	}
}


/* for In Batch  */
function inBatchTableIdSequence(inBatchTableRow) { 
	$(inBatchTableRow).each(
		function(i) {
			$(".datepickerMfg").removeClass("hasDatepicker");
			$(".datepickerExp").removeClass("hasDatepicker");
			$(this).find("input:text:eq(0)").attr("id", "inBatchSrNo" + i);
			$(this).find("input:text:eq(1)").attr("id", "batchNo" +i);
			$(this).find("input:text:eq(2)").attr("id", "quantityInBatch" +i);
			$(this).find("input:text:eq(3)").attr("id", "inBatchMfgdate" +i);
			$(this).find("input:text:eq(4)").attr("id", "inBatchExpirydate" +i);
			$(this).find("select:eq(0)").attr("id", "inBatchDecision" +i);
			$(this).find("select:eq(1)").attr("id", "inBatchRejectionreason" +i);
										
			$(this).find("input:text:eq(0)").val(i + 1);				
			$(this).find("input:text:eq(1)").attr("name","inspectionItemsList[" + i + "].itemNo");
			$(this).find("input:text:eq(2)").attr("name","inspectionItemsList[" + i + "].quantity");
			$(this).find("input:text:eq(3)").attr("name","inspectionItemsList[" + i + "].mfgDate");
			$(this).find("input:text:eq(4)").attr("name","inspectionItemsList[" + i + "].expiryDate");
			$(this).find("select:eq(0)").attr("name","inspectionItemsList[" + i + "].decision");
			$(this).find("select:eq(1)").attr("name","inspectionItemsList[" + i + "].rejectionReason");
			getDatePicker();
		});
}

/* for In Batch  */
function validateInBatch() {   
	var errorList = [];
	errorList = validateUniqueBatchNo();

	$('.inBatchTableRow').each(
		function(i) {
			var batchNo = $("#batchNo" + i).val();
			var quantityInBatch = $("#quantityInBatch" + i).val();
			var inBatchMfgdate = $("#inBatchMfgdate" + i).val();
			var inBatchExpirydate = $("#inBatchExpirydate" + i).val();
			var inBatchDecision = $("#inBatchDecision" + i).find("option:selected").attr('value');
			var inBatchRejectionreason = $("#inBatchRejectionreason" + i).find("option:selected").attr('value');

			if (batchNo == "0" || batchNo == null || batchNo == "" || batchNo == undefined) 
				errorList.push(getLocalMessage("material.validation.batch.no") + " " + (i + 1));
			if (quantityInBatch == "0" || quantityInBatch == null || quantityInBatch == "" || quantityInBatch == undefined) 
				errorList.push(getLocalMessage("material.management.quantity.validation.at.row") + " " + (i + 1));
			if (inBatchMfgdate == "0" || inBatchMfgdate == null  || inBatchMfgdate == "" || inBatchMfgdate == undefined) 
				errorList.push(getLocalMessage("material.MFG.date.validation") + " " + (i + 1));
			if( $('#isExpiry').val() == 'Y' ){
				if (inBatchExpirydate == "0" || inBatchExpirydate == null  || inBatchExpirydate == "" || inBatchExpirydate == undefined) 
					errorList.push(getLocalMessage("material.Expiry.date.validation") + " " + (i + 1));
			}
			if (inBatchDecision == "0" || inBatchDecision == null || inBatchDecision == ""  || inBatchDecision == undefined) 
				errorList.push(getLocalMessage("material.validation.select.Decision") + " " + (i + 1));
			if(inBatchDecision != "Y"){
				if(inBatchRejectionreason == "0" || inBatchRejectionreason == null || inBatchRejectionreason == ""  || inBatchRejectionreason == undefined) 
					errorList.push(getLocalMessage("material.Select.Rejection.Reason") + " " + (i + 1));
			}
		});

	return errorList;
}


/* for In Batch  */
function addUnitRowInBatch(obj) { 
	var errorList = [];
	errorList = validateInBatch(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var content = $("#inBatchTableId").find('tr:eq(1)').clone();
		$("#inBatchTableId").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		$('.error-div').hide();
		inBatchTableIdSequence('#inBatchTableId .inBatchTableRow');
		inBatchChangeDecision();
	}
}

/* for In Batch  */
var removeInBatchIdArray = [];
$("#inBatchTableId").on( 'click', '.deleteInBatchItemRow', function() { 
	var rowCount = $('#inBatchTableId tr').length;
	
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}else{
		var requestData = {
			rowIndex: $(this).closest('tr').index(), // Get the row index
		};
		var response = __doAjaxRequest('GoodsReceivedNoteInspection.html?doItemDetailsDeletion', 'POST', requestData, false, 'json');
		$(this).closest('tr').remove();
		inBatchTableIdSequence('.inBatchTableRow');
		var id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (id != '')
			removeInBatchIdArray.push(id);
		$('#removeInBatchIds').val(removeInBatchIdArray);
		calculateInBatch();
	}
});


function validateUniqueBatchNo(){
	$("#errorDiv").hide();
	var batchNoArray=[];
	var errorList = [];
	$('.inBatchTableRow').each(function(i){
		var batchNo = $("#batchNo" + i).val();
		if (batchNoArray.includes(batchNo)) 
			errorList.push(getLocalMessage("material.Duplicate.Batch.No.Found.for.Batch.No.")+" "+batchNo);
		batchNoArray.push(batchNo);
		$('#itemNumberList').val(batchNoArray);
	});		
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}

function validateUniqueSerialNo() {
	$("#errorDiv").hide();
	var serialNoArray = [];	
	var errorList = [];
	$('.serialTableRow').each(function(i) {
		var serialNumber = $("#serialFromTo" + i).val();
		if (serialNoArray.includes(serialNumber)) 
			errorList.push(getLocalMessage("material.Duplicate.Serial.Number.Found.for.Serial.Number")+ " " + serialNumber);
		serialNoArray.push(serialNumber);
		$('#itemNumberList').val(serialNoArray);
	});
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	return errorList;
}


/* for In Batch  */
function inBatchChangeDecision() {   
	$(".inBatchTableRow").each(function(i) {
		var checkDes = $('#inBatchDecision' + i ).val();
		var inBatchRejectionreason= $('#inBatchRejectionreason' + i);
		if('V' != $('#saveMode').val()){
			if (checkDes == 'Y')
				inBatchRejectionreason.prop("disabled", true);
			else 
				inBatchRejectionreason.prop("disabled", false);
		}				
		calculateInBatch();
	});
}


/* for In Batch  */
function calculateInBatch() {   
	var acceptQty = 0;
	var rejectQty = 0;
	$(".inBatchTableRow").each(function(i) {
		var checkDes = $('#inBatchDecision' + i ).val();
		var quantityInBatch= $('#quantityInBatch' + i).val();
		if (checkDes == 'Y') {
			if (quantityInBatch == '') {
				acceptQty += parseInt('0');
			} else {
				acceptQty += parseInt(quantityInBatch);
			}	
		}
		if (checkDes == 'N') {
			if (quantityInBatch == '') {
				rejectQty += parseInt('0');
			} else {
				rejectQty += parseInt(quantityInBatch);
			}	
		}	
	$('#totalAcceptedQuantity').val(acceptQty);
	$('#totalRejectedQuantity').val(rejectQty);
	});
}



/* for Serial  */
function serialTableIdSequence(serialTableRow) {
	$(serialTableRow).each(function(i) {
		$(".datepickerMfg").removeClass("hasDatepicker");
		$(".datepickerExp").removeClass("hasDatepicker");
		
		$(this).find("input:text:eq(0)").attr("id", "serSrNo" + i);
		$(this).find("input:text:eq(1)").attr("id", "serialFromTo" +i);
		$(this).find("input:text:eq(2)").attr("id", "serQuantity" +i);
		$(this).find("input:text:eq(3)").attr("id", "serialMfgdate" +i);
		$(this).find("input:text:eq(4)").attr("id", "serialExpirydate" +i);
		$(this).find("select:eq(0)").attr("id", "serialDecision" +i);
		$(this).find("select:eq(1)").attr("id", "serialRejectionreason" +i);
			
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("input:text:eq(1)").attr("name","inspectionItemsList[" + i + "].itemNo");
		//default value for Quantity in Serial is 1
		$(this).find("input:text:eq(2)").attr("name","inspectionItemsList[" + i + "].quantity").val('1');
		$(this).find("input:text:eq(3)").attr("name","inspectionItemsList[" + i + "].mfgDate");
		$(this).find("input:text:eq(4)").attr("name","inspectionItemsList[" + i + "].expiryDate");					
		$(this).find("select:eq(0)").attr("name","inspectionItemsList[" + i + "].decision");
		$(this).find("select:eq(1)").attr("name","inspectionItemsList[" + i + "].rejectionReason");
		getDatePicker();
	});
}

/* for Serial  */
function validateSerial() {   
	var errorList = [];
	errorList = validateUniqueSerialNo(errorList);
	$('.serialTableRow').each(function(i) {
				var serialFromTo = $("#serialFromTo" + i).val();
				var serQuantity = $("#serQuantity" + i).val();
				var serialMfgdate = $("#serialMfgdate" + i).val();
				var serialExpirydate = $("#serialExpirydate" + i).val();
				var serialDecision = $("#serialDecision" + i).find("option:selected").attr('value');
				var serialRejectionreason = $("#serialRejectionreason" + i).find("option:selected").attr('value');

				if (serialFromTo == "0" || serialFromTo == null || serialFromTo == "" || serialFromTo == undefined) 
					errorList.push(getLocalMessage("material.validation.Please.Enter.Serial.No.From") + " " + (i + 1));
				if (serQuantity == "0" || serQuantity == null || serQuantity == "" || serQuantity == undefined) 
					errorList.push(getLocalMessage("material.management.quantity.validation.at.row") + " " + (i + 1));
				if (serialMfgdate == "0" || serialMfgdate == null  || serialMfgdate == "" || serialMfgdate == undefined) 
					errorList.push(getLocalMessage("material.MFG.date.validation") + " " + (i + 1));
				if( $('#isExpiry').val() == 'Y' ){
					if (serialExpirydate == "0" || serialExpirydate == null  || serialExpirydate == "" || serialExpirydate == undefined) 
						errorList.push(getLocalMessage("material.Expiry.date.validation") + " " + (i + 1));
				}
				if (serialDecision == "0" || serialDecision == null || serialDecision == ""  || serialDecision == undefined) 
					errorList.push(getLocalMessage("material.validation.select.Decision") + " " + (i + 1));
				if(serialDecision != "Y"){
					if(serialRejectionreason == "0" || serialRejectionreason == null || serialRejectionreason == ""  || serialRejectionreason == undefined) 
						errorList.push(getLocalMessage("material.Select.Rejection.Reason") + " " + (i + 1));
				}
			});
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
		content.find("input:text").val('');
		content.find("select").val('0');
		$('.error-div').hide();
		serialTableIdSequence('#serialTableId .serialTableRow');
	}
}

/* for Serial  */
var removeSerialIdArray = [];
$("#serialTableId").on( 'click', '.deleteSerialUnitRow', function() { 
	var rowCount = $('#serialTableId tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}else{
		var requestData = {
			rowIndex: $(this).closest('tr').index(), // Get the row index
		};
		var response = __doAjaxRequest('GoodsReceivedNoteInspection.html?doItemDetailsDeletion', 'POST', requestData, false, 'json');
		$(this).closest('tr').remove();
		serialTableIdSequence('.serialTableRow');
		id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (id != '') {
			removeSerialIdArray.push(id);
		}
		$('#removeSerialIds').val(removeSerialIdArray);
		calculateSerial();
	}
});



/* for Serial  */
function serialChangeDecision() {
	$(".serialTableRow").each(function(i) {
		var checkDes = $('#serialDecision' + i).val();
		var serialRejectionreason = $('#serialRejectionreason' + i);
		if('V' != $('#saveMode').val()){
			if (checkDes == 'Y') 
				serialRejectionreason.prop("disabled", true);
			 else 
				serialRejectionreason.prop("disabled", false);
		}		
		calculateSerial();
	});
}


/* for Serial  */
function calculateSerial() {   
	var acceptQty = 0;
	var rejectQty = 0;
	$(".serialTableRow").each(function(i) {
		var checkDes = $('#serialDecision' + i ).val();
		var serQuantity= $('#serQuantity' + i).val();
		if (checkDes == 'Y') {
			if (serQuantity == '') 
				acceptQty += parseInt('0');
			else 
				acceptQty += parseInt(serQuantity);	
		}
		if (checkDes == 'N') {
			if (serQuantity == '') 
				rejectQty += parseInt('0');
			else 
				rejectQty += parseInt(serQuantity);
		}	
	$('#totalAcceptedQuantity').val(acceptQty);
	$('#totalRejectedQuantity').val(rejectQty);
	});
}


/* for Not In Batch  */
function notInBatchTableIdSequence(notInBatchTableRow) {
	$(notInBatchTableRow).each(
		function(i) {
			$(".datepickerMfg").removeClass("hasDatepicker");
			$(".datepickerExp").removeClass("hasDatepicker");
			
			$(this).find("input:text:eq(0)").attr("id", "notInBatchSequence" + i);
			$(this).find("input:text:eq(1)").attr("id", "notInBatchQuantity" +i);
			$(this).find("input:text:eq(2)").attr("id", "notInBatchMfgdate" + i);
			$(this).find("input:text:eq(3)").attr("id", "notInBatchExpirydate" +i);
			$(this).find("select:eq(0)").attr("id", "notInBatchDecision" +i);
			$(this).find("select:eq(1)").attr("id", "notInBatchRejectionreason" +i);
								
			$(this).find("input:text:eq(0)").val(i + 1);
			$(this).find("input:text:eq(1)").attr("name","inspectionItemsList[" + i + "].quantity");
			$(this).find("input:text:eq(2)").attr("name","inspectionItemsList[" + i + "].mfgDate");
			$(this).find("input:text:eq(3)").attr("name","inspectionItemsList[" + i + "].expiryDate");
			$(this).find("select:eq(0)").attr("name","inspectionItemsList[" + i + "].decision");
			$(this).find("select:eq(1)").attr("name","inspectionItemsList[" + i + "].rejectionReason");
			getDatePicker();
		});
}

/* for Not In Batch  */
function validateNotInBatch() {
	var errorList = [];
	$('.notInBatchTableRow').each(
			function(i) {
				var notInBatchQuantity = $("#notInBatchQuantity" + i).val();
				var notInBatchMfgdate = $("#notInBatchMfgdate" + i).val();
				var notInBatchExpirydate = $("#notInBatchExpirydate" + i).val();
				var notInBatchDecision = $("#notInBatchDecision" + i).find("option:selected").attr('value');
				var notInBatchRejectionreason = $("#notInBatchRejectionreason" + i).find("option:selected").attr('value');

				if (notInBatchQuantity == "0" || notInBatchQuantity == null || notInBatchQuantity == "" || notInBatchQuantity == undefined) 
					errorList.push(getLocalMessage("material.management.quantity.validation.at.row") + " " + (i + 1));
				if (notInBatchMfgdate == "0" || notInBatchMfgdate == null  || notInBatchMfgdate == "" || notInBatchMfgdate == undefined) 
					errorList.push(getLocalMessage("material.MFG.date.validation") + " " + (i + 1));
				if( $('#isExpiry').val() == 'Y' ){
					if (notInBatchExpirydate == "0" || notInBatchExpirydate == null  || notInBatchExpirydate == "" || notInBatchExpirydate == undefined) 
						errorList.push(getLocalMessage("material.Expiry.date.validation") + " " + (i + 1));
				}
				if (notInBatchDecision == "0" || notInBatchDecision == null || notInBatchDecision == ""  || notInBatchDecision == undefined) 
					errorList.push(getLocalMessage("material.validation.select.Decision") + " " + (i + 1));
				if(notInBatchDecision != "Y"){
					if(notInBatchRejectionreason == "0" || notInBatchRejectionreason == null || notInBatchRejectionreason == ""  || notInBatchRejectionreason == undefined) 
						errorList.push(getLocalMessage("material.Select.Rejection.Reason") + " " + (i + 1));
				}
			});
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
		content.find("input:text").val('');
		content.find("select").val('0');
		$('.error-div').hide();
		notInBatchTableIdSequence('.notInBatchTableRow');
		notInBatchChangeDecision();
	}
}


/* for Not In Batch  */
var removeNotInBatchIdArray = [];
$("#notInBatchTableID").on( 'click', '.deleteNotInBatchItemRow', function() { 
	var rowCount = $('#notInBatchTableID tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}else{
		var requestData = {
			rowIndex: $(this).closest('tr').index(), // Get the row index
		};
		var response = __doAjaxRequest('GoodsReceivedNoteInspection.html?doItemDetailsDeletion', 'POST', requestData, false, 'json');
		$(this).closest('tr').remove();
		notInBatchTableIdSequence('.notInBatchTableRow'); 
		id = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if (id != '') {
			removeNotInBatchIdArray.push(id);
		}
		$('#removeNotInBatchIds').val(removeNotInBatchIdArray);
		calculateNotInBatch();
	}
});


/* for Not In Batch  */
function notInBatchChangeDecision() {
	$(".notInBatchTableRow").each(function(i) {
		var checkDes = $('#notInBatchDecision' + i).val();
		var notInBatchRejectionreason = $('#notInBatchRejectionreason' + i);
		if('V' != $('#saveMode').val()){
			if (checkDes == 'Y')
				notInBatchRejectionreason.prop("disabled", true);
			else
				notInBatchRejectionreason.prop("disabled", false);
		}
		calculateNotInBatch();
	});
}

/* for Not In Batch  */
function calculateNotInBatch() {   
	var acceptQty = 0;
	var rejectQty = 0;
	$(".notInBatchTableRow").each(function(i) {
		var checkDes = $('#notInBatchDecision' + i ).val();
		var quantity= $('#notInBatchQuantity' + i).val();
		if (checkDes == 'Y') {
			if (quantity == '') {
				acceptQty += parseInt('0');
			} else {
				acceptQty += parseInt(quantity);
			}	
		}
		if (checkDes == 'N') {
			if (quantity == '') {
				rejectQty += parseInt('0');
			} else {
				rejectQty += parseInt(quantity);
			}	
		}	
	$('#totalAcceptedQuantity').val(acceptQty);
	$('#totalRejectedQuantity').val(rejectQty);
	});
}



/* save form */
function saveInspectionForm(element) { 
	var errorList = [];
	var managementMethod = $('#managementMethod').val();
	if (managementMethod == 'B')
		errorList = validateInBatch(errorList);
	else if (managementMethod == 'S')
		errorList = validateSerial(errorList);
	else
		errorList = validateNotInBatch();
	
	var receivedQuantity = parseInt($('#receivedqty').val());
	var totalAcceptedQuantity = parseInt($('#totalAcceptedQuantity').val());
	var totalRejectedQuantity = parseInt($('#totalRejectedQuantity').val());
	if(receivedQuantity != (totalAcceptedQuantity + totalRejectedQuantity))
		errorList.push(getLocalMessage("material.total.rejected.Quantity.equal.validation"));	
	
	var requestData = $('form').serialize();
	if (errorList.length == 0) {
		var duplicateItemNumbers = __doAjaxRequest("GoodsReceivedNoteInspection.html?duplicateChecker", 'POST', requestData, false, 'json');
		if(duplicateItemNumbers.length > 0){
			if( managementMethod == 'B')
				errorList.push(getLocalMessage("material.validation.Duplicate.Batch.Number.Batch.Number ")+ " " + duplicateItemNumbers);
			else if( managementMethod == 'S')
				errorList.push(getLocalMessage("material.Duplicate.Serial.Number.Found.for.Serial.Number")+ " " + duplicateItemNumbers);
		}
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading("GoodsReceivedNoteInspection.html?saveInspectionForm", requestData, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
}


/* back button */
function backButton(mode) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('GoodsReceivedNoteInspection.html?backToMainSearch');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	if('V' == mode){
		disableFields();
	}
}


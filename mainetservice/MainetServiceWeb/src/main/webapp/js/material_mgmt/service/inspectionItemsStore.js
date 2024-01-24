$(document).ready(function() { 

	getDatePicker();
	
	grnTimeRemover();
	
	checkIsExpired();
	
		
	if('V' == $('#saveMode').val()){
		disableFields();
	}
	
	var managementMethod = $('#managementMethod').val();
	if( managementMethod == 'B'){
		inBatchChangeDecision();		
	}else if( managementMethod == 'S'){
		serialChangeDecision();		
	}else{
		notInBatchChangeDecision();
	}
	
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

/********** checks whether Item is marked for Expiry  *********/
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


/*********************** for In Batch  ***********************************************/
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
			$(this).find("select:eq(2)").attr("id", "inBatchBinLocId" +i);
										
			$(this).find("input:text:eq(0)").val(i + 1);				
			$(this).find("input:text:eq(1)").attr("name","inspectionItemsList[" + i + "].itemNo");
			$(this).find("input:text:eq(2)").attr("name","inspectionItemsList[" + i + "].quantity");
			$(this).find("input:text:eq(3)").attr("name","inspectionItemsList[" + i + "].mfgDate");
			$(this).find("input:text:eq(4)").attr("name","inspectionItemsList[" + i + "].expiryDate");
			$(this).find("select:eq(0)").attr("name","inspectionItemsList[" + i + "].decision");
			$(this).find("select:eq(1)").attr("name","inspectionItemsList[" + i + "].rejectionReason");
			$(this).find("select:eq(2)").attr("name","inspectionItemsList[" + i + "].binLocation");
			
			getDatePicker();
		});
}

/*********************** for In Batch  ***********************************************/
function validateInBatch() {   
var errorList = [];
$('.inBatchTableRow').each(
		function(i) {
			var inBatchDecision = $("#inBatchDecision" + i).find("option:selected").attr('value');
			var inBatchBinLocId = $("#inBatchBinLocId" + i).find("option:selected").attr('value');
			
			if(inBatchDecision == "Y"){
				if(inBatchBinLocId == "0" || inBatchBinLocId == null || inBatchBinLocId == ""  || inBatchBinLocId == undefined) 
					errorList.push(getLocalMessage("please.Select.Bin.Location") + " " + (i + 1));
			}
		});
		return errorList;
}


/*********************** for In Batch  ***********************************************/
function inBatchChangeDecision() {   
	$(".inBatchTableRow").each(function(i) {
		var checkDes = $('#inBatchDecision' + i ).val();
		var binLocation = $('#inBatchBinLocId' + i);
		if('V' != $('#saveMode').val()){
			if (checkDes == 'Y')
				binLocation.prop("disabled", false);
			else 
				binLocation.prop("disabled", true);
		}				
		calculateInBatch();
	});
}


/*********************** for In Batch  ***********************************************/
function calculateInBatch() {   
	var acceptQty = 0;
	var rejectQty = 0;
	$(".inBatchTableRow").each(function(i) {
		var checkDes = $('#inBatchDecision' + i ).val();
		var quantityInBatch= $('#quantityInBatch' + i).val();
		if (checkDes == 'Y') {
			if (quantityInBatch == '')
				acceptQty += parseInt('0');
			else 
				acceptQty += parseInt(quantityInBatch);	
		}
		if (checkDes == 'N') {
			if (quantityInBatch == '') 
				rejectQty += parseInt('0');
			else 
				rejectQty += parseInt(quantityInBatch);	
		}	
	$('#totalAcceptedQuantity').val(acceptQty);
	$('#totalRejectedQuantity').val(rejectQty);
	});
}



/*********************** for Serial  ***********************************************/
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
		$(this).find("select:eq(2)").attr("id", "serialBinLocId" +i);
			
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("input:text:eq(1)").attr("name","inspectionItemsList[" + i + "].itemNo");
		$(this).find("input:text:eq(2)").attr("name","inspectionItemsList[" + i + "].quantity");
		$(this).find("input:text:eq(3)").attr("name","inspectionItemsList[" + i + "].mfgDate");
		$(this).find("input:text:eq(4)").attr("name","inspectionItemsList[" + i + "].expiryDate");					
		$(this).find("select:eq(0)").attr("name","inspectionItemsList[" + i + "].decision");
		$(this).find("select:eq(1)").attr("name","inspectionItemsList[" + i + "].rejectionreason");
		$(this).find("select:eq(2)").attr("name","inspectionItemsList[" + i + "].binLocation");
		getDatePicker();
	});
}

/*********************** for Serial  ***********************************************/
function validateSerial() {   
	var errorList = [];
	$('.serialTableRow').each(function(i) {
		var serialDecision = $("#serialDecision" + i).find("option:selected").attr('value');
		var serialBinLocId = $("#serialBinLocId" + i).find("option:selected").attr('value');

		if(serialDecision == "Y"){
			if(serialBinLocId == "0" || serialBinLocId == null || serialBinLocId == ""  || serialBinLocId == undefined) 
				errorList.push(getLocalMessage("please.Select.Bin.Location") + " " + (i + 1));
		}
	});
	return errorList;
}


/*********************** for Serial  ***********************************************/
function serialChangeDecision() {
	$(".serialTableRow").each(function(i) {
		var checkDes = $('#serialDecision' + i).val();
		var binLocation = $('#serialBinLocId' + i);
		if('V' != $('#saveMode').val()){
			if (checkDes == 'Y') 
				binLocation.prop("disabled", false);
			 else 
				 binLocation.prop("disabled", true);
		}		
		calculateSerial();
	});
}


/*********************** for Serial  ***********************************************/
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


/*********************** for Not In Batch  ***********************************************/
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
			$(this).find("select:eq(2)").attr("id", "notInBatchBinLocId" +i);
											
			$(this).find("input:text:eq(0)").val(i + 1);
			$(this).find("input:text:eq(1)").attr("name","inspectionItemsList[" + i + "].quantity");
			$(this).find("input:text:eq(2)").attr("name","inspectionItemsList[" + i + "].mfgDate");
			$(this).find("input:text:eq(3)").attr("name","inspectionItemsList[" + i + "].expiryDate");
			$(this).find("select:eq(0)").attr("name","inspectionItemsList[" + i + "].decision");
			$(this).find("select:eq(1)").attr("name","inspectionItemsList[" + i + "].rejectionReason");
			$(this).find("select:eq(2)").attr("name","inspectionItemsList[" + i + "].binLocation");
			getDatePicker();
		});
}

/*********************** for Not In Batch  ***********************************************/
function validateNotInBatch() {
	var errorList = [];
	$('.notInBatchTableRow').each(function(i) {
		var notInBatchDecision = $("#notInBatchDecision" + i).find("option:selected").attr('value');
		var notInBatchBinLocId = $("#notInBatchBinLocId" + i).find("option:selected").attr('value');

		if(notInBatchDecision == "Y"){
			if(notInBatchBinLocId == "0" || notInBatchBinLocId == null || notInBatchBinLocId == ""  || notInBatchBinLocId == undefined) 
				errorList.push(getLocalMessage("please.Select.Bin.Location") + " " + (i + 1));
		}
	});
	return errorList;
}


/*********************** for Not In Batch  ***********************************************/
function notInBatchChangeDecision() {
	$(".notInBatchTableRow").each(function(i) {
		var checkDes = $('#notInBatchDecision' + i).val();
		var binLocation = $('#notInBatchBinLocId' + i);
		if('V' != $('#saveMode').val()){
			if (checkDes == 'Y')
				binLocation.prop("disabled", false);
			else
				binLocation.prop("disabled", true);
		}
		calculateNotInBatch();
	});
}

/*********************** for Not In Batch  ***********************************************/
function calculateNotInBatch() {   
	var acceptQty = 0;
	var rejectQty = 0;
	$(".notInBatchTableRow").each(function(i) {
		var checkDes = $('#notInBatchDecision' + i ).val();
		var quantity= $('#notInBatchQuantity' + i).val();
		if (checkDes == 'Y') {
			if (quantity == '') 
				acceptQty += parseInt('0');
			else 
				acceptQty += parseInt(quantity);	
		}
		if (checkDes == 'N') {
			if (quantity == '') 
				rejectQty += parseInt('0');
			else 
				rejectQty += parseInt(quantity);	
		}	
	$('#totalAcceptedQuantity').val(acceptQty);
	$('#totalRejectedQuantity').val(rejectQty);
	});
}



/******************** save form ************/
function saveInspectionForm(element) { 
	var errorList = [];	
	
	var managementMethod = $('#managementMethod').val();
	if( managementMethod == 'B'){
		errorList = validateInBatch();
	}else if( managementMethod == 'S'){
		errorList =  validateSerial();	
	}else{
		errorList = validateNotInBatch();
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {		
		var requestData = $('form').serialize();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading("GoodsReceivedNoteStoreEntry.html?saveStoreEntryForm", requestData, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
}


/******************** back button ************/
function backButton(mode) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('GoodsReceivedNoteStoreEntry.html?backToMainSearch');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	if('V' == mode){
		disableFields();
	}
}

function disableFields(){
	$('select').attr("disabled", true);
	$('input[type=text]').attr("disabled", true);
	$('input[type="text"], textarea').attr("disabled", true);
	$('select').prop('disabled', true).trigger("chosen:updated");
}







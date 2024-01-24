
$(document).ready(function(e){
	$('.has2Decimal').change(function() {
		var $this = $(this);
		var value = parseFloat(Math.round($this.val())).toFixed(2);
		$this.val(value);
	});
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '+90d',
		yearRange: "-100:-0",
	}); 

});
$("#billTable").on('click','.academicAddRow',function(e){
	 
	
		//$('#errorDivId').hide();
		 var errorList = [];
		
	/*	var row=e;
 		var val=(row + 1);*/
	     $('.billDetails').each(function(i) {
			errorList = validatePayModeDetails(errorList,i);
	     });
		  
	     if(errorList.length > 0){ 
	    	 displayErrorsOnPage(errorList);
	    	 return false;
	 	 }
	     $(".lessthancurrdate").datepicker("destroy").removeAttr('id');
	       
			
		var content = $(this).closest('tr').clone();
		$(this).closest("tr").after(content);
		var clickedIndex = $(this).parent().parent().index() - 1;
		var currIndex = $(this).parent().parent().index();
		 
		
		
		
		//content.find("input:text").val("");
	 
		$(content).find("td:eq(0)").attr("id", "academicSrNoId"+(clickedIndex + 1));
		$(content).find("select:eq(0)").attr("id", "payModeIn"+(clickedIndex + 1));
		$(content).find("input:text:eq(0)").attr("id", "bmChqDDDate"+(clickedIndex + 1));
		$(content).find("input:text:eq(1)").attr("id", "chqueNoId"+(clickedIndex + 1));
		$(content).find("input:text:eq(2)").attr("id", "micrNoId"+(clickedIndex + 1));
		$(content).find("input:text:eq(3)").attr("id", "accNo"+(clickedIndex + 1));
		$(content).find("input:text:eq(4)").attr("id", "amount"+(clickedIndex + 1));
		
		content.find('.academicAddRow').attr("id", "billAddButton"+ (clickedIndex + 1));
	 	content.find('.deleteMode').attr("id", "billDelButton"+ (clickedIndex + 1));
	 //	$("#academicSrNoId" + clickedIndex).text(clickedIndex + 1);
		content.find("select:eq(0)").attr("name","offlineDTO.multiModeList[" + (clickedIndex + 1)+ "].payModeIn");
		content.find("input:text:eq(0)").attr("name","offlineDTO.multiModeList[" + (clickedIndex + 1)+ "].bmChqDDDate");
		content.find("input:text:eq(1)").attr("name","offlineDTO.multiModeList[" + (clickedIndex + 1)+ "].bmChqDDNo");
		content.find("input:text:eq(2)").attr("name","offlineDTO.multiModeList[" + (clickedIndex + 1)+ "].micrCode");
		content.find("input:text:eq(3)").attr("name","offlineDTO.multiModeList[" + (clickedIndex + 1)+ "].bmBankAccountId");
		content.find("input:text:eq(4)").attr("name","offlineDTO.multiModeList[" + (clickedIndex + 1)+ "].amount");
	
		reOrderTableIdSequenceBill();
		datepickerFun();
		    $('#chqueNoId'+currIndex).val('');
			$('#bmChqDDDate'+currIndex).val('');
			$('#micrNoId'+currIndex).val('');
			$('#accNo'+currIndex).val('');
			$('#amount'+currIndex).val('');
		
		disableFieldsOnCashSelection(currIndex);
		/* $(".lessthancurrdate").datepicker("destroy").removeAttr('id');*/
		/* 
		$(".lessthancurrdate").datepicker({
	 		dateFormat: 'dd/mm/yy', 
			changeMonth: true,
			changeYear: true,
			maxDate: '-0d',
			yearRange: "-100:-0"
		});*/
});


$("#billTable").on('click','.deleteMode',function(){
	
	if($("#billTable tr").length != 2){
		
			 $(this).parent().parent().remove();
			 reOrderTableIdSequenceBill();
	}else{
			alert("You can not delete first row");
	}
	totalMultiModeAmount(); 
});

reOrderTableIdSequenceBill();

 

$(document).ready(function(){
	$("#multiAmount").val($("#amount0").val());
})

function reOrderTableIdSequenceBill() {
	

	$('.billDetails').each(
			function(i) {
				
				// for generating dynamic Id
				 
				$(this).find("select:eq(0)").attr("id", "payModeIn" + i);
				$(this).find("select:eq(1)").attr("id", "bankID" + i);
				$(this).find("input:text:eq(0)").attr("id", "bmChqDDDate" + i);
				$(this).find("input:text:eq(1)").attr("id", "chqueNoId" + i);
				//$(this).find("input:text:eq(2)").attr("id", "micrNoId" + i);
				$(this).find("input:text:eq(2)").attr("id", "accNo" + i);
				$(this).find("input:text:eq(3)").attr("id", "amount" + i);
				 
				$(this).find("select:eq(0)").attr("onchange", "disableFieldsOnCashSelection("+(i)+")");
				//$(this).find("select:eq(1)").attr("onchange", "getBankCode("+(i)+")");
				 
				$(this).parents('tr').find('.deleteMode').attr("id", "billDelButton" + i);
						
				$(this).parents('tr').find('.academicAddRow').attr("id", "billAddButton" + i);
						

				// $("#academicSrNoId" + i).text(i + 1);

				// for generating dynamic path
				$(this).find("select:eq(0)").attr("name","offlineDTO.multiModeList[" + i + "].payModeIn");
				$(this).find("select:eq(1)").attr("name","offlineDTO.multiModeList[" + i + "].cbBankid");
				$(this).find("input:text:eq(0)").attr("name","offlineDTO.multiModeList[" + i + "].bmChqDDDate");
				$(this).find("input:text:eq(1)").attr("name","offlineDTO.multiModeList[" + i + "].bmChqDDNo");
				//$(this).find("input:text:eq(2)").attr("name","offlineDTO.multiModeList[" + i + "].micrCode");
				$(this).find("input:text:eq(2)").attr("name", "offlineDTO.multiModeList[" + i + "].bmBankAccountId");
				$(this).find("input:text:eq(3)").attr("name","offlineDTO.multiModeList[" + i + "].amount");
			 
			});
	 
	addValidationsJquery();
}

function disableFieldsOnCashSelection(i){
	
	
	var selectedValue = $('#payModeIn'+i).find(":selected").attr('code');
	var tableSize = $("#billTable tr").length;
	if(tableSize == 3 && i == 0 && selectedValue == 'POS') {
		$('#chqueNoId'+i).val('');
		$('#bmChqDDDate'+i).val('');
		$('#micrNoId'+i).val('');
		$('#accNo'+i).val('');
		$('#bankID'+i).val('').trigger('chosen:updated');
		
		$('#chqueNoId'+i).attr("disabled", true);
		$('#bmChqDDDate'+i).attr("disabled", true);
		$('#micrNoId'+i).attr("disabled", true);
		$('#accNo'+i).attr("disabled", true);
		$('#bankID'+i).attr("disabled", true);
		 $('#bankID'+i).prop('disabled', true).trigger("chosen:updated");
		 // disable add
		 $('#billAddButton'+i).attr("disabled", true);
		 
		 return;
		
	}
	if((i > 0 || tableSize > 3 ) && selectedValue == 'POS' ) {
		//error and delete row
		$('#payModeIn'+i).parent().parent().remove();
		 reOrderTableIdSequenceBill();
		 var errorList = [];
		 errorList
			.push("POS cannot be selected with multiple payment mode");
		 showError(errorList);
		 errorList = [];
		 return;
		
	}
	if (selectedValue == 'C') {
		$('#chqueNoId'+i).val('');
		$('#bmChqDDDate'+i).val('');
		$('#micrNoId'+i).val('');
		$('#accNo'+i).val('');
		$('#bankID'+i).val('').trigger('chosen:updated');
		
		$('#chqueNoId'+i).attr("disabled", true);
		$('#bmChqDDDate'+i).attr("disabled", true);
		$('#micrNoId'+i).attr("disabled", true);
		$('#accNo'+i).attr("disabled", true);
		$('#bankID'+i).attr("disabled", true);
		 $('#bankID'+i).prop('disabled', true).trigger("chosen:updated");
		 $('#billAddButton'+i).attr("disabled", false);
		
	}else{
		$('#bankID'+i).val('').trigger('chosen:updated');
		$('#chqueNoId'+i).attr("disabled", false);
		$('#bmChqDDDate'+i).attr("readonly", true);
		$('#micrNoId'+i).attr("disabled", false);
		$('#accNo'+i).attr("disabled", false);
		$('#bmChqDDDate'+i).attr("disabled", false);
		$('#bankID'+i).prop('disabled', false).trigger("chosen:updated");
		$('#billAddButton'+i).attr("disabled", false);
	}
}

function validatePayModeDetails(errorList,i){
	var selectedValue = $('#payModeIn'+i).find(":selected").attr('code');
	
	var payMode = $('#payModeIn'+i).val();
	var checkDate = $('#bmChqDDDate'+i).val();
	var chequeNo = $('#chqueNoId'+i).val();
	var micrNoId = $('#micrNoId'+i).val();
	var amount = $('#amount'+i).val();
	var bankID = $("#bankID" + i).val();
	var currDate = new Date();
	var dateBefore3Month = currDate.setMonth(currDate.getMonth() - 3);
	
	if(selectedValue != undefined && selectedValue!="" && (selectedValue=='C' || selectedValue=='POS')){
		if(amount==undefined || amount==""){
			errorList
			.push(getLocalMessage("cfc.enter.amount.selected.paymentMode"));
		} else if (amount == '0.0' || amount == '0' || amount == '0.00') {
			errorList
					.push(getLocalMessage("cfc.enter.zeroAmount.validate"));

		}
	}else{
		if(payMode==undefined||payMode==""||payMode==0){
			errorList
			.push(getLocalMessage("cfc.select.paymentMode"));
		}
		if (bankID == '0' || bankID == null) {
			errorList.push(getLocalMessage("prop.validDrwanOn"));
		}
		if(checkDate==undefined||checkDate==""){
			errorList
			.push(getLocalMessage("cfc.select.cheque.dd.payOrder.date"));
		} else if (getDate(checkDate) < dateBefore3Month) {
			errorList.push(getLocalMessage("cfc.notAccept.cheque.dd.payOrder.date.greater.threeMonths"));
		}
		if(chequeNo==undefined||chequeNo==""){
			errorList
			.push(getLocalMessage("cfc.enter.cheque.dd.payOrder.no"));
		}else if(chequeNo.length<6){
			errorList
			.push(getLocalMessage("cfc.cheque.dd.payOrder.no.contains.sixDigit"));
		}
	/*	if(micrNoId==undefined||micrNoId==""){
			errorList
			.push(getLocalMessage("Please Enter the Micr Number"));
		}else if(micrNoId.length<9){
			errorList
			.push(getLocalMessage("Micr Number Should contain 9 Digits"));
		}*/
		if(amount==undefined||amount==""){
			errorList
			.push(getLocalMessage("cfc.enter.amount.selected.paymentMode"));
		} else if (amount == '0.0' || amount == '0' || amount == '0.00') {
			errorList
					.push(getLocalMessage("cfc.enter.zeroAmount.validate"));

		}
	}
	return errorList;
	
}

function  datepickerFun(){
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0",
	}); 
}


function showDiv(obj) {

	var selectPayValue = obj.value;
	if (selectPayValue === "P") {
		$('#billTable').show();
	} else {
		$('#billTable').hide();
	}
}


function totalMultiModeAmount(){
	var finalTotal=0;
		var data=$(".totAddMultiAmount");
		for(var i = 0; i < data.length; i++){
			finalTotal=+finalTotal + +$(data[i]).val();
		}
	$("#multiAmount").val(finalTotal.toFixed(2));
}


function validationForCollectionType(errorList){
	
	var payAtCounter =  $("#payAtCounter").is(":checked");
	var payByPos =  $("#payByPos").is(":checked");  
	
	if(!payAtCounter && !payByPos ){
		errorList
		.push(getLocalMessage("cfc.select.collectionType"));
	}
	return errorList;
}

function getDate(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}



function addValidationsJquery() {
	$('input.mandClassColor').blur(function() {
		var input=$(this);
		
		if(input.val() == 0 || input.val() == null || input.val() == " " )
			{input.removeClass("valid").addClass("invalid");
			   
			}  
		else
		{input.removeClass("invalid").addClass("valid");
		}
	});
	
	$('select.mandClassColor').blur('select', function() {
		var select=$(this);
		if(select.val() == 0 || select.val() == null)
		{ select.removeClass("valid").addClass("invalid");
		}
		else
		{select.removeClass("invalid").addClass("valid");
		}

	});
	
	$('textarea.mandClassColor').blur('input', function() {
		var textarea=$(this);
		if(textarea.val() == 0 || textarea.val() == null)
		{ textarea.removeClass("valid").addClass("invalid");
		}
		else
		{textarea.removeClass("invalid").addClass("valid");
		}

	});
	
	
	$('input.cal').change(function() {
		var input=$(this);
		if(input.val() == 0 || input.val() == null || input.val() == "")
		{input.removeClass("valid").addClass("invalid");
		}
		else
		{input.removeClass("invalid").addClass("valid");
		}
	}); 
	
	$("input[value=Reset]").click(function(){
		   if ( $('.registration input.mandClassColor').is('.valid') ) {
			   $('.registration input.mandClassColor').removeClass("valid");
		   }
		    if ( $('.registration textarea.mandClassColor').is('.valid') ) {
			   $('.registration textarea.mandClassColor').removeClass("valid");
		   }
		    if ($('.registration select.mandClassColor').is('.valid')) {
			   $('.registration select.mandClassColor').removeClass("valid");
		   }
		   	if ($('.registration input.mandClassColor').is('.invalid') ) {
			   $('.registration input.mandClassColor').removeClass("invalid");
		   }
		   	if ( $('.registration textarea.mandClassColor').is('.invalid')) {
			   $('.registration textarea.mandClassColor').removeClass("invalid");
		  }
			if ( $('.registration select.mandClassColor').is('.invalid') ) {
			   $('.registration select.mandClassColor').removeClass("invalid");
		   }
	}); 

	$("input[value=Reset]").click(function(){
		   if ( $('.form input.mandClassColor').is('.valid') ) {
			   $('.form input.mandClassColor').removeClass("valid");
		   }
		    if ( $('.form textarea.mandClassColor').is('.valid') ) {
			   $('.form textarea.mandClassColor').removeClass("valid");
		   }
		    if ($('.form select.mandClassColor').is('.valid')) {
			   $('.form select.mandClassColor').removeClass("valid");
		   }
		   	if ($('.form input.mandClassColor').is('.invalid') ) {
			   $('.form input.mandClassColor').removeClass("invalid");
		   }
		   	if ( $('.form textarea.mandClassColor').is('.invalid')) {
			   $('.form textarea.mandClassColor').removeClass("invalid");
		  }
			if ( $('.form select.mandClassColor').is('.invalid') ) {
			   $('.form select.mandClassColor').removeClass("invalid");
		   }
	}); 

$("input[value=Clear]").click(function(){
		   if ( $('.form input.mandClassColor').is('.valid') ) {
			   $('.form input.mandClassColor').removeClass("valid");
		   }
		    if ( $('.form textarea.mandClassColor').is('.valid') ) {
			   $('.form textarea.mandClassColor').removeClass("valid");
		   }
		    if ($('.form select.mandClassColor').is('.valid')) {
			   $('.form select.mandClassColor').removeClass("valid");
		   }
		   	if ($('.form input.mandClassColor').is('.invalid') ) {
			   $('.form input.mandClassColor').removeClass("invalid");
		   }
		   	if ( $('.form textarea.mandClassColor').is('.invalid')) {
			   $('.form textarea.mandClassColor').removeClass("invalid");
		  }
			if ( $('.form select.mandClassColor').is('.invalid') ) {
			   $('.form select.mandClassColor').removeClass("invalid");
		   }
	}); 

	
$(".hasNumber1").keypress(function(event) { return isNumber(event)});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});
jQuery('.hasNumber1to999').keyup(function () { 
    
	var pattern = new RegExp('^([1-9]|[1-9][0-9]|[1-9][0-9][0-9])$');
	
	if(!pattern.test(this.value))
	{
		this.value='';
	}
});

jQuery('.hasNameClass').keyup(function () { 
    this.value = this.value.replace(/[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~0-9]/g, '');
 
});
	
		
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
});

jQuery('.hasNoSpace').keyup(function () { 
    this.value = this.value.replace(/\s/g,'');
   
});

jQuery('.maxLength20').keyup(function () { 
	 $(this).attr('maxlength','20');
  
});

jQuery('.maxLength1950').keyup(function () { 
	
	 $(this).attr('maxlength','2000');
 
});

jQuery('.maxLength30').keyup(function () { 
	 $(this).attr('maxlength','60');
  
});

jQuery('.maxLength3').keyup(function () { 
	 $(this).attr('maxlength','3');
 
});

jQuery('.maxLength4').keyup(function () { 
	 $(this).attr('maxlength','4');

});

jQuery('.maxLength1980').keyup(function () { 
	 $(this).attr('maxlength','2000');
 
});

jQuery('.maxLength100').keyup(function () { 
	 $(this).attr('maxlength','100');
 
});
jQuery('.maxLength200').keyup(function () { 
	$(this).attr('maxlength','200');
	
});

jQuery('.maxLength10').keyup(function () { 
	$(this).attr('maxlength','10');
	
});

jQuery('.maxLength300').keyup(function () { 
	 $(this).attr('maxlength','300');
  
});


jQuery('.hasDecimal').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

jQuery('.hasCharacterWithPeriod').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z\.]/g,'');
   
});
jQuery
('.hasPincode').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','6');
});

jQuery('.hasMobileNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','10');
    $(this).attr('minlength','10');
});

jQuery('.hasAadharNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','14');
    $(this).attr('minlength','12');
});


jQuery('.hasFaxNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','12');
});

jQuery('.hasSpecialChara').keyup(function () { 
		
	if (this.value.match(/[^a-zA-Z ]/g )|| this.value.match(/[^\u0900-\u0954 ]/g) ){
		this.value = this.value.replace(/[^a-zA-Z\u0900-\u0954 ]/g, '');
	}   
});

jQuery('.hasSpecialCharAndNumber').keyup(function () { 
	
	if (this.value.match(/[^(a-zA-Z|0-9)]/g )|| this.value.match(/[^\u0900-\u0954 ]/g) ){
		this.value = this.value.replace(/[^a-zA-Z|0-9\u0900-\u0954 ]/g, '');
	}   
});

	$('.hasAmount').keyup(function () {
	
		
		var regx	=	/^\d+(?:\.\d{0,2})?$/;
		//var regx	= /^[-+]?[0-9]*.[0-9]{2}$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			$(this).val('');	
		}   
	});
	
	$('.has3Decimal').keyup(function () {
			
			var regx	=   /^\d+(\.\d{0,3})?$/;
		
			var amount	=	$(this).val();
		
			if(!regx.test(amount))
			{
				amount = amount.substring(0, amount.length-1);
				
				$(this).val(amount);	
			}   
		});
	
	$('.has2Decimal').keyup(function () {
		
		var regx	=   /^\d+(\.\d{0,2})?$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		}   
	});
	
$('.has1Decimal').keyup(function () {
		
		var regx	=   /^\d+(\.\d{0,1})?$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		}   
	});
	
	
	$('.alphaNumeric').keyup(function() {
		
		var regx = /^[A-Za-z0-9_@./#&+-\s]*$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.decimal42').keyup(function() {
		
		var regx	=   /^\d{0,4}(\.\d{0,2})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.decimal22').keyup(function() {
		
		var regx	=   /^\d{0,2}(\.\d{0,2})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.decimal82').keyup(function() {
		
		var regx	=   /^\d{0,8}(\.\d{0,2})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.charsRemaining').hide(); 
	$('textarea').focus(function(){
	      $(this).parent().find('.charsRemaining').show();       
	})
	$('textarea').blur(function(){

      $(this).parent().find('.charsRemaining').hide();       
});
	
/* Used for Indian currency Formatter client side */
$('.currencyFormat').keyup(function() {
	var value=$(this).val().replace(/,/g,"");
	value=$(this).val().replace(/[^0-9\.]+/g, '')
	value=value.toString();
	var afterPoint = '';
	if(value.indexOf('.') > 0){
	   afterPoint = value.substring(value.indexOf('.'),value.length);
	   afterPoint = afterPoint.substring(0,3);
	}
	value = Math.floor(value);
	value=value.toString();
	var lastThree = value.substring(value.length-3);
	var otherNumbers = value.substring(0,value.length-3);
	if(otherNumbers != '')
	    lastThree = ',' + lastThree;
	var indianRupee = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
	$(this).val(indianRupee);
});	


$(".preventSpace").on("keypress", function(e) { 
    if ((e.which === 32 && !this.value.length )|| e.which == 13 || e.which == 34 || e.which == 39)
        e.preventDefault();
});

	}
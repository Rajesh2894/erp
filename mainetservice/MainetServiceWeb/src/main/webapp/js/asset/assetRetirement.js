$(document).ready(function() {
	
	$('.p_element').hide();
	$("#dispositionDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$("#nonfucDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$("#dispositionDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	var dateFields = $("#dispositionDate");
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	$('.decimal').on('input', function() {
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and
		// decimals
		// only
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than
		// once
		.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after
		// decimal
	});

	prepareDateTag();
	
	
	$("#disOrderDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$("#disOrderDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
	
	
	$("#instrumentDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	var minDates=$("#dispositionDate").val();
	$("#instrumentDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : minDates,
		maxDate : '+90d'
	});
	getdispositionMethod();
	getpaymodeDetails();
});

function saveRetire(element) {
	var errorList = [];
	$("#errorDiv").html('');
	//D#85383 1st check account live or not
	let accountLive = $('#accountLive').val();
	if(accountLive == "Y"){
		let costCenterDesc = $('#retireCostCenter').val();
		if(costCenterDesc == undefined || costCenterDesc == ''){
			errorList.push(getLocalMessage('asset.revaluation.valid.costCenter'));
		}
	}
	var dispositionDate = $('#dispositionDate').val();
	var retireDocDate = $("#retireDocDate").val();
	var dispositionMethod = $("#dispositionMethod").val();
	var nonfucDate = $("#nonfucDate").val();
	if (retireDocDate != "0" && retireDocDate != undefined
			&& retireDocDate != '') {
		errorList = validatedate(errorList, 'retireDocDate');
	}

	if (dispositionMethod == "0" || dispositionMethod == undefined
			|| dispositionMethod == '') {
		errorList.push(getLocalMessage('asset.retire.valid.dispMethod'));//"Disposition Method is required"
	}
	if (nonfucDate != "0" && nonfucDate != undefined
			&& nonfucDate != '') {
		errorList = validatedate(errorList, 'nonfucDate');
	}
	if (dispositionDate != "0" && dispositionDate != undefined
			&& dispositionDate != '') {
		errorList = validatedate(errorList, 'dispositionDate');
	}
	if (dispositionDate != "0" && dispositionDate != undefined
			&& dispositionDate != '' && nonfucDate != "0"
			&& nonfucDate != undefined && nonfucDate != '') {
		errorList = dateValidation(errorList, 'nonfucDate','dispositionDate');
	}
	errorList = validateRetire(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('asset.transfer.request.vldn.savesuccessmsg'),
				''+$("#moduleDeptUrl").val()+'', 'saveform');
		prepareDateTag();
	}
}

function validateRetire(errorList) {
	
	var dispositionMethod = $("#dispositionMethod").val();
	var dispositionDate = $('#dispositionDate').val();
	var disposedAmt = $("#amount").val();
	var acctHead = $("#retireChartOfAccount").val();
	var payModeSel = $("#payMode").val();
	var nonfucDate = $("#nonfucDate").val();
	var disOrderNumber = $("#disOrderNumber").val();
	var disOrderDate = $("#disOrderDate").val();
	var soldToName = $("#soldToName").val();
	
	if ($('#dispositionMethod option:selected').attr('code') == "SALE") {
		if(disposedAmt == undefined || disposedAmt == '') {
			errorList.push(getLocalMessage('asset.retire.valid.dispAmt'));//"Please enter Amount"
		}
		if(acctHead == undefined || acctHead == '') {
			errorList.push(getLocalMessage('asset.retire.valid.taxname'));//"Please select account head"
		}
		if(payModeSel == undefined || payModeSel == '' || payModeSel == '0') {
			errorList.push(getLocalMessage('asset.retire.valid.payMode'));//"Please select payment mode"
		}
		if(disOrderNumber == undefined || disOrderNumber == '' || disOrderNumber == '0') {
			errorList.push(getLocalMessage('asset.retire.valid.disordernum'));//"please enter receipt entry value "
		}
		if(disOrderDate == undefined || disOrderDate == '') {
			errorList.push(getLocalMessage('asset.retire.valid.disordate'));//"Please select disposition date"
		}
		if(soldToName == undefined || soldToName == '' || soldToName == '0') {
			errorList.push(getLocalMessage('asset.retire.valid.soldto'));//"please enter receipt entry value "
		}
		
		if (dispositionDate != "0" && dispositionDate != undefined
				&& dispositionDate != '' && disOrderDate != "0"
				&& disOrderDate != undefined && disOrderDate != '') {
			errorList = dateValidation(errorList, 'disOrderDate','dispositionDate');
		}
		
		 var option=$("#payMode option:selected").attr("code");
		 if( option!='' && option!=null && option!='C') { 
			 var bankName = $("#bankName").val();
			 var chequeNo = $("#chequeNo").val();
			 var instrumentDate = $("#instrumentDate").val();
			 
			 if(bankName == undefined || bankName == '' || bankName == '0') {
					errorList.push(getLocalMessage('asset.retire.valid.bankname'));
				}
			 
			 if(chequeNo == undefined || chequeNo == '' || chequeNo == '0') {
					errorList.push(getLocalMessage('asset.retire.valid.instrumentno'));
				}
			 
			 if(instrumentDate == undefined || instrumentDate == '' || instrumentDate == '0') {
					errorList.push(getLocalMessage('asset.retire.valid.instrumentdate'));
				}			 

		 }
		
	} 
		if(dispositionDate == undefined || dispositionDate == '') {
			errorList.push(getLocalMessage('asset.retire.valid.dispDate'));//"Please select disposition date"
		}
		if(nonfucDate == undefined || nonfucDate == '') {
			errorList.push(getLocalMessage('asset.retire.valid.nonfuncdate'));//"Please select non functional  date"
		}
	
	return errorList;
}

function resetRetire() {
	

	$('.warning-div').addClass('hide');
}

function backRetire() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', ''+$("#moduleDeptUrl").val()+'');
	$("#postMethodForm").submit();
}

function saveRetirementApprovalAction(approvalData)
{
	
	var errorList = [];
	
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	
	if(decision == undefined || decision == '') {
		errorList.push(getLocalMessage('asset.info.approval'));
	} else if(comments == undefined || comments =='') {
		errorList.push(getLocalMessage('asset.info.comment'));
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('work.estimate.approval.creation.success'),
				'AdminHome.html', 'appRtrAction');
	}

}
function getpaymodeDetails() {
	
	  var option=$("#payMode option:selected").attr("code");
	  if( option!= undefined && option!='' && option!=null && option!='C')
		  {
		  $('#getpaymentmodeDetails').show();
		  }
	  else
		  {
		  $('#getpaymentmodeDetails').hide();
		  }
	
}
function getdispositionMethod() {

		
		var saleType=$('#dispositionMethod option:selected').attr('code');
		if ( saleType!=undefined  &&  saleType!=''  &&saleType == "SALE") {
			
			$(".p_element").show();
		} else {
			
			$(".p_element").hide();
		}
	
}



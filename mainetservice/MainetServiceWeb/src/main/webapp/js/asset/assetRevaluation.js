$(document).ready(function() {
	
	$("#revaluateDocDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
	$("#revaluateDocDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$('#newAmount').keyup(function () {
		var oldVal = $("#revaluateCurrentValue").val();
		if(oldVal == '' || isNaN(oldVal)) {
			oldVal = 0.00;
		} else {
			oldVal = parseFloat(oldVal).toFixed(2);
		}
		var newVal = this.value;
		if(newVal == '' || isNaN(newVal)) {
			newVal = 0.00;
		} else {
			newVal = parseFloat(newVal).toFixed(2);
		}
	    showIncDec(oldVal, newVal);
	});
	$('#impCost').keyup(function () {
		if ($('#impType option:selected').attr('code') == "CPL") {
			var newVal = this.value;
			reflectImpCost(newVal);
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
	
	$("#impType").change(function(e) {
		
		if ($('#impType option:selected').attr('code') == "CPL") {
			
			$("#updUsefulLife").prop('readOnly', false);
			reflectImpCost($('#impCost').val());
		} else {
			
			$("#updUsefulLife").prop('readOnly', true);
			$('#newAmount').val($('#revaluateCurrentValue').val());
			$('#updUsefulLife').val($('#hiddenLife').val());
		}
	});
	
	if ($('#revalMode') != undefined && $('#revalMode').val() == 'IMP'  && $('#impType option:selected').attr('code') == "CPL") {
		
		$("#updUsefulLife").prop('readOnly', false);
	} else {
		
		$("#updUsefulLife").prop('readOnly', true);
	}

	
});

function reflectImpCost(newVal) {
	if(newVal == '' || isNaN(newVal)) {
		newVal = 0.00;
	} else {
		newVal = parseFloat(newVal).toFixed(2);
	}
	var oldVal = $('#revaluateCurrentValue').val();
	if(oldVal == '' || isNaN(oldVal)) {
		oldVal = 0.00;
	} else {
		oldVal = parseFloat(oldVal).toFixed(2);
	}
	$('#newAmount').val((parseFloat(oldVal) + parseFloat(newVal)).toFixed(2));
	showIncDec(oldVal,$('#newAmount').val());
}

function showIncDec(oldVal, newVal) {
	if(oldVal == '' || isNaN(oldVal)) {
		oldVal = 0.00;
	} else {
		oldVal = parseFloat(oldVal);
	}
	if(newVal == '' || isNaN(newVal)) {
		newVal = 0.00;
	} else {
		newVal = parseFloat(newVal);
	}
	if(oldVal == '' || newVal > oldVal) {
    	$("#updatedAmountIcon").removeClass('fa fa-arrow-down red');
    	$("#updatedAmountIcon").addClass('fa fa-arrow-up green');
    } else if(newVal == '' || newVal < oldVal) {
    	$("#updatedAmountIcon").removeClass('fa fa-arrow-up green');
    	$("#updatedAmountIcon").addClass('fa fa-arrow-down red');
    } else {
    	$("#updatedAmountIcon").removeClass('fa fa-arrow-down red');
    	$("#updatedAmountIcon").removeClass('fa fa-arrow-up green');
    }
}

function saveRevaluation(element) {
	var errorList = [];
	var revalAmt = $('#newAmount').val();
	//validation check for cost center and if Updated Book Value cannot be same as Current Value
	//D#83221
	let accountLive = $('#accountLive').val();
	var revaluateDocDate =$("#revaluateDocDate").val()
	if(accountLive == "Y"){
		let revaluateCostCenter = $('#revaluateCostCenter').val();
		//D#72425
		if(revaluateCostCenter == undefined || revaluateCostCenter == ''){
			errorList.push(getLocalMessage('asset.revaluation.valid.costCenter'));
		}
	}
	
	let revaluateCurrentValue = $('#revaluateCurrentValue').val();
	if(revalAmt == undefined || revalAmt == '') {
		errorList.push(getLocalMessage('asset.revaluation.valid.amount'));//'Please enter valid amount for revaluation'
	}
	
	if(revaluateCurrentValue  == undefined ||revaluateCurrentValue == ''){
		errorList.push(getLocalMessage('asset.revaluation.valid.currentValue'));
	}else if(revaluateCurrentValue == revalAmt){
		errorList.push(getLocalMessage('asset.revaluation.valid.currentValueAndUpdatedSameValue'));
	}
	if(revaluateDocDate == undefined || revaluateDocDate == ''){
		errorList.push(getLocalMessage('asset.revaluation.valid.ravaluate.date'));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('asset.revaluation.request.success'),
				''+$("#moduleDeptUrl").val()+'', 'saveform');
		prepareDateTag();
	}
}

function resetRetire() {
	

	$('.warning-div').addClass('hide');
}

function backRevaluation() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', ''+$("#moduleDeptUrl").val()+'');
	$("#postMethodForm").submit();
}

function saveRevaluationAction(approvalData)
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
				'AdminHome.html', 'appRevalAction');
	}

}
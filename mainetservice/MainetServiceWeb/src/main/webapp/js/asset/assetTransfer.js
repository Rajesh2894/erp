var transURL = "AssetTransfer.html";
$(document).ready(function() {

	$("#transferDocDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,
		onSelect : function(selected) {
			$("#transferPostDate").datepicker("option", "minDate", selected)
		}
	});
	$("#transferPostDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,
		onSelect : function(selected) {
			$("#transferDocDate").datepicker("option", "maxDate", selected)
		}
	});

	$("#transferPostDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#transferDocDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$('.p_element').hide();
	$('.radio_button:checked').each(function() {
		$('#' + $(this).attr('data-ptag')).show();
	});
	/*
	 * $(".typeOfTransfer") .change( function(e) {
	 * 
	 * if ($('.typeOfTransfer option:selected').attr( 'value') == "trans-emp") {
	 * 
	 * $("#trans-emp").show(); $("#trans-loc").hide(); $("#trans-cost").hide(); }
	 * else if ($('.typeOfTransfer option:selected') .attr('value') ==
	 * "trans-loc") {
	 * 
	 * $("#trans-emp").hide(); $("#trans-cost").hide(); $("#trans-loc").show(); }
	 * else if ($('.typeOfTransfer option:selected') .attr('value') ==
	 * "trans-cost") {
	 * 
	 * $("#trans-emp").hide(); $("#trans-loc").hide(); $("#trans-cost").show(); }
	 * 
	 * });
	 */
	$('.radio_button').change(function() {
		$("#empdesignationId ").val("");
		$('#remark').val('');
		if ($('.radio_button:checked').length == 0) {

			$('.p_element').hide();
		} else {

			$('.p_element').hide();
			$('.radio_button:checked').each(function() {

				$('#' + $(this).attr('data-ptag')).show();
			});
		}

	});
	
	$('#employeeId').change(function(){
		manipulateDropdowns();
	});
	

});

function saveTransfer(element) {
	var errorList = [];
	let assetSrNo = $('#assetSrNo').val();
	var transferDocDate = $("#transferDocDate").val();
	var transferPostDate = $("#transferPostDate").val();
	
	
	
	if(assetSrNo == '' || assetSrNo == undefined){
		errorList.push(getLocalMessage('asset.transfer.valid.assetCode'));
	}
	
	
	if (transferDocDate != "0" && transferDocDate != undefined
			&& transferDocDate != '') {

		errorList = dateValidation(errorList, 'transferDocDate',
				'transferPostDate');
		errorList = validatedate(errorList, 'transferDocDate');
	}

	var error = validateTransferType();
	if (error && error != null && error != '') {
		errorList.push(error);
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,'',
				'AdminHome.html', 'saveform');
		prepareDateTag();
	}
}

function validateTransferType() {

	var assetType = $("input[name='transDTO.transferType']:checked").val();
	// alert(assetType);
	if (assetType == undefined || assetType == '') {
		return getLocalMessage("asset.transfer.valid.transType");// Please select transfer type
	}
	var transEmp = $('#transEmpId').val();
	var transDept = $('#transDpDeptId').val();
	var transLoc = $('#transLocId').val();
	if ((assetType == "trans-emp") && transEmp == 0) {
		return getLocalMessage('asset.transfer.valid.employee');// "Please select employee to whom asset is to be transferred"
	} else if ((assetType == "trans-loc") && transLoc == '') {
		return getLocalMessage('asset.transfer.valid.location');// "Please select location to which asset is to be transferred"
	} else if ((assetType == "trans-dept") && transDept == '') {
		return getLocalMessage('asset.transfer.valid.department');// "Please select department to which asset is to be transferred"
	}
}

function resetTransfer() {

	$("#serviceStartDate").val("");
	$("#serviceExpiryDate").val("");
	$("#remark").val("");
	$().val("");
	$('.warning-div').addClass('hide');
}

function backTransfer() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', ''+$("#moduleDeptUrl").val()+'');
	$("#postMethodForm").submit();
}

function saveTransferAction(approvalData) {

	var errorList = [];

	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;

	if (decision == undefined || decision == '') {
		errorList.push(getLocalMessage('asset.info.approval'));
	} else if (comments == undefined || comments == '') {
		errorList.push(getLocalMessage('asset.info.comment'));
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('work.estimate.approval.creation.success'),
				'AdminHome.html', 'appTrfAction');
	}

}
function showempDesignation() {

	var assetType = $("input[name='transDTO.transferType']:checked").val();
	if (assetType != "trans-emp") {
		$("#empdesignationId ").val("");
		return;
	}
	var employeeId = $("#transEmpId").find("option:selected").val();
	if (employeeId != '' && employeeId != null && employeeId != undefined) {
		var requestData = {
			"employeeId" : employeeId,
		};
		var ajaxResponse = __doAjaxRequest(transURL + '?empDesignation',
				'POST', requestData, false, 'json');

		$("#empdesignationId").val(ajaxResponse);

	} else {
		$("#empdesignationId ").val("");
	}
}

function getAssetData(obj) {
	let assetId = $("#assetSrNo").find("option:selected").val();
	if (assetId != "" && assetId != 0 && assetId != undefined) {
		var requestData = {
			"assetId" : assetId
		}
		var response = __doAjaxRequest('AssetTransfer.html?getAssetData','POST', requestData, false, 'json');
		
	
		var transfer = response;
		if (transfer != null) {
			$("#empdesignationId ").val("");
			$('#remark').val('');
			$('#assetCodeSet').val(transfer.assetCode);

			if (transfer.assetDesc != null) {
				$('#assetDesc').val(transfer.assetDesc).prop('readonly', true);
			} else {
				$('#assetDesc').val("").prop('readonly', true);
			}

			if (transfer.department != null) {
				$('#dpDeptId').val(transfer.department).prop('disabled','disabled');
				$('#dpDeptId').trigger('chosen:updated');
			} else {
				$("#dpDeptId").prop('disabled', true);
				//$('#dpDeptId').chosen().addClass("chosen-select-no-results");
				$('#dpDeptId').val('').trigger('chosen:updated');
			}
			
			
			if (transfer.assetClass2 != null) {
				$('#assetClass2').val(transfer.assetClass2).prop('disabled','disabled');
				$('#assetClass2').trigger('chosen:updated');
			} else {
				$("#assetClass2").prop('disabled', true);
				//$('#dpDeptId').chosen().addClass("chosen-select-no-results");
				$('#assetClass2').val('').trigger('chosen:updated');
			}
			
			
		
			
			if (transfer.currentCostCenter != null) {
				$('#transferCostCenterId').val(transfer.currentCostCenter).prop('disabled', 'disabled');
				$('#transferCostCenterId').trigger('chosen:updated');
			} else {
				$("#transferCostCenterId").prop('disabled', true);
				//$('#transferCostCenterId').chosen().addClass("chosen-select-no-results");
				$('#transferCostCenterId').val('').trigger('chosen:updated');
			}

			if (transfer.currentEmployee != null) {
				$('#employeeId').val(transfer.currentEmployee).prop('disabled','disabled');
				$('#employeeId').trigger('chosen:updated');
			} else {
				$("#employeeId").prop('disabled', true);
				//$('#employeeId').chosen().addClass("chosen-select-no-results");
				$('#employeeId').val('').trigger('chosen:updated');
			}
			var selectOption = getLocalMessage('selectdropdown');
			$('#transEmpId').empty().append('<option selected="selected" value="0">'+ selectOption +'</option>');
			$.each(response.empList, function(key, value) {
				$('#transEmpId').append(
						$("<option></option>").attr("value", value.empId).text(value.empname));
			});
			$('#transEmpId').trigger('chosen:updated');
			if (transfer.currentLocationDesc != null) {
				$('#currentLocationDesc').val(transfer.currentLocationDesc).prop('readonly', true);
			} else {
				$('#currentLocationDesc').val("").prop('readonly',true);
			}
			manipulateDropdowns();

		}
		
		
		

	}
}

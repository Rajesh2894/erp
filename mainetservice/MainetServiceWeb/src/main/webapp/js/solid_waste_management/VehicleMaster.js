function addVehicle(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
jQuery('.hasDecimal').keyup(function() {
	this.value = this.value.replace(/[^0-9\.]/g, '');
});
function getVehiclemasterData(formUrl, actionParam, veId) {
	var data = {
		"veId" : veId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	sum1();
}

function getViewVehiclemasterData(formUrl, actionParam, veId) {
	
	var data = {
		"veId" : veId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	sum1();
}

function deleteVehiclemasterData(formUrl, actionParam, veId) {
	
	if (actionParam == "deleteVehiclemasterData") {
		showConfirmBoxForDelete(veId, actionParam);
	}
}
function showConfirmBoxForDelete(veId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + veId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(veId) {
	$.fancybox.close();
	var requestData = 'veId=' + veId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('VehicleMaster.html?'
			+ 'deleteVehiclemasterData', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function resetVehicle() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
function searchVehicle() {
	
	var data = {
		"vehicleType" : replaceZero($("#veVetype").val()),
		"vehicleRegNo" : $("#VehicleRegNo").val()
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'VehicleMaster.html?searchVehiclemasterData', data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function validateSearchForm(errorList) {
	return errorList;
}

function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

function saveVehicleMasterForm(element) {
	
	var errorList = [];
	errorList = validateForm(errorList);
	errorList = errorList.concat(validateFormDetails());
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				getLocalMessage('scheme.master.creation.success'),
				'VehicleMaster.html', 'saveform');
	}
}

function addEntryData(tableId) {
	
	var errorList = [];
	errorList = validateFormDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow(tableId, false);
	} else {
		displayErrorsOnPage(errorList);
	}
}
function validateFormDetails(errorList) {
	var errorList = [];
	
	var i = 0;
	if ($.fn.DataTable.isDataTable('#WasteDetailTable')) {
		$('#WasteDetailTable').DataTable().destroy();
	}
	$("#WasteDetailTable tbody tr")
			.each(
					function(i) {
						var wasteType = $("#wasteType" + i).val();
						var wasteCId = $("#wasteCId" + i).val();
						var rowCount = i + 1;

						if (wasteType == "0" || wasteType == null) {
							errorList
									.push(getLocalMessage("swm.validation.wastType")
											+ rowCount);
						}

						if (wasteCId == "" || wasteCId == null) {
							errorList
									.push(getLocalMessage("swm.validation.capacity.in.kg")
											+ rowCount);
						}

					});
	return errorList;

}

function validateForm(errorList) {
	
	var veVetype = $("#veVetype").val();
	if (veVetype == "0" || veVetype == null) {
		errorList
				.push(getLocalMessage('vehicle.master.validation.vehicleType'));
	}
	var vehicleRegNo = $('#veReg').val();
	if (vehicleRegNo == "" || vehicleRegNo == null) {
		errorList.push(getLocalMessage('vehicle.master.validation.vehicleRegNo'));
	}
	/*var stardWgt = $("#stardWgt").val();
	if (stardWgt == "" || stardWgt == null) {
		errorList.push(getLocalMessage('vehicle.master.validation.stdwgt'));
	}*/
	if (stardWgt < 0) {
		errorList
				.push(getLocalMessage('vehicle.master.validation.wghtvalidation'));
	}
	/*var MakeModel = $("#MakeModel").val();
	if (MakeModel == "" || MakeModel == null) {
		errorList
				.push(getLocalMessage('vehicle.master.validation.manufacture'));
	}*/	
	if ($("#DepartmentownedYes").is(":checked")) {
		/*var assetCode = $("#AssetCode").val();
		if (assetCode == "" || assetCode == null || assetCode == "0") {
			errorList.push(getLocalMessage('swm.validation.AssetId'));
		}*/

	} else if ($("#DepartmentownedNo").is(":checked")) {
		var veRentFromdate = $("#veRentFromdate").val();
		var veRentTodate = $("#veRentTodate").val();
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date($("#veRentFromdate").val().replace(pattern,
				'$3-$2-$1'));
		var sDate = new Date($("#veRentTodate").val().replace(pattern,
				'$3-$2-$1'));
		if (eDate >= sDate) {
			errorList.push("vehicle.master.validation.rentvalidation");
		}
		var vmVendorname = $("#vmVendorname").val();
		if (vmVendorname == "" || vmVendorname == null || vmVendorname == "0") {
			errorList
					.push(getLocalMessage('vehicle.master.validation.vendorName'));
		}
		if (vmContractno == "" || vmContractno == null || vmContractno == "0") {
			errorList
					.push(getLocalMessage('vehicle.master.validation.contractNo'));
		}
	} else {
		errorList.push(getLocalMessage('vehicle.master.validation.department'));
	}
	var deAreaUnit = $("#VehicleGPSID").val();
	if (deAreaUnit == "0" || deAreaUnit == null || deAreaUnit == "") {
		errorList
				.push(getLocalMessage('vehicle.master.validation.vehTracking'));
	}
	return errorList;
}

function backVehicleMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleMaster.html');
	$("#postMethodForm").submit();
}
$(document).ready(function() {
	var table = $('.vm').DataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bPaginate" : true,
		"bFilter" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});
	$("#VehicleRegNo").keypress(function(event) {
		
		var inputValue = event.which;
		if (!(inputValue != 32 && inputValue != 0)) {
			event.preventDefault();
		}		
	});

	$("#EngineNo").keypress(function(event) {
		
		var inputValue = event.which;
		// allow letters and whitespaces only.
		if (!(inputValue != 32 && inputValue != 0)) {
			event.preventDefault();
		}		
	});

	$("#chasisno").keypress(function(event) {
		
		var inputValue = event.which;
		// allow letters and whitespaces only.
		if (!(inputValue != 32 && inputValue != 0)) {
			event.preventDefault();
		}		
	});
	$('input[name="vehicleMasterDTO.veFlag"]').click(function() {
		
		if ($(this).attr("value") == "Y") {
			$(".hidebox").not(".yes").hide();
			$(".yes").show();
		}
		if ($(this).attr("value") == "N") {
			$(".hidebox").not(".no").hide();
			$(".no").show();
		}
	});
	$('input[name="vehicleMasterDTO.veFlag"]').click(function() {
		
		if ($(this).attr("value") == "Y") {
			$(".hidebox").not(".yes").hide();
			$(".yes").show();
			$('#veRentFromdate').val('');
			$('#veRentTodate').val('');
			$('#vmVendorname').val('');
			$('#veRentamt').val('');		
		}
		if ($(this).attr("value") == "N") {
			$(".hidebox").not(".no").hide();
			$(".no").show();
			$('#vePurDate').val('');
			$('#PurchasePrice').val('');
			$('#SourceofPurchase').val('');
			$('#AssetCode').val('');			
		}
	});
	$("#VehicleGPS").val($("#VehicleGPSID").val());
	sum1();
});
$("#vePurDateId").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate : '-0d',
});
$("#vePurDateId").datepicker('setDate', new Date());

$("#veRentTodateId").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate : '-0d',
});
$("#veRentTodateId").datepicker('setDate', new Date());

$("#veRentFromdateId").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate : '-0d',
});
$("#veRentFromdateId").datepicker('setDate', new Date());

function resetForm() {
	
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function replaceZero(value) {
	return value != 0 ? value : "";
}

function getContractNo() {
	
	$('#vmContractno').html('');
	var crtElementId = $("#vmVendorname").val();
	var requestUrl = "VehicleMaster.html?getContractNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');

	$('#vmContractno').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#vmContractno').append(
				$("<option></option>").attr("value", value.contId).attr(
						"fdate", value.fromDate).attr("tdate", value.toDate)
						.attr("code", value.contId).text(value.contractNo));
	});
	$('#vmContractno').trigger('chosen:updated');
	$("#veRentFromdate").val('');
	$("#veRentTodate").val('');
}

function getContrctDate() {
	
	$("#veRentFromdate").val($("#vmContractno option:selected").attr("fdate"));
	$("#veRentTodate").val($("#vmContractno option:selected").attr("tdate"));
}
$(function() {
	
	/* To add new Row into table */
	$("#WasteDetailTable").on('click', '.wasteAdd', function() {
		
		var content = $("#WasteDetailTable").find('tr:eq(1)').clone();
		$("#WasteDetailTable").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		$('.error-div').hide();
		reOrderUnitTabIdSequence('.firstWasteRow');
	});
});
function reOrderUnitTabIdSequence(firstWasteRow) {
	
	$(firstWasteRow).each(
			function(i) {
				
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "wasteType" + i);
				$(this).find("input:text:eq(1)").attr("id", "wasteCId" + i);
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr(
						"name",
						"vehicleMasterDTO.tbSwVehicleMasterdets[" + i
								+ "].wasteType");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"vehicleMasterDTO.tbSwVehicleMasterdets[" + i
								+ "].veCapacity");
			});
}
function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('firstWasteRow', obj, ids);
	$('#firstWasteRow').DataTable().destroy();
	triggerTable();
}

function sum1() {
	var i = 0;
	var gtotal = 0;
	$("#WasteDetailTable tbody tr").each(function(i) {
		var wasteCId = $("#wasteCId" + i).val();		
		gtotal = parseFloat(gtotal) + parseFloat(wasteCId);		
		if (gtotal == 'NaN') {
			gtotal = 0;
		}
	});
	
	$('#id_grand_Total').val(gtotal.toFixed(2));
}
$(document).ready(function() {
	$('#id_grand_Total').val("");
});

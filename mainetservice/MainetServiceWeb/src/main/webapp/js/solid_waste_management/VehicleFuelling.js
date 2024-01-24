var fileArray = [];

$(document).ready(function() {
	var table = $('.sm').DataTable({
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

	$(function() {
		$('#vefDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : 0
		});
	});
	$(function() {
		$('#vefDmdate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : 0
		});
	});

	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});

	sum();

});

function addVehicleFueling(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function deleteVehicleFuellingData(formUrl, actionParam, vefId) {
	if (actionParam == "deleteVehicleFueling") {
		showConfirmBoxForDelete(vefId, actionParam);
	}
}

function getPumpType() {

	var requestData = {
		'pumpId' : $("#puId").val(),
	}
	var url = 'VehicleFueling.html?fetchVehicleFuelingDetails';
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$("#pumpFuelDets").html(ajaxResponse);
	prepareTags();
}
function showConfirmBoxForDelete(vefId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + vefId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(vefId) {
	$.fancybox.close();
	var requestData = 'vefId=' + vefId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('VehicleFueling.html?'
			+ 'deleteVehicleFueling', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchVehicleFueling() {

	var data = {
		"vehicleType" : replaceZero($("#veVetype").val()),
		"pumpId" : replaceZero($("#puId").val()),
		"fromDate" : replaceZero($("#fromDate").val()),
		"todDate" : replaceZero($("#toDate").val())
	};
	var divName = '.content-page';
	var url = "VehicleFueling.html?searchVehicleFueling";
	var ajaxResponse = doAjaxLoading(url, data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchVehicleFuelling(formUrl, actionParam) {

	var data = {
		"vehicleType" : replaceZero($("#veVetype").val()),
		"pumpId" : replaceZero($("#puId").val()),
		"fromDate" : replaceZero($("#fromDate").val()),
		"todDate" : replaceZero($("#toDate").val())

	};
	var divName = '.content-page';
	var formUrl = "VehicleFueling.html?searchVehicleFueling";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getVehicleFuellingData(formUrl, actionParam, vefId) {

	var divName = '.content-page';
	var data = {
		"vefId" : vefId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getVehicleFuellingDataView(formUrl, actionParam, vefId) {

	var data = {
		"vefId" : vefId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveVehicleFuellingData(element) {
	debugger;
	var errorList = [];
	errorList = validateFormDetails(errorList);
	errorList = validateEntryDetails(errorList);
	if (errorList.length == 0) {
		var data = {
			"vehicleId" : $('#vemId').val()
		};
		var url = "VehicleFueling.html?lastMeterReading";
		var lastMeterReading = __doAjaxRequest(url, 'post', data, false, 'json')
		var curMeterReading = $('#vefReading').val();
		var mode = $('#mode').val();
		if (curMeterReading <= lastMeterReading && mode != 'E') {
			errorList
					.push("Plese enter valid meter reading. Previous reading was "
							+ lastMeterReading);
		}
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('vehicle.fuelling.add.success'),
				'VehicleFueling.html', 'saveform');
	}
}

function targetInfo() {

	var pumpId = $("#puId" + " option:selected").attr("value");
	if (pumpId != 0) {
		var data = {
			"pumpId" : pumpId
		};
		var URL = 'VehicleFueling.html?fetchVehicleFuelingDetails';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
		$("#pumpFuelDets").html(returnData);
	} else {
		$("#pumpFuelDets").html("");
	}
	prepareTags();
}

function addEntryData() {

	sum();
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateEntryDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('vehicleFuelling');
	} else {
		$('#vehicleFuelling').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function validateVehicleReading(formUrl, actionParam) {
	var reading = $("#vefReading").val();
	var vemId = $("#vemId").val();
	var data = {
		"vemId" : vemId,
		"reading" : reading
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html');
	return reading;
}

function process(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function validateFormDetails(errorList) {
	debugger;
	var vehicleType = $("#veVetype").val();
	var pumpId = $("#puId").val();
	var vefDate = $("#vefDate").val();
	var veNo = $("#vemId").val();
	var vefReading = $("#vefReading").val();
	var driverName = $("#driverName").val();
	var invoiceNumber = $("#vefDmno").val();
	var invoiceDate = $("#vefDmdate").val();
	var spc = " ";
	if (process(vefDate) > process(invoiceDate)) {
		errorList.push(getLocalMessage('vehicle.fuelling.adviceDate') + spc
				+ getLocalMessage('solid.waste.validation.greater') + spc
				+ getLocalMessage('vehicle.fuelling.fuelingDate'));
	}
	if (vehicleType == "" || vehicleType == null || vehicleType == 0) {
		errorList.push(getLocalMessage('VehicleMaintenanceMasterDTO.veVetype')
				+ spc + getLocalMessage('solid.waste.validation.empty'));
	}
	if (pumpId == "" || pumpId == null || pumpId == 'undefined') {
		errorList.push(getLocalMessage('refueling.pump.master.name') + spc
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (vefDate == "" || vefDate == null || vefDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.fuelingDate') + spc
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (veNo == "" || veNo == null || veNo == 'undefined') {
		errorList
				.push(getLocalMessage('vehicle.fuelling.vehicleRegistrationNumber')
						+ spc + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vefReading == "" || vefReading == null || vefReading == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.vehicleReading') + spc
				+ getLocalMessage('solid.waste.validation.empty'));

	}
	if (driverName == "" || driverName == null || driverName == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.driverName') + spc
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (invoiceNumber == "" || invoiceNumber == null
			|| invoiceNumber == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.adviceno') + spc
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (invoiceDate == "" || invoiceDate == null || invoiceDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.adviceDate') + spc
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	return errorList;
}

function validateEntryDetails(errorList) {

	debugger;
	var quant = 0;
	var row = 0;
	var length = 0;
	$(".appendableClass").each(
			function(i) {
				var vefdQuantity = $("#vefdQuantity" + i).val();
				var vefdCost = $("#vefdCost" + i).val();
				var rowCount = i + 1;
				length++;
				if (vefdQuantity == "" || vefdQuantity == null
						|| vefdQuantity == 'undefined' || vefdQuantity == 0) {
					errorList.push(getLocalMessage('fuelling.advice.quantity')
							+ rowCount
							+ getLocalMessage('solid.waste.validation.empty'));
					quant++;
				}
				if (vefdCost == "" || vefdCost == null
						|| vefdCost == 'undefined' || vefdCost == 0) {
					errorList.push(getLocalMessage('vehicle.fuelling.cost')
							+ rowCount
							+ getLocalMessage('solid.waste.validation.empty'));
					row++;
				}
			});
	if (quant == length || row == length || row != quant) {
		errorList.push(getLocalMessage('fuelling.advice.quantity.cost.empty'));
	}
	return errorList;
}

function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('vehicleFuelling', obj, ids);
	$('#vehicleFuelling').DataTable().destroy();
	sum();
	triggerTable();
}

function resetScheme() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleFueling.html');
	$('.error-div').hide();
}

function resetForm() {

	$("#vehicleFuelling").hide();
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function resetVehicleFuelling() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleFueling.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
function backVehicleFuellingForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleFueling.html');
	$("#postMethodForm").submit();
}

function showVehicleRegNo() {

	$('#vemId').html('');
	var crtElementId = $("#veVetype").val();
	var requestUrl = "VehicleFueling.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#vemId').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#vemId').append(
				$("<option></option>").attr("value", index).attr("code", index)
						.text(value));
	});
	$('#vemId').trigger('chosen:updated');
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function printdiv(formUrl, actionParam, vefId) {

	var data = {
		"vefId" : vefId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function PrintDiv(title) {

	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function sum() {

	var i = 0;
	var sumadd = 0;
	var multiply = 0;
	$("#vehicleFuelling tbody tr").each(function(i) {

		var vefdQuantity = $("#vefdQuantity" + i).val();
		var vefdCost = $("#vefdCost" + i).val();
		sumadd = parseFloat(sumadd) + parseFloat(vefdCost * vefdQuantity);
		multiply = parseFloat(vefdCost * vefdQuantity);
		$('#multi' + i).val(multiply);
	});
	if (isNaN(sumadd)) {
		$('#id_total').val(0);
	} else {
		$('#id_total').val(sumadd);
	}

}

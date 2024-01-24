function addVehicleMaintenance(formUrl, actionParam) {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function process(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function validateFormData(errorList) {
	var vemMetype = $("#vemMetype").val();
	var veVetype = $("#veVetype").val();
	var vemId = $("#vemId").val();
	var vemDowntime = $("#vemDowntime").val();
	var vemDowntimeunit = $("#vemDowntimeunit").val();
	var vemReceiptno = $("#vemReceiptno").val();
	var vemReceiptdate = $("#vemReceiptdate").val();
	var vemCostincurred = $("#vemCostincurred").val();
	var vemReading = $("#vemReading").val();
	var vemDate = $("#vemDate").val();
	if (process(vemReceiptdate) < process(vemDate)) {
		errorList.push(getLocalMessage('vehicle.maintenance.receiptDate')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.greater')
				+ '&nbsp;' + getLocalMessage('vehicle.maintenance.date'));
	}
	if (vemDate == "" || vemDate == null || vemDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.maintenance.date') + '&nbsp;'
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemReading == "" || vemReading == null || vemReading == 'undefined') {
		errorList.push(getLocalMessage('vehicle.maintenance.repair.reading')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemReceiptdate == "" || vemReceiptdate == null
			|| vemReceiptdate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.maintenance.receiptDate')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemCostincurred == "" || vemCostincurred == null
			|| vemCostincurred == 'undefined') {
		errorList.push(getLocalMessage('vehicle.maintenance.receiptDate')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemReceiptno == "" || vemReceiptno == null
			|| vemReceiptno == 'undefined') {
		errorList.push(getLocalMessage('vehicle.maintenance.receiptno')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemMetype == "" || vemMetype == null || vemMetype == 'undefined'
			|| vemMetype == "0") {
		errorList.push(getLocalMessage('vehicle.maintenance.type') + '&nbsp;'
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (veVetype == "" || veVetype == null || veVetype == 'undefined'
			|| veVetype == "0") {
		errorList.push(getLocalMessage('vehicle.maintenance.master.type')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemId == "" || vemId == null || vemId == 'undefined'
			|| vemId == "select" || vemId == '0') {
		errorList.push(getLocalMessage('vehicle.maintenance.regno') + '&nbsp;'
				+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemDowntime == "" || vemDowntime == null || vemDowntime == 'undefined') {
		errorList.push(getLocalMessage('vehicle.maintenance.downtime')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	if (vemDowntimeunit == "" || vemDowntimeunit == null
			|| vemDowntimeunit == 'undefined' || vemDowntimeunit == "0") {
		errorList.push(getLocalMessage('vehicle.maintenance.downtime.unit')
				+ '&nbsp;' + getLocalMessage('solid.waste.validation.empty'));
	}
	return errorList;
}

function searchVehicleMaintenance(formUrl, actionParam) {
	var data = {
		"maintenanceType" : replaceZero($("#vemMetype").val()),
		"vehType" : replaceZero($("#veVetype").val()),
		"fromDate" : replaceZero($("#fromDate").val()),
		"toDate" : replaceZero($("#toDate").val())
	};
	var divName = '.content-page';
	var formUrl = "VehicleMaintenance.html?searchVehicleMaintenance";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getVehicleMaintenance(formUrl, actionParam, vemId) {
	var divName = '.content-page';
	var data = {
		"vemId" : vemId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getVehicleMaintenanceView(formUrl, actionParam, vemId) {
	var data = {
		"vemId" : vemId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function deleteVehicleMaintenanceData(formUrl, actionParam, vemId) {
	if (actionParam == "deleteVehicleMaintenance") {
		showConfirmBoxForDelete(vemId, actionParam);
	}
}
function showConfirmBoxForDelete(vemId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + vemId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(vemId) {
	$.fancybox.close();
	var requestData = 'vemId=' + vemId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('VehicleMaintenance.html?'
			+ 'deleteVehicleMaintenance', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveVehicleMaintenance(element) {
	var errorList = [];
	errorList = validateFormData(errorList);
	if (errorList.length == 0) {
		var data = {
			"vehicleId" : $('#vemId').val()
		};
		var url = "VehicleMaintenance.html?lastMeterReading";
		var lastMeterReading = __doAjaxRequest(url, 'post', data, false, 'json')
		var curMeterReading = $('#vemReading').val();
		var mode = $('#mode').val();
		if (curMeterReading <= lastMeterReading && mode != 'E') {
			errorList.push(getLocalMessage('swm.validation.meter.reading') +" "+ lastMeterReading);
		}
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('vehicle.maintenance.add.success'),
				'VehicleMaintenance.html', 'saveform');
	}

}

function backVehicleMaintenanceForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleMaintenance.html');
	$("#postMethodForm").submit();
}

function resetScheme() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleMaintenance.html');
	$('.error-div').hide();
}

function resetForm() {

	$("#errorDiv").hide();
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function resetVehicleMaintenance() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VehicleMaintenance.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function printdiv(formUrl, actionParam, vemId) {
	
	var data = {
		"vemId" : vemId
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
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function showVehicleRegNo() {

	$('#vemId').html('');
	var crtElementId = $("#veVetype").val();
	var requestUrl = "VehicleMaintenance.html?vehicleNo";
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

});
function replaceZero(value) {
	return value != 0 ? value : undefined;
}
function printVehicleMaintenanceData(vemeId) {
	var data = {
		"vemeId" : vemeId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('VehicleMaintenance.html?'
			+ 'printVehicleMaintenance', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function getAmountFormatInDynamic(value) {

	var result = 0;
	var taxvalue = $("#taxValue").val();
	var inrecdInvamt = $("#vemCostincurred").val();
	var TaxType = $("#TaxType").val();
	var payType = $("#payTypeId option:selected").attr("code");
	if (TaxType == "D" && payType == "PER") {
		result = (parseFloat(inrecdInvamt) * parseFloat(taxvalue)) / 100;
		$("#dedAmt").val(result.toFixed(2));
	}
}
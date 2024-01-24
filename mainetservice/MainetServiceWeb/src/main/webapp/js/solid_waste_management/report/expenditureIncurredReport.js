/** ******************Generate Report Function*************** */
function expenditureIncurredReportPrint(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "report";
	}
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	} else if (errorList.length == 0) {
		var expenseType = $('#expenseType').val();
		var veVetype = $('#veVetype').val();
		if (veVetype == "-1") {
			veVetype = "0";
		}
		var veNo = $('#vemId').val();
		var fromdate = $('#veRentFromdate').val();
		var todate = $('#veRentTodate').val();
		var vemMetype = $('#vemMetype').val();
		var pumpId = $('#pumpId').val();
		var data = {
			"veVetype" : veVetype,
			"veNo" : veNo,
			"fromdate" : fromdate,
			"todate" : todate,
			"vemMetype" : vemMetype,
			"pumpId" : pumpId,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + expenseType, data,
				'html', divName);
		var obj = $(ajaxResponse).find('#flagMsg');
		if ($('.content').html(ajaxResponse)) {
			if (obj.val() == 'Y') {
				$(divName).removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
			} else {
				$(divName).removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
				errorList.push(getLocalMessage('swm.report.data.NoFound'))
				displayErrorsPage(errorList);
			}
		}
	}
}
/** *****************End Generate Report Function*********** */
function validateForm(errorList) {
	
	var expenseType = $('#expenseType').val();
	var veVetype = $('#veVetype').val();
	var veNo = $('#vemId').val();
	var fromdate = $('#veRentFromdate').val();
	var todate = $('#veRentTodate').val();
	var vemMetype = $('#vemMetype').val();
	var pumpId = $('#pumpId').val();
	if (expenseType == "0" || expenseType == "" || expenseType == null) {
		errorList.push(getLocalMessage('swm.waste.expense.type'));
		if (pumpId == "" || pumpId == null) {
			errorList.push(getLocalMessage('swm.waste.pump.name'));
		}
	}
	if (expenseType == "Fueling") {
		if (pumpId == "" || pumpId == null) {
			errorList.push(getLocalMessage('swm.waste.pump.name'));
		}
	}
	if (expenseType == "Maintenance") {
		if (vemMetype == "" || vemMetype == null) {
			errorList.push(getLocalMessage('swm.waste.maintenance.type'));
		}
	}
	if (veVetype == "0" || veVetype == "" || veVetype == null) {
		errorList.push(getLocalMessage('swm.waste.vehicle.type'));
	}
	if (veNo == "" || veNo == null) {
		errorList.push(getLocalMessage('swm.waste.vehicle.regno'));
	}
	if (fromdate == "0" || fromdate == "" || fromdate == null) {
		errorList.push(getLocalMessage('swm.fromdate.selected'));
	}
	if (todate == "0" || todate == "" || todate == null) {
		errorList.push(getLocalMessage('swm.todate.selected'));
	}
	return errorList;

}

/** ******************Start Reset Report Function*********** */
function resetForm() {
	
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
}
/** ******************End Reset Report Function*********** */

/** **********Start Back Button Report Function*********** */
function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',
			'ExpenditureIncurredOnTransportation.html');
	$("#postMethodForm").submit();
}
/** ********************************************************** */

/** **********Start Print Report Function*********** */
// Print Div
function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
/** **********End Print Report Function*********** */

/** **********Start Reset Report Function*********** */
function resetTrip() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	window.location.reload();
}
/** **********End Reset Report Function*********** */

/** ******************************************* */
function showVehicleRegNo() {
	
	$('#vemId').html('');
	var crtElementId = $("#veVetype").val();
	if (crtElementId == -1) {
		crtElementId = "";
	}
	var requestUrl = "ExpenditureIncurredOnTransportation.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	$('#vemId').append($("<option></option>").attr("value", "").attr("code", "").text("select"));
	$('#vemId').append($("<option></option>").attr("value", 0).attr("code", 0).text("All"));
	$.each(ajaxResponse, function(index, value) {
	$('#vemId').append($("<option></option>").attr("value", index).attr("code", value).text(value));
	});
	$('#vemId').trigger('chosen:updated');

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

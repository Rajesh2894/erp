/** ******************Generate Report Function*************** */
function wasteWiseSegregationPrint(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "report";
	}
	var errorList = [];
	errorList = validateReportList(errorList);
	if (errorList.length == 0) {
		var nrDisName = $('#nrDisName').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var codWast1 = $('#codWast1').val();
		var codWast2 = $('#codWast2').val();
		var codWast3 = $('#codWast3').val();
		var data = {
			"deId" : nrDisName,
			"codWast1" : codWast1,
			"codWast2" : codWast2,
			"codWast3" : codWast3,
			"fromDate" : fromDate,
			"toDate" : toDate,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data,
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
	} else {
		displayErrorsPage(errorList);
	}
}
function validateReportList(errorList) {
	
	var nrDisName = $('#nrDisName').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var codWast1 = $('#codWast1').val();
	var codWast2 = $('#codWast2').val();
	var codWast3 = $('#codWast3').val();
	if (nrDisName == "" || nrDisName == null) {
		errorList.push(getLocalMessage('swm.waste.wisesegregation.mrf.center'));
	}
	if (codWast1 == "0" || codWast1 == "" || codWast1 == null) {
		errorList.push(getLocalMessage('swm.waste.wisesegregation.waste.type'));
	}
	if (codWast2 == "0" || codWast2 == "" || codWast2 == null) {
		errorList.push(getLocalMessage('swm.waste.wisesegregation.waste.type1'));
	}
	if (codWast3 == "0" || codWast3 == "" || codWast3 == null) {
		errorList.push(getLocalMessage('swm.waste.wisesegregation.waste.type2'));
	}
	if (fromDate == "0" || fromDate == "" || fromDate == null) {
		errorList.push(getLocalMessage('swm.fromdate.selected'));
	}
	if (toDate == "0" || toDate == "" || toDate == null) {
		errorList.push(getLocalMessage('swm.todate.selected'));
	}
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var fDate = new Date($("#fromDate").val().replace(pattern, '$3-$2-$1'));
	var tDate = new Date($("#toDate").val().replace(pattern, '$3-$2-$1'));
	if (fDate > tDate) {
		errorList.push("To Date should be less than From Date");
	}
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

/** *****************End Generate Report Function*********** */

/** ******************Start Reset Report Function*********** */
function resetForm() {
	
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	window.location.reload();
}
/** ******************End Reset Report Function*********** */

/** **********Start Back Button Report Function*********** */
function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WasteWiseSegregation.html');
	$("#postMethodForm").submit();
}
/** ********************************************************** */

/** **********Start Print Report Function*********** */
// Print Div
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


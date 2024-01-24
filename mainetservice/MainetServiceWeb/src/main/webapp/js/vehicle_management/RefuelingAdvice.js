$(document).ready(function() {
	
	yearLength();
});

function yearLength() {
	var frmdateFields = $('#inrecFromdt');
	var todateFields = $('#inrecTodt');
	var inrecdInvdate = $("#inrecdInvdate");
	frmdateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
	todateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
	inrecdInvdate.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
}

function totalCost() {
	
	var i = 0;
	var sumadd = 0;
	$("#vehicleFuellingReconcilation tbody tr").each(function(i) {
		
		var vefdCost = $("#vefRmamount" + i).text();
		if ($("#puApplicable" + i).is(':checked')) {
			sumadd = parseFloat(sumadd) + parseFloat(vefdCost);
		}
		if (isNaN(sumadd)) {
			$('#id_total').val(0);
			$('#inrecdInvamt').val(0);
		} else {
			$('#id_total').val(sumadd.toFixed(2));
			$('#inrecdInvamt').val(sumadd.toFixed(2));
		}
	});
}

function addRefuelingAdvice(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getRefuelingAdvice(formUrl, actionParam, inrecId) {
	
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var data = {
		"inrecId" : inrecId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getRefuelingAdviceView(formUrl, actionParam, inrecId) {
	
	if (!actionParam) {
		actionParam = "view";
	}
	var data = {
		"inrecId" : inrecId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function deleteRefuelingAdvice(formUrl, actionParam, inrecId) {
	if (actionParam == "deleteRefuelingAdviceReconcilation") {
		showConfirmBoxForDelete(inrecId, actionParam);
	}
}

function showConfirmBoxForDelete(inrecId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger\" style=\"font-size:16px;\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-20\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + inrecId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(inrecId) {
	$.fancybox.close();
	var requestData = 'inrecId=' + inrecId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('fuellingAdvice.html?'
			+ 'deleteRefuelingAdviceReconcilation', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function isExistingInvoice() {
	var errorList = [];
	$("#errorDiv").hide();
	var inrecdInvno = $('#inrecdInvno').val();
	if (inrecdInvno != "" || inrecdInvno != null || inrecdInvno != 'undefined') {
		var requestData = {
			"inrecdInvno" : inrecdInvno,
		};
		var duplicateItemNumbers = __doAjaxRequest("fuellingAdvice.html?duplicateChecker", 'POST', requestData, false, 'json');
		if (duplicateItemNumbers == true)
			errorList.push(getLocalMessage("Invoice No. is already used, it must be unique"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
}




function saveRefuelingAdvice(element) {
	var errorList = [];
	errorList = validateFormData(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('fuelling.advice.add.success'),
				'fuellingAdvice.html', 'saveform');
	}

}

function process(date){
	
	   var parts = date.split("/");
	   return new Date(parts[2], parts[1] - 1, parts[0]);
	}

function validateFormData(errorList) {
	
	var inrecdInvno = $("#inrecdInvno").val();
	var inrecdInvdate = $("#inrecdInvdate").val();
	var inrecdInvamt = $("#inrecdInvamt").val();
	var puId = $("#puId").val();
	var fromDate = $("#inrecFromdt").val();
	var toDate = $("#inrecTodt").val();

	if (process(inrecdInvdate) < process(toDate)) {
		errorList.push(getLocalMessage('fuelling.advice.invoicedate')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.greater')
				+'&nbsp;'+ getLocalMessage('vehicle.fuelling.toDate'));
	}
	if (puId == "" || puId == null || puId == 0 || puId == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.pump.name')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (fromDate == "" || fromDate == null || fromDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.fromDate')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (toDate == "" || toDate == null || toDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.toDate')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (inrecdInvno == "" || inrecdInvno == null || inrecdInvno == 'undefined') {
		errorList.push(getLocalMessage('fuelling.advice.invoiceNo')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (inrecdInvdate == "" || inrecdInvdate == null
			|| inrecdInvdate == 'undefined') {
		errorList.push(getLocalMessage('fuelling.advice.invoicedate')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (inrecdInvamt == "" || inrecdInvamt == null
			|| inrecdInvamt == 'undefined' || inrecdInvamt == 0 ) {
		errorList.push(getLocalMessage('fuelling.advice.invoiceAmount')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	return errorList;
}

function searchRefuelingAdvice(formUrl, actionParam) {
		if (!actionParam) {
			actionParam = "search";
		}
		var data = {
			"puId" : $("#puId").val(),
			"fromDate" : replaceZero($("#inrecFromdt").val()),
			"toDate" : replaceZero($("#inrecTodt").val())
		};
		var divName = '.content-page';
		var formUrl = "fuellingAdvice.html?searchRefuelingAdviceReconcilation";
		var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();

}

function validateSearch(errorList) {
	var puId = $("#puId").val();
	var fromDate = $("#inrecFromdt").val();
	var toDate = $("#inrecTodt").val();
	if (puId == "" || puId == null || puId == 0 || puId == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.pump.name')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (fromDate == "" || fromDate == null || fromDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.fromDate')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	if (toDate == "" || toDate == null || toDate == 'undefined') {
		errorList.push(getLocalMessage('vehicle.fuelling.toDate')
				+'&nbsp;'+ getLocalMessage('solid.waste.validation.empty'));
	}
	return errorList;
}

function searchVehicleFuelingReconcilationDetails(formUrl, actionParam) {
	
	if (!actionParam) {
		actionParam = "search";
	}
	var data = {
		"puId" : $("#puId").val(),
		"fromDate" : replaceZero($("#inrecFromdt").val()),
		"toDate" : replaceZero($("#inrecTodt").val())
	};
	var errorList1 = [];
	errorList1 = validateSearch(errorList1);
	if (errorList1.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList1);
	} else {
		$("#errorDiv").hide();
		var divName = '.content-page';
		var formUrl = "fuellingAdvice.html?searchVehicleFuelingReconcilationDetails";
		var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		$('#inrecdInvamt').val(0);
	}
}

function backRefuelingAdvice() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fuellingAdvice.html');
	$("#postMethodForm").submit();
}

function resetScheme() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fuellingAdvice.html');
	$('.error-div').hide();
}

function resetForm() {
	
	addRefuelingAdvice('fuellingAdvice.html',
			'AddRefuelingAdviceReconcilation');
	$('.error-div').hide();
}

function resetRefuelingAdvice() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fuellingAdvice.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function printdiv(formUrl, actionParam, inrecId) {

	

	if (!actionParam) {
		actionParam = "formForPrint";
	}

	var data = {
		"inrecId" : inrecId
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
			.write('<link href="assets/css/style-darkblue.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-danger hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

$(document).ready(function() {
	var table = $('.vrr').DataTable({
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
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});

});
function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function ResetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'fuellingAdvice.html?AddRefuelingAdviceReconcilation', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('.error-div').hide();
	prepareTags();
}
function getAmountFormatInDynamic(value) {
	
	var result=0;
	var taxvalue = $("#taxValue").val();
	var inrecdInvamt = $("#inrecdInvamt").val();
	var TaxType = $("#TaxType").val();
	var payType = $("#payTypeId option:selected").attr("code");
	if (TaxType == "D" && payType == "PER") {
	   result = (parseFloat(inrecdInvamt) * parseFloat(taxvalue)) / 100;
		$("#dedAmt").val(result.toFixed(2));
	}
}
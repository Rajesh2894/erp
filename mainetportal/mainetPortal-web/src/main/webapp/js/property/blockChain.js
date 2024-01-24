$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
});
function initiateTransfer(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// prepareTags();
}

function back(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// prepareTags();
}

function initiateTransferCnfrm(formUrl, actionParam) {
	
	var divName = '.content-page';
	var data = {
			"oName": $("#witnessName1").val(),
			"oContactNo": $("#witnessContactNo1").val(),
			"oAddress": $("#witnessAddress1").val(),
			"oPanNo": $("#witnessPANNo1").val()
	}
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// prepareTags();
}

function printReport(formUrl, actionParam) {
	
	var divName = '.content-page';

	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// prepareTags();
}
function searchOwnershipDetails(formUrl, actionParam) {
	
	var data = {
		"propNo" : $('#propNo').val()
		/*"ownerName" : $('#ownerName').val(),
		"oldPropNo" : $('#oldpropNo').val(),*/
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	//prepareTags();
}

function addEntryData(tableId) {
	
	var errorList = [];
	// errorList = validateEntryDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow(tableId, false);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteEntry(tableId, obj, ids) {
	if (ids != "1") {
		deleteTableRow(tableId, obj, ids, false);
	}
}

function Proceed(element) {
	
	var errorList = [];
	errorList = ValidateForm(errorList);

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		 return saveOrUpdateForm(element,getLocalMessage('"Submitted SuccessFully'),
		 'BlockChain.html', 'saveform');
	}
}

function ValidateForm(errorList) {
/*	var actualTransferDate = $("#actualTransferDate").val();
	var marketValue = $("#marketValue").val();
	var salesDeedValue = $("#salesDeedValue").val();
	var stampDutyCharge = $("#stampDutyCharge").val();
	var registrationCharge = $("#registrationCharge").val();
	var otherCharges = $("#otherCharges").val();

	if (actualTransferDate == "" || actualTransferDate == null)
		errorList.push(getLocalMessage("Please Select Actual Transfer Date"));

	if (marketValue == "" || marketValue == null)
		errorList.push(getLocalMessage("Enter Market Value"));

	if (salesDeedValue == "" || salesDeedValue == null)
		errorList.push(getLocalMessage("Enter Sales Deed Value"));

	if (stampDutyCharge == "" || stampDutyCharge == null)
		errorList.push(getLocalMessage("Enter Stamp Duty Charge"));

	if (registrationCharge == "" || registrationCharge == null)
		errorList.push(getLocalMessage("Enter Registration Charge"));

	if (otherCharges == "" || otherCharges == null)
		errorList.push(getLocalMessage("Enter Other Charges"));
*/
	return errorList;
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
			.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
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

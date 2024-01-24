$(document).ready(function() {
	$('.fancybox').fancybox();

	var grandTotal = 0;
	$('#calculation tr').each(function(i) {
		var totalAmount = $("#amount" + i).text();
		if (totalAmount != 0) {
			grandTotal += isNaN(Number(totalAmount)) ? 0 : Number(totalAmount);
		}
	});

	$("#totalEstimate").text(grandTotal.toFixed(2));
});

function getContractDetail(obj) {
	
	var errorList = [];
	if ($("#contId").val() == '') {
		errorList.push(getLocalMessage('wms.SelectAgreementNumber'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var requestData = {
		"contId" : $(obj).val()
	}
	var response = __doAjaxRequest(
			'ContractAgreementPrint.html?getContractDetails', 'post',
			requestData, false, 'json');
	var value = response.split(",");
	if (value[0] != "null") {

		$('#contFromDate').val(value[0]);
	} else {
		$('#contFromDate').val("");
	}
	if (value[1] != 'null') {
		$('#contToDate').val(value[1]);
	} else {
		$('#contToDate').val("");
	}
	if (value[2] != 'null') {
		$('#contp2Name').val(value[2]);
	} else {
		$('#contp2Name').val("");
	}
	prepareTags();
}

/*
 * function formatDate(date) { var parts = date.split("-"); var formattedDate =
 * parts[2] + "/" + parts[1] + "/" + parts[0]; return formattedDate; }
 */

function printContractAgreement() {
	
	var divName = formDivName;
	var errorList = [];
	var requestData = {
		"contId" : $("#contId").val()
	}
	if ($("#contId").val() == '') {
		errorList.push(getLocalMessage('wms.SelectAgreementNumber'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var ajaxResponse = __doAjaxRequest(
			'ContractAgreementPrint.html?printContractDetails', 'post',
			requestData, false, 'html');
	var print=getLocalMessage('work.estimate.report.print');
	var close=getLocalMessage('wms.close');
	var divContents = ajaxResponse;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" type="button"><i class="fa fa-print padding-right-5" aria-hidden="true"></i>'+print+'</button>  <button onClick="window.close();" type="button" class="fa fa-times padding-right-5 hidden-print">'+close+'</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	prepareTags();
}
function printNoticeInvitingTender(){
	
	var errorList = [];
	var requestData = {
		"contId" : $("#contId").val()
	}
	if ($("#contId").val() == '') {
		errorList.push(getLocalMessage('wms.SelectAgreementNumber'));
		displayErrorsOnPage(errorList);
		return false;
	}
	
	var response = __doAjaxRequest('ContractAgreementPrint.html?printNoticeInvitingTender', 'POST',
			requestData, false, 'html');
	$('.content-page').html(response);	
}

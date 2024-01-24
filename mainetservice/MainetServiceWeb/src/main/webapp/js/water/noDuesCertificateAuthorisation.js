function saveForm(element) {
	
	var errorList = [];
	var decision = $("input[name='workflowActionDto.decision']:checked").val();
	var remark = $("#comments").val();
	if (decision == '' || decision == undefined) {
		errorList.push(getLocalMessage("water.select.atleast.oneDecision"));
	}
	if (remark == "") {
		errorList.push(getLocalMessage("water.enter.remark"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		saveOrUpdateForm(element, "Authorisation done successfully!",
				'AdminHome.html', 'saveNoDuesAuthoform');
		if (decision == 'APPROVED') {
			openNoDuesWindowTab();
		}
	}
}

function openNoDuesWindowTab() {

	var URL = 'NoDuesCertAuthoController.html?noDueCertificatePrint';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	var title = 'No Dues Certificate';
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
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}
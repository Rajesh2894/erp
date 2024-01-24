

$(document).ready(function(){

	$('#loiNo').val();
});



function resetForm() {
	
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#rtiLoiPrintingForm").validate().resetForm();
}

function saveLoiForm(obj) {
  
	var errorList = [];
    var x = $('#loiNo').val();
    data = {
        "loiNumber" : $("#loiNo").val()
    };

    
    if (x == 0) {
		errorList.push(getLocalMessage("rti.information.validation.report.Content"));
	}
    if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
    
    var divName = '.content-page';
    var formUrl = "LoiPrintingReport.html?PrintingReport";
    var returnData = __doAjaxRequest(formUrl, 'POST', data, false);

    var title = 'LOI Printing';
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
            .write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-primary hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-danger hidden-print">Close</button></div></div>')
    printWindow.document.write(returnData);
    printWindow.document.write('</body></html>');
    printWindow.document.close();

}
}

$(document).ready(function() {
	$("#noticeBillTable").dataTable(
			{
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
						[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
});

function searchNoticePrint() {
    var divName = '.content-page';
    var errorList = [];
    var vendorId = $("#vendorId").val();
    var contractId = $("#contractId").val();
    
    if (vendorId != 0 || contractId != 0) {
    	var requestData = {
    		    "vendorId" : vendorId,
    		    "contractId" : contractId
    		};
    	var ajaxResponse = doAjaxLoading('DemandNotice.html?searchNoticePrint', requestData,'html', divName);
    	$(divName).removeClass('ajaxloader');
    	$(divName).html(ajaxResponse);
    	prepareTags();
	} else {
		errorList.push(getLocalMessage("rnl.select.any.field"));
		displayErrorsOnPage(errorList);
	}
}

function printNotice(contractId,lang) {
	
	var divName = '.content-page';
	var URL = 'DemandNotice.html?printNotice';
	var requestData = {
			"contractId" : contractId,
			"lang":lang
	};
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false,'html');
	var title = 'Notice';
	let printWindow = window.open('', '_blank');
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
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	window.location.href = "DemandNotice.html";
}


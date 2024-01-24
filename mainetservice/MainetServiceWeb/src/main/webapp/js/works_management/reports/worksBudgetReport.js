
/*$(document).ready(function() {	
	$('.fancybox').fancybox();

	var grandTotal = 0;
	$('#calculation tr').each(function(i) {		
		var totalAmount = $("#budgetBalanceAmt" + i).text();
		if (totalAmount != 0) {
			grandTotal -= isNaN(Number(totalAmount)) ? 0 : Number(totalAmount);
		}
	});
	
	//$("#totalEstimate").text(grandTotal.toFixed(2));

});
*/

$(document).ready(function() {	
	
	$('.fancybox').fancybox();
	$('#calculation tr').each(function (i) {
	
	 var sum = 0;
	 var budgetBalancePerc = $("#budgetBalancePerc" + i).text();
	 var budgetBalanceAmt = ($("#budgetBalanceAmt" + i).text());
	 var contAmount = ($("#contAmount" + i).text());
		 sum = (((budgetBalanceAmt / contAmount) * (100)));
	
	 if(!isNaN(sum)){
		 $("#budgetBalancePerc" + i).text(sum.toFixed(2)+"%");
}
	});
	
	$("#calculation").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	
	var grandTotal = 0;
	$('#calculation tr').each(function(i) {		
		var totalAmount = $("#budgetBalanceAmt" + i).text();
		if (totalAmount != 0) {
			grandTotal -= isNaN(Number(totalAmount)) ? 0 : Number(totalAmount);
		}
	});
	//$("#totalEstimate").text(grandTotal.toFixed(2));
	
	
});

function viewBudgetReport() {
	
	var errorList = [];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	
	if(fromDate != "" && toDate != null){
		if(compareDate(fromDate) > compareDate(toDate)){
			errorList.push(getLocalMessage("wms.ToDateShouldbeEqulGreaterthanFromDate"));
		}		
	}	
	if (fromDate == ""  || fromDate == undefined) {
		errorList.push(getLocalMessage("sor.select.fromdate"));
	}
	if (toDate == ""  || toDate == undefined) {
		errorList.push(getLocalMessage("sor.select.todate"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = '&fromDate=' + fromDate+'&toDate=' +toDate;

		var ajaxResponse = doAjaxLoading(
				'BudgetWorksReport.html?viewBudgetReport', requestData,
				'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

/*function formatDate(date) {
	
	var parts = date.split("/");
	var formattedDate = parts[0] + "-" + parts[1] + "-" + parts[2];
	return formattedDate;
}*/

/*function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
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
*/
function printdiv(printpage){
	//Defect #92937
	if ($.fn.DataTable.isDataTable('#calculation')) {
		$('#calculation').DataTable().destroy();
	}
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	$('#calculation').DataTable();
	return false;
}

function backReportForm() {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'BudgetWorksReport.html');
		$("#postMethodForm").submit();
}

function resetBudgetForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'BudgetWorksReport.html');
	$("#postMethodForm").submit();
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}
$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
	});
	$(".datepicker").datepicker('setDate', new Date());
});

$(function() {
	$("#frmdate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	});

$(function() {
	$("#todate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	});


function viewDeductionReport(element) {
	//debugger;
	var errorList = [];
	var frmDate = $("#frmdate").val();
	var todate= $("#todate").val();
	var tdsTypeId= $("#tdsTypeId").val();
	
	  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	  var eDate = new Date($("#frmdate").val().replace(pattern,'$3-$2-$1'));
	  var sDate = new Date($("#todate").val().replace(pattern,'$3-$2-$1'));
	  if (eDate > sDate) {
		  errorList.push("To Date can not be less than From Date");
	    }

	if (frmDate == "") {
		errorList.push(getLocalMessage("account.select.fromDate"));
	}
	if (tdsTypeId == "") {
		errorList.push(getLocalMessage("account.select.tdsType"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = '&frmDate=' + frmDate +'&todate=' + todate + '&tdsTypeId=' + tdsTypeId;

		var ajaxResponse = doAjaxLoading(
				'DeductionRegister.html?getAccountDeductionRegisterData', requestData,
				'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

//Print Div
function PrintDiv(title) {
	var printBtn= getLocalMessage("account.budgetaryrevision.print");
	var closeBtn= getLocalMessage("account.close");
	var downloadBtn= getLocalMessage("acounts.download");
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");$(".table .tfoot").addClass("hide");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i>'+' '+printBtn+'</button> <button id="btnExport1" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i>'+' '+downloadBtn+'</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">'+closeBtn+'</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
} 

function backAbstractForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DeductionRegister.html');
	$("#postMethodForm").submit();
}

$(document).ready(function() {
	$('#importexcel tr th[data-sorter="false"]').css('background-image','none');
 });




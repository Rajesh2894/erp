$(document).ready(function() {
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
	});
function UserCollectionReportPrint(formUrl) {
	
	var errorList = [];
	errorList = validateReportList(errorList);
	var monthNo = $('#monthNo option:selected').attr('code');	
	var monthdesc = $('#monthNo option:selected').text();
	if (errorList.length == 0) {		
		var data = {
			"monthNo" :monthNo,	
			"monthName" :monthdesc
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + 'getUserCollectionChargeLogReport', data,'html', divName);
		var obj = $(ajaxResponse).find('#statusFlag');
		
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
	}else{
		displayErrorsPage(errorList);
	}
}
function validateReportList(errorList) {
	
	var monthNo = $('#monthNo option:selected').attr('code');	
	if (monthNo == "0" || monthNo == "" || monthNo == null) {
		errorList.push(getLocalMessage('swm.month.select'));
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

function resetArea() {
	
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}
function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'UserChargeCollectionReport.html');
	$("#postMethodForm").submit();
}


//Print Div
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
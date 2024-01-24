function loanreport(formUrl, actionParam) {
	   var errorList = [];
	   errorList = validateForm(errorList);
	/*   var faYearId = $('#faYearId option:selected').text();*/
		var faYearId = $('#faYearId').val();
	   var loanCode = $('#loanCode').val();
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		} else if (errorList.length == 0) {
	      var data = {
				"faYearId" : faYearId,
				"loanCode":loanCode,
			};
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
}
function validateForm(errorList) {
	var loanCode = $('#loanCode').val();
	if (loanCode == "" || loanCode == null) {
		errorList.push(getLocalMessage('account.select.loanCode'));
	}
	return errorList;
}

function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
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

/** **********Start Back Button Report Function*********** */
function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'loanreport.html');
	$("#postMethodForm").submit();
}
/** ********************************************************** */

function loanReportReset() {
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}

//=========================================================================================================================================================================



function PrintDiv(title) {
	var divContents = document.getElementById("reportTable").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, .tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style"); $(".table .tfoot").addClass("hide");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}	
	





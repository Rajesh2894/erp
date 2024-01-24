/********************Add Function************/
function add(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************SearchFunction************/
function search(formUrl, actionParam) {
	var data = {
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************EditFunction************/
function getEdit(formUrl, actionParam, popId) {
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************ViewFunction************/
function getView(formUrl, actionParam, popId) {
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************DeleteFunction************/
function deleteEmployee(formUrl, actionParam, popId) {
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************BackButton Function************/
function backButton() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VendorContractAgreement.html');
	$("#postMethodForm").submit();
}

function resetScheme() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'VendorContractAgreement.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
/********************DataTable  Function************/
$(document).ready(function(){
	var table = $('.VndIns').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true
	    });
		
});	

/*****************Date Picker Function****************/
$(document).ready(function() {
		$("#ContractDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
		$("#TenderDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
		
		$("#ResulationDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
		
		$("#ContractFromDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
		
		$("#ContractToDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
		$("#SecurityDepositDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
	});

/*****************End Date Picker Function****************/

/*****************Start Save Function With Validation****************/
function saveVendorVerificationForm(element){
	
	var errorList = [];
	errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else if (errorList.length == 0) {
	      return saveOrUpdateForm(element,getLocalMessage('swm.vendor.inspection.submit.success'), 'VendorInspection.html', 'saveform');
    }

}
function validateForm(errorList) {

var RemarksId = $("#Remarks").val();
if (RemarksId == "" || RemarksId == null) {
	errorList.push(getLocalMessage('swm.enter.remark'));
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
/*****************End Save Function With Validation****************/


/***************************Print function**********************/
function PrintDiv(title) {
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
/***************************End Print function**********************/
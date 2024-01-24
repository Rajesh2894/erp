$(document).ready(function() {
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date
			.getDate());
	$("#rmDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : today,
		changeYear : true
	});
	$("#rmDate").datepicker('setDate', new Date());

	$("#rmDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#receiptDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});


function searchForm(obj, formUrl, actionParam) {

	var errorList = [];
    errorList = validateReceiptSearchForm();
    var divName = '.content-page';
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	
	if (errorList.length == 0) {
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function ResetForm(){
	window.location.href = 'ReceiptForm.html';	
}

function validateReceiptSearchForm() {
	var errorList = [];
	var receiptNo = $("#rmRcptno").val();
	var receiptDate = $("#rmDate").val();
	var receiptAmount = $("#rmAmount").val();
	var payeeName = $("#rmReceivedfrom").val();
	var loiNo = $("#loiNo").val();
	var deptId = $("#deptId").val();
	var appNo = $("#appNo").val();
	var refNo = $("#refNo").val();
	 
	if (receiptNo == "" && receiptDate == "" && receiptAmount == ""
			&& payeeName == "" && loiNo == "" && loiNo == ""  && deptId == "" && refNo == "" && appNo == "") {
		errorList.push(getLocalMessage('receipt.search_data'));
	}
	return errorList;
}
function viewReceipt(rmRcptid,mode) {	
	var url = "ReceiptForm.html?viewReceipt";
	var requestdata = {
			"rmRcptid" : rmRcptid			
		};
	var returnData = __doAjaxRequest(url, 'post', requestdata,	false);	
	var divName = '.content';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}
function printReceipt(rmRcptid,mode) {
	var URL = 'ReceiptForm.html?printReceipt';
	var requestdata = {
		"rmRcptid" : rmRcptid			
	};
	var returnData = __doAjaxRequest(URL, 'POST', requestdata, false);

	var title = 'Receipt Print';
	var printWindow = window.open('', '_blank');
	window.location.href='AdminHome.html';
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}

function back() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ReceiptForm.html');
	$("#postMethodForm").submit();
}
$(document).ready(function() {
	
	$("#datatables").dataTable(
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
	
	$('#fromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect: function(selected) {
			$("#toDate").datepicker("option","minDate", selected);
	      }
	});
	$('#toDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect: function(selected) {
			$("#fromDate").datepicker("option", "maxDate",selected);
	      } 
	});
	
	searchLicenseSubCatagory()
})

function searchLicenseSubCatagory() {
	var requestData = {
		"triCod1" : $("#triCodeList1").val()
	};
	$('#triCodeList2').html('');
	$('#triCodeList2').append(
			$("<option></option>").attr("value", "0").text("All"));

	var ajaxResponse = doAjaxLoading(
			'RenewalReminderNotice.html?getLicenseSubCatagory', requestData,
			'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#triCodeList2').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#triCodeList2').trigger("chosen:updated");

}
function searchData() {
	var divName = '.content-page';
	var errorList = [];	
	var triCod1 = $("#triCodeList1").val();
	var triCod2 = $("#triCodeList2").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();	var triCod1 = $("#triCodeList1").val(),
	errorList = validateForm(errorList);
	if (errorList.length == 0) {
		requestData = {
				"triCod1" : triCod1,
				"triCod2"  : triCod2, 
				"fromDate" : fromDate, 
				"toDate" :  toDate
			};		
	var ajaxResponse = doAjaxLoading('RenewalReminderNotice.html?searchDetails', requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$("#triCodeList2").val($("#subCatId").val()).trigger("chosen:updated");
	}
 	 else {
		displayErrorsOnPage(errorList);
	}
}

function validateForm(errorList) {
	
	var triCod1 = $("#triCodeList1").val();
	var triCod2 = $("#triCodeList2").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	
	if(triCod1 == '' || triCod1 == undefined) {
		errorList.push(getLocalMessage("trade.valid.lic.cat"));
	}

	if(triCod2 == '' || triCod2 == undefined) {
		errorList.push(getLocalMessage("trade.valid.lic.subCat"));
	}
	

	if(fromDate == '' || fromDate == undefined) {
		errorList.push(getLocalMessage("trade.valid.fromDate"));
	}
	
	if(toDate == '' || toDate == undefined) {
		errorList.push(getLocalMessage("trade.valid.toDate"));
	}

return errorList;
}
	

function printRenewalreminderNotice(element,trdLicno) {
    var errorList = [];
    if (errorList.length == 0) {
	var URL = 'RenewalReminderNotice.html?printRenewalreminderNotice';	
	var requestData = {
			"trdLicno" : trdLicno
	};
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
		'html');
	showBoxForApproval();
	var title = 'Renewal Reminder Notice';
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
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
    } else {
	displayErrorsOnPage(errorList);
    }

}	


function showBoxForApproval() {
    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    succesMessage = getLocalMessage("trade.renewal.success.msg");
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

    message += '<div class=\'text-center padding-bottom-10\'>'
	    + '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
	    + Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
	    + '</div>';

    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
    $(childDivName).html(message);
    $(childDivName).show();
    $('#Proceed').focus();
    showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'RenewalReminderNotice.html';
    $.fancybox.close();
}
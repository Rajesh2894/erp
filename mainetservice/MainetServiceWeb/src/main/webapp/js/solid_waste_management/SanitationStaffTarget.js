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
	$("#id_sanitationStaffTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});
	yearLength();
});

function validateZero() {
	
	var errorList = [];
	if (this.selectionStart == 0 && (e.which == 48 || e.which == 46)) {
		errorList.push(getLocalMessage("swm.validation.garbagevol"));
		return false;
	}
}

function yearLength() {
	
	var frmdateFields = $('#sanTgfromdt');
	var todateFields = $('#sanTgtodt');
	frmdateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
	todateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
}

function openaddtarget(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	yearLength();
	prepareTags();
}

function Proceed(element) {
	
	var errorList = [];
	errorList = validateSanitationStaffTarget(errorList);
	errorList = errorList.concat(validateEntryDetails1(errorList));
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		saveOrUpdateForm(element,getLocalMessage('swm.saveSanitationStaffTarget'),'VehicleTarget.html', 'saveform');
		yearLength();
	}
}

function validateSanitationStaffTarget(errorList) {
	
	var targetfrmDt = $("#sanTgfromdt").val();
	var targettoDt = $("#sanTgtodt").val();
	if (targetfrmDt == "" || targetfrmDt == null)
		errorList.push(getLocalMessage("swm.validation.targetfrmDt"));
	if (targettoDt == "" || targettoDt == null)
		errorList.push(getLocalMessage("swm.validation.targettoDt"));
	
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;	
	var dateFrom = new Date(targetfrmDt.replace(pattern, '$3-$2-$1'));
	var dateTo = new Date(targettoDt.replace(pattern, '$3-$2-$1'));
	
	if (dateFrom > dateTo) {
		errorList.push(getLocalMessage('swm.fromDate')
				+'&nbsp;'+getLocalMessage("solid.waste.validation.greater")
				+'&nbsp;'+ getLocalMessage('swm.toDate'));
	}
	return errorList;
}
function addEntryData(tableId) {
	
	var errorList = [];
	if (tableId == "id_segregationformTable3") {
		errorList = validateEntryDetails1(errorList);
	}
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}
}
function deleteEntry(tableId, obj, ids) {
	if (ids != "1") {
		deleteTableRow(tableId, obj, ids);
	}
}
function modifySanitationStaffTarget(segId, formUrl, actionParam, mode) {
	
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : segId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	yearLength();
	prepareTags();
};

function searchtarget(formUrl, actionParam) {
	errorList = [];
	
	errorList = validateSanitationStaffTarget(errorList);
	if(errorList.length > 0){
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	else{
		var data = {
				"fromdate" : $('#sanTgfromdt').val(),
				"todate" : $('#sanTgtodt').val(),
			}
			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			yearLength();
			
	}
	prepareTags();
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}

function validateEntryDetails1(errorList) {
	var errorList = [];
	var veh = [];
	var route = [];
	
	var i = 0;
	if ($.fn.DataTable.isDataTable('#id_segregationformTable3')) {
		$('#id_segregationformTable3').DataTable().destroy();
	}
	$("#id_segregationformTable3 tbody tr")
			.each(function(i) {
						
						var veId = $("#empid" + i).val();
						var roId = $("#roId" + i).val();
						var sandVolume = $("#sandVolume" + i).val();
						veh.push(veId);
						route.push(roId);
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (veId == "" || veId == null) {
							errorList.push(getLocalMessage("swm.validation.veId")+ rowCount);
						}
						if (roId == "" || roId == null) {
							errorList.push(getLocalMessage("swm.validation.roId")+ rowCount);
						}

						if (sandVolume < 0.1 || sandVolume == ""|| sandVolume == null) {
							errorList.push(getLocalMessage("swm.validation.sandVolume")+ rowCount);
						}
					});
	var i = 0;
	var j = 1;
	var k = 1;
	var count = 0;
	for (i = 0; i <= veh.length; i++) {
		for (j = k; j <= veh.length; j++) {
			if (veh[i] == veh[j]) {
				if (route[i] == route[j]) {
					if (count == 0) {
						errorList.push(getLocalMessage("swm.target.validation.route")+ (j + 1));
					}
					count++;
				}

			}
		}
		k++;
	}
	return errorList;
}

function ResetForm(resetBtn) {
	
	resetForm(resetBtn);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('VehicleTarget.html?AddSanitationStaffTarget', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}
function showConfirmBoxForDelete(sanId) {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ getLocalMessage('swm.cnfrm.delete') + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + sanId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(sanId) {
	
	$.fancybox.close();
	var divName = '.content-page';
	var requestData = {
		"mode" : "D",
		"id" : sanId
	};
	var ajaxResponse = doAjaxLoading('VehicleTarget.html?DeleteSanitationStaffTarget',requestData, 'html', divName);
	if (ajaxResponse == 'false') {
		showErrormsgboxTitle(getLocalMessage('swm.error.occur'))
	}
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
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
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
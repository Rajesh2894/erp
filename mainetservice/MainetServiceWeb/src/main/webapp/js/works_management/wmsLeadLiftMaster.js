var removeIdArray = [];

$(document).ready(function() {
	$('.add').hide();
	$('#lelitbl').DataTable();
	$('#errorTableLeLiType').dataTable({
		"pageLength" : 5
	});
	if ($("#errorLengh").val() == 0) {
		$("#errorTable").hide();
	} else {
		if ($.fn.DataTable.isDataTable('#lelitbl')) {
			$('#lelitbl').DataTable().destroy();
		}
		$("#lelitbl").hide();
		$("#errorTable").show();
	}

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	var dateFields = $('.datepicker');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	if ($("#activeChkBox").val() == 'Y') {
		$("#leLiSlabFlg").prop("checked", true);
		$("#from input").attr('readonly', false);
	} else {
		$("#leLiSlabFlg").prop("checked", false);
		$("#from input").attr('readonly', true);
	}

});
$('.decimal').keyup('input', function() {
	this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals only
	.replace(/(^[\d]{5})[\d]/g, '$1') // not more than 5 digits at the
	// beginning
	.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
	.replace(/(\.[\d]{2})./g, '$1'); // not more than 2 digits after decimal
});

$(function() {

	$("#leadLiftgrid").jqGrid(
			{
				url : 'WmsLeadLiftMaster.html' + "?gridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '', getLocalMessage('sor.name'),
						getLocalMessage('material.master.ratetype'),
						getLocalMessage('leadlift.master.fromDate'),
						getLocalMessage('leadlift.master.toDate'),
						getLocalMessage('works.management.action') ],
				colModel : [ {
					name : "sorId",
					hidden : true
				}, {
					name : "sorName",
					align : 'center',
					sortable : true,
				}, {
					name : "leLiFlag",
					align : 'center',
					sortable : true,
				}, {
					name : "sorFromDate",
					align : 'center',
					sortable : true,
					formatter : dateTemplate,
				}, {
					name : "sorToDate",
					align : 'center',
					sortable : true,
					formatter : dateTemplate,
				}, {
					name : 'enbll',
					index : 'enbll',
					align : 'center !important',
					formatter : actionFormatter,
					search : false
				} ],
				pager : "#pagered",
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "code",
				sortorder : "desc",
				height : 'auto',
				viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,
				},
				autoencode : true,
				caption : 'Lead-Lift Master'
			});

	$("#leadLiftgrid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		search : true,
		refresh : false,
		closeAfterSearch : true
	});

	function actionFormatter(cellvalue, options, rowObject) {

		if (rowObject.sorToDate != "" && rowObject.sorToDate != null) {
			return "<a class='btn btn-blue-2 btn-sm' title='View' onclick=\"showGridOption('"
					+ rowObject.sorId
					+ "','"
					+ rowObject.leLiFlag
					+ "','V')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "
		} else {
			return "<a class='btn btn-blue-2 btn-sm' title='View' onclick=\"showGridOption('"
					+ rowObject.sorId
					+ "','"
					+ rowObject.leLiFlag
					+ "','V')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "
					+ "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"showGridOption('"
					+ rowObject.sorId
					+ "','"
					+ rowObject.leLiFlag
					+ "','E')\"><i class='fa fa-pencil-square-o' aria-hidden='true'></i></a> "
					+ "<a class='btn btn-danger btn-sm' title='Delete' onclick=\"showGridOption('"
					+ rowObject.sorId
					+ "','"
					+ rowObject.leLiFlag
					+ "','D')\"><i class='fa fa-trash' aria-hidden='true'></i></a> ";
		}
	}

});

function searchLeadLiftEntry() {
	var errorList = [];
	var sorId = $("#sorName").val();
	var leLiFlag = $("#leLi").val();

	if (sorId == "" || sorId == null) {
		errorList.push(getLocalMessage("leadlift.master.select.sorName"));
	}
	if (leLiFlag == "" || leLiFlag == null) {
		errorList.push(getLocalMessage("work.estimate.select.rate.type"));
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {

		var requestData = '&sorId=' + sorId + '&leLiFlag=' + leLiFlag;

		var ajaxResponse = doAjaxLoading('WmsLeadLiftMaster.html?searchData',
				requestData, 'html');
		if (ajaxResponse == '"N"') {
			$('.add').show();

			errorList
					.push(getLocalMessage("work.management.vldn.grid.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
			$(".add").show();

		} else {
			$("#errorDiv").hide();
		}
		$("#grid").show();
		$("#leadLiftgrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}

}

function showGridOption(sorId, leLiFlag, action) {

	var actionData;
	var divName = formDivName;
	var requestData = '&sorId=' + sorId + '&leLiFlag=' + leLiFlag + '&mode='
			+ action;
	if (action == "E" || action == "V") {
		actionData = 'editLeadLiftMasterData';
		var ajaxResponse = doAjaxLoading(
				'WmsLeadLiftMaster.html?' + actionData, requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	if (action == "D") {
		actionData = 'deleteLeadLiftMaster';
		showConfirmBoxForDelete(sorId, leLiFlag, actionData);
	}
	if (action == "U") {
		actionData = 'editLeadLiftMasterData';
		var leLi = $("#leLi").val();
		var sor = $("#sorName").val();
		requestData = '&sorId=' + sor + '&leLiFlag=' + leLi + '&mode=' + action;
		;
		var ajaxResponse = doAjaxLoading(
				'WmsLeadLiftMaster.html?' + actionData, requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
}

function getsorDatesBySorNames() {

	if ($("#sorName").val() == 0) {
		return false;
	}
	var sorId = $('#sorName').val();
	var requestData = {
		"sorId" : sorId
	};
	var result = __doAjaxRequest("WmsLeadLiftMaster.html?getSorDates", 'post',
			requestData, false, 'json');
	var dates = result.split(",");
	if (dates[0] != 'null') {
		$('#sorFromDate').val(formatDate(dates[0]));
	} else {
		$('#sorFromDate').val("");
	}
	if (dates[1] != 'null') {
		$('#sorToDate').val(formatDate(dates[1]));
	} else {
		$('#sorToDate').val("");
	}
}

function formatDate(date) {
	var parts = date.split("-");
	var formattedDate = parts[2] + "/" + parts[1] + "/" + parts[0];
	return formattedDate;
}

function saveData(obj) {
	var errorList = [];
	if ($.fn.DataTable.isDataTable('#lelitbl')) {
		$('#lelitbl').DataTable().destroy();
	}

	errorList = validateEntryDetails();

	if (errorList.length == 0) {
		var url = "WmsLeadLiftMaster.html";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
		$('#lelitbl').DataTable();
	}

}

function openLeadLiftAddForm(obj) {
	var divName = formDivName;
	var errorList = [];
	var leLiFlag = $("#leLi").val();
	var sorId = $("#sorName").val();

	if (leLiFlag == "" || leLiFlag == null) {
		errorList.push(getLocalMessage("work.estimate.select.rate.type"));
	}
	if (sorId == "" || sorId == null) {
		errorList.push(getLocalMessage("leadlift.master.select.sorName"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {

		var url = "WmsLeadLiftMaster.html?addLeadLiftMaster";
		data = {
			"leLiFlag" : leLiFlag,
			"sorId" : sorId
		};
		var response = __doAjaxRequest(url, 'post', data, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(response);

	}
}

function addEntryData() {

	var errorList = [];
	errorList = validateEntryDetails();
	if (errorList.length == 0) {
		addTableRow('lelitbl');
	} else {
		$('#lelitbl').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function validateEntryDetails() {
	var errorList = [];
	var j = 0;
	if ($.fn.DataTable.isDataTable('#lelitbl')) {
		$('#lelitbl').DataTable().destroy();
	}
	$("#lelitbl tbody tr")
			.each(
					function(i) {
						var from = $("#leLiFrom" + i).val();
						var to = $("#leLiTo" + i).val();
						var rate = $("#leLiRate" + i).val();
						var unit = $("#leLiUnit" + i).val();
						var isSlab = $("#leLiSlabFlg").is(':checked');
						var rowCount = i + 1;

						if (isSlab) {
							if (from == "" || from == null) {
								errorList
										.push(getLocalMessage("leadlift.master.select.from")
												+ rowCount);
							}
						} else {
							if (from != "") {
								errorList
										.push(getLocalMessage("leadlift.master.flat.rate.not.defined ")
												+ rowCount);
							}
						}
						if (to == "" || to == null) {
							errorList
									.push(getLocalMessage("leadlift.master.select.to")
											+ rowCount);
						}
						if (unit == "" || unit == null) {
							errorList
									.push(getLocalMessage("leadlift.master.select.unit")
											+ rowCount);
						}
						if (rate == "" || rate == null) {
							errorList
									.push(getLocalMessage("leadlift.master.select.charges")
											+ rowCount);
						}

						if (isSlab) {
							if (parseFloat(from) > parseFloat(to)) {

								errorList
										.push(getLocalMessage("leadlift.master.enter.fromValue")
												+ rowCount);

							}
							if (parseFloat($("#leLiFrom" + i).val()) <= parseFloat($(
									"#leLiTo" + (i - 1)).val())) {
								errorList
										.push(getLocalMessage("leadlift.master.previousvalue")
												+ rowCount);

							}

						} else {
							if (parseFloat($("#leLiTo" + i).val()) <= parseFloat($(
									"#leLiTo" + (i - 1)).val())) {
								errorList
										.push(getLocalMessage("wms.PresentTo-ValueCanNotBeSmallerThanPreviousTo-Value")
												+ rowCount);
							}
						}

					});

	return errorList;
}

function closeErrBox() {
	$(".warning-div").hide();
}
function displayErrorsOnPage(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$("#errorDiv").show();
	errorList = [];
	return false;
}

function inputPreventSpace(key, obj) {
	if (key == 32 && obj.value.charAt(0) == ' ') {
		$(obj).val('');
	}
}

function showConfirmBoxForDelete(sorId, leLiFlag, actionData) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-20\">'
			+ getLocalMessage("material.master.vldn.delete") + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + sorId + ',' + "'" + leLiFlag
			+ "'" + ')"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);

	return false;
}

function proceedForDelete(sorId, leLiFlag) {
	$.fancybox.close();
	var requestData = 'sorId=' + sorId + '&leLiFlag=' + leLiFlag;
	var response = __doAjaxRequest('WmsLeadLiftMaster.html?'
			+ 'deleteLeadLiftMaster', 'POST', requestData, false, 'json');
	if (response) {
		$("#leadLiftgrid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}
}

function uploadExcelFile() {

	var errorLis = [];
	var fileName = $("#excelFileName").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		displayErrorsOnPage(errorLis);
		return false;
	}
	$("#filePath").val(fileName);
	var requestData = $.param($('#WmsLeadLiftMaster').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WmsLeadLiftMaster.html?'
			+ "loadExcelData", requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	if ($("#errorLengh").val() == 0) {
		$("#lelitbl").Show();
		$("#errorTable").hide();
	} else {
		$("#lelitbl").hide();
		$("#errorTable").show();
	}
}

$('#leLiSlabFlg').change(function() {
	if ($('input:checkbox[id=leLiSlabFlg]').is(':checked')) {

		$('#leLiSlabFlg').attr('checked', true);
		$('#leLiSlabFlg').val(true);
		$("#from input").attr('readonly', false);
	} else {
		$('#leLiSlabFlg').attr('checked', false);
		$('#leLiSlabFlg').val(false);
		$("#from input").attr('readonly', true);

	}

});

function exportExcelData() {
	window.location.href = 'WmsLeadLiftMaster.html?exportExcelData';
}

function viewReport() {

	if ($.fn.DataTable.isDataTable('#lelitbl')) {
		$('#lelitbl').DataTable().destroy();
	}
	var theForm = "#WmsLeadLiftMaster";
	var requestData = __serializeForm(theForm);
	var url = "WmsLeadLiftMaster.html?leadliftReport";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');

	var divContents = ajaxResponse;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
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
	$('#lelitbl').DataTable();
}
function resetTable(obj) {
	var divName = formDivName;
	var leLiFlag = $("#leLi").val();
	var sorId = $("#sorId").val();
	var url = "WmsLeadLiftMaster.html?addLeadLiftMaster";
	data = {
		"leLiFlag" : leLiFlag,
		"sorId" : sorId
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function backLeLiMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WmsLeadLiftMaster.html');
	$("#postMethodForm").submit();
}

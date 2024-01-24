$(document).ready(function() {
	$("#datatables").dataTable({
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
		changeYear : true,
		maxDate : 0,
	});
	var dateFields = $('.datepicker');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
	var dateField = $('.dates');
	dateField.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

});

function searchMbDetails() {

	var errorList = [];
	var count = 0;
	var workOrderId = $('#workId').val();
	if (workOrderId == "") {
		errorList.push(getLocalMessage('mb.search.field'));
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var requestData = {
			'workOrderId' : workOrderId,

		};
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'LegacyMeasurementBook.html?filterMeasurementBookData',
				requestData, 'json');
		var result = [];
		if (ajaxResponse.length != 0) {
			$
					.each(
							ajaxResponse,
							function(index) {
								var obj = ajaxResponse[index];
								if (obj.mbStatus != "Draft") {
									result
											.push([
													obj.workOrderNo,
													obj.contractFromDateDesc,
													obj.contractMastDTO.contNo,
													obj.contractDate,
													obj.mbNo,
													'<div style="display: flex; justify-content: flex-end"> <div>'
															+ obj.mbTotalAmt
																	.toFixed(2)
															+ '</div></div>',
													obj.mbStatus,
													'<td >'
															+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''
															+ obj.mbId
															+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
															+ '</td>' ]);
								} else {
									result
											.push([
													obj.workOrderNo,
													obj.contractFromDateDesc,
													obj.contractMastDTO.contNo,
													obj.contractDate,
													obj.mbNo,
													'<div style="display: flex; justify-content: flex-end"> <div>'
															+ obj.mbTotalAmt
																	.toFixed(2)
															+ '</div></div>',
													obj.mbStatus,
													'<td >'
															+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''
															+ obj.mbId
															+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
															+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="getActionForDefination(\''
															+ obj.mbId
															+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
															+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="showWarningForSendRA(\''
															+ obj.mbId
															+ '\', \''
															+ obj.mbTotalAmt
															+ '\',\'R\')"  title="Send for RA Bill"><i class="fa fa-share-square-o"></i></button>'
															+ '</td>' ]);
								}

							});
			table.rows.add(result);
			table.draw();
			if ($('#workId').val() != '') {
				$('.add').removeClass("hide");
			} else {
				$('.add').addClass("hide");
			}
		} else {
			errorList.push(getLocalMessage('mb.mbNoRecordsFound'));
			displayErrorsOnPage(errorList);
			if (workOrderId != '')
				$('.add').removeClass("hide");
		}
	}

}

function fileCountUpload(obj) {

	var errorList = validate();
	if (errorList.length == 0) {
		var row = $("#attachDoc tbody .appendableClass").length;
		$("#length").val(row);
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var response = __doAjaxRequest(
				'LegacyMeasurementBook.html?fileCountUpload', 'POST',
				requestData, false, 'html');
		$("#uploadTagDiv").html(response);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

$("#attachDoc").on("click", '.delButton', function(e) {

	var row = $("#attachDoc tbody .appendableClass").length;
	if (row != 1) {
		$(this).parent().parent().remove();
		reOrderSequence('.appendableClass');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
	e.preventDefault();
});

function OpenCreateMb() {
	var divName = formDivName;
	var workId = $('#workId').val();
	var requestData = {

	}

	var url = "LegacyMeasurementBook.html?CreateMb";

	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function saveLegacyMeasurementBook(obj) {
	var errorList = validate();
	if (errorList.length == 0) {
		var url = "LegacyMeasurementBook.html";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validate() {

	var errorList = [];

	$("#attachDoc tbody .appendableClass")
			.each(
					function(i) {
						var oldMbNo = $("#oldMbNo" + i).val();
						var mbTotalAmt = $("#mbTotalAmt" + i).val();
						var workMbTakenDate = $("#workMbTakenDate" + i).val();

						var rowCount = i + 1;

						if (oldMbNo == "" || oldMbNo == null) {
							errorList
									.push(getLocalMessage("wms.enter.oldMeasurement.bookNo.entryNo")
											+ rowCount);
						}
						if (mbTotalAmt == "" || mbTotalAmt == null) {
							errorList
									.push(getLocalMessage("wms.enter.oldMeasurement.bookAmt.entryNo")
											+ rowCount);
						}
						if (workMbTakenDate == "" || workMbTakenDate == null) {
							errorList
									.push(getLocalMessage("wms.enter.actualMeasurement.takenDate.entryNo")
											+ rowCount);
						}

					});
	return errorList;
}

function getActionForDefination(workId, mode) {
	var divName = '.content-page';
	var url = "LegacyMeasurementBook.html?editMb";
	var actionParam = {
		'workId' : workId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function reOrderSequence(classNameFirst) {

	$(classNameFirst).each(
			function(i) {

				// id binding
				$(this).find("input:text:eq(0)").attr("id", "oldMbNo" + i);
				$(this).find("input:text:eq(1)").attr("id",
						"workMbTakenDate" + i);
				$(this).find("input:text:eq(2)").attr("id", "mbTotalAmt" + i);

				// path binding
				$(this).find("input:text:eq(0)").attr("name",
						"mbList[" + i + "].oldMbNo");
				$(this).find("input:text:eq(1)").attr("name",
						"mbList[" + i + "].workMbTakenDate");
				$(this).find("input:text:eq(2)").attr("name",
						"mbList[" + i + "].mbTotalAmt");

			});
}
$("#attachDoc").on('click', '.addCF', function() {

	var errorList = [];
	errorList = validate(errorList);
	if (errorList.length == 0) {

		var content = $("#attachDoc").find('tr:eq(1)').clone();
		$("#attachDoc").append(content);

		content.find("input:text").val('');
		content.find("select").val('');
		content.find("textarea").val('');

		content.find("input:hidden").val('');
		$('.error-div').hide();
		reOrderSequence('.appendableClass'); // reorder id and Path
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : 0,
		});
	} else {
		displayErrorsOnPage(errorList);
	}
});

function doFileDeletion(obj, id) {
	requestData = {
		"id" : id
	};
	url = 'LegacyMeasurementBook.html?doEntryDeletion';
	var row = $("#attachDoc tbody .appendableClass").length;
	if (row != 1) {
		var response = __doAjaxRequest(
				'LegacyMeasurementBook.html?doEntryDeletion', 'POST',
				requestData, false, 'html');
	}
}

function showWarningForSendRA(workId, amount, mode) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var yes = getLocalMessage("works.management.proceed");
	var no = getLocalMessage("mb.cancel");

	message += '<div class =" text-align:center";padding:25px ">'
			+ '<p class="text-blue-2 font-size:150%; text-align:center padding-15">'
			+ getLocalMessage("mb.do.you.want.to.proceed");
	+'</p>' + '</div>';
	message += '<div class=" text-center padding-bottom-10">' + '<p >'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="proceedForMbApproval(\'' + workId
			+ '\',\'' + amount + '\',\'' + mode + '\')"/>' + "  "
			+ '<input class="btn btn-danger" type=\'button\' value=\'' + no
			+ '\'  id=\'no\' ' + ' onclick="closeDecisionBox(\'' + errMsgDiv
			+ '\')"/>' + '</p>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;

}

function sendForRABillProcess(amount, workId, mode) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';

	sMsg = getLocalMessage('wms.mb.proceed.for.ra.approval.process');
	cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + sMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForMbApproval(\'' + workId + '\',\'' + amount
			+ '\',\'' + mode + '\')"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);

	return false;
}

function proceedForMbApproval(workId, amount, mode) {

	var requestData = {
		'workId' : workId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(
			"LegacyMeasurementBook.html?updateRaBillStatus", 'POST',
			requestData, false, 'html');
	searchMbDetails();
	$.fancybox.close();

}
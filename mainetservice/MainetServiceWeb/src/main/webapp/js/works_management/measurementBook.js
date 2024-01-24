$(document).ready(function() {

	var dateField = $('#workMbTakenDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0
	});
	// $('#nonSordataTables').DataTable();
	dateField.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	$("#datatables").dataTable({
		language: { search: "" }, //Defect #155403
	});
	$('.appendableClass').each(function(i) {
		getRateUnitBySorId(i);
	});
	calculateTotal();

	// calculateRateAnalysis();

	$('#measurementDetails tbody tr').each(function(i) {
		var count = i + 1;
		var meEstNumber = $("#meEstNumber" + i).val();
		var meActNumber = $("#meActNumber" + i).val();
		var meEstLength = $("#meEstLength" + i).val();
		var meActLength = $("#meActLength" + i).val();
		var meEstBreadth = $("#meEstBreadth" + i).val();
		var meActBreadth = $("#meActBreadth" + i).val();
		var meEstHeight = $("#meEstHeight" + i).val();
		var meActHeight = $("#meActHeight" + i).val();
		var meEstToltal = $("#meEstToltal" + i).val();
		var meActToltal = $("#meActToltal" + i).val();
		if (meEstNumber < meActNumber) {
			$("#meActNumber" + i).css("background-color", "#ffcccc");
		}
		if (parseFloat(meEstLength) < parseFloat(meActLength)) {
			$("#meActLength" + i).css("background-color", "#ffcccc");
		}
		if (parseFloat(meEstBreadth) < parseFloat(meActBreadth)) {
			$("#meActBreadth" + i).css("background-color", "#ffcccc");
		}
		if (parseFloat(meEstHeight) < parseFloat(meActHeight)) {
			$("#meActHeight" + i).css("background-color", "#ffcccc");
		}
		if (parseFloat(meEstToltal) < parseFloat(meActToltal)) {
			$("#meActToltal" + i).css("background-color", "#ffcccc");
		}
	});

	$('#rateAnalysis tbody tr').each(function(i) {

		var count = i + 1;
		var workEstQuantity = $("#workEstQuantity" + i).val();
		var workActQuantity = $("#workActQuantity" + i).val();
		var workEstAmount = $("#workEstAmount" + i).val();
		var workActAmt = $("#workActAmt" + i).val();
		if (!isNaN(workEstQuantity) && !isNaN(workActQuantity)) {
			if (parseFloat(workEstQuantity) < parseFloat(workActQuantity)) {
				$("#workActQuantity" + i).css("background-color", "#ffcccc");
			}
		}

		if (!isNaN(workEstAmount) && !isNaN(workActAmt)) {
			if (parseFloat(workEstAmount) < parseFloat(workActAmt)) {
				$("#workActAmt" + i).css("background-color", "#ffcccc");
			}
		}

	});

	$('#nonSordataTables tbody .appendableClass').each(function(i) {

		var count = i + 1;
		var workEstQuantity = $("#workEstQuantity" + i).val();
		var workActQuantity = $("#workActMbQuantity" + i).val();
		var workEstAmount = $("#workEstAmount" + i).val();
		var workActAmt = $("#workActAmt" + i).val();
		if (!isNaN(workEstQuantity) && !isNaN(workActQuantity)) {
			if (parseFloat(workEstQuantity) < parseFloat(workActQuantity)) {
				$("#workActQuantity" + i).css("background-color", "#ffcccc");
			}
		}

		if (!isNaN(workEstAmount) && !isNaN(workActAmt)) {
			if (parseFloat(workEstAmount) < parseFloat(workActAmt)) {
				$("#workActAmt" + i).css("background-color", "#ffcccc");
			}
		}

	});
	// calculateNonSorMbAmt();

	$('.hasNumber').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
	if ($("#saveMode").val() == 'V' || $("#saveMode").val() == 'AP') {
		$("#mbRateAnalysis :input").prop("disabled", true);
		$("#measurementBook :input").prop("disabled", true);
		$("#measurementDetail :input").prop("disabled", true);
		$("#directMeasurementDetail :input").prop("disabled", true);
		$("#mbEnclosuresForm :input").prop("disabled", true);
		$("#mbTaxDetails :input").prop("disabled", true);
		$("#checkList :input").prop("disabled", true);
		$("#measurementDetImages :input").prop("disabled", true);
		$("#backButton").prop("disabled", false);
		$('.Upload').prop("disabled", false);
	}
	if ($("#saveMode").val() == 'AP') {
		$("#selectedItems").hide();
	}

	var dateFields = $('.dates');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

});

function searchMbDetails() {
	var errorList = [];
	var count = 0;
	var subCount = 0;
	var workId = $('#workId').val();
	var status = $('#status').val();
	var mbNo = $('#mbNo').val().trim();
	var workName = $('#workName').val().trim();
	var vendorId = $("#vendorId").val();
	if (workId == "" && status == "" && mbNo == "" && workName == ""
			&& vendorId == "") {
		errorList.push(getLocalMessage('mb.search.field'));
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var requestData = {
			'workId' : workId,
			'status' : status,
			'mbNo' : mbNo,
			"workName" : workName,
			"vendorId" : vendorId
		};
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = __doAjaxRequest(
				'MeasurementBook.html?filterMeasurementBookData', 'POST',
				requestData, false, 'json');
		var result = [];
		if (ajaxResponse.length != 0) {
			$
					.each(
							ajaxResponse,
							function(index) {
								var obj = ajaxResponse[index];
								if (obj.mbStatus != "Draft"
										&& obj.mbStatus != "Approved") {
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
								} else if (obj.mbStatus == "Approved") {
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
															+ '<button type="button"  class="btn btn-warning btn-sm margin-right-10 margin-left-10"  onclick="getPrintRABill(\''
															+ obj.mbId
															+ '\',\'R\')" title="Print RA Bill"><i class="fa fa-print"></i></button>'
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
															+ obj.workId
															+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
															+ '<button type="button" class="btn btn-green-3 btn-sm margin-right-10" onclick="showWarningForSendRA(\''
															+ obj.workId
															+ '\', \''
															+ obj.mbTotalAmt
															+ '\',\'R\')"  title="Send for RA Bill"><i class="fa fa-share-square-o"></i></button>'
															+ '</td>' ]);
								}
								if (obj.mbStatus == "Draft"
										|| obj.mbStatus == "Pending") {
									count++;
								}
								if (obj.workStatus == 'C') {
									subCount++;
								}
							});
			table.rows.add(result);
			table.draw();
			/*
			 * if (count == 0 && $('#workId').val() != '' && subCount == 0 &&
			 * status == '') { $('.add').removeClass("hide"); } else {
			 * $('.add').addClass("hide"); }
			 */
		} else {
			errorList.push(getLocalMessage('mb.mbNoRecordsFound'));
			displayErrorsOnPage(errorList);
			/*
			 * if (status == '' && mbNo == '' && vendorId == '' && workName == '' &&
			 * workId != '') $('.add').removeClass("hide");
			 */
		}
	}

}

function getActionForDefination(workId, mode) {
	var divName = '.content-page';
	var url = "MeasurementBook.html?editMb";
	var actionParam = {
		'workId' : workId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$('#rateAnalysis tbody tr').each(function(i) {
		getRateUnitBySorId(i);
	});
}
function addMb() {
	var URL = 'MeasurementBook.html?addMb';
	var divName = '.content-page';

	var ajaxResponse = __doAjaxRequest(URL, 'POST', {}, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$("#workId").val('');
	$("#contNo").val('');
	$("#contractValue").val('');
	$("#startDate").val('');

	prepareTags();
}
function OpenCreateMb() {
	var divName = formDivName;
	var workId = $('#workId').val();
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';
	var errorList = [];
	var requestData = {
		'workId' : workId,
		'mode' : null,
	}
	if (workId = "" || workId == null || workId == false || workId == "0"
			|| workId == undefined) {
		errorList.push(getLocalMessage("mb.mbworkOrderNo"));
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var ajaxResponse = __doAjaxRequest(
				"MeasurementBook.html?getMbAmountSheet", 'POST', requestData,
				false, 'json');
	}

	/*
	 * Defect #82006-->not allowed to add MB entry if previous MB is not sent
	 * for approval
	 */
	if (ajaxResponse.mbStatus == "D") {
		errorList.push(getLocalMessage("mb.mbworkOrderNo.status"));
		displayErrorsOnPage(errorList);
		$("#workId").val('');
		$("#contNo").val('');
		$("#contractValue").val('');
		$("#startDate").val('');
		$("#projName").val('');
		$("#workName").val('');
		$("#workMbTakenDate").val('');
		$("#description").val('');
		$("#empId").val('');
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		return false;

	}
	if (ajaxResponse.status == "A" && ajaxResponse.mbConsumed != "Y") {
		var contractNo = $("#tenderNo").val();
		var url = "MeasurementBook.html?CreateMb";
		data = {
			"contractNo" : contractNo,
		};
		var response = __doAjaxRequest(url, 'post', data, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(response);
	} else {
		mbMsg = getLocalMessage("mb.all.items.are.consumed");

		sMsg = getLocalMessage("mb.beyond.deviated.amount");
		cMsg = getLocalMessage("mb.enter.atleast.one.lbh.item.codes");
		dMsg = getLocalMessage("mb.unselect.entry.item.codes");
		cls = getLocalMessage("wms.close");

		message += '<h4 class="text-center">Work Estimation Details</h4>';
		message += '<div class="margin-right-10 margin-left-10">';
		message += '<table class=\"table table-bordered"\>' + '<tr>' + '<th>'
				+ getLocalMessage("mb.work.estimate.amount") + '</th>'
				+ '<td class="text-right"> '
				+ parseFloat(ajaxResponse.estimateAmount).toFixed(2) + '</td> '
				+ '</tr>';
		message += '<tr>' + ' <th>'
				+ getLocalMessage("work.def.deviation.percentage") + '</th>'
				+ '<td class="text-right">'
				+ parseFloat(ajaxResponse.deviation).toFixed(2) + '</td>'
				+ '</tr>';
		message += '<tr>' + ' <th>'
				+ getLocalMessage("mb.estimated.deviation.amount") + '</th>'
				+ '<td class="text-right">'
				+ parseFloat(ajaxResponse.estimateAcessAmount).toFixed(2)
				+ '</td>' + '</tr>';
		message += '<tr>' + ' <th>' + getLocalMessage("mb.total.amount")
				+ '</th>' + '<td class="text-right">'
				+ parseFloat(ajaxResponse.mbAmount).toFixed(2) + '</td>'
				+ '</tr></table>';
		message += '</div>';

		if (ajaxResponse.itemCode != null && ajaxResponse.itemCode != "") {
			message += '<h4 class=\"text-center red padding-12\">' + cMsg
					+ ajaxResponse.itemCode + dMsg + '</h4>';
		} else {
			if (ajaxResponse.mbConsumed != null
					&& ajaxResponse.mbConsumed == "Y") {
				message += '<h4 class=\"text-center red padding-12\">' + mbMsg
						+ '</h4>';
			} else {
				message += '<h4 class=\"text-center red padding-12\">' + sMsg
						+ '</h4>';
			}

		}

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
		return false;
	}

}

function openMbMasForm() {

	var divName = formDivName;
	var url = "MeasurementBook.html?openMbMasForm";
	data = {};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function checkMbDetails(mbDetId) {

	var workId = $("#workId").val();
	var url = "MeasurementBook.html?checkMbDetails";
	data = {
		'mbDetId' : mbDetId,
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);

}

function getWorkOrderDetail(obj) {

	var errorList = [];
	if ($(obj).val() != '') {
		var requestData = {
			"workOrderId" : $(obj).val()
		}
		var response = __doAjaxRequest(
				'MeasurementBook.html?getWorkOrderDetail', 'post', requestData,
				false, 'json');
		var value = response.split(",");
		if (value[0] != "null") {

			$('#tenderNo').val(value[0]);
		} else {
			$('#tenderNo').val("");
		}
		if (value[1] != 'null') {
			if (value[1].length > 10) {
				$('#agreementFromDate').val(formatDate(value[1].substr(0, 10)));
			}
		} else {
			$('#agreementFromDate').val("");
		}
		prepareTags();
	} else {
		return false;
	}
}

function formatDate(date) {
	var parts = date.split("-");
	var formattedDate = parts[2] + "/" + parts[1] + "/" + parts[0];
	return formattedDate;
}

function addSelectedItems(obj) {
	var errorList = [];
	var count = 0;

	if ($.fn.DataTable.isDataTable('#datatables')) {
		$('#datatables').DataTable().destroy();
	}
	var workMbTakenDate = $("#workMbTakenDate").val();
	var empId = $("#empId").find("option:selected").attr('value');
	if (workMbTakenDate == '') {
		errorList.push(getLocalMessage('mb.SelectActualMeasurementTakenDate'));
		triggerDataTable();
		displayErrorsOnPage(errorList);
		return false;
	}
	if (empId == '') {
		errorList.push(getLocalMessage("wms.SelectEngineerName"));
		triggerDataTable();
		displayErrorsOnPage(errorList);
		return false;
	}
	var startDate = formatDate($("#StartDate").val().substr(0, 10));

	var endDate = formatDate($("#EndDate").val().substr(0, 10));

	if ($.datepicker.parseDate('dd/mm/yy', $("#workMbTakenDate").val()) < $.datepicker
			.parseDate('dd/mm/yy', startDate)
			|| $.datepicker.parseDate('dd/mm/yy', $("#workMbTakenDate").val()) > $.datepicker
					.parseDate('dd/mm/yy', endDate)) {

		errorList.push(getLocalMessage("mb.select.mbDate") + " : "
				+ getLocalMessage("work.order.contract.from.date") + " ==>"
				+ startDate + " , "
				+ getLocalMessage("work.order.contract.to.date") + " ==>"
				+ endDate);
		triggerDataTable();
		displayErrorsOnPage(errorList);
		return false;

	}
	$('#datatables tbody tr').each(function(i) {
		var ChkBx = $("#check" + i).is(':checked');
		if (ChkBx) {
			count++;
		}
	});
	if (count != 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'MeasurementBook.html?saveMbEstimationData', 'POST',
				requestData, false);

		showConfirmBox(getLocalMessage("mb.EstimatedItemsSavedsuccess"));
	} else {
		errorList.push(getLocalMessage('mb.AddEstimatedItems'));
		triggerDataTable();
		displayErrorsOnPage(errorList);
	}

}

function showConfirmBox(successMsg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceed() {
	var divName = '.content-page';
	var url = "MeasurementBook.html?AddMeasurementSheet";
	var formData = $("#measurementBook").serialize();
	var ajaxResponse = __doAjaxRequest(url, 'POST', formData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$.fancybox.close();
	calculateTotalMbAmt();
	$('#datatablesList').DataTable();
	prepareTags();
}

function measurementSheetAction(param, workEId, mbId, mbDetId, directFlag,
		sorId) {

	var errorList = [];
	if (param == 'openRateAnalysis') {
		if (sorId == null) {
			errorList.push(getLocalMessage("mb.SORIDNotDefined"));
			displayErrorsOnPage(errorList);
			return false;
		}
	}

	var actionParam = {
		'workEId' : workEId,
		'mbId' : mbId,
		'mbDetId' : mbDetId,
		'sorId' : sorId,
		'directFlag' : directFlag
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MeasurementBook.html' + '?' + param,
			actionParam, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	if (param == 'openRateAnalysis') {
		triggerRateTable();
	}
	if (param == 'measurementListByworkEstimateId') {
		triggerMeasurementTable();
	}
	if (directFlag == 'U') {
		calculateDirectMeasurementTotal();
	}

}
function measurementSheet() {
	var divName = '.content-page';
	var url = "MeasurementBook.html?AddMeasurementSheet";
	var actionParam = {
		'actionParam' : 'actionParam'
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	calculateTotalMbAmt();
	$('#datatablesList').DataTable();
	prepareTags();
}

function getRateUnitBySorId(index) {
	if ($("#gRateMastId" + index).find("option:selected").attr('code') != undefined) {
		var rateAndunit = $("#gRateMastId" + index).find("option:selected")
				.attr('code').split(",");
		$("#sorBasicRate" + index).val(rateAndunit[1]);
		$("#sorIteamUnit" + index).val(rateAndunit[0]);
		$("#workActAmt" + index).attr("readonly", true);
	} else {
		$("#workActQuantity" + index).attr("readonly", true);
	}
}
function saveMbMeasureDetails(element) {
	var errorList = [];
	errorList = validateMeasurementDetail();
	if (errorList == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('MeasurementBook.html?SaveLbhForm',
				'POST', requestData, false);
		// D72888
		showConfirmBoxforMbDetail(getLocalMessage("mb.mbLbhFormSaved"));
	} else {
		displayErrorsOnPage(errorList);
	}

}

function saveRateAnalysis(element) {
	var errorList = [];
	if ($.fn.DataTable.isDataTable('#rateAnalysis')) {
		$('#rateAnalysis').DataTable().destroy();
	}
	if ($('#rateAnalysis tbody .appendableClass').length != 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('MeasurementBook.html?saveRateAnalysis',
				'POST', requestData, false);
		showConfirmBox(getLocalMessage("works.estimate.rate.analysis.save"));
	} else {
		errorList.push(getLocalMessage("mb.norate.items"));
		displayErrorsOnPage(errorList);
		$('#rateAnalysis').DataTable();
	}
}

function saveEnclosuresData(enclosuresData) {
	var errorList = [];
	var formName = findClosestElementId(enclosuresData, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('MeasurementBook.html?saveMbEnclosuers',
			'POST', requestData, false);
	showConfirmBoxForFinalSubmit(getLocalMessage('mb.MbDetailsSaved'));
}
function showConfirmBoxForFinalSubmit(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '
			+ getLocalMessage(successMsg) + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForFinalSubmit()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceedForFinalSubmit() {

	if ($("#requestFormFlag").val() == 'AP') {
		var divName = '.content-page';
		var url = "WorkMBApproval.html?showCurrentForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		$.fancybox.close();
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'MeasurementBook.html');
		$("#postMethodForm").submit();
	}
}

function fileCountUpload(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('MeasurementBook.html?fileCountUpload',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
}

function openAddMbEnclosuersForm() {
	var formUrl = 'MeasurementBook.html';
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + 'AddMbEnclosuersForm', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function openMbTaxDetailsForm() {

	var formUrl = 'MeasurementBook.html';
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + 'mbTaxDetailsForm', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#mbTaxDetailsTab').DataTable();
	prepareTags();
}

function addMbTaxDetails() {
	var errorList = [];
	errorList = validateTaxDetails();
	if (errorList.length == 0) {
		addTableRow('mbTaxDetailsTab');
	} else {
		$('#mbTaxDetailsTab').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function saveMbTaxDetails(element) {

	var errorList = [];
	errorList = validateTaxDetails();
	if (errorList.length == 0) {
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequest(
				'MeasurementBook.html?saveMbTaxDetails', 'POST', requestData,
				false);
		showConfirmBoxForTax(getLocalMessage('mb.mbTaxDetails'));

	} else {
		$('#mbTaxDetailsTab').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function showConfirmBoxForTax(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedTax()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceedTax() {
	$.fancybox.close();
	openAddMbEnclosuersForm();
}

function validateTaxDetails() {

	var errorList = [];
	if ($.fn.DataTable.isDataTable('#mbTaxDetailsTab')) {
		$('#mbTaxDetailsTab').DataTable().destroy();
	}
	$("#mbTaxDetailsTab tbody tr")
			.each(
					function(i) {
						var taxId = $("#taxId" + i).val();
						var mbTaxType = $("#mbTaxType" + i).val();
						var mbTaxFact = $("#mbTaxFact" + i).val();
						var mbTaxFactCode = $("#mbTaxFact" + i).find(
								"option:selected").attr('code');
						var mbTaxValue = $("#mbTaxValue" + i).val();
						var rowCount = i + 1;

						if (taxId == "" || taxId == null) {
							errorList.push(getLocalMessage("mb.selectTaxCat")
									+ rowCount);
						}

						if (mbTaxType == "" || mbTaxType == null) {
							errorList.push(getLocalMessage("mb.mbTaxType")
									+ rowCount);
						}
						if (mbTaxFact == "" || mbTaxFact == null) {
							errorList.push(getLocalMessage("mb.mbTaxFactor")
									+ rowCount);
						}
						if (mbTaxValue == "" || mbTaxValue == null) {
							errorList.push(getLocalMessage("mb.mbTaxvalue")
									+ rowCount);
						}
						if (mbTaxFactCode == 'PER') {
							if (mbTaxValue != "" && mbTaxValue > 100) {
								errorList
										.push(getLocalMessage("mb.mbPercentLess")
												+ rowCount);
							}
						}

					});

	return errorList;

}

$("#attachDoc").on("click", '.delButton', function(e) {
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
	}
	e.preventDefault();
});

$("#deleteDoc").on(
		"click",
		'#deleteFile',
		function(e) {

			var errorList = [];
			var fileArray = [];

			if (errorList.length > 0) {
				$("#errorDiv").show();
				displayErrorsOnPage(errorList);
				return false;
			} else {
				$(this).parent().parent().remove();
				var fileId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');
				if (fileId != '') {
					fileArray.push(fileId);
				}
				$('#removeEnclosureFileById').val(fileArray);
			}
		});

function calculateTotal() {

	var errorList = [];
	var subTotal = 0;
	$('#measurementDetails tbody tr')
			.each(
					function(i) {

						var toatlAmount = 0.00;
						var meEstNumber = $("#meEstNumber" + i).val();
						var meActNumber = $("#meActNumber" + i).val();
						var meEstLength = $("#meEstLength" + i).val();
						var meActLength = $("#meActLength" + i).val();
						var meEstBreadth = $("#meEstBreadth" + i).val();
						var meActBreadth = $("#meActBreadth" + i).val();
						var meEstHeight = $("#meEstHeight" + i).val();
						var meActHeight = $("#meActHeight" + i).val();
						var meEstToltal = $("#meEstToltal" + i).val();
						var meActToltal = $("#meActToltal" + i).val();

						if (meEstNumber != ''
								&& meActNumber != ''
								&& parseInt(meEstNumber) < parseInt(meActNumber)) {
							$("#meActNumber" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActNumber" + i).css("background-color", "");
						}
						if (meEstLength != ''
								&& meActLength != ''
								&& parseFloat(meEstLength) < parseFloat(meActLength)) {
							$("#meActLength" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActLength" + i).css("background-color", "");
						}
						if (meEstBreadth != ''
								&& meActBreadth != ''
								&& parseFloat(meEstBreadth) < parseFloat(meActBreadth)) {
							$("#meActBreadth" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActBreadth" + i).css("background-color", "");
						}
						if (meEstHeight != ''
								&& meActHeight != ''
								&& parseFloat(meEstHeight) < parseFloat(meActHeight)) {
							$("#meActHeight" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActHeight" + i).css("background-color", "");
						}
						if (meEstToltal != ''
								&& meActToltal != ''
								&& parseFloat(meEstToltal) < parseFloat(meActToltal)) {
							$("#meActToltal" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActToltal" + i).css("background-color", "");
						}

						if ($("#meMentValType" + i).val() == "C") {

							if ($("#meActLength" + i).val() != ""
									&& $("#meActLength" + i).val() != 0
									&& $("#meActBreadth" + i).val() != ""
									&& $("#meActBreadth" + i).val() != 0
									&& $("#meActHeight" + i).val() != ""
									&& $("#meActHeight" + i).val() != 0)
								toatlAmount = $("#meActLength" + i).val()
										* $("#meActBreadth" + i).val()
										* $("#meActHeight" + i).val();
							else if ($("#meActLength" + i).val() != ""
									&& $("#meActLength" + i).val() != 0
									&& $("#meActBreadth" + i).val() != ""
									&& $("#meActBreadth" + i).val() != 0)
								toatlAmount = $("#meActLength" + i).val()
										* $("#meActBreadth" + i).val();
							else if ($("#meActHeight" + i).val() != ""
									&& $("#meActHeight" + i).val() != 0
									&& $("#meActBreadth" + i).val() != ""
									&& $("#meActBreadth" + i).val() != 0)
								toatlAmount = $("#meActBreadth" + i).val()
										* $("#meActHeight" + i).val();
							else if ($("#meActLength" + i).val() != ""
									&& $("#meActLength" + i).val() != 0
									&& $("#meActHeight" + i).val() != ""
									&& $("#meActHeight" + i).val() != 0)
								toatlAmount = $("#meActLength" + i).val()
										* $("#meActHeight" + i).val();
							else if ($("#meActLength" + i).val() != ""
									&& $("#meActLength" + i).val() != 0)
								toatlAmount = $("#meActLength" + i).val();
							else if ($("#meActBreadth" + i).val() != ""
									&& $("#meActBreadth" + i).val() != 0)
								toatlAmount = $("#meActBreadth" + i).val();
							else if ($("#meActHeight" + i).val() != ""
									&& $("#meActHeight" + i).val() != 0)
								toatlAmount = $("#meActHeight" + i).val();

							/*
							 * if (toatlAmount != "") { $("#meActValue" +
							 * i).val( (Number(toatlAmount)).toFixed(2));
							 * $("#meActValue" + i).val(); }
							 */
							if (toatlAmount == 0) {
								$("#meActValue" + i).val('');
							} else {
								$("#meActValue" + i).val(
										(Number(toatlAmount)).toFixed(4));
							}

						} else if ($("#meMentValType" + i).val() == "D") {
							toatlAmount = $("#meActValue" + i).val();
						} else if ($("#meMentValType" + i).val() == "F") {
							try {
								if ($("#meActFormula" + i).val() != "")
									toatlAmount = eval($("#meActFormula" + i)
											.val());
							} catch (e) {
								if (e instanceof SyntaxError) {
									toatlAmount = 0.00;
									errorList
											.push(getLocalMessage("work.measurement.sheet.details.formula.validation ")
													+ "  " + e.message);
									displayErrorsOnPage(errorList);
								}
							}
						}

						if (toatlAmount != undefined
								&& $("#meMentValType" + i).val() != "D") {
							if (toatlAmount == 0) {
								$("#meActValue" + i).val('');
							} else {
								$("#meActValue" + i).val(
										(Number(toatlAmount)).toFixed(4));
							}

						}

						if ($("#meMentType" + i).val() == "A"
								&& toatlAmount != undefined) {
							var amountAdd = toatlAmount
									* $("#meActNumber" + i).val();
							$("#meActToltal" + i).val(
									(Number(amountAdd)).toFixed(4));
						} else if ($("#meMentType" + i).val() == "D"
								&& toatlAmount != undefined) {
							var amountSub = toatlAmount
									* $("#meActNumber" + i).val();
							$("#meActToltal" + i).val(
									(-1 * (amountSub.toFixed(4))).toFixed(4));
						}
						/*
						 * if (toatlAmount != undefined && toatlAmount != '' &&
						 * $("#meMentValType" + i).val() == "D") { var amount =
						 * toatlAmount $("#meActValue" + i).val();
						 * $("#meActToltal" + i).val(
						 * (Number(amount).toFixed(2))); }
						 */

						subTotal += +$("#meActToltal" + i).val();

					});
	$("#subTotal").val(subTotal.toFixed(4));
}

function validateMeasurementDetail() {

	var errorList = [];
	$('#measurementDetails tbody tr')
			.each(
					function(i) {

						// var count = i + 1;
						var actNumber = $("#meActNumber" + i).val();
						var actLen = $("#meActLength" + i).val();
						var actBreadth = $("#meActBreadth" + i).val();
						var actHei = $("#meActHeight" + i).val();
						var valType = $("#meMentValType" + i).val();
						var actFormula = $("#meActFormula" + i).val();
						var actValue = $("#meActValue" + i).val();
						if (valType == 'C') {
							if (actValue != "" && actValue != 0
									&& actNumber == "") {
								errorList
										.push(getLocalMessage("mb.actualNoOfItems")
												+ (i + 1));
							}
							if (actNumber != "" && actNumber != 0
									&& actValue == "") {
								errorList
										.push(getLocalMessage("mb.mbLengthBreadthHeight")
												+ (i + 1));
							}

						} else if (valType == 'F') {

							if (actValue != "" && actValue != 0
									&& actNumber == "") {
								errorList
										.push(getLocalMessage("mb.actualNoOfItems")
												+ (i + 1));
							}
							if (actNumber != "" && actNumber != 0
									&& actFormula.trim() == "") {
								errorList
										.push(getLocalMessage("mb.ActualFormulae")
												+ (i + 1));
							}

						} else if (valType == 'D') {

							if (actValue != "" && actValue != 0
									&& actNumber == "") {
								errorList
										.push(getLocalMessage("mb.actualNoOfItems")
												+ (i + 1));
							}

							if (actValue == "" && actNumber != ""
									&& actNumber != 0) {
								errorList
										.push(getLocalMessage("mb.actualValue")
												+ (i + 1));
							}

						}
					});
	if ($('#measurementDetails tbody .appendableClass').length == 0) {
		errorList.push(getLocalMessage("mb.noMbDetails"));
	}

	return errorList;
}

function calculateRateAnalysis() {

	/*
	 * if ($.fn.DataTable.isDataTable('#rateAnalysis')) {
	 * $('#rateAnalysis').DataTable().destroy(); }
	 */
	$('#rateAnalysis tbody tr')
			.each(
					function(i) {
						var sum = 0;
						var basicRate = parseFloat($("#sorBasicRate" + i).val());
						var workEstQuantity = $("#workEstQuantity" + i).val();
						var workActQuantity = $("#workActQuantity" + i).val();

						var workEstAmount = $("#workEstAmount" + i).val();
						var workActAmt = $("#workActAmt" + i).val();

						if (!$("#workActQuantity" + i).is('[readonly]')) {
							if (workActQuantity != ''
									&& !isNaN(workActQuantity)
									&& !isNaN(basicRate)) {
								sum = (basicRate * workActQuantity);
								$("#workActAmt" + i).val(sum.toFixed(2));
								if (parseFloat(workEstQuantity) < parseFloat(workActQuantity)) {
									$("#workActQuantity" + i).css(
											"background-color", "#ffcccc");
								}
								if (parseFloat(workEstAmount) < parseFloat(sum)) {
									$("#workActAmt" + i).css(
											"background-color", '#ffcccc');
								}
							} else {
								$("#workActQuantity" + i).css(
										"background-color", "");
								$("#workActAmt" + i)
										.css("background-color", '');
							}

						} else {
							if (!isNaN(workActAmt) && workActAmt != '') {
								if (parseFloat(workEstAmount) < parseFloat(workActAmt)) {
									$("#workActAmt" + i).css(
											"background-color", '#ffcccc');
								} else {
									$("#workActAmt" + i).css(
											"background-color", '');
								}
							}
						}
					});
}

function triggerRateTable() {
	$('.appendableClass').each(function(i) {
		getRateUnitBySorId(i);
	});
	$("#rateAnalysis").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bStateSave" : true,
	});
}

function triggerMeasurementTable() {
	$("#measurementDetails").dataTable();
}
function triggerDataTable() {
	$("#datatables").dataTable();
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
			+ '\'  id=\'yes\' ' + ' onclick="sendForRABillProcess(\'' + workId
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

function sendForRABillProcess(workId, amount, mode) {

	var errorList = [];
	if (amount == '' || amount == '0') {
		errorList.push(getLocalMessage("mb.amount.connot.be.null"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {

		var requestData = {
			'workId' : workId,
			'mode' : mode
		}
		var ajaxResponse = __doAjaxRequest(
				"MeasurementBook.html?getMbAmountSheet", 'POST', requestData,
				false, 'json');
		showConfirmBoxForApproval(ajaxResponse, amount, workId, mode);
	}
}

function showConfirmBoxForApproval(ajaxResponse, amount, workId, mode) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';
	if (ajaxResponse.status == "A") {
		sMsg = getLocalMessage('wms.mb.proceed.for.ra.approval.process');
		cls = getLocalMessage("works.management.proceed");
	} else {
		sMsg = getLocalMessage("mb.beyond.deviated.amount");
		cMsg = getLocalMessage("mb.enter.atleast.one.lbh.item.codes");
		dMsg = getLocalMessage("mb.unselect.entry.item.codes");
		cls = getLocalMessage("wms.close");
	}

	message += '<h4 class="text-center">'
			+ getLocalMessage('mb.work.estimate.details') + '</h4>';
	message += '<div class="margin-right-10 margin-left-10">';
	message += '<table class=\"table table-bordered"\>' + '<tr>' + '<th>'
			+ getLocalMessage('mb.work.estimate.amount') + '</th>'
			+ '<td class="text-right"> '
			+ parseFloat(ajaxResponse.estimateAmount).toFixed(2) + '</td> '
			+ '</tr>';
	message += '<tr>' + ' <th>'
			+ getLocalMessage('work.def.deviation.percentage') + '</th>'
			+ '<td class="text-right">'
			+ parseFloat(ajaxResponse.deviation).toFixed(2) + '</td>' + '</tr>';
	message += '<tr>' + ' <th>'
			+ getLocalMessage('mb.estimated.deviation.amount') + '</th>'
			+ '<td class="text-right">'
			+ parseFloat(ajaxResponse.estimateAcessAmount).toFixed(2) + '</td>'
			+ '</tr>';
	message += '<tr>' + ' <th>' + getLocalMessage('mb.current.amount')
			+ '</th>' + '<td class="text-right">'
			+ parseFloat(amount).toFixed(2) + '</td>' + '</tr>';
	message += '<tr>' + ' <th>' + getLocalMessage('mb.total.amount') + '</th>'
			+ '<td class="text-right">'
			+ parseFloat(ajaxResponse.mbAmount).toFixed(2) + '</td>'
			+ '</tr></table>';
	message += '</div>';

	if (ajaxResponse.status == "A") {
		message += '<h4 class=\"text-center text-blue-2 padding-12\">' + sMsg
				+ '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceedForMbApproval(\'' + workId + '\',\'' + mode
				+ '\');"/></div>';
	} else {

		if (ajaxResponse.itemCode != null && ajaxResponse.itemCode != "") {
			message += '<h4 class=\"text-center red padding-12\">' + cMsg
					+ ajaxResponse.itemCode + dMsg + '</h4>';
		} else {
			message += '<h4 class=\"text-center red padding-12\">' + sMsg
					+ '</h4>';
		}

	}

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);

	return false;
}

function proceedForMbApproval(workId, mode) {

	var requestData = {
		'workId' : workId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(
			"MeasurementBook.html?updateRaBillStatus", 'POST', requestData,
			false, 'html');
	searchMbDetails();
	$.fancybox.close();

}

function closeDecisionBox(errorMsgDiv) {
	$.fancybox.close();
}

function calculateTotalMbAmt() {

	var subTotal = 0;
	if ($.fn.DataTable.isDataTable('#datatablesList')) {
		$('#datatablesList').DataTable().destroy();
	}

	$('#datatablesList tbody tr').each(function(i) {
		var sum = 0;
		var totalMbAmount = $("#totalMbAmount" + i).val();
		if (totalMbAmount != undefined && totalMbAmount != '')
			subTotal += +$("#totalMbAmount" + i).val();

	});
	$("#subTotalAmount").val((Number(subTotal).toFixed(2)));
	$('#datatablesList').DataTable();
}

function backToApprvForm() {

	var divName = '.content-page';
	var url = "WorkMBApproval.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function openMbNonSorForm() {

	var divName = formDivName;
	var url = "MeasurementBook.html?openMbNonSorForm";
	data = {};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	calculateNonSorMbAmt();
}

function calculateNonSorMbAmt() {

	var grandTotal = 0;
	$('#nonSordataTables tbody .appendableClass')
			.each(
					function(i) {
						var sum = 0;
						var toatlAmount = 0.00;
						var basicRate = parseFloat($("#sorBasicRate" + i).val());
						var workEstQuantity = $("#workEstQuantity" + i).val();
						var workActQuantity = $("#workActMbQuantity" + i).val();

						var workEstAmount = $("#workEstAmount" + i).val();
						var workActAmt = $("#workActAmt" + i).val();

						if (!$("#workActQuantity" + i).is('[readonly]')) {
							if (workActQuantity != ''
									&& !isNaN(workActQuantity)
									&& !isNaN(basicRate)) {
								sum = (basicRate * workActQuantity);
								$("#workActAmt" + i).val(sum.toFixed(2));
								if (parseFloat(workEstQuantity) < parseFloat(workActQuantity)) {
									$("#workActQuantity" + i).css(
											"background-color", "#ffcccc");
								}
								if (parseFloat(workEstAmount) < parseFloat(sum)) {
									$("#workActAmt" + i).css(
											"background-color", '#ffcccc');
								}
							} else {
								$("#workActQuantity" + i).css(
										"background-color", "");
								$("#workActAmt" + i)
										.css("background-color", '');
								$("#workActAmt" + i).val('');
							}
							if (sum != undefined && sum != '')
								grandTotal += +$("#workActAmt" + i).val();
						} else {
							if (!isNaN(workActAmt) && workActAmt != '') {
								if (parseFloat(workEstAmount) < parseFloat(workActAmt)) {
									$("#workActAmt" + i).css(
											"background-color", '#ffcccc');
								} else {
									$("#workActAmt" + i).css(
											"background-color", '');
								}
							}
						}
					});

	$("#subTotalAmount").val((Number(grandTotal).toFixed(2)));
	// $('#nonSordataTables').DataTable();
}

function saveMbNonSorItems(element) {

	var errorList = [];
	if ($.fn.DataTable.isDataTable('#nonSordataTables')) {
		$('#nonSordataTables').DataTable().destroy();
	}
	errorList = validateNonSorItems();
	if ($('#nonSordataTables tbody .appendableClass').length != 0
			&& errorList == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'MeasurementBook.html?saveMbNonSorItems', 'POST', requestData,
				false);
		// D72888
		showConfirmBoxforMbNonSor(getLocalMessage("mb.mbNonSorSave"));

	} else {
		if ($('#nonSordataTables tbody .appendableClass').length == 0) {
			errorList.push(getLocalMessage("mb.noMbNonSor"));
			displayErrorsOnPage(errorList);
			$('#nonSordataTables').DataTable();
		} else {
			displayErrorsOnPage(errorList);
			$('#nonSordataTables').DataTable();
		}

	}

}

function validateNonSorItems() {

	var errorList = [];
	$('#nonSordataTables tbody .appendableClass').each(
			function(i) {
				var workActMbQuantity = $("#workActMbQuantity" + i).val();
				var workActAmt = $("#workActAmt" + i).val();

				if (workActMbQuantity == "" || workActMbQuantity < 0) {
					errorList.push(getLocalMessage("wms.EnterActualQuantity")
							+ (i + 1));
				}

			});
	return errorList;
}

// used to add new estimate in mb creation

function openMBEstimateForm(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MeasurementBook.html?'
			+ 'openMBEstimateForm', requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	var actionParam = {
		'actionParam' : $("#sorList").val(),
		'sorId' : $("#sorId").val()
	}
	var url = "WorkEstimate.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}

function getPrintRABill(workNo, mode) {
	var divName = formDivName;
	var actionParam = {
		'appNo' : workNo,
	}
	var ajaxResponse = __doAjaxRequest('WorkMBApproval.html?printRABill',
			'post', actionParam, false, 'html');
	var divContents = ajaxResponse;
	$(divName).html(divContents);
	prepareTags();

}

function saveMbDirectMeasureDetails(element) {

	var errorList = [];
	errorList = validateDirectMeasurementDetail();
	if (errorList == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('MeasurementBook.html?SaveLbhForm',
				'POST', requestData, false);
		showConfirmBox(getLocalMessage("mb.mbLbhFormSaved"));
	} else {
		displayErrorsOnPage(errorList);
	}

}

function validateDirectMeasurementDetail() {

	var errorList = [];
	$('#directMeasurementDetails tbody tr').each(function(i) {
		var actNumber = $("#meActNumber" + i).val();
		var actValue = $("#meActToltal" + i).val();
		// comment by Suhel Said by NIlima Maim
		/*
		 * if (actValue != "" && actValue != 0 && actNumber == "") { errorList
		 * .push(getLocalMessage("mb.actualNoOfItems") + (i + 1)); }
		 */
		/*
		 * if (actNumber != "" && actNumber != 0 && actValue == "") { errorList
		 * .push(getLocalMessage("mb.mbLengthBreadthHeight") + (i + 1)); }
		 */
		/*
		 * if (actValue == "") { errorList
		 * .push(getLocalMessage("wms.SelectActualNoL/B/HEntry") + (i + 1)); }
		 */

	});
	return errorList;

}

function calculateDirectMeasurementTotal() {

	var errorList = [];
	var subTotal = 0;
	$('#directMeasurementDetails tbody .appendableClass')
			.each(
					function(i) {

						var toatlAmount = 0.00;
						var meEstNumber = $("#meEstNumber" + i).val();
						var meActNumber = $("#meActNumber" + i).val();

						var meEstLength = $("#meEstLength" + i).val();
						var meActLength = $("#meActLength" + i).val();
						var meEstBreadth = $("#meEstBreadth" + i).val();
						var meActBreadth = $("#meActBreadth" + i).val();
						var meEstHeight = $("#meEstHeight" + i).val();
						var meActHeight = $("#meActHeight" + i).val();
						var meEstToltal = $("#meEstToltal" + i).val();
						var meActToltal = $("#meActToltal" + i).val();
						//#154284-code added for direct abstract with no and without lbh		
					if ($("#meActNumber" + i).val() !="" &&  $("#meActToltal" + i).val() !="" &&
						   ($("#meActLength" + i).val() == "" || $("#meActLength" + i).val() == 0)
								&&($("#meActBreadth" + i).val() == "" || $("#meActBreadth" + i).val() == 0)
								&&($("#meActHeight" + i).val() == "" || $("#meActHeight" + i).val() == 0)){
						
							  toatlAmount = $("#meActToltal" + i).val();
							  subTotal =$("#meActNumber" + i).val() * $("#meActToltal" + i).val();
							  
						   }else{
						// if (meActNumber != '') {
						if ($("#meActLength" + i).val() != ""
								&& $("#meActLength" + i).val() != 0
								&& $("#meActBreadth" + i).val() != ""
								&& $("#meActBreadth" + i).val() != 0
								&& $("#meActHeight" + i).val() != ""
								&& $("#meActHeight" + i).val() != 0)
							toatlAmount = $("#meActLength" + i).val()
									* $("#meActBreadth" + i).val()
									* $("#meActHeight" + i).val();
						else if ($("#meActLength" + i).val() != ""
								&& $("#meActLength" + i).val() != 0
								&& $("#meActBreadth" + i).val() != ""
								&& $("#meActBreadth" + i).val() != 0)
							toatlAmount = $("#meActLength" + i).val()
									* $("#meActBreadth" + i).val();
						else if ($("#meActHeight" + i).val() != ""
								&& $("#meActHeight" + i).val() != 0
								&& $("#meActBreadth" + i).val() != ""
								&& $("#meActBreadth" + i).val() != 0)
							toatlAmount = $("#meActBreadth" + i).val()
									* $("#meActHeight" + i).val();
						else if ($("#meActLength" + i).val() != ""
								&& $("#meActLength" + i).val() != 0
								&& $("#meActHeight" + i).val() != ""
								&& $("#meActHeight" + i).val() != 0)
							toatlAmount = $("#meActLength" + i).val()
									* $("#meActHeight" + i).val();
						else if ($("#meActLength" + i).val() != ""
								&& $("#meActLength" + i).val() != 0)
							toatlAmount = $("#meActLength" + i).val();
						else if ($("#meActBreadth" + i).val() != ""
								&& $("#meActBreadth" + i).val() != 0)
							toatlAmount = $("#meActBreadth" + i).val();
						else if ($("#meActHeight" + i).val() != ""
								&& $("#meActHeight" + i).val() != 0)
							toatlAmount = $("#meActHeight" + i).val();

						if (toatlAmount != undefined
								&& $("#meActNumber" + i).val() != "") {
							var tamount = toatlAmount
									* $("#meActNumber" + i).val();
						} else {
							var tamount = toatlAmount;
						}

						/*
						 * if (toatlAmount != undefined && toatlAmount != '') {
						 * var tamount = toatlAmount $("#meActNumber" +
						 * i).val(); }
						 */
						if (tamount != undefined && tamount != 0) {
							$("#meActToltal" + i).val(
									(Number(tamount)).toFixed(2));
						} else {
							$("#meActToltal" + i).val("");
						}

						subTotal += +$("#meActToltal" + i).val();

						if (meEstNumber != ''
								&& meActNumber != ''
								&& parseInt(meEstNumber) < parseInt(meActNumber)) {
							$("#meActNumber" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActNumber" + i).css("background-color", "");
						}
						if (meEstLength != ''
								&& meActLength != ''
								&& parseFloat(meEstLength) < parseFloat(meActLength)) {
							$("#meActLength" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActLength" + i).css("background-color", "");
						}
						if (meEstBreadth != ''
								&& meActBreadth != ''
								&& parseFloat(meEstBreadth) < parseFloat(meActBreadth)) {
							$("#meActBreadth" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActBreadth" + i).css("background-color", "");
						}
						if (meEstHeight != ''
								&& meActHeight != ''
								&& parseFloat(meEstHeight) < parseFloat(meActHeight)) {
							$("#meActHeight" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActHeight" + i).css("background-color", "");
						}
						if (meEstToltal != ''
								&& meActToltal != ''
								&& parseFloat($("#meEstToltal" + i).val()) < parseFloat($(
										"#meActToltal" + i).val())) {
							$("#meActToltal" + i).css("background-color",
									"#ffcccc");
						} else {
							$("#meActToltal" + i).css("background-color", "");
						}

						// }
						   }
					});
	$("#subTotal").val(subTotal.toFixed(2));
}

function uploadImages(meMentId, mode, element) {

	var divName = '.content-page';
	var url = "MeasurementBook.html?updateMbImages";
	$("#measurementId").val(meMentId);
	$("#uploadMode").val(mode);
	/*
	 * var actionParam = { "meMentId" : meMentId, 'mode' : mode }
	 */

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function openCheckList() {

	var divName = formDivName;
	var url = "MeasurementBook.html?openCheckList";
	data = {};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function saveCheckListData(element) {

	var errorList = [];
	var checkDate = $("#checkDate").val();
	if (checkDate == '') {
		errorList.push(getLocalMessage('wms.PleaseSelectVerificationDate'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('MeasurementBook.html?saveCheckListData',
			'POST', requestData, false);
	showConfirmCheckListBox(getLocalMessage("wms.CheckListSavedSuccessfully"));
}

function showConfirmCheckListBox(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedCheckList()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceedCheckList() {
	var formUrl = 'MeasurementBook.html';
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + 'AddMbEnclosuersForm', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$.fancybox.close();
	prepareTags();
}

function proceedForOverHeads() {

	openMbNonSorForm('WorkEstimate.html', 'AddMbOverheadsForm');
	$.fancybox.close();
}

function openAddWorkOverheadsForm() {

	showConfirmBoxForFinalSubmit
	var divName = formDivName;
	var url = "MeasurementBook.html?AddMbOverheadsForm";
	data = {};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function saveWorkOverHeads(overHeadData) {

	var errorList = [];
	if ($('#overheadsDetails tbody .appendableClass').length == 0) {
		errorList.push(getLocalMessage("wms.noOverhead.save"));
		displayErrorsOnPage(errorList);
		$('#overheadsDetails').DataTable();
	}
	if ($(".defination").val() == "") {
		errorList
				.push(getLocalMessage("work.estimate.workDefination.required"));
	}
	if ($(".workName").val() == "") {
		errorList.push(getLocalMessage("work.estimate.work.name.required"));
	}
	errorList = validateOverheadsDetailsList(errorList);
	var formName = findClosestElementId(overHeadData, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = "MeasurementBook.html?validateOverheadAmount";
	var flag = __doAjaxRequest(url, 'post', requestData, false, 'json');
	if (flag == false) {
		errorList.push(getLocalMessage("wms.overheadVal.greater.workEstimateVal"));
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();

		var formName = findClosestElementId(overHeadData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('MeasurementBook.html?saveOverHeadData',
				'POST', requestData, false);
		showConfirmBoxForEnclosures(getLocalMessage("work.estimate.overhead.creation.success"));
	}
}

function showConfirmBoxForEnclosures(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '
			+ getLocalMessage(successMsg) + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedFromOverhead()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceedFromOverhead() {
	// D72888
	/* openAddWorkOverheadsForm('MeasurementBook.html','openCheckList'); */
	openCheckList();
	$.fancybox.close();

}
function validateOverheadsDetailsList(errorList) {

	var rowCount = $('#overheadsDetails tr').length;
	if (rowCount == 3 && $("#removeOverHeadById").val() != "") {
		return errorList;
	}
	$(".appendableClass")
			.each(
					function(i) {

						var itemCode = $("#overHeadCode" + i).val();
						var overHeadDesc = $("#overheadDesc" + i).val();
						var valueTypes = $("#overHeadvalType" + i).val();
						var overHeadRate = $("#overHeadRate" + i).val();
						var overHeadValue = $("#overHeadValue" + i).val();
						var actualAmount = $("#actualAmount" + i).val();

						var overHeadConstant = i + 1;
						if (itemCode == "") {
							errorList
									.push(getLocalMessage("work.estimate.enter.item.code")
											+ " " + overHeadConstant);
						}
						/*
						 * if(overHeadDesc == ""){
						 * errorList.push(getLocalMessage("work.estimate.enter.overhead.description")+"
						 * "+overHeadConstant); } if(overHeadRate == ""){ }
						 * if(overHeadValue == ""){
						 * errorList.push(getLocalMessage("work.estimate.enter.overheads.value")+"
						 * "+overHeadConstant); }
						 */
						if (actualAmount == "") {
							errorList
									.push(getLocalMessage("work.estimate.enter.overheads.amount")
											+ " " + overHeadConstant);
						}

					});
	return errorList;
}

function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'MeasurementBook.html');
	$("#postMethodForm").submit();
}

$('.appendableClass').each(function(i) {
	var grandTotal = 0;
	$(".overheadcalculation").each(function(i) {
		var overHeadValue = parseFloat(parseFloat($(this).val()));
		grandTotal += isNaN(overHeadValue) ? 0 : overHeadValue;
	});
	$("#totalOverhead").val(grandTotal.toFixed(2));
});

$("#overheadsDetails").on('input', function() {

	var grandTotal = 0;
	$(".overheadcalculation").each(function(i) {
		var actualAmount = parseFloat(parseFloat($(this).val()));
		grandTotal += isNaN(actualAmount) ? 0 : actualAmount;
	});
	$("#totalOverhead").val(grandTotal.toFixed(2));
});

function calculateTotalAmount(index) {

	var overHeadvalType = $("#overHeadvalType" + index).find("option:selected")
			.attr('code');
	var overHeadRate = $("#overHeadRate" + index).val();
	var workEstimateAmt = $("#overheadEstimateAmount").val();
	var totalOverHeadAmount = "";

	if (overHeadvalType != "" && overHeadRate != "" && workEstimateAmt != "") {
		if (overHeadvalType == "PER") {
			totalOverHeadAmount = (Number(workEstimateAmt
					* Number(overHeadRate) / 100)).toFixed(2);
			$("#actualAmount" + index).val(totalOverHeadAmount);
		} else if (overHeadvalType == "AMT") {
			$("#actualAmount" + index).val(overHeadRate);
		} else if (overHeadvalType == "MUL") {
			$("#actualAmount" + index).val(
					(workEstimateAmt * overHeadRate).toFixed(2));
		}
	}

	var grandTotal = 0;
	$(".overheadcalculation").each(function(i) {
		var actualAmount = parseFloat(parseFloat($(this).val()));
		grandTotal += isNaN(actualAmount) ? 0 : actualAmount;
	});
	$("#totalOverhead").val(grandTotal.toFixed(2));

}

function getAllOverheadDetails(index) {

	var overheadDesc = $("#overHeadCode" + index).find("option:selected").attr(
			'code');
	if (overheadDesc != "" && overheadDesc != undefined) {
		var overheadDesc = $("#overHeadCode" + index).find("option:selected")
				.attr('code').split(",");
		$("#overheadDesc" + index).val(overheadDesc[1]);

	}
}

$("#overheadsDetails").on("click", '.addOverHeadsDetails', function(e) {

	var count = $('#overheadsDetails tr').length - 1;
	var errorList = [];
	errorList = validateOverheadsDetailsList(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#overheadsDetails tr').last().clone();
		$('#overheadsDetails tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("input:text").val('');
		content.find("select").val('');
		content.find("input:text").val('');
		content.find('div.chosen-container').remove();
		content.find("select:eq(0)").chosen().trigger("chosen:updated");
		reOrderOverHeadsDetailsList();

	}
});

function reOrderOverHeadsDetailsList() {
	$('.appendableClass')
			.each(
					function(i) {

						// Id
						$(this).find("select:eq(0)").attr("id",
								"overHeadCode" + (i)).attr("onchange",
								"getAllOverheadDetails(" + (i) + ")");
						$(this).find("select:eq(1)").attr("id",
								"overHeadvalType" + (i)).attr("onchange",
								"calculateTotalAmount(" + (i) + ")");
						$(this).find("hidden:eq(0)").attr("id",
								"overHeadId" + (i));
						$(this).find("input:text:eq(0)")
								.attr("id", "sNo" + (i));

						$(this).find("input:text:eq(2)").attr("id",
								"overheadDesc" + (i));
						$(this).find("input:text:eq(3)").attr("id",
								"overHeadRate" + (i)).attr("onblur",
								"calculateTotalAmount(" + (i) + ")");
						;
						$(this).find("input:text:eq(4)").attr("id",
								"overHeadValue" + (i));
						$(this).find("input:text:eq(5)").attr("id",
								"actualAmount" + (i));

						// Names
						$(this).find("select:eq(0)")
								.attr(
										"name",
										"estimOverHeadDetDto[" + (i)
												+ "].overHeadCode");
						$(this).find("select:eq(1)").attr(
								"name",
								"estimOverHeadDetDto[" + (i)
										+ "].overHeadvalType");
						$(this).find("hidden:eq(0)").attr("name",
								"estimOverHeadDetDto[" + (i) + "].overHeadId");

						$(this).find("input:text:eq(2)")
								.attr(
										"name",
										"estimOverHeadDetDto[" + (i)
												+ "].overheadDesc");
						$(this).find("input:text:eq(3)")
								.attr(
										"name",
										"estimOverHeadDetDto[" + (i)
												+ "].overHeadRate");
						$(this).find("input:text:eq(4)").attr(
								"name",
								"estimOverHeadDetDto[" + (i)
										+ "].overHeadValue");
						$(this).find("input:text:eq(5)")
								.attr(
										"name",
										"estimOverHeadDetDto[" + (i)
												+ "].actualAmount");
						$("#sNo" + i).val(i + 1);
					});
}

$('#overheadsDetails').on(
		"click",
		'.deleteOverHeadsDetails',
		function(e) {

			var errorList = [];
			var count = 0;
			$('.appendableClass').each(function(i) {
				count += 1;
			});
			var rowCount = $('#overheadsDetails tr').length;

			if (rowCount == 3) {
				$("#overHeadCode0").val("");
				$("#overheadDesc0").val("");
				$("#overHeadvalType0").val("");
				$("#overHeadRate0").val("");
				$("#overHeadValue0").val("");
				$("#actualAmount0").val("");

				var overHeadId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');

				if (overHeadId != '') {
					removeOverHeadById.push(overHeadId);
					$('#removeOverHeadById').val(removeOverHeadById);
					$('#totalOverhead').val("0.00")
				}
			}
			if (rowCount != 3) {

				$(this).parent().parent().remove();
				var overHeadId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');
				if (overHeadId != '') {
					removeOverHeadById.push(overHeadId);
				}
			}
			$('#removeOverHeadById').val(removeOverHeadById);
			reOrderOverHeadsDetailsList();

		});

function calculateTotalAmount(index) {
	var overHeadvalType = $("#overHeadvalType" + index).find("option:selected")
			.attr('code');
	var overHeadRate = $("#overHeadRate" + index).val();
	var workEstimateAmt = $("#overheadEstimateAmount").val();
	var totalOverHeadAmount = "";

	if (overHeadvalType != "" && overHeadRate != "" && workEstimateAmt != "") {
		if (overHeadvalType == "PER") {
			totalOverHeadAmount = (Number(workEstimateAmt
					* Number(overHeadRate) / 100)).toFixed(2);
			$("#overHeadValue" + index).val(totalOverHeadAmount);
		} else if (overHeadvalType == "AMT") {
			$("#overHeadValue" + index).val(overHeadRate);
		} else if (overHeadvalType == "MUL") {
			$("#overHeadValue" + index).val(
					(workEstimateAmt * overHeadRate).toFixed(2));
		}
	}

	var grandTotal = 0;
	$(".overheadcalculation").each(function(i) {
		var overHeadValue = parseFloat(parseFloat($(this).val()));
		grandTotal += isNaN(overHeadValue) ? 0 : overHeadValue;
	});
	$("#totalOverhead").val(grandTotal.toFixed(2));

}
// D72888 function to open overhead form
function showConfirmBoxforMbDetail(successMsg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}
// D72888 function to open checklist form
function showConfirmBoxforMbNonSor(successMsg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedforOverheadForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}


function proceedforMbNonSorForm() {

	openMbNonSorForm();
	$.fancybox.close();
}
function proceedforOverheadForm() {

	openAddWorkOverheadsForm();
	$.fancybox.close();
}

function confirmBoxforMbDetail() {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var successMsg = getLocalMessage("mb.MbDetailsSaved");
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedforMbNonSorForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

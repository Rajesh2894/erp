$(document).ready(
		function() {
			$('#datatables').DataTable({
				"oLanguage" : {"sSearch" : ""},
			});

			$(function() {
				$('.datepicker').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
				});
			});
			$(function() {
				$('.datepiker').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate : '-0d',
				});
			});

			if ($("#advanceType").val() != ""
					&& $("#advanceType").val() != undefined
					&& $("#advanceType").val() != "0") {
				getBudgetHeadOnAdvanceType();
				$("#pacHeadId").val($("#hiddenHeadId").val());
				$("#vendorName").val($("#hiddenVenderId").val());
				getWorkOrderDetails();
				$("#referenceNo").val($("#hiddenReferenceNo").val());
				getContrcatAmount();
			}
		});

function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {
	$(document).on('click', '.createData', function() {

		var url = "AdvanceRequisition.html?AdvanceRequisitionForm";
		var ajaxResponse = __doAjaxRequest(url, 'post', {}, false);

		var divName = formDivName;
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	});
});

function saveRequisitionForm(obj) {
	var errorList = [];

	if ($("#entryDate").val() == "")
		errorList.push(getLocalMessage("advance.requisition.advdate"));

	if ($("#deptId").val() == "" || $("#deptId").val() == "0")
		errorList.push(getLocalMessage("advance.requisition.deptId"));

	if ($("#advanceType").val() == "" || $("#advanceType").val() == "0")
		errorList.push(getLocalMessage("advance.requisition.advanceType"));

	if ($("#sliStatus").val() == "L"
			&& ($("#pacHeadId").val() == "0" || $("#pacHeadId").val() == ""))
		errorList.push(getLocalMessage("advance.requisition.advancehead"));

	if ($("#vendorName").val() == "0" || $("#vendorName").val() == "")
		errorList.push(getLocalMessage("advance.requisition.vendorName"));

	if ($("#referenceNo").val() == "0" || $("#referenceNo").val() == "")
		errorList.push(getLocalMessage("advance.requisition.referenceNo"));

	if ($("#advanceAmount").val() == "0" || $("#advanceAmount").val() == "")
		errorList.push(getLocalMessage("advance.requisition.advanceAmount"));

	if ($("#particulars").val() == "")
		errorList.push(getLocalMessage("advance.requisition.particulars"));

	if (errorList.length > 0) {
		showErr(errorList);
		$("#errorDiv").show();
		return false;
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(obj,
				getLocalMessage("advance.requisition.save"),
				'AdvanceRequisition.html', 'saveform');
	}
}

function closeErrBox() {
	$('.error-div').hide();
}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

// for searching
function searchAdvanceRequitionList() {

	$('.error-div').hide();

	var errorList = [];

	var advanceEntryDate = $("#advanceEntryDate").val();
	var vendorId = $("#vendorId").val();
	var deptId = $("#deptId").val();

	if ((advanceEntryDate == "" || advanceEntryDate == null)
			&& (vendorId == "" || vendorId == "0")
			&& (deptId == "" || deptId == "0")) {
		errorList.push(getLocalMessage("advance.requisition.serachvalidation"));
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	if (errorList.length == 0) {
		var url = "AdvanceRequisition.html?searchRequisition";
		var requestData = {
			"advanceEntryDate" : advanceEntryDate,
			"vendorId" : vendorId,
			"deptId" : deptId,
		};

		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'json');

		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var result = [];
		if (ajaxResponse.length != 0) {
		$
				.each(
						ajaxResponse,
						function(index) {
							var obj = ajaxResponse[index];

							if (obj.advStatus != "Draft") {
								result
										.push([
												obj.advNo,
												obj.advStatus,
												obj.advDateStr,
												'<div style="display: flex; justify-content: flex-end"> <div>'+obj.advAmount+'</div></div>',
												'<td >'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getAdvanceRequistion(\''
														+ obj.advId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ '</td>' ]);
							} else {
								result
										.push([
												obj.advNo,
												obj.advStatus,
												obj.advDateStr,
												'<div style="display: flex; justify-content: flex-end"> <div>'+obj.advAmount+'</div></div>',
												'<td >'
														+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getAdvanceRequistion(\''
														+ obj.advId
														+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
														+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="getAdvanceRequistion(\''
														+ obj.advId
														+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
														+ '<button type="button" class="btn btn-green-3 btn-sm btn-sm margin-right-10" onclick="sendForApproval(\''
														+ obj.advId
														+ '\',\'S\')"  title="Send For Approval"><i class="fa fa-share-square-o"></i></button>'
														+ '</td>' ]);
							}
						});
		table.rows.add(result);
		table.draw();
		}
		else{
			errorList.push(getLocalMessage('ar.advance.requition.no.RecordsFound'));
			displayErrorsOnPage(errorList);
		}

	}
}

function getWorkOrderDetails() {

	var deptCode = $("#deptId").find("option:selected").attr('code');
	var vendorId = $("#vendorName").val();
	var referenceNo = $("#referenceNo").val();

	if (deptCode.trim() != "" && deptCode != undefined && vendorId != ""
			&& vendorId != 0 && vendorId != "0") {
		$('#referenceNo option').remove();
		var requestData = '&deptCode=' + deptCode + '&vendorId=' + vendorId
				+ '&referenceNumber=' + referenceNo;
		var ajaxResponse = doAjaxLoading(
				'AdvanceRequisition.html?getWorkOrderDetails', requestData,
				'json');
		$('#referenceNo').append(
				$("<option></option>").attr("value", "").text("select"));
		$.each(ajaxResponse, function(key, value) {
			$('#referenceNo').append(
					$("<option></option>").attr("value", value.referenceNo)
							.attr("code", value.referenceAmount).text(
									value.referenceNo));
		});

		$("#referenceNo").val(ajaxResponse.referenceNumber).trigger(
				'chosen:updated');
	}
}

function getAdvanceRequistion(advId, flag) {

	var actionData;
	var divName = formDivName;
	var requestData = '&advId=' + advId + '&flag=' + flag;
	if (flag == "E" || flag == "V") {
		var ajaxResponse = doAjaxLoading('AdvanceRequisition.html?'
				+ "ActionRequisition", requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function sendForApproval(advId, flag) {
	var actionData;
	var divName = formDivName;
	var requestData = '&advId=' + advId + '&flag=' + flag;
	var ajaxResponse = doAjaxLoading('AdvanceRequisition.html?'
			+ "sendForApproval", requestData, 'html');
	searchAdvanceRequitionList();
}

function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdvanceRequisition.html');
	$("#postMethodForm").submit();
}

function getBudgetHeadOnAdvanceType() {
	$('.error-div').hide();
	$('#pacHeadId option').remove();
	$("#pacHeadId").trigger("chosen:updated");
	$('#vendorName option').remove();
	$("#vendorName").trigger("chosen:updated");

	var accountType = $("#advanceType").val();
	var accountTypeCode = $("#advanceType").find("option:selected")
			.attr('code');

	var requestData = {
		"acountSubType" : accountType
	};

	var requestDataVendorType = '&accountTypeCode=' + accountTypeCode
			+ '&deptId=' + $("#deptId").val();

	var url = "AdvanceRequisition.html?getBudgetHeadCodeDesc";
	var urlAdv = "AdvanceRequisition.html?getEmployeType";
	$('#pacHeadId').append(
			$("<option></option>").attr("value", "").text("select"));
	$('#vendorName').append(
			$("<option></option>").attr("value", "").text("select"));
	var response = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	$.each(response, function(key, value) {
		$('#pacHeadId').append(
				$("<option></option>").attr("value", key).attr("code", value)
						.text(value));
	});

	$("#pacHeadId").val(response).trigger('chosen:updated');

	// for preparing vender type or employee type based on advance type
	var responseEmpType = __doAjaxRequest(urlAdv, 'POST',
			requestDataVendorType, false, 'json');
	$.each(responseEmpType, function(key, value) {
		$('#vendorName').append(
				$("<option></option>").attr("value", key).attr("code", value)
						.text(value));
	});

	$("#vendorName").val(responseEmpType).trigger('chosen:updated');

}

function getContrcatAmount() {
	var requestData = '&referenceNumber=' + $("#referenceNo").val();
	var ajaxResponse = doAjaxLoading(
			'AdvanceRequisition.html?getTotalUsedAmount', requestData, 'json');
	$("#totalAmount").val(
			$("#referenceNo").find("option:selected").attr('code'));
	$("#remainingAmt").val($("#totalAmount").val() - ajaxResponse);
}

function showConfirmBoxForApproval(approvalData) {
	var errorList = [];
	element = approvalData;

	if ($('input[id$="decision"]:checked').length === 0) {
		errorList.push(getLocalMessage("advance.requisition.decision"));

	}
	if ($("#comments").val() == "") {
		errorList.push(getLocalMessage("advance.requisition.remark"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage("advance.requisition.save"), 'AdminHome.html',
				'saveform');
	}
}

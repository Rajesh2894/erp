$(function() {
	// start
	var srno = 1;
	// User Story #85006 start
	$("#rtiEmpIncreTable").on('click', '.addEMP', function() {
		var errorList = [];
		errorList = validatePioResponseDeptEmpTable(errorList);
		if (errorList.length == 0) {
			var content = $("#rtiEmpIncreTable").find('tr:eq(1)').clone();
			srno = srno + 1;
			$("#rtiEmpIncreTable").append(content);
			content.find("input:text").val(srno);
			content.find("select:eq(0)").val('0');
			content.find("select:eq(1)").val('0');
			content.find("textarea").val('');
			reOrderRtiEmpTableSequence('.empRequest');
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}

	});

	$("#rtiEmpIncreTable").on('click', '.remvCF', function() {

		if ($("#rtiEmpIncreTable tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderRtiEmpTableSequence('.empRequest');
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("rti.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});

	function reOrderRtiEmpTableSequence(empRequest) {
		$(empRequest).each(
				function(i) {
					++i;
					$(this).find("input:text:eq(0)").attr("id", "unitNO" + i);
					$(this).find("select:eq(0)").attr("id", "rtiDeptId" + i);
					$(this).find("select:eq(1)").attr("id", "empname" + i);
					$(this).find("textarea:eq(0)").attr("id", "remList" + i);

					// path binding
					$(this).find("input:text:eq(0)").attr("name",
							"reqDTO.empSerNo" + i);
					$(this).find("select:eq(0)").attr("name",
							"reqDTO.deptIdList[" + i + "]")
					$(this).find("select:eq(1)").attr("name",
							"reqDTO.empList[" + i + "]");
					$(this).find("textarea:eq(0)").attr("name",
							"reqDTO.remList[" + i + "]");
					// to increment serial number
					$("#unitNO" + i).val(i);

				});

	}

	// User Story #85006 ends

	/* To add new Row into table */
	$("#attachDoc").on('click', '.addCF', function() {

		var errorList = [];
		errorList = validatePioResponseTable(errorList);
		if (errorList.length == 0) {
			var content = $("#attachDoc").find('tr:eq(1)').clone();
			$("#attachDoc").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			reOrderRtiRequestTableSequence('.appendableClass');
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}

	});

	/* To delete Row From the table */
	$("#attachDoc").on('click', '.delButton', function() {

		if ($("#attachDoc tr").length != 2) {
			$(deleteRow).parent().parent().remove();
			/* $(deleteRow).closest('tr').remove(); */
			reOrderRtiRequestTableSequence('.appendableClass');
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("rti.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});

	$("#rtiIncreTable").on('click', '.addCF', function() {

		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0) {
			var content = $("#rtiIncreTable").find('tr:eq(1)').clone();
			$("#rtiIncreTable").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			reOrderRtiRemarkTableSequence('.rtiRequestt');
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}

	});

	$("#rtiIncreTable").on('click', '.remCF', function() {

		if ($("#rtiIncreTable tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderRtiRemarkTableSequence('.rtiRequestt');
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("rti.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});

});

function reOrderRtiRequestTableSequence() {

	$(appendableClass).each(
			function(i) {

				// id binding
				$(this).find("input:text:eq(0)")
						.attr("id", "mediaSerialNo" + i);
				$(this).find("select:eq(0)").attr("id", "mediaType" + i);
				$(this).find("input:text:eq(1)").attr("id", "quantity" + i);
				$(this).find("input:text:eq(2)").attr("id", "mediaDesc" + i);

				// path binding
				$(this).find("input:text:eq(0)").attr("name",
						"rtiMediaListDTO.mediaSerialNo" + i);
				$(this).find("select:eq(0)").attr("name",
						"rtiMediaListDTO[" + i + "].mediaType");
				$(this).find("input:text:eq(1)").attr("name",
						"rtiMediaListDTO[" + i + "].quantity");
				$(this).find("input:text:eq(2)").attr("name",
						"rtiMediaListDTO[" + i + "].mediaDesc");

				// to increment serial number
				var incr = i + 1;
				$("#unitNo" + i).val(incr);

			});
}

function reOrderRtiRemarkTableSequence(rtiRequestt) {

	$(rtiRequestt).each(function(i) {

		// id binding
		$(this).find("input:text:eq(0)").attr("id", "unitNo" + i);
		$(this).find("input:text:eq(2)").attr("id", "remark" + i);

		// path binding
		$(this).find("input:text:eq(0)").attr("name", "reqDTO.srNo" + i);
		$(this).find("input:text:eq(1)").attr("name", "reqDTO.rtiRemarks" + i);

		// to increment serial number
		var incr = i + 1;
		$("#unitNo" + i).val(incr);

	});

}
// User Story #85006
function checkDuplicateEmployeeName(obj) {
	var errorList = [];
	var theForm = '#rtiPioForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'PioResponse.html?checkDuplicateEmpName';
	var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	var prePopulate = JSON.parse(response);
	if (prePopulate == true) {
		errorList.push(getLocalMessage("rti.duplicate .employee.choosen"));
	}
	return errorList;
}

function getEmployeeName(obj) {
	let serNo = $(".empRequest").length;
	var requestData = {
		"deptId" : $(obj).val()
	};
	$('#empname' + serNo).empty().append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = __doAjaxRequest('PioResponse.html?getEmpName', 'POST',
			requestData, false, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		if (value[1] == null) {
			value[1] = '';
		}
		if (value[2] == null) {
			value[2] = '';
		}
		if (value[3] == null) {
			value[3] = '';
		}
		$('#empname' + serNo).append(
				$("<option></option>").attr("value", value[0]).text(
						(value[1] + " " + value[2] + " " + value[3])));
	});
}

function deleteRow(btn) {

	var errorList = [];
	if ($("#attachDoc tr").length != 2) {
		var row = btn.parentNode.parentNode;
		row.parentNode.removeChild(row);
	} else {
		errorList.push(getLocalMessage("rti.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
}

$(document)
		.ready(
				function() {

					/* Defect #31606 */
					$("#rtiAction").val('0');
					$("#partialInfoFlag").val('0');
					$("#loiApplicable").val('0');

					$("#rtiAction").change(
							function(e) {

								if ($('#rtiAction option:selected')
										.attr('code') == "A")

								{
									$("#approved").show();
									$("#forward").hide();
									$("#forwardtodept").hide();
									$('#forward').children().find(
											'input,textarea').each(function() {
										$(this).val('');
									});
									$("#othForwardPioModNo").val('0');
									$("#uploadTagDiv").show();
									$("#reasonForLoiNa").hide();
									$("#fullpartial").show();
									$("#fullpartial").val('0');
									$("#remarkfields").hide();
									$("#forwardLoc").hide();
									$('.Locationwiseradio').hide();
									$("#WardZone").hide();
									getRemarkDetailsByAction();
									// $("#quantity").val('');

								}

								else if ($('#rtiAction option:selected').attr(
										'code') == "R") {
									$("#uploadTagDiv").hide();
									$("#approved").hide();
									$("#forward").hide();
									$("#forwardtodept").hide();
									$("#rejectremark").show();
									$("#fullpartial").hide();
									$('#forward').children().find(
											'input,textarea').each(function() {
										$(this).val('');
									});
									$('#approved').children().find(
											'input,textarea').each(function() {
										$(this).val('');
									});
									$("#othForwardPioModNo").val('0');
									$("#loiApplicable").val('0');
									$("#quantity").val('');
									$("#mediaDesc").val('');
									$("#remarkfields").show();
									$("#forwardLoc").hide();
									$('.Locationwiseradio').hide();
									$("#WardZone").hide();

								} else if ($('#rtiAction option:selected')
										.attr('code') == "FO") {
									$("#uploadTagDiv").hide();
									$("#forwardtodept").hide();
									$("#approved").hide();
									$('#approved').children().find(
											'input,textarea').each(function() {
										$(this).val('');
									});
									$("#loiApplicable").val('0');
									$("#quantity").val('');
									$("#mediaDesc").val('');
									$("#forward").show();
									$("#WardZone").show();
									$("#fullpartial").hide();
									$("#remarkfields").hide();
									$("#forwardLoc").hide();
									$('.Locationwiseradio').hide();

								} else if ($('#rtiAction option:selected')
										.attr('code') == "FDE") {

									$("#forwardtodept").show();
									$("#uploadTagDiv").hide();
									$("#approved").hide();
									$("#rtiDeptId").val('0');
									$('#approved').children().find(
											'input,textarea').each(function() {
										$(this).val('');
									});
									$("#loiApplicable").val('0');
									$("#quantity").val('');
									$("#mediaDesc").val('');
									$("#forward").show();
									$("#fullpartial").hide();
									$("#forward").hide();
									$("#remarkfields").hide();
									$("#forwardLoc").hide();
									$('.Locationwiseradio').hide();
									$("#WardZone").hide();
								} else if ($('#rtiAction option:selected')
										.attr('code') == "FDL") {
									$("#forwardLoc").show()
									$("#forwardtodept").hide();
									$("#uploadTagDiv").hide();
									$("#approved").hide();
									$("#rtiDeptId").val('0');
									$('#approved').children().find(
											'input,textarea').each(function() {
										$(this).val('');
									});
									$("#loiApplicable").val('0');
									$("#quantity").val('');
									$("#mediaDesc").val('');
									$("#forward").show();
									$("#fullpartial").hide();
									$("#forward").hide();
									$("#remarkfields").hide();
									$("#WardZone").hide();
								}
							});

					$("#loiApplicable").change(
							function(e) {
								if ($('#loiApplicable option:selected').attr(
										'code') == "LNA") {
									$("#reasonForLoiNa").show();
									$("#uploadTagDiv").hide();

								} else if ($('#loiApplicable option:selected')
										.attr('code') == "LA") {
									$("#reasonForLoiNa").hide();
									$("#uploadTagDiv").show();
								}
							});

					$("#partialInfoFlag")
							.change(

									function(e) {

										if ($(
												'#partialInfoFlag option:selected')
												.attr('code') == "F") {
											$("#rejectremark").hide();
											$("#remarkfields").hide();
											$("#approvalremark").hide();
										} else if ($(
												'#partialInfoFlag option:selected')
												.attr('code') == "P") {
											$("#rejectremark").show();
											$("#approvalremark").show();
											$("#remarkfields").show();
										}

									});

					/*
					 * $('#rtiPioForm').validate({ onkeyup: function(element) {
					 * this.element(element); console.log('onkeyup fired'); },
					 * onfocusout: function(element) { this.element(element);
					 * console.log('onfocusout fired'); } });
					 */

					fileCountUpload(this);

					$(function() {
						$('#rtiDeptidFdate').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
						// minDate: 0
						});
					});
					$('#fdlDeptId').change(
							function() {
								var requestData = "deptId="
										+ $('#fdlDeptId').val();
								var response = __doAjaxRequest(
										"PioResponse.html?isWardZoneRequired",
										'post', requestData, false, 'html');
								var isWardZoneRequired = (response == 'true');
								if (isWardZoneRequired) {
									enableWardZoneSelection();
								} else {
									$(".wardZoneBlockDiv").hide();
								}

							});
					$('#frdOrgId')
							.change(
									function() {
										var errorList = [];

										var requestData = "frdOrgId="
												+ $('#frdOrgId').val();
										var response1 = __doAjaxRequest(
												"PioResponse.html?checkWorkflow",
												'post', requestData, false,
												'html');
										var isworkFlowexist = (response1 == 'true');
										if (isworkFlowexist) {
											errorList
													.push(getLocalMessage("rti.workflow.error"));
										}
										if (!isworkFlowexist) {
											var response = __doAjaxRequest(
													"PioResponse.html?isWardZoneRequiredByOrgId",
													'post', requestData, false,
													'html');
											var isWardZoneRequired = (response == 'true');
											if (isWardZoneRequired) {
												$("#WardZone").show();
												getWard();
											} else {
												$("#WardZone").hide();
											}
										} else {
											displayErrorsOnPage(errorList);
										}

									});
					$('#pioWard').change(function() {

						getZone();
					});

				});

function resetPioForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#rtiPioForm").validate().resetForm();
}

function fileCountUpload(element) {
	var errorList = [];
	errorList = validatePioResponseTable(errorList);
	if (errorList.length == 0) {
		var row = $("#attachDoc tbody .appendableClass").length;
		$("#length").val(row);
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('PioResponse.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$("#uploadTagDiv").html(response);
		// prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function submitPioResponse(element) {
debugger;
	var errorList = [];
	var status;
	// errorList = validatePioResponseTable(errorList);
	errorList = validatePioResponse(errorList);
	if (errorList.length == 0) {
		clearField();

		if ($('#rtiAction option:selected').attr('code') == "FO") {
			// D#34043
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = {};
			requestData = __serializeForm(theForm);

			var ajaxResponse = __doAjaxRequest(
					'PioResponse.html?saveAndMediaCharge', 'POST', requestData,
					false, '', element);

			if ($.isPlainObject(ajaxResponse)) {
				displayMessageOnSubmit(getLocalMessage("rti.pio.submit"));
			}
		} else if ($('#rtiAction option:selected').attr('code') == "R") {
			return saveOrUpdateForm(element, getLocalMessage("rti.pio.submit"),
					"PioResponse.html?printRejectReport", 'saveAndMediaCharge');

		} else if (($('#rtiAction option:selected').attr('code') == "A")
				&& ($('#loiApplicable option:selected').attr('code') == "LA")) {
			// return saveOrUpdateForm(element, "Response Submitted
			// Successfully", "PioResponse.html?printLOILetter",
			// 'saveEditableLOI');
			errorList = validatePioResponseTable(errorList);
			if (errorList.length == 0) {
				return saveOrUpdateForm(element,
						getLocalMessage("rti.pio.submit"),
						"PioResponse.html?printLOILetter", 'saveAndMediaCharge');
			} else {
				displayErrorsOnPage(errorList);
			}

		} else {
			// User Story #85006
			var errorList = [];
			errorList = checkDuplicateEmployeeName(element);
			if (errorList.length == 0) {
				status= saveOrUpdateForm(element,
						getLocalMessage("rti.pio.submit"), "AdminHome.html",
						'saveAndMediaCharge');
				if($('#loiApplicable option:selected').attr('code') != "LNA"&&$('#rtiAction option:selected').attr('code')!='FDL'){
				openDispatchReportWindowTab(status);
				}
			} else {
				displayErrorsOnPage(errorList);
			}
		}

	} else {
		displayErrorsOnPage(errorList);
	}
}

function displayMessageOnSubmit(message) {
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="printFormEReport()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function printFormEReport() {
	var url = "PioResponse.html?PrintFormEReport";
	var ajaxResponse = __doAjaxRequest(url, 'post', '', false);

	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/* #31536,#31538 */

function editableToLetter(element) {

	var errorList = [];
	errorList = validateeditableLoiTable(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, "submitted",
				"PioResponse.html?printLOILetter", 'printLOILetter');
	} else {
		displayErrorsOnPage(errorList);
	}
	/*
	 * var formName = findClosestElementId(element, 'form'); var theForm = '#' +
	 * formName; var requestData = __serializeForm(theForm); var response =
	 * __doAjaxRequest('PioResponse.html?printLOILetter', 'POST', requestData,
	 * false, 'html');
	 */

	// $("#letter").html(response);
}

/* #31536,#31538 */
function validateeditableLoiTable(errorList) {

	var rowCount = $('#itemDetails tr').length;

	for (let i = 0; i < rowCount - 1; i++) {
		let editAmountType = "#chargeAmountList" + i + "\\.editedAmount";

		let editAmountType1 = $(editAmountType).val();

		if (editAmountType1 == "0" || editAmountType1 == undefined
				|| editAmountType1 == '') {
			errorList.push(getLocalMessage('rti.enter.amount.edit.row'
					+ (i + 1)));
		}
	}
	return errorList;
}

function clearField() {

}

// Validate Pio Response Table
function validatePioResponseTable(errorList) {

	$(".appendableClass").each(
			function(i) {

				var mediaType = $("#mediaType" + i).val();
				var quantity = $("#quantity" + i).val();
				var description = $("#mediaDesc" + i).val();

				var constant = i + 1;

				if (mediaType == '0' || mediaType == undefined
						|| mediaType == "") {
					errorList.push(getLocalMessage("rti.validation.MediaType")
							+ " " + constant);
				}
				if (quantity == "" || quantity == undefined) {
					errorList.push(getLocalMessage("rti.validation.Quantity")
							+ " " + constant);
				}
				if (description == "" || description == undefined) {
					errorList
							.push(getLocalMessage("rti.validation.Description")
									+ " " + constant);
				}

			});
	return errorList;
}

// Validate Pio Response Department-Employee Table
function validatePioResponseDeptEmpTable(errorList) {
	debugger;
	let serNo = $(".empRequest").length;
	// var deptName = $("#rtiDeptId").val();
	var empName = $("#empname1").val();

	/*
	 * if (deptName == "0" || deptName == undefined || deptName == "") {
	 * errorList.push(getLocalMessage("rti.validation.dept")); }
	 */
	if (empName == "0" || empName == undefined || empName == "") {
		errorList.push(getLocalMessage("rti.select.dept.emp"));
	}
	if (serNo > 1) {
		$(".appendableClass").each(function(i) {
			debugger;
			var rtiDeptId = $("#rtiDeptId" + serNo).val();
			var empname = $("#empname" + serNo).val();

			var constant = i + 1;

			if (rtiDeptId == '0' || rtiDeptId == undefined || rtiDeptId == "") {
				errorList.push(getLocalMessage("rti.validation.rtiDeptId"));
			}
			if (empname == "0" || empname == "" || empname == undefined) {
				errorList.push(getLocalMessage("rti.validation.empname"));
			}

		});
	}
	return errorList;
}

function validatePioResponse(errorList) {
	debugger;
	var action = $("#rtiAction").val();
	var fp = $("#partialInfoFlag").val();
	var loi = $("#loiApplicable").val();
	var remark = $("#remark").val();
	let serNo = $(".empRequest").length;
	/*
	 * var media = $("#mediaType").val(); var quantity = $("#quantity").val();
	 * var description = $("#info").val();
	 */
	var authorityName = $("#inwAuthorityName").val();
	var deptAndOffice = $("#othForwardDeptName").val();
	var deptName = $("#rtiDeptId").val();
	var empName = $('#empname' + serNo).val();
/* var otherRemarks = $('#otherreamrk').val(); */
	var otherRemarksFwd = $('#otherRemarks').val();
	var frdOrgId = $('#frdOrgId').val();

	var forwardDate = $("#rtiDeptidFdate").val();
	var fdlDeptId = $("#fdlDeptId").val();
	var pioWard = $("#pioWard").val();
	var pioZone = $("#pioZone").val();
	var codIdOperLevel1 = $("#codIdOperLevel1").val();
	var codIdOperLevel2 = $("#codIdOperLevel2").val();

	if (action == "0" || action == undefined || action == "") {
		errorList.push(getLocalMessage("rti.validation.Action"));
	} else if ($('#rtiAction option:selected').attr('code') == "A"
			&& (loi == "0" || loi == undefined || loi == "")) {
		errorList.push(getLocalMessage("rti.validation.loi"));
	}
	if ($('#rtiAction option:selected').attr('code') == "A"
			&& (fp == "0" || fp == undefined || fp == "")) {
		errorList.push(getLocalMessage("rti.validation.FullPartial"));
	}
	if ($('#rtiAction option:selected').attr('code') == "A"
			&& $('#partialInfoFlag option:selected').attr('code') == "P") {
		if (remark == "" || remark == undefined || remark == "0") {
			errorList.push(getLocalMessage("rti.validation.remarkDetails"));
		}
	}
	if ($('#loiApplicable option:selected').attr('code') == "LA"
			&& $('#rtiAction option:selected').attr('code') == "A") {
	} else if ($('#loiApplicable option:selected').attr('code') == "LNA"
			&& $('#rtiAction option:selected').attr('code') == "A") {
		if ($("#reasonForSkipLOI").val() == ""
				|| $("#reasonForSkipLOI").val() == undefined) {

			errorList.push(getLocalMessage("rti.validation.reasonOfSkip"));
		}
	}
	if ($('#rtiAction option:selected').attr('code') == "FO") {
		if ((frdOrgId == "0" || frdOrgId == undefined || frdOrgId == "")) {
			errorList.push(getLocalMessage("rti.fwd.org"));
		}
		if ((otherRemarksFwd == "0" || otherRemarksFwd == undefined || otherRemarksFwd == "")) {
			errorList.push(getLocalMessage("rti.validation.otherRemarks"));
		}
		if ((frdOrgId != "0" && frdOrgId != undefined && frdOrgId != "")) {
			var requestData = "frdOrgId=" + $('#frdOrgId').val();
			var response = __doAjaxRequest(
					"PioResponse.html?isWardZoneRequiredByOrgId", 'post',
					requestData, false, 'html');
			var isWardZoneRequired = (response == 'true');
			if (isWardZoneRequired) {
				if ((pioWard == "0" || pioWard == undefined || pioWard == "")) {
					errorList.push(getLocalMessage("rti.fwd.zone"));
				}
				if ((pioZone == "0" || pioZone == undefined || pioZone == "")) {
					errorList.push(getLocalMessage("rti.fwd.ward"));
				}
			}
			if ((pioZone != "0" && pioZone != undefined && pioZone != "")
					&& (pioWard != "0" && pioWard != undefined && pioWard != "")) {
				var requestData = {
					"frdOrgId" : frdOrgId,
					"trdWard1" : pioWard,
					"trdWard2" : pioZone

				};
				var response = __doAjaxRequest(
						"PioResponse.html?checkWorkflowByWardZone", 'post',
						requestData, false, 'html');
				var istrue = (response == 'true');
				if (istrue) {
					errorList.push(getLocalMessage("rti.fwd.ward.zone.notexist"));
				}
			}

		}

	}

	/*
	 * if ($('#rtiAction option:selected').attr('code') == "FDE") { if
	 * ((otherRemarks == "0" || otherRemarks == undefined || otherRemarks ==
	 * "")) { errorList.push(getLocalMessage("rti.validation.otherRemarks")); } }
	 */

	if ($('#rtiAction option:selected').attr('code') == "FDE") {
		if ((empName == "0" || empName == undefined || empName == "")) {
			errorList.push(getLocalMessage("rti.select.dept.emp"));
		}
	}
	if ($('#rtiAction option:selected').attr('code') == "FDE") {
		if (forwardDate == "0" || forwardDate == undefined || forwardDate == "") {
			errorList.push(getLocalMessage("rti.validation.forwardedDate"));
		}
	}
	if ($('#rtiAction option:selected').attr('code') == "FDL") {
		if (fdlDeptId == "0" || fdlDeptId == undefined || fdlDeptId == "") {
			errorList.push(getLocalMessage("rti.validation.forwardedLocation"));
		}
		if (fdlDeptId != "0" || fdlDeptId != undefined || fdlDeptId != "") {
			var requestData = "deptId=" + $('#fdlDeptId').val();
			var response = __doAjaxRequest(
					"PioResponse.html?isWardZoneRequired", 'post', requestData,
					false, 'html');
			var isWardZoneRequired = (response == 'true');
			if (isWardZoneRequired) {
				if ((codIdOperLevel1 == "0" || codIdOperLevel1 == undefined || codIdOperLevel1 == "")) {
					errorList.push(getLocalMessage("rti.fwd.ward"));
				}
				if ((codIdOperLevel2 == "0" || codIdOperLevel2 == undefined || codIdOperLevel2 == "")) {
					errorList.push(getLocalMessage("rti.fwd.zone"));
				}
			}
			if ((codIdOperLevel2 != "0" && codIdOperLevel2 != undefined && codIdOperLevel2 != "")
					&& (codIdOperLevel1 != "0" && codIdOperLevel1 != undefined && codIdOperLevel1 != "")) {
				var requestData = {
					"frdOrgId" : 0,
					"trdWard1" : codIdOperLevel1,
					"trdWard2" : codIdOperLevel2

				};
				var response = __doAjaxRequest(
						"PioResponse.html?checkWorkflowByWardZone", 'post',
						requestData, false, 'html');
				var istrue = (response == 'true');
				if (istrue) {
					errorList.push(getLocalMessage("rti.fwd.ward.zone.notexist"));
				}
			}
		}
	}

	return errorList;
}

function backPage() {
	window.location.href = getLocalMessage("rti.adminHome");
}

/*
 * function closePage(){ window.location.href=getLocalMessage("rti.adminHome"); }
 */

function getRemarkDetailsByAction(element) {

	var rtiAction = $('#rtiAction option:selected').attr('value');
	var requestData = {
		"rtiAction" : $('#rtiAction option:selected').attr('value')
	}
	var URL = 'PioResponse.html?remarkDetailsByAction';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	$('#remark').html('');
	$('#remark').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	$.each(returnData, function(index, value) {
		$('#remark').append(
				$("<option></option>").attr("value", value.artId).text(
						(value.artRemarks)));
	});
	$('#remark').trigger("chosen:updated");
}
/*
 * function otherTask() { return false; }
 */

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function printdiv(printpage) {
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
function enableWardZoneSelection() {

	$('.Locationwiseradio').show();
	var errorList = [];
	var deptId = $('#fdlDeptId').val();

	if (deptId == '') {
		errorList.push(getLocalMessage("care.error.department"));
	}
	if (errorList.length == 0) {

		var requestData = 'deptId=' + $('#fdlDeptId').val();
		var response = __doAjaxRequest("PioResponse.html?areaMapping", 'post',
				requestData, false, 'html');
		$('#areaMappingId').html(response);

		if ($('#prefixHidden').val() === null
				|| $('#prefixHidden').val() === '') {
			$('.Locationwiseradio').hide();
			showErrormsgboxTitle(getLocalMessage("care.error.warzone"));
			$('.WardZoneAll').removeAttr("checked", false);
		}
	} else {
		displayErrorsOnPage(errorList);
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

}
function getWard() {
	debugger;
	var errorList = [];
	var requestData = "frdOrgId=" + $('#frdOrgId').val();
	var URL = 'PioResponse.html?getWard';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#pioWard').html('');
	$('#pioWard').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));

	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#pioWard').append(
				$("<option></option>").attr("value", value.codId).text(
						(value.codDesc)));
	});
	$('#pioWard').trigger("chosen:updated");
}

function getZone() {

	var errorList = [];
	var pioWard = $('#pioWard').val();
	var frdOrgId = $('#frdOrgId').val();
	var requestData = {
		"pioWard" : pioWard,
		"frdOrgId" : frdOrgId

	};
	var URL = 'PioResponse.html?getZone';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#pioZone').html('');
	$('#pioZone').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));

	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#pioZone').append(
				$("<option></option>").attr("value", value.codId).text(
						(value.codDesc)));
	});
	$('#pioZone').trigger("chosen:updated");
}
function openDispatchReportWindowTab(status) {
	if (!status) {
		var URL = 'PioResponse.html?employeeInfoReport';
		var returnData = __doAjaxRequest(URL, 'POST', {}, false);

		var title = 'Applicant Information';
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
		// printWindow.document
		// .write('<div style="position:fixed; width:100%; bottom:0px;
		// z-index:1111;"><div class="text-center"><button
		// onclick="window.print();" class="btn btn-success hidden-print"
		// type="button"><i class="fa fa-print" aria-hidden="true"></i>
		// Print</button> <button onClick="window.close();" type="button"
		// class="btn btn-blue-2 hidden-print">Close</button></div></div>')
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		// printWindow.document.close();

	}
}

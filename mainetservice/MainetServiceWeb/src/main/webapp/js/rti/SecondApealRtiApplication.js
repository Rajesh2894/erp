$(document).ready(function() {

	$('#datatables').DataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bPaginate" : true,
		"bFilter" : true
	});

	$(function() {
		$('#rtiDeciRecDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		// minDate: 0
		});
	});

	$(function() {
		$('#rtiPioActionDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		// minDate: 0,
		});
	});

});

function viewRtiForm(applicationId) {
	var requestData = "applicationId=" + applicationId;
	var url = "SecondApealRtiApplication.html?view";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);

	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// $("#RtiSubject").val('');
	var appl = $("#appealTypeId").val();
	if (appl == 'F') {
		getLocationByDeptName()
	}

}

function saveSecondAppealForm(element) {

	var errorList = [];
	errorList = validateRtiForm(errorList);
	// errorList = actionDate(errorList);
	/*
	 * if (errorList.length == 0) { return saveOrUpdateForm(element, "Your
	 * application for RTI saved successfully!",
	 * 'SecondApealRtiApplication.html?proceed', 'save'); }
	 */
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				"Your application for RTI saved successfully!",
				'AdminHome.html', 'save');
	} else {
		displayErrorsOnPage(errorList);
	}

}

var formDivName = '.content-page';
function valueChanged() {

	if ($('.addr').is(":checked")) {
		$("#address").hide();
		$("#rtiAddress").val(' ');
	} else
		$("#address").show();
}

function resetRtiForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#secondrtiForm").validate().resetForm();

}

function getLocationByDeptName() {
	var errorList = [];
	var requestData = {
		"deptId" : $('#rtiDeptId option:selected').attr('value')
	}
	var URL = 'SecondApealRtiApplication.html?getLocationByDepartment';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#rtiLocationId').html('');
	$('#rtiLocationId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));

	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#rtiLocationId').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#rtiLocationId').trigger("chosen:updated");
}

$(document)
		.ready(
				function() {
					var isValidationError = $("#isValidationError").val();

					$("#resetform").on("click", function() {
						window.location.reload("#secondrtiForm")
					});

					$("#isBPL")
							.change(
									function(e) {

										if ($('#isBPL option:selected').attr(
												'code') == "Y") {
											$("#bplshow").show();
											$("#bplfields").show();
											$("#paymentDetails").hide();
											$("#rtiDeptId").val('0');

										} else if ($('#isBPL option:selected')
												.attr('code') == "N") {
											$("#bplshow").hide();
											$("#bplfields").hide();
											$("#rtiDeptId").val('0');

										}
									});

					$("#applReferenceMode")
							.change(
									function(e) {
										if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "E") {
											$("#E").show();
											$("#stamp").hide();
											$("#stampNo").val('');
											$("#paymentDetails").hide();
											// $("#stampAmt").val('');
											$("#custDate").data(
													'rule-required', true);
											$("#inwAuthorityName").data(
													'rule-required', true);
											$("#inwAuthorityDept").data(
													'rule-required', true);
											$("#inwAuthorityAddress").data(
													'rule-required', true);
											$("#inwReferenceNo").data(
													'rule-required', true);

										} else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "S") {
											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stamp").show();
											$("#stampNo").data('rule-required',
													true);
											$("#stampAmt").data(
													'rule-required', true);
											$("#paymentDetails").hide();
											$("#stampAmt").val('');

										} else if (($(
												'#applReferenceMode option:selected')
												.attr('code') == "D")
												|| ($(
														'#applReferenceMode option:selected')
														.attr('value') == "0")) {
											$("#E").hide();
											$("#stamp").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stampNo").val('');
											$("#paymentDetails").show();

										}
									});
					$("#applicationType")
							.change(
									function(e) {
										if ($(
												'#applicationType option:selected')
												.attr('code') == "O") {
											$("#organization").show();
											$("#disp").hide();
											$("#bplshow").hide();
											$("#bplfields").hide();

										} else if (($(
												'#applicationType option:selected')
												.attr('code') == "I")
												|| ($(
														'#applicationType option:selected')
														.attr('value') == "0")) {
											$("#organization").hide();
											$("#paymentDetails").hide();
											$("#apmOrgnName").val(' ');
											$("#disp").show();
											$("#isBPL").val('0');

										}
									});

					$("#applicationType")
							.change(
									function(e) {
										$("#isBPL")
												.change(
														function(e) {
															$(
																	"#applReferenceMode")
																	.change(
																			function(
																					e) {
																				if (($(
																						'#applicationType option:selected')
																						.attr(
																								'code') == "I")
																						&& ($(
																								'#isBPL option:selected')
																								.attr(
																										'code') == "Y")
																						&& ($(
																								'#applReferenceMode option:selected')
																								.attr(
																										'code') == "D")) {
																					$(
																							"#paymentDetails")
																							.hide();
																				}
																			});
														});
									});

					$("#rtiDeptId").change(function(e) {
						getLocationByDeptName();
					});

				});

function validateRtiForm(errorList) {
	var errorList = [];
	var location = $("#rtiLocationId").val();
	var RtiSubject = $("#RtiSubject").val();
	var rtiDeptId = $("#rtiDeptId").val();
	var actionTakenPIO = $("#rtiPioAction").val();
	/* Defect #32989 */
	var rtiDeciRecDate = $('#rtiDeciRecDate').val();
	var actionDate = $('#rtiPioActionDate').val();

	if (location == "0" || location == undefined || location == "") {
		errorList.push(getLocalMessage("rti.validation.Location"));
	}

	if ($('.addr').is(":unchecked")
			&& ($("#rtiAddress").val() == "" || $("#rtiAddress").val() == undefined)) {
		errorList.push(getLocalMessage("rti.validation.correspondingAddress"));
	}

	if (RtiSubject == "" || RtiSubject == undefined) {
		errorList.push(getLocalMessage("rti.validation.rtiSubject"));
	}

	if (rtiDeptId == "0" || rtiDeptId == undefined) {
		errorList.push(getLocalMessage("rti.validation.rtiDeptId"));
	}

	if (actionTakenPIO == "0" || actionTakenPIO == undefined
			|| actionTakenPIO == "") {
		errorList.push(getLocalMessage("rti.validation.actionTakenPIO"));
	}
	if (actionDate == "0" || actionDate == undefined || actionDate == "") {
		errorList.push(getLocalMessage("rti.validation.actionDates"));
	}

	if (rtiDeciRecDate == "0" || rtiDeciRecDate == undefined
			|| rtiDeciRecDate == "") {
		errorList.push(getLocalMessage("rti.validation.rtiDeciRecDates"));
	}

	var actionDates = moment(actionDate, "DD.MM.YYYY HH.mm").toDate();

	var rtiDeciRecDates = moment(rtiDeciRecDate, "DD.MM.YYYY HH.mm").toDate();

	if (actionDates.getTime() < rtiDeciRecDates.getTime()) {
		errorList.push(getLocalMessage("rti.actiondate.decisiondate"));
	}

	return errorList;
}

function loadPreDataOnValidation() {

	if ($('#applicationType option:selected').attr('code') == "O") {
		$("#organization").show();

	} else if ($('#applicationType option:selected').attr('code') == "I") {
		$("#disp").show();
		if ($('#isBPL option:selected').attr('code') == "Y") {
			$("#bplshow").show();
			$("#bplfields").show();

		} else if ($('#isBPL option:selected').attr('code') == "N") {
			$("#bplshow").hide();
			$("#bplfields").hide();
		}

	}
	if ($('#applReferenceMode option:selected').attr('code') == "E") {
		$("#E").show();
		$("#custDate").data('rule-required', true);
		$("#inwAuthorityName").data('rule-required', true);
		$("#inwAuthorityDept").data('rule-required', true);
		$("#inwAuthorityAddress").data('rule-required', true);
		$("#inwReferenceNo").data('rule-required', true);
	}
	if ($('.addr').is(":unchecked")) {
		$("#address").show();
	}

}

$(document).ready(function() {
	if ($('#applicationType option:selected').attr('code') == "O") {
		$("#organization").show();

	} else if ($('#applicationType option:selected').attr('code') == "I") {
		$("#disp").show();
		var bpl = $("#isBPL").val();
		if (bpl == 'Y') {
			$("#isBPL").val("Yes");
			$("#bplshow").show();
			$("#bplfields").show();
		} else {
			$("#isBPL").val("No");
			$("#bplshow").hide();
			$("#bplfields").hide();
		}

		if ($('#isBPL option:selected').attr('code') == "Y") {
			$("#bplshow").show();
			$("#bplfields").show();

		} else if ($('#isBPL option:selected').attr('code') == "N") {

			$("#bplshow").hide();
			$("#bplfields").hide();
		}

	}
	if ($('#applReferenceMode option:selected').attr('code') == "E") {
		$("#E").show();
		$("#custDate").data('rule-required', true);
		$("#inwAuthorityName").data('rule-required', true);
		$("#inwAuthorityDept").data('rule-required', true);
		$("#inwAuthorityAddress").data('rule-required', true);
		$("#inwReferenceNo").data('rule-required', true);
	} else if ($('#applReferenceMode option:selected').attr('code') == "S") {
		$("#stamp").show();
		$("#stampNo").data('rule-required', true);
		$("#stampAmt").data('rule-required', true);
	}
	if ($('.addr').is(":unchecked")) {
		$("#address").show();
	}

});
function rtiBack() {
	requestData = {};
	var url = "SecondApealRtiApplication.html?back";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function viewRtiForm(applicationId, mode) {

	var requestData = {
		"applicationId" : applicationId,
		"type" : "V",
	};
	var url = "AppealHistory.html?view";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);
	// $('.content').removeClass('ajaxloader');
	$(formDivName).html(ajaxResponse);
	// $("#RtiSubject").val('');
	getLocationByDeptName()

}
function valueChanged() {

	if ($('.addr').is(":checked")) {
		$("#address").hide();
		$("#rtiAddress").val(' ');
	} else
		$("#address").show();
}

function editRtiForm(applicationId) {

	var requestData = {
		"applicationId" : applicationId,
		"type" : "E",

	};

	// var requestData = "applicationId=" + applicationId;
	var url = "AppealHistory.html?view";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);
	// $('.content').removeClass('ajaxloader');
	$(formDivName).html(ajaxResponse);
	// $("#RtiSubject").val('');
	getLocationByDeptName()

}

function getLocationByDeptName() {

	var errorList = [];
	var requestData = {
		"deptId" : $('#rtiDeptId option:selected').attr('value')
	}
	var URL = 'AppealHistory.html?getLocationByDepartment';
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

					$('#datatables').DataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true,
								"bPaginate" : true,
								"bFilter" : true
							});

					var isValidationError = $("#isValidationError").val();

					$("#resetform").on("click", function() {
						window.location.reload("#appealHistoryForm")
					});

					$(function() {
						$('#rtiDeciRecDate').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
						// minDate: 0
						});
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

					/*
					 * var decidate = $('#rtiDeciRecDate').val(); if(decidate ==
					 * "") {
					 * 
					 * $('#rtiDeciRecDate').prop("disabled", false); } else {
					 * $('#rtiDeciRecDate').prop("disabled", true);
					 * 
					 * $("#save").hide(); }
					 */

				});

function saveAppealHistory(element) {

	var errorList = [];
	errorList = validateRtiForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				"Your application for RTI saved successfully!",
				'AppealHistory.html', 'save');
	} else {
		displayErrorsOnPage(errorList);
	}

}
function rtiBack() {

	requestData = {};
	var url = "AppealHistory.html?back";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}

function validateRtiForm(errorList) {

	var errorList = [];
	var decisionReceiveDate = $("#rtiDeciRecDate").val();

	if (decisionReceiveDate == "0" || decisionReceiveDate == undefined
			|| decisionReceiveDate == "") {
		errorList.push(getLocalMessage("rti.validation.decisionReceiveDate"));
	}
	return errorList;
}
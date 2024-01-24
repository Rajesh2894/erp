var formDivName = '.content-page';
var divName = ".widget-content";
function valueChanged() {

	if ($('.addr').is(":checked")) {
		$("#address").hide();
		$("#rtiAddress").val(' ');
	} else
		$("#address").show();
}

function resetRtiForm() {

	var ajaxResponse = __doAjaxRequest(
			'RtiApplicationUserDetailForm.html?accept', 'POST', {}, false,
			'html');

	$(formDivName).html(ajaxResponse);
}

function getChecklistAndChargesForIndividual() {
	var BPL = $('#isBPL option:selected').attr('code');
	var theForm = '#rtiForm';
	var requestData = __serializeForm(theForm);
	var URL = 'RtiApplicationUserDetailForm.html?getRtiCheckListAndCharge';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');

	if (returnData != null)

		if (BPL == undefined) {
			return false;
		}
	{
		if (BPL == "Y") {
			$("#payandCheckIdDiv").html(returnData);
		} else {
			$(formDivName).html(returnData);
		}
	}
}

function getChecklistAndChargesForOrganisation() {

	var theForm = '#rtiForm';
	var requestData = __serializeForm(theForm);
	var URL = 'RtiApplicationUserDetailForm.html?getRtiCheckListAndCharge';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');

	if (returnData != null) {
		$(formDivName).html(returnData);
	}
}

function getLocationByDeptName() {
	var errorList = [];
	var orgId = $('#orgId').val();
	if (orgId == undefined || orgId == null) {
		orgId = "0";
	}
	var requestData = {
		"deptId" : $('#rtiRelatedDeptId option:selected').attr('value'),
		"orgId" : orgId
	}
	var URL = 'RtiApplicationUserDetailForm.html?getLocationByDepartment';
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

					$('body').on(
							'focus',
							".hasMobileNo",
							function() {
								$('.hasMobileNo').keyup(
										function() {
											this.value = this.value.replace(
													/[^1-9][0-9]{9}/g, '');
											$(this).attr('maxlength', '10');
										});
							});

					$('body').on(
							'focus',
							".hasPincode",
							function() {
								$('.hasPincode').keyup(
										function() {
											this.value = this.value.replace(
													/[^1-9][0-9]{5}/g, '');
											$(this).attr('maxlength', '6');
										});
							});

					$(document).ready(function() {
						$('#custDate').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							maxDate : '0',

						});
						
					});
					$(document).ready(function() {
						$('#nonJudclDate').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							maxDate : '0',

						});
					});
					$(document).ready(function() {
						$('#challanDate').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							maxDate : '0',

						});
					});
					/*
					 * $("#resetform").on("click", function() {
					 * window.location.reload("#rtiForm") });
					 */

					/*
					 * $('#yearOfIssue').datepicker( { changeMonth : false,
					 * changeYear : true, showButtonPanel : false, dateFormat :
					 * 'yy', maxDate : '0d', duration : 'fast', stepMonths : 12,
					 * monthNames : [ "", "", "", "", "", "", "", "", "", "",
					 * "", "", "", "" ], onChangeMonthYear : function(year) {
					 * $(this).val(year); } });
					 */

					var year = (new Date).getFullYear();
					$('#yearOfIssue')
							.datepicker(
									{
										changeYear : true,
										dateFormat : 'yy',
										showButtonPanel : true,
										maxDate : new Date(year, 11, 31),
										beforeShow : function(el, dp) {
											$('#ui-datepicker-div').addClass(
													'hide-calendar');
										},
										onClose : function(dateText, inst) {
											var year = $(
													"#ui-datepicker-div .ui-datepicker-year :selected")
													.val();
											$(this).datepicker('setDate',
													new Date(year, 0, 1));
											$('#ui-datepicker-div')
													.removeClass(
															'hide-calendar');
										},
									});

					/*
					 * $('#rtiForm').validate({ onkeyup: function(element) {
					 * this.element(element); console.log('onkeyup fired'); },
					 * onfocusout: function(element) { this.element(element);
					 * console.log('onfocusout fired'); } });
					 */

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
											/*
											 * $('#bplshow').children().find('input').each(function(){
											 * $(this).val('');
											 * 
											 * });
											 */
										}
										getChecklistAndChargesForIndividual();
									});

					$("#applReferenceMode")
							.change(
									function(e) {
										if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "E") {
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#E").show();
											$("#stamp").hide();
											$("#stampNo").val('');
											$("#paymentDetails").hide();
											$("#Post").hide();
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
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stamp").show();
											$("#Post").hide();
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
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#E").hide();
											$("#stamp").hide();
											$("#Post").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stampNo").val('');
											$("#paymentDetails").show();

										} else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "P") {
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stamp").hide();
											$("#Post").show();
											$("#postalCardNo").data(
													'rule-required', true);
											$("#postalAmt").data(
													'rule-required', true);

											$("#paymentDetails").hide();

										} else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "C") {
											$("#Challan").show();
											$("#NonJudicial").hide();
											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stamp").hide();
											$("#Post").hide();
											$("#postalCardNo").data(
													'rule-required', true);
											$("#postalAmt").data(
													'rule-required', true);

											$("#paymentDetails").hide();

										}
										else if ($(
										'#applReferenceMode option:selected')
												.attr('code') == "N") {
											$("#Challan").hide();
											$("#NonJudicial").show();
											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stamp").hide();
											$("#Post").hide();
											$("#postalCardNo").data(
													'rule-required', true);
											$("#postalAmt").data(
													'rule-required', true);

											$("#paymentDetails").hide();

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
											getChecklistAndChargesForOrganisation();

										} else if (($(
												'#applicationType option:selected')
												.attr('code') == "I")
												|| ($(
														'#applicationType option:selected')
														.attr('value') == "0")) {
											$("#organization").hide();
											$("#paymentDetails").hide();
											$("#apmOrgnName").val('');
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

					$("#rtiRelatedDeptId").change(function(e) {
						getLocationByDeptName();
					});

					if (isValidationError == "Y") {
						loadPreDataOnValidation();
					}

				});

function saveRtiForm(element) {

	var errorList = [];
	errorList = validateRtiForm(errorList);
	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(element,
					"Your application for RTI saved successfully!",
					'RtiApplicationUserDetailForm.html?PrintReport', 'saveRti');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			return saveOrUpdateForm(element,
					"Your application for RTI saved successfully!",
					'RtiApplicationUserDetailForm.html?redirectToPay',
					'saveRti');
		} else {
			return saveOrUpdateForm(element,
					"Your application for RTI saved successfully!",
					'RtiApplicationUserDetailForm.html?proceed', 'saveRti');
		}
	} else {
		displayErrorsOnPage(errorList);
	}

}

function validateRtiForm(errorList) {
	
	var errorList = [];
	var applType = $("#applicationType").val();
	var titleId = $("#titleId").val();
	var orgName = $("#apmOrgnName").val();
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var gender = $("#gender").val();
	var mobileNo = document.getElementById('mobileNo');
	var location = $("#rtiLocationId").val();
	var address = $("#rtiAddress").val();
	// var village=$("#cityName").val();
	var pincode = document.getElementById('pincodeNo');
	// var aadharNo = document.getElementById('uid');
	var povertyLine = $("#isBPL").val();
	var refMode = $("#applReferenceMode").val();
	var inwardType = $("#inwardType").val();
	var bplNo = $("#bplNo").val();
	var yearOfIssue = document.getElementById('yearOfIssue');
	var bplIssuingAuthority = $("#bplIssuingAuthority").val();
	var inwAuthorityName = $("#inwAuthorityName").val();
	var inwAuthorityDept = $("#inwAuthorityDept").val();
	var inwAuthorityAddress = $("#inwAuthorityAddress").val();
	var inwReferenceNo = $("#inwReferenceNo").val();
	var custDate = $("#custDate").val();
	var stampNo = $("#stampNo").val();
	var stampAmt = $("#stampAmt").val();
	var RtiSubject = $("#RtiSubject").val();
	var RtiDetails = $("#RtiDetails").val();
	// var rtiDeptId = $("#rtiDeptId").val();
	var amountToPay = $("#amountToPay").val();
	var amountToPay1 = $("#amountToPay1").val();
	var oflPaymentMode = $("#oflPaymentMode").val();
	var payModeIn = $("#payModeIn").val();
	var bankID = $("#bankID").val();
	var instrumentNo = $("#instrumentNo").val();
	var ChqDdDate = $("#ChqDdDate").val();
	var isOther = $('#applicationType').find(":selected").attr("code");
	var postalCardNo = $("#postalCardNo").val();
	var postalAmt = $("#postalAmt").val();
	var nonJudclNo = $("#nonJudclNo").val();
	var challanNo = $("#challanNo").val();
	var nonJudclDate = $("#nonJudclDate").val();
	var challanDate = $("#challanDate").val();
	if (applType == "0" || applType == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantType"));
	} else if (isOther == "O") {
		if (orgName == "" || orgName == undefined) {
			errorList.push(getLocalMessage("rti.validation.OrganisationName"));
		}
	}
	/*
	 * if (titleId == "0" || titleId == undefined) {
	 * errorList.push(getLocalMessage("rti.validation.title")); }
	 */
	/*if (fName == "" || fName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantFirstName"));
	}
	if (lName == "" || lName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantLastName"));
	}
	if (mobileNo.value.length == "" || mobileNo.value.length == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo"));
	} else if (mobileNo.value.length < 10 || mobileNo.value.length > 10) {
		errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo1"));
	}*/
	/*
	 * if (gender == "0" || gender == undefined) {
	 * errorList.push(getLocalMessage("rti.validation.Gender")); }
	 */
	/*if (address == "" || address == undefined) {
		errorList.push(getLocalMessage("rti.validation.Address"));
	}
	if (pincode.value.length == "" || pincode.value.length == undefined) {
		errorList.push(getLocalMessage("rti.validation.PinCode"));
	} else if (pincode.value.length < 6 || pincode.value.length > 6) {
		errorList.push(getLocalMessage("rti.validation.PinCode1"));
	}*/
	if (isOther == "I") {
		if (povertyLine == "0" || povertyLine == undefined) {
			errorList.push(getLocalMessage("rti.validation.IsBPL"));
		} else if ($('#isBPL option:selected').attr('code') == "Y") {
			if (bplNo == "" || bplNo == undefined) {
				errorList.push(getLocalMessage("rti.validation.BplNo"));
			}
			if (yearOfIssue.value.length == ""
					|| yearOfIssue.value.length == undefined) {
				errorList
						.push(getLocalMessage("rti.validation.YearOfIssueBpl"));
			} else if (yearOfIssue.value.length < 4
					|| yearOfIssue.value.length > 4) {
				errorList.push(getLocalMessage("rti.validation.yearOfIssue"));
			}
			if (bplIssuingAuthority == "" || bplIssuingAuthority == undefined) {
				errorList
						.push(getLocalMessage("rti.validation.IssuingAuthority"));
			}
		}
	}
	if (refMode == "0" || refMode == undefined) {
		errorList.push(getLocalMessage("rti.validation.RefMode"));
	} else if ($('#applReferenceMode option:selected').attr('code') == "E") {
		if (inwAuthorityName == "" || inwAuthorityName == undefined) {
			errorList.push(getLocalMessage("rti.validation.authorityName"));
		}
		if (inwAuthorityDept == "" || inwAuthorityDept == undefined) {
			errorList.push(getLocalMessage("rti.validation.departmentName"));
		}
		if (inwAuthorityAddress == "" || inwAuthorityAddress == undefined) {
			errorList.push(getLocalMessage("rti.validation.address"));
		}
		if (inwReferenceNo == "" || inwReferenceNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.referenceNumber"));
		}
		if (custDate == "" || custDate == undefined) {
			errorList.push(getLocalMessage("rti.validation.referenceDate"));
		}
	} else if ($('#applReferenceMode option:selected').attr('code') == "S") {
		if (stampNo == " " || stampNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.stampNumber"));
		}

		if (stampAmt == "" || stampAmt == undefined || stampAmt == 0) {
			errorList.push(getLocalMessage("rti.validation.stampAmount"));
		}

	} else if ($('#applReferenceMode option:selected').attr('code') == "P") {
		if (postalCardNo == " " || postalCardNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.postalCardNo"));
		}

		if (postalAmt == "" || postalAmt == undefined || postalAmt == 0) {
			errorList.push(getLocalMessage("rti.validation.postalAmt"));
		}

	}	else if ($('#applReferenceMode option:selected').attr('code') == "C") {
		if (challanNo == "" || challanNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.challanNo"));
		}
		if (challanDate == "" || challanDate == undefined ) {
			errorList.push(getLocalMessage("rti.validation.challanDate"));
		}
	}
	else if ($('#applReferenceMode option:selected').attr('code') == "N") {
		if (nonJudclNo == "" || nonJudclNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.nonJudclNo"));
		}
		if (nonJudclDate == "" || nonJudclDate == undefined ) {
			errorList.push(getLocalMessage("rti.validation.nonJudclDate"));
		}
	}
	if (inwardType == "0" || inwardType == undefined) {
		errorList.push(getLocalMessage("rti.validation.InwardType"));
	}
	if (location == "0" || location == undefined || location == "") {
		errorList.push(getLocalMessage("rti.validation.Location"));
	}
	/*
	 * if (aadharNo.value.length == "" || aadharNo.value.length == undefined) {
	 * errorList.push(getLocalMessage("rti.validation.AadharNo")); } else if
	 * (aadharNo.value.length < 12 || aadharNo.value.length > 12) {
	 * errorList.push(getLocalMessage("rti.aadhar.msg")); }
	 */

	var selectedYear = document.getElementById("yearOfIssue").value;
	var rightnow = new Date();
	var y = rightnow.getFullYear();
	if (selectedYear > y) {
		errorList.push(getLocalMessage("rti.validation.YearOfIssue"));
	}

	if ($('.addr').is(":unchecked")
			&& ($("#rtiAddress").val() == "" || $("#rtiAddress").val() == undefined)) {
		errorList.push(getLocalMessage("rti.validation.correspondingAddress"));
	}

	if (RtiSubject == "" || RtiSubject == undefined) {
		errorList.push(getLocalMessage("rti.validation.rtiSubject"));
	}
	/*
	 * if(RtiDetails == "" || RtiDetails == undefined) {
	 * errorList.push(getLocalMessage("rti.validation.rtiDetails")); }
	 */
	/*
	 * if (rtiDeptId == "0" || rtiDeptId == undefined) {
	 * errorList.push(getLocalMessage("rti.validation.rtiDeptId")); }
	 */

	if (isOther == "I") {
		/*
		 * if(povertyLine == "0" || povertyLine == undefined){
		 * errorList.push(getLocalMessage("rti.validation.IsBPL")); }
		 */
		if ($('#isBPL option:selected').attr('code') == "N") {

			if ($('#applReferenceMode option:selected').attr('code') == "D") {
				if (amountToPay != undefined) {
					if (amountToPay > 0
							&& $(
									"input:radio[name='offlineDTO.onlineOfflineCheck']")
									.filter(":checked").val() == ""
							|| $(
									"input:radio[name='offlineDTO.onlineOfflineCheck']")
									.filter(":checked").val() == undefined) {
						errorList
								.push(getLocalMessage("rti.validation.collectionMode"));
					}
				} else if (amountToPay1 != undefined) {
					if (amountToPay1 > 0
							&& $(
									"input:radio[name='offlineDTO.onlineOfflineCheck']")
									.filter(":checked").val() == ""
							|| $(
									"input:radio[name='offlineDTO.onlineOfflineCheck']")
									.filter(":checked").val() == undefined) {
						errorList
								.push(getLocalMessage("rti.validation.collectionMode"));
					}
				}
			}

		}
	}
	// for DSCL and applicant type as other
	else if (isOther == "O") {
		if ($('#applReferenceMode option:selected').attr('code') == "D") {
			if (amountToPay1 != undefined) {
				if (amountToPay1 > 0
						&& $(
								"input:radio[name='offlineDTO.onlineOfflineCheck']")
								.filter(":checked").val() == ""
						|| $(
								"input:radio[name='offlineDTO.onlineOfflineCheck']")
								.filter(":checked").val() == undefined) {
					errorList
							.push(getLocalMessage("rti.validation.collectionMode"));
				}
			}
		}
	}

	/*
	 * if(amountToPay != undefined){ if(amountToPay > 0 &&
	 * $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == "" ||
	 * $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() ==
	 * undefined){
	 * errorList.push(getLocalMessage("rti.validation.collectionMode")); } }
	 */
	/*
	 * if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() ==
	 * 'N' && (oflPaymentMode == "0" || oflPaymentMode == undefined)){
	 * errorList.push(getLocalMessage("rti.validation.challanMode")); }
	 * if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() ==
	 * 'P'){ if(payModeIn == "0" || payModeIn == undefined){
	 * errorList.push(getLocalMessage("rti.validation.payModeIn")); }else
	 * if($('#payModeIn option:selected').attr('code') != "C"){ if(bankID == "" ||
	 * bankID == undefined){
	 * errorList.push(getLocalMessage("rti.validation.bankId")); }
	 * if(instrumentNo == "" || instrumentNo == undefined){
	 * errorList.push(getLocalMessage("rti.validation.instrumentNo")); }
	 * if(ChqDdDate == "" || ChqDdDate == undefined){
	 * errorList.push(getLocalMessage("rti.validation.instrumentDate")); } } }
	 */

	return errorList;
}

function loadPreDataOnValidation() {

	if ($('#applicationType option:selected').attr('code') == "O") {
		$("#organization").show();
		// getChecklistAndChargesForOrganisation();

	} else if ($('#applicationType option:selected').attr('code') == "I") {
		$("#disp").show();
		if ($('#isBPL option:selected').attr('code') == "Y") {
			$("#bplshow").show();
			$("#bplfields").show();

		} else if ($('#isBPL option:selected').attr('code') == "N") {
			$("#bplshow").hide();
			$("#bplfields").hide();
		}
		// getChecklistAndChargesForIndividual();

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
		$("#paymentDetails").hide();
	}
	else if ($('#applReferenceMode option:selected').attr('code') == "P") {

		$("#Post").show();
		$("#postalCardNo").data('rule-required', true);
		$("#postalAmt").data('rule-required', true);
		$("#paymentDetails").hide();
	}
	 else if ($('#applReferenceMode option:selected').attr('code') == "N") {

			$("#NonJudicial").show();
			$("#nonJudclNo").data('rule-required', true);
			$("#nonJudclDate").data('rule-required', true);
			$("#paymentDetails").hide();
		}
	 else if ($('#applReferenceMode option:selected').attr('code') == "C") {

			$("#Challan").show();
			$("#challanNo").data('rule-required', true);
			$("#challanDate").data('rule-required', true);
			$("#paymentDetails").hide();
		}
	if ($('.addr').is(":unchecked")) {
		$("#address").show();
	}

	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N') {
		$("#offlinePayement").show();
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'P') {
		$("#Collection_mode").show();
	}
	if ($('#payModeIn option:selected').attr('code') == "C") {
		$(".cash").hide();
	} else
		$(".cash").show();

}

$(document)
		.ready(
				function() {

					if ($('#applicationType option:selected').attr('code') == "O") {
						$("#organization").show();

					} else if ($('#applicationType option:selected').attr(
							'code') == "I") {
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
					} else if ($('#applReferenceMode option:selected').attr(
							'code') == "S") {
						$("#stamp").show();
						$("#stampNo").data('rule-required', true);
						$("#stampAmt").data('rule-required', true);
					}
					 else if ($('#applReferenceMode option:selected').attr('code') == "N") {

							$("#NonJudicial").show();
							$("#nonJudclNo").data('rule-required', true);
							$("#nonJudclDate").data('rule-required', true);
							$("#paymentDetails").hide();
						}
					 else if ($('#applReferenceMode option:selected').attr('code') == "C") {

							$("#Challan").show();
							$("#challanNo").data('rule-required', true);
							$("#challanDate").data('rule-required', true);
							$("#paymentDetails").hide();
						}
					if ($('.addr').is(":unchecked")) {
						$("#address").show();
					}

					if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
							.filter(":checked").val() == 'N') {
						$("#offlinePayement").show();
					} else if ($(
							"input:radio[name='offlineDTO.onlineOfflineCheck']")
							.filter(":checked").val() == 'P') {
						$("#Collection_mode").show();
					}
					if ($('#payModeIn option:selected').attr('code') == "C") {
						$(".cash").hide();
					} else
						$(".cash").show();

				});

function getOrganisation() {
	var distId = $('#district').val();
	$('#orgId').html('');
	/*
	 * $('#orgId').append( $("<option></option>").attr("value", "0").text(
	 * getLocalMessage('selectdropdown'))).trigger( 'chosen:updated');
	 */
	if (distId != "" && distId != 0) {
		var requestData = {
			"districtId" : $('#district').val()
		}
		var response = __doAjaxRequest(
				'RtiApplicationUserDetailForm.html?rtiOrganisations', 'post',
				requestData, false, 'json');
		$.each(response, function(index, value) {
			$('#orgId').append(
					$("<option></option>").attr("value", value.lookUpId).attr(
							"code", value.lookUpCode).text(value.lookUpDesc));
		});
		$('#orgId').trigger('chosen:updated');
	}

}

function backPage() {

	window.location.href = getLocalMessage("CitizenHome.html");
}
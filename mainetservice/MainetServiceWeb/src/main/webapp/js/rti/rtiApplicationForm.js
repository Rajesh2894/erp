var formDivName = '.content-page';
function valueChanged() {

	if ($('.addr').is(":checked")) {
		$("#address").hide();
		$("#rtiAddress").val(' ');
	} else
		$("#address").show();
}
$(document)
		.ready(
				function() {
					$("#MobileNumber")
							.change(
									function() {
										var mobileNumber = $("#MobileNumber")
												.val();
										var successCallback = function(response) {

											$("#titleId").val(response.titleId);
											$("#FirstName1")
													.val(response.fName);
											$("#MiddleName")
													.val(response.mName);
											$("#LastName1").val(response.lName);
											$("#gender").val(response.gender);
											$("#rtiAddress").val(
													response.areaName);
											$("#EmailID").val(response.email);
											$("#Pincode").val(
													response.pincodeNo);
										}
										var errorCallback = function(response) {
											$("#titleId,#gender").val(0);
											$(
													"#FirstName1,#MiddleName,#LastName1,#rtiAddress,#EmailID,#Pincode")
													.val("");
											// Mobile number not registered.
											// Please register before RTI
											// displayMessageForRegister();

										}

										if (mobileNumber.length == 10) {
											var requestData = {
												mobileNumber : mobileNumber
											};
											__doAjaxRequestWithCallback(
													'RtiApplicationDetailForm.html?getApplicationDetails',
													'post', requestData, false,
													'JSON', successCallback,
													errorCallback);
										} else {
											errorCallback();
										}
									});

				});

function resetRtiForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#rtiForm").validate().resetForm();
}

function getChecklistAndChargesForIndividual() {

	var BPL = $('#isBPL option:selected').attr('code');
	var theForm = '#rtiForm';
	var requestData = __serializeForm(theForm);
	var URL = 'RtiApplicationDetailForm.html?getRtiCheckListAndCharge';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	var errors = $(returnData).find(".error-msg");
	if (returnData != null) {

		if (BPL == undefined) {
			return false;
		}

		if (BPL == "Y") {
			if (errors.length != 0) {
				$(".content-page").html(returnData);
			} else {
				$("#payandCheckIdDiv").html(returnData);
			}
		} else {
			$(formDivName).html(returnData);
		}
	}
}

function getChecklistAndChargesForOrganisation() {

	var theForm = '#rtiForm';
	var requestData = __serializeForm(theForm);
	var URL = 'RtiApplicationDetailForm.html?getRtiCheckListAndCharge';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');

	if (returnData != null) {
		$(formDivName).html(returnData);
		prepareTags();
	}
}

function getLocationByDeptName() {

	var errorList = [];
	var requestData = {
		"deptId" : $('#rtiRelatedDeptId option:selected').attr('value')
	}
	var URL = 'RtiApplicationDetailForm.html?getLocationByDepartment';
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

					/*
					 * $(function() {
					 * 
					 * $('#custDate').datepicker({ dateFormat : 'dd/mm/yy',
					 * changeMonth : true, changeYear : true }); });
					 */

					/* #29378 by Priti */

					$(document).ready(function() {
						$('#inwReferenceDate').datepicker({
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

					/* #29358 by Priti */
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

					/* #29358 by Priti */

					/*
					 * $('#yearOfIssue').datepicker( { changeYear: true,
					 * dateFormat: 'yy', onClose: function(dateText, inst) { var
					 * year = $("#ui-datepicker-div .ui-datepicker-year
					 * :selected").val(); $(this).datepicker('setDate', new
					 * Date(year, 1)); } }); $("#yearOfIssue").focus(function () {
					 * $(".ui-datepicker-month").css("display","none");
					 * $(".ui-datepicker-calendar").css("display","none"); });
					 */

					$("#resetform").on("click", function() {
						window.location.reload("#rtiForm")
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

											/* #27515 by Priti */

											// to disable the payment receipt
											$('#offlinepayment').val('');
											$('#payModeIn').val('');
											$('#bankAccId').val('');
											$('#bankID').val('');
											$('#offlineLabel').val('');
											$('#payAtCounter').val('');
											$('#offlinebutton').val('');

											$("#paymentDetails").hide();

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

											$("#E").show();
											$("#stamp").hide();
											$("#stampNo").val('');
											$("#stampAmt").val('');
											$("#paymentDetails").hide();
											$("#Post").hide();
											// $("#stampAmt").val('');
											$("#inwReferenceDate").data(
													'rule-required', true);
											$("#inwAuthorityName").data(
													'rule-required', true);
											$("#inwAuthorityDept").data(
													'rule-required', true);
											$("#inwAuthorityAddress").data(
													'rule-required', true);
											$("#inwReferenceNo").data(
													'rule-required', true);
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#AMTSHOW").hide();
										} else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "S") {

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
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#paymentDetails").hide();
											$("#AMTSHOW").hide();
										} else if (($(
												'#applReferenceMode option:selected')
												.attr('code') == "D")
												|| ($(
														'#applReferenceMode option:selected')
														.attr('value') == "0")) {

											$("#E").hide();
											$("#stamp").hide();
											$("#Post").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#stampNo").val('');
											$("#stampAmt").val('');
											$("#paymentDetails").show();

										} // added regarding US#111612
										else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "P") {

											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#Challan").hide();
											$("#NonJudicial").hide();
											$("#stamp").hide();
											$("#Post").show();
											$("#postalCardNo").data(
													'rule-required', true);
											$("#postalAmt").data(
													'rule-required', true);

											$("#paymentDetails").hide();
											$("#AMTSHOW").hide();

										} else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "N") {

											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#NonJudicial").show();
											$("#stamp").hide();
											$("#Post").hide();
											$("#Challan").hide();
											$("#postalCardNo").data(
													'rule-required', true);
											$("#postalAmt").data(
													'rule-required', true);

											$("#paymentDetails").hide();
											$("#AMTSHOW").hide();

										} else if ($(
												'#applReferenceMode option:selected')
												.attr('code') == "C") {

											$("#E").hide();
											$('#E').children().find('input')
													.each(function() {
														$(this).val('');
													});
											$("#stamp").hide();
											$("#Post").hide();
											$("#Challan").show();
											$("#NonJudicial").hide();
											$("#postalCardNo").data(
													'rule-required', true);
											$("#postalAmt").data(
													'rule-required', true);

											$("#paymentDetails").hide();
											$("#AMTSHOW").hide();

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
	// added regarding US#111612
	var refMode = $('#applReferenceMode option:selected').attr('code');
	if (errorList.length == 0) {
		if (refMode != "S" && refMode != "P" && refMode != "E"&&refMode != "C"&&refMode != "N") {

			if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'N'
					|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
							.filter(":checked").val() == 'P') {
				return saveOrUpdateForm(element,
						getLocalMessage("rti.successMessage"),
						'RtiApplicationDetailForm.html?PrintReport', 'saveRti');
			}

			else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
					.filter(":checked").val() == 'Y') {
				return saveOrUpdateForm(element,
						getLocalMessage("rti.successMessage"),
						'RtiApplicationDetailForm.html?redirectToPay',
						'saveRti');
			} else {
				return saveOrUpdateForm(element,
						getLocalMessage("rti.successMessage"),
						'RtiApplicationDetailForm.html?proceed', 'saveRti');
			}

			/*
			 * else if (($('#applicationType option:selected').attr('code') ==
			 * "O") && (($('#applReferenceMode option:selected').attr('code') ==
			 * "E") || ($('#applReferenceMode option:selected').attr('code') ==
			 * "S"))) { return saveOrUpdateForm(element,"Your application for
			 * RTI saved successfully!", 'AdminHome.html', 'saveRti'); }
			 */
		} else {
			return saveOrUpdateForm(element,
					getLocalMessage("rti.successMessage"),
					'RtiApplicationDetailForm.html?proceed', 'saveRti');
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
	var fName = $("#FirstName1").val();
	var lName = $("#LastName1").val();
	var gender = $("#gender").val();
	var mobileNo = $("#MobileNumber").val();
	var location = $("#rtiLocationId").val();
	var address = $("#rtiAddress").val();
	// var village=$("#cityName").val();
	var pincode = $('#Pincode').val();
	var aadharNo = $('#uid').val();
	// document.getElementById('uid');
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
	var inwReferenceDate = $("#inwReferenceDate").val();
	var stampNo = $("#stampNo").val();
	var stampAmt = $("#stampAmt").val();
	var RtiSubject = $("#RtiSubject").val();
	// MOM POINTS var RtiDetails=$("#RtiDetails").val();
	// var rtiDeptId = $("#rtiDeptId").val();
	var amountToPay = $("#amountToPay").val();
	var oflPaymentMode = $("#oflPaymentMode").val();
	var payModeIn = $("#payModeIn").val();
	var emailId = $("#EmailID").val();
	var postalCardNo = $("#postalCardNo").val();
	var postalAmt = $("#postalAmt").val();
	var bankID = $("#bankID").val();
	var instrumentNo = $("#instrumentNo").val();
	var ChqDdDate = $("#ChqDdDate").val();
	var isOther = $('#applicationType').find(":selected").attr("code");
	var bpl = $('#isBPL option:selected').attr('code');
	var nonJudclNo = $("#nonJudclNo").val();
	var challanNo = $("#challanNo").val();
	var nonJudclDate = $("#nonJudclDate").val();
	var challanDate = $("#challanDate").val();
	if (emailId != "") {
		var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
		var valid = emailRegex.test(emailId);
		if (!valid) {
			errorList.push(getLocalMessage('rti.vldnn.emailid'));
		}
	}

	if (applType == "0" || applType == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantType"));
	} else if (isOther == "O") {
		if (orgName == "" || orgName == undefined) {
			errorList.push(getLocalMessage("rti.validation.OrganisationName"));
		} else if ($('#applReferenceMode option:selected').attr('code') == "D") {
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
			}

		}
	}

	/*
	 * if (titleId == "0" || titleId == undefined ||titleId == "") {
	 * errorList.push(getLocalMessage("rti.validation.title")); }
	 */

	if (fName == "" || fName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantFirstName"));
	}
	if (lName == "" || lName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantLastName"));
	}/*
		 * if (mobileNo.value.length == "" || mobileNo.value.length ==
		 * undefined) {
		 * errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo")); }
		 */
	if (mobileNo == "" || mobileNo == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo"));
	}

	else if (mobileNo.length < 10 || mobileNo.length > 10) {
		errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo1"));
	}

	if (gender == "0" || gender == undefined) {
		errorList.push(getLocalMessage("rti.validation.Gender"));
	}
	if (address == "" || address == undefined) {
		errorList.push(getLocalMessage("rti.validation.Address"));
	}
	if (pincode == "" || pincode == undefined) {
		errorList.push(getLocalMessage("rti.validation.PinCode"));
	}
	else if (pincode.length < 6|| pincode.length > 6) {
		errorList.push(getLocalMessage("rti.validation.pincode1"));
	}
	/*
	 * if (pincode.value.length == "" || pincode.value.length == undefined) {
	 * errorList.push(getLocalMessage("rti.validation.PinCode")); } else if
	 * (pincode.value.length < 6 || pincode.value.length > 6) {
	 * errorList.push(getLocalMessage("rti.validation.PinCode1")); }
	 */
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
		if (inwReferenceDate == "" || inwReferenceDate == undefined) {
			errorList.push(getLocalMessage("rti.validation.referenceDate"));
		}
	} else if ($('#applReferenceMode option:selected').attr('code') == "S") {
		if (stampNo == "" || stampNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.stampNumber"));
		}
		if (stampAmt == "" || stampAmt == undefined || stampAmt == 0) {
			errorList.push(getLocalMessage("rti.validation.stampAmount"));
		}
	} else if ($('#applReferenceMode option:selected').attr('code') == "P") {
		if (postalCardNo == "" || postalCardNo == undefined) {
			errorList.push(getLocalMessage("rti.validation.postalCardNo"));
		}
		if (postalAmt == "" || postalAmt == undefined || postalAmt == 0) {
			errorList.push(getLocalMessage("rti.validation.postalAmt"));
		}
	}	
	else if ($('#applReferenceMode option:selected').attr('code') == "C") {
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

	if ((aadharNo != "" && aadharNo != undefined)
			&& (aadharNo.length < 12 || aadharNo.length > 12)) {
		errorList.push(getLocalMessage("rti.aadhar.msg"));
	}

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
	/* MOM POINTS */
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
				}

			}

		}
	}
	// added regarding US#111612
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'P'
			&& $('#applReferenceMode option:selected').attr('code') != "S"
			&& $('#applReferenceMode option:selected').attr('code') != "P"
			&& $('#applReferenceMode option:selected').attr('code') != "E" && $('#applReferenceMode option:selected').attr('code') != "N" && $('#applReferenceMode option:selected').attr('code') != "C") {
		if (payModeIn == undefined || payModeIn == '0') {
			errorList.push(getLocalMessage("rti.validation.collectionMode"));
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
		getChecklistAndChargesForOrganisation();

	} else if ($('#applicationType option:selected').attr('code') == "I") {

		$("#disp").show();
		if ($('#isBPL option:selected').attr('code') == "Y") {
			$("#bplshow").show();
			$("#bplfields").show();

		} else if ($('#isBPL option:selected').attr('code') == "N") {
			$("#bplshow").hide();
			$("#bplfields").hide();
		}
		getChecklistAndChargesForIndividual();

	}
	if ($('#applReferenceMode option:selected').attr('code') == "E") {

		$("#E").show();
		$("#inwReferenceDate").data('rule-required', true);
		$("#inwAuthorityName").data('rule-required', true);
		$("#inwAuthorityDept").data('rule-required', true);
		$("#inwAuthorityAddress").data('rule-required', true);
		$("#inwReferenceNo").data('rule-required', true);
	} else if ($('#applReferenceMode option:selected').attr('code') == "S") {

		$("#stamp").show();
		$("#stampNo").data('rule-required', true);
		$("#stampAmt").data('rule-required', true);

		$("#paymentDetails").hide();
	} else if ($('#applReferenceMode option:selected').attr('code') == "P") {

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
						$("#inwReferenceDate").data('rule-required', true);
						$("#inwAuthorityName").data('rule-required', true);
						$("#inwAuthorityDept").data('rule-required', true);
						$("#inwAuthorityAddress").data('rule-required', true);
						$("#inwReferenceNo").data('rule-required', true);
						$("#paymentDetails").hide();
						$("#AMTSHOW").hide();
					} else if ($('#applReferenceMode option:selected').attr(
							'code') == "S") {

						$("#stamp").show();
						$("#stampNo").data('rule-required', true);
						$("#stampAmt").data('rule-required', true);
						$("#paymentDetails").hide();
					} else if ($('#applReferenceMode option:selected').attr(
							'code') == "P") {

						$("#Post").show();
						$("#postalCardNo").data('rule-required', true);
						$("#postalAmt").data('rule-required', true);
						$("#paymentDetails").hide();
					}
 					else if ($('#applReferenceMode option:selected').attr(
							'code') == "N") {

						$("#NonJudicial").show();
						$("#nonJudclNo").data('rule-required', true);
						$("#nonJudclDate").data('rule-required', true);
						$("#paymentDetails").hide();
					} else if ($('#applReferenceMode option:selected').attr(
							'code') == "C") {

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
function displayMessageForRegister() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Yes/No';

	message += '<h4 class=\"text-center text-blue-2 padding-10\">This Mobile No is not registered.Please Register before Filling RTI.Do You Want to Register ?</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="refreshPage()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}
function refreshPage() {
	$.fancybox.close();
	window.location.href = getLocalMessage("rti.adminHome");
}

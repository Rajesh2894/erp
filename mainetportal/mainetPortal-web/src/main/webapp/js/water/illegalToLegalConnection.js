$(document)
		.ready(
				function() {

					$('#frmNewWaterForm').validate({
						onkeyup : function(element) {
							this.element(element);
							console.log('onkeyup fired');
						},
						onfocusout : function(element) {
							this.element(element);
							console.log('onfocusout fired');
						}
					});
					$("#isBhagirathi").hide();
					$("#withBhagiRathi").attr("disabled", true);
					$("#bplNo").addClass("changeParameterClass");
					$(".changeParameterClass").change(function(e) {
						var valueAfterData = $(this).val();
						var id = $(this).attr("id");
						if ($('#free').val() != "O") {
							$('#divSubmit').hide();
							$('#chekListChargeId').show();
							clearCheckListAndCharges(id, valueAfterData);
						}

					});
					$(".changeParameter").keyup(function(e) {
						var valueAfterData = $(this).val();
						var id = $(this).attr("id");
						if ($('#free').val() != "O") {
							$('#divSubmit').hide();
							$('#chekListChargeId').show();
							clearCheckListAndCharges(id, valueAfterData);
						}

					});

					$(".ownerDetails").hide();
					var dateFields = $('.Moredatepicker');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					if ($("#taxPayerFlag").val() == 'Y') {
						$("#pandiv1").show();
					} else {
						$("#pandiv1").hide();
					}

					$("#taxPayerFlag").change(function() {

						var chk = $('#taxPayerFlag').val();
						if (chk == "Y") {

							$("#pandiv1").show();
							$("#taxPayerFlag").val("Y");
							$("#panNo").data('rule-required', true);

						} else {
							$("#pandiv1").hide();
							$("#taxPayerFlag").val("N");
							$("#panNo").data('rule-required', false);
						}

					});

					$('.p_element').hide();
					$("#trmGroup1").change(
							function(e) {

								if ($('#trmGroup1 option:selected')
										.attr('code') == "H") {
									$('#trans-restaurant').hide();
									$('#trans-hotel').show();
									$('#hotelNumber').prop("disabled", false);
								} else if ($('#trmGroup1 option:selected')
										.attr('code') == "RFC") {
									$('#trans-hotel').hide();
									$('#trans-restaurant').show();
									$('#restaurantNumber').prop("disabled",
											false);
								} else {
									$('#trans-restaurant').hide();
									$('#trans-hotel').hide();
									$('#hotelNumber').prop("disabled", true);
									$('#restaurantNumber').prop("disabled",
											true);
								}

							});

					$("#fromtoperiod").hide();
					$("#numberofday").hide();
					$("#plumberName").prop("readonly", true);
					$("#chargesDiv").hide();
					if ($("#typeOfApplication").val() == 'T') {

						$("#fromtoperiod").show();
						$("#numberofday").show();

					} else {
						$("#fromdate").val("");
						$("#todate").val("");
						$("#fromtoperiod").hide();
						$("#numberofday").hide();
					}

					if ($('#free').val() == 'Y' || $('#free').val() == 'N') {
						$('#divSubmit').show();
						$('#chekListChargeId').hide();
					}

					if ($('#free').val() == 'O') {
						$('#divSubmit').hide();
						$('#chekListChargeId').show();
					}
					$("#tbl2").hide();

					if ($('#bplNo').val() == 'N') {
						$("#bpldiv").hide();
					}
					if ($('#bplNo').val() == 'Y') {
						$("#bpldiv").show();
						$("#bplNo").data('rule-required', true);
					} else {
						$("#bpldiv").hide();
						$("#bplNo").data('rule-required', false);
					}
					if ($('#existingNumber').val() == 'Y') {
						$("#ExistingConnection").attr("checked", "checked");
					}
					var checked = $('#ExistingConnection').is(':checked');
					if (checked) {
						$("#tbl2").show();
						$("#consumerNo0").data('rule-required', true);
					} else {

						$("#tbl2").hide();
						$("#consumerNo0").data('rule-required', false);
					}
					if ($('#applicantType option:selected').attr('code') == "O"
							|| $('#applicantType option:selected').attr('code') == "G"
							|| $('#applicantType option:selected').attr('code') == "TR") {
						$("#OrgName").show();
						$("#orgName").data('rule-required', true);
						if ($('#applicantType option:selected').attr('code') == "O") {
							$("#orgLabel").show();
							$("#grpLabel").hide();
							$("#trustLabel").hide();
						}
						if ($('#applicantType option:selected').attr('code') == "G") {
							$("#grpLabel").show();
							$("#orgLabel").hide();
							$("#trustLabel").hide();
						}
						if ($('#applicantType option:selected').attr('code') == "TR") {
							$("#trustLabel").show();
							$("#grpLabel").hide();
							$("#orgLabel").hide();
						}
					} else {
						$("#orgName").val("");
						$("#OrgName").hide();
						$("#orgName").data('rule-required', false);
					}
					if ($('#applicantType option:selected').attr('code') == "G"
							|| $('#applicantType option:selected').attr('code') == "g") {
						$(".ownerDetails").show();
						$('[id^="ownerTitle"]').data('rule-required', true);
						$('[id^="ownerFName"]').data('rule-required', true);
						$('[id^="ownerLName"]').data('rule-required', true);
						$('[id^="ownerGender"]').data('rule-required', true);
					}

					$("input[name='csmrInfo.csPtype']").click(function() {
						
						if ($(this).attr('value') == 'U') {
							$("#ulbPlumber").show();
							$("#licensePlumber").hide();
							$("#licPlumber").attr("disabled", true);
							$("#plumber").attr("disabled", false);

						} else if ($(this).attr('value') == 'L') {
							$("#ulbPlumber").hide();
							$("#licensePlumber").show();
							$("#plumber").attr("disabled", true);
							$("#licPlumber").attr("disabled", false);
						}
					});

					if ($("input[name='csmrInfo.csPtype']").attr('value') == 'U') {

						$("#ulbPlumber").show();
						$("#licensePlumber").hide();
						$("#licPlumber").attr("disabled", true);
					} else {
						$("#ulbPlumber").hide();
						$("#licensePlumber").show();
						$("#plumber").attr("disabled", true);
					}

					$("#billing").click(function() {
						var chk = $('#billing').is(':checked');
						if (chk) {

							$("#hideBillingDetails").hide();
							$('#hiddenBillingSame').val("Y");
							$("#billingAreaName").data('rule-required', false);
							$("#billingPinCode").data('rule-required', false);

						} else {
							$('#hiddenBillingSame').val("N")
							$("#hideBillingDetails").show();
							$("#billingAreaName").data('rule-required', true);
							$("#billingPinCode").data('rule-required', true);
						}

					});

					$("#ExistingConnection").click(function() {
						var checked = $('#ExistingConnection').is(':checked');
						if (checked) {
							$("#consumerNo").prop("readonly", false);
							$("#connectionSize0").attr("readonly", false);
							$("#connectionSize0").val(0);
							$("#noOfTaps0").attr("readonly", false);
							$("#deleteConnection").removeClass("disabled");
							$("#addConnection").removeClass("disabled");
							$("#tbl2").show();
							$("#consumerNo0").data('rule-required', true);
						} else {
							$("#consumerNo0").val("");
							$("#connectionSize0").val("");
							$("#noOfTaps0").val("");
							$("#connectionSize").prop("disabled", false);
							$("#noOfTaps").prop("readonly", false);
							$("#consumerNo").prop("readonly", true);
							$("#deleteConnection").addClass("disabled");
							$("#addConnection").addClass("disabled");
							var cnt = $('#tbl2 tr').length - 1;
							while (cnt > 1) {
								$('#tbl2 tr:last-child').remove();
								cnt--;
							}
							$("#tbl2").hide();
							$("#consumerNo0").data('rule-required', false);
						}
					});
					$("#typeOfApplication").change(function(e) {

						if ($("#typeOfApplication").val() == 'T') {
							$("#fromtoperiod").show();
							$("#numberofday").show();
							$("#fromdate").data('rule-required', true);
							$("#todate").data('rule-required', true);
						} else {
							$("#fromdate").val("");
							$("#todate").val("");
							$("#fromtoperiod").hide();
							$("#numberofday").hide();
							$("#fromdate").data('rule-required', false);
							$("#todate").data('rule-required', false);
						}
					});

					$("#bplNo").change(function(e) {
						

						if ($("#bplNo").val() == 'Y') {
							$("#bpldiv").show();
							$("#bplNumber").val('');
							$("#bplNo").data('rule-required', true);
						} else {
							$("#bpldiv").hide();
							$("#bplNo").data('rule-required', false);
						}
					});

					$("#applicantType").change(
							function(e) {
								

								if ($('#applicantType option:selected').attr(
										'code') == "O"
										|| $('#applicantType option:selected')
												.attr('code') == "G"
										|| $('#applicantType option:selected')
												.attr('code') == "TR") {
									$("#OrgName").show();
									$("#orgName").data('rule-required', true);

									if ($('#applicantType option:selected')
											.attr('code') == "O") {
										$("#orgLabel").show();
										$("#grpLabel").hide();
										$("#trustLabel").hide();
									}
									if ($('#applicantType option:selected')
											.attr('code') == "G") {
										$("#orgLabel").hide();
										$("#grpLabel").show();
										$("#trustLabel").hide();
									}
									if ($('#applicantType option:selected')
											.attr('code') == "TR") {
										$("#orgLabel").hide();
										$("#grpLabel").hide();
										$("#trustLabel").show();
									}
								} else {
									$("#orgName").val("");
									$("#OrgName").hide();
									$("#orgName").data('rule-required', false);
								}
								if ($('#applicantType option:selected').attr(
										'code') == "G"
										|| $('#applicantType option:selected')
												.attr('code') == "g") {
									$(".ownerDetails").show();
									$('[id^="ownerTitle"]').data(
											'rule-required', true);
									$('[id^="ownerFName"]').data(
											'rule-required', true);
									$('[id^="ownerLName"]').data(
											'rule-required', true);
									$('[id^="ownerGender"]').data(
											'rule-required', true);
								} else {
									$(".ownerDetails").hide();
									$('[id^="ownerTitle"]').data(
											'rule-required', false);
									$('[id^="ownerFName"]').data(
											'rule-required', false);
									$('[id^="ownerLName"]').data(
											'rule-required', false);
									$('[id^="ownerGender"]').data(
											'rule-required', false);
								}
							});

					$(".datepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : 0,
					});
					$(".Moredatepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : '0',
					});
					$(".disDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								minDate : 0,
								onSelect : function(selected) {
									$(".distDate").datepicker("option",
											"minDate", selected)
								}
							});
					$(".distDate").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : 0,
					});
					var dateFields = $('.disDate,.distDate');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					$("#resetform").click(function() {
						this.form.reset();
						resetWaterForm();
						resetOtherFields();
					});

					var chkConsumer = $('#isConsumer').is(':checked');
					if (chkConsumer) {

						$("#hideConsumerDetails").hide();
						$("#cbillingAreaName").data('rule-required', false);
						$("#cbillingPinCode").data('rule-required', false);
						$("#csName").data('rule-required', false);
						$("#csLname").data('rule-required', false);
						$("#csTitle").data('rule-required', false);

					} else {

						$("#hideConsumerDetails").show();
						$("#cbillingAreaName").data('rule-required', true);
						$("#cbillingPinCode").data('rule-required', true);
						$("#csName").data('rule-required', true);
						$("#csLname").data('rule-required', true);
						$("#csTitle").data('rule-required', true);
					}

					$("#isConsumer").click(
							function() {

								var chkConsumer = $('#isConsumer').is(
										':checked');
								if (chkConsumer) {
									$('#hiddenConsumerSame').val("Y");
									$("#hideConsumerDetails").hide();
									$("#cbillingAreaName").data(
											'rule-required', false);
									$("#cbillingPinCode").data('rule-required',
											false);

								} else {
									$('#hiddenConsumerSame').val("N");
									$("#hideConsumerDetails").show();
									$("#cbillingAreaName").data(
											'rule-required', true);
									$("#cbillingPinCode").data('rule-required',
											true);
								}

							});

				});

function saveWaterForm(element) {
	
	if ($('#accept').is(':checked')) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(
					element,
					"Your application for new Water connection saved successfully!",
					'IllegalToLegalConnection.html?PrintReport',
					'SaveAndViewApplication');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			return saveOrUpdateForm(
					element,
					"Your application for new Water connection saved successfully!",
					'IllegalToLegalConnection.html?redirectToPay',
					'SaveAndViewApplication');
		} else {
			return saveOrUpdateForm(
					element,
					"Your application for new Water connection saved successfully!",
					'CitizenHome.html', 'SaveAndViewApplication');
		}
	} else {
		showErrormsgboxTitle(getLocalMessage("water.accept.terms.cond"));
	}
}

function showViewFormJsp(element) {

	$.fancybox.close();
	var formName = "frmNewWaterForm";
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'IllegalToLegalConnection.html?ShowViewForm';
	var errorList = validateFormDetails();
	if (errorList == 0) {
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var divName = '#validationDiv';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$(divName).show();
		$.fancybox.close();
	} else {
		displayErrorsOnPage(errorList);
	}

}

function editApplicationForm(element) {
	var requestData = {};
	var URL = 'IllegalToLegalConnection.html?EditApplicationForm';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	var divName = '#validationDiv';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	$(divName).show();
}

function continueForPayment(element) {
	if ($("#onlineOfflinePay").val() == 'N'
			|| $("#onlineOfflinePay").val() == 'P') {
		window.open('IllegalToLegalConnection.html?PrintReport', '_blank');
	} else if ($("#onlineOfflinePay").val() == 'Y') {
		closebox("", "IllegalToLegalConnection.html?redirectToPay");
	}
}

function removeRow(cnt) {
	if ($('#tbl1 tr').size() > 2) {
		$("#tr" + cnt).remove();
		reorderOwner();
		cnt--;
	} else {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
	}
}

function removeConnection(cntConnection) {
	if ($('#tbl2 tr').size() > 2) {

		$("#row" + cntConnection).remove();
		var data = {
			"deletedRow" : cntConnection
		};
		var URL = 'IllegalToLegalConnection.html?deletedLinkCCnRow';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);

		reorderConnection();
		cntConnection--;

	} else {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
	}

}

function reorderConnection() {
	$('.appendableClass')
			.each(
					function(i) {

						// Ids
						$(this).find("input:text:eq(0)").attr("id",
								"consumerNo" + (i));
						$(this).find("input:text:eq(0)").attr("onblur",
								"checkDuplicateCcn(" + (i) + ")");
						$(this).find("input:text:eq(1)").attr("id",
								"noOfTaps" + (i));
						$(this).find("select:eq(0)").attr("id",
								"connectionSize" + (i));
						$(this).closest("tr").attr("id", "row" + (i));
						// names
						$(this).find("input:text:eq(0)").attr("name",
								"csmrInfo.linkDetails[" + (i) + "].lcOldccn");
						$(this).find("input:text:eq(1)").attr("name",
								"csmrInfo.linkDetails[" + (i) + "].lcOldtaps");
						$(this).find("select:eq(0)").attr(
								"name",
								"csmrInfo.linkDetails[" + (i)
										+ "].lcOldccnsize");
						$(this).find("#deleteConnection").attr("onclick",
								"removeConnection(" + (i) + ")");
					});

}

function reorderOwner() {
	$('.ownerClass')
			.each(
					function(i) {
						// Ids
						$(this).find("input:text:eq(0)").attr("id",
								"ownerFName" + (i));
						$(this).find("input:text:eq(1)").attr("id",
								"ownerMName" + (i));
						$(this).find("input:text:eq(2)").attr("id",
								"ownerLName" + (i));
						$(this).find("input:text:eq(3)").attr("id",
								"ownerUID" + (i));
						$(this).find("select:eq(0)").attr("id",
								"ownerTitle" + (i));
						$(this).find("select:eq(1)").attr("id",
								"ownerGender" + (i));
						$(this).closest("tr").attr("id", "tr" + (i));
						// names
						$(this).find("input:text:eq(0)").attr(
								"name",
								"csmrInfo.ownerList[" + (i)
										+ "].ownerFirstName");
						$(this).find("input:text:eq(1)").attr(
								"name",
								"csmrInfo.ownerList[" + (i)
										+ "].ownerMiddleName");
						$(this).find("input:text:eq(2)")
								.attr(
										"name",
										"csmrInfo.ownerList[" + (i)
												+ "].ownerLastName");
						$(this).find("input:text:eq(3)").attr("name",
								"csmrInfo.ownerList[" + (i) + "].caoUID");
						$(this).find("select:eq(0)").attr("name",
								"csmrInfo.ownerList[" + (i) + "].ownerTitle");
						$(this).find("select:eq(1)").attr("name",
								"csmrInfo.ownerList[" + (i) + "].gender");
						$(this).find("#deleteOwner").attr("onclick",
								"removeRow(" + (i) + ")");
					});
}

function getChecklistAndCharges(element) {

	var errorList = [];
	var flag = false;
	if ($("#frmNewWaterForm").valid()) {
		$("#csOname").prop("disabled", false);
		$("#opincode").prop("disabled", false);
		$("#csOadd").prop("disabled", false);
		$("#mobileNo").prop("disabled", false);
		$("#emailId").prop("disabled", false);
		$("#csOGender").prop("disabled", false);

	}
	if ($("#propOutStanding").val() == 'Y' && $("#bplNo").val() == 'N') {
		errorList
				.push(getLocalMessage('Property Dues Exist Could Not Proceed For New Connection'));
		displayErrorsOnPage(errorList);
	}

	if (errorList.length == 0)
		var errorList = validateFormDetails();
	if (errorList.length == 0) {

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'IllegalToLegalConnection.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');

		if (returnData) {
			var divName = '#validationDiv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			var free = $('#free').val();
			if (free == 'N' || free == 'Y') {
				$('#chekListChargeId').hide();
				$('a[href$="#Applicant"]').addClass('collapsed');
				$('#Applicant').removeClass('in');
			} else {
				$('#chekListChargeId').show();
				$('.required-control').next().children().addClass(
						'mandColorClass');
			}

		}
	} else {
		displayErrorsOnPage(errorList);
	}

}

function resetWaterForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#frmNewWaterForm").validate().resetForm();
}
function clearCheckListAndCharges(id, value) {

	var URL = 'IllegalToLegalConnection.html?clearCheckListAndCharges';
	var returnData = __doAjaxRequest(URL, 'POST', '', false);
	var divName = '#validationDiv';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	$(divName).show();
	$("#" + id).val(value);
	if (id == 'trmGroup1') {
		$("#trmGroup1").val("0");
		$("#trmGroup2").val("0");
		$("#trmGroup3").val("0");
		$("#trmGroup4").val("0");
	}
	if (id == 'trmGroup2') {
		$("#" + id).val("0");
		$("#trmGroup1").val("0");
	}

}

function getPropertyDetails(element) {

	var propertyNo = $("#propertyNo").val();
	var theForm = '#frmNewWaterForm';
	if (propertyNo != '') {
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'IllegalToLegalConnection.html?getPropertyDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var errMsg = returnData["errMsg"];
		if (errMsg != '' && errMsg != undefined) {
			var errorList = [];
			errorList.push(errMsg);
			showMessageOnSubmit(errMsg, 'IllegalToLegalConnection.html');
		} else {
			$(formDivName).html(returnData);
			prepareDateTag();
		}
	}

}

function checkDuplicateCcn(index) {
	
	var ccnNo = $("#consumerNo" + index).val().trim();
	var duplicate = '';
	var myarray = new Array();
	for (i = 0; i < index; i++) {
		myarray[i] = $("#consumerNo" + i).val();
	}

	if (myarray.includes(ccnNo)) {
		duplicate = 'There is an existing connection with same number';
	}

	if (duplicate != '') {
		$("#consumerNo" + index).val('');
		$("#connectionSize" + index).val('');
		var errorList = [];
		errorList.push(duplicate);
		displayErrorsOnPage(errorList);
	} else {
		if (ccnNo != '') {
			var divName = formDivName;
			var requestData = {
				"ccnNo" : ccnNo,
			}
			var response = __doAjaxRequest(
					'IllegalToLegalConnection.html?getConnectionDetails',
					'post', requestData, false, 'json');

			if (response.csIdn != 0) {
				$("#connectionSize" + index).val(response.csCcnsize);
				$("#connectionSize" + index).attr("readonly", true);
				$("#noOfTaps" + index).val();
				$("#noOfTaps" + index).attr("readonly", true);
			} else {
				var errorList = [];
				$("#consumerNo" + index).val('');
				errorList
						.push(getLocalMessage('water.valid.check.connection.number'));
				displayErrorsOnPage(errorList);
			}
		}

	}
}

function getConnectionSize() {

	var bplNo = $("#bplNo").val();
	if (bplNo != '') {
		if (bplNo == 'Y') {
			$("#isBhagirathi").show();
			$("#notBhagirathi").hide();
			$("#withoutBhagiRathi").attr("disabled", true);
			$("#withBhagiRathi").attr("disabled", false);
		} else {
			$("#isBhagirathi").hide();
			$("#notBhagirathi").show();
			$("#withBhagiRathi").attr("disabled", true);
			$("#withoutBhagiRathi").attr("disabled", false);
		}
	}
}

function validateBhagirathiConnection() {

	var bplNo = $("#bplNo").val();
	if (bplNo != '') {
		var withBhagiRathi = $("#withBhagiRathi").val();

		if (bplNo == 'Y') {
			if (withBhagiRathi != '') {

				var bhagiRathi = $("#withBhagiRathi").find("option:selected")
						.attr('code');
				if (bhagiRathi != '0.5') {
					var errorList = [];
					errorList
							.push(getLocalMessage('water.connection.bagh.con'));
					displayErrorsOnPage(errorList);
				}
			}
		}
	} else {
		$("#withoutBhagiRathi").val('');
		var errorList = [];
		errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
		displayErrorsOnPage(errorList);
	}
}

function showMessageOnSubmit(message, redirectURL) {

	var errMsgDiv = '.msg-dialog-box';
	var cls = "Ok";

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

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

function proceed() {
	window.location.href = "IllegalToLegalConnection.html";
}

function fnValidatePAN(Obj) {
	
	$('.error-div').hide();
	var errorList = [];
	Obj = $('#panNo').val();
	if (Obj != "") {
		ObjVal = Obj;
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = ObjVal.substring(3, 4);
		if (ObjVal.search(panPat) == -1) {
			errorList.push('water.invalid.pan.num');
		} else if (code.test(code_chk) == false) {
			errorList.push('water.invalid.pan.num');
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	return errorList;
}

function validateFormDetails() {
	var errorList = [];
	var propertyNo = $.trim($('#propertyNo').val());
	var bplNo = $("#bplNo").val();
	var bplNumber = $("#bplNumber").val();
	var csOname = $("#csOname").val();
	if (propertyNo == '') {
		errorList.push(getLocalMessage('water.select.prop.num'));
	}

	if (bplNo == '') {
		errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
	} else {
		if (bplNo == 'Y' && bplNumber == '')
			errorList.push(getLocalMessage('Please Enter BPL No.'));
	}
	if (csOname == '') {
		errorList.push(getLocalMessage('water.enter.owner.name'));
	}
	var chkConsumer = $('#isConsumer').is(':checked');
	if (!chkConsumer) {
		var csFirstName = $("#csFirstName").val().trim();
		var csAddress1 = $("#csAddress1").val().trim();
		var csPinCode = $("#csPinCode").val();
		var csMobileNo = $("#csMobileNo").val().trim();

		if (csFirstName == '') {
			errorList.push(getLocalMessage('water.enter.consumer.name'));
		}
		if (csAddress1 == '') {
			errorList.push(getLocalMessage('water.enter.consumer.adddress'));
		}
		if (csPinCode == '') {
			errorList.push(getLocalMessage('water.validation.OpinCode'));
		}
		if (csMobileNo == '') {
			errorList
					.push(getLocalMessage('water.validation.Applicantentermobileno'));
		}

	}
	var chk = $('#billing').is(':checked');
	if (!chk) {
		var billingAreaName = $("#billingAreaName").val().trim();
		var billingPinCode = $("#billingPinCode").val().trim();

		if (billingAreaName == '') {
			errorList.push(getLocalMessage('water.enter.bill.area.name'));
		}
		if (billingPinCode == '') {
			errorList.push(getLocalMessage('water.enter.bill.pincode'));
		}

	}

	var typeOfApplication = $("#typeOfApplication").val();
	var trmGroup1 = $("#trmGroup1").val();

	if (typeOfApplication == '') {
		errorList.push(getLocalMessage('water.validation.typeApplication'));
	}

	if (trmGroup1 == 0) {
		errorList.push(getLocalMessage('water.validation.tariff'));
	}

	else {
		if ($('#trmGroup1 option:selected').attr('code') == "H") {
			var hotelNumber = $("#hotelNumber").val().trim();
			if (hotelNumber == '') {
				errorList.push(getLocalMessage('water.enter.rooms'));
			}
		} else if ($('#trmGroup1 option:selected').attr('code') == "RFC") {
			var restaurantNumber = $("#restaurantNumber").val().trim();
			if (restaurantNumber == '') {
				errorList.push(getLocalMessage('water.enter.table.num'));
			}
		}
	}
	if (bplNo != '') {
		if (bplNo == 'N') {
			var withoutBhagiRathi = $("#withoutBhagiRathi").val();
			if (withoutBhagiRathi == 0) {
				errorList.push(getLocalMessage('water.sel.connectionsize'));
			}
		} else {
			var withBhagiRathi = $("#withBhagiRathi").val();
			if (withBhagiRathi == 0) {
				errorList.push(getLocalMessage('water.sel.connectionsize'));
			}
		}
	}

	var csTYpe = document
			.querySelector('input[name="csmrInfo.csPtype"]:checked').value;

	if (csTYpe == 'U') {
		var plumber = $("#plumber").val();
		if (plumber == '') {
			errorList.push(getLocalMessage('water.select.plumber.name'));
		}
	}
	if (csTYpe == 'L') {
		var licPlumber = $("#licPlumber").val();
		if (licPlumber == '') {
			errorList.push(getLocalMessage('water.select.plumber.name'));
		}
	}

	var checked = $('#ExistingConnection').is(':checked');
	if (checked) {
		$("#tbl2 tbody tr")
				.each(
						function(i) {
							var consumerNo = $("#consumerNo" + i).val().trim();
							if (consumerNo == '') {
								errorList
										.push(getLocalMessage('water.select.connec.entry.num')
												+ (i + 1));
							}
						});
	}
	return errorList;
}
function clearInput(obj) {
	$(obj).val('');
}

function showConfirmBoxForSave(obj) {
	var errMsgDiv = '.child-popup-dialog';
	var message = '';
	var no = getLocalMessage('eip.commons.no');

	var yes = getLocalMessage('eip.commons.yes');

	var sure = getLocalMessage('water.are.sure');

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>' + sure
			+ '</h5>';

	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ yes
			+ '\'  id=\'btnNo\' onclick="showViewFormJsp(\''
			+ obj
			+ '\')"/>'
			+ '<input type=\'button\' class= "btn btn-danger" value=\''
			+ no
			+ '\'  id=\'no\' ' + ' onclick="closeBox()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}
function closeBox() {
	$.fancybox.close();
	$('#frmNewWaterForm').trigger("reset");

}

function getNoticeDetails(element) {

	var csIllegalNoticeNo = $("#csIllegalNoticeNo").val();
	var theForm = '#frmNewWaterForm';
	if (propertyNo != '') {
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'IllegalToLegalConnection.html?fetchConnectionByIllegalNoticeNo';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var errMsg = returnData["errMsg"];
		if (errMsg != '' && errMsg != undefined) {
			var errorList = [];
			errorList.push(errMsg);
			showMessageOnSubmit(errMsg, 'IllegalToLegalConnection.html');
		} else {
			$(formDivName).html(returnData);
			$("#csIllegalNoticeNo").attr('readonly', true);
			prepareDateTag();
		}
	}

}

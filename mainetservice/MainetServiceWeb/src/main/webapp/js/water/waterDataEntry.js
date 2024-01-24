$(document)
		.ready(
				function() {

					$('#waterDataEntry').validate({
						onkeyup : function(element) {
							this.element(element);
							console.log('onkeyup fired');
						},
						onfocusout : function(element) {
							this.element(element);
							console.log('onfocusout fired');
						}
					});
					
					$("#meterDetails").hide();

					if (($('option:selected', $("#csMeteredccn")).attr('code')) == 'MTR') {
						$("#meterDetails").show();
					} else {
						$("#meterDetails").hide();
					}

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

					var dateFields = $('.Moredatepicker');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					$("#fromtoperiod").hide();

					if ($("#typeOfApplication").val() == 'T') {

						$("#fromtoperiod").show();
						$("#numberofday").show();

					} else {
						$("#fromdate").val("");
						$("#todate").val("");
						$("#fromtoperiod").hide();
						$("#numberofday").hide();
					}

					$("#tbl2").hide();
					if ($("#modeType").val() == 'C') {
						var checked = $('#ExistingConnection').val();
						if (checked == "Y") {
							$("#tbl2").show();
							$("#consumerNo0").data('rule-required', true);
							// $("#connectionSize0").prop("readonly", "");
							// $("#noOfTaps0").prop("readonly", "");
						} else {
							$("#tbl2").hide();
							$("#consumerNo0").data('rule-required', false);
						}
					} else {
						var checked = $('#ExistingConnection').val();
						if (checked == "Y") {
							$("#tbl2").show();
							$("#tbl2 tbody tr").each(
									function(i) {
										$("#connectionSize" + i).attr(
												"readonly", true);
										$("#noOfTaps" + i).attr("readonly",
												true);
									});
						}
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

					$("#ExistingConnection").change(function(e) {
						
						var checked = $('#ExistingConnection').val();
						if (checked == "Y") {

							$("#consumerNo0").prop("readonly", false);
							$("#connectionSize0").attr("readonly", false);
							$("#connectionSize0").val(0);
							$("#noOfTaps0").attr("readonly", false);
							$("#deleteConnection").removeClass("disabled");
							$("#addConnection").removeClass("disabled");
							/* $("#consumerDiv").show(); */
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
						} else {
							$("#fromdate").val("");
							$("#todate").val("");
							$("#fromtoperiod").hide();
							$("#numberofday").hide();
						}
					});

					var dateFields = $('.Moredatepicker,.datepicker,.installdatepicker,.disDate,.distDate,.Mostdatepicker');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					$(".installdatepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d',
					});

					$(".datepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d',
					});
					$(".Moredatepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d'
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

					$(".Mostdatepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : new Date(),
					});
					/*
					 * var chkConsumer = $('#isConsumer').is(':checked'); if
					 * (chkConsumer) {
					 * 
					 * $("#hideConsumerDetails").hide();
					 * $("#cbillingAreaName").data('rule-required', false);
					 * $("#cbillingPinCode").data('rule-required', false);
					 * $("#csName").data('rule-required', false);
					 * $("#csLname").data('rule-required', false);
					 * $("#csTitle").data('rule-required', false); } else {
					 * 
					 * $("#hideConsumerDetails").show();
					 * $("#cbillingAreaName").data('rule-required', true);
					 * $("#cbillingPinCode").data('rule-required', true);
					 * $("#csName").data('rule-required', true);
					 * $("#csLname").data('rule-required', true);
					 * $("#csTitle").data('rule-required', true); }
					 * 
					 * $("#isConsumer").click(function() {
					 * 
					 * var chkConsumer = $('#isConsumer').is(':checked'); if
					 * (chkConsumer) { $('#hiddenConsumerSame').val("Y");
					 * $("#hideConsumerDetails").hide();
					 * $("#cbillingAreaName").data('rule-required', false);
					 * $("#cbillingPinCode").data('rule-required', false); }
					 * else { $('#hiddenConsumerSame').val("N");
					 * $("#hideConsumerDetails").show();
					 * $("#cbillingAreaName").data('rule-required', true);
					 * $("#cbillingPinCode").data('rule-required', true); }
					 * 
					 * });
					 */

				});

function yearLength() {
	var frmdateFields = $('#depositDate');

	frmdateFields.each(function() {

		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})

}

function meterDetail() {
	if (($('option:selected', $("#csMeteredccn")).attr('code')) == 'MTR') {
		$("#meterDetails").show();
	} else {
		$("#meterDetails").hide();
	}
}

function showHideBPL(value) {
	if (value == 'Y') {
		$('#bpldiv').show();
	} else {
		$('#bpldiv').hide();
	}
}

$("#pcDate").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});

$('body').on('focus', ".hasNumber", function() {
	$('.hasNumber').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

$("#meterInstallationDate").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});
function saveWaterDataEntry(element) {
	showloader(true);
	setTimeout(function(){
	var formName = "waterDataEntry";
	var theForm = '#' + formName;
	var errorList = [];
	errorList = fnValidatePAN(null);
	var connList = [];
	if (errorList.length == 0) {
		var checked = $('#ExistingConnection').val();
		if (checked == "Y") {
			$("#tbl2 tbody .appendableClass")
					.each(
							function(i) {
								var consumerNo = $("#consumerNo" + i).val();

								if (consumerNo == '') {
									errorList
											.push(getLocalMessage('water.select.connec.entry.num.')
													+ (i + 1));
								}
							});
		}
	}
	if ($("#typeOfApplication").val() == 'T') {
		
		var fromdate = $("#fromdate").val();
		var todate = $("#todate").val();

		if (fromdate == '') {
			errorList
					.push(getLocalMessage('water.dataentry.select.from.period'));
		}
		if (todate == '') {
			errorList.push(getLocalMessage('water.dataentry.select.to.period'));
		}

	}
	var errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'WaterDataEntrySuite.html?proceedNext';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		$(formDivName).html(returnData);
		prepareDateTag();
		yearLength();
		$("html, body").animate({
			scrollTop : 0
		}, "slow");
	}
	},2);
}

function backToEntry(element) {
	var theForm = '#WaterDataEntrySuiteOutstanding';
	var data = __serializeForm(theForm);
	var URL = 'WaterDataEntrySuite.html?back';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);

	$(formDivName).html(returnData);
	$("#propertyNo").prop("readonly", true);
}

function saveFrom(element) {
	showloader(true);
	setTimeout(function(){return saveOrUpdateForm(element, "Your Data saved successfully!",
			"WaterDataEntrySuite.html", 'saveform');},2);
}

function saveFormWithOutArrears(element) {
	showloader(true);
	setTimeout(function(){
	var errorList = []
	var errorList = validateForm(errorList)
	errorList = fnValidatePAN(null);
	var connList = [];
	if (errorList.length == 0) {
		var checked = $('#ExistingConnection').val();
		var propertyNo = $('#propertyNo').val();

		if (checked == "Y") {
			$("#tbl2 tbody tr")
					.each(
							function(i) {
								var consumerNo = $("#consumerNo" + i).val();
								if (consumerNo == '') {
									errorList
											.push(getLocalMessage('water.select.connec.entry.num')
													+ (i + 1));
								}
							});
		}
	}
	if ($("#typeOfApplication").val() == 'T') {
		
		var fromdate = $("#fromdate").val();
		var todate = $("#todate").val();

		if (fromdate == '') {
			errorList
					.push(getLocalMessage('water.dataentry.select.from.period'));
		}
		if (todate == '') {
			errorList.push(getLocalMessage('water.dataentry.select.to.period'));
		}

	}
	var errorList = validateForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, "Your Data saved successfully!",
				"WaterDataEntrySuite.html", 'saveFormWithOutArrears');

	}
	},2);
}

function getYear(element) {

	/*
	 * $("#yearOfConstruc0").val(''); $("#proAssAcqDate").val('');
	 */
	showloader(true);
	setTimeout(function(){
	var theForm = '#WaterDataEntrySuiteOutstanding';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'WaterDataEntrySuite.html?financialYear';

	var returnData = __doAjaxRequestValidationAccor(element, URL, 'POST',
			requestData, false, 'html');
	$(formDivName).html(returnData);
	},2);
}

function getPropertyDetails(element) {
	showloader(true);
	setTimeout(function(){
	var propertyNo = $("#propertyNo").val();
	var theForm = '#waterDataEntry';
	if (propertyNo != '') {
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'WaterDataEntrySuite.html?getPropertyDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var errMsg = returnData["errMsg"];
		if (errMsg != '' && errMsg != undefined) {
			var errorList = [];
			errorList.push(errMsg);
			showMessageOnSubmit(errMsg, 'WaterDataEntrySuite.htm');
		} else {
			$(formDivName).html(returnData);
			prepareDateTag();
			$("#propertyNo").prop("readonly", true);
			$("#searchPropNum").prop('disabled', true);
		}
	}},2);
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
	
	$.fancybox.close();
	var errorList = [];
	var checkWardZonePrefix = checkWardZonePrefixUp();
	if (checkWardZonePrefix != "N") {
		var data = {
			"type" : "C"
		};
		var URL = 'WaterDataEntrySuite.html?form';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
		$('.content-page').html(returnData);
	} else {

		errorList.push(getLocalMessage("water.dataentry.set.ward.zone"));
		showErr(errorList);
	}
}

function removeConnection(cntConnection) {
	
	if ($('#tbl2 tr').size() > 2) {

		$("#row" + cntConnection).remove();
		var data = {
			"deletedRow" : cntConnection
		};
		var URL = 'WaterDataEntrySuite.html?deletedLinkCCnRow';
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

function ShowHideBplDetails(element) {
	
	var checkValue = $("input[name='csmrInfo.bplFlag']:checked").val();
	if (checkValue == "Y") {
		$('.bpl_element').show();
	} else {
		$('.bpl_element').hide();
	}

}

function fnValidatePAN(Obj) {
	
	$('.error-div').hide();
	var errorList = [];
	Obj = $('#panNo').val();
	if (Obj != "") {
		ObjVal = Obj;

		var panVal = $('#panNo').val();
		var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

		if (regpan.test(panVal)) {
			// valid pan card number
		} else {
			errorList.push('water.invalid.pan.num');
		}

		/*
		 * var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/; var code =
		 * /([C,P,H,F,A,T,B,L,J,G])/; var code_chk = ObjVal.substring(3, 4); if
		 * (ObjVal.search(panPat) == -1) { errorList.push('Invaild PAN Number');
		 * $('#advPanno').val(""); } else if (code.test(code_chk) == false) {
		 * errorList.push('Invaild PAN Number'); $('#advPanno').val(""); }
		 */

	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	return errorList;
};

function checkDuplicateCcn(index) {
	
	var ccnNo = $("#consumerNo" + index).val().trim();
	var duplicate = '';
	var myarray = new Array();
	if (ccnNo != '') {
		for (i = 0; i < index; i++) {
			myarray[i] = $("#consumerNo" + i).val();
		}
	}
	if (myarray.includes(ccnNo)) {
		duplicate = 'There is an existing connection with same number';
	}
	if (duplicate != '') {
		$("#consumerNo" + index).val('');
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
					'NewWaterConnectionForm.html?getConnectionDetails', 'post',
					requestData, false, 'json');

			if (response.csIdn != 0) {

				$("#connectionSize" + index).val(response.csCcnsize);
				$("#connectionSize" + index).attr("readonly", true);
				$("#noOfTaps" + index).val();
				$("#noOfTaps" + index).attr("readonly", true);
			} else {
				$("#consumerNo" + index).val('');
				$("#connectionSize" + index).attr("readonly", false);
				$("#connectionSize" + index).val(0);
				$("#noOfTaps" + index).val('');
				$("#noOfTaps" + index).attr("readonly", false);
				var errorList = [];
				errorList
						.push(getLocalMessage('changeofowner.searchCriteria.noValid'));
				displayErrorsOnPage(errorList);
			}
		}

	}
}

function resetConnDetails() {
	
	$("#propertyNo").val('');
	$("#propertyUsageType").val('');
	$("#csOname").val('');
	$("#csOadd").val('');
	$("#opincode").val('');
	$("#mobileNo").val('');
	$("#csOGender").val('');
	$("#aadharNo").val('');
	$("#emailId").val('');
	$("#propertyNo").prop("readonly", false);
	$("#searchPropNum").prop('disabled', false);
}
function validateForm(errorList) {
	
	var propertyNo = $.trim($('#propertyNo').val());
	var bplNo = $.trim($('#bplNo').val());
	var typeOfApplication = $.trim($('#typeOfApplication').val());
	var ExistingConnection = $.trim($('#ExistingConnection').val());
	var taxPayerFlag = $.trim($('#taxPayerFlag').val());
	var csCcnsize = $.trim($('#csCcnsize').val());
	var csMeteredccn = $.trim($('#csMeteredccn').val());
	var csCcnstatus = $.trim($('#csCcnstatus').val());
	var trmGroup1 = $.trim($('#trmGroup1').val());
	var chkConsumer = $('#CounsumerFlag').is(':checked');
	var propNoOptional = $('#propNoOptionalFlag').val();
	/*
	 * var codDwzid1 = $.trim($('#codDwzid1').val()); var codDwzid2 =
	 * $.trim($('#codDwzid2').val());
	 */

	if(propNoOptional != null && propNoOptional == 'Y'){
		if(chkConsumer == true){
			if (propertyNo == "" || propertyNo == '0' || propertyNo == undefined) {
				errorList
						.push(getLocalMessage('water.dataentry.validate.property.num'));
			}
		}
	}else{
		if (propertyNo == "" || propertyNo == '0' || propertyNo == undefined) {
			errorList
					.push(getLocalMessage('water.dataentry.validate.property.num'));
		}
	}
	if (bplNo == "" || bplNo == '0' || bplNo == undefined) {
		errorList
				.push(getLocalMessage('water.dataentry.validation.bhagirathi'));
	}
	if (typeOfApplication == "" || typeOfApplication == '0'
			|| typeOfApplication == undefined) {
		errorList.push(getLocalMessage('water.validation.typeOfApplication'));
	}
	if (ExistingConnection == "" || ExistingConnection == '0'
			|| ExistingConnection == undefined) {
		errorList
				.push(getLocalMessage('waterDataEntrySuitModel.validate.isExisting'));
	}
	if (taxPayerFlag == "" || taxPayerFlag == '0' || taxPayerFlag == undefined) {
		errorList
				.push(getLocalMessage('waterDataEntrySuitModel.validate.taxType'));
	}
	if (csCcnsize == "" || csCcnsize == '0' || csCcnsize == undefined) {
		errorList.push(getLocalMessage('water.validation.connSize'));
	}
	if (csMeteredccn == "" || csMeteredccn == '0' || csMeteredccn == undefined) {
		errorList.push(getLocalMessage('water.validation.connMeterType'));
	}
	if (csCcnstatus == "" || csCcnstatus == '0' || csCcnstatus == undefined) {
		errorList.push(getLocalMessage('water.validation.connCCnStatus'));
	}
	if (trmGroup1 == "" || trmGroup1 == '0' || trmGroup1 == undefined) {
		errorList
				.push(getLocalMessage('water.dataentry.validation.tariif.category'));
	}

	/*
	 * if (codDwzid1 == "" || codDwzid1 == '0' || codDwzid1 == undefined) {
	 * errorList.push(getLocalMessage('water.validation.selZone')); } if
	 * (codDwzid2 == "" || codDwzid2 == '0' || codDwzid2 == undefined) {
	 * errorList.push(getLocalMessage('water.report.validation.ward')); }
	 */

	return errorList;
}
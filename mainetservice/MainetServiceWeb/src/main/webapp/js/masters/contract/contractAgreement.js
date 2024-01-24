/**
 * apurva.salgaonkar
 */
var fileArray = [];
$(document)
		.ready(
				function() {
					
				
					//$("#Appreciation").hide();
					// $(".display-hide").not(".Non-Commercial").hide();
					// $(".Non-Commercial").show();
					// Defect #77032
					// $(".Commercial").hide();
					// $(".bank").hide();

					var dateFields = $('.dateClass');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});
					
					if ($("#hiddeMode").val() == "V"
							|| $("#hiddeMode").val() == "E") {

						if ($("input[name='contractMastDTO.contMode']:checked")
								.val() == "C") {
							if ($("#contrType").val() != 'CL') {
								if ($("#contrType").val() == "CW") {
									$("#nonCommercial").hide();
									$("#Renewal").hide();
								}
								$("#taxGroup").hide();
								$("#NoofInstallments").hide();
								$("#installments").hide();
								$(".Commercial").show();
								$(".bank").show();

							} else {
								$("#taxGroup").show();
								$("#NoofInstallments").show();
								$("#installments").show();
								$(".Commercial").show();
								$(".bank").show();
								$("#ContractType").attr("disabled", true);
							}

						}
						if ($("input[name='contractMastDTO.contMode']:checked")
								.val() == "N") {
							$("#taxGroup").hide();
							$("#NoofInstallments").hide();
							$("#installments").hide();
							$(".Commercial").hide();
							$(".Commercial input:text").val("");
							$(".Commercial select").val("");
							$(".Commercial input:text").val("");
							$(".Commercial select").val("");
							$(".bank").hide();
							$(".bank input:text").val("");
							$(".bank select").val("");
							$(".bank input:text").val("");
							$(".bank select").val("");
						}

					}
				
					$('.ContractMode').click(
							function() {

								// Defect #77032
								// $(".display-hide").not(".Commercial").hide();
								// $(".display-hide").not(".bank").hide();

								var ContractType = $.trim($("#ContractType")
										.val());
								if (ContractType == 0 || ContractType == "") {
									showAlertBoxForContractType();
								} else {
									var ContType = $("#ContractType").find(
											"option:selected").attr('code')
											.split(",");

									if ($(this).attr("value") == "C") {
										
										$("#Appreciation").show();
										if (ContType != undefined
												|| ContType != '') {
											if (ContType != "CL") {
												if (ContType == "CW") {
													$("#nonCommercial").hide();
													$("#Renewal").hide();
													$("#addDeposite").hide();
												}
												$("#taxGroup").hide();
												$("#NoofInstallments").hide();
												$("#installments").hide();
												$(".Commercial").show();
												$(".bank").show();
											} else {
												$("#taxGroup").show();
												$("#NoofInstallments").show();
												$("#installments").show();
												$(".Commercial").show();
												$(".bank").show();
											}
										}

										if (ContType == "CAH") {
											$("#nonCommercial").hide();
											$("#NoofInstallments").show();
											$("#installments").show();
											$(".Commercial").show();
											$(".bank").show();
										}

									}

									if ($(this).attr("value") == "N") {
										$("#Appreciation").hide();
										$("#taxGroup").hide();
										$("#NoofInstallments").hide();
										$("#installments").hide();
										$(".Commercial").hide();
										$(".Commercial input:text").val("");
										$(".Commercial select").val("");
										$(".bank").hide();
										$(".bank input:text").val("");
										$(".bank select").val("");
									}
									
								}
							});

					if ($("#hiddeMode").val() == "V") {

						$("#partyDetails :input").prop("disabled", true);
						$("#ContractAgreement :input,select").prop("disabled",
								true);
						$('.addCF3').attr('disabled', true);
						$('.addCF4').attr('disabled', true);
						$('.addCF5').attr('disabled', true);
						$('.addCF2').attr('disabled', true);
						$('.remCF2').attr('disabled', true);
						$('.remCF3').attr('disabled', true);
						$('.remCF4').attr('disabled', true);
						$('.remCF5').attr('disabled', true);
						$('.uploadbtn').attr('disabled', false);
						$(".backButton").prop('disabled', false);

					}
					/*
					 * if ($("#hiddeMode").val() == "C") {
					 * $("#contToPeriod").val(parseInt(15)); }
					 */

				});

$(document).ready(
		function() {
			$(".lessdatepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				maxDate : '-0d',
				changeYear : true,
			});

			$(".datepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true
			});

			$(".datepickerResulation").datepicker(
					{
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						maxDate : '-0d',
						changeYear : true,
						onSelect : function(selected) {
							$(".datepickerTender").datepicker("option",
									"minDate", selected);
						}
					});

			$(".datepickerTender").datepicker(
					{
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						maxDate : '-0d',
						changeYear : true,
						onSelect : function(selected) {
							$(".lessthancurrdatefrom").datepicker("option",
									"minDate", selected);
							checkTenderDate();
						}
					});

			$('.lessthancurrdateto').datepicker(
					{
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						onSelect : function(selected) {
							$(".Insdatepicker").datepicker("option", "maxDate",
									selected);
							checkToDate();
						}

					});

			$('.lessthancurrdatefrom').datepicker(
					{
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						onSelect : function(selected) {
							$(".lessthancurrdateto").datepicker("option",
									"minDate", selected);
							$(".Insdatepicker").datepicker("option", "minDate",
									selected);
							checkFromDate();
						}
					});
			if ($("#hiddeMode").val() == "V" || $("#hiddeMode").val() == "E") {
				var resolutionDate = $("#resolutionDate").val();
				$(".datepickerTender").datepicker("option", "minDate",
						resolutionDate);
				var tenderDate = $("#tenderDate").val();
				$(".lessthancurrdatefrom").datepicker("option", "minDate",
						tenderDate);
				var fromDate = $("#contractFromDate").val();
				$(".lessthancurrdateto").datepicker("option", "minDate",
						fromDate);
			}

			if ($("#hiddeMode").val() == "C") {
				$("#AgreementDate").datepicker("setDate", new Date());
			}

			var countdat = 0;
			$('body').on(
					'focus',
					".Insdatepicker",
					function() {
						$('.Insdatepicker').datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true
						});
						var fromDate = $("#contractFromDate").val();
						// var toDate = $("#contractToDate").val();
						var contToPeriod = $('#contToPeriod').val();
						if (fromDate != '' && countdat == 0
								&& contToPeriod != '') {
							countdat++;
							var dateParts = $("#contractFromDate").val().split(
									"/");

							var toDate = new Date(dateParts[2],
									dateParts[1] - 1, dateParts[0]);
							//D#98311 here make run time due date of installments 
							let contToPeriodUnitCode = $("#contToPeriodUnit").find("option:selected").attr('code');
							if(contToPeriodUnitCode == "Y"){
								//Year
								contToPeriod = Number(contToPeriod)*365;
								toDate.setDate(toDate.getDate()
										+ parseInt(contToPeriod));
								
							}else if(contToPeriodUnitCode == "M"){
								//Month
								contToPeriod = Number(contToPeriod)*30;
								toDate.setDate(toDate.getDate()
										+ parseInt(contToPeriod));
							}else{
								//Day
								toDate.setDate(toDate.getDate()
										+ parseInt(contToPeriod));	
							}
							
							
							var dateTo = formatDate(new Date(toDate));
							$(".Insdatepicker").datepicker("option", "minDate",
									fromDate);

							$(".Insdatepicker").datepicker("option", "maxDate",
									dateTo);

						}
					});
			$('body').on('focus', ".hasNumber", function() {
				$(".hasNumber").keyup(function(event) {
					this.value = this.value.replace(/[^0-9]/g, '');
				});
			});
			getAllTaxesBasedOnDept();
			if ($('#hideTaxId').val() != ''
					&& $('#hideTaxId').val() != undefined) {
				$("#taxId option[value='" + $('#hideTaxId').val() + "']").prop(
						'selected', 'selected');
				$('#taxId').trigger("chosen:updated");
			}

		});

function hideDetails() {
	if ($("#hiddeMode").val() == "C") {
		$("input[name='contractMastDTO.contMode']").removeAttr('checked');
		if ($("#ContractType").val() != "" || $("#ContractType").val() != 0) {
			var ContType = $("#ContractType").find("option:selected").attr(
					'code').split(",");

			if (ContType == "CW") {
				$("#nonCommercial").hide();
				$("#Renewal").hide();

				// new code
				$("#ContractPayment option[value='R']").attr("selected",
						"selected");

				$("#Renewal").show();

				$("#Renewal option[value='N']").attr("selected", "selected");
				// new code end

			} else {
				$("#nonCommercial").show();
				$("#Renewal").show();

				if (ContType == "CAH") {
					$("#nonCommercial").hide();
					// new code
					$("#ContractPayment option[value='R']").attr("selected",
							"selected");

					$("#Renewal").show();

					$("#Renewal option[value='N']")
							.attr("selected", "selected");
					// new code end

				}

			}
		}

	} else {
		var ContType = $("#ContractType").find("option:selected").attr('code')
				.split(",");

		if ($("input[name='contractMastDTO.contMode']:checked").val() == "C") {
			if (ContType != undefined || ContType != '') {
				if (ContType != "CL") {
					if (ContType == "CW") {
						$("#nonCommercial").hide();
						$("#Renewal").hide();
					} else {
						$("#nonCommercial").show();
						$("#Renewal").show();
					}
					$("#taxGroup").hide();
					$("#NoofInstallments").hide();
					$("#installments").hide();
					$(".Commercial").show();
					$(".bank").show();

				} else {
					$("#taxGroup").show();
					$("#NoofInstallments").show();
					$("#installments").show();
					$(".Commercial").show();
					$(".bank").show();

					var url = "ContractAgreement.html?getAllTaxesBasedOnDept";
					var deptId = $("#tndDept").val();
					if (deptId != '' && deptId != '0') {
						var postdata = "deptId=" + deptId;

						var json = __doAjaxRequest(url, 'POST', postdata,
								false, 'json');
						$('#taxId option').remove();
						var optionsAsString = "<option value='0'>select Tax</option>";
						for (var j = 0; j < json.length; j++) {

							optionsAsString += "<option value='"
									+ json[j].lookUpId + "'>"
									+ json[j].descLangFirst + "</option>";
						}
						$('#taxId').append(optionsAsString);
					} else {
						errorList
								.push(getLocalMessage("agreement.selectTndDept"));
						displayErrorsOnPage(errorList);

					}

				}
			}
		} else {
			if (ContType == "CW") {
				$("#nonCommercial").hide();
			} else {
				$("#nonCommercial").show();
			}
		}
	}

}

function getAllTaxesBasedOnDept() {
	var url = "ContractAgreement.html?getAllTaxesBasedOnDept";
	/* Defect #31617 --> loa No field should be visible for WMS department only */
	var value = $("#tndDept").find("option:selected").attr('code');
	if (value == "WMS") {
		$('#CAloanNo').removeClass('hidden');
	} else {
		$('#CAloanNo').addClass('hidden');
	}

	// var deptId = $(obj).val();
	var deptId = $("#tndDept").val();
	if (deptId != null && deptId != "") {
		var postdata = "deptId=" + deptId;

		var json = __doAjaxRequest(url, 'POST', postdata, false, 'json');
		$('#taxId option').remove();
		var optionsAsString = "<option value='0'>select Tax</option>";
		http: // localhost/MainetService/AdminHome.html?#
		for (var j = 0; j < json.length; j++) {

			optionsAsString += "<option value='" + json[j].lookUpId + "'>"
					+ json[j].descLangFirst + "</option>";
		}
		$('#taxId').append(optionsAsString);
		// Defect #32852-->commented because it's showing contract against
		// hoarding by default in all mode
		/* $("#ContractType option[code='CAH']").attr("selected", "selected"); */
		var ContType = $("#ContractType").find("option:selected").attr('code')
				.split(",");
		if (ContType == "CAH") {
			$("#nonCommercial").hide();
			// new code
			$("#ContractPayment option[value='R']")
					.attr("selected", "selected");

			$("#Renewal").show();

			$("#Renewal option[value='N']").attr("selected", "selected");

			$("#NoofInstallments").show();
			$("#installments").show();
			$(".Commercial").show();
			$(".bank").show();

			// new code end
			// $("#commercial").prop('checked', true);

		}
	}
}

function reorderTermCon() {
	$('.appendableTermConClass').each(
			function(i) {
				// Ids
				var count = i + 1;
				$(this).find("textarea:eq(0)").attr("id", "termCon" + (i));
				$(this).find("input:hidden:eq(0)").attr("id",
						"presentRow" + (i));
				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
				// names
				$(this).find("textarea:eq(0)").attr(
						"name",
						"contractMastDTO.contractTermsDetailList[" + (i)
								+ "].conttDescription");
				$(this).find("input:hidden:eq(0)").attr(
						"name",
						"contractMastDTO.contractTermsDetailList[" + (i)
								+ "].active");
				$(this).find("input:text:eq(0)").attr("value", count);

			});
}

// Save and Validation
function addPartyDetails() {
	var errorList = [];
	var divName = formDivName;
	var tndDeptCode = $("#tndDept").val();
	var tndVendorId = $("#tndVendorId").val();
	var contp2vType = $("#contp2vType").val();
	var BGAmount = $("#BGAmount").val();
	var BGAmountupdated = $("#SecurityDeposit").val();
	var agreementFromDate = $("#contractFromDate").val();
	
	if (tndDeptCode == null || tndDeptCode == '') {
		errorList.push(getLocalMessage('agreement.selectTndDept'));
	}
	if (agreementFromDate == "" || agreementFromDate == 0) {
		errorList.push(getLocalMessage('agreement.selectAgreementFromDate'));
	}
	

	var bg = Number(BGAmount);
	var bgU = Number(BGAmountupdated);

	if (BGAmount != "" && BGAmountupdated != "") {
		if (bg > bgU) {
			errorList
					.push("BG amount should be greater than or equal to previous amount");
		}
	}
	var AgreementDate = $.trim($("#AgreementDate").val());
	if (AgreementDate == 0 || AgreementDate == "")
		errorList.push(getLocalMessage("agreement.selectAgreementDate"));

	var TenderNo = $.trim($("#TenderNo").val());
	if (TenderNo == 0 || TenderNo == "")
		errorList.push(getLocalMessage("agreement.selectTndNumber"));

	var tenderDate = $.trim($("#tenderDate").val());
	if (tenderDate == 0 || tenderDate == "")
		errorList.push(getLocalMessage("agreement.selectTndDate"));

	var resulationNo = $.trim($("#resulationNo").val());
	if (resulationNo == 0 || resulationNo == "")
		errorList.push(getLocalMessage("agreement.selectResolutionNo"));

	var ContractType = $.trim($("#ContractType").val());
	if (ContractType == 0 || ContractType == "")
		errorList.push(getLocalMessage("agreement.selectType"));
	if (ContractType != undefined && ContractType != '0') {
		var ContType = $("#ContractType").find("option:selected").attr('code')
				.split(",");

	}

	 var contractFromDate = $.trim($("#contractFromDate").val()); /*
	 *if
	 * (contractFromDate == 0 || contractFromDate == "")
	 * errorList.push(getLocalMessage("agreement.enterContractFromDate"));
	 */

	var contToPeriod = $.trim($("#contToPeriod").val());
	if (contToPeriod == '' || contToPeriod == 0)
		errorList.push(getLocalMessage("agreement.enterContractToPeriod"));
	//D#90801
	let contToPeriodUnit = $("#contToPeriodUnit").val();
	if (contToPeriodUnit == "" || contToPeriodUnit == 0)
		errorList.push(getLocalMessage('agreement.enterContractToPeriodUnit'));

	/**
	 * Commented as per SUDA UAT Regarding WMS
	 */
	/*
	 * var ContractToDate = $.trim($("#contractToDate").val()); if
	 * (ContractToDate == 0 || ContractToDate == "")
	 * errorList.push(getLocalMessage("agreement.enterContractToDate"));
	 */
	
	//D#40625
	var ContractAmount = $.trim($("#ContractAmount").val());
	if (ContractAmount == 0 || ContractAmount == "")
		errorList.push(getLocalMessage("agreement.enterContractAmount"));
	
	
	var ContractMode = $.trim($(".ContractMode").val());
	if (ContractMode == 0 || ContractMode == "")
		errorList.push(getLocalMessage("agreement.enterContractMode"));

	/**
	 * Commented as per SUDA UAT Regarding WMS
	 */
	/*
	 * var allowRenewal = $.trim($("#allowRenewal").val()); if (allowRenewal ==
	 * 0 || allowRenewal == "")
	 * errorList.push(getLocalMessage("agreement.enterAllowRenewal"));
	 */

	var ResulationDate = $.trim($("#resolutionDate").val());
	if (ResulationDate == 0 || ResulationDate == "")
		errorList.push(getLocalMessage("agreement.enterResolutionDate"));

	if (new Date($("#tenderDate")) >= new Date($("#AgreementDate"))) {
		errorList.push(getLocalMessage("agreement.enterContractDateGreater"));
	}

	var ContractMode = $.trim($(
			"input[name='contractMastDTO.contMode']:checked").val());
	if (ContractMode == 0 || ContractMode == "")
		errorList.push(getLocalMessage("agreement.enterContractMode"));

	if (ContractMode == 'C') {
		var ContractPayment = $.trim($("#ContractPayment").val());

		if (ContractPayment == 0 || ContractPayment == "")
			errorList.push(getLocalMessage("agreement.enterContractPayment"));

		

		/**
		 * Commented as per SUDA UAT Regarding WMS
		 */

		/*
		 * var SecurityDeposit = $.trim($("#SecurityDeposit").val()); if
		 * (SecurityDeposit == 0 || SecurityDeposit == "")
		 * errorList.push(getLocalMessage("agreement.enterSecurityDeposit"));
		 */

		if (ContType == 'CL') {
			var taxId = $.trim($("#taxId").val());
			if (taxId == 0 || taxId == "")
				errorList.push(getLocalMessage("agreement.enterTaxCode"));

			var id_noa = $.trim($("#id_noa").val());
			if (id_noa == 0 || id_noa == ""){
				errorList.push(getLocalMessage("agreement.enterNoOfInstall"));
			}else{
				//D#81626 check table body length
				let intmLength=$("#noOfInstallmentTable>tbody >tr").length;
				
				if(intmLength==1){
					errorList.push(getLocalMessage("master.enter.installment.details"));
				}
			}
				

			var totalAmt = 0;
			var count = 0;
			var errcount = 0;
			$('.appendableClassInstallments')
					.each(
							function(i) {
								count++;
								var level = i + 1;
								var PaymentTerms = $
										.trim($("#PaymentTerms" + i).val());
								var PaymentTermsCode = $(
										"#PaymentTerms" + i
												+ " option:selected").attr(
										'code');

								if (PaymentTerms == 0 || PaymentTerms == "")
									errorList
											.push(getLocalMessage("agreement.selectPayTerms")
													+ " " + level);

								var amt = $.trim($("#amt" + i).val());
								if (amt == 0 || amt == "")
									errorList
											.push(getLocalMessage("agreement.enterAmt")
													+ " " + level);

								if (PaymentTermsCode == "AMT") {
									totalAmt = parseFloat(totalAmt)
											+ parseFloat(amt);
								} else if (PaymentTermsCode == "PER") {
									var amount = (parseFloat(ContractAmount) * parseFloat(amt)) / 100;
									totalAmt = totalAmt + amount;
								}

								var installmentsDate = $.trim($(
										"#installmentsDate" + i).val());
								if (installmentsDate == 0
										|| installmentsDate == "") {
									errcount++;
									errorList
											.push(getLocalMessage("agreement.securityNo")
													+ " " + level);
								}
								/*
								 * Defect #31615-->Contract Agreement form
								 * displays wrong error on Date selected
								 */
								if (i > 0) {
									var installmentsDate1 = $.trim($(
											"#installmentsDate" + (i - 1))
											.val());
									var installmentsDate2 = $.trim($(
											"#installmentsDate" + i).val());

									var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
									var instaDate1 = new Date(installmentsDate1
											.replace(pattern, '$3-$2-$1'));
									var instaDate2 = new Date(installmentsDate2
											.replace(pattern, '$3-$2-$1'));

									if (instaDate1 > instaDate2) {
										errorList
												.push(getLocalMessage("agreement.dueDate")
														+ (i + 1)
														+ getLocalMessage("agreement.dueDateGreater")
														+ (i));
									}
								}
							});
			/*
			 * if (errcount == 0) { for (var i = 0; i < count; i++) { var idate =
			 * new Date($("#installmentsDate" + i).val()); for (var j = i + 1; j <
			 * count; j++) { var jdate = new Date($("#installmentsDate" +
			 * j).val()); if (idate >= jdate) { errorList
			 * .push(getLocalMessage("agreement.dueDate") + (j + 1) +
			 * getLocalMessage("agreement.dueDateGreater") + (i + 1)); } } } }
			 */
			if (!id_noa == 0 && !id_noa == "") {
				//U#39694 check in case of Lease 
				//if contract date is within month range of system date than don't fire the validation
				let contractDate = moment(contractFromDate,'DD-MM-YYYY');
				//selected month count in no of days
				let contractDateMonthInDays =contractDate.daysInMonth();
				//get difference of two date than compare
				let subDays = moment().diff(contractDate,'days');
				//D#92435
				console.log("days in Month of contract date "+contractDateMonthInDays +"and difference of sysdate and contract date is "+subDays );				
				if(subDays>=contractDateMonthInDays){
				  console.log("do not validate");
				}else if (totalAmt != ContractAmount) {
					errorList.push(getLocalMessage("agreement.noInstallNotMatch"));
				}
			}
		}
	}

	$('.appendableTermConClass')
			.each(
					function(i) {
						var level = i + 1;
						var termCon = $.trim($("#termCon" + i).val());
						if (termCon == 0 || termCon == "") {
							errorList
									.push(getLocalMessage("agreement.enterTemsConditions")
											+ " " + level);
						}
					});

	var fromDate = $("#SecurityDepositDate").val();
	var toDate = $("#SecurityDepositEndDate").val();
	if ((compareDate(fromDate)) > compareDate(toDate)) {
		errorList
				.push(getLocalMessage("master.tillDate.greater.fromDate"));
	}
	if (errorList.length > 0) {
		showRLValidation(errorList);
		return false;
	}
	var url = 'ContractAgreement.html?AddPartyDetails';
	var requestData = $.param($('#ContractAgreement').serializeArray());
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}
// End

// Compare dates
function compareDate(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	return false;
}

function checkTenderDate() {
	var resolutionDate = $("#resolutionDate").val();
	if (resolutionDate == '') {
		showAlertBoxFoTenderDate();
	}
}

function checkFromDate() {
	var tenderDate = $("#tenderDate").val();
	var resolutionDate = $("#resolutionDate").val();
	if (resolutionDate == '' || tenderDate == '') {
		showAlertBoxForFromDate();
	}
}

function checkToDate() {
	var tenderDate = $("#tenderDate").val();
	var resolutionDate = $("#resolutionDate").val();
	var fromDate = $("#contractFromDate").val();

	if (resolutionDate == '' || tenderDate == '' || fromDate == '') {
		showAlertBoxForToDate();
	}
}

function checkInstallDate() {
	var tenderDate = $("#tenderDate").val();
	var resolutionDate = $("#resolutionDate").val();
	var fromDate = $("#contractFromDate").val();
	// var toDate = $("#contractToDate").val();
	if (resolutionDate == '' || tenderDate == '' || fromDate == '') {
		showAlertBoxForInstallDate();
		return false;
	}
	return true;
}
function back(type) {
	if (type == '') {
		value = "ContractAgreement.html";
	} else if (type == 'ren') {
		value = "ContractRenewalEntry.html";
	} else if (type == 'WMS') {

		var divName = '.content-page';
		var url = "WmsProjectMaster.html?showProjectCurrentForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		return false;
	} else if (type == 'WOG') {

		var divName = '.content-page';
		var url = "PwWorkOrderGeneration.html?showWorkOrderCurrentForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		return false;
	} else if (type == 'SWM') {

		var divName = '.content-page';
		var url = "ContractMapping.html?contractMappingForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		reOrderUnitTabIdSequence('.firstUnitRow');
		return false;

	} else if (type == 'ADH') {
		value = "AdvertisementContractMapping.html";
	} else {
		value = "EstateContractMapping.html";
	}
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', value);
	$("#postMethodForm").submit();
}

function showAlertBoxFoTenderDate() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage('contract.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">'
			+ getLocalMessage('contract.resolution.date') + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$('#tenderDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForFromDate() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage('contract.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">'
			+ getLocalMessage('contract.tender.date.resolution.date')
			+ ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$('#contractFromDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForToDate() {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage('contract.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">'
			+ getLocalMessage('contract.tender.date.resolution.from.date')
			+ ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	// $('#contractToDate').val("");
	// $('#contToPeriod').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForInstallDate() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage('contract.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">'
			+ getLocalMessage('contract.tender.date.resolution.from.to.date')
			+ ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$('#id_noa').val('');
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function closeAlertForm() {
	var childDivName = '.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

function callbackOtherTask() {
	// alert("Callback called");
}

function getTenderDetails() {
	var url = "ContractAgreement.html?getVenderNameOnVenderType";
	var errorList = [];
	var divName = '.content-page';
	var tndDeptCode = $("#tndDept").val();
	var loaNo = $("#loaNo").val();
	var tndVendorId = $("#tndVendorId").val();
	var agreementDate = $("#AgreementDate").val();
	if (tndDeptCode == null || tndDeptCode == '') {
		errorList.push(getLocalMessage('agreement.selectTndDept'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (loaNo == null || loaNo == '') {
		errorList.push(getLocalMessage('agreement.selectLoaNo'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var status = checkLoaNo(loaNo);
	if (status != null && status != "") {
		errorList.push(getLocalMessage('agreement.LoaNoPresent'));
	} else {
		if (agreementDate == 0 || agreementDate == "")
			errorList.push(getLocalMessage("agreement.selectAgreeDate"));

		if (tndDeptCode == null || tndDeptCode == '') {
			errorList.push(getLocalMessage('agreement.selectTndDept'));
		}
		if (loaNo == null || loaNo == '') {
			errorList.push(getLocalMessage('agreement.selectLoaNo'));
		}
		if (errorList.length == 0) {

			var divName = formDivName;
			var requestData = {
				"tndDeptCode" : tndDeptCode,
				"loaNo" : loaNo,
				"agreementDate" : agreementDate
			}
			var response = __doAjaxRequest(
					'ContractAgreement.html?getTenderDetails', 'post',
					requestData, false, 'json');
             //#80038
			var value = $("#tndDept").find("option:selected").attr('code');
			if (value == "WMS" && loaNo != null) {		
				$("#ContractPayment option[value='P']").attr("selected","selected");
				$('#ContractType').val(response.contType).trigger('chosen:updated');		
			}
			
			if (response.contTndNo != null) {
				$("#loaNo").val(response.loaNo);

				$("#resulationNo").val(response.contRsoNo);
				if (response.contRsoDate == null) {
					$("#resolutionDate").val(null);
				} else {
					$("#resolutionDate").val(
							getFormattedDate(response.contRsoDate));

				}

				$("#TenderNo").val(response.contTndNo);
			    $("#ContractType").val(response.contType);
				$("#contToPeriod").val(
						response.contractDetailList[0].contToPeriod);
				if (response.contractDetailList[0].contDefectLiabilityPer != null
						&& response.contractDetailList[0].contDefectLiabilityPer != "") {
					$("#vc")
							.val(
									response.contractDetailList[0].contDefectLiabilityPer);
					$("#vc").prop("disabled", true);
				}

				if (response.contractDetailList[0].contDefectLiabilityUnit != null) {
					$('#contDefectLiabilityUnit').html('');
					$('#contDefectLiabilityUnit')
							.append(
									$("<option></option>")
											.attr(
													"value",
													response.contractDetailList[0].contDefectLiabilityUnit)
											.text(
													response.contractDetailList[0].defectLiaPeriodUnitDesc));

					$("#contDefectLiabilityUnit").attr("disabled", true);
					$('#contDefectLiabilityUnit').trigger('chosen:updated');
				}
				if (response.contractDetailList[0].contToPeriodUnit != null) {
					$('#contToPeriodUnit').html('');
					$('#contToPeriodUnit')
							.append(
									$("<option></option>")
											.attr(
													"value",
													response.contractDetailList[0].contToPeriodUnit)
											.text(
													response.contToPeriodUnit));

					$("#contToPeriodUnit").attr("disabled", true);
					$('#contToPeriodUnit').trigger('chosen:updated');
				}
				

				/*$("#contToPeriodUnit").val(response.contToPeriodUnit);
				$(".contPeriodday").hide();*/
				$("#ContractAmount")
						.val(
								(response.contractDetailList[0].contractAmt)
										.toFixed(2));
				$("#tenderDate").val(getFormattedDate(response.contTndDate));
				// $("#resulationNo").prop("disabled", true);

				// $("#resolutionDate").prop("disabled", true);
				$("#TenderNo").prop("disabled", true);
				$("#tenderDate").prop("disabled", true);
				//$("#ContractType").attr("disabled", true);
				$("#contToPeriod").prop("disabled", true);
				$("#contToPeriodUnit").prop("disabled", true);
				$("#loaNo").attr("readonly", true);
				$("#ContractAmount").attr("readonly", true);
				$("#nonCommercial").hide();
				$('#commercial').prop('checked', true);
				if ($("input[name='contractMastDTO.contMode']:checked").val() == "C") {
					if ($("#contrType").val() != 'CL') {
						if ($("#contrType").val() == "CW") {
							$("#nonCommercial").hide();
							$("#Renewal").hide();
						}
						$("#taxGroup").hide();
						$("#NoofInstallments").hide();
						$("#installments").hide();
						$(".Commercial").show();
						$(".bank").show();

					}
				}
				$("#Renewal").hide();
				var deptId = tndDeptCode;
				var taxData = "deptId=" + deptId;

				var json = __doAjaxRequest(
						'ContractAgreement.html?getAllTaxesBasedOnDept',
						'POST', taxData, false, 'json');
				$('#taxId option').remove();
				var optionsAsString = "<option value='0'>select Tax</option>";
				for (var j = 0; j < json.length; j++) {

					optionsAsString += "<option value='" + json[j].lookUpId
							+ "'>" + json[j].descLangFirst + "</option>";
				}
				$('#taxId').append(optionsAsString);

			} else {
				errorList.push(getLocalMessage('agreement.NoData'));
				var ajaxResponse = __doAjaxRequest(
						'ContractAgreement.html?form', 'POST', {}, false,
						'html');
				$('.content').html(ajaxResponse);
			}
		} else {
			errorList.push(getLocalMessage('agreement.NoData'));
			displayErrorsOnPage(errorList);
		}
	}
	if (errorList.length != 0) {
		displayErrorsOnPage(errorList);
	}
}
function checkLoaNo(loaNo) {
	var checkUrl = "ContractAgreement.html?validateLoaNo";
	var postdata = "loaNo=" + loaNo;
	var returnData = __doAjaxRequest(checkUrl, 'POST', postdata, '', '');
	return returnData;
}

function getFormattedDate(time) {

	var formattedDate = new Date(time);
	var twoDigitMonth = formattedDate.getMonth() + 1;
	var twoDigitDate = formattedDate.getDate() + "";
	if (twoDigitDate.length == 1)
		twoDigitDate = "0" + twoDigitDate;
	if (twoDigitMonth < 10) {
		twoDigitMonth = '0' + twoDigitMonth;
	}
	return twoDigitDate + "/" + twoDigitMonth + "/"
			+ formattedDate.getFullYear();
}

function getPartyDetails() {
	var divName = formDivName;
	var url = 'ContractAgreement.html?viewPartyDetails';
	var requestData = $.param($('#ContractAgreement').serializeArray());
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function dueDateUpdate(){
	//D#98311
	var contToPeriod = $('#contToPeriod').val();
	if (contToPeriod != '') {
		var dateParts = $("#contractFromDate").val().split("/");

		var fromDate = new Date(dateParts[2], dateParts[1] - 1,
				dateParts[0]);
		
		//D#98311 here make run time due date of installments 
		let contToPeriodUnitCode = $("#contToPeriodUnit").find("option:selected").attr('code');
		let contStartDate =moment(dateParts);
		if(contToPeriodUnitCode == "Y"){
			//Year
			contToPeriod = Number(contToPeriod)*365;
			fromDate.setDate(fromDate.getDate()
					+ parseInt(contToPeriod));
		}else if(contToPeriodUnitCode == "M"){
			//Month
			contToPeriod = Number(contToPeriod)*30;
			fromDate.setDate(fromDate.getDate()
					+ parseInt(contToPeriod));
		}else{
			//Day
			fromDate.setDate(fromDate.getDate()
					+ parseInt(contToPeriod));	
		}
		
		$("#contractToDate").val(formatDate(new Date(fromDate)));
		$(".Insdatepicker").datepicker("option", "maxDate",
				fromDate);
		checkToDate();
	}
}

$('#contToPeriod').blur(
		function() {

			var contToPeriod = $('#contToPeriod').val();
			if ($("#contractFromDate").val() == '') {
				checkToDate();
			} else {
				//D#98311
				dueDateUpdate();
			}

		});

function formatDate(date) {
	var d = new Date(date), month = '' + (d.getMonth() + 1), day = ''
			+ d.getDate(), year = d.getFullYear();

	if (month.length < 2)
		month = '0' + month;
	if (day.length < 2)
		day = '0' + day;

	return [ day, month, year ].join('/');
}

function showAlertBoxForContractType() {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage('contract.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">'
			+ getLocalMessage('contract.select.agreement.type') + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$("input[name='contractMastDTO.contMode']").removeAttr('checked');
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function resetForm() {
	var contractUrl = 'ContractAgreement.html';
	var ajaxResponse = __doAjaxRequest(contractUrl + '?form', 'POST', {},
			false, 'html');
	$('.content').html(ajaxResponse);
}
function clearInput(obj) {
	$(obj).val('');

}
function addTermsDetails(){
	
	var errorList = [];
	//errorList = validateEntryDetails();
	if (errorList.length == 0) {
		//addTableRow('customFields2');
		var content = $('#customFields2 tr').last().clone();
		$('#customFields2 tr').last().after(content);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		reorderDropDownTermCon();
	} else {
		$('#customFields2').DataTable();
		displayErrorsOnPage(errorList);
	}

}

function reorderDropDownTermCon() {
	
	$('.appendableTermConClass').each(
			function(i) {
				
				// Id
				var count = i + 1;
				//$(this).find("textarea:eq(0)").attr("id", "termCon" + (i));
				$(this).find("input:hidden:eq(0)").attr("id",
						"presentRow" + (i));
				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i)).val(i + 1);
				
				$(this).find("select:eq(0)")
				.attr("id", "termCon" + (i)).attr(
						"name",
						"contractMastDTO.contractTermsDetailList["
								+ (i) + "].conttDescription");
				
				$(this).find("input:hidden:eq(0)").attr(
						"name",
						"contractMastDTO.contractTermsDetailList[" + (i)
								+ "].active").val("Y");
				$(this).find("input:text:eq(0)").attr("value", count);

			});
}


function showHidefileds(){
	

		if ($('#apprApplicable option:selected').attr('code') == "A") {

			$("#ApprAmt-div").css('display','block');
			//$("#apprType).show();

		}else{
			$("#ApprAmt-div").css('display','none');
			//$("#apprType).hide();
		}

}



function changeIcon(){
	var icon;
	if ($('#apprType option:selected').attr('code') == "AMT") {
		icon="<i class='fa fa-inr'></i>";
	}else if($('#apprType option:selected').attr('code') == "PER"){
		icon="<i class='fa fa-percent'></i>";
	}else if($('#apprType option:selected').attr('code') == "MUL"){
		icon="<i class='fa fa-times'></i>";
	}else{
		icon="<i class='fa fa-inr'></i>";
	}
	
	$('#textIcon').html(icon);
}

function showHideLeavyPenaltyfileds() {
	if ($('#leavyPenalty option:selected').attr('code') == "Y") {

		$("#leavyPenalty-div").css('display', 'block');
		// $("#apprType).show();

	} else {
		$("#leavyPenalty-div").css('display', 'none');
		// $("#apprType).hide();
	}
}
function changeleavyPenaltyIcon(){
	
	var icon;
	if ($('#penaltyMode option:selected').attr('code') == "AMT") {
		icon="<i class='fa fa-inr'></i>";
	}else if($('#penaltyMode option:selected').attr('code') == "PER"){
		icon="<i class='fa fa-percent'></i>";
	}else if($('#penaltyMode option:selected').attr('code') == "MUL"){
		icon="<i class='fa fa-times'></i>";
	}else{
		icon="<i class='fa fa-inr'></i>";
	}
	
	$('#leavyPenaltyIcon').html(icon);
}

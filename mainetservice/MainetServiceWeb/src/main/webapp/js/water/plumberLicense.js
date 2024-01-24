$('.monthPick').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate : '-0d',
	yearRange : "-100:-0",
});

var monthFields = $('.monthPick');
monthFields.each(function() {
	var fieldValue = $(this).val();
	if (fieldValue.length > 10) {
		$(this).val(fieldValue.substr(0, 10));
	}
});
$(document).ready(
		function() {
			//This is for non-mandatory zone ward
			/*$('#dwzid1').attr('data-rule-prefixvalidation',false);
			$('#dwzid2').attr('data-rule-prefixvalidation',false);
			$('label[for="' + $('#dwzid1').attr('id') + '"]').removeClass('required-control');
			$('label[for="' + $('#dwzid2').attr('id') + '"]').removeClass('required-control');*/
			//This is for remove zone ward
			$('label[for="' + $('#dwzid1').attr('id') + '"]').hide();
			$('label[for="' + $('#dwzid2').attr('id') + '"]').hide();
			$('#dwzid1').hide();
			$('#dwzid2').hide();
			$('#frmPlumberLicenseForm').validate({
				onkeyup : function(element) {
					this.element(element);
					console.log('onkeyup fired');
				},
				onfocusout : function(element) {
					this.element(element);
					console.log('onfocusout fired');
				}
			});
			if ($("#plumFlag").val() == "Y") {
				$("#selectHideShow").hide();

			}

			else if ($("#plumFlag").val() == "N") {
				$("#selectHideShow").show();
			}

			if ($("#plumIdFlag").val() == "Y") {
				$("#selectPlHide,#getLicense,#hideButton").hide();

			} else if ($("#plumIdFlag").val() == "N") {
				$("#selectPlHide").show();
				$("#getLicense").show();
				$("#appli").find("input,select").attr("disabled", "disabled");
				$("#searchTab").find("input,select,button").attr("disabled",
						"disabled");
				$("#searchButt").hide();
			}

			if ($("#fromEvent").val()) {
				$("#appli").find("input,select").attr("disabled", "disabled");
			}

			var dateFields = $('.lessthancurrdate');
			dateFields.each(function() {
				var fieldValue = $(this).val();
				if (fieldValue.length > 10) {
					$(this).val(fieldValue.substr(0, 10));
				}
			});
			/*
			 * $("#povertyLineId").change(function(e) {
			 * 
			 * if ( $("#povertyLineId").val() == 'Y') { $("#bpldiv").show();
			 * $("#bplNo").data('rule-required',true); } else {
			 * $("#bpldiv").hide(); $("#bplNo").data('rule-required',false); }
			 * }); if($('#povertyLineId').val()=='N') { $("#bpldiv").hide(); }
			 * if($('#povertyLineId').val()=='Y') { $("#bpldiv").show();
			 * $("#bplNo").data('rule-required',true); } else {
			 * $("#bpldiv").hide(); $("#bplNo").data('rule-required',false); }
			 */

			$(".datepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				yearRange : "-100:-0",
				maxDate : 0,
				onSelect : function(selected, evnt) {
					updateExperienceDetails();
				}

			});

			$('.monthPick').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : '-0d',
				yearRange : "-100:-0",
			});

			var monthFields = $('.monthPick');
			monthFields.each(function() {
				var fieldValue = $(this).val();
				if (fieldValue.length > 10) {
					$(this).val(fieldValue.substr(0, 10));
				}
			});

			if ($("#chargesId").val() == 'Y') {
				$(".hideConfirmBtn").hide();
				$('input[type=text]').attr('disabled', true);
				/* $('select').attr("disabled", true); */
				$("#academicTableId").find("input,button,textarea,select")
						.attr("disabled", "disabled");
				$("#experienceTableId").find("input,button,textarea,select")
						.attr("disabled", "disabled");
				$(".academicAddRow").attr("disabled", "disabled");
				$(".academicDeleteRow").attr("disabled", "disabled");
				$(".experienceAddRow").attr("disabled", "disabled");
				$(".experienceDeleteRow").attr("disabled", "disabled");
				$("#appli").find("input,select").attr("disabled", "disabled");

				$("#oflPaymentMode").attr('disabled', false);
				$("#bankAccId").attr('disabled', false);
			}

			$("#academicTableId")
					.on(
							'click',
							'.academicAddRow',
							function(e) {

								$('#errorDivId').hide();
								var errorList = [];
								var row = e;
								var val = (row + 1);
								/*
								 * $('.academicAppendable') .each( function(i) {
								 * errorList = validateAcademicDetails(
								 * errorList, i); });
								 */
								errorList = validateAcademicDetails(errorList);
								if (errorList.length > 0) {
									displayErrorsOnPage(errorList);
									return false;
								}
								$(".monthPick").datepicker("destroy");
								var content = $(this).closest('tr').clone();
								$(this).closest("tr").after(content);
								var clickedIndex = $(this).parent().parent()
										.index() - 1;

								content.find("input:text").val("");

								$(content).find("td:eq(0)").attr("id",
										"academicSrNoId" + val);
								$(content).find("select:eq(0)").attr("id",
										"qualificationId" + val);
								/*
								 * $(content).find("input:text:eq(0)").attr("id",
								 * "qualificationId" + val);
								 */
								$(content).find("input:text:eq(0)").attr("id",
										"instituteNameId" + val);
								$(content).find("input:text:eq(1)").attr("id",
										"instituteAddrsId" + val);
								/*
								 * $(content).find("input:text:eq(2)").attr("id",
								 * "passYearId" + val);
								 */
								$(content).find("input:text:eq(2)").attr("id",
										"passMonthId" + val);
								$(content).find("input:text:eq(3)").attr("id",
										"percentGradeId" + val);
								content.find('.academicAddRow').attr("id",
										"academicAddButton" + val);
								content.find('.academicDeleteRow').attr("id",
										"academicDelButton" + val);

								content.find("select:eq(0)").attr(
										"name",
										"plumberQualificationDTOList["
												+ (clickedIndex + 1)
												+ "].plumQualification");
								content.find("input:text:eq(0)").attr(
										"name",
										"plumberQualificationDTOList["
												+ (clickedIndex + 1)
												+ "].plumInstituteName");
								content.find("input:text:eq(1)").attr(
										"name",
										"plumberQualificationDTOList["
												+ (clickedIndex + 1)
												+ "].plumInstituteAddress");
								/*
								 * content.find("input:text:eq(2)").attr(
								 * "name", "plumberQualificationDTOList[" +
								 * (clickedIndex + 1) + "].plumPassYear");
								 */
								content.find("input:text:eq(2)").attr(
										"name",
										"plumberQualificationDTOList["
												+ (clickedIndex + 1)
												+ "].plumPassMonth");
								content.find("input:text:eq(3)").attr(
										"name",
										"plumberQualificationDTOList["
												+ (clickedIndex + 1)
												+ "].plumPercentGrade");
								reOrderQualificationTableIdSequence();
								$('.monthPick').datepicker({
									dateFormat : 'dd/mm/yy',
									changeMonth : true,
									changeYear : true,
									maxDate : '-0d',
									yearRange : "-100:-0",
								});

								var monthFields = $('.monthPick');
								monthFields.each(function() {
									var fieldValue = $(this).val();
									if (fieldValue.length > 10) {
										$(this).val(fieldValue.substr(0, 10));
									}
								});
							});

			$("#academicTableId").on('click', '.academicDeleteRow', function() {

				if ($("#academicTableId tr").length != 2) {
					$(this).parent().parent().remove();
				} else {
					alert("You can not delete first row");
				}

				reOrderQualificationTableIdSequence();
			});

			$("#experienceTableId").on(
					'click',
					'.experienceAddRow',
					function(e) {

						$('#errorDivId').hide();
						var errorList = [];
						var row = e;
						/*
						 * $('.experienceAppendable') .each( function(i) {
						 * errorList = validateExperienceDetails( errorList, i);
						 * });
						 */
						errorList = validateExperienceDetails(errorList);

						if (errorList.length > 0) {
							displayErrorsOnPage(errorList);
							return false;
						}

						$(".datepicker").datepicker("destroy");
						var content = $(this).closest("tr").clone();
						$(this).closest("tr").after(content);

						content.find("select:eq(0)").attr("value", "");
						content.find("input:text").val("");

						$(content).find("td:eq(0)").attr("id",
								"experienceSrNoId" + (row + 1));
						$(content).find("input:text:eq(0)").attr("id",
								"companyNameId" + (row + 1));
						$(content).find("input:text:eq(1)").attr("id",
								"companyAddrsId" + (row + 1));
						$(content).find("input:text:eq(2)").attr("id",
								"expFromDateId" + (row + 1));
						$(content).find("input:text:eq(3)").attr("id",
								"expToDateId" + (row + 1));
						$(content).find("input:text:eq(4)").attr("id",
								"experienceId" + (row + 1));
						$(content).find("select:eq(0)").attr("id",
								"firmTypeId" + (row + 1));

						content.find('.experienceAddRow').attr("id",
								"experienceAddButton" + (row + 1));
						content.find('.experienceDeleteRow').attr("id",
								"experienceDelButton" + (row + 1));

						content.find("input:text:eq(0)").attr(
								"name",
								"plumberExperienceDTOList[" + (row + 1)
										+ "].plumCompanyName");
						content.find("input:text:eq(1)").attr(
								"name",
								"plumberExperienceDTOList[" + (row + 1)
										+ "].plumCompanyAddress");
						content.find("input:text:eq(2)").attr(
								"name",
								"plumberExperienceDTOList[" + (row + 1)
										+ "].expFromDate");
						content.find("input:text:eq(3)").attr(
								"name",
								"plumberExperienceDTOList[" + (row + 1)
										+ "].expToDate");
						content.find("input:text:eq(4)").attr(
								"name",
								"plumberExperienceDTOList[" + (row + 1)
										+ "].experience");
						content.find("select:eq(0)").attr(
								"name",
								"plumberExperienceDTOList[" + (row + 1)
										+ "].firmType");

						$(".datepicker").datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							yearRange : "-100:-0",
							maxDate : 0,
							onSelect : function(selected, evnt) {
								updateExperienceDetails();
							}

						});

						reOrderExperienceTableIdSequence();
					});

			$("#experienceTableId").on('click', '.experienceDeleteRow',
					function() {

						if ($("#experienceTableId tr").length != 3) {
							$(this).parent().parent().remove();
						} else {
							alert("You can not delete first row");
						}
						reOrderExperienceTableIdSequence();
					});

		});
function validateAcademicDetails(errorList) {

	var qualificationList = [];
	$('.academicAppendable')
			.each(
					function(i) {
						// errorList = validateAcademicDetails(errorList, i);
						var qualification = $.trim($("#qualificationId" + i)
								.val());
						var institute = $.trim($("#instituteNameId" + i).val());
						var address = $.trim($("#instituteAddrsId" + i).val());
						// var passYear = $.trim($("#passYearId" + i).val());
						var passMonth = $.trim($("#passMonthId" + i).val());
						var grade = $.trim($("#percentGradeId" + i).val());
						if (qualification == 0 || qualification == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterQual"));
						} else {

							if (qualificationList.includes(qualification)) {
								errorList
										.push(getLocalMessage("water.dup.qualification.entryNo")
												+ (i + 1));
							}
							if (errorList.length == 0) {
								qualificationList.push(qualification);
							}

						}
						if (institute == 0 || institute == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterInstituteName"));
						}
						if (address == 0 || address == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterInstituteAddrs"));
						}

						/*
						 * if (passYear == 0 || passYear == "") { errorList
						 * .push(getLocalMessage("water.plumberLicense.valMsg.enterPassingYear")); }
						 */

						if (passMonth == 0 || passMonth == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterPassingMonth"));
						}

						if (grade == 0 || grade == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterPercentOrGrade"));
						}
					});

	return errorList;
}

function validateExperienceDetails(errorList) {

	$('.experienceAppendable')
			.each(
					function(i) {
						var companyName = $.trim($("#companyNameId" + i).val());
						var companyAddrs = $.trim($("#companyAddrsId" + i)
								.val());
						var fromDate = $.trim($("#expFromDateId" + i).val());
						var toDate = $.trim($("#expToDateId" + i).val());
						var experience = $.trim($("#experienceId" + i).val());
						var firmType = $.trim($("#firmTypeId" + i).val());
						if (companyName == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterEmployeersName"));
						}
						if (companyAddrs == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.enterEmployeersAddrs"));
						}
						if (fromDate == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.selectFrmDt"));
						}
						if (toDate == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.selectToDt"));
						}
						if (firmType == 0 || firmType == "") {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.selectFrmType"));
						}
						if ((new Date(dateFormate(fromDate)) > new Date(
								dateFormate(toDate)))) {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.frmDtLessthanToDt"));
						}
						if ($.datepicker.parseDate('dd/mm/yy', fromDate) > $.datepicker
								.parseDate('dd/mm/yy', toDate)) {
							errorList
									.push(getLocalMessage("water.plumberLicense.valMsg.frmDtLessthanToDt"));
						}
					});
	return errorList;
}

/**
 * Formate date date picker formate to new date formate
 */
function dateFormate(date) {
	var chunks = date.split('/');
	var newDate = chunks[1] + '/' + chunks[0] + '/' + chunks[2];
	return newDate;
}

function updateExperienceDetails() {
	var sumYear = 0;
	var sumMonth = 0;
	var year = 0;
	var month = 0;
	$(".experienceAppendable").each(function(i) {
		var frmDt = $("#expFromDateId" + i).val();
		var toDt = $("#expToDateId" + i).val();
		var experience = calculateExp(frmDt, toDt);
		if (isNaN(toDt) && isNaN(frmDt)) {
			$("#experienceId" + i).val(experience);
			var splitExp = experience.split('.')
			var year = splitExp[0];
			var month = splitExp[1];
			sumYear += Number(year);
			sumMonth += Number(month);
		}
	});
	var YrsMnth = 0;
	var yearToMonth = sumYear * 12;
	var totalMonth = yearToMonth + Number(sumMonth);
	var remainderMonth = totalMonth % 12;
	if (totalMonth >= 12) {
		var year = (totalMonth / 12).toFixed(1);
		var str_array = year.split('.');
		var yrs = str_array[0];
		YrsMnth = yrs + "." + remainderMonth;
	} else {
		YrsMnth = "0." + remainderMonth;
	}
	$("#totalExpId").val(YrsMnth);
};

function calculateExp(a, b) {
	var chunks1 = a.split('/');
	var chunks2 = b.split('/');
	var fromDate = chunks1[1] + '/' + chunks1[0] + '/' + chunks1[2];
	var tDate = chunks2[1] + '/' + chunks2[0] + '/' + chunks2[2];
	if (isNaN(fromDate) && isNaN(tDate)) {
		var frmDate = new Date(fromDate);
		var toDate = new Date(tDate);
		var months;
		months = (toDate.getFullYear() - frmDate.getFullYear()) * 12;
		months -= frmDate.getMonth() + 1;
		months += toDate.getMonth();
		var total = months <= 0 ? 0 : months;
		var totalMonths = total + 1;
		var remainderMonths = totalMonths % 12;
		var YrsMnth = 0;
		if (totalMonths >= 12) {
			var year = (totalMonths / 12).toFixed(1);
			var str_array = year.split('.');
			var yrs = str_array[0];
			YrsMnth = yrs + "." + remainderMonths;
		} else {
			YrsMnth = "0." + remainderMonths;
		}

		return YrsMnth;
	}
}

/**
 * used to find applicable checklist and charges for change of plumber license
 * 
 * @param obj
 */
function getChecklistAndChargesPlumberLicense(obj) {

	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	if (errorList.length == 0)
		errorList = checkAcademicDetails(errorList);
	if (errorList.length == 0)
		errorList = checkExperienceDetails(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var request = __serializeForm(theForm);
		var url = 'NewPlumberLicenseForm.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(url, 'POST', request, false);
		$(formDivName).html(returnData);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function checkAcademicDetails(errorList) {
	/*$(".academicAppendable").each(function(i) {*/
		errorList = validateAcademicDetails(errorList, i);
	/*});*/
	return errorList;
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	return false;
}

function validateApplicantInfo(errorList) {
	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	var gender = $.trim($('#gender').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());
	var blockName = $.trim($('#blockName').val());
	var villTownCity = $.trim($('#villTownCity').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var applicantAdharNo = $.trim($('#adharNo').val());
	var dwzid1 = $.trim($('#dwzid1').val());
	var dwzid2 = $.trim($('#dwzid2').val());
	var povertyLineId = $('#povertyLineId').val();

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	}

	if (applicantMobileNo == "" || applicantMobileNo == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	}
	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantarea'));
	}
	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	}

	if (povertyLineId == "" || povertyLineId == '0'
			|| povertyLineId == undefined) {
		errorList.push(getLocalMessage('water.select.below.povertyLine'));
	}
	return errorList;
}

function closeOutErrBox() {
	$('.error-div').hide();
}

function savePlumberLicenseForm(element) {

	$("#drawnOn").prop('disabled', false);
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		return saveOrUpdateForm(
				element,
				"Your application for new Water connection saved successfully!",
				'NewPlumberLicenseForm.html?PrintReport', 'saveform');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		return saveOrUpdateForm(
				element,
				"Your application for new Water connection saved successfully!",
				'NewPlumberLicenseForm.html?redirectToPay', 'saveform');
	} else {
		return saveOrUpdateForm(
				element,
				"Your application for new Water connection saved successfully!",
				'AdminHome.html', 'saveform');
	}

}

function acceptDesclimer(obj) {

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var request = __serializeForm(theForm);
	var url = 'NewPlumberLicenseForm.html?getPlumberForm';
	var returnData = __doAjaxRequest(url, 'POST', request, false);
	$(formDivName).html(returnData);
}

function checkExperienceDetails(errorList) {

	$('.experienceAppendable')
			.each(
					function(i) {
						var companyName = $.trim($("#companyNameId" + i).val());
						var companyAddrs = $.trim($("#companyAddrsId" + i)
								.val());
						var fromDate = $.trim($("#expFromDateId" + i).val());
						var toDate = $.trim($("#expToDateId" + i).val());
						var experience = $.trim($("#experienceId" + i).val());
						var firmType = $.trim($("#firmTypeId" + i).val());

						if (companyName != "" || companyAddrs != ""
								|| fromDate != "" || toDate != ""
								|| experience != "" || firmType != 0) {
							if (companyName == "") {
								errorList
										.push(getLocalMessage("water.plumberLicense.valMsg.enterEmployeersName"));
							}
							if (companyAddrs == "") {
								errorList
										.push(getLocalMessage("water.plumberLicense.valMsg.enterEmployeersAddrs"));
							}
							if (fromDate == "") {
								errorList
										.push(getLocalMessage("water.plumberLicense.valMsg.selectFrmDt"));
							}
							if (toDate == "") {
								errorList
										.push(getLocalMessage("water.plumberLicense.valMsg.selectToDt"));
							}
							if (firmType == 0 || firmType == "") {
								errorList
										.push(getLocalMessage("water.plumberLicense.valMsg.selectFrmType"));
							}
							if ((new Date(dateFormate(fromDate)) > new Date(
									dateFormate(toDate)))) {
								errorList
										.push(getLocalMessage("water.plumberLicense.valMsg.frmDtLessthanToDt"));
							}
						}
					});
	return errorList;
}

function reOrderQualificationTableIdSequence() {

	$('.academicAppendable')
			.each(
					function(i) {

						// for generating dynamic Id
						$(this).find("td:eq(0)").attr("id",
								"academicSrNoId" + i);
						$(this).find("select:eq(0)").attr("id",
								"qualificationId" + i);
						$(this).find("input:text:eq(0)").attr("id",
								"instituteNameId" + i);
						$(this).find("input:text:eq(1)").attr("id",
								"instituteAddrsId" + i);
						/*
						 * $(this).find("input:text:eq(2)").attr("id",
						 * "passYearId" + i);
						 */
						$(this).find("input:text:eq(2)").attr("id",
								"passMonthId" + i);
						$(this).find("input:text:eq(3)").attr("id",
								"percentGradeId" + i);

						$(this).parents('tr').find('.academicDeleteRow').attr(
								"id", "academicDelButton" + i);
						$(this).parents('tr').find('.academicAddRow').attr(
								"id", "academicAddButton" + i);

						$("#academicSrNoId" + i).text(i + 1);

						// for generating dynamic path
						$(this).find("select:eq(0)").attr(
								"name",
								"plumberQualificationDTOList[" + i
										+ "].plumQualification");
						$(this).find("input:text:eq(0)").attr(
								"name",
								"plumberQualificationDTOList[" + i
										+ "].plumInstituteName");
						$(this).find("input:text:eq(1)").attr(
								"name",
								"plumberQualificationDTOList[" + i
										+ "].plumInstituteAddress");
						/*
						 * $(this).find("input:text:eq(2)").attr( "name",
						 * "plumberQualificationDTOList[" + i +
						 * "].plumPassYear");
						 */
						$(this).find("input:text:eq(2)").attr(
								"name",
								"plumberQualificationDTOList[" + i
										+ "].plumPassMonth");
						$(this).find("input:text:eq(3)").attr(
								"name",
								"plumberQualificationDTOList[" + i
										+ "].plumPercentGrade");

					});

}

//D#91264
function checkLastApproval(element) {
	debugger;
    var divName = '.content-page';
    var URL = 'PlumberLicenseAuth.html?checkLastApproval';
    var formName = findClosestElementId(element, 'form');
    var theForm = '#' + formName;
    var requestData = {};
    requestData = __serializeForm(theForm);
    var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');
    $(divName).removeClass('ajaxloader');
    $(divName).html(returnData);
    prepareTags();
    var payableFlag = $("#payableFlag").val();
    if (payableFlag == 'N') {
    	savePlumberLicenseAuthForm(element)
    }
}

function savePlumberLicenseAuthForm(element) {
	
	if ($("input[name='workflowActionDto.decision']:checked").val() == "APPROVED") {
		return saveOrUpdateForm(element,
				"Your application for Plumber License saved successfully!",
				'AdminHome.html', 'save');

	} else
		return saveOrUpdateForm(element,
				"Your application for Plumber License saved successfully!",
				'PlumberLicenseAuth.html?printRejectionReport', 'save');

}

function saveDuplicatePlumberLicenseAprv(element) {

	var decision = $("input:radio[name='workflowActionDto.decision']").filter(
			":checked").val();
	if (decision == "" || decision == null) {
		$("#errorDivId").html(
				getLocalMessage("construct.demolition.validation.decision"))
				.show();
		$("#validationerrordiv").hide();
		return false;
		// errorList.push(
		// getLocalMessage("construct.demolition.validation.decision"));
	}

	if ($('#comments').val() == "") {
		$("#errorDivId").html(
				getLocalMessage("construct.demolition.validation.remark"))
				.show();
		$("#validationerrordiv").hide();
		return false;
	}

	return saveOrUpdateForm(
			element,
			"Your application for Duplicate Plumber License Saved successfully!",
			'AdminHome.html', 'saveDecision');
}

function updateRenewApproval(element) {
	var result = saveOrUpdateForm(element,
			"License Generated Sucessfully successfully!", 'AdminHome.html',
			'updateRenewal');
	return result;
}

function viewLicenseDetails(obj) {

	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var url = 'PlumberLicenseRenewal.html?getLicApplicantDet';
	$(theForm).attr('action', url);
	$(theForm).submit();
}

function searchReceiptData(obj) {

	var errorList = [];
	var applicationId = $("#applicationId").val();
	var receiptNo = $("#receiptNo").val();
	if ((applicationId == null || applicationId == '')
			&& (receiptNo == null || receiptNo == '')) {
		errorList.push(getLocalMessage("water.select.atleast.oneField"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var request = __serializeForm(theForm);
		var url = 'PlumberLicenseGenerator.html?getReciptDet';
		var returnData = __doAjaxRequest(url, 'POST', request, false, 'html');
		$(formDivName).html(returnData);
	}
}

/*
 * function saveData(element) { return saveOrUpdateForm(element, "License
 * Generated Successfully", 'PlumberLicenseGenerator.html?printPlumberLicense',
 * 'saveLicGenerator'); }
 */

function saveData(obj) {

	var divName = '.content-page';
	var url = "PlumberLicenseGenerator.html?saveLicGenerator";
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var request = __serializeForm(theForm);
	var ajaxResponse = __doAjaxRequest(url, 'POST', request, false, 'json');
	showConfirmBoxForPlumber(ajaxResponse);
}

function showConfirmBoxForPlumber(ajaxResponse) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';
	var Proceed = getLocalMessage("Proceed");
	if (ajaxResponse.checkStausApproval == "Y") {
		sMsg = (getLocalMessage("water.application.plumberLic.approve")
				+ ajaxResponse.licNo);
	} else if (ajaxResponse.checkStausApproval == "N") {
		sMsg = getLocalMessage("work.estimate.approval.contact.administration");
	} else if (ajaxResponse.checkStausApproval == "D") {
		sMsg = (getLocalMessage("water.plumberLic.already.generate")
				+ ajaxResponse.licNo);
	} else if (ajaxResponse.checkStausApproval == "A") {
		sMsg = (getLocalMessage("water.application.dup.plumberLic.approve")
				+ ajaxResponse.licNo);
	} else {
		sMsg = getLocalMessage("work.estimate.approval.contact.administration");
		sMsg1 = getLocalMessage("work.estimate.approval.contact.administration");
	}

	if (ajaxResponse.checkStausApproval == "Y"
			|| ajaxResponse.checkStausApproval == "A") {
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>' + '</div>';

		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed
				+ '\'  id=\'Proceed\' '
				+ ' onclick="proceed();"/>'
				+ '</div>';
	} else if (ajaxResponse.checkStausApproval == "N") {

		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>' + '</div>';

		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed
				+ '\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>'
				+ '</div>';
	} else if (ajaxResponse.checkStausApproval == "D") {

		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>' + '</div>';

		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed
				+ '\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>'
				+ '</div>';
	} else {
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center red padding-12">' + sMsg + '</p>'
				+ '<p class="text-center red padding-12">' + sMsg1 + '</p>'
				+ '</div>';

		message += '<div class=\'text-center padding-top-10 padding-bottom-10\'>'
				+ '<input class="btn btn-info" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed
				+ '\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>'
				+ '</div>';
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function closeSend() {
	$.fancybox.close();
	window.location.href = 'PlumberLicenseGenerator.html';
}

function proceed() {
	$.fancybox.close();
	var url = "PlumberLicenseGenerator.html?printPlumberLicense";
	/*
	 * var formName = findClosestElementId(obj, 'form'); var theForm = '#' +
	 * formName; var request = __serializeForm(theForm);
	 */
	var ajaxResponse = __doAjaxRequest(url, 'post', {}, false, 'html');
	var divContents = ajaxResponse;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	/*
	 * printWindow.document .write('<div style="position:fixed; width:100%;
	 * bottom:0px; z-index:1111;"><div class="text-center"><button
	 * onclick="printContent(receipt);" class="fa fa-print padding-right-5
	 * hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i>
	 * Print</button> <button onClick="window.close();" type="button" class="fa
	 * fa-times padding-right-5 hidden-print">Close</button></div></div>')
	 */
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	prepareTags();
}

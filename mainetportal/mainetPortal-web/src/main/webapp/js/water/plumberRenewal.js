$('.monthPick').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	yearRange : "-100:-0",
});
$(document).ready(
		function() {
			
			if ($("#plumIdFlag").val() == "Y") {
				$("#selectHideShow").hide();
				$("#getLicense").hide();
			} else if ($("#plumIdFlag").val() == "N") {
				$("#appli").find("input,select").attr("disabled", "disabled");
				$("#selectHideShow").show();
				$("#getLicense").show();
				$("#searchButt").hide();
				$("#backButt").hide();
				$("#resetBtn").hide();
				$("#plumberLicenceNo").attr('disabled', true);
			}

			var dateFields = $('.lessthancurrdate');
			dateFields.each(function() {
				var fieldValue = $(this).val();
				if (fieldValue.length > 10) {
					$(this).val(fieldValue.substr(0, 10));
				}
			});
			if ($("#povertyLineId").val() == 'Y') {
				$("#bpldiv").show();
				$("#bplNo").data('rule-required', true);
			}
			if ($('#povertyLineId').val() == 'N') {
				$("#bpldiv").hide();
				$("#bplNo").data('rule-required', false);
			}

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
				yearRange : "-100:-0",
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
									yearRange : "-100:-0",
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
						$('.experienceAppendable').each(
								function(i) {
									errorList = validateExperienceDetails(
											errorList, i);
								});

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
							onSelect : function(selected, evnt) {
								updateExperienceDetails();
							}
						});

						reOrderExperienceTableIdSequence();
					});

			$("#experienceTableId").on('click', '.experienceDeleteRow',
					function() {

						if ($("#experienceTableId tr").length != 2) {

							$(this).parent().parent().remove();
						} else {
							alert("You can not delete first row");
						}
						reOrderExperienceTableIdSequence();
					});
			$('label[for="' + $('#dwzid1').attr('id') + '"]').hide();
			$('label[for="' + $('#dwzid2').attr('id') + '"]').hide();
			$('label[for="' + $('#dwzid3').attr('id') + '"]').hide();
			$('#dwzid1').hide();
			$('#dwzid2').hide();
			$('#dwzid3').hide();
			$("#applicantTitle").prop("disabled", false);
			
			var readonly_select = $('select');
			$(readonly_select).attr('readonly', true).attr('data-original-value', $(readonly_select).val()).on('change', function(i) {
			    $(i.target).val($(this).attr('data-original-value'));
			});
		});
function validateAcademicDetails(errorList, i) {

	var qualification = $.trim($("#qualificationId" + i).val());
	var institute = $.trim($("#instituteNameId" + i).val());
	var address = $.trim($("#instituteAddrsId" + i).val());
	var passYear = $.trim($("#passYearId" + i).val());
	var passMonth = $.trim($("#passMonthId" + i).val());
	var grade = $.trim($("#percentGradeId" + i).val());
	if (qualification == 0 || qualification == "") {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.enterQual"));
	}
	if (institute == 0 || institute == "") {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.enterInstituteName"));
	}
	if (address == 0 || address == "") {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.enterInstituteAddrs"));
	}

	if (passYear == 0 || passYear == "") {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.enterPassingYear"));
	}

	if (passMonth == 0 || passMonth == "") {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.enterPassingMonth"));
	}

	if (grade == 0 || grade == "") {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.enterPercentOrGrade"));
	}
	return errorList;
}

function validateExperienceDetails(errorList, i) {
	
	var companyName = $.trim($("#companyNameId" + i).val());
	var companyAddrs = $.trim($("#companyAddrsId" + i).val());
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
	if ((new Date(dateFormate(fromDate)) > new Date(dateFormate(toDate)))) {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.frmDtLessthanToDt"));
	}
	if ($.datepicker.parseDate('dd/mm/yy', fromDate) > $.datepicker.parseDate(
			'dd/mm/yy', toDate)) {
		errorList
				.push(getLocalMessage("water.plumberLicense.valMsg.frmDtLessthanToDt"));
	}
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
function getChecklistAndChargesPLRenewal(obj) {

	// var isBPL = $('#povertyLineId').val();

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var request = __serializeForm(theForm);
	var url = 'PlumberLicenseRenewal.html?getCheckListAndCharges';
	var returnData = __doAjaxRequest(url, 'POST', request, false);
	$(formDivName).html(returnData);

}

function checkAcademicDetails(errorList) {
	$(".academicAppendable").each(function(i) {
		errorList = validateAcademicDetails(errorList, i);
	});
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

/*	if (povertyLineId == "" || povertyLineId == '0'
			|| povertyLineId == undefined) {
		errorList.push(getLocalMessage('Please Select Is Below PovertyLine'));
	}*/
	return errorList;
}

function closeOutErrBox() {
	$('.error-div').hide();
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

function savePlRenwData(element) {
	
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		return saveOrUpdateForm(element,
				"Your application for License Renewal saved successfully!",
				'PlumberLicenseRenewal.html?PrintReport', 'saveLicRenewal');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		return saveOrUpdateForm(element,
				"Your application for License Renewal saved successfully!",
				'PlumberLicenseRenewal.html?redirectToPay', 'saveLicRenewal');
	} else {
		return saveOrUpdateForm(element,
				"Your application for License Renewal saved successfully!",
				'CitizenHome.html', 'saveLicRenewal');
	}

	return result;
}

function viewLicenseDetails(obj) {
	

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var request = __serializeForm(theForm);
	var url = 'PlumberLicenseRenewal.html?getLicApplicantDet';
	var returnData = __doAjaxRequest(url, 'POST', request, false, 'html');
	$(formDivName).html(returnData);
}

function searchApplicationData(obj) {
	
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var url = 'PlumberRenewalApproval.html?getApplicantDetails';

	$(theForm).attr('action', url);

	$(theForm).submit();
}

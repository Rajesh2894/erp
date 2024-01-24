$(document)
		.ready(
				function() {

					$('#form_grievanceReport').validate({
						onkeyup : function(element) {
							this.element(element);
							/* console.log('onkeyup fired'); */
						},
						onfocusout : function(element) {
							this.element(element);
							/* console.log('onfocusout fired'); */
						}
					});

					$('.datepicker').change(function() {
						$('.datepicker').trigger("keyup");
					});

					$('#department, #complaintType, #orgId').on(
							'chosen:hiding_dropdown', function(evt, params) {
								validateChosen(this);
							});

					$('#department, #complaintType, #orgId').chosen().addClass(
							"chosen-select-no-results");
					$('.datepicker').removeAttr("disabled");
					$('.btnReset').click(function(event) {
						$('select').val('0').trigger('chosen:updated');
						$('.compalint-error-div').hide();
						$("#id_status").data('rule-required', false);
					});

					$('#fromSlab')
							.after(
									"<small class='text-blue-2'> "
											+ getLocalMessage('care.reports.note.fromSlab'));
					$('#toSlab')
							.after(
									"<small class='text-blue-2'> "
											+ getLocalMessage('care.reports.note.toSlab'));
					$('#slabLevels')
							.after(
									"<small class='text-blue-2'> "
											+ getLocalMessage('care.reports.note.LevelsPlus'));

					// Ward Zone alignment
					$('.wzfilters').find('script').remove();
					var wzlabels = $('.wzfilters .container').children('label');
					var wzspan = $('.wzfilters .container').children('span');
					$.each(wzlabels, function(index, value) {
						var l = $(value).html();
						if (index % 2 === 0) {
							$(value).replaceWith(
									'<div class="pull-left padding-right-10">'
											+ l + ':</div>');
						} else {
							$(value).replaceWith(
									'<div class="pull-right padding-left-10">'
											+ l + ':</div>');
						}
					});

					$.each(wzspan,
							function(index, value) {
								var l = $(value).html();
								if (index % 2 === 0) {
									$(value).replaceWith(
											'<span class="text-left">' + l
													+ '</span>');
								} else {
									$(value).replaceWith(
											'<span class="text-left">' + l
													+ '</span>');
								}
							});

					$(".wzfilters .container span.text-left").each(function() {
						$(this).prev().append(this);
					});

					// On department change
					$('#department')
							.change(
									function() {
										if ($('#department').val()) {
											var requestData = {
												"deptId" : $('#department')
														.val()
											}
											// Fetch Ward Zone
											var response = __doAjaxRequest(
													'GrievanceReport.html?grievanceReportWardZone',
													'post', requestData, false,
													'html');
											if (response == "") {
												$('#zone-ward').html('');
												$('#zone-ward').hide();
											} else {
												$('#zone-ward').html(response);
												$('#zone-ward').show();
											}
											if ($('#department').val() > 0) {
												// Fetch Complaint Types
												var response = __doAjaxRequest(
														'GrievanceReport.html?grievanceReportComplaintType',
														'post', requestData,
														false, 'html');
												if (response == "") {
													$('#id_complaintType')
															.html('');
													$('#id_complaintType')
															.hide();
												} else {
													$('#id_complaintType')
															.html(response);
													$('#id_complaintType')
															.show();
												}
											}

											if ($('#department').val() == '-1') {
												$('#complaintType').val(-1)
														.trigger(
																"chosen:updated")
											}
										}
									});

					$("#rstButton").click(function() {
						this.form.reset();
						resetCareForm();
						// resetOtherFields();
					});

					$('#slabLevels').change(function() {
						var input = parseInt($('#slabLevels').val());
						if (input == 0)
							$('#slabLevels').val(1);
						if (input > 5)
							$('#slabLevels').val(5);
					});

					$('.datepicker').change(
							function() {
								$('.error-div').hide();
								var errorList = [];
								errorList = fromToDateValidation(errorList,
										'fromDate', 'toDate')
								if (errorList.length > 0) {
									displayErrorsOnPage(errorList);
								}
							});

				});

function resetCareForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$('#zone-ward').html('');
	$('#zone-ward').hide();
	$('.compalint-error-div').hide();
	$("#form_grievanceReport").validate().resetForm();
	$('select').val('0').trigger('chosen:updated');

}

function afterReset(e) {

	$('#zone-ward').html('');
	$('#zone-ward').hide();
	$('.compalint-error-div').hide();
	// $('.error.mandColorClass').hide();
}

// Print Div
function PrintDiv(title) {
	if ($.fn.DataTable.isDataTable('#report-table')) {
		$('#report-table').DataTable().destroy();
	}
	var divContents = document.getElementById("report-print").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot tr.print-remove").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	$('#report-table').DataTable();
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function fromToDateValidation(errorList, fromDateElementId, toDateElementId) {

	var formDateValue = $("#" + fromDateElementId).val();
	var toDateValue = $("#" + toDateElementId).val();

	if ((formDateValue == "" || formDateValue == null || formDateValue == undefined)
			&& (toDateValue == "" || toDateValue == null || toDateValue == undefined)) {
		errorList.push(getLocalMessage('care.reports.select.fromtoDate'));
	} else if ((formDateValue != "" && formDateValue != null && formDateValue != undefined)
			&& (toDateValue == "" || toDateValue == null || toDateValue == undefined)) {
		errorList.push(getLocalMessage('care.reports.select.toDate'));
	}
	/*
	 * else if((formDateValue == "" || formDateValue == null || formDateValue ==
	 * undefined) && (toDateValue != "" && toDateValue != null && toDateValue !=
	 * undefined)) {
	 * errorList.push(getLocalMessage('care.reports.select.fromDate')); }
	 */
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var eDate = new Date(formDateValue.replace(pattern, '$3-$2-$1'));
	var sDate = new Date(toDateValue.replace(pattern, '$3-$2-$1'));
	if (eDate > sDate) {
		errorList.push(getLocalMessage('care.reports.msgtoDate'));
	}
	if (formDateValue != "") {
		if (pattern.test(formDateValue) == false) {
			errorList.push(getLocalMessage('care.reports.wrongfromDate'));
		}
	}
	if (toDateValue != "") {
		if (pattern.test(toDateValue) == false) {
			errorList.push(getLocalMessage('care.reports.wrongtoDate'));
		}
	}

	var selectedDate = document.getElementById("toDate").value;
	var dateSplit = selectedDate.split("/");
	var length = dateSplit.length;
	var selectedDateObj = new Date(dateSplit[length - 1],
			dateSplit[length - 2] - 1, dateSplit[length - 3]);
	var rightnow = new Date();
	var dd = rightnow.getDate();
	var mm = rightnow.getMonth() + 1;
	var yyyy = rightnow.getFullYear();
	var currentDateObj = new Date(rightnow.getFullYear(), rightnow.getMonth(),
			dd);
	if ((selectedDateObj > currentDateObj)) {
		errorList.push(getLocalMessage('care.reports.futureDate'));
	}

	return errorList;

}

function submitComplaintAgeing(obj) {
	var errorList = [];
	errorList = validateComplaintAgeing(errorList);
	if (errorList.length == 0) {

		var statusValue = $("#id_status").val();
		var finalStatus = "";
		if (statusValue == "0") {
			finalStatus = "0";
		}
		if (statusValue == "1") {
			finalStatus = "-1";
		}
		if (statusValue == "2") {
			finalStatus = "APPROVED";
		}
		if (statusValue == "3") {
			finalStatus = "PENDING";
		}
		if (statusValue == "4") {
			finalStatus = "REJECTED";
		}
		if (statusValue == "5") {
			finalStatus = "HOLD";
		}
		$("#id_status").val(finalStatus);
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceAgeingReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateComplaintAgeing(errorList) {

	var dept = $("#department").val();
	var compType = $("#complaintType").val();
	var status = $("#id_status").val();
	var reportType = $("#id_reportType").val();
	var fromSlab = $("#fromSlab").val();
	var toSlab = $("#toSlab").val();
	var levels = $("#slabLevels").val();

	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}
	if (compType == "0" || compType == undefined) {
		errorList.push(getLocalMessage('care.validn.compSubType'));
	}

	if (status == "0" || status == undefined) {
		errorList.push(getLocalMessage('service.error.smServActive'));
	}
	if (reportType == "0" || reportType == undefined) {
		errorList.push(getLocalMessage('service.error.report'));
	}
	if (fromSlab == "" || fromSlab == undefined) {
		errorList.push(getLocalMessage('service.error.fromSlab'));
	}
	if (toSlab == "" || toSlab == undefined) {
		errorList.push(getLocalMessage('service.error.toSlab'));
	}
	if (fromSlab != "" && toSlab != "" && parseInt(fromSlab) > parseInt(toSlab)) {
		errorList.push(getLocalMessage('service.error.fromtoSlab'));
	}
	if (levels == "" || levels == undefined) {
		errorList.push(getLocalMessage('service.error.levels'));
	}

	return errorList;
}

function submitComplaintDept(obj) {

	var errorList = [];
	errorList = validateComplaintDept(errorList);
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')

	var statusValue = $("#id_status").val();
	var finalStatus = "";
	if (statusValue == "0") {
		finalStatus = "0";
	}
	if (statusValue == "1") {
		finalStatus = "-1";
	}
	if (statusValue == "2") {
		finalStatus = "APPROVED";
	}
	if (statusValue == "3") {
		finalStatus = "PENDING";
	}
	if (statusValue == "4") {
		finalStatus = "REJECTED";
	}
	if (statusValue == "5") {
		finalStatus = "HOLD";
	}
	$("#id_status").val(finalStatus);

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceDeptWiseReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateComplaintDept(errorList) {

	var dept = $("#department").val();
	var formDateValue = $("#fromDate").val();
	var toDateValue = $("#toDate").val();
	var status = $("#id_status").val();
	var kdmcEnv = $("#kdmcEnv").val();
	/*
	 * var codIdOperLevel1=$("#codIdOperLevel1").val(); var
	 * codIdOperLevel2=$("#codIdOperLevel2").val();
	 */

	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}

	if (status == "0" || status == undefined) {
		errorList.push(getLocalMessage('service.error.smServActive'));
	}
	//#129060 - changes for summary and details report in case of skdcl env
	if (kdmcEnv == 'Y'){
		reportMode =	$.trim($("input[name='careReportRequest.reportName']:checked").val())
		if (reportMode == 0 || reportMode == "")
			errorList.push(getLocalMessage("care.checkRadio.report"));
	}

	/*
	 * if(codIdOperLevel1 == "0" || codIdOperLevel1 == undefined){
	 * errorList.push(getLocalMessage('please select zone')); }
	 * 
	 * if(codIdOperLevel2 == "0" || codIdOperLevel2 == undefined){
	 * errorList.push(getLocalMessage('please select ward')); }
	 */

	return errorList;
}

function submitComplaintFeedback(obj) {

	var errorList = [];
    //#129060 - changes for summary and details report in case of skdcl env
    var kdmcEnv = $("#kdmcEnv").val();
    if (kdmcEnv == 'Y'){
		reportMode =	$.trim($("input[name='careReportRequest.reportName']:checked").val())
		if (reportMode == 0 || reportMode == "")
		errorList.push(getLocalMessage("care.checkRadio.report"));
    }
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceFeedbackReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function submitComplaintStsWise(obj) {

	var errorList = [];
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')

	var statusValue = $("#id_status").val();
	var finalStatus = "";
	if (statusValue == "0") {
		finalStatus = "0";
	}
	if (statusValue == "1") {
		finalStatus = "-1";
	}
	if (statusValue == "2") {
		finalStatus = "APPROVED";
	}
	if (statusValue == "3") {
		finalStatus = "PENDING";
	}
	if (statusValue == "4") {
		finalStatus = "REJECTED";
	}
	if (statusValue == "5") {
		finalStatus = "HOLD";
	}
	$("#id_status").val(finalStatus);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	errorList = validateComplaintStsWise(errorList);
	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceDeptStatWiseReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateComplaintStsWise(errorList) {

	var dept = $("#department").val();
	var status = $("#id_status").val();
	var kdmcEnv = $("#kdmcEnv").val();
	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}
	if (status == "0" || status == undefined) {
		errorList.push(getLocalMessage('service.error.smServActive'));
	}
    //#129060 - changes for summary and details report in case of skdcl env
	if (kdmcEnv == 'Y'){
		reportMode =	$.trim($("input[name='careReportRequest.reportName']:checked").val())
		if (reportMode == 0 || reportMode == "")
		errorList.push(getLocalMessage("care.checkRadio.report"));
	}
	return errorList;
}

function submitComplaintSlaWise(obj) {

	var errorList = [];
	errorList = validateComplaintSlaWise(errorList);
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceSlaWiseReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateComplaintSlaWise(errorList) {

	var dept = $("#department").val();
	var status = $("#id_status").val();
	var kdmcEnv = $("#kdmcEnv").val();
	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}
	if (status == "0" || status == undefined) {
		errorList.push(getLocalMessage('service.error.smServActive'));
	}
	
	//#129060 - changes for summary and details report in case of skdcl env
	if (kdmcEnv == 'Y'){
	reportMode =	$.trim($("input[name='careReportRequest.reportName']:checked").val())
	if (reportMode == 0 || reportMode == "")
	errorList.push(getLocalMessage("care.checkRadio.report"));
	}
	
	return errorList;
}

function submitComplaintGrading(obj) {

	var errorList = [];
	errorList = validateComplaintGrading(errorList);
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceGradingReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateComplaintGrading(errorList) {

	var dept = $("#department").val();
	var formDateValue = $("#fromDate").val();
	var toDateValue = $("#toDate").val();
	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}

	return errorList;
}

function validateChosen(obj) {
	var ID = $(obj).attr("id");
	if (!$(obj).valid()) {
		$("#" + ID + "_chosen").addClass("input-validation-error");
	} else {
		$("#" + ID + "_chosen").removeClass("input-validation-error");
	}
	if ($(obj).val() == -1) {
		$("#" + ID + "_chosen").removeClass("input-validation-error");
		var errorlable = $("#" + ID + "_chosen").next();
		if (errorlable.is('label.error')) {
			errorlable.hide();
		}
	}
}

function searchComplaintDeptAndSeviceWise(obj) {
	var errorList = [];
	errorList = validateComplaintDeptAndStatusWise(errorList);
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceDeptAndServiceWiseReport');
	} else {
		displayErrorsOnPage(errorList);
	}

}
function validateComplaintDeptAndStatusWise(errorList) {
	var dept = $("#department").val();
	var compType = $("#complaintType").val();

	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}
	if (compType == "0" || compType == undefined) {
		errorList.push(getLocalMessage('care.validn.compSubType'));
	}

	return errorList;

}
function searchComplaintDeptAndSeviceWiseAndUserWise(obj) {
	var errorList = [];
	errorList = validateComplaintDeptAndStatusWiseAndUserwise(errorList);
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceDeptAndServiceAndUserWiseReport');
	} else {
		displayErrorsOnPage(errorList);
	}

}
function validateComplaintDeptAndStatusWiseAndUserwise(errorList) {
	var dept = $("#department").val();
	var compType = $("#complaintType").val();
	let referenceMode = $('#referenceMode').val();
	var kdmcEnv = $("#kdmcEnv").val();
	if (dept == "0" || dept == undefined) {
		errorList.push(getLocalMessage('care.validn.compDept'));
	}
	if (compType == "0" || compType == undefined) {
		errorList.push(getLocalMessage('care.validn.compSubType'));
	}
	if (referenceMode == "0" || referenceMode == undefined) {
		errorList.push(getLocalMessage('care.validn.report.mode'));
	}
    //#129060 - changes for summary and details report in case of skdcl env
	if (kdmcEnv == 'Y'){
		reportMode =	$.trim($("input[name='careReportRequest.reportName']:checked").val())
		if (reportMode == 0 || reportMode == "")
		errorList.push(getLocalMessage("care.checkRadio.report"));
	}
	return errorList;

}
function submitComplaintSmsEmailHistoryForm(obj) {
	var errorList = [];
	errorList = fromToDateValidation(errorList, 'fromDate', 'toDate')
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "GrievanceReport.html",
				'grievanceSmsEmailHistoryReport');
	} else {
		displayErrorsOnPage(errorList);
	}
}
//Defect #112287
function resetFormData() {
$('input[type=text]').val('');
$(".alert-danger").hide();
$('#zone-ward').html('');
$('#zone-ward').hide();
$('.compalint-error-div').hide();
$("#form_grievanceReport").validate().resetForm();
$('select').val('0').trigger('chosen:updated');
}
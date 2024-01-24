/**
 * @Author hiren.poriya
 */

var TENDER_URL = "TenderInitiation.html";
var WorkDefURL = "WmsWorkDefinationMaster.html";
var removeWorkIdArray = [];
$(document)
		.ready(
				function() {

					$('#TenderInitiation').validate({
						onkeyup : function(element) {
							this.element(element);
							console.log('onkeyup fired');
						},
						onfocusout : function(element) {
							this.element(element);
							console.log('onfocusout fired');
						}
					});

					$('[id^="tenderType"]').data('rule-required', true);
					$('[id^="tenderValue"]').data('rule-required', true);
					$('[id^="vendorId"]').data('rule-required', true);
					$('[id^="vendorWorkPeriodUnit"]').data('rule-required',
							true);
					$('[id^="venderClassId"]').data('rule-required', true);

					$("#datatables").dataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true
							});

					$("#prepareTender").click(
							function() {
								var requestData = {
									"type" : "C"
								}
								var ajaxResponse = __doAjaxRequest(TENDER_URL
										+ '?form', 'POST', requestData, false,
										'html');
								$('.content-page').html(ajaxResponse);
							});

					// tender date

					$('.tenderDatePicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d',
						yearRange : "-100:-0"
					});

					var tenderDatePicker = $('.tenderDatePicker');
					tenderDatePicker.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					// resolution date picker
					$('.resolutionDatePicker').datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								maxDate : '-0d',
								yearRange : "-100:-0",
								onSelect : function(selected) {
									$(".issueFromDatePicker").datepicker(
											"option", "minDate", selected);
									$(".publishDatePicker").datetimepicker(
											"option", "minDate", selected);
									// $("#resolutionNo").addClass("mandColorClass");
									// $("#resolutionNo").data('rule-required',true);

								}
							});

					var resolutionDate = $('#resolutionDate');
					resolutionDate.each(function() {
						var resolutionDates = $(this).val();
						if (resolutionDates.length > 10) {
							$(this).val(resolutionDates.substr(0, 10));
						}
					});
					var dateFields = $('.resolutionDatePicker');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					// issue from date date picker
					$('.issueFromDatePicker').datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onSelect : function(selectedDate) {
									$(".issueToDatePicker").datepicker(
											"option", "minDate", selectedDate);
									$(".publishDatePicker").datetimepicker(
											"option", "maxDate", selectedDate);
									issueFromDateValid();
								}
							});
					var datepickerEndDate = $('.issueFromDatePicker');
					datepickerEndDate.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					// issue to date date picker
					$('.issueToDatePicker').datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onSelect : function(selectedDate) {
									$(".technicalOpenDatePicker")
											.datetimepicker("option",
													"minDate", selectedDate);
									issueToDateValid();
								}
							});

					var issueToDatePicker = $('.issueToDatePicker');
					issueToDatePicker.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					// technical open date
					$('.technicalOpenDatePicker').datetimepicker(
							{
								dateFormat : 'dd/mm/yy',
								timeFormat : "HH:mm",
								changeMonth : true,
								changeYear : true,
								yearRange : "1900:2200",
								onSelect : function(selectedDate) {

									$(".financialeOpenDatePicker")
											.datetimepicker("option",
													"minDate", selectedDate);
									technicalOpenDateValid();
								}
							});

					/*
					 * var technicalOpenDatePicker =
					 * $('.technicalOpenDatePicker');
					 * technicalOpenDatePicker.each(function () { var fieldValue =
					 * $(this).val(); if (fieldValue.length > 16) {
					 * $(this).val(fieldValue.substr(0, 16)); } });
					 */

					// financial open date
					$('.financialeOpenDatePicker').datetimepicker({
						dateFormat : 'dd/mm/yy',
						timeFormat : "HH:mm",
						changeMonth : true,
						changeYear : true,
						yearRange : "1900:2200",
						onSelect : function(selected) {
							financeDateValid();
						}
					});

					/*
					 * var financialeOpenDatePicker =
					 * $('.financialeOpenDatePicker');
					 * financialeOpenDatePicker.each(function () { var
					 * fieldValue = $(this).val(); if (fieldValue.length > 16) {
					 * $(this).val(fieldValue.substr(0, 16)); } });
					 */

					// publish date picker
					$('.publishDatePicker').datetimepicker({
						dateFormat : 'dd/mm/yy',
						timeFormat : "HH:mm",
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d',
						yearRange : "1900:2200",
						onSelect : function(selected) {
							publishDateValid();
						}

					});

					/*
					 * var publishDatePicker = $('.publishDatePicker');
					 * publishDatePicker.each(function () { var fieldValue =
					 * $(this).val(); if (fieldValue.length > 16) {
					 * $(this).val(fieldValue.substr(0, 16)); } });
					 */

					// financial open date
					$('.initiationDatePicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						yearRange : "1900:2200"
					});

					var initiationDatePicker = $('.initiationDatePicker');
					initiationDatePicker.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					$('.preBidMeetDatePicker').datetimepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						timeFormat : "HH:mm"

					});
					/*
					 * var preBidMeetDateDatePicker =
					 * $('.preBidMeetDatePicker');
					 * preBidMeetDateDatePicker.each(function () { var
					 * fieldValue = $(this).val(); if (fieldValue.length > 16) {
					 * $(this).val(fieldValue.substr(0, 16)); } });
					 */

					$("#resetTender").click(
							function() {
								var requestData = {
									"mode" : "C"
								}
								var ajaxResponse = __doAjaxRequest(TENDER_URL
										+ '?form', 'POST', requestData, false,
										'html');
								$('.content-page').html(ajaxResponse);
								$('.workProj').html("");
							});

					$("#searchTender")
							.click(
									function() {

										var errorList = [];
										var projId = $('#projId').val();
										// var initiationNo =
										// $('#initiationNo').val();
										// var initiationDate =
										// $('#initiationDate').val();
										var tenderCategory = $(
												'#tenderCategory').val();
										if (projId != ''
												|| tenderCategory != '0') {
											var requestData = 'projId='
													+ projId
													+ '&tenderCategory='
													+ tenderCategory;
											var table = $('#datatables')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest(
													TENDER_URL
															+ '?filterTenderData',
													'POST', requestData, false,
													'json');
											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {
																var obj = ajaxResponse[index];
																if ($(
																		'#serviceFlag')
																		.val() != 'A') {
																	debugger;
																	if (obj.initiationNo == null) {
																		result
																				.push([
																						obj.tenderNo,
																						obj.projectName,
																						obj.tenderCategoryDesc,
																						obj.tenderTotalEstiAmount,
																						'<td class="text-center">'
																								+ '<button type="button"  class="btn btn-green-3 btn-sm margin-right-10 " style="margin-left:10px;" onClick="initiateTender(\''
																								+ obj.tndId
																								+ '\')" title="Initiate Tender"><i class="fa fa-share-square-o"></i></button>'
																								+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="tenderView(\''
																								+ obj.tndId
																								+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																								+ '<button type="button" class="btn btn-warning btn-sm margin-right-10" onClick="editTender(\''
																								+ obj.tndId
																								+ '\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
																								+ '<button type="button" class="btn btn-danger btn-sm margin-right-10" onClick="deleteTender(\''
																								+ obj.tndId
																								+ '\')"  title="Delete"><i class="fa fa-trash"></i></button>'
																								+ '</td>' ]);
																	} else {
																		if (obj.tenderNo == null) {
																			result
																					.push([
																							obj.tenderNo,
																							obj.projectName,
																							obj.tenderCategoryDesc,
																							obj.tenderTotalEstiAmount,
																							'<td class="text-center">'
																									+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="tenderView(\''
																									+ obj.tndId
																									+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																									+ '<button type="button"  class="btn btn-primary btn-sm margin-right-10 " style="margin-left:10px;" onClick="updateTender(\''
																									+ obj.tndId
																									+ '\')" title="Update"><i class="fa fa-line-chart"></i></button>'
																									+
																									/*
																									 * '<button
																									 * type="button"
																									 * class="btn
																									 * btn-danger
																									 * btn-sm
																									 * margin-right-10"
																									 * onClick="deleteTender(\''+obj.tndId+'\')"
																									 * title="Delete"><i
																									 * class="fa
																									 * fa-trash"></i></button>'+
																									 */
																									'</td>' ]);
																		} else {
																			result
																					.push([
																							obj.tenderNo,
																							obj.projectName,
																							obj.tenderCategoryDesc,
																							obj.tenderTotalEstiAmount,
																							'<td class="text-center">'
																									+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="tenderView(\''
																									+ obj.tndId
																									+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																									+ '<button type="button"  class="btn btn-primary btn-sm margin-right-10 " style="margin-left:10px;" onClick="updateTender(\''
																									+ obj.tndId
																									+ '\')" title="Award details"><i class="fa fa-line-chart"></i></button>'
																									+ '<button type="button" class="btn btn-warning btn-sm margin-right-10" onClick="printLoaByTenderDetislId(\''
																									+ obj.tndId
																									+ '\')"  title="Print LOA"><i class="fa fa-print"></i></button>'
																									+ '</td>' ]);
																		}
																	}
																} else {
																	if (obj.status == "P"
																			|| obj.status == "R") {
																		result
																				.push([
																						obj.tenderNo,
																						obj.projectName,
																						obj.tenderCategoryDesc,
																						obj.tenderTotalEstiAmount,
																						obj.statusDesc,
																						'<td class="text-center">'
																								+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="tenderView(\''
																								+ obj.tndId
																								+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																								+ '</td>' ]);
																	} else if (obj.status == "A"
																			&& obj.tenderNo == null) {
																		result
																				.push([
																						obj.tenderNo,
																						obj.projectName,
																						obj.tenderCategoryDesc,
																						obj.tenderTotalEstiAmount,
																						obj.statusDesc,
																						'<td class="text-center">'
																								+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="tenderView(\''
																								+ obj.tndId
																								+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																								+ '<button type="button"  class="btn btn-primary btn-sm margin-right-10 " style="margin-left:10px;" onClick="updateTender(\''
																								+ obj.tndId
																								+ '\')" title="Award details"><i class="fa fa-line-chart"></i></button>'
																								+ '</td>' ]);
																	} else {
																		result
																				.push([
																						obj.tenderNo,
																						obj.projectName,
																						obj.tenderCategoryDesc,
																						obj.tenderTotalEstiAmount,
																						obj.statusDesc,
																						'<td class="text-center">'
																								+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="tenderView(\''
																								+ obj.tndId
																								+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																								+ '<button type="button"  class="btn btn-primary btn-sm margin-right-10 " style="margin-left:10px;" onClick="updateTender(\''
																								+ obj.tndId
																								+ '\')" title="Award details"><i class="fa fa-line-chart"></i></button>'
																								+ '<button type="button" class="btn btn-warning btn-sm margin-right-10" onClick="printLoaByTenderDetislId(\''
																								+ obj.tndId
																								+ '\')"  title="Print LOA"><i class="fa fa-print"></i></button>'
																								+ '</td>' ]);
																	}

																}
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage('tender.search.validation'));
											displayErrorsOnPage(errorList);
										}
									});

					if ($("#mode").val() == "E" || $("#mode").val() == "V") {

						// issue from date should be after resolution date.
						var resolutionDate = $("#resolutionDate").val();
						$(".issueFromDatePicker ").datepicker("option",
								"minDate", resolutionDate);

						if (resolutionDate != null && resolutionDate != "") {
							$("#resolutionNo").addClass("mandColorClass");
						}

						// issue to date should be after issue from date.
						var issueFromDate = $("#issueFromDate").val();
						$(".issueToDatePicker").datepicker("option", "minDate",
								issueFromDate);

						// publish date should be between resolution date and
						// issue from date.
						var pDate = $("#publishDate").val();
						$(".publishDatePicker").datetimepicker("option",
								"minDate", resolutionDate);
						$(".publishDatePicker").datetimepicker("option",
								"maxDate", issueFromDate);
						$("#publishDate").val(pDate);
						// technical open date should be after issue to date.
						var issueToDate = $("#issueToDate").val();
						var tDate = $("#technicalOpenDate").val();
						$(".technicalOpenDatePicker").datetimepicker("option",
								"minDate", issueToDate);
						$("#technicalOpenDate").val(tDate);

						// financial open date should be after technical open
						// date.
						var fDate = $("#financialeOpenDate").val();
						var technicalOpenDate = $("#technicalOpenDate").val();
						$(".financialeOpenDatePicker ").datetimepicker(
								"option", "minDate", technicalOpenDate);
						if (fDate != '')
							$("#financialeOpenDate").val(fDate);
					}
					if ($("#mode").val() == "C") {
						$("#tndValidityDay").val(parseInt(120));
					}

				});

function financeDateValid() {
	var technicalOpenDate = $("#technicalOpenDate").val();
	var resolutionDate = $("#resolutionDate").val();
	if (resolutionDate == '') {
		showAlertBoxForForDateValid("financialeOpenDate",
				getLocalMessage("tender.resolutiondate.valid"));
	} else {
		if (technicalOpenDate == '') {
			showAlertBoxForForDateValid("financialeOpenDate",
					getLocalMessage("tender.techbiddate.valid"));
		}
	}

}

function technicalOpenDateValid() {
	var issueToDate = $("#issueToDate").val();
	var resolutionDate = $("#resolutionDate").val();
	if (resolutionDate == '') {
		showAlertBoxForForDateValid("technicalOpenDate",
				getLocalMessage("tender.resolutiondate.valid"));
	} else {
		if (issueToDate == '') {
			showAlertBoxForForDateValid("technicalOpenDate",
					getLocalMessage("tender.issuetill.valid"));
		}
	}
}

function issueToDateValid() {
	var issueFromDate = $("#issueFromDate").val();
	if (issueFromDate == '') {
		showAlertBoxForForDateValid("issueToDate",
				getLocalMessage("tender.issuefrom.valid"));
	}
}

function issueFromDateValid() {
	var resolutionDate = $("#resolutionDate").val();
	if (resolutionDate == '') {
		showAlertBoxForForDateValid("issueFromDate",
				getLocalMessage("tender.resolutiondate.valid"));
	}
}

function publishDateValid() {

	var resolutionDate = $("#resolutionDate").val();
	var issueFromDate = $("#issueFromDate").val();
	if (resolutionDate == '' || issueFromDate == '') {
		showAlertBoxForForDateValid("publishDate",
				getLocalMessage("tender.resolutiondate.issuefrom.valid"));
	}
}

function showAlertBoxForForDateValid(id, msg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Ok';
	message += '<h4 class=\"text-center text-blue-2 padding-10\">' + msg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$('#' + id).val("");
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
function getProjects() {
	// var deptId = $(obj).val();
	var deptId = $('#deptId').val();
	$('#projId').html('');
	$('#projId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown'))).trigger(
			'chosen:updated');
	$('.workClass').hide();
	if (deptId != "" && deptId != 0) {
		var requestData = {
			"deptId" : deptId
		}
		var projectList = __doAjaxRequest(TENDER_URL + '?getProjectList',
				'POST', requestData, false, 'json');
		$.each(projectList, function(index, value) {
			$('#projId').append(
					$("<option></option>").attr("value", value.projId).attr(
							"code", value.rsoNumber + "," + value.rsoDate)
							.text(value.projNameEng));
		});
		$('#projId').trigger('chosen:updated');
	}
}

function editTender(tdId) {
	var requestData = 'tdId=' + tdId + '&mode=E';
	var ajaxResponse = __doAjaxRequest(TENDER_URL + '?form', 'POST',
			requestData, false, 'html');
	$('.content-page').html(ajaxResponse);
}

function tenderView(tdId) {

	var requestData = 'tdId=' + tdId + '&mode=V';
	var ajaxResponse = __doAjaxRequest(TENDER_URL + '?form', 'POST',
			requestData, false, 'html');
	$('.content-page').html(ajaxResponse);
	$("#TenderInitiation :input").prop("disabled", true);
	$("select").prop("disabled", true).trigger("chosen:updated");
	$(".workClass").prop("disabled", false);
	$(".viewEstimate").prop("disabled", false);
	$(".viewNIT").prop("disabled", false);
	$(".viewFormA").prop("disabled", false);
	$(".viewPQR").prop("disabled", false);
	$(".viewFormF").prop("disabled", false);
	$("#button-Cancel").prop("disabled", false);

}
function initiateTender(tenderId) {

	var requestData = 'tenderId=' + tenderId;
	var response = __doAjaxRequest(TENDER_URL + '?initiateTender', 'POST',
			requestData, false, 'json');
	if (response == "Y") {
		showConfirmBox(getLocalMessage("tender.initated.success"));
	} else {
		showConfirmBox(response);
	}
}

function deleteTender(tndId) {
	var requestData = 'tndId=' + tndId;
	var response = __doAjaxRequest(TENDER_URL + '?deleteTender', 'POST',
			requestData, false, 'json');
	if (response) {
		showConfirmBox(getLocalMessage("tender.delete.success"));
	} else {
		$(childDivName).html("Internal errors");
		showModalBox(childDivName);
	}
}

function showConfirmBox(msg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	message += '<h4 class=\"text-center text-blue-2 padding-10\">' + msg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="BackTenderHome()"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function getApprovedWork(Object) {

	var projId = $(Object).val();
	$('.workClass').hide();
	$('.workProj').html('');

	$("#resolutionNo").val('');
	$("#resolutionDate").val('');

	if (projId != "" && projId != "0") {
		var requestData = {
			"projId" : projId
		}
		var response = __doAjaxRequest(TENDER_URL + '?getEstimatedWork',
				'POST', requestData, false, 'html');
		if (response == "") {
			$('#projId').val("").trigger('chosen:updated');
			var warnMsg = getLocalMessage("tender.noaprrovedwork.valid");
			var message = '<p class="text-blue-2 text-center padding-15">'
					+ warnMsg + '</p>';
			$(childDivName).addClass('ok-msg').removeClass('warn-msg');
			$(childDivName).html(message);
			$(childDivName).show();
			$('#yes').focus();
			showModalBox(childDivName);
			return false;
		} else {
			$('.workClass').html(response);
			$('.workClass').show();

			$('[id^="initiated"]').prop('checked', false);

			// enable estimate button for view estimate
			$('.viewEstimate').prop("disabled", false);

			// disabled all fields
			$('[id^="tenderFeeAmt"]').prop('disabled', true);
			$('[id^="venderClassId"]').prop('disabled', true);
			$('[id^="tenderSecAmt"]').prop('disabled', true);
			$('[id^="vendorWorkPeriodUnit"]').prop('disabled', true);
			$('[id^="vendorWorkPeriod"]').prop('disabled', true);
			$('[id^="tenderType"]').prop('disabled', true);

			var projectDetails = $("#projId").find("option:selected").attr(
					'code').split(",");
			/*
			 * $("#resolutionNo").val(projectDetails[0]);
			 * $("#resolutionDate").val(formatDate(projectDetails[1]));
			 */
			$(".issueFromDatePicker").datepicker("option", "minDate",
					formatDate(projectDetails[1]));
			$(".publishDatePicker").datetimepicker("option", "minDate",
					formatDate(projectDetails[1]));
		}
	}
}

function saveTender(formObj) {

	var errorList = [];
	errorList = validateTenderDetails(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(formObj, "", TENDER_URL, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateTenderDetails(errorList) {

	var deptId = $("#deptId").val();
	var projId = $("#projId").val();
	var tenderCategory = $("#tenderCategory").val();

	var resolutionDate = $("#resolutionDate").val();
	var resolutionNo = $("#resolutionNo").val();

	var issueFromDate = $("#issueFromDate").val();
	var issueToDate = $("#issueToDate").val();
	var publishDate = $("#publishDate").val();
	var technicalOpenDate = $("#technicalOpenDate").val();
	// var financialeOpenDate = $("#financialeOpenDate").val();
	var tndValidityDay = $("#tndValidityDay").val();

	if (deptId == null || deptId == "") {
		errorList.push(getLocalMessage('tender.select.dept'));
	}
	if (projId == null || projId == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
	}
	if (tenderCategory == null || tenderCategory == "0") {
		errorList.push(getLocalMessage('tender.select.category'));
	}

	if (resolutionNo == null || resolutionNo == "") {
		errorList.push(getLocalMessage('tender.enter.resolutionNo'));
	}

	if (resolutionDate == null || resolutionDate == "") {
		errorList.push(getLocalMessage('tender.please.select.resolution.date'));
	}

	if (issueFromDate == null || issueFromDate == "") {
		errorList.push(getLocalMessage('tender.please.select.issue.from.date'));
	}

	if (issueToDate == null || issueToDate == "") {
		errorList.push(getLocalMessage('tender.please.select.issue.todate'));
	}

	if (publishDate == null || publishDate == "") {
		errorList.push(getLocalMessage('tender.please.select.publish.date'));
	}

	if (technicalOpenDate == null || technicalOpenDate == "") {
		errorList
				.push(getLocalMessage('tender.please.select.technical.open.date'));
	}

	/*
	 * if(financialeOpenDate == null ||financialeOpenDate == ""){
	 * errorList.push(getLocalMessage('wms.Pleaseselectfinancialopendate')); }
	 */

	if (tndValidityDay == null || tndValidityDay == "") {
		errorList.push(getLocalMessage('tender.validity.vldn'));
	}

	if (errorList.length == 0) {
		errorList = validateTenderWork(errorList);
	}
	return errorList;
}

function validateTenderWork(errorList) {
	var workSeleted = false;
	$(".tenderWork")
			.each(
					function(i) {
						var initiated = $("#initiated" + i).is(':checked');
						if (initiated) {
							workSeleted = true;
							var tenderFeeAmt = $("#tenderFeeAmt" + i).val();
							var venderClassId = $("#venderClassId" + i).val();
							var tenderSecAmt = $("#tenderSecAmt" + i).val();
							var vendorWorkPeriodUnit = $(
									"#vendorWorkPeriodUnit" + i).val();
							var vendorWorkPeriod = $("#vendorWorkPeriod" + i)
									.val();
							var tenderType = $("#tenderType" + i).val();
							var c = i + 1;

							if (tenderType == null || tenderType == "") {
								errorList
										.push(getLocalMessage('wms.please.select.tender.type')
												+ "  " + c);
							}
							if (tenderFeeAmt == null || tenderFeeAmt == "") {
								errorList
										.push(getLocalMessage('wms.please.enter.tender.fee.amount')
												+ "   " + c);
							}
							if (venderClassId == null || venderClassId == "") {
								errorList
										.push(getLocalMessage('wms.please.select.vendor.class')
												+ "   " + c);
							}
							if (tenderSecAmt == null || tenderSecAmt == "") {
								errorList
										.push(getLocalMessage('wms.please.enter.tender.security.deposit.amount')
												+ "   " + c);
							}
							if (vendorWorkPeriodUnit == null
									|| vendorWorkPeriodUnit == "") {
								errorList
										.push(getLocalMessage('wms.please.select.work.duration.unit')
												+ "   " + c);
							}
							if (vendorWorkPeriod == null
									|| vendorWorkPeriod == "") {
								errorList
										.push(getLocalMessage('wms.please.enter.work.duration')
												+ "   " + c);
							}
						} else {
							if ($("#mode").val() == 'E') {
								var tndWId = $("#tndWId" + i).val();
								if (tndWId != null && tndWId != "") {
									removeWorkIdArray.push(tndWId);
								}
							}
						}
					});

	$('#removeWorkIdArray').val(removeWorkIdArray);
	if (!workSeleted) {
		errorList.push(getLocalMessage('tender.select.anywork.valid'));
	}
	return errorList;
}

function setAmount(index) {
	if ($("#initiated" + index).is(':checked')) {
		$("#tenderFeeAmt" + index).prop("disabled", false);
		$("#venderClassId" + index).prop("disabled", false);
		$("#tenderSecAmt" + index).prop("disabled", false);
		$("#vendorWorkPeriodUnit" + index).prop("disabled", false);
		$("#vendorWorkPeriod" + index).prop("disabled", false);
		$("#tenderType" + index).prop("disabled", false);
		filterVendorClass($("#workEstimateAmt" + index).val(), index); // used
		// to
		// filter
		// vendor
		// class
		// based
		// on
		// amount
	} else {
		$("#tenderFeeAmt" + index).val("").prop("disabled", true);
		$("#venderClassId" + index).val("").prop("disabled", true);
		$("#tenderSecAmt" + index).val("").prop("disabled", true);
		$("#vendorWorkPeriodUnit" + index).val("").prop("disabled", true);
		$("#vendorWorkPeriod" + index).val("").prop("disabled", true);
		$("#tenderType" + index).val("").prop("disabled", true);
	}
	;

	$("#totalAmt").val("0.00");

	var amount = 0;
	$(".tenderWork").each(function(i) {
		var initiated = $("#initiated" + i).is(':checked');
		if (initiated) {
			var n = parseFloat(parseFloat($("#workEstimateAmt" + i).val()));
			if (isNaN(n)) {
				return n = 0;
			}
			amount += n;
			var result = amount.toFixed(2);
			$("#totalAmt").val(result);
		}
	});
}

function updateTender(tndId) {
	var requestData = 'tndId=' + tndId;
	var response = __doAjaxRequest(TENDER_URL + '?updateTender', 'POST',
			requestData, false, 'html');
	$('.content-page').html(response);
}
function openTenderUpdateForm() {
	debugger;
	var response = __doAjaxRequest(TENDER_URL + '?openTenderUpdateForm',
			'POST', {}, false, 'html');
	$('.content-page').html(response);

}

function saveupdatedTender(formObj) {

	var errorList = [];
	errorList = validateUpdateTenderDetails(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(formObj, "", TENDER_URL, 'saveUpdateTender');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateUpdateTenderDetails(errorList) {

	var projId = $("#projId").val();
	var tenderNo = $("#tenderNo").val();
	var tenderDate = $("#tenderDate").val();

	if (projId == null || projId == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
	}
	if (tenderNo == null || tenderNo == "") {
		errorList.push(getLocalMessage('tender.enter.tenderNo'));
	}
	if (tenderDate == null || tenderDate == "") {
		errorList.push(getLocalMessage('tender.select.tenderdate'));
	}
	var count = 0;
	$(".tenderWorkUpdate")
			.each(
					function(i) {

						var tenderType = $("#tenderType" + i).val();
						var tenderValue = $("#tenderValue" + i).val();
						var vendorId = $("#vendorId" + i).val();
						/*
						 * Removed Field AS per SUDA UAT perspective 27/10/2018
						 * 
						 */
						// var tenderStampFee = $("#tenderStampFee"+i).val();
						var tenderNoOfDayAggremnt = $(
								"#tenderNoOfDayAggremnt" + i).val();

						var c = i + 1;
						if (tenderType == null || tenderType == "") {
							errorList
									.push(getLocalMessage('wms.Pleaseselecttendertype')
											+ c);
						}
						if (tenderValue == null || tenderValue == "") {
							errorList
									.push(getLocalMessage('wms.PleaseselecttenderValue')
											+ c);
						}
						if (vendorId == null || vendorId == "") {
							errorList
									.push(getLocalMessage('wms.Pleaseselectvendor')
											+ c);
						}
						/*
						 * if(tenderStampFee == null ||tenderStampFee == ""){
						 * errorList.push(getLocalMessage('Stamp Paper Fee Must
						 * not be Empty-'+c)); }
						 */
						if (tenderNoOfDayAggremnt == null
								|| tenderNoOfDayAggremnt == "") {
							errorList
									.push(getLocalMessage('wms.NoOfDaysForAggrementNotEmpty')
											+ c);
						}

					});
	return errorList;
}

function openVendorMas() {
	var VendorURL = 'Vendormaster.html'
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', VendorURL);
	$("#postMethodForm").submit();
}
function BackTenderHome() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', TENDER_URL);
	$("#postMethodForm").submit();
}

function viewWorkEstimate(workId) {
	var divName = '.content-page';
	var url = TENDER_URL + "?openEstimation";
	var requestData = $('#TenderInitiation').serialize() + "&workId=" + workId;
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getSorByValue();
}

function getSorByValue() {
	var actionParam = {
		'actionParam' : $("#sorList").val(),
		'sorId' : $("#sorId").val()
	}
	var url = "WorkEstimate.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}

function printLoa(tndId) {
	// #81911
	var requestData = {
		'tndId' : tndId,
		"loaMode" : "P",
		"mode" : "V"
	}
	var response = __doAjaxRequest(TENDER_URL + '?updateTender', 'POST',
			requestData, false, 'html');
	$('.content-page').html(response);
}

function printLoaByTenderDetislId(tndId) {

	var requestData = {
		'tndId' : tndId,
	}
	var response = __doAjaxRequest(TENDER_URL + '?generateAndPrintLOA', 'POST',
			requestData, false, 'html');
	$('.content-page').html(response);
}

function calculateTotalAmount(index) {
	var tenderType = $("#tenderType" + index).find("option:selected").attr(
			'code');
	var tenderValue = $("#tenderValue" + index).val();
	var workEstimateAmt = $("#workEstimateAmt" + index).val();
	var tendTypePercent = $("#tendTypePercent" + index).find("option:selected")
			.attr("code");
	var totalTenderAmount = "";

	if (tenderType == "PER") {
		$("#tendTypePercent" + index).prop("disabled", false);
	} else {
		$("#tendTypePercent" + index).prop("disabled", true);
		$('#tendTypePercent' + index).prop('selectedIndex', 0);
	}

	if (tenderType != "" && tenderValue != "" && workEstimateAmt != "") {
		if (tenderType == "PER") {
			if (tendTypePercent == "B") {
				totalTenderAmount = (Number(workEstimateAmt
						* Number(100 - Number(tenderValue)) / 100)).toFixed(2);
				$("#totalTenderAmount" + index).val(totalTenderAmount);
			} else {
				totalTenderAmount = (Number(workEstimateAmt
						* Number(100 + Number(tenderValue)) / 100)).toFixed(2);
				$("#totalTenderAmount" + index).val(totalTenderAmount);
			}
		} else if (tenderType == "AMT") {
			$("#totalTenderAmount" + index).val(tenderValue);
		} else if (tenderType == "MUL") {
			$("#totalTenderAmount" + index).val(
					(workEstimateAmt * tenderValue).toFixed(2));
		}
	}

}

function formatDate(date) {
	var parts = date.split("-");
	var formattedDate = parts[2] + "/" + parts[1] + "/" + parts[0];
	return formattedDate;
}

function printNoticeInvitingTender(tndAndWorkId) {

	var requestData = {
		'tndAndWorkId' : tndAndWorkId,
	}
	var response = __doAjaxRequest(TENDER_URL + '?printNoticeInvitingTender',
			'POST', requestData, false, 'html');
	$('.content-page').html(response);
}
function preQualificationDocument(tndAndWorkId) {

	var requestData = {
		'tndAndWorkId' : tndAndWorkId,
	}
	var response = __doAjaxRequest(TENDER_URL + '?preQualificationDocument',
			'POST', requestData, false, 'html');
	$('.content-page').html(response);
}

/*
 * function printFormAandB(workId ,flagForms){
 * 
 * var requestData = { 'workId' : workId, 'flagForms' : flagForms, } var
 * response = __doAjaxRequest(TENDER_URL + '?printFormAandB', 'POST',
 * requestData, false, 'html'); $('.content-page').html(response); }
 */

function filterVendorClass(estimatedAmount, index) {

	$('#venderClassId' + index).html('');
	$('#venderClassId' + index).append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown'))).trigger(
			'chosen:updated');

	var requestData = {
		"estimatedAmount" : estimatedAmount
	}
	var vendorClassList = __doAjaxRequest(TENDER_URL + '?filterVendorClass',
			'POST', requestData, false, 'json');
	$.each(vendorClassList, function(i, value) {
		$('#venderClassId' + index).append(
				$("<option></option>").attr("value", value.lookUpId).attr(
						"code", value.lookUpCode).text(value.descLangFirst));
	});
	$('#venderClassId' + index).trigger('chosen:updated');

	/*
	 * var rateMasterModel = __doAjaxRequest(TENDER_URL + '?getEmdAmount',
	 * 'POST', requestData, false, 'json'); if (rateMasterModel != null &&
	 * rateMasterModel.emdValue!="") { $('#tenderSecAmt' +
	 * index).val((rateMasterModel.emdValue).toFixed(2)); $('#tenderSecAmt' +
	 * index).prop("readOnly", true); } else { var errorList = [];
	 * errorList.push('wms.tender.emd.vldn'); displayErrorsOnPage(errorList); }
	 */

	var urlTndFees = "TenderInitiation.html?getTndFees";
	var onSuccessCallback = function(responsetndFees) {

		if (responsetndFees != null && responsetndFees.flatRate != ""
				&& responsetndFees.flatRate != "0") {
			$('#tenderFeeAmt' + index).val(
					(responsetndFees.flatRate).toFixed(2));
			$('#tenderFeeAmt' + index).prop("readOnly", true);
		} else {
			// defect #78115
			if (responsetndFees != null && responsetndFees.flatRate == "0") {
				$('#tenderFeeAmt' + index).val("0.00");
				$('#tenderFeeAmt' + index).prop("readOnly", true);
			} else {
				var errorList = [];
				errorList.push('wms.tender.fees.vldn');
				displayErrorsOnPage(errorList);
			}

		}
	};

	var onErrorCallback = function(responsetndFees) {
		debugger;
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = '';
		var sMsg = '';
		var Proceed = getLocalMessage("works.management.proceed");
		sMsg = getLocalMessage("wms.rulesheet.notDefined.tenderFee.org");
		sMsg1 = getLocalMessage("work.estimate.approval.contact.administration");
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center red padding-12" style="margin-right:10px; margin-left:10px;">'
				+ sMsg
				+ '</p>'
				+ '<p class="text-center red padding-12 padding-top-5" style="margin-right:10px; margin-left:10px;"  >'
				+ sMsg1 + '</p>' + '</div>';

		message += '<div class="text-center padding-bottom-10 padding-top-10">'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed + '\'  id="Proceed" ' + 'onclick="redirectHome();"/>'
				+ '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		return false;
	};

	var responsetndFees = __doAjaxRequestWithCallback(urlTndFees, 'POST',
			requestData, false, 'json', onSuccessCallback, onErrorCallback);

	if ($('#tenderFeeAmt' + index).val() != null
			&& $('#tenderFeeAmt' + index).val() != '') {
		debugger;
		var url = "TenderInitiation.html?getEmdAmount";
		var onSuccessCallback = function(response) {

			if (response != null && response.emdValue != "") {
				$('#tenderSecAmt' + index).val((response.emdValue).toFixed(2));
				$('#tenderSecAmt' + index).prop("readOnly", true);
			} else {
				var errorList = [];
				errorList.push('wms.tender.emd.vldn');
				displayErrorsOnPage(errorList);
			}
		};

		var onErrorCallback = function(response) {
			debugger;
			var errMsgDiv = '.msg-dialog-box';
			var message = '';
			var cls = '';
			var sMsg = '';
			var Proceed = getLocalMessage("works.management.proceed");
			sMsg = getLocalMessage("wms.rulesheet.notDefined.org");
			sMsg1 = getLocalMessage("work.estimate.approval.contact.administration");
			message += '<div class="text-center padding-top-25">'
					+ '<p class="text-center red padding-12" style="margin-right:10px; margin-left:10px;">'
					+ sMsg
					+ '</p>'
					+ '<p class="text-center red padding-12 padding-top-5" style="margin-right:10px; margin-left:10px;"  >'
					+ sMsg1 + '</p>' + '</div>';

			message += '<div class="text-center padding-bottom-10 padding-top-10">'
					+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
					+ Proceed
					+ '\'  id="Proceed" '
					+ 'onclick="redirectHome();"/>' + '</div>';

			$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			$('#btnNo').focus();
			showModalBoxWithoutClose(errMsgDiv);
			return false;
		};
		var response = __doAjaxRequestWithCallback(url, 'POST', requestData,
				false, 'json', onSuccessCallback, onErrorCallback);
	}

}

function redirectHome() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'TenderInitiation.html');
	$("#postMethodForm").submit();
}

function printTenderFormReport(workId, flagForms) {

	var requestData = {
		'workId' : workId,
		'flagForms' : flagForms,
	}
	var response = __doAjaxRequest(TENDER_URL + '?printFormAandB', 'POST',
			requestData, false, 'html');
	$('.content-page').html(response);
}

function showApplicableChecklist() {
	debugger;
	var theForm = '#TenderInitiation';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = TENDER_URL + '?getApplicableCheckListAndCharges';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	if (returnData != null) {
		$('.content-page').html(returnData);
		getProjects();
		if ($('#project').val() != '') {
			$("#projId option[value='" + $('#project').val() + "']").prop(
					'selected', 'selected');
			$('#projId').trigger("chosen:updated");
		}
	}
}

function closeSend() {
	$.fancybox.close();
}
function showBIDFormForCreate(obj) {
	debugger;
	var divName = '.content-page';
	
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	
	var ajaxResponse = doAjaxLoading(TENDER_URL + '?openBIDAddForm',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function showBIDForm() {
		
	$.fancybox.close();
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(TENDER_URL + '?openBIDAddForm', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function showaddBIDDetailsForm(obj) {
	
	var errorList = [];

	var vendorId = $("#vendorId").val();
	var bidIdDesc = $("#bidIdDesc").val();
	var overAllTechScore = $("#overAllTechScore").val();
	var overAllComScore = $("#overAllComScore").val();
	var AccRejLost = $("#AccRejLost").val();
	var L1QCBS = $("#L1QCBS").val();

	if (vendorId == "" || bidIdDesc == "" || overAllTechScore == ""
			|| overAllComScore == "" || AccRejLost == "" || L1QCBS == "") {
		errorList.push(getLocalMessage("wms.allField.required"));
	}
	if (errorList.length != 0) {
		displayErrorsOnPage(errorList);

	} else {
		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var ajaxResponse = doAjaxLoading(
				"TenderInitiation.html?openAddBIDDetailsForm", requestData,
				'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function showBIDDetailsViewModeForm(){
	var divName = '.content-page';

	var ajaxResponse = doAjaxLoading(
			"TenderInitiation.html?openAddBIDDetailsForm", {}, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
	$("#BidMasterDetail :input").prop("disabled", true);
	$("#button-Cancel").prop('disabled', false);
}

function saveBIDDetails(obj) {
	
	var divName = '.content-page';

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	var url = "TenderInitiation.html?saveBIDDetails";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();

	showConfirmBoxforBID("BID Details saved successfully");

}

function showConfirmBoxforBID(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	var C = "C";
	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showBIDForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function addBidMasterRow() {
	
	var errorList = [];
	// errorList = validateEntryDetails();
	if (errorList.length == 0) {
		addTableRow('bidMaster');
	} else {
		$('#bidMaster').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function addTechnicalEntryData(size) {
	debugger;
	var errorList = [];
	// errorList = validateEntryDetails();
	if (errorList.length == 0) {
		// addTableRow('technicalDetail');

		var content = $('#technicalDetail tr').last().clone();
		$('#technicalDetail tr').last().after(content);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		reOrderTechnicalTableSeuence(size);

	} else {
		$('#technicalDetail').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function addCommercialEntryData(size) {

	var errorList = [];
	// errorList = validateEntryDetails();
	if (errorList.length == 0) {
		// addTableRow('commercialDetail');
		var content = $('#commercialDetail tr').last().clone();
		$('#commercialDetail tr').last().after(content);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		reOrderCommercialTableSeuence(size);

	} else {
		$('#commercialDetail').DataTable();
		displayErrorsOnPage(errorList);
	}
}
function reOrderCommercialTableSeuence(size) {
	debugger;
	$(".commercialEntry")
			.each(
					function(i) {
						debugger;
						if (size == true) {
							$(this).find("select:eq(0)").attr("id",
									"commParamDescId" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].paramDescId").attr(
									"onchange",
									"getLongValueforCommParam(" + i + ")");
							$(this).find("input:text:eq(0)").attr("id",
									"sequence" + (i)).val(i + 1);

							$(this).find("input:text:eq(1)").attr("id",
									"baseRate" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].baseRate");

							$(this).find("input:text:eq(2)").attr("id",
									"quotedPrice" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].quotedPrice").attr(
									"onkeypress",
									"return hasAmount(event, this, 8, 2)");

							$(this)
									.find("input:text:eq(3)")
									.attr("id", "mark" + (i))
									.attr(
											"name",
											"bidMasterDto.commercialBIDDetailDtos["
													+ (i) + "].mark")
									.attr("onchange",
											"getAmountFormatInDynamic((this),'yeBugAmount')")
									.attr("onkeypress",
											"return hasAmount(event, this, 8, 2)");

							$(this).find("input:text:eq(4)").attr("id",
									"obtained" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].obtained").attr(
									"onkeypress",
									"return hasAmount(event, this, 8, 2)");
							$(this)
									.find("input:text:eq(5)")
									.attr("id", "weightage" + (i))
									.attr(
											"name",
											"bidMasterDto.commercialBIDDetailDtos["
													+ (i) + "].weightage")
									.attr("onchange",
											"getAmountFormatInDynamic((this),'yeBugAmount')")
									.attr("onkeypress",
											"return hasAmount(event, this, 8, 2)");

							$(this).find("input:text:eq(6)").attr("id",
									"finalMark" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].finalMark").attr(
									"onkeypress",
									"return hasAmount(event, this, 8, 2)");
						} else {
							$(this).find("input:text:eq(0)").attr("id",
									"sequence" + (i)).val(i + 1);

							$(this).find("input:text:eq(1)").attr("id",
									"commParamDescId" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].paramDescId");
							$(this).find("input:text:eq(2)").attr("id",
									"baseRate" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].baseRate");

							$(this).find("input:text:eq(3)").attr("id",
									"quotedPrice" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].quotedPrice").attr(
									"onkeypress",
									"return hasAmount(event, this, 8, 2)");

							$(this)
									.find("input:text:eq(4)")
									.attr("id", "mark" + (i))
									.attr(
											"name",
											"bidMasterDto.commercialBIDDetailDtos["
													+ (i) + "].mark")
									.attr("onchange",
											"getAmountFormatInDynamic((this),'yeBugAmount')")
									.attr("onkeypress",
											"return hasAmount(event, this, 8, 2)");

							$(this).find("input:text:eq(5)").attr("id",
									"obtained" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].obtained").attr(
									"onkeypress",
									"return hasAmount(event, this, 8, 2)");
							$(this)
									.find("input:text:eq(6)")
									.attr("id", "weightage" + (i))
									.attr(
											"name",
											"bidMasterDto.commercialBIDDetailDtos["
													+ (i) + "].weightage")
									.attr("onchange",
											"getAmountFormatInDynamic((this),'yeBugAmount')")
									.attr("onkeypress",
											"return hasAmount(event, this, 8, 2)");

							$(this).find("input:text:eq(7)").attr("id",
									"finalMark" + (i)).attr(
									"name",
									"bidMasterDto.commercialBIDDetailDtos["
											+ (i) + "].finalMark").attr(
									"onkeypress",
									"return hasAmount(event, this, 8, 2)");

						}

					});

}

function reOrderTechnicalTableSeuence(size) {
	debugger;
	$(".techParamDetailRow").each(
			function(i) {
				debugger;
				if (size == true) {
					$(this).find("select:eq(0)")
							.attr("id", "paramDescId" + (i)).attr(
									"name",
									"bidMasterDto.technicalBIDDetailDtos["
											+ (i) + "].paramDescId").attr(
									"onchange",
									"getLongValueForTechParam(" + i + ")");

					$(this).find("input:text:eq(0)").attr("id",
							"sequence" + (i)).val(i + 1);

					$(this).find("input:text:eq(1)").attr("id", "mark" + (i))
							.attr(
									"name",
									"bidMasterDto.technicalBIDDetailDtos["
											+ (i) + "].mark");

					$(this).find("input:text:eq(2)").attr("id",
							"obtained" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].obtained").attr("onkeypress",
							"return hasAmount(event, this, 8, 2)");

					$(this).find("input:text:eq(3)").attr("id",
							"weightage" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].weightage").attr("onchange",
							"getAmountFormatInDynamic((this),'yeBugAmount')")
							.attr("onkeypress",
									"return hasAmount(event, this, 8, 2)");

					$(this).find("input:text:eq(4)").attr("id",
							"finalMark" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].finalMark").attr("onkeypress",
							"return hasAmount(event, this, 8, 2)");
				} else {
					$(this).find("input:text:eq(0)").attr("id",
							"sequence" + (i)).val(i + 1);

					$(this).find("input:text:eq(1)").attr("id",
							"paramDescId" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].paramDescId");

					$(this).find("input:text:eq(2)").attr("id", "mark" + (i))
							.attr(
									"name",
									"bidMasterDto.technicalBIDDetailDtos["
											+ (i) + "].mark");

					$(this).find("input:text:eq(3)").attr("id",
							"obtained" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].obtained").attr("onkeypress",
							"return hasAmount(event, this, 8, 2)");

					$(this).find("input:text:eq(4)").attr("id",
							"weightage" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].weightage").attr("onchange",
							"getAmountFormatInDynamic((this),'yeBugAmount')")
							.attr("onkeypress",
									"return hasAmount(event, this, 8, 2)");

					$(this).find("input:text:eq(5)").attr("id",
							"finalMark" + (i)).attr(
							"name",
							"bidMasterDto.technicalBIDDetailDtos[" + (i)
									+ "].finalMark").attr("onkeypress",
							"return hasAmount(event, this, 8, 2)");

				}

			});

}

function showBIDDetailsForm() {

	$.fancybox.close();

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(TENDER_URL + '?getDetailsOfBID', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}
function editBIDForm(bidId, mode) {

	$.fancybox.close();
	var requestData = {
		'bidId' : bidId,
		'mode' : mode
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(TENDER_URL + '?editBIDForm', requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	if (mode == 'V') {
		$("#BIDMaster :input").prop("disabled", true);
		$("#button-Cancel").prop('disabled', false);
		$("#bidDetail").prop('disabled', false);
	}

}

function deleteEntry(obj, ids) {

	deleteTableRow('commercialDetail', obj, ids);
	$('#commercialDetail').DataTable().destroy();

	triggerTable();
}

function deleteTechEntry(obj, ids) {

	deleteTableRow('technicalDetail', obj, ids);
	$('#technicalDetail').DataTable().destroy();

	triggerTable();
}

function getLongValueForTechParam(index) {
	debugger;
	var techParamName = $("#paramDescId" + index).val();
	var requestData = {
		'paramName' : techParamName
	}
	var techParamValue = __doAjaxRequest(TENDER_URL
			+ '?getLongValueForTechParam', 'post', requestData, false, 'json');

	$("#mark" + index).val(techParamValue);
	// $("#mark" + index).prop("disabled", true);
	$("#mark" + index).prop("readonly", true);

}

function getLongValueforCommParam(index) {
	debugger;
	var commParamName = $("#commParamDescId" + index).val();
	var requestData = {
		'paramName' : commParamName
	}
	var commParamValue = __doAjaxRequest(TENDER_URL
			+ '?getLongValueforCommParam', 'post', requestData, false, 'json');

	$("#baseRate" + index).val(commParamValue);
	$("#baseRate" + index).prop("readonly", true);
}

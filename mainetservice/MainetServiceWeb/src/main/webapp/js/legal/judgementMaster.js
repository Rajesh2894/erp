var removeIds = [];

$(document)
		.ready(
				function() {
					$("#judgementTable").dataTable(
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
					//Defect #35436
					$("#cseDate").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
					});
					
					var minYear = $('#caseDate').val();
					$('.datepicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : minYear,					
					});
						
			
					var dateFields = $('.datepicker');
					dateFields.each(function () {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					$(".judgementDate").bind("keyup change", function(e) {
						if (e.keyCode != 8) {
							if ($(this).val().length == 2) {
								$(this).val($(this).val() + "/");
							} else if ($(this).val().length == 5) {
								$(this).val($(this).val() + "/");
							}
						}
					});

					$('#searchJudgementData')
							.click(
									function() {
										var errorList = [];
										var cseSuitNo = $('#cseSuitNo').val();
										var cseDeptid = $('#cseDeptid').val();
										var cseDate = $('#cseDate').val();

										if (cseDeptid != 0 || cseDeptid != ''
												|| cseSuitNo != ''
												|| cseDate != '') {
											var requestData = '&cseSuitNo='
													+ cseSuitNo + '&cseDeptid='
													+ cseDeptid + '&cseDate='
													+ cseDate;
											var table = $('#judgementTable')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();

											var ajaxResponse = __doAjaxRequest(
													'JudgementMaster.html?searchJudgementDetails',
													'POST', requestData, false,
													'json');
											if (ajaxResponse.length == 0) {
												errorList
														.push(getLocalMessage("lgl.nofoundmsg"));
												displayErrorsOnPage(errorList);
												return false;
											}
											var result = [];
											$
													.each(
															ajaxResponse.caseEntryDtoList,
															function(index) {
																var obj = ajaxResponse.caseEntryDtoList[index];
																let cseId = obj.cseId;
																let crtId = obj.crtId;
																let cseSuitNo = obj.cseSuitNo;
																let cseDeptName = obj.cseDeptName;
																let cseTypeName = obj.cseTypeName;
																let cseName = obj.cseName;
																let cseDateDesc = obj.cseDateDesc;
																result
																		.push([
																				'<div class="text-center">'
																						+ (index + 1)
																						+ '</div>',
																				'<div class="text-center">'
																						+ cseSuitNo
																						+ '</div>',
																				'<div class="text-center">'
																						+ cseDeptName
																						+ '</div>',
																				'<div class="text-center">'
																						+ cseTypeName
																						+ '</div>',
																				'<div class="text-center">'
																						+ cseName
																						+ '</div>',
																				'<div class="text-center">'
																						+ cseDateDesc
																						+ '</div>',
																				'<div class="text-center">'
																						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="showGridOption('+cseId+','+crtId+',\'V\' '
																						+ ')" title="View"><i class="fa fa-eye"></i></button>'
																						+ '<button type="button" class="btn btn-danger btn-sm btn-sm"  onclick="showGridOption('+ cseId+ ','+ crtId+ ',\'E\' '
																						+ ')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
																						+ '</div>' ]);
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage("lgl.sel.search"));
											displayErrorsOnPage(errorList);
										}
									});
				});

var formDivName = '.content-page';
function getJudgementForm() {

	var requestData = {};
	var url = "JudgementMaster.html?getForm";
	var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false);
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function showGridOption(cseId, crtId, action) {
	
	var actionData;
	var divName = formDivName;
	var requestData = '&cseId=' + cseId + '&crtId=' + crtId;
	if (action == "E") {
		actionData = 'editJudgementDetails';
		var ajaxResponse = doAjaxLoading('JudgementMaster.html?' + actionData,
				requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		// $('#createMeetingBtId').show();

		prepareTags();
	}

	if (action == "V") {
		actionData = 'viewJudgementDetails';
		var ajaxResponse = doAjaxLoading('JudgementMaster.html?' + actionData,
				requestData, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function fileCountUpload(element) {

	var errorList = [];
	errorList = validateJudgementDetails(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var row = $("#judgementDetailsListTB tbody .appendableClass").length;
		$("#length").val(row);
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('JudgementMaster.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		var divName = formDivName;
		$(divName).html(response);
	} else {
		displayErrorsOnPage(errorList);
	}
}

// JUDGEMENT details form page code here
$('#judgementDetailsListTB').on("click", '.addReButton', function(e) {
	if ($.fn.DataTable.isDataTable('#judgementDetailsListTB')) {
		$('#judgementDetailsListTB').DataTable().destroy();
	}
	var errorList = [];

	errorList = validateJudgementDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		e.preventDefault();
		$(".datepicker").datepicker("destroy");
		
		 /*var clickedRow = $('#judgementDetailsListTB tr').length-2;
		 var cseDeptName= $('#cseDeptName'+clickedRow).val(); 
		 var cseCourtDesc= $('#cseCourtDesc'+clickedRow).val(); 
		 var cseDateDesc= $('#cseDateDesc'+clickedRow).val(); 
		 var cseBenchName= $('#cseBenchName'+clickedRow).val();*/
		 

		var content = $('#judgementDetailsListTB tr').last().clone();
		$('#judgementDetailsListTB tr').last().after(content);

		content.find("input:text:eq(5)").attr("id", "judDate").val("");
		content.find("textarea:eq(0)").attr("id", "judSummaryDetail").val("");

		

		reOrderTableIdRESequence();
		/*$('#cseDeptName'+(clickedRow+1)).val(cseDeptName);
		$('#cseCourtDesc'+(clickedRow+1)).val(itemNo);
		$('#cseDateDesc'+(clickedRow+1)).val(cseDateDesc);
		$('#cseBenchName'+(clickedRow+1)).val(cseBenchName);*/
		
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			
		});
		
	
	}
});

function reOrderTableIdRESequence() {

	$('.appendableClass')
			.each(
					function(i) {

						$(this).find("input:text:eq(0)").attr("id",
								"sequence" + (i));
						$("#sequence" + i).val(i + 1);

						$(this).find("input:hidden:eq(0)").attr("id",
								"judId" + i);

						$(this).find("input:text:eq(1)").attr("id",
								"cseDeptName" + i);
						$(this).find("input:text:eq(2)").attr("id",
								"cseCourtDesc" + i);
						$(this).find("input:text:eq(3)").attr("id",
								"cseDateDesc" + i);
						$(this).find("input:text:eq(4)").attr("id",
								"cseBenchName" + i);
						$(this).find("input:text:eq(5)").attr("id",
								"judDate" + (i));
						$(this).find("textarea:eq(0)").attr("id",
								"judSummaryDetail" + i);

						$(this).find("input:button:eq(0)").attr("id",
								"delButton" + i);

						$(this).parents('tr').find('.delButton').attr("id",
								"delButton" + i);

						$(this).find("input:hidden:eq(0)").attr("name",
								"judgementMasterDtoList[" + (i) + "].judId");
						$(this).find("input:text:eq(1)")
								.attr(
										"name",
										"judgementMasterDtoList[" + i
												+ "].cseDeptName");
						$(this).find("input:text:eq(2)").attr(
								"name",
								"judgementMasterDtoList[" + i
										+ "].cseCourtDesc");
						$(this).find("input:text:eq(3)")
								.attr(
										"name",
										"judgementMasterDtoList[" + i
												+ "].cseDateDesc");
						$(this).find("input:text:eq(4)").attr(
								"name",
								"judgementMasterDtoList[" + i
										+ "].cseBenchName");
						$(this).find("input:text:eq(5)").attr("name",
								"judgementMasterDtoList[" + i + "].judDate");
						$(this).find("textarea:eq(0)").attr(
								"name",
								"judgementMasterDtoList[" + i
										+ "].judSummaryDetail");

					});
	// triggerDatatable();
}

// validation method for momForm
function validateJudgementDetails(errorList) {

	$("#judgementDetailsListTB tbody tr")
			.each(
					function(i) {
						let position = (i + 1);
						let judDate = $('#judDate' + i).val();
						let judSummaryDetail = $('#judSummaryDetail' + i).val();

						if (judDate == "" || judDate == undefined) {
							errorList
									.push(getLocalMessage("legal.case.judgement.validation.select.date ")
											+ position);
						}
						if (judSummaryDetail == ""
								|| judSummaryDetail == undefined) {
							errorList
									.push(getLocalMessage("legal.case.judgement.validation.details ")
											+ position);
						}
						// document check
						/*
						 * let document = $('#uploadedDocumentPath'+i).val(); if
						 * (document == "" || document == undefined) {
						 * errorList.push(getLocalMessage("document cant be
						 * empty")+ position); }
						 */

					});
	return errorList;
}
function triggerDatatable() {
	$("#appendableClass").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true,
		"scrollCollapse" : true,
		"bSort" : false
	}).fnPageChange('last');
}




$("#judgementDetailsListTB").on("click", '.delButton', function(e) {

	var row = $("#judgementDetailsListTB tbody .appendableClass").length;
	if (row != 1) {
		$(this).parent().parent().remove();
		let removeId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (removeId != '') {
			removeIds.push(removeId);
			$('#removedIds').val(removeIds);
		}
		reOrderTableIdRESequence();
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("legal.case.judgement.validation.first.row.empty"));
		displayErrorsOnPage(errorList);
	}
	e.preventDefault();
});

function saveJudgementForm(obj) {
	var errorList = [];
	errorList = validateJudgementDetails(errorList);

	if (errorList.length == 0) {
		var url = "JudgementMaster.html";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function doFileDeletion(obj, id) {

	requestData = {
		"id" : id
	};

	var row = $("#judgementDetailsListTB tbody .appendableClass").length;
	if (row != 1) {
		var response = __doAjaxRequest('JudgementMaster.html?doEntryDeletion',
				'POST', requestData, false, 'html');
	}
}

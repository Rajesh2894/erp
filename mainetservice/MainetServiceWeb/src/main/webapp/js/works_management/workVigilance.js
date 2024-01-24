/**
 * @author vishwajeet.kumar
 * @since 23 May 2018
 */
var WorkVigilanceURL = "WorkVigilance.html";
var fileArray = [];
var receptionDetById = [];

$(document)
		.ready(
				function() {
					;


					/**
					 * This method is used to Search All work Vigilance
					 * workVigilanceSummary.jsp START METHOD
					 */
					$("#searchWorkVigilance")
							.click(
									function() {
										var errorList = [];
										var referenceType = $("#referenceType")
												.find("option:selected").attr(
														'value');
										var vigilenceReferenceNo = $(
												'#vigilenceReferenceNo').val();
										var memoDate = $('#memoDate').val();
										var inspectionDate = $(
												'#inspectionDate').val();

										if (referenceType != ''
												|| vigilenceReferenceNo != ''
												|| memoDate != ''
												|| inspectionDate != '') {

											var requestData = 'referenceType='
													+ referenceType
													+ '&vigilenceReferenceNo='
													+ vigilenceReferenceNo
													+ '&memoDate=' + memoDate
													+ '&inspectionDate='
													+ inspectionDate;
											var table = $('#datatables')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest(
													WorkVigilanceURL
															+ '?filterWorkVigilanceListData',
													'POST', requestData, false,
													'json');

											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {

																var obj = ajaxResponse[index];
																result
																		.push([
																				index + 1,
																				obj.referenceType,
																				obj.referenceNumber,
																				obj.memoDateDesc,
																				obj.inspectDateDesc,
																				obj.status,
																				'<td >'
																						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 "   onclick="viewWorkVigilance(\''
																						+ obj.vigilanceId
																						+ '\')" title="View"><i class="fa fa-eye"></i></button>'
																						+ '<button type="button"  class="btn btn-primary btn-sm margin-right-10 "    onclick="viewResponseAndAction(\''
																						+ obj.vigilanceId
																						+ '\')" title="View Response & Action"><i class="fa fa-gears"></i></button>'
																						+ '<button type="button" class="btn btn-success btn-sm margin-right-10"    onclick="editWorkVigilance(\''
																						+ obj.vigilanceId
																						+ '\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
																						+ '</td>' ]);
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage('work.management.valid.select.any.field'));
											displayErrorsOnPage(errorList);
										}
									});

					$("#attachDoc").on("click", '.delButton', function(e) {
						var countRows = -1;
						$('.appendableClass').each(function(i) {
							if ($(this).closest('tr').is(':visible')) {
								countRows = countRows + 1;
							}
						});
						row = countRows;
						if (row != 0) {
							$(this).parent().parent().remove();
							row--;

						}
						e.preventDefault();
					});

					$("#deleteDoc").on(
							"click",
							'#deleteFile',
							function(e) {
								var errorList = [];
								if (errorList.length > 0) {
									$("#errorDiv").show();
									displayErrorsOnPage(errorList);
									return false;
								} else {
									$(this).parent().parent().remove();
									var fileId = $(this).parent().parent()
											.find('input[type=hidden]:first')
											.attr('value');
									if (fileId != '') {
										fileArray.push(fileId);
									}
									$('#removeFileById').val(fileArray);
								}
							});

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

					$('.datepicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						yearRange : "-100:-0"
					});
					$(".reset").click(function() {
						$("#postMethodForm").prop('action', '');
						$("#postMethodForm").prop('action', WorkVigilanceURL);
						$("#postMethodForm").submit();

					});
					var inspRequired = $("#inspRequired").val();
					if(inspRequired=='N'){
						  $("#inspectiondate").prop("disabled", true);
						  $("#memoType").attr("disabled", true);
						  $("#inspectiondate").val('');
						  $("#memoType").val('');
					  }else{
						  $("#inspectiondate").prop("disabled", false);
						  $("#memoType").attr("disabled", false);
					  }

				});

function  searchDetails(){
var errorList=[];
var projectId = $('#projectId').val();
var workId = $('#workId').val();

if(projectId == '0' || projectId == ''){
	errorList.push(getLocalMessage('work.Def.valid.select.projName'));
    displayErrorsOnPage(errorList);
	return false;
}
else if (projectId != '0' && projectId != '' ){
	if(workId == '' || workId == '0'){
		errorList.push(getLocalMessage('work.estimate.select.work.name'));
		displayErrorsOnPage(errorList);
		return false;
	}
}
var requestData = '&projectId='+projectId+'&workId='+workId;
var divName = '.content-page';
var ajaxResponse = __doAjaxRequest(
		'WorkVigilance.html?searchDetails','POST', requestData, false,'html',
		divName);

$('.content').removeClass('ajaxloader');
$(divName).html(ajaxResponse);
prepareTags();
getprojectName();
$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated");
}

function openAddWorkVigilence(formUrl, requestData) {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + requestData, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated")

}

function getprojectName() {
	var divName = '.content-page';
	var requestData = {
		"projId" : $("#projectId").val()

	};
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	var ajaxResponse = doAjaxLoading('WorkVigilance.html?worksName',
			requestData, 'html');
	if(ajaxResponse==null || ajaxResponse==""){
		displayErrorsOnPage("Work does not exist Project Corresponding");
	}
	var prePopulate = JSON.parse(ajaxResponse);
	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");

	}

function getWorkRACcode() {

	var requestData = {
		"workId" : $("#workId").val(),
		"projId" : $("#projectId").val()
	};
	$('#racodeCode').html('');
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WorkVigilance.html?racodeMb',
			requestData, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getprojectName();
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated");
}

function getWorkMb() {
	var requestData = {
		"racode" : $("#racodeCode").val()
	};
	var ajaxResponse = __doAjaxRequest(WorkVigilanceURL + '?workMb', 'POST',
			requestData, false, 'html');

	$("#EstimateTagDiv").html(ajaxResponse);
	prepareTags();
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated")
}

function viewMbAbstractSheet(workMbId) {
	
	var divName = '.content-page';
	var requestData = '&workMbId=' + workMbId;

	var ajaxResponse = doAjaxLoading(
			'WorkVigilance.html?getWorkMBAbstractSheet', requestData, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getVIewMB(workMbId, mode) {
	
	var divName = '.content-page';
	var url = "WorkVigilance.html?editMb";
	var actionParam = $("form").serialize() + '&workMbId=' + workMbId
			+ '&mode=' + mode;
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated")
	prepareTags();

}

function saveVigilance(vigilanceData) {
	var errorList = [];

	var projectId = $("#projectId").val();
	var workId = $("#workId").val();
	//#39015
	//var racodeCode = $("#racodeCode").val();
	var inspRequired = $("#inspRequired").val();
	var inspectiondate = $("#inspectiondate").val();
	var memoType = $("#memoType").val();
	var memoDescription = $("#memoDescription").val();

	if (projectId == null || projectId == '') {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
	}
	if (workId == null || workId == '') {
		errorList.push(getLocalMessage('work.estimate.select.work.name'));
	}
	//#39015
	/*if (racodeCode == null || racodeCode == '') {
		errorList.push(getLocalMessage('work.estimate.select.raCode'));
	}*/
	if (inspRequired == null || inspRequired == '') {
		errorList.push(getLocalMessage('work.vigilance.inspection.required'));
	}
	//Defect #92671
	if(inspRequired == "Y"){
	if (inspectiondate == null || inspectiondate == '') {
		errorList
				.push(getLocalMessage('work.vigilance.please.select.inspection.date'));
	}
	if (memoType == null || memoType == '') {
		errorList
				.push(getLocalMessage('work.vigilance.please.select.memo.type'));
	}
	}	
	if (memoDescription == null || memoDescription == '') {
		errorList.push(getLocalMessage('work.vigilance.please.enter.comments'));
	}

	if (errorList.length > 0) {

		displayErrorsOnPage(errorList);
	}

	else {
		return saveOrUpdateForm(vigilanceData,
				getLocalMessage('work.Vigilance.creation.successfull'),
				'WorkVigilance.html', 'saveform');

	}
}

function viewWorkVigilance(vigilanceId, mode) {
	
	var divName = '.content-page';
	var requestData = {
		'vigilanceId' : vigilanceId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(WorkVigilanceURL + '?viewWorkVigilance',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getprojectName();
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated");

}

function getActionForDefination(workId, mode) {

	var divName = '.content-page';
	var url = "WorkEstimate.html?editEstimation";
	var actionParam = {
		'workId' : workId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function editWorkVigilance(vigilanceId) {
	;
	var divName = '.content-page';
	var requestData = {
		'vigilanceId' : vigilanceId,
	}
	var ajaxResponse = __doAjaxRequest(WorkVigilanceURL + '?editWorkVigilance',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getprojectName();
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated");
	getWorkMb();

}

function viewResponseAndAction(vigilanceId) {
	var divName = '.content-page';
	var requestData = {
		'vigilanceId' : vigilanceId,
	}
	var ajaxResponse = __doAjaxRequest(WorkVigilanceURL
			+ '?viewResponseAndAction', 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveResponseMemoVigilance(responsMemoData) {
	var errorList = [];

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		var formName = findClosestElementId(responsMemoData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(WorkVigilanceURL
				+ '?saveResponseAndAction', 'POST', requestData, false);
		showConfirmBox();
	}
}

function fileCountUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(WorkVigilanceURL + '?fileCountUpload',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
}

function getDesignationById(countRow) {
	var employeeDesignation = $("#empId" + countRow).find("option:selected")
			.attr('code');

	if (employeeDesignation != "" && employeeDesignation != undefined) {
		$("#design" + countRow).val(employeeDesignation);
	}
}

function validateReferenceDetails(errorList) {
	$(".vigilanceAppendableClass")
			.each(
					function(i) {
						var empId = $("#empId" + i).val();

						var constant = i + 1;
						if (empId == "") {
							errorList
									.push(getLocalMessage("work.vigilance.please.select.employee.name")
											+ " " + constant);
						}
					});
	return errorList;
}

$("#receptionDetailsTable").on("click", '.addReferenceDetails', function(e) {
	var count = $('#receptionDetailsTable tr').length - 1;
	var errorList = [];
	errorList = validateReferenceDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#receptionDetailsTable tr').last().clone();
		$('#receptionDetailsTable tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("input:text").val('');
		content.find("select").attr("selected", "selected").val('');
		reOrderVigilanceReferenceDetails();
	}
});

function reOrderVigilanceReferenceDetails() {
	$('.vigilanceAppendableClass').each(
			function(i) {
				$(this).find("hidden:eq(0)").attr("id", "vlDetId" + (i));
				$(this).find("select:eq(0)").attr("id", "empId" + (i)).attr(
						"onchange", "getDesignationById(" + i + ");");
				$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
				$(this).find("input:text:eq(1)").attr("id", "design" + (i));

				$(this).find("hidden:eq(0)").attr("name",
						"vigilanceDto.vigilanceDetails[" + (i) + "].vlDetId");
				$(this).find("select:eq(0)").attr("name",
						"vigilanceDto.vigilanceDetails[" + (i) + "].empId");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"vigilanceDto.vigilanceDetails[" + (i)
								+ "].empDesignation");
				$("#sNo" + i).val(i + 1);
			});
}

$('#receptionDetailsTable').on(
		"click",
		'.deleteVigilanceRefDetails',
		function(e) {
			var errorList = [];
			var count = 0;
			$('.vigilanceAppendableClass').each(function(i) {
				count += 1;
			});
			var rowCount = $('#receptionDetailsTable tbody tr').length;
			if (rowCount <= 1) {
				return false;
				errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
			}
			if (errorList.length > 0) {
				$("#errorDiv").show();
				displayErrorsOnPage(errorList);
				return false;
			} else {
				$(this).parent().parent().remove();
				var vigilance = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');
				if (vigilance != '') {
					receptionDetById.push(vigilance);
				}
				$('#referenceDetailsById').val(receptionDetById);
				reOrderVigilanceReferenceDetails();
			}
		});

function resetWorkVigilence() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', WorkVigilanceURL);
	$("#postMethodForm").submit();
}

function backWorkVigilance() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', WorkVigilanceURL);
	$("#postMethodForm").submit();
}

function backToApprvForm() {
	
	var divName = '.content-page';
	var url = "WorkVigilance.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getprojectName();
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated");
	getWorkMb();
	

}

function backToApprvForm(){
	
	var divName = '.content-page';
	var url = "WorkVigilance.html?showApprovalCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getprojectName();
	$("#workId").val($("#workIdAdd").val()).trigger("chosen:updated");
	getWorkMb();
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '
			+ getLocalMessage('scheme.master.creation.success') + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

//Defect #92671
function getInspectionStatus() {
	debugger;
  var  inspRequired = $("#inspRequired").val();
  if(inspRequired=='N'){
	  $("#inspectiondate").prop("disabled", true);
	  $("#memoType").attr("disabled", true);
	  $("#inspectiondate").val('');
	  $("#memoType").val('');
  }else{
	  $("#inspectiondate").prop("disabled", false);
	  $("#memoType").attr("disabled", false);
  }
  
}
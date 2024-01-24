$(document).ready(function() {
	
	$("#assDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate : 0
	});
	$("#cbboAssessEntSummTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	$("#cbboAssessEntTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

	$('.chosen-select-no-results').chosen();

});

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CBBOAssessmentEntry.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function addRow(obj) {
	
	var errorList = [];
	errorList = validateFormDetails(errorList);
	if (errorList.length == 0) {
		var content = $('#cbboAssessEntTable tr').last().clone();
		$('#cbboAssessEntTable tr').last().after(content);
		content.find("select").val('');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		reordeCbboDetailTable();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteRow(obj) {
	var errorList = [];
	var rowLength = $('#cbboAssessEntTable tr').length;
	if ($("#cbboAssessEntTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCbboDetailTable();
		rowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCbboDetailTable() {
	$("#cbboAssessEntTable tbody tr").each(
			function(i) {
				$(this).find("input:text:eq(0)").attr("id", "sNo" + i);
				$(this).find('select:eq(0)').attr('id', 'cbboId' + i);

				$(this).find('select:eq(0)').attr(
						"name",
						"assementMasterDto.cbboAssDetailDtoList[" + i
								+ "].cbboId");
				$("#sNo" + i).val(i + 1);
			});
}

function validateFormDetails() {
	

	var errorList = [];

	var rowCount = $('#cbboAssessEntTable tr').length;
	if ($.fn.DataTable.isDataTable('#cbboAssessEntTable')) {
		$('#cbboAssessEntTable').DataTable().destroy();
	}
	if (errorList == 0)
		$("#contactDetails tbody tr").each(
				function(i) {
					var cbboId = $("#cbboId" + i).val();

					if (cbboId == "" || cbboId == undefined || cbboId == "0") {
						errorList
								.push(getLocalMessage("sfac.validation.cbboId")
										+ " " + rowCount);
					}
				});
	return errorList;
}
// Function used to save assessment master data
function generateAssementNo(obj) {
	
	var errorList = [];
	var iaId = $("#iaId").val();
	var finYrId = $("#finYrId").val();
	if (iaId == '0' || iaId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.ianame"));
	}
	if (finYrId == '0' || finYrId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.assYear"));
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "CBBOAssesementEntry.html",
				'saveAssementMasterForm');

	} else {
		displayErrorsOnPage(errorList);
	}
}

// Function used to sent assessment for approval
function sendForApproval(assId, assessmentNo, cbboId) {
	

	var divName = '.content-page';
	var url = "CBBOAssessmentEntry.html?sendForApproval";
	var requestData = {
		'assId' : assId,
		'assessmentNo' : assessmentNo,
		'cbboId' : cbboId
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	showApprovalStatus(ajaxResponse, assId);

}

// function used to show proposal approval status
function showApprovalStatus(ajaxResponse, assId) {
	

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';
	var Proceed = getLocalMessage("council.proceed");
	if (ajaxResponse.checkStausApproval == "Y") {
		sMsg = getLocalMessage("council.proposal.approval.initiated");
	} else if (ajaxResponse.checkStausApproval == "N") {
		sMsg = getLocalMessage("council.proposal.approval.process.not.defined");
	} else if (ajaxResponse.checkStausApproval == "A") {
		sMsg = getLocalMessage("council.proposal.creation");
	} else {
		sMsg = getLocalMessage("council.proposal.approval.initiated.fail");
		sMsg1 = getLocalMessage("council.proposal.approval.contact.administration");
	}

	if (ajaxResponse.checkStausApproval == "Y") {
		message += '<div class="text-center padding-top-25">'
				+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>' + '</div>';

		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed
				+ '\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>'
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
	} else if (ajaxResponse.checkStausApproval == "A") {

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

// Function used to save assessment master Details
function saveAssessmentDetails(obj) {
	
	var errorList = [];
	var cbboId = $("#cbboId").val();
	var finYrId = $("#finYrId").val();
	
	if (cbboId == '0' || cbboId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.cbboId"));
	}
	if (finYrId == '0' || finYrId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.assYear"));
	}

	//errorList = validateFormDetails(errorList);

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Assessment Details Saved Successfully!",
				'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveApprovalData(element) {
	
	var errorList = [];
	if ($("#remark").val() == "") {
		errorList.push(getLocalMessage("sfac.reamrk.validate"));
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				"Assesment Details Saved Successfully!", 'AdminHome.html',
				'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function modifyCase(assId, formUrl, actionParam, mode) {
	
	var divName = formDivName;
	var url = "CBBOAssessmentEntry.html?EDIT";
	data = {
		"assId" : assId,
		"mode" : mode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();
}

function printAssessMentDetails(assId, formUrl, actionParam, mode) {
	var divName = formDivName;
	var url = "CBBOAssessmentEntry.html?printAssessMentDetails";
	data = {
		"assId" : assId,
		"mode" : mode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();

}

function searchForm(obj) {
	var errorList = [];
	var cbboId = $("#cbboId").val();
	// var assStatus = $("#assStatus").find("option:selected").attr('code');
	var assStatus = $("#assStatus").val();
	var assDate = $("#assDate").val();
	var divName = '.content-page';
	if ((cbboId == "0" || cbboId == undefined || cbboId == "")
			&& (assStatus == "0" || assStatus == undefined || assStatus == "")
			&& (assDate == "0" || assDate == undefined)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"cbboId" : cbboId,
			"assStatus" : assStatus,
			"assDate" : assDate
		};
		var ajaxResponse = doAjaxLoading('CBBOAssessmentEntry.html?searchForm',
				requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function calculateScore(ids) {
	var overallScore = $("#overallScore"+ids).val();
	var reg = $("#regiFpoCriteria"+ids).val();
	var addOverallScore = $("#addOverallScore").val();
	var addRegFulfill = $("#addRegFulfill").val();
	var addTotalScore =$("#addTotalScore").val();
	if ((overallScore != undefined && overallScore != "") && (reg !=undefined &&  reg != "")){
	var amt = (parseFloat(overallScore) * parseFloat(reg)) / 100;
	$("#score"+ids).val(amt.toFixed(2));
	 $("#addTotalScore").val(parseFloat(addTotalScore) + parseFloat(amt.toFixed(2)));
	}
	if (overallScore != undefined && overallScore !=""){
		$("#addOverallScore").val(parseFloat(addOverallScore) + parseFloat(overallScore));
	}
	
}

function calculateTotal(ids) {
	var overallScore = $("#overallScore"+ids).val();
	var reg = $("#regiFpoCriteria"+ids).val();
	var addOverallScore = $("#addOverallScore").val();
	var addRegFulfill = $("#addRegFulfill").val();
	var addTotalScore =$("#addTotalScore").val();
	if ((overallScore != undefined && overallScore != "") && (reg !=undefined &&  reg != "")){
	var amt = (parseFloat(overallScore) * parseFloat(reg)) / 100;
	$("#score"+ids).val(amt.toFixed(2));
	 $("#addTotalScore").val(parseFloat(addTotalScore) + parseFloat(amt.toFixed(2)));
	}
	if (reg !=undefined && reg != ""){
		$("#addRegFulfill").val(parseFloat(addRegFulfill) + parseFloat(reg));
	}
}
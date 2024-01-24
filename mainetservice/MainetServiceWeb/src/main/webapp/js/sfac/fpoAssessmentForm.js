/**
 * 
 */
$(document).ready(function() {
	$("#fpoAssessEntTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FPOAssessmentEntry.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}


//Function used to save assessment master Details
function saveAssessmentDetails(obj) {debugger
	
	var errorList = [];
	var fpoId = $("#fpoId").val();
	var finYrId = $("#finYrId").val();
	
	if (fpoId == '0' || fpoId == undefined) {
		errorList.push(getLocalMessage("sfac.fpo.mgmt.validation.fpo"));
	}
	if (finYrId == '0' || finYrId == undefined) {
		errorList.push(getLocalMessage("sfac.validation.assYear"));
	}

	if (errorList.length == 0) {
		var fpoMasName = $("#fpoId").find("option:selected").attr('code');
		$('#fpoName').val(fpoMasName);
		return saveOrUpdateForm(obj, "FPO Assessment Details Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}


function searchForm(obj) {
	var errorList = [];
	var fpoId = $("#fpoId").val();
	var assStatus = $("#assStatus").val();
	
	var divName = '.content-page';
	if ((fpoId == "0" || fpoId == undefined || fpoId == "")
			&& (assStatus == "0" || assStatus == undefined || assStatus == "")) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"fpoId" : fpoId,
			"assStatus" : assStatus
		};
		var ajaxResponse = doAjaxLoading('FPOAssessmentEntry.html?searchForm',
				requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function modifyCase(assId, formUrl, actionParam, mode) {
	
	var divName = formDivName;
	var url = "FPOAssessmentEntry.html?EDIT";
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
	var url = "FPOAssessmentEntry.html?printAssessMentDetails";
	data = {
		"assId" : assId,
		"mode" : mode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();

}

function calculateScore(ids) {
	var score =$("#score"+ids).val();
	var addScore =$("#addScore").val();
	
	if (score != undefined && score != ""){
	 $("#addScore").val(parseFloat(addScore) + parseFloat(score));
	}
}

function calculateTotal(ids) {
	var marks = $("#marksAwarded"+ids).val();
	var addMarks = $("#addMarks").val();
	if (marks !=undefined && marks != ""){
		$("#addMarks").val(parseFloat(addMarks) + parseFloat(marks));
	}
}


function saveApprovalData(element) {debugger
	
	var errorList = [];
	if ($("#finalRemark").val() == "") {
		errorList.push(getLocalMessage("sfac.reamrk.validate"));
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(element,
				"Fpo Assesment Approval Details Saved Successfully!", 'AdminHome.html',
				'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}
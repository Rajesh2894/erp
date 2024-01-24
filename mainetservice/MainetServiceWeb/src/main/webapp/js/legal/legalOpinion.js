var fileArray = [];
$(document).ready(function() {
	$("#opinionTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

});

function getLegalOpinionForm(id, mode, url) {

	
	var data = {
		"id" : id,
		"mode" : mode
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(url, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveLegalOpinionForm(element) {
	
	var errorList = [];
	errorList = ValidateLegalOpinionForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();

		return saveOrUpdateForm(element, "Saved Successfully",
				'AdminHome.html', 'saveform');
	}
}

function ValidateLegalOpinionForm(errorList) {

	var matterOfDispute = $("#matterOfDispute").val();
	var remark = $("#remark").val();
	var sectionActApplied = $("#sectionActApplied").val();
	var locId2 = $("#locId2").val();
	var opniondeptId= $("#legalOpinionDeptid").val();
	var radioCheck = $('input[type=radio]:checked').val();
	if (matterOfDispute == '' || matterOfDispute == 'undefined'
			|| matterOfDispute == null) {
		errorList.push(getLocalMessage("lgl.validation.query"))
		
	}
	if (remark == '' || remark == 'undefined' || remark == null) {
		errorList.push(getLocalMessage("lgl.validation.remark"))
	}
	
	
	if (radioCheck == '' || radioCheck == 'undefined' || radioCheck == null) {
		errorList.push(getLocalMessage("lgl.validation.withcase.withoutCase"))
	}
	
	if (radioCheck == "A"){
	if (locId2 == '' || locId2 == 'undefined' || locId2 == null
			|| locId2 == '0') {
		errorList.push(getLocalMessage("lgl.validation.location"))
	}
	if (opniondeptId == '' || opniondeptId == 'undefined' || opniondeptId == null
			|| opniondeptId == '0') {
		errorList.push(getLocalMessage("legal.casePendency.validation.department"))
	}

	}
	
	/*if (sectionActApplied == '' || sectionActApplied == 'undefined'
			|| sectionActApplied == null) {
		errorList.push(getLocalMessage("lgl.validation.section.act"))
	}*/
	if (radioCheck == "I") {

		var cseId = $("#cseId").val();
		if (cseId == '' || cseId == 'undefined' || cseId == null
				|| cseId == '0') {
			errorList.push(getLocalMessage("lgl.validation.case"))
		}

	}

	return errorList;

}
function searchCaseForLegalOpinion() {
	
	var data = {
		"Deptid" : $("#opinionDeptid").val()
	};

	var divName = '.content-page';
	var formUrl = "LegalOpinion.html?searchOpinionEntry";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getCase() {
	var r = $('input[type=radio]:checked').val();

	if (r == "A") {
		$("#caseNo").hide();
		$("#viewCase").hide();
		$(".withCase").show();
		
	}

	if (r == "I") {
		$("#caseNo").show();
		$("#viewCase").show();
		$(".withCase").hide();
	}

}

function viewCaseForm(e) {

	var caseId = $('#cseId option:selected').attr('value')
	var requestData = {
		"cseID" : caseId
	}
	var URL = 'LegalOpinion.html?viewCaseDetails';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');

	if (returnData != null)

	{
		$("#viewCase").show();
		$("#viewCase").html(returnData);
	}

	
			$("#matterOfDispute").val("");
			$("#sectionActApplied").val("");
			$("#remark").val("");
			//var opniondeptId = $('#opniondeptId option:selected').attr('value').val("") locId
			$("#opniondeptId").val("");
			$("#locId").val("");
 
		
}

function saveLegalOpinionDecisionForm(element) {
	
	var errorList = [];
	errorList = ValidateLegalOpinionDecisionForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
 
		return saveOrUpdateForm(element, "Saved Successfully",
				'AdminHome.html', 'saveDecision');
	}
}
function ValidateLegalOpinionDecisionForm(errorList) {
	
	var opinion = $("#opinion").val();
	var deptRemark = $("#deptRemark").val();

	if (opinion == '' || opinion == 'undefined' || opinion == null) {
		errorList.push(getLocalMessage("lgl.validation.details"))
	}
	if (deptRemark == '' || deptRemark == 'undefined' || deptRemark == null) {
		errorList.push(getLocalMessage("lgl.validation.dept.remark"))
	}
	return errorList;
}

//#141902
function viewOpinion(id, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function resetLegalopinionSearch(){
	$('#opinionDeptid').val('').trigger("chosen:updated");
}
$(document).ready(function() {
	
	$("#departmentDiv").hide();
	var artifactType = $("#artifactType").val();
	if(artifactType == '' || artifactType == '0')
		$("#artifactType").prop('disabled', false);
	
	$('#id_BpmBrmDeploymentMasterForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#id_bpmBrmDeploymentMaster").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$('#notifyUsers').change(function() {
		$("#notifyToDepartment").val(0);
		if($("#notifyUsers").is(':checked')){
			$("#departmentDiv").show();
		}else{
			$("#departmentDiv").hide();
		}
	});


});
function openAddBpmBrmDeploymentMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function Proceed(element) {
	var errorList = [];
	errorList = ValidateBpmEntry(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element,
				getLocalMessage('bpm.saveBpmBrmDeploymentMaster'),'BpmBrmDeploymentMaster.html', 'saveform');
	}

}

function modifyBpmBrmDeployment(bpmid, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode" : mode,
			"id" : bpmid
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function ValidateBpmEntry(errorList){
	

	var bpmRuntime = $("#bpmRuntime").val();
	var artifactType = $("#artifactType").val();
	var groupId = $("#groupId").val();
	var artifactId = $("#artifactId").val();
	var version = $("#version").val();
	var status = $("#status").val();
	var processId = $("#processId").val();
	var ruleFlowGroup = $('#ruleFlowGroup').val();
	var notifyUsers = $("#notifyUsers").is(':checked');
	var notifyToDepartment = $("#notifyToDepartment").val();

	if(bpmRuntime==""||bpmRuntime==null)
		errorList.push(getLocalMessage("bpm.validation.bpmRuntime"));
	if(artifactType==""||artifactType==null)
		errorList.push(getLocalMessage("bpm.validation.artifactType"));
	if(groupId==""||groupId==null)
		errorList.push(getLocalMessage("bpm.validation.groupId"));
	if(artifactId==""||artifactId==null)
		errorList.push(getLocalMessage("bpm.validation.artifactId"));
	if(version==""||version==null)
		errorList.push(getLocalMessage("bpm.validation.version"));
	if(status==""||status==null)
		errorList.push(getLocalMessage("bpm.validation.status"));

	if(artifactType == "bpm")
		if(processId==""||processId==null)
			errorList.push(getLocalMessage("bpm.validation.processId"));
	if(artifactType == "brm")
		if(ruleFlowGroup==""||ruleFlowGroup==null)
			errorList.push(getLocalMessage("bpm.validation.ruleFlowGroup"));

	if(notifyUsers && notifyToDepartment == '0')
		errorList.push(getLocalMessage("bpm.validation.notifyToDepartment"));
	
	return errorList;

}


function setGroupId(element){
	var artifactType = $(element).val();
	if(artifactType != '')
		$("#groupId").val( getLocalMessage("bpm.groupId."+artifactType) );
	else
		$("#groupId").val('');
}
var fileArray = [];
var removeIdArray = [];

var WorkProjectURL = 'WmsProjectMaster.html'
var WorkDefURL = "WmsWorkDefinationMaster.html";
$(document).ready(function() {
	
	/**
	 * This method is used to Search All Scheme
	 * wmsProjectMaster.jsp START METHOD
	 */
	
	$("#searchProjecmaster").click(function() {
		
		var errorList = [];
		var projCode = $('#projCode').val();
		var projectName = $('#projNameEng').val();
		var sourceCode = $('#sourceCode').val();
		var sourceName = $('#sourceName').val();
		var dpDeptId = $("#dpDeptId").val();
		var projStatus = $("#projStatus").val();
		var UADstatusForProject = $("#UADstatusForProject").val();
		var isDefaultStatusProj = $("#isDefaultStatusProj").val();
		/* #####Remove As Per SUDA UAT ##### */
			
		/*
		 * var startDate = $('#projStartDate').val(); var endDate = $('#projEndDate').val(); 
		 * if ((compareDate($("#projStartDate").val())) > compareDate($("#projEndDate") .val())) 
		 * errorList.push(getLocalMessage("project.master.vldn.projectdatevalidation"));
		 */

		/*if (errorList.length > 0) {$("#errorDiv").show();showErr(errorList);return false;} else {$("#errorDiv").hide();}*/
		
		if (projCode != '' || projectName != ''|| sourceCode != ''|| sourceName != ''
			|| dpDeptId != ''|| projStatus != '') {
			var requestData = '&projCode='+ projCode + '&projectName=' + projectName + '&sourceCode=' + sourceCode
			+ '&sourceName='+ sourceName + '&dpDeptId='+ dpDeptId + '&projStatus='+ projStatus;
			var table = $('#projectDatatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest(
					'WmsProjectMaster.html?getProjectMasterGridData',
					'POST', requestData, false,'json');
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("work.management.vldn.grid.nodatafound"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
			$.each(ajaxResponse,function(index) {
				
				var obj = ajaxResponse[index];
				if(UADstatusForProject == 'YES' && isDefaultStatusProj != 'Y'){
					result.push([index + 1,obj.projCode,obj.departmentName,obj.projNameEng,obj.schemeName,
						'<td >'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="showGridOption(\''
						+ obj.projId
						+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
						+ '</td>' ]);
				}else{
					result.push([index + 1,obj.projCode,obj.departmentName,obj.projNameEng,obj.schemeName,
						'<td >'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="showGridOption(\''
						+ obj.projId
						+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
						+ '<button type="button" class="btn btn-success btn-sm margin-right-10 "  onclick="showGridOption(\''
						+ obj.projId
						+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
						+ '<button type="button" class="btn btn-danger  btn-sm margin-right-10" onClick="showGridOption(\''
						+ obj.projId
						+ '\',\'D\')"  title="Delete"><i class="fa fa-trash "></i></button>'
						+ '</td>' ]);
				}
			});
			table.rows.add(result);
			table.draw();
		} else {
			errorList.push(getLocalMessage('work.management.valid.select.any.field'));
			displayErrorsOnPage(errorList);
		}
	});
	
	$("#projectDatatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});
	
	$("#datatablesProjectView").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});
	
	$(function() {
		$('.custDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true
		});
	});

	$('#wmsProjectMaster').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	if ($("#saveMode").val() == "E") {
		$(".reset").hide();
		if ($("#projEstimateCost").val() == "")
			$("#projEstimateCost").val("0.00");
		if ($("#projActualCost").val() == "")
			$("#projActualCost").val("0.00");
	}

	if ($("#saveMode").val() == "A") {
		$("#projEstimateCost").val("0.00");
		$("#projActualCost").val("0.00");
	}
	
	$("#projNameEng,#projDescription").keyup(
			function() {
				if (this.value.match(/[^a-zA-Z0-9-.!@&*, ]/g)) {
					this.value = this.value.replace(
							/[^a-zA-Z0-9-.!@&*, ]/g, '');
				}
			});

	$('.decimal').on('input', function() {
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals only
		.replace(/(^[\d]{13})[\d]/g, '$1') // not more than 13 digits at the beginning
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
		.replace(/(\.[\d]{2})./g, '$1'); // not more than 2 digits after decimal
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
			row--; // reOrderTableIdSequence();
		}
		e.preventDefault();
	});
		
	$("#deleteDoc").on("click",'#deleteFile',function(e) {
		var errorList = [];				
		if (errorList.length > 0) {
			$("#errorDiv").show();
			showErr(errorList);
			return false;
		} else {			
			$(this).parent().parent().remove();
			var fileId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if (fileId != '') {
				fileArray.push(fileId);
			}
			$('#removeFileById').val(fileArray);
		}
	});
	$('#startDateDesc').removeAttr('placeholder');
	$('#endDateDesc').removeAttr('placeholder');
	$('#rsoDateDesc').removeAttr('placeholder');
});

function openAddProjectMaster(formUrl, actionParam) {
	if (!actionParam) {

		actionParam = "add";
	}

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	prepareTags();

}

function saveProjectMaster(element) {

	var errorList = [];

	if ($("#dpDeptId").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.department"));

	if ($("#projNameEng").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.projectname"));

	if ($("#projNameReg").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.projectname.reg"));

	/*
	 * if ($("#projDescription").val() == "") errorList
	 * .push(getLocalMessage("project.master.vldn.projectdescription"));
	 */

	if ((compareDate($("#projStartDate").val())) > compareDate($("#projEndDate")
			.val()))
		errorList
				.push(getLocalMessage("project.master.vldn.projectdatevalidation"));

	/* ###### Removed As per the SUDA UAT ######### */

	/*
	 * if ($("#projNameEng").val().length < 50) errorList
	 * .push(getLocalMessage("project.master.vldn.projectname.length"));
	 */
	/*if ($("#projStartDate").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.projectstartdate"));
	if ($("#projEndDate").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.projectenddate"));*/

	if ((compareDate($("#projStartDate").val())) > compareDate($("#projEndDate")
			.val()))
		errorList
				.push(getLocalMessage("project.master.vldn.projectdatevalidation"));

	/*
	 * if ($(".schemeFromDate").val()!="")
	 * if((compareDate($("#projStartDate").val())) <
	 * compareDate($(".schemeFromDate") .val())) errorList
	 * .push(getLocalMessage("project.master.vldn.schemedatevalidation"));
	 * 
	 * if ($(".schemeToDate ").val()!="")
	 * if((compareDate($("#projEndDate").val())) > compareDate($(".schemeToDate ")
	 * .val())) errorList
	 * .push(getLocalMessage("project.master.vldn.schemedatevalidationenddate"));
	 */

	/*if($("#schId").val()== ""){
		errorList.push(getLocalMessage("project.master.vldn.scheme.name"));
	}*/
	/*if ($("#rsoNumber").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.rsonumber"));
	if ($("#rsoDate").val() == "")
		errorList.push(getLocalMessage("project.master.vldn.rsodate"));*/

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {
		$("#errorDiv").hide();

		return saveOrUpdateForm(element,
				getLocalMessage('project.master.vldn.savesuccessmsg'),
				'WmsProjectMaster.html', 'saveform');
	}
}
function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ getLocalMessage('project.master.vldn.savesuccessmsg') + '</h4>';
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

function proceed() {
	$("#postMethodForm").prop('action', 'WmsProjectMaster.html');
	$("#postMethodForm").submit();
	$.fancybox.close();

}

function backProjectMasterForm() {
	
	var saveMode = $("#saveMode").val();
	if (saveMode == 'SM') {
		$.fancybox.close();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('WmsSchemeMaster.html' + '?'
				+ 'showSchemeViewCurrentForm', {}, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'WmsProjectMaster.html');
		$("#postMethodForm").submit();
	}

}

function showGridOption(projId, action) {
	
	var actionData;
	var divName = formDivName;
	var requestData = 'projId=' + projId;
	if (action == "E") {
		actionData = 'editProjectMasterData';
		var ajaxResponse = doAjaxLoading('WmsProjectMaster.html?' + actionData,
				requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	if (action == "D") {
		actionData = 'deleteProjectMaster';
		showConfirmBoxForDelete(projId, actionData);
	}
	if (action == "V") {

		actionData = 'viewProjectMasterData';
		var ajaxResponse = doAjaxLoading('WmsProjectMaster.html?' + actionData,
				requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function showConfirmBoxForDelete(projId, actionData) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ getLocalMessage('material.master.vldn.delete') + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + projId + ')"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(projId) {	
	$.fancybox.close();
	var requestData = 'projId=' + projId;
	var ajaxResponse = doAjaxLoading('WmsProjectMaster.html?'
			+ 'deleteProjectMaster', requestData, 'html');
	if (ajaxResponse == 'false') {
		showErrormsgboxTitle(getLocalMessage("wms.proj.associate.work.not.delete"));
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'WmsProjectMaster.html');
		$("#postMethodForm").submit();
	}
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function compareDate(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function resetProjectMaster(resetBtn) {
	
	$('#dpDeptId').val('').trigger('chosen:updated');
	$('#schId').val('').trigger('chosen:updated');
	$('#projStatus').val('').trigger('chosen:updated');
	resetBtn.form.reset();
	$('[id*=file_list]').html('');
	$('#attachDoc').find('input:text').val('');
	$('.warning-div').addClass('hide');
}

function openSchemeMasterForm(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WmsProjectMaster.html?'
			+ 'openSchemeMasterForm', requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	prepareTags();
}

function fileCountUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('WmsProjectMaster.html?fileCountUpload',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}

function setDepertmentCode() {
	var selectedType = $("#dpDeptId").find("option:selected").attr('code');
	$("#departmentCode").val(selectedType);
}


function formatDate(date) {
	var parts = date.split("-");
	var formattedDate = parts[2] + "/" + parts[1] + "/" + parts[0];
	return formattedDate;
}

function viewWorkDef(workId) {

	//Defect #85134
	var errorList = [];
	var divName = '.content-page';
	var requestData = 'workId=' + workId + '&type=V';
	var ajaxResponse = __doAjaxRequest(WorkDefURL + '?formForUpdate',
			'POST', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$("#WmsWorkDefinationMaster :input").prop("disabled", true);
	$("#button-Cancel").prop("disabled", false);

	/*var errorList = [];
	var divName = '.content-page';
	var requestData = 'workId=' + workId;

	var contId = __doAjaxRequest(WorkProjectURL + '?getContractId', 'POST',
			requestData, false, 'json');

	if (contId == 0) {
		errorList.push("Contract is not defined");
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var type = 'V';
		showContractForm('ContractAgreement.html', contId, type);

		$("#partyDetails :input").prop("disabled", true);
		$("#ContractAgreement :input").prop("disabled", true);
		$('.addCF3').attr('disabled', true);
		$('.addCF4').attr('disabled', true);
		$('.addCF5').attr('disabled', true);
		$('.addCF2').attr('disabled', true);
		$('.remCF2').attr('disabled', true);
		$('.remCF3').attr('disabled', true);
		$('.remCF4').attr('disabled', true);
		$('.remCF5').attr('disabled', true);
		$('.uploadbtn').attr('disabled', false);
		$(".backButton").removeProp("disabled");
		// $("#partyDetails").removeProp("disabled");
	}

	$('.content').removeClass('ajaxloader');

	prepareTags();*/

}
function showContractForm(formUrl, contId, type) {

	var showForm = "WMS";
	var requestData = 'contId=' + contId + '&type=' + type + '&showForm='
			+ showForm;
	var ajaxResponse = doAjaxLoading(formUrl + '?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
}

function getSchemeDetails(obj) {
	
	// var sourceName = $("#sourceCode").find("option:selected").attr('code');
	var sourceId = $("#sourceCode").val();
	var requestData = {
		// "sourceName" : sourceName,
		"sourceId" : sourceId
	}
	$('#sourceName').html('');
	$('#sourceName').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('WmsProjectMaster.html?getSchemeFundSource',
			'post', requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		var source = value.lookUpCode + " -- " + value.lookUpDesc;
		$('#sourceName').append(
				$("<option></option>").attr("value", value.lookUpId).attr(
						"code", value.descLangSecond).text(source));
	});
	$('#sourceName').trigger("chosen:updated");
}
function otherTask() {
	return false;
}
function validationCheck(element){
	
	var error= [];
	var sourceCode = $('#sourceCode').val();
	var sourceName = $('#sourceName').val();
	if(sourceCode == ""){
		error.push(getLocalMessage('scheme.master.select.source.of.fund'));
		displayErrorsOnPage(error);
		return false;
	}
}
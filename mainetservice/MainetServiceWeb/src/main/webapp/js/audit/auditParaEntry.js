$(document).ready(function() {
	
	$("#addAuditParaDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});


	// Entry Date field handling on Add Audit Para Page

$('#auditEntryDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: '+0y',
		yearRange: "-30:+0",
		
	});

$('#fromDate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	
});

$('#toDate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	
});

$('#auditEntryDate').keyup(function(e) {

	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});




// As its a call without onclick reference in JSP file, it has to be within (document).ready
$('#search').click(function() {
	//
	var errorList = [];
	
	var auditPara = $('#auditParaCode').val();
	var auditType = $('#auditType').val();
	var auditDeptId = $('#auditDeptId').val();
	var auditWard1 = $('#auditWard1').val();
	var auditParaStatus = $('#auditParaStatus').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var table = $ ('#addAuditParaDataTable').DataTable();	
	
	
	if(auditPara != null || auditType != 0 || auditDeptId != 0 || auditWard1 != 0 || auditParaStatus != 0 || fromDate != "" || toDate != "")
	{
		var requestData = '&auditType=' + auditType  
		+ '&auditDeptId=' + auditDeptId + 	 '&auditWard1=' + auditWard1 + '&auditParaStatus=' + auditParaStatus
		+ '&auditParaCode=' + auditPara + '&fromDate=' + fromDate + '&toDate=' + toDate;
		
		searchData=requestData;
		
		table.rows().remove().draw();
		$('.error-div').hide();
		var ajaxResponse = __doAjaxRequest('AuditParaEntry.html?searchAuditPara','POST', requestData, false,'json');
	
		var len = ajaxResponse.searchAuditParaEntryDtoList.length;
		var len2 = ajaxResponse.locLst.length;
		var len3 = ajaxResponse.deptLst.length;
		
		
		
		
		if (len == 0) 
		{
			table.rows().remove().draw();
			$('.error-div').hide();
			errorList.push(getLocalMessage('audit.mgmt.validation.grid.nodatafound'));
			displayErrorsOnPage(errorList);
			return false;
		}
		else
		{
			var result = [];
			errorList = [];
			$('.error-div').hide();
			$.each(ajaxResponse.searchAuditParaEntryDtoList,function(index) 
			{
			var obj = ajaxResponse.searchAuditParaEntryDtoList[index];
			
			
			obj.auditDeptStr = fetchDepartment(obj.auditDeptId,ajaxResponse.deptLst);
			obj.auditZoneStr = fetchLocation(obj.auditWardDesc,ajaxResponse.locLst);
			obj.auditStatusStr = fetchStatus(obj.auditParaStatus,ajaxResponse.statusLst);
			var auditStatusCode = fetchStatusCode(obj.auditParaStatus,ajaxResponse.statusLst);
			
			if (auditStatusCode == 'D')
			{
				result.push([index + 1,obj.auditParaCode,obj.auditDateDesc,obj.auditDeptStr,obj.auditWardDesc,obj.auditStatusStr,
				'<td >'
				+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 "  onclick="showGridOption(\''
				+ obj.auditParaId
				+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
				+ '<button type="button" class="btn btn-danger btn-sm "  onclick="showGridOption(\''
				+ obj.auditParaId
				+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
		     	+ '</td>'
				]);
			}
			else
			{
				result.push([index + 1,obj.auditParaCode,obj.auditDateDesc,obj.auditDeptStr,obj.auditWardDesc,obj.auditStatusStr,
				'<td >'
				+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 "  onclick="showGridOption(\''
				+ obj.auditParaId
				+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
				+ '<button type="button" class="btn btn-sm "  onclick="dashboardViewHistory(\''
				+ 'null'
				+ '\',\''
				+ obj.auditParaCode
				+ '\',\''
				+ obj.auditEntryDate
				+ '\',\''
				+ "Audit Para Entry Approval"
				+ '\','
				+ obj.auditParaChk				
				+')"  title="History"><i class="fa fa-history"></i></button>'		     	
				+ '</td>'
				]);
				
			}
			
			
			});
			table.rows.add(result);
			table.draw();
		
		}
	}
	else
	{
		table.rows().remove().draw();
		errorList.push(getLocalMessage('audit.mgmt.validation.select.any.field'));
		displayErrorsOnPage(errorList);
		
	}
	
});	

$('#back').click(function()
{
	//
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AuditParaEntry.html');
	$("#postMethodForm").submit();
	
	
	
});


var type=($('option:selected', $("#categoryId")).attr('code'));

if(type == undefined || type == ""){
	$('.amountHide').hide();
} else if(type == 'FIN' || type == 'FL' || type == 'RECA' || type == 'OUTA'){
	$('.amountHide').show();
}else{
	$('.amountHide').hide();
}


	
});


var searchData="";



// Ajax call to  Add Audit Para Entry Form
function addAuditPara(formUrl, actionParam) {
	
	
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			formDivName);
	$(formDivName).removeClass('ajaxloader');
	$(formDivName).html(ajaxResponse);
	prepareTags();
}

// Reset Button Functionality 
function resetAuditParaEntry(resetBtn){
	
	resetForm(resetBtn);
}
// Back Button functionality on Add Audit Para Entry Form


var auditParaStatus;

// Save Data on Add Audit Para Entry Form
function submitform(obj) 
{
	 var checkValidations = saveSubmitValidationCheck();
     if(checkValidations)
	 {
	    $('#mode').val("O"); // setting mode
		return saveOrUpdateForm(obj, '', 'AuditParaEntry.html', 'saveform');
	    	
	 }
}


function saveform(obj) 
{
	var checkValidations = saveSubmitValidationCheck();
	if(checkValidations)
	{
	    	$('#mode').val("D");// setting mode
			return saveOrUpdateForm(obj, '', 'AuditParaEntry.html', 'saveform');
	}
}


function saveSubmitValidationCheck()
{
	
	var errorList = [];
	var entryDate = $('#auditEntryDate').val();
	var departmentName = $('#auditDeptId').val();
	var auditType = $('#auditType').val();
	var severity = $('#auditSeverity').val();
	var subject = $('#auditParaSub').val();
	var workName = $('#auditWorkName').val();
	var contractorName = $('#auditContractorName').val();
	var description = $('#auditAppendix').val();
	var recAmt = $('#recAmt').val();
	var auditParaYear = $('#auditParaYear').val();
	var auditParaTOYear = $('#auditParaTOYear').val();
	var zoneWard = $('#auditWard1').val();
	var categoryId = $('#categoryId').val();
	var subUnit = $('#subUnit').val();
	$('#auditParaStatus').value = 0;
	var auditParaStatus = $('#auditParaStatus').val();
	var type=($('option:selected', $("#categoryId")).attr('code'));
	
	
	if (entryDate == '') {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.entryDate'));
	}
	
	if (departmentName == '' || departmentName == 0) {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.departmentName'));
	}
	
	if (auditParaYear == '' || auditParaYear == 0) {
		errorList
				.push(getLocalMessage('audit.validation.from.date'));
	}
	
	if (auditParaTOYear == '' || auditParaTOYear == 0) {
		errorList
				.push(getLocalMessage('audit.validation.to.date'));
	}
	
	if (zoneWard == '' || zoneWard == 0) {
		errorList
				.push(getLocalMessage('audit.validation.zone'));
	}
	
	
	if (auditType == '' || auditType == 0) {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.auditType'));
	}
	
	if (severity == '' || severity == 0) {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.severity'));
	}
	
	if(recAmt =="") { 
		if(type == 'FIN' || type == 'FL' || type == 'RECA' || type == 'OUTA'){
			errorList
				.push(getLocalMessage('audit.validation.recovery.amount'));
		}
	}
	
	if (subject == '') {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.subject'));
	}
	
	if (categoryId == '' || categoryId == 0) {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.categoryId'));
	}
	
	if (subUnit == '' || subUnit == null || subUnit == undefined) {
		errorList
				.push(getLocalMessage('audit.validation.sub.units'));
	}
	
	/*if (workName == '') {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.workName'));
	}*/
		
	if (description == '' || description == undefined) {
		errorList
				.push(getLocalMessage('audit.mgmt.validation.description'));
	}
	
	if (errorList.length > 0) 
	{
		displayErrorsOnPage(errorList);
		return false;
	} 	
	else
	{
		return true;
	}



}





function showGridOption (auditParaId,action){

	var actionData;
	var requestData = '&auditParaId=' + auditParaId;
	if (action == "E") {
		actionData = 'editAuditPara';
		var ajaxResponse = doAjaxLoading('AuditParaEntry.html?' + actionData,
				requestData, 'html',formDivName);
		$('.content').removeClass('ajaxloader');
		$(formDivName).html(ajaxResponse);
		prepareTags();
	}
	
	if (action == "V") {

		actionData = 'viewAuditPara';
		var requestData = '&auditParaId=' + auditParaId;
		var ajaxResponse = doAjaxLoading('AuditParaEntry.html?' + actionData,
				requestData, 'html',formDivName);
		$('.content').removeClass('ajaxloader');
		$(formDivName).html(ajaxResponse);
		prepareTags();
	}
}



function fetchLocation(auditWard1, locList){
	var locName = "";
	$.each(locList,function(index) {
		var locObj = locList[index];
		if(locObj.locId == auditWard1){
			locName = locObj.locNameEng;
			
		}
	});
	return locName;
}
	

function fetchDepartment(auditDeptId, depList){
	var depName = "";
	$.each(depList,function(index) {
		var depObj = depList[index];
		if(depObj.dpDeptid == auditDeptId){
			depName = depObj.dpDeptdesc;
		}
	});
	return depName;
}


function fetchStatus(auditParaStatus, statusList){
	var status = "";
	$.each(statusList,function(index) {
		var statusObj = statusList[index];
		if(statusObj.lookUpId == auditParaStatus){
			status = statusObj.lookUpDesc;
			
		}
	});
	return status;
}

function fetchStatusCode(auditParaStatus, statusList){
	var statusCode = "";
	$.each(statusList,function(index) {
		var statusObj = statusList[index];
		if(statusObj.lookUpId == auditParaStatus){
			statusCode = statusObj.lookUpCode;
			
		}
	});
	return statusCode;
}

function dashboardViewHistory(appId,refId,auditDate,serviceId,workFlowId){
	var requestData = {"appId":appId,"refId":refId,"appDate":auditDate,"servName":serviceId,"workflowReqId":workFlowId};
    
	var response =__doAjaxRequest('AuditParaEntry.html?viewFormHistoryDetails', 'post', requestData, false, 'html');
	$(formDivName).html(response);
	

}

$(document).on('change', '.paraCategory', function() {
	var type=($('option:selected', $("#categoryId")).attr('code'));

				if(type == undefined || type == ""){
					$('.amountHide').hide();
				} else if(type == 'FIN' || type == 'FL' || type == 'RECA' || type == 'OUTA'){
					$('.amountHide').show();
				}else{
					$('.amountHide').hide();
				}
	});

function searchAuditReport(formUrl, actionParam) {
	var errorList = [];
	var data = {
			"deptId" : $('#auditDeptId').val(),
			"auditParaYear" : $('#auditParaYear').val(),
			"fromDate" : $('#fromDate').val(),
			"toDate" : $('#toDate').val()
		};
	if($('#auditDeptId').val() == ""){
		errorList.push(getLocalMessage('audit.report.validation.dept'));
	}
	if($('#auditParaYear').val() == 0){
		errorList.push(getLocalMessage('audit.report.validation.year'));
	}
	if($('#fromDate').val() == ""){
		errorList.push(getLocalMessage('audit.report.validation.fDate'));
	}
	if($('#toDate').val() == ""){
		errorList.push(getLocalMessage('audit.report.validation.tDate'));
	}
	if (errorList.length == 0){
		var URL = 'AuditParaReport.html?validateAuditReport';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
		if(returnData == "false"){
			errorList.push(getLocalMessage('audit.report.validation.records'));
		}
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}else{
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	}
}
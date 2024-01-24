function closeErrBox()
		{
			$('.error-div').hide();
			
		}

$(document).ready(function() {
	$('#others').hide();
	$('#daily').hide();
	$('#startOnDate_yearly_div').hide();
	$('#startOnDate_monthly_div').hide();
			
	if($('#cpdIdBfr').val()=='0'){
		$('#others').hide();
		$('#daily').hide();
		$('#startOnDate_yearly_div').hide();
		$('#startOnDate_monthly_div').hide();
		
	}else{
		/*var jobFrequency=$('option:selected',$("#cpdIdBfr")).val('code');*/
		var jobFrequency=$("#cpdIdBfr option:selected").attr("code");
		showAndHide(jobFrequency);
	}
	/*if($('#hiddenJobCode').val()!='0'){
		$('#jobId').val($('#hiddenJobCode').val());
	}*/
	
	$('#startAt_daily').timepicker({
	    
	});
	
	$('#startAt_others').timepicker({
		
	});
	
	$('#startOnDate_monthly').datepicker({
        changeMonth: true,
        changeYear: false,
        stepMonths: false,
        dateFormat: 'dd/mm/yy'
    });
	
	$('#startOnDate_yearly').datepicker({
		changeMonth: true,
		changeYear: false,
		stepMonths: false,
		dateFormat: 'dd/mm/yy'
	});
	
	var dept=$('#hiddenDept').val();
	if(dept!='' && dept!=undefined){
//		$('#departmentForQuartz').val(dept);
		
		findJobsByDepartment();
		
		var hiddencpdIdBjo=$('#hiddencpdIdBjo').val();
		if(hiddencpdIdBjo!='0' && hiddencpdIdBjo!=undefined && hiddencpdIdBjo!=''){
			$('#jobId').val(hiddencpdIdBjo);
		}
		var jobFrequency=$("#cpdIdBfr option:selected").attr("code");
		$('#hiddenJobFrequencyType').val(jobFrequency);
	}
	
	
});




function findJobsByDepartment(){
	
	var requestData = {};
	var url	='QuartzSchedulerMaster.html';
	param='jobName';
	url=url+'?'+param;

	var jobLookUps=__doAjaxRequest(url,'POST',requestData,false,'json');
	
	prepareChildrens(jobLookUps,'jobId');
	
	/*$('#jobId').val(jobNames);*/
	
}


function findJobs(obj,param){
	
	var theForm	=	'#'+findClosestElementId(obj,'form');
	var requestData = __serializeForm(theForm);
	var url	=	$(theForm).attr('action');
	url=url+'?'+param;
	
	var jobNames=__doAjaxRequest(url,'POST',requestData,false,'json');
	
	prepareChildrens(jobNames,'jobId');
	
	/*$('#jobId').val(jobNames);*/
	
}


function getAvailableJobsAfterValidationError(){
	var url="QuartzSchedulerMaster.html?availableJobs";
	var requestData={};
	var jobNameLookups=__doAjaxRequest(url,'GET',requestData,false,'json');
	
	prepareChildrens(jobNameLookups,'jobId');
	
	var jobVal=$('#hiddenJobCode').val();
	$('#jobId').val(jobVal);
}



function runOnDateAndTime()
	{
	
	var jobFrequency=$("#cpdIdBfr option:selected").attr("code");
	/*alert('jobFrequency='+jobFrequency);*/
	/*var jobFrequency=$('option:selected',$("#cpdIdBfr")).attr('code');*/
	/*alert('code='+jobFrequency);*/
	$('#hiddenJobFrequencyType').val(jobFrequency);
	
	if(jobFrequency=='M' || jobFrequency=='Q'){
		showCalenderForMonthly();
	}else if(jobFrequency=='Y' || jobFrequency=='HY'){
		showCalenderForYearly();
	}
	
	showAndHide(jobFrequency);
	
	if(jobFrequency=='0' || jobFrequency==null||jobFrequency==undefined ){
		$('.startOn').val('');
		$('#startAt_others').val('');
		$('#startAt_daily').val('');
		
	}
	
}


function showCalenderForMonthly(){
	
	$('#startOnDate_monthly').datepicker({
        changeMonth: true,
        changeYear: false,
        stepMonths: false,
        dateFormat: 'dd/mm/yy'
    });
}

function showCalenderForYearly(){
	
	$('#startOnDate_yearly').datepicker({
		changeMonth: true,
		changeYear: false,
		stepMonths: true,
		dateFormat: 'dd/mm/yy'
	});
}



function setJobCode(){
	
	$('#hiddenJobCode').val($('#jobId').val());
	
}


function showAndHide(jobFrequency){
	
	
	if(jobFrequency=='D'){
		$('#daily').show();
		$('#others').hide();
		$('#startOnDate_monthly_div').hide();
		$('#startOnDate_yearly_div').hide();
		
		
	}else if(jobFrequency=='M'){
		
		$('#others').show();
		$('#daily').hide();
		$('#startOnDate_monthly_div').show();
		$('#startOnDate_yearly_div').hide();
		
	}else if(jobFrequency=='OT'){
		$('#others').hide();
		$('#daily').hide();
		$('#startOnDate_monthly_div').show();
		$('#startOnDate_yearly_div').show();
	}else if(jobFrequency=='Q'){
		
		$('#others').show();
		$('#daily').hide();
		$('#startOnDate_monthly_div').show();
		$('#startOnDate_yearly_div').hide();
		
	}else if(jobFrequency=='HY'){
		$('#others').show();
		$('#daily').hide();
		$('#startOnDate_monthly_div').show();
		$('#startOnDate_yearly_div').hide();
	}else if(jobFrequency=='Y'){
		$('#others').hide();
		$('#daily').hide();
		$('#startOnDate_monthly_div').show();
		$('#startOnDate_yearly_div').show();
	}else if(jobFrequency=='W'){
		
		$('#others').show();
		$('#daily').hide();
		$('#startOnDate_monthly_div').show();
		$('#startOnDate_yearly_div').hide();
		
	}else if(jobFrequency==undefined){
		$('#others').hide();
		$('#daily').hide();
		$('#startOnDate_monthly_div').hide();
		$('#startOnDate_yearly_div').hide();
	}
	
}


function saveQuartzMasterForm(obj){
	
	
	var theForm	=	'#'+findClosestElementId(obj,'form');
	var requestData = __serializeForm(theForm);
	
	var succesMsg='Job details saved succesfully';
	var successURL='QuartzSchedulerMaster.html';
	return saveOrUpdateForm(obj, succesMsg,successURL, 'saveform');
	
}
function saveQuartzMasterAfterEdit(obj){
	
	
	var theForm	=	'#'+findClosestElementId(obj,'form');
	var requestData = __serializeForm(theForm);
	
	var succesMsg='Job details modified!';
	var successURL='QuartzSchedulerMaster.html';
	return saveOrUpdateForm(obj, succesMsg,successURL, 'save');
	
}


function openBatchJobEditForm(){
	
	var url="QuartzSchedulerMaster.html";
	var actionParam='batchJobForm';
//	var requestData={};
//	__doAjaxRequest(url,'GET',requestData,false,'html');
	
	openForm(url);
	
}


function searchAvailableJobsByDepartment(){
	
	var errorList=[];
	var selectedDept=$('#departmentForQuartz').val();
	
	var url="QuartzSchedulerMaster.html?searchJobs";
	var requestData = {
			"deptCode" : selectedDept
			
		};
		
	if(selectedDept=='' || selectedDept=='0'){
		errorList.push(getLocalMessage('quartz.form.field.department.validation.error'));
	}
	
	if(errorList.length==0){
		var isJobsAvailable=__doAjaxRequest(url, 'POST', requestData, false, 'json');
		
		if(isJobsAvailable=='N'){
			
			errorList.push(getLocalMessage('quartz.form.field.noJobs.validation.error'));
			promptError(errorList);
			$('.error-div').show();
		}else{
			$('.error-div').hide();
		}
	
		
	}else{
		promptError(errorList);
		$('.error-div').show();
	}
//	__doAjaxRequest(url,'POST',requestData,false,'html');
	
	
	
	
	reloadGrid('gridQuartzSchedulerMaster');
	
}


function promptError(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
	$('.error-div').show();
	
}


function closebox(a,b){
	
	window.location.href='QuartzSchedulerMaster.html';
}

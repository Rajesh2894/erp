//<!-- Add Table field row--> 
/*
 * This script file is being used for Workflow
 * 
 */


var count=0;
var eventNameId='';
var weIdlist=[];
$(document).ready(function(){
	
	
	$("#customFields").on('click','.addCF',function(i){
		
			var row=0;
			var errorList = [];
			errorList = validateSelectionData(errorList);
			
			if (errorList.length == 0) {
				
				$('.appendableClass').each(function(i) {
					row=i+1;
					errorList = validateWorkflowTableData(errorList,i);
				});
				
				if (errorList.length == 0 ) {
					 var romm=0;
					var content = $(this).closest('tr').clone();
					$(this).closest("tr").after(content);
					var clickedIndex = $(this).parent().parent().index() - 1;	
					
					content.find("input:text:eq(0)").attr("value", "");
					content.find("input:text:eq(1)").attr("value", "");
					//content.find("input:text:eq(2)").attr("value", "");
					//content.find("input:text:eq(3)").attr("value", "");
					content.find("input:hidden:eq(1)").attr("value",romm);
					content.find("input:hidden:eq(2)").attr("value", +row+1);
					$(content).find("td:eq(0)").attr("id", "srNoId_"+row+1);
					
					$(content).find("select:eq(0)").attr("id", "eventNameId_"+row);
					$(content).find("select:eq(1)").attr("id", "roleId_"+row);
					$(content).find("select:eq(2)").attr("id", "requiredId_"+row);					
					//$(content).find("input:text:eq(0)").attr("class", "appNoId_"+row);
					//$(content).find("input:text:eq(1)").attr("class", "dependId_"+row);
					$(content).find("input:text:eq(0)").attr("class", "conditionalNextstep_"+row);				
					$(content).find("input:text:eq(1)").attr("class", "slaId_"+row);
					$(content).find("input:hidden:eq(1)").attr("class", "weId_"+row);
					$(content).find("input:hidden:eq(2)").attr("class", "weStepNo_"+row);

					var roleID='workFlowEventDTO['+row+'].roleId';
				
					content.find("select:eq(0)").attr("name","workFlowEventDTO["+row+"].weServiceEvent");
					content.find("select:eq(1)").attr("name",roleID);
					content.find("select:eq(2)").attr("name","workFlowEventDTO["+row+"].weIsrequire");	
					// content.find("input:text:eq(0)").attr("name","workFlowEventDTO["+row+"].weNoOfApprover");					
					//content.find("input:text:eq(1)").attr("name","workFlowEventDTO["+row+"].weDependsOnSteps");									
					content.find("input:text:eq(0)").attr("name","workFlowEventDTO["+row+"].weCondiatinalFalseNextStep");
					content.find("input:text:eq(1)").attr("name","workFlowEventDTO["+row+"].weSla");
					content.find("input:hidden:eq(1)").attr("name","workFlowEventDTO["+row+"].weId");
					content.find("input:hidden:eq(2)").attr("name","workFlowEventDTO["+row+"].weStepNo");
			
					
					reOrderTableIdSequence();
				}else {
					displayErrorsOnPage(errorList);
				}
				
			}else {
				displayErrorsOnPage(errorList);
			}
		
	});
    $("#customFields").on('click','.remCF',function(){
		
		if($("#customFields tr").length != 2)
			{
				 $(this).parent().parent().remove();					
				 reOrderTableIdSequence();
			}
	   else
			{
				alert("You cannot delete first row");
			}
		
 });

});	


//<!-- Add Table field row-->
/*Event on organization selection*/
$(function() {
	$("#onOrgSelect").change(function(){
		
		$('#roleId_0').css('height','');
		
		var requestData = {
				"orgId":$(this).val()
		};
		$('#departmentId').html('');
		$('#departmentId').append($("<option></option>").attr("value","0").text(getLocalMessage('workflow.form.select.department')));
		var result=__doAjaxRequest("WorkflowHome.html?department",'POST',requestData,false,'json');	
		var deptList=result[0];
		var roles=result[1]; 	 
		 $.each(deptList, function(index, value) {
			 $('#departmentId')
	         .append($("<option></option>")
	         .attr("value",value[0])
	         .text(value[1]));
		});
 
		 $.each(roles, function(index, value) {
			 $('#roleId_0')
			 .append($("<option></option>")
					 .attr("value",value[0])
					 .text(value[1]))
					 .css('height','auto');
			 
			 
		 });
	});
		
});

//Event on selection of Department and populate Services by department

$(function() {
	$("#departmentId").change(function(){
		
		var requestData = {
				"orgId":$('#onOrgSelect').val(),
				"deptId":$('#departmentId').val()
		};
		$('#serviceId').html('');
		$('#serviceId').append($("<option></option>").attr("value","0").text(getLocalMessage('workflow.form.select.service')));
		var result=__doAjaxRequest("WorkflowHome.html?services",'post',requestData,false,'json');
		
		$.each(result, function(index, value) {
			$('#serviceId')
			.append($("<option></option>")
					.attr("value",value[0])
					.text(value[1]));
		});
		
		var data = {
				"deptId":$('#departmentId').val()
		};
		if (result.length !=0) {
			var response=__doAjaxRequest("WorkflowHome.html?wardZone",'post',data,false,'html');
			$('#ward_zoneId').html(response);
			$('input[name=wardZoneType]').attr("disabled",false);
		} else {
			$('input[name=wardZoneType]').attr("disabled",true);
			$('input[name=wardZoneType]').removeAttr("checked",true);
			
			$('#ward_zoneId').hide();
		}
		
	});
	
});

//make event on selection on Service, fetch list of available Event for the Service
$(function() {
	$("#serviceId").change(function(){
	
		var requestData = {
				"orgId":$('#onOrgSelect').val(),
				"deptId":$('#departmentId').val(),
				"smServiceId":$('#serviceId').val() 
				};
		$('#eventNameId_0').html('');
		$('#eventNameId_0').append($("<option></option>").attr("value","0").text(getLocalMessage('workflow.form.select.service')));
		var result=__doAjaxRequest("WorkflowHome.html?events",'post',requestData,false,'json');
		$.each(result, function(index, value) {
			$('#eventNameId_0')
			.append($("<option></option>")
					.attr("value",value[0])
					.text(value[1]));
		});
	});
	
});

function reOrderTableIdSequence() {

	
	$('.appendableClass').each(function(i) {

		$(this).find("td:eq(0)").attr("id", "srNoId_"+i);
		$(this).find("select:eq(0)").attr("id", "eventNameId_"+i);
		$(this).find("select:eq(1)").attr("id", "roleId_"+i);
		$(this).find("select:eq(2)").attr("id", "requiredId_"+i);			
		//$(this).find("input:text:eq(0)").attr("class", "appNoId_"+i);					
		// $(this).find("input:text:eq(1)").attr("class", "dependId_"+i);
		$(this).find("input:text:eq(0)").attr("class", "conditionalNextstep_"+i);
		$(this).find("input:text:eq(1)").attr("class", "slaId_"+i);	
		$(this).find("input:hidden:eq(1)").attr("class", "weId_"+i);
		$(this).find("input:hidden:eq(2)").attr("class", "weStepNo_"+i);
		$("#srNoId_"+i).text(i+1);

		var roleID='workFlowEventDTO['+i+'].roleId';
		
		$(this).find("select:eq(0)").attr("name","workFlowEventDTO["+i+"].weServiceEvent");
		$(this).find("select:eq(1)").attr("name",roleID);
		$(this).find("select:eq(2)").attr("name","workFlowEventDTO["+i+"].weIsrequire");
		//$(this).find("input:text:eq(0)").attr("name","workFlowEventDTO["+i+"].weNoOfApprover");
		//$(this).find("input:text:eq(1)").attr("name","workFlowEventDTO["+i+"].weDependsOnSteps");
		$(this).find("input:text:eq(0)").attr("name","workFlowEventDTO["+i+"].weCondiatinalFalseNextStep");		
		$(this).find("input:text:eq(1)").attr("name","workFlowEventDTO["+i+"].weSla");
		$(this).find("input:hidden:eq(1)").attr("name","workFlowEventDTO["+i+"].weId");
		$(this).find("input:hidden:eq(2)").attr("name","workFlowEventDTO["+i+"].weStepNo");
				
	});
	
}

//form submit
$(function() {
	$("#workFlowSubmit").click(function(){
		
		var errorList=[];
		var orgId=$('#onOrgSelect').val();
		var deptId=$('#departmentId').val();
		var serviceId=$('#serviceId').val();
		var workFlowName=$('#wdName').val();
		var payMode=$('input[name=wdMode]:checked', '#workflowId').val();
		
		if (orgId=='' || orgId=='0' || orgId == undefined) {
			errorList.push('Please select Organization.');
		} if (deptId == '' || deptId == '0' || deptId == undefined ) {
			errorList.push('Please select Department.');
		} if (serviceId == '' || serviceId == '0' || serviceId == undefined) {
			errorList.push('Please select Service.');
		} 
		
		/*if (payMode == '' || payMode == '0' || payMode == undefined) {
			errorList.push('Please select Payment Mode.');
		}if (workFlowName == '' || workFlowName == '0' || workFlowName == undefined) {
			errorList.push('Please Enter Work Flow Name');
		}*/
		
		if($("#customFields tr").length == 0){
			 errorList.push('Please map at least one Event');
		}
		
		
		$('.appendableClass').each(function(i) {
			
			errorList=validateWorkflowTableData(errorList, i);
		});
		
		
		if (errorList.length==0) {
			var requestData = __serializeForm('form');
			
			var result=__doAjaxRequest("WorkflowHome.html?create",'POST',requestData,false,'json');
			if (result == 'Y') {
				displayMessageOnSubmit();
			}else
			{
				displayMessageOnError(result);
			}
			
		} else {
			displayErrorsOnPage (errorList);
		}
		
		
	});
	
});


function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
	$(".error-div").show();
	
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}


function displayMessageOnSubmit(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Close';
	
	message	+='<p>Workflow data saved.Click on close button</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="refreshWorkflowPage()"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function displayMessageOnUpdate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Close';
	
	message	+='<p>Workflow data Update Successfully</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="refreshWorkflowPage()"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function refreshWorkflowPage () {
	$.fancybox.close();
	window.location.href='WorkflowHome.html';
}


function validateSelectionData(errorList) {
	
	var validateOrgId = $.trim($('#onOrgSelect').val());
	var validateDeptId = $.trim($('#departmentId').val());
	var workFlowName = $.trim($('#wdName').val());
	var validatedServiceId =$.trim($('#serviceId').val());
	var payMode=$('#wdMode').val();
	 var wardZoneType= $("input[name='wardZoneType']:checked").val();
	 var modeType= $("input[name='modeType']:checked").val();
	 
	 var dwzId1 = $('#dwzId1').val();
	 var dwzId2 = $('#dwzId2').val();
	 var dwzId3 = $('#dwzId3').val();
	 var dwzId4 = $('#dwzId4').val();
	 
	 if(validateOrgId =="" || validateOrgId =='0' || validateOrgId == undefined ){
		 errorList.push('Organization must be selected.');
	 }
	 if(validateDeptId =="" || validateDeptId =='0' || validateDeptId == undefined ){
		 errorList.push('Department must be selected.');
	 }
	 if(validatedServiceId == "" || validatedServiceId =='0' || validatedServiceId == undefined ){
		 errorList.push('Service must be selected.');
	 }
	/* if(workFlowName == "" || workFlowName == undefined){
		 errorList.push('WorkFlow Name cannot be empty.');
	 }*/
	 if(wardZoneType == "" || wardZoneType == undefined){
		 errorList.push('Ward-Zone Type must be selected.');
	 } else if (wardZoneType == "N") {
		 if (dwzId1 == '' || dwzId1 =='0' || dwzId1 == undefined) {
			 errorList.push('Zone must be selected.');
		 }
		 if (dwzId2 == '' || dwzId2 =='0' || dwzId2 == undefined) {
			 errorList.push('Ward must be selected.');
		 }
	 }
	 /*if(modeType == "" || modeType == undefined){
		 errorList.push('Mode Type must be selected.');
	 } else if (modeType == 'P') {
		 if(payMode == "" || payMode =='0'  || payMode == undefined){
			 errorList.push('Payment Mode must be selected. ');
		 }
	 }*/
	 	var isComplaint = $('#isComplaintId').is(':checked');
	 	
	 	if (isComplaint == true) {
	 		var complaintType = $('#complaint_type_id').val();
			 var complaintSubType = $('#complaintSubType_id').val();
			 if (complaintType == '' || complaintType =='0' || complaintType == undefined) {
				 errorList.push('Complaint type must be selected. ');
			 } if (complaintSubType == '' || complaintSubType =='0' || complaintSubType == undefined){
				 errorList.push('Complaint subtype must be selected. ');
			 }
	 	}
	
	 
	return errorList;	
}


function validateWorkflowTableData(errorList,i) {
	
   
	
	
	 var eventNameId = $.trim($("#eventNameId_"+i).val());
	 var roleId = $.trim($("#roleId_"+i).val());		
	// var appNoId = $.trim($(".appNoId_"+i).val());	
	 var slaId = $.trim($(".slaId_"+i).val());
	 var requiredId = $.trim($("#requiredId_"+i).val());	
	// var dependId = $.trim($(".dependId_"+i).val());		 
	 var conditionalNextstep_ = $.trim($(".conditionalNextstep_"+i).val());
	 var srId= $("#srNoId_"+i).text();
	 
	 if(eventNameId =="" || eventNameId =='0'  || eventNameId == undefined ){
		 errorList.push(getLocalMessage('workflow.select.eventName'));
	 }
	 if(roleId == ""  || roleId =='0'  || roleId == undefined){
		 errorList.push(getLocalMessage('workflow.type.val.sel.roleOrEmp'));
	 }
	 /*if(appNoId=="" || appNoId == undefined){
		 errorList.push(getLocalMessage('Approver Can not be empty'));
	 }	*/	
	 if(slaId=="" || slaId == undefined){
		 errorList.push(getLocalMessage('workflow.sla.not.empty'));
	 }		
	 		
	 if(requiredId=="" || requiredId =='0'  || requiredId == undefined){
		 errorList.push(getLocalMessage('workflow.select.requiredType'));
	 }
	  /*if(requiredId =="3")
       {
		  if(dependId=="" || dependId == undefined){
		    errorList.push(getLocalMessage('Depend On Event Can not be empty'));
	      }		 
       }	*/
	 var srValue=0;
	 if(requiredId =="C")
		{
		   if(conditionalNextstep_=="" || conditionalNextstep_ == undefined){
			 errorList.push('Conditional Next Step Can not be empty');
		   }else{
			   
			  if(parseInt(conditionalNextstep_) <= parseInt(srId)){
					 errorList.push('Conditional Next Step Can not be equal and less than of current step');
				}
			 }
		}
	
	
	
	
	 return errorList;
	
}

/*
 * function to Reset Form Data
 */
$(function(){
	$('#resetId').click(function(){
		window.location.href='WorkflowHome.html';
	});
});


jQuery('.hasNumberClass').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});

$(function(){
	$('#workFlowadd').click(function(){
	
	var errorList = [];
	errorList = validateSelectionData(errorList);
	
	if (errorList.length==0) {
		
		
		var orgId=$('#onOrgSelect').val();
		var deptId=$('#departmentId').val();
		var serviceId=$('#serviceId').val();
		var payMode=$('#wdMode').val();
		/*var requestData  ={"orgId":orgId,
				  "deptId":deptId,
				  "serviceId" :serviceId,					
				  "payMode":payMode				  
		          };
		*/
		var requestData = $('#workFlowFrmId').serialize();
		
		 var result =__doAjaxRequest("WorkflowHome.html?checkWorkFlowData",'post',requestData,false,'');	
	
		 if (result == 'Y') {
			 $('#addMode').show();
			 $('.errorblock').hide();
			}else{
				
			displayMessageOnAdd();	
			}
	} else {
		displayErrorsOnPage (errorList);
	}	
	});
});
$(function(){
	$('#workFlowedit').click(function(){	
		
		var errorList = [];
		errorList = validateSelectionData(errorList);	
		
		if (errorList.length==0) {
		
			/*var orgId=$('#onOrgSelect').val();
			var deptId=$('#departmentId').val();
			var serviceId=$('#serviceId').val();
			var payMode=$('input[name=wdMode]:checked', '#workflowId').val();
			var requestData  ={"orgId":orgId,
					  "deptId":deptId,
					  "serviceId" :serviceId,					
					  "payMode":payMode				  
			          };*/
			var requestData = $('#workFlowFrmId').serialize();
			 var returnData=__doAjaxRequest("WorkflowHome.html?editMode",'post',requestData,false,'');			
			 $("#divId").html(returnData);
					
		} 
		else 
		{
			displayErrorsOnPage (errorList);
		}
	});
});

$(function() {
	$("#workFlowEditSubmit").click(function(){
		var errorList=[];
		var orgId=$('#onOrgSelect').val();
		var deptId=$('#departmentId').val();
		var serviceId=$('#serviceId').val();
		var workFlowName=$('#wdName').val();
		var payMode=$('input[name=wdMode]:checked', '#workflowId').val();
		 var wardZoneType= $("input[name='wardZoneType']:checked").val();
		 
		 var dwzId1 = $('#dwzId1').val();
		 var dwzId2 = $('#dwzId2').val();
		 var dwzId3 = $('#dwzId3').val();
		 var dwzId4 = $('#dwzId4').val();
		 
		
		$("#weIdlist").val(weIdlist);
		if (orgId=='' || orgId=='0' || orgId == undefined) {
			errorList.push('Organization must be selected.');
		} if (deptId == '' || deptId == '0' || deptId == undefined ) {
			errorList.push('Department must be selected.');
		} if (serviceId == '' || serviceId == '0' || serviceId == undefined) {
			errorList.push('Service must be selected.');
		} 
		 if(wardZoneType == "" || wardZoneType == undefined){
			 errorList.push('Ward-Zone Type must be selected.');
		 } else if (wardZoneType == "N") {
			 if (dwzId1 == '' || dwzId1 =='0' || dwzId1 == undefined) {
				 errorList.push('Zone must be selected.');
			 }
			 if (dwzId2 == '' || dwzId2 =='0' || dwzId2 == undefined) {
				 errorList.push('Ward must be selected.');
			 }
		 }
		/*if (payMode == '' || payMode == '0' || payMode == undefined) {
			errorList.push('Please select Payment Mode.');
		}if (workFlowName == '' || workFlowName == '0' || workFlowName == undefined) {
			errorList.push('Please Enter Work Flow Name');
		}*/
		 var isComplaint = $('#isComplaintId').is(':checked');
		 	
		 	if (isComplaint == true) {
		 		var complaintType = $('#complaint_type_id').val();
				 var complaintSubType = $('#complaintSubType_id').val();
				 if (complaintType == '' || complaintType =='0' || complaintType == undefined) {
					 errorList.push('Complaint type must be selected. ');
				 } if (complaintSubType == '' || complaintSubType =='0' || complaintSubType == undefined){
					 errorList.push('Complaint subtype must be selected. ');
				 }
		 	}
		if($("#customFields tr").length == 0){
			 errorList.push('Please map at least one Event');
		}
		
		
		$('.appendableClass').each(function(i) {
			
			errorList=validateWorkflowTableData(errorList, i);
		});
		
		
		if (errorList.length==0) {
			var requestData = __serializeForm('form');
			
			var result=__doAjaxRequest("WorkflowHome.html?update",'POST',requestData,false,'json');
			if (result == 'Y') {
				displayMessageOnUpdate();
			}else{
				displayMessageOnError(result);	
			}
			
		} else {
			displayErrorsOnPage (errorList);
		}
		
		
	});
	
});

$(function(){
	$('#workFlowDelete').click(function(){	
		
		var errorList = [];
		errorList = validateSelectionData(errorList);	
		
		if (errorList.length==0) {
		
			/*var orgId=$('#onOrgSelect').val();
			var deptId=$('#departmentId').val();
			var serviceId=$('#serviceId').val();
			var payMode=$('input[name=wdMode]:checked', '#workflowId').val();
			var requestData  ={"orgId":orgId,
					  "deptId":deptId,
					  "serviceId" :serviceId,					
					  "payMode":payMode				  
			          };*/
			var requestData = $('#workFlowFrmId').serialize();
			var result =__doAjaxRequest("WorkflowHome.html?checkWorkFlowData",'post',requestData,false,'');	
			if(result=='N')
			 {
				 var returnData=__doAjaxRequest("WorkflowHome.html?DeleteMode",'post',requestData,false,'');
	
				 if (returnData == 'Y') {
						displayMessageOnDelete();
					}else
					{
					  displayMessageOnError(result);	
					}
			 }
			else
				{
				 displayMessageOnCheck();
				}
		} 
		else 
		{
			displayErrorsOnPage (errorList);
		}
	});
});
function removeEvent(weId)
{			  
	var errorList = [];
	errorList = validateSelectionData(errorList);	
	if (errorList.length==0) {	
		var result = confirm("Are you sure you want to delete this Even?");
	    if (result)
	    {	    	
		    weIdlist.push(weId);
		    displayMessageOnRemove();	     
	    }
	   }
	    else 
	    {
		displayErrorsOnPage (errorList);
	    }
	
}
function displayMessageOnDelete(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Close';
	
	message	+='<p>Workflow data Deleted.Click on close button</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="refreshWorkflowPage()"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}
function displayMessageOnRemove(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	
	message	+='<p>Workflow Event data Deleted.Click on close button</p>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}
function displayMessageOnAdd(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Close';
	
	message	+='<p>Workflow Data Already Present.Click on close button</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="refreshWorkflowPage()"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}
function displayMessageOnCheck(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Close';
	
	message	+='<p>Workflow Data are not Present.Click on close button</p>';
	 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
	'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
	' onclick="refreshWorkflowPage()"/>'+	
	'</p>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}
function displayMessageOnError(Msg){
	
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';	
	message	+='<p>'+Msg+'.Click on close button</p>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}


$(function() {
	 $('input:radio[name="wardZoneType"]').change(function () {
		 var wardZoneType= $("input[name='wardZoneType']:checked").val();
         if (wardZoneType == 'A') {
             $('#ward_zoneId').hide();
         } else if (wardZoneType == 'N') {
        	 $('#ward_zoneId').show();
         }
         
		});
});

$(function() {
	$('input:radio[name="modeType"]').change(function () {
		var modeType= $("input[name='modeType']:checked").val();
		if (modeType == 'C') {
			$('#payModeId').hide();
			$('#complaintId').show();
		} else if (modeType == 'P') {
			$('#payModeId').show();
			$('#complaintId').hide();
			$('#complaint_subtype_Id').hide();
			$('#complaint_type_id').val('0');
		}
		
	});
});

$(function() {
	$('#complaint_type_id').change(function () {
		var typeId= $("#complaint_type_id").val();
		
		var requestData = {
				"typeId":typeId
		};
		
		$('#complaintSubType_id').html('');
		$('#complaintSubType_id').append($("<option></option>").attr("value","0").text('Select Complaint SubType'));
		var result=__doAjaxRequest("WorkflowHome.html?complaintsubtype",'POST',requestData,false,'json');
		
		 $.each(result, function(index, value) {
			 $('#complaintSubType_id')
	         .append($("<option></option>")
	         .attr("value",value[0])
	         .text(value[1]));
		});
		 $('#complaint_subtype_Id').show();
	});
});


$(function() {
	 //set initial state.
    $('#isComplaintId').val($(this).is(':checked'));
    
	 $('#isComplaintId').change(function () {
		
		 var checked = $(this).is(':checked');
		 if (checked == true) {
			 $('#complaintId').show(); 
			 $('#complaint_subtype_Id').show(); 
		 } else {
			 $('#complaintId').hide(); 
			 $('#complaint_subtype_Id').hide(); 
		 }
		 
		});
	
});

function populateSubType(subTypeList) {
	
	alert('in lentgh='+subTypeList.length);
	 $.each(subTypeList, function(index, value) {
		 $('#complaintSubType_id')
         .append($("<option></option>")
         .attr("value",value[0])
         .text(value[1]));
		 
		 alert('in='+value[0]+','+value[1]);
	});
}



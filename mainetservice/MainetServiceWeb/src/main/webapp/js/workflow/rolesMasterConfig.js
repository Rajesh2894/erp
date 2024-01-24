$(document).ready(function() {
	$('.fancybox').fancybox();
	if($('#mode').val() == 'VIEW'){
		$('#eventMasterFormId input').prop('disabled', true);
		$('#eventMasterFormId select').prop('disabled', true);
		$('#eventMasterFormId textarea').prop('disabled', true);
		$('#eventMasterFormId radio').prop('disabled', true);
		$('#moveright').prop('disabled', true);
		$('#moverightall').prop('disabled', true);
		$('#moveleft').prop('disabled', true);
		$('#moveleftall').prop('disabled', true);
		$('#smServiceName').prop('disabled', true).removeClass('mandColorClass');
		$('.back-btn').prop('disabled', false);
	}
});


//Event on selection of Department and populate Services by department
$(function() {
	$("#departmentId").change(function(){
		var requestData = {
				"deptId":$('#departmentId').val()
		}
		
		var result=__doAjaxRequest("EventMaster.html?services",'post',requestData,false,'json');
		$('#serviceId').html('');
		$('#serviceId').append($("<option></option>").attr("value","0").text(getLocalMessage('workflow.form.select.service')));
		$.each(result, function(index, value) {
			$('#serviceId')
			.append($("<option></option>")
					.attr("value",value[0])
					.text(value[1]));
		});
		$(".chosen-select-no-results").trigger("chosen:updated");
		$('#eventsForm').trigger('reloadGrid');
	});
	
});


function validateEventMasterData(errorList,i) {
	
	 var eventNameTempId = $.trim($("#eventNameId_"+i).val());
	 var eventDescTempId = $.trim($("#eventDescId_"+i).val());
	 var servceURLTempId = $.trim($("#servceURLId_"+i).val());
	 if(eventNameTempId =="" ){
		 errorList.push(getLocalMessage('workflow.eventName.not.empty'));
	 }
	 if(eventDescTempId == ""){
		 errorList.push(getLocalMessage('workflow.eventName.desc.not.empty'));
	 }
	 if(servceURLTempId==""){
		 errorList.push(getLocalMessage('workflow.serviceUrl.not.empty'));
	 }		
	
	 return errorList;
	
}


function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {
	$("#submitBtnId").click(function() {debugger;
	
		var mode = $('#mode').val();
		var errorList = [];
		$('#decisionMapId option').not(':selected').prop('selected','selected');
		errorList  = formSubmitValidation(errorList);
		
		if(errorList==0) {			     
			   var form = document.getElementById('roleMasterFormId');
	           var formData = $(form).serialize();
	           
	           if(mode == 'CREATE'){
	        	   var resultCheck=__doAjaxRequest("RolesMaster.html?existServiceRole",'post',formData,'');
				    if(resultCheck == 'true'){ 	     
					     var result=__doAjaxRequest("RolesMaster.html?create",'post',formData,false);	
					     if (result == 'true') {
								displayMessageOnSubmit();
							}else{
								displayMessageOnError(result);
							}
				       }else{
				    	
				    	   
				    	    var	errMsgDiv		=	'.msg-dialog-box';
				    		var message='';
				    		var cls = 'Close';
				    		
				    		if(resultCheck == 'false'){
				    			
				    			message	+='<p class=\"text-blue-2 text-center padding-10\">One or more of the Selected events already exist for this service.</p>';
				    			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
				    			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
				    			' onclick="closeMsg()"/>'+	
				    			'</div>';
				
				    		}else{
				    			
				    			
				    			message	+='<p class=\"text-blue-2 text-center padding-10\">Some Internal Problem. Please try again.</p>';
				    			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
				    			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
				    			' onclick="closeMsg()"/>'+	
				    			'</div>';
		
				    		}
				    		
				    		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
				    		$(errMsgDiv).html(message);
				    		$(errMsgDiv).show();
				    		$('#btnNo').focus();
				    		showModalBoxWithoutClose(errMsgDiv);
				    	
				       }
	           	}else{
	           		var response=__doAjaxRequest("RolesMaster.html?update",'post',formData,'');
	           		if (response == true) {
						displayMessageOnSubmit();
					}else{
						 errorList.push(getLocalMessage('workflow.eventMaster.removed.event.validation'));
					}
	           	}
		      }

		 if(errorList.length > 0){ 
			showRoleMstError(errorList);
			return false;			
		 }		
	});
	
});


function displayMessageOnSubmit(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'OK';
	var msg = 'Form submitted successfully.';
	
	message	+='<h4 class=\"text-info padding-10 padding-bottom-0 text-center\">'+msg+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-primary\'    '+ 
	' onclick="refreshWorkflowPage()"/>'+	
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}


function refreshWorkflowPage () {
	$.fancybox.close();
	window.location.href='RolesMaster.html';
}


function closeMsg(){
	$.fancybox.close();
}

function formSubmitValidation(errorList) {
	var validatedecisionId=$.trim($('#decisionMapId').val());
	
	 if(validatedecisionId == "" || validatedecisionId =='0'){
		 errorList.push(getLocalMessage('Please Select at Least one decision'));
	 }
	return errorList;
}


function showSaveResultBox(redirectUrl) {

	var messageText ='Data Saved Successfully!';
	var message='';
	var cls = getLocalMessage('eip.page.process');
	
	if(redirectUrl=="" || redirectUrl==null){
	
		message	+='<p class=\"text-blue-2 text-center padding-10\">'+ messageText+'</p>';
		message	+='<div class=\'class="text-center padding-bottom-10"\'>'+'</div>';
		
	}
	else{
    
	 message	+='<p class=\"text-blue-2 text-center padding-15\">'+ messageText+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-danger\'    '+ 
	' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
	'</div>';
	}
	
		
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
   if(redirectUrl=="" || redirectUrl==null){
	      showModalBox(errMsgDiv);
	}else{
		  showModalBoxWithoutClose(errMsgDiv);
	}
	
	return false;
}


function move_list_items(sourceid, destinationid)
{
  $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
}

function move_list_items_all(sourceid, destinationid)
{
	if($("#exf1").val()==""){
		 $("#"+sourceid+" option").appendTo("#"+destinationid);
	}
 
}


$(function() {
	$('#exf1').instaFilta({
	    scope: '#ex1'
	});
});


function showRoleMstError(errorList){	
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('#errorDivId').html(errMsg);
	$('#errorDivId').show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
}



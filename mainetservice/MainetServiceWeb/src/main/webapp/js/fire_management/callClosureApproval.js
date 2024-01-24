/**
 * 
 */
function saveCallClosureApprovalData(element) {
	
	var errorList = [];	
	//$.fancybox.close();
	if($("#callRegClosureRemark").val()==""){
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.remark"));
		 displayErrorsOnPage(errorList);
	}
	else{
	 var divName = '.content';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveFireCallClosureApproval', 'POST',requestData, false,'json');		
	 if(object.error != null && object.error != 0  ){	
		 $.each(object.error, function(key, value){
			    $.each(value, function(key, value){
			    	if(value != null && value != ''){				    		
			    		errorList.push(value);
			    	}				        
			    });
			});
		 displayErrorsOnPage(errorList);
	 }else{
		if(object.FireWfStatus =='REJECTED'){
			 showBoxForApproval(getLocalMessage('FireCallRegisterDTO.para.rejection'));
		 }else if(object.FireWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('FireCallRegisterDTO.para.sendBack'));
		 }else if(object.FireWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('FireCallRegisterDTO.para.sendBack'));
		 }else{
			 showBoxForApproval(getLocalMessage("FireCallRegisterDTO.form.success.msg"));
		 }
	 } 
   }
}


function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage("FireCallRegister.form.proceed");
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}












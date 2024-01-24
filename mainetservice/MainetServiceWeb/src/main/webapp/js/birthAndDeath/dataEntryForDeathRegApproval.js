$(document).ready(function() {
	prepareDateTag();
	
});


function saveDeathApprovalData(element) {
    
	var errorList = [];	
	//$.fancybox.close();
	/*if($("#deathRegremark").val()==""){
		errorList.push("Please enter the remark")
		 displayErrorsOnPage(errorList);
	}*/
	//else{
	 var divName = '.content-page';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveDeathRegApproval', 'POST',requestData, false,'json');		
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
		if(object.DeathWfStatus =='REJECTED'){
			showBoxForApproval(getLocalMessage("TbDeathregDTO.submit.reject"));
		 }else if(object.DeathWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
		 }else if(object.DeathWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
		 }else{
			 showBoxForApproval(getLocalMessage("TbDeathregDTO.submit.approve"));
		 }
	 } 
   //}
}
function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Proceed';
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

$(document).ready(function() {
    var end = new Date();
    end.setFullYear(2016);
    $("#drDod").datepicker({
        dateFormat : 'dd/mm/yy',
        changeMonth : true,
         changeYear: true,
        yearRange: "-200:+200",
        maxDate : new Date(end.getFullYear(), 11, 31)
    });
});
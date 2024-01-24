$(document).ready(function() {
	
	prepareDateTag();
	$("#birthRegDraftDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function saveIssuanceDeathCertificateApprovalData(element) {
	var errorList = [];	
	//$.fancybox.close();
	if($("#deathRegremark").val()==""){
		errorList.push("Please enter the remark")
		 displayErrorsOnPage(errorList);
	}
	else{
	 var divName = '.content-page';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveIssuanceDeathCertificateApproval', 'POST',requestData, false,'json');		
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
		if(object.BirthWfStatus =='REJECTED'){
			showBoxForApproval(getLocalMessage("TbDeathregDTO.submit.reject"));
		 }else if(object.BirthWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
		 }else if(object.BirthWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
		 }else{
			 showBoxForApproval(getLocalMessage("bnd.approval.msg1") + object.certificateNo + " " + getLocalMessage("bnd.approval.msg2"));
		 }
	 } 
   }
}
function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage('TbDeathregDTO.form.proceed');
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


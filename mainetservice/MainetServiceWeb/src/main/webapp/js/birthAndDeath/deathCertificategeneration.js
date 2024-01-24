$(document).ready(function() {
	prepareDateTag();
});

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



function CertificateGeneration() {
	
	var divName = '.pagediv';
	 var requestData = {};
	 var object = __doAjaxRequest('DeathCertificateGeneration.html?finalSaveAndCertificategeneration', 'POST',requestData, false,'json');		
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
		 showBoxForApproval(getLocalMessage("bnd.approval.msg1") + object.certificateNo + " " + getLocalMessage("bnd.approval.msg2"));
	 }
	
}



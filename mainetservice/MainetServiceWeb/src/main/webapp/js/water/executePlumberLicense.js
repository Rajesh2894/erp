 function updatedPlumberLicenseExecutionDetailByDept(element){
	return saveOrUpdateForm(element,"Plumber License Execute Successfully! ", 'ExecutePlumberLicense.html?printPlumberLicense', 'saveform');
 }
 
 function showSaveResultBox(returnData, message,redirectUrl) {
 	var messageText ='Do you want to Print Plumber License Report';
	var message='';
	var cls = getLocalMessage('eip.page.process');
	var msgText1 = getLocalMessage("Yes");
	var msgText2 = getLocalMessage("No");
	if(redirectUrl=="" || redirectUrl==null){
		message	+='<p class=\"text-blue-2 text-center padding-10\">'+ messageText+'</p>';
		message	+='<div class=\'class="text-center padding-bottom-10"\'>'+'</div>';
			
	}else{
	    message	+='<p class="text-blue-2 text-center padding-15">'+ messageText +'</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<br/><input type=\'button\' value=\''+msgText1+'\'  id=\'btnNo\' class=\' btn btn-success \'    '+ 
		' onclick="printPlumberLicense(\''+redirectUrl+'\')"/>&nbsp;&nbsp;&nbsp<input type=\'button\' value=\''+msgText2+'\'  id=\'btnNo1\' class=\' btn btn-danger \'    '+ 
		' onclick="closePlumberLicensePrintBox()"/>'+	
		'</div>&nbsp;&nbsp;&nbsp';
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
 
 function printPlumberLicense(element){
	 var  URL =element;
	 var data = {};
	 var returnData=__doAjaxRequest(URL,'post',data,false,'html');
	 $('#plumExecuteDiv').html(returnData);
	 $.fancybox.close();
 }
 
 function closePlumberLicensePrintBox(){
		$.fancybox.close();
 }

 function checkedExecutionBox(){
	$("#executePlmLicId").prop("checked", true)
 }

 $('#executePlmLicId').change(function() {
	$("#executionDate").val("");
 });
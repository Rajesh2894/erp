function searchYearEndProcessFormData(obj) {
	$('.error-div').hide();
	var errorList = [];
	var faYearid = $('#faYearid').val();
	if (faYearid == '' || faYearid == null) {
		errorList.push('Please select financial year');
	}
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    		
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
    }
	
		if (errorList.length == 0) {
			
			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "AccountYearEndProcess.html?getAccountYearEndProcessList";

			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			
			if (returnData == 'Y') {
				var errorList = [];
				
				errorList.push(getLocalMessage("account.norecord.criteria"));
				
				if(errorList.length>0){
			    	
			    	var errorMsg = '<ul>';
			    	$.each(errorList, function(index){
			    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			    		
			    	});
			    	errorMsg +='</ul>';
			    	
			    	$('#errorId').html(errorMsg);
			    	$('#errorDivId').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');		    	
			    }
			} else{
			var divName = ".content";
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			return false;	
			}
	}
  
};

var tempObj;
function saveLeveledData(obj){
	 tempObj=obj;
	showConfirmBoxSave();
	/*var errorList = [];
	var url = "AccountYearEndProcess.html?create";
	var requestData = null; 
	var status = __doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
	if (status != false) {
		if(status == 'Y') {
			showConfirmBox();
		}else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	} else {
		displayErrorsPage(errorList);
	}*/
}

function showConfirmBoxSave(){
	
	  var auth=$("#modeCheckId").val();
	  var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
	    //if(auth=='Auth')
		 //saveorAproveMsg="Approve";
	 
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\">'+ ""+saveorAproveMsg+""+ '</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>  '+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg(){
	
	var errorList = [];
	var url = "AccountYearEndProcess.html?create";
	var requestData = null; 
	var status = __doAjaxRequestForSave(url, 'post', requestData, false,'', tempObj);
	if (status != false) {
		if(status == 'Y') {
			showConfirmBox();
		}else {
			$(".content-page").html(status);
			$(".content-page").show();
			closeConfirmBoxForm();
		}
	} else {
		displayErrorsPage(errorList);
	}
}


function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function redirectToDishonorHomePage () {
	//$.fancybox.close();
	window.location.href='AccountYearEndProcess.html';
}

function showPopUpMsg(childDialog){
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}
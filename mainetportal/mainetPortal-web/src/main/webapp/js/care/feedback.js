$(document).ready(function() {
	$('.fancybox').fancybox();
	var star = $("#hiddenFeedbackRate").val();
	if(star !='' || star !=null){
		$('#rating-input').val(star);
	} 
	$("textarea").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
	
	function submitComplaintFeedbck() {
		var errorList=[];
		
		var ratinginput=$('#rating-input').val();
		var ratingcontent=$('#rating-content').val();
		var requestNo=$('#hiddenTokenNumber').val();
		var feedbackId=$("#hiddenFeedbackId").val();
		
		if (ratinginput == undefined || ratinginput=='0'|| ratinginput == "") {
			errorList.push('Please select rate us Marking for feedback.');
		} 
		if (ratingcontent==''||ratingcontent == undefined) {
			errorList.push('Please enter tell us more as feedback.');
		} 
		
		
		if (errorList.length==0) {
			 var submitfeedback = "grievance.html?saveFeedbackDetails";
			 var requestData ='requestNo='+requestNo+'&ratinginput='+ratinginput+'&ratingcontent='+ratingcontent+'&feedbackId='+feedbackId;
			 var response = __doAjaxRequest(submitfeedback,'post',requestData,false,'html');
			 if(!$.isEmptyObject(response)){
				 displayMessageOnSubmit(response);
			 }
			} else {
				displayErrorsOnPage (errorList);
				$('html,body').animate({ scrollTop: 0 }, 'slow');
			}
		return true;				
		
	}

	function displayMessageOnError(Msg){
		
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';	
		message	+='<p>'+Msg+'</p>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}

	function displayMessageOnSubmit(Msg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		message	+='<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+Msg+'</h4>';
		 message	+='<div class="text-center">'+	
	     '<button type="button" class=\'btn btn-blue-2\' onclick=\'javascript:openRelatedForm(\"grievance.html?getAllGrievanceRaisedByRegisteredCitizenView\",\"this\");\'>Close</button>'+
	     '</div>'; 
			 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}


	
	function displayErrorsOnPage(errorList) {

		var errMsg = '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		errMsg += '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('.error-div').html(errMsg);
		$(".error-div").show();
		
		return false;
	}
	
});
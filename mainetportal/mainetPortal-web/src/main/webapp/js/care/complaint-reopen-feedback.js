function reopenComplaint(obj){
	var errorList=[];
	var remark = $('#Remark').val();
	var mobileOTP = $('#mobileOTP').val();
	var reopeningReason = $('#reopeningReason').val();
	var emploginname = $('#emploginname').val();
	if(remark != undefined && remark != null)
		remark = remark.trim();
	if(remark == undefined || remark == null || remark == ""){
		errorList.push(getLocalMessage('care.validation.error.remark'));	
	}

	if(emploginname == 'NOUSER'){
		if(mobileOTP != undefined && mobileOTP != null)
			mobileOTP = mobileOTP.trim();
		if(mobileOTP == undefined || mobileOTP == null || mobileOTP == ""){
			errorList.push(getLocalMessage('care.validation.error.otpEmpty'));	
		}
	}

	if(reopeningReason != undefined && reopeningReason != null)
		reopeningReason = reopeningReason.trim();
	if(reopeningReason == undefined || reopeningReason == null || reopeningReason == "" || reopeningReason == "0"){
		errorList.push(getLocalMessage('care.validation.error.reopeningReason'));	
	}

	if(errorList.length == 0){
		if(!validateMobileOtp(mobileOTP)){
			errorList.push(getLocalMessage('care.validation.error.otpInvalid'));	
		}
	}

	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,"", "grievance.html", 'saveReopenDetails');
	}else{
		displayErrorsOnPage(errorList);
		$('html,body').animate({ scrollTop: 0 }, 'slow');
	}

}

function validateMobileOtp(mobileOTP){
	var isValidateMobileOtp = false;
	var validateMobileOtpUrl = "grievance.html?validateMobileOtp";
	var requestData = {mobileOTP:mobileOTP};
	var response = __doAjaxRequest(validateMobileOtpUrl,'post',requestData,false,'JSON');
	if(!$.isEmptyObject(response))
		isValidateMobileOtp = response;
	return response;
}


function submitComplaintFeedbck() {
	var errorList=[];

	var ratinginput=$('#rating-input').val();
	var ratingcontent=$('#feedback').val();
	var requestNo=$('#hiddenTokenNumber').val();
	var feedbackId=$("#hiddenFeedbackId").val();

	if (ratinginput == undefined || ratinginput=='0'|| ratinginput == "") {
		errorList.push(getLocalMessage('care.validation.error.feedbackRating'));
	} 

	if(ratingcontent != undefined && ratingcontent != null)
		ratingcontent = ratingcontent.trim();

	if (ratingcontent==''||ratingcontent == undefined) {
		errorList.push(getLocalMessage('care.validation.error.feedback'));
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

function displayMessageOnSubmit(msg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+msg+'</h4>';
	message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="window.location.href=\'grievance.html?grievanceStatus\'"/>'+
	'</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
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

function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

function downloadActionFile(id){
	var downloadAttachments = "attachments/ajax/downloadActionFile.html"; 
	var requestData = 'id='+id;
	var response = __doAjaxRequest(downloadAttachments,'get',requestData,false,'html');
}

function generateOTP(){
	var errorList=[];
	var	formName =	findClosestElementId($("#id_grievanceReopenFeedback"), 'form');
	var theForm	=	'#'+formName;
	var url	=	$(theForm).attr('action')+"?sendOTP";
	var data	=	"mobileNo="+$("#complainantMobileNo").val();
	var returnData	=	__doAjaxRequest(url, 'post', data , false,'json');
	if(returnData == 'Y'){
		$(".alert").hide();	
		$("#btnOTP").attr('disabled',true);
		$(".otp").html(getLocalMessage("care.otpNote"));	
		setTimeout(function(){
			enableSubmit(); //this will send request again and again;
		}, 180000);
	}
	if(returnData == 'N'){
		errorList.push(getLocalMessage("care.validation.error.mobileNumber"));	
		displayErrorsOnPage(errorList);
		$('html,body').animate({ scrollTop: 0 }, 'slow');
	}

}
function enableSubmit()
{
	$("#btnOTP").attr('disabled',false);
}
function printDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$("#btnDiv1, .table-pagination, .remove-btn, .paging-nav, tfoot tr.print-remove, .dataTables_length, .dataTables_filter, .dataTables_info, .dataTables_paginate").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
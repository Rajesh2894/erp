$(document).ready(function(){
	
$(".error-div").hide();
	


});

function resubmitComplaint(obj){
	var errorList=[];
	var remark = $('#YourReply').val();
	if(remark != undefined && remark != null)
		remark = remark.trim();
	if(remark == undefined || remark == null || remark == ""){
		errorList.push(getLocalMessage('care.validation.error.remark'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,"", "GrievanceResubmission.html", 'saveDetails');
	}else{
		displayErrorsOnPage(errorList);
	}

}

function closeOutErrBox (){
	$(".error-div").hide();
}

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
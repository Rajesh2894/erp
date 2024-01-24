function formSubmit(element){
	
	var errorList = [];
	errorList=validateFormDetails(errorList); 
	if (errorList.length == 0) {
	//return saveOrUpdateForm(element,"", 'SpecialNoticeGeneration.html', 'generateSpecialNotice');
	}
	else{
		 showErrorOnPage(errorList);
	}
}

function validateFormDetails(errorList){
	var word1=$("#assWard1").val();
	var location=$("#locId").val();
	var amount=$("#amount").val();
	var noOfDefaulterList= $("#noOfDefaulter").val();
	if((location=='0' || location == undefined) && (word1=='0' || word1 == undefined )){
		  errorList.push(getLocalMessage('property.selectLoc')); 
	 }	
	if(amount=='' || amount == undefined ){
		 errorList.push("Please Enter Amount");
	}
	if(noOfDefaulterList=='' || noOfDefaulterList == undefined ){
		 errorList.push("Please Enter No Of Defaulter");
	}	
	return errorList;
	
}

function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}
function submitAssessmentList(element){
	var errorList = [];
	errorList=validateReportFormDetails(errorList); 
	if (errorList.length == 0) {
	//return saveOrUpdateForm(element,"", 'SpecialNoticeGeneration.html', 'generateSpecialNotice');
	}
	else{
		 showErrorOnPage(errorList);
	}
}

function validateReportFormDetails(errorList){
	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){	
		
		var propNo=$("#propertyNo").val();
		var oldPropNo= $("#oldPropertyNo").val();
		var fromDate=$("#fromDate").val();
		var toDate= $("#toDate").val();
		
		if (propNo == "" && oldPropNo == ""){
			errorList.push(getLocalMessage("property.changeInAss"));
		}
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
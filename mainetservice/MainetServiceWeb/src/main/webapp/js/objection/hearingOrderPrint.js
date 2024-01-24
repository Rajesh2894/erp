
function SearchRecords(){
	
	var errorList = [];
	errorList=validateFormDetails(errorList); 
	if (errorList.length == 0) {

		/*var formAction	=	$('#HearingInspectionDatePrintForm').attr('action');
		var url=formAction+'?proceed';
		$('#HearingInspectionDatePrintForm').attr('action', url);
		$('#HearingInspectionDatePrintForm').submit();*/

			}
	else{
		 showErrorOnPage(errorList);
	}
	
}

function validateFormDetails(errorList){
	
	
	var objectionDeptId= $("#objectionDeptId").val();
	if(objectionDeptId=='0' || objectionDeptId==null || objectionDeptId==undefined){
		errorList.push(getLocalMessage("obj.validation.Department"));
	}
	
/*	var serviceId= $("#serviceId").val();
	if(serviceId=='0' || serviceId==null || serviceId==undefined){
		errorList.push(getLocalMessage("obj.validation.ObjectionType"));
	}*/
	
	var referenceNo =$("#objectionReferenceNumber").val();
	if(referenceNo=='' || referenceNo == undefined){			 
		 errorList.push("Please enter Property No/RTI No/Building Permission No"); 
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
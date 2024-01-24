$(document).ready(function(){
	

});

function resubmitComplaint(obj){
	var errorList=[];
	var remark = $('#Remark').val();
	if(remark != undefined && remark != null)
		remark = remark.trim();
	if(remark == undefined || remark == null || remark == ""){
		errorList.push(getLocalMessage('care.validator.remark'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,"", "GrievanceResubmission.html", 'resubmitGrievance');
	}else{
		displayErrorsOnPage(errorList);
	}

}
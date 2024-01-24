
$(document).ready(function(){

	//prepareDateTag();  // function to set Date and Time
	$('.datetimepicker').datetimepicker({
		dateFormat: 'dd/mm/yy',
		timeFormat: "hh:mm tt",
		changeMonth: true,
		changeYear: true,
		//yearRange: "0:+100",
		//maxDate:'now'
		minDate: 0
	});
	yearLength();
});

function yearLength(){
	
	var dateFields = $('.dateClass');
    dateFields.each(function () {
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}

function submitInspectionForm(element){
	var errorList = [];
	errorList = validateObjDetailForm(errorList);
	if (errorList.length == 0) {
		 return saveOrUpdateForm(element,"shedule save successfully!", 'AdminHome.html', 'saveShedule');
	} else {
		displayErrorMsgs(errorList);
	}
}


function validateObjDetailForm(errorList){
	

	var selection = $("input[name='objectionDetailsDto.schedulingSelection']:checked").val();
	if(selection==0 || selection=="" ||selection == undefined){
	errorList.push(getLocalMessage("obj.validation.SchedulingSelection"));
	}
	var inspectionDate =  $("#inspectionDate").val();	
	if(inspectionDate == '' || inspectionDate == undefined){
		errorList.push(getLocalMessage("obj.validation.InspectionDateAndTime"));
	}	

	return errorList;
}

function setDateTime(){

	
}

function displayErrorMsgs(errorList){

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
$(document).ready(function(){
	showSingleMultiple();
$('.lessthancurrdate').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	maxDate: '-0d',
	yearRange: "-100:-0",
});

});
function showSingleMultiple(){

	
	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){
		$('.PropDetail').show();
		/*$('.propLable').show();*/
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.usageType').hide();
		
		$('.sectionSeperator').hide();
		
		$('#assWard1').val('');
		$('#assWard2').val('');
		$('#locId').val('');
		$('#finYear').val('');		
		$('#fromDate').val('');
		$('#toDate').val('');
		
	}
	else if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		$('.PropDetail').hide();
		$('.wardZone').show();
		$('.Loc').show();
		$('.usageType').show();
		
		$('.sectionSeperator').show();
		$('.PropDetail').val('');
		$('#propertyNo').val('');
		$('#oldPropertyNo').val('');
		//$('#finacialYear').val('');
		$('#fromDate').val('');
		$('#toDate').val('');
	}else{
		$('.PropDetail').hide();
		$('.wardZone').hide();
		$('.Loc').hide();
		/*$('.propLable').hide();*/
		$('.usageType').hide();
		$('.sectionSeperator').hide();
		
	}
	
}

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
		 if(fromDate=='' || fromDate == undefined){
			  errorList.push(getLocalMessage('property.AssessmentList.fromDate')); 
		 } 
		 if(toDate=='' || toDate == undefined){
			  errorList.push(getLocalMessage('property.AssessmentList.toDate')); 
		 } 
		
	}

	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		var word1=$("#assWard1").val();
		
		var fromDate=$("#fromDate").val();
		var toDate= $("#toDate").val();
	
		if(word1=='0' || word1 == undefined){
			  errorList.push(getLocalMessage('property.selectLoc')); 
		}	
		if(fromDate=='' || fromDate == undefined){
			  errorList.push(getLocalMessage('property.AssessmentList.fromDate')); 
		 } 
		if(toDate=='' || toDate == undefined){
			  errorList.push(getLocalMessage('property.AssessmentList.toDate')); 
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
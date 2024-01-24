$(document).ready(function() {

						showSingleMultiple();
						
					/*	$('#datatables').DataTable({
				    		"oLanguage": { "sSearch": "" } ,
				    		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
				    	    "iDisplayLength" : 5, 
				    	    "bInfo" : true,
				    	    "lengthChange": true,
				    	    "bPaginate": true,
				    	    "bFilter": true
				    	    });
						
						$('#selectall').click(function(event) {
							if (this.checked) {
								$('.checkall').each(function() {
									this.checked = true;
								});
							} else {
								$('.checkall').each(function() {
									this.checked = false;

								});
							}
						});*/
				});




function showSingleMultiple(){
	
	$('#selectall').prop('checked', true);
	$('.checkall').each(function() {
		this.checked = true;
	});
	
	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){
		$('.PropDetail').show();
		/*$('.propLable').show();*/
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.usageType').hide();
		$('.financialYear').hide();
		$('.sectionSeperator').hide();
		
		$('#assWard1').val('');
		$('#assWard2').val('');
		$('#locId').val('');
		$('#finYear').val('');

	}
	else if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		$('.PropDetail').hide();
		$('.wardZone').show();
		$('.Loc').show();
		$('.usageType').show();
		$('.financialYear').show();
		$('.sectionSeperator').show();
		$('.PropDetail').val('');
		$('#propertyNo').val('');
		$('#oldPropertyNo').val('');
		$('#finacialYear').val('');
	}else{
		$('.PropDetail').hide();
		$('.wardZone').hide();
		$('.Loc').hide();
		/*$('.propLable').hide();*/
		$('.usageType').hide();
		$('.financialYear').hide();
		$('.sectionSeperator').hide();
	}
	
}
$('#genNotCheck').change(function () {
	if($('#genNotCheck').prop('checked', false))
	{
	$("#checkValue").val("N"); 
	}
	else if($('#genNotCheck').prop('checked', true))
	{
	$("#checkValue").val("Y"); 
	}
});

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



function formSubmit(element){
	
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

	/*var reportName=$("reportName").val();
	if(reportName=="" || reportName==undefined || reportName=='0' ){
		errorList.push(getLocalMessage('property.report.SelectReportName'));
	}
	
	var reportType = $('input[name="specialNotGenSearchDto.noticeType:checked').val();
	if(reportType==0 || reportType=="" || reportType== undefined){
	errorList.push(getLocalMessage('property.report.SelectReportType'));
	}*/
	
	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){	
		
		var propNo=$("#propertyNo").val();
		var oldPropNo= $("#oldPropertyNo").val();
		var financialYear= $("#finacialYear").val();
		if (propNo == "" && oldPropNo == ""){
			errorList.push(getLocalMessage("property.changeInAss"));
		}		
		if(financialYear=="" || financialYear==undefined || financialYear=='0' ){
			errorList.push(getLocalMessage('property.report.finYear'));
		}
	}

	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		var word1=$("#assWard1").val();
		var location=$("#locId").val();
		var financialYear= $("#finYear").val();
		
		if((location=='0' || location == undefined) && (word1=='0' || word1 == undefined )){
			  errorList.push(getLocalMessage('property.selectLoc')); 
		 }	
		if(financialYear=="" ||financialYear=='0'|| financialYear==undefined ){
			errorList.push(getLocalMessage('property.report.finYear'));
		}
	}
	return errorList;
	
}
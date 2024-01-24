$(document).ready(function() {
					
						$('#datatables').DataTable({
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
						});
						
});

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
	var selection = $("input[name='objectionDetailsDto.schedulingSelection']:checked").val();
	if(selection==0 || selection=="" ||selection == undefined){
	errorList.push(getLocalMessage("obj.validation.Hearing/Inspection"));
	}
	var selection = $("input[name='objectionDetailsDto.inspectionType']:checked").val();
	if(selection==0 || selection=="" ||selection == undefined){
	errorList.push(getLocalMessage("obj.validation.InspectionType"));
	}
	
	var objectionDeptId= $("#objectionDeptId").val();
	if(objectionDeptId=='0' || objectionDeptId==null || objectionDeptId==undefined){
		errorList.push(getLocalMessage("obj.validation.Department"));
	}
	
	/*var serviceId= $("#serviceId").val();
	if(serviceId=='0' || serviceId==null || serviceId==undefined){
		errorList.push(getLocalMessage("obj.validation.ObjectionType"));
	}*/
	
	var objectionNumber =$("#objectionNumber").val();

	if(objectionNumber=='' || objectionNumber == undefined){			 
		 errorList.push(getLocalMessage("obj.validation.ObjectionNumber")); 
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
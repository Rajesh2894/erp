$(document).ready(function() {
	
	/* $('#codDwzid1').val('-1');*/

});

function resetDEVForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#dataEntryVerificationReport").validate().resetForm();
}

function saveDEVForm(obj) {
	
	var errorList = [];
	/*var Zone = $('#codDwzid1').val();
	var Ward = $("#codDwzid2").val();
	if (Zone == 0) {
		errorList.push(getLocalMessage('water.report.validation.zone'));
	}
	if (Ward == 0 && Zone != -1) {
		errorList.push(getLocalMessage('water.report.validation.ward'));
	}
	
	setting value as zero if user select all
	if(Zone == -1) {
		Zone=0; 
	}
	if(Ward == -1) {
		Ward=0; 
	}
	
	used if all levels are not available
	
	if(Ward == 0 &&  Zone == -1){
		Ward=0; 
		}
		
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {*/
		
		var divName = '.content-page';
		$("#errorDiv").hide();
		  var formName = findClosestElementId(obj, 'form'); 
		  var theForm ='#' + formName;
		  var requestData = __serializeForm(theForm); 
		/*var requestData = '&Zone=' + Zone + '&Ward=' + Ward;*/
		var URL = 'DataEntryVerificationReport.html?GetWardZoneWiseDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		
		if(returnData == "Fail"){
			errorList.push(getLocalMessage('water.data.entry.report.validation'));
			displayErrorsOnPage(errorList);
		}else{
			window.open(returnData,'_blank' );
		}
	}






$(document).ready(function() {


	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});
	

});


	
function searchForm(obj) {

	var errorList = [];
	var fireStation = $('#fireStation').val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var date=new Date();
	
	//Defect #158117
	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("CallDetailsReportDTO.validation.fromDate"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("CallDetailsReportDTO.validation.toDate"));
	}
	if (fireStation == null || fireStation == 0) {
		errorList.push(getLocalMessage("CallDetailsReportDTO.validation.fire.station"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		 //Defect #158117
		if (eDate > sDate) {
			errorList.push(getLocalMessage("CallDetailsReportDTO.validation.date.1"));
		}
		if (sDate > date) {
			errorList.push(getLocalMessage("CallDetailsReportDTO.validation.date.2"));
		}

		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			
		}
		else {
			var reqData= 'fireStation=' + fireStation + '&fromDate=' + fromDate +  '&toDate='+ toDate;
			var URL = 'CallDetails.html?GetCallDetails';
			var resData = __doAjaxRequest(URL, 'POST', reqData, false);
			if(resData == "false") {
				errorList.push(getLocalMessage("Please enter valid input"));
				displayErrorsOnPage(errorList);
				
			}
			else {
				window.open(resData,'_blank' );
			}
			return true;
		}
	} 

}


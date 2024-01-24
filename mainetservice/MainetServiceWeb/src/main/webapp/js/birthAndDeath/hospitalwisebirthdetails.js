/**
 * Author : Amit.kumar Created Date : 17 May, 2019
 */

$(document).ready(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maate : '-0d',
		changeYear : true,
	});
});


function viewReport(obj) {
	var errorList = [];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var date = new Date();
	
	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("From Date cannot be empty"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("To Date cannot be empty"));
	}
	
	
	if (errorList.length > 0) {
		checkDate(errorList);
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						//From script library
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));
	

		if (eDate >= sDate) {
			errorList.push(getLocalMessage("To Date cannot be less than From Date"));
		}
		if (sDate >= date) {
			errorList.push(getLocalMessage("To Date cannot be greater than Current  Date"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
		else {

		
			var reqData= 'fromDate=' + fromDate +  '&toDate='+ toDate;
			var URL = 'HospitalWiseBirthDetails.html?GetAllHospitalWiseBirthDetails';
			var resData = __doAjaxRequest(URL, 'POST', reqData, false);
			if(resData == "false") {
				errorList.push(getLocalMessage("Please enter valid input"));
				checkDate(errorList);
			}
			else {
				window.open(resData,'_blank' );
			}
			return true;
		}
	} 

}

function checkDate(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}






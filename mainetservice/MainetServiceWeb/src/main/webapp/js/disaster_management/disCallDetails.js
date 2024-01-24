$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});
	

});

function toCheck(element) {

	var errorList = [];
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var callType = $("#callType1").val();
	var subType=$("#callType2").val();
	var location = $("#location").val();
	var date=new Date();
	
	if (callType == null || callType == 0) {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.complaint.type"));
	}
	if (subType == null || subType == 0) {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.complaint.sub.type"));
	}
	if (location == "") {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.location"));
	} 
	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.from.date"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("ComplainRegisterDTO.validation.to.date"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						//From script library
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (eDate > sDate) {
			errorList.push(getLocalMessage("ComplainRegisterDTO.validation.date.2"));
		}
		if (sDate >= date) {
			errorList.push(getLocalMessage("ComplainRegisterDTO.validation.date.3"));
		}

		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else {
			
			var reqData= 'callType=' + callType+ '&location=' + location +  '&fromDate='+ fromDate+ '&toDate='+toDate + '&subType=' + subType;
			var URL = 'DisCallDetails.html?GetDisCallDetails';
			var resData = __doAjaxRequest(URL, 'POST', reqData, false);
			if(resData == "false") {
				errorList.push(getLocalMessage("ComplainRegisterDTO.validation.valid.input"));
				displayErrorsOnPage(errorList);
			}
			else {
				window.open(resData,'_blank' );
			}
			return true;		
		}
	} 

}





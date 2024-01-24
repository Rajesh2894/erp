


function confirmToProceed(element) {
	
	var errorList = [];
	var location=$('#location').val();
	var fromDate=$('#fromDate').val();
	var toDate=$('#toDate').val();
	var dDate=new Date();
	
	if(location=="")
		{
		errorList.push(getLocalMessage("AllAttendedDisasterDTO.location.validation"));
		}
	
	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("AllAttendedDisasterDTO.fromDate.validation"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("AllAttendedDisasterDTO.toDate.validation"));
	}

	if (errorList.length > 0) {
		checkDate(errorList);
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));
	

		if (eDate >= sDate) {
			errorList.push(getLocalMessage("To Date cannot be less than From Date"));
		}
		if (sDate >= dDate) {
			errorList.push(getLocalMessage("To Date cannot be greater than current  Date"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
		else 
		{
		var requestData='location='+ location +'&fromDate=' + fromDate + '&toDate=' +toDate;
		var URL='AllAttendedDisaster.html?GetAllAttendedDisaster';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if(returnData == "f")
		{
			errorList.push('Invalid');
			checkDate(errorList);
		}
		else
		{
			window.open(returnData,'_blank' );
		}
	}
}
}

function checkDate(errorList)
{
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}


$(document).ready(function() {


	$(".datepicker").datepicker(
			{
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : '-0d'
			});

});





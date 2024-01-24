function confirmToProceed(element) {
	
	
	var errorList = [];
	var registrationUnit=$('#registrationUnit').val();
	var periodOfReportBy=$('#periodOfReportBy').val();
	var fromDate=$('#fromDate').val();
	var toDate=$('#toDate').val();
	var date=new Date();
	var sortOrder=$('#sortOrder').val();
	
	if(registrationUnit == -1){
		registrationUnit=0;
	}
	if(registrationUnit== null)
	{
	errorList.push(getLocalMessage("Registration Unit cannot be empty"));
	}
	if(periodOfReportBy== null || periodOfReportBy== 0)
	{
	errorList.push(getLocalMessage("Period Of Report By cannot be empty"));
	}
	if(sortOrder== null || sortOrder== 0)
	{
	errorList.push(getLocalMessage("Sort Order cannot be empty"));
	}
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
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var fDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var tDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (fDate >= tDate) {
			errorList.push(getLocalMessage("To Date cannot be less than From Date"));
		}
		if (tDate >= date) {
			errorList.push(getLocalMessage("To Date cannot be greater than Current  Date"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
		else 
		{
		var requestData='registrationUnit='+ registrationUnit +'&periodOfReportBy=' + periodOfReportBy +'&sortOrder=' + sortOrder
		+'&fromDate=' + fromDate + '&toDate=' +toDate;
		var URL='DeathRegister.html?GetDeathRegister';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if(returnData == "f")
		{
			errorList.push('Invalid');
			displayErrorsOnPage(errorList);
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


function resetForm() {
	window.open('DeathRegister.html', '_self');
}

$(document).ready(function() {
	
	$("#registrationUnit").val(-1);
	
	$(".datepicker").datepicker(
			{
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : '-0d'
			});

});

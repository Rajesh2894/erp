function confirmToProceed(element) {
	
	
	var errorList = [];
	var registrationUnit=$('#registrationUnit').val();
	var periodOfReportBy=$('#periodOfReportBy').val();
	var periodFrom=$('#periodFrom').val();
	var periodTo=$('#periodTo').val();
	var user=$('#user').val();
	var date=new Date();
	
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
	if(user== null)
	{
	errorList.push(getLocalMessage("user cannot be empty"));
	}
	
	if (periodFrom == null || periodFrom == "") {
		errorList.push(getLocalMessage("Period From cannot be empty"));
	}
	if (periodTo == null || periodTo == "") {
		errorList.push(getLocalMessage("Period To cannot be empty"));
	}
	if(user == -1){
		user=0;
	}
	if (errorList.length > 0) {
		checkDate(errorList);
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var fDate = new Date(periodFrom.replace(pattern, '$3-$2-$1'));
		var tDate = new Date(periodTo.replace(pattern, '$3-$2-$1'));

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
		var requestData='registrationUnit='+ registrationUnit +'&periodOfReportBy=' + periodOfReportBy +'&user=' + user +'&periodFrom=' + periodFrom + '&periodTo=' +periodTo;
		var URL='DeathRegister.html?GetDeathRegisterFemale';
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
	
	$("#user").val(-1);
	$("#registrationUnit").val(-1);
	
	
	$(".datepicker").datepicker(
			{
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : '-0d'
			});

});

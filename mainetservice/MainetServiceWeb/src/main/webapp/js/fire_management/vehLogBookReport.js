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
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var veNo = $("#veNo").val();
	var date=new Date();
	
	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("VehicleLogBookDTO.validation.select.from.date"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("VehicleLogBookDTO.validation.select.to.date"));
	}
	if (veNo == null || veNo == "") {
		veNo='X';
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);	
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));
		if (eDate > sDate) {
			errorList.push(getLocalMessage("VehicleLogBookDTO.validation.date1"));
		}
		if (sDate > date) {
			errorList.push(getLocalMessage("VehicleLogBookDTO.validation.date2"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);	
		}
		else {
			var reqData= 'fromDate=' + fromDate + '&toDate='+ toDate+ '&veNo='+ veNo;
			var URL = 'VehLogBookReport.html?GetVehLogBook';
			var resData = __doAjaxRequest(URL, 'POST', reqData, false);
			if(resData == "false") {
				errorList.push(getLocalMessage("VehicleLogBookDTO.validation.enter.valid.input"));
				displayErrorsOnPage(errorList);	
			}
			else {
				window.open(resData,'_blank' );
			}
			return true;
		}
	} 

}

$(function(){
	$('#toDate').change(function(){
	var errorList = [];
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var date=new Date();
	var divName = '.content-page';
	
	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("VehicleLogBookDTO.validation.select.from.date"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("VehicleLogBookDTO.validation.select.to.date"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);	
	}
	else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));
		if (eDate > sDate) {
			errorList.push(getLocalMessage("VehicleLogBookDTO.validation.date1"));
		}
		if (sDate > date) {
			errorList.push(getLocalMessage("VehicleLogBookDTO.validation.date2"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);	
		}
		else {
			var reqData= 'fromDate=' + fromDate + '&toDate='+ toDate;
			var URL = 'VehLogBookReport.html?getVeNo';
			var returnData = __doAjaxRequest(URL, 'POST', reqData,false,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$("#fromDate").val(fromDate);
			$("#toDate").val(toDate);
		}
	} 

});
});
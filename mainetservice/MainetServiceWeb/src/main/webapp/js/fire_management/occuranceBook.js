$(document).ready(function() {
	$("#occuranceBookDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	/*Defect #152197*/
	fieldValidationInputGroup();
	$('#resetAllFields').on('click', function(){
		var fieldSelector = $('input, textarea');
		if(fieldSelector.hasClass('error')) {
			fieldSelector.next('label.error').css('display','none');
			$('.input-group').removeAttr('style');
		}
	});
});
/*Defect #152197*/
function fieldValidationInputGroup() {
	$(document).on("focusout",".input-group",function() {
		var validationCond1 = $(this).hasClass('has-error');
		var validationCond2 = $(this).children('label.error');
	    if(validationCond1 || validationCond2) {
			$(this).css('margin-bottom','20px');
		} else {
			$(this).removeAttr('style');
		}
	    if(!validationCond2.is(':visible')) {
	    	$(this).removeAttr('style');
	    }
	});
}

$(".datepicker").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true,
});

$(".timepicker").timepicker({
	// s dateFormat: 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
});

$("#time").timepicker({

});


$("#frmOccuranceBook").validate({
	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

function SearchOccuranceBookDetail() {
	var errorList = [];
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var date = new Date();
	var fireStation = $('#fireStation').val();
	
	//Defect #158115
	if(fireStation=="0"){
   	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("CallDetailsReportDTO.validation.fromDate"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("CallDetailsReportDTO.validation.toDate"));
	}
 }
	if (errorList.length > 0) {
		checkDate(errorList);
	}
	else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		//Defect #158115
		if (eDate > sDate) {
			errorList
					.push(getLocalMessage("CallDetailsReportDTO.validation.date.1"));
		}
		if (sDate >= date) {
			errorList
					.push(getLocalMessage("CallDetailsReportDTO.validation.date.2"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
	}
	if(errorList.length ==0){
	if (fromDate != '' || toDate != '' || fireStation != '') {
		var requestData = 'fromDate=' + fromDate + '&toDate=' + toDate
				+ '&fireStation=' + fireStation;
		var table = $('#occuranceBookDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(
				"OccuranceBook.html?searchOccurranceBook", 'POST', requestData,
				false, 'json');
		var occDtos = response;
		if (occDtos.length == 0) {
			errorList.push(getLocalMessage("OccuranceBookDTO.validation.record.not.found"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$

				.each(
						occDtos,
						function(index) {
							var obj = occDtos[index];
							var cmplntNo = obj.cmplntNo;
							let cmplntId = obj.cmplntId
							let date = getDateFormat(obj.date);
							let time = obj.time;
							let incidentDesc = obj.incidentDesc;
							let cpdFireStation = obj.fsDesc;

							result
									.push([
											'<div class="text-center">' + (index + 1) + '</div>',
											'<div class="text-center">' + date + '  ' + time + '</div>',
											'<div class="text-center">' + cmplntNo + '</div>',
											'<div class="text-center">' + incidentDesc + '</div>',
											'<div class="text-center">' + cpdFireStation + '</div>',
											'<div class="text-center">' 
													+ '<button type="button" class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyOccuranceBook(\''
													+ cmplntId
													+ '\',\'OccuranceBook.html\',\'occuranceBook\',\'A\',\''
													+ cmplntNo
													+ '\')"  title="Add"><i class="fa fa-plus-circle padding-right-5"></i></button>'
													+ '</div>' ]);

			
						});
		table.rows.add(result);
		table.draw();
	}
  }
	else {
		
		displayErrorsOnPage(errorList);
	}
}

function confirmToProceed(element) {
    
	var errorList = [];
	var date = $("#date").val();
	var time = $("#time").val();
	var incidentDesc = $("#incidentDesc").val();
	var operatorRemarks = $("#operatorRemarks").val();

	//Defect #158116
	if (date == null || date == "") {
		errorList.push(getLocalMessage("OccuranceBookDTO.validation.occurance.date"));
	}
	if (time == null || time == "") {
		errorList.push(getLocalMessage("OccuranceBookDTO.validation.occurance.time"));
	}
	if (incidentDesc == null || incidentDesc == "") {
		errorList.push(getLocalMessage("OccuranceBookDTO.validation.occurance.description"));
	}
	if (operatorRemarks == null || operatorRemarks == "") {
		errorList.push(getLocalMessage("OccuranceBookDTO.validation.remarks"));
	}
	
	validateDate(errorList);
	//validateTime(errorList);
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} 
	// errorList = validateComplainRegister(errorList);
	//if ($("#frmOccuranceBook").valid() == true) {
		else {
			
			return saveOrUpdateForm(element, "", 'OccuranceBook.html', 'saveform');	

		}

	}
		

function modifyOccuranceBook(cmplntId, formUrl, actionParam, mode, cmplntNo) {

	
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : cmplntId,
		"cmplntNo" : cmplntNo
	};

	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function openFormOcc(cmplntId, formUrl, actionParam, mode) {
	
	var divName = '.content-page';
	var cmplntNo=$("#cmplntNo").val();
	var requestData = {
		"mode" : mode,
		"id" : cmplntId,
		"cmplntNo" : cmplntNo

	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

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

function validateDate(errorList){
	
	var odate = $("#date").val();
	var otime = $("#time").val();
	var otime = $("#time").val();
	var date=new Date();
	var time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	var url = "OccuranceBook.html?validateDate";
	var requestData = "&cmplntId=" + $('#occcId').val() 
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');
	var comp = returnData.split(',');
	var oDate = moment(odate, "DD.MM.YYYY").toDate();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var comDate= new Date(comp[0].replace(pattern, '$3-$2-$1'));
	
	if(comDate > oDate)
	{
	errorList.push(getLocalMessage("OccuranceBookDTO.DateValid"));
	}
	//if(comDate.getTime() == oDate.getTime())
	//{

	if(     comDate.getFullYear() === oDate.getFullYear() &&
			comDate.getMonth() === oDate.getMonth() &&
			comDate.getDate() === oDate.getDate()){
		if(comp[1] > otime){
			errorList.push(getLocalMessage("OccuranceBookDTO.TimeValid"));
		}
	}
	
	//}
	if(oDate>date)
	{
	errorList.push(getLocalMessage("OccuranceBookDTO.occTimeAndDateValid"));
	}
	if(oDate.getDate == date)
		{
     if(otime > time)
     {
      errorList.push(getLocalMessage("Occurance Time should be less than Current Time"));
     }
  }
}


/*function validateDate(errorList){
    
	var url = "OccuranceBook.html?validateDate";
	var requestData = "inputDate=" + $("#date").val()+ "&cmplntId=" + $('#occcId').val() 
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');
	
	if(returnData=="true"){
	  //date is correct	
	}
	else{
		errorList.push(returnData);
	}
	
	
}*/


/*function validateTime(errorList){
   
	var url = "OccuranceBook.html?validateTime";
	var requestData = "inputTime=" + $("#time").val()+ "&cmplntId=" + $('#occcId').val() 
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');
	
	if(returnData=="true"){
	  //date is correct	
	}
	else{
		errorList.push(returnData);
	}

}*/


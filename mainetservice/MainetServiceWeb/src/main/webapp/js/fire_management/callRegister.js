/**
 */
$(document).ready(function() {

	$("#fireCallRegister").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	fieldValidationInputGroup(); //Defect #153509
	/*Defect #152216*/
	$('.timepicker, .timepicker1').attr("placeholder", "hh:mm"); //Defect #152195
	$('#resetAllFields').on('click', function(){
		$('form#frmFireCallRegister').find(':text:enabled, textarea:enabled, select:enabled').val('');
		$('select#fireStationsAttendCallList, select#callAttendEmployeeList').val('').trigger('chosen:updated');
	});
	
});

/*Defect #153509*/
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
/*Defect #152216*/
function fieldValidationInputGroup2() {
	var fieldValCond1 = $('.input-group').children('label.error');
	var fieldValCond2 = $('.input-group').parent().prev('label.required-control');
	if(fieldValCond1 && fieldValCond2) {
		$('.input-group').css('margin-bottom','20px');
	} else {
		$('.input-group').removeAttr('style');
	}
}

/*$('#date').change(
		function() {
			var date = $("#date").val();
			var requestData = {
				"date" : date
			};
			$("#nameVisitingOff").html("");
			var URL = "FireCallRegister.html?searchVehicle";
			var response = __doAjaxRequest(URL, 'POST', requestData, false,
					'json');
			$('#nameVisitingOff').append(
					$("<option></option>").attr("value", 0).attr("code", 0)
							.text("Select"));
			$.each(response, function(index) {
				var obj = response[index];
				$("#nameVisitingOff").append(
						$("<option></option>").attr("value", obj.assignVehicle)
								.text(obj.vehNoDesc));
			});
			$('#nameVisitingOff').trigger('chosen:updated');
		});*/

function saveDraftFireData(element) {
   
	var errorList = [];
	$("#status").val("D");
	var fireDraftId =$('#fireDraftId').val();
	var cpdFireStationList = $("#cpdFireStationList").val();
	var dutyOfficer = $("#dutyOfficer").val();
	var date = $("#date").val();
	var time = $("#time").val();
	var callerMobileNo = $("#callerMobileNo").val();
	var incidentDesc = $("#incidentDesc").val();
	var incidentLocation = $("#incidentLocation").val();
	var cpdCallType = $("#cpdCallType").val();
	var vehIntime = $("#vehicleInTime").val();
	var vehOuttime = $("#vehicleOutTime").val();
	var vehicleOutDate = $("#vehicleOutDate").val();
  //  var callerArea=$("callerArea").val();

	//Defect #158046
	if (date == null || date == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.date"));
	}
	/*if (time == null || time == "") {
		errorList.push(getLocalMessage("Please Enter Time"));
	}*/
	if (callerMobileNo == null || callerMobileNo == 0) {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.mobile.number"));
	}else if (callerMobileNo.length < 10) {
			errorList.push(getLocalMessage("FireCallRegisterDTO.validation.callerMobileNo"));
		}
		
	if (incidentDesc == null || incidentDesc == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.description"));
	}
	if (incidentLocation == null || incidentLocation == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.location"));
	}
	if (cpdCallType == null || cpdCallType == "" || cpdCallType == 0) {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.cpdCallType"));
	}
/*	if (callerArea == null || callerArea == "") {
		errorList.push(getLocalMessage("Please  select Caller Area"));
	}*/
	
	if (vehicleOutDate == null || vehicleOutDate == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.vehicleOutDate"));
	}
	
	if (cpdFireStationList == null || cpdFireStationList == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.fire.station"));
	}
	if (dutyOfficer == null || dutyOfficer == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.duty.officer"));
	}
	if((vehIntime!=null && vehIntime!="") && (vehOuttime!=null && vehOuttime!="")){
		if(vehIntime==vehOuttime){
			errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.vehicle.time.1"));
		}
	}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		
		if ($("#frmFireCallRegister").valid() == true) 
	
			var errorList = [];
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		} else {
			if ($("#frmFireCallRegister").valid() == true) {
				$('#fireDraftId').val();
				return saveOrUpdateForm(element, "", 'FireCallRegister.html',
						'saveform');
			} else {
			}
		}	
		
			return saveOrUpdateForm(element, "", 'FireCallRegister.html',
					'saveform');
		
			}
	}



var fileArray = [];
$(document).ready(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maate : '-0d',
		changeYear : true,
	});

	$(".timepicker").timepicker({
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	prepareDateTag();

	$("#frmFireCallRegister").validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	
	var timeFields = $('.timepicker');
	timeFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 5) {
			$(this).val(fieldValue.substr(0, 5));
		}
	});
	
	var lessthancurrdate = $('.lessthancurrdate');
	lessthancurrdate.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

});


function getFireCallRegister(id, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function confirmToProceed(element) {
	
	var errorList = [];
	$("#status").val("O");
	var date = $("#date").val();
	var time = $("#time").val();
	var callerMobileNo = $("#callerMobileNo").val();
	var incidentDesc = $("#incidentDesc").val();
	var incidentLocation = $("#incidentLocation").val();
	var cpdCallType = $("#cpdCallType").val();
	var cpdFireStationList = $("#cpdFireStationList").val();
	var dutyOfficer = $("#dutyOfficer").val();
	
	var vehIntime = $("#vehicleInTime").val();
	var vehOuttime = $("#vehicleOutTime").val();
	
	//time validation start
	//time
	var hmtime = time;  
	var a = hmtime.split(':'); 
	var timeMin = (+a[0]) * 60 + (+a[1]);
	
	//vehIntime
	var hmvehIntime = vehIntime;   
	var a = hmvehIntime.split(':'); 
	var vehIntimeMin = (+a[0]) * 60 + (+a[1]);
	
	//vehOuttime
	var hmvehOuttime = vehOuttime;   
	var a = hmvehOuttime.split(':'); 
	var vehOuttimeMin = (+a[0]) * 60 + (+a[1]);
	
	//Defect #158046
	if(timeMin != null && !(timeMin == "")) {
		if(vehIntimeMin != null && !(vehIntimeMin == "")) {
			if(vehIntimeMin < timeMin){
				errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.vehicle.time.2"));
			}
		}
		
		if(vehOuttimeMin != null && !(vehOuttimeMin == "")) {
			if(vehOuttimeMin < timeMin){
				errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.vehicle.time.3"));
			}
		}
	}
	//time validation end
	
	if (date == null || date == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.date"));
	}
	/*if (time == null || time == "") {
		errorList.push(getLocalMessage("Please Enter Time"));
	}*/
	if (callerMobileNo == null || callerMobileNo == 0) {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.mobile.number"));
	}else if (callerMobileNo.length < 10) {
			errorList.push(getLocalMessage("FireCallRegisterDTO.validation.callerMobileNo"));
		}
		
	if (incidentDesc == null || incidentDesc == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.description"));
	}
	if (incidentLocation == null || incidentLocation == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.location"));
	}
	if (cpdCallType == null || cpdCallType == "" || cpdCallType == 0) {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.cpdCallType"));
	}
	if (cpdFireStationList == null || cpdFireStationList == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.fire.station"));
	}
	if (dutyOfficer == null || dutyOfficer == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.duty.officer"));
	}
	if((vehIntime!=null && vehIntime!="") && (vehOuttime!=null && vehOuttime!="")){
		if(vehIntime==vehOuttime){
			errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.vehicle.time.1"));
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	
	errorList = validateFireCallRegister(errorList);
	if($("#frmFireCallRegister").valid() == true){
		return saveOrUpdateForm(element, "", 'FireCallRegister.html?fireCallRegisterAdd',
		'saveform');
	} else {
		
	}

}

function confirmToClosed(element) {
	var errorList = [];
	errorList = validateFireCallCloser(errorList);
	if(($("#frmFireCallRegister").valid() == true) && (errorList.length == 0)){
		$("#errorDiv").hide();
		$('#assignVehicle').prop("disabled",false);
		return saveOrUpdateForm(element, "", 'AdminHome.html',
		'saveform');
	} else {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		fieldValidationInputGroup2(); //Defect #152216
	}

}



function validateFireCallCloser(errorList){

	var date = $("#date").val();
	var occurDate = $("#occurDate").val();
	var occurNewDate=getDateFormat(occurDate);
	var occurTime = $("#occurTime").val(); 
	var arr=occurTime.split(":");
	var occurTimeNew=arr[0]+":"+arr[1];
	
	var operatorRemarks= $("#closerRemarks").val();
	var callAttendDate = $("#callAttendDate").val();
	var dutyOfficer=$("#dutyOfficer").val();
	var fireStationsAttendCallList=$("#fireStationsAttendCallList").val();
	var time=$("#time").val();
	var closerRemarks=$("#closerRemarks").val();
    var callAttendTime=$("#callAttendTime").val();
	var comDate = moment(date, "DD.MM.YYYY HH.mm").toDate();
	var closeDate=  moment(callAttendDate, "DD.MM.YYYY HH.mm").toDate();
	
	//Defect #158113
	if (callAttendDate == null || callAttendDate == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.validation.callattend.date"));
	}
	
	if (callAttendTime == null || callAttendTime == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.validation.call.attend.time"));
	}
	if (fireStationsAttendCallList == null || fireStationsAttendCallList == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.validation.station.name"));
	}
	
	if (closerRemarks == null || closerRemarks == "") {
		errorList.push(getLocalMessage("FireCallRegisterDTO.validation.remark"));
	}
	
	if(date!=null && callAttendDate!=null){
		if(callAttendDate<date){
			errorList.push(getLocalMessage("FireCallRegisterDTO.validation.date.1"));
		}else if(callAttendDate==date){
			if(callAttendTime<time){
				errorList.push(getLocalMessage("FireCallRegisterDTO.validation.time.1"));
			}
		}
	}
	
	if(callAttendDate!=null && occurNewDate!=null){
		if(callAttendDate<occurNewDate){
			errorList.push(getLocalMessage("FireCallRegisterDTO.validation.date.2"));
		}else if(callAttendDate==occurNewDate){
			if(callAttendTime<occurTimeNew){
				errorList.push(getLocalMessage("FireCallRegisterDTO.validation.time.2"));
			}
		}
	}
	
	/*if (closeDate.getTime() < comDate.getTime()) {
		errorList.push(getLocalMessage("Closer Date and Time should be greater than Call Date and Time"));
	}

	else if (closeDate < Date) {
		errorList.push(getLocalMessage("Closer Date and Time should be greater than last occurance Date and Time"));
	}
	else if (closeDate.getTime() == comDate.getTime()) {
		if (time >= callAttendTime) {
			errorList.push(getLocalMessage("Closer Time should be greater than Call Time"));
		}
	} else if (closeDate == date) {
		
		errorList.push(getLocalMessage("Closer Time should be greater than Call Occurance Time"));
	}*/
		
	return errorList;
}






function validateFireCallRegister(errorList) {

	var department = $("#department").val();
	var location = $("#location").val();

	var complaintType1 = $("#complaintType1").val();
	var complaintType2 = $("#complaintType2").val();
	var complaintType3 = $("#complaintType3").val();
	var complaintType4 = $("#complaintType4").val();
	var complaintType5 = $("#complaintType5").val();

	var codWard1 = $("#codWard1").val();
	var codWard2 = $("#codWard2").val();
	var codWard3 = $("#codWard3").val();
	var codWard4 = $("#codWard4").val();
	var codWard5 = $("#codWard5").val();
	
	var complainerName = $("#complainerName").val();
	var complainerMobile = $("#complainerMobile").val();
	var complainerAddress = $("#complainerAddress").val();
	var complaintDescription = $("#complaintDescription").val();

	if (department == "0" || department == null) {
		errorList.push($('label[for="department"]').text());
	}
	if (location == "0" || location == null) {
		errorList.push($('label[for="location"]').text());
	}

	if (complaintType1 != undefined)
		if (complaintType1 == "0" || complaintType1 == null) {
			errorList.push($('label[for="complaintType1"]').text());
		}
	if (complaintType2 != undefined)
		if (complaintType2 == "0" || complaintType2 == null) {
			errorList.push($('label[for="complaintType2"]').text());
		}
	if (complaintType3 != undefined)
		if (complaintType3 == "0" || complaintType3 == null) {
			errorList.push($('label[for="complaintType3"]').text());
		}
	if (complaintType4 != undefined)
		if (complaintType4 == "0" || complaintType4 == null) {
			errorList.push($('label[for="complaintType4"]').text());
		}
	if (complaintType5 != undefined)
		if (complaintType5 == "0" || complaintType5 == null) {
			errorList.push($('label[for="complaintType5"]').text());
		}

	if (codWard1 != undefined)
		if (codWard1 == "0" || codWard1 == null) {
			errorList.push($('label[for="codWard1"]').text());
		}
	if (codWard2 != undefined)
		if (codWard2 == "0" || codWard2 == null) {
			errorList.push($('label[for="codWard2"]').text());
		}
	if (codWard3 != undefined)
		if (codWard3 == "0" || codWard3 == null) {
			errorList.push($('label[for="codWard3"]').text());
		}
	if (codWard4 != undefined)
		if (codWard4 == "0" || codWard4 == null) {
			errorList.push($('label[for="codWard4"]').text());
		}
	if (codWard5 != undefined)
		if (codWard5 == "0" || codWard5 == null) {

		}

	if (complainerName == "" || complainerName == null) {
		errorList.push($('label[for="complainerName"]').text());
	}
	if (complainerMobile == "" || complainerMobile == null) {
		errorList.push($('label[for="complainerMobile"]').text());
	}
	if (complainerAddress == "" || complainerAddress == null) {
		errorList.push($('label[for="complainerAddress"]').text());
	}
	if (complaintDescription == "" || complaintDescription == null) {
		errorList.push($('label[for="complaintDescription"]').text());
	}

	return errorList;

}

function resetForm() {
	addFireCallRegister('FireCallRegister.html', 'ADD', 'A');
}

function getLable(lable) {
	return $('label[for=' + lable + ']').text();
}

function SearchDetails() {
	
	  var errorList = [];
	  var fromDate = $('#fromDate').val();
	  var toDate = $('#toDate').val();
	  var fireStation =$('#fireStation').val();
	  var cmplntNo =$('#cmplntNo').val();
	  var status = "D";
	  var date = new Date();
	 
	   if(cmplntNo==""){
	     if (fromDate == null || fromDate == "") {
			errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.from.date"));
		 }
	     if (toDate == null || toDate == "") {
				errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.to.date"));
			 }
		 
	     }
	 
   	if (errorList.length > 0) {
	 	checkDate(errorList);
	    }
	    else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

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
	    if (fromDate != '' || toDate != '' || fireStation !='' || cmplntNo!='') {
		var requestData = 'fromDate=' + fromDate + '&toDate=' + toDate + '&fireStation=' + fireStation +'&cmplntNo=' + cmplntNo  + '&status=' + status;
		var table = $('#fireCallRegister').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest('FireCallRegister.html?SearchData', 'POST',
				requestData, false, 'json');
		var fireCallRegisterdto = response;
		if (fireCallRegisterdto.length == 0) {
			errorList.push(getLocalMessage("record not found"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$.each(  fireCallRegisterdto,
						function(index) {
			
					var obj = fireCallRegisterdto[index];
					let date=getDateFormat(obj.date);
					let time = obj.time;
					let cmplntNo = obj.cmplntNo
					var incidentDesc = obj.incidentDesc;
					var dutyOfficer = obj.dutyOfficer;
					var cmplntId = obj.cmplntId;
					
				

					result
							.push([
									'<div class="text-center">' + (index + 1) + '</div>',
									'<div class="text-center">' + date + '  ' + time + '</div>',
									'<div class="text-center">' + cmplntNo + '</div>',
									'<div class="text-center">' + incidentDesc + '</div>',
									'<div class="text-center">' + dutyOfficer + '</div>',
									'<div class="text-center">' 
									+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyFireDetails(\''
									+ cmplntId
									+ '\',\'FireCallRegister.html\',\'edit\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
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

function modifyFireDetails(cmplntId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : cmplntId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}


$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maate : '-0d',
		changeYear : true,
	});
});


function search(){
	
	var errorList = [];
	var divName = '.content-page';
	var complainNo = $('.complainNo').val();
	var fireStation = replaceZero($('.fireStation').val());
	if(complainNo=="" && fireStation == undefined){
		errorList.push(getLocalMessage("FireCallRegisterDTO.validate.search"))
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		var requestData = {
				"complainNo" : complainNo,
				"fireStation" : fireStation
			};
			var ajaxResponse = doAjaxLoading('FireCallClosure.html?searchFireCallRegister', requestData,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
			$('.complainNo').val(complainNo);
			 $('.fireStation').val(fireStation == undefined ? "0": fireStation );
			
	}
	
}

function editCall(complainId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : complainId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function replaceZero(value){
	return value != 0 ? value : undefined;
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

function selectArea(element)
{
	var decision = $("input[id='callerArea']:checked").val();
	if(decision=="N")
	{
	$('#callForwarded1').hide();
	}
	else{
		$('#callForwarded1').show();
	}
}
/**
 * Author : Niraj.Kumar Created Date : 20 Aug, 2019
 */
$(document).ready(function() {
	var vehicleOutTime = $("#vehicleOutTime").val();
	var vehicleInTime = $("#vehicleInTime").val();
	if(vehicleOutTime!=undefined && vehicleOutTime != ""){
	var arr=vehicleOutTime.split(":");
	var vehicleOutTimeNew=arr[0]+":"+arr[1];
	$("#vehicleOutTime").val(vehicleOutTimeNew); 
	}
	if(vehicleInTime!=undefined && vehicleInTime!=""){
	var arr=vehicleInTime.split(":");
	var vehicleInTimeNew=arr[0]+":"+arr[1];
	$("#vehicleInTime").val(vehicleInTimeNew); 
	}
	
	prepareDateTag();
	$("#frmVehicleLogBookTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bPaginate": true,
		"bInfo" : true,
		"bFilter": true,
		"lengthChange" : true
		//"order": [[ 1, "desc" ]]
	});
	
	
	
	$('#fromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});
	$("#fromDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#toDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});

	$("#toDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});	
	
	$('#outDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});

	$("#outDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	
	$('#inDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});

	$("#inDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
});	
	
	
/*	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});
*/
	$(".timepicker").timepicker({
		// s dateFormat: 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#time").timepicker({

	});
	
	$("#frmVehicleLogBook").validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

//	prepareDateTag();


   function SearchLogBookDetails() {
          
		  var errorList = [];
		  
		  var fromDate = $('#fromDate').val();
		  var toDate = $('#toDate').val();
		  var date = new Date();
		  var veNo =$('#veNo').val();

		
		     if(veNo==""){
		     if (fromDate == null || fromDate == "") {
				errorList.push(getLocalMessage("vehiclelogbook.select.from.date"));
			 }
			 if (toDate == null || toDate == "") {
				errorList.push(getLocalMessage("vehiclelogbook.select.to.date"));
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
						.push(getLocalMessage("vehicle.logbook.validation.date1"));
			}
			if (sDate >= date) {
				errorList
						.push(getLocalMessage("vehicle.logbook.validation.date2"));
			}

			if (errorList.length > 0) {
				checkDate(errorList);
			}
		    }
		    if(errorList.length ==0){
		    if (fromDate != '' || toDate != '' || veNo !='') {
			var requestData = '&fromDate=' + fromDate + '&toDate=' + toDate + '&veNo=' + veNo;
			var table = $('#frmVehicleLogBookTable').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var response = __doAjaxRequest(
					'vehLogBook.html?searchLogBook', 'POST',
					requestData, false, 'json');
			var veLogBoookDtos = response;
			if (veLogBoookDtos.length == 0) {
				errorList.push(getLocalMessage("vehiclelogbook.record.not.found"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
			$
					.each(  veLogBoookDtos,
							function(index) {
						var obj = veLogBoookDtos[index];
						let veID = obj.veID
						var veNo = obj.veName;
						var driverEmpID = obj.driverName;
						let outDate = getDateFormat(obj.outDate);
						let vehicleOutTime = obj.vehicleOutTime;
						let inDate = getDateFormat(obj.inDate);
						let vehicleInTime = obj.vehicleInTime;

						result
								.push([
										'<div class="text-center">' + (index + 1) + '</div>',
										'<div class="text-center">' + veNo + '</div>',
										'<div class="text-center">' + driverEmpID + '</div>',
										'<div class="text-center">' + outDate + '</div>',
										'<div class="text-center">' + vehicleOutTime + '</div>',
										'<div class="text-center">' + inDate + '</div>',
										'<div class="text-center">' + vehicleInTime + '</div>',
										'<div class="text-center">' 
										+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyLogBook(\''
										+ veID
										+ '\',\'vehLogBook.html\',\'viewVLB\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
										+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyLogBook(\''
										+ veID
										+ '\',\'vehLogBook.html\',\'editVLB\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
										+ '</div>' ]);

					});
	table.rows.add(result);
	table.draw();
     }
		    } 
		    
	else {
	//	errorList.push(getLocalMessage("Select At least One Criteria"));
		displayErrorsOnPage(errorList);
}
}
	
	function modifyLogBook(veID, formUrl, actionParam, mode) {
        
		var divName = '.content-page';
		var requestData = {
			"mode" : mode,
			"id" : veID
		};
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html', false);
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

	 
function confirmToProceed(element,mode) {
	   	var errorList = [];
	   	var typeOfVehicle = $("#typeOfVehicle").val();
	   	if(typeOfVehicle=="0" || typeOfVehicle==null){
	   		errorList.push(getLocalMessage("vehiclelogbook.select.vehicle.type"));
	   	}
	    var veNo = $("#veNo").val();
		if (veNo == null || veNo == "" || veNo == 0) {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.select.vehicle.number"));
	  	}
		
	  	var driverName = $("#driverName").val();
	  	if (driverName == null || driverName == "") {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.select.driver.name"));
	  	}
	  	var outDate = $("#outDate").val();
	  	var inDate= $("#inDate").val();
		var vehicleOutTime = $("#vehicleOutTime").val();
	  	var vehicleInTime= $("#vehicleInTime").val();
	  	
	  	/*if(mode == 'E'){
	  		if( vehicleInTime == "" || vehicleInTime == null ){
	  			errorList.push(getLocalMessage("Please Enter vehicle In Time"));
	  		}
	  	}*/
	  	if ((outDate!="" && inDate!=""  && outDate==inDate )||(outDate!="" && inDate=="")){
		  	if (vehicleOutTime != "" && vehicleInTime != "") {
		  		if (vehicleInTime <= vehicleOutTime)
		  			errorList.push(getLocalMessage("vehicle.logbook.validation.vehicle.intime"));
		  	}
	  	}
	  	var vehicleJourneyFrom = $("#vehicleJourneyFrom").val();
	  	var dayStartMeterReading = $("#dayStartMeterReading").val();
	  	var dayEndMeterReading = $("#dayEndMeterReading").val();
	  	if(dayEndMeterReading != "" ){
			if (parseFloat(dayStartMeterReading) >= parseFloat(dayEndMeterReading)) {
				errorList.push(getLocalMessage("vehicle.logbook.validation.meter.reading1"));
			}
			}

	  	var dayVisitDescription = $("#dayVisitDescription").val();
	  	var reason = $("#reason").val();
	
	  	
	  /*	if (outDate==null || outDate=="") {
	  		errorList.push(getLocalMessage("vehicle.logbook.validation.enter.outdate"));
	  	}*/
	  	
	  	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var outDate = new Date(outDate.replace(pattern, '$3-$2-$1'));
		var inDate = new Date(inDate.replace(pattern, '$3-$2-$1'));
	    var date = new Date();
        
	    if(outDate > date){
	    	errorList.push(getLocalMessage("vehicle.logbook.validation.meter.reading1"));
	    	
	    }
	    
		if (inDate <  outDate) {
			errorList.push(getLocalMessage("vehicle.logbook.validation.date4"));
		}
		/*if(vehicleOutTime != null && vehicleInTime!=null){
			if(vehicleOutTime == vehicleInTime ){
				errorList.push("In Time and Out Time must be different");
			}
			
		}*/

	    /*if (veNo == null || veNo == "") {
	  		errorList.push(getLocalMessage("Please Enter Vehicle Number"));
	  	}
	  	if (driverName == null || driverName == "") {
	  		errorList.push(getLocalMessage("Please Enter Driver Name"));
	  	}*/

	  	/*if (vehicleOutTime == null || vehicleOutTime == "") {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.enter.outtime"));
	  	}
	  	if (vehicleJourneyFrom == null || vehicleJourneyFrom == "") {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.enter.vehicle.journey.from"));
	  	}
	  	if (dayStartMeterReading == null || dayStartMeterReading == "") {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.enter.start.meter.reading"));
	  	}
	  	if (dayVisitDescription == null || dayVisitDescription == "") {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.visit.description"));
	  	}
	  	if (reason == null || reason == "") {
	  		errorList.push(getLocalMessage("vehiclelogbook.validation.enter.reason"));
	  	}*/
	  	
	  	if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			checkDate(errorList);
			return false;
		} 
	  	else{
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var requestUrl = "vehLogBook.html?save";
		var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData,
				false, 'json');
		if (ajaxResponse == false) {
			errorList.push(getLocalMessage('vehicle.logbook.duplicate.exists'));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			checkDate(errorList);
			return false;
		} else {
			return saveOrUpdateForm(element, "", 'vehLogBook.html', 'saveform');
		}
	}
	 	
		}



function openForm(formUrl, actionParam) {
    
	var divName = '.content-page';
	var requestData = {

	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}	

function showVehicleRegNo() {
	
	$('#veNo').html('');
	var crtElementId = $("#typeOfVehicle").val();
	var requestUrl = "vehLogBook.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	$('#veNo').append(
			$("<option></option>").attr("value", 0).attr("code", 0).text(
					"select"));
	$.each(ajaxResponse, function(index, value) {
		$('#veNo').append(
				$("<option></option>").attr("value", index).attr("code", index)
						.text(value));
	});
	$('#veNo').trigger('chosen:updated');
}


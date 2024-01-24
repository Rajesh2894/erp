/**
 * ritesh.patil
 */
$('#waterTankerBookingForm').validate({
		   onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	       onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}});

   function getChecklist(element){	   
	   var errorList = [];
	   var fromDate = $("#fromDate").val();
	   var toDate =  $("#toDate").val();
	   if(fromDate != "" && toDate != ""){	   
		   var data = {
					"fromDate" :fromDate,
					"toDate":toDate
					};			   
		   var URL = "WaterTankerBooking.html?getValidationFromDateToDate";
		   var result=__doAjaxRequest(URL,'POST',data,false,'json');			   
		   if(result.UadLevelError != null){
		    	errorList.push(getLocalMessage('rnl.estate.booking.prop.date.great.date') +" "+ result.propName +" "+ getLocalMessage("rnl.with.in") +" "+ result.UadLevelError +" "+ getLocalMessage('rnl.from.days') +" " );				
		    }
		   if(result.days != null){
			   errorList.push(getLocalMessage('Maximum') +" "+result.days +" "+getLocalMessage('Days') +" "+ getLocalMessage('Booking Allowed for') +" "+result.propName );				
		   }
	   }
	   if(errorList.length  == 0 ){
		   var	formName =	findClosestElementId(element, 'form');
			var theForm	=	'#'+formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
		    var URL = "WaterTankerBooking.html?getCheckListAndCharges";
	         var returnData = __doAjaxRequestValidationAccor(element,URL, 'POST', requestData, false,'html');
	    	 if(returnData != false){
	    	     $('.content').html(returnData);
	    	     $('#toDate').attr("disabled", true) 
		    	   $('#fromDate').attr("disabled", true) 
	    	     getPropertyFromToTime(propId);
	    	 }
		   
	   }else{
		   showRLValidation(errorList);  
	   }	
	}
   
   /*function getChecklistAndCharges(element)
   {
   	     var errorList = [];
   	     errorList = validateApplicantInfo(errorList);
   	     errorList = validateBookingInfo(errorList);
   	     if (errorList.length == 0) {
   		    var	formName =	findClosestElementId(element, 'form');
   			var theForm	=	'#'+formName;
   			var requestData = {};
   			requestData = __serializeForm(theForm);
   		    var URL = bookinEstateUrl+"?getCheckListAndCharges";
   	        var returnData =__doAjaxRequest(URL,'POST',requestData, false);
   	        $('.content').html(returnData);
   		}else{
   	    	  showRLValidation(errorList);
   	    	  
   	      }
   				
   	}*/


/**
 * validate applicant info
 * @param errorList
 * @returns
 */
function validateApplicantInfo(errorList) {
	
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var gender= $.trim($('#gender').val());
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var applicantAreaName= $.trim($('#areaName').val());
	var villTownCity= $.trim($('#villTownCity').val());
	var applicantPinCode= $.trim($('#pinCode').val());

	
	var applicantEmailId =$.trim($('#emailId').val());
	
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(gender == "" || gender =='0' || gender == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantGender'));
	 }		 
	
	 if (applicantMobileNo == '' || applicantMobileNo ==null  || applicantMobileNo == undefined) {
			errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
		} else {
			if(!validateMobile(applicantMobileNo)) {
				errorList.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
			}
	  }

		
		
	 if(applicantAreaName == "" || applicantAreaName == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantarea'));
	 }

	 if(villTownCity == "" || villTownCity == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	 
	 
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 return errorList;
}

/**
 * validate booking info
 * @param errorList
 * @returns
 */
function validateBookingInfo(errorList){
	
	var fromDate= $('#fromDate').val();
	var toDate= $('#toDate').val();
	var shiftId= $('#shiftId').val();
	var purpose= $.trim($('#purposeofBooking').val());
	
	if(fromDate == "" || fromDate == undefined){
		 errorList.push(getLocalMessage('Please select from date'));
	 }
	if(toDate == "" || toDate == undefined){
		 errorList.push(getLocalMessage('Please select to date'));
	 }
	
	if(shiftId == "" || shiftId =='0' || shiftId == undefined ){
		 errorList.push(getLocalMessage('Please select shift'));
	 }
	if(purpose == "" || purpose == undefined){
		 errorList.push(getLocalMessage('Please enter purpose'));
	 }
	
	return errorList;
}

function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}

/**
 * used to validate a valid mobile no
 * @param mobile
 */
function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}


/*--------------  Save Form  -----------*/

/**
 * 
 */
/*function saveBooking(element) { 
	  return doAjaxOperation(element,"Your application for estate booking saved successfully!", 'WaterTankerBooking.html?redirectToPay', 'save');
}*/



function saveBooking(element,flag){
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'WaterTankerBooking.html?redirectToPay', 'saveform');
		}
		else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')
			{
			 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'WaterTankerBooking.html?PrintReport', 'saveform');
			}
		else
		{
		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'CitizenHome.html', 'saveform');
		}
}




/**
 * to validate Change Of Ownership Form data on submit
 * @param errorList
 * @returns
 */
function validateBookingFormData(errorList) {
	
	var payMode= $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val();
	var payModeIn=$('#payModeIn').val();
	var accept = $('#accept1').is(':checked');
	errorList = validateApplicantInfo(errorList);
	errorList = validateBookingInfo(errorList);
	console.log("accept "+accept)
	if(payMode == "" || payMode == undefined){
		 errorList.push(getLocalMessage('Select payment mode.'));
	 
	}
	if(payMode != 'P'){
		 errorList.push(getLocalMessage('Wrong payment mode selected.'));
	 
	}
	if(payModeIn == '0' || payModeIn == "" || payModeIn == undefined){
		 errorList.push(getLocalMessage('Please Select Payent Mode By'));
	}
	if(accept != true){
		errorList.push(getLocalMessage('Please accept Terms and Conditions.'));
	}
	return errorList;	

	
}


function closeAlertForm()
{
	var childDivName	=	'.child-popup-dialog';
	$(childDivName).hide();
	$(childDivName).empty();
	disposeModalBox();
	$.fancybox.close();
}

function showAlertBoxForShift(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">please select From Date and To Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	 $('#toDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForDateRang(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Not Allowed.Dates between From Date and To Date is already booked</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	 $('#toDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function showAlertBoxForFfromdate(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Please select From Date first</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#toDate').val("");
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}	

function showAlertBoxForAllThreeShiftBooked(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Ok';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Not Allowed.All Shifts are booked for selected Date</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="closeAlertForm()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$('#toDate').val("");
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}	

function getShiftList()
{
	var fromDate =  $("#fromDate").val();
	var toDate =  $("#toDate").val();
	if(toDate!='' && fromDate!=''){
	var url = 'WaterTankerBooking.html?getShiftsBasedOnDate';
	var data = {
		"fromDate" :fromDate,
		"toDate":toDate
		};	
	var result=__doAjaxRequest(url,'POST',data,false,'json');	
	if(result=='')
	{
		showAlertBoxForAllThreeShiftBooked();
		$('#toDate').val("");
	}
	else{
		//D#103497 here shift type don't add in DROPDOWN because of freeze property in case of GENERAL 
		//in other shift type add(because already filtered shift coming based on freeze)
		let shiftStatus=result[0].shiftStatus;
		$('#shiftId option').remove();
		if(shiftStatus == 'GENERAL'){
			let errorList = [];
			errorList.push("Property is freezed for selected period, please select another period to proceed.");	
			showRLValidation(errorList);
		}else{
			$('#errorDivId').hide();
			var  optionsAsString="<option value=''>Select</option>";
		 	for (var j = 0; j < result.length; j++){
				optionsAsString += "<option value='" +result[j].propShift+"'>" + result[j].propShiftDesc+"</option>";
	              }
			$('#shiftId').append(optionsAsString);	
		}
		
	}
}
}



function checkBookedDate(toDate)
{
	var url = 'WaterTankerBooking.html?dateRangBetBookedDate';
	var fromDate =  $("#fromDate").val();
	var toDate =  $("#toDate").val();	
	if(toDate!='')
	{
		if(fromDate==null || fromDate==''){
			showAlertBoxForFfromdate();	
		}	
		else{
			if(fromDate!=toDate)
			{
				var data = {
						"fromDate" :fromDate,
						"toDate":toDate};	
				var result=__doAjaxRequest(url,'POST',data,false,'json');
				if(result=="fail"){
					showAlertBoxForDateRang();			
				}
				else if(result=="pass"){
					getShiftList();
				}
			}
			else
			{
				getShiftList();	
			}
		}
	} 
}

function getPropertyFromToTime(propId){
	var shiftId = $("#shiftId").find("option:selected").attr('value');	
	if(shiftId == null || shiftId == '' ){
		$("#fromTime").val('');
		$("#toTime").val('');
	}
	else{
		var requestData = {
				'shiftId' : shiftId,
				'propId'  : propId
			}
		var response = __doAjaxRequest('WaterTankerBooking.html?getPropFormToTime','POST', requestData, false, 'json');
		
		if(response.startTime != null){
			$("#fromTime").val(response.startTime);
			let fromDate = $('#fromDate').val();
			if(fromDate!= ""){
				fromDate = moment(fromDate,"DD/MM/YYYY");
				if(!fromDate.isAfter(moment())){
					//based on current time and Check in time BT disable chekListChargeId
					 let currentTime = moment(moment().format("HH:mm"),'HH:mm');
					 var beginningTime = moment(response.startTime, 'HH:mm');
					 if(beginningTime.isBefore(currentTime)){
						 //disable BT
						 $('#confirmToProceedBT').prop('disabled', true);
						 $('#submit').prop('disabled', true);
					 }else{
						 //enable BT
						 $('#confirmToProceedBT').prop('disabled', false);
						 $('#submit').prop('disabled', false);
					 }	
				}else{
					$('#confirmToProceedBT').prop('disabled', false);
					$('#submit').prop('disabled', false);
				}
			}else{
				$('#confirmToProceedBT').prop('disabled', false);
				$('#submit').prop('disabled', false);
			}
		}
		 if(response.endTime != null){
			$("#toTime").val(response.endTime);
		}	
		 if(response.startTime == null){
			 $("#fromTime").val('');
				$("#toTime").val('');
			}	
	}	
}

function checkValidationOfInvitees(){
	var invitess = $("#noOfInvitess").val();
	if(invitess >= 100){
		$("#displayOrNot").show();
	}else{
		$("#displayOrNot").hide();
		$("#agrreId").prop("checked", false);
	}
}

$( document ).ready(function() {
	
	$('#empOrg').val('N');
	$('#isBPL').val('N');
	
	// Commented for #113125
	/* $('#isBPL').on('change', function() {
	    var responseId = $(this).val();
       if(responseId=='Y'){
	   $("#bpldiv").show();
   }
   else{
	   $("#bpldiv").hide();  
   }
	}); */
	
	// Added code for #113125
	
	$('#isBPL').on('change', function() {
	    var responseId = $(this).val();
	
       if(responseId=='Y'){
    	   $('#empOrg').prop('disabled', 'disabled');
    	   $("#bpldiv").show();
	   }
	   else{
		   $('#empOrg').prop('disabled', false);
		   $("#bpldiv").hide();
		   $('#bplNo').val('');
	   }
   });
	
	$('#empOrg').on('change', function() {
		var responseId = $(this).val();
		
       if(responseId=='Y'){
    	   $('#isBPL').prop('disabled', 'disabled');
    	   $("#bpldiv").hide();
    	   $('#bplNo').val('');
	   }
	   else{
		   $('#isBPL').prop('disabled', false);  
	   }
	});
	
});

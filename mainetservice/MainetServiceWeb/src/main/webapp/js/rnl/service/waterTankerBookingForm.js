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

   function getChecklist(element){ debugger;
	   var errorList = [];
	   var pancard = $("#panNo").val();
	   var fromDate = $("#fromDate").val();
	   var toDate =  $("#toDate").val();
	   
	  /* if(pancard != ""){
		   var panCardNo = $.trim($("#panNo").val());
			var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
	        var code = /([C,P,H,F,A,T,B,L,J,G])/;
	        var code_chk = panCardNo.substring(3,4);
	        if (!panPat.test(panCardNo)) {
	        	errorList.push(getLocalMessage("emp.error.notValid.pancard"));	 
	        }}*/
	   
	   if(fromDate != "" && toDate != ""){	   
		   var data = {
					"fromDate" :fromDate,
					"toDate":toDate
					};			   
		   var URL = "WaterTankerBooking.html?getValidationFromDateToDate";
		   var result=__doAjaxRequest(URL,'POST',data,false,'json');			   
		    if(result.UadLevelError != null){
		    	errorList.push(getLocalMessage('rnl.estate.booking.valid.date.exceed.date'));				
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
		    var URL = "WaterTankerBooking.html?getCheckList";
	        var returnData = __doAjaxRequestValidationAccor(element,URL, 'POST', requestData, false,'html');
	    	if(returnData != false){
	    	   $('.content').html(returnData);
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
	/*var blockName= $.trim($('#blockName').val());*/
	var villTownCity= $.trim($('#villTownCity').val());
	var applicantPinCode= $.trim($('#pinCode').val());
	/*var applicantAdharNo= $.trim($('#adharNo').val());*/
	
	
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
	  /*if (applicantEmailId !='' && applicantEmailId !=null && applicantEmailId !=undefined) {
			if(!validateEmail(applicantEmailId)) {
				errorList.push(getLocalMessage('emailId.invalid'));
			}
		}*/
		
		
	 if(applicantAreaName == "" || applicantAreaName == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantarea'));
	 }
	 /*if(blockName == "" || blockName == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantBlockName'));
	 }*/
	 if(villTownCity == "" || villTownCity == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	 
	 
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }else {
		 //D#82972
		 var pattern=/^\d{6}$/;
		if (!pattern.test(applicantPinCode)) {
			errorList.push(getLocalMessage('rnl.pincode.sixDigit'));
	    }
	 
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
		 errorList.push(getLocalMessage('rnl.estate.booking.select.from.date '));
	 }
	if(toDate == "" || toDate == undefined){
		 errorList.push(getLocalMessage('rnl.estate.booking.select.to.date'));
	 }
	
	if(shiftId == "" || shiftId =='0' || shiftId == undefined ){
		 errorList.push(getLocalMessage('rnl.estate.booking.enter.shift'));
	 }
	if(purpose == "" || purpose == undefined){
		 errorList.push(getLocalMessage('rnl.estate.purpose'));
	 }
	if(eventId == "" || eventId == undefined){
		 errorList.push(getLocalMessage('rnl.estate.booking.enter.event'));
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



function saveBooking(element) {
	//D#82982
	var errorList = [];
	let collectionMode = $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val(); 
	let amountToPay = $("#amountToPay").val();
	//check payment details selected or not
    //Defect #32656
	if(amountToPay !='0.0')
		if(collectionMode == undefined || collectionMode==''){
		errorList.push("Please select Collection Type");
	}
	if (errorList.length == 0) {					
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
					.filter(":checked").val() == 'N'
					|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
							.filter(":checked").val() == 'P' && ($("#amountToPay").val() !='0.0')) {
				return saveOrUpdateForm(element, "Bill Payment done successfully!",
						'WaterTankerBooking.html?PrintReport', 'saveForm');
			}
		//Defect #32656
		if ($("#amountToPay").val() == '0.0') {
			return saveOrUpdateForm(element,
					"Your application saved successfully!",
					'AdminHome.html', 'saveForm');
		}			 
			else {
				return saveOrUpdateForm(element, "Bill Payment done successfully!",
						'WaterTankerBooking.html', 'saveForm');
			}
					
	}else {
		showRLValidation(errorList);
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
		 errorList.push(getLocalMessage('rnl.select.paymentMode'));
	 
	}
	if(payMode != 'P'){
		 errorList.push(getLocalMessage('rnl.wrong.paymentMode'));
	 
	}
	if(payModeIn == '0' || payModeIn == "" || payModeIn == undefined){
		 errorList.push(getLocalMessage('rnl.select.paymentMode.by'));
	}
	if(accept != true){
		errorList.push(getLocalMessage('rnl.accept.terms.condition'));
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
	if(result==''){
		showAlertBoxForAllThreeShiftBooked();
		$('#toDate').val("");
	}else{
		//D#103497 here shift type don't add in DROPDOWN because of freeze property in case of GENERAL 
		//in other shift type add(because already filtered shift coming based on freeze)
		let shiftStatus=result[0].shiftStatus;
		if(shiftStatus == 'GENERAL'){
			$('#shiftId option').remove();
			let errorList = [];
			errorList.push("Property is freezed for selected period, please select another period to proceed.");	
			showRLValidation(errorList);
		}else{
			$('#errorDivId').hide();
			$('#shiftId option').remove();
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
			//D#36804
			let fromDate = $('#fromDate').val();
			if(fromDate!= ""){
				fromDate = moment(fromDate,"DD/MM/YYYY");
				if(!fromDate.isAfter(moment())){
					//based on current time and Check in time BT disable chekListChargeId
					 let currentTime = moment(moment().format("HH:mm"),'HH:mm');
					 var beginningTime = moment(response.startTime, 'HH:mm');
					 var endTime=moment(response.endTime, 'HH:mm');
					 if(!currentTime.isBefore(endTime)){
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

function saveWaterTankerApprovalForm(element) {
	var levelCheck = $('#levelCheck').val();
	var errorList =[];
	if(levelCheck == 1 || levelCheck == "1"){
		var driverId = $("#driverId").val();
		if(driverId == "" || driverId == 0 || driverId == null){
			errorList.push(getLocalMessage("Please Select Driver Name"));
		}
	}
	if(levelCheck == 2 || levelCheck == "2"){
		var returnDate = $("#returnDate").val();
		if(returnDate == "" || returnDate == 0 || returnDate == null){
			errorList.push(getLocalMessage("Please Select Return Date"));
		}
	}
	if(errorList.length == 0){
			return saveOrUpdateForm(element, "Application Approved Succesfully!",
					'AdminHome.html', 'save');
	}else{
		showRLValidation(errorList);  
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

var maxDays;
$(document).ready(function() {

	/*$('.datePicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});*/
	
	var licMaxTenureDays = $('#licMaxTenureDays').val();
	
	$('.fromDatePicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : '-0d',
		maxDate : '0',
	});
	
	$('.toDatePicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : '-0d',
		maxDate : +licMaxTenureDays,
	});

	$("#licenseFromDate").bind("keyup change", function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#licenseToDate").bind("keyup change", function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	 
	getAgencyDetails();
	if ($('#hideAgnId').val() != '') {
		 $("#agnId option[value='"+$('#hideAgnId').val()+"']").prop('selected', 'selected'); 
		$('#agnId').trigger("chosen:updated");
	}
	
	if($("#scrutinyViewMode").val() == 'V' && $("#scrutinyViewMode").val() != null){
		
		$('#newAdvertisementApplicationView *').attr('readonly', 'readonly');
	}
});

function getApplicableChecklistAndCharges(element) {
	
	var errorList = [];
	errorList = validateApplicationInformation(errorList);
	errorList = validateApplicantDetails(errorList);
	errorList = getLocationMapping(errorList)
	if(errorList == 0){
		var theForm = '#newHoardingApplication';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'NewHoardingApplication.html?getApplicableCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData != null) {
			$('.content-page').html(returnData);
		}
	}else{
		displayErrorsOnPage(errorList);
	}	
}
function getLocationMapping(errorList) {

	var locationId = $("#locId").val();
	if(locationId != '' || locationId != null){
		var requestData = {
				"locationId" : locationId
		};
		var ajaxResponse = __doAjaxRequest(
				'NewHoardingApplication.html?getLocationMapping',
				'POST', requestData, false,'json');
		if(ajaxResponse == "N"){
			errorList.push(getLocalMessage('adh.mapLoc.operationalWardZone'));
		}
	}
	return errorList;
}
function validateApplicationInformation(errorList){
	
	var applicantTitle = $("#applicantTitle").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var gender = $("#gender").val();
	var mobileNo = $("#mobileNo").val();
	var areaName = $("#areaName").val();
	var villTownCity = $("#villTownCity").val();
	var pinCode = $("#pinCode").val();
	//$("#sorIteamUnit"+i).find("option:selected").attr('value');
	if(applicantTitle == 0 || applicantTitle == null ){
		errorList.push(getLocalMessage("adh.select.title"));
	}
	if(firstName == "" || firstName == null ){
		errorList.push(getLocalMessage("adh.applicant.validation.enter.first.name"));
	}
	if(lastName == "" || lastName == null){
		errorList.push(getLocalMessage("adh.applicant.validation.enter.last.name"));
	}
	if(gender == 0 ||gender == null){
		errorList.push(getLocalMessage("adh.applicant.validation.select.gender"));
	}
	if(mobileNo == "" || mobileNo == null){
		errorList.push(getLocalMessage("adh.applicant.validation.enter.mob.no"));
	}
	if(areaName == "" || areaName == null){
		errorList.push(getLocalMessage("adh.applicant.validation.enter.area.name"));
	}
	if(villTownCity == '' || villTownCity == null){
		errorList.push(getLocalMessage("adh.applicant.validation.enter.village.city"));
	}
	if(pinCode == "" || pinCode == null){
		errorList.push(getLocalMessage("adh.applicant.validation.enter.pincode"));
	}
	return errorList;
		  	   
}

function validateApplicantDetails(errorList){
	
	var advertiseCategory = $("#advertiseCategory").val();
	var licenseType = $("#licenseType").val();
	var locCatType = $("#locCatType").val();
	var agencyId = $("#agnId").val();
	var locId = $("#locId").val();
	var propTypeId = $("#propTypeId").val();
	/*var adhStatus = $("#adhStatus").val();*/
	var fromDate = $("#licenseFromDate").val();
	var toDate = $("#licenseToDate").val();
	
	var fromDt = moment(fromDate, "DD.MM.YYYY HH.mm").toDate();
	var toDt = moment(toDate, "DD.MM.YYYY HH.mm").toDate();
	if (fromDate == "") {
		errorList.push(getLocalMessage("adh.validate.from.date"));
	}
	if (toDate == "") {
		errorList.push(getLocalMessage("adh.validate.to.date"));
	}
	/*if (fromDate != null) {
		errorList = validatedate(errorList, 'licenseFromDate');
	}
	if (toDate != null) {
		errorList = validatedate(errorList, 'licenseToDate');
	}*/
	if ((fromDt.getTime()) > (toDt.getTime())) {
		errorList.push(getLocalMessage("adh.compare.from.to.date"));
	}
	if(advertiseCategory == "" || advertiseCategory  == null){
		errorList.push(getLocalMessage("adh.advertisement.validation.advertiser.select.category"));
	}
	if(licenseType == "" || licenseType == null ){
		errorList.push(getLocalMessage("adh.advertisement.validation.select.license.type"))
	}
	if(locCatType =="" || locCatType == null){
		errorList.push(getLocalMessage("adh.advertisement.validation.select.location.type"))
	}
	if(agnId == 0 || agnId == null){
		errorList.push(getLocalMessage("adh.advertisement.validation.select.advertiser.name"))
	}
	if(locId == "" || locId == null ||locId == undefined ){
		errorList.push(getLocalMessage("adh.advertisement.validation.select.location"))
	}
	if(propTypeId == "" || propTypeId == null){
		errorList.push(getLocalMessage("adh.advertisement.validation.select.property.type"))
	}	
	
	return errorList;
}

function saveNewAdvApplication(object) {
	
	var errorList = [];
	if (errorList.length == 0) {
		return saveOrUpdateForm(
				object,
				"New Hoarding Application Permission Submitted Successfully","AdminHome.html", 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}

}
function getAgencyDetails(){
	
	var advertiserCategoryId = $("#advertiseCategory").val();
	if(advertiserCategoryId != '' || advertiserCategoryId != null ){
		var requestData = {
				"advertiserCategoryId" : advertiserCategoryId
			};
		$('#agnId').html('');
		$('#agnId').append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var ajaxResponse = doAjaxLoading('NewHoardingApplication.html?getAgencyName',
				requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);

		$.each(prePopulate, function(index, value) {
			$('#agnId').append(
					$("<option></option>").attr("value", value.agencyId).text(
							(value.agencyName)));
		});
		$('#agnId').trigger("chosen:updated");
	}
}

function validateMobileNumber(){
	
	var mobileNumber = $("#mobileNo").val();
	var filter = /[1-9]{1}[0-9]{9}/;
	if (filter.test(mobileNumber)) {		
		$('#mobNumber').hide();
    }
	else {
		$('#mobNumber').show();
		$('#mobNumber').html('Invalid Mobile Number');
        $('#mobNumber').css('color', 'red');
	}  
}

function validatePinCodeNumber(){
	
	var pincode = $("#pinCode").val();
	 var pattern=/^\d{6}$/;
	if (pattern.test(pincode)) {		
		$('#pinCodeNumber').hide();
    }
	else {
		$('#pinCodeNumber').show();
		$('#pinCodeNumber').html('Pin code should be 6 digits');
        $('#pinCodeNumber').css('color', 'red');
        $('#pincode').focus();
	}  
}

function emailValidation(){
	
	var emailId = $("#emailId").val();
	var filter = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(emailId != ""){
		if (filter.test(emailId)) {		
			$('#email').hide();
	    }
		else {
			$('#email').show();
			$('#email').html('Please enter valid email.');
	        $('#email').css('color', 'red');
		}  
	}	
	else{
		$('#email').hide();
		return false;
	}
}
 function isFromDate(){
	 var currVal = $('#licenseFromDate').val();
	 if(currVal != ''){
		 if(checkDateValidation(currVal)){
			 $('#fromDate').hide(); 
		 }else{
			  $('#fromDate').show();
			  $('#fromDate').html('Please enter valid date.');
		      $('#fromDate').css('color', 'red');
		 }		  
	 }else{
		 $('#fromDate').hide(); 
		 return false;
	 }	
 }

 function isToDate(){
	 var currVal = $('#licenseToDate').val();
	 if(currVal != ''){
		 if(checkDateValidation(currVal)){
			 $('#toDate').hide(); 
		 }else{
			  $('#toDate').show();
			  $('#toDate').html('Please enter valid date.');
		      $('#toDate').css('color', 'red');
		 }		  
	 }else{
		 $('#toDate').hide(); 
		 return false;
	 }		 
 }

function resetAdvApplication(obj) {
	
	$('input[type=text]').val('');
	$('select').val('').trigger('chosen:updated');
	$(".alert-danger").hide();
	resetForm(obj);
}

function checkDateValidation(currVal){
	
	//Declare Regex 
	  var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	  var dtArray = currVal.match(rxDatePattern); // is format OK?
	 
	  if (dtArray == null){
    	  return false;
	  }	       
	  //Checks for dd/mm/yyyy format.
	      dtDay = dtArray[1];
	      dtMonth= dtArray[3];
	      dtYear = dtArray[5];  

	  if (dtMonth < 1 || dtMonth > 12){		 
	      return false;
	  }	 
	  else if (dtDay < 1 || dtDay> 31){		  
	      return false;
	  }
	  else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31){
		  return false;
	  }		  
	  else if (dtMonth == 2){
	     var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	     if (dtDay> 29 || (dtDay ==29 && !isleap)){
	    	 return false;
	     }
	  }
		return true;
}

//Defect #123725

function getLicType() {
	 
	var errorList = [];  
    $("#licenseFromDate").datepicker('setDate', null);

	$("#licenseToDate").datepicker('setDate', null);
	    
	 $("#licenseToDate").datepicker("destroy");
	 $("#licenseFromDate").datepicker("destroy");
	 
	var licType = $("#licenseType").val();
	
	requestData = {
		"licType" : licType,
		
	};
	
	if(licType=="")
		{
		
		}
	else
	{   
		var response = doAjaxLoading('NewHoardingApplication.html?getLicenceType', requestData,'html');				
	
		//for getting calculate year type
		var yearType = doAjaxLoading('NewHoardingApplication.html?getCalculateYearType', requestData,'json');
		
		
		maxDays=response;
		
		if(response !="")
		{
		var licMaxTenureDays = response;
		 $("#licenseFromDate").datepicker({
	            dateFormat : 'dd/mm/yy',
	            changeMonth : true,
	            changeYear : true,
	            minDate : '0',
	            maxDate : 0,
	            onSelect : function(selected) {
	            var sdate = $(this).datepicker("getDate");
	            var cdd = 11-sdate.getMonth();
	            var fdd = 2-sdate.getMonth();
	            var mths = Math.floor(licMaxTenureDays/30);
	            var fmths = 0;
	            if(yearType=="C"){
	              if(cdd<mths){
	            	  sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	                sdate.setMonth(11);
	                
	                sdate.setDate(31);
	              }else{
	            	sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	              }
	            }else{
	            	if(fdd>=0){
	            		fmths = fdd;
	            	}else{
	            		fmths = 12+fdd;
	            	}
	            	if(mths>fmths){
	            		sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));sdate.setMonth(2);sdate.setDate(31);
	            	}else{
	            		sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays));
	            	}		
	            }
	            $("#licenseToDate").datepicker("option", "minDate",selected);
	            $("#licenseToDate").datepicker("setDate",sdate);
	            $("#licenseToDate").datepicker("option", "maxDate",sdate);
	            }
	    
	        });
		
		$("#licenseToDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : '0',
			maxDate : +licMaxTenureDays,
		});
		}
		
		else
		{
		
		errorList
		.push("Date Range Is not Defined for License Type");

		displayErrorsOnPage(errorList);
	    }
	
    }
   }



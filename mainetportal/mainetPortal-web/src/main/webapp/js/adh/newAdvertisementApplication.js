var maxDays;
$(document).ready(
		function() {

			var licMaxTenureDays = $('#licMaxTenureDays').val();

			$('#licenseFromDate').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : '0',
				maxDate : '0',
			});

			$("#licenseToDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : '0',
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
				$("#agnId option[value='" + $('#hideAgnId').val() + "']").prop(
						'selected', 'selected');
				$('#agnId').trigger("chosen:updated");
			}

			if ($("#scrutinyViewMode").val() == 'V'
					&& $("#scrutinyViewMode").val() != null) {

				$('#newAdvertisementApplicationView *').attr('readonly',
						'readonly');
			}
			//D#79968
			if ($("#saveMode").val() != null && $("#saveMode").val() == 'VIEW') {
				$("#ApplicantInformation *").attr("disabled","disabled");
				$("#a2 *").attr("disabled","disabled").trigger("chosen:updated");
				$("#AdevertisementDetails *").attr("disabled","disabled");
			}
			reOrderAdvertisingIdSequence('.advertisingDetailsClass');
		});

function getApplicableChecklistAndCharges(element) {
	
	var errorList = [];
	errorList = validateOutstandingAmount(errorList)
	errorList = validateApplicationInformation(errorList);
	errorList = validateApplicantDetails(errorList);
	errorList = validateAdvertisingDetails(errorList);
	if (errorList == 0) {
		var theForm = '#newAdvertisementApplication';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'NewAdvertisementApplication.html?getApplicableCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		if (returnData != null) {
			$('.content-page').html(returnData);
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateOutstandingAmount(errorList) {

	var outstandingAmount = $("#propOutstandingAmt").val();
	if (outstandingAmount > 0) {
		errorList.push(getLocalMessage("Dues are pending you can not proceed"));
	}
	return errorList;
}

function validateApplicationInformation(errorList) {

	var applicantTitle = $("#applicantTitle").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var gender = $("#gender").val();
	var mobileNo = $("#mobileNo").val();
	var areaName = $("#areaName").val();
	var villTownCity = $("#villTownCity").val();
	var pinCode = $("#pinCode").val();
	// $("#sorIteamUnit"+i).find("option:selected").attr('value');
	if (applicantTitle == 0 || applicantTitle == null) {
		errorList.push(getLocalMessage("Please Select title"));
	}
	if (firstName == "" || firstName == null) {
		errorList.push(getLocalMessage("Please Enter First Name"));
	}
	if (lastName == "" || lastName == null) {
		errorList.push(getLocalMessage("Please Enter Last Name"));
	}
	if (gender == 0 || gender == null) {
		errorList.push(getLocalMessage("Please Select Gender"));
	}
	if (mobileNo == "" || mobileNo == null) {
		errorList.push(getLocalMessage("Please Enter Mobile Number"));
	}
	if (areaName == "" || areaName == null) {
		errorList.push(getLocalMessage("Please Enter Area Name"));
	}
	if (villTownCity == '' || villTownCity == null) {
		errorList.push(getLocalMessage("Please Enter Village Town City"));
	}
	if (pinCode == "" || pinCode == null) {
		errorList.push(getLocalMessage("Please Enter Pincode "));
	}
	return errorList;

}

function validateApplicantDetails(errorList) {

	var advertiseCategory = $("#advertiseCategory").val();
	var licenseType = $("#licenseType").val();
	var locCatType = $("#locCatType").val();
	var agencyId = $("#agnId").val();
	var locId = $("#locId").val();
	var propTypeId = $("#propTypeId").val();
	var adhStatus = $("#adhStatus").val();

	if (advertiseCategory == "" || advertiseCategory == null) {
		errorList.push(getLocalMessage("Please Select Advertiser Category "));
	}
	if (licenseType == "" || licenseType == null) {
		errorList.push(getLocalMessage("Please Select License Type"))
	}
	if (locCatType == "" || locCatType == null) {
		errorList.push(getLocalMessage("Please Select Location Type"))
	}
	if (agnId == 0 || agnId == null) {
		errorList.push(getLocalMessage("Please Select Advertiser Name"))
	}
	if (locId == "" || locId == null || locId == undefined) {
		errorList.push(getLocalMessage("Please Select Location "))
	}
	if (propTypeId == "" || propTypeId == null) {
		errorList.push(getLocalMessage("Please Select Property Type"))
	}
	/*
	 * if(adhStatus =="" || adhStatus == null){
	 * errorList.push(getLocalMessage("Please Select Application Status")) }
	 */

	return errorList;
}

/*
 * function saveNewAdvApplication(object) {
 * 
 * var errorList = []; if (errorList.length == 0) { return saveOrUpdateForm(
 * object, "New Advertisement Application Permission Submitted Successfully",
 * "NewAdvertisementApplication.html", 'saveform'); } else {
 * displayErrorsOnPage(errorList); }
 *  }
 */

function saveNewAdvApplication(object) {
	var errorList = [];
	//Defect #126130
	var online = document.getElementById('offlineDTO.onlineOfflineCheck1').checked;
	/*if(online){
	errorList.push(getLocalMessage("Payment integration not completed please select offline mode"))
	}*/
	if (errorList.length == 0) {
		var theForm = '#newAdvertisementApplication';
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest(
				"NewAdvertisementApplication.html?saveAdvertisementApplication",
				'POST', requestData, false, 'json');

		if (object.error != null && object.error != 0) {
			$.each(object.error, function(key, value) {
				$.each(value, function(key, value) {
					if (value != null && value != '') {
						errorList.push(value);
					}
				});
			});
			displayErrorsOnPage(errorList);
		} else {
			if (object.applicationNo != null) {
				showMessageForSuccess(object.applicationNo, object.servicefree);
			}
		}
	}
	else{
		displayErrorsOnPage(errorList);
	}
}

function showMessageForSuccess(applicationNo, servicefree) {
	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("adh.proceed");
	var no = 'No';
	var redirectUrl = 'NewAdvertisementApplication.html?PrintReport';
	message += '<p class="text-blue-2 text-center padding-5">'
			/*+ getLocalMessage('adh.application.creation.success') +","*/
			+ getLocalMessage('Your Application No.') +""+ applicationNo
	'</p>';
	if (servicefree == 'Y') {
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed + '\'  id=\'Proceed\' '
				+ ' onclick="closeApplication();"/>' + '</div>';
	}
	//Defect #77088-for both online and offline payment
	else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y'){
		var redirectUrl = 'NewAdvertisementApplication.html?redirectToPay';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closebox(\''
			+ errMsgDiv + '\',\'' + redirectUrl + '\')"/>' + '</div>';
	}
	else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'){
		var redirectUrl = 'NewAdvertisementApplication.html?PrintReport';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closebox(\''
			+ errMsgDiv + '\',\'' + redirectUrl + '\')"/>' + '</div>';
	}
	else {
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closebox(\''
				+ errMsgDiv + '\',\'' + redirectUrl + '\')"/>' + '</div>';
	}

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
	return false;
}

function closeApplication() {
	
	window.location.href = 'CitizenHome.html';
	$.fancybox.close();
}

function printReport() {
	
}
function getAgencyDetails() {
	
	var advertiserCategoryId = $("#advertiseCategory").val();
	if (advertiserCategoryId != "" && advertiserCategoryId != null) {
		var requestData = {
			"advertiserCategoryId" : advertiserCategoryId
		};
		$('#agnId').html('');
		$('#agnId').append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var ajaxResponse = doAjaxLoading(
				'NewAdvertisementApplication.html?getAgencyName', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		$.each(prePopulate, function(index, value) {
			$('#agnId').append(
					$("<option></option>").attr("value", value.agencyId).text(
							(value.agencyName)));
		});
		$('#agnId').trigger("chosen:updated");
	}
}

function validateMobileNumber() {

	var mobileNumber = $("#mobileNo").val();
	var filter = /[1-9]{1}[0-9]{9}/;
	if (mobileNumber != '') {
		if (filter.test(mobileNumber)) {
			$('#mobNumber').hide();
		} else {
			$('#mobNumber').show();
			$('#mobNumber').html('Invalid Mobile Number');
			$('#mobNumber').css('color', 'red');
		}
	} else {
		$('#mobNumber').hide();
	}
}

function validatePinCodeNumber() {

	var pincode = $("#pinCode").val();
	var pattern = /^\d{6}$/;
	if (pincode != '') {
		if (pattern.test(pincode)) {
			$('#pinCodeNumber').hide();
		} else {
			$('#pinCodeNumber').show();
			$('#pinCodeNumber').html('Pin code should be 6 digits');
			$('#pinCodeNumber').css('color', 'red');
			$('#pincode').focus();
		}
	} else {
		$('#pinCodeNumber').hide();
	}
}

function emailValidation() {

	var emailId = $("#emailId").val();
	var filter = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (emailId != '') {
		if (filter.test(emailId)) {
			$('#email').hide();
		} else {
			$('#email').show();
			$('#email').html('Please enter valid email.');
			$('#email').css('color', 'red');
		}
	} else {
		$('#email').hide();
	}
}

function isFromDate() {
	var currVal = $('#licenseFromDate').val();
	if (currVal != '') {
		if (checkDateValidation(currVal)) {
			$('#fromDate').hide();
		} else {
			$('#fromDate').show();
			$('#fromDate').html('Please enter valid date.');
			$('#fromDate').css('color', 'red');
		}
	} else {
		$('#fromDate').hide();
		return false;
	}
}

function isToDate() {
	var currVal = $('#licenseToDate').val();
	if (currVal != '') {
		if (checkDateValidation(currVal)) {
			$('#toDate').hide();
		} else {
			$('#toDate').show();
			$('#toDate').html('Please enter valid date.');
			$('#toDate').css('color', 'red');
		}
	} else {
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


//Defect #129856

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
		var response = doAjaxLoading('NewAdvertisementApplication.html?getLicenceType', requestData,'json');				
	
		//for getting calculate year type
		var yearType = doAjaxLoading('NewAdvertisementApplication.html?getCalculateYearType', requestData,'json');
		
		
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
	                //sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays))
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
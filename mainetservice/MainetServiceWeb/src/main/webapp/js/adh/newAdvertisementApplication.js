
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

			/*
			 * $('.datePicker').datepicker({ dateFormat : 'dd/mm/yy',
			 * changeMonth : true, changeYear : true });
			 */

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
			//reOrderAdvertisingIdSequence('.advertisingDetailsClass');
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
	if (outstandingAmount > 0 ) {
		errorList.push(getLocalMessage("Dues are pending you can not proceed"));
	}
	return errorList;
}
function validateApplicationInformation(errorList) {

	var applicantTitle = $.trim($("#applicantTitle").val());
	var firstName = $.trim($("#firstName").val());
	var lastName = $.trim($("#lastName").val());
	var gender = $.trim($("#gender").val());
	var mobileNo = $.trim($("#mobileNo").val());
	var areaName = $.trim($("#areaName").val());
	var villTownCity = $.trim($("#villTownCity").val());
	var pinCode = $.trim($("#pinCode").val());
	// $("#sorIteamUnit"+i).find("option:selected").attr('value');
	if (applicantTitle == 0 || applicantTitle == null) {
		errorList.push(getLocalMessage("applicant.validate.title"));
	}
	if (firstName == "" || firstName == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.enter.first.name"));
	}
	if (lastName == "" || lastName == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.enter.last.name"));
	}
	if (gender == 0 || gender == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.select.gender"));
	}
	if (mobileNo == "" || mobileNo == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.enter.mob.no"));
	}
	if (areaName == "" || areaName == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.enter.area.name"));
	}
	if (villTownCity == '' || villTownCity == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.enter.village.city"));
	}
	if (pinCode == "" || pinCode == null) {
		errorList
				.push(getLocalMessage("adh.applicant.validation.enter.pincode"));
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
	var licenseFromDate = $('#licenseFromDate').val();
	var licenseToDate = $('#licenseToDate').val();
	/* var adhStatus = $("#adhStatus").val(); */

	if (advertiseCategory == "" || advertiseCategory == null) {
		errorList
				.push(getLocalMessage("adh.advertisement.validation.advertiser.select.category"));
	}
	if (licenseType == "" || licenseType == null) {
		errorList
				.push(getLocalMessage("adh.advertisement.validation.select.license.type"))
	}
	if (locCatType == "" || locCatType == null) {
		errorList
				.push(getLocalMessage("adh.advertisement.validation.select.location.type"))
	}
	if (agnId == 0 || agnId == null) {
		errorList
				.push(getLocalMessage("adh.advertisement.validation.select.advertiser.name"))
	}
	if (locId == "" || locId == null || locId == undefined) {
		errorList
				.push(getLocalMessage("adh.advertisement.validation.select.location"))
	}
	if (propTypeId == "" || propTypeId == null) {
		errorList
				.push(getLocalMessage("adh.advertisement.validation.select.property.type"))
	}
	if (licenseFromDate == "" || licenseFromDate == undefined
			|| licenseFromDate == null) {
		errorList.push(getLocalMessage('adh.select.license.fromDate'));
	}
	if (licenseToDate == "" || licenseToDate == undefined
			|| licenseToDate == null) {
		errorList.push(getLocalMessage('adh.select.license.toDate'));
	} else {
		if ($.datepicker.parseDate('dd/mm/yy', licenseFromDate) > $.datepicker
				.parseDate('dd/mm/yy', licenseToDate)) {
			errorList
					.push(getLocalMessage("advertiser.master.validate.lic.to.date.from.date"))
		}
	}
	/*
	 * if(adhStatus =="" || adhStatus == null){
	 * errorList.push(getLocalMessage("Please Select Application Status")) }
	 */
	/*
	 * if(licenseFromDate != "" || licenseFromDate != null){
	 * isDate(licenseFromDate); } if(licenseToDate != "" || licenseToDate !=
	 * null){ validationOfdate(errorList,'licenseToDate'); }
	 */
	return errorList;
}

/*
 * function saveNewAdvApplication(object) {
 * 
 * var errorList = []; if (errorList.length == 0) { return saveOrUpdateForm(
 * object, "New Advertisement Application Permission Submitted Successfully",
 * "NewAdvertisementApplication.html?PrintReport", 'saveform'); } else {
 * displayErrorsOnPage(errorList); } }
 */

function saveNewAdvApplication(object) {

	var errorList = [];
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
}

function showMessageForSuccess(applicationNo, servicefree) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("adh.proceed");
	var no = 'No';
	var redirectUrl = 'NewAdvertisementApplication.html?PrintReport';
	message += '<p class="text-blue-2 text-center padding-15">'
			+ getLocalMessage('adh.application.creation.success') + " , "
			+ getLocalMessage("adh.application.no") + "" + applicationNo
	'</p>';
	if (servicefree == 'Y') {
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed + '\'  id=\'Proceed\' '
				+ ' onclick="closeApplication();"/>' + '</div>';
	} else {
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
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
}

function getAgencyDetails() {

	var advertiserCategoryId = $("#advertiseCategory").val();
	if (advertiserCategoryId != '' || advertiserCategoryId != null) {
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
	if (emailId != "") {
		if (filter.test(emailId)) {
			$('#email').hide();
		} else {
			$('#email').show();
			$('#email').html('Please enter valid email.');
			$('#email').css('color', 'red');
		}
	} else {
		$('#email').hide();
		return false;
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

function checkDateValidation(currVal) {

	// Declare Regex
	var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var dtArray = currVal.match(rxDatePattern); // is format OK?

	if (dtArray == null) {
		return false;
	}
	// Checks for dd/mm/yyyy format.
	dtDay = dtArray[1];
	dtMonth = dtArray[3];
	dtYear = dtArray[5];

	if (dtMonth < 1 || dtMonth > 12) {
		return false;
	} else if (dtDay < 1 || dtDay > 31) {
		return false;
	} else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11)
			&& dtDay == 31) {
		return false;
	} else if (dtMonth == 2) {
		var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
		if (dtDay > 29 || (dtDay == 29 && !isleap)) {
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
		var response = doAjaxLoading('NewAdvertisementApplication.html?getLicenceType', requestData,'html');				
	
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
	                
	                sdate.setDate(sdate.getDate()+parseInt(licMaxTenureDays))
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




var maxDays;
$(document).ready(
		function() {
			
			
			var licMaxTenureDays = $('#licMaxTenureDays').val();

			$('#licenseFromDate').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : '-0d',
				maxDate : '0',
			});

			$("#licenseToDate").datepicker({
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
				$("#agnId option[value='" + $('#hideAgnId').val() + "']").prop(
						'selected', 'selected');
				$('#agnId').trigger("chosen:updated");
			}

			if ($("#scrutinyViewMode").val() == 'V'
					&& $("#scrutinyViewMode").val() != null) {
				$('#renewalAdvertisementLicenseView *').attr('disabled',
						'true');
			}
			
			if ($("#saveMode").val() == 'R' && $("#saveMode").val() != null) {
				
				$("#ApplicantInformation *").attr('disabled', 'true');
				$("#AdevertisementDetails *").attr('disabled', 'true');
				/*
				 * $("#advertiseCategory").attr("disabled", "disabled");
				 * $("#licenseType").attr("disabled", "disabled");
				 * $("#locCatType").attr("disabled", "disabled");
				 * $("#agnId").attr("disabled", "disabled");
				 * $("#locId").attr("disabled", "disabled");
				 * $("#propTypeId").attr("disabled", "disabled");
				 */
				$("#propOwnerName").attr('readonly', 'readonly');
			}
			//D#79968
			if ($("#saveMode").val() != null && $("#saveMode").val() == 'VIEW') {
				$("#ApplicantInformation *").attr("disabled","disabled");
				$("#a2 *").attr("disabled","disabled").trigger("chosen:updated");
				$("#AdevertisementDetails *").attr("disabled","disabled");
			}
		});

function getAvertisementApplicationDetails() {
	
	var licenseNo = $("#licenseNo").val();
	if (licenseNo != '' && licenseNo != null) {
		var requestData = {
			"licenseNo" : licenseNo
		};
		var returnData = __doAjaxRequest(
				'RenewalAdvertisementApplication.html?getAdevertisementApplicationForm',
				'Post', requestData, false, 'html');
		$('.content-page').html(returnData);
		if(returnData ==""){
			
		}else{
			//getLicType();
			var licType = $("#licenseType").val();
			requestData = {
				"licType" : licType,
				
			};
			var response = doAjaxLoading('RenewalAdvertisementApplication.html?getLicenceType', requestData,'json');
			var licMaxTenureDays = response;
			var yearType = doAjaxLoading('RenewalAdvertisementApplication.html?getCalculateYearType', requestData,'json');
			var fromDt = $('#licenseFromDate').val();
			var from = fromDt.split("/");
			
			var sdate = new Date(from[2], from[1] - 1, from[0]);
			var licToDate = new Date(from[2], from[1] - 1, from[0]);
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
		            $("#licenseFromDate").datepicker("option", "minDate",licToDate);
		            $("#licenseFromDate").datepicker("setDate",licToDate);
		            $("#licenseFromDate").datepicker("option", "maxDate",licToDate);
		            $("#licenseToDate").datepicker("option", "minDate",licToDate);
		            $("#licenseToDate").datepicker("setDate",sdate);
		            $("#licenseToDate").datepicker("option", "maxDate",sdate);
		    
		            $("#licenseFromDate").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : licToDate,
						maxDate : licToDate,
					});
			$("#licenseToDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : licToDate,
				maxDate : sdate,
			});
			 
			
		}

	}
}

function getAgencyDetails() {
	
	var advertiserCategoryId = $("#advertiseCategory").val();
	if (advertiserCategoryId != '' && advertiserCategoryId != null
			&& advertiserCategoryId != undefined) {
		var requestData = {
			"advertiserCategoryId" : advertiserCategoryId
		};
		$('#agnId').html('');
		$('#agnId').append(
				$("<option></option>").attr("value", "0").text(
						getLocalMessage('selectdropdown')));
		var ajaxResponse = doAjaxLoading(
				'RenewalAdvertisementApplication.html?getAgencyName',
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

function getRenewalAdvertChecklistAndCharges(element) {
	var errorList = [];
	var licenseFromDate = $("#licenseFromDate").val();
	var licenseToDate = $("#licenseToDate").val();
	licenseFromDate=licenseFromDate.split("12:00 AM",1);
	licenseToDate=licenseToDate.split("12:00 AM",1);
	var fromDt = moment(licenseFromDate, "DD.MM.YYYY HH.mm").toDate();
	var toDt = moment(licenseToDate, "DD.MM.YYYY HH.mm").toDate();
	if (fromDate == "") {
		errorList.push(getLocalMessage("adh.validate.from.date"));
	}
	if (toDate == "") {
		errorList.push(getLocalMessage("adh.validate.to.date"));
	}
	console.log("fromDt: "+fromDt.getTime());
	console.log("toDt: "+toDt.getTime());
	if ((fromDt.getTime()) > (toDt.getTime())) {
		errorList.push(getLocalMessage("adh.compare.from.to.date"));
	}
	errorList = validateApplicantDetails(errorList);
	if (errorList == 0) {
		var theForm = '#renewalAdvertisementApplicationForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'RenewalAdvertisementApplication.html?getApplicableCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		if (returnData != null) {
			$('.content-page').html(returnData);
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveRenewalAdvApplication(object) {
	var errorList = [];
	if (errorList.length == 0) {
		var theForm = '#renewalAdvertisementApplicationForm';
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest(
				"RenewalAdvertisementApplication.html?saveAdvertisementApplication",
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
	var redirectUrl = 'RenewalAdvertisementApplication.html?PrintReport';
	message += '<p class="text-blue-2 text-center padding-15">'
			+ getLocalMessage('adh.application.creation.success') + " , "
			+ getLocalMessage("Your Application No.") + "" + applicationNo
	'</p>';
	if (servicefree == 'Y') {
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed + '\'  id=\'Proceed\' '
				+ ' onclick="closeApplication();"/>' + '</div>';
	}
	//Defect #77088-online and offline payment
	else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y'){
		var redirectUrl = 'RenewalAdvertisementApplication.html?redirectToPay';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closebox(\''
			+ errMsgDiv + '\',\'' + redirectUrl + '\')"/>' + '</div>';
	}
	else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'){
		var redirectUrl = 'RenewalAdvertisementApplication.html?PrintReport';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closebox(\''
			+ errMsgDiv + '\',\'' + redirectUrl + '\')"/>' + '</div>';
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
	
	window.location.href = 'CitizenHome.html';
	$.fancybox.close();
}

function resetRenewalForm(obj) {
	
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


function validateApplicantDetails(errorList) {

	var advertiseCategory = $("#advertiseCategory").val();
	var licenseType = $("#licenseType").val();
	var locCatType = $("#locCatType").val();
	var agencyId = $("#agnId").val();
	var locId = $("#locId").val();
	var propTypeId = $("#propTypeId").val();
	var adhStatus = $("#adhStatus").val();
	var licenseFromDate = $("#licenseFromDate").val();
    var licenseToDate = $("#licenseToDate").val();
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
	
	if (licenseFromDate == "" || licenseFromDate == null) {
		errorList.push(getLocalMessage("Please enter license from date "))
	}
	if (licenseToDate == "" || licenseToDate == null) {
		errorList.push(getLocalMessage("Please enter license to date "))
	}
	return errorList;
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
		var response = doAjaxLoading('RenewalAdvertisementApplication.html?getLicenceType', requestData,'json');				
	
		//for getting calculate year type
		var yearType = doAjaxLoading('RenewalAdvertisementApplication.html?getCalculateYearType', requestData,'json');
		
		
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

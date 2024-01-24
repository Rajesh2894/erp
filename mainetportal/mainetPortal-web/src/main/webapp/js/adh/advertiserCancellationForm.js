
//ABM3223User Story 112154 

$(document).ready(function() {

    $('#cancellationDate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
	maxDate : '0',
    });
    $('#AdvertisercancellationForm').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });

});

$("#cancellationDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});
/*function searchAdvertiserNameByLicNo() {
    var errorList = [];
    var requestData = {
	"agencyLicNo" : $("#agencyLicNo").val()
    };
    $('#agencyName').val('');
    $("#agencyAdd").val('');
    $("#agencyContactNo").val('');
    $("#agencyEmail").val('');
    $("#agencyStatus").val('');
    $("#agencyOwner").val('');
    $("#panNumber").val('');
    $("#agencyCategory").val('');
    //$("#agencyLicIssueDate").val('');
    //$("#agencyLicFromDate").val('');
    //$("#agencyLicToDate").val('');
    $("#gstNo").val('');
    $("#uidNo").val('');
    $("#panNumber").val('');
    $('#agencyRemark').val('');
    $("#applicantFirstName").val('');
    $("#applicantMiddleName").val('');
    $("#applicantLastName").val('');
    $("#mobileNo").val('');
    $("#emailId").val('');
    $("#areaName").val('');
    $("#villageTownSub").val('');
    $("#roadName").val('');
    $("#pinCode").val('');
    $("#aadharNo").val('');


   
    var ajaxResponse = doAjaxLoading(
	    'AdvertisercancellationForm.html?searchAdvertiserNameByLicNo',
	    requestData, 'json');

    if (ajaxResponse == 'N') {
	errorList
		.push(getLocalMessage('Against this license Number license have cancelled'));
	displayErrorsOnPage(errorList);
	//$("#save").hide();
    } else {
	$("#errorDiv").hide();
	$('#agencyName').val(ajaxResponse.masterDto.agencyName);
	$("#agencyAdd").val(ajaxResponse.masterDto.agencyAdd);
    $("#agencyContactNo").val(ajaxResponse.masterDto.agencyContactNo);
    $("#agencyEmail").val(ajaxResponse.masterDto.agencyEmail);
    $("#agencyStatus").val(ajaxResponse.masterDto.agencyStatus);
    $("#agencyOwner").val(ajaxResponse.masterDto.agencyOwner);
    $("#panNumber").val(ajaxResponse.masterDto.panNumber);
    $("#agencyCategory").val(ajaxResponse.masterDto.agencyCategory);
    //$("#agencyLicIssueDate").val(ajaxResponse.masterDto.agencyLicIssueDate);
    //$("#agencyLicFromDate").val(ajaxResponse.masterDto.agencyLicFromDate);
    //$("#agencyLicToDate").val(ajaxResponse.masterDto.agencyLicToDate);
    //$("#agencyLicFromDate").val(ajaxResponse.masterDto.moment(agencyLicFromDate, "DD.MM.YYYY HH.mm").toDate());
    //$("#agencyLicToDate").val(ajaxResponse.masterDto.moment(agencyLicToDate, "DD.MM.YYYY HH.mm").toDate());
    //$("#agencyLicIssueDate").val(ajaxResponse.masterDto.moment(agencyLicIssueDate, "DD.MM.YYYY HH.mm").toDate());
    $("#gstNo").val(ajaxResponse.masterDto.gstNo);
    $("#agencyRemark").val(ajaxResponse.masterDto.agencyRemark);
    $("#uidNo").val(ajaxResponse.masterDto.uidNo);
    $("#panNumber").val(ajaxResponse.masterDto.panNumber);
    $("#applicantFirstName").val(ajaxResponse.applicantDetailDTO.applicantFirstName);
    $("#applicantMiddleName").val(ajaxResponse.applicantDetailDTO.applicantMiddleName);
    $("#applicantLastName").val(ajaxResponse.applicantDetailDTO.applicantLastName);
    $("#mobileNo").val(ajaxResponse.applicantDetailDTO.mobileNo);
    $("#emailId").val(ajaxResponse.applicantDetailDTO.emailId);
    $("#areaName").val(ajaxResponse.applicantDetailDTO.areaName);
    $("#villageTownSub").val(ajaxResponse.applicantDetailDTO.villageTownSub);
    $("#roadName").val(ajaxResponse.applicantDetailDTO.roadName);
    $("#pinCode").val(ajaxResponse.applicantDetailDTO.pinCode);
    $("#aadharNo").val(ajaxResponse.applicantDetailDTO.aadharNo);
    
    }

}*/


function searchAdvertiser() {

	var divName = '.content-page';
	var requestData = {
		"agencyLicNo" : $("#agencyLicNo").val()

	};

	var ajaxResponse = doAjaxLoading(
			'AdvertisercancellationForm.html?searchAdvertiserNameByLicNo',
			requestData, 'html', divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// prepareTags();
}


//New code

function getChecklistAndCharges(element) {
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = validateForm(errorList);

	if (errorList.length == 0) {
		var divName = '.content-page';
		var URL = 'AdvertisercancellationForm.html?getChecklistAndCharges';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
				'html');
		
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}

}

function saveAgencyRegistrationCancellation(element) {

	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = validateForm(errorList);

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}

	else {
		$.fancybox.close();
		var divName = '.content-page';

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest(
				"AdvertisercancellationForm.html?saveAdvertiserCancellation",
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

			if (object.applicationId != null) {
				showBoxForApproval(getLocalMessage("Your application No." + " "
						+ object.applicationId + " "
						+ "has been submitted successfully."));
			}
			//agencyRegAcknow();
		}
	}

}



function showBoxForApproval(succesMessage) {
	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("Proceed");
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
    + '</p>';
	
	var payableFlag = $("#payableFlag").val();
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
	var redirectUrl = 'AdvertisercancellationForm.html?redirectToPay';
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input type=\'button\' value=\'' + 'Proceed'
		+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
		+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
		+ '\')"/>' + '</div>';

	}
	else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'){
		var redirectUrl = 'AdvertisercancellationForm.html?PrintReport';
		message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + 'Proceed'
			+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
			+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
			+ '\')"/>' + '</div>';
	}
	else {
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed + '\'  id=\'Proceed\' '
		+ ' onclick="closeApproval();"/>' + '</div>';

	}
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
	return false;
}

function closeApproval() {
	window.location.href = 'CitizenHome.html';
	$.fancybox.close();
}

function validateApplicantInfo(errorList) {
	var applicantTitle = $.trim($('#applicantTitle').val());
	var applicantFirstName = $.trim($('#applicantFirstName').val());
	var applicantLastName = $.trim($('#applicantLastName').val());
	var mobileNo = $.trim($('#mobileNo').val());
	var areaName = $.trim($('#areaName').val());
	var pinCode = $.trim($('#pinCode').val());
	var emailId = $.trim($('#emailId').val());

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList.push(getLocalMessage('applicant.validate.title'));
	}
	if (applicantFirstName == "" || applicantFirstName == undefined) {
		errorList.push(getLocalMessage('applicant.validate.first.name'));
	}
	if (applicantLastName == "" || applicantLastName == undefined) {
		errorList.push(getLocalMessage('applicant.validate.last.name'));
	}
	if (mobileNo == "" || mobileNo == undefined) {
		errorList.push(getLocalMessage('applicant.validate.mobile.no'));
	} else {

		if (mobileNo.length < 10) {
			errorList.push(getLocalMessage('adh.validate.mobile.number'));
		}

	}
	if (areaName == "" || areaName == undefined) {
		errorList.push(getLocalMessage('applicant.validate.area.name'));
	}

	if (pinCode == "" || pinCode == undefined) {
		errorList.push(getLocalMessage('applicant.validate.pincode'));
	}
	if (emailId != "") {
		var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
		var valid = emailRegex.test(emailId);
		if (!valid) {
			errorList.push(getLocalMessage('Applicant Email is invalid'));
		}
	}
	return errorList;
}

function validateForm(errorList) {
	
	//var advertiserLicense_Number = $("#agencyLicNo").val();
	var advertiserName = $("#agencyName").val();
	var advertiserAddress = $("#agencyAdd").val();
	var advertiserMobileNo = $("#agencyContactNo").val();
	var advertiserEmailId = $("#agencyEmail").val();
	var advertiserOwner = $("#agencyOwner").val();
	var panNumber = $("#panNumber").val();
	var cancellationReason = $("#cancellationReason").val();
	var cancellationDate = $("#cancellationDate").val();
	var agencyCategory = $("#agencyCategory").val();

	//var licenseFromDate = moment(agencyLicFromDate, "DD.MM.YYYY HH.mm").toDate();
	//var licenseToDate = moment(agencyLicToDate, "DD.MM.YYYY HH.mm").toDate();
	
	/*if (advertiserLicense_Number == "" || advertiserLicense_Number == undefined
			|| advertiserLicense_Number == null || advertiserLicense_Number == 0) {
		errorList
				.push(getLocalMessage('advertiser.cancellation.validate.licNo'));
	}*/

	if (agencyCategory == "" || agencyCategory == undefined
			|| agencyCategory == null || agencyCategory == 0) {
		errorList
				.push(getLocalMessage('agency.registration.validate.category'));
	}
	if (advertiserName == "" || advertiserName == undefined
			|| advertiserName == null) {
		errorList
				.push(getLocalMessage('agency.registration.validate.agency.name'));
	}

	if (advertiserAddress == "" || advertiserAddress == undefined
			|| advertiserAddress == null) {
		errorList
				.push(getLocalMessage('agency.registration.validate.agency.address'));
	}

	if (advertiserMobileNo == "" || advertiserMobileNo == undefined
			|| advertiserMobileNo == null) {
		errorList
				.push(getLocalMessage('agency.registration.validate.agency.mobileNo'));
	} else {
		if (advertiserMobileNo.length < 10) {
			errorList.push(getLocalMessage('adh.validate.mobile.number'));
		}

	}

	if (advertiserEmailId == "" || advertiserEmailId == undefined
			|| advertiserEmailId == null) {
		errorList
				.push(getLocalMessage('agency.registration.validate.agency.emailId'));
	} else {
		if (advertiserEmailId != "") {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(advertiserEmailId);
			if (!valid) {
				errorList.push(getLocalMessage('adh.validate.emailid'));
			}
		}
	}

	if (advertiserOwner == "" || advertiserOwner == undefined
			|| advertiserOwner == null) {
		errorList
				.push(getLocalMessage('agency.registration.validate.agency.owner'));
	}
	if (panNumber != "") {
		var panVal = $('#panNumber').val();
		var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

		if (!regpan.test(panVal)) {
			errorList.push(getLocalMessage('adh.validate.panNumber'));
		}
	}
	if (cancellationDate == "" || cancellationDate == undefined
		    || cancellationDate == null) {
		errorList
			.push(getLocalMessage('advertiser.cancellation.validate.cancellation.date'));
	    }
	    if (cancellationReason == "" || cancellationReason == undefined
		    || cancellationReason == null) {
		errorList
			.push(getLocalMessage('advertiser.cancellation.validate.cancellation.reason'));
	    }

	return errorList;
}

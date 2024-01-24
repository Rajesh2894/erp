$(document).ready(function() {

    var licMaxTenureDays = $('#licMaxTenureDays').val();
    $("#agencyLicToDate").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
	maxDate : +licMaxTenureDays,
    });

    $('#agencyLicFromDate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
	maxDate : '0',
    });

    $('#AgencyRegistration').validate({
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

$("#agencyLicFromDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});

$("#agencyLicToDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});

function getChecklistAndCharges(element) {
    var errorList = [];
    errorList = validateApplicantInfo(errorList);
    errorList = errorList.concat(validateOwnerDetailsTable());
    errorList = validateForm(errorList);
    //errorList = errorList.concat(validateOwnerDetailsTable());
                 

    if (errorList.length == 0) {
	var divName = '.content-page';
	var URL = 'AgencyRegistration.html?getChecklistAndCharges';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
		'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	prepareTags();
	$("#reset").hide();
    } else {
	displayErrorsOnPage(errorList);
    }

}
function saveAgencyRegistrationForm(element) {
    var errorList = [];
    errorList = validateApplicantInfo(errorList);
    errorList = errorList.concat(validateOwnerDetailsTable());
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
		"AgencyRegistration.html?saveAgencyRegistration", 'POST',
		requestData, false, 'json');

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
	    agencyRegAcknow();
	}

    }

}

function validateForm(errorList) {
	//var ownershipType = $("#trdFtype").val();
    var advertiserName = $("#agencyName").val();
    var advertiserAddress = $("#agencyAdd").val();
    //var advertiserMobileNo = $("#agencyContactNo").val();
    //var advertiserEmailId = $("#agencyEmail").val();
    //var advertiserOwner = $("#agencyOwner").val();
    //var panNumber = $("#panNumber").val();
    var agencyLicFromDate = $("#agencyLicFromDate").val();
    var agencyLicToDate = $("#agencyLicToDate").val();
    var gstNo = $("#gstNo").val();
    
    /*if (ownershipType == "0" || ownershipType == undefined
			|| ownershipType == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownershipType"));
	}*/

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

    /*if (advertiserMobileNo == "" || advertiserMobileNo == undefined
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
    }*/
    if (agencyLicFromDate == "" || agencyLicFromDate == undefined
	    || agencyLicFromDate == null) {
	errorList
		.push(getLocalMessage('agency.registration.validate.licence.form.date'));
    }
    if (agencyLicToDate == "" || agencyLicToDate == undefined
	    || agencyLicToDate == null) {
	errorList
		.push(getLocalMessage('agency.registration.validate.licence.to.date'));
    }

    if ($.datepicker.parseDate('dd/mm/yy', agencyLicFromDate) > $.datepicker
	    .parseDate('dd/mm/yy', agencyLicToDate)) {
	errorList
		.push(getLocalMessage("advertiser.master.validate.lic.to.date.from.date"))
    }

    var reggst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([a-zA-Z]){1}([0-9]){1}?$/;
    if (!reggst.test(gstNo) && gstNo != '') {
	errorList
		.push(getLocalMessage('adh.gstNo.notValid.format'));
    }

    return errorList;
}

function validateApplicantInfo(errorList) {
    var applicantTitle = $.trim($('#applicantTitle').val());
    var firstName = $.trim($('#firstName').val());
    var lastName = $.trim($('#lastName').val());
    var mobileNo = $.trim($('#mobileNo').val());
    var areaName = $.trim($('#areaName').val());
    var pinCode = $.trim($('#pinCode').val());
    var aadharNo = $.trim($('#aadharNo').val());
    var emailId = $.trim($('#emailId').val());
    var ownershipType = $("#trdFtype").val();

    if (applicantTitle == "" || applicantTitle == '0'
	    || applicantTitle == undefined) {
	errorList.push(getLocalMessage('applicant.validate.title'));
    }
    if (firstName == "" || firstName == undefined) {
	errorList.push(getLocalMessage('applicant.validate.first.name'));
    }
    if (lastName == "" || lastName == undefined) {
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
    
    if (aadharNo.length !=0 && aadharNo.length<12) {
    	errorList.push(getLocalMessage('trade.valid.adharno'));
        }
    if (emailId != "") {
	var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	var valid = emailRegex.test(emailId);
	if (!valid) {
	    errorList.push(getLocalMessage('adh.applicant.email.invalid'));
	}
    }
    if (ownershipType == "0" || ownershipType == undefined
			|| ownershipType == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownershipType"));
	}
    return errorList;
}

function agencyRegAcknow(element) {
    var URL = 'AgencyRegistration.html?printAgencyRegAckw';
    var returnData = __doAjaxRequest(URL, 'POST', {}, false);

    var title = 'Agency Registration Acknowlegement';
    prepareTags();
    var printWindow = window.open('', '_blank');

    printWindow.document.write('<html><head><title>' + title + '</title>');
    printWindow.document
	    .write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
    printWindow.document
	    .write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
    printWindow.document
	    .write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
    printWindow.document
	    .write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
    printWindow.document
	    .write('<script src="js/mainet/ui/jquery.min.js"></script>')
    printWindow.document
	    .write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
    printWindow.document
	    .write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
    printWindow.document.write('</head><body style="background:#fff;">');
    printWindow.document.write(returnData);
    printWindow.document.write('</body></html>');
    printWindow.document.close();
}

function saveAgencyApprovalForm(element) {
    if ($("input[name='workflowActionDto.decision']:checked").val() == "APPROVED") {
	return saveOrUpdateForm(element, "Application Approved Succesfully!",
		'AdminHome.html', 'save');

    } else
	return saveOrUpdateForm(element, "Application Rejected Succesfully",
		'AdminHome.html', 'save');

}

function showBoxForApproval(succesMessage) {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

    var payableFlag = $("#payableFlag").val();
    if (payableFlag == 'Y') {
	var redirectUrl = 'AgencyRegistration.html?PrintReport';
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input type=\'button\' value=\'' + 'Proceed'
		+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
		+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
		+ '\')"/>' + '</div>';

    } else {
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
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}

function checkLastApproval(element) {
    var payableFlag = $("#payableFlag").val();
    var divName = '.content-page';
    var URL = 'AgencyRegistrationAuth.html?checkLastApproval';
    var formName = findClosestElementId(element, 'form');
    var theForm = '#' + formName;
    var requestData = {};
    requestData = __serializeForm(theForm);
    var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');

    $(divName).removeClass('ajaxloader');
    $(divName).html(returnData);
    prepareTags();
    var payableFlag = $("#payableFlag").val();
    if (payableFlag == 'N') {
	saveAgencyApprovalForm(element)
    }
}

function getOwnerTypeDetails() {
	var ownerType = $("#trdFtype" + " option:selected").attr("code");
	if (ownerType != undefined) {
		var data = {
			"ownershipType" : ownerType
		};
		var URL = 'AgencyRegistration.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'Post', data, false,'html');
		$("#owner").html(returnData);
		//$("#owner").find("select").prop("disabled","disabled");
		$("#owner").find("input").prop("disabled","disabled");
		//$("#btnId").prop("disabled","disabled");
		$('.addCF').attr('disabled', true);
		$('.remCF').attr('disabled', true);
		$("#owner").show();
	} else {
		$("#owner").html("");
	}
	

	
}


/*function getSingleAndJoinOwnersDet(ownerType) {

	if (ownerType == "P") {

		$("singleOwnerDetail").show();
		
		$("jointOwnerDetails").hide();
		

	}

	if (ownerType == "A") {

		$("singleOwnerDetail").hide();

		$("jointOwnerDetails").show();
		
	}

	
	$(".appendableClass").show();

}*/

function getOwnerTypeDetailsFoForm() {
	var ownerType = $("#trdFtype" + " option:selected").attr("code");
	if (ownerType != undefined) {
		var data = {
			"ownershipType" : ownerType
		};
		var URL = 'AgencyRegistration.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'Post', data, false,'html');
		$("#owner").html(returnData);
		//$("#owner").find("select").prop("disabled","disabled");
		//$("#btnId").prop("disabled","disabled");
		$("#owner").show();
	} else {
		$("#owner").html("");
	}
	

	
}

var flag=$("#trdFtype").val();
if(flag != null && flag!="" && flag!=undefined ){	
getOwnerTypeDetails();
}


function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}

$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});

function deleteEntry(obj, ids) {
	
	deleteTableRow('ownerDetail', obj, ids);
	$('#ownerDetail').DataTable().destroy();
	$("#ownerDetail tbody tr").each(function(i) {

	});

}

function validateOwnerDetailsTable() {
	var errorList = [];
	var rowCount = $('#ownerDetail tr').length;	
	var mobNo = [];
	var email=[];
	var aadharno=[];
	var panno=[];
	

	if ($.fn.DataTable.isDataTable('#ownerDetail')) {
		$('#ownerDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerDetail tbody tr").each(function(i) {
			
			if(rowCount<=2){

							var ownerName = $("#agencyOwner" + i).val();
							var ownerMobileNo = $("#agencyContactNo" + i).val();
							var emailId = $("#agencyEmail" + i).val();
							var ownerAdharNo = $("#uidNo" + i).val();
							var pancardno = $("#panNumber" + i).val();
							
							
							var constant = 1;
			}
			else{
				var ownerName = $("#agencyOwner" + i).val();
				var ownerMobileNo = $("#agencyContactNo" + i).val();
				if(ownerMobileNo!=undefined)
					mobNo.push($("#agencyContactNo" + i ).val());
				var emailId = $("#agencyEmail" + i).val();
				if(emailId!=undefined)
					email.push($("#agencyEmail" + i ).val());
				var ownerAdharNo = $("#uidNo" + i).val();
				if(ownerAdharNo!=undefined)
					aadharno.push($("#uidNo" + i ).val());
				var pancardno = $("#panNumber" + i).val();
				if(pancardno!=undefined)
					panno.push($("#panNumber" + i ).val());
				
				
				var constant = i+1;
			}
							if (ownerName == '0' || ownerName == undefined
									|| ownerName == "") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownername")
												+" " +constant);
							}
						/*	if (ownerAdharNo == "" || ownerAdharNo == undefined || ownerAdharNo == "0") {
								errorList.push(getLocalMessage("tradelicense.validation.ownerAdharNo")
										+" " +constant);
							}*/
							if (ownerMobileNo == "" || ownerMobileNo == undefined || ownerMobileNo == "0") {
								errorList.push(getLocalMessage("tradelicense.validation.ownerMobileNo")
										+" " +constant);
							}
							else {	
								if (!validateMobile(ownerMobileNo)) 
								{
									errorList.push(getLocalMessage("tradelicense.validation.validMobileNo")+" " +constant);
								}
							}
							 if (emailId == "" || emailId == undefined
									    || emailId == null) {
									errorList
										.push(getLocalMessage('agency.registration.validate.agency.emailId'));
								    }else{
								    	if (emailId !="")		
										{
										  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
										  var valid = emailRegex.test(emailId);
										   if (!valid) {
											   errorList.push(getLocalMessage('trade.vldnn.emailid')+" " +constant);
										   } 
										}
								    }
							
							if(ownerAdharNo.length !=0 && ownerAdharNo.length<12){
								errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
							}
							if (ownerAdharNo != "")
							{
								var adharRegex = new RegExp(/^[0-9]\d{12}$/i);
								 var valid = adharRegex.test(ownerAdharNo);
								  if (!valid) {
									  errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
								  }
							}
							
							if(pancardno.length !=0 && pancardno.length<10){
								errorList.push(getLocalMessage('adh.validate.panNumber')+" " +constant);
							}
							if (pancardno != "")
							{
								var regpan = new RegExp(/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/);
								 var valid = regpan.test(pancardno);
								  if (!valid) {
									  errorList.push(getLocalMessage('adh.validate.panNumber')+" " +constant);
								  }
							}
							
							
							
						});
	
	return errorList;
}


$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});
function deleteEntry(obj, ids) {
	
	deleteTableRow('ownerDetail', obj, ids);
	$('#ownerDetail').DataTable().destroy();
	$("#ownerDetail tbody tr").each(function(i) {

	});

}
$("#selectall").click(function() {
	$('.case').attr('checked', this.checked);
});
/*function doFileDeletion(obj, id) {

	requestData = {
		"id" : id
	};
	url = 'AgencyRegistration.html?doEntryDeletion';
	var row = $("#ownerDetail tbody .appendableClass").length;
	if (row != 1) {
		//$("#ownerDetail tbody .appendableClass").parent().parent().remove();
		var response = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	}

}*/


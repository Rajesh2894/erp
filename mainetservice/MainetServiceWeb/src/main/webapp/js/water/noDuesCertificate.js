$(document).ready(function() {

    $('#consumerDetails').hide();
    $("#back").hide();
    $('#copies').hide();
    $('#chekListChargeId').hide();
    $("#chargesDiv").hide();
    $("#duelist").hide();
    $("#NodivSubmit").hide();
    $("#checkListDetails").hide();
    $('#chekListChargeId').hide();
    if ($('#free').val() == 'F') {
	$("#payment").hide();
    } else {
	$("#payment").show();
    }
    if ($('#povertyLineId').val() == 'N') {
	$("#bpldiv").hide();
    }
    if ($('#povertyLineId').val() == 'Y') {
	$("#bpldiv").show();
    } else {
	$("#bpldiv").hide();
    }

    $("#povertyLineId").change(function(e) {

	if ($("#povertyLineId").val() == 'Y') {

	    $("#bpldiv").show();

	} else {
	    $("#bpldiv").hide();
	}

    });

});
function saveNoDuesCertificateForm(element) {
    var errorList = [];
    var errorList = validateApplicantInfo(errorList);

    if (errorList.length == 0) {
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
		":checked").val() == 'N'
		|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val() == 'P') {

	    var status = saveOrUpdateForm(
		    element,
		    "Your application for No Dues Certificate saved successfully!",
		    'NoDuesCertificateController.html?PrintReport', 'save');
	    //openNoDuesWindowTab(status);

	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")

	.filter(":checked").val() == 'Y') {

	    return saveOrUpdateForm(
		    element,
		    "Your application for No Dues Certificate saved successfully!",
		    'NoDuesCertificateController.html?redirectToPay', 'save');
	} else

	{
	 
		var status = saveOrUpdateForm(
			    element,
			    "Your application for No Dues Certificate saved successfully!",
			    'AdminHome.html', 'save');
		    //openNoDuesWindowTab(status);
	}
    } else {
	$("#NodivSubmit").show();
	displayErrors(errorList);

    }

}

function openNoDuesWindowTab(status) {
    if (!status) {
	var URL = 'NoDuesCertificateController.html?noDueCertificatePrint';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'No Dues Certificate';
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
	printWindow.document
		.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

    }
}

function validateNoDueFormData(errorList) {

    var applicantTitle = $.trim($('#applicantTitle').val());
    var firstName = $.trim($('#firstName').val());
    var lastName = $.trim($('#lastName').val());
    var applicantMobileNo = $.trim($('#mobileNo').val());
    var villTownCity = $.trim($('#villTownCity').val());
    var blockName = $.trim($('#blockName').val());
    var applicantPinCode = $.trim($('#pinCode').val());
    var conNum = $.trim($('#consumerNo').val());
    // var finYear= $.trim($('#onYearSelect').val());
    var cooNoname = $.trim($('#consumerName').val());
    var isBPL = $.trim($('#povertyLineId').val());
    var isBPLNO = $.trim($('#bplNo').val());
    var free = $.trim($('#free').val());

    var payMode = $("input:radio[name='offlineDTO.onlineOfflineCheck']")
	    .filter(":checked").val();

    if (applicantTitle == "" || applicantTitle == '0'
	    || applicantTitle == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
    }
    if (firstName == "" || firstName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
    }
    if (lastName == "" || lastName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
    }
    if (applicantMobileNo == "" || applicantMobileNo == undefined) {
	errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
    }
    if (villTownCity == "" || villTownCity == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
    }
    /*
     * if(blockName == "" || blockName == undefined){
     * errorList.push(getLocalMessage('water.validation.ApplicantBlockName')); }
     */
    if (applicantPinCode == "" || applicantPinCode == undefined) {
	errorList.push(getLocalMessage('water.validation.applicantPinCode'));
    }
    if (conNum == "" || conNum == undefined) {
	errorList.push(getLocalMessage('water.validation.connectionno'));
    }
    /*
     * if(finYear == "" || finYear =='0' || finYear == undefined){
     * errorList.push(getLocalMessage('water.validation.finYear')); }
     */
    if (cooNoname == "" || cooNoname == undefined) {
	errorList.push(getLocalMessage('water.validation.ownerfirstname'));
    }
    if (free != 'F') {
	if (payMode == "" || payMode == undefined) {
	    errorList.push(getLocalMessage('water.validation.paymode'));
	}
    }

    if (isBPL == "0" || isBPL == undefined) {

	errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
    }
    if (isBPL == "Y" && isBPLNO == '') {

	errorList.push(getLocalMessage('water.validation.bplnocantempty'));
    }

    return errorList;

}

function saveNoDuesCertificateExeForm(element) {

    var errorList = [];
    errorList = validateNoDuesExFormData(errorList);
    if (errorList.length == 0) {
	return saveOrUpdateForm(element,
		"Your application for No Dues Certificate saved successfully!",
		'AdminHome.html', 'saveExecutionForm');

    } else {
	$("#NodivSubmit").show();
	displayErrors(errorList);
    }
}

function validateNoDuesExFormData(errorList) {

    var approveBy = $.trim($('#approveBy').val());
    var approveDate = $.trim($('#datepicker').val());
    var noDuesCertiDate = $.trim($('#datepicker2').val());

    if (approveBy == "" || approveBy == undefined) {
	errorList.push(getLocalMessage('water.valid.approveBy'));
    }
    if (approveDate == "" || approveDate == undefined) {
	errorList.push(getLocalMessage('water.valid.approveDate'));
    }
    if (noDuesCertiDate == "" || noDuesCertiDate == undefined) {
	errorList.push(getLocalMessage('water.valid.dueDate'));
    }

    return errorList;

}
function getNODuesChecklistAndCharges(element) {
     
    var errorList = [];
    $("#applicantTitle").prop("disabled", false);
    $("#firstName").prop("disabled", false);
    $("#middleName").prop("disabled", false);
    $("#lastName").prop("disabled", false);
    $("#mobileNo").prop("disabled", false);
    $("#emailId").prop("disabled", false);
    $("#gender").prop("disabled", false);
    var isBPL = $.trim($('#povertyLineId').val());
    var isBPLNO = $.trim($('#bplNo').val());
    // var fin =$('select#onYearSelect').val();
    var noOfCopies = $("#noOfCopies").val();

    // var validateOtp = $("#validateOtp").val();
    /*
     * if( fin==null || fin[0] =="-1" ) {
     * errorList.push(getLocalMessage('water.validation.finYear')); }
     */
    if (noOfCopies == '' || noOfCopies == 'undefined') {
	errorList.push(getLocalMessage('water.validation.noOfCopies'));
    }

    if (isBPL == "0" || isBPL == undefined) {

	errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
    }
    if (isBPL == "Y" && isBPLNO == '') {
	errorList.push(getLocalMessage('water.validation.bplnocantempty'));
    }

    if (errorList.length == 0) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'NoDuesCertificateController.html?getCharges';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);

	var errMsg = returnData["errMsg"];
	if (errMsg != '' && errMsg != undefined) {
	    var errorList = [];
	    errorList.push(errMsg);
	    displayErrors(errorList);
	}
	var divName = '#NoDuesCertiForm';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	$(divName).show();
	var free = $('#free').val();

	if (free == 'F') {
	    $('#checkListDetails').show();
	    //$('#chekListChargeId').show();
	    $('.required-control').next().children().addClass('mandColorClass');
	} else {

	    $("#chargesDiv").show();
	    $('#checkListDetails').show();
	    $("#payment").show();
	    $('#chekListChargeId').hide();
	    $("#NodivSubmit").show();
	    document.getElementById('consumerNo').readOnly = true;
	}

	$("#checkListDetails").show();
	$('#consumerDetails').show();
	//$("#checkListDetails").show();
	$('#copies').show();
	$("#noOfCopies").prop("readonly", true);
	$("#NodivSubmit").show();
	$("#viewdues").prop('disabled', true);
	$("#resetConnection").prop('disabled', true);
    } else {

	displayErrors(errorList);
    }
}

function resetDueWaterForm() {
    $('input[type=text]').val('');
}

function searchConnection(element) {
    var errorList = [];
    var errorList = validateApplicantInfo(errorList);
    var connectionNo = $("#consumerNo").val();

    if (connectionNo == '' || connectionNo == undefined) {
	errorList
		.push(getLocalMessage('water.meterCutOff.validationMSG.connectionNo'));
    }
    if (errorList.length == 0) {
	var theForm = '#noDuesCertificate';
	var requestData = __serializeForm(theForm);
	var URL = 'NoDuesCertificateController.html?' + 'getConnectionDetail';

	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);

	if (returnData == "" || returnData == null
		|| returnData.errorMsg == 'exception') {
	    var URL = $("form").attr('action');
	    _openPopUpForm(URL, "showErrorPage");
	}
	if (returnData.error) {
	    errorList.push(getLocalMessage('water.nodues.no.record.found')
		    + " " + connectionNo);
	    $("#consumerNo").val('');
	    $("#connectionSize").val(0);
	    displayErrors(errorList);

	} else {
	    
	    if (returnData.billGenerated) {
		errorList.push(getLocalMessage('water.nodues.bill.generate ')
			+ " " + connectionNo + " "
			+ getLocalMessage('water.nodues.eligible'));
		displayErrors(errorList);
	    } else if (returnData.dues) {
		errorList.push(getLocalMessage('water.nodues.exist') + " "
			+ connectionNo+ " "
			+ getLocalMessage('water.nodues.eligible'));
		$("#consumerNo").val('');
		$("#connectionSize").val(0);
		displayErrors(errorList);
		$("#back").show();
	    } else {
		$("#consumerName").val(returnData.consumerName);
		$("#consumerAddress").val(returnData.consumerAddress);
		$("#waterDues").val(returnData.waterDues);
		$("#duesAmount").val(returnData.duesAmount);
		$('#consumerDetails').show();
		$("#duelist").show();
		$('#copies').show();
		$('#chekListChargeId').show();
		$("#consumerNo").prop("readonly", true);
		$("#back").hide();
	    }

	}

    } else {
	displayErrors(errorList);
    }
}

function resetConnDetails() {
    
    $('#consumerDetails').hide();
    $('#generateOtp').hide();
    $("#consumerNo").prop("readonly", false);
    $("#consumerNo").val('');
    $("#noOfCopies").val('');
    $('#copies').hide();
    $('#chekListChargeId').hide();

}
function displayErrors(errorList) {
    var errMsg = '<button type="button" class="close" onclick="closeErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

    errMsg += '<ul>';

    $.each(errorList, function(index) {
	errMsg += '<li>' + errorList[index] + '</li>';
    });

    errMsg += '</ul>';
    $('.msg-error').html(errMsg);
    $(".msg-error").show();
    $("#divSubmit").show();
    return false;
}

function closeErrBox() {
    $('.msg-error').hide();

}

/*
 * function generateOTP(element) {
 * 
 * var theForm = '#noDuesCertificate'; var requestData =
 * __serializeForm(theForm); var URL = 'NoDuesCertificateController.html?' +
 * 'generateOtp'; var returnData = __doAjaxRequest(URL, 'POST', requestData,
 * false); }
 */

function validateApplicantInfo(errorList) {
    var applicantTitle = $.trim($('#applicantTitle').val());
    var firstName = $.trim($('#firstName').val());
    var lastName = $.trim($('#lastName').val());
    var mobileNo = $.trim($('#mobileNo').val());
    var areaName = $.trim($('#areaName').val());
    var pinCode = $.trim($('#pinCode').val());
    var povertyLineId = $.trim($('#povertyLineId').val());

    if (applicantTitle == "" || applicantTitle == '0'
	    || applicantTitle == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
    }
    if (firstName == "" || firstName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
    }
    if (lastName == "" || lastName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
    }
    if (mobileNo == "" || mobileNo == undefined) {
	errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
    }
    if (areaName == "" || areaName == undefined) {
	errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
    }
	const regex = new RegExp("^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$");

    if (pinCode == "" || pinCode == undefined) {
	  errorList.push(getLocalMessage('water.validation.applicantPinCode'));
    }else if(!regex.test(pinCode)){
  	errorList.push(getLocalMessage('water.validation.invalidPinCode'));
	  }

    if (povertyLineId == "" || applicantTitle == '0'
	    || povertyLineId == undefined) {
	errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
    }
    return errorList;
}
function printContent(el) {
    
    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;
}
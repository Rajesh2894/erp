$(document).ready(function() {
	var flag = $("#immediateService").val();
	if (flag == 'N') {
		$("#offlinebutton").hide();
		$("#offlineLabel").hide();
	} else {
		$("#offlinebutton").show();
		$("#offlineLabel").show();
	}

	$("#resetform").on("click", function() {
		window.location.reload("#renewalLicenseForm")
	});

});

function getLicenseDetails() {

	var errorList = [];
	var trdLicno = $("#licenseNo").val();
	if (trdLicno == "" || trdLicno == undefined) {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}

	if (errorList.length == 0) {
		var theForm = '#renewalLicenseForm';
		var trdLicno = $("#licenseNo").val();
		var requestData = {
			'trdLicno' : trdLicno
		}
		var response = __doAjaxRequest(
				'RenewalLicenseForm.html?getLicenseDetails', 'POST',
				requestData, false, 'html');

		if (response != null) {
			$(formDivName).html(response);
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function getRenewalCharges(obj) {

	/*
	 * var flag = $("#immediateService").val(); if (flag == 'N') {
	 * $("#offlinebutton").hide(); $("#offlineLabel").hide(); } else {
	 * $("#offlinebutton").show(); $("#offlineLabel").show(); }
	 */
	var errorList = [];
	var totalOutsatandingAmt = $("#totalOutsatandingAmt").val();
	var sudaEnv = $("#sudaEnv").val();
	if (sudaEnv == 'Y')
		if (totalOutsatandingAmt > 0) {
			errorList.push(getLocalMessage("trade.validation.propertyTax"));
			displayErrorsOnPage(errorList);
			return;
		} 
	errorList = validateRenewalLicenseForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"Renewal License Form Submitted Successfully",
				"RenewalLicenseForm.html?saveRenewalLicense",
				'getChargesFromBrms');

	} else {
		displayErrorsOnPage(errorList);
	}

}

function validateRenewalLicenseForm(errorList) {
	var licenseNo = $("#licenseNo").val();
	var troMobileno = $("#troMobileno").val();
	var renewalPeriod = $("#renewalPeriod").val();
	
	if (licenseNo == "" || licenseNo == undefined || licenseNo == "") {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}if (troMobileno == "" || troMobileno == undefined || troMobileno == "") {
		errorList.push(getLocalMessage("tradelicense.validation.ownerMobileNo"));
	}
	else if(troMobileno.length <10){
		errorList.push(getLocalMessage("tradelicense.validation.validMobileNo"));
	}
	if (renewalPeriod == "" || renewalPeriod == undefined || renewalPeriod == 0){
		errorList.push(getLocalMessage("tradelicense.validation.renperiod"));
	}
	return errorList;
}

/*
 * function getChargesFromBrms(obj){ var theForm = '#renewalLicenseForm'; var
 * requestData = {}; requestData = __serializeForm(theForm); var URL =
 * 'RenewalLicenseForm.html?getChargesFromBrms'; var returnData =
 * __doAjaxRequest(URL, 'POST', requestData, false, 'html'); if(returnData !=
 * null){
 * 
 * $(formDivName).html(returnData); prepareTags(); } }
 */

function generateChallanAndPaymentForRenewal(element) {
	//#129512
	var status;
	saveObj = element;
	var yes = getLocalMessage('license.yes');
	var no = getLocalMessage('license.No');
	var warnMsg = getLocalMessage('payment.popup');

	message = '<p class="text-blue-2 text-center padding-15">' + warnMsg
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="onPaymentYes()"/>&nbsp;'
			+ '<input class="btn btn-success " type=\'button\' value=\'' + no
			+ '\'  id=\'no\' ' + ' onclick="closeConfirmBoxForm()"/>'
			+ '</div>';

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;

}

//#129512
function onPaymentYes(){
$.fancybox.close();
var errorList = [];
var status;
var renewalPeriod = $("#renewalPeriod").val();

if (renewalPeriod == "" || renewalPeriod == undefined
		|| renewalPeriod == '0') {
	errorList.push("Please select renewal period");
}

if (errorList.length == 0) {
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
					.filter(":checked").val() == 'P') {
		status= saveOrUpdateForm(saveObj,
				"Your application Data  saved successfully!",
				'RenewalLicenseForm.html?PrintReport',
				'generateChallanAndPayement');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val() == 'Y') {
		status= saveOrUpdateForm(saveObj,
				"Your application Data  saved successfully!!",
				'RenewalLicenseForm.html?redirectToPay',
				'generateChallanAndPayement');
	} else {

		 status = saveOrUpdateForm(saveObj,
				"Your application Data  saved successfully!!",
				'AdminHome.html', 'generateChallanAndPayement');
	}
	
	if(!$.trim($('#validationerrordiv').html()).length){
		
		agencyRegAcknow();
	}
	

} else {
	displayErrorsOnPage(errorList);
}
}

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function printdiv(printpage) {

	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
function resetRenewalForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#renewalLicenseForm").validate().resetForm();
}
var licMaxTenureDays = $('#licMaxTenureDays').val();
/*
 * $("#renewalLictoDate").datepicker({ dateFormat : 'dd/mm/yy', changeMonth :
 * true, changeYear : true, minDate : '0', maxDate : +licMaxTenureDays, });
 * 
 * $('#renewalLicfromDate').datepicker({ dateFormat : 'dd/mm/yy', changeMonth :
 * true, changeYear : true, minDate : '0', maxDate : '0', });
 */

function saveTradeLicenseForm(obj) {

	var errorList = [];

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"transfer Trade License Form Submitted Successfully",
				"TransperLicense.html", 'saveTransferLicenseForm');

	} else {
		displayErrorsOnPage(errorList);
	}
}
function updateRenewlForm(obj) {

	var errorList = [];
	errorList = validateRenewalUpdateForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"transfer Trade License Form Submitted Successfully",
				"AdminHome.html", 'updateTransferLicenseForm');
	} else {
		displayErrorsOnPage(errorList);

	}

}
function saveRenewalFormData(obj) {

	var errorList = [];

	// errorList = validateTransferLicenseForm(errorList);
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"transfer Trade License Form Submitted Successfully",
				"AdminHome.html", 'generateChallanAndPayement');
	} else {
		displayErrorsOnPage(errorList);

	}

}

function validateRenewalUpdateForm(errorList) {
	var errorList = [];
	var renewalPeriod = $("#renewalPeriod").val();

	if (renewalPeriod == "" || renewalPeriod == undefined
			|| renewalPeriod == '0') {
		errorList.push("Please select renewal period");
	}

	return errorList;
}
function agencyRegAcknow() {

	var URL = 'RenewalLicenseForm.html?printAgencyRegAckw';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'License Acknowlegement';
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
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
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

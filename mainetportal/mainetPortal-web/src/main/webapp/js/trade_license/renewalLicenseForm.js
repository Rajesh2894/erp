$(document).ready(function() {
	
	$("#licenseDetails").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	var flag=$("#immediateService").val();
	if(flag=='N'){	
			$("#offlinebutton").hide();
			$("#offlineLabel").hide();
	}
	else{
			$("#offlinebutton").show();
			$("#offlineLabel").show();
	}
	
	
		$("#itemDetails tbody tr").each(function() {
		    $(this).find('select').prop('disabled', true);
		});
		
});



function getLicenseDetails() {
	
	var errorList = [];
	var trdLicno = $("#licenseNo").val();
	if (trdLicno == "" || trdLicno == undefined) {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}
	if(errorList.length ==0){
	var theForm = '#renewalLicenseForm';
	var trdLicno = $("#licenseNo").val();
	var requestData = {
			'trdLicno' :trdLicno
	}
	var response = __doAjaxRequest('RenewalLicenseForm.html?getLicenseDetails',
			'POST', requestData, false, 'html');

	$(formDivName).html(response);
	}
	else{
		displayErrorsOnPage(errorList);
	}
}

function getCharges(obj) {
	
	var errorList = [];
	var totalOutsatandingAmt = $("#totalOutsatandingAmt").val();
	var totalWaterOutsatandingAmt = $('#totalWaterOutsatandingAmt').val();
	var sudaEnv = $("#sudaEnv").val();
	if (sudaEnv == 'Y')
		if (totalOutsatandingAmt > 0) {
			errorList.push(getLocalMessage("trade.validation.propertyTax"));
			displayErrorsOnPage(errorList);
			return;
		} 
	if (sudaEnv != 'Y')
	errorList = validateRenewalLicenseForm(errorList);
	
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"Renewal License Form Submitted Successfully", "RenewalLicenseForm.html?saveRenewalLicense",
				'getChargesFromBrms');
	}else {
		displayErrorsOnPage(errorList);
	}
	
}

function validateRenewalLicenseForm(errorList) {
	var errorList = [];
	var licenseNo = $("#licenseNo").val();
	var troMobileno = $("#troMobileno").val();
	var renewalPeriod = $("#renewalPeriod").val();
	
	
	if (licenseNo == "" || licenseNo == undefined) {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}
	if (troMobileno == "" || troMobileno == undefined) {
		errorList.push(getLocalMessage("tradelicense.validation.validMobileNo"));
	}
	else if(troMobileno.length <10){
		errorList.push(getLocalMessage("tradelicense.validation.validMobileNo"));
	}
	if(renewalPeriod == "" || renewalPeriod == undefined || renewalPeriod ==0){
		errorList.push(getLocalMessage("tradelicense.validation.renperiod"));
	}
	
	return errorList;
}

/*
 * function getChargesFromBrms(obj){
 * 
 * var theForm = '#renewalLicenseForm'; var requestData = {}; requestData =
 * __serializeForm(theForm); var URL =
 * 'RenewalLicenseForm.html?getChargesFromBrms'; var returnData =
 * __doAjaxRequest(URL, 'POST', requestData, false, 'html'); if(returnData !=
 * null){
 * 
 * $(formDivName).html(returnData); prepareTags(); } }
 */

function generateChallanAndPayment(element) {
	// #129512
	var errorList = [];
	var totalOutsatandingAmt = $("#totalOutsatandingAmt").val();
	var totalWaterOutsatandingAmt = $('#totalWaterOutsatandingAmt').val();
	var sudaEnv = $("#sudaEnv").val();
	if (sudaEnv == 'Y'){
		if (totalOutsatandingAmt > 0) {
			errorList.push(getLocalMessage("proprty.tax.validation"));
			displayErrorsOnPage(errorList);
			return;
		}
		if (totalWaterOutsatandingAmt > 0) {
			errorList.push(getLocalMessage("water.tax.validation"));
			displayErrorsOnPage(errorList);
			return;
		}
	}
	if (errorList.length == 0) {
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
	else {
		displayErrorsOnPage(errorList);
	}
	}	


	
// #129512
function onPaymentYes() {
	$.fancybox.close();
	var errorList = [];

	$("#validationerrordiv").html("");
	if (errorList.length == 0) {

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(saveObj,
					"Your application Data  saved successfully!",
					'RenewalLicenseForm.html?PrintReport',
					'generateChallanAndPayement');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			return saveOrUpdateForm(saveObj,
					"Your application Data  saved successfully!!",
					'RenewalLicenseForm.html?redirectToPay',
					'generateChallanAndPayement');
		} else {
			return saveOrUpdateForm(saveObj,
					"Your application Data  saved successfully!!",
					'CitizenHome.html', 'generateChallanAndPayement');
		}
	} else {
		displayErrorsOnPage(errorList);
		return;
	}
}

function backPage() {

	window.location.href = getLocalMessage("CitizenHome.html");
}	

function viewDetails(trdLicno) {
	var requestData = {
			'trdLicno' :trdLicno
	}
	var response = __doAjaxRequest('RenewalLicenseForm.html?getLicenseDetails',
			'POST', requestData, false, 'html');
	$(formDivName).html(response);
}

function billPayment(trdLicno) {
	var requestData = {
			'trdLicno' :trdLicno
	}
	var response = __doAjaxRequest('RenewalLicenseForm.html?getchargesAndPay',
			'POST', requestData, false, 'html');
	$(formDivName).html(response);	
	
}
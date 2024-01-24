$(document).ready(function() {

	var flag = $("#trdFtype").val();
	if (flag != null && flag != "" && flag != undefined) {
		getOwnerTypeDetails();
	}

	$("#itemDetails tbody tr").each(function() {
	    $(this).find('select').prop('disabled', true);
	});
});
function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}

function deleteItemDetailEntry(obj, ids) {

	deleteTableRow('itemDetails', obj, ids);
	$('#itemDetails').DataTable().destroy();
	$("#itemDetails tbody tr").each(function(i) {

	});
}

function searchLicenseDetails() {

	debugger;
	var errorList = [];
	var theForm = '#TransperLicense';
	var trdLicno = $("#trdLicno").val();
	validateTransferLicenseNo(errorList);
	if (errorList.length == 0) {
		var requestData = {
			'trdLicno' : trdLicno
		}
		var response = __doAjaxRequest(
				'TransperLicense.html?getLicenseDetails', 'POST', requestData,
				false, 'html');

		var flag = $("#trdFtype").val();
		if (flag != null && flag != "" && flag != undefined) {
			getOwnerTypeDetails();
		}

		$(formDivName).html(response);
	} else {
		displayErrorsOnPage(errorList);
	}
}
function backPage() {

	$("#TransperLicenseForm").prop('action', '');
	$("#TransperLicenseForm").prop('action', 'TransperLicense.html');
	$("#TransperLicenseForm").submit();

}

function validateCancellationLicenseForm(errorList) {

	var errorList = [];
	var remark = $("#remarks").val();

	if (remark == "" || remark == undefined || remark == "") {
		errorList.push(getLocalMessage("trade.validation.remark"));
	}

	return errorList;
}

function addEntryData() {
	$("#errorDiv").hide();
	var errorList = [];

	if (errorList.length == 0) {
		addTableRow('casePlantiffDetails');
	} else {
		$('#casePlantiffDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}
function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('casePlantiffDetails', obj, ids);
	$('#casePlantiffDetails').DataTable().destroy();
	triggerTable();
}

function saveTradeLicenseForm(obj) {
	debugger;
	var errorList = [];
	errorList = validateTradeForm(errorList);
	errorList = errorList.concat(validateOwnerDetailsTable());
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj,
				"transfer Trade License Form Submitted Successfully",
				"TransperLicense.html", 'saveTransferLicenseForm');

	} else {
		displayErrorsOnPage(errorList);
	}
}

function printChallanAndPayment(element) {

	// #129512
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
function onPaymentYes() {
	$.fancybox.close();
	var errorList = [];
	errorList = validateTradeForm(errorList);
	errorList = errorList.concat(validateOwnerDetailsTable());
	if (errorList.length == 0) {

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(saveObj,
					"Your application Data  saved successfully!",
					'TransperLicense.html?PrintReport',
					'saveTransferLicenseForm');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			return saveOrUpdateForm(saveObj,
					"Your application Data  saved successfully!!",
					'TransperLicense.html?redirectToPay',
					'saveTransferLicenseForm');
		} else {

			var status = saveOrUpdateForm(saveObj,
					"Your application Data  saved successfully!!",
					'CitizenHome.html', 'saveTransferLicenseForm');
		}
	} else {

		displayErrorsOnPage(errorList);
	}
	agencyRegAcknow(status);
}

function updateTradeLicenseForm(obj) {

	var errorList = [];
	errorList = validateTransferLicenseForm(errorList);
	if (errorList.length == 0) {

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData = __doAjaxRequest(
				'TransperLicense.html?updateTransferLicenseForm', 'POST',
				requestData, false);

		try {
			returnData.command.message;
			showConfirmBoxFoLoiPayment(returnData);
		} catch (err) {
			$(formDivName).html(returnData);
			prepareTags();
		}

	} else {
		displayErrorsOnPage(errorList);

	}

}

function showTermsConditionForm(element) {

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(
			'TradeApplicationForm.html?viewTermsCondition', 'POST',
			requestData, false, 'html');
	// $(formDivName).html(response);
	var divContents = response;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}
function validateTransferLicenseForm(errorList) {

	var errorList = [];
	var checkbox = document.getElementById("checkId");

	if (checkbox.checked == false) {
		errorList.push(getLocalMessage('Please Accept Terms And Condition'));
	}

	return errorList;
}
function showConfirmBoxFoLoiPayment(sucessMsg) {
	debugger;
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ sucessMsg.command.message + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showScrutinyLabel()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function showScrutinyLabel() {
	debugger;
	$.fancybox.close();
	loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule', $(
			'#_appId').val(), $('#_labelId').val(), $('#_serviceId').val());
}

function getOwnerTypeDetails() {

	debugger;
	var ownerType = $("#trdFtype" + " option:selected").attr("code");
	if (ownerType == 'R') {
		$("#agreementDate").show();
	} else {
		$("#agreementDate").hide();
	}

	if (ownerType != undefined) {
		var data = {
			"ownershipType" : ownerType
		};
		var URL = 'TransperLicense.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#owner").html(returnData);
		$("#owner").show();
	} else {
		$("#owner").html("");
	}

}

function validateTradeForm(errorList) {

	var ownershipType = $("#trdFtype").val();
	if (ownershipType == "0" || ownershipType == undefined
			|| ownershipType == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownershipType"));
	}

	return errorList;

}
function validateOwnerDetailsTable() {

	var errorList = [];
	var rowCount = $('#ownerDetail tr').length;

	if ($.fn.DataTable.isDataTable('#ownerDetail')) {
		$('#ownerDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerDetail tbody tr")
				.each(
						function(i) {

							if (rowCount <= 2) {

								var ownerName = $("#troName" + i).val();
								var fatherhusbandName = $("#troMname" + i)
										.val();
								var ownerGender = $("#troGen" + i).val();
								var ownerAddress = $("#troAddress" + i).val();
								var ownerMobileNo = $("#troMobileno" + i).val();
								var ownerAdharNo = $("#troAdhno" + i).val();
								var emailId = $("#troEmailid" + i).val();
								var constant = 1;
							} else {
								var ownerName = $("#troName" + i).val();
								var fatherhusbandName = $("#troMname" + i)
										.val();
								var ownerGender = $("#troGen" + i).val();
								var ownerAddress = $("#troAddress" + i).val();
								var ownerMobileNo = $("#troMobileno" + i).val();
								var ownerAdharNo = $("#troAdhno" + i).val();
								var emailId = $("#troEmailid" + i).val();

								var constant = i + 1;
							}
							if (ownerName == '0' || ownerName == undefined
									|| ownerName == "") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownername")
												+ " " + constant);
							}
							if (fatherhusbandName == ""
									|| fatherhusbandName == undefined
									|| fatherhusbandName == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.fatherhusbandname")
												+ " " + constant);
							}
							if (ownerGender == "" || ownerGender == undefined
									|| ownerGender == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownergender")
												+ " " + constant);
							}
							if (ownerAddress == "" || ownerAddress == undefined
									|| ownerAddress == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.owneraddress")
												+ " " + constant);
							}

							if (ownerMobileNo == ""
									|| ownerMobileNo == undefined
									|| ownerMobileNo == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownerMobileNo")
												+ " " + constant);
							} else {
								if (!validateMobile(ownerMobileNo)) {
									errorList
											.push(getLocalMessage("tradelicense.validation.validMobileNo")
													+ " " + constant);
								}
							}

							if (emailId != "") {
								var emailRegex = new RegExp(
										/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
								var valid = emailRegex.test(emailId);
								if (!valid) {
									errorList
											.push(getLocalMessage('trade.vldnn.emailid')
													+ " " + constant);
								}
							}

							if (ownerAdharNo.length != 0
									&& ownerAdharNo.length < 12) {
								errorList
										.push(getLocalMessage('trade.valid.adharno')
												+ " " + constant);
							}

							if (ownerAdharNo != "") {
								var adharRegex = new RegExp(/^[0-9]\d{12}$/i);
								var valid = adharRegex.test(ownerAdharNo);
								if (!valid) {
									errorList
											.push(getLocalMessage('trade.valid.adharno')
													+ " " + constant);
								}
							}

						});

	return errorList;
}
function validateTransferLicenseNo(errorList) {
	var trdLicno = $("#trdLicno").val();

	if (trdLicno == "" || trdLicno == undefined || trdLicno == "") {
		errorList.push(getLocalMessage("trade.ValidateLicenseNo"));
	}

	return errorList;
}
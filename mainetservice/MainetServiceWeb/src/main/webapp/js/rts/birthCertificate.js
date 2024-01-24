function saveBirthData(element) {
	
	var errorList = [];
	var chargeStatus = $("#chargeStatus").val();
	var amount=$(".amount").val();
	errorList = validateBndData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} 
       else if(amount == "N"){
		errorList.push(getLocalMessage("rts.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
	
	} else if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	}  else if (chargeStatus == "CA") {
		
			if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val() != "N"
					&& $("input[name='offlineDTO.onlineOfflineCheck']:checked")
							.val() != "P") {
				errorList.push(getLocalMessage("rts.paymentType"));
			} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
					.val() == "N") {
				if ($("#oflPaymentMode").val() == 0) {
					errorList.push(getLocalMessage("rts.paymentMode"));
					;
				}
			} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
					.val() == "P") {
				if ($("#payModeIn").val() == 0) {
					errorList.push(getLocalMessage("rts.paymentMode"));
				}
			}
		
			if (errorList.length > 0) {
				checkDate(errorList);
				displayErrorsOnPage(errorList);
			}
			else{ return saveOrUpdateForm(element, "",
				'ApplyforBirthCertificate.html?PrintReport', 'saveform');}
	} else {
		 saveOrUpdateForm(element, "",
				'ApplyforBirthCertificate.html?printAgencyRegAckw', 'saveform');
	//	agencyRegAcknow(status);
	}
}

function agencyRegAcknow(status) {
	
	var title = 'Agency Registration Acknowlegement';
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<html><link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(status);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function validateBndData(errorList) {
	
	var errorList = [];
	var dob = $('#brDob').val();
	var sex = $("#brSex").val();
	var brChildName = $("#brChildName").val();
	var brChildNameMar = $("#brChildNameMar").val();
	var placeOfBrEng = $("#brBirthPlace").val();
	var placeOfBrMar = $("#brBirthPlaceMar").val();
	var brAddEng = $("#brBirthAddr").val();
	var brAddMar = $("#brBirthAddrMar").val();
	var pdFathername = $("#pdFathername").val();
	var pdFathernameMar = $("#pdFathernameMar").val();
	var pdMothername = $("#pdMothername").val();
	var pdMothernameMar = $("#pdMothernameMar").val();
	var motheraddress = $("#motheraddress").val();
	var motheraddressMar = $("#motheraddressMar").val();
	var pdParaddress = $("#pdParaddress").val();
	var pdParaddressMar = $("#pdParaddressMar").val();
	var noOfCopies = $("#noOfCopies").val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var brDob = new Date(dob.replace(pattern, '$3-$2-$1'));
	var rowcount = $("#DeathTable tr").length
	if (dob == "") {
		errorList.push(getLocalMessage("rts.date"));
	}
	if (brDob > currDate) {
		errorList.push(getLocalMessage("rts.val.date"));
	}
	if (sex == "0") {
		errorList.push(getLocalMessage("rts.sex"));
	}
	if (brChildName == "") {
		errorList.push(getLocalMessage("rts.childNameEng"));
	}
	if (brChildNameMar == "") {
		errorList.push(getLocalMessage("rts.chlidNameMar"));
	}
	if (placeOfBrEng == "") {
		errorList.push(getLocalMessage("rts.birthPlaceEng"));
	}
	if (placeOfBrMar == "") {
		errorList.push(getLocalMessage("rts.birthPlaceMar"));
	}
	if (brAddEng == "") {
		errorList.push(getLocalMessage("rts.birthAddEng"));
	}
	if (brAddMar == "") {
		errorList.push(getLocalMessage("rts.birthAddMar"));
	}
	if (pdFathername == "") {
		errorList.push(getLocalMessage("rts.fatherNameEng"));
	}
	if (pdFathernameMar == "") {
		errorList.push(getLocalMessage("rts.fatherNameMar"));
	}
	if (pdMothername == "") {
		errorList.push(getLocalMessage("rts.motherNameEng"));
	}
	if (pdMothernameMar == "") {
		errorList.push(getLocalMessage("rts.motherNameMar"));
	}
	if (pdParaddress == "") {
		errorList.push(getLocalMessage("rts.addressEng"));
	}
	if (pdParaddressMar == "") {
		errorList.push(getLocalMessage("rts.addressMar"));
	}
	if (noOfCopies == "") {
		errorList.push(getLocalMessage("rts.noOfCopies"));
	}
	for (var i = 0; i < rowcount - 1; i++) {
		var checklistUploadedOrNot = $("#checkList" + i).val();
		if (checklistUploadedOrNot == "") {
			errorList.push(getLocalMessage("rts.checklist") + (i + 1));
		}
	}
	/*var chargeStatus = $("#chargeStatus").val();
	if (chargeStatus == "CA") {
		if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val() != "N"
				&& $("input[name='offlineDTO.onlineOfflineCheck']:checked")
						.val() != "P") {
			errorList.push(getLocalMessage("rts.paymentType"));
		} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
				.val() == "N") {
			if ($("#oflPaymentMode").val() == 0) {
				errorList.push(getLocalMessage("rts.paymentMode"));
				;
			}
		} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
				.val() == "P") {
			if ($("#payModeIn").val() == 0) {
				errorList.push(getLocalMessage("rts.paymentMode"));
			}
		}
	}*/
	return errorList;
}

function checkDate(errorList) {

	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}

$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});

});

function getAmountOnNoOfCopes() {

	var errorList = [];
	var chargeStatus = $("#chargeStatus").val();
	if (chargeStatus == 'CA') {
		var form_url = $("#frmBirthCertificateForm").attr("action");
		var url = 'ApplyforBirthCertificate.html?getBNDCharge';
		var isscopy = $("#alreayIssuedCopy").val();
		if (isscopy == '' || isscopy == undefined) {
			isscopy = 0;
		}
		if ($('#noOfCopies').val() != '' && $('#noOfCopies').val() != undefined) {
			var requestData = "noOfCopies=" + $('#noOfCopies').val()
					+ "&issuedCopy=" + isscopy;
			var returnData = __doAjaxRequest(url, 'post', requestData, false,
					'json');
			$(".amount").val(returnData);
			if (returnData == "N") {
				$('#amount').hide();
				$('.amount').hide();
				$('#payId').hide();
				errorList.push(getLocalMessage("rts.validation.brmscharges"));
				displayErrorsOnPage(errorList);
			}
			if (returnData == '0' || returnData == "N") {
				$('#payId').hide();
				$('#amountid').hide();
			} else {
				$('#payId').show();
				$('#amountid').show();
			}
		} 
	}
}

function resetMemberMaster(element){
	//resetForm(element);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ApplyforBirthCertificate.html?resetBirthForm', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function previousPage()
{  
	var applicationId =$("#apmApplicationId").val();
	
	if(applicationId == "" || applicationId == null || applicationId== undefined)
		{
		applicationId ="0";
		}
	
	var divName = '.content-page';
	requestData =
	{"applicationId":applicationId};
	

	var ajaxResponse = doAjaxLoading('rtsService.html?applicantForm', requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

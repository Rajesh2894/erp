$(document).ready(function() {
	// prepareDateTag();

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(2016, 11, 31),
		yearRange : "-100:-0"
	});
});

function getAmountOnNoOfCopes() {
	
	var errorList = [];
	var chargeStatus = $("#chargeStatus").val();
	var brDob = $("#brDob").val();
	if (chargeStatus == 'CA') {
		var form_url = $("#frmNacForBirthRegForm").attr("action");
		var url = 'NacForBirthReg.html?getBNDCharge';
		var isscopy = $("#alreayIssuedCopy").val();
		if (isscopy == '' || isscopy == undefined) {
			isscopy = 0;
		}
		if ($('#noOfCopies').val() == '' || $('#noOfCopies').val() == undefined ) {
			errorList.push(getLocalMessage("death.label.demandcop"));
		}
		if (brDob == "") {
			errorList.push(getLocalMessage("BirthRegDto.BrDt"));
		}
		if (errorList.length > 0) {
			  displayErrorsOnPage(errorList);
		  }else{
			var requestData = "noOfCopies=" + $('#noOfCopies').val()
					+ "&issuedCopy=" + isscopy+ "&brDob=" + brDob;
			var returnData = __doAjaxRequest(url, 'post', requestData, false,
					'json');
			$(".amount").val(returnData);
			if (returnData == "N") {
				$('#amount').hide();
				$('.amount').hide();
				$('#payId').hide();
				errorList.push(getLocalMessage("bnd.validation.brmscharges"));
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

function saveBirthData(element) {

	var errorList = [];
	var chargeStatus = $("#chargeStatus").val();
	var amount = $(".amount").val();
	errorList = validateBndData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else if (amount == "N") {
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
		displayErrorsOnPage(errorList);
	} else if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else if (chargeStatus == "CA") {

		if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val() != "N"
				&& $("input[name='offlineDTO.onlineOfflineCheck']:checked")
						.val() != "P") {
			errorList.push(getLocalMessage("bnd.paymentType"));
		} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
				.val() == "N") {
			if ($("#oflPaymentMode").val() == 0) {
				errorList.push(getLocalMessage("bnd.paymentMode"));
				;
			}
		} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
				.val() == "P") {
			if ($("#payModeIn").val() == 0) {
				errorList.push(getLocalMessage("bnd.paymentMode"));
			}
		}

		if (errorList.length > 0) {
			checkDate(errorList);
			displayErrorsOnPage(errorList);
		} else {
			 saveOrUpdateForm(element, "",
					'NacForBirthReg.html?PrintReport', 'saveform');
			 bndRegAcknow();
		}
	} else {
		saveOrUpdateForm(element, "", 'NacForBirthReg.html',
				'saveform');
		bndRegAcknow();
	}
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
		errorList.push(getLocalMessage("bnd.date"));
	}
	if (brDob > currDate) {
		errorList.push(getLocalMessage("bnd.val.date"));
	}
	if (sex == "0") {
		errorList.push(getLocalMessage("bnd.sex"));
	}
	if (brChildName == "") {
		errorList.push(getLocalMessage("bnd.childNameEng"));
	}
	if (brChildNameMar == "") {
		errorList.push(getLocalMessage("bnd.chlidNameMar"));
	}
	if (placeOfBrEng == "") {
		errorList.push(getLocalMessage("bnd.birthPlaceEng"));
	}
	if (placeOfBrMar == "") {
		errorList.push(getLocalMessage("bnd.birthPlaceMar"));
	}
	if (brAddEng == "") {
		errorList.push(getLocalMessage("bnd.birthAddEng"));
	}
	if (brAddMar == "") {
		errorList.push(getLocalMessage("bnd.birthAddMar"));
	}
	if (pdFathername == "") {
		errorList.push(getLocalMessage("bnd.fatherNameEng"));
	}
	if (pdFathernameMar == "") {
		errorList.push(getLocalMessage("bnd.fatherNameMar"));
	}
	if (pdMothername == "") {
		errorList.push(getLocalMessage("bnd.motherNameEng"));
	}
	if (pdMothernameMar == "") {
		errorList.push(getLocalMessage("bnd.motherNameMar"));
	}
	if (pdParaddress == "") {
		errorList.push(getLocalMessage("bnd.addressEng"));
	}
	if (pdParaddressMar == "") {
		errorList.push(getLocalMessage("bnd.addressMar"));
	}
	if (noOfCopies == "") {
		errorList.push(getLocalMessage("bnd.noOfCopies"));
	}
	for (var i = 0; i < rowcount - 1; i++) {
		var checklistUploadedOrNot = $("#checkList" + i).val();
		if (checklistUploadedOrNot == "") {
			errorList.push(getLocalMessage("bnd.checklist") + (i + 1));
		}
	}
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

function resetMemberMaster(element) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('NacForBirthReg.html?resetBirthForm', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}


function previousPage(){  
	
	var applicationId =$("#apmApplicationId").val();
	
	if(applicationId == "" || applicationId == null || applicationId== undefined)
		{
		applicationId ="0";
		}
	
	var divName = '.content-page';
	requestData =
	{"applicationId":applicationId};
	

	var ajaxResponse = doAjaxLoading('NacForBirthReg.html?backOnApplicantForm', requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	prepareTags();
}

function bndRegAcknow(element) {
	var URL = 'NacForBirthReg.html?printBndAcknowledgement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	if(returnData!=null && returnData!=""){
	var title = 'Birth Registration Correction Acknowlegement';
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
}
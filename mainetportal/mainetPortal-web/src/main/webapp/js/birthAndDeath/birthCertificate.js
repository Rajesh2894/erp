function saveBirthData(element) {
	
	var errorList = [];
	var applicationChargeFlag = $("#applicationChargeFlag").val();
	var amount = $(".amount").val();
	errorList = validateBndData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else if(applicationChargeFlag=="N"){
		 saveOrUpdateForm(
				element,
				"",
				'IssuanceBirthCertificate.html', 'saveform');
		bndRegAcknow();
	}
	else if (amount == "N") {
		errorList.push(getLocalMessage("rts.validation.brmscharges"));
		displayErrorsOnPage(errorList);
	} else if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} /*
		 * else if(chargeStatus=="CA"){
		 * 
		 * return saveOrUpdateForm(element, "",
		 * 'ApplyforBirthCertificate.html?PrintReport', 'saveform'); }
		 */
	else {
		if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val() != "Y") {
			errorList.push(getLocalMessage("bnd.paymentType"));
		}
		if (errorList.length > 0) {
			checkDate(errorList);
			displayErrorsOnPage(errorList);
		} else {
			var status = saveOrUpdateForm(element, "",
					'IssuanceBirthCertificate.html?redirectToPay', 'saveform');
			bndRegAcknow();
		}
	}
}


function validateBndData(errorList){
	
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
	var rowcount=$("#DeathTable tr").length 
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
	/*for (var i = 0; i < rowcount - 1; i++) {
		var checklistUploadedOrNot = $("#checkList" + i).val();
		if (checklistUploadedOrNot == "") {
			errorList.push(getLocalMessage("rts.checklist") + (i + 1));
		}
	}*/
	//var chargeStatus = $("#chargeStatus").val();
	//if(chargeStatus=="CA"){
		/*if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="Y" )
		{
		errorList.push("Select Payment Type");
		}
		if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="Y")
		{
		errorList.push("Payment Gateway Not Defined");
		}	*/
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
	
	var errorsList = [];
	var applicationChargeFlag = $("#applicationChargeFlag").val();
	//if (applicationChargeFlag == 'Y') {
	var form_url = $("#frmBirthCertificateForm").attr("action");
	var url = 'bndApplyforBirthCertificate.html?getBNDCharge';
	var isscopy = 0;
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
			errorsList.push(getLocalMessage("rts.validation.brmscharges"));
			displayErrorsOnPage(errorsList);
		}
		if (returnData == '0' || returnData == "N") {
			$('#payId').hide();
			$('#amountid').hide();
		} else {
			$('#payId').show();
			$('#amountid').show();
		}
	} else {
		errorsList.push("Enter Minimum 1 Demanded copy");
		displayErrorsOnPage(errorsList);
	}
	// }
}

function resetSchemeApplicationForm()
{
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'bndApplyforBirthCertificate.html?resetBirthForm', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function bndRegAcknow(element) {
	
	var URL = 'bndApplyforBirthCertificate.html?printBndAcknowledgement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'Birth Registration Correction Acknowlegement';
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
			.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

$(document).ready(function() {
	//D#127333 Translator
	  var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$('#brChildName').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#brChildName").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'brChildNameMar',event,'');
				}else{
					$("#brChildNameMar").val('');
				}
			});
			$('#brBirthPlace').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#brBirthPlace").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'brBirthPlaceMar',event,'');
				}else{
					$("#brBirthPlaceMar").val('');
				}
			});
			$('#brBirthAddr').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#brBirthAddr").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'brBirthAddrMar',event,'');
				}else{
					$("#brBirthAddrMar").val('');
				}
			});
			$('#pdFathername').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#pdFathername").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'pdFathernameMar',event,'');
				}else{
					$("#pdFathernameMar").val('');
				}
			});
		
			$('#pdMothername').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#pdMothername").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'pdMothernameMar',event,'');
				}else{
					$("#pdMothernameMar").val('');
				}
			});
			$('#pdParaddress').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#pdParaddress").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'pdParaddressMar',event,'');
				}else{
					$("#pdParaddressMar").val('');
				}
			});
		}
		
});
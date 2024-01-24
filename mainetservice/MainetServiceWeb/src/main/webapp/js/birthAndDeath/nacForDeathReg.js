$(document).ready(function() {
	// prepareDateTag();

	/*
	 * $('.datepicker').datepicker({ dateFormat : 'dd/mm/yy', changeMonth :
	 * true, changeYear : true, maxDate : new Date(2016, 11, 31), yearRange :
	 * "-100:-0" });
	 */
});

function getAmount(element) {
	
	var errorList = [];
	var demandedCopies = $("#demandedCopies").val();
	var chargeStatus = $("#chargeStatus").val();
	var drDod = $("#drDod").val();
	var issuedCopies = 0;
	if (chargeStatus == "CA") {
		
		
		  if (demandedCopies == 0 || demandedCopies == "" || demandedCopies ==
		  undefined) {
		 errorList.push(getLocalMessage("death.label.demandcop"));
		 //displayErrorsOnPage(errorList);
		   } 
		  if (drDod == "") {
			  errorList.push(getLocalMessage("TbDeathregDTO.label.drDodblank"));
		  }
		  if (errorList.length > 0) {
			  displayErrorsOnPage(errorList);
		  }else {
		 
		var url = "NacForDeathReg.html?getBNDCharge";
		var requestData = "demandedCopies=" + demandedCopies + "&issuedCopies="
				+ issuedCopies + "&drDod="
				+ drDod;

		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		$(".amount").val(returnData);
		$("#chargesAmount").val(returnData);
		if (returnData == "N") {
			$('#payId').hide();
			$('#amount').hide();
			$('.amount').hide();
			errorList.push(getLocalMessage("bnd.validation.brmscharges"));
			displayErrorsOnPage(errorList);
		}

		if (returnData == 0 || returnData == 0.0) {
			$('#payId').hide();
		}
		 } 
	}
}

function saveDeathCertificateData(element) {

	var errorList = [];
	var chargeStatus = $("#chargeStatus").val();
	var amount = $(".amount").val();
	errorList = validateBndData(element);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if (amount == "N") {
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
		displayErrorsOnPage(errorList);
	} else if (errorList.length > 0) {
		// checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else if (chargeStatus != "CA" || amount == 0 || amount == ""
			|| amount == undefined) {
		saveOrUpdateForm(element, "", 'NacForDeathReg.html',
				'saveform');
		bndRegAcknow();
	} else {
		if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val() != "N"
				&& $("input[name='offlineDTO.onlineOfflineCheck']:checked")
						.val() != "P") {
			errorList.push(getLocalMessage("bnd.paymentType"));

		} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
				.val() == "N") {
			if ($("#oflPaymentMode").val() == 0) {
				errorList.push(getLocalMessage("bnd.paymentMode"));
			}
		} else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked")
				.val() == "P") {
			if ($("#payModeIn").val() == 0) {
				errorList.push(getLocalMessage("bnd.paymentMode"));
			}
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		} else {
			 saveOrUpdateForm(element, "",
					'NacForDeathReg.html?PrintReport', 'saveform');
			bndRegAcknow();
		}
	}
}

function validateBndData(element) {
	var errorList = [];
	var drDod = $('#drDod').val();
	var drSex = $("#drSex").val();
	var drDeceasedname = $("#drDeceasedname").val();
	var drMarDeceasedname = $("#drMarDeceasedname").val();
	var drRelativeName = $("#drRelativeName").val();
	var drMarRelativeName = $("#drMarRelativeName").val();
	var drMotherName = $("#drMotherName").val();
	var drMarMotherName = $("#drMarMotherName").val();
	var drDeceasedaddr = $("#drDeceasedaddr").val();
	var drMarDeceasedaddr = $("#drMarDeceasedaddr").val();
	var drDcaddrAtdeath = $("#drDcaddrAtdeath").val();
	var drDcaddrAtdeathMar = $("#drDcaddrAtdeathMar").val();
	var drDeathplace = $("#drDeathplace").val();
	var drMarDeathplace = $("#drMarDeathplace").val();
	var demandedCopies = $("#demandedCopies").val();
	var offlinebutton = $("#offlinebutton").val();
	var payAtCounter = $("#payAtCounter").val();
	var offlineModeFlagId = $("#offlineModeFlagId").val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drdod = new Date(drDod.replace(pattern, '$3-$2-$1'));

	var rowcount = $("#DeathTable tr").length

	for (var i = 0; i < rowcount - 1; i++) {
		var checklistUploadedOrNot = $("#checkList" + i).val();
		if (checklistUploadedOrNot == "") {
			errorList.push(getLocalMessage("death.label.upload"));
		}
	}

	if (drDod == "") {
		errorList.push(getLocalMessage("death.label.drDod"));
	}
	if (drdod > currDate) {
		errorList.push(getLocalMessage("death.label.drDodcurrDate"));
	}

	if (drSex == "0") {
		errorList.push(getLocalMessage("death.label.drSex"));
	}

	if (drDeceasedname == "") {
		errorList.push(getLocalMessage("death.label.drDeceasedname"));
	}
	if (drMarDeceasedname == "") {
		errorList.push(getLocalMessage("death.label.drMarDeceasedname"));
	}
	if (drRelativeName == "") {
		errorList.push(getLocalMessage("death.label.drRelativeName"));
	}
	if (drMarRelativeName == "") {
		errorList.push(getLocalMessage("death.label.drMarRelativeName"));
	}
	if (drMotherName == "") {
		errorList.push(getLocalMessage("death.label.drMotherName"));
	}
	if (drMarMotherName == "") {
		errorList.push(getLocalMessage("death.label.drMarMotherName"));
	}
	if (drDeceasedaddr == "") {
		errorList.push(getLocalMessage("death.label.drDeceasedaddr"));
	}
	if (drMarDeceasedaddr == "") {
		errorList.push(getLocalMessage("death.label.drMarDeceasedaddr"));
	}
	if (drDcaddrAtdeath == "") {
		errorList.push(getLocalMessage("death.label.drDcaddrAtdeath"));
	}
	if (drDcaddrAtdeathMar == "") {
		errorList.push(getLocalMessage("death.label.drDcaddrAtdeathMar"));
	}

	if (drDeathplace == "") {
		errorList.push(getLocalMessage("death.label.drDeathplace"));
	}
	if (drMarDeathplace == "") {
		errorList.push(getLocalMessage("death.label.drMarDeathplace"));
	}

	if (demandedCopies == 0 || demandedCopies == ""
			|| demandedCopies == undefined) {
		errorList.push(getLocalMessage("death.label.demandcop"));
	}
	return errorList;
}

function resetMemberMaster(element) {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('NacForDeathReg.html?resetDeathForm', {},
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
	

	var ajaxResponse = doAjaxLoading('NacForDeathReg.html?backOnApplicantForm', requestData, 'html',
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
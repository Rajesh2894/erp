function resetDeathData() {
	window.open('DeathRegistration.html', '_self');
}

function saveDeathCertificateData(element) {
	
	var errorList = [];
	var amount=$(".amount").val();
	var applicationChargeFlag = $("#applicationChargeFlag").val();
	errorList = validateBndData(element);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if(applicationChargeFlag=="N"){
		 saveOrUpdateForm(
				element,
				"",
				'IssuanceDeathCertificate.html', 'saveform');
		bndRegAcknow();
	}else if(amount == "N"){
		errorList.push(getLocalMessage("rts.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
	} else {
		
		if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="Y")
		{
			errorList.push(getLocalMessage("rts.paymentType"));
		}
		 if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			} else {
	 saveOrUpdateForm(element, "Application Saved Successfully", 'IssuanceDeathCertificate.html?redirectToPay', 'saveform');
     bndRegAcknow();			
}
   }

}

function validateBndData(element){
	
	var errorList = [];
	var drDod = $('#drDod').val();
	var drSex = $("#drSex").val();
	var drDeceasedname = $("#drDeceasedname").val();
	var drMarDeceasedname = $("#drMarDeceasedname").val();
	var drRelativeName = $("#drRelativeName").val();
	var drMarRelativeName  = $("#drMarRelativeName").val();
	var drMotherName = $("#drMotherName").val();
	var drMarMotherName=$("#drMarMotherName").val();
	var drDeceasedaddr = $("#drDeceasedaddr").val();
	var drMarDeceasedaddr = $("#drMarDeceasedaddr").val();
	var drDcaddrAtdeath = $("#drDcaddrAtdeath").val();
	var drDcaddrAtdeathMar = $("#drDcaddrAtdeathMar").val();
	var drDeathplace = $("#drDeathplace").val();
	var drMarDeathplace = $("#drMarDeathplace").val();
	var demandedCopies = $("#demandedCopies").val();
	var offlinebutton=$("#offlinebutton").val();
	var payAtCounter=$("#payAtCounter").val();
	var offlineModeFlagId=$("#offlineModeFlagId").val();
	var currDate = new Date();
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var drdod = new Date(drDod.replace(pattern, '$3-$2-$1'));
	var rowcount=$("#DeathTable tr").length 
	/*for(var i=0;i<rowcount-1;i++){
	 var checklistUploadedOrNot=$("#checkList"+i).val();
	 if(checklistUploadedOrNot==""){
		 errorList.push(getLocalMessage("TbDeathregDTO.label.upload"));
        }
    }*/
	if (drDod == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDod"));
	}
	if (drdod > currDate) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDodcurrDate"));
	}
	if (drSex == "0") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drSex"));
	}
	if (drDeceasedname == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeceasedname"));
	}
	if (drMarDeceasedname == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeceasedname"));
	}
	if (drRelativeName == "" ) {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drRelativeName"));
	}
	if (drMarRelativeName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarRelativeName"));
	}
	if (drMotherName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMotherName"));
	}
	if (drMarMotherName == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarMotherName"));
	}
	if (drDeceasedaddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeceasedaddr"));
	}
	if (drMarDeceasedaddr == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeceasedaddr"));
	}
	if (drDcaddrAtdeath == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDcaddrAtdeath"));
	}
	if (drDcaddrAtdeathMar == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDcaddrAtdeathMar"));
	}
	if (drDeathplace == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drDeathplace"));
	}
	if (drMarDeathplace == "") {
		errorList.push(getLocalMessage("TbDeathregDTO.label.drMarDeathplace"));
	}
	if(demandedCopies == 0 || demandedCopies == "" || demandedCopies == undefined){
		errorList.push(getLocalMessage("TbDeathregDTO.label.demandcop"));
	}
	/*if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="Y")
	{
		errorList.push(getLocalMessage("rts.paymentType"));
	}
	 if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="Y")
		{
		errorList.push("Payment Gateway Not Defined");
		}	*/		
	return errorList;
}

function resetMemberMaster(element){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'bndapplyForDeathCertificate.html?resetDeathForm', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function getAmount(element){

	var errorList = [];
	var demandedCopies = $("#demandedCopies").val();
	var chargeStatus =$("#chargeStatus").val();
	var issuedCopies= 0;
	if(demandedCopies == 0 || demandedCopies == "" || demandedCopies == undefined){
		errorList.push("Enter Minimum 1 Demanded copy");
 		displayErrorsOnPage(errorList);
 		
	}else
		{
		var url = "bndApplyForDeathCertificate.html?getBNDCharge";
		var requestData = "demandedCopies=" + demandedCopies + "&issuedCopies="
				+ issuedCopies;
		var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
		$(".amount").val(returnData);
		if(returnData == "N")
		{
		$('#payId').hide();
		$('#amount').hide();
		$('.amount').hide();
		errorList.push(getLocalMessage("rts.validation.brmscharges"));
		 displayErrorsOnPage(errorList);
		}
		}
	
}

$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
	});
});


function bndRegAcknow(element) {
	
	var URL = 'bndApplyForDeathCertificate.html?printBndAcknowledgement';
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
			$('#drDeceasedname').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#drDeceasedname").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'drMarDeceasedname',event,'');
				}else{
					$("#drMarDeceasedname").val('');
				}
			});
			$('#drRelativeName').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#drRelativeName").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'drMarRelativeName',event,'');
				}else{
					$("#drMarRelativeName").val('');
				}
			});
			$('#drMotherName').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#drMotherName").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'drMarMotherName',event,'');
				}else{
					$("#drMarMotherName").val('');
				}
			});
			$('#drDeceasedaddr').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#drDeceasedaddr").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'drMarDeceasedaddr',event,'');
				}else{
					$("#drMarDeceasedaddr").val('');
				}
			});
			$('#drDcaddrAtdeath').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#drDcaddrAtdeath").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'drDcaddrAtdeathMar',event,'');
				}else{
					$("#drDcaddrAtdeathMar").val('');
				}
			});
			$('#drDeathplace').bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#drDeathplace").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'drMarDeathplace',event,'');
				}else{
					$("#drMarDeathplace").val('');
				}
			});
		}
});
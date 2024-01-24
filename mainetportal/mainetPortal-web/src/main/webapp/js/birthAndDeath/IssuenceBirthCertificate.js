function searchBirthData() {
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
		showloader(false);
		$('#frmIssuCertificateForm').trigger("reset");
	} else {
		var url = "IssuanceBirthCertificate.html?searchBirthDetail";
		var requestData = "brCertNo=" + $('#brManualCertNo').val()    //need to be change brManualcertNo
				+ "&applicationId=" + $('#applicationId').val() + "&year="
				+ $("#year").val() + "&brRegNo=" + $("#brRegNo").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		if(returnData.brStatus == "A")
			{
		if(returnData.birthWfStatus == "OPEN")
			{
			errorsList.push(getLocalMessage("BirthRegistrationDTO.call.norecord"));
			$('#frmIssuCertificateForm').trigger("reset");
			displayErrorsOnPage(errorsList);
			
			}
		else if (returnData.birthWfStatus == "APPROVED" || returnData.birthWfStatus == "CLOSED" || returnData.birthWfStatus == "REJECTED") {
			$('#brChildName').val(returnData.brChildName);
			$('#brChildNameMar').val(returnData.brChildNameMar);
			$('#brSex').val(returnData.brSex);
			$('#brDob').val(getDateFormat(returnData.brDob));
			$('#pdFathername').val(returnData.parentDetailDTO.pdFathername);
			$('#pdFathernameMar').val(returnData.parentDetailDTO.pdFathernameMar);
			$('#pdMothername').val(returnData.parentDetailDTO.pdMothername);
			$('#pdMothernameMar').val(returnData.parentDetailDTO.pdMothernameMar);
			$('#brBirthPlace').val(returnData.brBirthPlace);
			$('#brBirthPlaceMar').val(returnData.brBirthPlaceMar);
			$('#brBirthAddr').val(returnData.brBirthAddr);
			$('#brBirthAddrMar').val(returnData.brBirthAddrMar);
			$('#brCertNo').val(returnData.brCertNo);
			$('#brId').val(returnData.brId);
			$('#alreayIssuedCopy').val(returnData.alreayIssuedCopy);
			$('#noOfCopies').val(returnData.noOfCopies);
			// $(formDivName).html(returnData);
			// $("#RegisDetail").show();
		}
			}
		else {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"))
			$('#frmIssuCertificateForm').trigger("reset");
			displayErrorsOnPage(errorsList);
		}
	}
	},2);
}

function searchDeathData() {
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
		showloader(false);
		$('#frmIssuCertificateForm').trigger("reset");
	} else {
		var url = "IssuanceDeathCertificate.html?searchDeathDetail";
		var requestData = "drCertNo=" + $('#drManualCertno').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applicationId=" + $("#applicationNo").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'html');
		
		if((returnData.drStatus=="Y") && (returnData.DeathWFStatus=="OPEN"))
			{
			errorsList.push(getLocalMessage("BirthRegistrationDTO.call.norecord"))
			  $("#frmIssuCertificateForm")[0].reset();
			displayErrorsOnPage(errorsList);
			}
		if (returnData != 'Internal Server Error.') {
			$(".content-page").html(returnData);
		} else {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"))
			  $("#frmIssuCertificateForm")[0].reset();
			displayErrorsOnPage(errorsList);
		}
	}
    },2);
}

function validateBirthSearchForm(errorsList) {

	var certNo = $('#brManualCertNo').val();
	var regDate = $("#year").val();
	var regisNo = $("#brRegNo").val();
	var brDob = $('#brDob').val();
	var brChildName = $('#brChildName').val();
	// validate the year
	validatedates(errorsList);
	if (regDate == "" && regisNo == "" && brDob == "" && brChildName == "") {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	}else if(((regDate != "" && regisNo == "")||(regDate == "" && regisNo != ""))&&(brChildName != "" && brDob== "")){
		errorsList.push(getLocalMessage("bnd.yearAndRegNo"));
		errorsList.push(getLocalMessage("bnd.date"));
	}else if((regDate != "" && regisNo == "")||(regDate == "" && regisNo != "")){
		errorsList.push(getLocalMessage("bnd.yearAndRegNo"));
	}
	else if((brChildName != "" && brDob== "")){
		errorsList.push(getLocalMessage("bnd.date"));
	}else {
		//go for search
	}

	return errorsList;
}

function validateDeathSearchForm(errorsList) {

	var certNo = $('#drManualCertno').val();
	var regDate = $("#year").val();
	var regisNo = $("#drRegno").val();
	var drDod = $("#drDod").val();
	var drDeceasedname = $("#drDeceasedname").val();
	// validate the year
	validatedates(errorsList);
	if (drDod == "" && regDate == "" && regisNo == "" && drDeceasedname == "") {
		errorsList
				.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	}else if(((regDate != "" && regisNo == "")||(regDate == "" && regisNo != ""))&&((drDod != "" && drDeceasedname == "")||(drDod == "" && drDeceasedname != ""))){
		errorsList.push(getLocalMessage("bnd.yearAndRegNo"));
		errorsList.push(getLocalMessage("bnd.drDodAndDeceasedName"));
	}else if((regDate != "" && regisNo == "")||(regDate == "" && regisNo != "")){
		errorsList.push(getLocalMessage("bnd.yearAndRegNo"));
	}else if(drDod == "" && drDeceasedname != ""){
		errorsList.push(getLocalMessage("bnd.drDod"));
	}else {
		//go for search
	}
	return errorsList;
}

function saveBirthCertFormAndGenerateAppNo(element) {

	var errorList = [];
	var amount = $('#amount').val();
 	var chargeStatus = $("#chargeStatus").val();
 	//Division validation added 04/08/22
 	var bndDw1=$('#bndDw1').val();
 	if (bndDw1 == 0 || bndDw1 == '') {
 		errorList.push(getLocalMessage("bnd.div.validate"));
		displayErrorsOnPage(errorList);
	}
 	if(amount == "N"){
 		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
 	}else{
 	if($('#noOfCopies').val()=="" || $('#noOfCopies').val()==undefined || $('#noOfCopies').val()==0){
		errorList.push(getLocalMessage("TbDeathregDTO.label.valdinfornoofcopies"));
	}
 	if(chargeStatus == "CA" && amount !="0.0" && amount !="0" && amount !=null)
 		{
 		if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="Y")
	{
	errorList.push(getLocalMessage("bnd.paymentType"));
	}
	
	else if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="N")
		{
		if($("#oflPaymentMode").val()==0 ) 
		{
		errorList.push("Select Payment Mode");}
		}else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="P")
			{
			if( $("#payModeIn").val()==0)
				{
				errorList.push(getLocalMessage("bnd.paymentMode"));
		       }
			}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else
			{
			 saveOrUpdateForm(element,
					"Application Number generation done successfully",
					'IssuanceBirthCertificate.html?redirectToPay', 'saveform');
			bndRegAcknow('IssuanceBirthCertificate.html?printBndAcknowledgement');
			}
			}
	else{
		if (chargeStatus != 'CA' || amount == "0" || amount==null || amount == "0.0") {
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			}
			else {
				 saveOrUpdateForm(element,
						"Application Number generation done successfully",
						'IssuanceBirthCertificate.html', 'saveform');
				bndRegAcknow('IssuanceBirthCertificate.html?printBndAcknowledgement');
				}
			}
		}
 	}
}

function saveDeathCertFormAndGenerateAppNo(element) {
	
	var errorList = [];
	var amount = $('#amount').val();
 	var chargeStatus = $("#chargeStatus").val();
 	//Division validation added 04/08/22
	var bndDw1=$('#bndDw1').val();
 	if (bndDw1 == 0 || bndDw1 == '') {
 		errorList.push(getLocalMessage("bnd.div.validate"));
		displayErrorsOnPage(errorList);
	}
 	if(amount=="N"){
 		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
 	}else{
 	if($('#numberOfCopies').val()=="" || $('#numberOfCopies').val()==undefined || $('#numberOfCopies').val()==0){
		errorList.push(getLocalMessage("TbDeathregDTO.label.valdinfornoofcopies"));
	}
 	if(chargeStatus == "CA" && amount !="0.0" && amount !="0" && amount !=null)
 		{
 		if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="Y")
	{
	errorList.push("Select Payment Type");
	}
	
	else if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="N")
		{
		if($("#oflPaymentMode").val()==0 ) 
		{
		errorList.push(getLocalMessage("bnd.paymentMode"));}
		}else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="P")
			{
			if( $("#payModeIn").val()==0)
				{
				errorList.push(getLocalMessage("bnd.paymentMode"));
		       }
			}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else
			{
			  saveOrUpdateForm(element,
						"Application Number generation done successfully",
						'IssuanceDeathCertificate.html?redirectToPay', 'saveform');
			 bndRegAcknow('IssuanceDeathCertificate.html?printBndAcknowledgement');
			}
			}
	else{
		if (chargeStatus != 'CA' || amount == "0" || amount==null || amount =="0.0") {
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			}
			else {
				 saveOrUpdateForm(element,
						"Application Number generation done successfully",
						'IssuanceDeathCertificate.html', 'saveform');
				 bndRegAcknow('IssuanceDeathCertificate.html?printBndAcknowledgement');
				}
			}
		}
 	}
}

function OffLinePayment() {
	
	$('#PaymnetMode').show();
}

function validatedates(errorList) {
	
	$('.error-div').hide();
	var name = "Issuance certificate";
	var trTenderDate;
	if (($("#year").val()) != undefined) {
		trTenderDate = $("#year").val();
	}
	if (trTenderDate != null && trTenderDate != "") {
		var yy = parseInt(trTenderDate);
		if (yy < 1902 || yy > (new Date()).getFullYear()) {
			errorList.push("Invalid value for year: " + yy
					+ " - must be between 1902 and "
					+ (new Date()).getFullYear());
		} else if (yy < 1902) {
			errorList.push("Invalid value for year: " + yy
					+ " - must be between 1902 and "
					+ (new Date()).getFullYear());
		}
	}
	return errorList;
}


// this common for both Issance of Birth and Death service
function getAmountOnNoOfCopes(){
	
 	var errorsList= [];
 	var chargeStatus = $("#chargeStatus").val();
 	if (chargeStatus == 'CA') {
 	var form_url = $("#frmIssuCertificateForm").attr("action");
  	var url=form_url+'?getBNDCharge';
 	var isscopy=$("#alreayIssuedCopy").val();
 	if(isscopy=='' || isscopy==undefined ){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined && $('#noOfCopies').val()!=0){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
	 $("#amountToPay").val(returnData);
	 if(returnData=='N'){
		 $("#amountid").hide();
		 $("#amountToPay").val("0.0");
		 
	 }else{
		 $("#amountid").show();
	 }
	 
 	}
 	else if($('#numberOfCopies').val()!='' && $('#numberOfCopies').val()!=undefined && $('#numberOfCopies').val()!=0){
 		var requestData = "noOfCopies=" + $('#numberOfCopies').val()+ "&issuedCopy=" +isscopy;
 		var returnData = __doAjaxRequest(url, 'post', requestData, false,
 				'json');  
 		 $("#amount").val(returnData);
 		$("#amountToPay").val(returnData);
 		if(returnData=='N'){
 			 $("#amountid").hide();
 			$("#amountToPay").val("0.0");
 			
 		 }else{
 			 $("#amountid").show();
 		 }
 		
 	}
 	else{
 		errorsList.push(getLocalMessage("TbDeathregDTO.label.valdinfornoofcopies"));
 		displayErrorsOnPage(errorsList);
 	}
 	}
}
$(document).ready(function(){
	var chargeStatus = $("#chargeStatus").val();
 	if (chargeStatus == 'CA') {
 	var form_url = $("#frmIssuCertificateForm").attr("action");
  	var url=form_url+'?getBNDCharge';
 	var isscopy=$("#alreayIssuedCopy").val();
 	if(isscopy=='' || isscopy==undefined ){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined && $('#noOfCopies').val()!=0){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
	 $("#amountToPay").val(returnData);
	 if(returnData=='N'){
		 $("#amountid").hide();
		 $("#amountToPay").val("0.0");
		 
	 }else{
		 $("#amountid").show();
	 }
	 
 	}
 	else if($('#numberOfCopies').val()!='' && $('#numberOfCopies').val()!=undefined && $('#numberOfCopies').val()!=0){
 		var requestData = "noOfCopies=" + $('#numberOfCopies').val()+ "&issuedCopy=" +isscopy;
 		var returnData = __doAjaxRequest(url, 'post', requestData, false,
 				'json');  
 		 $("#amount").val(returnData);
 		$("#amountToPay").val(returnData);
 		if(returnData=='N'){
 			 $("#amountid").hide();
 			$("#amountToPay").val("0.0");
 			
 		 }else{
 			 $("#amountid").show();
 		 }
 		
 	}
 	}
 	
 	
 	var drDod = $('#drDod').val();
 	if (drDod)
 		$('#drDod').val(drDod.split(' ')[0]);

});
function SearchDeathCertificateData() {
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
		showloader(false);
	} else {
		var table = $('#deathCorrDataTable').DataTable();
		var url = "DeathRegistrationCorrection.html?searchDeathCorrection";
		var requestData = "drCertNo=" + $('#drCertNo').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applnId=" + '0'
				+ "&drDod=" + $("#drDod").val()+"&drDeceasedname=" + $("#drDeceasedname").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		
		table.rows().remove().draw();
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
			//displayMessageOnSubmit('ADC',"IssuanceDeathCertificate.html?applyForDeathCert");
		}else{
			var n=0;
		
		var result = [];
		$
				.each(
						returnData,
						function(index) {
							var obj = returnData[index];
							if(obj.drStatus == "Y"){
								n++;
							let drId = obj.drId;
							let drDod = obj.drDod;
							let drDeceasedname=obj.drDeceasedname;
							let drRegno = obj.drRegno;
							let drSex = obj.drSex;
							// let cpdRegUnit = obj.cpdDesc;
							let drRelativeName = obj.drRelativeName;
							let drMotherName = obj.drMotherName;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drDod) + '</div>',
											'<div class="text-center">'
													+ drRegno + '</div>',
											'<div class="text-center">'
													+ drDeceasedname + '</div>',
											'<div class="text-center">'
													+ drSex + '</div>',
											'<div class="text-center">'
													+ drRelativeName + '</div>',
											'<div class="text-center">'
													+ drMotherName + '</div>',
											/*'<div class="text-center">'
													+ cpdRegUnit + '</div>',*/
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-3 btn-sm margin-right-5"  onclick="modifyDeath(\''
													+ drId
													+ '\',\'IssuanceDeathCertificate.html\',\'editBND\',\'A\')" title="Issue certificate"><i class="fa fa-building-o"></i></button>'
													+ '</div>' ]);
							}
							});
		table.rows.add(result);
		table.draw();
		if(n==0){
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
			//displayMessageOnSubmit('ADC',"IssuanceDeathCertificate.html?applyForDeathCert");
			showloader(false);
			}
	} 
		showloader(false);
 }
	
	},2);

	
	
}


function searchBirthCertificateData() {
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
		showloader(false);
	} else {
		var table = $('#BirthCorrDataTable').DataTable();
		var url = "BirthCorrectionForm.html?searchBirthDetail";
		var requestData = "brCertNo=" + $('#brCertNo').val()
				+ "&applnId=" + '0' + "&year="
				+ $("#year").val() + "&brRegNo=" + $("#brRegNo").val() +"&brDob=" + $("#brDob").val() + "&brChildName=" +$("#brChildName").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
		table.rows().remove().draw();
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
			//displayMessageOnSubmit('ABC',"IssuanceBirthCertificate.html");
		}
		else{
	var n=0;
		//var m=0; 
			var result = [];
			
			$
			.each(
			returnData,
			function(index) {
			var obj = returnData[index];
			if(obj.brStatus == "A"){
					n++;
			let brDob = obj.brDob;
			let brChildName=obj.brChildName;
			if(brChildName == null){
				brChildName = "";
			}
			
			let pdFathername=obj.parentDetailDTO.pdFathername;
			let pdMothername=obj.parentDetailDTO.pdMothername;
			let brRegNo = obj.brRegNo;
			let brSex = obj.brSex;
			let cpdRegUnit = obj.cpdRegUnit;
			let brId = obj.brId;

			result
			.push([
			'<div class="text-center">'
			+ (index + 1) + '</div>',
			'<div class="text-center">'
			+ getDateFormat(brDob) + '</div>',
			'<div class="text-center">'
			+ brRegNo + '</div>',
			'<div class="text-center">'
			+ brChildName + '</div>',
			'<div class="text-center">'
			+ brSex + '</div>',
			'<div class="text-center">'
			+ pdFathername + '</div>',
			'<div class="text-center">'
			+ pdMothername + '</div>',
			/*'<div class="text-center">'
			+ cpdRegUnit + '</div>',*/
			'<div class="text-center">'
			+ '<button type="button"  class="btn btn-blue-3 btn-sm margin-right-5"  onclick="modifybirth(\''
			+ brId
			+ '\',\'IssuanceBirthCertificate.html\',\'editBND\',\'A\')" title="Issue certificate"><i class="fa fa-building-o"></i></button>'
			+ '</div>' ]);
			}
			
			});
			table.rows.add(result);
			table.draw();
			
			if(n==0){
				errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
				displayErrorsOnPage(errorsList);
				//displayMessageOnSubmit('ABC','IssuanceBirthCertificate.html?applyForBirthCert');
				}
			}
		showloader(false);
	}
	},2);
	
}


function displayMessageOnSubmit(shortCode,url) {
	var message ="";
	var Yes = getLocalMessage("apply.yes");
	var No = getLocalMessage("apply.no");
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';
	if(shortCode=='ABC'){
		message =getLocalMessage("apply.birth");
	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' name="Yes" class= "btn btn-success" value=\''
			+ Yes
			+ '\'  id=\'btnNo\' onclick="ApplyForBirthCertificate()"/><input type=\'button\' name="No" class= "btn btn-success" value=\''
			+ No + '\'  id=\'btnNo\' onclick="IssuenceofBirthCertRedirect()"/></div>';

	}else{
		message =getLocalMessage("apply.death");
		var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
				+ '</h5>';
		d += '<div class=\'text-center\'><input type=\'button\' name="Yes" class= "btn btn-success" value=\''
				+ Yes
				+ '\'  id=\'btnNo\' onclick="ApplyForDeathCertificate()"/><input type=\'button\' name="No" class= "btn btn-success" value=\''
				+ No
				+ '\'  id=\'btnNo\' onclick="IssuenceofDeathCertRedirect()"/></div>';
	}
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function IssuenceofBirthCertRedirect(){
	var requestData ={};
	var returnData = __doAjaxRequest('IssuanceBirthCertificate.html?applyForBirthCert', 'post', requestData, false,
	'html');	
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
	
}

function IssuenceofDeathCertRedirect(){
	var requestData ={};
	var returnData = __doAjaxRequest('IssuanceDeathCertificate.html?applyForDeathCert', 'post', requestData, false,
	'html');	
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
	
}

function ApplyForBirthCertificate(){
	var requestData ="shortCode=" + 'ABC';
	var returnData = __doAjaxRequest('bndService.html?applyForNewCertificate', 'post', requestData, false,
	'html');
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
}

function ApplyForDeathCertificate(){
	var requestData ="shortCode=" + 'ADC';
	var returnData = __doAjaxRequest('bndService.html?applyForNewCertificate', 'post', requestData, false,
	'html');
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
}

function bndRegAcknow(url) {

	var URL = url;
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'BND Registration Acknowlegement';
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
			.write('<link href="assets/css/print-theme-6.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
	/*printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')*/
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
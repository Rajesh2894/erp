function searchBirthData() {
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
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
			errorsList.push("No record Found for Select criteria")
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
			errorsList.push("Application already is in progress")
			  $("#frmIssuCertificateForm")[0].reset();
			displayErrorsOnPage(errorsList);
			}
		if (returnData != 'Internal Server Error.') {
			$(".content-page").html(returnData);
		} else {
			errorsList.push(getLocalMessage("BirthDeath.NoRecord.Error"))
			  $("#frmIssuCertificateForm")[0].reset();
			displayErrorsOnPage(errorsList);
		}
	}
    },2);
}

function validateBirthSearchForm(errorsList) {
	
	var certNo = $('#brManualCertNo').val();
	//var regisApplicationNo = $('#applicationId').val();
	var regDate = $("#year").val();
	var regisNo = $("#brRegNo").val();
	var brSex = $('#brSex').val();
	var hospitalList = $('#hospitalList').val();
	var pdFathername = $('#pdFathername').val();
	var pdMothername = $('#pdMothername').val();
	var brChildName = $('#brChildName').val();
	var brDob = $('#brDob').val();
	// validate the year
	validatedates(errorsList);
	if (brDob == "" && regDate == ""
			&& regisNo == "" && brSex == "0" && hospitalList == "" && pdFathername == "" && pdMothername == "" && brChildName == "") {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	}else {
		// go for Search
	}
	return errorsList;
}

function validateDeathSearchForm(errorsList) {

	var certNo = $('#drManualCertno').val();
	//var regisApplicationNo = $('#applicationNo').val();
	var regDate = $("#year").val();
	var regisNo = $("#drRegno").val();
	var drDod = $("#drDod").val();
	var drDeceasedname = $("#drDeceasedname").val();
	var cpdDeathcauseId = $("#cpdDeathcauseId").val();
	var drSex = $("#drSex").val();
	// validate the year
	validatedates(errorsList);
	if (drSex == "0" && cpdDeathcauseId == "0" && regDate == "" && drDod == "" && drDeceasedname == "" && regisNo == "") {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	}else {
		// go for Search
	} 
	return errorsList;
}

function saveBirthCertFormAndGenerateAppNo(element) {

	var errorList = [];
	var amount = $('#amount').val();
 	var chargeStatus = $("#chargeStatus").val();
   errorList = validateApplicantDetails(errorList);
	
 	if(amount == "N"){
 		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
 	}else{
 	if($('#noOfCopies').val()=="" || $('#noOfCopies').val()==undefined || $('#noOfCopies').val()==0){
		errorList.push(getLocalMessage("TbDeathregDTO.label.valdinfornoofcopies"));
	}
 	if(chargeStatus == "CA" && amount !="0.0" && amount !="0" && amount !=null)
 		{
	if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="N" && $("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="P" )
	{
		errorList.push(getLocalMessage("bnd.select.payment"));
	}
	else if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="N")
		{
		if($("#oflPaymentMode").val()==0 ) 
		{
		errorList.push(getLocalMessage("bnd.select.payment.mode"));}
		}else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="P")
			{
			if( $("#payModeIn").val()==0)
				{
				errorList.push(getLocalMessage("bnd.select.payment.mode"));
		       }
			}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else
			{
			 saveOrUpdateForm(element,
					"Application Number generation done successfully",
					'IssuanceBirthCertificate.html?PrintReport', 'saveform');
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

function bndRegAcknow(url) {
	var URL = url;
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	if(returnData!=null && returnData!=""){
	var appId = $($.parseHTML(returnData)).find("#applicationId").html();
	var title = appId;
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
}

function saveDeathCertFormAndGenerateAppNo(element) {
	
	var errorList = [];
	var amount = $('#amount').val();
 	var chargeStatus = $("#chargeStatus").val();
   errorList = validateApplicantDetails(errorList);
 	if(amount=="N"){
 		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
 	}else{
 	if($('#numberOfCopies').val()=="" || $('#numberOfCopies').val()==undefined || $('#numberOfCopies').val()==0){
		errorList.push(getLocalMessage("TbDeathregDTO.label.valdinfornoofcopies"));
	}
 	
 	if(chargeStatus == "CA" && amount !="0.0" && amount !="0" && amount !=null)
 		{
	if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="N" && $("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="P" )
	{
		errorList.push(getLocalMessage("bnd.select.payment"));
	}
	else if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="N")
		{
		if($("#oflPaymentMode").val()==0 ) 
		{
		errorList.push(getLocalMessage("bnd.select.payment.mode"));}
		}else if ($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()=="P")
			{
			if( $("#payModeIn").val()==0)
				{
				errorList.push(getLocalMessage("bnd.select.payment.mode"));
		       }
			}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
		else
			{
			  saveOrUpdateForm(element,
						"Application Number generation done successfully",
						'IssuanceDeathCertificate.html?PrintReport', 'saveform');
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
		if (yy < 1940 || yy > (new Date()).getFullYear()) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		} else if (yy < 1940) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
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


function SearchDeathCertificateData(element) {
	
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
		showloader(false);
	} else {
		var table = $('#deathCorrDataTable').DataTable();
		var url = "DeathRegistrationCorrection.html?searchDeathDataForCorr";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		/*var requestData = "drCertNo=" + $('#drCertNo').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applnId=" + $("#applnId").val()
				+ "&drDod=" + $("#drDod").val()+"&drDeceasedname=" + $("#drDeceasedname").val();*/
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		
		table.rows().remove().draw();
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			//displayMessageOnSubmit('ADC',"IssuanceDeathCertificate.html?applyForDeathCert");
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
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
							let drRegdate = obj.drRegdate;
							let drSex = obj.drSex;
							let cpdRegUnit = obj.cpdDesc;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drDod) + '</div>',
											'<div class="text-center">'
													+ drRegno + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drRegdate) + '</div>',
											'<div class="text-center">'
													+ drDeceasedname + '</div>',
											'<div class="text-center">'
													+ drSex + '</div>',
											'<div class="text-center">'
													+ cpdRegUnit + '</div>',
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
			displayMessageOnSubmit('ADC',"IssuanceDeathCertificate.html?applyForDeathCert");
			showloader(false);
			}
	} 
		showloader(false);
 }
	
	},2);

	
	
}


function searchBirthCertificateData(element) {
	
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#BirthCorrDataTable').DataTable();
		var url = "BirthCorrectionForm.html?searchBirthDetailForCorr";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		/*var requestData = "brCertNo=" + $('#brCertNo').val()
				+ "&applnId=" + $('#applnId').val() + "&year="
				+ $("#year").val() + "&brRegNo=" + $("#brRegNo").val() +"&brDob=" + $("#brDob").val() + "&brChildName=" +$("#brChildName").val();*/
		var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
		table.rows().remove().draw();
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			//displayMessageOnSubmit('ABC',"IssuanceBirthCertificate.html");
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
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
			let brRegDate = obj.brRegDate;
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
			+ getDateFormat(brRegDate) + '</div>',
			'<div class="text-center">'
			+ brChildName + '</div>',
			'<div class="text-center">'
			+ pdFathername + '</div>',
			'<div class="text-center">'
			+ pdMothername + '</div>',
			'<div class="text-center">'
			+ brSex + '</div>',
			'<div class="text-center">'
			+ cpdRegUnit + '</div>',
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
				displayMessageOnSubmit('ABC','IssuanceBirthCertificate.html?applyForBirthCert');
				}
			}
		showloader(false);
	}
	},2);
	
}

function searchBndReceiptData(element) {
	debugger;
	//showloader(true);
	//setTimeout(function(){
	var errorsList = [];
	var brChildName = $('#brChildName').val();
	var deathName = $('#deathName').val();
	var rmRcptno = $('#rmRcptno').val();
	var rmDate = $('#rmDate').val();
	var birthDate = $('#birthDate').val();
	var deathDate = $('#deathDate').val();
	
	if(brChildName==''){
		$('#brChildName').val('X');
	}
	if(deathName==''){
		$('#deathName').val('X');
	}
		if(brChildName =='' && deathName =='' && rmRcptno==''  && rmDate==''  && birthDate==''  && deathDate=='' ){
			errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
		}
	
	if (errorsList.length > 0) {
		if(brChildName==''){
			$('#brChildName').val('');
		}
		if(deathName==''){
			$('#deathName').val('');
		}
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#BirthReceiptDataTable').DataTable();
		var url = "IssuanceBirthCertificate.html?searchBirthReceiptData";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		
		var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
		table.rows().remove().draw();
		if(brChildName==''){
			$('#brChildName').val('');
		}
		if(deathName==''){
			$('#deathName').val('');
		}
		if ((returnData == 'Internal Server Error.')||(returnData ==0)) {
			//displayMessageOnSubmit('ABC',"IssuanceBirthCertificate.html");
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
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
			let brDob = obj.rmRcptno;
			let receiptDate=obj.rmDate;
			let payeeName=obj.rmReceivedfrom;
			let receiptAmount=obj.rmAmount;

			result
			.push([
			'<div class="text-center">'
			+ (index + 1) + '</div>',
			'<div class="text-center">'
			+ brDob + '</div>',
			'<div class="text-center">'
			+ getDateFormat(receiptDate) + '</div>',
			'<div class="text-center">'
			+ payeeName + '</div>',
			'<div class="text-center">'
			+ receiptAmount + '</div>',
			+ '</div>' ]);
			
			});
			table.rows.add(result);
			table.draw();
			}
		//showloader(false);
	}
	//},2);
	
}


function displayMessageOnSubmit(shortCode,url) {
	
	var Yes = getLocalMessage("apply.yes");
	var No = getLocalMessage("apply.no");
	
	var message ="";
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';
	if(shortCode=='ABC'){
		message =getLocalMessage("apply.birth");
	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' name="Yes" class= "btn btn-success" value=\''
			+ Yes
			+ '\'  id=\'btnNo\' onclick="ApplyForBirthCertificate()"/>  <input type=\'button\' name="No" class= "btn btn-success" value=\''
			+ No + '\'  id=\'btnNo\' onclick="IssuenceofBirthCertRedirect()"/></div>';

	}else{
		message =getLocalMessage("apply.death");
		var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
				+ '</h5>';
		d += '<div class=\'text-center\'><input type=\'button\' name="Yes" class= "btn btn-success" value=\''
				+ Yes
				+ '\'  id=\'btnNo\' onclick="ApplyForDeathCertificate()"/> <input type=\'button\' name="No" class= "btn btn-success" value=\''
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
	var returnData = __doAjaxRequest('rtsServices.html?applyForNewCertificate', 'post', requestData, false,
	'html');
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
}

function ApplyForDeathCertificate(){
	var requestData ="shortCode=" + 'ADC';
	var returnData = __doAjaxRequest('rtsServices.html?applyForNewCertificate', 'post', requestData, false,
	'html');
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
}


function validateApplicantDetails(errorList) {
	var titleId = $("#titleId").val();
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var cityName = $("#cityName").val();
	var pincodeNo = $("#pincodeNo").val();
	var mobileNo = $("#mobileNo").val();
	var email = $("#email").val();
	
	if (titleId == "0") {
		errorList.push(getLocalMessage("requestDTO.valid.title"));
	}
	if (fName == "" || fName == null || fName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.fName"));
	}
	if (lName == "" || lName == null || lName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.lName"));
	}
	
	if (cityName == "" || cityName == null || cityName == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.cityName"));
	}
	if (pincodeNo == "" || pincodeNo == null || pincodeNo == undefined) {

	} else {
		if (pincodeNo.length < 6) {
			errorList.push(getLocalMessage("requestDTO.valid.invalidPinCode"));
		}
	}
	
	if (mobileNo == "" || mobileNo == null || mobileNo == undefined) {
		errorList.push(getLocalMessage("requestDTO.valid.mobile"));
	} else {
		if (mobileNo.length < 10) {
			errorList.push(getLocalMessage("requestDTO.valid.invalidMobile"));
		}
	}
	if (email == "" || email == null || email == undefined) {
		
	} else {
		var emailId = $.trim($("#email").val());
		if (emailId != "") {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(emailId);
			if (!valid) {
				errorList.push(getLocalMessage("requestDTO.valid.invalidEmail"));
			}
		}
	}
	
	return errorList;
}
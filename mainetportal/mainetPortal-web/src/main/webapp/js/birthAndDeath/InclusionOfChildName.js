$(document).ready(function() {
	
	
	$("#InclusionOfChildName").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	  var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#brChildName").bind('click keyup', function(event) {
				var no_spl_char;
				no_spl_char = $("#brChildName").val().trim();
				if(no_spl_char!=''){
					commonlanguageTranslate(no_spl_char,'brChildNameMar',event,'');
				}else{
					$("#brChildNameMar").val('');
				}
			});
		}
});


function searchBirthData(element) {
	showloader(true);
	setTimeout(function(){
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
		showloader(false);
	} else {
		var table = $('#ChildNameCorrDataTable').DataTable();
		var url = "InclusionOfChildName.html?searchBirthDetails";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		table.rows().remove().draw();
		if (returnData == 'Internal Server Error.') {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
			displayErrorsOnPage(errorsList);
			showloader(false);
		}
		var n=0;
			var result = [];
			
			$
			.each(
			returnData,
			function(index) {
            
			var obj = returnData[index];
			if(obj.brStatus == "A" && (obj.brChildName=="" && obj.brChildNameMar=="" || obj.brChildName==null && obj.brChildNameMar==null)){
				n++;
			let brDob = obj.brDob;
			let brRegNo = obj.brRegNo;
			let brSex = obj.brSex;
			let cpdRegUnit = obj.cpdRegUnit;
			let brId = obj.brId;
			let pdFathername=obj.parentDetailDTO.pdFathername;
			let pdMothername=obj.parentDetailDTO.pdMothername;
			let brRegDate = obj.brRegDate;
			let hospital = obj.brHospital;
			if(hospital == null){
				hospital = "";
			}

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
			+ pdFathername + '</div>',
			'<div class="text-center">'
			+ pdMothername + '</div>',
			'<div class="text-center">'
			+ brSex + '</div>',
			'<div class="text-center">'
			+ cpdRegUnit + '</div>',
			'<div class="text-center">'
			+ hospital + '</div>',
			'<div class="text-center">'
			+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifybirth(\''
			+ brId
			+ '\',\'InclusionOfChildName.html\',\'editBND\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
			+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifybirth(\''
            +brId
			+ '\',\'InclusionOfChildName.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
			+ '</div>' ]);
			}
			});
			table.rows.add(result);
			table.draw();
			if(n==0){
				errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"));
				displayErrorsOnPage(errorsList);
				showloader(false);
				}
			showloader(false);
			}

	},2);
}

function modifybirth(brId, formUrl, actionParam, mode) {
	
	var divName = '.content-page';
	var requestData = {
	"mode" : mode,
	"id" : brId
	 };
	
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#brDob').prop("disabled", true);
	//$('#hospitalList').prop("disabled", true);
	//$('#brRegNo').prop("disabled", true);
}

function searchDeathData() {
	
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var url = "InclusionOfChildName.html?searchDeathDetail";
		var requestData = "drCertNo=" + $('#drCertNo').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applicationId=" + $("#applicationNo").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'html');
		if (returnData != null) {
			$(".content-page").html(returnData);
		} else {
			errorsList.push(getLocalMessage("TbDeathregDTO.label.norec"))
			displayErrorsOnPage(errorsList);
		}
	}
}

function validateBirthSearchForm(errorsList) {
	
	var certNo = $('#brCertNo').val();
	var brDob = $('#brDob').val();
	var regDate = $("#year").val();
	var regisNo = $("#brRegNo").val();
	var pdFathername = $('#pdFathername').val();
	var pdMothername = $('#pdMothername').val();
	// validate the year
	validatedates(errorsList);
	if (brDob == ""  && regDate == "" && regisNo == "" && pdFathername == "" && pdMothername == "") {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	} else if(((regDate != "" && regisNo == "")||(regDate == "" && regisNo != ""))&&((pdFathername != "" && pdMothername == "")||(pdFathername == "" && pdMothername != ""))){
		errorsList.push(getLocalMessage("bnd.yearAndRegNo"));
		errorsList.push(getLocalMessage("bnd.brfatherAndMotherName"));
	}else if((regDate != "" && regisNo == "")||(regDate == "" && regisNo != "")){
		errorsList.push(getLocalMessage("bnd.yearAndRegNo"));
	}else if((pdFathername != "" && pdMothername == "")||(pdFathername == "" && pdMothername != "")){
		errorsList.push(getLocalMessage("bnd.brfatherAndMotherName"));
	}else if(brDob != ""){
		//go for seach
	}else{
		//go for seach
	}
	return errorsList;
}

function validateDeathSearchForm(errorsList) {
	
	var certNo = $('#drCertNo').val();
	var regisApplicationNo = $('#applicationNo').val();
	var regDate = $("#year").val();
	var regisNo = $("#drRegno").val();
	// validate the year
	validatedates(errorsList);
	if (certNo == "" && regisApplicationNo == "" && regDate == "" && regisNo == "") {
		errorsList.push(getLocalMessage("TbDeathregDTO.label.searchcrit"));
	} else if (certNo != "" || regisApplicationNo != "") {
		// go for Search
	} else if (regDate != "" && regisNo != "") {
		// go for Search
	} else {
		errorsList.push(getLocalMessage('Please enter year and registration No.'));
	}
	return errorsList;
}

function saveInclusionOfChildName(element) {
	
	var errorList = [];
	var chargeStatus=$("#chargeStatus").val();
	var amount=$("#amount").val();
	var brChildName=$("#brChildName").val();
	var brChildNameMar=$("#brChildNameMar").val();
	var brId =$("#brId").val();
	var noOfCopies=$("#noOfCopies").val();
	//Division validation added 04/08/22
	var bndDw1=$('#bndDw1').val();
	if(amount == "N"){
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
	}else{
	if (brChildName == "") {
		errorList.push(getLocalMessage("bnd.validation.childname.eng"));
	}
	if (brChildNameMar == "") {
		errorList.push(getLocalMessage("bnd.validation.childname.mar"));
	}
	if (noOfCopies == "" || noOfCopies ==0) {
		errorList.push(getLocalMessage("death.label.demandcop"));
	}
	if (bndDw1 == 0 || bndDw1 == '') {
		errorList.push(getLocalMessage("bnd.div.validate"));
	}
	var rowcount=$("#BirthIncTable tr").length 
	/*for(var i=0;i<rowcount-1;i++){
	var checklistUploadedOrNot=$("#checkList"+i).val();
	 if(checklistUploadedOrNot==""){
		 errorList.push(getLocalMessage("TbDeathregDTO.label.checklist")+(i+1));
       }
	}*/
		if(chargeStatus != 'CA' || amount == "0" || amount == "0.0" ||  amount == null){
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			}
			else{
			  saveOrUpdateForm(element, "", 'InclusionOfChildName.html', 'saveform');
			  bndRegAcknow();  
			}
			
		 }else {
			 if($("input[name='offlineDTO.onlineOfflineCheck']:checked").val()!="Y")
				{
				errorList.push(getLocalMessage("bnd.paymentType"));
				}
			 if (errorList.length > 0) {
					displayErrorsOnPage(errorList);
				} else {
			  saveOrUpdateForm(element, "", 'InclusionOfChildName.html?redirectToPay', 'saveform');
			  bndRegAcknow();
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


function getChecklistAndCharges(element) {
	var errorList = [];
	var amount=$("#amount").val();
	var brDob=$("#brDob").val();
	var noOfCopies=$("#noOfCopies").val();
	//Division validation added 04/08/22
	var bndDw1=$('#bndDw1').val();
	if (bndDw1 == 0 || bndDw1 == '') {
 		errorList.push(getLocalMessage("bnd.div.validate"));
		displayErrorsOnPage(errorList);
	}
	if (noOfCopies == "" || noOfCopies ==0) {
		errorList.push(getLocalMessage("death.label.demandcop"));
	}
	if(amount == "N"){
		errorList.push(getLocalMessage("bnd.validation.brmscharges"));
 		displayErrorsOnPage(errorList);
	}else{
	var flag = false;
	var brChildName=$("#brChildName").val();
	var brChildNameMar=$("#brChildNameMar").val();
	if (brChildName == "") {
		errorList.push(getLocalMessage("bnd.validation.childname.eng"));
	}
	if (brChildNameMar == "") {
		errorList.push(getLocalMessage("bnd.validation.childname.mar"));
	}
	if (errorList.length > 0) {
		//checkDate(errorList);
		displayErrorsOnPage(errorList);
	}else if ($("#frmInclusionOfChildName").valid() == true) {
		
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		//requestData=requestData+"&"+"birthRegDto.brDob="+brDob;
		var URL = 'InclusionOfChildName.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		
		if (returnData) {
			var divName = '.pageDiv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
			$('#resetId').hide();
			$('#backId').hide();
			$('#brDob').prop("disabled", true);
			}
		}
}
}


function getAmountOnNoOfCopes(){
	
 	var errorsList= [];
 	var chargeStatus = $("#chargeStatus").val();
 	if (chargeStatus == 'CA' || chargeStatus == 'CC') {
 	var form_url = $("#frmInclusionOfChildName").attr("action");
  	var url=form_url+'?getBNDChargeForInclusion';
 	var isscopy=$("#alreayIssuedCopy").val();//0
 	if(isscopy==''){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	$("#amount").val(returnData.chargesAmount);
	$("#serviceCharge").val(returnData.serviceCharge);
	$("#certificateFee").val(returnData.certificateFee);
	 if(returnData.chargesAmount=='0' || returnData.chargesAmount=='N'){
		 $('#payId').hide();
		 $('#amountid').hide();
		 $('#chargeid').hide();
		 $("#serviceCharge").hide();
		 $("#certificateFee").hide();
		 
	 }else{
		 $('#payId').show();
		 $('#amountid').show();
		 $('#chargeid').show();
		 $("#serviceCharge").show();
		 $("#certificateFee").show();

	 }
	 
 	}
 	else{
 		//errorsList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorsList);
 	}
 	}
}



$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maate : '-0d',
		changeYear : true,
	});
});

function resetMemberMaster(resetBtn){

	resetForm(resetBtn);
}


function bndRegAcknow(element) {
	var URL = 'InclusionOfChildName.html?printBndAcknowledgement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'Inclusion of Child Acknowlegement';
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
			.write('<script src="assets/libs/jquery/jquery.min.js" ></script>')
	/*printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')*/
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
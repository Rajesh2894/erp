$(document).ready(function() {
	prepareDateTag();
	
	$("#printcertiDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
});
function SearchDateForPrintCertificate() {
	
	var errorsList = [];
	var coordinate = $('#coordinates').val();
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#printcertiDataTable').DataTable();
		var url = "PrintCertificate.html?printbirthAndDeathCert";
		var requestData ="applnId=" + $("#applnId").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		
		table.rows().remove().draw();
		
		if (returnData.length==0) {
			errorsList.push(getLocalMessage("bnd.no.data"));
			displayErrorsOnPage(errorsList);
		}
		
		var result = [];
		$
				.each(
						returnData,
						function(index) {
							var obj = returnData[index];
							var signCertBtn = getLocalMessage('property.sign.certificate');
							if(obj.filePath!=null){
							let applnId = obj.rtIdOrApplicationId;
							let regNo = obj.regNo;
							let value=obj.filePath;
							let filePath=value.replace(/\\/g,"\\\\");
							let certNo=obj.certNo;
							var  statusforprint;
							if(obj.status=="N"){
								statusforprint=getLocalMessage('birth.death.notPrinted');
							}else if(obj.status=="P"){
								statusforprint=getLocalMessage('birth.death.printed');
							}else{
								statusforprint=getLocalMessage('birth.death.canceled');
							}
							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ applnId + '</div>',
											'<div class="text-center">'
													+ regNo + '</div>',
											'<div class="text-center">'
													+ certNo + '</div>',
											'<div class="text-center">'
													+ statusforprint + '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="getCertificateData(\''
													+ applnId
													+ '\',\'PrintCertificate.html\',\'getCertificateData\',\''+certNo+'\',\''+obj.status+'\',\''+obj.bdId+'\')"  title="Print"><i class="fa fa-print"></i></button>'
													+ '<button type="button" class="btn btn-primary btn-sm "  onclick="downloadFile(\''
													+ filePath
													+ '\',\'PrintCertificate.html?Download\',)"  title="Download Certificate"><i class="fa fa-download"></i></button>'
													+ '</div>' ]);
							
							}
							else{
								let applnId = obj.rtIdOrApplicationId;
								let regNo = obj.regNo;
								
								let certNo=obj.certNo;
								var  statusforprint;
								if(obj.status=="N"){
									statusforprint=getLocalMessage('birth.death.notPrinted');
								}else if(obj.status=="P"){
									statusforprint=getLocalMessage('birth.death.printed');
								}else{
									statusforprint=getLocalMessage('birth.death.canceled');
								}
								result
										.push([
												'<div class="text-center">'
														+ (index + 1) + '</div>',
												'<div class="text-center">'
														+ applnId + '</div>',
												'<div class="text-center">'
														+ regNo + '</div>',
												'<div class="text-center">'
														+ certNo + '</div>',
												'<div class="text-center">'
														+ statusforprint + '</div>',
												'<div class="text-center">'
														+ '<button type="button" class="btn btn-warning btn-sm "  onclick="getCertificateData(\''
														+ applnId
														+ '\',\'PrintCertificate.html\',\'getCertificateData\',\''+certNo+'\',\''+obj.status+'\',\''+obj.bdId+'\')"  title="Print"><i class="fa fa-print"></i></button>'
														+ '</div>' ]);
							}
						});
	
		
		table.rows.add(result);
		table.draw();
	} 
}


function validateDeathSearchForm(errorsList) {
	
	var drCertNo = $('#drCertNo').val();
	var applnId = $('#applnId').val();
	var year = $("#year").val();
	var drRegno = $("#drRegno").val();
	var drDod= $("#drDod").val();
	var drDeceasedname=$("#drDeceasedname").val();
	if (drCertNo == "" && applnId == "" && year == ""
			&& drRegno == "" && drDod == "" && drDeceasedname == "") {
		errorsList
				.push(getLocalMessage('Please enter at least one criteria for search'));
	} else if (drCertNo != "" || applnId != "" || drDod != "" || drDeceasedname != "") {
		// go for Search
	} else if (year != "" && drRegno != "") {
		// go for Search
	} 
	else {
		errorsList
				.push(getLocalMessage('Please enter year and registration No.'));
	}
	return errorsList;
}


function getCertificateData(ApplnId, formUrl, actionParam,certNo,status,bdId) {
	
	
	var divName = '.content-page';
	var requestData = {
	"ApplnId" : ApplnId,
	"certiNo":  certNo,
	"status":status,
	"bdId":bdId
	 };
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#cpdDeathplaceType').prop("disabled", true);
	$('#hospitalList').prop("disabled", true);
	$('#drDod').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	
	
}


function getChecklistAndCharges(element) {
	var errorList = [];
	var drDod = $("#drDod").val();
	var mcVerifnDate = $("#mcVerifnDate").val();
	var currDate= new Date();
	
		if(drDod == ""){
			errorList.push(getLocalMessage("deceased Date Should not be blank"));
		}
		
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
		var drDod = new Date(drDod.replace(pattern, '$3-$2-$1'));
		var mcVerifnDate = new Date(mcVerifnDate.replace(pattern, '$3-$2-$1'));
	
		if (drDod > currDate) {
			errorList.push(getLocalMessage("deceased Date cannot be greater than Current Date"));
		}

		if (drDod > mcVerifnDate) {
			errorList.push(getLocalMessage("Post-Mortem cannot be less than Deceassed Date"));
		}
		if (errorList.length > 0) {
			checkDate(errorList);
		}
		
		else{
		var flag = false;
		if ($("#frmDeathRegCorrForm").valid() == true) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'PrintCertificate.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');

		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
			$('#drDod').prop("disabled", true);
			$('#within1').prop("disabled", true)
			$('#within2').prop("disabled", true)
			$('#ceName').prop("disabled", true);
			$('#ceNameMar').prop("disabled", true);
			$('#ceAddr').prop("disabled", true);
			$('#ceAddrMar').prop("disabled", true);
			$('#cemeteryList').prop("disabled", true);
		}

   	}
 }
}
function disFields(element) {
	
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", false);
}

function enaFields(element) {
	$('#ceName').prop("disabled", false);
	$('#ceNameMar').prop("disabled", false);
	$('#ceAddr').prop("disabled", false);
	$('#ceAddrMar').prop("disabled", false);
	$('#cemeteryList').prop("disabled", true);
}

function resetDeathCorrData() {
	window.open('PrintCertificate.html', '_self');
}

function saveDeathCorrData(element) {
	if ($("#frmDeathRegCorrForm").valid() == true) {
		$('#drDod').prop("disabled", false);
		return saveOrUpdateForm(element, "Application Number generation done successfully", 'PrintCertificate.html',
				'saveform');
	} else {
	}
}

function selecthosp(element)
{
    var code=$(element).find(':selected').attr('code')
	if(code=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
}
function saveDeathRegApprovalData(obj){ 
	  
	  var remark=$("#deathRegremark").val();
	  var approveOrReject=$("#deathRegstatus").val();
	  var errorList = [];
	  if(remark=="" || remark==undefined ){
		 errorList.push("Please enter the Remark");
		}
	  if(approveOrReject=="" || approveOrReject==undefined){
		 errorList.push("Please select Radio Button");
		}
	  if(errorList.length>0){
		 displayErrorsOnPage(errorList);
		}
	  else{
			//Save code
	  } 
}
function getAmountOnNoOfCopes(){
	
 	var errorsList= [];
 	var form_url = $("#frmDeathRegCorrForm").attr("action");
  	var url=form_url+'?getBNDCharge';
 	var isscopy=0;
 	var isscopy=$("#brCertNo").val();
 	if(isscopy=='' || isscopy==undefined ){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
 	}
 	else if($('#numberOfCopies').val()!='' && $('#numberOfCopies').val()!=undefined){
 		var requestData = "noOfCopies=" + $('#numberOfCopies').val()+ "&issuedCopy=" +isscopy;
 		var returnData = __doAjaxRequest(url, 'post', requestData, false,
 				'json');  
 		 $("#amount").val(returnData);
 	}
 	else{
 		//errorsList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorsList);
 	}
}
function printCertificate(printpage){
      
      var certificateNo=$("#certificateNo").val();
      var brOrdrID=$("#brOrdrID").val();
      var serviceCode=$("#serviceCode").val();
      var status = $("#status").val();
      var bdId = $("#bdId").val();
 	   var form_url = $("#print-div").attr("action");
 	   var url=form_url+'?updatePrintStatusAfterPrint';
     if(status=="N"){
   	 var requestData = "certificateNo=" + certificateNo + "&brOrdrID="+brOrdrID +"&serviceCode="+serviceCode +"&bdId="+bdId;
        var returnData = __doAjaxRequest(url, 'post', requestData, false,
	     'json');  
    }

     //var status=$("#ststusforcerti").val();
     var headstr = "<html><head><title></title></head><body>";
	    var footstr = "</body>";
	    var newstr = document.all.item(printpage).innerHTML;
	    var oldstr = document.body.innerHTML;
	    document.body.innerHTML = headstr + newstr + footstr;
	    window.print();
	    document.body.innerHTML = oldstr;
 	//return false;
     
       
}

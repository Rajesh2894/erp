$(document).ready(function(){
	
//disable offline payment based on process id and scrutiny applicable flag
	var flag=$("#immediateService").val();
	if(flag=='N'){	
			$("#offlinebutton").hide();
			$("#offlineLabel").hide();
	}
	else{
			$("#offlinebutton").show();
			$("#offlineLabel").show();
	}
	
	reOrderTableIdSequenceProp('propertyDetails', 'propDet');
	
	$("#propertyDetails tbody tr").each(function(i) {
		getFlatDetails(i,true)
	});
	var flag=$("#successFlag").val();
	if(flag=='F'){
		$("#finacialYearId0").attr("readOnly", false);
		var billingMethod=$("#billMethod").val();
		if('W' == billingMethod){
			$("#flatNo0").val('');
		}
	}
	
});


function showConfirmBoxToProceed(element) {
	var requestData = {};
	var theForm	=	'#PropertyNoDuesCertificate';
	var divName = '.content-page';
	requestData = __serializeForm(theForm);
	var ajaxResponse = doAjaxLoading('PropertyNoDuesCertificate.html?searchPage', requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
}

function getNoDuesDetails(element){
	var propNo= $("#propNo").val();
	var oldPropNo= $("#oldpropno").val();
	var errorList = [];
	errorList = ValidateInfo(errorList);	
	if (errorList.length == 0) {
	var data = {"propNo" : propNo,
			  "oldPropNo": oldPropNo
			};
	var URL = 'PropertyNoDuesCertificate.html?getPropertyDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	
	$("#applicantName").val($("#ownerName").val());
	$("#appliAddress").val($("#ownerAddress").val());
	
	
	}
	else {
		displayErrorOnPage(errorList);
	}
	
}

function getCheckListAndCharges(element,serviceurl){
	var errorList = [];
	errorList = validateNoDuesForm(errorList);
	if (errorList.length == 0){
		var flatNos =[];
		$("#propertyDetails tbody tr").each(function(i) {
			flatNos.push($("#flatNo"+i).val());
		});
		var theForm	=	'#PropertyNoDuesCertificate';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = serviceurl+'?getCheckListAndCharges';
		var returnData =__doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		
		if (returnData) {
			$(formDivName).html(returnData);
			if ($("#totalPayAmt").val() != undefined) {				
				$(".read").attr("readOnly", true);
				$("#finacialYearId0").attr("readOnly", true);
				$("#amountToPay").val($("#totalPayAmt").val());
				// $("#checkListCharge").hide();
				$("#noOfCopies").attr("readOnly", true);
			}

		
			$(".read").attr("readOnly", true);
			$("#amount0").val($("#amount").val());		
			totalMultiModeAmount();
		}
		for (var i = 0; i < flatNos.length; i++) {
			$("#flatNo" + i).val(flatNos[i]);			
		}
	}else {
		displayErrorOnPage(errorList);
	}
}

function ValidateInfo(errorList){	
	if ($("#propNo").val() == "" && $("#oldpropno").val() == "") {
		errorList.push(getLocalMessage("prop.no.oldPidNo.not.empty"));
	}
	return errorList;
}

function displayErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}

function savePropertyFrom(element,url){
	var errorList = [];
	errorList = validateNoDuesForm(errorList);
	 $('.billDetails').each(function(i) {
			errorList = validatePayModeDetails(errorList,i);
	   });
	if (errorList.length == 0){
		var flatNos =[];
		
		if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N' || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
	  	{
			
			var status = generateReport(element , url, 'PrintReport') ;
			 //  saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", url+'?PrintReport', 'saveform');	
			$(".read").attr("readOnly", true);
			$("#noOfCopies").attr("readOnly", true);
			 return status;
	  	}
	    else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y')
	    {
	    	 var status =  saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", url+'?redirectToPay', 'saveform');
	    	 $(".read").attr("readOnly", true);
			$("#noOfCopies").attr("readOnly", true);
	    	 return status;	       
	    } 	   
	   else
	    {
		   var status =  generateReport(element , url, 'proceed') ;
		   $(".read").attr("readOnly", true);
			$("#noOfCopies").attr("readOnly", true);
		// saveOrUpdateForm(element,"Your application for No Dues Cettificate saved successfully!", url+'?proceed', 'saveform');  
		   return status;

	    }
	}else {
		displayErrorOnPage(errorList);
	}		
}

function validateNoDuesForm(errorList){
	var fName = $("#firstName").val();
	var lName = $("#lastName").val();
	var dwzid1=$("#dwzid1").val();
	var dwzid2=$("#dwzid2").val();
	var dwzid3=$("#dwzid3").val();
	var dwzid4=$("#dwzid4").val();
	var dwzid5=$("#dwzid5").val();
	var noOfCopies=$("#noOfCopies").val();
	//var email=$("#appliEmail").val();
	
	if(fName=="" || lName==""){
		errorList.push(getLocalMessage("prop.enter.firstName.lastName"));
	}
	var status = false;
	if(dwzid1!=undefined && dwzid1=="0"){
		status= true;
	}
	if(dwzid2!=undefined && dwzid2=="0"){
		status= true;
	}
	if(dwzid3!=undefined && dwzid3=="0"){
		status= true;
	}
	if(dwzid4!=undefined && dwzid4=="0"){
		status= true;
	}
	if(dwzid5!=undefined && dwzid5=="0"){
		status= true;
	}
	if(status){
		errorList.push(getLocalMessage("prop.fields.mandatory"));
	}
	if (noOfCopies == "" || noOfCopies == undefined  || noOfCopies == null) {
		errorList.push(getLocalMessage("prop.enter.no.copies"));
		
	}
	if (noOfCopies == "0"){
		errorList.push(getLocalMessage("prop.enter.no.copies.greater.than.zero"));
	}
	if($("#multiAmount").val() !=undefined && $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == undefined){
		errorList.push(getLocalMessage("prop.select.collection.type"));
	}
	

		$("#propertyDetails tbody tr").each(function(i) {
			var financialYear= $("#finacialYearId" + i).val();
			var propNo = $("#propNo" + i).val();
			var flatCheck = $("#flatNo" + i).prop('disabled');
			var flatNo=$("#flatNo" + i).val();
			
			if(financialYear!=undefined && financialYear =="0"){
				errorList.push(getLocalMessage("prop.select.financialYear.srNo" + (i+1)));
			}
			
			if(propNo!=undefined && propNo ==""){
				errorList.push(getLocalMessage("prop.enter.propertyNo.srNo" + (i+1)));
			}
			if(!flatCheck){
				if(flatNo!=undefined && flatNo ==""){
					errorList.push(getLocalMessage("Please Select Flat No. For Sr No." + (i+1)));
				}
			}
	});
		
		if($("#multiAmount").val() != undefined && $("#totalPayAmt").val() != undefined){			
			if(Math.round($("#multiAmount").val()) != Math.round($("#totalPayAmt").val())){
				errorList.push(getLocalMessage("prop.totalAmt.match.totalPayAmt"));
			}
		}
			
	return errorList;
}


function openNoDuesWindowTab(status,url) {
    /*if (!status) {*/
	var URL = url+'?getAcknowledement';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	
	/*window.open(returnData, '_blank');	
	
*/
	var title = 'No Dues Certificate';
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
	printWindow.document
		.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
/*
    }*/
}


function checkLastApproval(element) {
    var payableFlag = $("#payableFlag").val();
    var divName = '.content-page';
    var URL = 'PropertyNoDuesCertificateAuthorization.html?checkLastApproval';
    var formName = findClosestElementId(element, 'form');
    var theForm = '#' + formName;
    var requestData = {};
    requestData = __serializeForm(theForm);
    var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');

    $(divName).removeClass('ajaxloader');
    $(divName).html(returnData);
    prepareTags();
    var payableFlag = $("#payableFlag").val();
    if (payableFlag == 'N') {
	saveAgencyApprovalForm(element)
    }
}

function saveAgencyApprovalForm(element) {
	var serviceShrtCode = $("#serviceShrtCode").val();
	var certificateGenUrl="";
	if(serviceShrtCode == 'NDT'){
		certificateGenUrl='PropertyNoDuesCertificate.html?proceed';
	}else if (serviceShrtCode == 'DUB'){
		certificateGenUrl='DuplicateBillForm.html?proceed';
	}else if(serviceShrtCode == 'EXT'){
		certificateGenUrl='ExtractProperty.html?proceed';
	}else if (serviceShrtCode == 'ACG'){
		certificateGenUrl='AssessmentCertificateGenrator.html?proceed';
	}else{
		certificateGenUrl='AdminHome.html';
	}
    if ($("input[name='workflowActionDto.decision']:checked").val() == "APPROVED") {
	return saveOrUpdateForm(element, "Application Approved Succesfully!",
			certificateGenUrl, 'save');	
	//window.open("AdminHome.html", '_blank');

    } else{
	return saveOrUpdateForm(element, "Application Rejected Succesfully",
			'AdminHome.html', 'save');
    }

}



function getPropertyDetails(index) {
	var propNo = $("#propNo" + index).val();
	var flatNo = $("#flatNo" + index).val();
	var errorList = [];
	if (propNo == "" && flatNo == "") {
		errorList.push("Please enter property No and Flat No for Sr No."
				+ (index + 1))
	}
	if (errorList.length == 0) {
		var data = {
			"propNo" : propNo,
			"flatNo" : flatNo
		};
		var URL = 'PropertyNoDuesCertificate.html?getPropertyDetailsByFlatNo';
		var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false);
		if (ajaxResponse) {
			$("#propNo" + index).attr("value", ajaxResponse.secretoryName).val(ajaxResponse.propNo);
			if(ajaxResponse.flatNo == '' || ajaxResponse.flatNo == "null"){
				$("#flatNo" + index).attr("value", '').val('');
			}else{
				$("#flatNo" + index).attr("value", ajaxResponse.secretoryName).val(ajaxResponse.flatNo);
			}
			$("#ownerName" + index).attr("value", ajaxResponse.ownerName).val(ajaxResponse.ownerName);
			$("#address" + index).attr("value", ajaxResponse.ownerAddress).val(ajaxResponse.ownerAddress);
			$("#totalOutsatandingAmt" + index).attr("value", ajaxResponse.totalOutsatandingAmt).val(ajaxResponse.totalOutsatandingAmt);
			
		} else {
			$("#propNo" + index).attr("value", '').val('');
			$("#flatNo" + index).attr("value", '').val('');
			$("#ownerName" + index).attr("value", '').val('');
			$("#address" + index).attr("value", '').val('');
			$("#totalOutsatandingAmt" + index).attr("value", '').val(0);
			errorList.push("No Property Details Found")
			displayErrorOnPage(errorList);
		}

	} else {
		displayErrorOnPage(errorList);
	}
}


function reOrderTableIdSequenceProp(tableId, rowId) {
if($("#finacialYearId0").val()==undefined){
	$("#" + tableId + " tbody tr").each(function(i) {
		var count = i + 1;
		$(this).closest("tr").attr("class", rowId+(i)).attr("id", rowId + (i));
		$(this).find("input:text:eq(0)").attr("id", "SrNo" + (i)).attr("value", (count)).attr("text",count).val(count);
		$(this).find("input:text:eq(1)").attr("id", "propNo" + (i)).attr("name", "noDuesCertificateDto.propertyDetails[" + (i) + "].propNo").attr("onblur", "getFlatDetails(" + (i) + ")");
		$(this).find("select:eq(0)").attr("id", "flatNo" + (i)).attr("name", "noDuesCertificateDto.propertyDetails[" + (i) + "].flatNo").attr("onblur", "getPropertyDetails(" + (i) + ")");
		$(this).find("input:text:eq(2)").attr("id", "ownerName" + (i)).attr("name", "noDuesCertificateDto.propertyDetails[" + (i) + "].ownerName");
		$(this).find("input:text:eq(3)").attr("id", "address" + (i)).attr("name", "noDuesCertificateDto.propertyDetails[" + (i) + "].ownerAddress");
		//$(this).find("#delete" + rowId).attr("onclick", "remove" + rowId + "(" + (i) + ")");
		$(this).find("input:hidden:eq(0)").attr("id", "totalOutsatandingAmt" + (i)).attr("name", "noDuesCertificateDto.propertyDetails[" + (i) + "].totalOutsatandingAmt");
		$(this).find(".btn-danger").attr("onclick", "removeConnectionProp('" + tableId + "','" + (i) + "','" + rowId +"')");
	});
}
}

function addTableEntryProp(table_id, rowId) {
	var content = $("#" + table_id).find('tr:eq(1)').clone();
	content.find("select").val('0');
	content.find("input:text").val('');
	$("#" + table_id).append(content);
	reOrderTableIdSequenceProp(table_id, rowId);
}

function removeConnectionProp(table_id, rowcnt,rowclass) {
	var tableSize = $('#' + table_id + ' tr').size();
	if (tableSize > 2) {
			$("."+rowclass + rowcnt).remove();
			var data = {
				"deletedRow" : rowcnt
			};
			var URL = 'PropertyNoDuesCertificate.html?deletePropertyNo';
			var returnData = __doAjaxRequest(URL, 'POST', data, false);
			reOrderTableIdSequenceProp(table_id, rowclass);
		} else {
			var msg = "can not remove";
			showErrormsgboxTitle(msg);
		}

	
}





function generateReport(element,url,methodName) {	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);

	var ajaxResponse = __doAjaxRequest(url + '?saveform', 'POST', requestData,
			false, '', element);
	if ($.isPlainObject(ajaxResponse)) {
		displayMessageOnSubmit(ajaxResponse.command.message, url, methodName);
	}else if (typeof(ajaxResponse) === "string") {
		$('.content-page').html(ajaxResponse);	
	}

}


function displayMessageOnSubmit(message ,url, methodName) {
	window.open(url + '?' + methodName,'_blank');
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="printCertificate(\''+url+ '\')"/></div></br>';

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

function printCertificate(uri){
	var requestData = {};
	var url = uri + "?getAcknowledement";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData,false);
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$.fancybox.close();
	generateCertificate(uri);
}

function generateCertificate(uri){
	var requestData = {};
	var url = uri + "?generateCertificate";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData,false);
window.open(ajaxResponse,'_blank');
	
}

function getFlatDetails(rowNo, dec) {
	var optionsAsString = '';
	// $('#taxCategory' + (rowNo + 2)).html('');
	var propNo = $("#propNo" + rowNo).val();
	$("#flatNo" + (rowNo)).text('');
	if (propNo != 0) {
		var data = {
			"propNo" : propNo
		};
		var URL = 'PropertyNoDuesCertificate.html?getBillingMethod';
		var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false, 'json');		
		if (ajaxResponse) {
			if (ajaxResponse.length != 0) {
				let flatMap = ajaxResponse;
				var flatlist = [];
				var singleFlatBilling = false;
				if (flatMap.I != undefined) {
					singleFlatBilling = true;
					flatlist = flatMap.I;
				} else if (flatMap.W != undefined) {
					singleFlatBilling = false;
					//flatlist = flatMap.W;
				} else if (flatMap.NULL != undefined) {
					singleFlatBilling = false;
					//flatlist = flatMap.NULL;
				}
				if (dec == undefined && singleFlatBilling) {
					optionsAsString += "<option value=''> Select</option>";
				}
				for (var i = 0; i < flatlist.length; i++) {
					optionsAsString += "<option value='" + flatlist[i] + "'>"
							+ flatlist[i] + "</option>";
				}
				$("#flatNo" + (rowNo)).append(optionsAsString);
				if (singleFlatBilling) {
					if (dec == undefined) {
						$("#flatNo" + (rowNo)).attr("readOnly", false);
						$("#ownerName" + (rowNo)).attr("readOnly", true).val('');
						$("#address" + (rowNo)).attr("readOnly", true).val('');
					}
				} else {
					if (dec == undefined) {
						$("#flatNo" + (rowNo)).attr("readOnly", true);
						$("#ownerName" + (rowNo)).attr("readOnly", true).val('');
						$("#address" + (rowNo)).attr("readOnly", true).val('');
						getPropertyDetails(rowNo);
					}
				}

			}
		}

	}
}


function ResetForm(url) {
	window.open(url,'_self');
}

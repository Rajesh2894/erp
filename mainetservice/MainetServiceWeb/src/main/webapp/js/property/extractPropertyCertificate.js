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
});


function showConfirmBoxToProceed(element) {
	var requestData = {};
	var theForm	=	'#PropertyNoDuesCertificate';
	var divName = '.content-page';
	requestData = __serializeForm(theForm);
	var ajaxResponse = doAjaxLoading('PropertyNoDuesCertificate.html?searchPage', requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	
	//var message = __doAjaxRequest('PropertyNoDuesCertificate.html?searchPage', 'POST', {}, false, 'html');
	//var returnData =__doAjaxRequestValidationAccor(element,'PropertyNoDuesCertificate.html?searchPage','POST',requestData, false,'html');
	
	
	//$(formDivName).html(returnData);
	//openPopupWithResponse(message);

	
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
	var URL = 'ExtractProperty.html?getPropertyDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	
	$("#applicantName").val($("#ownerName").val());
	$("#appliAddress").val($("#ownerAddress").val());
	
	
	}
	else {
		displayErrorOnPage(errorList);
	}
	
}

function getCheckListAndCharges(element){
	var errorList = [];
	errorList = validateNoDuesForm(errorList);
	if (errorList.length == 0){
		var theForm	=	'#PropertyNoDuesCertificate';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ExtractProperty.html?getCheckListAndCharges';
		var returnData =__doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		
		if(returnData){
		$(formDivName).html(returnData);
		$("#amountToPay").val($("#totalPayAmt").val());	
		$("#checkListCharge").hide();
		}else{
			$("#checkListCharge").show();
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

function savePropertyFrom(element) 
{
	var errorList = [];
	errorList = validateNoDuesForm(errorList);
	if (errorList.length == 0){
		
		if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N' || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
	  	{
		   return saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", 'ExtractProperty.html?PrintReport', 'saveform');
	    }
	    else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y')
	    {
	        saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", 'ExtractProperty.html?redirectToPay', 'saveform');
	       
	    } 	   
	   else
	    {
	      return saveOrUpdateForm(element,"Your application for No Dues Cettificate saved successfully!", 'ExtractProperty.html?proceed', 'saveform');

	    }
	}else {
		displayErrorOnPage(errorList);
	}		
}

/*function closebox(divName,redirectUrl){
	
	alert();
	
	if(typeof redirectUrl!== undefined && redirectUrl!=='undefined' && redirectUrl!=null && (redirectUrl!=''))
	{	
		try
		{
		 var lastIndex = redirectUrl.lastIndexOf("?");
		 
	     var lastchar = redirectUrl.substring(lastIndex	+	1);	
	     	   	   
		if(lastchar == 'PrintReport' || lastchar == 'PrintChallanOnline' || lastchar == 'PrintCounterReceipt' || lastchar == 'PrintChecklistReport')
			{
				 window.open(redirectUrl,'_blank');	//print loi and challan receipt		
				 alert("....");
				 //window.location.href='PropertyNoDuesCertificate.html?proceed';
				generateNoDuesCertificate(element);
			}
		else if(lastchar == 'PrintULBCounterReceipt' )
		     {
				 window.open(redirectUrl,'_blank');			
				 window.location.href='ChallanAtULBCounter.html?clean';
			}
		else if(lastchar == 'PrintRegenerateChallan'){
			 window.open(redirectUrl,'_blank');			
			 window.location.href='ChallanAtULBCounter.html?search';
		}
		else
			{ 
			//printchecklistreport
				$("#postMethodFormSuccess").prop('action', '');
				$("#postMethodFormSuccess").prop('action', redirectUrl);
				$("#postMethodFormSuccess").submit();			
			}
		}
		catch(e)
		{
			//window.location.href=redirectUrl;
			$("#postMethodFormSuccess").prop('action', '');
			$("#postMethodFormSuccess").prop('action', redirectUrl);
			$("#postMethodFormSuccess").submit();
		}
	}
	
	
	return false;
}

function disposeModalBox()
{	
	$('#'+modalDivName).hide();
	return false;
}

function generateNoDuesCertificate(element){

	
    //return saveOrUpdateForm(element,"Your application for No Dues Cettificate saved successfully!", 'PropertyNoDuesCertificate.html?proceed', 'saveform');

	var Data = {};
	var URL = 'PropertyNoDuesCertificate.html?proceed';
	var returnData = __doAjaxRequest(URL, 'POST', Data, false,'html');		

	//var returnData =__doAjaxRequest(URL,'POST',Data, false,'html');
}*/

function validateNoDuesForm(errorList){
	var name=$("#applicantName").val();
	var address=$("#appliAddress").val();
	var pinCode=$("#appliPincode").val();
	var mobNo=$("#appliMobileno").val();
	var noOfCopies=$("#noOfCopies").val();
	//var email=$("#appliEmail").val();
	
	if(name=="" || address=="" || pinCode=="" || mobNo=="" ){
		errorList.push(getLocalMessage("prop.fields.mandatory"));
	}
	if (noOfCopies == "" || noOfCopies == undefined
		    || noOfCopies == null) {
		errorList.push(getLocalMessage("prop.enter.no.copies"));
		
	}
	return errorList;
}

function openNoDuesWindowTab(status) {
    if (!status) {
	var URL = 'ExtractProperty.html?proceed';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'Extraction Of Property';
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
		var URL = 'ExtractProperty.html?getPropertyDetailsByFlatNo';
		var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false);
		if (ajaxResponse) {
			$("#propNo" + index).attr("value", ajaxResponse.secretoryName).val(ajaxResponse.propNo);
			$("#flatNo" + index).attr("value", ajaxResponse.secretoryName).val(ajaxResponse.flatNo);
			$("#ownerName" + index).attr("value", ajaxResponse.ownerName).val(ajaxResponse.ownerName);
			$("#address" + index).attr("value", ajaxResponse.ownerAddress).val(ajaxResponse.ownerAddress);
		} else {
			$("#propNo" + index).attr("value", '').val('');
			$("#flatNo" + index).attr("value", '').val('');
			$("#ownerName" + index).attr("value", '').val('');
			$("#address" + index).attr("value", '').val('');
			showErrormsgboxTitle("No Property Details Found");
		}

	} else {
		displayErrorOnPage(errorList);
	}
}
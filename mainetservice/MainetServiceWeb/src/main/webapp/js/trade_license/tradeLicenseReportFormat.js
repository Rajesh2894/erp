$(document).ready(function() {
	
	var value= $("#imgMode").val();
	var hyperLink='';  
	 hyperLink+="<img src='./"+value+"'/>";
	 
	 $('#propImages').html(hyperLink);

	$("#resetform").on("click", function(){ 
		  window.location.reload("#tradeLicensePrint")
		});
	
	});

function backPage() {
window.location.href = getLocalMessage("AdminHome.html");
}

function viewLicense(element) 
{
	var errorList = [];
	var trdLicno = $("#trdLicno").val();
	
	if (trdLicno == "") {
		errorList.push(getLocalMessage("trade.printing.licno"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);	
	} 
	else {
	
	var divName = '.content-page';
	var actionParam = {
			'trdLicno' : trdLicno
		}
	var url = "TradeLicenseReportFormat.html?viewLicense";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	}

	 
}

function printdiv(printpage) {
	
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

function resetLicenseForm()
{
	
	$('input[type=text]').val('');  
	$(".alert-danger").hide();
	$("#tradeLicensePrint").validate().resetForm();
}

function displayImage(images){
	hyperLink='';  
	if(images != null){
		  $.each( images, function( key, value ) {
			  hyperLink+="<a href='./"+value+"' class='fancybox'><img src='./"+value+"' class='img-thumbnail margin-top-10'/></a>";
			});
	  }
	  $('#propImages').html(hyperLink);
}

function SearchLicense(element) {

	var errorList = [];
	var divName = '.content-page';
	var trdLicno = $("#trdLicno").val();
	if (trdLicno == "") {
		errorList.push(getLocalMessage("trade.VaildLicenseNo"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var data = {
			"trdLicno" : trdLicno
		};
		var URL = 'TradeLicenseReportFormat.html?getLicenseDetail';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		prepareTags();
	}
}

function printCertificate(trdLicno) {

	var errorList = [];
	var requestData = 'trdLicno=' + trdLicno ;
	var URL = 'TradeLicenseReportFormat.html?PrintLicenseCertificate';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	if (returnData == "f") {
		errorList.push('Invalid');
		displayErrorsOnPage(errorList);
	} else {
		window.open(returnData, '_blank');
	}

}

function email(trdLicno) {

	var errorList = [];
	var requestData = 'trdLicno=' + trdLicno ;
	var URL = 'TradeLicenseReportFormat.html?EmailCertificate';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	if (returnData == "f") {
		showBoxForApproval(getLocalMessage('trade.fail.email'));
	} else {
		showBoxForApproval(getLocalMessage('trade.sucess.email'));
	}
}

function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = 'Proceed';
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
			+ '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}
function closeApproval() {
	window.location.href = 'TradeLicenseReportFormat.html?PrintCertificate';
	$.fancybox.close();
}

function uploadSignCertificate(trdLicno) {

	var errorList = [];
	var divName = '.content-page';
	var requestData = 'trdLicno=' + trdLicno;
	var URL = 'TradeLicenseReportFormat.html?uploadSignCertificate';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}

function saveSignCertificate(element) {

	var errorList = [];
	var trdLicno =$("#trdLicno").val();
	var requestData = 'trdLicno=' + trdLicno;
	var URL = 'TradeLicenseReportFormat.html?saveSignCertificate';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	if (returnData == "false") {
		errorList
		.push(getLocalMessage("trade.uploadCertificate.error"));
		displayErrorsOnPage(errorList);
	} else if (returnData == "e") {
		errorList
		.push(getLocalMessage("trade.file.exist"));
		displayErrorsOnPage(errorList);
	} else {
		showBoxForApproval(getLocalMessage('trade.certificate.save.msg'));
	}
}


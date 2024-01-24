$(document).ready(function() {
	
	showVehicleRegNo();
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate:'0'		
	});
});
function saveWasteCollector(element) {
	
	var errorList = [];
	var	file=$("#attachments0").val();
	

  if (file == "" || file == undefined || file == '0') {
  errorList.push(getLocalMessage('swm.upload.garbage.collection.img')); }
 

	// errorList = validateApplicantDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
		getVehicleRegNo();
	} else {
		$("#errorDiv").hide();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		return doAjaxOperation(element,
				"Your application for C&D collection saved successfully!",
				'WasteCollector.html?PrintReport', 'saveform');
	}
}

function showVehicleRegNo() {
	
	$('#veid').html('');
	var crtElementId = $("#vehicleType").val();
	var requestUrl = "WasteCollector.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#veid').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
	$('#veid').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#veid').trigger('chosen:updated');	

}

function getVehicleRegNo() {
	
	$('#veid').html('');
	var crtElementId = $("#vehicleType").val();
	var requestUrl = "WasteCollector.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');	
	 $.each(ajaxResponse, function(index, value) {
	$('#veid').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#veid').trigger('chosen:updated');	

}


function validateApplicantDetails(errorList) {
	
	var applicantTitle = $.trim($('#applicantTitle').val());
	var firstName = $.trim($('#firstName').val());
	var lastName = $.trim($('#lastName').val());
	var gender = $.trim($('#gender').val());
	var applicantMobileNo = $.trim($('#mobileNo').val());
	var applicantAreaName = $.trim($('#areaName').val());
	var villTownCity = $.trim($('#villTownCity').val());
	var applicantPinCode = $.trim($('#pinCode').val());
	var capacity = $.trim($('#capacity').val());
	var trip = $.trim($('#noTrip').val());
	var permission = $('input[type=radio]').attr('value');
	var complaintNo = $.trim($('#complainNo').val());
	var locationId = $.trim($('#locationId').val());
	var vehicleId = $.trim($('#vehicleType').val());
	var vehicleNo = $.trim($('#veid').val());
	var locAddress = $.trim($('#locAddress').val());

	if (applicantTitle == "" || applicantTitle == '0'
			|| applicantTitle == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantNameTitle'));
	}
	if (firstName == "" || firstName == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantFirstName'));
	}
	if (lastName == "" || lastName == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantLastName'));
	}
	if (gender == "" || gender == '0' || gender == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.ApplicantGender'));
	}

	if (applicantMobileNo == '' || applicantMobileNo == null
			|| applicantMobileNo == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.applicantMobileNo'));
	} else {
		if (!validateMobile(applicantMobileNo)) {
			errorList
					.push(getLocalMessage('construct.demolition.validation.ApplicantInvalidmobile'));
		}
	}
	if (applicantAreaName == "" || applicantAreaName == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.applicantarea'));
	}


	if (applicantPinCode == "" || applicantPinCode == undefined) {
		errorList
				.push(getLocalMessage('construct.demolition.validation.applicantPinCode'));
	}
	if (capacity == "" || capacity == undefined || capacity == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.capacity'));
	}
	if (trip == "" || trip == undefined || trip == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.trip'));
	}

	if (locationId == "" || locationId == undefined || locationId == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.locationId'));
	}
	if (vehicleId == "" || vehicleId == undefined || vehicleId == '0') {
		errorList
				.push(getLocalMessage('construct.demolition.validation.vehicleId'));
	}
	if (locAddress == "" || locAddress == undefined || locAddress == '0') {
		errorList
				.push(getLocalMessage('swm.address.constructionSite.not.empty'));
	}

	return errorList;
}

function getChecklistAndCharges(element) {
	
	var errorList = [];
	errorList = validateApplicantDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);		
	} else {		
	var veNo = $("#veid").val();
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = "WasteCollector.html?getCheckListAndCharges";
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');

	if (returnData != null) {
		$('.content-page').removeClass('ajaxloader');
		$('.content-page').html(returnData);
	}
	getVehicleRegNo();
	$("#veid").val(veNo);
	}
}

function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function showErrAstInfo(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstInfo()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	errorList = [];
}

function back() {
	window.location.href = "AdminHome.html";
}

function showVehicleRegNo() {
	
	$('#veid').html('');
	var crtElementId = $("#vehicleType").val();
	var requestUrl = "WasteCollector.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	 $('#veid').append($("<option></option>").attr("value",0).attr("code",0).text("select"));
	 $.each(ajaxResponse, function(index, value) {
	$('#veid').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#veid').trigger('chosen:updated');	

}

function getVehicleRegNo() {
	
	$('#veid').html('');
	var crtElementId = $("#vehicleType").val();
	var requestUrl = "WasteCollector.html?vehicleNo";
	var requestData = {
		"id" : crtElementId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');	
	 $.each(ajaxResponse, function(index, value) {
	$('#veid').append($("<option></option>").attr("value",index).attr("code",index).text(value));
	});
	$('#veid').trigger('chosen:updated');	

}

function saveFinalApproval(formUrl, actionParam,obj) {
	var errorList = [];
	var decision = $("input:radio[name='workflowActionDto.decision']").filter(":checked").val();

	errorList = ValidateConNDemolition(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	}else{
	
	var divName = '.content-page';
	var requestData = __serializeForm('#constructDemolitionWasteCollector');
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData, 'html',
			divName);
	$(divName).removeClass('ajaxloader');	
	$(divName).html(ajaxResponse);
	prepareTags();	
	if(decision =="APPROVED"){
	showSaveResultBox2("WasteCollector.html?WasteApproval");
	}else{
		showSaveResultBox2("AdminHome.html");
	}
	}

}
function ValidateConNDemolition(errorList){
	
	var veid = $("#veid").val();
	var mrfId = $("#mrfId").val();
	var empName = $("#empName").val();
	var pickUpDate = $("#pickUpDate").val();
	var decision = $("input:radio[name='workflowActionDto.decision']").filter(":checked").val();
	if(decision =="" || decision==null){
		errorList.push( getLocalMessage("construct.demolition.validation.decision"));
	}
	if(decision=="APPROVED"){
		if (veid == "0" || veid == null)
			errorList.push( getLocalMessage("construct.demolition.validation.vehicleNo"));
		if (mrfId == "0" || mrfId == null)
			errorList.push( getLocalMessage("construct.demolition.validation.mrfcenter"));
		if (empName == "" || empName == null)
			errorList.push( getLocalMessage("construct.demolition.validation.empName"));
		if (pickUpDate == "0" || pickUpDate == null || pickUpDate == "")
			errorList.push( getLocalMessage("construct.demolition.validation.dateofPickup"));
		
	}
	return errorList;
}

function showSaveResultBox2(redirectUrl) {
	
	var messageText = getLocalMessage("swm.application.submit.success");
	var message = '';
	var cls = getLocalMessage('eip.page.process');
	message += '<p class="text-blue-2 text-center padding-15">' + messageText
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
			+ ' onclick="closebox2(\'' + errMsgDiv + '\',\'' + redirectUrl
			+ '\')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
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
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function closebox2(divName,redirectUrl){
	
	if(redirectUrl.lastIndexOf("?") == "-1"){
		window.location.href='AdminHome.html';
	}else{
	
		$("#postMethodFormSuccess").prop('action', '');
		$("#postMethodFormSuccess").prop('action', redirectUrl);
		$("#postMethodFormSuccess").submit();			
	}		
	disposeModalBox();	
	return false;
}

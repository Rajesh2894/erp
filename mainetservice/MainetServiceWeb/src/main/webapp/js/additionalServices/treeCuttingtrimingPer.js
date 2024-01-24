var programList = new Array();
$(document).ready(function() {

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
	});
});

function ResetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'TreeCuttingTrimmingPermission.html?formForCreate', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'TreeCuttingTrimmingPermission.html?formForCreate', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetTreeDetailForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'TreeCuttingTrimmingPermission.html?ResetTreeDetailForm', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function resetSummaryForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'TreeCuttingTrimmingPermission.html?summary', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function proceedToTreeInfo(obj) {
	debugger;
	var errorList = [];
	errorList = validateApplicantForm(errorList);
	if (errorList.length == 0) {

		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "TreeCuttingTrimmingPermission.html?treeInfo";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function proceedToSaveDetails(obj) {
	debugger;
	var errorList = [];
	// errorList = validateForm(errorList)

	/*
	 * var len=$("#checkList").length; if(len == 0){ errorList.push("Upload
	 * Mandatory documents"); }
	 */
	if (errorList.length == 0) {
		/*
		 * saveOrUpdateForm(obj, 'Nursing Home Information Saed succesfully',
		 * 'NursingHomePermission.html', 'saveform');
		 */

		/*saveOrUpdateForm(obj, "Your application Data  saved successfully!!",
				'TreeCuttingTrimmingPermission.html',
				'generateChallanAndPayement');*/
		
		var object=__doAjaxRequest(
				'TreeCuttingTrimmingPermission.html?generateChallanAndPayement', 'POST',
				{}, false, 'json');
		if (object.error != null && object.error != 0) {
			$.each(object.error, function(key, value) {
				$.each(value, function(key, value) { 
					if (value != null && value != '') {
						errorList.push(value);
					}
				});
			});
			displayErrorsOnPage(errorList);
		}else{
			if (object.Success != null && object.Success != undefined ) {
				//showBoxForApproval(getLocalMessage("Your application No." + " "+ object.applicationId + " "+ "has been submitted successfully."));
				showConfirmBox(object.Success);
				// print acknowledgement
				nocRegAcknow();
			}
		}
	} else
		displayErrorsOnPage(errorList);
}

function showConfirmBox(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("NHP.proceed");
	var C = "C";
	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeBox();"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function closeBox() {
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
}

function nocRegAcknow() {
	var URL = 'TreeCuttingTrimmingPermission.html?printCFCAckRcpt';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	
	var appId = $($.parseHTML(returnData)).find("#applicationId").html();
	var title = appId;
	prepareTags();
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<html><link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}


function validateApplicantForm(errorList) {
	debugger;
	var errorList = [];
	var titleId = $("#titleId").val();
	var firstName = $("#firstName").val();
	var midName = $("#midName").val();
	var lName = $("#lName").val();
	var gender = $("#gender").val();
	var buildingName = $("#buildingName").val();
	var block = $("#block").val();
	var roadName = $("#roadName").val();
	var pinCode = $("#pinCode").val();
	var city = $("#city").val();
	var mobNumber = $("#mobNumber").val();
	var emailId = $("#emailId").val();
	var serviceId = $("#serviceId").val();
	var cfcWard1 = $("#cfcWard1").val();
	var languageId=$("#langId").val();
	
	if (firstName == "") {
		errorList.push(getLocalMessage("NHP.validation.firstName"));
	}
	/*if (midName == "") {
		errorList.push(getLocalMessage("NHP.validation.midName"));
	}*/
	if (lName == "") {
		errorList.push(getLocalMessage("NHP.validation.lName"));
	}
	
	if(languageId == 1){
		if(firstName != "" && !validateName(firstName)) {
			errorList.push(getLocalMessage("CFC.firstName.validn"));
		}
		if(midName != "" && !validateName(midName)) {
			errorList.push(getLocalMessage("CFC.middleName.validn"));
		}
		if(lName != "" && !validateName(lName)) {
			errorList.push(getLocalMessage("CFC.lastName.validn"));
		}
	}else{
		if(firstName != "" && !validateRegName(firstName)) {
			errorList.push(getLocalMessage("CFC.firstName.validn"));
		}
		if(midName != "" && !validateRegName(midName)) {
			errorList.push(getLocalMessage("CFC.middleName.validn"));
		}
		if(lName != "" && !validateRegName(lName)) {
			errorList.push(getLocalMessage("CFC.lastName.validn"));
		}
	}
	
	if (gender == "") {
		errorList.push(getLocalMessage("NHP.validation.gender"));
	}
	/*if (buildingName == "") {
		errorList.push(getLocalMessage("NHP.validation.buildingName"));
	}*/	//from 
		if(languageId == 1){
			if (buildingName != "" && !validateName(buildingName)){
				errorList.push(getLocalMessage("CFC.buildingName.validn"));
			}
		}else{
			if(buildingName != "" && !validateRegName(buildingName)) {
				errorList.push(getLocalMessage("CFC.buildingName.validn"));
			}
		}//to
	
	/*if (block == "") {
		errorList.push(getLocalMessage("NHP.validation.block"));
	}*/
	/*if (roadName == "") {
		errorList.push(getLocalMessage("NHP.validation.roadName"));
	}	//from
	*/	if(languageId == 1){
			if (roadName != "" && !validateName(roadName)){
				errorList.push(getLocalMessage("CFC.validation.roadName.validn"));
			}
		}else{
			if(roadName != "" && !validateRegName(roadName)) {
				errorList.push(getLocalMessage("CFC.validation.roadName.validn"));
			}
		}//to
	
	/*if (pinCode == "") {
		errorList.push(getLocalMessage("NHP.validation.pinCode"));
	}*/
	if (cfcWard1 == 0) {
		errorList.push(getLocalMessage("CFC.select.ward"));
	}
	if (city == "") {
		errorList.push(getLocalMessage("NHP.validation.city"));
	}	//from
		if(languageId == 1){
			if (city != "" && !validateName(city)){
				errorList.push(getLocalMessage("CFC.validation.city.validn"));
			}
		}else{
			if(city != "" && !validateRegName(city)) {
				errorList.push(getLocalMessage("CFC.validation.city.validn"));
			}
		} //to
	
	if (mobNumber == "") {
		errorList.push(getLocalMessage("NHP.validation.mobNumber"));
	}
	/*if (emailId == "") {
		errorList.push(getLocalMessage("NHP.validation.emailId"));
	}*/
	if (serviceId == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.serviceId"));
	}
	//from
	var pattern = /^(?!0{6})[0-9]{6}$/;//to
	if(pinCode !=""){
	if (!pattern.test(pinCode)) {
		errorList.push(getLocalMessage("NHP.validation.pinCode.valid"));
	}
	}

	var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	if(emailId !=""){
	var valid = emailRegex.test(emailId);
	if (!valid) {
		errorList.push(getLocalMessage("NHP.validation.emailId.valid"));
	}
	}
	if (!validateMobile(mobNumber)) {
		errorList
				.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
	}

	return errorList;
}

function validateName(name){
	var regexPattern = /^([a-zA-Z\u0900-\u097F\ \.\-\']){2,30}$/;
	return regexPattern.test(name);
}

function validateRegName(name){
	var regexPattern = /^([a-zA-Z\u0900-\u097F\ \.\-\']){2,30}$/;
	return regexPattern.test(name);
}

function validateMobile(mobile) {

	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}
/*function validateCount(count) {

	var regexPattern = /^[1-9]\d{5}$/;
	return regexPattern.test(count);
}*/
//from 
function validateAddress(address){
	
	var regexPattern = /^([a-zA-Z0-9\u0900-\u097F\ \.\-\']){2,30}$/;
	return regexPattern.test(address);
	
	}

function validateRegAddress(address){
	
	var regexPattern = /^([a-zA-Z0-9\u0900-\u097F\ \.\-\']){2,30}$/;
	return regexPattern.test(address);
	
	}//to

function backToApplicantForm() {
	debugger;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading("TreeCuttingTrimmingPermission.html?form",
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function ResetTreeDetailForm() {

	var appName = $("#appName").val("");

	var mobNumber = $('#mobNumber').val("");

	var emailId = $("#emailId").val("");
	var address = $("#address").val("");
	var treeDesc = $("#treeDesc").val("");
	var count = $("#count").val("");
	var reason = $("#reason").val("");
	var location = $("#location").val("");
}

function proceedToChecklist(obj) {
	debugger;
	var errorList = [];
	errorList = validateTreeData(errorList);
	if (errorList.length == 0) {
		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "TreeCuttingTrimmingPermission.html?proceedToCheckList";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}

}

function backToTreeForm(obj) {
	var errorList = [];

	if (errorList.length == 0) {
		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "TreeCuttingTrimmingPermission.html?treeDetailEntryForm";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function proceedForTreeDetailForm(obj) {
	
	var errorList = [];
	errorList = validateApplicantForm(errorList);
	if (errorList.length == 0) {
		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "TreeCuttingTrimmingPermission.html?treeDetailEntryForm";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}

}

function validateTreeData(errorList) {
	debugger;
	var errorList = [];
	var appName = $("#appName").val();
	
	var mobNumber = $('#mobNumber').val();

	var emailId = $("#emailId").val();
	var address = $("#address").val();
	var treeDesc = $("#treeDesc").val();
	var count = $("#count").val();
	var reason = $("#reason").val();
	var location = $("#location").val();
	var languageId=$("#langId").val();
	
	if (appName == "") {
		errorList.push(getLocalMessage("TCP.validation.app.name"));
	}

	if(languageId == 1){
		if(appName != "" && !validateName(appName)) {
			errorList.push(getLocalMessage("TCP.applicant.valid.name"));
		}
	}
	else{
		if(appName != "" && !validateRegName(appName)) {
			errorList.push(getLocalMessage("TCP.applicant.valid.name"));
		}	
	}	
	if (mobNumber == "") {
		errorList.push(getLocalMessage("TCP.validation.mobile.no"));
	}
	/*if (emailId == "") {
		errorList.push(getLocalMessage("TCP.validation.email.id"));
	}*/
	if (address == "") {
		errorList.push(getLocalMessage("TCP.validation.address"));
	}
	//from
	if(languageId == 1){
		if(address != "" && !validateAddress(address)) {
			errorList.push(getLocalMessage("TCP.applicant.valid.address"));
		}
	}
	else{
		if(address != "" && !validateRegAddress(address)) {
			errorList.push(getLocalMessage("TCP.applicant.valid.address"));
		}	
	}//to	
	if (treeDesc == "") {
		errorList.push(getLocalMessage("TCP.validation.tree.desc"));
	}
	//from
	if(languageId == 1){
		if(treeDesc != "" && !validateName(treeDesc)) {
			errorList.push(getLocalMessage("TCP.info.tree.desc.valid"));
		}
	}
	else{
		if(treeDesc != "" && !validateRegName(treeDesc)) {
			errorList.push(getLocalMessage("TCP.info.tree.desc.valid"));
		}	
	}//to	
	
	if (count == "") {
		errorList.push(getLocalMessage("TCP.validation.tree.count"));
	}
	if (reason == "") {
		errorList.push(getLocalMessage("TCP.validation.tree.count"));
	}
	//from
	if(languageId == 1){
		if(reason != "" && !validateName(reason)) {
			errorList.push(getLocalMessage("TCP.info.reason.valid"));
		}
	}
	else{
		if(reason != "" && !validateRegName(reason)) {
			errorList.push(getLocalMessage("TCP.info.reason.valid"));
		}	
	}//to
	
	if (location == "") {
		errorList.push(getLocalMessage("TCP.validation.location"));
	}
	//from
	if(languageId == 1){
		if(location != "" && !validateName(location)) {
			errorList.push(getLocalMessage("TCP.info.location.valid"));
		}
	}
	else{
		if(location != "" && !validateRegName(location)) {
			errorList.push(getLocalMessage("TCP.info.location.valid"));
		}	
	}//to
	
	if (!validateMobile(mobNumber)) {
		errorList
				.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
	}
	count=count*1;
	if(count == 0){
		errorList
		.push(getLocalMessage("TCP.validation.count.valid"));
	}
	/*if (!validateCount(count)) {
		errorList
				.push(getLocalMessage("Please enter valid count"));
	}*/
	
	
	var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	if(emailId != ""){
	var valid = emailRegex.test(emailId);
	if (!valid) {
		errorList.push(getLocalMessage('NHP.validation.emailId.valid'));
	}
	}
	

	return errorList;
}

function searchData() {
	debugger;
	var errorList = [];

	var serviceId = $("#serviceId").val();
	var appId = $("#appId").val();

	if (serviceId == "" || appId == "") {
		errorList.push(getLocalMessage("CFC.fields.requires"));
	}

	if (errorList.length == 0) {

		formUrl = "TreeCuttingTrimmingPermission.html?searchData";
		var requestdata = {
			"serviceId" : serviceId,
			"refId" : appId
		};

		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl, requestdata, 'html', divName);

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();

		var flag = $("#flag").val();
		if (flag == "E") {
			errorList.push(getLocalMessage("CFC.no.record"));
			displayErrorsOnPage(errorList);
		}
	} else {
		displayErrorsOnPage(errorList);
	}

}

function viewTreeInfoData(formUrl, actionParam, appId) {
	debugger;

	// var appId=$("#appId").val();
	var requestdata = {
		"appId" : appId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

	$("#TreeCuttingPermissionForm :input").prop("disabled", true);

	$("#viewTreeInfo").prop('disabled', false);
	$("#backButton").prop('disabled', false);
}

function viewTreeInfoD(formUrl, actionParam) {
	debugger;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

	$("#TreeCuttingPermissionForm :input").prop("disabled", true);
	$("#viewTreeInfo").prop('disabled', false);
	$("#backButton").prop('disabled', false);
}

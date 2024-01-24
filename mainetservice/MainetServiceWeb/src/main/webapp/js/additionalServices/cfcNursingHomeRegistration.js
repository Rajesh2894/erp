var programList = new Array();
var facilityTest = new Array();
var facilityCenter = new Array();
$(document).ready(function() {

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
	});
});

function ResetForm() {
	var bred=localStorage.getItem('breadCrumbData');
	var sumvar=localStorage.getItem('someVarName');
	localStorage.clear();
	localStorage.setItem('breadCrumbData',bred);
	localStorage.setItem('someVarName',sumvar);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('NursingHomePermission.html?form', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function formForCreate() {
	var bred=localStorage.getItem('breadCrumbData');
	var sumvar=localStorage.getItem('someVarName');
	localStorage.clear();
	localStorage.setItem('breadCrumbData',bred);
	localStorage.setItem('someVarName',sumvar);
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(
			'NursingHomePermission.html?formForCreate', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetHospitalForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('NursingHomePermission.html?hospitalInfo',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}
function ResetSummaryForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('NursingHomePermission.html?resetSummary',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function proceedTohospitalInfo(obj) {
	var errorList = [];
	errorList = validateApplicantForm(errorList);
	if (errorList.length == 0) {
		
		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "NursingHomePermission.html?hospitalInfo";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function addProgeams(obj) {
	var value = $(obj).attr("value");

	if ($(obj).is(":checked")) {
		programList.push(value);
	} else {
		programList.pop(programList.indexOf(value), 1);
	}
}

 // #129863
function addfacilitiesTest(obj) {
	var value = $(obj).attr("value");

	if ($(obj).is(":checked")) {
		facilityTest.push(value);		
	} else {
		facilityTest.pop(facilityTest.indexOf(value), 1);		
	}
}

function addfacilitiesCenter(obj) {
    var prediagcenter = $("#centerType").val();
	var value = $(obj).attr("value");
	if ($(obj).is(":checked")) {
		facilityCenter.push(value);
	}else{
	 facilityCenter.pop(facilityCenter.indexOf(value), 1);
	}
}
function proceedToSaveDetails(obj) {
	var errorList = [];
	
	
	var paymentCheck = $("#paymentCheck").val();
	if(paymentCheck == 'Y'){
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
		":checked").val() != 'N' && $("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val() != 'Y' &&  $("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val() != 'P') {
		
			errorList.push(getLocalMessage("NHP.validation.payment.mode"));
			displayErrorsOnPage(errorList);
		}
		
		if($("input:radio[name='offlineDTO.onlineOfflineCheck']")
			.filter(":checked").val() == 'P'){
			var payModeIn = $("#payModeIn").val();
			if(payModeIn == "0"){
				errorList.push(getLocalMessage("NHP.validation.colllection.mode"));
			}
			var payModeIn = $('#payModeIn option:selected').attr('code');
			if(payModeIn != undefined && payModeIn != 'C'){
				if(payModeIn == "D"){
					if(!validateChkNo($("#chqNo").val())) {
						errorList.push(getLocalMessage("NHP.validation.DD.no"));
					}
				}else{
					if(!validateChkNo($("#chqNo").val())) {
						errorList.push(getLocalMessage("NHP.validation.chk.no"));
					}
				}
				
				if(!validateAcNo($("#acNo").val())) {
					errorList.push(getLocalMessage("NHP.validation.account.no"));
				}
			}
			
		}
	}
	
	//var checkList = $("#checkList").val();
	
	if (errorList.length == 0) {
		/*
		 * saveOrUpdateForm(obj, 'Nursing Home Information Saed succesfully',
		 * 'NursingHomePermission.html', 'saveform');
		 */
		if(paymentCheck == 'Y'){
	     saveOrUpdateForm(obj, "Your application Data  saved successfully!!",
				'NursingHomePermission.html?PrintReport', 'generateChallanAndPayement');
		}else{
			/*saveOrUpdateForm(obj, "Your application Data  saved successfully!!",
					'NursingHomePermission.html', 'generateChallanAndPayement');*/
			
			var object=__doAjaxRequest(
					'NursingHomePermission.html?saveApplication', 'POST',
					{}, false, 'json');
			/*var msg=saveOrUpdateForm(obj, "Your application Data  saved successfully!!",
					'NOCForOtherGovtDept.html', 'generateChallanAndPayement');*/
			
			//display error MSG if any
			if (object.error != null && object.error != 0) {
				$.each(object.error, function(key, value) {
					$.each(value, function(key, value) { 
						if (value != null && value != '') {
							errorList.push(value);
						}
					});
				});
				showErrNursInfo(errorList);
			}else{
				if (object.Success != null && object.Success != undefined ) {
					//showBoxForApproval(getLocalMessage("Your application No." + " "+ object.applicationId + " "+ "has been submitted successfully."));
					showConfirmBox(object.Success);
					// print acknowledgement
					nocRegAcknow();

				}
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
	var URL = 'NursingHomePermission.html?printCFCAckRcpt';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	
	var title = 'NOC Registration Acknowlegement';
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
	var cfcWard2 = $("#cfcWard2").val();
	var langId=$("#langId").val();
	/**** Local Storage Data Set **********/
		localStorage.setItem('titleId',titleId);
		localStorage.setItem('firstName',firstName);
		localStorage.setItem('midName',midName);
		localStorage.setItem('lName',lName);
		localStorage.setItem('gender',gender);
		localStorage.setItem('buildingName',buildingName);
		localStorage.setItem('block',block);
		localStorage.setItem('roadName',roadName);
		localStorage.setItem('pinCode',pinCode);
		localStorage.setItem('city',city);
		localStorage.setItem('mobNumber',mobNumber);
		localStorage.setItem('emailId',emailId);
		localStorage.setItem('serviceId',serviceId);
		localStorage.setItem('cfcWard1',cfcWard1);
		localStorage.setItem('cfcWard2',cfcWard2);
	/**** Local Storage Data Set End ******/
	if (firstName == "") {
		errorList.push(getLocalMessage("NHP.validation.firstName"));
	}
	/*if (midName == "") {
		errorList.push(getLocalMessage("NHP.validation.midName"));
	}*/
	if (lName == "") {
		errorList.push(getLocalMessage("NHP.validation.lName"));
	}
	if(langId == 1){
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
	}*/
	if(langId == 1){
		if (buildingName != "" && !validateName(buildingName)){
			errorList.push(getLocalMessage("CFC.buildingName.validn"));
		}
	}else{
		if(buildingName != "" && !validateRegName(buildingName)) {
			errorList.push(getLocalMessage("CFC.buildingName.validn"));
		}
	}

	/*if (block == "") {
		errorList.push(getLocalMessage("NHP.validation.block"));
	}*/
	if (block !="" && !validateBlock(block)) {
		errorList.push(getLocalMessage("CFC.validation.block.validn"));
	}
	
	
	/*if (roadName == "") {
		errorList.push(getLocalMessage("NHP.validation.roadName"));
	}*/
	if(langId == 1){
		if (roadName != "" && !validateName(roadName)){
			errorList.push(getLocalMessage("CFC.validation.roadName.validn"));
		}
	}else{
		if(roadName != "" && !validateRegName(roadName)) {
			errorList.push(getLocalMessage("CFC.validation.roadName.validn"));
		}
	}
	
	/*if (pinCode == "") {
		errorList.push(getLocalMessage("NHP.validation.pinCode"));
	}*/
	if (cfcWard1 == 0) {
		errorList.push(getLocalMessage("CFC.select.ward"));
	}
	if (city == "") {
		errorList.push(getLocalMessage("NHP.validation.city"));
	}
	if(langId == 1){
		if (city != "" && !validateName(city)){
			errorList.push(getLocalMessage("CFC.validation.city.validn"));
		}
	}else{
		if(city != "" && !validateRegName(city)) {
			errorList.push(getLocalMessage("CFC.validation.city.validn"));
		}
	}
	
	if (mobNumber == "") {
		errorList.push(getLocalMessage("NHP.validation.mobNumber"));
	}
	/*if (emailId == "") {
		errorList.push(getLocalMessage("NHP.validation.emailId"));
	}*/
	
	 var pattern=/^(?!0{6})[A-Z0-9][0-9]{5}$/;
	 if (pinCode != ""){
		if (!pattern.test(pinCode)) {
			errorList.push(getLocalMessage("NHP.validation.pinCode.valid"));
	    }
     }
	if (emailId != ""){
	var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(emailId);
	   if (!valid) {
			   errorList.push(getLocalMessage("NHP.validation.emailId.valid"));
		  } 
	   }
   
	
	if(!validateMobile(mobNumber)) {
		errorList.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
	}
	
	if (serviceId == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.serviceId"));
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

function validateBlock(block) {
	
	var regexPattern = /^([a-zA-Z0-9_ ]){2,200}$/;
	return regexPattern.test(block);
}

//changes
function validateNa(name){
	var regexPattern = /^([a-zA-Z\u0900-\u097F\ \.\-\']){2,200}$/;    
	return regexPattern.test(name);
}

function validateRegNa(name){
	var regexPattern = /^([a-zA-Z\u0900-\u097F\ \.\-\']){2,200}$/;
	return regexPattern.test(name);
}
function validateBl(block) {
	
	var regexPattern = /^([a-zA-Z0-9_\u0900-\u097F\ \.\-\ ]){2,250}$/;
	return regexPattern.test(block);
}
//

function validateRegNo(regNo) {
	
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(regNo);
}

function validateChkNo(chkNo) {
	
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(chkNo);
}
function validateAcNo(acNo) {
	
	var regexPattern = /^[0-9]\d{15}$/;
	return regexPattern.test(acNo);
}

function backToApplicantForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading("NursingHomePermission.html?form", {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function BackTohospitalInfo(){
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading("NursingHomePermission.html?hospitalInfo", {},
			'html', divName);
	var ajaxResponseFacilityCenter = doAjaxLoading("NursingHomePermission.html?getFacilityCenter", {},
			'html', divName);
	var ajaxResponseFacilityTest = doAjaxLoading("NursingHomePermission.html?getFacilityTest", {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	var arrayValues = ajaxResponseFacilityCenter.toString().replace(/[\])}[{(]/g, '');
	var arrayFacilityCenter = arrayValues.split(',');
	$.each(arrayFacilityCenter, function(i, val){
		 $("input[value='" + val + "']").prop('checked', true);
				facilityCenter.push(val);
	});
	var arrayValues2 = ajaxResponseFacilityTest.toString().replace(/[\])}[{(]/g, '');
	var arrayFacilityTest = arrayValues2.split(',');
	$.each(arrayFacilityTest, function(i, val){
		 $("input[value='" + val + "']").prop('checked', true);
		 facilityTest.push(val);
	});
	prepareTags();
}
 // #129863
function proceedToChecklist(obj) {
	var serShortCode = $("#serShortCode").val();

	if (serShortCode =='HSR'){
		var url = "NursingHomePermission.html?addFacilities";
		var requestData = {
			"facilityTest" : facilityTest.toString(),
			"facilityCenter" : facilityCenter.toString()
		}
		var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false,
				'json');
	}	
	else if (programList != 'undefined' && programList.length > 0) {

		var url = "NursingHomePermission.html?addProgemDtl";
		var requestData = {
			"department" : programList.toString()
		}

		var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false,
				'json');

	}
	/*var checkboxes = document.getElementsByName('check');
	
	checkboxes.forEach((item) => {
        if (item !== checkbox) 
        	item.checked = false;
            $("#hospitalType").val();
    })*/

	var errorList = [];
	if (serShortCode =='HSR'){
		errorList =	validateSonographyForm(errorList)
	}else{
	  errorList = validateHospitalData(errorList);
	}
	if (errorList.length == 0) {
		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "NursingHomePermission.html?proceedToCheckList";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}

}

function onlyOne(checkbox) {
    var checkboxes = document.getElementsByName('check');
    var flag=true;
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false;
        else{
        	if(flag == true){
        		$("#hospitalType").val(item.defaultValue);
        		flag=false;
        	}	
        }
        
    })
}
function validateHospitalData(errorList) {
	
	var errorList = [];
	var doctorName = $("#doctorName").val();
	// svar hospitalType=$("hospitalType").val();
	var hospitalType = $('#hospitalType').val();
	// $('#hospitalType').prop('checked');
	var maternityBedCount = $("#maternityBedCount").val();
	var otherBedCount = $("#otherBedCount").val();
	var nursingBedCount = $("#nursingBedCount").val();
	var totalBedCount = $("#totalBedCount").val();
	var clinicAddress = $("#clinicAddress").val();
	var hospitalAddress = $("#hospitalAddress").val();
	var clinicContactNo = $("#clinicContactNo").val();
	var hospitalContactNo = $("#hospitalContactNo").val();
	var eduEligibility = $("#eduEligibility").val();
	var regNumber = $("#regNumber").val();
	var regDate = $("#regDate").val();
	var doctorCount = $("#doctorCount").val();
	var nurseCount = $("#nurseCount").val();
	var employeeCount = $("#employeeCount").val();
	var securityCount = $("#securityCount").val();
	var numberOfYears = $("#numberOfYears").val();
	var registeredNursingHome = $("#registeredNursingHome").val();
	var department = $("#department").val();
	//var visitingDoctor = $("#visitingDoctor").val();
	var regBranchANdState=$("#regBranchANdState").val();
	var medicalProfType=$("#medicalProfType").val();
	var complYears=$("#complYears").val();
	var businessStartDate=$("#businessStartDate").val();
	var langId=$("#langId").val();
	
	
	if(hospitalType == ""){
		errorList.push(getLocalMessage("NHP.validation.hospital.type"));
	}
	
	if(clinicContactNo != "" && !validateMobile(clinicContactNo)) {
		errorList.push(getLocalMessage("NHP.validation.invalidecontactClinic"));
	}
	if(hospitalContactNo != "" && !validateMobile(hospitalContactNo)) {
		errorList.push(getLocalMessage("NHP.validation.invalidecontactHospital"));
	}

	if (doctorName == "") {
		errorList.push(getLocalMessage("NHP.validation.doctorName"));
	}
	//from changes
	if(langId==1)
		{
			if(doctorName != "" && !validateName(doctorName)) {
				errorList.push(getLocalMessage("CFC.doctorName.validn"));
			}
		}
		else
		{
			if(doctorName != "" && !validateRegName(doctorName)) {
				errorList.push(getLocalMessage("CFC.doctorName.validn"));
			}
		}//to
	
	
	if (regBranchANdState == "") {
		errorList.push(getLocalMessage("NHP.validation.regBranchANdState"));
	}
	if(langId == 1){
		if (regBranchANdState != "" && !validateName(regBranchANdState)){
			errorList.push(getLocalMessage("CFC.validation.regBranchANdState.validn"));
		}
	}else{
		if(regBranchANdState != "" && !validateRegName(regBranchANdState)) {
			errorList.push(getLocalMessage("CFC.validation.regBranchANdState.validn"));
		}
	}
	
	
	if (medicalProfType == "") {
		errorList.push(getLocalMessage("NHP.validation.medicalProfType"));
	}
	if(langId == 1){
		if (medicalProfType != "" && !validateName(medicalProfType)){
			errorList.push(getLocalMessage("CFC.validation.medicalProfType.validn"));
		}
	}else{
		if(medicalProfType != "" && !validateRegName(medicalProfType)) {
			errorList.push(getLocalMessage("CFC.validation.medicalProfType.validn"));
		}
	}
	
	
	if (complYears == "") {
		errorList.push(getLocalMessage("NHP.validation.complYears"));
	}
	
	if (businessStartDate == "") {
		errorList.push(getLocalMessage("NHP.validation.businessStartDate"));
	}
	
	if (maternityBedCount == "") {
		errorList.push(getLocalMessage("NHP.validation.maternityBedCount"));
	}
	if (otherBedCount == "") {
		errorList.push(getLocalMessage("NHP.validation.otherBedCount"));
	}
	if (nursingBedCount == "") {
		errorList.push(getLocalMessage("NHP.validation.nursingBedCount"));
	}
	if (totalBedCount == "") {
		errorList.push(getLocalMessage("NHP.validation.totalBedCount"));
	}
	if (clinicAddress == "") {
		errorList.push(getLocalMessage("NHP.validation.clinicAddress"));
	}
		//if (!validateBlock(clinicAddress)) {
		//errorList.push(getLocalMessage("CFC.validation.clinicAddress.validn"));
	//}
	//new changes
	if(langId==1)
	{
		if( clinicAddress != "" && !validateNa(clinicAddress)) {
			errorList.push(getLocalMessage("CFC.validation.clinicAddress.validn"));
		}
	}
	else
	{
		if(clinicAddress != "" && !validateRegNa(clinicAddress)) {
			errorList.push(getLocalMessage("CFC.validation.clinicAddress.validn"));
		}
	}
	
	if (hospitalAddress == "") {
		errorList.push(getLocalMessage("NHP.validation.hospitalAddress"));
	}
	//if (!validateName(hospitalAddress)) {
	//	errorList.push(getLocalMessage("CFC.validation.hospitalAddress.validn"));
	//}
	if(langId==1)
	{
		if( hospitalAddress != "" && !validateNa(hospitalAddress) && !validateBl(hospitalAddress)) {
			errorList.push(getLocalMessage("CFC.validation.hospitalAddress.validn"));
		}
	}
	else
	{
		if(hospitalAddress != "" && !validateRegNa(hospitalAddress) && !validateBl(hospitalAddress)) {
			errorList.push(getLocalMessage("CFC.validation.hospitalAddress.validn"));
		}
	}
	
		
	/*if (clinicContactNo == "") {
		errorList.push(getLocalMessage("NHP.validation.clinicContactNo"));
	}
	if (hospitalContactNo == "") {
		errorList.push(getLocalMessage("NHP.validation.hospitalContactNo"));
	}*/
	if (eduEligibility == "") {
		errorList.push(getLocalMessage("NHP.validation.eduEligibility"));
	}
//from changes
	if(langId==1)
		{
			if(eduEligibility != "" && !validateName(eduEligibility)) {
				errorList.push(getLocalMessage("CFC.education.eligible.validn"));
			}
		}
		else
		{
			if(eduEligibility != "" && !validateRegName(eduEligibility)) {
				errorList.push(getLocalMessage("CFC.education.eligible.validn"));
			}
		}//to
	if (regNumber == "") {
		errorList.push(getLocalMessage("NHP.validation.regNumber"));
	}
	
	if(!validateRegNo(regNumber)) {
		errorList.push(getLocalMessage("NHP.validation.regNumber.validation"));
	}
	
	if (regDate == "") {
		errorList.push(getLocalMessage("NHP.validation.regDate"));
	}
	if (doctorCount == "") {
		errorList.push(getLocalMessage("NHP.validation.doctorCount"));
	}
	if (nurseCount == "") {
		errorList.push(getLocalMessage("NHP.validation.nurseCount"));
	}
	if (employeeCount == "") {
		errorList.push(getLocalMessage("NHP.validation.employeeCount"));
	}
	if (securityCount == "") {
		errorList.push(getLocalMessage("NHP.validation.securityCount"));
	}
	if (numberOfYears == "") {
		errorList.push(getLocalMessage("NHP.validation.numberOfYears"));
	}
	if (department == "") {
		errorList.push(getLocalMessage("NHP.validation.department"));
	}
	/*if (visitingDoctor == "") {
		errorList.push(getLocalMessage("NHP.validation.visitingDoctor"));
	}
	
	if (programList == 'undefined' || programList.length == 0) {
		errorList.push(getLocalMessage("NHP.validation.select.program"));
	}*/

	return errorList;
}

function searchData() {

	var errorList = [];

	var serviceId = $("#serviceId").val();
	var appId = $("#appId").val();

	if (serviceId == "" || appId == "") {
		errorList.push(getLocalMessage("CFC.fields.requires"));
	}

	if (errorList.length == 0) {

		formUrl = "NursingHomePermission.html?searchData";
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

function viewNursingHomeData(formUrl, actionParam, appId) {


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

	$("#nursingHomePermission :input").prop("disabled", true);

	$("#viewButton").prop('disabled', false);
	$("#backButton").prop('disabled', false);
}

function viewHospitalInfo(formUrl,actionParam){
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
	$("#hospitalInformation :input").prop("disabled", true);
	$("#nursingHomePermission :input").prop("disabled", true);
	$("#viewButton").prop('disabled', false);
	$("#backButton").prop('disabled', false);
}

//#128871
function ResetSearchForm(resetBtn) {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'NursingHomePermission.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
	prepareTags();

}

function showErrNursInfo(errorList) {

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

function closeErrBoxAstInfo() {
	$('.warning-div').addClass('hide');
}

 // #129863
function validateSonographyForm(errorList) {
	
	var errorList = [];
	var centerType = $("#centerType").val();
	var applicantName = $("#applicantName").val();
	var applyCapacity = $("#applyCapacity").val();
	var centerName = $("#centerName").val();
	var centerAddress = $("#centerAddress").val();
	var contactNo = $("#contactNo").val();
	var emailId = $("#emailId").val();
	var institutionType = $("#institutionType").val();
	var workArea = $("#workArea").val();
	var diagProcedure = $("#diagProcedure").val();
	var langId=$("#langId").val();
	
	if(centerType == ""){
		errorList.push(getLocalMessage("NHP.validation.center.type"));
	}
	
	if (applicantName == "") {
		errorList.push(getLocalMessage("TCP.validation.app.name"));
	}
	
	if(langId == 1){
		if(applicantName != "" && !validateName(applicantName)) {
			errorList.push(getLocalMessage("TCP.applicant.valid.name"));
		}
	}
	else{
		if(applicantName != "" && !validateRegName(applicantName)) {
			errorList.push(getLocalMessage("TCP.applicant.valid.name"));
		}	
	}	
	
	if (applyCapacity == "") {
		errorList.push(getLocalMessage("NHP.validation.capacity"));
	}
	
	if (centerName == "") {
		errorList.push(getLocalMessage("NHP.validation.centreName"));
	}
	
	if (centerAddress == "") {
		errorList.push(getLocalMessage("NHP.validation.centreAddress"));
	}
	
	if (contactNo == "") {
		errorList.push(getLocalMessage("NHP.validation.conNumber"));
	}
	// non mandatory
	/*if (emailId == "") {
		errorList.push(getLocalMessage("NHP.validation.emailId"));
	}*/
	
	if (institutionType == "") {
		errorList.push(getLocalMessage("NHP.validation.instituteType"));
	}
	
	if (workArea == "") {
		errorList.push(getLocalMessage("NHP.validation.workarea"));
	}
	
	if (diagProcedure == "") {
		errorList.push(getLocalMessage("NHP.validation.diagTest"));
	}
	
	if (facilityTest == 'undefined' || facilityTest.length == 0) {
		errorList.push(getLocalMessage("NHP.validation.facilityTest"));
	}
	
	if (facilityCenter == 'undefined' || facilityCenter.length == 0) {
		errorList.push(getLocalMessage("NHP.validation.facilityCenter"));
	}
		
		
	var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	 if (emailId != ""){
	   var valid = emailRegex.test(emailId);
	   if (!valid) {
			   errorList.push(getLocalMessage("NHP.validation.emailId.valid"));
		  } 
	 }
	if(!validateMobile(contactNo)) {
		errorList.push(getLocalMessage('water.validation.ApplicantInvalidmobile'));
	}

	return errorList;
}

function centerTypeCheck(checkbox) {
    var checkboxes = document.getElementsByName('check');
    var flag=true;
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false;
        else{
        	if(flag == true){
        		$("#centerType").val(item.defaultValue);
        		flag=false;
        	}	
        }
        
    })
}

function totalCount(){
	var maternityBedCount = $("#maternityBedCount").val();
	var otherBedCount = $("#otherBedCount").val();
	var nursingBedCount = $("#nursingBedCount").val();
	
	var total=+maternityBedCount + +otherBedCount + +nursingBedCount;
	 $("#totalBedCount").val(total);
	
}

function totalEmpCount(){
	var doctorCount = $("#doctorCount").val();
	var nurseCount = $("#nurseCount").val();
	var securityCount = $("#securityCount").val();
	
	var total=+doctorCount + +nurseCount + +securityCount;
	 $("#employeeCount").val(total);
	
}
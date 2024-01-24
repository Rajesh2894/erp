/**
 * 
 */
$(document).ready(function() {
	$("#farmerMastertables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	 
	$("#dateOfBirth").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200",
		maxDate: 0
		
	});

});

function saveFarmerMasterForm(obj) {
	var errorList = [];
	errorList = validateFarmerMasterForm(errorList);

	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Farmer Master Details Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateFarmerMasterForm(errorList) {
    var  frmFPORegNo = $("#frmFPORegNo").val();
    var  frmName = $("#frmName").val();
    var  frmType = $("#frmType").val();
    var  frmReservation = $("#frmReservation").val();
    var  frmFatherName = $("#frmFatherName").val();
    var  frmMotherName = $("#frmMotherName").val();
    var  frmSDB1 = $("#frmSDB1").val();
    var  frmSDB2 = $("#frmSDB2").val();
    var  frmSDB3 = $("#frmSDB3").val();
    var  frmLandDet = $("#frmLandDet").val();
    var  frmLandUnit = $("#frmLandUnit").val();
    var  frmEquityShare = $("#frmEquityShare").val();
    var  frmAadharNo = $("#frmAadharNo").val();
    var frmMobNo=$("#frmMobNo").val();
    var frmGender = $("#frmGender").val();
    var dateOfBirth = $("#dateOfBirth").val();
    var address = $("#address").val();
    var pinCode = $("#pinCode").val();
    var landOwned = $("#landOwned").val();
    var landLeased = $("#landLeased").val();
    var frmTotalEquity = $("#frmTotalEquity").val();
    var frmVoterCardNo = $("#frmVoterCardNo").val();
    
    if (frmFPORegNo == "") {
		errorList.push(getLocalMessage("sfac.validation.FPORegNo"));
	}
	if (frmName == "" || frmName == null || frmName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.farmerName"));
	}
	
	if (frmGender == "0" || frmGender == null || frmGender == undefined) {
		errorList.push(getLocalMessage("sfac.validation.frmGender"));
	}
	/*if (frmType == "" || frmType == "0" ) {
		errorList.push(getLocalMessage("sfac.validation.farmerType"));
	}*/

	
	if (frmFatherName == "" || frmFatherName == null || frmFatherName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.fatherName"));
	}
	
	if (dateOfBirth == "0" || dateOfBirth == null || dateOfBirth == "") {
		errorList.push(getLocalMessage("sfac.validation.dateOfBirth"));
	}
	
	if (frmReservation == "" || frmReservation == "0" ) {
		errorList.push(getLocalMessage("sfac.validation.reservation"));
	}
	
	if (address == "0" || address == null || address == "") {
		errorList.push(getLocalMessage("sfac.validation.address"));
	}
	
	
	if (pinCode == "0" || pinCode == null || pinCode == "") {
		errorList.push(getLocalMessage("sfac.validation.pinCode"));
	}
	if (pinCode != "" && pinCode<=6){
		errorList.push(getLocalMessage("sfac.valid.pinCode"));
	}
	
	if (frmSDB1 == "" || frmSDB1 == "0" ) {
		errorList.push(getLocalMessage("sfac.validation.SDB1"));
	}
	
	
	if (frmSDB2 == "" || frmSDB2 == "0" ) {
		errorList.push(getLocalMessage("sfac.validation.SDB2"));
	}
	
	
	if (frmSDB3 == "" || frmSDB3 == "0" ) {
		errorList.push(getLocalMessage("sfac.validation.SDB3"));
	}
	
	if($.trim($("#frmVoterCardNo").val()) != "") {
    	var voterNo = $.trim($("#frmVoterCardNo").val());
        var regVoter = /^([a-zA-Z]){3}([0-9]){7}?$/g;
        if(!regVoter.test(voterNo) && voterNo!=''){
                errorList.push(getLocalMessage("sfac.fpo.notValid.frmVoterCardNo"));
        }
	}
	
	
	/*if (frmMotherName == "" || frmMotherName == null || frmMotherName == undefined) {
		errorList.push(getLocalMessage("sfac.validation.MotherName"));
	}*/
	
	if (frmMobNo == "" || frmMobNo == null || frmMobNo == undefined) {
		errorList.push(getLocalMessage("sfac.validation.mobileNo"));
	}
	
	
	
	/*if (frmLandDet == "" || frmLandDet == null || frmLandDet == undefined) {
		errorList.push(getLocalMessage("sfac.validation.landDetails"));
	}
	if (frmLandUnit == "" || frmLandUnit == "0" ) {
		errorList.push(getLocalMessage("sfac.validation.landUnit"));
	}*/
	
	
	if (frmEquityShare == "0" || frmEquityShare == null || frmEquityShare == "") {
		errorList.push(getLocalMessage("sfac.validation.equityShare"));
	}
	
	if (frmTotalEquity == "0" || frmTotalEquity == null || frmTotalEquity == "") {
		errorList.push(getLocalMessage("sfac.validation.frmTotalEquity"));
	}
	if (frmAadharNo != "" && frmAadharNo<12){
		errorList.push(getLocalMessage("sfac.validation.AdharNo"));
	}
	
	


	
	
	if (landOwned == "0" || landOwned == null || landOwned == "") {
		errorList.push(getLocalMessage("sfac.validation.landOwned"));
	}
	
	if (landLeased == "0" || landLeased == null || landLeased == "") {
		errorList.push(getLocalMessage("sfac.validation.landLeased"));
	}
	
	return errorList;
}

function modifyCase(frmId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"frmId" : frmId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchForm(obj) {
	var errorList = [];
	var frmId = $("#frmId").val();
	var frmFPORegNo = $("#frmFPORegNo").val();
	var divName = '.content-page';
	if ((frmId == "" || frmId == undefined || frmId == "0") && (frmFPORegNo == undefined || frmFPORegNo == "")) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
			"frmId" : frmId,
			"frmFPORegNo" : frmFPORegNo
		};
		var ajaxResponse = doAjaxLoading('FarmerMasterForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FarmerMasterForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function ResetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FarmerMasterForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function otherTask() {
	var divName = '.content-page';
	var data = {};

	var response = __doAjaxRequest('FarmerMasterForm.html?getUploadedImage',
			'POST', data, false, 'html');
	//$("#uploadTagDiv").html(response);
	$(divName).html(response)
	
	//prepareTags();

}
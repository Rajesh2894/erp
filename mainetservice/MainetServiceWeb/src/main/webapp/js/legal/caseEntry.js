var fileArray = [];
$(document).ready(function() {
	/*$('#CaseEntryForm').validate({
		onkeyup : function(element) {
			this.element(element);		
		},
		onfocusout : function(element) {
			this.element(element);	
		}
	});*/

	
	$("#id_caseEntryTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});


			var envFlag = $("#envFlag").val();
			if (envFlag == 'Y') {
				$(".showMand").hide();
				$('.alpaSpecial').keypress(
						function(e) {
							var regex = new RegExp("^[A-Za-z0-9_./-()]+$");
							var str = String.fromCharCode(!e.charCode ? e.which
									: e.charCode);
							if (regex.test(str)) {
								return true;
							}
							e.preventDefault();
							return false;
						});
			}else{
				$(".showMand").show();
			}
/*	$('#oicAppointmentDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
		

	});*/

  //#117444
	
    var minYear= new Date();
	$('.dateValidation').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: minYear,
	
	});	

function caseDatePicker() {
		$("#cseDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200",
		maxDate : 0,
		onSelect : function(selected) {
		var caseDate = $("#cseDate").val();
		
		var dParts = caseDate.split('/');
		var dateFormat = new Date(dParts[2] + '/' + dParts[1] + '/' + (+dParts[0]));
		
		var myDate = new Date(dateFormat);
		var noOfDays = 30;
				myDate.setDate( myDate.getDate()+ parseInt(noOfDays));    
			
		var days = ("0" + (myDate.getDate())).slice(-2);
		var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
		var dateString = days + '/' + month + '/'+ myDate.getFullYear();
		  $("#cseEntryDt").datepicker("option", "minDate",selected);
		 $("#cseEntryDt").datepicker("option", "maxDate",dateString);
 		}
			
    
	});
}
caseDatePicker();

function caseEntryDatePicker() {	
	$("#cseEntryDt").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200"		
	});
}
caseEntryDatePicker();
	
	$("#orderDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200",
		maxDate : 0		
	});
	
	
	// $('#judgefeeTable').hide();

	let cseTypValue = $("#cseTypId").val();
	var value = $("#cseTypId").find("option:selected").attr('code');
	if (value == 'A') {
		$("#judgefeeTable").show();
	} else {
		$("#judgefeeTable").hide();
	}

	prepareTags();
});

function Proceed(element) {
	var errorList = [];
	errorList = ValidateCaseEntry(errorList);
	errorList = validateDefenderEntryDetails(errorList);
	errorList = validatePlantiffEntryDetails(errorList);
	errorList = validateOfficerInchargeDetails(errorList);
	var cseTypId = $("#cseTypId").val();
	if (cseTypId == 42208) {
		errorList = validateJudgedetails(errorList);
	}
	
   //#117454
	var saveMode = $("#saveMode").val();
	if(saveMode == 'C'){
	var data = {"cseSuitNo" : $("#cseSuitNo").val()};
	var applicantDataResponse = __doAjaxRequest(
			'CaseEntry.html?checkCaseNoAlreadyPresent','post', data, false,'json');
	if (applicantDataResponse == true) {
		errorList.push(getLocalMessage("lgl.validate.case.no"));
		displayErrorsOnPage(errorList);
	}
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element, getLocalMessage('lgl.saveCaseEntry'),
				'CaseEntry.html', 'saveform');
	}
}

function addEntryData() {
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validatePlantiffEntryDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('casePlantiffDetails');
		 fixTableLayout();
	} else {
		$('#casePlantiffDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function fixTableLayout() {
    // Set a fixed layout for the table
    document.getElementById('casePlantiffDetails').style.tableLayout = 'fixed';
    // You can also adjust column widths or other styles here as needed
}

function searchCase() {

	var data = {

		"cseSuitNo" : $("#cseSuitNo").val(),
		"caseNo" : $("#caseNo").val(),
		"cseDeptid" : $("#cseDeptid").val(),
		"cseDate" : $("#cseDate").val(),
		"cseCatId1" : replaceZero($("#cseCatId1").val()),
		"cseCatId2" : replaceZero($("#cseCatId2").val()),
		"cseTypId" : replaceZero($("#cseTypId").val()),
		"crtId" : replaceZero($("#crtId").val()),

	};
	var divName = '.content-page';
	var formUrl = "CaseEntry.html?searchCaseEntry";
	var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}
function deleteEntry(obj, ids) {
	
	if ($("#casePlantiffDetails tr").length == 2) {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
	else{
		var totalWeight = 0;
		deleteTableRow('casePlantiffDetails', obj, ids);
		$('#casePlantiffDetails').DataTable().destroy();
		triggerTable();
	
	}

}

function addEntryData1() {

	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateDefenderEntryDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('caseDefenderDetails');
	} else {
		$('#caseDefenderDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deleteEntry1(obj, ids) {
	
	if ($("#caseDefenderDetails tr").length == 2) {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}else{
		var totalWeight = 0;
		deleteTableRow('caseDefenderDetails', obj, ids);
		$('#caseDefenderDetails').DataTable().destroy();
		triggerTable();
		event.preventDefault();
	}

}

function resetCaseEntryForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CaseEntry.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function modifyCase(cseId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var envFlag = $("#envFlag").val();
	var requestData = {
		"mode" : mode,
		"id" : cseId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	if (envFlag == 'Y'){
		$(".showMand").hide();
	}else{
		$(".showMand").show();
	}
	prepareTags();
};

function printCase(cseId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : cseId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function ValidateCaseEntry() {
	var errorList = [];

	var cseName = $("#cseName").val();
	var cseSuitNo = $("#cseSuitNo").val();
	var cseDeptid = $("#cseDeptid").val();
	var cseCatId1 = $("#cseCatId1").val();
	var cseCatId2 = $("#cseCatId2").val();
	var cseTypId = $("#cseTypId").val();
	var csePeicDroa = $("#csePeicDroa").val();
	var cseDate = $("#cseDate").val();
	var cseEntryDt = $("#cseEntryDt").val();


	/* var locId = $("#locId").val(); */
	var cseMatdet1 = $("#cseMatdet1").val();
	var cseRemarks = $("#cseRemarks").val();
	var cseCaseStatusId = $("#cseCaseStatusId").val();
	var crtId = $("#crtId").val();
	var locId = $("#locId").val();
/*	var appointmentDate = $("#appointmentDate").val();*/
	var cseState = $("#cseState").val();
	var cseCity = $("#cseCity").val();

	/*var officeIncharge = $("#officeIncharge").val();*/
	var advId = $("#advId").val();

	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';

	if (advId == "" || advId == null || advId == 'undefined')
		errorList.push(getLocalMessage("lgl.validate.advocateName"));

	if (cseName == "" || cseName == null || cseName == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseName"));


	if (csePeicDroa == "0" || csePeicDroa == null || csePeicDroa == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.csePeicDroa"));

	if (cseDate == "" || cseDate == null || cseDate == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseDate"));

	if (cseEntryDt == "" || cseEntryDt == null || cseEntryDt == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseEntryDt"));

	/*
	 * if (locId == "" || locId == null || locId == 'undefined')
	 * errorList.push(getLocalMessage("lgl.validation.locId"));
	 */

	if (cseMatdet1 == "" || cseMatdet1 == null || cseMatdet1 == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseMatdet1"));

	if (cseRemarks == "" || cseRemarks == null || cseRemarks == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseRemarks"));

	if (cseCaseStatusId == "0"  || cseCaseStatusId == null
			|| cseCaseStatusId == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseCaseStatusId"));

	if (crtId == "" || crtId == null || crtId == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.crtId"));

	/*
	 * if (locId == "" || locId == null || locId == 'undefined')
	 * errorList.push(getLocalMessage("lgl.validation.locId"));
	 */
	/*if (cseMatdet1 == "" || cseMatdet1 == null || cseMatdet1 == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseSectAppl"));*/

	/*if (cseMatdet1 == "0" || cseMatdet1 == null || cseMatdet1 == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseCaseStatusId"));*/

	/*if (appointmentDate == "" || appointmentDate == null
			|| appointmentDate == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.appointmentDate"));

	if (officeIncharge == "0" || officeIncharge == null
			|| officeIncharge == 'undefined')

		errorList.push(getLocalMessage("lgl.validation.officeIncharge"));
*/
	if (cseState == "0" || cseState == null || cseState == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseState"));

	if (cseCity == "" || cseCity == null || cseCity == 'undefined')
		errorList.push(getLocalMessage("lgl.validation.cseCity"));
	

	return errorList;
}

function validatePlantiffEntryDetails(errorList) {
	var caseEntryList = [];

	var csedEmail = [], phoneNo = [], emailId = [];

	$(".appendableClassP")
			.each(
					function(i) {
						var csedNamePlantiff = $("#csedNamePlantiff" + i).val();
						var csedFNamePlantiff = $("#csedFNamePlantiff" + i).val();
						var csedContactnoPlantiff = $(
								"#csedContactnoPlantiff" + i).val();
						var csedEmailidPlantiff = $("#csedEmailidPlantiff" + i)
								.val();
						var csedAddressPlantiff = $("#csedAddressPlantiff" + i)
								.val();
						var csedPartyType = $("#csedPartyType" + i).val();
						var envFlag = $("#envFlag").val();

						var rowCount = i + 1;

						if(csedEmailidPlantiff!=''&& csedEmailidPlantiff!= undefined){
							if (emailId.includes(csedEmailidPlantiff)) {
								errorList
										.push(getLocalMessage("lgl.validation.duplicate.emailId")
												+ rowCount);
							} else {
								emailId.push(csedEmailidPlantiff);
							}
						}
						//D#76895
						if(csedContactnoPlantiff!= '' && csedContactnoPlantiff != undefined){
							if (phoneNo.includes(csedContactnoPlantiff)) {
								errorList
										.push(getLocalMessage("lgl.validation.duplicate.phone")
												+ rowCount);
							} else {
								phoneNo.push(csedContactnoPlantiff);
							}
						}
						
						if (csedPartyType == "" || csedPartyType == null
								|| csedPartyType == 'undefined'
								|| csedPartyType == '0') {
							errorList
									.push(getLocalMessage("lgl.validation.csedPartyType.plantiff")
											+ rowCount);
						}

						if (csedNamePlantiff == "" || csedNamePlantiff == null
								|| csedNamePlantiff == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.csedName.plantiff")
											+ rowCount);
						}
						if (envFlag != 'Y'){
						if (csedFNamePlantiff == "" || csedFNamePlantiff == null
								|| csedFNamePlantiff == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.csedFname.plantiff")
											+ rowCount);
						}
						}
						
						if (csedContactnoPlantiff == ""
								|| csedContactnoPlantiff == null
								|| csedContactnoPlantiff == 'undefined') {
							//D#76895
							//errorList.push(getLocalMessage("lgl.validation.csedContactno.plantiff")+ rowCount);
						}
						else{
								if (!validatePlainTiffMobile(csedContactnoPlantiff)) {
									errorList
										.push(getLocalMessage('lgl.validation.less.plaintiffPhoneNo'));
									}
						}
						if (envFlag != 'Y'){
						if (csedAddressPlantiff == ""
								|| csedAddressPlantiff == null
								|| csedAddressPlantiff == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.csedAddress.plantiff")
											+ rowCount);
						 }
						}
						
						if (csedEmailidPlantiff == ""
								|| csedEmailidPlantiff == null
								|| csedEmailidPlantiff == 'undefined') {
							//D#76895
							//errorList.push(getLocalMessage("lgl.validation.csedEmailid.plantiff")+ rowCount);
						} else {
							if (csedEmailidPlantiff != "") {
								var emailRegex = new RegExp(
										/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
								var valid = emailRegex
										.test(csedEmailidPlantiff);
								if (!valid) {
									errorList
											.push(getLocalMessage("Invalid.plaintiff.Email")
													+ rowCount);
								}
							}
						}

					});
	return errorList;
}

function validateDefenderEntryDetails(errorList) {
	var caseEntryList = [], phoneNo = [], emailId = [];
	$(".appendableClassD")
			.each(
					function(i) {

						var csedNameDefender = $("#csedNameDefender" + i).val();
						var csedContactnoDefender = $(
								"#csedContactnoDefender" + i).val();
						var csedEmailidDefender = $("#csedEmailidDefender" + i)
								.val();
						var csedAddressDefender = $("#csedAddressDefender" + i)
								.val();

						var csedParty = $("#csedParty" + i).val();
						var envFlag = $("#envFlag").val();

						var rowCount = i + 1;
						
						if(csedContactnoDefender!= '' && csedContactnoDefender!= undefined){
							if (emailId.includes(csedContactnoDefender)) {
								errorList
										.push(getLocalMessage("lgl.validation.duplicate.phone ")
												+ rowCount);
							} else {
								emailId.push(csedContactnoDefender);
							}
						}
										
						if(csedEmailidDefender!= '' && csedEmailidDefender != undefined){
							if (phoneNo.includes(csedEmailidDefender)) {
								errorList
										.push(getLocalMessage("lgl.validation.duplicate.emailId ")
												+ rowCount);
							} else {
								phoneNo.push(csedEmailidDefender);
							}
						}
						

						if (csedNameDefender == "" || csedNameDefender == null
								|| csedNameDefender == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.csedName.defender")
											+ rowCount);
						}

						if (csedParty == "" || csedParty == null
								|| csedParty == 'undefined' || csedParty == '0') {
							errorList
									.push(getLocalMessage("lgl.validation.csedParty.defender")
											+ rowCount);
						}
						if (csedContactnoDefender == ""
								|| csedContactnoDefender == null
								|| csedContactnoDefender == 'undefined') {
							//D#76895
							//errorList.push(getLocalMessage("lgl.validation.csedContactno.defender")+ rowCount);
						}
						else{
								if (!validateDefenderMobile(csedContactnoDefender)) {
									errorList
										.push(getLocalMessage('lgl.validation.less.defenderPhoneNo'));
									}
						}
						if (envFlag != 'Y'){
						if (csedAddressDefender == ""
								|| csedAddressDefender == null
								|| csedAddressDefender == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.csedAddress.defender")
											+ rowCount);
						 }
						}
						if (csedEmailidDefender == ""
								|| csedEmailidDefender == null
								|| csedEmailidDefender == 'undefined') {
							//D#76895
							//errorList.push(getLocalMessage("lgl.validation.csedEmailid.defender")+ rowCount);
						} else {
							if (csedEmailidDefender != "") {
								var emailRegex = new RegExp(
										/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
								var valid = emailRegex
										.test(csedEmailidDefender);
								if (!valid) {
									errorList
											.push(getLocalMessage("Invalid.def.Email")
													+ rowCount);
								}
							}
						}

					});
	return errorList;
}

function validateOfficerInchargeDetails(errorList) {
	var caseEntryList = [];

	var phoneNo = [], emailId = [];

	$(".appendableClassO")
			.each(
					function(i) {
						var officerInchargeName = $("#officerInchargeName" + i)
								.val();
						var officerInchargeDesignation = $(
								"#officerInchargeDesignation" + i).val();

						var officerInchargePhoneNo = $(
								"#officerInchargePhoneNo" + i).val();

						var officerInchargeEmailId = $(
								"#officerInchargeEmailId" + i).val();

						var officerInchargeAddress = $(
								"#officerInchargeAddress" + i).val();

						var officerInchargeOrderNo = $(
								"#officerInchargeOrderNo" + i).val();

						var officerInchargeAppointmentDate = $(
								"#officerInchargeAppointmentDate" + i).val();

						var officerInchargeDepartment = $(
								"#officerInchargeDepartment" + i).val();
						
						var envFlag = $("#envFlag").val();

						var rowCount = i + 1;
         
						if (emailId.includes(officerInchargeEmailId)) {
							errorList
									.push(getLocalMessage("lgl.validation.duplicate.emailId")
											+ rowCount);
						} else {
							emailId.push(officerInchargeEmailId);
						}
						if (phoneNo.includes(officerInchargePhoneNo)) {
							errorList
									.push(getLocalMessage("lgl.validation.duplicate.phone")
											+ rowCount);
						} else {
							phoneNo.push(officerInchargePhoneNo);
						}
				        if (envFlag != 'Y'){
						if (officerInchargeName == null
								|| officerInchargeName.trim() == ""
								|| officerInchargeName == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicName")
											+ rowCount);
						}
						if (officerInchargeDesignation == null
								|| officerInchargeDesignation.trim() == ""
								|| officerInchargeDesignation == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicDesg")
											+ rowCount);
						}

						if (officerInchargePhoneNo == null
								|| officerInchargePhoneNo.trim() == ""
								|| officerInchargePhoneNo == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicPhoneNo")
											+ rowCount);
						}
						else{
							if (!validateMobile(officerInchargePhoneNo)) {
								errorList
									.push(getLocalMessage('lgl.validation.less.oicPhoneNo'));
								}
						}

						if (officerInchargeAddress == null
								|| officerInchargeAddress.trim() == ""
								|| officerInchargeAddress == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicAddress")
											+ rowCount);
						}

						if (officerInchargeOrderNo == null
								|| officerInchargeOrderNo.trim() == ""
								|| officerInchargeOrderNo == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicOrderNo")
											+ rowCount);
						}

						if (officerInchargeAppointmentDate == null
								|| officerInchargeAppointmentDate.trim() == ""
								|| officerInchargeAppointmentDate == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicAppointmentDate")
											+ rowCount);
						}
						

						if (officerInchargeEmailId == null
								|| officerInchargeEmailId.trim() == ""
								|| officerInchargeEmailId == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicEmailId")
											+ rowCount);
						} 
				        }
					   if (officerInchargeEmailId != "") {
						var emailRegex = new RegExp(
								/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
						var valid = emailRegex
								.test(officerInchargeEmailId);
						if (!valid) {
							errorList
									.push(getLocalMessage("Invalid.oic.Email")
											+ rowCount);
						}
					   }
						if (officerInchargeDepartment == null
								|| officerInchargeDepartment.trim() == ""
								|| officerInchargeDepartment == 'undefined') {
							errorList
									.push(getLocalMessage("lgl.validation.oicDept")
											+ rowCount);
						}


					});
	return errorList;
}

function addEntryData3() {
	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateOfficerInchargeDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('officerInchargeDetails');
	} else {
		// $('#officerInchargeDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deleteEntry3(obj, ids) {
	if ($("#officerInchargeDetails tr").length == 2) {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}else{
		var totalWeight = 0;
		deleteTableRow('officerInchargeDetails', obj, ids);
		$('#officerInchargeDetails').DataTable().destroy();
		triggerTable();
		event.preventDefault();
	}
	
}

function setValues(obj) {

	$('#oicMobile').val($('#officeIncharge option:selected').attr('mobno'));
	$('#oicEmail').val($('#officeIncharge option:selected').attr('email'));
	$('#oicDepartment').val($('#officeIncharge option:selected').attr('dept'));
}

function displayJudgeFeesTable() {
	let cseTypValue = $("#cseTypId").val();
	var value = $("#cseTypId").find("option:selected").attr('code');
	if (value == 'A') {
		$("#judgefeeTable").show();
	} else {
		$("#judgefeeTable").hide();
	}
}

function addEntryData2() {

	$("#errorDiv").hide();
	var errorList = [];
	errorList = validateJudgedetails(errorList);
	if (errorList.length == 0) {
		addTableRow('judgeFeeDetails');
	} else {
		$('#judgeFeeDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function deleteEntry2(obj, ids) {
	
	if ($("#judgeFeeDetails tr").length == 2) {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
	else{
		var totalWeight = 0;
		deleteTableRow('judgeFeeDetails', obj, ids);
		$('#judgeFeeDetails').DataTable().destroy();
		triggerTable();
		event.prefentDefault();
	}
	
}

function validateJudgedetails(errorList) {

	$("#judgeFeeDetails tbody tr").each(
			function(i) {

				var judgeName = $('#judgeName' + i).val();
				var feeType = $('#feeType' + i).val();
				var rowCount = i + 1;
				if (judgeName == '' || judgeName == 'undefined'
						|| judgeName == null || judgeName == 0) {
					errorList.push(getLocalMessage("lgl.select.judge")
							+ rowCount)
				}

				if (feeType == '' || feeType == 'undefined' || feeType == null
						|| feeType == 0) {
					errorList.push(getLocalMessage("legal.select.feeType")
							+ rowCount)
				}

			});
	return errorList;
}

$('body').on('focus', ".hasMobileNo", function() {
	$('.hasMobileNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
		$(this).attr('maxlength', '10');
	});
});

$('body').on('focus', ".hasNameClass", function() {
	$('.hasNameClass').keyup(function() {
		 this.value = this.value.replace(/[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~0-9]/g, '');

	});
});

//120649
function resetCaseEntry(resetBtn) {
	var cseId = $('#cseId').val();
	modifyCase(cseId,'CaseEntry.html', 'EDIT','E');
}


function openAddForm(formUrl,actionParam)
{
	var envFlag = $("#envFlag").val();
	if (!actionParam) {
		
		actionParam = "add";
	}
	
	var divName	=	formDivName;
	var ajaxResponse	=	doAjaxLoading(formUrl+'?'+actionParam,{},'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	caseDatePicker();
	caseEntryDatePicker();
	if (envFlag == 'Y'){
		$("#showMand").hide();
	}else{
		$("#showMand").show();
	}
	prepareTags();	
}

function validateMobile(officerInchargePhoneNo) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(officerInchargePhoneNo);
}

function validateDefenderMobile(csedContactnoDefender) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(csedContactnoDefender);
}

function validatePlainTiffMobile(csedContactnoPlantiff) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(csedContactnoPlantiff);
}

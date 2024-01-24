$(document).ready(function() {
	$("#showCompanyApiHeading").hide();
	$(".currentDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
	});
	
	
	$("#newLicenseTableId").dataTable({
		"oLanguage": {
			"sSearch": ""
		},
		"aLengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
		"iDisplayLength": 5,
		"bInfo": true,
		"lengthChange": true,
		"ordering": false,
		"order": [[1, "desc"]]
	});
	
	
	 $("#itemDetails tbody tr.itemDetailClass").each(function(i) {
		 var subpurpose1 = $("#subpurpose1" + i).val();
		 if(subpurpose1 != undefined && subpurpose1 != "0"){
			 getSubpurpose2(i);
			 var hiddenSubPur2 = $("#selSubPurpose2" + i).val();
			 $("#subpurpose2" + i).val(hiddenSubPur2);
		 }
		 
	 });
	 
	 var devTypeCode=$('#devType option:selected').attr('code');
		if(devTypeCode=="COM" || devTypeCode=="PAF"){
			$("#CINDiv").show();
			$("#companyDiv").show();
			$("#dateOfIncorporation").show();
			$("#commonDetails").show();
			$("#stakeholderDiv").show();
			$("#LLPDiv").hide();
			$("#nameDiv").hide();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").hide();
			$("#firmNameDiv").hide();
			if($("#companyDetailsAPIFlagId").val()=='Y'){
				$("#showCompanyApiHeading").show();
			}
			$("#fetchCinBtn").show();
		}else if(devTypeCode=="IND" || devTypeCode=="HUF"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").hide();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").hide();
			$("#nameDiv").show();
			$("#genderDiv").show();
			$("#dobDiv").show();
			$("#panNo").show();
			$("#firmNameDiv").hide();	
			$("#showCompanyApiHeading").hide();
			$("#fetchCinBtn").hide();
		}
		else if(devTypeCode=="PRF"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").hide();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").hide();
			$("#nameDiv").show();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").show();
			$("#firmNameDiv").hide();
			$("#showCompanyApiHeading").hide();
			$("#fetchCinBtn").hide();
		}
		else if(devTypeCode=="LLP"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").show();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").show();
			$("#nameDiv").hide();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").hide();
			$("#firmNameDiv").show();
			$("#showCompanyApiHeading").hide();
			$("#fetchCinBtn").hide();
		}
});

function showTab(tabId) {
	$('.error-div').hide();
	$('#newLicenseParentTab a[href="' + tabId + '"]').tab('show');
}


function openNewLicenseForm(formUrl, actionParam, mode) {
	var divName = '.content-page';
	var id = null;
	var data = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function draftNewLicenseForm(formUrl, actionParam, mode, id) {
	var divName = '.content-page';
	var data = {
		"mode" : mode,
		"id" : id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

//to populate data in acquisition table function in JSP
var rowCountClick = 0;


function saveApplicationForm(element){	
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#applicantForm';
		var targetDivName = '#applicationPurpose';
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicense', 'POST', requestData,
				false, 'html');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {				
			$(divName).removeClass('ajaxloader');	
				$(targetDivName).html(applicantResponse);
				let parentTab =  '#newLicenseParentTab';
				var disabledTab = $(parentTab).find('.disabled');
				if (disabledTab) {
					$(disabledTab).removeClass('disabled');
					$('.nav li#applicationPurpose-tab').removeClass('disabled');
					$('.nav li#applicationPurpose-tab').removeClass('link-disabled');
					$('.nav li#landSchedule-tab').addClass('disabled');
					$('.nav li#detailsOfLand-tab').addClass('disabled');
					$('.nav li#charges-tab').addClass('disabled');
				}
				$(''+parentTab+' a[href="#applicationPurpose"]').tab('show');
				prepareDateTag();
				$('html, body').animate({
				    scrollTop: 0
				}, 'fast');
		}else{
			$(divName).html(applicantResponse);
			prepareDateTag();
			return false;
		}	
 
	}
}

function saveApplicationPurpose(element){	
	var errorList = [];
	errorList = validatePurposeInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#applicationPurpose';
		var targetDivName = '#landSchedule';
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicensePurpose', 'POST', requestData,
				false, '', 'html');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {	
			$(divName).removeClass('ajaxloader');	
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#newLicenseParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#landSchedule-tab').removeClass('disabled');
				$('.nav li#landSchedule-tab').removeClass('link-disabled');
				//$('.nav li#landSchedule-tab').addClass('disabled');
				$('.nav li#detailsOfLand-tab').addClass('disabled');
				$('.nav li#charges-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#landSchedule"]').tab('show');
			prepareDateTag();
			$('html, body').animate({
			    scrollTop: 0
			}, 'fast');
		}else{
			$(divName).html(applicantResponse);
			prepareDateTag();
			return false;
		}
	}
}

function saveApplicationLand(element){	
	var errorList = [];
	errorList = validateLandInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#landSchedule';
		var targetDivName = '#detailsOfLand';
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicenseLandDetails', 'POST', requestData,
				false, '', 'html');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$(divName).removeClass('ajaxloader');	
				$(targetDivName).html(applicantResponse);
				let parentTab =  '#newLicenseParentTab';
				var disabledTab = $(parentTab).find('.disabled');
				if (disabledTab) {
					$(disabledTab).removeClass('disabled');
					$('.nav li#detailsOfLand-tab').removeClass('disabled');
					$('.nav li#detailsOfLand-tab').removeClass('link-disabled');
					$('.nav li#charges-tab').addClass('disabled');
				}
				$(''+parentTab+' a[href="#detailsOfLand"]').tab('show');
				prepareDateTag();
				$('html, body').animate({
				    scrollTop: 0
				}, 'fast');
		}else{
			$(divName).html(applicantResponse);
			prepareDateTag();
			return false;
		}
 
	}
}	

function saveApplicationDetaisofLand(element){	
	var errorList = [];
	errorList = validateDetailsofLandInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
 
	} else {
		$("#errorDiv").hide();
		var divName = '#detailsOfLand';
		var targetDivName = '#chargesPage';
		var elementData = null;
 
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		applicantResponse = __doAjaxRequest(
				'NewTCPLicenseForm.html?saveNewLicenseDetailsOfLandAndGetCharges', 'POST', requestData,
				false, '', 'html');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$(divName).removeClass('ajaxloader');	
				$(targetDivName).html(applicantResponse);
				let parentTab =  '#newLicenseParentTab';
				var disabledTab = $(parentTab).find('.disabled');
				if (disabledTab) {
					$(disabledTab).removeClass('disabled');
					$('.nav li#charges-tab').removeClass('disabled');
					$('.nav li#charges-tab').removeClass('link-disabled');
				}
				$(''+parentTab+' a[href="#chargesPage"]').tab('show');
				prepareDateTag();
		}else{
			$(divName).html(applicantResponse);
			prepareDateTag();
			return false;	
		}
 
	}
}

function saveFinalApp(obj){
	var errorList = [];
	//errorList = validateForm(errorList);
	
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "Form Saved successfully",
				"NewTCPLicenseForm.html?proceedToPayment", 'saveform');
	} else {
		//$(".compalint-error-div").show();
		displayErrorsOnPage(errorList);
	}
	
	
}

$(function() {
	
$("#itemDetails").off('click', '.addItemCF').on('click', '.addItemCF', function() {
		
		var errorList = [];
		//errorList = validateitemDetailsTable(errorList);
		if (errorList.length == 0) {

			var content = $("#itemDetails").find('tr:eq(1)').clone();
			$("#itemDetails").append(content);

			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			//content.find("select:eq(1)").val(null);
			
		
			$('.error-div').hide();
			reOrderItemDetailsSequence(); // reorder id and Path
			hasNumber();
		} else {
			displayErrorsOnPage(errorList);
		}
	});

	$("#itemDetails").on('click', '.delButton', function() {

		if ($("#itemDetails tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderItemDetailsSequence();
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});



		$("#surroundingDetail").off('click','.addCF').on('click','.addCF',function(){
              
		var errorList = [];
		//errorList = validateOwnerDetailsTable(errorList);
		if (errorList.length == 0) {
			var content = $("#surroundingDetail").find('tr:eq(1)').clone();
			$("#surroundingDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}
	});
	
		$("#acqDetTable").off('click', '.addAcqDet').on('click','.addAcqDet',function() {debugger
	    var errorList = [];
	    // errorList = validateAcquisitionDetailsTable(errorList);

	    if (errorList.length == 0) {
	    	var rowCount = $("#acqDetTable tr").length - 1;
	        var newRow = $("#acqDetTable").find('tr:eq(1)').clone();
	        reOrderAcquisitionDetailsSequence(newRow, rowCount);// reorder id and Path
	        $("#acqDetTable").append(newRow);
	    	rowCountClick++;
	        calculateTotalArea();
	        $('.error-div').hide();
	        // Add any additional logic you need after adding a new row
	        return false;
	    } else {
	        displayErrorsOnPage(errorList);
	    }
	});
	
	
$(function() {
		
	$("#acqDetTable").on('click', '.delButtonTable', function() {
	    if ($("#acqDetTable tbody tr").length !== 1) {
	        $(this).parent().parent().parent().remove();
	        reOrderAcquisitionDetailsSequenceDel(); // reorder id and Path, starting from the deleted row's index
	        calculateTotalArea();
	    } else {
	        var errorList = [];
	        errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
	        displayErrorsOnPage(errorList);
	    }
	});
	});
	
	
});

$(function() {
	
	$("#surroundingDetail").on('click', '.delButtonPocket', function() {
		

		if ($("#surroundingDetail tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderOwnerDetailsSequence(); 
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});


function reOrderOwnerDetailsSequence(appendableClass) {
	
	$(appendableClass).each(
		function(i) 
		{
			
			// id binding
			$(this).find("input:text:eq(0)").attr("id", "pocketName" + i);
			$(this).find("input:text:eq(1)").attr("id", "north" + i);
			$(this).find("input:text:eq(2)").attr("id", "south" + i);
			$(this).find("input:text:eq(3)").attr("id", "east" + i);
			$(this).find("input:text:eq(4)").attr("id", "west"+i);

			
			$(this).find("input:text:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].pocketName");
			$(this).find("input:text:eq(1)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].north");
			$(this).find("input:text:eq(2)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].south");
			$(this).find("input:text:eq(3)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].east");
			$(this).find("input:text:eq(4)").attr("name", "licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.licenseApplicationLandSurroundingsDtoList[" + i + "].west");
		

		});
}

function reOrderItemDetailsSequence() {
    $("#itemDetails tbody tr.itemDetailClass").each(function(i) {
       

        $(this).find("select:eq(0)").attr("id", "subpurpose1" + i);
        $(this).find("select:eq(1)").attr("id", "subpurpose2" + i);
        $(this).find("select:eq(2)").attr("id", "far" + i);
        $(this).find("input:hidden:eq(0)").attr("id", "purposeId" + i);

        $(this).find("input:text:eq(0)").attr("id", "area" + i);

        $(this).find("select:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].applicationPurpose2").attr("onchange", "getSubpurpose2(" + i +")");
        $(this).find("select:eq(1)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].applicationPurpose3");
        $(this).find("select:eq(2)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].far");
        $(this).find("input:text:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].area");
        $(this).find("input:hidden:eq(0)").attr("name", "licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[" + i + "].purposeId");
    });
}

function reOrderAcquisitionDetailsSequence(newRow, rowCount) {
	
    var fields = ["district", "devPlan", "zone", "sector", "thesil", "revEstate", "hadbastNo", "rectangleNo",
        "khasraNo", "min", "landOwnerName", "landType", "chInfo", "mustilCh", "khasaraCh", "landOwnerMUJAM",
        "devColab", "devCompName", "collabAgrDate", "collabAgrRev", "authSignLO", "authSignDev", "regAuth",
        "collabDec", "brLO", "brDev", "collabAgrDoc", "consolType", "kanal", "marla", "sarsai", "bigha", "biswa",
        "biswansi", "acqStat", "consolTotArea", "nonConsolTotArea"];

    fields.forEach(function (field, index) {
    	
        var input = newRow.find("input:text:eq(" + index + ")");
        input.attr("id", field + rowCount);
        input.attr("name", "licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[" + rowCount + "]." + field);
        input.attr("readonly", "true");
    });
    
}

function reOrderAcquisitionDetailsSequenceDel() {
    var fields = ["district", "devPlan", "zone", "sector", "thesil", "revEstate", "hadbastNo", "rectangleNo",
        "khasraNo", "min", "landOwnerName", "landType", "chInfo", "mustilCh", "khasaraCh", "landOwnerMUJAM",
        "devColab", "devCompName", "collabAgrDate", "collabAgrRev", "authSignLO", "authSignDev", "regAuth",
        "collabDec", "brLO", "brDev", "collabAgrDoc", "consolType", "kanal", "marla", "sarsai", "bigha", "biswa",
        "biswansi", "acqStat", "consolTotArea", "nonConsolTotArea"];

    $("#acqDetTable tr:gt(0)").each(function(i) {
        var currentRow = $(this);

        fields.forEach(function (field, index) {
            var input = currentRow.find("input:text:eq(" + index + ")");
            input.attr("id", field + i);
            input.attr("name", "licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[" + i + "]." + field);
        });
    });
}

function hasNumber() {
	$('.hasNumber').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
}

function getSubpurpose2(id) {

	subpurpose1 = $('#subpurpose1' + id).val();

	var requestData = {
		"subpurpose1" : subpurpose1
	};
	var URL = 'NewTCPLicenseForm.html?getSubPurpose2List';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');

	var subPurpose2Select = $('#subpurpose2' + id);
	subPurpose2Select.empty();

	// Append a default option
	subPurpose2Select.append('<option value="">Select</option>');
	for (var i = 0; i < returnData.length; i++) {
		var lookUp = returnData[i];
		subPurpose2Select.append('<option value="' + lookUp.lookUpId + '">'
				+ lookUp.lookUpDesc + '</option>');
	}

}

function getTehsilList(){
	
	var district = $("#ddz1").val();
	
	var requestData = {
			"districtId" : district
	};
	
	var URL = 'NewTCPLicenseForm.html?getTehsilListByDistId';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var tehsilList = returnData;

    $('#khrsThesil').empty();

    $('#khrsThesil').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < tehsilList.length; i++) {
        $('#khrsThesil').append($('<option>', {
            value: tehsilList[i].code, // Adjust this based on your tehsil object structure
            text: tehsilList[i].name // Adjust this based on your tehsil object structure
        }));
    }
	
}


function getVillageList(){
	
	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil
	};
	
	var URL = 'NewTCPLicenseForm.html?getVillagesList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var tehsilList = returnData;

    $('#khrsRevEst').empty();

    $('#khrsRevEst').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < tehsilList.length; i++) {
        $('#khrsRevEst').append($('<option>', {
            value: tehsilList[i].code, // Adjust this based on your tehsil object structure
            text: tehsilList[i].name // Adjust this based on your tehsil object structure
        }));
    }
	
}

function getMurabaList(){
	
	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	var khrsRevEst = $("#khrsRevEst").val();
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil,
			"NVCode": khrsRevEst
	};
	
	var URL = 'NewTCPLicenseForm.html?getMurabaByNVCODE';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var murabaData = returnData.must;

    $('#khrsMustil').empty();

    $('#khrsMustil').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < murabaData.length; i++) {
        $('#khrsMustil').append($('<option>', {
            value: murabaData[i],
            text: murabaData[i]// Adjust this based on your tehsil object structure
        }));
    }
	
	
}

function getKhasraList(){
	
	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	var khrsRevEst = $("#khrsRevEst").val();
	var khrsMustil = $("#khrsMustil").val();
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil,
			"NVCode": khrsRevEst,
			"muraba": khrsMustil
	};
	
	var URL = 'NewTCPLicenseForm.html?getKhasraListByNVCODE';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var murabaData = returnData.must;

	var tehsilList = returnData;

    $('#khrsKilla').empty();

    $('#khrsKilla').append($('<option>', {
        value: '',
        text: 'select'
    }));

    // Add options for each tehsil in the returned list
    for (var i = 0; i < tehsilList.length; i++) {
        $('#khrsKilla').append($('<option>', {
            value: tehsilList[i].khewats, // Adjust this based on your tehsil object structure
            text: tehsilList[i].killa // Adjust this based on your tehsil object structure
        }));
        
    }
	
	
}

function getOwnerData(){

	var district = $("#ddz1").val();
	var khrsThesil = $("#khrsThesil").val();
	var khrsRevEst = $("#khrsRevEst").val();
	var khrsKilla = $("#khrsKilla").val();
	
	
	var requestData = {
			"districtId" : district,
			"tCode": khrsThesil,
			"NVCode": khrsRevEst,
			"_Khewat": khrsKilla
	};
	
	var URL = 'NewTCPLicenseForm.html?getOwnersbykhewatOnline';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	
	var tehsilList = returnData;
	
	$('#tcpNameOfLO').val("");
	
	var names = ""; 
	
	
	// Add options for each tehsil in the returned list
	for (var i = 0; i < tehsilList.length; i++) {
		names += tehsilList[i].name;
	}
	
	$('#tcpNameOfLO').val(names);
	$("#landOwnerName" + rowCountClick).val(names);
	

}

function validateApplicantInfo(errorList){
	 if (!$('#confirmCheckbox').is(':checked')) {
		 errorList.push(getLocalMessage("Please confirm if Information fetched from developer registration is updated"));
	 }
	 
	 return errorList;
}

function validatePurposeInfo(errorList){
	
	var appPAppType = $("#appPAppType").val();
	var purpose = $("#appPLicPurposeId").val();
	var district = $("#ddz1").val();
	var developmentPlan = $("#ddz2").val();
	var zone = $("#ddz3").val();
	var sector = $("#ddz4").val();
	var tehsil = $("#khrsThesil").val();
	var revEstate = $("#khrsRevEst").val();
	var hadbastNo = $("#khrsHadbast").val();
	var mustil = $("#khrsMustil").val();
	var khrsKilla = $("#khrsKilla").val();
	var khrsDevCollabValue = $('input[name="licenseApplicationMasterDTO.khrsDevCollab"]:checked').val();
	var khrsLandTypeId = $('#khrsLandTypeId').val();
	var changeInfoChecked = $('#changeInfo').is(':checked');
	var consolTypeValue = $('input[name="licenseApplicationMasterDTO.ciConsType"]:checked').val();
	var aquStatus = $('#ciAquStatus').val();
	var saveMode = $('#hiddenSaveMode').val();
	
	if(appPAppType=="" || appPAppType==null || appPAppType=="0"){
		errorList.push(getLocalMessage("Please select Application type"));
	}
	
	if(purpose=="" || purpose==null || purpose=="0"){
		errorList.push(getLocalMessage("Please select Purpose type"));
	}
	
	if(district=="" || district==null || district=="0"){
		errorList.push(getLocalMessage("Please select District"));
	}
	
	if(developmentPlan=="" || developmentPlan==null || developmentPlan=="0"){
		errorList.push(getLocalMessage("Please select Development plan"));
	}
	
	if(zone=="" || zone==null || zone=="0"){
		errorList.push(getLocalMessage("Please select Zone"));
	}
	
	if(sector=="" || sector==null || sector=="0"){
		errorList.push(getLocalMessage("Please select Sector"));
	}
	
	if(tehsil=="" || tehsil==null || tehsil==undefined){
		errorList.push(getLocalMessage("Please select Tehsil"));
	}

	if(revEstate=="" || revEstate==null || revEstate==undefined){
		errorList.push(getLocalMessage("Please select Revenue estate"));
	}
	
	if(hadbastNo=="" || hadbastNo==null || hadbastNo==undefined){
		errorList.push(getLocalMessage("Please enter Hadbast no."));
	}
	
	if(mustil=="" || mustil==null || mustil== undefined){
		errorList.push(getLocalMessage("Please select Rectangle/Mustil"));
	}
	
	if(khrsKilla=="" || khrsKilla==null || khrsKilla== undefined){
		errorList.push(getLocalMessage("Please select Khasra/Killa"));
	}
	
	if (khrsDevCollabValue === undefined || khrsDevCollabValue === null || khrsDevCollabValue === "") {
	    errorList.push(getLocalMessage("Please select whether Khasra has been developed in collaboration"));
	}
	
	if(khrsDevCollabValue == "Y"){
		
		var khrsDevComName = $('#khrsDevComName').val();
		if (khrsDevComName === "" || khrsDevComName === null || khrsDevComName === undefined) {
		    errorList.push(getLocalMessage("Please enter the Name of the developer company"));
		}

		var khrsColabRegDate = $('#khrsColabRegDate').val();
		if (khrsColabRegDate === "" || khrsColabRegDate === null || khrsColabRegDate === undefined) {
		    errorList.push(getLocalMessage("Please enter the Date of registering collaboration agreement"));
		}

		var isIrrevocable = $('input[name="licenseApplicationMasterDTO.khrsCollabAggRevo"]:checked').val();
		if (isIrrevocable === undefined || isIrrevocable === null || isIrrevocable === "") {
		    errorList.push(getLocalMessage("Please select whether collaboration agreement is irrevocable"));
		}

		var authorizedSignatoryOwner = $('#authorizedSignatoryOwner').val();
		if (authorizedSignatoryOwner === "" || authorizedSignatoryOwner === null || authorizedSignatoryOwner === undefined) {
		    errorList.push(getLocalMessage("Please enter the Name of authorized signatory on behalf of land owner(s)"));
		}

		var khrsAurSignDev = $('#khrsAurSignDev').val();
		if (khrsAurSignDev === "" || khrsAurSignDev === null || khrsAurSignDev === undefined) {
		    errorList.push(getLocalMessage("Please enter the Name of authorized signatory on behalf of developer"));
		}
		
		
		var khrsRegAuth = $('#khrsRegAuth').val();
		if (khrsRegAuth === "" || khrsRegAuth === null || khrsRegAuth === undefined) {
		    errorList.push(getLocalMessage("Please enter the Registering Authority"));
		}

		
	}
	
	if (khrsLandTypeId === "" || khrsLandTypeId === null || khrsLandTypeId === undefined) {
	    errorList.push(getLocalMessage("Please select the Type of land"));
	}
	
	if(changeInfoChecked){
		
		var recMustChangeValue = $('#recMustChange').val();
		if (recMustChangeValue === null || recMustChangeValue === undefined || recMustChangeValue.trim() === "") {
		    errorList.push(getLocalMessage("Please enter Rectangle No./Mustil(Changed)"));
		}

		var khasNoChangeValue = $('#khasNoChange').val();
		if (khasNoChangeValue === null || khasNoChangeValue === undefined || khasNoChangeValue.trim() === "") {
		    errorList.push(getLocalMessage("Please enter Khasra number(Changed)"));
		}

		var loNameMutValue = $('#LoNameMut').val();
		if (loNameMutValue === null || loNameMutValue === undefined || loNameMutValue.trim() === "") {
		    errorList.push(getLocalMessage("Please enter Name of the Land Owner as per Mutation/Jamabandi"));
		}
	}
	
	if (consolTypeValue === undefined || consolTypeValue === null || consolTypeValue === "") {
	    errorList.push(getLocalMessage("Please select Consolidation Type "));
	}
	if(saveMode != 'V'){
	if(consolTypeValue != null){
		if(consolTypeValue ==="C"){
			var ciConsTypeKanalValue = $('#ciConsTypeKanal').val();
			if (ciConsTypeKanalValue === null || ciConsTypeKanalValue === undefined || ciConsTypeKanalValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Kanal"));
			}

			var ciConsTypeMarlaValue = $('#ciConsTypeMarla').val();
			if (ciConsTypeMarlaValue === null || ciConsTypeMarlaValue === undefined || ciConsTypeMarlaValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Marla"));
			}

			var ciConsTypeSarsaiValue = $('#ciConsTypeSarsai').val();
			if (ciConsTypeSarsaiValue === null || ciConsTypeSarsaiValue === undefined || ciConsTypeSarsaiValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Sarsai"));
			}
		}else if(consolTypeValue ==="NC"){
			
			var nonConsTypeId = $('input[name="licenseApplicationMasterDTO.ciNonConsTypeId"]:checked').val();
			if (nonConsTypeId === undefined || nonConsTypeId === null || nonConsTypeId.trim() === "") {
			    errorList.push(getLocalMessage("Please select Non Consolidation Type"));
			}

			var bighaNonConsolidationValue = $('#bighaNonConsolidation').val();
			if (bighaNonConsolidationValue === null || bighaNonConsolidationValue === undefined || bighaNonConsolidationValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Bigha"));
			}

			var biswaNonConsolidationValue = $('#biswaNonConsolidation').val();
			if (biswaNonConsolidationValue === null || biswaNonConsolidationValue === undefined || biswaNonConsolidationValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Biswa"));
			}

			var biswansiNonConsolidationValue = $('#biswansiNonConsolidation').val();
			if (biswansiNonConsolidationValue === null || biswansiNonConsolidationValue === undefined || biswansiNonConsolidationValue.trim() === "") {
			    errorList.push(getLocalMessage("Please enter Biswansi"));
			}
		}
	}
	}
	
	if(aquStatus=="" || aquStatus==null || aquStatus==undefined){
		errorList.push(getLocalMessage("Please enter Aquistion Status"));
	}
	
	return errorList;
}

function validateDetailsofLandInfo(errorList){
	
	$("#itemDetails tbody tr.itemDetailClass").each(function(i) {
		
		var purpose = $("#subpurpose1" + i).val();
		var subpurpose = $("#subpurpose2" + i).val();
		var far = $("#far" + i).val();
		var area = $("#area" + i).val();
		var row = i + 1;
		if(purpose=="" || purpose==null || purpose==undefined){
			errorList.push(getLocalMessage("Please select component at :" + row));
		}
		
		if(subpurpose=="" || subpurpose==null || subpurpose==undefined){
			errorList.push(getLocalMessage("Please select sub component at :" + row));
		}
		
		if(far=="" || far==null || far==undefined){
			errorList.push(getLocalMessage("Please select far at :" + row));
		}
		
		if(area=="" || area==null || area==undefined){
			errorList.push(getLocalMessage("Please select area at :" + row));
		}
		
	});
	
	return errorList;
	
}

function validateLandInfo(errorList){
	
	var encub =  $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.encumb"]:checked').val();
	var exiLitigation =  $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigation"]:checked').val();
	var insolv =  $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.insolv"]:checked').val();
	var spAppliedLand = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLand"]:checked').val();
	var spRevRasta = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRasta"]:checked').val();
	var spWatercourse = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spWatercourse"]:checked').val();
	var spAppliedLandPatwari = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandPatwari"]:checked').val();
	var spAppliedLandBoundary = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAppliedLandBoundary"]:checked').val();
	var spAcqStatus = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatus"]:checked').val();
	var spCompactBlock = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spCompactBlock"]:checked').val();
	var existAppr = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.existAppr"]:checked').val();
	var paSiteAprSR = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSR"]:checked').val();
	var paSiteAprSPR = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprSPR"]:checked').val();
	var paSiteAprOther = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.paSiteAprOther"]:checked').val();
	var scVac = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scVac"]:checked').val();
	var scHtLine = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scHtLine"]:checked').val();
	var scIocGasPline = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scIocGasPline"]:checked').val();
	var scNallah = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scNallah"]:checked').val();	
	var scUtilityLine = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLine"]:checked').val();
	var scUtilityLineAL = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scUtilityLineAL"]:checked').val();
	var scOtherFeature = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.scOtherFeature"]:checked').val();
	var spAcqStatusExcluAquPr = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusExcluAquPr"]:checked').val();
	
	if (encub == "" || encub == null || encub == undefined){
		errorList.push(getLocalMessage("Please select encumbrance"));
	}
	if(encub != "" && encub != "None"){
		var emburanceRemark = $('#emburanceRemark').val();
		if(emburanceRemark == "" || emburanceRemark == null){
			errorList.push(getLocalMessage("Please select encumbrance remark"));
		}
	}
	if (exiLitigation == "" || exiLitigation == null || exiLitigation == undefined){
		errorList.push(getLocalMessage("Please select Existing litigation, if any, concerning applied land including co-sharers and collaborator."));
	}
	if(exiLitigation == "Yes"){
		var exiLitigationCOrd = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.exiLitigationCOrd"]:checked').val();
		if(exiLitigationCOrd == "" || exiLitigationCOrd == null){
			errorList.push(getLocalMessage("Please select whether Court orders, if any, affecting applied land."));
		}
		
	}
	if (insolv == "" || insolv == null || insolv == undefined){
		errorList.push(getLocalMessage("Please select if any insolvency/liquidation proceedings against the Land Owing Company/Developer Company"));
	}
	if(insolv == "Yes"){
		var insolvencyRemark = $('#insolvencyRemark').val();
		if(insolvencyRemark == null || insolvencyRemark == ""){
			errorList.push(getLocalMessage("Please select insolvency/liquidation proceedings remark"));
		}
	}
	if (spAppliedLand == "" || spAppliedLand == null || spAppliedLand == undefined){
		errorList.push(getLocalMessage("Please select if as per applied land"));
	}
	if (spRevRasta == "" || spRevRasta == null || spRevRasta == undefined){
		errorList.push(getLocalMessage("Please select revenue rasta"));
	}
	if(spRevRasta =="Yes"){
		var spRevRastaTyp = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spRevRastaTyp"]:checked').val();
		var spRevRastaTypConId = $('#spRevRastaTypConId').val();
		var spRevRastaTypConWid = $('#spRevRastaTypConWid').val();
		
		if(spRevRastaTyp =="" || spRevRastaTyp ==null){
			errorList.push(getLocalMessage("Please select rasta type"));
		}
		if(spRevRastaTypConId =="" || spRevRastaTypConId ==null || spRevRastaTypConId == undefined){
			errorList.push(getLocalMessage("Please select consolidated/unconsolidated rasta type"));
		}
		if(spRevRastaTypConWid =="" || spRevRastaTypConWid ==null || spRevRastaTypConWid == undefined){
			errorList.push(getLocalMessage("Please enter rasta type width"));
		}
	}
	if (spWatercourse == "" || spWatercourse == null || spWatercourse == undefined){
		errorList.push(getLocalMessage("Please select Watercourse"));
	}
	if(spWatercourse == "Yes"){
		var watercourseType = $('#watercourseType').val();
		var spWatercourseTypWid = $('#spWatercourseTypWid').val();
		var watercourseRemark = $('#watercourseRemark').val();
		
		if(watercourseType == null || watercourseType == undefined || watercourseType ==""){
			errorList.push(getLocalMessage("Please select Watercourse type"));
		}
		if(spWatercourseTypWid == null || spWatercourseTypWid == undefined || spWatercourseTypWid ==""){
			errorList.push(getLocalMessage("Please enter Watercourse width"));
		}
		if(watercourseRemark == null || watercourseRemark == undefined || watercourseRemark ==""){
			errorList.push(getLocalMessage("Please enter Watercourse remark"));
		}
	}
	if (spAppliedLandPatwari == "" || spAppliedLandPatwari == null || spAppliedLandPatwari == undefined){
		errorList.push(getLocalMessage("Please select if original shajra plan by patwari"));
	}
	if (spAppliedLandBoundary == "" || spAppliedLandBoundary == null || spAppliedLandBoundary == undefined){
		errorList.push(getLocalMessage("Please select if Original Shajra plan showing Boundary of Applied Land"));
	}
	if (spAcqStatus == "" || spAcqStatus == null || spAcqStatus == undefined){
		errorList.push(getLocalMessage("Please select Acquisition status"));
	}
	if(spAcqStatus == "Yes"){
		var spAcqStatusD4Not = $('#spAcqStatusD4Not').val();
		var spAcqStatusD6Not = $('#spAcqStatusD6Not').val();
		var spAcqStatusDAward = $('#spAcqStatusDAward').val();
		if (spAcqStatusD4Not == "" || spAcqStatusD4Not == null || spAcqStatusD4Not == undefined){
			errorList.push(getLocalMessage("Please enter date of section 4 notification"));
		}
		if (spAcqStatusD6Not == "" || spAcqStatusD6Not == null || spAcqStatusD6Not == undefined){
			errorList.push(getLocalMessage("Please enter date of section 6 notification"));
		}
		if (spAcqStatusDAward == "" || spAcqStatusDAward == null || spAcqStatusDAward == undefined){
			errorList.push(getLocalMessage("Please enter date of award"));
		}
		if (spAcqStatusExcluAquPr == "" || spAcqStatusExcluAquPr == null || spAcqStatusExcluAquPr == undefined){
			errorList.push(getLocalMessage("Please select whether land Released/Excluded from aqusition proceeding"));
		}
		if(spAcqStatusExcluAquPr == "Yes"){
			var spAcqStatusLdComp = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusLdComp"]:checked').val();
			//var spAcqStatusRelStat = $('#spAcqStatusRelStat').val();
			var spAcqStatusRelDate = $('#spAcqStatusRelDate').val();
			var siteDetials = $('#siteDetials').val();
			var spAcqStatusRelLitig = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.spAcqStatusRelLitig"]:checked').val();

			
			if(spAcqStatusLdComp == "" || spAcqStatusLdComp ==null){
				errorList.push(getLocalMessage("Please select whether land compensation"));
			}
			/*if(spAcqStatusRelStat == undefined || spAcqStatusRelStat ==null){
				errorList.push(getLocalMessage("Please select status of release"));
			}*/
			if(spAcqStatusRelDate == "" || spAcqStatusRelDate ==null){
				errorList.push(getLocalMessage("Please select date of release"));
			}
			if(siteDetials == "" || siteDetials == null){
				errorList.push(getLocalMessage("Please select site details"));
			}
			if(spAcqStatusRelLitig == "" || spAcqStatusRelLitig == null){
				errorList.push(getLocalMessage("Please select whether litigation regarding release of Land"));
			}
			if(spAcqStatusRelLitig == "Y"){
				
				var cwpSlpNumber = $("#cwpSlpNumber").val();
				if(cwpSlpNumber == "" || cwpSlpNumber == null){
					errorList.push(getLocalMessage("Please enter CWP/SLP number"));
				}
			}
			
		}

		
	}
	if (spCompactBlock == "" || spCompactBlock == null || spCompactBlock == undefined){
		errorList.push(getLocalMessage("Please select Whether in Compact Block"));
	}
	var spCompactBlockSep = $('#spCompactBlockSep').val();
	var spCompactBlockPkt = $('#spCompactBlockPkt').val();
	
	if(spCompactBlockSep == null || spCompactBlockSep == "" || spCompactBlockSep == undefined){
		errorList.push(getLocalMessage("Please select separated by"));
	}
	if(spCompactBlockPkt == null || spCompactBlockPkt == "" || spCompactBlockPkt == undefined){
		errorList.push(getLocalMessage("Please enter number of pockets"));
	}
	if(spCompactBlockPkt != null || spCompactBlockPkt != ""){
		var surTableLength = $("#surroundingDetail tbody tr").length;
		
		if(spCompactBlockPkt != surTableLength){
			errorList.push(getLocalMessage("No of rows in surrounding table must be equal to the number of pockets"));
		}
	}
	if (existAppr == "" || existAppr == null || existAppr == undefined){
		errorList.push(getLocalMessage("Please select if details of existing approach as per policy dated 20-10-2020"));
	}
	if(existAppr == "Category-I approach"){
		var aAppWRevRas = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.aAppWRevRas"]:checked').val();
		var bAppASR = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.bAppASR"]:checked').val();
		var cAppCSR = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.cAppCSR"]:checked').val();
		var dAppAccLOA = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.dAppAccLOA"]:checked').val();
		var eAppOL = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.eAppOL"]:checked').val();
		
		if(aAppWRevRas == "" || aAppWRevRas == null){
			errorList.push(getLocalMessage("Please select if approach available from minimum 4 karam (22 ft) wide revenue rasta"));
		}
		if(bAppASR == "" || bAppASR == null){
			errorList.push(getLocalMessage("Please select if approach available from minimum 11 feet wide revenue rasta and applied site abuts acquired alignment of the sector road"));
		}
		if(cAppCSR == "" || cAppCSR == null){
			errorList.push(getLocalMessage("Please select if applied site abouts already constructed sector road or internal circulation road of approved sectoral plan"));
		}
		if(dAppAccLOA == "" || dAppAccLOA == null){
			errorList.push(getLocalMessage("Please select if applied land is accessible from a minimum 4 karam wide rasta through adjoining own land of the applicant"));
		}
		if(dAppAccLOA =="Yes"){
			var d1AppAccGPM = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.d1AppAccGPM"]:checked').val();
			if(d1AppAccGPM == "" || d1AppAccGPM == null){
				errorList.push(getLocalMessage("Please select if applicable, whether the applicant has donated at least 4 karam wide strip from its adjoining own land in favour of the Gram Panchayat/Municipality"));
			}
		}
		if(eAppOL == "" || eAppOL == null){
			errorList.push(getLocalMessage("Please select if applied land is accessible from a minimum 4 karam wide rasta through adjoining other’s land"));
		}
		if(eAppOL == "Yes"){
			var e1AppOLGM = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.e1AppOLGM"]:checked').val();
			if(e1AppOLGM == "" || e1AppOLGM == null){
				errorList.push(getLocalMessage("Please select whether the land-owner of the adjoining land has donated at least 4 karam wide strip of land to the Gram Panchayat/Municipality"));
			}
		}
	}else if(existAppr == "Category-II approach"){
		var cat2Width = $('#cat2Width').val();
		var cat2irrv = $('input[name="licenseApplicationMasterDTO.landCategoryDTO.cat2irrv"]:checked').val();
		var accessNHSRFlag = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.accessNHSRFlag"]:checked').val();

		if(cat2Width == "" || cat2Width == null){
			errorList.push(getLocalMessage("Please enter width in meters of Category-II approach"));
		}
		if(cat2irrv == "" || cat2irrv == null){
			errorList.push(getLocalMessage("Please select whether irrevocable consent from such developer/ colonizer for uninterrupted usage of such internal road for the purpose of development of the colony"));
		}
		if(accessNHSRFlag == "" || accessNHSRFlag == null){
			errorList.push(getLocalMessage("Please select whether access from NH/SR"));
		}
	}
	if (paSiteAprSR == "" || paSiteAprSR == null || paSiteAprSR == undefined){
		errorList.push(getLocalMessage("Please select if Site approachable from proposed sector road/ Development Plan Road"));
	}
	if(paSiteAprSR === "Yes"){
		var aPaSiteAprSrWid = $('#aPaSiteAprSrWid').val();
		var bPaSiteAprSrAcq = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSrAcq"]:checked').val();
		var cPaSiteAprSrCons = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSrCons"]:checked').val();
		var dPaSiteAprSrSRA = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.dPaSiteAprSrSRA"]:checked').val();
		var ePaSiteAprSrSRC = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.ePaSiteAprSrSRC"]:checked').val();
		
		if(aPaSiteAprSrWid == "" || aPaSiteAprSrWid == null){
			errorList.push(getLocalMessage("Please enter width in meters in details of proposed approach"));
		}
		if(bPaSiteAprSrAcq == "" || bPaSiteAprSrAcq == null){
			errorList.push(getLocalMessage("Please select whether acquired?"));
		}
		if(cPaSiteAprSrCons == "" || cPaSiteAprSrCons == null){
			errorList.push(getLocalMessage("Please select whether constructed?"));
		}
		if(dPaSiteAprSrSRA == "" || dPaSiteAprSrSRA == null){
			errorList.push(getLocalMessage("Please select whether service road along sector road acquired?"));
		}
		if(ePaSiteAprSrSRC == "" || ePaSiteAprSrSRC == null){
			errorList.push(getLocalMessage("Please select whether service road along sector road constructed?"));
		}
	}
	if (paSiteAprSPR == "" || paSiteAprSPR == null || paSiteAprSPR == undefined){
		errorList.push(getLocalMessage("Please select if Site approachable from internal circulation / sectoral plan road"));
	}
	if(paSiteAprSPR == "Yes"){
		var aPaSiteAprSprWid = $('#internalCirculationwidthMtrs').val();
		var bPaSiteAprSprAcq = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.bPaSiteAprSprAcq"]:checked').val();
		var cPaSiteAprSprCons = $('input[name="licenseApplicationMasterDTO.licenseApplicationLandScheduleDTO.cPaSiteAprSprCons"]:checked').val();

		if(aPaSiteAprSprWid == null || aPaSiteAprSprWid == ""){
			errorList.push(getLocalMessage("Please enter width in meters for site approachable from internal circulation / sectoral plan road."));
		}
		if(bPaSiteAprSprAcq == null || bPaSiteAprSprAcq == ""){
			errorList.push(getLocalMessage("Please select whether acquired for site approachable from internal circulation / sectoral plan road."));
		}
		if(cPaSiteAprSprCons == null || cPaSiteAprSprCons == ""){
			errorList.push(getLocalMessage("Please select whether constructed for site approachable from internal circulation / sectoral plan road."));
		}
	}
	if (paSiteAprOther == "" || paSiteAprOther == null || paSiteAprOther == undefined){
		errorList.push(getLocalMessage("Please select if any other type of existing approach available"));
	}
	if (scVac == "" || scVac == null || scVac == undefined){
		errorList.push(getLocalMessage("Please select if Vacant"));
	}
	if(scVac == "No"){
		var vacantRemark = $('#vacantRemark').val();
		if (vacantRemark == "" || vacantRemark == null || vacantRemark == undefined){
			errorList.push(getLocalMessage("Please enter vacant remark"));
		}
	}
	if (scHtLine == "" || scHtLine == null || scHtLine == undefined){
		errorList.push(getLocalMessage("Please select if HT line"));
	}
	var htLineRemark = $('#htLineRemark').val();
	if (htLineRemark == "" || htLineRemark == null || htLineRemark == undefined){
		errorList.push(getLocalMessage("Please enter HT line remark"));
	}
	if (scIocGasPline == "" || scIocGasPline == null || scIocGasPline == undefined){
		errorList.push(getLocalMessage("Please select if Ioc Gas Pipeline"));
	}
	var gasPipelineRemark = $('#gasPipelineRemark').val();
	if (gasPipelineRemark == "" || gasPipelineRemark == null || gasPipelineRemark == undefined){
		errorList.push(getLocalMessage("Please enter Ioc Gas Pipeline remark"));
	}
	if (scNallah == "" || scNallah == null || scNallah == undefined){
		errorList.push(getLocalMessage("Please select Nallah"));
	}
	var nallahRemark = $('#nallahRemark').val();
	if (nallahRemark == "" || nallahRemark == null || nallahRemark == undefined){
		errorList.push(getLocalMessage("Please enter Nallah remark"));
	}
	if (scUtilityLine == "" || scUtilityLine == null || scUtilityLine == undefined){
		errorList.push(getLocalMessage("Please select Utility/Permit line"));
	}
	var permitLineRemark = $('#permitLineRemark').val();
	if (permitLineRemark == "" || permitLineRemark == null || permitLineRemark == undefined){
		errorList.push(getLocalMessage("Please enter Utility/Permit Line remark"));
	}
	if (scUtilityLineAL == "" || scUtilityLineAL == null || scUtilityLineAL == undefined){
		errorList.push(getLocalMessage("Please select whether other land falls within the applied land"));
	}
	var fallsWithinAppliedLandRemark = $('#fallsWithinAppliedLandRemark').val();
	if (fallsWithinAppliedLandRemark == "" || fallsWithinAppliedLandRemark == null || fallsWithinAppliedLandRemark == undefined){
		errorList.push(getLocalMessage("Please select enter whether other land falls within the applied land remark"));
	}
	if (scOtherFeature == "" || scOtherFeature == null || scOtherFeature == undefined){
		errorList.push(getLocalMessage("Please select whether any other feature passing through site"));
	}
	if(scOtherFeature == "Yes"){
	var detailsthereof = $('#detailsthereof').val();
	if(detailsthereof =="" || detailsthereof ==null){
		errorList.push(getLocalMessage("Please enter details thereof"));
	}
	}
	$("#surroundingDetail tbody tr.appendableClass").each(function(i) {
		 var pocketName = $("#pocketName" + i).val();
		 var north = $("#north" + i).val();
		 var south = $("#south" + i).val();
		 var east = $("#east" + i).val();
		 var west = $("#west" + i).val();
		 
		 var row = i + 1
		 
		 if (pocketName == "" || pocketName == null || pocketName == undefined){
				errorList.push(getLocalMessage("Please select Pocket Name at :" + row));
		 }
		
		 if (north == "" || north == null || north == undefined){
				errorList.push(getLocalMessage("Please select north at :" + row));
		 }
		 if (south == "" || south == null || south == undefined){
				errorList.push(getLocalMessage("Please select south at :" + row));
		 }
		 if (east == "" || east == null || east == undefined){
				errorList.push(getLocalMessage("Please select east at :" + row));
		 }
		 if (west == "" || west == null || west == undefined){
				errorList.push(getLocalMessage("Please select south at :" + row));
		 }
	});
	
	
	
	return errorList;
}

function handleEditClick(button) {
    var rowIndex = $(button).closest('tr').index();
    rowCountClick = rowIndex;
    alert('Button in row ' + (rowIndex + 1) + ' clicked.');
}

function doesLicExist(){
	
	var errorList = [];
	
	var district = $("#ddz1").val();
	var developmentPlan = $("#ddz2").val();
	var zone = $("#ddz3").val();
	var sector = $("#ddz4").val();
	var tehsil = $("#khrsThesil").val();
	var revEstate = $("#khrsRevEst").val();
	var hadbastNo = $("#khrsHadbast").val();
	var mustil = $("#khrsMustil").val();
	var khrsKilla = $("#khrsKilla").val();
	
	var requestData = {
		"khrsDist" : district,
		"khrsDevPlan" : developmentPlan,
		"khrsZone" : zone,
		"khrsSec" : sector,
		"khrsThesil" : tehsil,
		"khrsRevEst" : revEstate,
		"khrsHadbast" : hadbastNo,
		"khrsMustil" : mustil,
		"khrsKilla" : khrsKilla
	};
	
	var URL = 'NewTCPLicenseForm.html?checkIfDetailsExist';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	
	if(returnData != ""){
		errorList.push(getLocalMessage("Already Attached with License no :" + returnData ));
		displayErrorsOnPage(errorList);
	}

}
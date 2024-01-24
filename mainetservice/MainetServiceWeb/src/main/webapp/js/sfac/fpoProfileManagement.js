
$(document).ready(
		function() {




			$(".datepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				yearRange : "1900:2200",
				maxDate: 0

			});

			$('.chosen-select-no-results').chosen();

			$('.alphaNumeric').keyup(function() {

				var regx = /^[A-Za-z0-9.\s]*$/;

				var amount	=	$(this).val();

				if(!regx.test(amount))
				{
					amount = amount.substring(0, amount.length-1);

					$(this).val(amount);	
				} 
			});

		});

var tabcheck = false;

function showPreviousTab(previousTab, alternateTab) {
	if(!$('#fpoPMParentTab a[href='+previousTab+']').parent().hasClass('disabled')) {
		$('#fpoPMParentTab a[href='+previousTab+']').tab('show');
	} else {
		$('#fpoPMParentTab a[href='+alternateTab+']').tab('show');
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

//D#34059
function processTabSaveRes(response, nextTab, currentDiv,parentTab) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');

	if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
		//$(targetDivName).html(response);
		//$('#assetParentTab a[href='+currentDiv+']').data('loaded',false);
		$(''+parentTab+' a[href='+nextTab+']').tab('show');

		var errorPreviousTab = $(currentDiv).find('#validationerrordiv');
		if(errorPreviousTab.length > 0){
			var divError = $(currentDiv).find('#validationerrordiv');
			$(divError).addClass('hide');
		}
	} else {
		//window.scrollTo(0, 0);
		$(currentDiv).html(response);
		prepareDateTag();
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function showTab(tabId) {
	$('#fpoPMParentTab a[href=#'+tabId+']').tab('show');
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function navigateTab(id, tabToSubmit, element) {debugger

	if (!($('.nav li#' + id).hasClass("disabled"))) {

		
			$('#fpoPMParentTab a[href="#'+tabToSubmit+'"]').tab('show');
		
		prepareDateTag();
		var activeTab = $('#fpoPMParentTab').find('.active');
		if(activeTab)
			$(activeTab).removeClass('active');
		$('.nav li#' + id).addClass('active');
		
		

	}
	return false;
}



function modifyCase(fpoId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode" : mode,
			"fpoId" : fpoId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	var viewMode = $('#modeType').val();
	if(viewMode == 'V'){
		$('#license-tab').find('a').attr('href', '#licenseInfo');
		var disabledTab = $('#fpoPMParentTab').find('.disabled');
		$(disabledTab).removeClass('disabled');
		$('#fpoPMParentTab' ).find('a').attr('data-toggle', 'tab');

	}
	prepareTags();

}



function searchForm(obj) {
	var errorList = [];
	var fpoId = $("#fpoId").val();
	var fpoRegNo = $("#fpoRegNo").val();
	var iaId = $("#iaId").val();
	var divName = '.content-page';
	if ((fpoId == "" || fpoId == "0" || fpoId == undefined) && (fpoRegNo == "0" || fpoRegNo == undefined || fpoRegNo == "")) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
				"fpoId" : fpoId,
				"fpoRegNo" : fpoRegNo,
				"iaId" :iaId
		};
		var ajaxResponse = doAjaxLoading('FPOProfileManagementForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}


function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('FPOProfileManagementForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}


function addLicenseButton(obj) {
	var errorList = [];
	errorList = validateLicenseDetailsTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#licenseEntryTable tr').last().clone();
		$('#licenseEntryTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:file").val('');


		content.find("input:checkbox").prop('checked', false);
		content.find("a:eq(0)").html('');
		content.find('[id^="LicenseType"]').chosen().trigger("chosen:updated");
		content.find('[id^="file_list"]').html('');




		reordeLicenseDetailsTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}



function fileCountUpload(element) {
	var errorList = [];
	errorList = validateLicenseDetailsTable(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#fpoPMLicenceInfo';
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('FPOProfileManagementForm.html?fileCountUpload','POST',requestData,false,'html');
		console.log('res' + response);
		$("#licenseInfo").html(response);
		prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}

}



function fileCountUploadBP(element) {
	var errorList = [];
	errorList = validateBPTable(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#fpoPMBPInfo';
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('FPOProfileManagementForm.html?fileCountUploadBP','POST',requestData,false,'html');
		console.log('res' + response);
		$("#bpInfo").html(response);
		prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}

}


function fileCountUploadABS(element) {
	var errorList = [];
	errorList = validateABSTable(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#fpoPMABSInfo';
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('FPOProfileManagementForm.html?fileCountUploadABS','POST',requestData,false,'html');
		console.log('res' + response);
		$("#absInfo").html(response);
		prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}

}


function reordeLicenseDetailsTable() {
	$("#licenseEntryTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		                      


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('select:eq(0)').attr('id','LicenseType' + i);
		$(this).find('input:text:eq(2)').attr('id','licenseDesc' + i);
		$(this).find('input:text:eq(3)').attr('id','licIssueDate' + i);
		$(this).find('input:text:eq(4)').attr('id','licExpDate' + i);
		$(this).find('input:text:eq(5)').attr('id','licIssueAuth' + i);
		$(this).find("input:file:eq(0)").attr("id", "dto.licenseInformationDetEntities" + i + ".attachmentsLi0.uploadedDocumentPath");


		$(this).find("input:text:eq(0)").attr("name", "dto.licenseInformationDetEntities[" + i + "].sNo");


		$(this).find("select:eq(0)").attr("name", "dto.licenseInformationDetEntities[" + i + "].LicenseType");
		$(this).find("input:text:eq(2)").attr("name", "dto.licenseInformationDetEntities[" + i + "].licenseDesc");
		$(this).find("input:text:eq(3)").attr("name", "dto.licenseInformationDetEntities[" + i + "].licIssueDate");
		$(this).find("input:text:eq(4)").attr("name", "dto.licenseInformationDetEntities[" + i + "].licExpDate");
		$(this).find("input:text:eq(5)").attr("name", "dto.licenseInformationDetEntities[" + i + "].licIssueAuth");
		$(this).find("input:file:eq(0)").attr("name", "dto.licenseInformationDetEntities[" + i + "].attachmentsLi[0].uploadedDocumentPath");

		$("#sNo" + i).val(i + 1);

		$('#licenseEntry').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});

		//$(this).find('[id^="file_list"]').attr('id','file_list'+i);
		//$(this).find('[id=^"dto.licenseInformationDetEntities').attr('code','file_'+i);




	});
}

function validateLicenseDetailsTable() {
	var errorList = [];
	var rowCount = $('#licenseEntryTable tr').length;	


	if (errorList == 0)
		$("#licenseEntryTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var licIssueDate = $("#licIssueDate" + i).val();
				var licExpDate = $("#licExpDate" + i).val();
				var licIssueAuth = $("#licIssueAuth" + i).val();
				var LicenseType = $("#LicenseType" + i).val();
				var constant = 1;
			}
			else{
				var licIssueDate = $("#licIssueDate" + i).val();
				var licExpDate = $("#licExpDate" + i).val();
				var licIssueAuth = $("#licIssueAuth" + i).val();
				var LicenseType = $("#LicenseType" + i).val();
				var constant = i+1;
			}

			if (LicenseType == '0' || LicenseType == undefined || LicenseType == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.licenseType") +" " + (i + 1));
			}
			if (licIssueDate == undefined || licIssueDate == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.licenseIssueDt") + " " + (i + 1));
			}

			if (licExpDate == undefined || licExpDate == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.licenseExpiryDt") + " " + (i + 1));
			}
			if (licIssueAuth == undefined || licIssueAuth == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.licenseAuth") + " " + (i + 1));
			}
			/*if( document.getElementById("dto.licenseInformationDetEntities" + i + ".attachments" + i + ".uploadedDocumentPath").files.length == 0 ){
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.docUpload") + " " + (i + 1));
				}*/

		});

	return errorList;
}

function addStorageInfoButton(obj) {
	var errorList = [];
	errorList = validateStorageInfoTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#storageInfoTable tr').last().clone();
		$('#storageInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);

		//  content.find('[id^="LicenseType"]').chosen().trigger("chosen:updated");
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeStorageInfoTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteStorageDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#storageInfoTable tr').length;
	if ($("#storageInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeStorageInfoTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeStorageInfoTable() {
	$("#storageInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','commodityName' + i);
		$(this).find('input:text:eq(2)').attr('id','storageName' + i);
		$(this).find('input:text:eq(3)').attr('id','storageAddress' + i);
		$(this).find('input:text:eq(4)').attr('id','storageAdminName' + i);
		$(this).find('input:text:eq(5)').attr('id','contactNumber' + i);
		$(this).find('input:text:eq(6)').attr('id','email' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].commodityName");
		$(this).find("input:text:eq(2)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].storageName");
		$(this).find("input:text:eq(3)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].storageAddress");
		$(this).find("input:text:eq(4)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].storageAdminName");
		$(this).find("input:text:eq(3)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].contactNumber");
		$(this).find("input:text:eq(4)").attr("name", "dto.strorageInfomartionDTOs[" + i + "].email");

		$("#sNo" + i).val(i + 1);



	});
}

function validateStorageInfoTable() {
	var errorList = [];
	var rowCount = $('#storageInfoTable tr').length;	


	if (errorList == 0)
		$("#storageInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var commodityName = $("#commodityName" + i).val();
				var storageName = $("#storageName" + i).val();
				var storageAddress = $("#storageAddress" + i).val();

				var constant = 1;
			}
			else{
				var commodityName = $("#commodityName" + i).val();
				var storageName = $("#storageName" + i).val();
				var storageAddress = $("#storageAddress" + i).val();
				var constant = i+1;
			}


			if (commodityName == undefined || commodityName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityName") + " " + (i + 1));
			}

			if (storageName == undefined || storageName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.storageName") + " " + (i + 1));
			}
			if (storageAddress == undefined || storageAddress == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.storageAddress") + " " + (i + 1));
			}

		});

	return errorList;
}

function addCHCenterButton(obj) {
	var errorList = [];
	errorList = validateCHCenterTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#customHiringCenterInfoTable tr').last().clone();
		$('#customHiringCenterInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);


		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeCHCenterTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCHCenterDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#customHiringCenterInfoTable tr').length;
	if ($("#customHiringCenterInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCHCenterTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCHCenterTable() {
	$("#customHiringCenterInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','equipmentName' + i);
		$(this).find('input:text:eq(2)').attr('id','noOfEquipment' + i);
		$(this).find('input:text:eq(3)').attr('id','priceOfEquipment' + i);
		$(this).find('input:text:eq(4)').attr('id','equipmentDesc' + i);


		$(this).find("input:text:eq(0)").attr("name", "dto.equipmentInfoDtos[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.equipmentInfoDtos[" + i + "].equipmentName");
		$(this).find("input:text:eq(2)").attr("name", "dto.equipmentInfoDtos[" + i + "].noOfEquipment");
		$(this).find("input:text:eq(3)").attr("name", "dto.equipmentInfoDtos[" + i + "].priceOfEquipment");
		$(this).find("input:text:eq(4)").attr("name", "dto.equipmentInfoDtos[" + i + "].equipmentDesc");

		$("#sNo" + i).val(i + 1);



	});
}

function validateCHCenterTable() {
	var errorList = [];
	var rowCount = $('#customHiringCenterInfoTable tr').length;	


	if (errorList == 0)
		$("#customHiringCenterInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var equipmentName = $("#equipmentName" + i).val();
				var noOfEquipment = $("#noOfEquipment" + i).val();
				var priceOfEquipment = $("#priceOfEquipment" + i).val();
				var constant = 1;
			}
			else{

				var equipmentName = $("#equipmentName" + i).val();
				var noOfEquipment = $("#noOfEquipment" + i).val();
				var priceOfEquipment = $("#priceOfEquipment" + i).val();
				var constant = i+1;
			}


			if (equipmentName == undefined || equipmentName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.equipmentName") + " " + (i + 1));
			}

			if (noOfEquipment == undefined || noOfEquipment == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.noOfEquipment") + " " + (i + 1));
			}
			if (priceOfEquipment == undefined || priceOfEquipment == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.priceOfEquipment") + " " + (i + 1));
			}


		});

	return errorList;
}

function addCHServiceButton(obj) {
	var errorList = [];
	errorList = validateCHServiceTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#customHiringServiceInfoTable tr').last().clone();
		$('#customHiringServiceInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);


		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeCHServiceTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCHServiceDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#customHiringServiceInfoTable tr').length;
	if ($("#customHiringServiceInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCHServiceTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCHServiceTable() {
	$("#customHiringServiceInfoTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNoS" + (i));

		$(this).find('input:text:eq(1)').attr('id','rentedItemName' + i);
		$(this).find('input:text:eq(2)').attr('id','itemQuantity' + i);
		$(this).find('input:text:eq(3)').attr('id','rentedFromDate' + i);
		$(this).find('input:text:eq(4)').attr('id','rentedToDate' + i);


		$(this).find("input:text:eq(0)").attr("name", "dto.customHiringServiceInfoDTOs[" + i + "].sNoS");
		$(this).find("input:text:eq(1)").attr("name", "dto.customHiringServiceInfoDTOs[" + i + "].rentedItemName");
		$(this).find("input:text:eq(2)").attr("name", "dto.customHiringServiceInfoDTOs[" + i + "].itemQuantity");
		$(this).find("input:text:eq(3)").attr("name", "dto.customHiringServiceInfoDTOs[" + i + "].rentedFromDate");
		$(this).find("input:text:eq(4)").attr("name", "dto.customHiringServiceInfoDTOs[" + i + "].rentedToDate");

		$("#sNoS" + i).val(i + 1);



	});
}

function validateCHServiceTable() {
	var errorList = [];
	var rowCount = $('#customHiringServiceInfoTable tr').length;	


	if (errorList == 0)
		$("#customHiringServiceInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var rentedItemName = $("#rentedItemName" + i).val();
				var itemQuantity = $("#itemQuantity" + i).val();
				var rentedFromDate = $("#rentedFromDate" + i).val();
				var rentedToDate = $("#rentedToDate" + i).val();
				var constant = 1;
			}
			else{
				var rentedItemName = $("#rentedItemName" + i).val();
				var itemQuantity = $("#itemQuantity" + i).val();
				var rentedFromDate = $("#rentedFromDate" + i).val();
				var rentedToDate = $("#rentedToDate" + i).val();
				var constant = i+1;
			}


			if (rentedItemName == undefined || rentedItemName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.rentedItemName") + " " + (i + 1));
			}

			if (itemQuantity == undefined || itemQuantity == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.itemQuantity") + " " + (i + 1));
			}
			if (rentedFromDate == undefined || rentedFromDate == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.rentedFromDate") + " " + (i + 1));
			}

			if (rentedToDate == undefined || rentedToDate == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.rentedToDate") + " " + (i + 1));
			}


		});

	return errorList;
}


function addPDButton(obj) {
	var errorList = [];
	errorList = validatePDTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#pdInfoTable tr').last().clone();
		$('#pdInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);


		content.find('[id^="unit"]').chosen().trigger("chosen:updated");
		reordePDTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deletePDDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#pdInfoTable tr').length;
	if ($("#pdInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordePDTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordePDTable() {
	$("#pdInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','commodityNamePD' + i);
		$(this).find('input:text:eq(2)').attr('id','itemQuantityPD' + i);
		$(this).find('select:eq(0)').attr('id','unit' + i);



		$(this).find("input:text:eq(0)").attr("name", "dto.productionInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.productionInfoDTOs[" + i + "].commodityNamePD");
		$(this).find("input:text:eq(2)").attr("name", "dto.productionInfoDTOs[" + i + "].itemQuantityPD");
		$(this).find("select:eq(0)").attr("name", "dto.productionInfoDTOs[" + i + "].unit");


		$("#sNo" + i).val(i + 1);

		$('#pdInfoTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});

	});
}

function validatePDTable() {
	var errorList = [];
	var rowCount = $('#pdInfoTable tr').length;	


	if (errorList == 0)
		$("#pdInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var commodityName = $("#commodityNamePD" + i).val();
				var itemQuantity = $("#itemQuantityPD" + i).val();
				var unit = $("#unit" + i).val();

				var constant = 1;
			}
			else{
				var commodityName = $("#commodityNamePD" + i).val();
				var itemQuantity = $("#itemQuantityPD" + i).val();
				var unit = $("#unit" + i).val();
				var constant = i+1;
			}


			if (commodityName == undefined || commodityName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityName") + " " + (i + 1));
			}

			if (itemQuantity == undefined || itemQuantity == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.itemQuantity") + " " + (i + 1));
			}
			if (unit == undefined || unit == "" || unit == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.unit") + " " + (i + 1));
			}



		});

	return errorList;
}


function addSDButton(obj) {
	var errorList = [];
	errorList = validateSDTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#sdInfoTable tr').last().clone();
		$('#sdInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);


		content.find('[id^="unitSale"]').chosen().trigger("chosen:updated");
		reordeSDTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteSDDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#sdInfoTable tr').length;
	if ($("#sdInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeSDTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeSDTable() {
	$("#sdInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNoSale" + (i));

		$(this).find('input:text:eq(1)').attr('id','commodityNameSD' + i);
		$(this).find('input:text:eq(2)').attr('id','commodityQuantity' + i);
		$(this).find("select:eq(0)").attr('id','unitSale' + i);
		$(this).find('input:text:eq(4)').attr('id','commodityRate' + i);
		$(this).find('input:text:eq(5)').attr('id','revenueGenerated' + i);
		$(this).find('input:text:eq(6)').attr('id','commoditySoldPrice' + i);

		$(this).find('input:text:eq(7)').attr('id','mandiName' + i);
		$(this).find('input:text:eq(8)').attr('id','mandiAddress' + i);
		$(this).find('input:text:eq(9)').attr('id','nameOfTrader' + i);



		$(this).find("input:text:eq(0)").attr("name", "dto.salesInfoDTOs[" + i + "].sNoSale");
		$(this).find("input:text:eq(1)").attr("name", "dto.salesInfoDTOs[" + i + "].commodityNameSD");
		$(this).find("input:text:eq(2)").attr("name", "dto.salesInfoDTOs[" + i + "].commodityQuantity");
		$(this).find("select:eq(0)").attr("name", "dto.salesInfoDTOs[" + i + "].unitSale");
		$(this).find("input:text:eq(4)").attr("name", "dto.salesInfoDTOs[" + i + "].commodityRate");
		$(this).find("input:text:eq(5)").attr("name", "dto.salesInfoDTOs[" + i + "].revenueGenerated");
		$(this).find("input:text:eq(6)").attr("name", "dto.salesInfoDTOs[" + i + "].commoditySoldPrice");
		$(this).find("input:text:eq(7)").attr("name", "dto.salesInfoDTOs[" + i + "].mandiName");
		$(this).find("input:text:eq(8)").attr("name", "dto.salesInfoDTOs[" + i + "].mandiAddress");
		$(this).find("input:text:eq(9)").attr("name", "dto.salesInfoDTOs[" + i + "].nameOfTrader");


		$("#sNoSale" + i).val(i + 1);

		$('#sdInfoTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});


	});
}

function validateSDTable() {
	var errorList = [];
	var rowCount = $('#sdInfoTable tr').length;	


	if (errorList == 0)
		$("#sdInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var commodityName = $("#commodityNameSD" + i).val();
				var itemQuantity = $("#commodityQuantity" + i).val();
				var unit = $("#unitSale" + i).val();
				var commodityRate = $("#commodityRate" + i).val();
				var commoditySoldPrice = $("#commoditySoldPrice" + i).val();

				var constant = 1;
			}
			else{
				var commodityName = $("#commodityNameSD" + i).val();
				var itemQuantity = $("#commodityQuantity" + i).val();
				var unit = $("#unitSale" + i).val();
				var commodityRate = $("#commodityRate" + i).val();
				var commoditySoldPrice = $("#commoditySoldPrice" + i).val();
				var constant = i+1;
			}


			if (commodityName == undefined || commodityName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityName") + " " + (i + 1));
			}

			if (itemQuantity == undefined || itemQuantity == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityQuantity") + " " + (i + 1));
			}
			if (unit == undefined || unit == "" || unit == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.unit") + " " + (i + 1));
			}
			if (commodityRate == undefined || commodityRate == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityRate") + " " + (i + 1));
			}
			if (commoditySoldPrice == undefined || commoditySoldPrice == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commoditySoldPrice") + " " + (i + 1));
			}



		});

	return errorList;
}


function addSubsidiesButton(obj) {
	var errorList = [];
	errorList = validateSubsidiesTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#subsidiesInfoTable tr').last().clone();
		$('#subsidiesInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeSubsidiesTable();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteSubsidiesDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#subsidiesInfoTable tr').length;
	if ($("#subsidiesInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeSubsidiesTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeSubsidiesTable() {
	$("#subsidiesInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','schemeName' + i);
		$(this).find('input:text:eq(2)').attr('id','schemeAgency' + i);
		$(this).find('input:text:eq(3)').attr('id','subsidiesAmount' + i);
		$(this).find('input:text:eq(4)').attr('id','totalAmount' + i);
		$(this).find('input:text:eq(5)').attr('id','amountPaidByFarmer' + i);


		$(this).find("input:text:eq(0)").attr("name", "dto.subsidiesInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.subsidiesInfoDTOs[" + i + "].schemeName");
		$(this).find("input:text:eq(2)").attr("name", "dto.subsidiesInfoDTOs[" + i + "].schemeAgency");
		$(this).find("input:text:eq(3)").attr("name", "dto.subsidiesInfoDTOs[" + i + "].subsidiesAmount");
		$(this).find("input:text:eq(4)").attr("name", "dto.subsidiesInfoDTOs[" + i + "].totalAmount");
		$(this).find("input:text:eq(5)").attr("name", "dto.subsidiesInfoDTOs[" + i + "].amountPaidByFarmer");



		$("#sNo" + i).val(i + 1);



	});
}

function validateSubsidiesTable() {
	var errorList = [];
	var rowCount = $('#subsidiesInfoTable tr').length;	


	if (errorList == 0)
		$("#subsidiesInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var schemeName = $("#schemeName" + i).val();
				var schemeAgency = $("#schemeAgency" + i).val();
				var subsidiesAmount = $("#subsidiesAmount" + i).val();
				var totalAmount = $("#totalAmount" + i).val();
				var amountPaidByFarmer = $("#amountPaidByFarmer" + i).val();
				var constant = 1;
			}
			else{
				var schemeName = $("#schemeName" + i).val();
				var schemeAgency = $("#schemeAgency" + i).val();
				var subsidiesAmount = $("#subsidiesAmount" + i).val();
				var totalAmount = $("#totalAmount" + i).val();
				var amountPaidByFarmer = $("#amountPaidByFarmer" + i).val();
				var constant = i+1;
			}


			if (schemeName == undefined || schemeName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.schemeName") + " " + (i + 1));
			}
			if (schemeAgency == undefined || schemeAgency == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.schemeAgency") + " " + (i + 1));
			}
			if (subsidiesAmount == undefined || subsidiesAmount == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.subsidiesAmount") + " " + (i + 1));
			}
			if (totalAmount == undefined || totalAmount == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.totalAmount") + " " + (i + 1));
			}
			if (amountPaidByFarmer == undefined || amountPaidByFarmer == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.amountPaidByFarmer") + " " + (i + 1));
			}

		});

	return errorList;
}

function addPreharveshButton(obj) {
	var errorList = [];
	errorList = validatePreharveshTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#preInfoTable tr').last().clone();
		$('#preInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordePreharveshTable();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function deletePreharveshDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#preInfoTable tr').length;
	if ($("#preInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordePreharveshTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordePreharveshTable() {
	$("#preInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','storageType' + i);
		$(this).find('input:text:eq(2)').attr('id','storageCapicity' + i);
		$(this).find('input:text:eq(3)').attr('id','phpDescription' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.preHarveshInfraInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.preHarveshInfraInfoDTOs[" + i + "].storageType");
		$(this).find("input:text:eq(2)").attr("name", "dto.preHarveshInfraInfoDTOs[" + i + "].storageCapicity");
		$(this).find("input:text:eq(3)").attr("name", "dto.preHarveshInfraInfoDTOs[" + i + "].phpDescription");




		$("#sNo" + i).val(i + 1);



	});
}

function validatePreharveshTable() {
	var errorList = [];
	var rowCount = $('#preInfoTable tr').length;	


	if (errorList == 0)
		$("#preInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var storageType = $("#storageType" + i).val();
				var storageCapicity = $("#storageCapicity" + i).val();
				var constant = 1;
			}
			else{
				var storageType = $("#storageType" + i).val();
				var storageCapicity = $("#storageCapicity" + i).val();
				var constant = i+1;
			}


			if (storageType == undefined || storageType == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.storageType") + " " + (i + 1));
			}

			if (storageCapicity == undefined || storageCapicity == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.storageCapicity") + " " + (i + 1));
			}

		});

	return errorList;
}


function addPostharvestButton(obj) {
	var errorList = [];
	errorList = validatePostharvestTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#postInfoTable tr').last().clone();
		$('#postInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordePostharvestTable();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function deletePostharvestDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#postInfoTable tr').length;
	if ($("#postInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordePostharvestTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordePostharvestTable() {
	$("#postInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','storageTypePost' + i);
		$(this).find('input:text:eq(2)').attr('id','storageCapicityPost' + i);
		$(this).find('input:text:eq(3)').attr('id','processing' + i);
		$(this).find('input:text:eq(4)').attr('id','qualityAnalysis' + i);
		$(this).find('input:text:eq(5)').attr('id','phpDescriptionPost' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.postHarvestInfraInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.postHarvestInfraInfoDTOs[" + i + "].storageTypePost");
		$(this).find("input:text:eq(2)").attr("name", "dto.postHarvestInfraInfoDTOs[" + i + "].storageCapicityPost");
		$(this).find("input:text:eq(3)").attr("name", "dto.postHarvestInfraInfoDTOs[" + i + "].processing");
		$(this).find("input:text:eq(4)").attr("name", "dto.postHarvestInfraInfoDTOs[" + i + "].qualityAnalysis");
		$(this).find("input:text:eq(5)").attr("name", "dto.postHarvestInfraInfoDTOs[" + i + "].phpDescriptionPost");




		$("#sNo" + i).val(i + 1);



	});
}

function validatePostharvestTable() {
	var errorList = [];
	var rowCount = $('#postInfoTable tr').length;	


	if (errorList == 0)
		$("#postInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var storageType = $("#storageTypePost" + i).val();
				var storageCapicity = $("#storageCapicityPost" + i).val();
				var constant = 1;
			}
			else{
				var storageType = $("#storageTypePost" + i).val();
				var storageCapicity = $("#storageCapicityPost" + i).val();
				var constant = i+1;
			}


			if (storageType == undefined || storageType == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.storageType") + " " + (i + 1));
			}

			if (storageCapicity == undefined || storageCapicity == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.storageCapicity") + " " + (i + 1));
			}

		});

	return errorList;
}

function addVehicleButton(obj) {
	var errorList = [];
	errorList = validateVehicleTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#vehicleInfoTable tr').last().clone();
		$('#vehicleInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeVehicleTable();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteVehicleDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#vehicleInfoTable tr').length;
	if ($("#vehicleInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeVehicleTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeVehicleTable() {
	$("#vehicleInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','vehicleType' + i);
		$(this).find('input:text:eq(2)').attr('id','vehicleNumber' + i);
		$(this).find('input:text:eq(3)').attr('id','noOfVehicle' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.transpostVehicleInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.transpostVehicleInfoDTOs[" + i + "].vehicleType");
		$(this).find("input:text:eq(2)").attr("name", "dto.transpostVehicleInfoDTOs[" + i + "].vehicleNumber");
		$(this).find("input:text:eq(3)").attr("name", "dto.transpostVehicleInfoDTOs[" + i + "].noOfVehicle");




		$("#sNo" + i).val(i + 1);



	});
}

function validateVehicleTable() {
	var errorList = [];
	var rowCount = $('#vehicleInfoTable tr').length;	


	if (errorList == 0)
		$("#vehicleInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var vehicleType = $("#vehicleType" + i).val();
				var noOfVehicle = $("#noOfVehicle" + i).val();
				var constant = 1;
			}
			else{
				var vehicleType = $("#vehicleType" + i).val();
				var noOfVehicle = $("#noOfVehicle" + i).val();
				var constant = i+1;
			}


			if (vehicleType == undefined || vehicleType == "" || vehicleType == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.vehicleType") + " " + (i + 1));
			}

			if (noOfVehicle == undefined || noOfVehicle == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.noOfVehicle") + " " + (i + 1));
			}

		});

	return errorList;
}


function addMLButton(obj) {
	var errorList = [];
	errorList = validateMLTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#mlInfoTable tr').last().clone();
		$('#mlInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeMLTable();

	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteMLDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#mlInfoTable tr').length;
	if ($("#mlInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeMLTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeMLTable() {
	$("#mlInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','commodityNameML' + i);
		$(this).find('input:text:eq(2)').attr('id','marketPlace' + i);
		$(this).find('input:text:eq(3)').attr('id','commodityRateML' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.marketLinkageInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.marketLinkageInfoDTOs[" + i + "].commodityNameML");
		$(this).find("input:text:eq(2)").attr("name", "dto.marketLinkageInfoDTOs[" + i + "].marketPlace");
		$(this).find("input:text:eq(3)").attr("name", "dto.marketLinkageInfoDTOs[" + i + "].commodityRateML");




		$("#sNo" + i).val(i + 1);



	});
}

function validateMLTable() {
	var errorList = [];
	var rowCount = $('#mlInfoTable tr').length;	


	if (errorList == 0)
		$("#mlInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var commodityName = $("#commodityNameML" + i).val();
				var commodityRate = $("#commodityRateML" + i).val();
				var constant = 1;
			}
			else{
				var commodityName = $("#commodityNameML" + i).val();
				var commodityRate = $("#commodityRateML" + i).val();
				var constant = i+1;
			}


			if (commodityName == undefined || commodityName == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityName") + " " + (i + 1));
			}

			if (commodityRate == undefined || commodityRate == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityRate") + " " + (i + 1));
			}

		});

	return errorList;
}

function addBPButton(obj) {
	var errorList = [];
	errorList = validateBPTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#bpInfoTable tr').last().clone();
		$('#bpInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:file").val('');
		content.find("input:checkbox").prop('checked', false);

		reordeBPTable();

	} else {
		displayErrorsOnPage(errorList);
	}
}


function reordeBPTable() {
	$("#bpInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('input:text:eq(1)').attr('id','documentDescription' + i);
		$(this).find("input:file:eq(0)").attr("id", "dto.businessPlanInfoDTOs" + i + ".attachmentsBP0.uploadedDocumentPath");

		$(this).find("input:text:eq(0)").attr("name", "dto.businessPlanInfoDTOs[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.businessPlanInfoDTOs[" + i + "].documentDescription");
		$(this).find("input:file:eq(0)").attr("name", "dto.businessPlanInfoDTOs[" + i + "].attachmentsBP[0].uploadedDocumentPath");





		$("#sNo" + i).val(i + 1);



	});
}

function validateBPTable() {
	var errorList = [];
	var rowCount = $('#bpInfoTable tr').length;	


	if (errorList == 0)
		$("#bpInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var documentDescription = $("#documentDescription" + i).val();

				var constant = 1;
			}
			else{
				var documentDescription = $("#documentDescription" + i).val();
				var constant = i+1;
			}


			if (documentDescription == undefined || documentDescription == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.documentDescription") + " " + (i + 1));
			}
			/*if( document.getElementById("dto.businessPlanInfoDTOs" + i + ".attachments" + i + ".uploadedDocumentPath").files.length == 0 ){
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.docUpload") + " " + (i + 1));
				}*/


		});

	return errorList;
}



function validateABSTable() {
	var errorList = [];
	var rowCount = $('#absInfoTable tr').length;	


	if (errorList == 0)
		$("#absInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var financialYear = $("#financialYear" + i).val();

				var constant = 1;
			}
			else{
				var financialYear = $("#financialYear" + i).val();
				var constant = i+1;
			}


			if (financialYear == undefined || financialYear == "" || financialYear =="0") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.finYear") + " " + (i + 1));
			}

			/*if( document.getElementById("dto.auditedBalanceSheetInfoDetailEntities" + i + ".attachments" + i + ".uploadedDocumentPath").files.length == 0 ){
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.docUpload") + " " + (i + 1));
				}*/


		});

	return errorList;
}


function addFinButton(obj) {
	var errorList = [];
	errorList = validateFinDetailsTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#finInfoTable tr').last().clone();
		$('#finInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);

		content.find('[id^="financialYear"]').chosen().trigger("chosen:updated");
		// content.find('[id^="ifsc"]').chosen().trigger("chosen:updated");
		reordeFinDetailsTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteFinDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#finInfoTable tr').length;
	if ($("#finInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeFinDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeFinDetailsTable() {
	$("#finInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('select:eq(0)').attr('id','financialYear' + i);
		$(this).find('input:text:eq(2)').attr('id','revenue' + i);
		$(this).find('input:text:eq(3)').attr('id','businessActivities' + i);
		$(this).find('input:text:eq(4)').attr('id','noBeneficiaryFarmers' + i);
		$(this).find('input:text:eq(5)').attr('id','netProfit' + i);


		$(this).find("input:text:eq(0)").attr("name", "dto.financialInformationDto[" + i + "].sNo");

		$(this).find('select:eq(0)').attr("name", "dto.financialInformationDto[" + i + "].financialYear");
		$(this).find("input:text:eq(2)").attr("name", "dto.financialInformationDto[" + i + "].revenue");
		$(this).find("input:text:eq(3)").attr("name", "dto.financialInformationDto[" + i + "].businessActivities");
		$(this).find("input:text:eq(4)").attr("name", "dto.financialInformationDto[" + i + "].noBeneficiaryFarmers");
		$(this).find("input:text:eq(5)").attr("name", "dto.financialInformationDto[" + i + "].netProfit");


		$("#sNo" + i).val(i + 1);

		$('#finInfoDiv').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});

	});
}

function validateFinDetailsTable() {
	var errorList = [];
	var rowCount = $('#finInfoTable tr').length;	


	if (errorList == 0)
		$("#finInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var financialYear = $("#financialYear" + i).val();
				var revenue = $("#revenue" + i).val();
				var businessActivities = $("#businessActivities" + i).val();
				var noBeneficiaryFarmers = $("#noBeneficiaryFarmers" + i).val();
				var netProfit = $("#netProfit" + i).val();
				var constant = 1;
			}
			else{
				var financialYear = $("#financialYear" + i).val();
				var revenue = $("#revenue" + i).val();
				var businessActivities = $("#businessActivities" + i).val();
				var noBeneficiaryFarmers = $("#noBeneficiaryFarmers" + i).val();
				var netProfit = $("#netProfit" + i).val();
				var constant = i+1;
			}

			if (financialYear == undefined || financialYear == "" || financialYear == '0' ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.finYear") +" " + (i + 1));
			}
			if (revenue == undefined || revenue == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.revenue") + " " + (i + 1));
			}

			if (businessActivities == undefined || businessActivities == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.bussActivity") + " " + (i + 1));
			}
			if (noBeneficiaryFarmers == undefined || noBeneficiaryFarmers == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.nobf") + " " + (i + 1));
			}
			if (netProfit == undefined || netProfit == "") {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.netProfit") + " " + (i + 1));
			}

		});

	return errorList;
}


function addCreditInfoButton(obj) {
	var errorList = [];
	errorList = validateCreditInfoTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#creditInfoTable tr').last().clone();
		$('#creditInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);

		content.find('[id^="financialYearCI"]').chosen().trigger("chosen:updated");
		content.find('[id^="loanApplied"]').chosen().trigger("chosen:updated");
		content.find('[id^="LOANDISBURSED"]').chosen().trigger("chosen:updated");
		reordeCreditInfoDetailsTable();



	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCreditInfoDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#creditInfoTable tr').length;
	if ($("#creditInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCreditInfoDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCreditInfoDetailsTable() {
	$("#creditInfoTable tbody tr").each(function(i) {
		// Id


		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));

		$(this).find('select:eq(0)').attr('id','financialYearCI' + i);
		$(this).find('select:eq(1)').attr('id','loanApplied' + i);
		$(this).find('input:text:eq(3)').attr('id','AmtOfLoanApplied' + i);
		$(this).find('input:text:eq(4)').attr('id','PurposeOfLoanApplied' + i);
		$(this).find('input:text:eq(5)').attr('id','institutionAvailed' + i);
		$(this).find('input:text:eq(6)').attr('id','LoanSanctioned' + i);
		$(this).find('select:eq(2)').attr('id','LOANDISBURSED' + i);
		$(this).find('input:text:eq(8)').attr('id','loanAmountDisbursed' + i);
		$(this).find('input:text:eq(9)').attr('id','utilizationOfLoan' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.creditInformationDetEntities[" + i + "].sNo");
		$(this).find('select:eq(0)').attr("name", "dto.creditInformationDetEntities[" + i + "].financialYear");
		$(this).find('select:eq(1)').attr("name", "dto.creditInformationDetEntities[" + i + "].loanApplied");

		$(this).find("input:text:eq(3)").attr("name", "dto.creditInformationDetEntities[" + i + "].AmtOfLoanApplied");
		$(this).find("input:text:eq(4)").attr("name", "dto.creditInformationDetEntities[" + i + "].PurposeOfLoanApplied");
		$(this).find("input:text:eq(5)").attr("name", "dto.creditInformationDetEntities[" + i + "].institutionAvailed");
		$(this).find("input:text:eq(6)").attr("name", "dto.creditInformationDetEntities[" + i + "].LoanSanctioned");
		$(this).find('select:eq(2)').attr("name", "dto.creditInformationDetEntities[" + i + "].LOANDISBURSED");
		$(this).find("input:text:eq(8)").attr("name", "dto.creditInformationDetEntities[" + i + "].loanAmountDisbursed");
		$(this).find("input:text:eq(9)").attr("name", "dto.creditInformationDetEntities[" + i + "].utilizationOfLoan");


		$("#sNo" + i).val(i + 1);
		$('#creditInfoDiv').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});


	});
}

function validateCreditInfoTable() {
	var errorList = [];
	var rowCount = $('#creditInfoTable tr').length;	


	if (errorList == 0)
		$("#creditInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var financialYear = $("#financialYearCI" + i).val();
				var loanApplied = $("#loanApplied" + i).val();
				var loanAppliedTxt = $("#loanApplied" + i ).find(':selected').text();
				var AmtOfLoanApplied = $("#AmtOfLoanApplied" + i).val();
				var PurposeOfLoanApplied = $("#PurposeOfLoanApplied" + i).val();
				var institutionAvailed = $("#institutionAvailed" + i).val();

				var LoanSanctioned = $("#LoanSanctioned" + i).val();
				var LOANDISBURSED = $("#LOANDISBURSED" + i).val();
				var LOANDISBURSEDTxt = $("#LOANDISBURSED" + i).find(':selected').text();
				var loanAmountDisbursed = $("#loanAmountDisbursed" + i).val();
				var utilizationOfLoan = $("#utilizationOfLoan" + i).val();
				var constant = 1;
			}
			else{
				var financialYear = $("#financialYearCI" + i).val();
				var loanApplied = $("#loanApplied" + i).val();
				var loanAppliedTxt = $("#loanApplied" + i ).find(':selected').text();
				var AmtOfLoanApplied = $("#AmtOfLoanApplied" + i).val();
				var PurposeOfLoanApplied = $("#PurposeOfLoanApplied" + i).val();
				var institutionAvailed = $("#institutionAvailed" + i).val();

				var LoanSanctioned = $("#LoanSanctioned" + i).val();
				var LOANDISBURSED = $("#LOANDISBURSED" + i).val();
				var LOANDISBURSEDTxt = $("#LOANDISBURSED" + i).find(':selected').text();
				var loanAmountDisbursed = $("#loanAmountDisbursed" + i).val();
				var utilizationOfLoan = $("#utilizationOfLoan" + i).val();
				var constant = i+1;
			}

			if (financialYear == undefined || financialYear == "" || financialYear == '0' ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.finYear") +" " + (i + 1));
			}


			if (loanApplied == undefined || loanApplied == "" || loanApplied == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.loanApplied") + " " + (i + 1));
			} if (loanAppliedTxt == 'Yes'){
				if (AmtOfLoanApplied == undefined || AmtOfLoanApplied == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.AmtOfLoanApplied") + " " + (i + 1));
				}
				if (PurposeOfLoanApplied == undefined || PurposeOfLoanApplied == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.PurposeOfLoanApplied") + " " + (i + 1));
				}
				if (institutionAvailed == undefined || institutionAvailed == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.institutionAvailed") + " " + (i + 1));
				}
				if (LoanSanctioned == undefined || LoanSanctioned == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.LoanSanctioned") + " " + (i + 1));
				}
			}

			if (LOANDISBURSED == undefined || LOANDISBURSED == "" || LOANDISBURSED == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.LOANDISBURSED") + " " + (i + 1));
			}
			if(LOANDISBURSEDTxt == 'Yes'){
				if (loanAmountDisbursed == undefined || loanAmountDisbursed == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.loanAmountDisbursed") + " " + (i + 1));
				}
				if (utilizationOfLoan == undefined || utilizationOfLoan == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.utilizationOfLoan") + " " + (i + 1));
				}

			}

		});

	return errorList;
}

function addEquityInfoButton(obj) {
	var errorList = [];
	errorList = validateEquityInfoTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#equityInfoTable tr').last().clone();
		$('#equityInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);

		content.find('[id^="isEquityGrantApplied"]').chosen().trigger("chosen:updated");
		content.find('[id^="isEquityGrantRelease"]').chosen().trigger("chosen:updated");

		reordeEquityInfoDetailsTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteEquityInfoDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#equityInfoTable tr').length;
	if ($("#equityInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeEquityInfoDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeEquityInfoDetailsTable() {
	$("#equityInfoTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('input:text:eq(1)').attr('id','authSharedCapital' + i);
		$(this).find('input:text:eq(2)').attr('id','totalEquityAmount' + i);
		$(this).find('select:eq(0)').attr('id','isEquityGrantApplied' + i);


		$(this).find('input:text:eq(4)').attr('id','dateOfApplication' + i);
		$(this).find('input:text:eq(5)').attr('id','amountOfEquityGrantApplied' + i);
		$(this).find('select:eq(1)').attr('id','isEquityGrantRelease' + i);
		$(this).find('input:text:eq(7)').attr('id','amountOfEquityGrantRelease' + i);

		$(this).find('input:text:eq(8)').attr('id','dateOfEquityRelease' + i);
		$(this).find('input:text:eq(9)').attr('id','addShareIssueByFPO' + i);
		$(this).find('input:text:eq(10)').attr('id','dateOfAddItionalShareByFPO' + i);
		$(this).find('input:text:eq(11)').attr('id','utilisationEquityGrant' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.equityInformationDetDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.equityInformationDetDto[" + i + "].authSharedCapital");
		$(this).find("input:text:eq(2)").attr("name", "dto.equityInformationDetDto[" + i + "].totalEquityAmount");
		$(this).find('select:eq(0)').attr("name", "dto.equityInformationDetDto[" + i + "].isEquityGrantApplied");


		$(this).find("input:text:eq(4)").attr("name", "dto.equityInformationDetDto[" + i + "].dateOfApplication");
		$(this).find("input:text:eq(5)").attr("name", "dto.equityInformationDetDto[" + i + "].amountOfEquityGrantApplied");

		$(this).find('select:eq(1)').attr("name", "dto.equityInformationDetDto[" + i + "].isEquityGrantRelease");

		$(this).find("input:text:eq(7)").attr("name", "dto.equityInformationDetDto[" + i + "].amountOfEquityGrantRelease");
		$(this).find("input:text:eq(8)").attr("name", "dto.equityInformationDetDto[" + i + "].dateOfEquityRelease");
		$(this).find("input:text:eq(9)").attr("name", "dto.equityInformationDetDto[" + i + "].addShareIssueByFPO");

		$(this).find("input:text:eq(10)").attr("name", "dto.equityInformationDetDto[" + i + "].dateOfAddItionalShareByFPO");
		$(this).find("input:text:eq(11)").attr("name", "dto.equityInformationDetDto[" + i + "].utilisationEquityGrant");


		$("#sNo" + i).val(i + 1);
		$('#equityInfoDiv').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});


	});
}

function validateEquityInfoTable() {
	var errorList = [];
	var rowCount = $('#equityInfoTable tr').length;	


	if (errorList == 0)
		$("#equityInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var authSharedCapital = $("#authSharedCapital" + i).val();
				var totalEquityAmount = $("#totalEquityAmount" + i).val();
				var isEquityGrantApplied = $("#isEquityGrantApplied" + i).val();
				var isEquityGrantAppliedTxt = $("#isEquityGrantApplied" + i ).find(':selected').text();
				var dateOfApplication = $("#dateOfApplication" + i).val();
				var amountOfEquityGrantApplied = $("#amountOfEquityGrantApplied" + i).val();
				var isEquityGrantRelease = $("#isEquityGrantRelease" + i).val();
				var isEquityGrantReleaseTxt = $("#isEquityGrantRelease" + i).find(':selected').text();
				var amountOfEquityGrantRelease = $("#amountOfEquityGrantRelease" + i).val();

				var dateOfEquityRelease = $("#dateOfEquityRelease" + i).val();

				var addShareIssueByFPO = $("#addShareIssueByFPO" + i).val();
				var dateOfAddItionalShareByFPO = $("#dateOfAddItionalShareByFPO" + i).val();

				var utilisationEquityGrant = $("#utilisationEquityGrant" + i).val();
				var constant = 1;
			}
			else{
				var authSharedCapital = $("#authSharedCapital" + i).val();
				var totalEquityAmount = $("#totalEquityAmount" + i).val();
				var isEquityGrantApplied = $("#isEquityGrantApplied" + i).val();
				var isEquityGrantAppliedTxt = $("#isEquityGrantApplied" + i ).find(':selected').text();
				var dateOfApplication = $("#dateOfApplication" + i).val();
				var amountOfEquityGrantApplied = $("#amountOfEquityGrantApplied" + i).val();
				var isEquityGrantRelease = $("#isEquityGrantRelease" + i).val();
				var isEquityGrantReleaseTxt = $("#isEquityGrantRelease" + i).find(':selected').text();
				var amountOfEquityGrantRelease = $("#amountOfEquityGrantRelease" + i).val();

				var dateOfEquityRelease = $("#dateOfEquityRelease" + i).val();

				var addShareIssueByFPO = $("#addShareIssueByFPO" + i).val();
				var dateOfAddItionalShareByFPO = $("#dateOfAddItionalShareByFPO" + i).val();

				var utilisationEquityGrant = $("#utilisationEquityGrant" + i).val();
				var constant = i+1;
			}




			if (authSharedCapital == undefined || authSharedCapital == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.authSharedCapital") + " " + (i + 1));
			}if (totalEquityAmount == undefined || totalEquityAmount == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.totalEquityAmount") + " " + (i + 1));
			}
			if (isEquityGrantApplied == undefined || isEquityGrantApplied == "" || isEquityGrantApplied == '0' ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.isEquityGrantApplied") +" " + (i + 1));
			}


			if (isEquityGrantAppliedTxt == 'Yes'){
				if (dateOfApplication == undefined || dateOfApplication == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.dateOfApplication") + " " + (i + 1));
				}
				if (amountOfEquityGrantApplied == undefined || amountOfEquityGrantApplied == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.amountOfEquityGrantApplied") + " " + (i + 1));
				}

			}

			if (isEquityGrantRelease == undefined || isEquityGrantRelease == "" || isEquityGrantRelease == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.isEquityGrantRelease") + " " + (i + 1));
			}
			if(isEquityGrantReleaseTxt == 'Yes'){
				if (amountOfEquityGrantRelease == undefined || amountOfEquityGrantRelease == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.amountOfEquityGrantRelease") + " " + (i + 1));
				}
				if (dateOfEquityRelease == undefined || dateOfEquityRelease == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.dateOfEquityRelease") + " " + (i + 1));
				}
				if (utilisationEquityGrant == undefined || utilisationEquityGrant == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.utilisationEquityGrant") + " " + (i + 1));
				}

			}

		});

	return errorList;
}


function addFarmerSummaryButton(obj) {
	var errorList = [];
	errorList = validateFarmerSummaryTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#FarmerSummaryInfoTable tr').last().clone();
		$('#FarmerSummaryInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);



		reordeFarmerSummaryDetailsTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteFarmerSummaryDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#FarmerSummaryInfoTable tr').length;
	if ($("#FarmerSummaryInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeFarmerSummaryDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeFarmerSummaryDetailsTable() {
	$("#FarmerSummaryInfoTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('input:text:eq(1)').attr('id','dateOfEntry' + i);
		$(this).find('input:text:eq(2)').attr('id','noOFSmallFarmerSH' + i);


		$(this).find('input:text:eq(3)').attr('id','noOFMarginalFarmerSH' + i);
		$(this).find('input:text:eq(4)').attr('id','noOFLandlessSH' + i);
		$(this).find('input:text:eq(5)').attr('id','noOFTenantFarmer' + i);
		$(this).find('input:text:eq(6)').attr('id','totalSharehold' + i);

		$(this).find('input:text:eq(7)').attr('id','noOFWomenSH' + i);
		$(this).find('input:text:eq(8)').attr('id','noOFSCSH' + i);
		$(this).find('input:text:eq(9)').attr('id','noOFSTSH' + i);


		$(this).find("input:text:eq(0)").attr("name", "dto.farmerSummaryDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.farmerSummaryDto[" + i + "].dateOfEntry");
		$(this).find("input:text:eq(2)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFSmallFarmerSH");


		$(this).find("input:text:eq(3)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFMarginalFarmerSH");
		$(this).find("input:text:eq(4)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFLandlessSH");

		$(this).find("input:text:eq(5)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFTenantFarmer");
		$(this).find("input:text:eq(6)").attr("name", "dto.farmerSummaryDto[" + i + "].totalSharehold");
		$(this).find("input:text:eq(7)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFWomenSH");

		$(this).find("input:text:eq(8)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFSCSH");
		$(this).find("input:text:eq(9)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFSTSH");


		$("#sNo" + i).val(i + 1);



	});
}

function validateFarmerSummaryTable() {
	var errorList = [];
	var rowCount = $('#FarmerSummaryInfoTable tr').length;	


	if (errorList == 0)
		$("#FarmerSummaryInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var dateOfEntry = $("#dateOfEntry" + i).val();

				var constant = 1;
			}
			else{
				var dateOfEntry = $("#dateOfEntry" + i).val();
				var constant = i+1;
			}




			if (dateOfEntry == undefined || dateOfEntry == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.dateOfEntry") + " " + (i + 1));
			}

		});

	return errorList;
}



function addManagementCostButton(obj) {
	var errorList = [];
	errorList = validateManagementCostTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#ManagementCostTable tr').last().clone();
		$('#ManagementCostTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);



		reordeManagementCostDetailsTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteManagementCostDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#ManagementCostTable tr').length;
	if ($("#ManagementCostTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeManagementCostDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeManagementCostDetailsTable() {
	$("#ManagementCostTable tbody tr").each(function(i) {
		// Id
		$('.datepicker').removeClass("hasDatepicker");

		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('input:text:eq(1)').attr('id','managementCostDisbursed' + i);
		$(this).find('input:text:eq(2)').attr('id','dateOfRelease' + i);


		$(this).find('input:text:eq(3)').attr('id','amountRelease' + i);
		$(this).find('input:text:eq(4)').attr('id','cbboCostDisbursed' + i);
		$(this).find('input:text:eq(5)').attr('id','cbboDateOfRelease' + i);
		$(this).find('input:text:eq(6)').attr('id','cbbodAmountRelease' + i);




		$(this).find("input:text:eq(0)").attr("name", "dto.managementCostDetailDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.managementCostDetailDto[" + i + "].managementCostDisbursed");
		$(this).find("input:text:eq(2)").attr("name", "dto.managementCostDetailDto[" + i + "].dateOfRelease");


		$(this).find("input:text:eq(3)").attr("name", "dto.managementCostDetailDto[" + i + "].amountRelease");
		$(this).find("input:text:eq(4)").attr("name", "dto.managementCostDetailDto[" + i + "].cbboCostDisbursed");

		$(this).find("input:text:eq(5)").attr("name", "dto.managementCostDetailDto[" + i + "].cbboDateOfRelease");
		$(this).find("input:text:eq(6)").attr("name", "dto.managementCostDetailDto[" + i + "].cbbodAmountRelease");



		$("#sNo" + i).val(i + 1);



	});
}

function validateManagementCostTable() {
	var errorList = [];
	var rowCount = $('#ManagementCostTable tr').length;	


	if (errorList == 0)
		$("#ManagementCostTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var managementCostDisbursed = $("#managementCostDisbursed" + i).val();
				var dateOfRelease = $("#dateOfRelease" + i).val();
				var amountRelease = $("#amountRelease" + i).val();
				var cbboCostDisbursed = $("#cbboCostDisbursed" + i).val();
				var cbboDateOfRelease = $("#cbboDateOfRelease" + i).val();
				var cbbodAmountRelease = $("#cbbodAmountRelease" + i).val();

				var constant = 1;
			}
			else{
				var managementCostDisbursed = $("#managementCostDisbursed" + i).val();
				var dateOfRelease = $("#dateOfRelease" + i).val();
				var amountRelease = $("#amountRelease" + i).val();
				var cbboCostDisbursed = $("#cbboCostDisbursed" + i).val();
				var cbboDateOfRelease = $("#cbboDateOfRelease" + i).val();
				var cbbodAmountRelease = $("#cbbodAmountRelease" + i).val();
				var constant = i+1;
			}




			if ((managementCostDisbursed == undefined || managementCostDisbursed == "" )&& (cbboCostDisbursed == undefined || cbboCostDisbursed == "") ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.atCost") + " " + (i + 1));
			}

			if (managementCostDisbursed != "") {
				if (dateOfRelease == "" || dateOfRelease == undefined) {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.dateOfRelease") + " " + (i + 1));
				}
				if (amountRelease == "" || amountRelease == undefined) {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.amountRelease") + " " + (i + 1));
				}
			}

			if (cbboCostDisbursed != "" ) {
				if (cbboDateOfRelease == "" || cbboDateOfRelease == undefined) {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.cbboDateOfRelease") + " " + (i + 1));
				}
				if (cbbodAmountRelease == "" || cbbodAmountRelease == undefined) {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.cbbodAmountRelease") + " " + (i + 1));
				}
			}

		});

	return errorList;
}

function addCreditGrandButton(obj) {
	var errorList = [];
	errorList = validateCreditGrandTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#CreditGrantTable tr').last().clone();
		$('#CreditGrantTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);

		content.find('[id^="cgfAvailed"]').chosen().trigger("chosen:updated");


		reordeCreditGrandDetailsTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteCreditGrandDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#CreditGrantTable tr').length;
	if ($("#CreditGrantTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeCreditGrandDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeCreditGrandDetailsTable() {
	$("#CreditGrantTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('input:text:eq(1)').attr('id','dateOfCGF' + i);
		$(this).find('select:eq(0)').attr('id','cgfAvailed' + i);


		$(this).find('input:text:eq(3)').attr('id','amountOfCGF' + i);
		$(this).find('input:text:eq(4)').attr('id','actCGF' + i);

		$(this).find('input:text:eq(5)').attr('id','totalCovrageCGF' + i);



		$(this).find("input:text:eq(0)").attr("name", "dto.creditGrantDetailDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.creditGrantDetailDto[" + i + "].dateOfCGF");

		$(this).find('select:eq(0)').attr("name", "dto.creditGrantDetailDto[" + i + "].cgfAvailed");


		$(this).find("input:text:eq(3)").attr("name", "dto.creditGrantDetailDto[" + i + "].amountOfCGF");
		$(this).find("input:text:eq(4)").attr("name", "dto.creditGrantDetailDto[" + i + "].actCGF");


		$(this).find("input:text:eq(5)").attr("name", "dto.creditGrantDetailDto[" + i + "].totalCovrageCGF");



		$("#sNo" + i).val(i + 1);
		$('#CreditGrantDiv').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});


	});
}

function validateCreditGrandTable() {
	var errorList = [];
	var rowCount = $('#CreditGrantTable tr').length;	


	if (errorList == 0)
		$("#CreditGrantTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var dateOfCGF = $("#dateOfCGF" + i).val();

				var cgfAvailed = $("#cgfAvailed" + i).val();
				var cgfAvailedTxt = $("#cgfAvailed" + i ).find(':selected').text();

				var amountOfCGF = $("#amountOfCGF" + i).val();
				var actCGF = $("#actCGF" + i).val();
				var totalCovrageCGF = $("#totalCovrageCGF" + i).val();
				var constant = 1;
			}
			else{
				var dateOfCGF = $("#dateOfCGF" + i).val();

				var cgfAvailed = $("#cgfAvailed" + i).val();
				var cgfAvailedTxt = $("#cgfAvailed" + i ).find(':selected').text();

				var amountOfCGF = $("#amountOfCGF" + i).val();
				var actCGF = $("#actCGF" + i).val();
				var totalCovrageCGF = $("#totalCovrageCGF" + i).val();
				var constant = i+1;
			}





			if (cgfAvailed == undefined || cgfAvailed == "" || cgfAvailed == '0') {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.cgfAvailed") + " " + (i + 1));
			}
			if (cgfAvailedTxt == 'Yes'){
				if (dateOfCGF == undefined || dateOfCGF == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.dateOfCGF") + " " + (i + 1));
				}
				if (amountOfCGF == undefined || amountOfCGF == "") {
					errorList.push(getLocalMessage("sfac.fpo.pm.validation.amountOfCGF") + " " + (i + 1));
				}

			}



		});

	return errorList;
}


function addDPRButton(obj) {
	var errorList = [];
	errorList = validateDPRInfoTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#dprInfoTable tr').last().clone();
		$('#dprInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);



		reordeDPRInfoDetailsTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteDPRDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#dprInfoTable tr').length;
	if ($("#dprInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeDPRInfoDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeDPRInfoDetailsTable() {
	$("#dprInfoTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('input:text:eq(1)').attr('id','dprRecDt' + i);
		$(this).find('input:text:eq(2)').attr('id','dprReviewer' + i);
		$(this).find('input:text:eq(3)').attr('id','dprScore' + i);
		$(this).find('input:text:eq(4)').attr('id','dprRevSubmDt' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.farmerSummaryDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.farmerSummaryDto[" + i + "].dprRecDt");
		$(this).find("input:text:eq(2)").attr("name", "dto.farmerSummaryDto[" + i + "].dprReviewer");
		$(this).find("input:text:eq(3)").attr("name", "dto.farmerSummaryDto[" + i + "].dprScore");
		$(this).find("input:text:eq(4)").attr("name", "dto.farmerSummaryDto[" + i + "].dprRevSubmDt");


		$("#sNo" + i).val(i + 1);

	});
}

function validateDPRInfoTable() {
	var errorList = [];
	var rowCount = $('#dprInfoTable tr').length;	


	if (errorList == 0)
		$("#dprInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var dprRecDt = $("#dprRecDt" + i).val();
				var dprReviewer = $("#dprReviewer" + i).val();
				var dprScore = $("#dprScore" + i).val();
				var dprRevSubmDt = $("#dprRevSubmDt" + i).val();

				var constant = 1;
			}
			else{
				var dprRecDt = $("#dprRecDt" + i).val();
				var dprReviewer = $("#dprReviewer" + i).val();
				var dprScore = $("#dprScore" + i).val();
				var dprRevSubmDt = $("#dprRevSubmDt" + i).val();
				var constant = i+1;
			}




			if (dprRecDt == undefined || dprRecDt == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.dprRecDt") + " " + (i + 1));
			}
			if (dprReviewer == undefined || dprReviewer == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.dprReviewer") + " " + (i + 1));
			}
			if (dprScore == undefined || dprScore == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.dprScore") + " " + (i + 1));
			}
			if (dprRevSubmDt == undefined || dprRevSubmDt == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.dprRevSubmDt") + " " + (i + 1));
			}

		});

	return errorList;
}




function addTrainingInfoButton(obj) {
	var errorList = [];
	errorList = validateTrainingInfoTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#TrainingInfoTable tr').last().clone();
		$('#TrainingInfoTable tr').last().after(content);
		content.find("select").val('0');
		content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);



		reordeTrainingInfoDetailsTable();
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200",
			maxDate: 0

		});


	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteTrainingInfoDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#TrainingInfoTable tr').length;
	if ($("#TrainingInfoTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeTrainingInfoDetailsTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeTrainingInfoDetailsTable() {
	$("#TrainingInfoTable tbody tr").each(function(i) {
		// Id

		$('.datepicker').removeClass("hasDatepicker");
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find('input:text:eq(1)').attr('id','dateOfTraining' + i);
		$(this).find('input:text:eq(2)').attr('id','noOFCBO' + i);
		$(this).find('input:text:eq(3)').attr('id','noOFBODSInvolved' + i);
		$(this).find('input:text:eq(4)').attr('id','noOFBODSConduct' + i);
		$(this).find('input:text:eq(5)').attr('id','noOFAccountantTraningConduct' + i);
		$(this).find('input:text:eq(6)').attr('id','noOFTrainingCompleteFPO' + i);
		$(this).find('input:text:eq(7)').attr('id','noOFSHFTraining' + i);

		$(this).find("input:text:eq(0)").attr("name", "dto.farmerSummaryDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.farmerSummaryDto[" + i + "].dateOfTraining");
		$(this).find("input:text:eq(2)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFCBO");
		$(this).find("input:text:eq(3)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFBODSInvolved");
		$(this).find("input:text:eq(4)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFBODSConduct");
		$(this).find("input:text:eq(5)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFAccountantTraningConduct");
		$(this).find("input:text:eq(6)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFTrainingCompleteFPO");
		$(this).find("input:text:eq(7)").attr("name", "dto.farmerSummaryDto[" + i + "].noOFSHFTraining");

		$("#sNo" + i).val(i + 1);

	});
}

function validateTrainingInfoTable() {
	var errorList = [];
	var rowCount = $('#TrainingInfoTable tr').length;	


	if (errorList == 0)
		$("#TrainingInfoTable tbody tr").each(function(i) {
			if(rowCount <= 3){

				var dateOfTraining = $("#dateOfTraining" + i).val();

				var constant = 1;
			}
			else{
				var dateOfTraining = $("#dateOfTraining" + i).val();
				var constant = i+1;
			}




			if (dateOfTraining == undefined || dateOfTraining == "" ) {
				errorList.push(getLocalMessage("sfac.fpo.pm.validation.dateOfTraining") + " " + (i + 1));
			}

		});

	return errorList;
}


function saveCreditGrandForm(element){

	var errorList = []
	//errorList = validateFinDetailsTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#creditGrantInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#storageInfo';


		var mode = $("#modeType").val();
		var elementData = null;
		var fpmid = $('#fpmId').val();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveCreditGrandForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//	manageDependentTabs();
				console.log("removed disabled");
			}
			//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#storageInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}



}

function saveManagementInfoForm(element){


	var errorList = []
	//errorList = validateFinDetailsTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#mgmtInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#creditGrantInfo';


		var mode = $("#modeType").val();
		var elementData = null;
		var fpmid = $('#fpmId').val();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveProfileManagementForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//	manageDependentTabs();
				console.log("removed disabled");
			}
			//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#creditGrantInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}




}

function saveFPOPMFarmerSummaryForm(element){

	var errorList = []
	//errorList = validateFinDetailsTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#farmerSummaryInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#mgmtInfo';


		var mode = $("#modeType").val();
		var elementData = null;
		var fpmid = $('#fpmId').val();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveProfileFarmerSummaryForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//	manageDependentTabs();
				console.log("removed disabled");
			}
			//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#mgmtInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}



}

function saveEquityInfoForm(element){
	var errorList = []
	//errorList = validateFinDetailsTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#equityInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#farmerSummaryInfo';


		var mode = $("#modeType").val();
		var elementData = null;
		var fpmid = $('#fpmId').val();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveEquityInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//	manageDependentTabs();
				console.log("removed disabled");
			}
			//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#farmerSummaryInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}

}

function saveCreditInfoForm(element){



	var errorList = []
	//errorList = validateFinDetailsTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#creditInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#equityInfo';


		var mode = $("#modeType").val();
		var elementData = null;
		var fpmid = $('#fpmId').val();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveCreditInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//	manageDependentTabs();
				console.log("removed disabled");
			}
			//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#equityInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}

}

function saveLicenseInfoForm(element){


	var errorList = []
	//errorList = validateFinDetailsTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#licenseInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#creditInfo';


		var mode = $("#modeType").val();
		var elementData = null;
		var fpmid = $('#fpmId').val();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveLicenseInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//	manageDependentTabs();
				console.log("removed disabled");
			}
			//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#creditInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}


}

function validateMstWithFInTable(){
	var errorList = [];



	if (errorList == 0){

		var commodityName = $("#CommodityNameMst").val();
		var commodityQuanityMst = $("#commodityQuanityMst").val();

		if (commodityName == undefined || commodityName == "") {
			errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityName"));
		}

		if (commodityQuanityMst == undefined || commodityQuanityMst == "") {
			errorList.push(getLocalMessage("sfac.fpo.pm.validation.commodityQuantity"));
		}

	}

	return errorList;
}

function saveFPOProfileMasterForm(element){
	var errorList = []
	errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$(".error-div").hide();
		

		var divName = '#finInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#licenseInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveFPOProfileMasterPage', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$(parentTab ).find('a').attr('data-toggle', 'tab');

			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/
			$('#license-tab').find('a').attr('href', '#licenseInfo');
			$(''+parentTab+' a[href="#licenseInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function saveStorageInfoForm(element){
	var errorList = []
//	errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		


		var divName = '#storageInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#customInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveStorageInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#customInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function saveCustomHiringInfoForm(element){
	var errorList = []
//	errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();


		var divName = '#customInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#pnsInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveCustomInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#pnsInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function savePNSInfoForm(element){
	var errorList = []
//	errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();


		var divName = '#pnsInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#subsidiesInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?savePNSInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#subsidiesInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}


function saveSubsidiesInfoForm(element){
	var errorList = []
//	errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#subsidiesInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#postharvestInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveSubsidiesInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#postharvestInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function savePreharveshInfoForm(element){
	var errorList = []
	//errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#preharveshInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#postharvestInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?savePreharveshInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#postharvestInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function savePostharvestInfoForm(element){
	var errorList = []
//	errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#postharvestInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#transportInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?savePostharvestInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#transportInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function saveTransportVehicleInfoForm(element){
	var errorList = []
	//errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#transportInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#mlInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveTransportVehicleInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#mlInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function saveMarketLinkageForm(element){
	var errorList = []
	//errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#mlInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#bpInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveMLInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#bpInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}


function saveBPInfoForm(element){
	var errorList = []
	//errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#bpInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#absInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveBPInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#absInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function saveABSInfoForm(element){
	var errorList = []
	//errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#absInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#dprInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveABSInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#dprInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}


function saveDPRInfoForm(element){
	var errorList = []
	//errorList = validateMstWithFInTable();

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();;


		var divName = '#dprInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

		targetDivName = '#traningInfo';


		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'FPOProfileManagementForm.html?saveDPRInfoForm', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		window.scrollTo(0, 0);

		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');

		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$('#fpmId').val(response);
			//#D34059
			let parentTab =  '#fpoPMParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			var disabledTab = $(parentTab).find('.disabled');

			if (disabledTab) {
				$(disabledTab).removeClass('disabled');


			}
			//
			/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/

			$(''+parentTab+' a[href="#traningInfo"]').tab('show');
			$(".error-div").hide();

			var errorPreviousTab = $(divName).find('#validationerrordiv');
			if (errorPreviousTab.length > 0) {
				var divError = $(divName).find('#validationerrordiv');
				$(divError).addClass('hide');
			}
		} else {
			$(divName).html(response);
			prepareDateTag();
		}

	}
}

function saveFPOPMTraningInfoForm(obj) {

	var errorList = [];
	var fpoAge =$("#fpoAge").val();
	//errorList = validateFinDetailsTable(errorList);
	// errorList = errorList.concat(validateCropDetailsTable());

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "FPO Profile  Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteLicenseDetails(obj, id) {

	requestData = {
			"id" : id
	};

	var row = $("#licenseEntryTable tbody .appendableLicenseDetails").length;
	if (row != 1) {



		var response = __doAjaxRequest('FPOProfileManagementForm.html?doEntryDeletionLi',
				'POST', requestData, false, 'html');
		console.log('res' + response);
		$("#licenseInfo").html(response);
		prepareTags();
	}
}

function deleteBPDetails(obj, id) {

	requestData = {
			"id" : id
	};

	var row = $("#bpInfoTable tbody .appendableBPDetails").length;
	if (row != 1) {
		var response = __doAjaxRequest('FPOProfileManagementForm.html?doEntryDeletionBP',
				'POST', requestData, false, 'html');
		$("#bpInfo").html(response);
		prepareTags();
	}
}

function deleteABSDetails(obj, id) {

	requestData = {
			"id" : id
	};

	var row = $("#absInfoTable tbody .appendableABSDetails").length;
	if (row != 1) {
		var response = __doAjaxRequest('FPOProfileManagementForm.html?doEntryDeletionABS',
				'POST', requestData, false, 'html');
		$("#absInfo").html(response);
		prepareTags();
	}
}



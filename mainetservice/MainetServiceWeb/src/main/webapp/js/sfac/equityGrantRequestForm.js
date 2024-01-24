$(document).ready(function() {

	var bakId = $('#bankId').val();
	if(bakId!='0'){
		$('#branchId').val(bakId);
		$('#accNo').val(bakId);
		$('#bankAdd').val(bakId);
		$('#ifsc').val(bakId);
	}

	$("#regIncorpFpc").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0
	});
	$("#dateBoardMeetingsLastYear").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
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
});


function getDistrictList() {
	
	var requestData = {
			"state" : $("#state").val()
	};
	var URL = 'StateInformationMaster.html?getDistrictList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#district').html('');
	$('#district').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#district').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#district').trigger("chosen:updated");
}

function getBankDetails(){

	var bakId = $('#bankId').val();
	$('#branchId').val(bakId);
	$('#accNo').val(bakId);
	$('#bankAdd').val(bakId);
	$('#ifsc').val(bakId);


}

function saveEquityGrantForm(obj){

	var errorList = [];

	errorList = validateEquityForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "FPO Equity Grand Request Saved Successfully!", 'EquityGrantRequest.html', 'saveEquityGrantRequest');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateEquityForm(errorList){

	return errorList;
}


function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('EquityGrantRequest.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];

	var fpoId = $("#fpoId").val();

	var fy = $("#status").val();
	
	var divName = '.content-page';
	if ((fpoId == "0" || fpoId == "" || fpoId == undefined)  ) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
				"fpoId" : fpoId,

				"status":fy
		};
		var ajaxResponse = doAjaxLoading('EquityGrantRequest.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}


function modifyCase(egId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode" : mode,
			"egId" : egId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getCheckList(obj) 
{

	var errorList = [];
	errorList = validateEGRData(errorList);
	
	if (errorList.length == 0)
	{
		
		return saveOrUpdateForm(obj,
				" ", "EquityGrantRequest.html",
				'getCheckList');
		
	}
	else 
	{
		displayErrorsOnPage(errorList);
	}
}

function validateEGRData(errorList){
	
	var businessofFPC = $("#businessofFPC").val();
	var noOfSMLShareholderMemb = $("#noOfSMLShareholderMemb").val();
	var amountofEquityGrant = $("#amountofEquityGrant").val();
	var maxIndShareholdMem = $("#maxIndShareholdMem").val();
	
	
	var noOfDirectors = $("#noOfDirectors" ).val();
	var womenDirectors = $("#womenDirectors").val();
	var dateBoardMeetingsLastYear = $("#dateBoardMeetingsLastYear").val();
	var modeOfBoardFormation = $("#modeOfBoardFormation").val();
	var bankId = $("#bankId").val();
	
	
	
	if (businessofFPC == undefined || businessofFPC == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.businessofFPC") );
	}
	if (noOfSMLShareholderMemb == undefined || noOfSMLShareholderMemb == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.noOfSMLShareholderMemb"));
	}
	if (amountofEquityGrant == undefined || amountofEquityGrant == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.amountofEquityGrant") );
	}
	
	if (maxIndShareholdMem == undefined || maxIndShareholdMem == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.maxIndShareholdMem") );
	}
	if (modeOfBoardFormation == undefined || modeOfBoardFormation == "" || modeOfBoardFormation == "0") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.modeOfBoardFormation") );
	}
	if (noOfDirectors == undefined || noOfDirectors == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.noOfDirectors"));
	}
	
	if (womenDirectors == undefined || womenDirectors == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.womenDirectors") );
	}
	if (dateBoardMeetingsLastYear == undefined || dateBoardMeetingsLastYear == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.dateBoardMeetingsLastYear") );
	}
	
	if (bankId == undefined || bankId == "" || bankId == "0") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.bankId") );
	}


return errorList;
}


function saveEquityGrantApprovalData(obj){
	return saveOrUpdateForm(obj, "Equity Grant Request Saved Successfully!", 'AdminHome.html', 'saveform');
}


function addBODButton(obj) {
	var errorList = [];
    errorList = validateBODTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#detailsBoardOfDirectorsTable tr').last().clone();
		$('#detailsBoardOfDirectorsTable tr').last().after(content);
		 content.find("select").val('0');
	    content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		
      
        content.find('[id^="role"]').chosen().trigger("chosen:updated");
		reordeBODTable();
	
		
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteBODDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#detailsBoardOfDirectorsTable tr').length;
	if ($("#detailsBoardOfDirectorsTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeBODTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeBODTable() {
	$("#detailsBoardOfDirectorsTable tbody tr").each(function(i) {
		// Id
		

		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
	
		$(this).find('input:text:eq(1)').attr('id','name' + i);
		$(this).find('select:eq(0)').attr('id','role' + i);
		$(this).find('input:text:eq(3)').attr('id','aadhaarNo' + i);
		$(this).find('input:text:eq(4)').attr('id','dinNo' + i);
		$(this).find('input:text:eq(5)').attr('id','qualification' + i);
		$(this).find('input:text:eq(6)').attr('id','tenure' + i);
		$(this).find('input:text:eq(7)').attr('id','contactNoAddress' + i);
		$(this).find('input:text:eq(8)').attr('id','landHolding' + i);
		$('#name'+i).removeAttr('disable');
		
		
		
		$(this).find("input:text:eq(0)").attr("name", "dto.equityGrantDetailDto[" + i + "].sNo");
		$(this).find("input:text:eq(1)").attr("name", "dto.equityGrantDetailDto[" + i + "].name");
		$(this).find("select:eq(0)").attr("name", "dto.equityGrantDetailDto[" + i + "].role");
		$(this).find("input:text:eq(3)").attr("name", "dto.equityGrantDetailDto[" + i + "].aadhaarNo");
		$(this).find("input:text:eq(4)").attr("name", "dto.equityGrantDetailDto[" + i + "].dinNo");
		$(this).find("input:text:eq(5)").attr("name", "dto.equityGrantDetailDto[" + i + "].qualification");
		$(this).find("input:text:eq(6)").attr("name", "dto.equityGrantDetailDto[" + i + "].tenure");
		$(this).find("input:text:eq(7)").attr("name", "dto.equityGrantDetailDto[" + i + "].contactNoAddress");
		$(this).find("input:text:eq(8)").attr("name", "dto.equityGrantDetailDto[" + i + "].landHolding");
		
		
		
		$("#sNo" + i).val(i + 1);
		
		$('#detailsBoardOfDirectorsTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	
	});
}

function validateBODTable() {
	var errorList = [];
	var rowCount = $('#detailsBoardOfDirectorsTable tr').length;	


	if (errorList == 0)
		$("#detailsBoardOfDirectorsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var name = $("#name" + i).val();
				var role = $("#role" + i).val();
				
				
				var constant = 1;
       }
        else{
        	var name = $("#name" + i).val();
			var role = $("#role" + i).val();
			var contactNoAddress = $("#contactNoAddress" + i).val();
			
	        var constant = i+1;
     }
		
				
				if (name == undefined || name == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.name") + " " + (i + 1));
				}
				
			
				if (role == undefined || role == "" || role == '0') {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.role") + " " + (i + 1));
				}
				
				
				
		});
	
	return errorList;
}


function addBOMButton(obj) {
	var errorList = [];
    errorList = validateBOMTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#detailsBoardOfMemberTable tr').last().clone();
		$('#detailsBoardOfMemberTable tr').last().after(content);
		 content.find("select").val('0');
	    content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		
      
        content.find('[id^="role"]').chosen().trigger("chosen:updated");
		reordeBOMTable();
	
		
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteBOMDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#detailsBoardOfMemberTable tr').length;
	if ($("#detailsBoardOfMemberTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeBOMTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeBOMTable() {
	$("#detailsBoardOfMemberTable tbody tr").each(function(i) {
		// Id
		

		$(this).find("input:text:eq(0)").attr("id", "sNoBOM" + (i));
	
		$(this).find('input:text:eq(1)').attr('id','nameBOM' + i);
		$(this).find('select:eq(0)').attr('id','roleBOM' + i);
		$(this).find('input:text:eq(3)').attr('id','aadhaarNoBOM' + i);
		$(this).find('input:text:eq(4)').attr('id','qualificationBOM' + i);
		$(this).find('input:text:eq(5)').attr('id','tenureBOM' + i);
		$(this).find('input:text:eq(6)').attr('id','contactNoAddressBOM' + i);
		$(this).find('input:text:eq(7)').attr('id','landHoldingBOM' + i);
		$('#nameBOM'+i).removeAttr('disable');
		
		
		
		$(this).find("input:text:eq(0)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].sNoBOM");
		$(this).find("input:text:eq(1)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].nameBOM");
		$(this).find("select:eq(0)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].roleBOM");
		$(this).find("input:text:eq(3)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].aadhaarNoBOM");
		$(this).find("input:text:eq(4)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].qualificationBOM");
		$(this).find("input:text:eq(5)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].tenureBOM");
		$(this).find("input:text:eq(6)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].contactNoAddressBOM");
		$(this).find("input:text:eq(7)").attr("name", "dto.equityGrantDetailDtoBOM[" + i + "].landHoldingBOM");
		
		
		
		$("#sNoBOM" + i).val(i + 1);
		
		$('#detailsBoardOfMemberTable').find('select').each(function() {
			$(this).addClass('mandColorClass');
			$(".chosen-select-no-results").trigger("chosen:updated");
		});
	
	});
}

function validateBOMTable() {
	var errorList = [];
	var rowCount = $('#detailsBoardOfMemberTable tr').length;	


	if (errorList == 0)
		$("#detailsBoardOfMemberTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var name = $("#nameBOM" + i).val();
				var role = $("#roleBOM" + i).val();
				var contactNoAddress = $("#contactNoAddressBOM" + i).val();
				
				var constant = 1;
       }
        else{
        	var name = $("#nameBOM" + i).val();
			var role = $("#roleBOM" + i).val();
			
			
	        var constant = i+1;
     }
		
				
				if (name == undefined || name == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.name") + " " + (i + 1));
				}
				
			
				if (role == undefined || role == "" || role == '0') {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.role") + " " + (i + 1));
				}
				
				
				
		});
	
	return errorList;
}



function addFCDButton(obj) {
	var errorList = [];
    errorList = validateFCDTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#detailsfcdTable tr').last().clone();
		$('#detailsfcdTable tr').last().after(content);
		 content.find("select").val('0');
	    content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		
      
        content.find('[id^="role"]').chosen().trigger("chosen:updated");
		reordeFCDTable();
	
		
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteFCDDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#detailsfcdTable tr').length;
	if ($("#detailsfcdTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeFCDTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeFCDTable() {
	$("#detailsfcdTable tbody tr").each(function(i) {
		// Id
		

		$(this).find("input:text:eq(0)").attr("id", "sNoFCD" + (i));
	
		$(this).find('input:text:eq(1)').attr('id','fcName' + i);
	
		$(this).find('input:text:eq(2)').attr('id','majorActivities' + i);
		
		
		
		
		
		$(this).find("input:text:eq(0)").attr("name", "dto.equityGrantFunctionalCommitteeDetailDtos[" + i + "].sNoFCD");
		$(this).find("input:text:eq(1)").attr("name", "dto.equityGrantFunctionalCommitteeDetailDtos[" + i + "].fcName");
		
		$(this).find("input:text:eq(2)").attr("name", "dto.equityGrantFunctionalCommitteeDetailDtos[" + i + "].majorActivities");
		
		
		
		
		$("#sNoFCD" + i).val(i + 1);
		
	
	
	});
}

function validateFCDTable() {
	var errorList = [];
	var rowCount = $('#detailsfcdTable tr').length;	


	if (errorList == 0)
		$("#detailsfcdTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var fcName = $("#fcName" + i).val();
				var majorActivities = $("#majorActivities" + i).val();
				
				
				var constant = 1;
       }
        else{
        	var fcName = $("#fcName" + i).val();
			var majorActivities = $("#majorActivities" + i).val();
	        var constant = i+1;
     }
		
				
				if (fcName == undefined || fcName == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.fcName") + " " + (i + 1));
				}
				
				if (majorActivities == undefined || majorActivities == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.majorActivities") + " " + (i + 1));
				}
				
				
				
				
		});
	
	return errorList;
}

function addSHButton(obj) {
	var errorList = [];
    errorList = validateSHTable(errorList); 
	if (errorList.length == 0) {
		var content = $('#detailsOfShareholdTable tr').last().clone();
		$('#detailsOfShareholdTable tr').last().after(content);
		 content.find("select").val('0');
	    content.find('div.chosen-container').remove();
		content.find("input:hidden").val('');
		content.find("input:text").val('');
		content.find("input:checkbox").prop('checked', false);
		
      
        content.find('[id^="role"]').chosen().trigger("chosen:updated");
		reordeFCDTable();
	
		
		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function deleteSHDetails(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#detailsOfShareholdTable tr').length;
	if ($("#detailsOfShareholdTable tr").length != 2) {
		$(obj).parent().parent().remove();
		reordeSHTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function reordeSHTable() {
	$("#detailsOfShareholdTable tbody tr").each(function(i) {
		// Id
		

		$(this).find("input:text:eq(0)").attr("id", "sNoSH" + (i));
		$(this).find('input:text:eq(1)').attr('id','noOfShareHolder' + i);
	
		$(this).find('input:text:eq(2)').attr('id','faceValueShareAllotted' + i);
	
		$(this).find('input:text:eq(3)').attr('id','totalAmtPaid' + i);
		
		
		
		
		
		$(this).find("input:text:eq(0)").attr("name", "dto.equityGrantShareHoldingDetailDtos[" + i + "].sNoSH");
		$(this).find("input:text:eq(1)").attr("name", "dto.equityGrantShareHoldingDetailDtos[" + i + "].noOfShareHolder");
		$(this).find("input:text:eq(2)").attr("name", "dto.equityGrantShareHoldingDetailDtos[" + i + "].faceValueShareAllotted");
		
		$(this).find("input:text:eq(3)").attr("name", "dto.equityGrantShareHoldingDetailDtos[" + i + "].totalAmtPaid");
		
		
		
		
		$("#sNoSH" + i).val(i + 1);
		
		
	
	});
}

function validateSHTable() {
	var errorList = [];
	var rowCount = $('#detailsOfShareholdTable tr').length;	


	if (errorList == 0)
		$("#detailsOfShareholdTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var noOfShareHolder = $("#noOfShareHolder" + i).val();
				var faceValueShareAllotted = $("#faceValueShareAllotted" + i).val();
				var totalAmtPaid = $("#totalAmtPaid" + i).val();
				
				
				var constant = 1;
       }
        else{
        	var noOfShareHolder = $("#noOfShareHolder" + i).val();
			var faceValueShareAllotted = $("#faceValueShareAllotted" + i).val();
			var totalAmtPaid = $("#totalAmtPaid" + i).val();
	        var constant = i+1;
     }
		
				
				if (noOfShareHolder == undefined || noOfShareHolder == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.noOfShareHolder") + " " + (i + 1));
				}
				
				if (faceValueShareAllotted == undefined || faceValueShareAllotted == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.faceValueShareAllotted") + " " + (i + 1));
				}
				if (totalAmtPaid == undefined || totalAmtPaid == "") {
					errorList.push(getLocalMessage("sfac.fpo.egr.validation.totalAmtPaid") + " " + (i + 1));
				}
				
				
				
				
		});
	
	return errorList;
}


function ResetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('EquityGrantRequest.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}



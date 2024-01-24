$(document).ready(function() {
    $('.datepicker').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	yearRange : "-100:-0",
    });

    $('#AdvertisementContractMapping').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });

    $("#hoardingDetailTable").dataTable({
	"oLanguage" : {
	    "sSearch" : ""
	},
	"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	"iDisplayLength" : 5,
	"bInfo" : true,
	"lengthChange" : true
    });
    /*Defect #156249*/
    $("#hoardingDetailTable").on('click', '.btn-danger', function() {
    	var errorList = [];
    	var tableRowLength = $('#hoardingDetailTable tr').length;
		if (tableRowLength <= 2) {
			errorList.push(getLocalMessage('agreement.first.row.validation'));
			displayErrorsOnPage(errorList);
		}
    });
});
$("#ContractDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});
function addContractMapping(formUrl, actionParam) {
    var divName = '.content-page';
    var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
	    divName);
    $(divName).removeClass('ajaxloader');
    $(divName).html(ajaxResponse);
    prepareTags();
}

function getHoardingDetailByHoardingNo(element) {

    var errorList = [];
    errorList = checkDuplicateHoardingNo(errorList);
    var hoardingId = $(element).attr('id');
    var index = hoardingId.charAt(hoardingId.length - 1);
    if (errorList.length == 0) {

	var requestData = {
	    "hoardingId" : $(element).val()
	};
	$(".warning-div").hide();

	var ajaxResponse = doAjaxLoading(
		'AdvertisementContractMapping.html?getHoardingDetailByHoardingNo',
		requestData, 'json');

	if (ajaxResponse.successFlag == 'Y') {
	    $("#hoardingNumber" + index).val(
		    ajaxResponse.hoardingDto.hoardingNumber);
	    $("#hoardingId" + index).val(ajaxResponse.hoardingDto.hoardingId);
	    $("#hoardingDescription" + index).val(
		    ajaxResponse.hoardingDto.hoardingDescription);
	    $("#hoardingHeight" + index).val(
		    ajaxResponse.hoardingDto.hoardingHeight);
	    $("#hoardingLength" + index).val(
		    ajaxResponse.hoardingDto.hoardingLength);
	    $("#hoardingArea" + index).val(
		    ajaxResponse.hoardingDto.hoardingArea);
	    $("#displayIdDesc" + index).val(
		    ajaxResponse.hoardingDto.displayIdDesc);
	} else

	{
	    $("#hoardingNumber" + index).val('');
	    $("#hoardingId" + index).val('');
	    $("#hoardingDescription" + index).val('');
	    $("#hoardingHeight" + index).val('');
	    $("#hoardingLength" + index).val('');
	    $("#hoardingArea" + index).val('');
	    $("#displayIdDesc" + index).val('');
	    errorList.push(getLocalMessage('adh.already.hoarding.mapped'));
	    displayErrorsOnPage(errorList);
	}
    } else {
	$("#hoardingNumber" + index).val('');
	$("#hoardingId" + index).val('');
	$("#hoardingDescription" + index).val('');
	$("#hoardingHeight" + index).val('');
	$("#hoardingLength" + index).val('');
	$("#hoardingArea" + index).val('');
	$("#displayIdDesc" + index).val('');
	displayErrorsOnPage(errorList);
    }
}

function addHoardingDetails() {

    var errorList = [];
    $("#errorDiv").hide();
    errorList = validateHoardindDetails(errorList);
    // errorList = validateAttendeeDetails(errorList);
    if (errorList.length == 0) {
	// addTableRow('hoardingDetailTable', false);
	addTableRow('hoardingDetailTable');
	$('#hoardingDetailTable').DataTable();
    } else {
	$('#hoardingDetailTable').DataTable();
	displayErrorsOnPage(errorList);
    }
}

//Add specific table-Row
function addTableRow(tableId, isDataTable) {
	
	var id = "#" + tableId;
	// remove datatable specific properties
	if ((isDataTable == undefined || isDataTable) && $.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".datepicker").datepicker("destroy");
	$(".chosen-select-no-results").chosen('destroy');
	var content = $('' + id + ' tr').last().clone();
	$('' + id + ' tr').last().after(content);
	
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find(".chosen-select-no-results").val('0').chosen();
	content.find("input:checkbox").removeAttr('checked');
	reOrderTable("hoardingDetailTable");
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	if(isDataTable == undefined || isDataTable) {
		// adding datatable specific properties
		dataTableProperty(id);
	}
}

function deleteHoardingRow(obj, ids) {
    deleteTableRow('hoardingDetailTable', obj, ids);
    $('#hoardingDetailTable').DataTable().destroy();
    triggerTable();
}

function saveADHContractMapping(element) {
   
    var errorList = [];
    errorList = validateform(errorList);
    errorList = checkDuplicateContract(errorList);
    if (errorList.length == 0) {
	return saveOrUpdateForm(element, 'Contract Mapping added succesfully',
		'AdvertisementContractMapping.html', 'saveform');
    } else {
	displayErrorsOnPage(errorList);
    }
}

function searchContractMapping() {
    var errorList = [];
    var contractNo = $('#contractNo').val();
    var contDate = $('#conDate').val();
    if ($('#contractNo').val() != '' || $('#contractNo').val() != 0
	    || $('#conDate').val() != '') {
	var requestData = 'contractNo=' + contractNo + '&contDate=' + contDate;

	var table = $('#hoardingDetailTable').DataTable();
	table.rows().remove().draw();

	var ajaxResponse = __doAjaxRequest(
		'AdvertisementContractMapping.html?searchContractByContNoOrContDate',
		'POST', requestData, false, 'json');
	$("#errorDiv").hide();
	var result = [];
	$
		.each(
			ajaxResponse,
			function(index) {
			    var lookUp = ajaxResponse[index];
			    result
				    .push([
					    lookUp.contractNo,
					    lookUp.contDate,
					    lookUp.deptName,
					    lookUp.representedBy,
					    lookUp.vendorName,
					    lookUp.fromDate,
					    lookUp.toDate,
					    "<a href='javascript:void(0);' class='btn btn-blue-2 btn-sm margin-left-30'  onClick=viewAdhContractMapping(\""
						    + lookUp.contId
						    + "\",\'V\')><strong class='fa fa-eye'></strong><span class='hide'>View</span></a>"/*
																	 * , "<a
																	 * href='javascript:void(0);'
																	 * class='btn
																	 * btn-darkblue-3
																	 * margin-left-30'
																	 * onClick=printContractEstate(\"" +
																	 * lookUp.contId +
																	 * "\")><i
																	 * class='fa
																	 * fa-print'></i></a>"
																	 */]);
			});
	table.rows.add(result);
	table.draw();
	if (ajaxResponse.length == 0) {
	    errorList.push(getLocalMessage("adh.validate.search"));
	    $("#errorDiv").show();
	    displayErrorsOnPage(errorList);
	} else {
	    $("#errorDiv").hide();
	}
    } else {
	errorList.push(getLocalMessage('adh.select.any.field'));
	displayErrorsOnPage(errorList);
    }
}

function viewAdhContractMapping(contId, saveMode) {

    var requestData = {
	'contId' : contId,
	'saveMode' : saveMode
    }
    var ajaxResponse = __doAjaxRequest(
	    'AdvertisementContractMapping.html?viewAdhContractMapping', 'POST',
	    requestData, false, 'html');
    $('.content-page').html(ajaxResponse);
}

function checkDuplicateContract(errorList) {

   
    var contractNo = $("#contId").val();
    var requestData = {
	"contId" : $("#contId").val()
    };

    var ajaxResponse = doAjaxLoading(
	    'AdvertisementContractMapping.html?checkDuplicateContract',
	    requestData, 'json');

    if (ajaxResponse == 'N') {
	errorList.push(getLocalMessage('adh.already.contract.mapped'));
	displayErrorsOnPage(errorList);
    } else {
	$("#errorDiv").hide();

    }
    return errorList;
}

function showContract(contId, type) {
    if (type == 'V') {

	showContractForm(contId, type);
	// $("#ContractAgreement :input").prop("disabled", true);
	$('.addCF3').attr('disabled', true);
	$('.addCF4').attr('disabled', true);
	$('.addCF5').attr('disabled', true);
	$('.addCF2').attr('disabled', true);
	$('.remCF2').attr('disabled', true);
	$('.remCF3').attr('disabled', true);
	$('.remCF4').attr('disabled', true);
	$('.remCF5').attr('disabled', true);
	$('.uploadbtn').attr('disabled', false);
	$("#backButton").removeProp("disabled");
    }

    if (type == 'E') {
	var contractUrl = 'ContractAgreement.html';
	var requestData = 'contId=' + contId + '&type=' + type;
	var ajaxResponse = doAjaxLoading(contractUrl
		+ '?findContractMapedOrNot', requestData, 'html');
	if (ajaxResponse != '"Y"') {
	    showContractForm(contId, type);
	    $("#resetButton").prop("disabled", true);
	    $("#AgreementDate").prop('disabled', true);
	} else {
	    showAlertBox();
	}
    }
}

function showContractForm(contId, type) {
    var contractUrl = 'ContractAgreement.html';
    var showForm = 'ADH';
    var requestData = 'contId=' + contId + '&type=' + type + '&showForm='
	    + showForm;
    var ajaxResponse = doAjaxLoading(contractUrl + '?form', requestData, 'html');
    $('.content').removeClass('ajaxloader');
    $('.content').html(ajaxResponse);
}

function checkDuplicateHoardingNo(errorList) {
  
    $("#hoardingDetailTable tbody tr")
	    .each(
		    function(i) {
		
			var hoardingNo = $("#hoardingNo" + i).val();
			var duplicate = '';
			var myarray = new Array();
			if (hoardingNo != '') {
			    for (j = 0; j < i; j++) {
				myarray[j] = $("#hoardingNo" + j).val();
			    }
			}
			if (myarray.includes(hoardingNo)) {
			    duplicate = 'contract.validate.duplicate.hoarding';
			}
			if (duplicate != '') {
			    $("#hoardingNo" + i).val('');
			    $("#hoardingId" + i).val('');
			    $("#hoardingDescription" + i).val('');
			    $("#hoardingHeight" + i).val('');
			    $("#hoardingLength" + i).val('');
			    $("#hoardingArea" + i).val('');
			    $("#displayIdDesc" + i).val('');

			    errorList
				    .push(getLocalMessage("contract.validate.duplicate.hoarding"));

			}

		    });

    return errorList;
}

function validateform(errorList) {

    var contId = $("#contId").val();
    if (contId == "0" || contId == undefined || contId == null) {
	errorList.push(getLocalMessage("contract.validate.contract.no"));
    }
    validateHoardindDetails(errorList);
    // checkDuplicateHoardingNo(errorList);
    return errorList;
}

function validateHoardindDetails(errorList) {
 
    {

	var i = 0;
	if ($.fn.DataTable.isDataTable('#hoardingNo')) {
	    $('#hoardingDetailTable').DataTable().destroy();
	}

	$("#hoardingDetailTable tbody tr")
		.each(
			function(i) {

			    var hoardingNo = $("#hoardingNo" + i).val();
			    var hoardingDescription = $(
				    "#hoardingDescription" + i).val();
			    var hoardingHeight = $("#hoardingHeight" + i).val();
			    var hoardingLength = $("#hoardingLength" + i).val();
			    var hoardingArea = $("#hoardingArea" + i).val();
			    /*var displayTypeId = $("#displayIdDesc" + i).val();*/

			    var rowCount = i + 1;

			    if (hoardingNo == "0" || hoardingNo == undefined
				    || hoardingNo == null) {
				errorList
					.push(getLocalMessage("contract.validate.hoardingNo")
						+ rowCount);
			    }
			    if (hoardingDescription == "0"
				    || hoardingDescription == undefined
				    || hoardingDescription == null
				    || hoardingDescription == "") {
				errorList
					.push(getLocalMessage("hoarding.master.validate.description")
						+ rowCount);
			    }
			    if (hoardingHeight == "0"
				    || hoardingHeight == undefined
				    || hoardingHeight == null
				    || hoardingHeight == "") {
				errorList
					.push(getLocalMessage("contract.validate.hoarding.height")
						+ rowCount);
			    }
			    if (hoardingLength == "0"
				    || hoardingLength == undefined
				    || hoardingLength == null
				    || hoardingLength == "") {
				errorList
					.push(getLocalMessage("contract.validate.hoarding.length")
						+ rowCount);
			    }
			    if (hoardingArea == "0"
				    || hoardingArea == undefined
				    || hoardingArea == null
				    || hoardingArea == "") {
				errorList
					.push(getLocalMessage("hoarding.master.validate.hoardingArea")
						+ rowCount);
			    }
			    /*if (displayTypeId == "0"
				    || displayTypeId == undefined
				    || displayTypeId == null
				    || displayTypeId == "") {
				errorList
					.push(getLocalMessage("adh.validate.displaytypeid")
						+ rowCount);
			    }*/

			});
	return errorList;

    }
}
function reOrderTable(tableName){
	var tableId="#"+tableName;
	var rowName = $('#hoardingDetailTable tr');
	var rowCount = rowName.length;
	var reTable = $(tableId+'tr');
	for (var i=0; i<=rowCount; i++){
		rowName.eq(i+1).find('input[id^=sequence]').val(i+1);
		rowName.eq(i+1).find('input[id^=sequence]').attr('id','sequence'+i);

        rowName.eq(i+1).find('input[id^=hoardingNumber]').attr('name','contractMappingDto.hoardingMasterList['+i+'].hoardingNumber');
        rowName.eq(i+1).find('input[id^=hoardingNumber]').attr('id','hoardingNumber'+i);

		rowName.eq(i+1).find('select[id^=hoardingNo]').attr('name','contractMappingDto.hoardingMasterList['+i+'].hoardingNo');
        rowName.eq(i+1).find('select[id^=hoardingNo]').attr('id','hoardingNo'+i);
         
		rowName.eq(i+1).find('input[id^=hoardingDescription]').attr('name','contractMappingDto.hoardingMasterList['+i+'].hoardingDescription');
        rowName.eq(i+1).find('input[id^=hoardingDescription]').attr('id','hoardingDescription'+i);

		rowName.eq(i+1).find('input[id^=hoardingHeight]').attr('name','contractMappingDto.hoardingMasterList['+i+'].hoardingHeight');
        rowName.eq(i+1).find('input[id^=hoardingHeight]').attr('id','hoardingHeight'+i);
	
  		rowName.eq(i+1).find('input[id^=hoardingLength]').attr('name','contractMappingDto.hoardingMasterList['+i+'].hoardingLength');
        rowName.eq(i+1).find('input[id^=hoardingLength]').attr('id','hoardingLength'+i);

		rowName.eq(i+1).find('input[id^=hoardingArea]').attr('name','contractMappingDto.hoardingMasterList['+i+'].hoardingArea');
		rowName.eq(i+1).find('input[id^=hoardingArea]').attr('id','hoardingArea'+i);

		rowName.eq(i+1).find('input[id^=displayIdDesc]').attr('name','contractMappingDto.hoardingMasterList['+i+'].displayIdDesc');
		rowName.eq(i+1).find('input[id^=displayIdDesc]').attr('id','displayIdDesc'+i);
  }
}
$(document).ready(function() { 

	$("#id_storemaster").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});

	if('V' == $('#saveMode').val()){
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr("disabled", true);
		$('select').prop('disabled', true).trigger("chosen:updated");
	}

});
/**
 * addStoreForm
 * 
 * @param formUrl
 * @param actionParam
 * @returns
 */
function addStoreForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/**
 * getStoremasterData
 * 
 * @param formUrl
 * @param actionParam
 * @param storeId
 * @returns
 */
function getStoremasterData(formUrl, actionParam, storeId) {
	var data = {
		"storeId" : storeId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getStoreIncharge() {
	$('#storeInchargeId').html('');
	var locId = $("#locId").val();
	var requestUrl = "StoreMaster.html?getStoreInchargeList";
	var requestData = {
		"locId" : locId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false, 'json');
	$('#storeInchargeId').append($("<option></option>").attr("value", "").attr("code", "").text("select"));
	$.each(ajaxResponse, function(index, value) {
		$('#storeInchargeId').append($("<option></option>").attr("value", value[3]).attr("code", value[3]).text(value[0]));
	});
	$('#storeInchargeId').trigger('chosen:updated');
}

/**
 * Back Route Master Form
 * 
 * @returns
 */
function backStoreMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'StoreMaster.html');
	$("#postMethodForm").submit();
}

/**
 * search Store Master
 * 
 * @returns
 */
function searchStoreMaster() {
	var errorList = [];
	var locationId = $('#locationId').val();
	var storeName = $('#storeName').val();
	
	if ((locationId == "" || locationId == null || locationId == undefined || locationId == 0)
			&& (storeName == "" || storeName == null || storeName == undefined || storeName == 0))
		errorList.push(getLocalMessage("grn.Select"));	
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else {
		var data = {
			"locationId": locationId,
			"storeName": storeName,
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('StoreMaster.html?searchStoreMaster', data, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function resetStoreMaster() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'StoreMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}


function resetStoreMasterAdd() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}


function exportExcelData() {
	window.location.href = 'StoreMaster.html?exportStoreExcelData';
}


function saveStoreMasterForm(element) {
	var errorList = [];
	var storeName = $("#storeName").val();
	var locId = $("#locId").val();
	var storeInchargeId = $("#storeInchargeId").val();
	var statusId = $("#statusId").val();
	if (locId == "")
		errorList.push(getLocalMessage("material.management.Location.name"));
	if (storeName == "")
		errorList.push(getLocalMessage("material.management.store.name"));
	if (storeInchargeId == "")
		errorList.push(getLocalMessage("material.management.store.incharged"));
	if (statusId == "")
		errorList.push(getLocalMessage("material.management.status"));

	errorList = validateGroups(errorList);
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	else
		return saveOrUpdateForm(element, '', 'StoreMaster.html', 'saveform');
}

function validateGroups(errorList) {
	$('.firstUnitRow').each(function(i) {
		var itemGroupId = $.trim($("#itemGroupId" + i).val());
		if (itemGroupId == 0 || itemGroupId == "") {
			errorList.push(getLocalMessage("material.management.item.group.name") + " " + (i + 1));
		}
	});
	return errorList;
}

$(function() {
	$("#unitDetailTable").on('click', '.addPurReq', function() {
		var errorList = [];
		errorList = validateGroups(errorList);
		if (errorList.length > 0)
			displayErrorsOnPage(errorList);
		else {
			var content = $("#unitDetailTable").find('tr:eq(1)').clone();
			$("#unitDetailTable").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstUnitRow');
		}
	});
});

function reOrderUnitTabIdSequence(firstRow) {
	$(firstRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("select:eq(0)").attr("id", "itemGroupId" + i);
		
		$("#sNo"+i).val(i+1);
		$(this).find("select:eq(0)").attr("name", "storeMasterDTO.storeGrMappingDtoList[" + i + "].itemGroupId");
	});
}

function deleteEntry(obj, ids) {
	var rowCount = $('#unitDetailTable tr').length;
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		displayErrorsOnPage(errorList);
	} else {
		deleteTableRow('firstUnitRow', obj, ids);
		$('#firstUnitRow').DataTable().destroy();
		triggerTable();
	}
}

function uploadExcelFile() {
	var errorLis = [];
	var fileName = $("#excelFilePath").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		showErr(errorLis);
		return false;
	}
	$("#filePath").val(fileName);
	var requestData = $.param($('#wmsMaterialMaster').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('StoreMaster.html?' + "loadExcelData",
			requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	showSaveResultBox1("StoreMaster.html");
	prepareTags();
}

function showSaveResultBox1(redirectUrl) {
	var messageText = getLocalMessage("swm.population.excel.upload.success.message");
	var message = '';
	var cls = getLocalMessage('eip.page.process');
	message += '<p class="text-blue-2 text-center padding-15">' + messageText
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
			+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
			+ '\')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}


$('.hasCharacterNumbers').on('keyup', function() {
    this.value = this.value.replace(/[^a-z A-Z 0-9]/g,'');
});

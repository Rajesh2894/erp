$(document).ready(function() {

})

/*
 * function saveFieldConfiguration(element) { debugger; var errorList = [];
 * errorList = validateForm(errorList) if (errorList.length == 0) {
 * saveOrUpdateForm(element, getLocalMessage(' saved sucessfully.'),
 * 'fieldConfiguration.html', 'saveform'); } else
 * displayErrorsOnPage(errorList); }
 */

function saveFieldConfiguration(element) {
	var errorList = [];
	errorList = validateForm(errorList)
	if (errorList.length == 0) {
		return saveOrUpdateForm(element, 'saved sucessfully',
				'fieldConfiguration.html', 'saveform');
	} else
		displayErrorsOnPage(errorList);

}

function validateForm(errorList) {
	var errorList = [];

	return errorList;
}

function getPageInfo() {

	$('.warning-div').addClass('hide');
	var resId = $("#resId").val();

	var url = "fieldConfiguration.html?searchResourceDetData";
	var requestData = {
		"resId" : resId
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');

	$('#fieldId').html('');
	$('#isMandatory').html('');
	$('#isVisible').html('');

	var i = 0;
	if (ajaxResponse != null) {

		$("#fieldTable").find("tr:not(:first)").remove();
	}
	$.each(ajaxResponse, function(index, data) {

		$("#fieldTable tbody").append(
				"<tr>" + "<td>" + data.fieldId + "</td>" + "<td>"
						+ data.isMandatory + "</td>" + "<td>" + data.isVisible
						+ "</td>" + "</tr>");
	});
	$('.error-div').hide();
	prepareTags();
}

function ResetSearchForm(resetBtn) {
	debugger;

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action',
			'fieldConfiguration.html?searchResourceMasData');
	$("#postMethodForm").submit();
	$('.error-div').hide();
	prepareTags();

}

function editPageFields(formUrl, actionParam) {
	debugger;
	resId = $("#resId").val();
	var errorList = [];
	if (resId == "0") {
		errorList.push("Please Select atleast one entry");
		
	} else {
		var requestData = {
			"resId" : resId
		}
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);

	}
	if(errorList.length != 0){
		displayErrorsOnPage(errorList);
	}
}

function getData() {
	debugger;
	$('.warning-div').addClass('hide');
	var resId = $("#resId").val();

	var url = "fieldConfiguration.html?searchResourceMasDto";
	var requestData = {
		"resId" : resId
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fieldConfiguration.html');
	$("#postMethodForm").submit();
}

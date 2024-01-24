$(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0d',
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)

		}
	});

});

function resetSummaryPage() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'rtsService.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
	prepareTags();
}

function searchService(element, formUrl, actionParam) {
	debugger;
	var requestData = element;
	var divName = '.content-page'
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	debugger;

	var deId = $("#deId").val();
	/*
	 * var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
	 * requestData, false, '', 'html');
	 */
	var requestdata = {
		"deId" : deId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).html(ajaxResponse);
	$(divName).removeClass('ajaxLoader')
	prepareTags();
}

function validateForm() {
	var errorList = [];
	var serviceId = $("#serviceId").val();
	var applicationId = $("#applicationId").val();

	if ((serviceId == null || serviceId == undefined || serviceId == "")
			&& (applicationId == null || applicationId == undefined || applicationId == "")) {
		errorList.push(getLocalMessage("DrainageConnectionDTO.label.searchcrit"));
	}
	return errorList;
}

function searchData() {
	var errorList = [];
	errorList = validateForm();
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var serviceId = $("#serviceId").val();
		var applicationId = $("#applicationId").val();
		var formUrl = "rtsService.html";
		var actionParam = "search"
		var divName = '.content-page';
		var requestData = {
			"applicationId" : applicationId,
			"serviceId" : serviceId
		};

		var response = doAjaxLoading(formUrl + '?' + actionParam, requestData,
				'html', divName);
		$(divName).html(response);
		$(divName).removeClass('ajaxLoader')
		prepareTags();
	}
}

function loadApplicationNo(formUrl, actionParam) {
	var serviceId = $("#serviceId").val();
	if (serviceId != null && serviceId != '' && serviceId != undefined) {
		var requestData = {
			"serviceId" : serviceId
		};
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function loadApplicantForm(formUrl, actionParam, applicationId) {
	debugger;
	var divName = '.content-page'
	var divName = '.content-page';
	var applicationId = applicationId;
	requestData = {
		"applicationId" : applicationId
	};

	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

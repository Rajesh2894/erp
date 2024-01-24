$(document).ready(function() {
	prepareDateTag();
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	$('.datepickerEndDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2021:2200"
	});

});

/* Form.jsp */
function saveDelayReason(element) {
	debugger;
	var errorList = [];
	errorList = validateForm(errorList)
	if (errorList.length == 0) {
		saveOrUpdateForm(element, 'Work Delay Reason added sucsessfully',
				'DelayReason.html', 'saveform');
	} else
		displayErrorsOnPage(errorList);

}

function searchForm(formUrl, actionParam) {
	debugger;
	var errorList = [];

	/* errorList = validateHomeForm(errorList); */
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var projId = $("#projId").val();
		var workId = $("#workId").val();
		var status = $("#status").val();
		var occuranceDate = $("#dateOccu").val();
		
		if(projId == '0' && projId == '' && status == '' && occuranceDate == ''){
			errorList.push(getLocalMessage('work.management.valid.select.any.field'));
			displayErrorsOnPage(errorList);
			return false;
		}
		 if (projId != '0' && projId != '' ){
				if(workId == '' || workId == '0'){
					errorList.push(getLocalMessage('work.estimate.select.work.name'));
					displayErrorsOnPage(errorList);
					return false;
				}
			}

		var requestdata = {
			"projId" : projId,
			"workId" : workId,
			"status" : status,
			"occuranceDate" : occuranceDate,
		};

		requestData = __serializeForm("#delayReason");
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestdata, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	
	}
}
function viewDelayReason(formUrl, actionParam, delResId, projId) {
	debugger;
	var requestdata = {
		"delResId" : delResId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getCreateWorkName(projId);

}

function editDelayReason(formUrl, actionParam, delResId, projId) {
	debugger;
	var requestdata = {
		"delResId" : delResId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
    getCreateWorkName(projId);}

function addSequenceMaster(formUrl, actionParam) {
	debugger;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}

function validateForm(errorList) {
	debugger;
	var errorList = [];

	var projId = $("#projId").val();
	var workId = $("#workId").val();
	var dateOccu = $("#dateOccu").val();
	var weightage = $("#weightage").val();
	var remark = $("#remark").val();
	var siteEngId = $("#siteEngId").val();
	var natHindrnc = $("#natHindrnc").val();
	var contName = $("#contName").val();

	if (projId == "") {
		errorList.push(getLocalMessage("wms.delayReason.selectProjName"));
	}
	if (workId == "") {
		errorList.push(getLocalMessage("wms.delayReason.selectWorkName"));
	}
	if (dateOccu == "") {
		errorList.push(getLocalMessage("wms.delayReason.enterDateOccurance"));
	}
	if (weightage == "") {
		errorList.push(getLocalMessage("wms.delayReason.enterDateOfHindrnc"));
	}
	if (remark == "") {
		errorList.push(getLocalMessage("wms.delayReason.enterRemarks"));
	}
	if (siteEngId == "") {
		errorList.push(getLocalMessage("wms.delayReason.enterSiteEngName"));
	}
	if (natHindrnc == "") {
		errorList.push(getLocalMessage("wms.delayReason.enterNatureOfHindrnc"));
	}
	if (contName == "") {
		errorList.push(getLocalMessage("wms.delayReason.enterContName"));
	}

	return errorList;
}

function getCreateWorkName(obj) {
	debugger;
	var requestData = {
		"projId" : $(obj).val()
	}
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('DelayReason.html?worksName', 'post',
			requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DelayReason.html');
	$("#postMethodForm").submit();
}

function ResetSearchForm(resetBtn) {
	debugger;
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'DelayReason.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
	prepareTags();

}

function ResetAddForm(resetBtn) {

	var divName = '.content-page';

	var ajaxResponse = doAjaxLoading(
			'DelayReason.html?addDetailsofDelayReason', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
	

}

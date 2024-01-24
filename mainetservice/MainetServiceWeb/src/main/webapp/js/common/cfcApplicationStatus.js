$(document).ready(function() {

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
	});

	$("#cfcStatusTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function getServiceByDeptId() {

	$('.warning-div').addClass('hide');

	var deptId = $('#deptId option:selected').val();
	var langId = $('#langId').val();
	
	var errorList = [];

	if (errorList.length > 0) {
		showErr(errorList);
	} else {
		var url = "CFCApplicationStatus.html?getServiceByDept";
		var requestData = {
			"deptId" : deptId
		}

		var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false,
				'json');

		$('#serviceId').html('');

		$('#serviceId').append(
				$("<option></option>").attr("value", "").text(getLocalMessage("cfc.select")));

		$.each(ajaxResponse, function(index, data) {
			if(langId==1){
				$('#serviceId').append(
						$("<option></option>").attr("value", data.smServiceId)
								.text(data.smServiceName));
			}
			else
			{
				$('#serviceId').append(
						$("<option></option>").attr("value", data.smServiceId)
								.text(data.smServiceNameMar));
			}
		});
		$('#serviceId').trigger('chosen:updated');

	}
}

function ResetForm() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CFCApplicationStatus.html?form', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj, formUrl, actionParam) {
	showloader(true);
	setTimeout(function () {
			var errorList = [];
			errorList = validateHomeForm(errorList);
			
			var divName = '.content-page';
		
			var formName = findClosestElementId(obj, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			
			var response = __doAjaxRequest('CFCApplicationStatus.html?isDataExist', 'POST',
					requestData, false, 'json');
			if (response) {
				errorList.push(getLocalMessage("work.management.vldn.grid.nodatafound"));
			} 
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			} else {
			
				var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
						requestData, 'html', divName);
		
				$(divName).removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
			}
			showloader(false);
	},2000);
	
}

function validateHomeForm(errorList) {

	var errorList = [];
	var firstName = $("#firstName").val();
	var middleName = $("#middleName").val();
	var lastName = $("#lastName").val();
	var date = $("#date").val();
	var deptId = $("#deptId").val();
	var serviceId = $("#serviceId").val();
	var appNo = $("#appNo").val();
	
	if (appNo == "") {
		if (firstName == "") {
			if (middleName == "") {
				if (lastName == "") {
					if (date == "") {
						if (deptId == "") {
							if (serviceId == "") {
								errorList
										.push(getLocalMessage("SFT.validation.select.field"));
							}
						}
					}
				}
			}

		}

	}
	if(langId == 1){
		if(firstName != "" && !validateName(firstName)) {
			errorList.push(getLocalMessage("CFC.firstName.validn"));
		}
		if(middleName != "" && !validateName(middleName)) {
			errorList.push(getLocalMessage("CFC.middleName.validn"));
		}
		if(lastName != "" && !validateName(lastName)) {
			errorList.push(getLocalMessage("CFC.lastName.validn"));
		}
	}else{
		if(firstName != "" && !validateRegName(firstName)) {
			errorList.push(getLocalMessage("CFC.firstName.validn"));
		}
		if(middleName != "" && !validateRegName(middleName)) {
			errorList.push(getLocalMessage("CFC.middleName.validn"));
		}
		if(lastName != "" && !validateRegName(lastName)) {
			errorList.push(getLocalMessage("CFC.lastName.validn"));
		}
	}
	
	return errorList;
}

function validateName(name){
	var regexPattern = /^([a-zA-Z\u0900-\u097F\ \.\-\']){2,30}$/;    
	return regexPattern.test(name);
}

function validateRegName(name){
	var regexPattern = /^([a-zA-Z\u0900-\u097F\ \.\-\']){2,30}$/;
	return regexPattern.test(name);
}


function getActionForWorkFlow(applicationId,mode){
	var url = "CFCApplicationStatus.html?getWorkFlowHistory";
	var actionParam = {
			'applicationId' : applicationId,
			'mode' :mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	var errMsgDiv = '.msg-dialog-box';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(ajaxResponse);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
	return false;
}

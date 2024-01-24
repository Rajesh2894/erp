function openPropertyDetailss() {

	var errorList = [];
	var propNo = $("#assNo").val();
	var oldPropNo = $("#assOldpropno").val();

	if (propNo == "" && oldPropNo == "") {
		errorList.push(getLocalMessage("property.noDues.propertySearchValid"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	} else {
		var data = {
			"propNo" : propNo,
			"oldPropNo" : oldPropNo
		};
		var URL = 'BillingMethodChange.html?getPropertyDetails';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}
}

function saveBillingMethodForm(element) {
	var errorList = [];
	var billMethodCodePrev = $("#billMethodCode").val();
	var billMethodCurrent = $('#billMethod option:selected').attr('code');
	if (billMethodCodePrev == billMethodCurrent) {
		errorList.push(getLocalMessage("property.billMethodUpdate"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, "", 'AdminHome.html', 'saveform');
	}
}

function backToSearchPage(element) {
	var requestData = {};
	var URL = 'BillingMethodChange.html?backToSearch';
	var returnData = __doAjaxRequest(URL, 'post', requestData, false, 'html');
	var divName = '.content-page';
	$(divName).html(returnData);
}

function saveForms(element) {

	var theForm = '#BillingMethodChange';
	var appId = $("input[name='lableValueDTO.applicationId']").val();
	var labelId = $("input[name='lableValueDTO.lableId']").val();

	var serviceId = $("#serviceId").val();
	var requestData = __serializeForm(theForm);
	var URL = 'BillingMethodAuthorization.html?saveDataInProviosionals';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'json');
	if (returnData) {
		loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',
				appId, labelId, serviceId);
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("property.saveWorkflowError"));
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
	}

	// saveOrUpdateForm(element, "Authorisation done successfully!",
	// 'AdminHome.html', 'saveDataInProviosionals');
	/*
	 * var errorList = []; var decision =
	 * $("input[name='workflowActionDto.decision']:checked").val(); var remark =
	 * $("#comments").val(); if (decision == '' || decision == undefined) {
	 * errorList.push(getLocalMessage("property.select.atleast.oneDecision")); }
	 * if (remark == "") {
	 * errorList.push(getLocalMessage("property.enter.remark")); } if
	 * (errorList.length > 0) { displayErrorsOnPage(errorList); } else {
	 * saveOrUpdateForm(element, "Authorisation done successfully!",
	 * 'AdminHome.html', 'saveDataInProviosionals'); }
	 */
}

function addArrearDetailsForFlat(flatCount, element) {
	$("#flatNoOfRow").val(flatCount);
	var theForm = '#BillingMethodChangeAuth';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'BillingMethodAuthorization.html?addArrears';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	if (returnData) {
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}
}

function backToFirstPage(obj) {
	var requestData = {};
	var URL = 'BillingMethodAuthorization.html?backToEditForm';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}

function confirmToNext(obj) {
	var requestData = {};
	var errorList = [];
	errorList = validateTaxEntryAdjustment(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var theForm = '#BillingArrearsAuthorization';
		requestData = __serializeForm(theForm);
		var URL = 'BillingMethodAuthorization.html?saveAdjustedArrears';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}
}

function displayMessageOnSubmit(element) {

	var Yes = getLocalMessage("unitdetails.Yes");
	var No = getLocalMessage("Unitdetails.No");
	var message = "";
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	message = getLocalMessage("property.arrearsChangesConfirmation");
	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' name="Yes" class= "btn btn-success" value=\''
			+ Yes
			+ '\'  id=\'btnNo\' onclick="confirmToProceed()"/>  <input type=\'button\' name="No" class= "btn btn-success" value=\''
			+ No + '\'  id=\'btnNo\' onclick="closeConfirmBoxForm()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}

function confirmToProceed(element) {

	var theForm = '#BillingMethodChangeAuth';
	var requestData = __serializeForm(theForm);
	var URL = 'BillingMethodAuthorization.html?confirmToProceed';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
	$.fancybox.close();
}

function validateTaxEntryAdjustment(errorList) {

	$('.firstUnitRow')
			.each(
					function(i) {

						var bdBalAmtToTransfer = $("#bdBalAmtToTransfer" + i)
								.val();
						var arrear = $("#arrear" + i).val();
						if (arrear != '') {
							arrear = parseFloat(arrear);
							if (arrear > bdBalAmtToTransfer) {
								errorList
										.push(getLocalMessage("property.adjustedArrearsCheck")
												+ " " + (i + 1));
							}
						}

					});
	return errorList;
}


function saveWorkOrder(element) {
	return saveOrUpdateForm(element, "Record saved successfully!",
			'AdminHome.html', 'saveWorkOrder');
}
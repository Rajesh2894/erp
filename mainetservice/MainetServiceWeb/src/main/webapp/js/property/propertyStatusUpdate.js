function getPropertyDetailss() {

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
		var URL = 'PropertyStatusUpdate.html?getPropertyDetails';
		var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
		var divName = '.content-page';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
	}
}

function confirmToProceed(element) {
	return saveOrUpdateForm(element, "", 'PropertyStatusUpdate.html',
			'saveform');

}

function backToSearch() {
	var data = {};
	var URL = 'PropertyStatusUpdate.html?backToSearch';
	var returnData = __doAjaxRequest(URL, 'POST', data, false, 'html');
	var divName = '.content-page';
	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);
}
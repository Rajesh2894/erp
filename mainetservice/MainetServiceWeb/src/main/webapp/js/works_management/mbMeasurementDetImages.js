$(document).ready(function() {
	
	if ($("#saveMode").val() == 'V' || $("#saveMode").val() == 'AP') {
		$("#measurementDetImages :input").prop("disabled", true);
		$("#backButton").prop("disabled", false);
		$('.backButton').prop("disabled", false);
	}
});
function otherUploadTask() {
	
	var divName = '.content-page';
	var theForm = "#measurementDetImages";
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('MeasurementBook.html?getUploadedImage',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);

}

function otherDeletionTask(ob) {
	
	var divName = '.content-page';
	var theForm = "#measurementDetImages";
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('MeasurementBook.html?getUploadedImage',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
}

function saveMbUploadedImage() {
	
	var errorList = [];
	var docDescription = $("#docDescription").val();
	if (docDescription == '') {
		errorList.push(getLocalMessage('wms.PleaseEnterDocumentDescription'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var divName = '.content-page';
	var theForm = "#measurementDetImages";
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('MeasurementBook.html?saveMbUploadedImage',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
}
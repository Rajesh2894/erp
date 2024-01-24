$(document).ready(function() {
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});

	prepareDateTag();
});

function prepareDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}
function searchProperty() {

	var errorList = [];	
	var propertNo = $("#assNo").val();
	var oldPropNo = $("#assOldpropno").val();
	if((propertNo == "" || propertNo == null || propertNo == undefined) && (oldPropNo == "" || oldPropNo == null || oldPropNo == undefined)){
		errorList.push(getLocalMessage("property.changeInAss"));
		return showErrorOnPage(errorList);
	}
	var requestData = $('#NochangeInAssessmentId').serialize();
	var ajaxResponse = __doAjaxRequest(
		'NoChangeInAssessment.html?SearchPropNo', 'POST',
		requestData, false, 'html');

	if (($('option:selected', $("#selectedAssType")).attr('code')) == 'NC') {
		$("#dataDiv").html(ajaxResponse);
	}
	$("#dataDiv").html(ajaxResponse);

	return false;
}

function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}
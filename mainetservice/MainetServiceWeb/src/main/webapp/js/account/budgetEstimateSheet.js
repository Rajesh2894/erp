function budgetReport(formUrl, actionParam) {
	var errorList = [];
	errorList = validateForm(errorList);
	   var reportType = $("input[name='reporttype']:checked").val();
	   var financialYear = $('#faYearId option:selected').text();
	   var financialYearId = $('#faYearId').val();
	   var deptName=$('#deptId option:selected').text();
	   var deptId = $('#deptId').val();
	   var functionName=$('#functionId option:selected').text();
	   var functionId=$('#functionId').val();
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		} else if (errorList.length == 0) {
	      var data = {
				"financialYearId" : financialYearId,
				"financialYear":financialYear,
				"deptName":deptName,
				"deptId":deptId,
				"functionName":functionName,
				"functionId":functionId,
				"reportType":reportType,
			};
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
}
}

function validateForm(errorList) {  
	 var financialYearId = $('#faYearId').val();
	if (financialYearId == "" || financialYearId == null) {
		errorList.push(getLocalMessage('account.select.financialYear.id'));
	}
	var functionId = $('#functionId').val();
	if (functionId == "" || functionId == null) {
		errorList.push(getLocalMessage('account.select.functionName'));
	}
	var deptId = $('#deptId').val();
	if (deptId == "" || deptId == null) {
		errorList.push(getLocalMessage('account.select.deptName'));
	}
	return errorList;
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

/** **********Start Back Button Report Function*********** */
function back() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'budgetEstimationSheetsFormat.html');
	$("#postMethodForm").submit();
}
function budgetReset() {
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}


function budgetVarianceReport(formUrl, actionParam) {
	
	   var errorList = [];
	   errorList = validateForm(errorList);
	   var financialYear = $('#financialYearId option:selected').text();
	   var financialYearId = $('#financialYearId').val();
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		} else if (errorList.length == 0) {
	      var data = {
				"financialYearId" : financialYearId,
				"financialYear":financialYear,
			};
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
}
function validateForm(errorList) {
	var financialYearId = $('#financialYearId').val();
	if (financialYearId == "" || financialYearId == null) {
		errorList.push(getLocalMessage('account.select.financialYear.id'));
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
	$("#postMethodForm").prop('action', 'QuaterlyBudgetVarianceReport.html');
	$("#postMethodForm").submit();
}
/** ********************************************************** */

function varianceBudgetReset() {
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}
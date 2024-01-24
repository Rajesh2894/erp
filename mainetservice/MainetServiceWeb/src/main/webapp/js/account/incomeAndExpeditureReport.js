
function incomeAndExpenditureReport(formUrl, actionParam) {
	//debugger;
	var errorList = [];
	errorList = validateForm(errorList);
	   var reportType = $("input[name='reporttype']:checked").val();
	   var primaryHeadId = $('#primaryHeadId').val();
	   var primaryAcHeadDesc = $('#primaryHeadId option:selected').text();
	   var financialYear = $('#financialYearId option:selected').text();
	   var financialYearId = $('#financialYearId').val();
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		} else if (errorList.length == 0) {
	      var data = {
				"reportType" : reportType,
				"primaryHeadId" : primaryHeadId,
				"financialYearId" : financialYearId,
				"primaryAcHeadDesc":primaryAcHeadDesc,
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
	//debugger;
	   var reportType = $("input[name='reporttype']:checked").val();
	   var primaryHeadId = $('#primaryHeadId').val();
	   var financialYearId = $('#financialYearId').val();
	if (reportType == "0" || reportType == "" || reportType == null) {
		errorList.push(getLocalMessage('account.select.reportType'));
	}
	if (primaryHeadId == "" || primaryHeadId == null) {
		errorList.push(getLocalMessage('account.select.primaryHead'));
	}
	if (financialYearId == "" || financialYearId == null) {
		errorList.push(getLocalMessage('account.select.financialYear.id'));
	}
	return errorList;
}

function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp; '+ errorList[index] + '</li> &nbsp;';
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
	$("#postMethodForm").prop('action', 'incomeAndExpenditureSheduleReport.html');
	$("#postMethodForm").submit();
}
/** ********************************************************** */
function expenditute(){
	  var reportType = $("input[name='reporttype']:checked").val();
	  var data = {
				"reportType" : reportType,
			};
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading('incomeAndExpenditureSheduleReport.html' ,data,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
	  
}

function expenditute() {
	
	$('#primaryHeadId').html('');
	var reportType = $("input[name='reporttype']:checked").val();
	var requestUrl = "incomeAndExpenditureSheduleReport.html?primaryHeadType";
	var requestData = {
		"reportType" : reportType
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,'json');
	$('#primaryHeadId').append($("<option></option>").attr("value", "").attr("code", "").text("select"));
	var codeValue = null;
	$.each(ajaxResponse, function(index, value) {
		codeValue = index + " - " + value;
	$('#primaryHeadId').append($("<option></option>").attr("value", index).text(codeValue));
	});
	$('#primaryHeadId').trigger('chosen:updated');
}

function incomeExpendituterReset() {
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}

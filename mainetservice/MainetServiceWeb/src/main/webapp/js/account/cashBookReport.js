
function cashBookReport(formUrl, actionParam) {
	
	   var errorList = [];
	   errorList = validateForm(errorList);
	   
	   var fromDateId = $('#fromDateId').val();
	   var toDateId=$("#toDateId").val();
	   
	   
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		} else if (errorList.length == 0) {
	      var data = {
				 "fromDateId" : fromDateId,
				 "toDateId"   :toDateId
			};
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,data,'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
}
function validateForm(errorList) {
	var fromDateId = $('#fromDateId').val();
	var toDateId=$("#toDateId").val();
	if (fromDateId == "" || fromDateId == null) {
		errorList.push(getLocalMessage('account.select.fromDate'));
	}
	
	if (toDateId == "" || toDateId == null) {
		errorList.push(getLocalMessage('account.select.toDate'));
	}
	if (errorList.length > 0) {
		return errorList;
	}
	else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	  var eDate = new Date($("#fromDateId").val().replace(pattern,'$3-$2-$1'));
	  var sDate = new Date($("#toDateId").val().replace(pattern,'$3-$2-$1'));
	  if (eDate > sDate) {
		  errorList.push("To Date can not be less than From Date");
	    }
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
	$("#postMethodForm").prop('action', 'cashBookReport.html');
	$("#postMethodForm").submit();
}
/** ********************************************************** */

function cashBookReset() {
	//debugger;
	$("#postMethodForm").prop('action', '');
	$("#fromDateId").val("").trigger("chosen:updated");
	$("#toDateId").val("").trigger("chosen:updated");
	$('.error-div').hide();
	prepareTags();
}

/********************Add Function************/
function add(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************SearchFunction************/
function search(formUrl, actionParam) {
	if (!actionParam) {
		actionParam = "search";
	}
	var data = {
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************EditFunction************/
function getEdit(formUrl, actionParam, popId) {
	if (!actionParam) {
		actionParam = "edit";
	}
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************ViewFunction************/
function getView(formUrl, actionParam,popId) {
	if (!actionParam) {
		actionParam = "view";
	}
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/********************DeleteFunction************/
function deleteEmployee(formUrl, actionParam, popId) {
	if (!actionParam) {
		actionParam = "delete";
	}
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html');
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();
}


/*******************Datepicker Function************/
$(document).ready(function() {
		$("#complaintDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});

		$("#inspectionDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
		});
	});
	$("#complaintDateId").datepicker('setDate', new Date());
	$("#inspectionDateId").datepicker('setDate', new Date());
	
/**************Save function With Validation***********/	
function saveEmployeeVerificationForm(element){
		
		var errorList = [];
		errorList = validateForm(errorList);
		if (errorList.length > 0) {
			displayErrorsPage(errorList);
		}else if (errorList.length == 0) {
		      return saveOrUpdateForm(element,getLocalMessage('swm.employee.verify,submit.success'), 'EmployeeInspection.html', 'saveform');
	    }

}
function validateForm(errorList) {
	
	var RemarksId = $("#Remarks").val();
	if (RemarksId == "" || RemarksId == null) {
		errorList.push(getLocalMessage('swm.enter.remark'));
	}
	return errorList;
}	
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
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
/********************BackButton Function************/
function backButton() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'EmployeeInspection.html');
	$("#postMethodForm").submit();
}
/********************DataTable  Function************/
$(document).ready(function(){
	var table = $('.EmpIns').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true
	    });
		
});	
$(document).ready(function() {
	
	$('#datatables').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true
	});
	
	$('.datetimepicker').datetimepicker({
		format: 'LT'
	});
	
	
	$("#challanFromDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d'
	});
	$('#challanFromDate').attr('placeholder','');
	
	$("#challanToDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d'
	});
	$('#challanToDate').attr('placeholder','');
	
	var inputSelector = $("#eChallanSummaryForm input");
	inputSelector.on('keypress', function(e) {
	    if(e.which == 13) {
	    	e.preventDefault();
	    }
	});
});

/********************Add new Page Function************/
function addNewPage(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function validateSearchForm(errorList) {
	var errorList = [];
	if($("#challanFromDate").val( ) != '' && 
			$("#challanToDate").val() == ''){
		errorList.push(getLocalMessage("EChallan.validateChallanToDate"));
	}
	
	if($("#challanToDate").val( ) != '' && 
			$("#challanFromDate").val() == ''){
		errorList.push(getLocalMessage("EChallan.validateChallanFromDate"));
	}
	
	if($("#challanNo").val()=='' && $("#offenderName").val()=='' && $("#challanFromDate").val()=='' 
		&& $("#challanToDate").val()=='' && $("#offenderMobNo").val()=='' ){
		errorList.push(getLocalMessage("EChallan.selectCriteria"));
	}
	
	return errorList;
}

/********************SearchFunction************/
function searchChallanData(formUrl, actionParam) {
	showloader(true);
	setTimeout(function() {
		var errorList = [];
		var fromDate=$('#challanFromDate').val();
		var toDate=$('#challanToDate').val();
		
		var data = {
			"challanNo" : $('#challanNo').val(),
			"raidNo" : $('#raidNo').val(),
			"offenderName" : $('#offenderName').val(),
			"challanFromDate" : $('#challanFromDate').val(),
			"challanToDate" : $('#challanToDate').val(),
			"offenderMobNo" : $('#offenderMobNo').val()
		};

		errorList = validateSearchForm();
		
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var fDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var tDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (fDate > tDate) {
			errorList.push(getLocalMessage("EChallan.toDateNotLessThanFromDate"));
		}
		
		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			$("#errorDiv").hide();

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			// prepareTags();
		}
		showloader(false);
	}, 200);

}

function getActionForDefination(challanId, mode) {
	var divName = '.content-page';
    var requestData = {
	"saveMode" : mode,
	"challanId" : challanId
    };
    var ajaxResponse = doAjaxLoading('EChallanEntry.html?ViewForm', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}

/********** Print button ***************/
function printContent(challanId) {
	var divName = '.content-page';
    var requestData = {
	"challanId" : challanId
    };
    var ajaxResponse = doAjaxLoading('EChallanEntry.html?receiptDownload', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}

/******************** reset button ************/
function resetForm() {
	$('#challanNo').val('');
	$('#offenderName').val('');
	$('#challanFromDate').val('');
	$('#challanToDate').val('');
	$('#offenderMobNo').val('');
	
	$('.error-div').hide();
}





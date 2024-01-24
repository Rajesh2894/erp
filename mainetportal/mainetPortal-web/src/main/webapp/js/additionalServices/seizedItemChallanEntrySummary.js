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
	});
	$('#challanFromDate').attr('placeholder','');
	
	$("#challanToDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d'
	});
	$('#challanToDate').attr('placeholder','');
	
	$("#raidDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d'
	});
	$('#raidDate').attr('placeholder','');
});


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
	
	if($("#raidNo").val()=='' && $("#offenderName").val()=='' && $("#challanFromDate").val()=='' 
		&& $("#challanToDate").val()=='' && $("#offenderMobNo").val()=='' ){
		errorList.push('Select any one criteria');
	}
	return errorList;
}

/********************SearchFunction************/
function searchRaidData(formUrl, actionParam) {
	showloader(true);
	setTimeout(function() {
		var errorList = [];
		var data = {
			"raidNo" : $('#raidNo').val(),
			"offenderName" : $('#offenderName').val(),
			"challanFromDate" : $('#challanFromDate').val(),
			"challanToDate" : $('#challanToDate').val(),
			"offenderMobNo" : $('#offenderMobNo').val()
		};

		errorList = validateSearchForm();

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
			prepareTags();
		}
		showloader(false);
	}, 200);
}

function getActionForDefination(challanId, mode) {
	var divName = '.content-page';
    var requestData = {
     "challanId" : challanId,
	 "saveMode" : mode
    };
    var ajaxResponse = doAjaxLoading('SeizedItemChallanEntry.html?ViewForm', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}

function proceedSave(element) {
	
	return saveOrUpdateForm(element,"Raid Number Saved Successfully", 
					'SeizedItemChallanEntry.html?printAcknowledgement', 'saveform');
}

/********** Print button ***************/
function printContent(challanId) {
	debugger;
	var divName = '.content-page';
    var requestData = {
	"challanId" : challanId
    };
    var ajaxResponse = doAjaxLoading('SeizedItemChallanEntry.html?receiptDownload', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}


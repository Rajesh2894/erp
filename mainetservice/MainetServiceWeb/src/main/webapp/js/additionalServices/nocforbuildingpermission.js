$(document).ready(function() {

	prepareDateTag();
	$("#nocbuildpermissionSummaryDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

$(document).ready(function() {

	// var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	$("#brDob").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : today,
		maxDate : today
	});
	$("#brDob").datepicker('setDate', new Date());
	
	$("#refDob").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : today
	});
	$("#refDob").datepicker('setDate', new Date());
});




function resetMemberMaster(resetBtn) {

	/* $('#designation').val('').trigger('chosen:updated'); */
	resetForm(resetBtn);
}

function refreshServiceData(element, formUrl, actionParam) {

	var requestData = element;
	var divName = '.content-page'
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;

	var requestData = __serializeForm(theForm);
	var serviceName = $("#serviceId>option:selected").text();
	var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
			requestData, false, '', 'html');
	$(divName).html(response);
	$(divName).removeClass('ajaxLoader');
	prepareTags();

}

function getServiceList(element, formUrl, actionParam) {
	var requestData = element;
	var divName = '.pagediv'
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;

	var requestData = __serializeForm(theForm);
	var serviceName = $("#serviceId>option:selected").text();
	var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
			requestData, false, '', 'html');
	$(divName).html(response);
	$(divName).removeClass('ajaxLoader');
	$(divName).show();
	prepareTags();
}

function saveData(element) {
	
	var errorList = [];
	var status;
	errorList = validateData();
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		status = saveOrUpdateForm(element, "",
				'NOCForBuildingPermission.html', 'saveform');
		nocAcknow(status);

	}
}

function nocAcknow(status) {

	var URL = 'NOCForBuildingPermission.html?printNOCAck';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	var title = 'NOC For Building Permission Acknowlegement';
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<html><link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function validateData(errorList) {
	
	var errorList = [];
	var date = $('#date').val();
	var sex = $("#sex").val();
	var fName = $("#fName").val();
	var mName = $("#mName").val();
	var lName = $("#lName").val();
	var titleId = $("#titleId").val();
	var applicationType = $("#applicationType").val();
	//var buildingPermissionAppNo = $("#buildingPermissionAppNo").val();
	var surveyNo = $("#surveyNo").val();
	var plotNo = $("#plotNo").val();
	var citiySurveyNo = $("#citiySurveyNo").val();
	var address = $("#address").val();
	var location = $("#location").val();
	var plotArea = $("#plotArea").val();
	var builtUpArea = $("#builtUpArea").val();
	var applicantAddress = $("#applicantAddress").val();
	var refNo = $("#refNo").val();
	var fNo = $("#fNo").val();
	var refDate = $('#refDob').val();
	var usageType1 = $("#usageType1").val();
	
	
	var rowcount = $("#NOCTable tr").length

	if (titleId == "0") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.titleId"));
	}
	
	if (fName == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.fName"));
	}
	if (lName == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.lName"));
	}
	if (sex == "0") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.sex"));
	}
	if (applicationType == "0") {
		errorList
				.push(getLocalMessage("NOCBuildingPermission.label.applicationType"));
	}
	/*if (buildingPermissionAppNo == "") {
		errorList
				.push(getLocalMessage("NOCBuildingPermission.label.buildingPermissionAppNo"));
	}*/
	
	if (applicantAddress == "") {
		
		errorList.push(getLocalMessage("NOCBuildingPermission.label.applicantAddress"));
	}
	
	if (refNo == "") {
		
		errorList.push(getLocalMessage("NOCBuildingPermission.label.refNo"));
	}
	if (fNo == "") {
		
		errorList.push(getLocalMessage("NOCBuildingPermission.label.fno"));
		
	}

	if (refDate == "") {
		
		errorList.push(getLocalMessage("NOCBuildingPermission.label.refDate"));
	}
	if (usageType1 == "0") {
		
		errorList.push(getLocalMessage("NOCBuildingPermission.label.usageType1"));
		
	}
	
	/*if (plotNo == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.plotNo"));
	}*/
	
	/*if (address == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.address"));
	}*/
	/*if (location == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.location"));
	}*/

	if (plotArea == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.plotArea"));
	}
	else if(isNaN(plotArea))
		{
		errorList.push(getLocalMessage("NOCBuildingPermission.valid.plotArea"));
		}
	if (builtUpArea == "") {
		errorList
				.push(getLocalMessage("NOCBuildingPermission.label.builtUpArea"));
	}
	else 
	if(builtUpArea != "" && isNaN(builtUpArea))
	{
		errorList
		.push(getLocalMessage("NOCBuildingPermission.valid.builtUpArea"));
	}
	
	
	/*for (var i = 0; i < rowcount - 1; i++) {
		var checklistUploadedOrNot = $("#checkList" + i).val();
		if (checklistUploadedOrNot == "") {
			errorList.push(getLocalMessage("NOCBuildingPermission.checklist"));
		}
	}
*/
	return errorList;
}

function checkDate(errorList) {

	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}

function searchData(element) {
	

	var errorList = [];
	var divName = '.content-page';
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var apmApplicationId = $("#apmApplicationId").val();
	errorList = validateSearchForm(errorList);
	if (errorList.length > 0) {
		checkDate(errorList);
		displayErrorsOnPage(errorList);
	} else {
		var table = $('#nocbuildpermissionSummaryDataTable').DataTable();
		var url = "NOCForBuildingPermission.html?searchData";
		var requestData = "&apmApplicationId=" + $("#apmApplicationId").val()
				+ "&fromDate=" + $("#fromDate").val() + "&toDate="
				+ $("#toDate").val();
		var ajaxResponse = doAjaxLoading(url, requestData, 'html', divName);

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		$('.fromDate').val(fromDate);
		$('.toDate').val(toDate);
		$('.apmApplicationId').val(apmApplicationId);
	}
}

function validateSearchForm(errorList) {

	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var apmApplicationId = $("#apmApplicationId").val();
	var date = new Date();
	if ((fromDate == "" && toDate == "") && apmApplicationId == "") {
		errorList
				.push(getLocalMessage('noc.enter.search.criteria'));
	} else if (apmApplicationId != "") {
		// go for search
	}

	else if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("noc.fromDate.not.empty"));
	} else if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("noc.toDate.not.empty"));
	} else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var fDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var tDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (fDate > tDate) {
			errorList
					.push(getLocalMessage("noc.toDate.not.less.fromDate"));
		}
		if (tDate >= date) {
			errorList
					.push(getLocalMessage("noc.toDate.not.greater.currentDate"));
		}

	}

	return errorList;

}

function modify(bpId, formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : bpId
	};
	// $('#brDob').prop("disabled", true);
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}


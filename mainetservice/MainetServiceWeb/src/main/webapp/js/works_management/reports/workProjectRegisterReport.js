
$(document).ready(function() { $(function()
	 {
	$('.datetimepicker').datetimepicker({
		dateFormat: 'dd/mm/yy',
		timeFormat: "hh:mm tt",
		changeMonth: true,
		changeYear: true,
		//yearRange: "0:+100",
		//maxDate:'now'
	});
	
	$("#proRegTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true
	});
	
 });
});

function getProjectName(obj) {	
	
	var requestData = {
		//"orgId" : $('#orgId').val(),
		"wmSchId" : $(obj).val()
	};
	$('#projId').html('');
	$('#projId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('ProjectRegisterReport.html?projectName',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);
	$('#projId').append(
			$("<option></option>").attr("value", "-1").text(
					("All")));
	$.each(prePopulate, function(index, value) {
		$('#projId').append(
				$("<option></option>").attr("value", value.projId).text(
						(value.projNameEng)));
	});
	$('#projId').trigger("chosen:updated");
}

function viewProjectRegisterReport(obj) {	
	
	var errorList = [];
	var projId =  $("#projId").val();
	var schId =  $("#schId").val();
	var workType = $("#workType").val();
	
	if (projId == "") {
		errorList.push(getLocalMessage("work.Def.valid.select.projName"));
	}
	if (workType == "" || workType == "0") {
		errorList.push(getLocalMessage("work.Def.valid.select.worktype"));
	}
	
	var requestData = {
			"orgId" : $('#orgId').val(),
			"projId" : projId,
			"schId" : schId,
			"workType" : workType
		};
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = '&projId=' + projId + '&schId=' + schId + '&workType=' + workType;

		var ajaxResponse = doAjaxLoading(
				'ProjectRegisterReport.html?getWorkProjectRegisterReport',
				requestData, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}
function printdiv(printpage) {
	
	var dataTable = $('#proRegTbl').DataTable();
	if ($.fn.DataTable.isDataTable('#proRegTbl')) {
		$('#proRegTbl').DataTable().destroy();
	}
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML; 
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	dataTable = $('#proRegTbl').DataTable();
	return false;

}

function backReportToApprovalForm() {
	
	var divName = '.content-page';
	var url = "WorkEstimateApproval.html?showApprovalCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function backReportForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ProjectRegisterReport.html');
	$("#postMethodForm").submit();
}

function resetFormProject(resetBtn) {	
	resetBtn.form.reset();
	$('[id*=file_list]').html('');
    $("#schId").trigger("chosen:updated");
    $("#projId").trigger("chosen:updated");
    $("#workType").trigger("chosen:updated");
    prepareTags();
}
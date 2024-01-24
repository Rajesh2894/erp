function getWorkName(obj) {	
	
	var requestData = {
		"orgId" : $('#orgId').val(),
		"projId" : $(obj).val()
	};
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('ApprovalLetterPrint.html?worksName',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

function getSanctionNumber(obj) {	
	
	var requestData = {
		//"orgId" : $('#orgId').val(),
		"workId" : $(obj).val()
	};
	$('#sanctionNo').html('');
	$('#sanctionNo').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('ApprovalLetterPrint.html?sanctionNumber',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#sanctionNo').append(
				$("<option></option>").attr("value", value.workSancNo).text(
						(value.workSancNo)));
	});
	$('#sanctionNo').trigger("chosen:updated");
}

function viewWorkReport()
{
	
	var errorList = [];
	var projId = $("#projId").val();
	var workId = $("#workId").val();
	var workSancNo = $("#sanctionNo").val();
	
	if (projId == "") {
		errorList.push(getLocalMessage("work.Def.valid.select.projName"));
	}
	if (workId == "" || workId == "0") {
		errorList.push(getLocalMessage("work.estimate.select.work.name"))
	}
	if (workSancNo == "" || workSancNo == "0") {
		errorList.push(getLocalMessage("work.estimate.sanction.no"))
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);	
	} 
	else {
	
	var divName = '.content-page';
	var actionParam = {
			'projId' : projId,
			'workId' :workId,
			'workSancNo' :workSancNo
		}
	var url = "ApprovalLetterPrint.html?viewWorkReport";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	}

}

function backReportForm() 
{
	var mode = $("#saveMode").val();
	if(mode== 'AP'){
		window.location.href='AdminHome.html';
	}else{
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'ApprovalLetterPrint.html');
		$("#postMethodForm").submit();
	}	
}

function printdiv(printpage)
{
	
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

function resetApprovalLetterPrint() {
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}


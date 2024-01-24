/*
 * Vishwajeet.Kumar 
 * 01/02/2018
 */

$(document).ready(function() {	
	$('.fancybox').fancybox();

	var grandTotal = 0;
	$('#calculation tr').each(function(i) {		
		var totalAmount = $("#amount" + i).text();
		if (totalAmount != 0) {
			grandTotal += isNaN(Number(totalAmount)) ? 0 : Number(totalAmount);
		}
	});
	
	$("#totalEstimate").text(grandTotal.toFixed(2));

});

function getWorkName(obj) {	
	var requestData = {
		"orgId" : $('#orgId').val(),
		"projId" : $(obj).val(),
        "workType" : $("#workType").val()
	
	};
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('WorkEstimateReport.html?worksName',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);
	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

//Defect #85135
function getProjList(obj) {	
	var errorList = [];
	var requestData = {	
		"deptId" : $(obj).val(),
		"orgId" : $('#orgId').val()
	};
	$('#projId').html('');
	$('#projId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('WorkEstimateReport.html?projList',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	if(prePopulate.length == '0'){
		errorList.push(getLocalMessage("work.Def.valid.proj"));
		displayErrorsOnPage(errorList);
		return false;
	}
	$.each(prePopulate, function(index, value) {
		var lang=$('#lang').val();
		if(lang == 1){
		$('#projId').append(
				$("<option></option>").attr("value", value.projId).text(
						(value.projNameEng)));
		}
		else{
			
			$('#projId').append(
					$("<option></option>").attr("value", value.projId).text(
							(value.projNameReg)));	
		}
	});
	$('#projId').trigger("chosen:updated");
}
function viewWorkReport(element) {	
	
	var errorList = [];
	var deptId = $("#deptId").val();
	var workType = $("#workType").val();
	var projId = $("#projId").val();
	var workId = $("#workId").val();
	var reportType = $("#reportType").find("option:selected").attr('code');

	
	if (deptId == "") {
		errorList.push(getLocalMessage("work.Def.valid.select.execut.dept"));
	}
	if (workType == "") {
		errorList.push(getLocalMessage("work.Def.valid.select.worktype"));
	}

	if (projId == "") {
		errorList.push(getLocalMessage("work.Def.valid.select.projName"));
	}

	if (workId == "" || workId == "0") {
		errorList.push(getLocalMessage("work.estimate.select.work.name"))
	}

	if (reportType == "" ||reportType == undefined) {
		errorList.push(getLocalMessage("please.select.report.type"))
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = '&deptId=' + deptId+ '&workType=' + workType +'&projId=' + projId + '&workId=' + workId
				+ '&reportType=' + reportType;

		var ajaxResponse = doAjaxLoading(
				'WorkEstimateReport.html?getWorkEstimateAbstractSheet',
				requestData, 'html');
		if(ajaxResponse ==''){
			errorList.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			return false;
		}else{
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}		
	}
}

function printdiv(printpage) {
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
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

function resetReport(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WorkEstimateReport.html');
	$("#postMethodForm").submit();
}

function backReportForm(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WorkEstimateReport.html');
	$("#postMethodForm").submit();
}


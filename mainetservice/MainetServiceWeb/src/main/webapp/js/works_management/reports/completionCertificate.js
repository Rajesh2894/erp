$(document).ready(function() {
	
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#datatables").DataTable({
		"oLanguage" : {
			"sSearch" : ""
		}
	});
});
function getWorkName(obj, flag) {
	$("#errorDiv").hide();
	var requestData = {
		"projId" : $(obj).val(),
		"flag" : flag,
	}
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('CompletionCertificate.html?worksName',
			'post', requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}
function getCompletionForm() {
	var divName = formDivName;
	var url = "CompletionCertificate.html?getCompletionForm";
	data = {};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}
function getWorkDetails(obj) {
	var divName = formDivName;
	var url = "CompletionCertificate.html?getWorkDetails";
	var errorList = [];

	data = {
		"workId" : $(obj).val()
	};
	var onSuccessCallback = function(response) {
		$("#workName").val(response.workName);
		$("#workStartDate").val(response.workStartDate);
		$("#workEndDate").val(response.workEndDate);
		$("#workType").val(response.workTypeDesc);
		$("#contractNo").val(response.contractNo);
		$("#contractStartDate").val(response.contractStartDate);
		$("#contractEndDate").val(response.contractEndDate);
		$("#contractorName").val(response.contractorName);
		$("#assetCategory").val(response.assetCategory);
	};

	var onErrorCallback = function(response) {
		errorList.push(getLocalMessage('wms.TenderWorkEntryNotFound'));
		getCompletionForm();
		displayErrorsOnPage(errorList);
	};
	var response = __doAjaxRequestWithCallback(url, 'post', data, false,
			'json', onSuccessCallback, onErrorCallback);

}

function getAssetDetails(obj) {
	
	var errorList = [];
	var divName = formDivName;
	var url = "CompletionCertificate.html?getAssetDetails";
	data = {
		astSerialNo : $("#astSerialNo").val()
	};
	var onSuccessCallback = function(response) {
		$("#assetClass1Desc").val(response.assetClass1Desc);
		$("#assetClass2Desc").val(response.assetClass2Desc);
	};

	var onErrorCallback = function(response) {
		errorList.push(getLocalMessage('wms.AssetNumberNotFound'));
		displayErrorsOnPage(errorList);
	};

	var response = __doAjaxRequestWithCallback(url, 'post', data, false,
			'json', onSuccessCallback, onErrorCallback);

}

function saveCompletionForm(obj) {
	var errorList = [];
	var projId = $("#projId").val();
	var workId = $("#workId").val();
	var assetCategory = $("#assetCategory").val();
	var assetName = $("#assetName").val();
	var assetDescription = $("#assetDescription").val();
	var purpose = $("#purpose").val();
	var completionDate = $("#completionDate").val();
	var acquisitionDate = $("#acquisitionDate").val();
	if (projId == '') {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (workId == '') {
		errorList.push(getLocalMessage('work.estimate.select.work.name'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (assetCategory == '') {
		errorList.push(getLocalMessage('work.estimate.select.asset.class'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (assetName == '') {
		errorList.push(getLocalMessage('work.estimate.select.asset.name'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (assetDescription == '') {
		errorList.push(getLocalMessage('work.estimate.enter.asset.description'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (purpose == '') {
		errorList.push(getLocalMessage('work.estimate.select.asset.purpose'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (acquisitionDate == '') {
		errorList.push(getLocalMessage('work.estimate.select.acquisition.date'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (completionDate == '') {
		errorList.push(getLocalMessage('wms.SelectCompletionDate'));
		displayErrorsOnPage(errorList);
		return false;
	}

	if ($.datepicker.parseDate('dd/mm/yy', $("#completionDate").val()) < $.datepicker
			.parseDate('dd/mm/yy', $("#contractStartDate").val()))
		/*Defect #78823*/
			/*|| $.datepicker.parseDate('dd/mm/yy', $("#completionDate").val()) > $.datepicker
					.parseDate('dd/mm/yy', $("#contractEndDate").val()))*/ {

		errorList
				.push(getLocalMessage("wms.SelectCompletionDateBetweenContractStartDateEnddate"));
		displayErrorsOnPage(errorList);
		return false;

	}
	if (projId != '') {
		if (workId == '') {
			errorList.push(getLocalMessage('work.estimate.select.work.name'));
			displayErrorsOnPage(errorList);
			return false;
		}
	}
	if (errorList.length == 0) {
		
		var formName = findClosestElementId(obj, 'form');
		 var theForm = '#' + formName; 
		 var requestData = __serializeForm(theForm);
		var url = "CompletionCertificate.html";
		//return saveOrUpdateForm(obj, "", url, 'saveform');
		
		/*Defect #93342*/
		var data = __doAjaxRequest("CompletionCertificate.html?saveAndPrintCompletionReport", 'POST',requestData, false,'json');
		var message='';
		var Proceed = getLocalMessage("works.management.proceed");
		var childDivName = '.msg-dialog-box';
		
		 message += '<h4 class=\"text-center text-blue-2 padding-12\">'+data.sucessMsg+'</h4>';
		 message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
				+ ' onclick="getActionForDefination('+data.workId+');"/>' + '</div>';
		
		$(childDivName).addClass('ok-msg').removeClass('warn-msg');
		$(childDivName).html(message);
		$(childDivName).show();
		$('#Proceed').focus();	   
		showModalBox(childDivName);	
	} else {
		displayErrorsOnPage(errorList);
	}

}


function searchCompletion() {
	
	$("#errorDiv").hide();
	var errorList = [];

	var projId = $("#projId").val();
	var workId = $("#workId").val();
	var completionNo = $("#completionNo").val().trim();

	if (projId == '' && workId == '' && completionNo == '') {
		errorList.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
		return false;
	}

	if (projId != '' || workId != '' || completionNo != '') {

		var requestData = '&projId=' + projId + '&workId=' + workId
				+ '&completionNo=' + completionNo;
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'CompletionCertificate.html?getAllCompletionData', requestData,
				'html');
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();

			$.each(prePopulate,function(index) {
				var obj = prePopulate[index];
				result.push([
					obj.completionNo,
					obj.completionDate,
					obj.projName,
					obj.workName,
					obj.contractorName,
					
					'<td class="text-center">'
					+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="getActionForDefination(\''
					+ obj.workId
					+ '\')"  title="Print Completion"><i class="fa fa-print"></i></button>',
					'</td>' ]);
			});
			table.rows.add(result);
			table.draw();
		}
	}
}

function getActionForDefination(workId) {
	$.fancybox.close();
	var divName = '.content-page';
	$("#errorDiv").hide();
	var requestData = '&workId=' + workId;

	var ajaxResponse = doAjaxLoading(
			'CompletionCertificate.html?getCompletionCertificateData',
			requestData, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
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
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}
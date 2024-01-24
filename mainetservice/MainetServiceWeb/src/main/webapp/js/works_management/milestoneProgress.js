$(document).ready(function() {
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	// otherTask();

	$('#datatables').DataTable({
		"oLanguage" : {
			"sSearch" : ""
		}
	});

});
function getWorkName(obj) {

	var requestData = {
		"projId" : $(obj).val()
	}
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var response = __doAjaxRequest('MilestoneProgress.html?worksName', 'post',
			requestData, false, 'html');
	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

function searchMilestone() {

	var errorList = [];
	var totalWeightage = 0;
	var projId = $("#projId").val();
	var workId = $("#workId").val();

	if (projId == '') {
		errorList
				.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
		return false;
	}

	if (projId != '' || workId != '') {

		var requestData = '&projId=' + projId + '&workId=' + workId;
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'MilestoneProgress.html?getAllMileStoneData', requestData,
				'html');
		var updatemsg=getLocalMessage("scheme.master.update.progress");
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							result
									.push([
											index + 1,
											obj.mileStoneDesc,
											obj.mileStoneWeight,
											obj.msStartDate,
											obj.msEndDate,

											'<td class="text-center" align="center">'
													+ '<button type="button" class="btn btn-primary btn-sm  margin-right-10" onclick="getActionForDefination(\''
													+ obj.mileId
													+ '\')" title="'+updatemsg+'"><i class="fa fa-line-chart" align="center"></i></button>'
													+ '</td>' ]);

						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
	}
}

function getActionForDefination(mileId) {

	var divName = formDivName;
	var url = "MilestoneProgress.html?milestoneProgress";
	data = {
		"mileId" : mileId
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function fileCountUpload(obj) {

	var row = $("#attachDoc tbody .appendableClass").length;
	$("#length").val(row);
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	var response = __doAjaxRequest('MilestoneProgress.html?fileCountUpload',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
}

function doFileDeletephoto(listElement, id) {

	$('#' + id).hide();
	fileArray.push(listElement);
	$('#removeFileById').val(fileArray);
}

function validate() {

	var errorList = [];
	var arrStartDate = $("#startDate").val().split("/");
	var start = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var arrEndDate = $("#endDate").val().split("/");
	var end = new Date(arrEndDate[2], arrEndDate[1] - 1, arrEndDate[0]);

	$("#attachDoc tbody .appendableClass")
			.each(
					function(i) {
						var phyPercent = $("#phyPercent" + i).val();
						var proDate = $("#proUpdateDate" + i).val();

						var rowCount = i + 1;

						if (phyPercent == "" || phyPercent == null) {
							errorList
									.push(getLocalMessage("milestone.progress.phyPercent")
											+ rowCount);
						}
						if (proDate == "" || proDate == null) {
							errorList
									.push(getLocalMessage("milestone.progress.progressDate")
											+ rowCount);
						}

						if (phyPercent != "" && phyPercent > 100) {
							errorList
									.push(getLocalMessage("milestone.progress.phyPercentValue")
											+ rowCount);
						}

						if ($.datepicker.parseDate('dd/mm/yy', $(
								"#proUpdateDate" + i).val()) < new Date(start)
								|| $.datepicker.parseDate('dd/mm/yy', $(
										"#proUpdateDate" + i).val()) > new Date(
										end)) {

							errorList
									.push(getLocalMessage("milestone.progress.progressDateBetween")
											+ rowCount);

						}

						if (i > 0) {

							if ($.datepicker.parseDate('dd/mm/yy', $(
									"#proUpdateDate" + (i - 1)).val()) > $.datepicker
									.parseDate('dd/mm/yy', $(
											"#proUpdateDate" + i).val())) {

								errorList
										.push(getLocalMessage("milestone.progress.progressDatePrevious")
												+ rowCount);

							}
						}

						if (parseFloat($("#phyPercent" + i).val()) <= parseFloat($(
								"#phyPercent" + (i - 1)).val())) {
							errorList
									.push(getLocalMessage("milestone.progress.presentProgressValue")
											+ rowCount);
						}
					});
	return errorList;
}

function saveProgress(obj) {
	var errorList = validate();
	if (errorList.length == 0) {
		var url = "MilestoneProgress.html";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function doFileDeletion(obj, id) {

	requestData = {
		"id" : id
	};
	url = 'MilestoneProgress.html?doEntryDeletion';
	var row = $("#attachDoc tbody .appendableClass").length;
	if (row != 1) {
		var response = __doAjaxRequest(
				'MilestoneProgress.html?doEntryDeletion', 'POST', requestData,
				false, 'html');
	}

}
$("#attachDoc").on("click", '.delButton', function(e) {

	var row = $("#attachDoc tbody .appendableClass").length;
	if (row != 1) {
		$(this).parent().parent().remove();
	}
	e.preventDefault();
});

function otherTask() {

	var requestData = $.param($('#milestoneProgress').serializeArray());

	var response = __doAjaxRequest('MilestoneProgress.html?getUploadedImage',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}

function otherDeletionTask(obj){
	var requestData = $.param($('#milestoneProgress').serializeArray());

	var response = __doAjaxRequest('MilestoneProgress.html?getUploadedImage',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
}

function prepHref(obj, src) {

	obj.href = src;
}

jQuery('.numbersOnly').keyup(function() {

	this.value = this.value.replace(/[^0-9]/g, '');
});
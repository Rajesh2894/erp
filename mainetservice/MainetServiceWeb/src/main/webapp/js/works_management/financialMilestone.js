var total = 0;
$(document).ready(function() {
	
	$('#milestoneTbl').DataTable().destroy();
	$("#milestoneTbl tbody tr").each(function(i) {

		var mileStoneWeight = $("#mileStoneWeight" + i).val();
		total += parseFloat(mileStoneWeight);
		if (mileStoneWeight != "")
			$("#totalWeightage").val(total);
	});

	triggerTable();
	$('.fancybox').fancybox();
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#datatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true

	});
	$(".reset").click(function() {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'FinancialMilestone.html');
		$("#postMethodForm").submit();

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
	var response = __doAjaxRequest('FinancialMilestone.html?worksName', 'post',
			requestData, false, 'html');
	// var ajaxResponse = __doAjaxRequest('PhysicalMilestone.html?worksName',
	// requestData, 'html');

	var prePopulate = JSON.parse(response);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workId').trigger("chosen:updated");
}

function getCreateworkNameByProjId(obj) {
	
	var errorList = [];
	checkMilestone();
	var checkMileStone = $("#checkMileStone").val();
	if (checkMileStone == 'true') {
		errorList.push(getLocalMessage('wms.MileStoneEntriesAlreadyExsist'));
	}
	var requestData = {
		"projId" : $(obj).val()
	}
	$('#workId').html('');
	$('#workId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading(
			'FinancialMilestone.html?worksNameByProjId', requestData, 'html');

	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#workId').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)).attr("code",value.workName));
	});
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
	$('#workId').trigger("chosen:updated");

}

function searchFinancialMileStone() {
	var errorList = [];

	var projId = $("#projId").val();
	var workId = $("#workId").val();
	// var mileStoneFlag = $("#mileStoneFlag").val();

	if (projId == '') {
		errorList.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
		return false;
	}

	if (projId != '' || workId != '') {

		var requestData = '&projId=' + projId + '&workId=' + workId;
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'FinancialMilestone.html?getAllMileStoneData', requestData,
				'html');
		var updateProgress=getLocalMessage("scheme.master.update.progress");
		var viewProgress=getLocalMessage("work.management.viewtool");
		var editProgress=getLocalMessage("work.management.edittool");
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							var lang=$('#lang').val();
							if(lang==1){
								obj.projNameEng=obj.projNameEng;
								
							}
							
							else{
								obj.projNameEng=obj.projNameReg;
							}
							result
									.push([
											obj.projCode,
											obj.projNameEng,
											obj.workName,
											/*obj.startDate,
											obj.endDate,*/
											obj.projActualCost,
											'<td class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="getActionForDefination(\''
													+ obj.projId
													+ '\',\'V\','
													+ obj.workId
													+ ',\''
													+ obj.workName
													+ '\')" title="'+viewProgress+'"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-5" onclick="getActionForDefination(\''
													+ obj.projId
													+ '\',\'E\','
													+ obj.workId
													+ ',\''
													+ obj.workName
													+ '\')"  title="'+editProgress+'"><i class="fa fa-pencil-square-o"></i></button>',
											'</td>'
													+ '<td class="text-center" align="center">'
													+ '<button type="button" class="btn btn-primary btn-sm  margin-right-5" onclick="getActionForDefination(\''
													+ obj.projId
													+ '\',\'F\','
													+ obj.workId
													+ ',\''
													+ obj.workName
													+ '\')"  title="'+updateProgress+'"><i class="fa fa-line-chart"></i></button>'
													+ '</td>' ]);
						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			showErr(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
	}
}
function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function openMilestoneAddForm(mode) {

	var errorList = [];
	var url = "FinancialMilestone.html?checkForProjId";
	var isDefaulValue = __doAjaxRequest(url, 'POST', {}, false, 'json');

	if (isDefaulValue == "N") {
		errorList.push(getLocalMessage("project.not.defined"));
		displayErrorsOnPage(errorList);
	} else {
		var divName = formDivName;
		var requestData = {
			"mode" : mode
		}
		var url = "FinancialMilestone.html?AddFinancialMilestone";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(response);
	}
}

function getActionForDefination(projId, formMode, workId, workName) {
	var divName = formDivName;
	var url = "FinancialMilestone.html?editMilestone";
	data = {
		"projId" : projId,
		"formMode" : formMode,
		"workId" : workId,
		"workName" : workName
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function openFinancialMilestoneAddForm(mode) {
	
	var divName = formDivName;
	var requestData = {
		"mode" : mode
	}
	var url = "FinancialMilestone.html?AddFinancialMilestone";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);

}

function addEntryData() {
	
	var errorList = [];
	errorList = validateEntryDetails();
	if (errorList.length == 0) {
		addTableRow('milestoneTbl');
	} else {
		$('#milestoneTbl').DataTable({
			"oLanguage" : {
				"sSearch" : ""
			}
		});
		displayErrorsOnPage(errorList);
	}
}

function saveData(obj) {
	var errorList = [];
	var checkMileStone = $("#checkMileStone").val();
	var projId = $("#projId").val();
	var workId = $("#workId").val();
	var workName=$('#workId option:selected').attr('code');
	if ($.fn.DataTable.isDataTable('#milestoneTbl')) {
		$('#milestoneTbl').DataTable().destroy();
	}
	if (checkMileStone == 'true') {
		if (workId == null || workId == "")
			errorList.push(getLocalMessage('wms.MileStoneEntriesAlready'));
	}
	var requestData = {
			"workId" : workId,
	}	
		var url = "FinancialMilestone.html?getWorkDetails";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$("#milestoneTbl tbody tr")
			.each(
					function(i) {
						var msStartDate = $("#msStartDate" + i).val();
						var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
						var fDate = new Date(response.replace(pattern,'$3-$2-$1'));
						var sDate = new Date(msStartDate.replace(pattern,'$3-$2-$1'));
						var rowCount = i + 1;
						if (fDate > sDate) {
							errorList.push(getLocalMessage("wms.startDate.notLess.commencementDate")+ " " + response);
							displayErrorsOnPage(errorList);
							return false;
						}
					});
		
	if (errorList.length == 0)
		errorList = validateEntryDetails();
	if (errorList.length == 0) {
		var url = "FinancialMilestone.html";
		var res=saveOrUpdateForm(obj, "", url, 'saveform');
		$('#workId').html('');
		$('#workId').append(
				$("<option></option>").attr("value", workId).text(
						(workName)).attr("code", workName));
		$('#workId').prop('disabled', true);
	} else {
		displayErrorsOnPage(errorList);
		$('#milestoneTbl').DataTable({
			"oLanguage" : {
				"sSearch" : ""
			}
		});
	}

}

function validateEntryDetails() {
	var errorList = [];
	var totalWeightage = 0;
	if ($.fn.DataTable.isDataTable('#milestoneTbl')) {
		$('#milestoneTbl').DataTable().destroy();
	}
	var projId = $("#projId").val();
	if (projId == "" || projId == null) {
		errorList.push(getLocalMessage("project.master.vldn.projectname"));
	}
	if (errorList == 0)
		$("#milestoneTbl tbody tr")
				.each(
						function(i) {

							var mileStoneDesc = $("#mileStoneDesc" + i).val();
							var mileStoneWeight = $("#mileStoneWeight" + i)
									.val();
							var msStartDate = $("#msStartDate" + i).val();
							var msEndDate = $("#msEndDate" + i).val();

							var rowCount = i + 1;

							if (mileStoneDesc == "" || mileStoneDesc == null) {
								errorList
										.push(getLocalMessage("mileStone.financial.desc")
												+ rowCount);
							}

							if (mileStoneWeight == ""
									|| mileStoneWeight == null) {
								errorList
										.push(getLocalMessage("mileStone.financial.weight")
												+ rowCount);
							}
							if (msStartDate == "" || msStartDate == null) {
								errorList
										.push(getLocalMessage("mileStone.financial.startDate")
												+ rowCount);
							}
							if (msEndDate == "" || msEndDate == null) {
								errorList
										.push(getLocalMessage("mileStone.financial.endDate")
												+ rowCount);
							}
							if (parseFloat(mileStoneWeight) > 100) {
								errorList
										.push(getLocalMessage("mileStone.financial.weightagRange"));
							}
							if (msStartDate != "" && msEndDate != "")
								if ((compareDate(msStartDate)) > compareDate(msEndDate))
									errorList
											.push(getLocalMessage("mileStone.financial.dateRange")
													+ rowCount);
							totalWeightage += parseFloat(mileStoneWeight);
						});
	if (totalWeightage > 100) {
		$("#totalWeightage").val(totalWeightage);
		errorList.push(getLocalMessage("mileStone.financial.weightagRange"));
	} else {
		$("#totalWeightage").val(totalWeightage);
	}

	return errorList;
}
function savePercentage(obj) {

	var errorList = [];
	var totalWeightage = 0;
	if ($.fn.DataTable.isDataTable('#milestoneTbl')) {
		$('#milestoneTbl').DataTable().destroy();
	}
	$("#milestoneTbl tbody tr")
			.each(
					function(i) {
						var msPercent = $("#msPercent" + i).val();
						var rowCount = i + 1;
						if (msPercent == "" || msPercent == null) {
							errorList
									.push(getLocalMessage("wms.EnterPercentageCompleted")
											+ rowCount);
						} else {
							if (parseFloat(msPercent) > 100) {
								errorList
										.push(getLocalMessage("wms.PercentageCompletedCanThanHundred")
												+ rowCount);
							}

						}
					});
	if (errorList.length == 0) {
		var url = "FinancialMilestone.html";
		return saveOrUpdateForm(obj, "", url, 'saveform');
	} else {
		displayErrorsOnPage(errorList);
		$('#milestoneTbl').DataTable();
	}
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}
function getValue() {
	var totalWeight = 0;
	$('#milestoneTbl').DataTable().destroy();
	$("#milestoneTbl tbody tr").each(function(i) {
		var mileStoneWeight = $("#mileStoneWeight" + i).val();
		totalWeight += parseFloat(mileStoneWeight);
		if (mileStoneWeight != "")
			$("#totalWeightage").val(totalWeight);
	});
	triggerTable();
}

function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('milestoneTbl', obj, ids);
	$('#milestoneTbl').DataTable().destroy();
	$("#milestoneTbl tbody tr").each(function(i) {
		var mileStoneWeight = $("#mileStoneWeight" + i).val();
		totalWeight += parseFloat(mileStoneWeight);
		if (mileStoneWeight != "")
			$("#totalWeightage").val(totalWeight);
	});
	triggerTable();
}

function resetFinancialMilestone() {
	openMilestoneAddForm('C');
}

function checkMilestone() {
	
	var projId = $("#projId").val();

	var requestData = {
		"projId" : projId,
	}
	var url = "FinancialMilestone.html?checkMilestone";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	if (response == 'true') {
		$("#checkMileStone").val(true);
	} else {
		$("#checkMileStone").val(false);
	}

}

function triggerTable() {
	$('#milestoneTbl').dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"bStateSave" : true,
		"lengthChange" : true,
		"scrollCollapse" : true,
		"bSort" : false
	});
}
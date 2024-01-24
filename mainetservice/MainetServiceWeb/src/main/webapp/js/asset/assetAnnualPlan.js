$(document).ready(function() {
	//D#81529
	$('#assetDetTable').dataTable(
			{
				
				"bInfo" : true,
				"bStateSave" : true,
				"lengthChange" : true,
				"scrollCollapse" : true,
				"bSort" : false,
				searching: false,
				paging: false,
				info: false
			});
	
	$("#annualPlanSummaryDT").dataTable(
			{
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
						[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
	
	$("#searchAnnualPlan").click(function() {
				var errorList = [];
				let finYearId = $("#financialYear").val();
				let deptId = $("#astDept").val();
				let locId = $("#astLoc").val();

				if (finYearId != 0 || deptId != ''||  locId != '') {

					var requestData = '&finYearId=' + finYearId + '&deptId=' + deptId + '&locId=' +locId;
					var table = $('#annualPlanSummaryDT').DataTable();
					table.rows().remove().draw();
					$(".warning-div").hide();
					var ajaxResponse = __doAjaxRequest('AssetAnnualPlan.html?searchAnnualPlanData','POST', requestData, false,'json');
					if (ajaxResponse.length == 0) {
						errorList.push(getLocalMessage("asset.search.dataNotFound"));
						displayErrorsOnPage(errorList);
						return false;
					}

					var result = [];
					$.each(
									ajaxResponse,
									function(index) {
									    var obj = ajaxResponse[index];
										let dateDesc = obj.dateDesc;
										let locName = obj.locationDesc;
										let deptName = obj.departDesc;
										let status = obj.status;
										let astAnnualPlanId = obj.astAnnualPlanId;
									
										result
												.push([
														'<div class="text-center">'
																+ (index + 1)
																+ '</div>',
														'<div class="text-center">'
																+ dateDesc
																+ '</div>',
														'<div class="text-center">'
																+ locName
																+ '</div>',
														'<div class="text-center">'
																+ deptName
																+ '</div>',
														'<div class="text-center">'
																+ status
																+ '</div>','<div class="text-center">'
																+ '<button type="button"  class="btn btn-blue-2 margin-right-5"  onclick="getActionForDefination('
																+ astAnnualPlanId
																+ ',\'VIEW\')" title="View"><i class="fa fa-eye"></i></button>'
																+ '</div>' ]);
									});
					table.rows.add(result);
					table.draw();
				} else {
					errorList
							.push(getLocalMessage("land.acq.val.selectAtleastOneField"));
					displayErrorsOnPage(errorList);
				}

			});
	
});

function addEntryData() {
	var errorList = [];
	errorList = validateEntryDetails(errorList);
	if (errorList.length == 0) {
		addTableRow('assetDetTable');	
	} else {
		displayErrorsOnPage(errorList);
	}

	$('#assetDetTable').DataTable().destroy();
	triggerTable();
}


function validateEntryDetails(errorList) {
	let tempArrayIds = [];
	$("#assetDetTable tbody tr")
			.each(
					function(i) {
						var atSrNo = getLocalMessage("asset.validation.at.sr.no");
						let position = ' ' + atSrNo + ' ' + (i + 1);
						let astClass = $('#astClass' + i).val();
						let astDesc = $('#astDesc' + i).val();
						let astQty = $('#astQty' + i).val();
						
						if (astClass == "0" || astClass == undefined || astClass == "") {
							errorList.push(getLocalMessage("asset.annualPlan.vldnn.astClass")+ position);
						}
						
						if (astDesc == '' || astDesc == undefined) {
							errorList.push(getLocalMessage('asset.annualPlan.vldnn.description') +position);
						}
						
						if (astQty == '' || astQty == undefined) {
							errorList.push(getLocalMessage('asset.annualPlan.vldnn.quantity') +position);
						}
						if(errorList.length == 0){
							tempArrayIds.push(astClass);	
						}
					});

	var sortedArray = tempArrayIds.sort();
	var results = [];
	for (var i = 0; i < sortedArray.length - 1; i++) {
		if (sortedArray[i + 1] == sortedArray[i]) {
			results.push(sortedArray[i]);
		}
	}
	if (results.length > 0) {
		//D#76574
		//errorList.push(getLocalMessage("asset.annualPlan.vldnn.duplicateClass"));
	}
	return errorList;

}
function deleteEntry(obj, ids) {
	// get table row number
	var rowCount = $('#assetDetTable >tbody >tr').length;
	let errorList = [];
	if (rowCount == 1) {
		errorList.push(getLocalMessage("asset.annualPlan.vldnn.delete.entry"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}

	deleteTableRow('assetDetTable', obj, ids);
	$('#assetDetTable').DataTable().destroy();
	triggerTable();	
}

function triggerTable() {
	$('#assetDetTable').dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"bStateSave" : true,
		"lengthChange" : true,
		"scrollCollapse" : true,
		"bSort" : false,
		searching: false,
		paging: false,
		info: false
	});
}

function saveAnnualPlan(element) {
	debugger;
	var errorList = [];
	$("#errorDiv").html('');
	let financialYear = $('#financialYear').val();
	let astDept = $('#astDept').val();
	let astLoc = $("#astLoc").val();
	errorList = validateEntryDetails(errorList);

	if (financialYear == "0" || financialYear == undefined || financialYear == '') 
		errorList.push(getLocalMessage('asset.annualPlan.vldnn.finYear'));
	if (astDept == "0" || astDept == undefined || astDept == '')
		errorList.push(getLocalMessage('asset.annualPlan.vldnn.department'));
	if (astLoc == "0" || astLoc == undefined || astLoc == '')
		errorList.push(getLocalMessage('asset.annualPlan.vldnn.location'));

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element, '', ''+$("#moduleDeptUrl").val()+'', 'saveform');
	}
}

function saveDecisionAction(approvalData){
	var errorList = [];
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	
	if(decision == undefined || decision == '') {
		errorList.push(getLocalMessage('asset.info.approval'));
	} else if(comments == undefined || comments =='') {
		errorList.push(getLocalMessage('asset.info.comment'));
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData, '', 'AdminHome.html', 'saveDecision');
	}

}

function openAnnualPlanForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getActionForDefination(astAnnualPlanId, mode) {
	if (mode == 'VIEW') {
		var divName = '.content-page';
		var url = ""+$("#moduleDeptUrl").val()+"?viewAnnualPlanData";
		var actionParam = {
			'astAnnualPlanId' : astAnnualPlanId
		}
		var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false,'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

	


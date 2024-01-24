var cdmURL = "ChartOfDepreciationMaster.html";
$(document)
		.ready(
				function() {
					$('#ChartOfDepreciationMaster').validate({
						onkeyup : function(element) {
							this.element(element);
							console.log('onkeyup fired');
						},
						onfocusout : function(element) {
							this.element(element);
							console.log('onfocusout fired');
						}
					});

					$('.decimal').on('input', function() {
						this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals only
						.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
						.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after decimal
					});

					$("#dtCdmHome").dataTable(
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
					
					//T#92465 set cdmUrl based on moduleDept
					cdmURL=$("#moduleDeptUrl").val();
					$("#searchCDM")
							.click(
									function() {
										var errorList = [];
										var groupName = $('#name').val();
										var accountCode = $('#accountCode').val();
										var assetClass = $('#assetClass').val();
										var frequency = $('#frequency').val();
										//D#33152
										accountCode = accountCode == '0' ?'':accountCode;
										assetClass = assetClass == '0' ?'':assetClass;
										frequency = frequency == '0' ?'':frequency;
										
										if (groupName != ''
												|| accountCode != '0'
												|| assetClass != '0'
												|| frequency != '0') {
											
											var requestData = 'name='
													+ groupName
													+ '&accountCode='
													+ accountCode
													+ '&assetClass='
													+ assetClass
													+ '&frequency=' + frequency;
											var table = $('#dtCdmHome')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											
											var ajaxResponse = __doAjaxRequest(
													cdmURL
															+ '?filterCDMListData',
													'POST', requestData, false,
													'json');
											
											if (ajaxResponse != null
													&& ajaxResponse != "") {
												var result = [];
												$
														.each(
																ajaxResponse,
																function(index) {
																	var obj = ajaxResponse[index];
																	result
																			.push([
																					obj.name,
																					obj.accountCodeDesc,
																					obj.assetClassDesc,
																					obj.frequencyDesc,
																					'<td class="text-center">'
																							+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewCDM(\''
																							+ obj.groupId
																							+ '\')" title="View Chart Of Depreciation"><i class="fa fa-eye"></i></button>'
																							+ '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="editCDM(\''
																							+ obj.groupId
																							+ '\')"  title="Edit Chart Of Depreciation"><i class="fa fa-pencil"></i></button>'
																							+ '</td>' ]);
																});
												table.rows.add(result);
												table.draw();
											} else {
												errorList
														.push(getLocalMessage("asset.chartOfDepreciationMaster.criteria"));
												displayErrorsOnPage(errorList);
											}
										} else {
											errorList
													.push(getLocalMessage('asset.select.record'));
											displayErrorsOnPage(errorList);
										}
									});

					$("#createCDM").click(
							function() {
								
								var requestData = {
									"type" : "C"
								}
								
								var ajaxResponse = __doAjaxRequest(cdmURL
										+ '?form', 'POST', requestData, false,
										'html');
								
								$('.pagediv').html(ajaxResponse);
							});

				});

function saveAssetChartOfDepreciationMaster(element) {
	

	var errorList = [];
	var groupName = $("#name").val().trim();
	var assetClass = $("#cdmAstCls").val();
	var frequency = $("#cdmFreq").val();
	var depreciationKey = $("#cdmDepreKey").val();
	var accountCode = $("#accountCode").val();
	var rate = $("#cdmRate").val();
	errorList = validateDuplicateAssetClass(element);
	errorList = validateDepreciationName(element);// defect number 2675 is solved 
	
	if (groupName == "0" || groupName == undefined || groupName == '') {
		errorList
				.push(getLocalMessage("asset.depreciationMaster.vldnn.groupName"));
	}

	if (assetClass == "0" || assetClass == undefined || assetClass == '') {
		errorList
				.push(getLocalMessage("asset.depreciationMaster.vldnn.assetClass"));
	}

	if (frequency == "0" || frequency == undefined || frequency == '') {
		errorList
				.push(getLocalMessage("asset.depreciationMaster.vldnn.frequency"));
	}

	if (depreciationKey == "0" || depreciationKey == undefined
			|| depreciationKey == '') {
		errorList
				.push(getLocalMessage("asset.depreciationMaster.vldnn.depreciationKey"));
	}

	/*if (rate == "0" || rate == undefined || rate == '') {
		errorList.push(getLocalMessage("asset.depreciationMaster.vldnn.rate"));
	}*/

	if (accountCode == "0" || accountCode == undefined || accountCode == '') {
		errorList
				.push(getLocalMessage("asset.depreciationMaster.vldnn.accountCode"));
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		
		//$("#errorDiv").hide();
		return saveOrUpdateForm(
				element,
				getLocalMessage('chartOfDepreciation.master.vldn.savesuccessmsg'),
				cdmURL, 'saveform');
	}

}

function closeErrBox() {
	$('.warning-div').addClass('hide');
}

function resetCDM() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', cdmURL);
	$("#postMethodForm").submit();
}

function resetCdmForm() {
	
	$("#name").val("");
	$("#cdmAstCls").val("0");
	$("#cdmFreq").val("0");
	$("#cdmDepreKey").val("0");
	$("#accountCode").val("0");
	$("#cdmFreq").val("");
	$("#remark").val("");
	$('.warning-div').addClass('hide');
}

function editCDM(groupId) {
	
	var requestData = 'groupId=' + groupId + '&type=E';
	
	var response = __doAjaxRequest(cdmURL + '?form', 'POST', requestData,
			false, 'html');
	
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
}

function viewCDM(groupId) {
	
	var requestData = 'groupId=' + groupId + '&type=V';
	
	var response = __doAjaxRequest(cdmURL + '?form', 'POST', requestData,
			false, 'html');
	$('.pagediv').removeClass('ajaxloader');
	
	$('.pagediv').html(response);
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$(".warning-div").show();
	errorList = [];
	return false;
}

function validateDepreciationName(obj) {
	
	var errorList = [];
	var name = $("#name").val().trim();
	var modeType = $("#modeType").val();
	var requestData = {};
	requestData = {
		name : name
	};
	var actionParam = "validateDepreciationName";
	var url = cdmURL + '?' + actionParam;
	var obj = "";
	if (modeType == 'C') {
		var response = __doAjaxRequestForSave(url, 'post', requestData, false,
				'', obj);
		var duplicate = response["errMsg"];
		if (duplicate != '') {
			errorList.push(duplicate);
		}
	}
	return errorList;
}

function BackCDM() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', cdmURL);
	$("#postMethodForm").submit();
}
// defect number 2675 is solved 
function validateDuplicateName(obj) {
	
	var errorList = [];
	errorList = validateDepreciationName(obj);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}

}

function validateDuplicateAssetClass(obj) {
	
	var errorList = [];
	var assetClass = $("#cdmAstCls").val();
	var modeType = $("#modeType").val();
	var requestData = {};
	requestData = {
		assetClass : assetClass
	};
	var actionParam = "validateAssetClass";
	var url = cdmURL + '?' + actionParam;
	var obj = "";
	if (modeType == 'C') {
		var response = __doAjaxRequestForSave(url, 'post', requestData, false,
				'', obj);
		var duplicate = response["errMsg"];
		if (duplicate != '') {
			errorList.push(duplicate);
		}
	}
}

function validateClass(obj) {
	
	var errorList = [];
	errorList = validateDuplicateAssetClass(obj);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}

}
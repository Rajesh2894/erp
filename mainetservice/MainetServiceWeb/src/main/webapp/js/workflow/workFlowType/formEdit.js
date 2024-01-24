/**
 * ritesh.patil
 * 
 */
var removeIdArray = [];
$(document)
		.ready(
				function() {  
					$('#sourceOfFundDiv').hide();
					$('#schemeNames').hide();
					$('#workflowBaseDeptDiv').hide();
					$('#workflowBaseDeptDiv').hide();
					$('#workdeprtIdByDept').hide();
					$('#vehMainteinedBy').hide();
					$('#sfacMasterDiv').hide();

					$('#codIdOperLevel').prop('disabled', true);
					
					
					var wardZoneType = $(
							"input[name='workFlowMasDTO.type']:checked").val()
					var complaint = $("#complaintId").val();
					if (complaint != null && complaint != 0
							&& complaint != undefined) {
						$('#complaintTypeDiv').show();
					} else {
						$('#complaintTypeDiv').hide();
					}
					if (wardZoneType == 'A') {
						$('#zone-ward').hide();
					} else {
						$('#zone-ward').show();
					}

					var serviceCode = $("#serviceId :selected").attr('code');
					var deptCode =  $("#departmentId :selected").attr('code');
					var auditDeptWiseFlag = $("#auditDeptWiseFlag").val();
					if (serviceCode == "WOA") {
						$('#sourceOfFundDiv').show();
						$('#schemeNames').show();
						$('#workflowBaseDeptDiv').hide();
					} else if (serviceCode == "LQE") {
						$('#sourceOfFundDiv').hide();
						$('#schemeNames').hide();
						$('#workflowBaseDeptDiv').show();
					} else if (serviceCode == "NTL" || serviceCode=="RTL" || serviceCode=="TLA") {
						$('#sourceOfFundDiv').hide();
						$('#schemeNames').hide();
						$('#tradeCateg').show();
					}
					else{
						$('#sourceOfFundDiv').hide();
						$('#schemeNames').hide();
						$('#workflowBaseDeptDiv').hide();
					}
					if (serviceCode == "VB" || serviceCode=="MBA") {
						$('#billTypeDiv').show();
					}
					else{
						$('#billTypeDiv').hide();
					}
					if (serviceCode == "NTL" || serviceCode=="RTL" || serviceCode=="TLA") {
						$('#tradeCateg').show();
					}
					else{
						$('#tradeCateg').hide();
					}
					
					if(serviceCode=="MOV"){
						 $('#vehMainteinedBy').show();
			            $('#sfacMasterDiv').remove();
			        }
					if(serviceCode=="CAE"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					if(serviceCode=="FMC"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					if(serviceCode=="EGR"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					if(serviceCode=="CGF"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					
					if(serviceCode=="MSC"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					if(serviceCode=="DPR"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					if(serviceCode=="ABS"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					
					if(serviceCode=="FPO"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
					if(serviceCode=="FAE"){
						$('#sfacMasterDiv').show();
			            $('#vehMainteinedBy').remove();
			        }
								
					if(serviceCode=="IARS" || deptCode=="CMT" || (auditDeptWiseFlag == "Y" && deptCode=="AD")){
						 $("#workdeprtIdByDept").show(); 
			        }
					
					if(serviceCode=="AR"){
						 $("#assetclassby").show(); 
			        }

					$('#submitFlow')
							.click(
									function() {
										var errorList = [];
										errorList = validateFlowForm(errorList);
										if (errorList.length == 0) {
											errorList = validateFlowMapping(errorList);
											if (errorList.length == 0) {
												var wfChkBx = $("#wfStatus")
														.is(':checked');
												if (!wfChkBx) {
													var requestData = 'id='
															+ $("#wfId").val();
													var response = __doAjaxRequest(
															workFlowUrl
																	+ '?deleteFlow',
															'POST',
															requestData, false,
															'json');
													if (response) {
														showConfirmBox();
													} else {
														$(childDivName)
																.html(
																		"Internal errors");
														showModalBox(childDivName);
													}
												} else {
													requestData = $(
															'#workFlowTypeForm')
															.serialize()
															+ "&"
															+ $(
																	"#workFlowSubTypeForm")
																	.serialize();
													var returnData = __doAjaxRequestForSave(
															'WorkFlowType.html?saveform',
															'post',
															requestData, false,
															'', $(this));
													getSaveFormResponse(returnData);
												}
											} else {
												showRLValidation(errorList);
											}
										} else {
											showRLValidation(errorList);
										}
									});

					$("#customFields")
							.on(
									'click',
									'.addCF',
									function(i) {
										var row = 0;
										var errorList = [];
										errorList = validateFlowMapping(errorList);

										if (errorList.length == 0) {
											var content = $(this).closest('tr')
													.clone();
											$(this).closest("tr")
													.after(content);
											var clickedIndex = $(this).parent()
													.parent().index() - 1;
											content.find("input:text").val('');
											content.find("input:hidden")
													.val('');
											content.find("select").val('0');
											content.find(
													".multiselect-container")
													.empty();
											content
													.find(
															".multiselect-selected-text")
													.text("None selected");
											$('.error-div').hide();
											reOrderTableIdSequence();
											$("#mapOrgId_" + (clickedIndex + 1))
													.val(
															$("#onOrgSelect")
																	.val());
											if ($("#complaintId :selected")
													.attr('code') > 0) {
												$(
														'#mapDeptId_'
																+ (clickedIndex + 1))
														.val(
																$(
																		"#complaintId :selected")
																		.attr(
																				'code'));
											} else {
												$(
														'#mapDeptId_'
																+ (clickedIndex + 1))
														.val(
																$(
																		"#departmentId")
																		.val());
											}
										} else {
											displayErrorsOnPageView(errorList);
										}
									});

					$("#customFields")
							.on(
									'click',
									'.remCF',
									function() {
										if ($("#customFields tr").length != 2) {
											$(this).parent().parent().remove();
											reOrderTableIdSequence();
											id = $(this)
													.parent()
													.parent()
													.find(
															'input[type=hidden]:first')
													.attr('value');
											if (id != '') {
												removeIdArray.push(id);
											}
											$('#removeChildIds').val(
													removeIdArray);
										} else {
											var errorList = [];
											errorList
													.push(getLocalMessage("rl.subChild.deletrw.validtn"));
											displayErrorsOnPageView(errorList);
										}
									});
					if($('#hiddeValue').val() == 'V'){
						 $("#backBtn").prop('disabled', false);
					}

				});

$('#wfStatus')
		.change(
				function() {
					var wfChkBx = $("#wfStatus").is(':checked');
					if (!wfChkBx) {
						var id = $("#wfId").val();
						var yes = getLocalMessage('eip.commons.yes');
						var no = getLocalMessage('eip.commons.no');

						var warnMsg = "Are you sure to Inactive Workflow? Inactive Workflow can not be activated.";
						message = '<p class="text-blue-2 text-center padding-15">'
								+ warnMsg + '</p>';
						message += '<div class=\'text-center padding-bottom-10\'>'
								+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
								+ yes
								+ '\'  id=\'yes\' '
								+ ' onclick="onDelete(\''
								+ id
								+ '\')"/>'
								+ '<input class="btn btn-danger" style="margin-right:10px" type=\'button\' value=\''
								+ no
								+ '\'  id=\'yes\' '
								+ ' onclick="CloseIt()"/>' + '</div>';

						$(childDivName).addClass('ok-msg').removeClass(
								'warn-msg');
						$(childDivName).html(message);
						$(childDivName).show();
						$('#yes').focus();
						showModalBoxWithoutClose(childDivName);
						return false;
					} else {
						$('#wfStatus').prop('checked', true);
					}
				});

function CloseIt() {
	$('#wfStatus').prop('checked', true);
	$.fancybox.close();

}

function onDelete(id) {
	$('#wfStatus').prop('checked', false);
	$.fancybox.close();
}

function showMappingDept(obj) {
	var arr = $(obj).prop('id').split('_');
	var mappedOrg = $(obj).val();
	$('#roleType_' + arr[1]).val('0');
	$('#roleOrEmpId_' + arr[1]).val('0');

	if (mappedOrg > 0) {
		$('#mapDeptId_' + arr[1]).html('');
		$('#mapDeptId_' + arr[1]).append(
				$("<option></option>").attr("value", "0").text("Select"));
		var requestData = {
			"orgId" : mappedOrg
		}
		var result = __doAjaxRequest("WorkFlowType.html?department", 'POST',
				requestData, false, 'json');
		$.each(result, function(index, value) {
			$('#mapDeptId_' + arr[1]).append(
					$("<option></option>").attr("value", value[0]).text(
							value[1]));
		});
		if ($(obj).val() == $("#onOrgSelect").val()) {
			$('#mapDeptId_' + arr[1]).val($("#departmentId").val());
		}
		$('#apprCount_' + arr[1]).val('1');
	}
	$('.mul_' + arr[1]).empty();
	$('.mul_' + arr[1])
			.append(
					'<select id="roleOrEmpId_'
							+ (arr[1])
							+ '" name="workFlowMasDTO.workflowDet['
							+ (arr[1])
							+ '].roleOrEmpIds" class="form-control multiple-chosen" multiple="multiple"></select>')
	$('#roleOrEmpId_' + arr[1]).multiselect('rebuild');
}

function reOrderTableIdSequence() {

	$('.appendableClass').each(
			function(i) {
				$(this).find("input:hidden:eq(0)").attr("id", "id_" + i);
				$(this).find("select:eq(0)").attr("id", "eventMasterId_" + i)
						.attr('onchange',
								'checkForDuplicateEvent(this,' + i + ')');
				$(this).find("select:eq(1)").attr("id", "mapOrgId_" + i);
				$(this).find("select:eq(2)").attr("id", "mapDeptId_" + i);
				$(this).find("select:eq(3)").attr("id", "roleType_" + i);
				$(this).find("select:eq(4)").attr("id", "roleOrEmpId_" + i);
				$(this).find("select:eq(5)").attr("id", "units_" + i);

				$(this).find("td:eq(4)").removeAttr('class').addClass(
						"mul_" + (i));
				$(this).find("input:text:eq(0)").attr("id", "sla_" + i);
				$(this).find("input:text:eq(1)").attr("id", "apprCount_" + i);

				$(this).find("input:hidden:eq(0)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].wfdId");

				$(this).find("select:eq(0)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].eventId");
				$(this).find("select:eq(1)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].mapOrgId");
				$(this).find("select:eq(2)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].mapDeptId");
				$(this).find("select:eq(3)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].roleType");
				$(this).find("select:eq(4)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].roleOrEmpIds");
				$(this).find("select:eq(5)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].unit");

				$(this).find("input:text:eq(0)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].sla");
				$(this).find("input:text:eq(1)").attr("name",
						"workFlowMasDTO.workflowDet[" + i + "].apprCount");
			});

}

function validateFlowMapping(errorList) {
	$('.appendableClass').each(function(i) {
		row = i + 1;
		errorList = validateDetailsTableData(errorList, i);
	});

	return errorList;

}

function checkForDuplicateEvent(event, currentRow) {
	$(".error-div").hide();
	var errorList = [];
	var serviceCode = $("#serviceId :selected").attr('code');
	if (serviceCode == '0' || serviceCode == undefined) {
		errorList.push(getLocalMessage('workflow.select.service'));
	}
	if (errorList.length == 0) {
		$('.appendableClass')
				.each(
						function(i) {
							if (currentRow != i
									&& (event.value == $("#eventMasterId_" + i)
											.val()) && serviceCode != "CARE") {
								$("#eventMasterId_" + currentRow).val("0");
								errorList
										.push(getLocalMessage('workflow.form.validation.duplicant.event'));
								displayErrorsOnPageView(errorList);
								return false;
							}
						});
	} else {
		$("#eventMasterId_" + currentRow).val("0");
		displayErrorsOnPageView(errorList);
		return false;
	}
}

/**
 * validate each mandatory column of additional owner details
 * 
 * @param errorList
 * @param i
 * @returns
 */
function validateDetailsTableData(errorList, i) {
	var res = [];
	var event = $("#eventMasterId_" + i).val();
	var roleEmpName = $("#roleType_" + i).val();
	var details = $("#roleOrEmpId_" + i).val();
	var sla = $.trim($("#sla_" + i).val());
	var units = $("#units_" + i).val();
	var org = $("#mapOrgId_" + i).val();
	var dept = $("#mapDeptId_" + i).val();
	var approver = $.trim($("#apprCount_" + i).val());
	var wfMode = $("#workflowMode :selected").attr('code');
	var j = i + 1;
	var numItems = $('#roleOrEmpId_' + i + ' option:selected').length;
	var type = $('#roleType_' + i + ' :selected').attr('value');

	if (event == "" || event == '0' || event == undefined) {
		errorList.push(getLocalMessage('workflow.form.validation.select.event')
				+ " -" + j);
	}

	if (org == "" || org == '0' || org == undefined) {
		errorList.push(getLocalMessage('workflow.form.validation.select.org')
				+ " -" + j);
	}

	if (dept == "" || dept == '0' || dept == undefined) {
		errorList.push(getLocalMessage('workflow.form.validation.select.dept')
				+ " -" + j);
	}
	if (roleEmpName == "" || roleEmpName == '0' || roleEmpName == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.select.roleemp')
						+ " -" + j);
	}
	if (details == "" || details == '0' || details == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.select.details')
						+ " -" + j);
	}

	if (wfMode == "AE") {
		if (sla == "" || sla == undefined) {
			errorList
					.push(getLocalMessage('workflow.form.validation.enter.sla')
							+ " -" + j);
		}
		if (units == "" || units == '0' || units == undefined) {
			errorList
					.push(getLocalMessage('workflow.form.validation.select.unit')
							+ " -" + j);
		}
	}

	if (sla != "" && sla != undefined && units != "" && units != '0'
			&& units != undefined) {
		res = sla.split('.');
		if ($('#units_' + i + ' option:selected').attr('code') == 'D') {
			if (res.length > 0 && res[1] >= 24) {
				errorList
						.push(getLocalMessage('workflow.enter.value.hours')
								+ " -" + j);
			}
		}
		if ($('#units_' + i + ' option:selected').attr('code') == 'H') {
			if (res.length > 0 && res[1] >= 60) {
				errorList
						.push(getLocalMessage('workflow.enter.value.minutes')
								+ " -" + j);
			}
		}
	}

	if (approver == "" || approver == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.enter.aprvcount')
						+ " -" + j);
	}
	if (approver == '0') {
		errorList.push(getLocalMessage('workflow.form.validation.apprvr.zero')
				+ " -" + j);
		$("#apprCount_" + i).val('');
	}
	if (approver != null && approver != '0' && approver != undefined
			&& approver != "" && roleEmpName != "" && roleEmpName != '0'
			&& roleEmpName != undefined) {
		if (type == 'R') {
			var roleIds = $("#roleOrEmpId_" + i).val().toString();
			var requestData = {
				"orgId" : org,
				"roleIds" : roleIds
			};
			var totalEmp = __doAjaxRequest(
					"WorkFlowType.html?getTotalEmployeeCountByRoles", 'POST',
					requestData, false, '');
			if (approver > totalEmp) {
				$("#apprCount_" + i).val('')
				errorList
						.push(getLocalMessage('workflow.form.validation.apprvrcount.detailselection')
								+ " -"
								+ j
								+ ". Total: "
								+ totalEmp
								+ " Employees are mapped for selected roles.");
			}
		} else {
			if (approver > numItems) {
				$("#apprCount_" + i).val('')
				errorList
						.push(getLocalMessage('workflow.form.validation.apprvrcount.detailselection')
								+ " -" + j);
			}
		}
	}
	return errorList;
}

function validateFlowForm(errorList) {

	var deptId = $('#departmentId').val();
	var serviceId = $('#serviceId').val();
	var wardZoneType = $("input[name='workFlowMasDTO.type']:checked").val();
	// var taskManager =$('#managerId').val();
	var workflowMode = $('#workflowMode').val();
	var complaintId = $('#complaintId').val();

	if (deptId == '0' || deptId == undefined) {
		errorList.push(getLocalMessage('workflow.form.validation.select.dept'));
	}
	if (serviceId == '0' || serviceId == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.select.service'));
	}
	if (workflowMode == '0' || workflowMode == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.select.wfmode'));
	}
	var serviceCode = $("#serviceId :selected").attr('code');
	if (serviceCode == "CARE") {
		if (complaintId == '0' || complaintId == undefined) {
			errorList
					.push(getLocalMessage('workflow.form.validation.select.comptype'));
		}
	}

	/*
	 * if(taskManager == '0' || taskManager == undefined ){
	 * errorList.push(getLocalMessage('workflow.form.validation.select.taskmanager')); }
	 */

	if (wardZoneType == '0' || wardZoneType == undefined) {
		errorList.push(getLocalMessage('workflow.select.wardZone'));
	} else {

		if (wardZoneType == 'N') {
			var OperLevel1 = $("#codIdOperLevel1").val();
			var OperLevel2 = $("#codIdOperLevel2").val();
			var OperLevel3 = $("#codIdOperLevel3").val();
			var OperLevel4 = $("#codIdOperLevel4").val();
			var OperLevel5 = $("#codIdOperLevel5").val();

			if (OperLevel1 != undefined && OperLevel1 == '0') {
				errorList.push("workflow.form.validation.wardzone1");
			}
			if (OperLevel2 != undefined && OperLevel2 == '0') {
				errorList.push("workflow.form.validation.wardzone2");
			}
			if (OperLevel3 != undefined && OperLevel3 == '0') {
				errorList.push("workflow.form.validation.wardzone3");
			}
			if (OperLevel4 != undefined && OperLevel4 == '0') {
				errorList.push("workflow.form.validation.wardzone4");
			}
			if (OperLevel5 != undefined && OperLevel5 == '0') {
				errorList.push("workflow.form.validation.wardzone5");
			}
		}
	}

	return errorList;
}

function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	return false;
}

function closeOutErrBox() {
	$('.error-div').hide();
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h4 class=\"text-center text-blue-2 padding-10\">Work Flow Inactive Sucessfully</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceed() {
	back();
}

function showEmpOrRole(obj) {
	$('.error-div').hide();
	arr = $(obj).prop('id').split('_');
	$('#roleOrEmpId_' + arr[1]).html('');
	var mapOrg = $('#mapOrgId_' + arr[1]).val();
	var mapDept = $('#mapDeptId_' + arr[1]).val();
	var type = $('#roleType_' + arr[1]).val();
	var errorList = [];
	$('#roleOrEmpId_' + arr[1]).prop("disabled", false);

	if (mapOrg == '0' || mapOrg == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.select.mapping.org'));
	}

	if (type == '0' || type == undefined) {
		errorList
				.push(getLocalMessage('workflow.form.validation.select.roleemp'));
	}

	if (errorList.length > 0) {
		$('#roleType_' + arr[1]).val('0');
		$('#roleOrEmpId_' + arr[1]).append(
				$("<option></option>").attr("value", "0").text("Select"));

		showRLValidation(errorList);
	} else {
		var requestData = {
			"orgId" : mapOrg,
			"deptId" : mapDept,
			"flag" : $(obj).val()
		}
		result = __doAjaxRequest("WorkFlowType.html?empOrRole", 'POST',
				requestData, false, 'json');
		$('.mul_' + arr[1]).empty();
		$('.mul_' + arr[1])
				.append(
						'<select id="roleOrEmpId_'
								+ (arr[1])
								+ '" name="workFlowMasDTO.workflowDet['
								+ (arr[1])
								+ '].roleOrEmpIds" class="form-control multiple-chosen" multiple="multiple"></select>')
		if ($(obj).val() == 'E') {
			$.each(result, function(index, value) {
				mvalue = '';
				if (value[1] != null) {
					mvalue = value[1];
				}
				$('#roleOrEmpId_' + arr[1]).append(
						$("<option></option>").attr("value", value[3]).text(
								value[0] + " " + mvalue + " " + value[2]));
			});
		} else {
			$.each(result, function(index, value) {
				$('#roleOrEmpId_' + arr[1]).append(
						$("<option></option>").attr("value", value[0]).text(
								value[1]));
			});
		}
		multiSel();
		$('#roleOrEmpId_' + arr[1]).multiselect('rebuild');
	}
}

function multiSel() {
	$(".multiple-chosen").multiselect({
		numberDisplayed : 1,
		includeSelectAllOption : true,
		selectAllName : 'select-all-name'
	});
}

function checkOrgDept(errorList, i) {
	orgId = $('#onOrgSelect').val();
	deptId = $('#departmentId').val();
	if (orgId == '0' || orgId == undefined) {
		errorList.push(getLocalMessage('workflow.form.validation.select.org'));
	}

	if (deptId == '0' || deptId == undefined) {
		errorList.push(getLocalMessage('workflow.form.validation.select.dept'));
	}

	return errorList;
}

function getSaveFormResponse(returnData) {
	successUrl = 'WorkFlowType.html';
	if ($.isPlainObject(returnData)) {
		var message = returnData.command.message;
		var hasError = returnData.command.hasValidationError;
		if (!message) {
			message = successMessage;
		}
		if (message && !hasError) {
			if (returnData.command.hiddenOtherVal == 'SERVERERROR')
				showSaveResultBox(returnData, message, 'AdminHome.html');
			else
				showSaveResultBox(returnData, message, successUrl);
		} else if (hasError) {
			$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');
		} else
			return returnData;

	} else if (typeof (returnData) === "string") {
		$(formDivName).html(returnData);
		prepareTags();
	} else {
		alert("Invalid datatype received : " + returnData);
	}

	return false;
}

function changeWardZone(){
	$('input:radio[name="workFlowMasDTO.type"][value="A"]').prop('checked', true);
	 $('#zone-ward').hide();
}

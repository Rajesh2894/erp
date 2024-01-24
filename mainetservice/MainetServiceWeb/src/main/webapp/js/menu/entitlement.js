var entitleReqMap = 'entitlement.html';

function unselectedNodes(nodeList, unselArr) {
	for(var k=0; k<nodeList.length; k++){
		if(nodeList[k].hasSubSel && !nodeList[k].bSelected){
			unselArr.push(nodeList[k].data.target);
			if(nodeList[k].childList && nodeList[k].childList.length > 0){
				unselectedNodes(nodeList[k].childList, unselArr);
			}
		}
	}
}

$(document)
		.ready(
				function() {
					
                    $("#existTemp").hide();
					$('#existTempBtn').hide();
					$('.existStructure').hide();
					$("#updateNodeBtn").hide();
					$("#tempDpDeptId").chosen().val('');
					$('select').trigger("chosen:updated");
					
					$("#tree").dynatree(
									{
										checkbox : true,
										selectMode : 3,
										isFolder: false,
										onSelect : function(select, node) {
											var selKeys = $.map(node.tree.getSelectedNodes(),
														function(node) {
																return node.data.target;
														});
											var partsel = new Array();
											$(".dynatree-partsel:not(.dynatree-selected)").each(
													function() {
														var node = $.ui.dynatree.getNode(this);
															if(node.hasSubSel && !partsel.includes(node.data.target)){
																partsel.push(node.data.target);
																if(node.childList && node.childList.length > 0) {
																	unselectedNodes(node.childList,partsel);
																}
															}
															
												
															});
											selKeys = selKeys.concat(partsel);
											$("#menuIds").val(selKeys);

										},
										onActivate : function(node) {
											node.select(true);

										},
										autoCollapse : false
									});
					
					
                 $('#roleSelect').change(
							function() {
								$('.existStructure').show();
								
								var deptResponse = __doAjaxRequest(entitleReqMap
										+ '?getDepartment', 'post', "roleId="
										+ $(this).val(), false, 'html');
								
								if(deptResponse == 0){
									$("#dpDeptId").val('');
									$("#tempDpDeptId").chosen().val('');
								}else{
									$("#dpDeptId").val(deptResponse);
									$("#tempDpDeptId").chosen().val(deptResponse);
								}

								
								$('select').trigger("chosen:updated");
								var response = __doAjaxRequest(entitleReqMap
										+ '?existTemplate', 'post', "roleId="
										+ $(this).val(), false, 'html');
								$('.existStructure').html(response);
								$('.existStructure').addClass('mandColorClass');
								$("#tree1").dynatree("getRoot").visit(
										function(node) {
											node.select(true);
								});
							});

				

					$('#entitleSubmit')
							.click(
									function() {
										
										$('#errorDivEntitle').hide();
										var url = "entitlement.html?checkDuplicateRoleCode"
										var errorList= [];
										var msg = '';

										if($('#roleSelect').val() == 0){
											errorList.push(getLocalMessage('menu.node.edit.code'));
										}
										if ($('#roleName').val() == '') {
											errorList.push(getLocalMessage('menu.newRoleCode.validation'));
										}

										if ($('#menuIds').val() == '') {
											errorList.push(getLocalMessage('menu.select.validation'));
										}
										if ($('#dpDeptId').val() == '') {
											errorList.push(getLocalMessage('menu.department.validation'));
											$("#dpDeptId").val('');
										}
										
										
										if (errorList.length > 0) {
											showEntitleError(errorList);	
										} else {
											var requestData = {
													"roleName" : $('#roleName').val()
											}
											var returnData =__doAjaxRequest(url,'post',requestData,false);
											if(returnData == null || returnData == ""){
												$("#entitleSubmit").prop(
														"disabled", true);
												$("#frmmanageRoleForm").submit();
											}else{
												errorList.push(returnData);
												showEntitleError(errorList);
											}
											
										}
									});

					$("#entitleAdd")
							.click(
									function() {

										var response = __doAjaxRequest(
												entitleReqMap + '?addNode',
												'post', {}, false, 'html');
										$('.form-div').html(response);

									});
					
					$("#entitleEdit")
					.click(
							function() {

								var response = __doAjaxRequest(
										entitleReqMap + '?editNode',
										'post', {}, false, 'html');
								$('.form-div').html(response);

							});
					
					

					$("#updateNodeBtn")
							.click(
									function() {

										var msg = '';

										if ($('#roleSelect').val() == '0') {
											msg += "<h5 class='text-blue-2'>"
													+ getLocalMessage('menu.node.edit.code')
													+ "</h5>";
										}

										if (msg != '') {
											$('.msg-dialog-box').html(
													"<div class='padding-10'>" + msg + "</div>");
											showModalBox('.msg-dialog-box');

										} else {
											getDeselectedNodes();
											if ($('#deActiveNodes').val() == '') {
												msg += "<h5 class='text-blue-2'>"
														+ getLocalMessage('menu.deselect.nodes')
														+ "</div>";
											}

											if (msg != '') {
												$('.msg-dialog-box').html(
														"<div class='padding-10'>" + msg + "</div>");
												showModalBox('.msg-dialog-box');
											} else {

												var response = __doAjaxRequestForSave(
														entitleReqMap
																+ '?hasEmpGrp',
														'post',
														'roleId='
																+ $("#roleSelect")
																		.val(),
														false, 'json');

												if (parseInt(response) != 0) {
													showConfirmDialogBoxForUpdate(parseInt(response));

												} else {
													updateNodeForRole();

												}

											}
										}

									});
					
					
					
					$("#activeNewNodeBtn")
					.click(
							function() {

								var response = __doAjaxRequest(
										entitleReqMap + '?activeNewNode',
										'post', {}, false, 'html');
								$('.form-div').html(response);
							});
					
					$("#dataEntitleBtn").click(function(){
						var response = __doAjaxRequest(
								entitleReqMap + '?dataEntitle',
								'post', {}, false, 'html');
						$('.form-div').html(response);
					});
					
					$('#activeRoleSelect').change(
							function() {
								var response = __doAjaxRequest(entitleReqMap
										+ '?activeExistTemplate', 'post', "roleId="
										+ $(this).val(), false, 'json');
								
								$("#tree").dynatree("getTree").visit(function(node) {
									node.select(false);
									
								if(response)
								{
									$('#roleDescriptionEng').val(response.roleDescriptionEng);
									$('#roleDescriptionReg').val(response.roleDescriptionReg);
									$('#dpDeptId').val(response.dpDeptId);
									
									$.each(response.roleEntitleIds, function(key, value) {
									var treeId=node.data.target;
									var string = treeId + "";
									var d = string.split("/"); 
										if(key===d[0])
										{
											node.select(true);
										}
									});
								}
								});
								
							});
					
					$('#entitleActiveSubmit')
					.click(
							function() {
					
								var msg = '';

								if ($('#activeRoleSelect').val() === ''|| $('#activeRoleSelect').val() ==='0') {
									msg += "<h5 class='text-blue-2'>"
											+ getLocalMessage('menu.node.edit.code')
											+ "</h5>";
								}
								
								if (msg != '') {
									$('.msg-dialog-box').html(
											"<div class='padding-10'>" + msg + "</div>");
									showModalBox('.msg-dialog-box');
								} else {
									
									$("#entitleActiveSubmit").prop("disabled", true);
									$("#activeNewNode").submit();
								}
							});
					
					$("#addRole")
					.click(
							function() {
								var response = __doAjaxRequest(
										entitleReqMap + '?addRole',
										'post', {}, false, 'html');
								$('.form-div').html(response);

							});

				});

function updateNode() {

}

function showTemplate(data) {
	$("#updateNodeBtn").hide();
	$('#entitleSubmit').show();
    $('.success-div').hide();
    $('.alert').hide();
	if (data == 'E') {
		$("#tree").dynatree("getRoot").visit(function(node) {
			node.select(false);
		});

		$('#menuIds').val('');
		$('#roleName').val('');
		$('#createTemp').show();
		$('#createTempBtn').show();
		$("#checkAction").val(data);
		$('#activeNewNodeBtn').hide();

		$('#pull_exist').hide();
		$('.createStructure').hide();
		$('.existStructure').hide();
		$("#existTemp").show();
		$('#existTempBtn').show();
		$('#createTempBtn').hide();
		if ($("#roleSelect").val() == '0' || $("#roleSelect").val() == null) {
			var response = __doAjaxRequest(entitleReqMap + '?grpList', 'post',
					{}, false, 'json');
			$("#roleSelect").find('option').remove();
			$("#roleSelect").append(
					'<option value="0">'
							+ getLocalMessage("menu.dropdown.select")
							+ '</option>');
			$.each(response, function(key, value) {
				$("#roleSelect").append(
						'<option value="' + key + '">' + value + '</option>');

			});

		}

		$("#roleSelect").val(0);
		$("#tempDpDeptId").chosen().val('');
		$("#dpDeptId").val('');

	} else {

		if ($('#roleSelect').val() != '0' && $('#menuIds').val() != '') {
			$("#tree1").dynatree("getRoot").visit(function(node) {
				node.select(false);
			});
		}
		$("#checkAction").val(data);
		$('#roleName').val('');
		$('#menuIds').val('');
		$("#existTemp").hide();
		$('#existTempBtn').hide();
		$('.existStructure').hide();
		$('.createStructure').show();
		$('#pull_exist').show();
		$('#createTemp').show();
		$('#existTempBtn').hide();
		$('#createTempBtn').show();
	
		$('#activeNewNodeBtn').show();
		$("#tempDpDeptId").chosen().val('');
		$("#dpDeptId").val('');
		
		
     
	}
	$('select').trigger("chosen:updated");

}

function getDeselectedNodes() {

	var nodeList = [];
	var partialSelectList = [];
	var finalList = [];
	$("#tree1").dynatree("getTree").visit(function(node) {
		if (!node.bSelected) {
			nodeList.push(node.data.target);
		}
	});

	$(".dynatree-partsel:not(.dynatree-selected)").each(function() {
		var node = $.ui.dynatree.getNode(this);
		partialSelectList.push(node.data.target);
	});

	$.each(nodeList, function(key, value) {
		if ($.inArray(value, partialSelectList) == -1) {
			finalList.push(value);
		}
	});

	$("#deActiveNodes").val(finalList);
}

function showConfirmDialogBoxForUpdate(response) {

	var askMessage = getLocalMessage("menu.assign.role1") + " " + response
			+ getLocalMessage + " " + ("menu.assign.role2");
	var msgText1 = 'Yes';
	var message = '';
	var cls = getLocalMessage('eip.page.process');

	message += '<p>' + askMessage + '</p>';
	message += '<p style=\'text-align:center; margin:5px\'>'
			+ '<br/><input type=\'button\' value=\'' + msgText1
			+ '\'  id=\'btnNo\' class=\'css_btn \'    '
			+ ' onclick="updateNodeForRole()"/>' + '</p>&nbsp;&nbsp;&nbsp';

	$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);

}

function updateNodeForRole() {

	var url = entitleReqMap + '?updateNode';
	var ajaxResponse = __doAjaxRequest(url, 'post', 'nodesList='
			+ $("#deActiveNodes").val() + '&roleId=' + $('#roleSelect').val(),
			false, 'json');
	var message = '';

	var cls = getLocalMessage('eip.page.process');
	if (ajaxResponse) {
		messageText = getLocalMessage("menu.success.update");
	} else if (!ajaxResponse) {
		messageText = getLocalMessage("menu.deselect.msg");
	} else {
		messageText = getLocalMessage("menu.exception");
	}

	message += '<p>  ' + messageText + '  </p>';
	message += '<p style=\'text-align:center; margin:5px\'>'
			+ '<br/><input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'css_btn \'    '
			+ ' onclick="goTOEntitleForm('+ajaxResponse+')"/>' + '</p>' ;

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}
function goTOEntitleForm(value) {
	if (!value)
		$.fancybox.close();
	else
		back();

}

$(function() {
	$(document).on('change', '#tempDpDeptId', function() {
		$("#dpDeptId").val($("#tempDpDeptId").val());
	});
});

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'entitlement.html');
	$("#postMethodForm").submit();
}


function closeOutErrBox(){
	$("#errorDivEntitle").hide();
}

function showEntitleError(errorList){
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivEntitle").html(errMsg);
	$("#errorDivEntitle").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
}


 	

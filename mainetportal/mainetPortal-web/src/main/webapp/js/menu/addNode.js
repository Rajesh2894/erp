$(document)
		.ready(
				function() {

					$('#showMasterForm').hide();
					$("#addDataBackButton").hide();

					$("#tree3")
							.dynatree(
									{
										checkbox : true,
										classNames : {
											checkbox : "dynatree-radio"
										},
										selectMode : 1,
										autoCollapse : false,
										onSelect : function(select, node) {
											$('#rootNode').attr('checked', false);
											$("#etId").val('');
											$("#flag").val('');
											var selKeys2 = $
													.map(
															node.tree
																	.getSelectedNodes(),
															function(node) {
																return node.data.target;
															});
											var string = selKeys2 + "";
											
											var d = string.split("#");
											if ( d!=''&& lowerCase(d[1]) != "sf" && lowerCase(d[1]) != "pf") {
												$("#etId").val(d[0]);
												$("#flag").val(d[1]);
												$("#smfaction").val('').attr('readonly',false).removeClass("disablefield").addClass("mandClassColor");
												if($("#addOredit").val()==='E')
													{
													$('#smfname').val(d[2]);
													$('#smfname_mar').val(d[3]);
													$('#smfdescription').val(d[4]);
													$("#smfaction").val(d[5]);
													$('#smParam1').val(d[6]);
													$("#smParam2").val(d[7]);
													}
                                                $("#noDataBackButton").hide();
												$('#showMasterForm').show();
												$("#addDataBackButton").show();
												$('html, body')
														.animate(
																{
																	scrollTop : $(
																			"#showMasterForm")
																			.offset().top
																}, 2000);
											} 
											else if( d!='' && $("#addOredit").val()==='E' && (lowerCase(d[1]) === "sf" || lowerCase(d[1]) ==="pf")){
													$("#etId").val(d[0]);
													$("#flag").val(d[1]);
													$('#smfname').val(d[2]);
													$('#smfname_mar').val(d[3]);
													$('#smfdescription').val(d[4]);
													$("#smfaction").val(d[5]);
													$('#smParam1').val(d[6]);
													$("#smParam2").val(d[7]);
                                                $("#noDataBackButton").hide();
												$('#showMasterForm').show();
												$("#addDataBackButton").show();
												$('html, body')
														.animate(
																{
																	scrollTop : $(
																			"#showMasterForm")
																			.offset().top
																}, 2000);
											}
											
											else {
												$("#etId").val('');
												$("#flag").val('');
												$('#smfname').val('')
												$('#smfname_mar').val('')
												$('#smfdescription').val('')
												$('#smParam1').val('');
												$("#smParam2").val('');
												$("#smfaction").val('').attr('readonly',false).removeClass("disablefield").addClass("mandClassColor");												
												$("#noDataBackButton").show();
												$("#addDataBackButton").hide();
												$('#showMasterForm').hide();
												if(d!='')
													{
												$('.msg-dialog-box')
														.html(
																getLocalMessage("menu.add.child.valid"));
												showModalBox('.msg-dialog-box');
													}
											}
										},
										onActivate : function(node) {
											node.select(true);

										},
									});

					$("#addNodeButton")
							.click(
									function() {

										var msg = '';
										if ($('#smfname').val() == '') {
											msg += "<h5 class='text-blue-2'>"
													+ getLocalMessage('menu.node.name.valid')
													+ getLocalMessage('menu.langType.english.label')
													+ "</h5>";
										}
										if ($('#smfname_mar').val() == '') {
											msg += "<h5 class='text-blue-2'>"
													+ getLocalMessage('menu.node.name.valid')
													+ getLocalMessage('menu.langType.reg.label')
													+ "</h5>";

										}
										if ($('#smfdescription').val() == '') {
											msg += "<h5 class='text-blue-2'>"
													+ getLocalMessage("menu.node.description.valid")
													+ "</h5>";

										}
										if ($('#smfaction').val() == '') {
											msg += "<h5 class='text-blue-2'>"
													+ getLocalMessage('menu.node.action.valid')
													+ "</h5>";

										}

										if (msg != '') {
											$('.msg-dialog-box').html(
													"<div class='padding-10'>" + msg + "</div>");
											showModalBox('.msg-dialog-box');
										} else {
											saveNode(
													$(this),
													getLocalMessage('menu.success.msg'),
													'entitlement.html?getSessionData');
										}
									});
					
					
					$("#rootNode").change(function(){
						
						
						var ischecked= $(this).is(':checked');
						if(ischecked){
							$("#tree3").dynatree("getRoot").visit(function(node){
								   node.select(false);
								});	
							$('#rootNode').prop('checked',true);
							$("#etId").val(null);
							$("#flag").val('R'); // Root node
							$("#smfaction").val('#').attr('readonly',true).removeClass("mandClassColor").addClass("disablefield");
	                        $("#noDataBackButton").hide();
							$('#showMasterForm').show();
							$("#addDataBackButton").show();
							$('html, body').animate({scrollTop : $("#showMasterForm").offset().top}, 2000);
						}else{
							$('#smfname').val('')
							$('#smfname_mar').val('')
							$('#smfdescription').val('')
							$('#smParam1').val('');
							$("#smParam2").val('');
							$("#etId").val('');
							$("#flag").val('');
							$("#smfaction").val('').attr('readonly',false).removeClass("disablefield").addClass("mandClassColor");
							$("#noDataBackButton").show();
							$('#showMasterForm').hide();
							$("#addDataBackButton").hide();
	                    }
						
						/**/
						
					});

				});

function lowerCase(value) {
	return value.toLowerCase();
}

function back() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'entitlement.html');
	$("#postMethodForm").submit();
}

function saveNode(obj, successMessage, successUrl) {

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};

	requestData = __serializeForm(theForm);

	var url = "entitlement.html?save";

	var returnData = __doAjaxRequestForSave(url, 'post', requestData, false,
			'', obj);
	if ($.isPlainObject(returnData)) {
		var message = returnData.command.message;

		var hasError = returnData.command.hasValidationError;

		if (!message) {
			message = successMessage;
		}

		if (message && !hasError) {
			if (returnData.command.hiddenOtherVal == 'SERVERERROR')
				showSaveResultBox(returnData, message, 'CitizenHome.html');

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
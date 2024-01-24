$(document)
		.ready(
				function() {
				
					var selectedChildren = [];
					var selectedParentChildList = [];
					var parent = '';
					var selectedEtId = '';
					var hasChild = false;
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
										
											parent = node.data.title;
											
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
											//D#131262(Changes from # to ~ after discussion with Rishikesh)
											var d = string.split("~");
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
													if($('#flag').val() == "S"){
														$('#smfaction').attr('readonly',true);
													}
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
											
											if($("#addOredit").val() =='E'){
												if(node.parent.parent.data.title == null){
													$.each(node.parent.childList, function( index, value ) {
														selectedParentChildList.push(value.data.title);
													});
													
												}
											}else{
												if(node.childList != null){
													hasChild = true;
													$.each(node.childList, function( index, value ) {
														  selectedChildren.push(value.data.title);
													});
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
										var errorList=[];
										
										if ($('#smfname').val() == '') {
											errorList.push(getLocalMessage('menu.node.name.valid')
													+ getLocalMessage('menu.langType.english.label'));
										}
										if ($('#smfname_mar').val() == '') {
											errorList.push(getLocalMessage('menu.node.name.valid')
													+ getLocalMessage('menu.langType.reg.label'));
										}
										if ($('#smfdescription').val() == '') {				
											errorList.push(getLocalMessage("menu.node.description.valid"));
										}
										if ($('#smfaction').val() == '') {
											errorList.push(getLocalMessage('menu.node.action.valid'));
										}
										
										/*var flag = $("#flag").val();
										var smFaction = $('#smfaction').val();
										var endHtml = '.html';
										
										if(flag != 'R'){
												if (!smFaction.endsWith(endHtml)) {
											            errorList.push("Please enter valid Node action (URL) which ends with '.html'");
												}
										}*/
										
										if (errorList.length > 0) {
											showEntitleError(errorList);
										} else {
										
											var nodeName = $.trim($('#smfname').val());
											var isChildAdded = false;
											
											if($("#addOredit").val() =='E'){
												$.each(selectedParentChildList, function( index, value ) {
													if(parent != value){
														if(value.toLowerCase() == nodeName.toLowerCase()){
															isChildAdded = true;
															return false;
														}
													}
													
												});
											}else{
												$.each(selectedChildren, function( index, value ) {
													if(value.toLowerCase() == nodeName.toLowerCase()){
														isChildAdded = true;
														return false;
													}
												});
											}
											
											if(isChildAdded){	
												errorList.push("Node name '"+nodeName+"' is already added");
											}
											
											
											if(errorList.length > 0){
												showEntitleError(errorList);
											}else{
												saveNode(
														$(this),
														getLocalMessage('menu.success.msg'),
														'entitlement.html?getSessionData');	
											}
										}
									});
					
					
						$("#rootNode").change(function(){
						
						if($("#tree3")[0].children[0].childNodes != null){
							hasChild = true;
							$.each($("#tree3")[0].children[0].childNodes, function( index, value ) {
								  selectedChildren.push(value.innerText);
							});
						}
						
					
						$("#errorDivEntitle").hide();
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
				
					});
				});

function lowerCase(value) {
	return value.toLowerCase();
}

function back() {
	window.location.href='entitlement.html';
	/*$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'entitlement.html');
	$("#postMethodForm").submit();*/
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
$("#smfaction").change(function(){
	if($('#smfaction').val() == '#'){
		$('#smfaction').attr("maxlength",1);
	}	
});
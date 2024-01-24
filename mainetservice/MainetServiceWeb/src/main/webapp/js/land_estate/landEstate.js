$(document)
		.ready(
				function() {
					
					$('#lnArea').keypress(
					    function(e) {
						var errorList = [];
						var charCode = (e.which) ? e.which : e.keyCode;
						if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
						    return false;
						}
						
						//after decimal restriction
						var regx	=   /^\d+(\.\d{0,2})?$/;
						var no	=	$(this).val();
						if(!regx.test(no)){
							no = no.substring(0, no.length-1);
							$(this).val(no);	
						}else{
							//total length restriction
							if(no.length>13){
								return false;
							}
						}
					 });
					
					
					
					

					$("#acqSummaryDatatables").dataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true,
								//D#40232
								"columnDefs": [ 
							        { targets: 2, orderable: false }
							    ]
								
							});
					
					

					$('.datepicker').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						minDate : '0d',
						maxDate:'0d',
						yearRange : "-100:+20",  
					});

					var dateFields = $('.datepicker');
					dateFields.each(function() {
						var fieldValue = $(this).val();
						if (fieldValue.length > 10) {
							$(this).val(fieldValue.substr(0, 10));
						}
					});

					
					$("#searchlandAcquisition")
							.click(
									function() {
										var errorList = [];
										var proposalNo = $("#proposalNo").val();
										var payTo = $("#payTo").val();
										var acqStatus = $("#acqStatusId").val();
										var locId = $("#locId").val();
										
										

										if (proposalNo == undefined)
											proposalNo = "";

										if (payTo == undefined)
											payTo = "";
										//D#78185
										if (acqStatus == undefined)
											//acqStatus = "A";//ACQUIRED
											acqStatus = "";

										if (locId == undefined)
											locId = "";

										if (proposalNo != 0 || payTo != ''|| acqStatus != ''|| locId != 0) {

											var requestData = '&proposalNo=' + proposalNo + '&payTo=' + payTo + '&acqStatus=' + acqStatus +'&locId=' +locId;
											var table = $('#acqSummaryDatatables').DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest('LandAcquisition.html?searchAcqData','POST', requestData, false,'json');
											if (ajaxResponse.length == 0) {
												errorList.push(getLocalMessage("land.acq.val.dataNotFound"));
												displayErrorsOnPage(errorList);
												return false;
											}

											var result = [];
											$.each(
															ajaxResponse,
															function(index) {
															    var obj = ajaxResponse[index];
																let lnaqId = obj.lnaqId;
																//$('#lnaqId').val(lnaqId);
																let apmApplicationId = obj.apmApplicationId;
																let acqDateDesc = obj.acqDateDesc;
																let lnDesc = obj.lnDesc;
																let lnServno = obj.lnServno;
																let acqStatus = obj.acqStatus;
																let assetId = obj.assetId;
															
																result
																		.push([
																				'<div class="text-center">'
																						+ (index + 1)
																						+ '</div>',
																				'<div class="text-center">'
																						+ apmApplicationId
																						+ '</div>',
																				'<div class="text-center">'
																						+ acqDateDesc
																						+ '</div>',
																				'<div class="text-center">'
																						+ lnDesc
																						+ '</div>',
																				'<div class="text-center">'
																						+ lnServno
																						+ '</div>',
																				'<div class="text-center">'
																						+ acqStatus
																						+ '</div>',
																				'<div class="text-center">'
																						+ '<button type="button"  class="btn btn-blue-2 margin-right-5"  onclick="getActionForDefination('
																						+ lnaqId
																						+ ','
																						+ apmApplicationId
																						+ ','
																						+ assetId
																						+ ',\'VIEW\')" title="View"><i class="fa fa-eye"></i></button>'
																						+ '<button  type="button" class="btn btn-blue-2"  onclick="getActionForDefination('
																						+ lnaqId
																						+ ','
																						+ apmApplicationId
																						+ ','
																						+ assetId
																						+ ',\'ASSET\')" title="Transfer In Asset"><i class="fa fa-arrow-right"></i></button>'
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
					/*Reset Form*/
					$("#resetForm").click(function(){
						var divName = '.content-page';
						var ajaxResponse = doAjaxLoading('LandAcquisition.html?addLandAcq', {}, 'html',divName);
						$(divName).removeClass('ajaxloader');
						$(divName).html(ajaxResponse);
						prepareTags();
					});
				});



function getActionForDefination(lnaqId, apmApplicationId, assetId,mode) {
	
	if (mode == 'VIEW') {
		var divName = '.content-page';
		var url = "LandAcquisition.html?viewLandAcqData";
		var actionParam = {
			'lnaqId' : lnaqId,
			'apmApplicationId' : apmApplicationId
		}
		var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false,'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else if(assetId != "" && assetId != null ){
			let errorList = [];
			errorList.push(getLocalMessage('land.acq.val.asset'));
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
				return false;
			}
	}else{
		pushInAssetModule(lnaqId,apmApplicationId);			
	}
}

function addLandAcq(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
	
}

function landAcquisitionValidation(errorList) {
	
	var payTo = $("#payTo").val();
	var lnArea = $("#lnArea").val();
	var address = $('#lnAddress').val();
	var locId = $("#locId").val();
	var lnDesc = $("#lnDesc").val();
	var acqPurpose = $("#acqPurpose").val();
	var acqModeId = $("#acqModeId").val();
	var lnServno = $("#lnServno").val();
	var currentUsage = $("#currentUsage").val();
	var lnOth = $("#lnOth").val();
	/*var acqDate = $("#acqDate").val();*/

	if (payTo == undefined || payTo == '') {
		errorList.push(getLocalMessage('land.acq.val.payTo'));
	}
	if (lnArea == undefined || lnArea == '') {
		errorList.push(getLocalMessage('land.acq.val.lnArea'));
	}
	
	if (address == undefined || address == '') {
		errorList.push(getLocalMessage('land.acq.val.lnAddress'));
	}

	if (locId == "0" || locId == undefined || locId == '') {
		errorList.push(getLocalMessage('land.acq.val.locId_chosn'));
	}
	if (acqModeId == "0" || acqModeId == undefined || acqModeId == '') {
		errorList.push(getLocalMessage('land.acq.val.acqType'));
	}
	if (lnServno == undefined || lnServno == '') {
		errorList.push(getLocalMessage('land.acq.val.surveyNo'));
	}
	if (currentUsage == undefined || currentUsage == '') {
		errorList.push(getLocalMessage('land.acq.val.currentUsage'));
	}
	if (acqPurpose == undefined || acqPurpose == '') {
		errorList.push(getLocalMessage('land.acq.val.acqPurpose'));
	}
	if (lnDesc == undefined || lnDesc == '') {
		errorList.push(getLocalMessage('land.acq.val.lnDesc'));
	}
	if (lnOth == "0" || lnOth == undefined || lnOth == '') {
		errorList.push(getLocalMessage('land.acq.val.lnOth'));
	}
	
	/*if(acqDate != ''){
		var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
		if(!pattern.test(acqDate)){
			errorList.push(getLocalMessage('land.acq.val.date'));
		}	
	}*/
	
	return errorList;
}

function getChecklistAndCharge(obj){
	
	var theForm = '#landAcqForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'LandAcquisition.html?getCheckListAndCharges';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
			'html');
	if(returnData != null){
		
		//var divName = '.content';
		//$(divName).html(returnData);
		$(formDivName).html(returnData);
		//$(divName).show();
	
		//$('.showDivDetails').html(returnData);
		prepareTags();
	}
}

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

//save land acquisition for
function saveLaqform(obj) {
	var errorList = [];
	errorList = landAcquisitionValidation(errorList);
	if (errorList.length > 0) {
		// display error msg
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'LandAcquisition.html', 'saveform');
	}
}

//CODE FOR ASSET INTEGRATION  BEGIN
function pushInAssetModule(lnaqId,apmApplicationId){
	var divName = '.content-page';
	var url = "LandAcquisition.html?assetIntegrate";
	var requestData = {
			'apmApplicationId' : apmApplicationId,
			'lnaqId' : lnaqId
			
		}
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	showCustomPopupForAsset(ajaxResponse);
	//$( "#searchlandAcquisition" ).trigger("click");
	
	
}

function showCustomPopupForAsset(ajaxResponse) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';
	var proceed =  getLocalMessage("bt.proceed");
	var cancel =  getLocalMessage("bt.back");
	let pushInAsset = ajaxResponse;
	
	if (pushInAsset == "Y") {
		
		sMsg = getLocalMessage("land.acq.asset.push");
		message += '<div class="text-center padding-top-25">'+'<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>'+'</div>';
		
		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+proceed+'\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>' + '</div>';		
	} else{
			sMsg = getLocalMessage(pushInAsset);
			message += '<div class="text-center padding-top-25">'+'<p class="text-center text-blue-2 padding-12">' + sMsg
			+ '</p>'+'</div>';
		
			message += '<div class=\'text-center padding-top-10 padding-bottom-10\'>'
			    	+ '<input class="btn btn-info" style="margin-right:10px" type=\'button\' value=\''+proceed+'\'  id=\'Proceed\' '
			    	+ ' onclick="closeSend();"/>' + '</div>';
		}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function closeSend(){
	$.fancybox.close();
	window.location.href='LandAcquisition.html';
}
//CODE FOR ASSET INTEGRATION  END


//LOI payable CODE START
function submitLOIPayable(obj) {
	var errorList = [];
	if ($("#wokflowDecision").val() == '') {
		errorList.push('Please select final Decision');
	}
	if (errorList.length > 0) {
		// display error MSG
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(obj, '', 'AdminHome.html', 'saveform');
	}
}
//LOI payable CODE END


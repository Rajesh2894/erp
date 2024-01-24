$(document)
		.ready(
				function() {
					$("#marriageCertificateTB").dataTable(
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
					
					$("footer").hide();

					$("#marDate").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : new Date()
					});

					$("#marDate").keyup(function(e) {
						if (e.keyCode != 8) {
							if ($(this).val().length == 2) {
								$(this).val($(this).val() + "/");
							} else if ($(this).val().length == 5) {
								$(this).val($(this).val() + "/");
							}
						}
					});

					$('#searchMRMCertificate')
							.click(
									function() {
										let errorList = [];
										let marriageDate = $('#marDate').val();
										let serialNo = $('#serialNo').val();
										let husbandId = $('#husbandId').val();
										let wifeId = $('#wifeId').val();
										if (wifeId != '' || husbandId != ''
												|| marriageDate != ''
												|| serialNo != '') {
											var requestData = '&marriageDate='
													+ marriageDate
													+ '&serialNo=' + serialNo
													+ '&husbandId=' + husbandId
													+ '&wifeId=' + wifeId;
											var table = $(
													'#marriageCertificateTB')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest(
													'MarriageCertificateGeneration.html?searchMRMCertificate',
													'POST', requestData, false,
													'json');
											if (ajaxResponse.length == 0) {
												errorList
														.push(getLocalMessage("mrm.validation.grid.nodatafound"));
												displayErrorsOnPage(errorList);
												return false;
											}
											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {
																var obj = ajaxResponse[index];
																let marId = obj.marId;
																let serialNo = obj.serialNo;
																let husbandName = obj.husbandDTO.firstNameEng
																		+ " "
																		+ obj.husbandDTO.lastNameEng;
																let wifeName = obj.wifeDTO.firstNameEng
																		+ " "
																		+ obj.wifeDTO.lastNameEng;
																let marDate = new Date(
																		obj.marDate);
																let month = marDate
																		.getMonth() + 1;
																let marFormateDate = marDate
																		.getDate()
																		+ "-"
																		+ month
																		+ "-"
																		+ marDate
																				.getFullYear()

																result
																		.push([
																				'<div align="center">'
																						+ (index + 1)
																						+ '</div>',
																				'<div align="center">'
																						+ marFormateDate
																						+ '</div>',
																				'<div align="center">'
																						+ serialNo
																						+ '</div>',
																				'<div align="center">'
																						+ husbandName
																						+ '</div>',
																				'<div align="center">'
																						+ wifeName
																						+ '</div>',
																				'<div class="text-center">'
																						+ '<button type="button"  class="btn btn-danger btn-sm btn-sm"  onclick="printCertificate(\''
																						+ marId
																						+ '\')" title="Print"><i class="fa fa-print"></i></button>'
																						+ '</div>' ]);
															});
											table.rows.add(result);
											table.draw();

										} else {
											errorList
													.push(getLocalMessage("lqp.validation.select.any.field"));
											displayErrorsOnPage(errorList);
										}
									});

				});

function closeErrBox() {
	$('.warning-div').addClass('hide');
}

function openMarriageCharge(marId) {
	var requestData = 'marId=' + marId;
	var response = __doAjaxRequest(
			'MarriageCertificateGeneration.html?openMarriageCharge', 'POST',
			requestData, false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

//D#122249
function displayMarCharge() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MarriageRegistration.html?displayMarCharge', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

//D#129628 start
function openHusbPhotoThumb() {
	//D#134156
	var errorList = [];
	var appId=$('#appid').val();
	var countdata=$('#docCount').val();
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('mrm.approval.decision'));
	else if(comments == undefined || comments =='')
		errorList.push(getLocalMessage('mrm.info.comment'));

		if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
	/*********** Set Local Storage Data ************/
		localStorage.setItem('appid',appId);
		localStorage.setItem('count',countdata);
		localStorage.setItem('Decision',decision);
		localStorage.setItem('comments',comments);
	/*********** End Local Storage Data ************/	
		$("#errorDiv").hide();
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('MarriageRegistration.html?openHusbPhotoThumb', {}, 'html',divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	
}
//D#129628 end

//D#134156
function backToHusbPhotoThumb() {
	$("#errorDiv").hide();
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MarriageRegistration.html?openHusbPhotoThumb', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}

function openWitnessPhotoThumb() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MarriageRegistration.html?openWitnessPhotoThumb', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

// Marriage Charge screen JS

function getCharges(element) {
	var errorList = [];
	let noOfCopies = $("#noOfCopies").val();

	if (noOfCopies == undefined || noOfCopies == '') {
		errorList.push(getLocalMessage('mrm.vldnn.noOfCopyReqd'));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		var requestData = element;
		var divName = '.content-page'
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequest(
				'MarriageCertificateGeneration.html?getCharges', 'POST',
				requestData, false, '', 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}

}

function saveAndGenerateLoi(obj) {
	return saveOrUpdateForm(obj,
			getLocalMessage('mrm.approve.create.success'), 'AdminHome.html',
			'generateLOIForMRM');
	//result= saveOrUpdateForm(loiSaveObj,getLocalMessage("master.loi.val.saveSucessfully"), 'LoiPayment.html?PrintReport', 'saveform');
}

function generatePrintAfterPayment(obj) {
	// common method of payment than after POPUP open certificate at new screen
}

function generatePrintAfterPayment(element) {
	let marId = $('#marId').val();
	
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			var	formName =	findClosestElementId(element, 'form');
			var theForm	=	'#'+formName;
			var requestData = {};
			requestData = __serializeForm(theForm);
			
			var ajaxResponse = __doAjaxRequest(
					'MarriageCertificateGeneration.html?saveCommonPayment', 'POST',
					requestData, false, '', element);
			if ($.isPlainObject(ajaxResponse)) {
				displayMessageOnSubmit(ajaxResponse.command.message,marId);
			}
			//window.location.href = 'MarriageCertificateGeneration.html';

		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {

			return saveOrUpdateForm(
					element,
					"Your application for No Dues Certificate saved successfully!",
					'MarriageCertificateGeneration.html?redirectToPay', 'save');
		}

}


function displayMessageOnSubmit(message,marId) {
	window.open('MarriageCertificateGeneration.html?PrintReport','_blank');
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	var d = '<h5 class=\'text-blue-2 text-center padding-15\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="printCertificate('+marId+')"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function printCertificate(marId){
	var requestData = 'marId=' + marId;
	var url = "MarriageCertificateGeneration.html?printCertificate";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData,false);
	var divName = formDivName;
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$.fancybox.close();
}

//D#134156
function approvalDecision(approvalData){
	var errorList = [];
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('mrm.approval.decision'));
	else if(comments == undefined || comments =='')
		errorList.push(getLocalMessage('mrm.info.comment'));

		if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,getLocalMessage('creation.success'),'AdminHome.html', 'approvalDecision');
	}

}
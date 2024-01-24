$(document)
		.ready(
				function() {

					$('#assetLinear').validate({
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
						this.value = this.value.replace(/[^\d.]/g, '') // numbers
																		// and
																		// decimals
																		// only
						.replace(/(\..*)\./g, '$1') // decimal can't exist more
													// than once
						.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits
															// after decimal
					});

					$(function() {
						$("#customFields").on(
								'click',
								'.addCF',
								function(i) {

									var $tableBody = $("#customFields").find(
											"tbody"), $trLast = $tableBody
											.find("tr:last"), $trNew = $trLast
											.clone();

									var newValue = parseInt($trLast.find(
											"#OISrNo").val()) + 1;
									$trNew.find("#OISrNo").val(newValue);
									$trNew.find("#b").val('');
									$trLast.after($trNew);
								});

						$("#customFields")
								.on(
										'click',
										'.remCF',
										function() {

											if ($("#customFields tr").length != 2) {
												$(this).parent().parent()
														.remove();
												reOrderTableIdSequence();
											} else {
												var errorList = [];
												errorList
														.push(getLocalMessage("asset.linear.delete.first.row"));
												displayErrorsOnPageView(errorList);
											}
										});
					});

				});
function saveAssetLinear(element) {
	
	var errorList = [];
	errorList = validateLinearAsset(errorList);
	
	if (errorList.length > 0) {
		$("#errorDivLine").show();
		showErrAstLine(errorList);
	} else {
		
		var divName = '#astLine';
		var targetDivName = '#astDoc';
		var requestData = __serializeForm('#assetLinear');
		var response = __doAjaxRequest(
				'AssetRegistration.html?saveAstLinePage', 'POST', requestData,
				false, 'html');
		$(divName).removeClass('ajaxloader');
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		//D#34059
		var mode = $("#modeType").val();
		let parentTab =  '#assetParentTab';
		if(mode == 'D'){//Draft
			parentTab = '#assetViewParentTab';
		}
		processTabSaveRes(response, targetDivName, divName,parentTab);
		prepareDateTag();
	}
}

function showErrAstLine(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstLine()"><span aria-hidden="true">&times;</span></button><ul>';
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
	errorList = [];
}

function closeErrBoxAstLine() {
	$('.warning-div').addClass('hide');
}
function showConfirmBoxAstLine(sucessMsg) {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + sucessMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedAstLine()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}

function proceedAstLine() {
	
	saveAstLine();
	$.fancybox.close();
}

function saveAstLine() {
	
	var divName = '.tab-pane';
	var requestData = __serializeForm('#assetLinear');
	var response = __doAjaxRequest('AssetRegistration.html?saveAstLinePage',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	$('.nav li#linear-tab').removeClass('active');
	$('.nav li#doc-tab').addClass('active');
}

function backFormAstLine() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}
function saveAstLinear(element) {
	
	var errorList = [];
	var modeType = $("#modeType").val();
	if (modeType == 'E') {
		errorList = validateLinearAsset(errorList);
		
		if (errorList.length > 0) {
			$("#errorDivL").show();
			showErrAstLine(errorList);
		} else {
			var requestData = __serializeForm('#assetLinear');
			var ajaxResponse = __doAjaxRequest(
					'AssetRegistration.html?saveAstLinePage', 'POST',
					requestData, false, '', element);
			if ($.isPlainObject(ajaxResponse)) {
				
				var message = ajaxResponse.command.message;
				displayMessageOnSubmit(message);
			}
		}
	} else {
		saveAssetLinear(element);
	}

}
function validateLinearAsset(errorList) {
	var startPoint = $("#startPoint").val();
	var endPoint = $("#endPoint").val();
	var length = $("#linearLength").val();
	let linearTabStartPoiint = $("#linearTabStartPoiint").val();
	let linearTabEndPoiint = $("#linearTabEndPoiint").val();
	if (startPoint == "0" || startPoint == undefined || startPoint == '') {
		errorList.push(getLocalMessage("asset.vldnn.startPoint"));
	}

	if (endPoint == "0" || endPoint == undefined || endPoint == '') {
		errorList.push(getLocalMessage("asset.vldnn.endPoint"));
	}

	if (length == "0" || length == undefined || length == '') {
		errorList.push(getLocalMessage("asset.vldnn.length"));
	}
	//D#80699
	if (linearTabStartPoiint == ".") {
		errorList.push(getLocalMessage("asset.linear.start.point.grid"));
	}
	if (linearTabEndPoiint == ".") {
		errorList.push(getLocalMessage("asset.linear.end.point.grid"));
	}
	
	return errorList;
}

function resetLine() {
	
	var divName = '#astLine';
	var requestData = {
			"resetOption":"reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstLinePage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}
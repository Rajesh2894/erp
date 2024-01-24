$(document).ready(function() {

	$("#InsuranceStartDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,
		onSelect : function(selected) {
			$("#InsuranceExpiryDate").datepicker("option", "minDate", selected)
		}
	});
	$("#InsuranceStartDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#InsuranceExpiryDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,
		onSelect : function(selected) {
			$("#InsuranceStartDate").datepicker("option", "maxDate", selected)
		}
	});
	$("#InsuranceExpiryDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('.decimal').on('input', function() {
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals only
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
		.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after decimal
	});

	$('#assetInsurance').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	showAndHides();
});

function saveAssetInsuranceDetails(element) {
	
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInsu(errorList);
	} else {
		$("#errorDiv").hide();
		//showConfirmBoxAstInsu("Asset Insurance Details Saved Successfully");
		var divName = '#astInsu';
		var targetDivName = '#astLease';
		/*var theForm = '#' + formName;
		
		var requestData = __serializeForm(theForm);*/
		var requestData = __serializeForm('#assetInsurance');
		var response = __doAjaxRequest(
				'AssetRegistration.html?saveAstInsuPage', 'POST', requestData,
				false, 'html');
		$(divName).removeClass('ajaxloader');
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#assetLeasing');
		
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			targetDivName = '#astCod';
		}
		//#D34059
		var mode = $("#modeType").val();
		let parentTab =  '#assetParentTab';
		if(mode == 'D'){//Draft
			parentTab = '#assetViewParentTab';
		}
		processTabSaveRes(response, targetDivName, divName,parentTab);
		prepareDateTag();

	}
}
function showErrAstInsu(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstInsu()"><span aria-hidden="true">&times;</span></button><ul>';
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

function closeErrBoxAstInsu() {
	$('.warning-div').addClass('hide');
}
function showConfirmBoxAstInsu(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + sucessMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedAstInsu()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	$("#validationDiv :input").prop("disabled", true);

	showModalBoxWithoutClose(errMsgDiv);

}

function proceedAstInsu() {
	
	saveAstInsu();
	$.fancybox.close();
}

function saveAstInsu() {
	
	var divName = '.tab-pane';
	var requestData = __serializeForm('#assetInsurance');
	var response = __doAjaxRequest('AssetRegistration.html?saveAstInsuPage',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	$('.nav li#insurance-tab').removeClass('active');
	$("#acquisitionMethod").change(function(e) {
		
		if ($('#acquisitionMethod option:selected').attr('code') == "LE") {
			$('.nav li#leasing-comp').addClass('active');
		} else {
			$('.nav li#depre-tab').addClass('active');
		}
	});
}

function backFormAstInsu() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}
//this function is for save data in both case edit or save 
function saveAstInsurDet(element) {
	
	var errorList = [];
	errorList = insurnaceValidation(errorList);
	if (errorList.length > 0) {
		$("#errorDivI").show();
		showErrAstInsu(errorList);
	} else {
		var modeType = $("#modeType").val();
		var subModeType = $("#subModeType").val();
		if (modeType == 'E') {
			var requestData = __serializeForm('#assetInsurance');
			var ajaxResponse = __doAjaxRequest(
					'AssetRegistration.html?saveAstInsuPage', 'POST',
					requestData, false, '', element);

			if (subModeType == 'Add' || subModeType == 'Edit') {
				$('.pagediv').html(ajaxResponse);
//				$('.pagediv').html(ajaxResponse);

			}

			if ($.isPlainObject(ajaxResponse)) {
				
				var message = ajaxResponse.command.message;
				displayMessageOnSubmit(message);
			}
		} else {
			saveAssetInsuranceDetails(element);
		}
	}

}

//this function is used to sacve dfunction
function backToAstInsurDataTable() {
	
	var errorList = [];

	var modeType = $("#modeType").val();
	//	var subModeType = 	$("#subModeType").val();
	if (modeType == 'E') {
//		var requestData = __serializeForm('#assetInsurance');
		var ajaxResponse = __doAjaxRequest(
				'AssetRegistration.html?showAstInsuPageDataTable', 'POST',
				'', false, '', '');

		$('.pagediv').html(ajaxResponse);
		/*if (subModeType == 'Add' || subModeType == 'Edit') {
			$('.pagediv').html(ajaxResponse);
			$('.pagediv').html(ajaxResponse);

		}*/
	}
}
function insurnaceValidation(errorList) {
	
	var modeType = $("#modeType").val();
	if ($('input:checkbox[id=isInsuApplicable]').is(':checked') && (modeType=='C' ||modeType=='D'))
		{
	var InsuranceStartDate = $('#InsuranceStartDate').val();
	var InsuranceExpiryDate = $('#InsuranceExpiryDate').val();
	if (InsuranceStartDate != "0" && InsuranceStartDate != undefined
			&& InsuranceStartDate != "") {
		errorList = dateValidation(errorList, 'InsuranceStartDate',
				'InsuranceExpiryDate');
		errorList = validateFutureDate(errorList, 'InsuranceStartDate');

	}
	if (InsuranceExpiryDate != "0" && InsuranceExpiryDate != undefined
			&& InsuranceExpiryDate != "") {
		errorList = dateValidation(errorList, 'InsuranceStartDate',
				'InsuranceExpiryDate');
		errorList = validateFutureDate(errorList, 'InsuranceExpiryDate');

	}
		}

	return errorList;

}

function resetInsurance() {
	
	var divName = '#astInsu';
	var requestData = {
		"resetOption" : "reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstInsuPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}
function showAndHides() {
	if ($('input:checkbox[id=isInsuApplicable]').is(':checked')){
		//id-'hideAndShowDeatils' to hideAndShowInsurance
		//id name changing because id-'hideAndShowDeatils' already added in DOM structure with display :none
		$("#hideAndShowInsurance").show();
		$("#insuApplicableLbl").addClass("required-control");
	}else{
		$("#hideAndShowInsurance").hide();
		$("#insuApplicableLbl").removeClass( "required-control");
	}
}
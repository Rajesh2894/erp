$(document).ready(function(){
	
	 $("#InsuranceStartDate").datepicker({
	    	dateFormat: 'dd/mm/yy',
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	          $("#InsuranceExpiryDate").datepicker("option","minDate", selected)
	        }
	    });
	 $("#InsuranceStartDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });

	    $("#InsuranceExpiryDate").datepicker({
	    	dateFormat: 'dd/mm/yy',
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	           $("#InsuranceStartDate").datepicker("option","maxDate", selected)
	        }
	    });
	    $("#InsuranceExpiryDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
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
		onkeyup: function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout: function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}});
});


function saveAssetInsuranceDetails(element) {
	
	var errorList = [];
         if (errorList.length > 0) {
		 $("#errorDiv").show();
		 showErrAstInsu(errorList);
		 } 
	    else {
		$("#errorDiv").hide();
		//showConfirmBoxAstInsu("Asset Insurance Details Saved Successfully");
		var divName = '#astInsu';
		var targetDivName = '#astLease';
		/*var theForm = '#' + formName;
		
		var requestData = __serializeForm(theForm);*/
		var requestData = __serializeForm('#assetInsurance');
		var response = __doAjaxRequest(
				'AssetRegistration.html?saveAstInsuPage', 'POST',
				requestData, false, 'html');
		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#assetLeasing');
		
		if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
			targetDivName = '#astCod';
		}
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
	function showErrAstInsu(errorList) {
	     
	     var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstInsu()"><span aria-hidden="true">&times;</span></button><ul>';
	     $.each(errorList, function(index) {
		 errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
	     errMsg += '</ul>';			 
	     $(".warning-div").html(errMsg);					
	     $(".warning-div").removeClass('hide')
	     $('html,body').animate({ scrollTop: 0 }, 'slow');
	     errorList = [];	
	}

function closeErrBoxAstInsu() {
        $('.warning-div').addClass('hide');
    }
function showConfirmBoxAstInsu(sucessMsg) {
	     var errMsgDiv = '.msg-dialog-box';
	     var message = '';
	     var cls = 'Proceed';
         message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
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
	var response = __doAjaxRequest(
			'AssetRegistration.html?saveAstInsuPage', 'POST',
			requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	$('.nav li#insurance-tab').removeClass('active');
	$("#acquisitionMethod").change(function(e){
    	
		if($('#acquisitionMethod option:selected').attr('code') == "LE")
		{
			$('.nav li#leasing-comp').addClass('active');
		}else{
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
function saveAstInsurDetDataTable(element) {
	
	var errorList = [];
	errorList = insurnaceValidation(errorList);
    if (errorList.length > 0) {
	 $("#errorDivI").show();
	 showErrAstInsu(errorList);
	 }
    else{
	var modeType = 	$("#modeType").val();
//	var subModeType = 	$("#subModeType").val();
	
	var requestData = {
			"subModeType":"save"
	}
	
	if(modeType=='E')
		{
//		var requestData = __serializeForm('#assetInsurance');
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?saveAstInsuPage', 'POST', requestData, false,'', element);
		if ($.isPlainObject(ajaxResponse))
		{
			
			var message = ajaxResponse.command.message;
			displayMessageOnSubmit(message);
		}
		}
	else
		{
		saveAssetInsuranceDetails(element);
		}
    }
	
}


/*
 * Used to display edit page
 * 
 * */
function saveAstInsurDetDataTable(element,subModeType) {
	
	var errorList = [];
	errorList = insurnaceValidation(errorList);
    if (errorList.length > 0) {
	 $("#errorDivI").show();
	 showErrAstInsu(errorList);
	 }
    else{
	var modeType = 	$("#modeType").val();
//	var subModeType = 	$("#subModeType").val();
	
	var requestData = {
			"subModeType":"save"
	}
	
	if(modeType=='E')
		{
//		var requestData = __serializeForm('#assetInsurance');
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?saveAstInsuPage', 'POST', requestData, false,'', element);
		if ($.isPlainObject(ajaxResponse))
		{
			
			var message = ajaxResponse.command.message;
			displayMessageOnSubmit(message);
		}
		}
	else
		{
		saveAssetInsuranceDetails(element);
		}
    }
	
}



function insurnaceValidation(errorList) {
	
	var InsuranceStartDate = $('#InsuranceStartDate').val();
	var InsuranceExpiryDate = $('#InsuranceExpiryDate').val();
	if (InsuranceStartDate != "0" && InsuranceStartDate != undefined
			&& InsuranceStartDate != "") {
		errorList = dateValidation(errorList,'InsuranceStartDate','InsuranceExpiryDate');
		errorList =  validateFutureDate(errorList , 'InsuranceStartDate');
		
	}
	if (InsuranceExpiryDate != "0" && InsuranceExpiryDate != undefined
			&& InsuranceExpiryDate != "") {
		errorList = dateValidation(errorList,'InsuranceStartDate','InsuranceExpiryDate');
		errorList =  validateFutureDate(errorList , 'InsuranceExpiryDate');
		
	}
	
	return errorList;
	
	
}

function resetInsurance() {
	
	$('#astInsu').find('input[type=text]').val('');
	$('#insuranceCostCenterId').val('').trigger("chosen:updated");
}

function openASTInsu(urlParam,subModeType,insuId) {
	
	
	
	var requestData = {
			"subModeType":subModeType,
			"insuId":insuId
	}

	var response = __doAjaxRequest('AssetRegistration.html?' + urlParam + '',
			'POST', requestData, false, 'html');
	$('.pagediv').html(response);
	$('.pagediv').html(response);
	prepareDateTag();
}

function viewASTInsuPagePopUp(urlParam,subModeType,insuId) {
	
	
	
	var requestData = {
			"subModeType":subModeType,
			"insuId":insuId
	}

	var response = __doAjaxRequest('AssetRegistration.html?' + urlParam + '',
			'POST', requestData, false, 'html');
	
	
	var divName = '.child-popup-dialog';					
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	showMsgModalBox(divName);
	prepareDateTag();
	
	
	/*$('.pagediv').html(response);
	$('.pagediv').html(response);
	prepareDateTag();*/
}

function showMsgModalBox(childDialog) {
	
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
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


function CloseFancyBox(element) {
	
	$.fancybox.close();

}

function backToHomePage() {
	
	var response = __doAjaxRequest('AssetRegistration.html?showEditAssetPage','POST', {}, false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

$(document).ready(function(){	
	 $("#leaseStartingDate").datepicker({
	    	dateFormat: 'dd/mm/yy',
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	          $("#leaseEndingDate").datepicker("option","minDate", selected)
	        },
	     minDate : new Date()
	    });
	 $("#leaseStartingDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });

	    $("#leaseEndingDate").datepicker({
	    	dateFormat: 'dd/mm/yy',
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	           $("#leaseStartingDate").datepicker("option","maxDate", selected)
	        }
	    });  
	    $("#leaseEndingDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });
	    $("#leaseAgreementDate").datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			maxDate : new Date()
		});
	    $("#leaseAgreementDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });
	    $("#leaseNoticeDate").datepicker({
			dateFormat: 'dd/mm/yy',	
			changeMonth: true,
			changeYear: true,
			maxDate : new Date()
		});
	    $("#leaseNoticeDate").keyup(function(e){
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
	    
	$('#assetLeasing').validate({
		onkeyup: function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout: function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}});
	
});


function saveLeasingCompany(element) {

	
	var errorList = [];
	     if (errorList.length > 0) {
			 $("#errorDiv").show();
			 showErrAstLease(errorList);
		 } 
	
	    else {
			$("#errorDiv").hide();
			
			//showConfirmBoxAstLease("Asset Leasing Company Details Saved Successfully");
			var divName = '#astLease';
			var targetDivName = '#astCod';
			/*var theForm = '#' + formName;
			
			var requestData = __serializeForm(theForm);*/
			var requestData = __serializeForm('#assetLeasing');
			var response = __doAjaxRequest(
					'AssetRegistration.html?saveAstLeasePage', 'POST',
					requestData, false, 'html');
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
			/*$(divName).html(response);
			$('.nav li#leasing-comp').removeClass('active');
			$('.nav li#depre-tab').addClass('active');*/
		}
     }
	function showErrAstLease(errorList) {
	     
	     var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstLease()"><span aria-hidden="true">&times;</span></button><ul>';
	     $.each(errorList, function(index) {
		 errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
	     errMsg += '</ul>';			 
	     $(".warning-div").html(errMsg);					
	     $(".warning-div").removeClass('hide')
	     $('html,body').animate({ scrollTop: 0 }, 'slow');
	     errorList = [];	
	}

function closeErrBoxAstLease() {
        $('.warning-div').addClass('hide');
    }
function showConfirmBoxAstLease(sucessMsg) {
	     var errMsgDiv = '.msg-dialog-box';
	     var message = '';
	     var cls = 'Proceed';
         message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
		 message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceedAstLease()"/>' + '</div>';
		 
		 $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		 $(errMsgDiv).html(message);
		 $(errMsgDiv).show();
		 $('#btnNo').focus();
		$("#validationDiv :input").prop("disabled", true); 
		 showModalBoxWithoutClose(errMsgDiv);
}


function proceedAstLease() {
	
	saveAstLease();
	$.fancybox.close();
}

function saveAstLease() {
	
	var divName = '.tab-pane';
	var requestData = __serializeForm('#assetLeasing');
	var response = __doAjaxRequest(
			'AssetRegistration.html?saveAstLeasePage', 'POST',
			requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	$('.nav li#leasing-comp').removeClass('active');
	$('.nav li#depre-tab').addClass('active');
}

function backFormAstLease() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}
function saveLeasComp(element) {
	
	var errorList = [];
	errorList = validationLeasingDate(errorList);
    if (errorList.length > 0) {
		 $("#errorDivL").show();
		 showErrAstLease(errorList);
	 } 
    else{
	var modeType = 	$("#modeType").val();
	if(modeType=='E')
		{
		var requestData = __serializeForm('#assetLeasing');
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?saveAstLeasePage', 'POST', requestData, false,'', element);
		if ($.isPlainObject(ajaxResponse))
		{
			
			var message = ajaxResponse.command.message;
			displayMessageOnSubmit(message);
		}
		}
	else
		{
		saveLeasingCompany(element);
		}
    }
	
}

function validationLeasingDate(errorList) {
	
	
	var leaseStartingDate = $("#leaseStartingDate").val();
	var leaseEndingDate = $("#leaseEndingDate").val();
	var leaseAgreementDate = $("#leaseAgreementDate").val();
	var leaseNoticeDate = $("#leaseNoticeDate").val();
	if (leaseStartingDate != "0" && leaseStartingDate != undefined
			&& leaseStartingDate != '') {
		errorList = dateValidation(errorList,'leaseStartingDate','leaseEndingDate');
		errorList =  validatedate(errorList , 'leaseStartingDate');
		
	}
	if (leaseEndingDate != "0" && leaseEndingDate != undefined
			&& leaseEndingDate != '') {
		errorList = dateValidation(errorList,'leaseStartingDate','leaseEndingDate');
		errorList =  validateFutureDate(errorList , 'leaseEndingDate');
		
	}
	if (leaseAgreementDate != "0" && leaseAgreementDate != undefined
			&& leaseAgreementDate != '') {
		
		errorList = validatedate(errorList,'leaseAgreementDate');
		
	}
	if (leaseNoticeDate != "0" && leaseNoticeDate != undefined
			&& leaseNoticeDate != '') {
		
		errorList = validatedate(errorList,'leaseNoticeDate');
		
	}
	return errorList;
}

function resetLease() {
	
	var divName = '#astLease';
	var requestData = {
			"resetOption":"reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstLeasePage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}
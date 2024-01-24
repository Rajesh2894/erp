$(document).ready(function(){
	chosen();
	$("#accumulDeprDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date()
	});
	
	$("#accumulDeprDate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });


$('#assetDepreChart').validate({
		onkeyup: function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout: function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}});

$('.decimal').on('input', function() {
	this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals only
	.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
	.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after decimal
});

showAndHide();
});

function saveDepriciationChart(element) {
		    
	         var errorList = [];
			    
			    errorList = validateChart(errorList);
		     
             if (errorList.length > 0) {
			 $("#errorDiv").show();
			 showErrAstDepreChart(errorList);
			 } 
		
		    else {
			
			//showConfirmBoxAstDepreChart("Asset Depreciation Chart Details Saved Successfully");
			var divName = '#astCod';
			
			var targetDivName = '#astLine';
			/*var theForm = '#' + formName;
			
			var requestData = __serializeForm(theForm);*/
			var requestData = __serializeForm('#assetDepreChart');
			var response = __doAjaxRequest(
					'AssetRegistration.html?saveAstDepreChartPage', 'POST',
					requestData, false, 'html');
			$(divName).removeClass('ajaxloader');
			//document.getElementById(divName).style.display = "none";
			//$(divName).css("display", "none");
			var tempDiv = $('<div id="tempDiv">' + response + '</div>');
			var errorsPresent = tempDiv.find('#assetLinear');
			
			if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
				targetDivName = '#astDoc';
			}
			//#D34059
			var mode = $("#modeType").val();
			let parentTab =  '#assetParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			processTabSaveRes(response, targetDivName, divName,parentTab);
			prepareDateTag();
			/*$(divName).html(response);
			$('.nav li#depre-tab').removeClass('active');
			$("#assetGroup").change(function(e){
				if($('#assetGroup option:selected').attr('code') == "L")
				{
					$('.nav li#linear-tab').addClass('active');
				}
				else{
						$('.nav li#doc-tab').addClass('active');
					}
			});	*/
			}
          }
		
	

	function showErrAstDepreChart(errorList) {
		     var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstDepreChart()"><span aria-hidden="true">&times;</span></button><ul>';
		     $.each(errorList, function(index) {
			 errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
		});
		     errMsg += '</ul>';			 
		     $(".warning-div").html(errMsg);					
		     $(".warning-div").removeClass('hide')
		     $('html,body').animate({ scrollTop: 0 }, 'slow');
		     errorList = [];	
		}
 
   function closeErrBoxAstDepreChart() {
	        $('.warning-div').addClass('hide');
	    }
	function showConfirmBoxAstDepreChart(sucessMsg) {
		
		     var errMsgDiv = '.msg-dialog-box';
		     var message = '';
		     var cls = 'Proceed';
	         message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
			 message += '<div class=\'text-center padding-bottom-10\'>'
					+ '<input type=\'button\' value=\'' + cls
					+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
					+ ' onclick="proceedAstDepreChart()"/>' + '</div>';

			 $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
			 $(errMsgDiv).html(message);
			 $(errMsgDiv).show();
			 $('#btnNo').focus();
			 showModalBoxWithoutClose(errMsgDiv);
	}
	
	
	function proceedAstDepreChart() {
		
		saveAstDepreChart();
		$.fancybox.close();
	}
	
	function saveAstDepreChart() {
		
		var divName = '.tab-pane';
		var requestData = __serializeForm('#assetDepreChart');
		var response = __doAjaxRequest(
				'AssetRegistration.html?saveAstDepreChartPage', 'POST',
				requestData, false, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		$('.nav li#depre-tab').removeClass('active');
		$("#assetGroup").change(function(e){
			if($('#assetGroup option:selected').attr('code') == "L")
			{
				$('.nav li#linear-tab').addClass('active');
			}
			else{
					$('.nav li#doc-tab').addClass('active');
				}
		});	
	}
	
	function backFormAstDepreChart() {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'AssetRegistration.html');
		$("#postMethodForm").submit();
	}
	
	function saveDeprChart(element) {
		
		var errorList=[];
		var modeType = 	$("#modeType").val();
		errorList = validateChart(errorList);
        if (errorList.length > 0) {
		 $("#errorDivD").show();
		 showErrAstDepreChart(errorList);
		 } 
        else
        	{
		if(modeType=='E')
			{
			var requestData = __serializeForm('#assetDepreChart');
			var ajaxResponse = __doAjaxRequest('AssetRegistration.html?saveAstDepreChartPage', 'POST', requestData, false,'', element);
			if ($.isPlainObject(ajaxResponse))
			{
				
				var message = ajaxResponse.command.message;
				displayMessageOnSubmit(message);
			}
			}
		else
			{
			saveDepriciationChart(element);
			prepareDateTag();
			}
        	}
		
	}
	
function  validateChart(errorList) {
	
	if ($('input:checkbox[id=deprApplicable]').is(':checked'))
		{
		var deprApplicable=$('input:checkbox[id=deprApplicable]').is(':checked');
	 var chartOfDepreName =   $("#chartOfDepreName").val();
     var accumulDeprDate =   $("#accumulDeprDate").val();
     var oriUseYear =   $("#oriUseYear").val();
     var accumuDepAcId =   $("#accumuDepAc").val();
     
     if(chartOfDepreName == "0" || chartOfDepreName == undefined || chartOfDepreName == ''){
	 errorList.push(getLocalMessage('asset.depreciation.category'));
	 }
    
     if(accumulDeprDate != "0" || accumulDeprDate != undefined || accumulDeprDate != ''){
    	 errorList =  validatedate(errorList , 'accumulDeprDate');
		 }
     if(deprApplicable == "0" || deprApplicable == undefined || deprApplicable == ''){
		 errorList.push(getLocalMessage('asset.depreciation.app'));
		 }
     if(oriUseYear == "0" || oriUseYear == undefined || oriUseYear == ''){
		 errorList.push(getLocalMessage('asset.depreciation.agelife'));
		 }
     //D#33153
     if($('#accountIsActiveOrNot').val() == 'true'){
    	 if(accumuDepAcId == "0" || accumuDepAcId == undefined || accumuDepAcId == ''){
    		 errorList.push(getLocalMessage('asset.depreciation.accumlateddepre'));
    	 }	 
     }
     
		}
	return errorList;
	
}	

function resetChart() {
	
	var divName = '#astCod';
	var requestData = {
			"resetOption":"reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstDepreChartPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}
function showAndHide() {
	if ($('input:checkbox[id=deprApplicable]').is(':checked')){
			$("#hideAndShowDeatil").show();
			//show BT based on modeType
			if($("#modeType").val()=='E'){
			//D#83926
				$('#saveDprBT').show();
			}
			$("#deprApplicableLbl").addClass("required-control");
		
		}else{
			$("#hideAndShowDeatil").hide();
			//hide BT based on modeType
			if($("#modeType").val()=='E'){
				$('#saveDprBT').hide();
			}
			$("#deprApplicableLbl").removeClass( "required-control");
		}
	
}
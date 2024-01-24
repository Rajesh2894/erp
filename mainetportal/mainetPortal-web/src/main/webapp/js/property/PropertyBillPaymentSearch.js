 function SearchButton(obj)
 {
		var errorList = [];
		errorList = ValidateDetails(errorList);	
		if (errorList.length == 0)
		{
/*			var formAction	=	$('#PropertyBillPayment').attr('action');
			var url=formAction+'?getBillPaymentDetail';
			$('#PropertyBillPayment').attr('action', url);
			$('#PropertyBillPayment').submit();
			$("#paymentInfo").show(); */	
			
			var theForm	=	'#PropertyBillPaymentSearch';
			var requestData = {};
			requestData = __serializeForm(theForm);
			var ajaxResponse = __doAjaxRequest(
					'PropertyBillPayment.html?getBillPaymentDetail', 'POST',
					requestData, false, 'html');

			$("#dataDiv").html(ajaxResponse);
			return false;
			
 	 	}		
		else{
 		showErrorOnPage(errorList);
		}
 }
 
 function ValidateDetails(errorList){
		
		var propNo=$("#assNo").val();
		var oldPropNo= $("#assOldpropno").val();
		if(propNo== "" && oldPropNo==""){
			errorList.push(getLocalMessage("property.changeInAss"));
		}
		return errorList;

	}
	 function showErrorOnPage(errorList){
			var errMsg = '<ul>';
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$("#errorDiv").html(errMsg);
			$("#errorDiv").show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
			return false;
		}
	 
	 function redirectPage(){
		 
		 var loginUser=$("#empLoginName").val();
			if(loginUser==getLocalMessage("citizen.noUser.loginName")){
					getCitizenLoginForm("N");
				}else{
					 window.location.assign("NoChangeInAssessment.html");
				}
	 }
	 
	 
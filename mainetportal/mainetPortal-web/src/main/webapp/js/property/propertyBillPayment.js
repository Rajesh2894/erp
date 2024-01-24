/*$(document).ready(function(){
	$("#paymentInfo").hide();
 });
*/
 




 
 function savePropertyFrom(element,flag){
	 
		var errorList = [];
		var payAmount= $("#payAmount").val();
		if(payAmount=='' ||payAmount == undefined || (payAmount<=0))
		{
				errorList.push(getLocalMessage("property.EnterAmountValid"));
				showError(errorList);
		}
		else{

	 	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
	 		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'PropertyBillPayment.html?redirectToPay', 'saveform');
	 		}
	 		else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
	 			{
	 			 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'PropertyBillPayment.html?PrintReport', 'saveform');
	 			}
	 		else
	 		{
	 		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'PropertyBillPayment.html', 'saveform');
	 		}

	 var a ="String";
	 }
}
 
 function showError(errorList){
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
 
 function searchBillPay(element)
 {
 var url = "PropertyBillPayment.html?propertyBackButton";
 var postdata = {};
 var response = __doAjaxRequest(url,'POST', postdata, false, 'html');
 $('.content-page').html(response);
 }
 
 function resetButton(element){
	 var theForm	=	'#PropertyBillPayment';
	 var url = "PropertyBillPayment.html?getBillPaymentDetail";
	 var requestData = {};
	 requestData = __serializeForm(theForm);
	 var response = __doAjaxRequest(url,'POST', requestData, false, 'html');
	 $('.content-page').html(response);
 }

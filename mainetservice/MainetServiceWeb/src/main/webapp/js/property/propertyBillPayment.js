$(document).ready(function(){
	prepareDateTag();
	let totalPay= Number($('#totalPayable').val());
	$('#totalPayable').val(totalPay.toFixed(1));
});
 

 function savePropertyFrom(element,flag){
	 var errorList = [];
		var partialAmt= $("#payAmount").val();
		var a = $("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val();
		if(partialAmt=='' ||partialAmt == undefined || (partialAmt<=0))
		{
				errorList.push(getLocalMessage("property.billPayment.amount"));
				showError(errorList);
		}else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == '' || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == undefined){
			errorList.push(getLocalMessage("prop.select.collection.mode"));
			showError(errorList);
		}
		else{
	 	$("#errorDiv").hide();	
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
	 	
	 	}
	 }
 
 function prepareDateTag() {
		var dateFields = $('.trimDateTime');
		dateFields.each(function () {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
	}

function backToMain(){	
	var data={};
	var URL='PropertyBillPayment.html?backToMainPage';
	var returnData=__doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
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




/**
 * 
 */
$(document).ready(function(){
	$('#trdWard1').val(-1);
	$('#trdWard2').val(-1);
	/*$('#trdWard3').val(-1);
	$('#trdWard4').val(-1);
	$('#trdWard5').val(-1);*/
	
});
	function resetForm(){
		
		$('input[type=text]').val('');
		$(".alert-danger").hide();
		$("#TradeLicenseRegisterFormReport").validate().resetForm();
	}
	
	function saveForm(){
		
		var errorList=[];
		var ward1=$('#trdWard1').val();
		var ward2=$("#trdWard2").val();
		/*var ward3=$("#trdWard3").val();
		var ward4=$("#trdWard4").val();
		var ward5=$("#trdWard5").val();*/
		
		if (ward1 == 0) {
		errorList.push(getLocalMessage("trade.licence.register.validation.ward"));
		}
		if(ward2 == 0 && ward1 != -1){
         errorList.push(getLocalMessage("trade.licence.register.validation.zone"));
			
		}
		/*if(ward3 ==0){
			errorList.push(getLocalMessage("trade.validation.ward3"));
		}
		if(ward4 ==0){
			errorList.push(getLocalMessage("trade.validation.ward4"));
		}
		if(ward5 == 0){
			errorList.push(getLocalMessage("trade.validation.ward5"));
		}*/
		
		if(ward1==-1){
			ward1=0;
			
			}
		if(ward2==-1){
			ward2=0;
		}
		
		
		if((ward2 == 0 &&  ward1 == -1) || (ward2 == undefined)){
			ward2=0; 
		}
		/*if(ward3==-1){
			ward3 =0;
		}
		if(ward4==-1){
			ward4=0;
		}
		if(ward5==-1){
			ward5=0;
		}*/
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
		}
	 else {
			var divName = '.content-page';
			$("#errorDiv").hide();
			
			var requestData = '&ward1=' + ward1 + '&ward2=' + ward2;
			var URL = 'TradeLicenseRegister.html?GetTradeLicense';
			var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
			window.open(returnData, '_blank');
			
		}
	}


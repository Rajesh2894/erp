/*$(document)
		.ready(
				function() {
					$('#PrintButn').click(function() {
						$('.error-div').hide();
						var errorList = [];
						
						var Fromdate = $("#frmdate").val();
						if (Fromdate == "") {
							errorList.push('Please Enter From Date ..!!');
						}
						if (errorList.length > 0) {
							var errorMsg = '<ul>';
								$.each(errorList,function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
									  });
								errorMsg += '</ul>';
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({scrollTop : 0}, 'slow');
							
						} 
						if (errorList.length == 0) 
						{
							//var Fromdate1 = $("#frmdate").val();
						//	alert("Transacdate"+EmployeeListid1+EmployeeListid1)
							//var theForm = '#frmBillRegisterReport';
		                   // var requestData = __serializeForm(theForm);
							//var url ="AdvanceRegister.html?registerOfAdvanceReport";
							 requestData = {
									"Fromdate" : Fromdate,
								};
							//alert(requestData);
							 return saveOrUpdateForm(Fromdate,getLocalMessage('scheme.master.creation.success'), 'AdvanceRegister.html', 'saveform');
						//	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
							 if (ajaxResponse == 'ERROR' && ajaxResponse=="") {
								 var errorList = [];
									errorList.push(getLocalMessage("No records found for selected criteria"));
									$("#viewDishonourDetails").hide();
									if(errorList.length>0){		    	
								    	var errorMsg = '<ul>';
								    	$.each(errorList, function(index){
								    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
								    	});
								    	errorMsg +='</ul>';		    	
								    	$('#errorId').html(errorMsg);
								    	$('#errorDivId').show();
										$('html,body').animate({ scrollTop: 0 }, 'slow');	 
							 }
							 }else{
							$('#content').html(ajaxResponse);
							 }
						}
						
						});
				});*/

$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
	});
	$(".datepicker").datepicker('setDate', new Date());

});
$(function() {
	$("#frmdate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	});

function saveData(obj) {
	
	var errorList = [];
	var Fromdate = $("#frmdate").val();
	if (Fromdate == "") {
		errorList.push('Please Enter From Date ..!!');
	}

	if (errorList > 0) {

		showErr(errorList);
	} else {
		return saveOrUpdateForm(obj,
				getLocalMessage('scheme.master.creation.success'),
				'AdvanceRegister.html', 'saveform');

	}
}

function viewWorkReport(element) {
	
	var errorList = [];

	var frmDate = $("#frmdate").val();

	if (frmDate == "") {
		errorList.push(getLocalMessage("account.select.fromDate"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = '&frmDate=' + frmDate;

		var ajaxResponse = doAjaxLoading(
				'AdvanceRegister.html?getAdvanceRegisterData', requestData,
				'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

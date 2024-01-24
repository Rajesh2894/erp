$(document)
		.ready(
				function() {
							
					$('#PrintButn').click(function() {
					
						$('.error-div').hide();
						var errorList = [];

						var fromDate = $("#fromDateId").val();
						var toDate = $("#toDateId").val();
						var EmployeeListid = $("#EmployeeListid").val();
					
						if (fromDate == "") {
							
							errorList.push(getLocalMessage('acc.Enter.From.Date'));
					
						}
						if (toDate == "") {
							
					        errorList.push(getLocalMessage('acc.Enter.To.Date'));
						}

						if (EmployeeListid == "") {
							
							errorList.push(getLocalMessage('please.Select.Employee.Name'));
						
						}
						if(fromDate!=null)
							{
							errorList = validatedate(errorList,'fromDateId');
							}
						if(toDate!=null)
						{
						errorList = validatedate(errorList,'toDateId');
						}
						if(errorList.length == 0){
						errorList = dateValidation(errorList,'fromDateId','toDateId');
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
							
							var fromDate1 = $("#fromDateId").val();
							var toDate1 = $("#toDateId").val();
							var EmployeeListid1 = $("#EmployeeListid").val();
							var theForm = '#frmAccountRecieptReport';
		                    var requestData = __serializeForm(theForm);
							var url ="RecieptRegisterController.html?PrintChallanReceiptReport";
							 requestData = {
									 "fromDate" : fromDate1,
									 "toDate" : toDate1,
									"EmployeeListid" : EmployeeListid1,
								};
							var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
							 if (ajaxResponse == 'ERROR' && ajaxResponse=="") {
								 var errorList = [];
									
									errorList.push(getLocalMessage("account.norecord.criteria"));
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
					
			
				});
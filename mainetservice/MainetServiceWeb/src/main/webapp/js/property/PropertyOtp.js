/**
 * Ramu Janaki
 */

$(document)
		.ready(
				function() {
					
					
					$('#linkClick')
							.click(
									function() {

										var ajaxResponse = __doAjaxRequest(
												'propertyTaxDashBoardController.html?SelfAssesment',
												'GET', {}, false, 'html');
										$('#homeTot').html(ajaxResponse);
									});

					$('#changeclick')
							.click(
									function() {

										var ajaxResponse = __doAjaxRequest(
												'propertyTaxDashBoardController.html?ChangeAssesment',
												'GET', {}, false, 'html');
										$('#homeTot').html(ajaxResponse);
									});

					$('#Nochange')
							.click(
									function() {

										var ajaxResponse = __doAjaxRequest(
												'propertyTaxDashBoardController.html?NoChangeAssessment',
												'GET', {}, false, 'html');
										$('#homeTot').html(ajaxResponse);
									});

					$('#NocahngeSubmit')
							.click(
									function() {

										var ajaxResponse = __doAjaxRequest(
												'propertyTaxDashBoardController.html?NoChangeAssessmentSubmit',
												'GET', {}, false, 'html');
										$('#homeTot').html(ajaxResponse);
									});

					/*$('#discBtn')
							.click(
									function() {

										var checkVal = $("#iagree").is(
												':checked') // document.getElementById('iagree').checked
												

										if (checkVal) {
											var ajaxResponse = __doAjaxRequest(
													'propertyTaxDashBoardController.html?ChangeAssesmentOTP',
													'GET', {}, false, 'html');
											$('#homeTot').html(ajaxResponse);

										} else {
											
											$('.error-div').hide();
											var errorList = [];
											
											errorList.push("please select check box .")
											if (errorList.length > 0) {
												//	alert("First >> "+errorList.length);
													var errorMsg = '<ul>';
														$.each(errorList,function(index) {
																			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
															  });
														errorMsg += '</ul>';
												
														$('#errorId').html(errorMsg);
														$('#errorDivId').show();
														$('html,body').animate({scrollTop : 0}, 'slow');
													
												} 
										}

									});*/
					
					$('#NochangeForm')
					.click(
							function() {

								var checkVal = $("#NoChangeiagree").is(
										':checked') // document.getElementById('iagree').checked

								if (checkVal) {
									var ajaxResponse = __doAjaxRequest(
											'propertyTaxDashBoardController.html?NoChangeAssesmentOTP',
											'GET', {}, false, 'html');
									$('#homeTot').html(ajaxResponse);

								} else {
									alert("In order to file your Property Tax, you must read & accept the Terms & Conditions as mentioned above.")
									return false;
								}

							});
					
					$('#ProceedBtn').click(function() {
					
						$('.error-div').hide();
						var errorList = [];

						var mobile = $("#MobileNo").val();
						var otp = $("#OTP").val();

						if (mobile == "") {
							errorList.push('Please Enter Mobile Number ..!!');
					
						}

						if (otp == "") {
							errorList.push("Please Enter OTP ..!! ");
						
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
							
						} else {
							
							var dbMobile = $('#dbMobile').val();
							var genOTP = $('#genOTP').val();

						//	alert("dbMobile >> "+dbMobile + " mobile >> "+mobile);
							
							if (mobile != dbMobile) {
								errorList.push('Please Enter Proper Mobile Number');
							}
							if (otp != genOTP) {
								errorList.push('Please Enter Proper OTP');
							}
							if (errorList.length > 0) {
							//	alert("2nd >> "+errorList.length);
								var errorMsg = '<ul>';
								$.each(errorList,function(index) {
									errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';

								  });
								errorMsg += '</ul>';
								
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({scrollTop : 0}, 'slow');
								
							} else {
									var ajaxResponse = __doAjaxRequest(
										'ChangeAssessmentController.html?SubmitChangeAsses',
										'GET', {}, false, 'html');
								$('#homeTot').html(ajaxResponse);
							}
						}
						
							});
					
					
					$('#NoChangeProceedBtn').click(function() {
						
						$('.error-div').hide();
						var errorList = [];

						var mobile = $("#MobileNo").val();
						var otp = $("#OTP").val();

						if (mobile == "") {
							errorList.push('Please Enter Mobile Number ..!!');
						//	alert("mobile number empty..");
						}

						if (otp == "") {
							errorList.push("Please Enter OTP ..!! ");
						//	alert("otp empty..");
						}

						if (errorList.length > 0) {
						//	alert("First >> "+errorList.length);
							var errorMsg = '<ul>';
								$.each(errorList,function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
									  });
								errorMsg += '</ul>';
						
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({scrollTop : 0}, 'slow');
							
						} else {
							
							var dbMobile = $('#dbMobile').val();
							var genOTP = $('#genOTP').val();

						//	alert("dbMobile >> "+dbMobile + " mobile >> "+mobile);
							
							if (mobile != dbMobile) {
								errorList.push('Please Enter Proper Mobile Number');
							}
							if (otp != genOTP) {
								errorList.push('Please Enter Proper OTP');
							}
							if (errorList.length > 0) {
							//	alert("2nd >> "+errorList.length);
								var errorMsg = '<ul>';
								$.each(errorList,function(index) {
									errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';

								  });
								errorMsg += '</ul>';
								
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({scrollTop : 0}, 'slow');
								
							} else {
									var ajaxResponse = __doAjaxRequest(
										'propertyTaxDashBoardController.html?NoSubmitChangeAsses',
										'GET', {}, false, 'html');
								$('#homeTot').html(ajaxResponse);
							}
						}
						
						
							});

				});


function SearchButton() {
	$('.error-div').hide();

	var errorList = [];
	
	if ($("#EnterPropertyNo").val() == "" && $("#OLDPID").val() == "") {
		errorList.push('Please Enter Property Number or Old PID');
		

	} else {
		
		var requestData = $('#changeInAssessMentId').serialize();
		var ajaxResponse = __doAjaxRequest(
				'propertyTaxDashBoardController.html?propertySearch', 'POST',
				requestData, false, 'json');
		var resMobileOTP = ajaxResponse.toString();
	
		if(resMobileOTP != "~"){
		var mobOTP = resMobileOTP.replace(/"/g, "").split("~");
		
		if(mobOTP != null && mobOTP != "") {
		//	alert("mobOTP2::" + mobOTP[1])
			$('#dbMobile').val(mobOTP[0]);
			$('#genOTP').val(mobOTP[1]);
			$("#MobileNo").prop("disabled", false);
			$("#OTP").prop("disabled", false);
			$("#MobileNo").val(mobOTP[0]);
			$("#MobileNo").prop("readonly", true);
			$("#MobileOtp").show();
			$("#serchBtn").prop("disabled", true);
			$("#EnterPropertyNo").prop("disabled", true);
			$("#OLDPID").prop("disabled", true);
			}
		}
		else{
		var mobOTP = resMobileOTP.replace("~", "");
		errorList.push(getLocalMessage("prop.norecord.select.criteria"));	
		$("#MobileOtp").hide();
	}
	}
	
	
	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

}

function closeErrBox() {
	$('.error-div').hide();
}

function NoChangeSearchButton() {
	$('.error-div').hide();

	var errorList = [];
	if ($("#EnterPropertyNo").val() == "" && $("#OLDPID").val() == "") {
		errorList.push('Please Enter Property Number or Old PID');
		

	} else {
		var requestData = $('#NOchangeInAssessMentId').serialize();
		// alert(requestData);
		var ajaxResponse = __doAjaxRequest(
				'propertyTaxDashBoardController.html?propertySearchNoChange', 'POST',
				requestData, false, 'html');

		

		var resMobileOTP = ajaxResponse.toString();

		var mobOTP = resMobileOTP.replace(/"/g, "").split("~");
		$('#dbMobile').val(mobOTP[0]);
		$('#genOTP').val(mobOTP[1]);
		$("#MobileNo").prop("disabled", false);
		$("#OTP").prop("disabled", false);

	}
	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

}



function submitChangeAssistment(obj){
    
    var requestData = $('#formAction').serialize();
		
	var response= __doAjaxRequestValidationAccor(obj,'propertyTaxDashBoardController.html?ChangeAssesmentOTP', 'GET', requestData, false, 'html');
	if(response != false){
	       $('.content').html(response);
	       //$('#homeTot').html(ajaxResponse);
	}	
	
}





$(document)
		.ready(
				function() {

					$('#searchPropertyBasisBtn')
							.click(
									function() {
										var requestData = $(
												'#SelfAssessmentAuthorizationForm')
												.serialize();
										// alert(requestData);
										var ajaxResponse = __doAjaxRequest(
												'SelfAssessmentAuthorization.html?propertySearch',
												'GET', requestData, false,
												'html');

										// alert("ajaxResponse >>
										// "+ajaxResponse);
										if (ajaxResponse == "") {
											$('#searchData')
													.html(
															"<b> No Records Found..!! </b>");
										} else {
											$('#searchData').html(ajaxResponse);
										}

									});

					$('#viewAuthFormBtn')
							.click(
									function() {
										var requestData = $(
												'#SelfAssessmentAuthorizationForm')
												.serialize();
									//	 alert("requestData >> "+requestData);
										var ajaxResponse = __doAjaxRequest(
												'SelfAssessmentAuthorization.html?propertyView',
												'GET', requestData, false,
												'html');
										$('.contentTot').html(ajaxResponse);
										$('html,body').animate({
											scrollTop : 0
										}, 'slow');
									});

					$('#EditSelfAssForm')
							.click(
									function() {
										var requestData = $(
												'#SelfAssessmentAuthorizationFormId')
												.serialize();
								//		alert("requestData >> "+requestData);
										var ajaxResponse = __doAjaxRequest(
												'SelfAssessmentAuthorization.html?propertyEdit',
												'GET', requestData, false,
												'html');
										$('.contentTot').html(ajaxResponse);
										$('html,body').animate({
											scrollTop : 0
										}, 'slow');
									});

					$('#resetBasisBtn').click(function() {
						$('#applicationNo').val("");
						$('#propertyNo').val("");
						$('#oldPropertyNo').val("");
						$('#ownerName').val("");
						$('#searchData').html("");
					});

					$('#ProceedSelfAssForm')
							.click(
									function() {
										var requestData = $(
												'#SelfAssessmentAuthorizationForm')
												.serialize();
							//			alert("requestData >> " + requestData);
										var ajaxResponse = __doAjaxRequest(
												'SelfAssessmentAuthorization.html?propertyPreview',
												'GET', requestData, false,
												'html');
										$('.content').html(ajaxResponse);
										$('html,body').animate({
											scrollTop : 0
										}, 'slow');
									});

					$('#submitSelfAssForm')
							.click(
									function() {
										var requestData = $(
												'#selfAssessmentForm')
												.serialize();
									//	alert("requestData >> " + requestData);
										var ajaxResponse = __doAjaxRequest(
												'SelfAssessmentAuthorization.html?propertySave',
												'GET', requestData, false,
												'html');
										$('.content').html(ajaxResponse);
										$('html,body').animate({
											scrollTop : -10
										}, 'slow');
									});
					
					
					
					$('#editPreviewForm')
					.click(
							function() {
								alert("requestData >> calling..!!");
								var requestData = $(
										'#SelfAssessmentAuthorizationFormId')
										.serialize();
								alert("requestData >> "+requestData);
								var ajaxResponse = __doAjaxRequest(
										'SelfAssessmentAuthorization.html?previewEdit',
										'GET', requestData, false,
										'html');
								$('.contentTot').html(ajaxResponse);
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');
							});

					var $radios = $('input:radio[name=billPayment]');

					if ($('#assLpReceiptNo').val() != "") {
						$radios.filter('[value=na]').prop('disabled', true);
						$radios.filter('[value=manual]').prop('checked', true);
					}else{
						$radios.filter('[value=na]').prop('disabled', true);
						$radios.filter('[value=manual]').prop('disabled', true);
					}

				});

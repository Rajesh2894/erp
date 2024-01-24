$(document)
		.ready(
				function() {
					
					$('#roleSelected').change(function(){
						var msg = '';
                        if ($(this).val() == '0') {
							msg += "<h5 class='text-blue-2'>"
									+ getLocalMessage('select Role Code.')
									+ "</h5>";
						}
						
                        if (msg != '') {
							$('.msg-dialog-box').html(
									"<div class='padding-10'>" + msg + "</div>");
							$('#rolesDiv').html('');
							showModalBox('.msg-dialog-box');
						} else {
                       
							var response = __doAjaxRequest(entitleReqMap
									+ '?getDataEntitle', 'post', "groupId="
									+ $(this).val(), false, 'html');

							$('#rolesDiv').html(response);
						}
                        $('#addDataBackButtonNo').hide();
    					$('#addDataBackButton').show();
					});
					
			});
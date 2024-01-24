<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/password-validation/css/password-strength.css" rel="stylesheet" type="text/css"/>
<script src="assets/libs/password-validation/js/password-strength.js"></script>
<script src="assets/libs/password-hide-show/js/bootstrap-show-password.min.js"></script>
<style>
.table-bordered .form-group{margin:0;}
</style>
<c:set var="langId" value="${userSession.languageId}"/> 
<script>
	var modelAttributeValue = '';
	//var regiMobNoTags = [];
	$(document).ready(
			function() {
				
			/* 	var regisMobiNos= $("#regisMobiNos").val().split(',');
				$.each(regisMobiNos, function(index, value) {
					regiMobNoTags.push(value);
				});
				
				$(function() {
				$("#empmobno").autocomplete({
					highlightClass : "bold-text",
					source : regiMobNoTags
				});
			}); */
				
				$('#hiddenDeleteFlag').val('N');
				
				var end = new Date();
				$("#empDOB").datepicker({
					dateFormat: 'dd/mm/yy',
					changeMonth: true,
				    changeYear: true,
					yearRange: "-200:+200",
					maxDate : new Date(end.getFullYear()-18, 11, 31)
				});
				$('.error-div').hide();
				modelAttributeValue = '${errorvalue}';
				if (modelAttributeValue == 'N') {
					$('.error-div').hide();
				} else if (modelAttributeValue == 'Y') {
					$('.error-div').show();
				}

				$(".error-div ul").each(function() {
					var lines = $(this).html().split("<br>");
					$(this).html('<li>' + lines.join("</li><li>") + '</li>');
				});
				$('#file_list_0').hide();
				$('#uploadPreview').hide();
				$('#loginpass').hide();
				otherTask();

				if ($('#mode').val() == "N") {
					$('#editOriew').find('*').attr('disabled', 'disabled')
							.removeClass("mandClassColor");
					$('#employeeDetails input').prop('disabled', true);
					$('#employeeDetails select').prop('disabled', true);
					$('#employeeDetails textarea').prop('disabled', true);
					$('#employeeDetails checkbox').prop('disabled', true);
					$('#backBtn').prop('disabled', false);
					$("#reportingManager").prop('disabled', true);
				}
		
				if ($('#mode').val() == "Y") {
					 $("#empDOB").datepicker("destroy");
					// $('#editOriew').find('*').attr('disabled', 'disabled')
						//	.removeClass("mandClassColor");
					//$('#employeeDetails input').prop('disabled', true);
					//$('#employeeDetails select').prop('disabled', true);
					//$('#employeeDetails textarea').prop('disabled', true);
					//$('#employeeDetails checkbox').prop('disabled', true);
					//$('#backBtn').prop('disabled', false);
					//$("#reportingManager").prop('disabled', false);
					//$("#gmid").prop('disabled', false);
					
					
				}
				
				if($('#mainFormMode').val() !== 'create'){
			
					
					if ($('#uwmsOwner').is(':checked')) {
						$('#uwmsOwnerOption').show();
					} else {
						$('#regOwner').prop('checked', false);
						$('#roomOwner').prop('checked', false);
						$('#outOwner').prop('checked', false);
						$('#netOwner').prop('checked', false);
						$('#uwmsOwnerOption').hide();
					}
				
				}
			/* if ($('#logindetailupdate').is(':checked')) {
					$('#loginpass').show();
				} else

				{
					$('#confirmnewpass').val('');
					$('#newpass').val('');
					$('#loginpass').hide();
				} */

				$('#empUidNo').click(function() {
					$.mask.definitions['~'] = '[+-]';
					$('#empUidNo').mask('9999 9999 9999');

				});

			/* 	$('#logindetailupdate').click(function() {
					if ($(this).is(':checked')) {
						$('#loginpass').show();
						$('#confirmnewpass').val('');
						$('#newpass').val('');
					} else

					{
						$('#confirmnewpass').val('');
						$('#newpass').val('');
						$('#loginpass').hide();
					}
				}); */

			//	User Story #1524  changes for fetching location and designation 
				
			 	$("#deptId").change(function() {
					var language=${langId};
					var deptId = $(this).val();
				$('#locationId').html('');
				$('#designId').html('');
				$('#locationId').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown')));
				$('#designId').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown')));
					
					if (deptId > 0 && deptId!='')
					{
						var postdata = 'deptId=' + deptId;
						
						var json = __doAjaxRequest('EmployeeMaster.html?locationList','POST', postdata, false, 'json');
						
						var userJson = __doAjaxRequest('EmployeeMaster.html?designationList','POST', postdata, false, 'json');
					 
						 $.each(json, function( index, value) {
							 if(value.locId != null){
								 
								 if(language==1)
									{
									 $("#locationId")
									 .append($("<option></option>")
											 .attr("value",value.locId)
											 .text(value.locNameEng));
									}else{
										$("#locationId")
										 .append($("<option></option>")
												 .attr("value",value.locId)
												 .text(value.locNameReg));
									}
								
							 }
						 });
						
						$.each(userJson, function( index, value) {
							 if(value.dsgid != null){
								 
								 if(language==1)
									{
									 $("#designId")
									 .append($("<option></option>")
											 .attr("value",value.dsgid)
											 .text(value.dsgname));
									}else{
										$("#designId")
										 .append($("<option></option>")
												 .attr("value",value.dsgid)
												 .text(value.dsgdescription));
									}
								
							 }
						 });
						
						 $(".chosen-select-no-results").trigger("chosen:updated");
					
					}
							
					  }); 
			 	
				var passVal = $('.password-validation');
			    passVal.keyup(function(event) {
			        var password = $(this).val();
			        checkPasswordStrength(password);
			    });
		        $('#passwordCriterion').hide();
		        passVal.focus(function() {
		            $('#passwordCriterion').show('slow');
		        });
		        passVal.password();
				
			});
		
	function otherTask() {
		
		var URL = 'EmployeeMaster.html?getUploadedImage';
		var data = {};
		
		var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
		$('#uploadPreview ul').empty();
		$('#file_list_0').hide();
		
		$('#isEmpPhotoDeleted').val("N");
		if (returnData != '' && returnData != null && returnData != 'null') {
			$('#uploadPreview ul')
					.append(
							'<li id="0_file_0_t"><img src="'+returnData+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="showMsgConfirmBox();" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
			$('#uploadPreview').show();
		}
		else{
			var modeVal=$('#mode').val();
			var imagePath=$('#hiddenEmpImagePath').val();
			if(modeVal=='Y' && imagePath != "" && imagePath != null){
				$('#uploadPreview ul')
				.append(
						'<li id="0_file_0_t"><img src="'+imagePath+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="showMsgConfirmBox();" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
				$('#uploadPreview').show();
			}
		}
	}

	function doFileDeletephoto(obj) {

		var url = '';
		
		$('#isEmpPhotoDeleted').val("Y");
		
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		url = 'EmployeeMaster.html?doFileDeletion';
		$('#uploadPreview').hide();
		
		$('#hiddenDeleteFlag').val('Y');
		if (!(($.browser.msie) && ($.browser.version == 9.0))) {
			data1 = 'browserType=Other';
		} else {
			data1 = 'browserType=IE';

		}
		var t_id = '0_file_0';//$(obj).attr('id');
		var data = 'fileId=' + t_id + '&' + data1;

		var jsonResponse = __doAjaxRequest(url, 'post', data, false, 'json');
		$('#' + t_id + '_t').remove();

		if (!(($.browser.msie) && ($.browser.version == 9.0))) {

			$('.fileUploadClass').val(null);
		} else {

			var jsonMessage = jsonResponse.message;

			var msg = "";
			var firstLoop = jsonMessage.split('?');
			$
					.each(
							firstLoop,
							function(index, value) {
								if (index == 0) {
									msg = "<ul><li><b>" + value + "</b></li>";
								}

								if (index != 0) {
									var secondLoop = value.split('*');
									$
											.each(
													secondLoop,
													function(index, value) {
														if (index == 0)
															msg += "<li>"
																	+ value;
														if (index != 0)
															msg += "<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"
																	+ value
																	+ "' onclick='doFileDeletephoto()' ></li>";
													});
								}

							});

			window.location.reload(false);
		}
		$.fancybox.close();		
	}

	function closeErrBox() {
		$('.error-div').hide();
	}
	
	function showMsgConfirmBox() {		
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Yes';

		message += '<p class="text-center text-blue-2 padding-10">Are you sure want to delete?</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="doFileDeletephoto()"/>' + '</p>';
	
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function submitEmployeeMaster(e) {
		
		var errorList=[];

		
		if ($.trim($("#empmobno").val()) == ''
				|| $.trim($("#empmobno").val()) == null) {
			errorList.push(getLocalMessage("emp.error.mobileno"));
		} else {
			var phoneno = /^[1-9]{1}[0-9]{9}$/;
			var phNo = $.trim($("#empmobno").val());
			if (!phoneno.test(phNo)) {
				errorList.push(getLocalMessage("emp.error.notValid.mobile"));
			}
		}
		
		if ($("#envFlag").val() == 'Y') {
			if ($("#orgid").val() == '')
				errorList.push(getLocalMessage("emp.error.notValid.orgid"));
			if ($("#masId").val() == '' && ($("#orgid").find("option:selected").attr('code') != 'NPMA'))
				errorList.push(getLocalMessage("emp.error.notValid.deptSfac"));
			
		}
		
		if ($("#mainFormMode").val() == 'create') {
			if (($("#envFlag").val() != 'Y') && $("#deptId").val() == '') {
				errorList
						.push(getLocalMessage("emp.error.notValid.dept"));
			}
			
			if ($("#locationId").val() == '') {
				errorList.push(getLocalMessage("emp.error.notValid.loc"));
			}
			if ($("#designId").val() == '') {
				errorList.push(getLocalMessage("emp.error.notValid.desg"));
			}
		}
		
		
		if ($("#emptitle").val() == 0) {
			errorList.push(getLocalMessage("emp.error.title"));
		}

		if ($("#reportingManager").val() == $("#empId").val()) {
			errorList.push(getLocalMessage("emp.error.reportingHeadEmp"));
		}
		if ($.trim($("#empname").val()) == '' || $("#empname").val() == null) {
			errorList.push(getLocalMessage("emp.error.fname"));
		}

		var regExSpace = /\s/;
		var loginNme = $.trim($("#empname").val());
		if (regExSpace.test(loginNme)) {
			errorList.push(getLocalMessage("emp.error.notValid.fname"));
		}

		if ($.trim($("#emplname").val()) == '' || $("#emplname").val() == null) {
			errorList.push(getLocalMessage("emp.error.lname"));
		}
		if ($("#empgender").val() == 0) {
			errorList.push(getLocalMessage("emp.error.gender"));
		}
		/* if($("#empDOB").val()=='' || $("#empDOB").val()==null){
			errorList.push(getLocalMessage("emp.error.dob"));
		} */
		if ($.trim($("#empAddress").val()) == ''
				|| $("#empAddress").val() == null) {
			errorList.push(getLocalMessage("emp.error.address1"));
		}

	
		if ($.trim($("#empemail").val()) != '') {
			var emailAdd = $.trim($("#empemail").val());
			var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			if (!emailPattern.test(emailAdd)) {
				errorList.push(getLocalMessage("emp.error.notValid.email"));
			}
		}
		//Defect #114265
		/*   if($.trim($("#panNo").val())=='' || $("#panNo").val()==null){
			errorList.push(getLocalMessage("emp.error.pancardno"));
		} */

		if ($.trim($("#panNo").val()) != "") {
			var panCardNo = $.trim($("#panNo").val());
			var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
			var code = /([C,P,H,F,A,T,B,L,J,G])/;
			var code_chk = panCardNo.substring(3, 4);
			if (!panPat.test(panCardNo)) {
				errorList.push(getLocalMessage("emp.error.notValid.pancard"));
			}
		}

		/* if($.trim($("#emppincode").val())=='' || $.trim($("#emppincode").val())==null){
			errorList.push(getLocalMessage("emp.error.pincode"));
		} */if ($.trim($("#emppincode").val()) != '') {
			var pinCodePattern = /^[1-9][0-9]{5}$/;
			var pinCode = $.trim($("#emppincode").val());
			if (!pinCodePattern.test(pinCode)) {
				errorList.push(getLocalMessage("emp.error.notValid.pin"));
			}
		}

		if ($("#isDeleted").val() == 1) {
			if ($.trim($("#reason").val()) == ''
					|| $.trim($("#reason").val()) == null) {
				errorList.push(getLocalMessage("emp.error.reason"));
			}
		}

			if ($.trim($("#loginName").val()) == ''
					|| $("#loginName").val() == null) {
				errorList.push(getLocalMessage("emp.error.loginname"));
			}

			if ($("#mainFormMode").val() == 'create') {
				/* if ($("#envFlag").val() == 'Y') {
					if ($("#masId").val() == '')
						errorList
								.push(getLocalMessage("emp.error.notValid.deptSfac"));
				} */
				/* if (($("#envFlag").val() != 'Y') && $("#deptId").val() == '') {
					errorList
							.push(getLocalMessage("emp.error.notValid.dept"));
				} */
				

				if ($.trim($("#newpass").val()) == ''
						|| $.trim($("#newpass").val()) == null) {
					errorList.push(getLocalMessage("emp.error.newpassword"));
				}
				if ($.trim($("#confirmnewpass").val()) == ''
						|| $.trim($("#confirmnewpass").val()) == null) {
					errorList.push(getLocalMessage("emp.error.confirmnewpass"));
				}

			}

		

		//User Story #1524  changes for password validation

		// check password only for new user. no need to check for existion user. remove $("#logindetailupdate").is(":checked")
		if (($("#mainFormMode").val() == 'create')
				&& ($("#alreadResisteredUser").val() != 'Y')) {
			if ($.trim($("#newpass").val()) != ''
					&& $.trim($("#confirmnewpass").val()) != '') {
				var newPass = $.trim($("#newpass").val());
				var confirmPass = $.trim($("#confirmnewpass").val());
				if (newPass == confirmPass) {
					if (newPass.length > 7) {
						if (newPass.length < 16) {
							var passwordValidationRE = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
							var check = newPass.match(passwordValidationRE);
							if (check != null && check != 'null' && check != '') {
							} else {
								errorList
										.push(getLocalMessage("citizen.login.passMustContain.1st.error"));
								errorList
										.push(getLocalMessage("citizen.login.passMustContain.2nd.error"));
								errorList
										.push(getLocalMessage("citizen.login.passMustContain.3rd.error"));
								errorList
										.push(getLocalMessage("citizen.login.passMustContain.4th.error"));
							}
						} else {
							errorList
									.push(getLocalMessage("citizen.login.passMustContain.error"));
						}

					} else {
						errorList
								.push(getLocalMessage("citizen.login.passMustContain.8char.error"));
					}
				} else {
					errorList
							.push(getLocalMessage("emp.error.notEqual.password"));
				}
			}

		}

		if ($("#gmid").val() == 0) {
			errorList.push(getLocalMessage("emp.error.groupname"));
		}

		if ($("#reportingManager").val() == ''
				|| $("#reportingManager").val() == 0
				|| $("#reportingManager").val() == null) {
			errorList.push(getLocalMessage("emp.error.reportingHead"));
		}

		var empValidateUrl = "EmployeeMaster.html?validateEmp";
		var reqData = {
			"mobileNo" : $.trim($("#empmobno").val()),
			"uid" : $.trim($("#empUidNo").val()),
			"pancardNo" : $.trim($("#panNo").val()),
			"mode" : $("#mainFormMode").val(),
			"empId" : $("#empId").val(),
			"gmid" : $("#gmid").val()
		}

		var rtrnData = __doAjaxRequestForSave(empValidateUrl, 'post', reqData,
				false, '', '');
		for ( var i in rtrnData) {
			errorList.push(rtrnData[i]);
		}

		if (errorList.length > 0) {
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$(".error-div").html(errMsg);
			$(".error-div").show();
			$("html, body").animate({
				scrollTop : 0
			}, "slow");
			return false;
		}

		// login name validation for new unregistered user
		if ($("#mainFormMode").val() == 'create') {

			var validateUrl = 'EmployeeMaster.html?validateEmployee';
			var validateData = {
				"emploginname" : $("#loginName").val()
			};
			$
					.ajax({
						url : validateUrl,
						data : validateData,
						type : 'POST',
						success : function(response) {
							if (response != 0 || response != '0') {
								var errMsg = '<button type="button" class="close"><span onclick="closeErrBox()">&times;</span></button><ul>';
								errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;Login Name already exists</li></ul>';
								$(".error-div").html(errMsg);
								$(".error-div").show();
								$("html, body").animate({
									scrollTop : 0
								}, "slow");
								return false;
							} else {
								var url;

								if ($('#mode').val() == "Y") {
									url = "EmployeeMaster.html?update";
								}
								if ($('#mode').val() == "A") {
									url = "EmployeeMaster.html?create";
								}
								var requestData = __serializeForm('form');

								$
										.ajax({
											url : url,
											data : requestData,
											type : 'POST',
											beforeSend : function() {

												$("#save")
														.html(
																'<option>Please wait...</option>');
												$(".btn")
														.prop('disabled', true);
												/*     $("#reset").prop('disabled', true);
												    $("#backBtn").prop('disabled', true); */

											},
											success : function(response) {
												if (response == "success") {
													$("#save").html('save');
													$(".btn").prop('disabled',
															false);
													/*   $("#reset").prop('disabled', false);
													  $("#backBtn").prop('disabled', false); */
												}
												if ($.isPlainObject(response)) {
													showConfirmBox();
												} else {
													var divName = '.widget';
													$(divName).removeClass(
															'ajaxloader');
													$(divName).html(response);
													return false;
												}
											},
											error : function(xhr, ajaxOptions,
													thrownError) {
												var errorList = [];
												errorList
														.push(getLocalMessage("admin.login.internal.server.error"));
												showError(errorList);
											}
										});
								return false;
							}
						},
						error : function(xhr, ajaxOptions, thrownError) {
							var errorList = [];
							errorList
									.push(getLocalMessage("admin.login.internal.server.error"));
							showError(errorList);
						}
					});

		} else {
			var updateUrl = "EmployeeMaster.html?update";
			var requestData = __serializeForm('form');

			console.log(requestData);
			$
					.ajax({
						url : updateUrl,
						data : requestData,
						type : 'POST',
						success : function(response) {

							if ($.isPlainObject(response)) {
								showConfirmBox();
							} else {

								var divName = '.widget';
								$(divName).removeClass('ajaxloader');
								$(divName).html(response);
								return false;
							}
						},
						error : function(xhr, ajaxOptions, thrownError) {
							var errorList = [];
							errorList
									.push(getLocalMessage("admin.login.internal.server.error"));
							showError(errorList);
						}
					});
			return false;
		}

		return false;
	}

	function showConfirmBox() {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var msg = 'Record Saved Successfully';
		var cls = 'Proceed';
		message += '<h4 class=\"text-info padding-10 padding-bottom-0 text-center\">'
				+ msg + '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2\'    '
				+ ' onclick="proceed()"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {
		window.location.href = 'EmployeeMaster.html';
	}

	function resetEmp() {
		//$('.error-div').hide();
		//	$(':input').removeAttr('readonly disabled');
		//$("#empDOB").attr('readonly', true);
		var url = "EmployeeMaster.html?addEmployeeData";
		var requestData = {};
		$
				.ajax({
					url : url,
					data : requestData,
					type : 'POST',
					success : function(response) {

						var divName = '.content';
						$(divName).removeClass('ajaxloader');
						$(divName).html(response);

					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						alert(errorList);
						showEmpError(errorList);
					}
				});
	}

	//fetch registered employee details
	function getUserData() {
		$('#uploadPreview ul').empty();
		var empmobno = $.trim($('#empmobno').val());
		var URL = 'EmployeeMaster.html?getRegiUserData';
		var data = {
			'empMobNo' : empmobno
		}
		var response = __doAjaxRequest(URL, 'post', data, false, 'html');
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		if ($('#mainFormMode').val() === 'create') {
			$(divName).html(response);
		}
		// flag to identify user is already registered
		if ($("#alreadResisteredUser").val() == 'Y') {
			$("#empDOB").datepicker("destroy");
			$('#employeeDetails input').prop('readOnly', true);
			$('#employeeDetails textarea').prop('readOnly', true);
			$('#empmobno').prop('readOnly', false);
			$("#emptitle").prop('disabled', true);
			$("#empgender").prop('disabled', true);
			//set fake password on UI
			$("#newpass").val("XXXXXXXX").attr('readonly', true);
			$("#confirmnewpass").val("XXXXXXXX").attr('readonly', true);
		} else {
			$('#empmobno').val(empmobno)
		}
	}

	$(function() {
		$("#isDeleted option:disabled").removeAttr('disabled');

		var getReason = $('#isDeleted').val();
		if (getReason == "1") {
			$('#giveReason').removeClass('hidden');
		} else {
			$('#giveReason').addClass('hidden');
		}
		$(document).on('change', '#isDeleted', function() {
			var getStatus = $(this).val();
			if (getStatus == "1") {
				$('#giveReason').removeClass('hidden');
			} else {
				$('#giveReason').addClass('hidden');
			}

		});

	});

	$("#orgid").change(
			function() {
				$('#masId').html('');
				$('#masId').append(
						$("<option></option>").attr("value", "").text(
								getLocalMessage('selectdropdown')));
				var orgCode = $("#orgid").find("option:selected").attr('code');
				var orgid = $("#orgid").val();
				var postdata = 'orgid=' + orgid;

				if (orgCode == 'NPMA')
					$(".showMand").hide();
				else
					$(".showMand").show();		
				var json = __doAjaxRequest(
						'EmployeeMaster.html?getMasterDetail', 'POST',
						postdata, false, 'json');
				$.each(json, function(index, value) {
					if (value.id != null) {
						$("#masId").append(
								$("<option></option>").attr("value", value.id)
										.text(value.name)).trigger(
								"chosen:updated");
					}
				});
				var json = __doAjaxRequest(
						'EmployeeMaster.html?getDeptIdByOrgIdForSFAC', 'POST',
						postdata, false, 'json');

				$('#deptIdSfac').val(json);
				$('#locationId').html('');
				$('#designId').html('');
				$('#locationId').append(
						$("<option></option>").attr("value", "").text(
								getLocalMessage('selectdropdown')));
				$('#designId').append(
						$("<option></option>").attr("value", "").text(
								getLocalMessage('selectdropdown')));
				var deptId = $('#deptIdSfac').val();
				var language = $
				{
					langId
				}
				;
				if (deptId > 0 && deptId != '') {
					var postdata = 'deptId=' + deptId;

					var json = __doAjaxRequest(
							'EmployeeMaster.html?locationList', 'POST',
							postdata, false, 'json');

					var userJson = __doAjaxRequest(
							'EmployeeMaster.html?designationList', 'POST',
							postdata, false, 'json');

					$.each(json, function(index, value) {
						if (value.locId != null) {

							if (language == 1) {
								$("#locationId").append(
										$("<option></option>").attr("value",
												value.locId).text(
												value.locNameEng));
							} else {
								$("#locationId").append(
										$("<option></option>").attr("value",
												value.locId).text(
												value.locNameReg));
							}

						}
					});

					$.each(userJson, function(index, value) {
						if (value.dsgid != null) {

							if (language == 1) {
								$("#designId").append(
										$("<option></option>").attr("value",
												value.dsgid)
												.text(value.dsgname));
							} else {
								$("#designId").append(
										$("<option></option>").attr("value",
												value.dsgid).text(
												value.dsgdescription));
							}

						}
					});
					$(".chosen-select-no-results").trigger("chosen:updated");
				}

			});
</script>
<div class="form-div" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="common.employee.master" text="Employee Master"/></h2>
			<apptags:helpDoc url="EmployeeMaster.html" helpDocRefURL="EmployeeMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<c:url value="${saveAction}" var="url_form_submit" />
			<!------------------------------ Form Area Starts Here	---------------------------------------->
			<form method="post" action="EmployeeMaster.html"
				name="employeeDetails" id="employeeDetails" class="form-horizontal">
				
				<div class="error-div alert alert-danger alert-dismissible"></div>
				<input type="hidden" value="${mode}" id="mainFormMode"/>
				
				
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="employee.empId" id="empId"/>
				<form:hidden path="employee.hasError" />
				<form:hidden path="employee.modeFlag" id="mode" />
				<form:hidden path="employee.isEmpPhotoDeleted" id="isEmpPhotoDeleted"/>
				<%-- <form:hidden path="employee.isDeleted" value="${employee.isDeleted}"/> --%>
				<!-- new changes for single sign in -->
				<form:hidden id="alreadResisteredUser"  path="employee.alreadResisteredUser" /> 
				<form:hidden id="regiCpdTtlId"  path="employee.regiCpdTtlId" /> 
				<form:hidden id="regiEmpGender"  path="employee.regiEmpGender" /> 
				<form:hidden id="regiEmpDOB"  path="employee.regiEmpDOB" /> 
				<input type="hidden" value="${envFlag}" id="envFlag" />
				<input type="hidden" value="${employee.dpDeptid}" id="deptIdSfac"/>
			<%--    <input type="hidden" id="regisMobiNos" value="${regisMobiNos}"> --%>
			   
			   
				<!-- User Story #1524  changes for disabled in view mode and edit mode start-->
				
				<h4><spring:message code="master.emp.mobno" text="Employee Mobile No."/></h4>
				<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message code="master.MobNo" text="Mobile No."/> </label>
					<div class="col-sm-4">
						<form:input path="employee.empmobno" placeholder="Mobile No" id="empmobno" onchange="getUserData()"
							class="form-control hasMobileNo" maxlength="10"/>
					</div>
				</div>
				<h4><spring:message code="master.emp.persional.det" text="Employee Personal Details"/></h4>
		  
		        <c:if  test="${envFlag == 'N'}">
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="contract.label.department" text="Department"/></label>
					<div class="col-sm-4">
					<c:if test="${employee.modeFlag eq 'A'}">	
					 <c:if test="${userSession.languageId eq 1}">
	              		<form:select id="deptId" path="employee.dpDeptid" cssClass="form-control chosen-select-no-results">
							<form:option value="" ><spring:message code="employee.select.department" text="Select Department"/></form:option>
							<c:forEach items="${departmentlist}" var="departMstData">						
								<form:option value="${departMstData.dpDeptid}">${departMstData.dpDeptdesc}</form:option>						
							</c:forEach> 
						</form:select>
					</c:if>
					 <c:if test="${userSession.languageId eq 2}">
					 <form:select id="deptId" path="employee.dpDeptid" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="employee.select.department" text="Select Department"/></form:option>
						<c:forEach items="${departmentlist}" var="departMstData">						
							<form:option value="${departMstData.dpDeptid}">${departMstData.dpNameMar}</form:option>						
						</c:forEach> 
					</form:select>
					 </c:if>
					 </c:if>
					 <c:if test="${employee.modeFlag eq 'N' || employee.modeFlag eq 'Y'  }">
					  <form:input type="text" path="employee.deptName" readonly="true"
							value="${employee.deptName}" class="form-control" />
							<form:hidden path="employee.dpDeptid" />
					 </c:if>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message code="common.master.location" text="Location"/></label>
					<div class="col-sm-4">
					<c:if test="${employee.modeFlag eq 'A'}">
						<form:select id="locationId" path="employee.depid" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="employee.select.location" text="Select Location"/></form:option>
					</form:select>
					</c:if>
					<c:if test="${employee.modeFlag eq 'N' || (employee.empRoleFlag ne 'Y' && not empty employee.empRoleFlag ) }">
					 <form:input type="text" path="employee.location" readonly="true"
							value="${employee.location}" class="form-control" /> 
							<form:hidden path="employee.depid" />
					 </c:if>
					 <!-- 123332 -->
					 <c:if test="${employee.empRoleFlag eq 'Y' && employee.modeFlag ne 'N'}">
					  <c:if test="${userSession.languageId eq 1}">
					  <form:select id="locationId" path="employee.depid" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="employee.select.department" text="Select Department"/></form:option>
						<c:forEach items="${locationlist}" var="locationData">						
							<form:option value="${locationData.locId}">${locationData.locNameEng}</form:option>						
						</c:forEach> 
					</form:select>
					</c:if>
					<c:if test="${userSession.languageId eq 2}">
					<form:select id="locationId" path="employee.depid" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="employee.select.department" text="Select Department"/></form:option>
						<c:forEach items="${locationlist}" var="locationData">						
							<form:option value="${locationData.locId}">${locationData.locNameReg}</form:option>						
						</c:forEach> 
					</form:select>
					</c:if>
					</c:if>
					</div>
				</div>
				</c:if>
				 <c:if  test="${envFlag == 'Y'}">
				 <div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="emp.organisation.type" text="Organisation Type"/></label>
					<div class="col-sm-4">
					<c:if test="${employee.modeFlag eq 'A'}">
					 <c:if test="${userSession.languageId eq 1}">
					 <form:select id="orgid" path="employee.orgid" cssClass="form-control chosen-select-no-results">
							<form:option value="" ><spring:message code="master.selectDropDwn" text="Select"/></form:option>
							<c:forEach items="${orgList}" var="org">						
								<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgname}</form:option>						
							</c:forEach> 
						</form:select>
					</c:if>
					 <c:if test="${userSession.languageId eq 2}">
					 <form:select id="orgid" path="employee.orgid" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="master.selectDropDwn" text="Select"/></form:option>
						<c:forEach items="${orgList}" var="org">						
							<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgnameMar}</form:option>						
						</c:forEach> 
					</form:select>
					 </c:if>
					 </c:if>
					 <c:if test="${employee.modeFlag eq 'N' || employee.modeFlag eq 'Y' }">
					 <c:if test="${userSession.languageId eq 1}">
					 <form:select id="orgid" path="employee.orgid" cssClass="form-control chosen-select-no-results" disabled="true">
							<form:option value="" ><spring:message code="master.selectDropDwn" text="Select"/></form:option>
							<c:forEach items="${orgList}" var="org">						
								<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgname}</form:option>						
							</c:forEach> 
						</form:select>
					</c:if>
					 <c:if test="${userSession.languageId eq 2}">
					 <form:select id="orgid" path="employee.orgid" cssClass="form-control chosen-select-no-results" disabled="true">
						<form:option value="" ><spring:message code="master.selectDropDwn" text="Select"/></form:option>
						<c:forEach items="${orgList}" var="org">						
							<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgnameMar}</form:option>						
						</c:forEach> 
					</form:select>
					 </c:if>
					 </c:if>
					 
					</div>
					<label class="col-sm-2 control-label "><spring:message code="emp.organisation.Name" text="Organisation Name"/><span class="showMand"><i
											class="text-red-1">*</i></span></label>
					<div class="col-sm-4">
						<c:if test="${employee.modeFlag eq 'A'}">	
						<form:select id="masId" path="employee.masId"
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="comparentMas.select"
											text="Select" />
									</form:option>
						</form:select>
				
					 </c:if>
					  <c:if test="${employee.modeFlag eq 'N' || employee.modeFlag eq 'Y'}">
					  <form:input type="text" path="employee.mastName" readonly="true"
							value="${employee.mastName}" class="form-control" />
							<form:hidden path="employee.masId" />
					 </c:if>
					</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="common.master.location" text="Location" /></label>
						<div class="col-sm-4">
							<c:if test="${employee.modeFlag eq 'A'}">
								<form:select id="locationId" path="employee.depid"
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="employee.select.location"
											text="Select Location" />
									</form:option>
								</form:select>
							</c:if>
							<c:if
								test="${employee.modeFlag eq 'N' || employee.modeFlag eq 'Y'}">
								<form:input type="text" path="employee.location" readonly="true"
									value="${employee.location}" class="form-control" />
								<form:hidden path="employee.depid" />
							</c:if>
							
					<%-- 		<c:if test="${employee.empRoleFlag eq 'Y' && employee.modeFlag eq 'Y'}">
								<c:if test="${userSession.languageId eq 1}">
									<form:select id="locationId" path="employee.depid"
										cssClass="form-control chosen-select-no-results">
										<form:option value="">
											<spring:message code="employee.select.department"
												text="Select Department" />
										</form:option>
										<c:forEach items="${locationlist}" var="locationData">
											<form:option value="${locationData.locId}">${locationData.locNameEng}</form:option>
										</c:forEach>
									</form:select>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:select id="locationId" path="employee.depid"
										cssClass="form-control chosen-select-no-results">
										<form:option value="">
											<spring:message code="employee.select.department"
												text="Select Department" />
										</form:option>
										<c:forEach items="${locationlist}" var="locationData">
											<form:option value="${locationData.locId}">${locationData.locNameReg}</form:option>
										</c:forEach>
									</form:select>
								</c:if>
							</c:if> --%>
						</div>
					</div>

				</c:if>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="contract.label.designation" text="Designation"/></label>
					<div class="col-sm-4">
					<c:if test="${employee.modeFlag eq 'A'}">
						<form:select id="designId" path="employee.dsgid" cssClass="form-control chosen-select-no-results">
								<form:option value=""><spring:message code="employee.select.designation" text="Select Designation"/></form:option>
								<c:forEach items="${designlist}" var="desig">
									<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
								</c:forEach>
						</form:select>
					</c:if>
					<c:if test="${employee.modeFlag eq 'N' || (employee.empRoleFlag ne 'Y' && not empty employee.empRoleFlag) }">
						<form:input type="text" path="employee.designName" readonly="true"
							value="${employee.designName}" class="form-control" />
							<form:hidden path="employee.dsgid" />
					</c:if>
	                <!-- 123332 -->
					<c:if test="${employee.empRoleFlag eq 'Y' && employee.modeFlag ne 'N'}">
					<c:if test="${userSession.languageId eq 1}">
							<form:select id="designId" path="employee.dsgid" cssClass="form-control chosen-select-no-results">
								<form:option value=""><spring:message code="employee.select.designation" text="Select Designation"/></form:option>
								<c:forEach items="${designlist}" var="desig">
									<form:option value="${desig.dsgid}">${desig.dsgname}</form:option>
								</c:forEach>
							</form:select>
					 </c:if>	
					 <c:if test="${userSession.languageId eq 2}">				
					 	<form:select id="designId" path="employee.dsgid" cssClass="form-control chosen-select-no-results">
								<form:option value=""><spring:message code="employee.select.designation" text="Select Designation"/></form:option>
								<c:forEach items="${designlist}" var="desig">
									<form:option value="${desig.dsgid}">${desig.dsgdescription}</form:option>
								</c:forEach>
						</form:select>
					 </c:if>	
					  </c:if>				
					</div>
				<!-- User Story #1524  changes for disabled in view mode and edit mode end-->	
				</div>
				<div id="editOriew">

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="common.master.title" text="Title"/>
							</label>
						<div class="col-sm-4">
							<form:select id="emptitle" path="employee.cpdTtlId"
								cssClass="form-control" disabled="${employee.modeFlag eq 'Y'}">
								<form:option value="0"><spring:message code="common.master.select.title" text="Select title"/></form:option>
								<c:forEach items="${titleLookups}" var="title">
									<form:option value="${title.lookUpId}">${title.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
							<c:if test="${employee.modeFlag eq 'Y'}">
							<form:hidden path="employee.cpdTtlId" />
							</c:if>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message code="employee.lbl.fname" text="First Name"/></label>
						<div class="col-sm-4">
							<form:input path="employee.empname" placeholder="First Name" id="empname"
								class="form-control hasSpecialChara"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="employee.lbl.mname" text="Middle Name"/></label>
						<div class="col-sm-4">
							<form:input path="employee.empmname" placeholder="Middle Name" id="middleName"
								class="form-control hasSpecialChara"/>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message code="employee.lbl.lname" text="Last Name"/></label>
						<div class="col-sm-4">
							<form:input path="employee.emplname" placeholder="Last Name" id="emplname"
								class="form-control hasSpecialChara"/>
						</div>

					</div>
				
				

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="master.gender" text="Gender"/></label>
					<div class="col-sm-4">
						<form:select id="empgender" path="employee.empGender"
							cssClass="form-control" disabled="${employee.modeFlag eq 'Y'}">
							<form:option value="0"><spring:message code="master.selectDropDwn" text="Select"/></form:option>
							<c:forEach items="${genderLookups}" var="gender">
								<form:option value="${gender.lookUpCode}">${gender.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
						<c:if test="${employee.modeFlag eq 'Y'}">
							<form:hidden path="employee.empGender" />
							</c:if>
					</div>

					<c:if test="${employee.modeFlag ne 'N'}">
						<label class="col-sm-2 control-label "><spring:message code="master.DOB" text="Date of Birth"/></label>
						<div class="col-sm-4">
						<div class="input-group">
							<form:input path="employee.empDOB" cssClass="form-control mandColorClass" id="empDOB" readonly="true"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
						</div>
					</c:if>
					<c:if test="${employee.modeFlag eq 'N'}">
						<label class="col-sm-2 control-label"><spring:message code="master.DOB" text="Date of Birth"/></label>
						<div class="col-sm-4">
						<div class="input-group">
							<form:input path="employee.empDOB" cssClass="form-control mandColorClass" id="empDOB" readonly="true"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</c:if>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="master.address1" text="Address 1"/></label>
					<div class="col-sm-4">
						<form:textarea path="employee.empAddress" class="form-control" placeholder="Address 1" id="empAddress" readonly="${employee.modeFlag eq 'Y'}"/>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="master.address2" text="Address 2"/></label>
					<div class="col-sm-4">
						<form:textarea path="employee.empAddress1" class="form-control" placeholder="Address 2" id="empAddress1" readonly="${employee.modeFlag eq 'Y'}"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="master.phoneNo" text="Phone No."/></label>
					<div class="col-sm-4">
						<form:input path="employee.empphoneno" placeholder="Phone No"
							class="form-control hasNumber" maxlength="11" id="empphoneno" readonly="${employee.modeFlag eq 'Y'}"/>
					</div>
					<%-- <label class="col-sm-2 control-label required-control">Mobile No.</label>
					<div class="col-sm-4">
						<form:input path="employee.empmobno" placeholder="Mobile No" id="empmobno"
							class="form-control hasMobileNo" maxlength="10"/>
					</div> --%>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="master.emailId" text="Email Id"/></label>
					<div class="col-sm-4">
						<form:input path="employee.empemail" cssClass="form-control" placeholder="Email Id" id="empemail" readonly="${employee.modeFlag eq 'Y' && employee.empRoleFlag ne 'Y'}"/>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="master.panCardNo" text="Pan Card No."/></label>
					<div class="col-sm-4">
						<form:input path="employee.panNo" class="form-control hasNoSpace" placeholder="Pan Card No" id="panNo"
							maxlength="10" readonly="${employee.modeFlag eq 'Y'}" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message code="master.pincode" text="Pincode"/></label>
					<div class="col-sm-4">
						<form:input path="employee.emppincode" placeholder="Pincode" id="emppincode"
							class="form-control hasPincode" maxlength="6" readonly="${employee.modeFlag eq 'Y'}"/>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="master.UIDNo" text="UID No"/></label>
					<div class="col-sm-4">
						<form:input path="employee.empuid" id="empUidNo" placeholder="UID No"
							cssClass="form-control" maxlength="14" readonly="${employee.modeFlag eq 'Y'}" />
					</div>
                    </div>		 
				<c:if test="${isAdmin eq 'Y' && employee.modeFlag ne 'A'}">
				<div class="form-group">
				<input type="hidden" value="${isAdmin}" id="isAdmin"/>
				<label class="col-sm-2 control-label required-control"><spring:message code="master.status" text="Status"/></label>
				<div class="col-sm-4">
						<form:select path="employee.isDeleted" cssClass="form-control" id="isDeleted">
						<%-- <form:option value="" >
										<spring:message code="employee.select.status"
											text="Select Status" />
									</form:option> --%>
							<form:option value="0" selected="selected"><spring:message code="master.isdeleted.active" text="Active"/></form:option>
							<form:option value="1"><spring:message code="master.isdeleted.inactive" text="InActive"/></form:option>
						</form:select>
					</div>
						
				<div class="hidden" id="giveReason">
				<label class="col-sm-2 control-label required-control"><spring:message code="master.reason" text="Reason"/></label>
					<div class="col-sm-4">
						<form:textarea path="employee.empreason" class="form-control preventSpace" placeholder="enter reason" id="reason"/>
					</div>
				</div>
				</div>
			
				</c:if>
				
				
		</div>		
				<h4><spring:message code="master.LoginDetails" text="Login Details"/></h4>
				<c:if test="${employee.modeFlag ne 'N' && employee.modeFlag ne 'A'}">
					<%-- <div class="form-group">
						<div class="col-sm-12 checkbox">
							<label><form:checkbox path="employee.updatelogin"
									value="Y" id="logindetailupdate" /> Update Login Password?</label>
						</div>
					</div> --%>
				</c:if>

				<c:if test="${employee.modeFlag ne 'A'}">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="master.loginName" text="Login Name"/></label>
						<div class="col-sm-4">
							<form:input path="employee.emploginname" class="form-control" id="loginName"
								 />
						</div>
					</div>
				</c:if>

				<c:if test="${employee.modeFlag eq 'A'}">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="master.loginName" text="Login Name"/></label>
						<div class="col-sm-4">
							<form:input path="employee.emploginname" class="form-control" placeholder="Login Name" id="loginName"/>
						</div>
					</div>
				</c:if>
				
				<%-- <c:if test="${employee.modeFlag eq 'Y'}">
					<div class="form-group" id="loginpass">
						<label class="col-sm-2 control-label required-control">New
							Password</label>
						<div class="col-sm-4">
							<form:password path="employee.newPassword" id="newpass"
								class="form-control" maxlength="50" />
						</div>
						<label class="col-sm-2 control-label required-control">Confirm
							New Password</label>
						<div class="col-sm-4">
							<form:password path="employee.confirmNewPassword"
								id="confirmnewpass" class="form-control" maxlength="50" />
						</div>
					</div>
				</c:if> --%>
				<c:if test="${employee.modeFlag eq 'A'}">
					<div class="form-group" id="addloginpass">
						<label class="col-sm-2 control-label required-control"><spring:message code="master.newPwd" text="New Password"/></label>
						<div class="col-sm-4">
							<form:password path="employee.newPassword" id="newpass"
								class="form-control password-validation" maxlength="50" />
							<div id="password-strength-status"></div>
							<ul class="pswd_info" id="passwordCriterion">
								<li data-criterion="length" class="invalid"><spring:message code="eip.citizen.val.message.1" text="8-15 Characters"/></li>
								<li data-criterion="capital" class="invalid"><spring:message code="eip.citizen.val.message.2" text="At least one capital letter"/></li>
								<li data-criterion="small" class="invalid"><spring:message code="eip.citizen.val.message.3" text="At least one small letter"/></li>
								<li data-criterion="number" class="invalid"><spring:message code="eip.citizen.val.message.4" text="At least one number"/></li>
								<li data-criterion="special" class="invalid"><spring:message code="eip.citizen.val.message.5" text="At least one special character"/></li>
							</ul>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message code="master.cnfrmNewPwd" text="Confirm New Password"/></label>
						<div class="col-sm-4">
							<form:password path="employee.confirmNewPassword"
								id="confirmnewpass" class="form-control" maxlength="50" />
						</div>
					</div>
				</c:if>
       
				<h4><spring:message code="master.accessDetails" text="Access Details"/></h4>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="master.grpName" text="Group Name"/></label>
					<div class="col-sm-4">
						<form:select path="employee.gmid" cssClass="form-control" id="gmid">
							<form:option value="0"><spring:message code="master.selectGrpName" text="Select Group"/></form:option>
							<form:options items="${groupMap}" />
						</form:select>
					</div>
					<!-- User Story #1524  changes for add reporting head-->	
					<label class="col-sm-2 control-label"><spring:message code="employee.lbl.rephead" text="Reporting Head"/><span class="mand">*</span></label>
					<div class="col-sm-4">
						<form:select path="employee.reportingManager" cssClass="form-control chosen-select-no-results" id="reportingManager" disabled="${employee.modeFlag eq 'N'}">
								<form:option value="0"><spring:message code="selectdropdown" text="select"/></form:option>
								<c:forEach items="${command.headList}" var="objArray">
										<form:option value="${objArray[3]}" label="${objArray[0]} ${objArray[2]}  => ${objArray[4]}"></form:option>
									</c:forEach>
						</form:select>
					</div>
				</div>
				

		<form:hidden path="employee.filePath" id="hiddenEmpImagePath" value="${employee.filePath}"/>
		<form:hidden path="employee.deleteFlag" id="hiddenDeleteFlag" value="N"/>
		
				<!-- Add Mode -->
				<c:if test="${employee.modeFlag eq 'A' && employee.alreadResisteredUser ne 'Y'}">
					<h4><spring:message code="master.upldImgNDoc" text="Upload Images and Documents"/>
					<small class="text-blue-2"><spring:message code="common.fileupload.msg"/></small>
					</h4>

					<div class="form-group">
						<label class="control-label col-sm-2"><spring:message code="master.uploadEmpPhoto" text="Upload Employee Photo"/></label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="employee.empphotoPath" labelCode="" currentCount="0"
								showFileNameHTMLId="true" folderName="0"
								fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />			
						</div>
						
						<div id="uploadPreview" class="col-sm-4">
								<ul></ul>
							</div>
							
					</div>



					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr>
									<th><spring:message code="master.docName" text="Document Name" /></th>
									<th><spring:message code="master.serviceActive" text="Status" /></th>
									<th><spring:message code="master.upldDoc" text="Upload Documents"/></th>
								</tr>


								<tr id="docrowre1">
									<td><spring:message code="master.empSign" text="Employee Signature"/></td>
									<td><spring:message code="master.optional" text="Optional"/></td>
									<td><apptags:formField fieldType="7"
											fieldPath="employee.scansignature" labelCode=""
											currentCount="1" showFileNameHTMLId="true" folderName="1"
											fileSize="COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />	
									</td>
								</tr>
								<tr id="docrowre2">
									<td><spring:message code="master.empUIDCard" text="Employee UID Card"/></td>
									<td><spring:message code="master.optional" text="Optional"/></td>
									<td><apptags:formField fieldType="7"
											fieldPath="employee.empuiddocPath" labelCode=""
											currentCount="2" showFileNameHTMLId="true" folderName="2"
											fileSize="COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="ALL_UPLOAD_VALID_EXTENSION" />
									</td>
								</tr>
						</table>
					</div>
				</c:if>
				
				
				<!-- Update Mode -->
				<c:if test="${employee.modeFlag eq 'Y'}">
					<h4><spring:message code="master.upldImgNDoc" text="Upload Images and Documents"/>
					<small class="text-blue-2"><spring:message code="common.fileupload.msg"/></small>
					</h4>

					<div class="form-group">
						<label class="control-label col-sm-2"><spring:message code="master.uploadEmpPhoto" text="Upload Employee Photo"/></label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="employee.empphotoPath" labelCode="" currentCount="0"
								showFileNameHTMLId="true" folderName="0"
								fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
						</div>
						<div id="uploadPreview" class="col-sm-4">
								<ul></ul>
							</div>
							
					</div>



					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr>
									<th><spring:message code="master.docName" text="Document Name" /></th>
									<th><spring:message code="master.serviceActive" text="Status" /></th>
									<th><spring:message code="master.upldDoc" text="Upload Documents"/></th>
								</tr>


								<tr id="docrowre1">
									<td><spring:message code="master.empSign" text="Employee Signature"/></td>
									<td><spring:message code="master.optional" text="Optional"/></td>
									<td>
									
									<div class="form-group">
									<div class="col-sm-4">
									<apptags:formField fieldType="7"
											fieldPath="employee.scansignature" labelCode=""
											currentCount="1" showFileNameHTMLId="true" folderName="1"
											fileSize="COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
									</div>
											
									<div class="col-sm-8">
											
										<c:if test="${employee.scansignature ne '' && employee.scansignature ne null }">
										<apptags:filedownload filename="${employee.empSignDocName}"
											filePath="${employee.empSignDocPath}"
											actionUrl="EmployeeMaster.html?Download"></apptags:filedownload>
										
										</c:if>
									</div>
									</div>
									
									</td>
								</tr>
								<tr id="docrowre2">
									<td><spring:message code="master.empUIDCard" text="Employee UID Card"/></td>
									<td><spring:message code="master.optional" text="Optional"/></td>
									<td>
									<div class="form-group">
									<div class="col-sm-4">
									<apptags:formField fieldType="7"
											fieldPath="employee.empuiddocPath" labelCode=""
											currentCount="2" showFileNameHTMLId="true" folderName="2"
											fileSize="COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="ALL_UPLOAD_VALID_EXTENSION" />
									</div>
									<div class="col-sm-8">
									<c:if test="${employee.empuiddocPath ne '' && employee.empuiddocPath ne null }">
										<div>
										<apptags:filedownload filename="${employee.empUidDocName}"
											filePath="${employee.empuiddocPath}"
											actionUrl="EmployeeMaster.html?Download"></apptags:filedownload>
										</div>
									</c:if>
									</div>
									</div>
									</td>
								</tr>
						</table>
					</div>
				</c:if>
				
				
				
				<!-- View Mode -->
				<c:if test="${employee.modeFlag eq 'N' || employee.alreadResisteredUser eq 'Y'}">
				
					<h4>
						<spring:message code="master.vwImgNDoc" text="View Images and Documents" />
					</h4>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="master.upldEmpPhoto" text="Uploaded Employee Photo" />
							</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when
									test="${employee.filePath ne '' && employee.filePath ne null }">
									<img src="${employee.filePath}" width="100" height="100">
								</c:when>
								<c:otherwise>
									<b><spring:message code="master.photoNtUploded" text="Photo not Uploaded" /></b>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
							<th><spring:message code="master.docName" text="Document Name" /></th>
							<th><spring:message code="master.serviceActive" text="Status" /></th>
							<th><spring:message code="master.vwDoc" text="View Documents" /></th>
							</tr>
							<tr id="docrowre1">
								<td><spring:message code="master.empSign" text="Employee Signature"/></td>
								<td><spring:message code="master.optional" text="Optional"/></td>
								<td align="center"><c:choose>
										<c:when
											test="${employee.signPath ne '' && employee.signPath ne null }">
											<img src="${employee.signPath}" width="100" height="100">
										</c:when>
										<c:otherwise>
											<b><spring:message code="master.scanSignNtUploded" text="Scan Signature not Uploaded" /></b>
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr id="docrowre2">
								<td><spring:message code="master.empUIDCard" text="Employee UID Card"/></td>
								
								<td><spring:message code="master.optional" text="Optional"/></td>
								<td align="center"><c:choose>
								
										<c:when
											test="${employee.empuiddocPath ne '' && employee.empuiddocPath ne null }">
											<div>
											
												<img src="${employee.uiddocPath}" width="100" height="100">
											
											</div>
										</c:when>
										<c:otherwise>
											<b><spring:message code="master.UIDCardNtUploded" text="UID Card not Uploaded" /></b>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</table>
					</div>
				</c:if>
				<div class="text-center margin-top-10">
					<c:if test="${employee.modeFlag ne 'N'}">
						<input type="button" value="<spring:message code="master.save" text="Save"/>" class="btn btn-success btn-submit" id="save"
							onclick="return submitEmployeeMaster(this)">
							<c:if test="${employee.modeFlag eq 'A'}">
						<input type="button" id="reset" class="btn btn-warning" value="<spring:message code="reset.msg" text="Reset"/>" onclick="resetEmp()"/></c:if>
					</c:if>
					<input type="button" value="<spring:message code="back.msg" text="Back"/>" class="btn btn-danger"
						onclick="window.location.href='EmployeeMaster.html'" id="backBtn">
				</div>
			</form>
		</div>
	</div>
</div>



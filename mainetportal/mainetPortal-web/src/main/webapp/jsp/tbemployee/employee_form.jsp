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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="js/mainet/file-upload.js"></script>
<style>
.table-bordered .form-group{margin:0;}
</style>
<script>
	var modelAttributeValue = '';
	
	$(document).ready(
			function() {
				$("input[name=searchlist]").removeAttr("title");
				$("input[name=searchlist]").attr("aria-label","searchlist");
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
				
				if ($('#logindetailupdate').is(':checked')) {
					$('#loginpass').show();
				} else

				{
					$('#confirmnewpass').val('');
					$('#newpass').val('');
					$('#loginpass').hide();
				}

				jQuery(function($) {
					$.mask.definitions['~'] = '[+-]';
					$('#empUidNo').mask('9999 9999 9999');

				});

				$('#logindetailupdate').click(function() {
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
				});

				$('#uwmsOwner').click(function() {
					if ($(this).is(':checked')) {
						$('#uwmsOwnerOption').show();
					} else {
						$('#regOwner').prop('checked', false);
						$('#roomOwner').prop('checked', false);
						$('#outOwner').prop('checked', false);
						$('#netOwner').prop('checked', false);
						$('#uwmsOwnerOption').hide();
					}

				});
				
				var passVal = $('.password-validation');
				passVal.keyup(function(event) {
					var password = $(this).val();
					checkPasswordStrength(password);
				});
				$('#password-strength-status').removeAttr('class');
				$('#passwordCriterion').hide().removeClass('mandColorClass');
				passVal.focus(function() {
					$('#passwordCriterion').show(300);
				});
				passVal.focusout(function() {
					$('#passwordCriterion').hide(300);
			    });
				passVal.password();
				passVal.parent('.input-group').css({'width':'100%'});
				$('.password-validation').next('.input-group-append').find('.btn').css({'border-radius':'0','z-index':'4','position':'absolute','top':'1px','right':'1px'});
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
							'<li id="0_file_0_t"><img alt="Delete" src="'+returnData+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="showMsgConfirmBox();" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
			$('#uploadPreview').show();
		}
		else{
			var modeVal=$('#mode').val();
			var imagePath=$('#hiddenEmpImagePath').val();
			if(modeVal=='Y' && imagePath != "" && imagePath != null){
				$('#uploadPreview ul')
				.append(
						'<li id="0_file_0_t"><img alt="Delete" src="'+imagePath+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="showMsgConfirmBox();" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
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
		if($("#emptitle").val()==0){
			errorList.push(getLocalMessage("emp.error.title"));
		}
		if($.trim($("#empname").val())=='' || $("#empname").val()==null){
			errorList.push(getLocalMessage("emp.error.fname"));
		}
		
		var regExSpace = /\s/;
		var loginNme = $.trim($("#empname").val());
		if(regExSpace.test(loginNme)){
			errorList.push(getLocalMessage("emp.error.notValid.fname"));
		}
		
		if($.trim($("#emplname").val())=='' || $("#emplname").val()==null){
			errorList.push(getLocalMessage("emp.error.lname"));
		}
		if($("#empgender").val()==0){
			errorList.push(getLocalMessage("emp.error.gender"));
		}
		if($("#empDOB").val()=='' || $("#empDOB").val()==null){
			//df# 120068 dob needs to be optional 
			//errorList.push(getLocalMessage("emp.error.dob"));
		}
		if($.trim($("#empAddress").val())=='' || $("#empAddress").val()==null){
			errorList.push(getLocalMessage("emp.error.address1"));
		}
		if($("#empmobno").val()=='' || $("#empmobno").val()==null){
			errorList.push(getLocalMessage("emp.error.mobileno"));
		} else {
			var phoneno = /^[1-9]{1}[0-9]{9}$/;
			var phNo = $("#empmobno").val();
			if(!phoneno.test(phNo)) {
				errorList.push(getLocalMessage("emp.error.notValid.mobile"));
			}
		}
		if($.trim($("#empemail").val()) != '') {
			var emailAdd = $.trim($("#empemail").val());
			var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			if(!emailPattern.test(emailAdd)) {
				errorList.push(getLocalMessage("emp.error.notValid.email"));
			}
		}
		
		 if($.trim($("#panNo").val())!='' && $("#panNo").val() !=null){
			 
			
			var panCardNo = $.trim($("#panNo").val());
			var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
	        var code = /([C,P,H,F,A,T,B,L,J,G])/;
	        var code_chk = panCardNo.substring(3,4);
	        if (!panPat.test(panCardNo)) {
	        	errorList.push(getLocalMessage("emp.error.notValid.pancard"));
	        }        
		} 
		
		if($.trim($("#emppincode").val())=='' || $.trim($("#emppincode").val())==null){
			//df# 120068 pincode needs to be optional 
			//errorList.push(getLocalMessage("emp.error.pincode"));
		} else {
			var pinCodePattern=/^[1-9][0-9]{5}$/;
			var pinCode = $.trim($("#emppincode").val());
			if (!pinCodePattern.test(pinCode)) {
	        	errorList.push(getLocalMessage("emp.error.notValid.pin"));
	        }
		}
		
		if($.trim($("#loginName").val())=='' || $("#loginName").val()==null){
			errorList.push(getLocalMessage("emp.error.loginname"));
		}
		
		if($("#mainFormMode").val() == 'create' || $("#logindetailupdate").is(':checked') == true) {
			if($("#newpass").val()=='' || $("#newpass").val()==null) {
				errorList.push(getLocalMessage("emp.error.newpassword"));
			} 
			if($("#confirmnewpass").val()=='' || $("#confirmnewpass").val()==null){
				errorList.push(getLocalMessage("emp.error.confirmnewpass"));
			}else  {

				//CHECK FOR STRONG PASSWORD
				if(getLocalMessage("eip.password.validation.enable")=='Y'){

					var newPass = $.trim($("#newpass").val());
					var confirmPass = $.trim($("#confirmnewpass").val());
					if(newPass != confirmPass) {
						errorList.push(getLocalMessage("emp.error.notEqual.password"));
					} else {
					if ($.trim(newPass) == $.trim(confirmPass)) {

						if (newPass.length > 7) {

							if (newPass.length < 16) {

								var passwordValidationRE = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
								var check = newPass.match(passwordValidationRE);

								if (check != null && check != 'null' && check != '') {

									// errorList.push("Entered password proper.");

								} else {
									errorList.push(getLocalMessage("admin.login.passMustContain.1st.error"));
									errorList.push(getLocalMessage("admin.login.passMustContain.2nd.error"));
									errorList.push(getLocalMessage("admin.login.passMustContain.3rd.error"));
									errorList.push(getLocalMessage("admin.login.passMustContain.4th.error"));
								}

							} else {
								errorList.push(getLocalMessage("admin.login.passMustContain.error"));
							}

						} else {
							errorList.push(getLocalMessage("admin.login.passMustContain.8char.error"));
						}

					} else {
						errorList.push(getLocalMessage("admin.login.bothMustSame.error"));
					}
				}
			} else {
					var newPass = $.trim($("#newpass").val());
					var confirmPass = $.trim($("#confirmnewpass").val());
					if(newPass != confirmPass) {
						errorList.push(getLocalMessage("emp.error.notEqual.password"));
					}

				}

				}
			
		}
		
		if($("#gmid").val()==0){
			errorList.push(getLocalMessage("emp.error.groupname"));
		}

		if($.trim($("#empemail").val()) != '') {
			var empValidateUrl1 = "EmployeeMaster.html?validateEmplEmail";
			var reqData1 = {
					"empemail": $.trim($("#empemail").val()),
					"empId": $("#empId").val()
			}
			
			var rtrnData1 =__doAjaxRequest(empValidateUrl1,'post',reqData1, false,'','');
			for (var i in rtrnData1) {
				errorList.push(rtrnData1[i]);
			}
			
			if(errorList.length > 0){
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
				$.each(errorList, function(index) {
					errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
				});

				errMsg += '</ul>';
				$(".error-div").html(errMsg);
				$(".error-div").show();
				$("html, body").animate({ scrollTop: 0 }, "slow");
				return false;
			}

	 }
		var empValidateUrl = "EmployeeMaster.html?validateEmp";
		var reqData = {
				"mobileNo": $.trim($("#empmobno").val()),
				"uid": $.trim($("#empUidNo").val()),
				"pancardNo": $.trim($("#panNo").val()),
				"mode": $("#mainFormMode").val(),
				"empId": $("#empId").val()
		}
		
		var rtrnData =__doAjaxRequest(empValidateUrl,'post',reqData, false,'','');
		for (var i in rtrnData) {
			errorList.push(rtrnData[i]);
		}
		
		if(errorList.length > 0){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$(".error-div").html(errMsg);
			$(".error-div").show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
			return false;
		}
		
		if($("#mainFormMode").val() == 'create') {
			var validateUrl = 'EmployeeMaster.html?validateEmployee';
			var validateData = {"emploginname" : $("#loginName").val()};
			 var token = $("meta[name='_csrf']").attr("content");
			 var header = $("meta[name='_csrf_header']").attr("content");
			$.ajax({
				url : validateUrl,
				data : validateData,
				beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
				type : 'POST',
				success : function(response) {
					if(response != 0 || response != '0') {
						var errMsg = '<button type="button" class="close"><span onclick="closeErrBox()">&times;</span></button><ul>';
						errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;Login Name already exists</li></ul>';
						$(".error-div").html(errMsg);
						$(".error-div").show();
						$("html, body").animate({ scrollTop: 0 }, "slow");
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
						 var token = $("meta[name='_csrf']").attr("content");
						 var header = $("meta[name='_csrf_header']").attr("content");
						
						$.ajax({
								url : url,
								data : requestData,
								beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
								type : 'POST',
								success : function(response) {
									
									if(response == "success") {
										showConfirmBox();
									} else {				
										var errorList = [];
										errorList.push(getLocalMessage("admin.login.internal.server.error"));
										showError(errorList);
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
			 var token = $("meta[name='_csrf']").attr("content");
			 var header = $("meta[name='_csrf_header']").attr("content");

			$.ajax({
					url : updateUrl,
					data : requestData,
					beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
					type : 'POST',
					success : function(response) {

						if(response == "success") {
							showConfirmBox();
						} else {
							var errorList = [];
							errorList.push(getLocalMessage("admin.login.internal.server.error"));
							showError(errorList);
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
	
	function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message = '';
		var mode = $('#mode').val()
		if(mode == 'A'){
		var msg=getLocalMessage('admin.save.successmsg');
		}else{
			 msg=getLocalMessage('admin.update.successmsg');
		}
		var cls = 'Proceed';
		message	+='<h4 class=\"text-info padding-10 padding-bottom-0 text-center\">'+msg+'</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\'    '+ 
		' onclick="proceed()"/>'+	
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {
		window.location.href='EmployeeMaster.html';
	}
	
		
	function resetEmp(){
		$('.error-div').hide();
	}
	
	function countChar(val) {
		var maxlength=1000;
		var len = val.value.length;
		if (len >= maxlength) {
		  val.value = val.value.substring(0, maxlength);
		  $('.charsRemaining').next('P').text(maxlength - len);
		} else {
			$('.charsRemaining').next('P').text(maxlength - len);
			$(this).siblings(".charsRemaining").show();
		}
	}; 
	
	$(function() {
		$("#employeeDetails").validate();
	});
	$('select.form-control.mandColorClass').on('blur',function(){
	    var check = $(this).val();
	    var validMsg =$(this).attr("data-msg-required");
	    var optID=$(this).attr('id');
	    var fildID="#"+optID+"_error_msg";
	    if(check == '' || check == '0'){
	   		 $(this).parent().switchClass("has-success","has-error");
			     $(this).addClass("shake animated");
			     $(fildID).addClass('error');
			     $(fildID).css('display','block');
			     $(fildID).html(validMsg);
		}else
	    {$(this).parent().switchClass("has-error","has-success");
	    $(this).removeClass("shake animated");
	    $(fildID).css('display','none');}
	});
</script>
<div class="form-div" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="common.employee.master" text="Employee Master"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><span class="hide">Help Doc</span><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
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
				<form:hidden path="employee.dpDeptid" />
				<form:hidden path="employee.hasError" />
				<form:hidden path="employee.modeFlag" id="mode" />
				<form:hidden path="employee.isEmpPhotoDeleted" id="isEmpPhotoDeleted"/>
				<form:hidden path="employee.isdeleted" value="${employee.isdeleted}"/>
				

				<div class="form-group">
					<label for="deptName" class="col-sm-2 control-label"><spring:message code="contract.label.department" text="Department"/></label>
					<div class="col-sm-4">
						<form:input type="text" path="employee.deptName" readonly="true"
							value="${employee.deptName}" class="form-control" />
					</div>
					
					
					<label for="reportingManager" class="col-sm-2 control-label">Reporting Manager</label>
					<div class="col-sm-4">
						<form:select path="employee.reportingManager" cssClass="form-control chosen-select-no-results">
							<form:option value="0">Select</form:option>
							<c:forEach items="${employeeList}" var="employee">
								<form:option value="${employee.empId}">${employee.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div id="editOriew">

					<div class="form-group">
						<label for="emptitle" class="col-sm-2 control-label required-control"><spring:message code="common.master.title" text="Title"/>
							</label>
						<div class="col-sm-4">
							<spring:message code="employee.title.msg" text="Title must not be empty" var="TitleMsg"/> 
							<form:select id="emptitle" path="employee.title"
								cssClass="form-control mandColorClass" data-rule-required="true" data-msg-required="${TitleMsg}">
								<form:option value="0"><spring:message code="common.master.select.title" text="Select title"/></form:option>
								<c:forEach items="${titleLookups}" var="title">
									<form:option value="${title.lookUpId}">${title.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
							<label id="emptitle_error_msg" style="display:none; border:none;"></label>
						</div>
						<label for="empname" class="col-sm-2 control-label required-control"><spring:message code="employee.lbl.fname" text="First Name"/></label>
						<div class="col-sm-4">
							<form:input path="employee.empname" placeholder="First Name" id="empname"
								class="form-control hasSpecialChara"
								data-rule-required="true" data-msg-required="Enter First Name" />
						</div>
					</div>
					<div class="form-group">
						<label for="middleName" class="col-sm-2 control-label">Middle Name</label>
						<div class="col-sm-4">
							<form:input path="employee.empMName" placeholder="Middle Name" id="middleName"
								class="form-control hasSpecialChara" />
						</div>
						<label for="emplname" class="col-sm-2 control-label required-control">Last
							Name</label>
						<div class="col-sm-4">
							<form:input path="employee.empLName" placeholder="Last Name" id="emplname"
								class="form-control hasSpecialChara"
								data-rule-required="true" data-msg-required="Enter Last Name" />
						</div>

					</div>
				
				

				<div class="form-group">
					<label for="empgender" class="col-sm-2 control-label required-control">Gender</label>
					<div class="col-sm-4">
						<spring:message code="employee.gender.msg" text="Gender must not be empty" var="GenderMsg"/> 
						<form:select id="empgender" path="employee.empGender"
							cssClass="form-control mandColorClass" data-rule-required="true" data-msg-required="${GenderMsg}">
							<form:option value="0">Select</form:option>
							<c:forEach items="${genderLookups}" var="gender">
								<form:option value="${gender.lookUpCode}">${gender.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
						<label id="empgender_error_msg" style="display:none; border:none;"></label>
					</div>

					<c:if test="${employee.modeFlag ne 'N'}">
						<label for="empDOB" class="col-sm-2 control-label ">Date of Birth</label>
						<div class="col-sm-4">
						<div class="input-group">
							<form:input path="employee.empdob" cssClass="form-control mandColorClass" id="empDOB" readonly="true"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
						</div>
					</c:if>
					<c:if test="${employee.modeFlag eq 'N'}">
						<label for="empdob" class="col-sm-2 control-label">Date of Birth</label>
						<div class="col-sm-4">
						<div class="input-group">
							<form:input path="employee.empdob" cssClass="form-control mandColorClass" id="empDOB" readonly="true"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</c:if>
				</div>

				<div class="form-group">
					<label for="empAddress" class="col-sm-2 control-label required-control">Address 1</label>
					<div class="col-sm-4">
						<form:textarea path="employee.empAddress" class="form-control" placeholder="Address 1" id="empAddress"
						data-rule-required="true" data-msg-required="Enter Address1"
						onkeyup="countChar(this)" onfocus="countChar(this)"/>
								<p class="charsRemaining" id="P3">characters remaining</p>
								<p class="charsRemaining"></p>
					</div>
					<label for="empAddress1" class="col-sm-2 control-label">Address 2</label>
					<div class="col-sm-4">
						<form:textarea path="employee.empAddress1" class="form-control" placeholder="Address 2" id="empAddress1"
						onkeyup="countChar(this)" onfocus="countChar(this)"/>
								<p class="charsRemaining" id="P3">characters remaining</p>
								<p class="charsRemaining"></p>
						
					</div>
				</div>

				<div class="form-group">
					<label for="empphoneno" class="col-sm-2 control-label">Phone No.</label>
					<div class="col-sm-4">
						<form:input path="employee.empphoneno" placeholder="Phone No"
							class="form-control hasNumber" maxlength="11" id="empphoneno"/>
					</div>
					<label for="empmobno" class="col-sm-2 control-label required-control">Mobile No.</label>
					<div class="col-sm-4">
						<form:input path="employee.empmobno" placeholder="Mobile No" id="empmobno"
							class="form-control hasMobileNo" maxlength="10"
							data-rule-required="true" data-msg-required="Enter Mobile No"/>
					</div>
				</div>

				<div class="form-group">
					<label for="empemail" class="col-sm-2 control-label">Email Id</label>
					<div class="col-sm-4">
						<form:input path="employee.empemail" cssClass="form-control" placeholder="Email Id" id="empemail"/>
					</div>
					<label for="panNo" class="col-sm-2 control-label ">Pan Card No.</label>
					<div class="col-sm-4">
						<form:input path="employee.panCardNo" class="form-control" placeholder="Pan Card No" id="panNo"
							maxlength="10" />
					</div>
				</div>

				<div class="form-group">
					<label for="emppincode" class="col-sm-2 control-label ">Pincode</label>
					<div class="col-sm-4">
						<form:input path="employee.pincode" placeholder="Pincode" id="emppincode"
							class="form-control hasPincode" maxlength="6"/>
					</div>
					<label for="empUidNo" class="col-sm-2 control-label">UID No</label>
					<div class="col-sm-4">
						<form:input path="employee.empuid" id="empUidNo" placeholder="UID No"
							cssClass="form-control" maxlength="14" />
					</div>
				</div>
		</div>		
				<strong>Login Details</strong>
				<c:if test="${employee.modeFlag ne 'N' && employee.modeFlag ne 'A'}">
					<div class="form-group">
						<div class="col-sm-12 checkbox">
							<label for="logindetailupdate"><form:checkbox path="employee.updatelogin"
									value="Y" id="logindetailupdate" /> Update Login Password?</label>
						</div>
					</div>
				</c:if>

				<c:if test="${employee.modeFlag ne 'A'}">
					<div class="form-group">
						<label for="emploginname" class="col-sm-2 control-label required-control">Login Name</label>
						<div class="col-sm-4">
							<form:input path="employee.emploginname" class="form-control" id="loginName"
								readonly="true" 
								data-rule-required="true" data-msg-required="Enter Login Name"/>
						</div>
					</div>
				</c:if>

				<c:if test="${employee.modeFlag eq 'A'}">
					<div class="form-group">
						<label for="loginName" class="col-sm-2 control-label required-control">Login Name</label>
						<div class="col-sm-4">
							<form:input path="employee.emploginname" class="form-control" placeholder="Login Name" id="loginName"
							data-rule-required="true" data-msg-required="Enter Login Name"/>
						</div>
					</div>
				</c:if>
				<c:if test="${employee.modeFlag eq 'Y'}">
					<div class="form-group" id="loginpass">
						<label for="newpass" class="col-sm-2 control-label required-control">New
							Password</label>
						<div class="col-sm-4">
							<form:password path="employee.newPassword" id="newpass"
								class="form-control password-validation" maxlength="50" 
								data-rule-required="true" data-msg-required="Enter New password"/>
							<div id="password-strength-status"></div>
							<ul class="pswd_info" id="passwordCriterion">
								<li data-criterion="length" class="invalid"><spring:message code="eip.citizen.val.message.1" text="8-15 Characters"/></li>
								<li data-criterion="capital" class="invalid"><spring:message code="eip.citizen.val.message.2" text="At least one capital letter"/></li>
								<li data-criterion="small" class="invalid"><spring:message code="eip.citizen.val.message.3" text="At least one small letter"/></li>
								<li data-criterion="number" class="invalid"><spring:message code="eip.citizen.val.message.4" text="At least one number"/></li>
								<li data-criterion="special" class="invalid"><spring:message code="eip.citizen.val.message.5" text="At least one special character"/></li>
							</ul>
						</div>
						<label for="confirmnewpass" class="col-sm-2 control-label required-control">Confirm
							New Password</label>
						<div class="col-sm-4">
							<form:password path="employee.confirmNewPassword"
								id="confirmnewpass" class="form-control" maxlength="50" 
								data-rule-required="true" data-msg-required="Enter Confirm new password"/>
						</div>
					</div>
				</c:if>
				<c:if test="${employee.modeFlag eq 'A'}">
					<div class="form-group" id="addloginpass">
						<label for="newpass" class="col-sm-2 control-label required-control">New
							Password</label>
						<div class="col-sm-4">
							<form:password path="employee.newPassword" id="newpass"
								class="form-control password-validation" maxlength="50"
								data-rule-required="true" data-msg-required="Enter New password" />
							<div id="password-strength-status"></div>
							<ul class="pswd_info" id="passwordCriterion">
								<li data-criterion="length" class="invalid"><spring:message code="eip.citizen.val.message.1" text="8-15 Characters"/></li>
								<li data-criterion="capital" class="invalid"><spring:message code="eip.citizen.val.message.2" text="At least one capital letter"/></li>
								<li data-criterion="small" class="invalid"><spring:message code="eip.citizen.val.message.3" text="At least one small letter"/></li>
								<li data-criterion="number" class="invalid"><spring:message code="eip.citizen.val.message.4" text="At least one number"/></li>
								<li data-criterion="special" class="invalid"><spring:message code="eip.citizen.val.message.5" text="At least one special character"/></li>
							</ul>
						</div>
						<label for="confirmnewpass" class="col-sm-2 control-label required-control">Confirm
							New Password</label>
						<div class="col-sm-4">
							<form:password path="employee.confirmNewPassword"
								id="confirmnewpass" class="form-control" maxlength="50"
								data-rule-required="true" data-msg-required="Enter Confirm new password" />
						</div>
					</div>
				</c:if>
       
				<strong>Access Details</strong>

				<div class="form-group">
					<label for="gmid" class="col-sm-2 control-label required-control">Role Code</label>
					<div class="col-sm-4">
						<spring:message code="employee.RoleCode.msg" text="Role code must not be empty" var="roleCodeMsg"/> 
						<form:select path="employee.gmid" cssClass="form-control mandColorClass" id="gmid"  data-rule-required="true" data-msg-required="${roleCodeMsg}" >
							<form:option value="0">Select Role Code</form:option>
							<form:options items="${groupMap}" />
						</form:select>
						 <label id="gmid_error_msg" style="display:none; border:none;"></label>
					</div>
					
					
				</div>
				

		<form:hidden path="employee.filePath" id="hiddenEmpImagePath" value="${employee.filePath}"/>
		<form:hidden path="employee.isdeleted" id="hiddenDeleteFlag" value="N"/>
		
				<!-- Add Mode -->
				<c:if test="${employee.modeFlag eq 'A'}">
					<strong>Upload Images and Documents</strong>

					<div class="form-group margin-top-5">
						<label for="empphotopath" class="control-label col-sm-2">Upload Employee Photo</label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="employee.empphotopath" labelCode="" currentCount="0"
								showFileNameHTMLId="true" folderName="0"
								fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />	
								<small class="text-blue-2"><spring:message code="common.fileupload.msg"/></small>		
						</div>
						
						<div id="uploadPreview" class="col-sm-4">
								<ul></ul>
							</div>
							
					</div>



					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr>
									<th><spring:message code="" text="Document Name" /></th>
									<th><spring:message code="" text="Status" /></th>
									<th><spring:message code="" text="Upload Documents"/></th>
								</tr>


								<tr id="docrowre1">
									<td>Employee Signature</td>
									<td>Optional</td>
									<td><apptags:formField fieldType="7"
											fieldPath="employee.scansignature" labelCode=""
											currentCount="1" showFileNameHTMLId="true" folderName="1"
											fileSize="COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />	
									</td>
								</tr>
								<tr id="docrowre2">
									<td>Employee UID Card</td>
									<td>Optional</td>
									<td><apptags:formField fieldType="7"
											fieldPath="employee.empuiddocpath" labelCode=""
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
					<h4>Upload Images and Documents</h4>

					<div class="form-group margin-top-5">
						<label for="empphotopath" class="control-label col-sm-2">Upload Employee Photo</label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="employee.empphotopath" labelCode="" currentCount="0"
								showFileNameHTMLId="true" folderName="0"
								fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
								<small class="text-blue-2"><spring:message code="common.fileupload.msg"/></small>
						</div>
						<div id="uploadPreview" class="col-sm-4">
								<ul></ul>
							</div>
							
					</div>



					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr>
									<th><spring:message code="" text="Document Name" /></th>
									<th><spring:message code="" text="Status" /></th>
									<th><spring:message code="" text="Upload Documents"/></th>
								</tr>


								<tr id="docrowre1">
									<td>Employee Signature</td>
									<td>Optional</td>
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
									<td>Employee UID Card</td>
									<td>Optional</td>
									<td>
									<div class="form-group">
									<div class="col-sm-4">
									<apptags:formField fieldType="7"
											fieldPath="employee.empuiddocpath" labelCode=""
											currentCount="2" showFileNameHTMLId="true" folderName="2"
											fileSize="COMMOM_MAX_SIZE"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="ALL_UPLOAD_VALID_EXTENSION" />
									</div>
									<div class="col-sm-8">
									<c:if test="${employee.empuiddocpath ne '' && employee.empuiddocpath ne null }">
										<div>
										<apptags:filedownload filename="${employee.empuiddocname}"
											filePath="${employee.empuiddocpath}"
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
				<c:if test="${employee.modeFlag eq 'N'}">
				
					<h4>
						<spring:message code="" text="View Images and Documents" />
					</h4>

					<div class="form-group">
						<label for="" class="col-sm-2 control-label">Uploaded Employee
							Photo</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when
									test="${employee.filePath ne '' && employee.filePath ne null }">
									<img alt="Employee Photo" src="${employee.filePath}" width="100" height="100">
								</c:when>
								<c:otherwise>
									<b><spring:message code="" text="Photo not Uploaded" /></b>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
								<th><spring:message code="" text="Document Name"/></th>
								<th><spring:message code="" text="Status" /></th>
								<th><spring:message code="" text="View Documents" /></th>
							</tr>
							<tr id="docrowre1">
								<td>Employee Signature</td>
								<td>Optional</td>
								<td align="center"><c:choose>
										<c:when
											test="${employee.signPath ne '' && employee.signPath ne null }">
											<img alt="Employee Signature" src="${employee.signPath}" width="100" height="100">
										</c:when>
										<c:otherwise>
											<b><spring:message code=""
													text="Scan Signature not Uploaded" /></b>
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr id="docrowre2">
								<td>Employee UID Card</td>
								
								<td>Optional</td>
								<td align="center"><c:choose>
								
										<c:when
											test="${employee.empuiddocpath ne '' && employee.empuiddocpath ne null }">
											<div>
											
												<img alt="Employee UID Card" src="${employee.uiddocPath}" width="100" height="100">
											
											</div>
										</c:when>
										<c:otherwise>
											<b><spring:message code="" text="UID Card not Uploaded" /></b>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</table>
					</div>
				</c:if>

				<div class="text-center margin-top-10">
					<c:if test="${employee.modeFlag ne 'N'}">
						<input type="button" value="Save" class="btn btn-success"
							onclick="return submitEmployeeMaster(this)">
						<input type="reset" class="btn btn-warning" value="<spring:message code="reset.msg"/>" onclick="resetEmp()"/>
					</c:if>
					<input type="button" value="Back" class="btn btn-danger"
						onclick="window.location.href='EmployeeMaster.html'" id="backBtn">
				</div>
			</form>
		</div>
	</div>
</div>



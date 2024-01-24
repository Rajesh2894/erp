<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script  src="js/mainet/file-upload.js"></script>
<script  src="js/mainet/validation.js"></script>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<style>
span#typePrompt { display: none;font-size: 0.9em; margin-left: 5px; color: #a0a0a2; }
.lessthancurrdate:hover + span#typePrompt { display: inline; }
.lessthancurrdate:focus + span#typePrompt { display: inline; }
.lessthancurrdate{margin-bottom:0px !important;}
.margin-bottom-20{margin-bottom:20px;}
</style>
<script>

$(document).ready(function() {
			$('#resettButon').on('click',function() {
				
								$('#citizenRegistrationForm').find('input:text, input:password, select, textarea').val('');
								$('#isAcceptance').prop('checked', false);
								$('#isCorrespondence1').prop('checked', true);
								$('.error-div').hide();
								getCitizenRegistrationForm();
			})
		});
		
	$('.form-control').bind("cut copy paste",function(e) {
	    e.preventDefault();
	});
	jQuery('.hasCharacter').keyup(function() {
		this.value = this.value.replace(/[^a-z A-Z]/g, '');
	});

	function dispose() {

		$('.dialog').html('');
		$('.dialog').hide();
		disposeModalBox();
	}

	$(document).ready(function() {

		$("#isCorrespondence1").prop("checked", true);

		if ($("#isCorrespondence1").val() == "Y") {
			$("#idCAD").hide();
			$("#idCAD2").hide();
			$("#pincode").hide();
		}

		$("#title").val($("#title option[code='O']").val());
	/* 	$("#empGender").val($("#empGender option[code='O']").val()); */

		$("#resettButon").click(function() {
			$('.error-div').html("");

			$('#title').val(0);
			$("#empGender").get(0).selectedIndex = 0;
			$("input[type=text], textarea").val("");
			$(".defaultRadio").attr('checked', true);
			$("#idCAD").hide();
			$("#idCAD2").hide();

		});

		$("input[name='entity.empdob']").change(function(e) {

			this.focus();

		});

		$('.lessthancurrdate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange: "-100:+0"
		});

		jQuery('.hasPincode').keyup(function() {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '6');
		});
		jQuery('.hasMobileNo').keyup(function() {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '12');
		});

		jQuery('.hasMobileNoExt').keyup(function() {
			this.value = this.value.replace(/[^0-9 +]/g, '');
			$(this).attr('maxlength', '6');
		});
		
		jQuery('.hasNumber').keyup(function () { 
		    this.value = this.value.replace(/[^0-9]/g,'');
		  
		});
		
		$("#isCorrespondence1").click(function() {
			if ($(this).is(':checked')) {

				$("#idCAD").hide();
				$("#idCAD2").hide();
				$("#pincode").hide();
			}
		});
		$("#isCorrespondence2").click(function() {
			if ($(this).is(':checked')) {

				$("#idCAD").show();
				$("#idCAD2").show();
				$("#pincode").show();

			}
		});
		$("html, body").animate({ scrollTop: 0 }, "slow");
	});

	function privacyNotAvailabel() {
		var msg = getLocalMessage("eip.citizen.policy.not.avail");
		showErrormsgboxTitle(msg);
	}
	
	  $('.datepicker1').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		showButtonPanel: true,
		yearRange : "-450:+450",
		minDate : new Date('1600/01/01'),
		maxDate : new Date(),
		onClose: function() {
	        $(this).valid();
	    }
	});  
	
	$(function() {
		$("#citizenRegistrationForm").validate();
	});
</script>

<div class="row padding-40" id="CitizenService">
	<div class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 col-xs-12">
		<div class="login-panel cit-reg-form">
			<div class="widget margin-bottom-0">
				<div class="widget-header">
					<h2 class="login-heading">
						<strong><spring:message code="eip.citizen.reg.FormHeader" /></strong>
					</h2>
				</div>
				<div class="widget-content padding">
					<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
					<div id="basic-form">
						<form:form id="citizenRegistrationForm"
							name="citizenRegistrationForm" method="POST"
							action="CitizenRegistration.html" class="registration" autocomplete="off">
							
							<spring:message code='eip.select.gender' var="genderSel" />
							<spring:message code="eip.select.title" var="titleSel" />
							<spring:message code="eip.email.placeholder" var="emailPlaceholder" />
							<spring:message code='eip.captcha.placeholder' var="captchaP" />
							
							<spring:message code='eip.citizen.reg.firstName' var="firstName" />
							<spring:message code='eip.citizen.reg.MiddleName' var="middleName" />
							<spring:message code='eip.citizen.reg.LastName' var="lastName" />
							<spring:message code='eip.citizen.reg.mob.num.placeholder' var="citizenMobNumPlaceholder" />
							
							<div class="form-group">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 padding-0">
									<label class="form-label"><spring:message code="employee.name" text="Name" /><span class="mand">*</span></label>
									<div class="name-field">
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 padding-left-0">
											<c:set var="TitleFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.title.msg') }"></c:set>
											<form:select path="newCitizen.title" class="form-control" aria-label="title" data-rule-required="true" data-msg-required="${TitleFieldLevelMsg}">
											<c:set var="baseLookupCode" value="TTL" />
											<form:option value=""><spring:message code="eip.admin.reg.Title" text="Title" /></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
											</form:select>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 padding-0">
											<c:set var="FNameFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.FName.msg') }"></c:set>
											<form:input path="newCitizen.empname" maxlength="50"
												cssClass="form-control hasCharacter" placeholder="${firstName}" aria-label="First name" autocomplete="off" data-rule-required="true" data-msg-required="${FNameFieldLevelMsg}"/>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 padding-0">
											<form:input path="newCitizen.empMName" maxlength="50"
												cssClass="form-control hasCharacter" placeholder="${middleName}" aria-label="Middle name" autocomplete="off"/>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 padding-0">
											<c:set var="LNameFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.LName.msg') }"></c:set>
											<form:input path="newCitizen.empLName" maxlength="50"
												cssClass="form-control hasCharacter" placeholder="${lastName}" aria-label="Last name" autocomplete="off" data-rule-required="true" data-msg-required="${LNameFieldLevelMsg}"/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="form-group clear">
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 padding-left-0">
									<label for="newCitizen.empGender" class="form-label required-control">
										<spring:message code="eip.citizen.reg.Gender" text="Gender" />
									</label>
									<c:set var="genderFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.Gender.msg') }"></c:set>
									<form:select path="newCitizen.empGender" id="empGender"
										class="form-control" aria-label="Gender" data-rule-required="true" data-msg-required="${genderFieldLevelMsg}">
										<c:set var="baseLookupCode" value="GEN" />
										<form:option value=""><spring:message code="eip.select.gender" text="Gender" /></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 padding-right-0">
									<label for="newCitizen.empdob" class="form-label required-control" >
										<spring:message code="eip.citizen.reg.dateOfBirth" text="Date Of Birth" />
									</label>
									<c:set var="dobFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.dob.msg') }"></c:set>
									<form:input path="newCitizen.empdob" cssClass="form-control datepicker1" maxlength = "10" placeholder="dd/mm/yyyy" readonly="false" data-rule-required="true" data-msg-required="${dobFieldLevelMsg}" />
									<%-- <span id="typePrompt"><spring:message
									code="eip.citizen.reg.dateOfBirth" text="Date Of Birth" /></span> --%>
								</div>
							</div>
							
							<div class="form-group clear">
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 padding-left-0">
									<label for="newCitizen.empemail" class="form-label required-control">
										<spring:message code="eip.citizen.reg.emailAddress" text="Email" />
									</label>
									<c:set var="emailFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.email.msg') }"></c:set>
									<form:input path="newCitizen.empemail" maxlength="50" aria-label="Email id"
										cssClass="form-control" placeholder="${emailPlaceholder}" autocomplete="off" data-rule-required="true" data-rule-email="true" data-msg-required="${emailFieldLevelMsg}"/>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 padding-right-0">
									<label class="form-label">
										<spring:message code="phone.number" text="Phone Number" /><span class="mand">*</span>
									</label>
									<div class="input-group clear mob-num-field">
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 padding-0">
											<c:set var="MobileExtFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.mobile.ext.msg') }"></c:set>
											<form:input path="newCitizen.mobileExtension" maxlength="6" aria-label="Mobile Extension"
												cssClass="form-control hasMobileNoExt " placeholder="91" autocomplete="off" data-rule-required="true" data-msg-required="${MobileExtFieldLevelMsg}"/>
										</div>
										<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9 padding-0">
											<c:set var="MobileNoFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.mobile.no.msg') }"></c:set>
											<form:input path="newCitizen.empmobno" maxlength="12" aria-label="Mobile number"
												cssClass="form-control hasMobileNo " placeholder="${citizenMobNumPlaceholder}" autocomplete="off" data-rule-required="true" data-msg-required="${MobileNoFieldLevelMsg}"/>
										</div>
									</div>
								</div>
							</div>
						
							<%-- <div class="row ">
							
								<div class="col-sm-6 margin-top-5">
									<label for="newCitizen.empAddress"> <spring:message
											code="eip.citizen.reg.address1" text="Parmananet Address" />
										<spring:message code="eip.citizen.reg.add2" /><span class="mand">*</span></label>
							
							
							
									<form:textarea path="newCitizen.empAddress" maxlength="150"
										rows="2" cssClass="form-control" />
							
								</div>
							
								<div class="col-sm-6 margin-top-5">
									<label for="newCitizen.empAddress1"><spring:message
											code="eip.citizen.reg.add1" /></label>
									<form:textarea path="newCitizen.empAddress1" maxlength="150"
										rows="2" cssClass="form-control" />
								</div>
								</div> --%>
							<%-- <div class="row ">
							
								<div class="col-sm-6 margin-top-5">
									<label for="newCitizen.pincode"> <spring:message
											code="eip.citizen.reg.pincode" /> <span class="mand">*</span>
									</label>
							
									<form:input path="newCitizen.pincode"
										cssClass="hasPincode form-control" isDisabled="false" rows="2" />
							
								</div>
							
							
							
							
								<div class="col-sm-6 margin-top-5">
									<label><spring:message code="eip.citizen.selectAdd" /></label>
							
								<form:radiobutton path="isCorrespondence" value="Y"
									id="isCorrespondence1" />
								&nbsp;
								<spring:message code="eip.citizen.yes" />
								&nbsp; &nbsp;
								<form:radiobutton path="isCorrespondence" value="N"
									id="isCorrespondence2" />
								&nbsp;&nbsp;
								<spring:message code="eip.citizen.no" />
								</div>
							</div> --%>
							
							
							<%-- 
											<div class="row " id="idCAD">
												<div class="col-sm-6 margin-top-5">
													<label> <spring:message code="eip.citizen.reg.address2" />
														<span class="mand">*</span>:
													</label>
							
													<form:textarea path="newCitizen.empCorAddress1" maxlength="150"
														rows="2" cssClass="form-control" />
							
												</div>
												<div class="col-sm-6 margin-top-5">
													<label for="newCitizen.empAddress1"><spring:message
															code="eip.citizen.reg.add1" /></label>
													<form:textarea path="newCitizen.empCorAddress2" maxlength="150"
														rows="2" cssClass="form-control" />
												</div>
							
												
											</div> --%>
							<%-- <div class="row " id="idCAD2">
							<div class="col-sm-6 margin-top-5" id="pincode">
							
									<label><spring:message code="eip.citizen.pinCode" /><span
										class="mand">*</span></label>
							
							
									<form:input path="newCitizen.corPincode"
										cssClass="hasPincode form-control" />
							
							
								</div>
								</div> --%>
							
							<div class="form-group clear">
								<div id="captchaDiv" class="col-xs-12 col-sm-6 col-md-6 col-lg-6 padding-left-0">
									<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
									<img id="simg" alt="Captcha content" src="CitizenRegistration.html?captcha&id=${rand}" aria-label="Captcha"/>
									<a href="javascript:void(0);" onclick="doRefreshCaptcha()" aria-label="Refresh Captcha"><i
											class="fa fa-refresh fa-lg"></i></a>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 padding-right-0">
									<c:set var="CaptchaFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.captcha.msg') }"></c:set>
									<form:input path="captchaSessionValue" cssClass="form-control hasNumber" maxlength="4"
									placeholder="${captchaP}" aria-label="Enter captcha" autocomplete="off" data-rule-required="true" data-msg-required="${CaptchaFieldLevelMsg}"/>
								</div>
							</div>
								<div class="row margin-bottom-20">
								<label class="col-sm-2 control-label "> <spring:message code="profile.image" text="Profile Image"/></label>
								<apptags:formField fieldType="7" hasId="true"
							fieldPath="newCitizen.empphotopath"
							isMandatory="false" labelCode="" currentCount="0"
							showFileNameHTMLId="true" folderName="EIP_HOME"
							fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
							cssClass="form-control" callbackOtherTask="otherTaskWithImageThumbNail('CitizenRegistration.html')"/>
					
							<div id="uploadPreview" class="col-sm-4">
								<ul></ul>
							</div>
								</div>
							<div class="clear"></div>
							<div class="row margin-bottom-20">
								<div class="col-sm-12">
									<input type="checkbox" name="isAcceptance" id="isAcceptance" aria-label="Select Agree to Terms and conditions">
									<spring:message code="citizen.login.policy.approval.msg1" />
									<c:choose>
									<c:when test="${not empty command.commonHelpDoc.moduleName}">
									<a href="CitizenRegistration.html?ShowHelpDoc" id="PrivacyLink"
										class="text-blue-2" target="_blank"> <spring:message
											code="citizen.login.policy.approval.msg2" /></a>
									</c:when>
									<c:otherwise>
									<a onclick="privacyNotAvailabel();" href="javascript:void(0);"
										id="PrivacyLink" class=""> <spring:message
											code="citizen.login.policy.approval.msg2" /></a>
									</c:otherwise>
									
									</c:choose>
								</div>
							</div>
							
							<div class="text-center form-buttons clearfix">
								<input type="button" class="btn btn-success"
									value="<spring:message code="eip.commons.submitBT"/>"
									onclick="doCitizenRegistrationForm();" />
								<input type="button" class="btn btn-warning"
									value="<spring:message code="eip.agency.login.resetBT"/>"
									id="resettButon">
								<%-- <a href="CitizenHome.html" class="btn btn-danger"><spring:message code="bckBtn" text="Back"/></a> --%>
								<input type="button" class="btn btn-danger" value="<spring:message code="bckBtn" text="Back" />" onclick="getCitizenLoginForm('N')">	
							</div>
								
						</form:form>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
<hr/>
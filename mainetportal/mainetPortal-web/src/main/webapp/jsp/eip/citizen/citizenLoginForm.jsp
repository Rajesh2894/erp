<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
    request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
%>

<script>
	$('.form-control').bind("cut copy paste", function(e) {
		e.preventDefault();
	});

	function dispose() {

		$('.dialog').html('');
		$('.dialog').hide();
		disposeModalBox();
	}

	$(document).ready(function() {

		setTimeout(function() {
			/* $('#ename').focus() */
		}, 500);

		$(document).keyup(function(e) {

		});
	});

	$(document).ready(function() {

		$('.error-div').hide();
		/* 	$('#loginselectedOrg').change(function(event){
			var selectedOrg = $("#loginselectedOrg").find("option:selected").attr('value');
			
			
			
			var data="orgId="+selectedOrg;
			var ulbURL = __doAjaxRequest("CitizenLogin.html?org", 'post' ,data , false, 'json');
			
			$('.error-div').hide();
		});
		 */

	});

	$(function() {
		chosen();
	});
	
	$(function() {
		$("#citizenLoginForm").validate();
	});
	
	function resetCitizenLoginFrm(){
		$('.error-div').hide();
		$('.showpassword').prop('type','password');
		doRefreshLoginCaptcha();
	}
</script>

<script>
	(function($) {
		$(document).ready(function() {
			if (getCookie("accessibility") == 'Y') {
				$("#captchaL").hide()
			}
			var passVal = $('.password-validation');
			passVal.password();
			passVal.parent('.input-group').css({'width':'100%'});
			$('.password-validation').next('.input-group-append').find('.btn').css({'border-radius':'0','z-index':'4','position':'absolute','top':'1px','right':'1px'});
		});
	})(jQuery);
</script>
<style>
.login-panel .chosen-container {
	font-size: 1.07em;
	height: 36px;
	padding: 3px 0px !important;
	margin-bottom: 15px;
}

.login-panel .chosen-container-active .chosen-single {
	border: none;
	box-shadow: none;
}

.login-panel .chosen-container .chosen-drop {
	border-top: 1px solid #848484;
}

.login-panel .chosen-container .chosen-results {
	max-height: 150px;
}
</style>

<div class="row padding-40" id="CitizenService">
	<div
		class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
		<div class="login-panel">
			<div class="widget margin-bottom-0">
				<div class="widget-header">
					<h2>
						<spring:message code="eip.citizen.login.FormHeader" />
					</h2>
				</div>
				<div class="widget-content padding">
					<div id="basic-form">
						<div class="error-div alert alert-danger alert-dismissable"
							role="alert" style="display: none"></div>
						<form:form id="citizenLoginForm" name="citizenLoginForm"
							method="POST" action="CitizenLogin.html" autocomplete="on">
							<spring:message code='eip.captcha.placeholder' var="captchaP" />
							<spring:message code="eip.password.placeholer" var="passP" />
							<spring:message code='eip.userid.email.placeholer' text="Email or Mobile Number" var="mobileP" />
							<input type="hidden" id ="uniqueKeyId" value ="${userSession.getCurrent().getUniqueKeyId()}">
							<c:if
								test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
								<div class="form-group">
									<%-- <label for="orgId"> <i class="fa fa-university" aria-hidden="true"></i>
						<spring:message code="eip.org.select" text="Please select your Municipality"/></label> --%>
									<span> <form:select path="orgid"
											aria-label="Please select your Municipality"
											id="loginselectedOrg"
											cssClass="form-control chosen-select-no-results"
											autofocus="true">
											<form:option value="-1">
												<spring:message code="eip.org.select" />
											</form:option>
											<c:forEach items="${command.userSession.organisationsList}"
												var="orglist">
												<optgroup label="${orglist.key}">
													<c:forEach items="${orglist.value}" var="org">
														<c:if test="${userSession.languageId eq 1}">
															<option value="${org.orgid}">${org.ONlsOrgname}</option>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
														</c:if>
													</c:forEach>
												</optgroup>

											</c:forEach>
										</form:select>
									</span>
								</div>
							</c:if>


							<div class="form-group">
								<%-- <label for="ename"><i class="fa fa-user"><span class="hide">login id</span></i> <spring:message
							code="eip.citizen.login.loginName" /> </label> --%>
								<c:set var="EmailFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.login.email.msg') }"></c:set>
								<form:input path="citizenEmployee.emploginname"
									aria-label="Login id" cssClass="form-control"
									onkeypress="return tryCitizenLogin(event)" maxlength="50"
									id="ename" placeholder="${mobileP}" autocomplete="off"
									readonly="true" onfocus="this.removeAttribute('readonly');" 
									data-rule-required="true" data-msg-required="${EmailFieldLevelMsg}" />
							</div>

							<div class="form-group">
								<%-- <label for="citizenEmployee.emppassword"><i
						class="fa fa-lock"></i> <spring:message
							code="eip.citizen.login.password" /></label> --%>
								<c:set var="PassFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.login.pass.msg') }"></c:set>
								<form:password path="citizenEmployee.emppassword"
									aria-label="Password" cssClass="form-control margin-bottom-0 password-validation"
									placeholder='${passP}'
									onkeypress="return tryCitizenLogin(event)" maxlength="15"
									autocomplete="off" readonly="true"
									onfocus="this.removeAttribute('readonly');" 
									data-rule-required="true" data-msg-required="${PassFieldLevelMsg}"/>
							</div>

							<div class="form-group" id="captchaL">
								<div class=" margin-top-10" id="captchaLDiv">
									<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
									<img id="cimg"
										src="CitizenRegistration.html?captcha&id=${rand}"
										alt="captcha value <%=request.getDateHeader("captcha3")%>" />
									<a href="#" onclick="doRefreshLoginCaptcha()"
										class="margin-left-20" tabindex="-1"><i
										class="fa fa-refresh fa-lg"><span class="hide">Refresh</span></i></a>
								</div>
								<div class="">
									<!-- <label for="captchaSessionLoginValue" class="hide">captchaP</label> -->
									<c:set var="CaptchaFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.login.captcha.msg') }"></c:set>
									<form:input path="captchaSessionLoginValue"
										aria-label="Enter Captcha Value"
										cssClass="form-control margin-top-20"
										placeholder='${captchaP}'
										onkeypress="return tryCitizenLogin(event)" autocomplete="off" maxlength="4" 
										data-rule-required="true" data-msg-required="${CaptchaFieldLevelMsg}"/>
								</div>
							</div>


							<div class="clear"></div>

							<div class="row margin-top-10">
								<div
									class="col-lg-4 col-md-4 col-sm-4 col-xs-6 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">
									<input type="button" class="btn btn-success btn-block"
										onclick="doCitizenLogin(this);"
										value="<spring:message code="eip.commons.submitBT"/>" />
								</div>
								<div class="col-lg-4 col-md-4 col-sm-4  col-xs-6 ">
									<input type="reset" class="btn btn-warning btn-block"
										value="<spring:message code="eip.commons.resetBT"/>"
										onclick="resetCitizenLoginFrm()">

								</div>
							</div>

							<div class="text-center margin-top-10">
								<a href="javascript:void(0);"
									onclick="getCitizenRegistrationForm();"
									class="text-blue-4 text-large"><spring:message
										code="eip.citizen.reg" text="Citizen Register" /></a> | <a
									href="javascript:void(0);"
									onclick="getCitizenForgotPassStep1('Register');"
									class="text-success text-large"><spring:message
										code="eip.citizen.login.forgotPassword" text="Recover OTP" /></a>
								| <a href="javascript:void(0);"
									onclick="getCitizenResetPassStepI();"
									class="text-danger text-large"><spring:message
										code="eip.citizen.login.ResetPassword" text="Reset Password" /></a>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<hr />
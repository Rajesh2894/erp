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
	$('.form-control').bind("cut copy paste",function(e) {
	    e.preventDefault();
	});

	function dispose() {

		$('.dialog').html('');
		$('.dialog').hide();
		disposeModalBox();
	}

	$(document).ready(function() {

		setTimeout(function() {
			$('#ename').focus()
		}, 500);

		$(document).keyup(function(e) {

		
		});
	});
	
$(document).ready(function(){
		
		$('.error-div').hide();
		$('#loginselectedOrg').change(function(event){
		var selectedOrg = $("#loginselectedOrg").find("option:selected").attr('value');
		
		
		
		var data="orgId="+selectedOrg;
		var ulbURL = __doAjaxRequest("CitizenLogin.html?org", 'post' ,data , false, 'json');
		
		$('.error-div').hide();
	});
		
		
	});
 </script>




<div class="row padding-40">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
	<div class="widget-header">
		<h2>
			<spring:message code="eip.citizen.login.FormHeader" />
		</h2>
	</div>
	<div class="widget-content padding">
		<div id="basic-form">
			<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
			<form:form id="citizenLoginForm" name="citizenLoginForm"
				method="POST" action="CitizenLogin.html" autocomplete="on">
				<spring:message code='eip.captcha.placeholder' var="captchaP" />
				<spring:message code="eip.password.placeholer" var="passP" />
				<spring:message code='eip.email.mobile.placeholer' var="mobileP" />
				
                 <c:if test="${empty userSession.getEmployee().getEmploginname()}">
                 <script>
                 $(document).ready(function(){
                	 
                	 localStorage.setItem('selectorg','Y');
                	
             	});
              </script>
                  <div class="form-group">
                       <label for="orgId">
						<spring:message code="eip.select.ulb" text="Please select your Municipality"/></label> <span>

				      <form:select path="orgid" id="loginselectedOrg" cssClass="form-control">
						<form:option value="-1"> <spring:message code="eip.landingpage.select" /></form:option>
								<c:forEach items="${command.organisationsList}" var="org">
										<option value="${org.orgid}">
											<c:if test="${userSession.languageId eq 1}">
								               ${org.ONlsOrgname}
								       </c:if>
											<c:if test="${userSession.languageId eq 2}">
								               ${org.ONlsOrgnameMar}
								       </c:if>

										</option>
									</c:forEach>
				    </form:select>
				    </span>
                   </div>
                </c:if>


				<div class="form-group">
					<label for="ename"><i
						class="fa fa-user"><span class="hide">login id</span></i> <spring:message
							code="eip.citizen.login.loginName" /> </label>
					<form:input path="citizenEmployee.emploginname"
						cssClass="form-control" onkeypress="return tryCitizenLogin(event)"
						maxlength="50" id="ename" placeholder="${mobileP}" tabindex="1" autocomplete="off" />
				</div>

				<div class="form-group">
					<label for="citizenEmployee.emppassword"><i
						class="fa fa-lock"></i> <spring:message
							code="eip.citizen.login.password" /></label>
					<form:password path="citizenEmployee.emppassword"
						cssClass="form-control" placeholder='${passP}'
						onkeypress="return tryCitizenLogin(event)" maxlength="50"
						tabindex="2" autocomplete="off" />
				</div>

				<div class="row form-group" id="captchaL">
					<div class="col-lg-6" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="CitizenRegistration.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()"><i
							class="fa fa-refresh"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="col-lg-6">
					<label for="captchaSessionLoginValue" class="hide">captchaP</label>
						<form:input path="captchaSessionLoginValue"
							cssClass="form-control" placeholder='${captchaP}'
							onkeypress="return tryCitizenLogin(event)" autocomplete="off" />
					</div>
				</div>




				<div class="row">
					<div class="col-lg-6">
						<input type="button" class="btn btn-success btn-block" onclick="doCitizenLogin(this);"
							value="<spring:message code="eip.commons.submitBT"/>" />
					</div>
					<div class="col-lg-6">
						<input type="reset" class="btn btn-warning btn-block"
							value="<spring:message code="eip.commons.resetBT"/>"
							onclick="{$('.error-div').html('');$('.showpassword').prop('type','password') }">

					</div>
				</div>

				<div class="text-center margin-top-5">
					<a href="javascript:void(0);" onclick="getCitizenRegistrationForm();"><spring:message code="eip.citizen.reg" text="Citizen Register" /></a> |
					<a href="javascript:void(0);" onclick="getCitizenForgotPassStep1('Register');"><spring:message code="eip.citizen.login.forgotPassword" text="Recover OTP" /></a> |
					<a href="javascript:void(0);" onclick="getCitizenResetPassStepI();"><spring:message code="eip.citizen.login.ResetPassword" text="Reset Password" /></a>
				</div>
		</form:form>
		</div>
	</div>
</div>
</div>
</div>
</div>

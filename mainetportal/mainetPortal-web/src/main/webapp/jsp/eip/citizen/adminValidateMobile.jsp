<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script>

$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
$( document ).ready(function()
{
	$("#mobNo").focus();
	jQuery('.hasMobileNo').keyup(function () 
	{ 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','10');
	});
});

$(function() {
	$("#adminValidateMobileForm").validate();
});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});
function resetAdminPassFrm(){
	$('.error-div').hide();
	doRefreshLoginCaptcha();
}
</script>
<script>
(function($){
  $(document).ready(function(){		
	if(getCookie("accessibility")=='Y')
		{
		$("#captchaL").hide()
		}
  });
})(jQuery);
</script>

<div class="padding-40 form-group clearfix min-height-400" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
  <div class="widget-header">
    <h2><strong><spring:message code="eip.citizen.resetPassword.validateMobile.FormHeader" /> </strong></h2>
  </div>
  <div class="widget-content padding">
    <div id="basic-form">
    <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
     <spring:message code="feedback.Mobile" var="Mobile"/>
      <form:form id="adminValidateMobileForm" name="adminValidateMobileForm" method="POST" role="form">
        <div class="form-group">
       <%--  <label><i class="fa fa-user"></i> <spring:message code="eip.citizen.resetPassword.validateMobile.MobileNo" /></label> --%>
        <c:set var="AdminMobileFieldLevelMsg" value="${command.getAppSession().getMessage('eip.admin.mobile.msg') }"></c:set>
        <form:input id="mobNo" aria-label="Enter Mobile number" cssClass="form-control mandClassColor hasMobileNo12" path="mobileNumber" onkeypress="return admintryStepI(event)"  maxlength="12" placeholder="${Mobile}"
        data-rule-required="true" data-msg-required="${AdminMobileFieldLevelMsg}" />
        </div>
        <div class="form-group" id="captchaL">
					<div class=" margin-top-10" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="AdminResetPassword.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()" class="margin-left-20" tabindex="-1"><i
							class="fa fa-refresh fa-lg"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="">
					<label for="captchaSessionLoginValue" class="hide">captchaP</label>
						<c:set var="AdminCaptchaFieldLevelMsg" value="${command.getAppSession().getMessage('eip.admin.login.captcha.msg') }"></c:set>
						<form:input path="captchaSessionLoginValue"
							cssClass="form-control margin-top-20 hasNumber" placeholder='${captchaP}'
							onkeypress="return admintryStepI(event)" autocomplete="off" maxlength="4" 
							data-rule-required="true" data-msg-required="${AdminCaptchaFieldLevelMsg}"/>
					</div>
				</div>
				<div class="clear"></div>
					
               
        <div class="row">
        	<div class="col-lg-4 col-md-4 col-xs-4"><input type="button" class="btn btn-success btn-block" onclick="getAdminResetPassStepII();"	value="<spring:message code="eip.commons.submitBT" text="Submit"/>"/></div>
        	<div class="col-lg-4 col-md-4 col-xs-4"><input type="reset" class="btn btn-warning btn-block" value="<spring:message code="eip.commons.resetBT"/>" onclick="resetAdminPassFrm()"></div>
        	<div class="col-lg-4 col-md-4 col-xs-4">
			 	<input type="button" class="btn btn-danger btn-block" value="<spring:message code="bckBtn" text="Back" />" onclick="getAdminLoginForm()">
			</div>
        </div>
        
        
      </form:form>
    </div>
  </div>
</div>
</div>
</div>
</div>
<hr/>
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
function restOtpform(){
	openPopup(citizenResetPassword);
	/* $('#mobileNumber').val('');
	$('#captchaSessionLoginValue').val('');
	$('.error-div').hide();
	$('#validationerrordiv').hide(); */
}
$( document ).ready(function()
{
	/* $("#mobileNumber").focus(); */
	$("html, body").animate({ scrollTop: 0 }, "slow");
	jQuery('.hasMobileNo').keyup(function () 
	{ 
		this.value = this.value.replace(/[^0-9]/g,'');
		$(this).attr('maxlength','10');
	});
});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});

$(function() {
	$("#citizenValidateMobileForm").validate();
});
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
<style>p{padding:0 !important;}</style>
<div class="row padding-40" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">

  <div class="widget-header">
    <h2><strong><spring:message code="eip.citizen.resetPassword.validateMobile.FormHeader" text="Citizen Reset Password" /></strong></h2>
  </div>
   <spring:message code="feedback.Mobile" var="Mobile"/>
  <div class="widget-content padding">
    <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
   <jsp:include page="/jsp/tiles/validationerror.jsp" />
    <div id="basic-form">
       <form:form id="citizenValidateMobileForm" name="citizenValidateMobileForm" method="POST" role="form">
        <div class="form-group">
<%--         <label><i class="fa fa-user"></i> <spring:message code="eip.citizen.forgotPassword.MobileNo" text="Mobile No."/></label>
 --%>       <!--  <input type="text" class="form-control"> -->
           <p class="float" id="mobileTextForKeyPress">
           		<label for="mobileNumber" class="hide">${Mobile}</label>
           		<c:set var="MobNoFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.resetPassword.mobile.msg') }"></c:set>
                <form:input id="mobileNumber" cssClass="form-control hasMobileNo12" path="mobileNumber" onkeypress="return tryStepI(event)"  maxlength="12" Placeholder="${Mobile}" autocomplete="off" 
                data-rule-required="true" data-msg-required="${MobNoFieldLevelMsg}"/>  
				
			</p>
        </div>
        
        <div class="form-group" id="captchaL">
        			<spring:message code='eip.captcha.placeholder' var="captchaP" />
					<div class=" margin-top-10" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="CitizenResetPassword.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="javascript:void(0)" onclick="doRefreshLoginCaptcha()" class="margin-left-20" tabindex="-1" aria-label="Refresh Captcha"><i
							class="fa fa-refresh fa-lg"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="">
						<c:set var="PassFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.resetPassword.captcha.msg') }"></c:set>
						<form:input path="captchaSessionLoginValue"
							cssClass="form-control margin-top-20 hasNumber" placeholder="${captchaP}"
							onkeypress="return tryStepI(event)" autocomplete="off" aria-label="Enter captcha" maxlength="4"
							data-rule-required="true" data-msg-required="${PassFieldLevelMsg}"/>
					</div>
					
		</div>
        	
        	
		<div  id="checkOTPType" class="row">
		 <div class="col-lg-4 col-md-4 col-xs-4"> <input type="button"  class="btn btn-success btn-block" onclick="validateFormStepI(this);" value="<spring:message code="eip.commons.submitBT" text="Submit"/>" /></div>
		 <div class="col-lg-4 col-md-4 col-xs-4"> <input type="reset"  class="btn btn-warning btn-block" value="<spring:message code="eip.commons.reset" />"   onclick="restOtpform()"> </div>
		 <div class="col-lg-4 col-md-4 col-xs-4">
		 	<input type="button" class="btn btn-danger btn-block" value="<spring:message code="bckBtn" text="Back" />" onclick="getCitizenLoginForm('N')">
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
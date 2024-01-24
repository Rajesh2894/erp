<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
$(function(e){
	$("#mobileNumber").focus();
	jQuery('.hasMobileNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','10');
		});
});
(function($){
	  $(document).ready(function(){		
		if(getCookie("accessibility")=='Y')
			{
			$("#captchaL").hide()
			}
	  });
	})(jQuery);
	
	
$(function() {
	$("#citizenForgotPasswordForm").validate();
});
</script>

<div class="row padding-40" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">

  <div class="widget-header">
    <h2><strong><spring:message code="eip.citizen.forgotPassword.FormHeader" text="Citizen Forgot Password" /> </strong></h2>
  </div>
  
  <div class="widget-content padding">
  <jsp:include page="/jsp/tiles/validationerror.jsp" />
  <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
    <div id="basic-form">
         <spring:message code="feedback.Mobile" var="Mobile"/>
      <form:form id="citizenForgotPasswordForm" name="citizenForgotPasswordForm" method="POST" role="form" autocomplete="on">
        <div class="form-group">
<%--         <label><i class="fa fa-user"></i> <spring:message code="eip.citizen.forgotPassword.MobileNo" text="Mobile No."/></label>
 --%>           <p class="float" id="mobileTextForKeyPress">
 				<label for="mobileNumber" class="hide">${Mobile}</label>
                <form:input id="mobileNumber" cssClass="form-control hasMobileNo12" path="mobileNumber" onkeypress="return tryStep1(event)"  maxlength="12" autocomplete="off" Placeholder="${Mobile}"
                data-rule-required="true" data-msg-required="Mobile No. must not be Empty"/>  
				
			</p>
        </div>
        		<div class="form-group" id="captchaL">
        			<spring:message code='eip.captcha.placeholder' var="captchaP" />
					<div class=" margin-top-10" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="CitizenForgotPassword.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()" class="margin-left-20" tabindex="-1"><i
							class="fa fa-refresh fa-lg"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="">
					<!-- <label for="captchaSessionLoginValue" class="hide">captchaP</label> -->
						<form:input path="captchaSessionLoginValue" aria-label="Enter Captcha Value"
							cssClass="form-control margin-top-20" placeholder="${captchaP}" maxlength="4"
							onkeypress="return tryCitizenLogin(event)" autocomplete="off"
							data-rule-required="true" data-msg-required="Please Enter Captcha Code" />
					</div>
				</div>
        	
		<div id="checkOTPType" class="row">
		<input type="hidden" value="" id="mobType">
		<c:if test="${userSession.employee.emploginname eq 'NOUSER' }">
		  <div class="col-lg-4 col-md-4 col-xs-4 " id="Register"> <input type="button" id="nouser" value="<spring:message code="eip.submit" text="Submit"/>" class="btn btn-success btn-block" onclick="validateCitizenFormStep1(this,'Register')"></div>
		</c:if>
		<c:if test="${userSession.employee.emploginname ne 'NOUSER' and  userSession.employee.loggedIn eq 'Y' }">
		 <div class="col-lg-4 col-md-4 col-xs-4 " id="Nonregister"> <input type="button" id="user" value="<spring:message code="eip.submit" text="Submit"/>" class="btn btn-success btn-block" onclick="validateCitizenFormStep1(this,'Nonregister')"></div>
		 </c:if>
		 <div class="col-lg-4 col-md-4 col-xs-4" >  <input type="reset"  class="btn btn-warning btn-block"  value="<spring:message code="eip.commons.reset" />"   onclick="{$('.error-div').hide();}"></div> 

        <c:if test="${userSession.employee.emploginname ne 'NOUSER' }">
      		<div class="col-lg-4 col-md-4 col-xs-4 ">
         		<a href="CitizenHome.html?EditUserProfile" id="Nonregister" class="btn btn-danger btn-block"><spring:message code="portal.common.button.back" /></a>
         	</div>      	
      	</c:if>
      	 </div> 
        <c:if test="${userSession.employee.emploginname eq 'NOUSER' }">
        <script>        	
        	function tryStep1(e) {        	
		    if (e.keyCode == 13) {		    	
		    	validateCitizenFormStep1($('#nouser'),'Register');
        		return false;
    		}
			}
        </script>
        </c:if>
        <c:if test="${userSession.employee.emploginname ne 'NOUSER' and  userSession.employee.loggedIn eq 'Y' }">
         <script>
        	function tryStep1(e) {
		    if (e.keyCode == 13) {
		    	validateCitizenFormStep1($('#user'),'Nonregister');
        		return false;
    		}
			}
        </script>
        </c:if>
        </form:form>
    </div>
  </div>
</div>
</div>
</div>
</div>
<hr/>
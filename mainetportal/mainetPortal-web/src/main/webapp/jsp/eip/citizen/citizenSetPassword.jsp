<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
<style> 
 .set-pass { color: #445cae !important;font-size: 1em !important;margin: 0px !important;padding-top: 5px !important;}

 .form-elements{margin-bottom: 20px;}
</style>
<script>
$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
$(function(e)
{
	$("#enterPassword").focus();
});

$(document).ready(function() {
	$('#resettButon').on('click',function() {
						$('#citizenSetPasswordForm').find('input:text').val('');
						$('.error-div').hide();
	});
	
	var passVal = $('.password-validation');
	passVal.keyup(function(event) {
		var password = $(this).val();
		checkPasswordStrength(password);
	});
	$('#passwordCriterion').hide();
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
</script>

<div class="row padding-40">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">

  <div class="widget-header">
    <h2><strong><spring:message code="eip.citizen.set.FormHeader"/></strong></h2>
  </div>
  <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
  <div class="widget-content padding">
    <div id="basic-form">
      <form:form id="citizenSetPasswordForm" name="citizenSetPasswordForm" method="POST" action="CitizenSetPassword.html" class="form" role="form" autocomplete="off">
        <div class="form-group">
     <spring:message code="eip.citizen.set.newPassword" var="setpasswprd"/>
     <spring:message code="eip.citizen.set.reEnteredPassword" var="renterpasswprd"/>
			<div class="form-elements clear margin_top_5">
					<%-- <label for="newPassword">
						<spring:message code="eip.citizen.set.newPassword"/>
					</label> --%>
					<form:password path="newPassword" id="enterPassword" cssClass=" form-control margin-bottom-0 hasMobileNo mandClassColor password-validation" maxlength="15" placeholder="${setpasswprd}"/>
					<%-- <p class="set-pass"><spring:message code="eip.citizen.resetPassword.restrict.pass" text="Use 8 to 15 character with a mix of letters,numbers & symbols"/></p> --%>
					<div id="password-strength-status"></div>
					<ul class="pswd_info" id="passwordCriterion">
						<li data-criterion="length" class="invalid"><spring:message code="eip.citizen.val.message.1" text="8-15 Characters"/></li>
						<li data-criterion="capital" class="invalid"><spring:message code="eip.citizen.val.message.2" text="At least one capital letter"/></li>
						<li data-criterion="small" class="invalid"><spring:message code="eip.citizen.val.message.3" text="At least one small letter"/></li>
						<li data-criterion="number" class="invalid"><spring:message code="eip.citizen.val.message.4" text="At least one number"/></li>
						<li data-criterion="special" class="invalid"><spring:message code="eip.citizen.val.message.5" text="At least one special character"/></li>
					</ul>
				</div>
			
			
			<div class="form-elements clear margin_top_5">
					<%-- <label for="reEnteredPassword">
						<spring:message code="eip.citizen.set.reEnteredPassword"/>
					</label> --%>
			</div>	
			<div class="form-elements clear margin_top_5">	
					<span>
						<input type="password" class=" form-control margin-bottom-0 hasMobileNomandClassColor" name="reEnteredPassword" id="reEnteredPassword"  maxlength="15" placeholder="${renterpasswprd}" />
					</span>
				</div>
        </div>
        <div class="colum">
        	<!-- <div class="col-lg-6 col-md-6  col-xs-6"><button type="reset" class="btn btn-danger btn-block">Reset</button></div> --> 
        	
		<div  class="row padding-bottom-20" id="checkOTPType">
		 <div class="col-lg-4 col-lg-offset-2" id="checkOTPType">
		  <input type="button"  class="btn btn-success btn-block" onclick="doCitizenSetPassword();" value="<spring:message code="eip.commons.submitBT"/>" />
		  </div>
		  <div class="col-lg-4" id="checkOTPType">
		  <input type="button" id="resettButon" class="btn btn-warning btn-block" value="<spring:message code="eip.commons.reset" />"> 
		  </div>	
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
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
$(function(e) {
	/* $("#newPassword").focus(); */
	
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

$(function() {
	$("#resetPasswordForm").validate();
});
</script>

<div class="row padding-40" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">

<div class="widget margin-bottom-0">

  <div class="widget-header"><h2><spring:message code="eip.citizen.set.FormHeader"/></h2></div>
	
 <div class="widget-content padding">
 
 <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
 
					<form:form id="resetPasswordForm" name="resetPasswordForm" method="POST" action="AdminResetPassword.html" cssClass="form-horizontal" autocomplete="on">
						
						<div class="form-group">
			              <label class="col-sm-12" for="newPassword"><spring:message text="Enter New Password" code="eip.admin.set.newPassword"/></label>
			              <div class="col-sm-12">
			              	<form:password path="newPassword" onkeypress="return admintryStepIII(event)" maxlength="15" cssClass="form-control margin-bottom-0 mandClassColor password-validation" autocomplete="off" data-rule-required="true" data-msg-required="Please enter password."/>
			              	<div id="password-strength-status"></div>
							<ul class="pswd_info" id="passwordCriterion">
								<li data-criterion="length" class="invalid"><spring:message code="eip.citizen.val.message.1" text="8-15 Characters"/></li>
								<li data-criterion="capital" class="invalid"><spring:message code="eip.citizen.val.message.2" text="At least one capital letter"/></li>
								<li data-criterion="small" class="invalid"><spring:message code="eip.citizen.val.message.3" text="At least one small letter"/></li>
								<li data-criterion="number" class="invalid"><spring:message code="eip.citizen.val.message.4" text="At least one number"/></li>
								<li data-criterion="special" class="invalid"><spring:message code="eip.citizen.val.message.5" text="At least one special character"/></li>
							</ul>
			              </div>			              
			            </div>
			            
			            <div class="form-group">
			              <label class="col-sm-12" for="reEnteredPassword"><spring:message text="Re-Enter Password" code="eip.admin.set.newPassword"/></label>
			              <div class="col-sm-12">
			                <input type="password" onkeypress="return admintryStepIII(event)" name="reEnteredPassword" id="reEnteredPassword" class="form-control mandClassColor" maxlength="15" autocomplete="off" data-rule-required="true" data-msg-required="Please re-enter password."/>
							<form:hidden path="mobileNumber" />
			              </div>			              
			            </div>
            
            	
	<div class="row clear padding_top_10">
			
				<div class="col-lg-6" ><input type="button" class="btn btn-success form-control" onclick="getAdminResetPassStepIV();"	value="<spring:message text="Submit" code="eip.commons.submitBT"/>"  tabindex="3"/></div>
				
				 <div class="col-lg-6" ><input type="reset" class="btn btn-warning  form-control" onclick="{$('.error-div').hide();}" value="<spring:message text="Reset" code="eip.commons.resetBT"/>" tabindex="4"></div>
				
			</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<hr/>
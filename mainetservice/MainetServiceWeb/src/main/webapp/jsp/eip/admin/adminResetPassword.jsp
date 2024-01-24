<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 

<%
	response.setContentType("text/html; charset=utf-8");
%>
<link href="assets/libs/password-validation/css/password-strength.css" rel="stylesheet" type="text/css"/>
<script src="assets/libs/password-validation/js/password-strength.js"></script>
<script src="assets/libs/password-hide-show/js/bootstrap-show-password.min.js"></script>


<script>
$(function(e)
{
	$("#newPassword").focus();
});
 $( '.form-control' ).on( "copy cut paste drop", function() {
	     return false;
	 });
 
$(document).ready(function() {
	  $('.widget.login').css({'width':'400px'});
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

</script>



<%-- <div class="dialog-header">
	<h2><spring:message code="eip.citizen.set.FormHeader"/></h2>	
</div>
<div class="popup-form-div">
<div class="error-div"></div> --%>
<div class="widget login">
<div class="widget-header"><h2><strong><spring:message code="eip.citizen.set.FormHeader"/></strong></h2></div>
	<div class="widget-content padding">
<div class="error-div alert alert-danger"></div>
<form:form id="resetPasswordForm" name="resetPasswordForm" method="POST" action="AdminResetPassword.html" autocomplete="on">
<div class="form-group"><label for="newPassword"><spring:message text="Enter New Password" />:</label>
<form:password path="newPassword" onkeypress="return admintryStepIII(event)" maxlength="15" cssClass="form-control mandClassColor password-validation" autocomplete="off"/></div>
<div id="password-strength-status"></div>
<ul class="pswd_info" id="passwordCriterion">
	<li data-criterion="length" class="invalid"><spring:message code="eip.citizen.val.message.1" text="8-15 Characters"/></li>
	<li data-criterion="capital" class="invalid"><spring:message code="eip.citizen.val.message.2" text="At least one capital letter"/></li>
	<li data-criterion="small" class="invalid"><spring:message code="eip.citizen.val.message.3" text="At least one small letter"/></li>
	<li data-criterion="number" class="invalid"><spring:message code="eip.citizen.val.message.4" text="At least one number"/></li>
	<li data-criterion="special" class="invalid"><spring:message code="eip.citizen.val.message.5" text="At least one special character"/></li>
</ul>

<div class="form-group"><label for="reEnteredPassword"><spring:message text="Re-Enter Password"/>:</label>
<input type="password" onkeypress="return admintryStepIII(event)" name="reEnteredPassword" id="reEnteredPassword" class="form-control mandClassColor" maxlength="15" autocomplete="off"/>
</div>
<form:hidden path="mobileNumber"/>
<div class="row">		
<div class="col-sm-6">
<input type="button"  class="btn btn-success btn-block" onclick="getAdminResetPassStepIV();"	value="<spring:message text="Submit" code="eip.commons.submitBT"/>"  /></div>
<div class="col-sm-6">
<input type="reset" class="btn btn-danger btn-block" onclick="{$('.error-div').html('');}" > 	</div>
</div>
</form:form>
</div>
</div>
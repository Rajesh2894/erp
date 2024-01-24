

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#mobNo").focus();
	});
	
	 jQuery('.hasMobileNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
		$(this).attr('maxlength', '10');
	}); 
</script>
<div class="widget login">
	<div class="widget-header">
		<h2>
			<strong><spring:message
					code="eip.citizen.forgotPassword.FormHeader"
					text="Citizen Forgot Password" /></strong>
		</h2>
	</div>
	<div class="widget-content padding">


		<div class="error-div alert alert-danger" id="otp_error"></div>

		<form:form id="adminForgotPasswordForm" name="adminForgotPasswordForm"
			method="POST" autocomplete="on">

			<div class="form-group">
				<label class="control-label" for="newPassword"><spring:message
						code="eip.admin.forgotPassword.MobileNo" text="Mobile No." /></label>


				<form:input id="mobNo" path="mobileNumber" autocomplete="off" 
					class="form-control hasMobileNo"
					onkeypress="return admintryStep1(event)"/>
			</div>


			<div class="row">
				<div class="col-sm-6">

					<input type="button" class="btn btn-success btn-block"
						onclick="getAdminForgotPassStep2();"
						value="<spring:message code="eip.commons.submitBT" text="Submit"/>" />
				</div>
				<div class="col-sm-6">
					<input type="reset" class="btn btn-danger btn-block"
						value="<spring:message code="eip.agency.login.resetBT"/>"
						id="resettButon" onclick="{$('.error-div').html('');}">

				</div>
			</div>

		</form:form>
	</div>
</div>
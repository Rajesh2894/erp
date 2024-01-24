<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script
	src="js/eip/agency/agencyRegistrationForm.js"></script>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>

<script>

$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
	$(function(e) {
		$("#enterPassword").focus();
	});
</script>
<div class="row padding-40">
	<div class="col-md-4 col-md-offset-4">
		<div class="login-panel">
			<div class="widget margin-bottom-0">
				<div class="widget-header">
					<h2 class="login-heading">
						<spring:message code="eip.citizen.set.FormHeader" />
					</h2>
				</div>
				<div class="widget-content padding">
					<div class="error-div alert alert-danger alert-dismissable"
						role="alert" style="display: none"></div>
					<form:form id="agencySetPasswordForm" cssClass="form-horizontal" name="agencySetPasswordForm"
						method="POST" action="AgencySetPassword.html" autocomplete="off">

						<div class="form-group">
							<label class="col-sm-4 control-label"><spring:message code="eip.citizen.set.newPassword" /></label>
							<div class="col-sm-8"><form:password path="newPassword" id="enterPassword" cssClass="mandClassColor form-control" maxlength="15" /></div>
						</div>
						<div class="form-group">
							<label for="reEnteredPassword" class="col-sm-4 control-label">
								<spring:message code="eip.citizen.set.reEnteredPassword" /></label>
							<div class="col-sm-8">
								<input type="password" class="mandClassColor form-control" name="reEnteredPassword" id="reEnteredPassword" maxlength="15" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<input type="button" class="btn btn-primary btn-block"
									onclick="doAgencySetPassword();"
									value="<spring:message code="eip.commons.submitBT" text="Submit"/>" />
							</div>
							<div class="col-sm-6">
								<input type="button" class="btn btn-danger btn-block"
									value="<spring:message code="eip.agency.login.resetBT"/>"
									id="resettButon" onclick="{$('.error-div').html('');}">
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
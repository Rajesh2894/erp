<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
	$(document).ready(function() {
		$('#resettButon').on('click', function() {
			$('#frmNewsLetterSubscription').find('input:text').val('');
			$('.error-div').hide();
			$('.message').hide();
		});
		$('.message').hide();
	});

	jQuery('.hasMobileNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
		$(this).attr('maxlength', '10');
	});

	function showError1(errorList) {
		var errMsg = '<button type="button" class="close" onclick="closeErrBox1()"><span aria-hidden="true">&times;</span></button><ul>'
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('.message').html(errMsg);
		$('.error-div').hide();
		$('.message').show();
		$('#frmNewsLetterSubscription').find(
				'input:text, input:password, select, textarea').val('');
	}
	function closeErrBox1() {
		$('.message').hide();
	}
</script>
<style>
.message {
	border: 1px solid rgba(0, 153, 0, 0.2);
	background: rgba(0, 153, 0, 0.2);
	text-align: center;
	width: 95.5%;
	padding: 7px 5px;
	margin: 5px 0;
	font-style: 14px;
	color: #333;
}
</style>
<div class="row padding-40">
	<div class="col-md-4 col-md-offset-4">
		<div class="login-panel">
			<div class="widget margin-bottom-0">
				<div class="widget  margin-bottom-0" id="content">
					<div class="widget-header">
						<h2>
							<strong><spring:message code="portal.newssub.title"  text="NEWS LETTER SUBSCRIPTION"/></strong>
						</h2>
					</div>


					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="widget-content padding">
						<div class="message alert" role="alert" style="display: none"></div>
						<div class="error-div alert alert-danger alert-dismissable"
							role="alert" style="display: none"></div>
						<form:form method="post"
							action="NewsLetterSubscription.html?subscribe"
							name="frmNewsLetterSubscription" id="frmNewsLetterSubscription"
							role="form" onclick="return false">



							<div class="form-group">
								<label for="emailId"><spring:message
										code="feedback.Email" /><span class="mand">*</span> </label>
								<apptags:inputField fieldPath="subscription.emailId"
									hasId="true" cssClass=" form-control mandClassColor "
									isDisabled="" />
							</div>
							<div class="row">
								<div class="col-xs-6">
									<input type="button" class="btn btn-primary btn-block"
										onclick="return subscribeNews(this);"
										value="<spring:message code="portal.subscribe" text="Subscribe"/>" />
								</div>
								<div class="col-xs-6">
									<input type="button" class="btn btn-danger btn-block"
										value="<spring:message code="eip.agency.login.resetBT"/>"
										id="resettButon">
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


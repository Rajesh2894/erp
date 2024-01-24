<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script>
	function submitPayuForm(obj) {
		/* return saveOrUpdateForm(obj,
				getLocalMessage("trade.license.edit"), "EgrassPaymentController.html?confirm",
				'confirm'); */
		var actUrl = $('#actionUrl').val();
		var theForm = '#frmPayForm';
		var requestData = __serializeForm(theForm);
		var URL = actUrl + '?confirmToPay';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		var errors = $(returnData).find(".error-msg");
		$(formDivName).html(returnData);
	}
</script>
<style>
.addColor {
	background-color: #fff !important
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="license.egras" text="PayU Payment "></spring:message></b>
			</h2>

			<apptags:helpDoc url="TradeApplicationForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">

			<form:form action="" method="POST" name="frmPayForm" id="frmPayForm"
				class="form-horizontal">

				<form:hidden path="actionUrl" id="actionUrl" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.amount" text="Due Amount " /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor  form-control"
							path="paymentReqDto.finalAmount" id="Amount"  disabled="true"/>
					</div>

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.appName" text="Applicant Name" /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor  form-control"
							path="paymentReqDto.applicantName" id="applicantName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.email" text="Email " /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor  form-control"
							path="paymentReqDto.email" id="email"  />
					</div>

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="eip.payment.mobno" text="Mobile No" /></label>
					<div class="col-sm-4">
						<form:input cssClass="mandClassColor  form-control"
							path="paymentReqDto.mobNo" id="mobNo" />
					</div>
				</div>
	

				<br>
				<div class="text-center padding_top_10">

					<button type="button" class="btn btn-success" id="continueForm"
						onclick="submitPayuForm(this);">
						<spring:message code="trade.btn.pay" text="Pay" />
					</button>
					&nbsp;<input type="button" class="btn btn-warning"
						onclick="cleardata(this)"
						value="<spring:message code="eip.payment.resetButton"  text="Reset"/>" />&nbsp;
					<button type="button" class="btn btn-primary"
						onclick="window.location.href='AdminHome.html'">Back</button>
				</div>
			</form:form>




		</div>
	</div>
</div>



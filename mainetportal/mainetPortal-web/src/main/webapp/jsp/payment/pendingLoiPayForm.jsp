<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>

<script>
	$(document).ready(
			function() {
				$("input:radio[name='offlineDTO.onlineOfflineCheck']").prop(
						'checked', false);
			});

	function saveLoiForm(element) {
		var succMsg = getLocalMessage('eip.citizen.loipayment.msg');

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() === 'Y')

			return saveOrUpdateForm(element, succMsg,
					'PendingLoiForm.html?redirectToPay', 'saveform');
		else
			return saveOrUpdateForm(element, succMsg,
					'PendingLoiForm.html?PrintReport', 'saveform');
	}
</script>



<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i></a></li>
	<li><spring:message code="form.loi.home" text="Home"/></li>
	<li class="active"><spring:message code="form.loi" text="LOI" />&nbsp;
		<spring:message code="form.loi.payment" text="Payment" /></li>
</ol>
<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="form.loi" text="LOI" />
				<strong> <spring:message code="form.loi.payment"
						text="Payment" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="form.loi.field" text="Field with" />
					<i class="text-red-1">*</i> <spring:message code="form.loi.mand"
						text="is mandatory" /> </span>
			</div>
			<form:form action="PendingLoiForm.html" method="POST"
				class="form-horizontal" id="loipaymentSearch">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="accordion-toggle">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#App-details">
									<spring:message code="form.loi.applicant.details"
										text="Application Details" />
								</a>
							</h4>
						</div>
						<div class="panel-collapse collapse in" id="App-details">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.serv.name" text="Service Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.serviceName" type="text"
											class="form-control" readonly="true" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.app.no" text="Application Number" /></label>
									<div class="col-sm-4">
										<form:input path="dto.loiMasData.loiApplicationId" type="text"
											class="form-control" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#LOI-details">
									<spring:message code="loi.form.let.inten"
										text="Letter of Intent (LOI) Details" />

								</a>
							</h4>

						</div>
						<div class="panel-collapse collapse in" id="LOI-details">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.num" text="LOI Number" /></label>
									<div class="col-sm-4">
										<form:input path="dto.loiMasData.loiNo" type="text"
											class="form-control" readonly="true" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.date" text="LOI Date" /></label>
									<div class="col-sm-4">
										<fmt:formatDate pattern="dd/MM/yyyy"
											value="${command.dto.loiMasData.loiDate}" var="loiDate" />
										<form:input path="" type="text" class="form-control"
											value="${loiDate}" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#Applicant-details"> <spring:message
										code="form.loi.app.detail" text="Applicant Details" />

								</a>
							</h4>
						</div>
						<div class="panel-collapse collapse in" id="Applicant-details">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="app.mob.no" text="Applicant Mobile No" /></label>
									<div class="col-sm-4">
										<form:input path="dto.mobileNo" type="text"
											class="form-control" readonly="true" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.mailid" text="Applicant EmailId" /></label>
									<div class="col-sm-4">
										<form:input path="dto.email" type="text" class="form-control"
											readonly="true" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="loi.form.appName" text="Applicant Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.applicantName" type="text"
											class="form-control" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#LoiFees-details"> <spring:message
										code="form.loi.charge" text="LOI Fees and Charges in Details" />

								</a>
							</h4>
						</div>
						<div class="panel-collapse collapse in" id="LoiFees-details">
							<div class="panel-body">

								<div class="table-responsive">
									<table class="table table-bordered table-striped">
										<tr>
											<th scope="col" width="80"><spring:message
													code="loi.sr.no" text="Sr. No" /></th>
											<th scope="col"><spring:message code="loi.charge.name"
													text="Charge Name" /></th>
											<th scope="col"><spring:message code="loi.charge.amt"
													text="Amount" /></th>
										</tr>
										<c:forEach var="charges"
											items="${command.dto.getChargeDesc()}" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td><form:input path="" type="text"
														class="form-control" value="${charges.key}"
														readonly="true" /></td>
												<td><fmt:formatNumber value="${charges.value}"
														type="number" var="amount" minFractionDigits="2"
														maxFractionDigits="2" groupingUsed="false" /> <form:input
														path="" type="text" class="form-control text-right"
														value="${amount}" readonly="true" /></td>
											</tr>
										</c:forEach>

										<tr>
											<td colspan="2"><span class="pull-right"><b><spring:message
															code="form.loi.tot.amt" text="Total LOI Amount" /></b></span></td>
											<td><fmt:formatNumber value="${command.dto.total}"
													type="number" var="totalAmount" minFractionDigits="2"
													maxFractionDigits="2" groupingUsed="false" /> <form:hidden
													path="dto.total" cssClass="form-control text-right"
													readonly="true" /> <form:input path=""
													value="${totalAmount}" cssClass="form-control text-right"
													readonly="true" /></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div id="onlineOffline">
					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
				</div>

				<div class="text-center padding-bottom-20">
					<input type="button" class="btn btn-success"
						onclick="return saveLoiForm(this);"
						value="<spring:message code="citizen.editProfile.submit" text="Submit" />" />
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>

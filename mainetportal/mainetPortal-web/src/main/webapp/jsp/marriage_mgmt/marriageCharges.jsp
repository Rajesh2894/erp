<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/marriage_mgmt/marriageCertificate.js"></script>

<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
	<li>Home</li>
	<li>LOI</li>
	<li class="active">Marriage Charge</li>
</ol>
<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				Marriage <strong> Charge</strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<form:form action="MarriageCertificateGeneration.html" method="POST"
				class="form-horizontal" id="loiGenerationForm"
				name="loiGenerationForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="marriageDTO.marId" id="marId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<h4>Application Details</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label">Service Name</label>
					<div class="col-sm-4">
						<form:input path="serviceName" type="text" class="form-control"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label">Application Number</label>
					<div class="col-sm-4">
						<form:input path="marriageDTO.applicationId" type="text"
							class="form-control" readonly="true" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">Applicant Name</label>
					<div class="col-sm-4">
						<form:input path="marriageDTO.applicantName" type="text"
							class="form-control" readonly="true" />
					</div>
					<label class="col-sm-2 control-label">Application Date</label>
					<div class="col-sm-4">
						<form:input path="marriageDTO.marDate" type="text"
							class="form-control" readonly="true" />
					</div>
				</div>


				<c:choose>
					<c:when test="${not empty command.printBT }">
						<div class="form-group" id="copies">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.lable.name.noOfcopies" /></label>
							<div class="col-sm-4">
								<form:input path="marriageDTO.noOfCopies" id="noOfCopies"
									name="noOfCopies" type="number" class="form-control"></form:input>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<!-- true from workflow charge generate task  -->
						<c:if test="${empty command.printBT }">
							<h4>LOI Fees and Charges in Details</h4>
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<tr>
										<th scope="col" width="80">Sr. No</th>
										<th scope="col">Charge Name</th>
										<th scope="col">Amount</th>

									</tr>
									<c:forEach var="charges" items="${command.loiDetail}"
										varStatus="status">
										<tr>
											<td>1</td>
											<td>${charges.loiRemarks}</td>
											<td><fmt:formatNumber value="${charges.loiAmount}"
													type="number" var="amount" minFractionDigits="2"
													maxFractionDigits="2" groupingUsed="false" /> <form:input
													path="" type="text" class="form-control text-right"
													value="${charges.loiAmount}" readonly="true" /></td>
										</tr>
									</c:forEach>

									<tr>
										<td colspan="2"><span class="pull-right"><b>Total
													LOI Amount</b></span></td>
										<td class="text-right">${command.totalLoiAmount}</td>

									</tr>
								</table>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>

				<!-- true when get input of no of copies -->
				<c:if test="${command.payableFlag eq 'Y' }">
					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
					<div class="form-group margin-top-10">
						<label class="col-sm-2 control-label"><spring:message
								code="mrm.field.name.amounttopay" /></label>
						<div class="col-sm-4">
							<input type="text" class="form-control text-right"
								value="${command.offlineDTO.amountToShow}" maxlength="12"
								disabled="true"></input> <a
								class="fancybox fancybox.ajax text-small text-info"
								href="MarriageRegistration.html?showChargeDetails"><spring:message
									code="mrm.lable.name.chargedetail" /> <i
								class="fa fa-question-circle "></i></a>
						</div>
					</div>
				</c:if>

				<div class="padding-top-10 text-center">
					<c:choose>
						<c:when
							test="${not empty  command.offlineDTO.amountToShow && empty command.printBT}">

							<c:set var="backButtonAction"
								value="MarriageCertificateGeneration.html" />
							<button type="button" class="btn btn-success" id="submit"
								onclick="saveAndGenerateLoi(this);">
								<spring:message code="mrm.button.submit" text="Submit"></spring:message>
							</button>
						</c:when>
						<c:otherwise>
							<c:set var="backButtonAction"
								value="MarriageCertificateGeneration.html" />

							<c:if test="${ empty  command.offlineDTO.amountToShow}">
								<button type="button" class="btn btn-success" id="submit"
									onclick="getCharges(this);">
									<spring:message code="mrm.button.submit" text="Submit"></spring:message>
								</button>
							</c:if>
							<c:if test="${ not empty  command.offlineDTO.amountToShow}">
								<button type="button" class="btn btn-success" id="submit"
									onclick="generatePrintAfterPayment(this);">
									<spring:message code="mrm.button.submit" text="Submit"></spring:message>
								</button>
							</c:if>



						</c:otherwise>
					</c:choose>

					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='${backButtonAction}'">
						<spring:message code="mrm.back" text="Back"></spring:message>
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>

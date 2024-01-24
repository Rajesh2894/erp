<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/adhBillContractPayment.js"></script>
<%-- Defect #156295 --%>
<style>
	#adhBillPayment #Paymentmode .radio {
		padding-top: 7px;
	}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="adh.contract.installment.collection" text="Contract Installment Collection" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="ADHContractBillPayment.html" method="post"
				class="form-horizontal" name="adhBillPayment" id="adhBillPayment">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="contract.details.label.number" text="Contract Number" /></label>
					<div class="col-sm-4">
						<form:input path="contractAgreementSummaryDTO.contNo"
							cssClass="form-control preventSpace" id="contractNo" data-rule-required="true"
							readonly="${command.formFlag eq 'Y' ? true : false }" />
					</div>

					<c:if test="${command.formFlag eq 'N'}">
						<div class="col-sm-6">
							<button type="button" class="btn btn-info" id="btnsearch"
								onclick="searchContract(this)">
								<i class="fa fa-search"></i>
								<spring:message code="adh.search" text="Search" />
							</button>
							<button type="button" class="btn btn-warning"
								onclick="window.location.href='ADHContractBillPayment.html'">
								<spring:message code="adh.reset" text="Reset" />
							</button>
						</div>
					</c:if>

				</div>

				<c:if test="${command.formFlag eq 'Y'}">
					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a1"><spring:message code=""
									text="Contract Details" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="a1">
							<div class="form-group">
								<apptags:input labelCode="contract.details.label.date"
									path="contractAgreementSummaryDTO.contDate"></apptags:input>
								<apptags:input labelCode="contract.label.department"
									path="contractAgreementSummaryDTO.contDept"></apptags:input>
							</div>
							<div class="form-group">
								<apptags:input labelCode="contract.label.represented.by"
									path="contractAgreementSummaryDTO.contp1Name"></apptags:input>
								<apptags:input labelCode="contract.label.vendor.name"
									path="contractAgreementSummaryDTO.contp2Name"></apptags:input>
							</div>
							<div class="form-group">
								<apptags:input labelCode="contract.details.label.from.date"
									path="contractAgreementSummaryDTO.contFromDate"></apptags:input>
								<apptags:input labelCode="contract.details.label.to.date"
									path="contractAgreementSummaryDTO.contToDate"></apptags:input>
							</div>
						</div>
					</div>
					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a2"><spring:message code=""
									text="Receipt Amount Details" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="a2">
							<div class="table-responsive">

								<table class="table table-bordered table-condensed">

									<tr>
										<th><spring:message code="" text="Agreement Amount" /></th>
										<th><spring:message code="" text="Balance Amount" /></th>
										<th><spring:message code="" text="Overdue Amount" /></th>
									</tr>
									<tr>
										<td><p class="text-center">${command.contractAgreementSummaryDTO.contAmount}</p></td>
										<td><p class="text-center">${command.contractAgreementSummaryDTO.balanceAmount}</p></td>
										<td><p class="text-center">${command.contractAgreementSummaryDTO.overdueAmount}</p></td>
									</tr>
								</table>
							</div>

							<div class="form-group padding-top-10">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Enter Receipt Amount" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="payAmount"
											cssClass="form-control mandColorClass hasDecimal text-right"
											maxlength="12" id="payAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											onchange="getAmountFormatInDynamic((this),'payAmount')" />
										<label class="input-group-addon"><i class="fa fa-inr"></i></label>
									</div>
								</div>
							</div>
						</div>
					</div>





					<div class="panel panel-default">
						<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
					</div>

					<div class="text-center padding-top-10">
						<button type="button" id="saveData"
							class="btn btn-success btn-submit" onclick="saveBillData(this)">
							<spring:message code="" text="Submit" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='ADHContractBillPayment.html'">
							<spring:message code="" text="Back" />
						</button>
					</div>
				</c:if>
			</form:form>
		</div>
	</div>
</div>

<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/property/billingMethodChange.js" type="text/javascript"></script>
<div id="dataDiv">
	<!-- End JSP Necessary Tags -->

	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- Start Content here -->
	<div class="content">
		<div class="widget">

			<div class="widget-header">
				<h2>
					<strong><spring:message code="property.billMethodChange"
							text="Bill Method Change" /></strong>
				</h2>
			</div>

			<div class="widget-content padding">
				<!-- Start Form -->
				<form:form action="BillingMethodAuthorization.html"
					class="form-horizontal form" name="BillingMethodChange"
					id="BillingMethodChange">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<form:hidden path="lableValueDTO.applicationId"
						value="${command.lableValueDTO.applicationId}" />
					<form:hidden path="lableValueDTO.lableId"
						value="${command.lableValueDTO.lableId}" />
					<form:hidden path="serviceId" value="${command.serviceId}" />

					<c:if test="${not empty  command.oldBillMasList}">
						<h4 class="panel-title table margin-top-10" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
									code="property.existingBillDetails"
									text="Existing Bill Details" /></a>
						</h4>
						<div style="height: auto; overflow: auto">
							<table id="BillDetails"
								class="table table-striped table-bordered margin-top-10">
								<tr>
									<th width="10%"><spring:message code="propertyTax.SrNo" /></th>
									<th width="10%"><spring:message code="" text="Bill No" /></th>
									<th width="10%"><spring:message code="" text="Bill Date" /></th>
									<th width="10%"><spring:message
											code="property.Year(From-To)" text="Year(From-To)" /></th>
									<th width="10%"><spring:message
											code="property.TotalDemand" text="Total Demand" /></th>
									<th width="15%"><spring:message
											code="property.BalanceAmount" text="Balance Amount" /></th>
								</tr>
								<tbody>
									<div id="a3" class="panel-collapse collapse in">
										<c:forEach var="billMasList" items="${command.oldBillMasList}"
											varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td>${billMasList.bmNo}</td>
												<td>${billMasList.bmBilldtString}</td>
												<td>${billMasList.bmCcnOwner}</td>
												<fmt:formatNumber type="number"
													value="${billMasList.bmTotalAmount}" groupingUsed="false"
													maxFractionDigits="2" minFractionDigits="2"
													var="bmTotalAmount" />
												<td class="text-right">${bmTotalAmount}</td>
												<fmt:formatNumber type="number"
													value="${billMasList.bmTotalOutstanding}"
													groupingUsed="false" maxFractionDigits="2"
													minFractionDigits="2" var="bmTotalOutstanding" />
												<td class="text-right">${bmTotalOutstanding}</td>
											</tr>
										</c:forEach>
									</div>
								</tbody>
							</table>
						</div>
					</c:if>

					<c:if test="${not empty  command.newBillMasList}">
						<h4 class="panel-title table margin-top-10" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
									code="property.newBillDetails" text="New Bill Details" /></a>
						</h4>
						<div style="height: auto; overflow: auto">
							<table id="BillDetails"
								class="table table-striped table-bordered margin-top-10">
								<tr>
									<th width="10%"><spring:message code="unitdetails.flatNo"
											text="Flat No" /></th>
									<th width="10%"><spring:message code="prop.bill.print.No"
											text="Bill No" /></th>
									<th width="10%"><spring:message code="unitdetails.year"
											text="Year" /></th>
									<th width="10%"><spring:message code="property.billdate"
											text="Bill Date" /></th>
									<th width="10%"><spring:message code="property.dateFromTo"
											text="Date(From-To)" /></th>
									<th width="15%"><spring:message code="property.billamount"
											text="Bill Amount" /></th>
								</tr>
								<tbody>
									<div id="a3" class="panel-collapse collapse in">
										<c:forEach var="billMasList" items="${command.newBillMasList}"
											varStatus="status">
											<tr>
												<td>${billMasList.flatNo}</td>
												<td>${billMasList.bmNo}</td>
												<td class="text-center">${billMasList.billDistrDateString}</td>
												<td class="text-center">${billMasList.bmBilldtString}</td>
												<td class="text-center">${billMasList.bmCcnOwner}</td>
												<fmt:formatNumber type="number"
													value="${billMasList.bmTotalOutstanding}"
													groupingUsed="false" maxFractionDigits="2"
													minFractionDigits="2" var="bmTotalOutstanding" />
												<td class="text-right">${bmTotalOutstanding}</td>
											</tr>
										</c:forEach>
									</div>
								</tbody>
							</table>
						</div>
					</c:if>

					<%-- <div class="form-group">
						<apptags:CheckerAction showInitiator="true" hideForward="true"
							hideSendback="true"></apptags:CheckerAction>
					</div> --%>
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveForms(this)" id="submit">
							<spring:message code="bt.save" text="Submit" />
						</button>

						<button class="btn btn-blue-2" type="button"
							onclick="backToFirstPage(this)" id="back">
							<spring:message code="bt.backBtn" text="Back"></spring:message>
						</button>
					</div>

					<!-- End Each Section -->
				</form:form>
				<!-- End Form -->

			</div>
		</div>
	</div>
</div>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>

<div class="widget margin-bottom-0">
	<div class="widget-header">
		<h2>
			<strong><spring:message
					code="account.expenditure.budget.detail" text="Budget provision Details" /></strong>
		</h2>

	</div>
	<div class="padding-10" style="backgound: #ddd;">

		<form:form action="" method="POST" class="form-horizontal"
			modelAttribute="accountBillEntryBean">
			<form:hidden path="" value="${errorMsg}" id="errorMsg" />

			<div class="table-responsive" id="budgetDetTable">
				<table id="budgetDetailTable" border="0" cellspacing="0"
					cellpadding="0"
					class="table table-bordered table-striped appendableBudgetDetClass">
					<tbody>
						<tr>
							<form:hidden
								path="expenditureDetailList[0].projectedExpenditureId" />
							<form:hidden path="expenditureDetailList[0].newBalanceAmount" />
							<th scope="col" width="50%"><spring:message code="account.approvedProposals"
									 text="Approved Proposals" /></th>
							<td width="50%"><form:input id="originalEstimate"
									path="expenditureDetailList[0].originalEstimate"
									disabled="true" readonly="" class="form-control text-right" /></td>
						</tr>
						<tr>
							<th scope="col"><spring:message code="account.tenderApproved"
									 text="Tender Approved" /></th>
							<td><form:input id="balanceAmount"
									path="expenditureDetailList[0].balanceAmount" disabled="true"
									class="form-control text-right" /></td>
						</tr>
						<tr>
							<th scope="col"><spring:message code="account.workOrderIssued"
									 text="Work Order Issued" /></th>
							<td><form:input id="expActAmt0"
									path="expenditureDetailList[0].actualAmount" disabled="true"
									class="form-control text-right" /></td>
						</tr>
						<tr>
							<th scope="col"><spring:message code="account.administrativeApprovals"
								 text="Administrative Approvals" /></th>
							<td><form:input id="balProvisionAmount"
									path="expenditureDetailList[0].balProvisionAmount"
									readonly="true" class="form-control text-right" /></td>
						</tr>


					</tbody>
				</table>
				<div class="text-center padding-10">
					<button type="button" class="btn btn-danger"
						onclick="setPrevBalanceAmount()">
						<spring:message code="account.close" text="Close" />
					</button>
				</div>



			</div>

		</form:form>
	</div>

</div>
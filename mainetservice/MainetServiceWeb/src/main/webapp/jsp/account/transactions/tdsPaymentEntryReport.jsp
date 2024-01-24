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
<script src="js/account/transaction/tdsPaymentEntry.js"
	type="text/javascript"></script>

<script>
$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	maxDate: '0',
});
$(".datepicker").datepicker('setDate', new Date()); 
</script>
<!-- Start Content here -->
<div class="widget">
	<div class="widget-header">
		<h2><spring:message code="account.tds.payment.entry" text="TDS Payment Entry Report" /></h2>
	</div>
	<div class="widget-content padding">
		<form:form method="POST" action="TdsPaymentEntry.html"
			cssClass="form-horizontal" name="paymentEntry"
			id="tdsPaymentEntryFrm" modelAttribute="tdsPaymentEntryDto">
			<div id="receipt">
				<div class="col-xs-12 text-right">
				<p><spring:message code="account.from.gen" text="Form GEN-5" /></p>
				</div>
				<div class="form-group">
					<div class="col-xs-12 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
						<c:if test="${userSession.languageId eq 1}">
							${userSession.organisation.ONlsOrgname}
						</c:if>
						<c:if test="${userSession.languageId ne 1}">
							${userSession.organisation.ONlsOrgnameMar}
						</c:if>
						
						</h3>
						 ${oPaymentReportDto.voucherType}
					</div>
					<div class="col-xs-12">
					<p><strong><spring:message code="budget.consolidation.format.functionary" text="Name of the Bank:" /></strong> ${oPaymentReportDto.bankName}</p>
					</div>
					<div class="col-xs-8">
					<p><strong><spring:message code="tds.cartificate.date" text="Date:" /></strong>${oPaymentReportDto.payDate}</p>
					</div>
					<div class="col-xs-4">
					<p><strong><spring:message code="account.expenditure.name.fund" text="Name of the fund:" /></strong> ${oPaymentReportDto.nameOfTheFund}</p>
					</div>
					<div class="col-xs-8">
					<p><strong><spring:message code="voucher.template.entry.master.department" text="Department: " /> </strong>${oPaymentReportDto.depatmentDesc}</p>
					</div>
					<div class="col-xs-4">
					<p><strong><spring:message code="account.tds.cpv.bpv.no" text="CPV/BPV No" /> </strong>${oPaymentReportDto.bankNumber}</p>
					</div>
					<div class="col-xs-8">
					<p><strong><spring:message code="account.Name.of.Claimant" text="Name of Claimant:" /></strong> ${oPaymentReportDto.vendorCodeDescription}</p>
					</div>
					<div class="col-xs-4">
					<p><strong><spring:message code="account.expenditure.p.o.w.o" text="P.O / W.O No." /> </strong></p>
					</div>
					
				</div>
				<table class="table table-bordered table-condensed">
				<thead>
				<tr>
				<th colspan="2"><spring:message code="budget.reappropriation.master.budget" text="Budget" /></th>
				<th rowspan="2"><spring:message code="account.deposit.accountHead" text="Account Head" /></th>
				<th rowspan="2"><spring:message code="contingent.claim.bill.no" text="Payment Order No." /></th>
				<th rowspan="2"><spring:message code="account.Cheque.No" text="Cheque No." /></th>
				<th rowspan="2"><spring:message code="account.deposit.rs" text="Amount (Rs.)" /></th>
				</tr>
				
				<tr>
				<th><spring:message code="account.budget.code.master.functioncode" text="Function" /></th>
				<th><spring:message code="budget.consolidation.format.functionary" text="Functionary" /></th>
				</tr>
				
				<tr>
				<th>1</th>
				<th>2</th>
				<!-- <th>3</th> -->
				<th>3</th>
				<th>4</th>
				<th>5</th>
				<th>6</th>
				</tr>
				</thead>
				
				<tbody>
				<c:forEach items="${oPaymentReportDto.paymentDetailsDto}"
						var="directPaymentList" varStatus="status">
				<tr>
				<td>${directPaymentList.functionDesc}</td>
				<td>${directPaymentList.functionaryDesc}</td>
				<td>${directPaymentList.accountCode}</td>
				<!-- <td>&nbsp;</td> -->
				<td>${oPaymentReportDto.voucherNo}</td>
				<td>${oPaymentReportDto.chequeNo}</td>
				<td style="text-align: right">${directPaymentList.paymentAmountDesc}</td>
				</tr>
				</c:forEach>
				
				<tr>
				<td colspan="2"><b><spring:message code="accounts.total.in.words" text="Total (in Words)" /></b></td>
				<td>${oPaymentReportDto.amountInWords}</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<!-- <td>&nbsp;</td> -->
				</tr>
				
				<tr>
				<td colspan="2"><b><spring:message code="account.Prepared.by" text="Prepared by:" /></b> ${oPaymentReportDto.preparedBy}</td>
				<td ><b><spring:message code="account.report.Verified.by" text="Verified by:" /></b> </td>
				<td colspan="2"><b><spring:message code="account.report.Approved.by" text="Approved by:" /></b> </td>
				<td><b><spring:message code="account.report.Posted.by" text="Posted by:" /></b> </td>
				</tr>
				
				<tr>
				<td colspan="2"><b><spring:message code="accounts.date" text="" /></b> ${oPaymentReportDto.preparedDate}</td>
				<td ><b><spring:message code="accounts.date" text="Date:" /></b></td>
				<td colspan="2"><b><spring:message code="accounts.date" text="Date:" /></b></td>
				<td><b><spring:message code="accounts.date" text="Date:" /></b></td>
				</tr>
				
				<tr>
				<td colspan="3" class="text-right"><b><spring:message code="account.Received.Payment" text="Received Payment" /></b></td>
				<td colspan="3" class="text-right">${oPaymentReportDto.voucherAmount}</td>
				</tr>
				<tr>
						<td colspan="7"
							class="height-155 text-right vertical-align-bottom"><p>...................................................................................</p>
							<p>
								<spring:message code="account.voucher.auth.offc.sign"
									text="Signature of Authorised Officer" />
							</p>
							<p>
								<spring:message code="account.voucher.auth.offc.name"
									text="(Name / Designation of the Authorised Officer)" />
							</p></td>
					</tr>
				</tbody>
				</table>
				<div class="text-center hidden-print padding-10">
					<button onclick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
				  </button>
						
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('TdsPaymentEntry.html');"
						value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
				</div>
			</div>
		</form:form>
	</div>
</div>

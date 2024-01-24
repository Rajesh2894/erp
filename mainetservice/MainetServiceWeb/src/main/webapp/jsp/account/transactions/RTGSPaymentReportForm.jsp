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
<script src="js/account/transaction/RTGSPaymentEntry.js"
	type="text/javascript"></script>

<script>
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0',
	});
	$(".datepicker").datepicker('setDate', new Date());
</script>
<script language="javascript">
	function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>

<!-- Start Content here -->
<div class="widget">
	<!-- <div class="widget-header">
		<h2>RTGS Payment Entry Form</h2>
	</div> -->
	<div class="widget-content padding">
		<form:form method="POST" action="PaymentEntry.html"
			cssClass="form-horizontal" name="paymentEntry" id="paymentEntry"
			modelAttribute="oPaymentReportDto">
			<div id="receipt">
				<div class="form-group">
					<div class="col-xs-12 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
						<c:if test="${userSession.languageId eq 1}">
						${userSession.getCurrent().organisation.ONlsOrgname}
						</c:if>
						<c:if test="${userSession.languageId ne 1}">
						${userSession.organisation.ONlsOrgnameMar}
						</c:if>
						
						</h3>
					</div>
				</div>
				<table class="table table-bordered table-condensed">
					<tr>
						<th style="text-align: left"><spring:message code="account.RTGS.Entry.No"
								text="RTGS Entry No." /></th>
						<td>${oPaymentReportDto.voucherNo}</td>
						<th style="text-align: left"><spring:message code="advance.management.master.paymentdate" text="Payment Date"></spring:message></th>
						<td colspan="2">${oPaymentReportDto.payDate}</td>
					</tr>
					<c:forEach items="${oPaymentReportDto.listOfTbPaymentRepor}"
						var="listOfTbPayment" varStatus="status">
						<c:set value="${status.index}" var="count"></c:set>
						<tr>
							<th style="text-align: left"><spring:message code="account.ULB.Bank.Name"
									text="ULB Bank Name" /></th>
							<td>${listOfTbPayment.bankName}</td>
							<th style="text-align: left"><spring:message code="accounts.receipt.branch.name"
									text="Branch Name" /></th>
							<td colspan="2">${listOfTbPayment.branchName}</td>
						</tr>
						<tr>
							<th style="text-align: left"><spring:message code="bank.master.acc.number"
									text="Bank A/c No" /></th>
							<td>${listOfTbPayment.bankNumber}</td>
							<th style="text-align: left"><spring:message code="accounts.receipt.cheque.number"
									text="Cheque No." /></th>
							<td colspan="2">${oPaymentReportDto.chequeNo}</td>
						</tr>
					</c:forEach>
					<tr>
						<th style="text-align: left"><spring:message code="account.Beneficiary.Name"
								text="Beneficiary Name" /></th>
						<th style="text-align: left"><spring:message code="account.Bank.Name.Baranch.Name"
								text="Bank Name & Baranch Name" /></th>
						<th style="text-align: left"><spring:message code="account.Beneficiary.A/c.No"
								text="Beneficiary A/c No." /></th>
						<th style="text-align: left"><spring:message code="accounts.vendormaster.ifsccode"
								text="IFSC Code" /></th>
						<th><spring:message code="direct.payment.entry.amount"
								text="Amount (Rs.)" /></th>
					</tr>

					<c:forEach items="${oPaymentReportDto.paymentDetailsDto}"
						var="directPaymentList" varStatus="status">
						<tr>
							<td>${directPaymentList.vendorName}</td>
							<td>${directPaymentList.bankName}-
								${directPaymentList.branchName}</td>
							<td>${directPaymentList.bankAccountNumber}</td>
							<td>${directPaymentList.ifscCode}</td>
							<td style="text-align: right">${directPaymentList.paymentAmountDesc}</td>
						</tr>
					</c:forEach>

					<tr>
						<th colspan="4" class="text-right"><spring:message
								code="account.voucher.total" text="Total" /></th>
						<th style="text-align: right">${oPaymentReportDto.voucherAmount}</th>

					</tr>
					<tr>
						<th style="text-align: left"><spring:message
								code="accounts.receipt.amt.in.words" text="Amount in Words" /></th>
						<td colspan="4">${oPaymentReportDto.amountInWords}</td>
					</tr>
				</table>
				<div class="text-center hidden-print padding-10">
					<button onclick="printdiv('receipt');"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('RTGSPaymentEntry.html');"
						value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
				</div>
			</div>
		</form:form>
	</div>
</div>

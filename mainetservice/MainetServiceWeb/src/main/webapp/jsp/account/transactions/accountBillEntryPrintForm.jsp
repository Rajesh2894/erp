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
<script src="js/account/transaction/accountBillEntry.js"
	type="text/javascript"></script>

<script>
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

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="contingent.claim.bill"
					text="Contingent Claim Bill" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="form-group">
						<div class="col-xs-12 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
							<c:if test="${userSession.languageId eq 1}">
							${userSession.getCurrent().organisation.ONlsOrgname}
							</c:if>
							
							<c:if test="${userSession.languageId ne 1}">
							${userSession.getCurrent().organisation.ONlsOrgnameMar}
							</c:if>
							
							
							
							</h3>
							
							<strong><spring:message code="contingent.claim.bill"
									text="Payment Order" /></strong>
							<!-- <br> -->
						</div>
					</div>
					<div class="padding-5 clear">&nbsp;</div>
					<table class="table table-bordered table-condensed">
						<tbody>
							<tr>
								<th style="text-align: left;"><spring:message
										code="contingent.claim.bill.no" text="Payment Order No.:" /></th>
								<td colspan="2">${AccountBillEntryMasterBean.billNo}</td>
								<th style="text-align: left;"><spring:message
										code="contingent.claim.bill.date" text="Date:" /></th>
								<td colspan="2">${AccountBillEntryMasterBean.billDate}</td>
							</tr>
							<tr>
								<th style="text-align: left;"><spring:message
										code="contingent.claim.bill.name" text="Name of the Claimant:" /></th>
								<td colspan="5">${AccountBillEntryMasterBean.vendorName}</td>
							</tr>

							<tr>
								<th style="text-align: left;"><spring:message
										code="contingent.claim.bill.details" text="Bill details:" /></th>
								<td colspan="5">${AccountBillEntryMasterBean.narration}</td>
							</tr>


							<tr>
								<th colspan="3" class="text-left"><spring:message
										code="contingent.claim.bill.itemiseds"
										text="Expenditure Discription" /></th>
								<th colspan="3"><spring:message
										code="contingent.claim.bill.grosstotal"
										text="Gross Amount (Rs.)" /></th>
							</tr>

							<c:forEach
								items="${AccountBillEntryMasterBean.expenditureDetailList}"
								var="expenditure" varStatus="theCount">
								<tr>
									<td colspan="3">${expenditure.vendorDesc}</td>
									<td colspan="3" align="right">${expenditure.actualAmountStr}</td>
								</tr>
							</c:forEach>

							<tr>
								<th colspan="3" class="text-left"><spring:message
										code="contingent.claim.bill.deductions"
										text="Deductions and Recoveries" /></th>
								<th colspan="3"><spring:message
										code="contingent.claim.bill.grosstotal"
										text="Gross Amount (Rs.)" /></th>
							</tr>
							<c:forEach
								items="${AccountBillEntryMasterBean.deductionDetailList}"
								var="deductionlist" varStatus="theCounts">
								<tr>
									<td colspan="3">${deductionlist.vendorCodeDescription}</td>
									<td colspan="3" align="right">${deductionlist.dedcutionAmountStr}</td>
								</tr>
							</c:forEach>
							<tr>
								<th colspan="3" class="vertical-align-middle"><spring:message
										code="contingent.claim.bill.particulars" text="Particulars" />
								</th>
								<th colspan="3"><spring:message
										code="contingent.claim.bill.amount" text="Amount (Rs.)" /></th>
							</tr>
							<tr>
								<th colspan="2" style="text-align: left;"><spring:message
										code="contingent.claim.bill.totalgross"
										text="Total Gross Amount (Rs.)" /></th>
								<td colspan="4" align="right">${AccountBillEntryMasterBean.totalActualamt}</td>
							</tr>
							<tr>
								<th colspan="2" style="text-align: left;"><spring:message
										code="contingent.claim.bill.totaldeduction"
										text="Total Deductions and recoveries (Rs.)" /></th>
								<td colspan="4" align="right">${AccountBillEntryMasterBean.totalDeductions}</td>
							</tr>
							<tr>
								<th colspan="2" style="text-align: left;"><spring:message
										code="contingent.claim.bill.netamount"
										text="Net amount payable in figures (Rs.)" /></th>
								<td colspan="4" align="right">${AccountBillEntryMasterBean.netPayable}</td>
							</tr>
							<tr>
								<th colspan="2" style="text-align: left;"><spring:message
										code="contingent.claim.bill.netpayable"
										text="Net amount payable in words (Rs.)" /></th>
								<td colspan="4" align="left">${AccountBillEntryMasterBean.totalSanctionedAmtStr}</td>
							</tr>
						</tbody>
					</table>



					<p class="text-small padding-top-10"> <spring:message code="account.certify.msg" text="(I certify that the above
						charges have been really paid to the proper payees and that the
						sub-vouchers have been so cancelled so that they cannot again be used.)"></spring:message>
						</p>
					<p class="text-small"><spring:message code="account.certify.payable.msg" text="(I hereby certify that the above charges
						are fully payable to the proper payees and the sub-vouchers or
						claim bills)"></spring:message></p>
					<div class="row padding-vertical-10">
						<div class="col-xs-5 text-center">
							<p> <spring:message code="account.Prepared.by" text="Prepared by "></spring:message>
								</br><spring:message code="account.person.preparing.the.voucher" text="(Name, designation, code of the person preparing
								the voucher)"></spring:message> 
							</p>
						</div>
					</div>

					<br>
					<p class="text-small"> <spring:message code="account.bills.checked.msg" text="The bills have been checked and found to
						be complying with the rules. Appropriate payment may be released
						to the claimant."></spring:message></p>

					<div class="row padding-vertical-10">
						<div class="col-xs-4 text-center">
							<p>
								<spring:message code="account.report.Approved.by" text="Approved by"></spring:message> </br><spring:message code="account.msg.the.Authorised.Officer" text="(Name, designation, code of the Authorised Officer)"></spring:message> 
							</p>
						</div>
					</div>

					<div class="text-center hidden-print padding-top-20">
						<button onclick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i> <spring:message code="account.budgetaryrevision.print" text="Print"></spring:message>
						</button>
						<c:if
							test="${AccountBillEntryMasterBean.authorizationMode eq 'Auth'}">
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountBillAuthorization.html'"
								value="<spring:message code="account.back" text="Back"/>" id="cancelEdit" />
						</c:if>
						<c:if
							test="${AccountBillEntryMasterBean.authorizationMode ne 'Auth' && grantFlag ne 'Y'}">
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountBillEntry.html'"
								value="<spring:message code="account.back" text="Back"/>" id="cancelEdit" />
						</c:if>
						<c:if
							test="${AccountBillEntryMasterBean.authorizationMode ne 'Auth' && grantFlag eq 'Y'}">
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('grantMaster.html');"
								value="<spring:message code="account.back" text="Back"/>" id="cancelEdit" />
						</c:if>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
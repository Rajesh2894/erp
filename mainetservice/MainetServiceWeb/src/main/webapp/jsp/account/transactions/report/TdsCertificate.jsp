
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountFinancialReport.js"
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
<!-- Start Content here -->
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header"></div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div
							class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-2  text-center">
							<h3 class="text-large margin-bottom-0 margin-top-0 text-bold">${ userSession.getCurrent().organisation.ONlsOrgname}
							</h3>
						</div>


						<div class="clearfix padding-10"></div>
						<div class="container">

							<table class="table table-bordered  table-fixed">
								<tr class="text-center">

								</tr>
								<tr class="text-center">

								</tr>
								<tr class="text-center">
									<td colspan="6"><spring:message
											code="tds.cartificate.section203"
											text="Certificate under Section 203 of the Income Tax Act, 1961 for Tax Deducted at Source" /></td>
								</tr>

								<tr>
									<th colspan="3" width="50%"><spring:message
											code="tds.cartificate.no" text="Certificate No.:" /></th>
									<th colspan="3"><spring:message
											code="tds.cartificate.last.on" text="Last Updated On:" /></th>
								</tr>
								<tr>
									<th colspan="3" class="text-center"><spring:message
											code="tds.cartificate.deductor"
											text="Name and Address of the Deductor (Transferee)" /></th>
									<th colspan="3" class="text-center"><spring:message
											code="tds.cartificate.deductee"
											text="Name and Address of the Deductee (Transferor)" /></th>
								</tr>
								<tr>
									<td colspan="3">${reportData.vendorName}</td>
									<td colspan="3">&nbsp;</td>
								</tr>
								<tr>
									<th class="text-center" colspan="2" width="32%"><spring:message
											code="tds.cartificate.pan.deductor"
											text="PAN of the Deductor (Transferee)" /></th>
									<th class="text-center" colspan="2"><spring:message
											code="tds.cartificate.pan.deductee"
											text="PAN of the Deductee (Transferor)" /></th>
									<th class="text-center" colspan="2"><spring:message
											code="tds.cartificate.assesment" text="Assessment Year" /></th>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<th colspan="6" class="text-center"><spring:message
											code="tds.cartificate.summary"
											text="Summary of Transaction(s)" /></th>
								</tr>
								<tr>
									<th><spring:message code="tds.cartificate.sr.no"
											text="Sr. No." /></th>
									<th colspan="2"><spring:message
											code="tds.cartificate.unique.number"
											text="Unique Acknowledgement Number" /></th>
									<th colspan="2"><spring:message
											code="tds.cartificate.amount.paid"
											text="Amount Paid / Credited" /></th>
									<th><spring:message code="tds.cartificate.payment"
											text="Date of Payment / Credit (dd/mm/yyyy)" /></th>
								</tr>
								<tr>
									<td>1</td>
									<td colspan="2">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<th><spring:message code="tds.cartificate.total"
											text="Total(Rs.)" /></th>
									<td colspan="2">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<th colspan="6" class="text-center">I. DETAILS OF TAX
										DEDUCTED AND DEPOSITED WITH RESPECT TO THE DEDUCTEE<br>
										(Income Tax + Surcharge + Edu. Cess)
									</th>
								</tr>
								<tr>
									<th rowspan="2"><spring:message
											code="tds.cartificate.sr.no" text="Sr. No." /></th>
									<th colspan="2" rowspan="2" width="25%"><spring:message
											code="tds.cartificate.amounts" text="Amount (Rs.)" /></th>
									<th colspan="3" class="text-center"><spring:message
											code="tds.cartificate.challan"
											text="Challan Identification number (CIN)" /></th>
								</tr>
								<tr>
									<th><spring:message code="tds.cartificate.bsr"
											text="BSR Code of the Bank Branch" /></th>
									<th><spring:message code="tds.cartificate.tax"
											text="Date on which Tax Deposited(dd/mm/yyyy)" /></th>
									<th><spring:message code="tds.cartificate.serial.no"
											text="Challan Serial Number" /></th>
								</tr>
								<tr>
									<td>1</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>

								<tr>
									<th colspan="6" class="text-center">II. DETAILS OF TAX
										DEPOSITED IN THE CENTRAL GOVERNMENT ACCOUNT THROUGH CHALLAN <br>
										(Income Tax + Surcharge + Edu. Cess + Fee + Interest)
									</th>
								</tr>
								<tr>
									<th rowspan="2"><spring:message
											code="tds.cartificate.sr.no" text="Sr. No." /></th>
									<th colspan="2" rowspan="2" width="25%"><spring:message
											code="tds.cartificate.amounts" text="Amount (Rs.)" /></th>
									<th colspan="3" class="text-center"><spring:message
											code="tds.cartificate.challan"
											text="Challan Identification number (CIN)" /></th>
								</tr>
								<tr>
									<th><spring:message code="tds.cartificate.bsr"
											text="BSR Code of the Bank Branch" /></th>
									<th><spring:message code="tds.cartificate.tax"
											text="Date on which Tax Deposited(dd/mm/yyyy)" /></th>
									<th><spring:message code="tds.cartificate.serial.no"
											text="Challan Serial Number" /></th>
								</tr>
								<tr>
									<td>1</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<th colspan="6" class="text-center"><spring:message
											code="tds.cartificate.varification" text="Verification" /></th>
								</tr>
								<tr>
									<td colspan="6">I, , son / daughter of working in the
										capacity of (designation) do hereby certify that a sum of Rs.
										[Rs. (in words)] has been deducted and deposited to the credit
										of the Central Government. I further certify that the
										information given above is true, complete and correct and is
										based on the books of account, documents, TDS statements, TDS
										deposited and other available records.</td>
								</tr>
								<tr>
									<th><spring:message code="tds.cartificate.place"
											text="Place:" /></th>
									<td>&nbsp;</td>
									<th colspan="4"><spring:message
											code="tds.cartificate.signature.person"
											text="Signature of person responsible for deduction of tax:" /></th>
								</tr>
								<tr>
									<th><spring:message code="tds.cartificate.date"
											text="Date:" /></th>
									<td>&nbsp;</td>
									<th colspan="4"><spring:message
											code="tds.cartificate.fullname" text="Full Name:" /></th>
								</tr>
							</table>

						</div>
						<div class="text-center hidden-print padding-10">
							<button onclick="printdiv('receipt');"
								class="btn btn-primary hidden-print" title="Print">
								<i class="fa fa-print"></i>
								<spring:message code="account.budgetestimationpreparation.print"
									text="Print" />
							</button>
							<button type="button" class="btn btn-danger"
								onclick="window.location.href='AccountOtherReports.html'" title="Back">
								<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
								<spring:message code="account.bankmaster.back" text="Back" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

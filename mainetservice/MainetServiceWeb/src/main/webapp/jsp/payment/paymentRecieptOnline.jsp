<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script type="text/javascript">

	function PrintDiv(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}

	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});
		});
	});
</script>

<style type="text/css">
.bold-text {
	font-weight: bold;
}

.message {
	font-size: 12px;
	font-weight: bold;
}
</style>
<div class="widget">
	<div class="widget-content padding" id="receipt">
		<form action="" method="get" class="form-horizontal">
			<div id="receipt" class="receipt-content">

				<p style="text-align: center;">
					<spring:message code="" text="E-Payment Receipt" />
				</p>
				<div class="clearfix padding-10"></div>
				 <div> <c:if test="${not empty userSession }"> 
					<img width="80"  src="${userSession.orgLogoPath}" alt="Organisation Logo"
						class="not-logged-avatar"></c:if>
				</div>
				<div class="clearfix padding-10"></div>
				<p class="text-center message">
					<spring:message code="" text="(To be retained by applicant)" />
				</p>
				
				
				<div id="export-excel">
					<table class="table table-bordered table-condensed">
						<tbody>
							<tr>
								<td width="25%" style="text-align: left;" ><spring:message code="" text="Case Type" /></td>
								<td width="25%" class="bold-text"><spring:message code="" text="New Licence" /></td>

								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Application Type" /></td>
								<td width="25%"  class="bold-text"><spring:message code=""
										text="Letter of Intent (LOI)" /></td>
							</tr>
							<tr>
								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Licence Fee" /></td>
								<td width="25%"  class="bold-text">0</td>
								
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Scrutiny Fee" /></td>
								<td width="25%" class="bold-text">0</td>
								
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Total Fee" /></td>
								<fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								 <td width="25%" class="bold-text">${amount}</td>

								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
							
				<div class="clearfix padding-5"></div>
				<div id="export-excel">
					<table class="table table-bordered table-condensed">
						<tbody>
							<tr>
								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Case Id" /></td>
								<td width="25%" class="bold-text">${command.caseId}</td>

								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Application Id" /></td>
								<td width="25%" class="bold-text">${command.appId}</td>
							</tr>
							<tr>
								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Reference No" /></td>
								<td width="25%" class="bold-text">${command.applicationId}</td>
								
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Mobile No" /></td>
								<td width="25%" class="bold-text">${command.mobileNo}</td>

								<td width="25%" style="text-align: left;"><spring:message code=""
										text="Email Id" /></td>
								<td width="25%" class="bold-text">${command.email}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="clearfix padding-10"></div>
				
				

				<table class="table" id="paymentDetails">
					<tr>
						<td><spring:message text="(1)" /> <spring:message
								text="Transaction No." /></td>
						<td class="text-right bold-text">${command.trackId}</td>
					</tr>
					<tr>
						<td><spring:message text="(2)" /> <spring:message
								text="Transaction Date" /></td>
						<td class="text-right bold-text"><c:set value="${command.paymentDateTime}" var="paymentDateTime"></c:set>
							<fmt:formatDate type="date" value="${paymentDateTime}" pattern="dd/MM/yyyy hh:mm a" /></td>
					</tr>
					<tr>
						<td><spring:message text="(3)" /> <spring:message
								text="GR No./Txn No." /></td>
						<td class="text-right bold-text">${command.transactionId}</td>
					</tr>
					<tr>
						<td><spring:message text="(4)" /> <spring:message
								text="Status" /></td>
						<td class="text-right bold-text">${command.status}</td>
					</tr>
					<tr>
						<td><spring:message text="(5)" /> <spring:message
								text="Received Amount Date" /></td>
						<td class="text-right bold-text">
							      <c:set value="${command.paymentDateTime}"
									 var="paymentDateTime">
							       </c:set> 
							       <fmt:formatDate type="date" value="${paymentDateTime}" pattern="dd/MM/yyyy" />
										</td>
					</tr>
					<tr>
						<td><spring:message text="(6)" /> <spring:message
								text="Payment Aggregator" /></td>
						<td class="text-right bold-text">IDBI</td>
					</tr>
					<tr>
						<td><spring:message text="(7)" /> <spring:message
								text="Total Amount" /></td>
						<fmt:formatNumber type="number" value="${command.amount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amount"/>			 					 
								 <td class="text-right bold-text">${amount}</td>
					</tr>
					<tr>
						<td><spring:message text="(8)" /> <spring:message
								text="Remarks" /></td>
						<td class="text-right bold-text">${command.remark}</td>
					</tr>
					<tr>
						<td><spring:message text="(9)" /> <spring:message
								text="Payment Mode" /></td>
						<td class="text-right bold-text">Online</td>
					</tr>
				</table>
				<div class="clearfix padding-10"></div>


				<style>
				@media print {
					@page {
						margin: 0 10px;
					}
					.receipt-content {
						overflow: hidden;
						padding: 10px !important;
						margin-top: 10px !important;
					}
					.border-black {
						border: 1px solid #000;
					}
					.overflow-hidden {
						overflow: hidden !important;
					}
					.receipt-content .ulb-name {
						margin-bottom: 0 !important;
					}
					.message {
						font-size: 12px;
						font-weight: bold;
					}					
					.bold-text {
						font-weight: bold;
					}
				}
				</style>
			</div>
			
			<div class="text-center hidden-print padding-10">
				<button onclick="PrintDiv('receipt');" class="btn btn-primary hidden-print" type="button">
					<i class="fa fa-print"></i>
					<spring:message code="material.management.print" text="Print" />
				</button>
				<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="bckBtn" text="Back" />
			</div>
		</form>
	</div>
</div>

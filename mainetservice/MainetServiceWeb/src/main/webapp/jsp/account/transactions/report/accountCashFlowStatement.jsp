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
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="js/account/accountCollectionSummaryReport.js"
	type="text/javascript"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script>
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
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message 
						text="Cash Flow Report" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="form-group">
							<div
								class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3>
								<h3 class="text-extra-large margin-bottom-0 margin-top-0 excel-title"><spring:message code="cashflow" text="Cash Flow Report" /></h3>
								<p>
									<strong><spring:message code="cashflowyearended" text="For the year ended 31st March 2018" /></strong>
								</p>
							</div>
							<div class="col-sm-2 col-xs-3">
								<p>
									Date:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br>Time:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
							</div>
						</div>
						<div class="clearfix"></div>
						<!-- <div class="padding-5 clear">&nbsp;</div> -->
						<div class="overflow-visible">
							<div id="export-excel">
							<table class="table table-bordered">
						<tbody>
							<tr>
								<th class="text-center" width=50%><spring:message code="cashflow.particular" text="Particulars" /></th>
								<th class="text-center"><spring:message code="cashflowcurrentyear" text="Current Year" /></th>
								<th class="text-center"><spring:message code="cashflowpreviousyear" text="Previous Year" /></th>
							</tr>
							<tr>
								<th class="text-center">1</th>
								<th class="text-center">2</th>
								<th class="text-center">3</th>
							</tr>
							<tr>
								<th><spring:message code="cashflowopactivity" text="[A] Cash flows operating activities" /></th>
								<th></th>
								<th></th>
							</tr>
							<tr>
								<td><spring:message code="cashflowgrosssurplus" text="Gross surplus / (deficit) over expenditure" /></td>
								<td class="text-right">${reportList.currentYearAmount}</td>
								<td class="text-right">${reportList.previousIncome}</td>
							</tr>
							<tr>
								<td class="text-bold"><spring:message code="cashflowaddadjustment" text="Add:Adjustments for" /></td>
								<td class="text-right"></td>
								<td class="text-right"></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowdepreciation" text="Depreciation" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowprovisions" text="Provisions" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowinterest" text="Interest & finance expenses" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td class="text-bold"><spring:message code="cashflowlessadjusment" text="Less:Adjustments for" /></td>
								<td class="text-right"></td>
								<td class="text-right"></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowprofitofdisposal" text="Profit on disposal of assets" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowdividentincome" text="Dividend Income" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowinvestmentinc" text="Investment Income" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowadjcurrentliability" text="Adjusted income over expenditure before effecting changes in current assets and current liabilities and extra ordinary items" /></td>
								<td class="text-right">${reportList.currentYearAmount}</td>
								<td class="text-right">${reportList.previousIncome}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowchangescurrentassets" text="Changes in current assets and current liabilities" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecsundry" text="(Increase)/decrease in Sundry debtors" /></td>
								<td class="text-right">${reportList.amounts[0]}</td>
								<td class="text-right">${reportList.prevusAmt[0]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecstock" text="(Increase)/decrease in Stock in hand" /></td>
								<td class="text-right">${reportList.amounts[1]}</td>
								<td class="text-right">${reportList.prevusAmt[1]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecprepaid" text="(Increase)/decrease in prepaid expenses" /></td>
								<td class="text-right">${reportList.amounts[2]}</td>
								<td class="text-right">${reportList.prevusAmt[2]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecother" text="(Increase)/decrease in other current assets" /></td>
								<td class="text-right">${reportList.amounts[3]}</td>
								<td class="text-right"></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecdepositsr" text="(Decrease)/increase in Deposits received" /></td>
								<td class="text-right">${reportList.amounts[7]}</td>
								<td class="text-right">${reportList.prevusAmt[7]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecdepositsworks" text="(Decrease)/increase in Deposits works" /></td>
								<td class="text-right">${reportList.amounts[8]}</td>
								<td class="text-right">${reportList.prevusAmt[8]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowdecincliabilities" text="(Decrease)/increase in other current liabilities" /></td>
								<td class="text-right">${reportList.amounts[9]}</td>
								<td class="text-right">${reportList.prevusAmt[9]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecprovisions" text="(Decrease)/increase in provisions" /></td>
								<td class="text-right">${reportList.amounts[10]}</td>
								<td class="text-right">${reportList.prevusAmt[10]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowextraordinary" text="Extraordinary items{please specify)" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<th><spring:message code="cashflownetcashA" text="Net cash generated from /(used in)  operating activities [A]" /></th>
								<c:set var="totalA" value="${0}"/>
								<c:set var="totalA" value="${totalA + reportList.currentYearAmount+reportList.amounts[0]+reportList.amounts[1]+reportList.amounts[2]+reportList.amounts[3]+reportList.amounts[7]+reportList.amounts[8]+reportList.amounts[9]+reportList.amounts[10]}" />
								<th class="text-right"><c:out value = "${totalA}"/></th>
								<c:set var="totalpA" value="${0}"/>
								<c:set var="totalpA" value="${totalpA + reportList.previousIncome+reportList.prevusAmt[0]+reportList.prevusAmt[1]+reportList.prevusAmt[2]+reportList.prevusAmt[3]+reportList.prevusAmt[7]+reportList.prevusAmt[8]+reportList.prevusAmt[9]+reportList.prevusAmt[10]}" />
								<th class="text-right"><c:out value = "${totalpA}"/></th>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<th><spring:message code="cashflowinvesting" text="[B] Cash flows from investing activities" /></th>
								<th></th>
								<th></th>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdeccwip" text="(Increase)/decrease of fixed assets & CWIP" /></td>
								<td class="text-right">${reportList.amounts[4]}</td>
								<td class="text-right">${reportList.prevusAmt[4]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecgrants" text="(Increase)/decrease in Special funds/grants" /></td>
								<td class="text-right">${reportList.amounts[11]}</td>
								<td class="text-right">${reportList.prevusAmt[11]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowincdecearmarked" text="(Increase)/decrease in Earmarked funds" /></td>
								<td class="text-right">${reportList.amounts[12]}</td>
								<td class="text-right">${reportList.prevusAmt[12]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowinvestment" text="(Increase)/decrease of Investments" /></td>
								<td class="text-right">${reportList.amounts[5]}</td>
								<td class="text-right">${reportList.prevusAmt[15]}</td>
							</tr>
							<%-- </c:forEach> --%>
							<tr>
								<td class="text-bold"><spring:message code="cashflowadd" text="Add:" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowproceeds" text="Proceeds from disposal of assets" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowdisposalinvest" text="Proceeds from disposal of investments" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowinsincome" text="Investment income received" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<th><spring:message code="cashflownetcash" text="Net cash generated from /(used in)  investing activities [B]" /></th>
								<c:set var="totalB" value="${0}"/>
								<c:set var="totalB" value="${totalB +reportList.amounts[4]+reportList.amounts[11]+reportList.amounts[12]+reportList.amounts[5]}" />
								<th class="text-right"><c:out value = "${totalB}"/></th>
								<c:set var="totalpB" value="${0}"/>
								<c:set var="totalpB" value="${totalpB +reportList.prevusAmt[4]+reportList.prevusAmt[11]+reportList.prevusAmt[12]+reportList.prevusAmt[5]}" />
								<th class="text-right"><c:out value = "${totalpB}"/></th>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<th><spring:message code="cashflowfinancialact" text="[c] Cash flows from financing activities" /></th>
								<th></th>
								<th></th>
							</tr>
							<tr>
								<td class="text-bold"><spring:message code="cashflowadd" text="Add:" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowgrantreceived" text="Grants Received" /></td>
								<td class="text-right"></td>
								<td class="text-right"></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowloanbanksreceived" text="Loans from banks/others received" /></td>
								<td class="text-right">${reportList.amounts[13]}</td>
								<td class="text-right">${reportList.prevusAmt[13]}</td>
							</tr>
							<tr>
								<td class="text-bold"><spring:message code="cashflowless" text="Less:" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowgrantrepaid" text="Grants Repaid"/></td>
								<td class="text-right"></td>
								<td class="text-right"></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowloansrepaid" text="Loans repaid during the period"/></td>
								<td class="text-right">${reportList.amounts[14]}</td>
								<td class="text-right">${reportList.prevusAmt[14]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowloansadvances" text="Loans & advances to employees" /></td>
								<td class="text-right">${reportList.amounts[6]}</td>
								<td class="text-right">${reportList.prevusAmt[6]}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowloansfinance" text="Loans to others Finance expenses" /></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<th><spring:message code="cashflownetcashc" text="Net cash generated from (used in) financing activities [c]" /></th>
								<c:set var="totalC" value="${0}"/>
								<c:set var="totalC" value="${totalC +reportList.amounts[13]+reportList.amounts[14]+reportList.amounts[6]}" />
								<th class="text-right"><c:out value = "${totalC}"/></th>
								<c:set var="totalpC" value="${0}"/>
								<c:set var="totalpC" value="${totalpC +reportList.prevusAmt[13]+reportList.prevusAmt[14]+reportList.prevusAmt[6]}" />
								<th class="text-right"><c:out value = "${totalpC}"/></th>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<th><spring:message code="cashflownetincdecequivalents" text="Net increase/ (decrease) in cash and cash equivalents (A + B + C)"/></th>
								<c:set var="totalABC" value="${0}"/>
								<c:set var="totalABC" value="${totalC +totalA+totalB}" />
								<th class="text-right"><c:out value = "${totalABC}"/></th>
								<c:set var="totalpABC" value="${0}"/>
								<c:set var="totalpABC" value="${totalpC +totalpA+totalpB}" />
								<th class="text-right"><c:out value = "${totalpABC}"/></th>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<th><spring:message code="cashflowbeginequivalants" text="Cash and cash equivalents at beginning of period" /></th>
								<th class="text-right">${reportList.openingBalance}</th>
								<th class="text-right">${reportList.openblancePreous}</th>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td class="text-bold"><spring:message code="cashflowendequivalents" text="Cash and cash equivalents at end of period" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowcashblnc" text="Cash and Cash equivalents at the end of the year comprises of the following account balances at the end of the year:" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><spring:message code="cashflowcashbalance" text="• Cash Balances" /></td>
								<td class="text-right">${reportList.cashBalance}</td>
								<td class="text-right">${reportList.cashBalanceP}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowbankbalances" text="• Bank Balances"/></td>
								<td class="text-right">${reportList.bankBalance}</td>
								<td class="text-right">${reportList.bankBalanceP}</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowscheduleco" text="• Scheduled co-operative banks"/></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowpostoffices" text="• Balances with Post offices"/></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<td><spring:message code="cashflowblncotherbank" text="• Balances with other banks"/></td>
								<td class="text-right">-</td>
								<td class="text-right">-</td>
							</tr>
							<tr>
								<th><spring:message code="cashflowbreakupcash" text="Total of the breakup of cash and cash equivalents at the end of the period"/></th>
								<c:set var="totalPeriod" value="${0}"/>
								<c:set var="totalPeriod" value="${totalPeriod +reportList.cashBalance+reportList.bankBalance}" />
								<th class="text-right"><c:out value = "${totalPeriod}"/></th>
								<c:set var="totalPeriodP" value="${0}"/>
								<c:set var="totalPeriodP" value="${totalPeriodP +reportList.cashBalanceP+reportList.bankBalanceP}" />
								<th class="text-right"><c:out value = "${totalPeriodP}"/></th>
							</tr>
						</tbody>
					</table>
					</div>
					</div>
					</div>
						<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('receipt');"
							class="btn btn-primary hidden-print" title="Print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AccountFinancialReport.html'" title="Back">
							<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
							<spring:message code="account.bankmaster.back" text="Back" />
						</button>

					</div>
				</form>
			</div>
		</div>
	</div>
	</div>					
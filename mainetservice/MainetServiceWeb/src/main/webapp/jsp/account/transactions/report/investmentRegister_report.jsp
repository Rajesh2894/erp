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
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>	
<script
	src="assets/libs/excel-export/excel-export.js"></script>	
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<script>
$(document).ready(function() {

	/* $("#importexcel").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	}); */
	
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
			size: 12
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
});

function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

</script>
<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="account.investment.register.title" text="Investment Register" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>

		<div class="widget-content padding">

			<div id = "investmentRegister">
				<form action="" method="get" class="form-horizontal"  >
				<div class="invReport" id = "invReport">

					<div class="form-group">
						<div
							class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-2  text-center text-bold">
							
							
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if>
									<br>
									<spring:message code="account.investment.register.title" text="Investment Register" />
								<span class="text-bold"><spring:message
									code="form.in" text="[Form IN-1]" /></span>
							</h3>
							<span class="text-bold">${command.accountInvestmentMasterDto.invstNo}</span><br>
							<span class="text-bold" >${command.accountInvestmentMasterDto.fundName}</span><br>
							<span class="text-bold" >${command.accountInvestmentMasterDto.invdate}
										${command.accountInvestmentMasterDto.invDueDate}
							</span>
							
						</div>

						<div class="col-sm-2 col-xs-12 pull-right-lg text-center">
							<p>
								<strong><spring:message code="acc.date" text="Date: " /></strong>
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<strong><spring:message code="swm.day.wise.month.report.time" text="Time  : " /></strong> 
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
					</div>
				
						<div class="" id="export-excel">
						<div class="table-responsive margin-top-10">
							<table class="table table-striped table-condensed table-bordered" id="importexcel">
									<div class="excel-title" id="tlExcel" style="display: none">
										<spring:message code="" text="Investment Register" />
									</div>
									<thead>
									<tr>
										<th data-sorter="false"><spring:message code="account.bankmaster.srno" text="Sr No." /></th>
										<th ><spring:message code="account.number.resolution.investment" text="Number And Date Of Resolution Authorising Investment" /></th>
										<th><spring:message code="account.date.of.investment" text="Date Of Investment" /></th>

										<th><spring:message code="account.Goverment.Paper.Or.FDR.Number.Of.The.Bank" text="Particulars Of Investment Quoting Number
																		And Date Of Goverment Paper Or FDR Number Of The Bank" /></th>
										<th  width = "8%"><spring:message code="account.purchase.price" text="Purchase Price(Rs.)/Face Value(Rs.)" /></th>
										<th  width = "18%" ><spring:message code="account.receipt.interest" text="Due Date Of Receipt Of Interest" /></th>
										<th><spring:message code="account.amount.interest.due.on" text="Amount Of Interest Due On" /></th>
										<th><spring:message code="account.initials.authorised.officer" text="Initials Of Authorised Officer" /></th>
										<th><spring:message code="account.amount.interest.recovered" text="Amount Of Interest Recovered (Rs.)" /></th>
										<th><spring:message code="account.interest.recovered" text="Date On Which Interest Recovered" /></th>
										<th><spring:message code="account.date.month.adjusted.accounts" text="Date/Month In Which Adjusted In Accounts" /></th>

										<th><spring:message code="account.released.sale.maturity" text="Amount Released Either On Sale Or Maturity Of Investment (Rs.)" /></th>
										<th><spring:message code="account.date.of.proceed" text="Date On Which Proceeds Were Relased" /></th>
										<th><spring:message code="account.date.month.adjustment.accounts" text="Date/ Month Of Adjustment In Accounts" /></th>
										<th><spring:message code="accounts.outstanding.bill.register.remarks" text="Remarks" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="data" items = "${command.acInvstDtoList}"  varStatus="loopCounter">
									<c:set var="e" value="0" scope="page" />
										<tr>
											<td align="center"> <c:out value="${loopCounter.count}"/>
											</td>
											<td align="center">${data.resNo}<br /><br /><fmt:formatDate value="${data.resDate}" type="date" pattern="dd-MM-yyyy"/></td>
											<td align="center">
											<fmt:formatDate value="${data.invstDate}" type="date" pattern="dd-MM-yyyy"/>
											 </td>
											<td align="center">${data.inFdrNo}</td>
											<td class="text-right">${data.invstAmount}</td>
										<td   width = "18%" align="center">	<fmt:formatDate value="${data.invstDueDate}" type="date" pattern="dd-MM-yyyy"/></td>
											
											<td class="text-right">${data.instAmt}</td>
											<td>${command.empName}</td>
										<td class="text-right">${data.rmAmount}</td>
											<td   width = "18%" align="center">	<fmt:formatDate value="${data.rmDate}" type="date" pattern="dd-MM-yyyy"/></td>
											<td   width = "18%" align="center">	<fmt:formatDate value="${data.rmDate}" type="date" pattern="dd-MM-yyyy"/></td>
											<td class="text-right">${data.rmAmount}</td>
											<td   width = "18%" align="center">	<fmt:formatDate value="${data.rmDate}" type="date" pattern="dd-MM-yyyy"/></td>
											<td   width = "18%" align="center">	<fmt:formatDate value="${data.rmDate}" type="date" pattern="dd-MM-yyyy"/></td>
											<td>${data.remarks}</td>
											
										</tr>
										<c:set var="e" value="${e + 1}" scope="page" />
									</c:forEach>
								
								</tbody>
                                 <tfoot class="tfoot">
										<tr>
											<th colspan="1" style="text-align: right"><spring:message
													code="mb.Total" text="Total:" /></th>
											<th></th>
											<th></th>
											<th></th>
											<th style="text-align: right">
												${command.accountInvestmentMasterDto.totPurchasePrice}</th>
											<th></th>
											<th style="text-align: right">
												${command.accountInvestmentMasterDto.totAmtInteresDue}</th>
											<th></th>
											<th style="text-align: right">
												${command.accountInvestmentMasterDto.totAmtInteresRecovered}
											</th>
											<th></th>
											<th></th>
											<th style="text-align: right">
												${command.accountInvestmentMasterDto.totAmtReleased}</th>
										</tr>
										<tr>
										<th colspan="15" class="ts-pager form-horizontal">
											<div class="btn-group">
												<button type="button" class="btn first">
													<i class="fa fa-step-backward" aria-hidden="true"></i>
												</button>
												<button type="button" class="btn prev">
													<i class="fa fa-arrow-left" aria-hidden="true"></i>
												</button>
											</div> <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
											<div class="btn-group">
												<button type="button" class="btn next">
													<i class="fa fa-arrow-right" aria-hidden="true"></i>
												</button>
												<button type="button" class="btn last">
													<i class="fa fa-step-forward" aria-hidden="true"></i>
												</button>
											</div> <select class="pagesize input-mini form-control"
											title="Select page size">
												<option selected="selected" value="5" class="form-control">5</option>
												<option value="12">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all">All Records</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>

							</table>
							</div>
								<div class = "pull-right">
									<br><br><br><br><br>
									<div><p><spring:message code="account.seal.signature.authorized.officer" text="Seal/Signature Of Authorised Officer"></spring:message></p></div>
								</div>
						</div>
					
					
					
				</div>
			</form>
			<style>
				@media print{
					.tfoot{
						visibility: hidden;
					}
					@page {
							margin: 15px;
						} 
				}
			  </style>
			</div>
								
	</div>
	
	<div class="clear text-center hidden-print padding-10">
				<%-- 	<button onclick="PrintDiv('${account.receivable.register}');" 
					 class="btn btn-primary hidden-print"
						data-toggle="tooltip" data-original-title="Print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button> --%>
					
					<button onclick="printContent('investmentRegister')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
					<button id="btnExport1" type="button"
							class="btn btn-blue-2 hidden-print" data-toggle="tooltip"
							data-original-title="Download">
							<i class="fa fa-file-excel-o"></i> <spring:message code="acounts.download"
							text="Download" />
					</button>
					<button type="button" class="btn btn-danger" onclick="back();"
						data-toggle="tooltip" data-original-title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
</div>
</div>

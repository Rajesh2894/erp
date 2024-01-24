
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
<apptags:breadcrumb></apptags:breadcrumb>
<%-- --%>


<script language="javascript">
	function printhiv(printpage) {

		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}

	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>

<script>
	$(document).ready(function() {

		/* $("#loanTableId").dataTable({
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
</script>



<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="register.of.Loans" text="Register of Loans" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>

		<div class="widget-content padding">

			<div id="reportTable">

				<form action="" method="get" class="form-horizontal">
					<div class="Lreport">

						<div class="form-group">
							<div
								class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-2  text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if>
									<br>
									<spring:message code="register.of.Loans" text="Register of Loans" />
								</h3>
							</div>

							<div class="col-sm-2 pull-right">
								<p>
									<strong><spring:message code="account.date" text="Date" /></strong> :
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br> <strong><spring:message
											code="swm.day.wise.month.report.time" text="Time  : " /></strong>
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
								<span class="text-bold"><spring:message code=""
										text="Form BR-1" /></span>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-6 margin-bottom-10">
								<p>
									<span class="text-bold"><spring:message code=""
											text="1.Department From Which Loan Recieved :" /></span>
									${command.accountLoanReportDto.lnDeptname}

								</p>

								<p>
									<span class="text-bold"><spring:message code=""
											text="2.Purpose Of Loan : " /></span>
									${command.accountLoanReportDto.lnPurpose }

								</p>

								<p>
									<span class="text-bold"><spring:message code=""
											text="3.No. & Date Of Resolution/Order Sanctioning The Loan :" /></span>${command.accountLoanReportDto.resNo}
									&
									<fmt:formatDate value="${command.accountLoanReportDto.resDate}"
										type="date" pattern="dd-MM-yyyy" />
									/ <span> ${command.accountLoanReportDto.sanctionNo} & <fmt:formatDate
											value="${command.accountLoanReportDto.sanctionDate}"
											type="date" pattern="dd-MM-yyyy" /></span>

								</p>

								<p>
									<span class="text-bold"><spring:message code=""
											text="4.Amount Of Loan Sanctioned : " /></span>${command.accountLoanReportDto.santionAmount }

								</p>
							</div>
							<div class="col-sm-6 margin-bottom-10">
								<p>

									<span class="text-bold"><spring:message code=""
											text="5.Rate Of Interest: " /></span>
									${command.accountLoanReportDto.lnInrate}
								</p>

								<p>

									<span class="text-bold"><spring:message code=""
											text="6.Number Of Installments" /></span> [
									${command.accountLoanReportDto.loanPeriodUnit } ] :
									${command.accountLoanReportDto.noOfInstallments }
								</p>

								<p>

									<span class="text-bold"><spring:message code=""
											text="7.Amount Of Each Installments : " /></span>${command.accountLoanReportDto.instAmt}

								</p>
								<p>

									<span class="text-bold"><spring:message code=""
											text="8.Remarks : " /></span>${command.accountLoanReportDto.lmRemark}

								</p>
							</div>

						</div>

					
		<div class="" id="export-excel">
							<div class="table-responsive margin-top-10">
								<table
									class="table table-striped table-condensed table-bordered"
									id="importexcel" class="loanTableId">  <!-- configurationId -->
									<div class="excel-title" id="tlExcel" style="display: none">
										<spring:message code="" text="Register of Loans" />
									</div>
									<thead border="1px solid white">
										<tr>
											<th colspan="3" class="text-center" width="8%"><spring:message
													code="" text="Receipt Of Loan" /></th>
											<th colspan="4" rowspan="1" class="text-center"><spring:message
													code="" text="Amount Due For Payment" /></th>
											<th rowspan="2" class="text-center"><spring:message
													code="" text="Initials Of the Officer" /></th>
											<th colspan="4" class="text-center"><spring:message
													code="" text="Amount Repaid" /></th>
											<th colspan="3" class="text-center"><spring:message
													code="" text="Balance" /></th>
										</tr>

										<tr>
											<th class="text-center" width="8%"><spring:message
													code="" text="Date Of Receipt" /></th>
											<th class="text-center"><spring:message code=""
													text="Amount Received" /></th>
											<th class="text-center"><spring:message code=""
													text="Total Amount Received" /></th>

											<th class="text-center"><spring:message code=""
													text="Due Date Of Repayment" /></th>
											<th class="text-center"><spring:message code=""
													text="Amount Of Principal" /></th>
											<th class="text-center"><spring:message code=""
													text="Amount Of Interest" /></th>
											<th class="text-center" width="8%"><spring:message code=""
													text="Total Amount Due To Repayment" /></th>

											<th class="text-center"><spring:message code=""
													text="Date Of Repayment" /></th>
											<th class="text-center"><spring:message code=""
													text="Principal Amount" /></th>
											<th class="text-center"><spring:message code=""
													text="Interest" /></th>
											<th class="text-center"><spring:message code=""
													text="Total" /></th>

											<th class="text-center"><spring:message code=""
													text="Principal Amount" /></th>
											<th class="text-center"><spring:message code=""
													text="Interest" /></th>
											<th class="text-center"><spring:message code=""
													text="Total" /></th>
										</tr>
										<tr>
											<th>1</th>
											<th>2</th>
											<th>3</th>
											<th>4</th>
											<th>5</th>
											<th>6</th>
											<th>7</th>
											<th>8</th>
											<th>9</th>
											<th>10</th>
											<th>11</th>
											<th>12</th>
											<th>13</th>
											<th>14</th>
											<th>15</th>

										</tr>
									</thead>
									<tfoot class="tfoot">
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
												<option value="12">12</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all">All Records</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<c:set var="new_total" value="0.00" />
									<tbody>
										<c:forEach var="data" items="${command.accountLoanReportDto.accountLoanReportDTOList}"  varStatus="loop">
									 	
											<tr>
								
												<td><fmt:formatDate value="${data.rmDate }" type="date"
														pattern="dd-MM-yyyy" /></td>
												<td class="text-right">${data.rmAmount }</td>
												<td class="text-right">
													<c:set var="new_total" value="${new_total + data.rmAmount}" />
													<c:choose>
														<c:when test="${not empty data.rmDate}" >
															${new_total}
														</c:when>
													<c:otherwise>
														
													</c:otherwise>
													</c:choose>
													
													
												
												</td>
												<td><fmt:formatDate value="${data.instDueDate }"
														type="date" pattern="dd-MM-yyyy" /></td>
												<td class="text-right">${data.prnpalAmount}</td>
												<td class="text-right">${data.intAmount}</td>
												<td class="text-right">${data.balIntAmt}</td>
												<td>${command.accountLoanReportDto.employeeName}</td>
												<td><fmt:formatDate value="${data.billEntryDate}" type="date" pattern="dd-MM-yyyy" /></td>
												<td class="text-right">
													<c:choose>
														<c:when test="${not empty data.billEntryDate}" >
															${data.prnpalAmount}
														</c:when>
													<c:otherwise>
														
													</c:otherwise>
													</c:choose>
												</td>
													<td class="text-right">
													<c:choose>
														<c:when test="${not empty data.billEntryDate}" >
															${data.intAmount}
														</c:when>
													<c:otherwise>
														
													</c:otherwise>
													</c:choose>
													</td>
												<td class="text-right">${data.billTotalAmount}</td>
												 <c:set var="total_prnpal" value="${data.prnpalAmount - data.prnpalAmount}" />
												<td class="text-right"> 
												<c:choose>
														<c:when test="${not empty data.billEntryDate}" >
															${total_prnpal}
														</c:when>
													<c:otherwise>
														${data.prnpalAmount}
													</c:otherwise>
													</c:choose>
												</td>
												<c:set var="total_interest" value="${data.intAmount - data.intAmount}" />
												
												
												
												<td class="text-right">
													<c:choose>
														<c:when test="${not empty data.billEntryDate}" >
															${total_interest}
														</c:when>
													<c:otherwise>
														${data.intAmount}
													</c:otherwise>
													</c:choose>
												
												
												
												</td>
												<c:set var="total_balance" value="${data.balIntAmt - data.billTotalAmount}" />
												<td class="text-right">${total_balance}</td>
											</tr>


										</c:forEach>
									</tbody>
									
								</table>

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

		<div class="text-center hidden-print padding-10">

			<button onclick="printContent('reportTable')"
				class="btn btn-primary hidden-print">
				<i class="fa fa-print"></i>
				<spring:message code="account.budgetestimationpreparation.print"
					text="Print" />
			</button>
			<button id="btnExport1" type="button"
				class="btn btn-blue-2 hidden-print" data-toggle="tooltip"
				data-original-title="Download">
				<i class="fa fa-file-excel-o"></i> <spring:message code="acounts.download"
					text="Print" />
			</button>
			<button type="button" class="btn btn-danger" onclick="back();"
				data-toggle="tooltip" data-original-title="Back">
				<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
				<spring:message code="solid.waste.back" text="Back" />
			</button>
		</div>

	</div>


</div>

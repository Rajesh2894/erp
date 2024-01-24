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
<!-- <link href="assets/css/style-green.css" rel="stylesheet" type="text/css"  id=""/> -->
<script
	src="assets/libs/excel-export/excel-export.js"></script>	
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<script>
$(document).ready(function() {

	/* $("#registerId").dataTable({
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
				<spring:message code="account.grant.register.title" text="Grant Register" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>

		<div class="widget-content padding">

			<div id ="grantRegister">
			<form:form  action="" method="get" class="form-horizontal">
				<div class="grtReport" id = "grtReport">
					<form:hidden path = "" id ="id1" value = "${command.faYearName}"></form:hidden>
					<div class="form-group">
						<div
							class="col-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if>
									<br>
									<spring:message code="account.grant.register.title" text="Grant Register" />
								
							</h3>
				
						</div>
									
							<div class="col-sm-2 col-xs-3 pull-right">
							<p>
								<strong><spring:message
                            code="account.date" text="Date" /></strong>
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<strong><spring:message code="swm.day.wise.month.report.time" text="Time  : " /></strong> 
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						
						
						<div
							class="col-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center text-bold">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								<span class="text-bold">${command.accountGrantMasterDto.grtName}</span><br>
							<span class="text-bold" >${command.faYearName}</span>
								
							</h3>
				
						</div>
						

					</div>
					<div id="export-excel">
						<div class="table-responsive margin-top-10">
							<table class="table table-striped table-condensed table-bordered" id="importexcel">
									<div class="excel-title" id="tlExcel" style="display: none">
										<spring:message code="" text="Grant Register" />
									</div>
									<thead>
									<tr>
									<th width = "12%"rowspan = "2" class="text-center" data-sorter="false"><spring:message
												code="account.bankmaster.srno" text="Sr No" /></th>
										<th width = "12%"rowspan = "2" class="text-center" ><spring:message
												code="account.Sanction.Details" text="Sanction Details" /></th>
										<th rowspan = "2" "><spring:message code="account.Nature.Of.Grant"
												text="Nature Of Grant" /></th>
										<th rowspan = "2" ><spring:message code="account.Period.Of.Grant"
												text="Period Of Grant" /></th>
										<th rowspan = "2" ><spring:message code="Sanction.Amount"
												text="Sanction Amount" /></th>
										<th colspan = "2"><spring:message code="account.grant.received.advance" text="Grant Received In Advance" /></th>
										<th colspan = "5"><spring:message code="account.Expenditure.Incurred.On.Specific.Grants" text="Expenditure Incurred On Specific Grants" /></th>
										<th rowspan = "2" ><spring:message code="account.Unutilized.Grants" text="Unutilized Grants" /></th>
										<th colspan = "2" ><spring:message code="account.refund.utilized" text="Refund Of Utilized Grants" /></th>
									</tr>

									<tr>

										<th><spring:message code="contingent.claim.bill.date" text="Date" /></th>
										<th><spring:message code="contingent.claim.bill.amount" text="Amount (Rs.)" /></th>
										<th><spring:message code="contingent.claim.bill.date" text="Date" /></th>
										<th><spring:message code="account.Voucher.Number"
												text="Voucher Number" /></th>

										<th><spring:message code="accounts.registerof.permanent.davance.natureof.expenditure" text="Nature Of Expenditure" /></th>
										<th><spring:message code="contingent.claim.bill.amount" text="Amount(Rs.)" /></th>
										<th><spring:message code="account.grant.Date.Of.Payment" text="Date Of Payment" /></th>
										<th><spring:message code="contingent.claim.bill.date" text="Date" /></th>
										<th><spring:message code="contingent.claim.bill.amount" text="Amount (Rs.)" /></th>
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
								<tbody>
								<!-- <tr> -->
									<c:choose>
										<c:when test="${fn:length(command.accountGrantMasterDtoList) > 0}">
										<c:forEach var="data" items = "${command.accountGrantMasterDtoList }" varStatus="count">
										<tr>
										   <td>${count.count}</td>
											<td>${data.grtNo}</td>
											<td>${data.grtNature}</td>
											<td class = "text-center">
												${data.fromYear} <br>To<br> ${data.toYear}
											</td>
											<td class="text-right">${data.santionAmt}</td>
											<td>
											 <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.adavancedGrandDetail}"
														var="data1">

														<tr>
														<td width="40%">${data1[0]}</td>
															
														</tr>

													</c:forEach>
												</table>
											</td>
											<td>
											 <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.adavancedGrandDetail}"
														var="data1">

														<tr>
														<td width="40%">${data1[1]}</td>
															
														</tr>

													</c:forEach>
												</table>
											</td>
											
											 <td>
											 <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.paymentList}"
														var="data1">

														<tr>
														<td width="40%">${data1.paymentEntryDate}</td>
															
														</tr>

													</c:forEach>
												</table>
											</td>
											<td>
											 <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.paymentList}"
														var="data1">

														<tr>
															<td width="40%">${data1.paymentNo}</td>
														</tr>

													</c:forEach>
												</table>
											</td>
											<td>
											  <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.paymentList}"
														var="data1">

														<tr>
															<td width="40%">${data1.narration}</td>
														</tr>

													</c:forEach>
												</table>
											
											</td>
											<td>
											 <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.paymentList}"
														var="data1">

														<tr>
															<td width="40%">${data1.paymentAmount}</td>
														</tr>

													</c:forEach>
												</table>
											</td>
											<td>
											 <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.paymentList}"
														var="data1">

														<tr>
														<td width="40%">${data1.paymentEntryDate}</td>
															
														</tr>

													</c:forEach>
												</table>
											
											</td>
											<td> <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.billDetailAgainstUtilize}"
														var="data1">

														<tr>
														<td width="40%">${data1[0]}</td>
															
														</tr>

													</c:forEach>
												</table>
												
												</td>
											<td> <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.billDetailAgainstRefund}"
														var="data1">

														<tr>
														<td width="40%">${data1[1]}</td>
															
														</tr>

													</c:forEach>
												</table>
												</td>
											<td> <table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${data.billDetailAgainstRefund}"
														var="data1">

														<tr>
														<td width="40%">${data1[0]}</td>
															
														</tr>

													</c:forEach>
												</table>
												</td>
										</tr>
										</c:forEach>
										
										</c:when>
										<c:otherwise>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										</c:otherwise>
									</c:choose>
									<!-- </tr> -->
								</tbody>
							</table>
							</div>
							</div>
					
					
				</div>
			</form:form>
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
	<!-- onclick="PrintDiv('${account.receivable.register}');"   onclick="back();"-->
	
	<div class="text-center hidden-print padding-10">
					<%-- <button 
					 class="btn btn-primary hidden-print"
						data-toggle="tooltip" data-original-title="Print"
						 onclick="PrintDiv('${account.receivable.register}');"
						>
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button> --%>
					
					
					<button onclick="printContent('grantRegister')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
			<button id="btnExport1" type="button"
				class="btn btn-blue-2 hidden-print" data-toggle="tooltip"
				data-original-title="Download">
				<i class="fa fa-file-excel-o"></i>	<spring:message code="acounts.download" text="Download" />
			</button>
			<button type="button" class="btn btn-danger"
						data-toggle="tooltip"  onclick="back();" data-original-title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
</div>
</div>


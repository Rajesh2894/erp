<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/invoiceEntry.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript">
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
<style>
	.message {
		font-size: 12px;
		font-weight: bold;
	}
	
	.time-date {
		font-size: 12px;
	}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.management.invoice" text="Invoice" />
			</h2>
		</div>
		<div class="widget-content padding" id="receipt">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt" class="receipt-content">
					<div class="col-xs-2">
						<h1>
							<img width="80" src="${userSession.orgLogoPath}">
						</h1>
					</div>
					<div class="col-xs-8 col-xs-8  text-center">
						<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<c:if test="${userSession.languageId eq 1}">
								${ userSession.getCurrent().organisation.ONlsOrgname}<br />
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br />
							</c:if>
							  <br />
						</h2>
						<p>
							<spring:message code="material.management.invoice" text="Invoice" />
						</p>
					</div>

					<div class="col-xs-2">
						<br><span class="message">
							<spring:message code="material.management.date" text="Date" />
						</span>
						<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" var="formattedDate" />
						<span class="time-date">${formattedDate}</span>
						<br>
						<span class="message">
							<spring:message code="material.management.time" text="Time" />
						</span>
						<fmt:formatDate value="${date}" pattern="hh:mm a" var="formattedTime" />
						<span class="time-date">${formattedTime}</span>
					</div>
					<%-- <input type="hidden" value="${validationError}" id="errorId"> --%>
					<div class="clearfix padding-10"></div>
					<!-- <div class="overflow-visible"> -->
					<div id="export-excel">
						<!-- <div class="overflow-visible"> -->
						<table class="table table-bordered table-condensed">
							<tbody>
								<tr>
									<th style="text-align: left;"><spring:message code="store.master.name"
											text="Store Name" /></th>
									<td colspan="1">${command.invoiceEntryDTO.storeName}</td>
								</tr>							
								
								<tr>
									<th style="text-align: left;"><spring:message code="material.management.invoice.number"
											text="Invoice Number" /></th>
									<td colspan="1">${command.invoiceEntryDTO.invoiceNo}</td>

									<th style="text-align: left;"><spring:message code="material.management.invoice.date"
											text="Invoice Date" /></th>
									<c:choose>
										<c:when test="${0 eq command.levelCheck}">
											<td colspan=""><fmt:parseDate var="parsedDate" value="${command.invoiceEntryDTO.invoiceDate}"
												pattern="E MMM dd HH:mm:ss z yyyy" />
											<fmt:formatDate pattern="dd/MM/yyyy" value="${parsedDate}" /></td>
										</c:when>
										<c:otherwise>
											<td><fmt:parseDate var="parsedDate" value="${command.invoiceEntryDTO.invoiceDate}" 
												pattern="yyyy-MM-dd HH:mm:ss.S" />
												<fmt:formatDate pattern="dd/MM/yyyy" value="${parsedDate}" /></td>
										</c:otherwise>
									</c:choose>
								</tr>
								
								<tr>
									<th style="text-align: left; column-span: 2"><spring:message
											code="purchase.requisition.purOrderNo" text="Purchase Order No." /></th>
									<td colspan="1">${command.invoiceEntryDTO.poNumber}</td>
									
									<th style="text-align: left;"><spring:message
											code="purchase.requisition.vendorName" text="Vendor Name" /></th>
									<td colspan="1">${command.invoiceEntryDTO.vendorName}</td>
								</tr>
							</tbody>
						</table>
						
						<h4>
							<spring:message code="material.management.grn.details" text="GRN Details" />
						</h4>
						<div class="panel-default">
							<div class="panel-collapse collapse in" id="UnitDetail">
								<div class=" clear padding-8">
									<c:set var="d" value="0" scope="page" />
									<table id="unitDetailTable"
										class="table table-striped table-bordered appendableClass unitDetail">
										<thead>
											<tr>
												<th width="10%"><spring:message code="store.master.srno" text="Sr.No." /></th>
												<th><spring:message code="material.management.grnNo" text="GRN No." /></th>
												<th><spring:message code="material.management.grnDate" text="GRN Date" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.invoiceEntryDTO.invoiceEntryGRNDTOList}" var="data" varStatus="index">
												<tr class="firstUnitRow">
													<td align="center" colspan="1">${d+1}</td>
													<td align="center">${data.grnNumber}</td>
													<td align="center"><fmt:parseDate var="parsedDate" value="${data.grnDate}"
															pattern="E MMM dd HH:mm:ss z yyyy" />
														<fmt:formatDate pattern="dd/MM/yyyy" value="${parsedDate}" /></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						
						<h4>
							<spring:message code="material.item.master.itemDetails" text="Item Details" />
						</h4>
						<div class="panel-default">
							<div class="panel-collapse collapse in" id="UnitDetail">
								<div class=" clear padding-8">
									<c:set var="d" value="0" scope="page" />
									<table id="unitDetailTable"
										class="table table-striped table-bordered appendableClass unitDetail">
										<thead>
											<tr>
												<th><spring:message code="store.master.srno" text="Sr.No." /></th>
												<th><spring:message code="material.item.master.name" text="Item Name" /></th>
												<th><spring:message code="store.master.UoM" text="UoM" /></th>
												<th><spring:message code="material.management.acceptedQuantity" 
														text="Accepted Quantity" /></th>
												<th><spring:message code="purchase.requisition.unitPrice" 
														text="Unit Price" /></th>
												<th><spring:message code="material.management.invoice.total.amount" 
														text="Total Amount" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.invoiceEntryDTO.invoiceEntryDetailDTOList}" var="data" varStatus="index">
												<tr class="firstUnitRow">
													<td align="center" width="10%">${d+1}</td>
													<td align="center" style="width: 20%">${data.itemName}</td>
													<td align="center" style="width: 15%">${data.uomName}</td>
													<td align="center" style="width: 15%">${data.quantity}</td>
													<td align="center" width="20%">${data.unitPrice}</td>
													<td align="center" width="20%">${data.totalAmt}</td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						
						<h4>
							<spring:message code="material.management.deductions" text="Deductions" />
						</h4>
						<div class="panel-default">
							<div class="panel-collapse collapse in" id="UnitDetail">
								<div class=" clear padding-8">
									<c:set var="d" value="0" scope="page" />
									<table id="unitDetailTable"
										class="table table-striped table-bordered appendableClass unitDetail">
										<thead>
											<tr>
												<th width="10%"><spring:message code="store.master.srno" text="Sr.No." /></th>
												<th><spring:message  code="material.management.deductions" text="Deductions" /></th>
												<th><spring:message code="material.management.decision" text="Decision" /></th>
												<th><spring:message code="purchase.requisition.Amt" text="Amount" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.invoiceEntryDTO.invoiceOverheadsDTOList}" var="data" varStatus="index">
												<tr class="firstUnitRow">
													<td align="center">${d+1}</td>
													<td align="center">${data.description}</td>
													<td align="center"><%-- ${data.overheadType} --%><spring:message code=""
														text="Deduction" /></td>
													<td align="center">${data.amount}</td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="text-center padding-10"></div>
						<div class="text-center padding-10"></div>
						<div class="text-right padding-10">
							<spring:message code="vehicle.fuelling.print.footer" />
						</div>
					</div>
					
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
						    .time-date{font-size: 12px;}
	 					}
	 				</style>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('receipt');"
						class="btn btn-primary hidden-print" type="button">
						<i class="fa fa-print"></i>
						<spring:message code="material.management.print" text="Print" />
					</button>
					<c:choose>
						<c:when test="${0 ne command.levelCheck}">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>										
						</c:when>
						<c:otherwise>
							<apptags:backButton url="StoreInvoiceEntry.html"></apptags:backButton>				
						</c:otherwise>
					</c:choose>
				</div>
			</form>
		</div>
	</div>
</div>
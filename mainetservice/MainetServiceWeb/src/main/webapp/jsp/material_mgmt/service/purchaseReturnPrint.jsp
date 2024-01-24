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
<script type="text/javascript" src="js/material_mgmt/service/purchaseReturn.js"></script>
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
				<spring:message code="material.management.purchaseReturn" text="Purchase Return" />
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
							<spring:message code="material.management.purchaseReturn" text="Purchase Return" />
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

					<div class="clearfix padding-10"></div>
					<div id="export-excel">
						<!-- <div class="overflow-visible"> -->
						<table class="table table-bordered table-condensed">
							<tbody>
								<tr>
									<th style="text-align: left;"><spring:message code="material.management.purchaseReturnNo"
											text="Purchase Return No." /></th>
									<td colspan="1">${command.purchaseReturnDto.returnNo}</td>
									
									<th style="text-align: left;"><spring:message code="material.management.purchase.return.date"
											text="Purchase Return Date" /></th>
									<td colspan=""><fmt:parseDate var="parsedDate" value="${command.purchaseReturnDto.returnDate}"
										pattern="E MMM dd HH:mm:ss z yyyy" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${parsedDate}" /></td>									
								</tr>
								
								<tr>
									<th style="text-align: left;"><spring:message code="material.management.grnNo"
											text="GRN No." /></th>
									<td colspan="1">${command.purchaseReturnDto.grnNo}</td>

									<th style="text-align: left;"><spring:message code="material.management.grnDate"
											text="GRN Date" /></th>
									<td colspan="">${command.purchaseReturnDto.grnDate}</td>
								</tr>
								
								<tr>
									<th style="text-align: left; column-span: 2"><spring:message
											code="purchase.requisition.purOrderNo" text="Purchase Order No." /></th>
									<td colspan="1">${command.purchaseReturnDto.poNo}</td>
									
									<th style="text-align: left;"><spring:message
											code="purchase.requisition.vendorName" text="Vendor Name" /></th>
									<td colspan="1">${command.purchaseReturnDto.vendorName}</td>
								</tr>
							</tbody>
						</table>
						<h4>
							<spring:message code="material.management.purchase.return.items"
								text="Return Item Details" />
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
												<th><spring:message code="store.master.uom" text="UoM" /></th>
												<th><spring:message code="material.management.rejectedQuantity"
														text="Rejected Quantity" /></th>
												<th><spring:message code="purchase.requisition.bill.remarks"
														text="Remarks" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.purchaseReturnDto.purchaseReturnDetDtoList}" var="data" varStatus="index">
												<tr class="firstUnitRow">
													<td align="center" width="10%">${d+1}</td>
													<td align="center" style="width: 25%">${data.itemName}</td>
													<td align="center" style="width: 20%">${data.uomName}</td>
													<td align="center" style="width: 20%">${data.quantity}</td>
													<td align="center" width="25%">${data.rejectionRemark}</td>
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
					<apptags:backButton url="PurchaseReturn.html"></apptags:backButton>
				</div>
			</form>
		</div>
	</div>
</div>
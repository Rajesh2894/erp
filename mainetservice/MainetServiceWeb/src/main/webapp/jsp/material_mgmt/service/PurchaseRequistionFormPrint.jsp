<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript" src="js/material_mgmt/service/purRequisition.js"></script>
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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="purchase.requisition.fomr.heading" text="Purchase Requisition" />
			</h2>
		</div>
		<div class="widget-content padding" id="receipt">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
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
							<spring:message code="purchase.requisition.fomr.heading" text="Purchase Requisition" />
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
					
					<input type="hidden" value="${validationError}" id="errorId">
					<div class="clearfix padding-10"></div>
					<div id="export-excel">
						<table class="table table-bordered table-condensed">
							<tbody>
								<tr>
									<th style="text-align: left;"><spring:message code="purchase.requisition.prno" text="PR.No" /></th>
									<td colspan="3">${command.purchaseRequistionDto.prNo}</td>

								</tr>
								<tr>
									<th style="text-align: left;"><spring:message
											code="store.master.name" text="Store Name" /></th>
									<td colspan="1">${command.purchaseRequistionDto.storeName}</td>

									<th style="text-align: left;"><spring:message
											code="purchase.requisition.reqDate" text="Requisition Date:" /></th>
									<td colspan="1"><fmt:formatDate pattern="dd/MM/yyyy"
											value="${command.purchaseRequistionDto.prDate}" /></td>
								</tr>
								<tr>
									<th style="text-align: left;"><spring:message code="store.master.store.incharge"
											text="Store Incharge" /></th>
									<td colspan="1">${command.purchaseRequistionDto.departmentName}</td>

									<th style="text-align: left;"><spring:message code="store.master.location"
											text="Location" /></th>
									<td colspan="1">${command.purchaseRequistionDto.requestedName}</td>
								</tr>
							</tbody>
						</table>
						<h4>
							<spring:message code="material.item.master.itemDetails"
								text="Item Details" />
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
												<th><spring:message code="store.master.uom" text="Uom" /></th>
												<th><spring:message code="store.master.quantity" text="Quantity" /></th>
												<th><spring:message code="material.item.master.tax" text="Tax" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.purchaseRequistionDto.purchaseRequistionDetDtoList}"
												var="data" varStatus="index">
												<tr class="firstUnitRow">
													<td align="center" width="20%">${d+1}</td>
													<td align="center" style="width: 20%">${data.itemName}</td>
													<td align="center" style="width: 20%">${data.uonName}</td>
													<td align="center" style="width: 20%">${data.quantity}</td>
													<td align="center" width="20%"></td>
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
					<button onclick="PrintDiv('receipt');" class="btn btn-primary hidden-print" type="button">
						<i class="fa fa-print"></i><spring:message code="material.management.print" text="Print" />
					</button>
					<button type="button" class="button-input btn btn-danger" name="button-Cancel" 
						value="Cancel" onclick="backPurReqForm();" id="button-Cancel">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
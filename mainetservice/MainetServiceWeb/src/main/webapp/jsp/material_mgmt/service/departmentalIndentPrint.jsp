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
<script src="js/material_mgmt/master/indentProcess.js"
	type="text/javascript"></script>
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

	function PrintDiv(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
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
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="department.indent.details" text="Indent Details" />
			</h2>
		</div>
		<div class="content animated slideInDown">
			<div class="widget">
				<div class="widget-content padding">
					<form:form action="IndentProcess.html" method="get"
						class="form-horizontal">
						<div id="receipt" class="receipt-content">
							<div id="receipt">

								<div class="col-xs-2">
									<h1>
										<img width="80" src="${userSession.orgLogoPath}">
									</h1>
								</div>
								<div class="col-xs-8 col-xs-8  text-center">
									<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
		                              		${ userSession.getCurrent().organisation.ONlsOrgname}<br>
											</c:when>
											<c:when test="${userSession.languageId eq 2}">
											${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
											</c:when>
										</c:choose>
									</h2>
									<p>
										<spring:message code="department.indent.details" text="Indent Details" />
									</p>
								</div>
								<div class="col-xs-2">
									<br>
									<span class="message"> <spring:message code="material.management.date" text="Date" /></span>
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" var="formattedDate" />
									<span class="time-date">${formattedDate}</span>
									<br> 
									<span class="message"> <spring:message code="material.management.time" text="Time" /></span>
									<fmt:formatDate value="${date}" pattern="hh:mm a" var="formattedTime" />
									<span class="time-date">${formattedTime}</span>
								</div>
								<div class="clear"></div>
								<div class="clear"></div>

								<div id="export-excel">
									<table class="table table-bordered table-condensed">
										<tbody>
											<tr>
												<th style="text-align: left;"><spring:message
														code="store.master.name" text="Store Name" /></th>
												<td>${command.indentProcessDTO.storeDesc}</td>
											</tr>
											<tr>
												<th style="text-align: left; column-span: 2"><spring:message
														code="department.indent.no" text="Indent No." /></th>
												<td>${command.indentProcessDTO.indentno}</td>

												<th style="text-align: left;"><spring:message 
														code="department.indent.date" text="Indent Date" /></th>
												<td><fmt:parseDate var="parsedDate"
														value="${command.indentProcessDTO.indentdate}"
														pattern="E MMM dd HH:mm:ss z yyyy" /> <fmt:formatDate
														pattern="dd/MM/yyyy" value="${parsedDate}" /></td>
											</tr>
											<tr>
												<th style="text-align: left; column-span: 2"><spring:message
														code="department.indent.indentor.name" text="Indenter Name" /></th>
												<td>${command.indentProcessDTO.indenterName}</td>

												<th style="text-align: left;"><spring:message code="material.management.receivedDate"
														text="Received Date" /></th>
												<td>${command.indentProcessDTO.issuedDate}<fmt:parseDate
														var="parsedDate"
														value="${command.indentProcessDTO.issuedDate}"
														pattern="E MMM dd HH:mm:ss z yyyy" /> <fmt:formatDate
														pattern="dd/MM/yyyy" value="${parsedDate}" /></td>
											</tr>
										</tbody>
									</table>

									<h4>
										<spring:message code="department.indent.item.details" text="Indent Item Details" />
									</h4>
									<div class="panel-default">
										<div class="panel-collapse collapse in" id="UnitDetail">
											<div class=" clear padding-8">
												<c:set var="d" value="0" scope="page" />
												<table id="unitDetailTable"
													class="table table-striped table-bordered appendableClass unitDetail">
													<thead>
														<tr>
															<th width="10%"><spring:message
																	code="store.master.srno" text="Sr.No." /></th>
															<th><spring:message code="material.item.master.name"
																	text="Item Name" /></th>
															<th width="20%"><spring:message
																	code="store.master.uom" text="UoM" /></th>
															<th width="20%"><spring:message
																	code="department.indent.requested.quantity"
																	text="Requested Quantity" /></th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${command.indentProcessDTO.item}"
															var="data" varStatus="index">
															<tr class="firstUnitRow">
																<td align="center" colspan="1">${d+1}</td>
																<td align="center">${data.itemName}</td>
																<td align="center">${data.uom}</td>
																<td align="center">${data.quantity}</td>
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
									<c:when test="${ 0 eq command.levelcheck}">
										<apptags:backButton url="IndentProcess.html"></apptags:backButton>
									</c:when>
									<c:otherwise>
										<apptags:backButton url="AdminHome.html"></apptags:backButton>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

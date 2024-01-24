<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/material_mgmt/service/expiryItems.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="material.exp.disposalofStock"
						text="Disposal of Stock" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form:form action="#" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-7 col-xs-7  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								${ userSession.getCurrent().organisation.ONlsOrgname} <br>

							</h2>
							<p>
								<spring:message code="material.exp.disposalofStock"
									text="Disposal of Stock" />
							</p>
						</div>
						<div class="col-xs-3">
							<p>
								<br>
								<spring:message code="material.management.date" text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="material.management.time" text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>

						<input type="hidden" value="${validationError}" id="errorId">
						<div class="clearfix padding-10"></div>
						<!-- <div class="overflow-visible"> -->
						<div id="export-excel">
							<table class="table table-bordered table-condensed">
								<tr>
									<th style="text-align: left"><spring:message
											code="material.expired.item.entry.movement.no"
											text="Movement No" /></th>
									<td width="20%" align="left">${command.expiryItemsDto.movementNo}</td>

									<th style="text-align: left"><spring:message
											code="material.expired.item.entry.movement.date"
											text="Movement Date" /></th>
									<td width="20%" align="left">${command.expiryItemsDto.movementDate}</td>

								</tr>
								<tr>
									<th style="text-align: left"><spring:message
											code="material.expired.item.entry.store" text="Store" /></th>
									<td align="left">${command.expiryItemsDto.storeName}</td>

									<th style="text-align: left"><spring:message
											code="material.expired.item.entry.movement.by"
											text="Movement By" /></th>
									<td align="left">${command.expiryItemsDto.movementByName}</td>
								</tr>
								<tr>
									<th style="text-align: left"><spring:message
											code="material.expired.item.entry.expiry.on.or.before.date"
											text="Expiry on or before Date" /></th>
									<td align="left"><fmt:formatDate
											pattern="dd/MM/yyyy" value="${command.expiryItemsDto.expiryCheck}" /> </td>
								</tr>
							</table>
							<div class="panel-default">
								<div class="panel-collapse collapse in" id="UnitDetail">
									<div class=" clear padding-8">
										<c:set var="d" value="0" scope="page" />
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th width="10%"><spring:message
															code="store.master.srno" text="Sr.No." /></th>
													<th width="20%"><spring:message
															code="binLocMasDto.binLocation" text="Bin Location" /></th>
													<th width="15%"><spring:message
															code="material.expired.item.entry.item.description"
															text="item Description" /></th>
													<th width="15%"><spring:message
															code="material.expired.item.entry.uom" text="UoM" /></th>
													<th width="20%"><spring:message
															code="material.exp.batchNoSerialNo"
															text="Batch No/ Serial No" /></th>
													<th width="20%"><spring:message
															code="material.exp.expiredQty" text="Expired Qty" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach
													items="${command.expiryItemsDto.expiryItemDetailsDtoList}"
													var="data" varStatus="index">
													<tr class="firstUnitRow">
														<td align="center" width="10%">${d+1}</td>
														<td align="center" width="10%">${data.binLocName}</td>
														<td align="center" style="width: 20%">${data.itemName}</td>
														<td align="center" width="10%">${data.uomName}</td>
														<td align="center" width="10%">${data.transactionId}</td>
														<td align="center" width="10%">${data.quantity}</td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</tbody>
										</table>

									</div>
								</div>
							</div>
						</div>
						<!-- </div> -->
						<div class="text-center padding-10"></div>
						<div class="text-center padding-10"></div>
						<div class="text-center padding-10"></div>
						<div class="text-center padding-10"></div>
						<div class="text-right padding-10">
							<spring:message code="vehicle.fuelling.print.footer" />
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv();" class="btn btn-success hidden-print"
							type="button">
							<i class="fa fa-print"></i>
							<spring:message code="material.management.print" text="Print" />
						</button>

						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backForm();" id="button-Cancel">
							<spring:message code="material.management.back" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
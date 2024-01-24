<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/VehicleFuelling.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="vehicle.fuelling.heading"
						text="Vehicle Fueling" />
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
								<spring:message code="vehicle.fuelling.heading"
									text="Vehicle Fueling" />
							</p>
						</div>
						<div class="col-xs-3">
							<p>
								<br>
								<spring:message code="swm.day.wise.month.report.date"
									text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="swm.day.wise.month.report.time"
									text="Time" />
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
											code="vehicle.fuelling.pump.name" /></th>
									<td width="20%" align="left">${vehicleFuellingDTO.puPumpname}</td>
									<th style="text-align: left"><spring:message
											code="vehicle.master.vehicle.vendor.name" /></th>
									<td width="20%" align="left">${vehicleFuellingDTO.vName}</td>

								</tr>
								<tr>
									<th style="text-align: left"><spring:message
											code="vehicle.fuelling.adviceno" /></th>
									<td align="left">${vehicleFuellingDTO.vefDmno}</td>
									<th style="text-align: left"><spring:message
											code="vehicle.fuelling.adviceDate" /></th>
									<td align="left"><c:set var="vefDmdate"
											value="${vehicleFuellingDTO.vefDmdate}"></c:set> <fmt:formatDate
											value="${vefDmdate}" pattern="dd/MM/yyyy" /></td>
								</tr>
								<tr>
									<th style="text-align: left"><spring:message
											code="vehicle.maintenance.regno" /></th>
									<td align="left">${vehicleFuellingDTO.veNo}</td>
									<th style="text-align: left"><spring:message
											code="vehicle.fuelling.driverName" /></th>
									<td align="left">${vehicleFuellingDTO.driverName}</td>
								</tr>
							</table>
							<div class="text-left padding-10 form-group">
								<spring:message code="vehicle.fuelling.print.vendorIdentity" />${ userSession.getCurrent().organisation.ONlsOrgname}
							</div>
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th width="10%"><spring:message
												code="vehicle.maintenance.master.id" /></th>
										<th width="30%"><spring:message
												code="vehicle.fuelling.itemSold" />(<spring:message
												code="vehicle.fuelling.unit" />)</th>
										<th width="20%"><spring:message
												code="vehicle.fuelling.quantity" /></th>
										<th width="20%"><spring:message
												code="vehicle.fuelling.cost" /></th>
										<th width="20%"><spring:message
												code="vehicle.fuelling.totalCost" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th width="10%"></th>
										<th width="30%"></th>
										<th width="20%"></th>
										<th width="20%" align="right"><span
											style="font-weight: bold"><spring:message
													code="swm.total" text="Total" /></span></th>
										<th width="20%" align="center"><div align="center"
												class="input-group">${vehicleFuellingDTO.vefRmamount}</div></th>

									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${vehicleFuellingDTO.tbSwVehiclefuelDets}"
										var="item" varStatus="status">
										<c:if
											test="${item.itemDesc ne null && item.vefdUnitDesc ne null}">
											<tr>

												<td align="center" width="5%">${status.count}</td>
												<td>${item.itemDesc}(${item.vefdUnitDesc})</td>
												<td>${item.vefdQuantity}</td>
												<td>${item.vefdCost}</td>
												<td>${item.multi}</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
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
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>
							<spring:message code="solid.waste.print" text="Print" />
						</button>

						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backVehicleFuellingForm();" id="button-Cancel">
							<spring:message code="solid.waste.back" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
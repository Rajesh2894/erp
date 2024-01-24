<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/RefuelingAdvice.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
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

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="fuelling.advice.heading"></spring:message>
			</h2>
		</div>
		<!-- Start JSP Necessary Tags -->

		<div class="content animated slideInDown">
			<!-- Start info box -->
			<div class="widget">

				<div class="widget-content padding">

					<form:form action="RefuelingAdvice.html" name="RefuelingAdvice"
						id="RefuelingAdviceForm" class="form-horizontal">
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
									<spring:message code="fuelling.advice.heading"
										text="Refueling Advice Reconcilation" />
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
							
							<div class="clear"></div>
							<div class="clear"></div>

							<c:set var="inrecdInvdate"
								value="${vehicleFuelReconciationDTO.inrecdInvdate}"></c:set>

							<div class="form-group">

								<label class="control-label col-xs-2 col-xs-2 " for="Census"><spring:message
										code="vehicle.fuelling.pump.name" text="Pump Name" /></label>

								<div class="col-xs-4 col-xs-4">${vehicleFuelReconciationDTO.puPumpname}</div>

								<label class="control-label col-xs-2 col-xs-2 " for="Census"><spring:message
										code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
								<div class="col-xs-4 col-xs-4">${vehicleFuelReconciationDTO.puVendorname}</div>
							</div>

							<div class="form-group">

								<label class="control-label col-xs-2 col-xs-2 " for="Census"><spring:message
										code="vehicle.maintenance.receiptno" text="Pump Name" /></label>

								<div class="col-xs-4 col-xs-4">${vehicleFuelReconciationDTO.inrecdInvno}</div>

								<label class="control-label col-xs-2 col-xs-2 " for="Census"><spring:message
										code="vehicle.maintenance.receiptDate" text="Vendor Name" /></label>
								<div class="col-xs-4 col-xs-4">
									<fmt:formatDate value="${inrecdInvdate}" pattern="dd/MM/yyyy" />
								</div>
							</div>
							<table class="table table-bordered ">
								<%-- table-condensed --%>
								<thead>
									<tr>
										<th><spring:message code="fuelling.advice.suppyDate" /></th>
										<th><spring:message code="vehicle.fuelling.adviceno" /></th>
										<th><spring:message code="fuelling.advice.itemSold" /></th>
										<th><spring:message
												code="vehicle.maintenance.master.type" /></th>
										<th><spring:message code="vehicle.master.vehicle.no" /></th>
										<th><spring:message code="fuelling.advice.driverName" /></th>
										<th><spring:message code="fuelling.advice.rate" /></th>
										<th><spring:message code="fuelling.advice.quantity" /></th>
										<th><spring:message code="fuelling.advice.totalAmount" /></th>
									</tr>
								</thead>
								<tbody>
									<c:set var="totalCost" value="0"></c:set>
									<c:forEach items="${vehicleFuelReconcilationList}" var="item"
										varStatus="status">
										<c:if test="${item.puFuid ne null}">
										<tr>
											<td><c:set var="adviceDate" value="${item.adviceDate}"></c:set>
												<fmt:formatDate value="${adviceDate}" pattern="dd/MM/yyyy" />

											</td>
											<td>${item.adviceNumber}</td>											
											<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.puFuid)" var="lookup"/>${lookup.lookUpDesc}</td>
																						
											<td><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.veVetype)"
													var="lookup" />${lookup.lookUpDesc}</td>
											<td>${item.veNo}</td>
											<td>${item.driverName}</td>
											<td class="text-right" ><c:set var="vefdCost" value="${item.vefdCost}"></c:set>
												${vefdCost}</td>
											<td class="text-right" ><c:set var="vefdQuantity"
													value="${item.vefdQuantity}"></c:set> ${vefdQuantity}</td>
											<td class="text-right">
											<c:set var="amount" value="${item.sumOfAmount}"></c:set>
												${amount} <c:set var="totalCost" value="${totalCost+amount}"></c:set>
											
												</td>
										</tr>
										</c:if>
									</c:forEach>
								</tbody>

								<tfoot>
									<tr>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th align="right"><span style="font-weight: bold"><spring:message
													code="swm.total" text="Total" /></span></th>
										<th class="text-right">
											<div class="text-right" class="input-group">
												<c:out value="${totalCost}" />
											</div>
										</th>
									</tr>
								</tfoot>
							</table>
							<div class="text-center padding-10"></div>
							<div class="text-center padding-10"></div>
							<div class="text-center padding-10"></div>
							<div class="text-center padding-10"></div>
							<div class="text-right padding-10">
								<spring:message code="fuelling.advice.print.footer" />
							</div>
						</div>
						<div class="text-center padding-top-10">
							<button
								onclick="PrintDiv('Refueling Advice Reconciliation Print');"
								class="btn btn-success hidden-print" type="button">
								<i class="fa fa-print"></i>
								<spring:message code="solid.waste.print" text="print" />
							</button>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backRefuelingAdvice();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
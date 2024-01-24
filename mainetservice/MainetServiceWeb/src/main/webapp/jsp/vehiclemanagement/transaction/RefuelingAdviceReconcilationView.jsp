<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/RefuelingAdvice.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="fuelling.advice.form.heading"
					text="Invoice Reconcilation" />
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="fuellingAdvice.html" name="RefuelingAdvice"
				id="RefuelingAdviceForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="vehicle.fuelling.pump.name" /></label>
					<div class="col-sm-4">
						<form:select cssClass="form-control"
							path="vehicleFuelReconciationDTO.puId" id="puId" disabled="true">
							<form:option value="">
								<spring:message code="solid.waste.select" text="Select" />
							</form:option>
							<c:forEach items="${pumps}" var="pump">
								<form:option value="${pump.puId}">${pump.puPumpname}</form:option>
							</c:forEach>

						</form:select>
					</div>
				</div>
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.fromDate" isMandatory="true"
						datePath="vehicleFuelReconciationDTO.inrecFromdt"
						cssClass="custDate " isDisabled="true">
					</apptags:date>
					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.toDate"
						datePath="vehicleFuelReconciationDTO.inrecTodt" isMandatory="true"
						cssClass="custDate " isDisabled="true">
					</apptags:date>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="fuelling.details" text="Fueling Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive clear">
									<table summary="Dumping Ground Data"
										id="vehicleFuellingReconcilation"
										class="table table-bordered table-striped vrr">
										<thead>
											<tr>
												<th><spring:message
														code="vehicle.maintenance.master.id" text="Sr. No." /></th>
												<th><spring:message code="route.master.vehicle.type"
														text="Vehicle Type" /></th>
												<th><spring:message code="vehicle.master.vehicle.no"
														text="Vehicle Reg. No." /></th>

												<th><spring:message code="vehicle.fuelling.adviceno"
														text="Advice No." /></th>

												<th><spring:message code="vehicle.fuelling.adviceDate"
														text="Advice Date" /></th>

												<th><spring:message code="vehicle.fuelling.cost"
														text="Total Cost" /></th>

												<th width="100"><spring:message
														code="refueling.pump.master.isApplicable" /></th>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th align="right"><span style="font-weight: bold"><spring:message
															code="swm.total" text="Total" /></span></th>
												<th align="right"><form:input
														path="vehicleFuelReconciationDTO.sum"
														value="${command.vehicleFuelReconciationDTO.inrecdInvamt}"
														readonly="true" class="form-control text-right"
														id="id_total" /></th>
												<th></th>
											</tr>
										</tfoot>
										<tbody>
											<c:if test="${vehicleFuelReconcilationList.size() > 0}">
												<c:forEach items="${vehicleFuelReconcilationList}"
													var="data" varStatus="status">
													<c:if test="${data.inrecdActive eq 'Y' }">
														<tr class="appendableClass">
															<td align="center">${status.count}</td>
															<td><spring:eval
																	expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.veVetype)"
																	var="lookup" />${lookup.lookUpDesc }</td>
															<%-- <td align="center">${data.veVetype}</td> --%>
															<td align="center">${data.veNo}</td>
															<td align="center">${data.adviceNumber}</td>
															<td align="center"><fmt:formatDate
																	value="${data.adviceDate}" pattern="dd/MM/yyyy" /></td>
															<td id="vefRmamount${status.index}"
																class="vefRmamount${status.index}" align="right">${data.vefRmamount}</td>
															<td class="text-right"><c:set var="amount"
																	value="${data.vefRmamount}" /> <c:set var="index"
																	value="${status.index}" /> <form:checkbox
																	path="vehicleFuelReconciationDTO.tbSwVehiclefuelInrecDets[${status.index}].inrecdActive"
																	id="puApplicable${status.index}" value="Y"
																	cssClass="case" disabled="true" /></td>
														</tr>
													</c:if>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="invoice.details" text="Invoice Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="fuelling.advice.invoiceNo"
										path="vehicleFuelReconciationDTO.inrecdInvno" isMandatory="true"
										cssClass="hasNumber" isDisabled="true"></apptags:input>
									<apptags:date fieldclass="datepicker"
										labelCode="fuelling.advice.invoicedate"
										datePath="vehicleFuelReconciationDTO.inrecdInvdate"
										isMandatory="true" cssClass="custDate" isDisabled="true">
									</apptags:date>
								</div>
								<div class="form-group">
									<apptags:input labelCode="fuelling.advice.invoiceAmount"
										path="vehicleFuelReconciationDTO.inrecdInvamt" isMandatory="true"
										cssClass="hasDecimal text-right" isDisabled="true" />
								
								</div>
								<c:if test="${! command.attachDocsList.isEmpty()}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<th width="8%"><spring:message
														code="population.master.srno" text="Sr. No." /></th>
												<th><spring:message code="scheme.view.document" text="" /></th>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<%-- <td>${lookUp.dmsDocName}</td> --%>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="fuellingAdvice.html?Download" /></td>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<div id="targettypesubmit">
					<div class="text-center padding-top-10">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backRefuelingAdvice();" id="button-Cancel">
							<spring:message code="solid.waste.back" text="Back" />
						</button>

					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>